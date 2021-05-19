package com.p6e.germ.jurisdiction.infrastructure.cache;

import com.p6e.germ.cache.redis.P6eCacheRedisAbstract;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eCacheRedisJurisdiction extends P6eCacheRedisAbstract implements IP6eCacheJurisdiction {

    /** 数据源的名称 */
    private static final String SOURCE = "JURISDICTION-CACHE";

    /** 缓存名称 */
    private static final String NAME = "P6E:JURISDICTION:USER:";

    /** 缓存时间 5分钟 */
    private static final long TIME = 300;

    @Override
    public void set(String key, String value) {
        this.getStringRedisTemplate(SOURCE).opsForValue().set(NAME + key, value, TIME, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return this.getStringRedisTemplate(SOURCE).opsForValue().get(NAME + key);
    }

    @Override
    public void del(String key) {
        this.getStringRedisTemplate(SOURCE).delete(NAME + key);
    }
}
