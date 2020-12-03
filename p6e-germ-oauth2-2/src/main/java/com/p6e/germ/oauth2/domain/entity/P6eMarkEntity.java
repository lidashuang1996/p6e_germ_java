package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheMark;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;
import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eMarkEntity implements Serializable {

    /** 标记记号 */
    private final String mark;

    /** auth key/value */
    private final P6eAuthKeyValue p6eAuthKeyValue;

    /** 注入缓存服务 */
    private final IP6eCacheMark p6eCacheMark = P6eSpringUtil.getBean(IP6eCacheMark.class);

    /**
     * 读取 mark 数据
     * @param mark 参数数据
     */
    public P6eMarkEntity(String mark) {
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
        } catch (Exception e) {
            throw new RuntimeException(this.getClass() + " construction ==> AnalysisException.");
        }
    }

    /**
     * 写入 mark 数据
     * @param p6eAuthKeyValue 写入的数据对象
     */
    public P6eMarkEntity(P6eAuthKeyValue p6eAuthKeyValue) {
        this.mark = GeneratorUtil.uuid();
        this.p6eAuthKeyValue = p6eAuthKeyValue;
        if (this.p6eAuthKeyValue == null) {
            throw new NullPointerException(this.getClass() + " construction fetch data ==> NullPointerException.");
        }
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

    public String getMark() {
        return mark;
    }

    public P6eAuthKeyValue getP6eAuthKeyValue() {
        return p6eAuthKeyValue;
    }
}
