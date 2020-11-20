package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheMark;
import com.p6e.germ.oauth2.infrastructure.exception.AnalysisException;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eMarkEntity implements Serializable {

    /** 标记记号 */
    @Getter
    private final String mark;

    /** auth key/value */
    @Getter
    private final P6eAuthKeyValue p6eAuthKeyValue;

    /** 唯一标记 */
    private final String uniqueId;

    /** 注入缓存服务 */
    private final IP6eCacheMark p6eCacheMark = SpringUtil.getBean(IP6eCacheMark.class);

    /**
     * 读取方式获取对象
     * @param mark 记号
     * @return P6eMarkEntity 对象
     */
    public static P6eMarkEntity fetch(String mark) {
        return new P6eMarkEntity(mark);
    }

    /**
     * 创建方式获取对象
     * @param mark 记号
     * @return P6eMarkEntity 对象
     */
    public static P6eMarkEntity create(String mark, P6eAuthKeyValue p6eAuthKeyValue) {
        return new P6eMarkEntity(mark, p6eAuthKeyValue);
    }

    /**
     * 读取 mark 数据
     * @param mark 参数数据
     */
    private P6eMarkEntity(String mark) {
        try {
            this.mark = mark;
            final String content = p6eCacheMark.get(mark);
            if (content == null) {
                throw new NullPointerException(this.getClass() + " construction fetch data ==> NullPointerException.");
            } else {
                this.p6eAuthKeyValue = JsonUtil.fromJson(content, P6eAuthKeyValue.class);
                if (this.p6eAuthKeyValue == null) {
                    throw new NullPointerException(this.getClass() + " construction fetch data ==> NullPointerException.");
                }
            }
            this.uniqueId = GeneratorUtil.uuid();
        } catch (Exception e) {
            throw new AnalysisException(this.getClass() + " construction ==> AnalysisException.");
        }
    }

    /**
     * 写入 mark 数据
     * @param mark 记号
     * @param p6eAuthKeyValue 写入的数据对象
     */
    private P6eMarkEntity(String mark, P6eAuthKeyValue p6eAuthKeyValue) {
        this.mark = mark;
        this.p6eAuthKeyValue = p6eAuthKeyValue;
        if (this.p6eAuthKeyValue == null) {
            throw new NullPointerException(this.getClass() + " construction fetch data ==> NullPointerException.");
        }
        this.cache();
        this.uniqueId = GeneratorUtil.uuid();
    }

    /**
     * 缓存
     */
    public void cache() {
        p6eCacheMark.set(mark, JsonUtil.toJson(p6eAuthKeyValue));
    }

    /**
     * 清除缓存
     */
    public void clean() {
        p6eCacheMark.del(mark);
    }
}
