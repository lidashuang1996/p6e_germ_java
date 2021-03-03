package com.p6e.germ.oauth2.infrastructure.cache;

import com.p6e.germ.cache.redis.P6eCacheRedisAbstract;
import com.p6e.germ.common.config.P6eConfig;
import com.p6e.germ.common.utils.P6eSpringUtil;

/**
 * 缓存接口的 redis 的实现
 * @author lidashuang
 * @version 1.0
 */
public abstract class P6eCacheRedis extends P6eCacheRedisAbstract {

    static {
        try {
            setConfig(P6eSpringUtil.getBean(P6eConfig.class).getCache().getRedis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}