package com.p6e.germ.cache.ehcache;

import com.p6e.germ.cache.IP6eCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IEhcacheCache extends IP6eCache {

    /**
     * 获取 EhCacheCacheManager 对象
     * @return EhCacheCacheManager 对象
     */
    public EhCacheCacheManager getEhCacheCacheManager();

    /**
     * 实现获取缓存类型的接口
     * @return 缓存的类型
     */
    @Override
    default String toType() {
        return "EHCACHE_TYPE";
    }
}
