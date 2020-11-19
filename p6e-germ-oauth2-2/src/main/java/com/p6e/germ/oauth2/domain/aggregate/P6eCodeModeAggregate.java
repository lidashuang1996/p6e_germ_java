package com.p6e.germ.oauth2.domain.aggregate;

import com.p6e.germ.oauth2.domain.entity.P6eClientEntity;
import com.p6e.germ.oauth2.domain.keyvalue.P6eCodeModeKeyValueParam;
import com.p6e.germ.oauth2.domain.keyvalue.P6eCodeModeKeyValueResult;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheAuth;
import com.p6e.germ.oauth2.infrastructure.exception.ParamException;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;

import java.io.Serializable;

/**
 * CODE 授权认证模式
 * @author lidashuang
 * @version 1.0
 */
public class P6eCodeModeAggregate implements Serializable {

    /**
     * 创建对象
     * @return P6eCodeModeAggregate 对象
     */
    public static P6eCodeModeAggregate create() {
        return new P6eCodeModeAggregate();
    }

    /** 注入缓存对象 */
    private final IP6eCacheAuth p6eCacheAuth = SpringUtil.getBean(IP6eCacheAuth.class);

    /** 私有构造方法 */
    private P6eCodeModeAggregate() { }

    /**
     * 生成 CODE 授权认证模式需要的数据
     * @param param 参数
     * @return 结果
     */
    public P6eCodeModeKeyValueResult generate(final P6eCodeModeKeyValueParam param) {
        final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
        if (p6eClientEntity.verificationScope(param.getScope())
                && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())) {
            final String mark = GeneratorUtil.uuid();
            p6eCacheAuth.setMark(mark, JsonUtil.toJson(param));
            return new P6eCodeModeKeyValueResult(mark, "v-test");
        } else {
            throw new ParamException(this.getClass() + " generate() ==> Failed to verify scope and redirectUri.");
        }
    }

    public void execute(final String mark) {
    }







}
