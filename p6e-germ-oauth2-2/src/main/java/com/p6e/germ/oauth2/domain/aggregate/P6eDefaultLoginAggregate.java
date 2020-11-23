package com.p6e.germ.oauth2.domain.aggregate;

import com.p6e.germ.oauth2.domain.entity.*;
import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eDefaultAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import lombok.Getter;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eDefaultLoginAggregate {

    /** 认证 CODE 的名称 */
    private static final String AUTH_CODE_NAME = "CODE";

    /**
     * 属性
     */
    @Getter
    private final String mark;

    @Getter
    private final String voucher;

    @Getter
    private final String account;

    @Getter
    private final String password;

    /** 记号实体 */
    private final P6eMarkEntity p6eMarkEntity;

    /** 用户实体 */
    private final P6eUserEntity p6eUserEntity;

    /** 凭证实体 */
    private final P6eVoucherEntity p6eVoucherEntity;


    /**
     * 创建 P6eDefaultLoginAggregate 对象
     * @param mark 记号
     * @param voucher 凭证
     * @param account 账号
     * @param password 密码
     * @return P6eDefaultLoginAggregate 对象
     */
    public static P6eDefaultLoginAggregate create(String mark, String voucher,
                                                  String account, String password) {
        return new P6eDefaultLoginAggregate(mark, voucher, account, password);
    }

    /**
     * 构造返回创建
     * @param mark 记号
     * @param voucher 凭证
     * @param account 账号
     * @param password 密码
     */
    private P6eDefaultLoginAggregate(String mark, String voucher,
                                    String account, String password) {
        this.mark = mark;
        this.voucher = voucher;
        this.account = account;
        this.password = password;
        this.p6eMarkEntity = P6eMarkEntity.fetch(mark);
        this.p6eUserEntity = P6eUserEntity.fetch(account);
        this.p6eVoucherEntity = P6eVoucherEntity.fetch(voucher);
    }

    /**
     * 验证账号密码操作
     * @return 验证后得到的结果
     */
    public P6eDefaultAuthKeyValue verification() {
        try {
            // 对密码进行解密并且验证正确性
            if (this.p6eUserEntity.defaultVerification(this.p6eVoucherEntity.execute(this.password))) {
                // 读取标记里面的数据信息
                final P6eAuthKeyValue p6eAuthKeyValue = this.p6eMarkEntity.getP6eAuthKeyValue();
                // 清除缓存
                this.p6eMarkEntity.clean();
                this.p6eVoucherEntity.clean();
                // 创建缓存实体
                final P6eTokenEntity p6eTokenEntity = this.p6eUserEntity.createTokenCache();
                // 判断是否为 code 的认证
                if (AUTH_CODE_NAME.equals(p6eAuthKeyValue.getResponseType().toUpperCase())) {
                    // code 模式
                    return new P6eDefaultAuthKeyValue(
                            p6eTokenEntity.getModel().getAccessToken(),
                            p6eTokenEntity.getModel().getRefreshToken(),
                            p6eTokenEntity.getModel().getTokenType(),
                            p6eTokenEntity.getModel().getExpiresIn(),
                            p6eAuthKeyValue.getRedirectUri(),
                            P6eAuthEntity.create(GeneratorUtil.uuid(), p6eTokenEntity.getKey()).getKey(),
                            p6eAuthKeyValue.getState()
                    );
                } else {
                    // 简化模式
                    final long expirationTime = 120;
                    // 删除 refreshToken 且修改过期的时间戳
                    p6eTokenEntity.delRefreshToken();
                    p6eTokenEntity.setAccessTokenExpirationTime(expirationTime);
                    return new P6eDefaultAuthKeyValue(
                            p6eTokenEntity.getModel().getAccessToken(),
                            null,
                            p6eTokenEntity.getModel().getTokenType(),
                            expirationTime,
                            p6eAuthKeyValue.getRedirectUri(),
                            null,
                            p6eAuthKeyValue.getState()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 输出 null 表示账号密码错误
        return null;
    }
}
