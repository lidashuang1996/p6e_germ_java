package com.p6e.germ.cache.redis;

import com.p6e.germ.common.config.P6eCacheRedisConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Redis 缓存的连接器抽象类
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eCacheRedisConnector {

    /** 注入的配置文件对象 */
    protected final P6eCacheRedisConfig p6eCacheRedisConfig;

    /**
     * 构造方法
     * 注入配置文件参数对象
     * @param p6eCacheRedisConfig 配置文件参数对象
     */
    public P6eCacheRedisConnector(P6eCacheRedisConfig p6eCacheRedisConfig) {
        this.p6eCacheRedisConfig = p6eCacheRedisConfig;
        if (this.p6eCacheRedisConfig == null) {
            throw new NullPointerException(this.getClass()
                    + " ==> " + P6eCacheRedisConfig.class + " config is null.");
        }
    }

    /**
     * 连接 Redis 服务端
     * @param source 数据源
     * @return Map<String, StringRedisTemplate> 对象
     */
    public abstract Map<String, StringRedisTemplate> connect(String source);

    /**
     * 创建 RedisStandaloneConfiguration 对象
     * @return RedisStandaloneConfiguration 对象
     */
    protected RedisStandaloneConfiguration createRedisStandaloneConfiguration(String source) {
        final P6eCacheRedisConfig.Node node;
        if (source == null) {
            node = p6eCacheRedisConfig.getNode();
        } else {
            final Map<String, P6eCacheRedisConfig.Node> nodeMap = p6eCacheRedisConfig.getSource();
            if (nodeMap == null) {
                return null;
            }
            node = nodeMap.get(source);
        }
        if (node == null) {
            return null;
        } else {
            final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
            configuration.setPort(node.getPort());
            configuration.setHostName(node.getHost());
            configuration.setDatabase(node.getDatabase());
            if (node.getPassword() != null)  {
                configuration.setPassword(node.getPassword());
            }
            return configuration;
        }
    }

    /**
     * 创建 RedisSentinelConfiguration 对象
     * @return RedisSentinelConfiguration 对象
     */
    protected RedisSentinelConfiguration createRedisSentinelConfiguration(String source) {
        final P6eCacheRedisConfig.Node node;
        if (source == null) {
            node = p6eCacheRedisConfig.getNode();
            if (node == null || node.getSentinel() == null) {
                return null;
            }
        } else {
            final Map<String, P6eCacheRedisConfig.Node> nodeMap = p6eCacheRedisConfig.getSource();
            if (nodeMap == null || (node = nodeMap.get(source)) == null || node.getSentinel() == null) {
                return null;
            }
        }
        final P6eCacheRedisConfig.Node.Sentinel sentinel = node.getSentinel();
        final RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration.setDatabase(node.getDatabase());
        if (node.getPassword() != null) {
            redisSentinelConfiguration.setPassword(node.getPassword());
        }
        redisSentinelConfiguration.setMaster(sentinel.getMaster());
        final List<RedisNode> redisNodeList = sentinel.getNodes().stream().map(s -> {
            try {
                final String[] n = s.split(":");
                return new RedisNode(n[0], Integer.parseInt(n[1]));
            } catch (Exception e) {
                throw new NullPointerException(this.getClass()
                        + " ==> " + P6eCacheRedisConfig.Node.Sentinel.class + " sentinel nodes is null.");
            }
        }).collect(Collectors.toList());
        redisSentinelConfiguration.setSentinels(redisNodeList);
        if (sentinel.getPassword() != null) {
            redisSentinelConfiguration.setSentinelPassword(sentinel.getPassword());
        }
        return redisSentinelConfiguration;
    }

    /**
     * 创建 RedisClusterConfiguration 对象
     * @return RedisClusterConfiguration 对象
     */
    protected RedisClusterConfiguration createRedisClusterConfiguration(String source) {
        final P6eCacheRedisConfig.Node node;
        if (source == null) {
            node = p6eCacheRedisConfig.getNode();
            if (node == null || node.getCluster() == null) {
                return null;
            }
        } else {
            final Map<String, P6eCacheRedisConfig.Node> nodeMap = p6eCacheRedisConfig.getSource();
            if (nodeMap == null || (node = nodeMap.get(source)) == null || node.getCluster() == null) {
                return null;
            }
        }
        final P6eCacheRedisConfig.Node.Cluster cluster = node.getCluster();
        final RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        final List<RedisNode> redisNodeList = cluster.getNodes().stream().map(s -> {
            try {
                final String[] n = s.split(":");
                return new RedisNode(n[0], Integer.parseInt(n[1]));
            } catch (Exception e) {
                throw new NullPointerException(this.getClass()
                        + " ==> " + P6eCacheRedisConfig.Node.Cluster.class + " cluster nodes is null.");
            }
        }).collect(Collectors.toList());
        redisClusterConfiguration.setClusterNodes(redisNodeList);
        redisClusterConfiguration.setMaxRedirects(cluster.getMaxRedirects());
        if (node.getPassword() != null) {
            redisClusterConfiguration.setPassword(node.getPassword());
        }
        return redisClusterConfiguration;
    }

    /**
     * 创建 GenericObjectPoolConfig 对象
     * @return GenericObjectPoolConfig 对象
     */
    protected GenericObjectPoolConfig<?> createGenericObjectPoolConfig() {
        final P6eCacheRedisConfig.Pool pool = p6eCacheRedisConfig.getPool();
        if (pool == null) {
            return null;
        } else {
            final GenericObjectPoolConfig<?> genericObjectPoolConfig = new GenericObjectPoolConfig<>();
            genericObjectPoolConfig.setMaxIdle(pool.getMaxIdle());
            genericObjectPoolConfig.setMinIdle(pool.getMinIdle());
            genericObjectPoolConfig.setMaxTotal(pool.getMaxActive());
            genericObjectPoolConfig.setMaxWaitMillis(pool.getMaxWait().toMillis());
            genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(pool.getTimeBetweenEvictionRuns().toMillis());
            return genericObjectPoolConfig;
        }
    }
}
