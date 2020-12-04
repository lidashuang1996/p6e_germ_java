package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.oauth2.infrastructure.repository.db.P6eOauth2UserDb;
import com.p6e.germ.oauth2.infrastructure.repository.mapper.P6eOauth2UserMapper;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eUserEntity implements Serializable {

    /** DB */
    private P6eOauth2UserDb p6eOauth2UserDb;

    /** 注入 DB 操作对象 */
    private final P6eOauth2UserMapper p6eUserMapper = P6eSpringUtil.getBean(P6eOauth2UserMapper.class);

    /**
     * 构造创建
     * @param id id
     */
    public P6eUserEntity(Integer id) {
        this.p6eOauth2UserDb = p6eUserMapper.queryById(id);
        if (this.p6eOauth2UserDb == null) {
            throw new RuntimeException();
        }
    }

    /**
     * 构造创建
     * @param account 账号
     */
    public P6eUserEntity(String account) {
        this.p6eOauth2UserDb = p6eUserMapper.queryByAccount(account);
        if (this.p6eOauth2UserDb == null) {
            throw new RuntimeException();
        }
    }

    /**
     * 构造创建
     * @param p6eOauth2UserDb 数据对象
     */
    public P6eUserEntity(P6eOauth2UserDb p6eOauth2UserDb) {
        this.p6eOauth2UserDb = p6eOauth2UserDb;
        if (this.p6eOauth2UserDb == null) {
            throw new RuntimeException();
        }
    }

    /**
     * 获取数据对象
     * @return 数据对象
     */
    public P6eOauth2UserDb get() {
        return p6eOauth2UserDb;
    }

    public P6eOauth2UserDb delete() {
        return p6eUserMapper.delete(p6eOauth2UserDb.getId()) > 0 ? p6eOauth2UserDb : null;
    }

    public P6eOauth2UserDb create() {
        if (p6eUserMapper.create(p6eOauth2UserDb) > 0) {
            this.p6eOauth2UserDb = p6eUserMapper.queryById(p6eOauth2UserDb.getId());
            return p6eOauth2UserDb;
        } else {
            return null;
        }
    }

    public P6eOauth2UserDb update() {
        if (p6eUserMapper.update(p6eOauth2UserDb) > 0) {
            this.p6eOauth2UserDb = p6eUserMapper.queryById(p6eOauth2UserDb.getId());
            return p6eOauth2UserDb;
        } else {
            return null;
        }
    }

    /**
     * 验证密码是否正确
     * @param password 需要认证的密码
     * @return 认证的结果
     */
    public boolean defaultVerification(String password) {
        if (this.p6eOauth2UserDb.getPassword() == null || "".equals(this.p6eOauth2UserDb.getPassword())) {
            return false;
        } else {
            return this.p6eOauth2UserDb.getPassword().equals(encryption(password));
        }
    }

    public P6eTokenEntity createTokenCache() {
        final Map<String, String> map = new HashMap<>(3);
        map.put("email", p6eOauth2UserDb.getEmail());
        map.put("phone", p6eOauth2UserDb.getPhone());
        map.put("id", String.valueOf(p6eOauth2UserDb.getId()));
        return new P6eTokenEntity(String.valueOf(p6eOauth2UserDb.getId()), map);
    }

    /**
     * 对数据库的 password 字段进行加密操作
     * 采用独特的加密方式---详情见下面
     * 这样做的目的防止（降低）密码被暴力破解
     * @param content 待加密的内容
     * @return 加密后的内容
     */
    public static String encryption(String content) {
        // 带加密的内容 md5 加密一次
        final String md5Pwd = DigestUtils.md5DigestAsHex(content.getBytes(StandardCharsets.UTF_8));
        // 截取 0-8 和 8 以后的内容
        final String md5Pwd1 = md5Pwd.substring(0, 8);
        final String md5Pwd2 = md5Pwd.substring(8);
        // 对 0-8 的内容再 md5 加密一次，8 以后的内容不进行加密
        return DigestUtils.md5DigestAsHex(md5Pwd1.getBytes(StandardCharsets.UTF_8)) + md5Pwd2;
    }
}
