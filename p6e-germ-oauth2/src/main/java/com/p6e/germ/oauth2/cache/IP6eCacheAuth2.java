package com.p6e.germ.oauth2.cache;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface IP6eCacheAuth2 {

    /** 缓存名称 */
    String AUTH_NAME = "P6E:AUTH:";

    /** 缓存时间 15分钟 */
    long AUTH_TIME = 900;

    /**
     * 写入客户端凭证数据
     * @param key 凭证数据
     * @param value 客户端编号
     */
    public void setCodeMark(String key, String value);

    public String getCodeMark(String key);
    public void delCodeMark(String key);

    public void setCodeVoucher(String key, String value);
    public String getCodeVoucher(String key);
    public void delCodeVoucher(String key);

    public void setTokenMark(String key, String value);
    public String getTokenMark(String key);
    public void delTokenMark(String key);

}
