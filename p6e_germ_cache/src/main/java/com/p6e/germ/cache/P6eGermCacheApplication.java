package com.p6e.germ.cache;

import com.p6e.germ.cache.redis.P6eCacheRedisAbstract;
import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.common.utils.P6eJsonUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 多数据源的缓存的配置
 * @author lidashuang
 * @version 1.0
 */
@SpringBootApplication
public class P6eGermCacheApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(P6eGermCacheApplication.class, args);


        P6eConfig config = context.getBean(P6eConfig.class);
        System.out.println(P6eJsonUtil.toJson(config.getCache().getRedis()));

        P6eCacheRedisAbstract.setConfig(config.getCache().getRedis());
        System.out.println(P6eCacheRedisAbstract.getStringRedisTemplate("A", 0));
    }

}
