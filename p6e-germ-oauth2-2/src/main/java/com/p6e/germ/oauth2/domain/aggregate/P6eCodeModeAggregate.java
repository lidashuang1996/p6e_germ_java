package com.p6e.germ.oauth2.domain.aggregate;

import com.p6e.germ.oauth2.domain.entity.P6eClientEntity;
import com.p6e.germ.oauth2.domain.entity.P6eMarkEntity;
import com.p6e.germ.oauth2.domain.entity.P6eVoucherEntity;
import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eCodeAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.cache.IP6eCacheAuth;
import com.p6e.germ.oauth2.infrastructure.exception.ParamException;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;

import java.io.Serializable;

/**
 * CODE 授权认证模式
 * @author lidashuang
 * @version 1.0
 */
public class P6eCodeModeAggregate implements Serializable {

    /** 唯一标记 */
    private final String uniqueId;

    /** 注入缓存对象 */
    private final IP6eCacheAuth p6eCacheAuth = SpringUtil.getBean(IP6eCacheAuth.class);

    /**
     * 创建对象
     * @return P6eCodeModeAggregate 对象
     */
    public static P6eCodeModeAggregate create() {
        return new P6eCodeModeAggregate();
    }

    /** 私有构造方法 */
    private P6eCodeModeAggregate() {
        uniqueId = GeneratorUtil.uuid();
    }

    /**
     * 生成 CODE 授权认证模式需要的数据
     * @param param 参数
     * @return 结果
     */
    public P6eCodeAuthKeyValue generate(final P6eAuthKeyValue param) {
        final P6eClientEntity p6eClientEntity = new P6eClientEntity(param.getClientId());
        if (p6eClientEntity.verificationScope(param.getScope())
                && p6eClientEntity.verificationRedirectUri(param.getRedirectUri())) {
            return new P6eCodeAuthKeyValue(
                    P6eMarkEntity.create(GeneratorUtil.uuid(), param).getMark(),
                    P6eVoucherEntity.create(GeneratorUtil.uuid()).getKey()
            );
        } else {
            throw new ParamException(this.getClass() + " generate() ==> Failed to verify scope and redirectUri.");
        }
    }

    public void execute(final String mark) {
    }







}
