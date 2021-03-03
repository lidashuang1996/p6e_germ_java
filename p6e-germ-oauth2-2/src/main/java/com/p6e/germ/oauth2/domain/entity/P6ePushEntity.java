package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.oauth2.domain.keyvalue.P6ePushKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCachePush;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6ePushEntity {

    public static final String SMS_LOGIN_TYPE = "SMS_LOGIN_TYPE";
    public static final String EMAIL_LOGIN_TYPE = "EMAIL_LOGIN_TYPE";
    private static final Logger LOGGER = LoggerFactory.getLogger(P6ePushEntity.class);

    /**
     * UID
     */
    private Integer uid;

    /**
     * 验证码的 KEY
     */
    private String key;

    /**
     * 生成的验证码
     */
    private String code;

    /**
     * 类型
     */
    private String type;

    /**
     * 缓存对象
     */
    private final IP6eCachePush p6eCachePush = P6eCache.push;

    public P6ePushEntity setCache(Integer uid, String type) {
        this.uid = uid;
        this.type = type;
        if (this.uid == null || this.type == null) {
            throw new NullPointerException(this.getClass() + " setCache data ==> NullPointerException.");
        }
        this.key = P6eGeneratorUtil.uuid();
        this.code = P6eGeneratorUtil.uuid();
        final P6ePushKeyValue p6ePushKeyValue = new P6ePushKeyValue();
        p6ePushKeyValue.setKey(this.key);
        p6ePushKeyValue.setUid(this.uid);
        p6ePushKeyValue.setCode(this.code);
        p6ePushKeyValue.setType(this.type);
        p6eCachePush.set(key, P6eJsonUtil.toJson(p6ePushKeyValue));
        return this;
    }

    public P6ePushEntity getCache(String key) {
        final String context = p6eCachePush.get(key);
        if (context == null) {
            throw new NullPointerException(this.getClass() + " getCache data ==> NullPointerException.");
        }
        try {
            final P6ePushKeyValue p6ePushKeyValue = P6eJsonUtil.fromJson(context, P6ePushKeyValue.class);
            this.key = p6ePushKeyValue.getKey();
            this.uid = p6ePushKeyValue.getUid();
            this.code = p6ePushKeyValue.getCode();
            this.type = p6ePushKeyValue.getType();
        } catch (Exception e){
            throw new NullPointerException(this.getClass() + " getCache data ==> NullPointerException.");
        }
        return this;
    }

    public void smsPush(String context) {
        // 通过短信的方式推送信息
        LOGGER.info(" [ SMS ] PUSH ==> SMS: " + context + ", TEXT: " + code);
    }

    public void emailPush(String context) {
        // 通过邮件的方式推送信息
        LOGGER.info(" [ EMAIL ] PUSH ==> EMAIL: " + context + ", TEXT: " + code);
    }

    public boolean verification(String code, String type) {
        return this.code != null && this.type != null
                && this.code.equals(code) && this.type.equals(type);
    }

    public void clean() {
        if (key != null) {
            p6eCachePush.del(key);
        }
    }

    public Integer getUid() {
        return uid;
    }
}
