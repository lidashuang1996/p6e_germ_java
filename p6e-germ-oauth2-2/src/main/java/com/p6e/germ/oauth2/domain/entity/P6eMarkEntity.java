package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.domain.keyvalue.P6eMarkKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheMark;
import com.p6e.germ.oauth2.infrastructure.cache.P6eCache;
import com.p6e.germ.oauth2.infrastructure.utils.P6eGeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.P6eJsonUtil;
import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eMarkEntity implements Serializable {

    /** 标记记号 */
    private final String mark;

    /** auth key/value */
    private final P6eMarkKeyValue p6eMarkKeyValue;

    /** 注入缓存服务 */
    private final IP6eCacheMark p6eCacheMark = P6eCache.mark;

    /**
     * 读取 mark 数据
     * @param mark 参数数据
     */
    public P6eMarkEntity(String mark) {
        try {
            // 赋值，读取数据
            this.mark = mark;
            final String content = p6eCacheMark.get(mark);
            if (content == null) {
                throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
            } else {
                // 序列化数据为对象
                this.p6eMarkKeyValue = P6eJsonUtil.fromJson(content, P6eMarkKeyValue.class);
                if (this.p6eMarkKeyValue == null) {
                    throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
                }
            }
        } catch (Exception e) {
            throw new NullPointerException(this.getClass() + " construction data ==> " + e.getMessage());
        }
    }

    /**
     * 写入 mark 数据
     * @param p6eAuthKeyValue 写入的数据对象
     */
    public P6eMarkEntity(P6eMarkKeyValue p6eAuthKeyValue) {
        this.mark = P6eGeneratorUtil.uuid();
        this.p6eMarkKeyValue = p6eAuthKeyValue;
        if (this.p6eMarkKeyValue == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    /**
     * 缓存
     */
    public P6eMarkEntity cache() {
        p6eCacheMark.set(mark, P6eJsonUtil.toJson(p6eMarkKeyValue));
        return this;
    }

    /**
     * 清除缓存
     */
    public void clean() {
        p6eCacheMark.del(mark);
    }

    /**
     * 读取记号
     * @return 记号数据
     */
    public String getMark() {
        return mark;
    }

    /**
     * 读取标记对象
     * @return 标记对象
     */
    public P6eMarkKeyValue getP6eMarkKeyValue() {
        return p6eMarkKeyValue;
    }
}
