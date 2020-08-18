package com.p6e.germ.cache;

import com.p6e.germ.cache.ehcache.EhcacheCacheTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 多数据源的缓存的配置
 * @author lidashuang
 * @version 1.0
 */
@SpringBootApplication
public class GermCacheApplication {

    public static void main(String[] args) throws InterruptedException {
        EhcacheCacheTest cacheAuth = SpringApplication.run(GermCacheApplication.class, args).getBean(EhcacheCacheTest.class);
        cacheAuth.setData();
        System.out.println(cacheAuth.getData());
        // 缓存
        Thread.sleep(6 * 1000);
        System.out.println(cacheAuth.getData());
    }

}
