package com.p6e.germ.cache.redis;

import com.p6e.germ.common.config.P6eCacheRedisConfig;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisJedisConnector extends P6eCacheRedisConnector {

    /** 客户端线程池配置对象 */
    private final JedisClientConfiguration jedisClientConfiguration;

    /**
     * 构造方法
     * 注入需要的配置文件对象
     * @param p6eCacheRedisConfig 配置文件对象
     */
    public P6eCacheRedisJedisConnector(P6eCacheRedisConfig p6eCacheRedisConfig) {
        super(p6eCacheRedisConfig);
        this.jedisClientConfiguration = createRedisJedisClientConfiguration();
        if (this.jedisClientConfiguration == null) {
            throw new NullPointerException(this.getClass() + " ==> JedisClientConfiguration is null.");
        }
    }


    @Override
    public Map<String, StringRedisTemplate> connect(String source) {
        final Map<String, StringRedisTemplate> result = new HashMap<>(3);
        // 创建配置文件对象
        final RedisClusterConfiguration redisClusterConfiguration = createRedisClusterConfiguration(source);
        final RedisSentinelConfiguration redisSentinelConfiguration = createRedisSentinelConfiguration(source);
        final RedisStandaloneConfiguration redisStandaloneConfiguration = createRedisStandaloneConfiguration(source);
        if (redisStandaloneConfiguration != null) {
            // 创建 LettuceConnectionFactory 对象
            final JedisConnectionFactory jedisConnectionFactory =
                    new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
            jedisConnectionFactory.afterPropertiesSet();
            final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
            stringRedisTemplate.afterPropertiesSet();
            if (source == null) {
                result.put("$DEFAULT.$DEFAULT", stringRedisTemplate);
            } else {
                result.put(source + ".$DEFAULT", stringRedisTemplate);
            }
        }

        if (redisSentinelConfiguration != null) {
            // 创建 LettuceConnectionFactory 对象
            final JedisConnectionFactory jedisConnectionFactory =
                    new JedisConnectionFactory(redisSentinelConfiguration, jedisClientConfiguration);
            jedisConnectionFactory.afterPropertiesSet();
            final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
            stringRedisTemplate.afterPropertiesSet();
            if (source == null) {
                result.put("$DEFAULT.$SENTINEL", stringRedisTemplate);
            } else {
                result.put(source + ".$SENTINEL", stringRedisTemplate);
            }
        }

        if (redisClusterConfiguration != null) {
            // 创建 LettuceConnectionFactory 对象
            final JedisConnectionFactory jedisConnectionFactory =
                    new JedisConnectionFactory(redisClusterConfiguration, jedisClientConfiguration);
            jedisConnectionFactory.afterPropertiesSet();
            final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
            stringRedisTemplate.afterPropertiesSet();
            if (source == null) {
                result.put("$DEFAULT.$CLUSTER", stringRedisTemplate);
            } else {
                result.put(source + ".$CLUSTER", stringRedisTemplate);
            }
        }
        return result;
    }


    /**
     * 创建 RedisConnectionFactory 对象
     * @return RedisConnectionFactory 对象
     */
    protected JedisClientConfiguration createRedisJedisClientConfiguration() {
        final JedisClientConfiguration.DefaultJedisClientConfigurationBuilder jedisBuilder =
                (JedisClientConfiguration.DefaultJedisClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisBuilder.usePooling();
        jedisBuilder.poolConfig(createGenericObjectPoolConfig());
        jedisBuilder.connectTimeout(p6eCacheRedisConfig.getTimeout());
        return jedisBuilder.build();
    }
}
