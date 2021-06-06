package com.p6e.germ.oauth2.domain.entity;

import com.p6e.germ.common.utils.P6eCopyUtil;
import com.p6e.germ.common.utils.P6eGeneratorUtil;
import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.oauth2.domain.keyvalue.P6eUserKeyValue;
import com.p6e.germ.oauth2.infrastructure.mapper.P6eOauth2UserMapper;
import com.p6e.germ.oauth2.model.db.P6eOauth2UserDb;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eUserEntity implements Serializable {

    /** DB */
    private P6eUserKeyValue.Content data;

    /** 注入 DB 操作对象 */
    private final P6eOauth2UserMapper mapper = P6eSpringUtil.getBean(P6eOauth2UserMapper.class);


    public static P6eUserEntity get(int id) {
        return new P6eUserEntity(id);
    }

    public static P6eUserEntity get(P6eUserKeyValue.Account account) {
        return new P6eUserEntity(account);
    }

    public static P6eUserEntity get(P6eUserKeyValue.Qq qq) {
        return new P6eUserEntity(qq);
    }

    public static P6eUserEntity get(P6eUserKeyValue.WeChat weChat) {
        return new P6eUserEntity(weChat);
    }

    public static P6eUserEntity get(P6eUserKeyValue.Sina sina) {
        return new P6eUserEntity(sina);
    }

    /**
     * 构造创建
     * @param id id
     */
    public P6eUserEntity(int id) {
        this.data = P6eCopyUtil.run(mapper.queryById(id), P6eUserKeyValue.Content.class);
        if (this.data == null) {
            throw new RuntimeException();
        }
    }

    /**
     * 构造创建
     * @param account 账号
     */
    private P6eUserEntity(P6eUserKeyValue.Account account) {
        if (account == null) {
            throw new NullPointerException();
        }
        this.data = P6eCopyUtil.run(mapper.queryByAccount(account.getContent()), P6eUserKeyValue.Content.class);
        if (this.data == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    /**
     * 构造创建
     * @param qq qq
     */
    private P6eUserEntity(P6eUserKeyValue.Qq qq) {
        if (qq == null || qq.getContent() == null) {
            throw new NullPointerException();
        } else {
            P6eOauth2UserDb db = mapper.queryByQq(qq.getContent());
            if (db == null) {
                if (mapper.create(new P6eOauth2UserDb().setPassword(P6eGeneratorUtil.uuid()).setQq(qq.getContent())) > 0) {
                    db  = mapper.queryByQq(qq.getContent());
                    this.data = P6eCopyUtil.run(db, P6eUserKeyValue.Content.class);
                } else {
                    throw new NullPointerException();
                }
            } else {
                this.data = P6eCopyUtil.run(db, P6eUserKeyValue.Content.class);
            }
        }
    }

    /**
     * 构造创建
     * @param weChat weChat
     */
    private P6eUserEntity(P6eUserKeyValue.WeChat weChat) {
        if (weChat == null) {
            throw new NullPointerException();
        }
    }

    private P6eUserEntity(P6eUserKeyValue.Sina sina) {
        if (sina == null) {
            throw new NullPointerException();
        }
    }

    public P6eUserEntity(P6eUserKeyValue.Content data) {
        this.data = data;
        if (this.data == null) {
            throw new NullPointerException(this.getClass() + " construction data ==> NullPointerException.");
        }
    }

    /**
     * 获取数据对象
     * @return 数据对象
     */
    public P6eUserKeyValue.Content getData() {
        return data;
    }

    /**
     * 验证密码是否正确
     * @param password 需要认证的密码
     * @return 认证的结果
     */
    public boolean defaultVerification(String password) {
        if (data.getPassword() == null || "".equals(data.getPassword())) {
            return false;
        } else {
            return data.getPassword().equals(encryption(password));
        }
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
