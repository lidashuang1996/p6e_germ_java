package com.p6e.germ.cache.redis;

import com.p6e.germ.cache.IP6eCache;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis 缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheRedis extends IP6eCache {

    /**
     * 实现获取缓存类型的接口
     * @return 缓存的类型
     */
    @Override
    public default String toType() {
        return "REDIS_TYPE";
    }

    /**
     * 根据数据源名称读取的数据源对象
     * @param source 数据源名称
     * @return 数据源对象
     */
    public StringRedisTemplate getStringRedisTemplate(String source);
}
