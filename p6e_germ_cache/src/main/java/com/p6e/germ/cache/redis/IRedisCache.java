package com.p6e.germ.cache.redis;

import com.p6e.germ.cache.ICache;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IRedisCache extends ICache {

    /**
     * 获取缓存数据
     * @return 缓存数据列表
     */
    public List<String> getCache();

    /**
     * 根据数据源名称获取数据源
     * @param key 数据源名称
     * @return 数据源对象
     */
    public StringRedisTemplate getRedisTemplate(String key);

    /**
     * 实现获取缓存类型的接口
     * @return 缓存的类型
     */
    @Override
    default String toType() {
        return "REDIS_TYPE";
    }
}
