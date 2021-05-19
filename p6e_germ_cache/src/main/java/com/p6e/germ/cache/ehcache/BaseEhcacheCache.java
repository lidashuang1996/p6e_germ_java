package com.p6e.germ.cache.ehcache;

import org.springframework.cache.ehcache.EhCacheCacheManager;

/**
 * 以后更新
 * @author lidashuang
 * @version 1.0
 */
public abstract class BaseEhcacheCache implements IEhcacheCache {

    /** 创建 EhCacheCacheManager 对象 */
    private static final EhCacheCacheManager EH = new EhCacheCacheManager();
    static {
        EH.afterPropertiesSet();
    }

    /**
     * 获取 EhCacheCacheManager 对象
     * @return EhCacheCacheManager 对象
     */
    @Override
    public EhCacheCacheManager getEhCacheCacheManager() {
        return EH;
    }

}
