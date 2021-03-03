package com.p6e.germ.cache.redis;

import com.p6e.germ.common.config.P6eCacheRedisConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eCacheRedisLettuceConnector extends P6eCacheRedisConnector {

    /** 客户端线程池配置对象 */
    private final LettucePoolingClientConfiguration lettucePoolingClientConfiguration;

    /**
     * 构造方法
     * 注入需要的配置文件对象
     * @param p6eCacheRedisConfig 配置文件对象
     */
    public P6eCacheRedisLettuceConnector(P6eCacheRedisConfig p6eCacheRedisConfig) {
        super(p6eCacheRedisConfig);
        this.lettucePoolingClientConfiguration = createRedisLettucePoolingClientConfiguration();
        if (this.lettucePoolingClientConfiguration == null) {
            throw new NullPointerException(this.getClass() + " ==> LettucePoolingClientConfiguration is null.");
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
            final LettuceConnectionFactory lettuceConnectionFactory =
                    new LettuceConnectionFactory(redisStandaloneConfiguration, lettucePoolingClientConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
            stringRedisTemplate.afterPropertiesSet();
            if (source == null) {
                result.put("$DEFAULT.$DEFAULT", stringRedisTemplate);
            } else {
                result.put(source + ".$DEFAULT", stringRedisTemplate);
            }
        }

        if (redisSentinelConfiguration != null) {
            // 创建 LettuceConnectionFactory 对象
            final LettuceConnectionFactory lettuceConnectionFactory =
                    new LettuceConnectionFactory(redisSentinelConfiguration, lettucePoolingClientConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
            stringRedisTemplate.afterPropertiesSet();
            if (source == null) {
                result.put("$DEFAULT.$SENTINEL", stringRedisTemplate);
            } else {
                result.put(source + ".$SENTINEL", stringRedisTemplate);
            }
        }

        if (redisClusterConfiguration != null) {
            // 创建 LettuceConnectionFactory 对象
            final LettuceConnectionFactory lettuceConnectionFactory =
                    new LettuceConnectionFactory(redisClusterConfiguration, lettucePoolingClientConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
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
    protected LettucePoolingClientConfiguration createRedisLettucePoolingClientConfiguration() {
        final LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                lettucePoolingClientConfigurationBuilder = LettucePoolingClientConfiguration.builder();
        if (p6eCacheRedisConfig.isSsl()) {
            lettucePoolingClientConfigurationBuilder.useSsl();
        }
        lettucePoolingClientConfigurationBuilder.clientName(p6eCacheRedisConfig.getClientName());
        lettucePoolingClientConfigurationBuilder.commandTimeout(p6eCacheRedisConfig.getTimeout());
        lettucePoolingClientConfigurationBuilder.shutdownTimeout(p6eCacheRedisConfig.getShutdownTimeout());
        final GenericObjectPoolConfig<?> genericObjectPoolConfig = createGenericObjectPoolConfig();
        if (genericObjectPoolConfig == null) {
            return null;
        } else {
            lettucePoolingClientConfigurationBuilder.poolConfig(genericObjectPoolConfig);
            return lettucePoolingClientConfigurationBuilder.build();
        }
    }
}
