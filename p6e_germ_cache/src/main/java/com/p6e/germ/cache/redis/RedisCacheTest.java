package com.p6e.germ.cache.redis;

import com.p6e.germ.cache.ICacheTest;
import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
@Component
public class RedisCacheTest extends BaseRedisCache implements ICacheTest {

    private static final String SOURCE_NAME1 = "source-1";
    private static final String SOURCE_NAME2 = "source-2";

    @Override
    public String getData() {
        return this.getRedisTemplate(SOURCE_NAME1).opsForValue().get("TEST_DATA")
                + "-" + this.getRedisTemplate(SOURCE_NAME2).opsForValue().get("TEST_DATA");
    }

    @Override
    public void setData() {
        this.getRedisTemplate(SOURCE_NAME1).opsForValue().set("TEST_DATA", "TEST_AAA");
        this.getRedisTemplate(SOURCE_NAME2).opsForValue().set("TEST_DATA", "TEST_BBB");
    }
}
