package com.p6e.germ.security.cache;

import java.util.Map;

/**
 * 认证缓存的接口
 * @author lidashuang
 * @version 1.0
 */
public interface P6eCacheAuth {

    static final String USER_NAME = "P6E:GERM:AUTH:USER:";
    static final String USER_TOKEN_NAME = "P6E:GERM:AUTH:USER:TOKEN:";
    static final String USER_REFRESH_TOKEN_NAME = "P6E:GERM:AUTH:USER:REFRESH_TOKEN:";
    static final String TOKEN_NAME = "P6E:GERM:AUTH:TOKEN:";
    static final String REFRESH_TOKEN_NAME = "P6E:GERM:AUTH:REFRESH_TOKEN:";
    static final long EXPIRATION_TIME = 604800L;

    /**
     * 写入缓存数据
     * @param map 缓存对象
     * @return 返回的对象
     */
    public Map<String, Object> setData(Map<String, Object> map);

    /**
     * 根据 token 删除数据
     * @param token token 参数
     * @return 删除的结果
     */
    public Map<String, Object> delDataByToken(String token);

    /**
     * 根据 token 获取数据
     * @param token token 参数
     * @return 获取的结果
     */
    public Map<String, Object> getDataByToken(String token);
}
