package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.domain.service.P6eDefaultLoginPasswordService;
import com.p6e.germ.oauth2.infrastructure.repository.mapper.P6eUserMapper;
import com.p6e.germ.oauth2.infrastructure.utils.CopyUtil;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import com.p6e.germ.oauth2.infrastructure.utils.SpringUtil;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eUserEntity implements Serializable {

    @Getter
    private Integer id;
    @Getter
    private String email;
    @Getter
    private String phone;
    @Getter
    private String password;
    @Getter
    private String qq;
    @Getter
    private String weChat;

    /** 唯一标记 */
    private final String uniqueId;

    /** 注入 DB 操作对象 */
    private final P6eUserMapper p6eUserMapper = SpringUtil.getBean(P6eUserMapper.class);

    /** 注入密码服务对象 */
    private final P6eDefaultLoginPasswordService p6eDefaultLoginPasswordService = new P6eDefaultLoginPasswordService();

    /**
     * 读取方式获取对象
     * @param account 用户的账号
     * @return P6eUserEntity 对象
     */
    public static P6eUserEntity fetch(String account) {
        return new P6eUserEntity(account);
    }

    /**
     * 根据账号获取
     * @param account 账号
     */
    private P6eUserEntity(String account) {
        this.uniqueId = GeneratorUtil.uuid();
        CopyUtil.run(p6eUserMapper.queryByAccount(account), this);
    }

    /**
     * 验证密码是否正确
     * @param password 需要认证的密码
     * @return 认证的结果
     */
    public boolean defaultVerification(String password) {
        if (this.password == null || "".equals(this.password)) {
            return false;
        } else {
            return this.password.equals(this.p6eDefaultLoginPasswordService.encryption(password));
        }
    }

    public P6eTokenEntity createTokenCache() {
        final Map<String, String> map = new HashMap<>(3);
        map.put("email", email);
        map.put("phone", phone);
        map.put("id", String.valueOf(id));
        return P6eTokenEntity.create(GeneratorUtil.uuid(), String.valueOf(id), map);
    }
}
