package com.p6e.germ.cache;

import com.p6e.germ.cache.config.P6eConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 多数据源的缓存的配置
 * @author lidashuang
 * @version 1.0
 */
@SpringBootApplication
public class P6eGermCacheApplication {

    public static void main(String[] args) {
        System.out.println(SpringApplication.run(P6eGermCacheApplication.class, args).getBean(P6eConfig.class).getCache().getRedis().getType());
    }

}
