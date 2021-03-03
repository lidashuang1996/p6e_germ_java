package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eStateEntity {
    /** 类型 */
    public static final String QQ_TYPE = "QQ_TYPE";
    public static final String WE_CHAT_TYPE = "WE_CHAT_TYPE";

    /** mark*/
    private final String mark;
    /** 类型 */
    private final String type;
    /** 状态 */
    private final String state;

    public P6eStateEntity(String state, String type) {
        this.state = state;
        switch (type) {
            case QQ_TYPE:
                this.type = type;
                this.mark = P6eCache.state.getQq(this.state);
                break;
            case WE_CHAT_TYPE:
                this.type = type;
                this.mark = P6eCache.state.getWeChat(this.state);
                break;
            default:
                throw new NullPointerException();
        }
        if (this.mark == null) {
            throw new NullPointerException();
        }
    }

    public P6eStateEntity(String state, String mark, String type) {
        this.mark = mark;
        this.state = state;
        if (this.mark == null || this.state == null) {
            throw new NullPointerException();
        }
        switch (type) {
            case QQ_TYPE:
            case WE_CHAT_TYPE:
                this.type = type;
                break;
            default:
                throw new NullPointerException();
        }
    }

    public P6eStateEntity cache() {
        switch (this.type) {
            case QQ_TYPE:
                P6eCache.state.setQq(this.state, this.mark);
                break;
            case WE_CHAT_TYPE:
                P6eCache.state.setWeChat(this.state, this.mark);
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public P6eStateEntity clean() {
        switch (this.type) {
            case QQ_TYPE:
                P6eCache.state.delQq(this.state);
                break;
            case WE_CHAT_TYPE:
                P6eCache.state.delWeChat(this.state);
                break;
            default:
                throw new RuntimeException();
        }
        return this;
    }

    public String getState() {
        return state;
    }

    public String getMark() {
        return mark;
    }

    public String getType() {
        return type;
    }


}
