package com.p6e.germ.cache.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现多数据源管理连接
 * @author lidashuang
 * @version 1.0
 */
@Component
public abstract class BaseRedisCache implements IRedisCache {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRedisCache.class);

    /** 缓存连接 redis 的连接对象的地方 */
    private static final Map<String, Model> REDIS_MODEL = new ConcurrentHashMap<>();

    private static final String MODE_JEDIS = "jedis";
    private static final String MODE_LETTUCE = "lettuce";

    /**
     * 注入的配置文件的信息
     */
    @Resource
    private RedisConfig redisConfig;

    /**
     * 初始化 redis 连接的地方
     * 避免一个 redis 连接对象多次创建，该方法需要上锁
     * @param key 初始化数据源的 key
     * @return 创建的 redis model 对象
     */
    private synchronized Model init(final String key) {
        // 查询到数据则返回数据
        if (REDIS_MODEL.get(key) != null) {
            return REDIS_MODEL.get(key);
        } else {
            // 读取全局的 pool 配置
            final RedisProperties.Pool pool = redisConfig.getPool();
            // 读取数据源
            final Map<String, RedisProperties> dataSource = redisConfig.getDataSource();
            if (dataSource != null && dataSource.get(key) != null) {
                // 读取当前数据源的配置信息
                final RedisProperties redisProperties = dataSource.get(key);
                String mode = MODE_LETTUCE;
                if (redisProperties.getJedis() != null
                        && redisProperties.getJedis().getPool() != null) {
                    mode = MODE_JEDIS;
                }
                if (pool != null) {
                    if (MODE_JEDIS.equals(mode)) {
                        redisProperties.getJedis().setPool(pool);
                    } else {
                        redisProperties.getLettuce().setPool(pool);
                    }
                }
                // 缓存连接 redis 成功的对象
                final Model model = new Model(key.toLowerCase(),
                        redisProperties.toString(), this.createRedisTemplate(redisProperties, mode));
                REDIS_MODEL.put(key, model);
                return model;
            } else {
                throw new NullPointerException("Redis data configuration data not found.");
            }
        }
    }

    /**
     * 创建 redis 连接对象
     * @param redisProperties redis 连接的配置文件
     * @return 创建好的连接对象
     */
    private StringRedisTemplate createRedisTemplate(final RedisProperties redisProperties, final String mode) {
        LOGGER.info("create redis template ==> start, [ " + mode + " ] " + redisProperties);
        /* ========= 基本配置 ========= */
        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPort(redisProperties.getPort());
        configuration.setHostName(redisProperties.getHost());
        configuration.setDatabase(redisProperties.getDatabase());
        // 判断密码是否存在
        final String password = redisProperties.getPassword();
        if (password != null && !"".equals(password))  {
            configuration.setPassword(password);
        }
        /* ========= 连接池通用配置 ========= */
        final GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
        if (MODE_JEDIS.equals(mode)) {
            genericObjectPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
            genericObjectPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
            genericObjectPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
            genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getJedis().getPool().getMaxWait().toMillis());
        } else {
            genericObjectPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
            genericObjectPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
            genericObjectPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
            genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
        }
        RedisConnectionFactory connection;
        if (MODE_JEDIS.equals(mode)) {
            /* ========= jedis pool ========= */
            JedisClientConfiguration.DefaultJedisClientConfigurationBuilder jedisBuilder =
                    (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration.builder();
            jedisBuilder.usePooling();
            jedisBuilder.poolConfig(genericObjectPoolConfig);
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration, jedisBuilder.build());
            jedisConnectionFactory.afterPropertiesSet();
            connection = jedisConnectionFactory;
        } else {
            /* ========= lettuce pool ========= */
            final LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                    lettuceBuilder = LettucePoolingClientConfiguration.builder();
            lettuceBuilder.poolConfig(genericObjectPoolConfig);
            lettuceBuilder.commandTimeout(redisProperties.getTimeout());
            LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(configuration, lettuceBuilder.build());
            lettuceConnectionFactory.afterPropertiesSet();
            connection = lettuceConnectionFactory;
        }
        /* ========= redis template create ========= */
        final StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(connection);
        redisTemplate.afterPropertiesSet();
        LOGGER.info("create redis template ==> end, redisTemplate:" + redisTemplate);
        return redisTemplate;
    }

    /**
     * 获取一个 redis template 对象
     * @param key 名称
     * @return redis template 对象
     */
    @Override
    public StringRedisTemplate getRedisTemplate(final String key) {
        // 读取数据源的配置信息
        final Map<String, RedisProperties> dataSource = redisConfig.getDataSource();
        if (dataSource != null && dataSource.get(key) != null) {
            // 读取 model 内容
            final Model model = REDIS_MODEL.get(key);
            if (model == null || model.getRedisTemplate() == null) {
                return init(key).getRedisTemplate();
            } else {
                return model.getRedisTemplate();
            }
        } else {
            // 找不到对应的数据源
            throw new NullPointerException("The corresponding data source configuration was not found.");
        }
    }

    @Override
    public List<String> getCache() {
        final List<String> list = new ArrayList<>();
        for (String key : REDIS_MODEL.keySet()) {
            list.add(REDIS_MODEL.get(key).toString());
        }
        return list;
    }


    /**
     * 这个是 redis 连接模型的类
     */
    public static class Model {
        private String name;
        private String config;
        private StringRedisTemplate redisTemplate;

        private Model(String name, String config, StringRedisTemplate redisTemplate) {
            this.name = name;
            this.config = config;
            this.redisTemplate = redisTemplate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getConfig() {
            return config;
        }

        public void setConfig(String config) {
            this.config = config;
        }

        public StringRedisTemplate getRedisTemplate() {
            return redisTemplate;
        }

        public void setRedisTemplate(StringRedisTemplate redisTemplate) {
            this.redisTemplate = redisTemplate;
        }
    }
}
