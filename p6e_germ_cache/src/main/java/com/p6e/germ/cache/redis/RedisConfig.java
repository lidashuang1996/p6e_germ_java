package com.p6e.germ.cache.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 读取 yml 里面的配置信息
 * 当前读取的是为 redis 节点下的数据
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    /** 数据源的配置文件 */
    private Map<String, RedisProperties> dataSource;

    /** 全局的线程池配置 */
    private RedisProperties.Pool pool;

    public Map<String, RedisProperties> getDataSource() {
        return dataSource;
    }

    public void setDataSource(Map<String, RedisProperties> dataSource) {
        this.dataSource = dataSource;
    }

    public RedisProperties.Pool getPool() {
        return pool;
    }

    public void setPool(RedisProperties.Pool pool) {
        this.pool = pool;
    }

}
