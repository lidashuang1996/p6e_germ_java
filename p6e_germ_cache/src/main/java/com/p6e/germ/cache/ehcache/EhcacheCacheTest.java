package com.p6e.germ.cache.ehcache;

import com.p6e.germ.cache.ICacheTest;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class EhcacheCacheTest extends BaseEhcacheCache implements ICacheTest {

    @Override
    public String getData() {
        Cache cache = this.getEhCacheCacheManager().getCache("CCC");
        return cache.get("LDS").get().toString();
    }

    @Override
    public void setData() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("CCC");
        cacheConfiguration.setMaxElementsInMemory(1000);
        cacheConfiguration.setEternal(false);
        cacheConfiguration.setTimeToIdleSeconds(5);
        cacheConfiguration.setTimeToLiveSeconds(5);
        cacheConfiguration.setOverflowToDisk(false);
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
        this.getEhCacheCacheManager().getCacheManager().addCacheIfAbsent(new net.sf.ehcache.Cache(cacheConfiguration));
        Cache cache = this.getEhCacheCacheManager().getCache("CCC");
        System.out.println(cache);
        System.out.println(cache.getName());
        cache.put("LDS", "456");
    }

}
