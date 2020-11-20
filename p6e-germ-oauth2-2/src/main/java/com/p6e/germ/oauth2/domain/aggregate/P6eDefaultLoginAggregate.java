package com.p6e.germ.oauth2.domain.aggregate;

import com.p6e.germ.oauth2.domain.entity.P6eAuthEntity;
import com.p6e.germ.oauth2.domain.entity.P6eMarkEntity;
import com.p6e.germ.oauth2.domain.entity.P6eUserEntity;
import com.p6e.germ.oauth2.domain.entity.P6eVoucherEntity;
import com.p6e.germ.oauth2.domain.keyvalue.P6eAuthKeyValue;
import com.p6e.germ.oauth2.domain.keyvalue.P6eDefaultAuthKeyValue;
import com.p6e.germ.oauth2.infrastructure.utils.GeneratorUtil;
import lombok.Getter;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eDefaultLoginAggregate {

    public static P6eDefaultLoginAggregate create(String mark, String voucher,
                                                  String account, String password) {
        return new P6eDefaultLoginAggregate(mark, voucher, account, password);
    }

    @Getter
    private final String mark;
    @Getter
    private final String voucher;
    @Getter
    private final String account;
    @Getter
    private final String password;
    private final P6eMarkEntity p6eMarkEntity;
    private final P6eUserEntity p6eUserEntity;
    private final P6eVoucherEntity p6eVoucherEntity;

    public P6eDefaultLoginAggregate(String mark, String voucher,
                                    String account, String password) {
        this.mark = mark;
        this.voucher = voucher;
        this.account = account;
        this.password = password;
        this.p6eMarkEntity = P6eMarkEntity.fetch(mark);
        this.p6eUserEntity = P6eUserEntity.fetch(account);
        this.p6eVoucherEntity = P6eVoucherEntity.fetch(voucher);
    }


    public P6eDefaultAuthKeyValue verification() {
        try {
            // 对密码进行解密并且验证正确性
            if (this.p6eUserEntity.defaultVerification(this.p6eVoucherEntity.execute(this.password))) {
                // 读取标记里面的数据获取 client 的信息
                final P6eAuthKeyValue p6eAuthKeyValue = this.p6eMarkEntity.getP6eAuthKeyValue();
                // 清除缓存
                this.p6eMarkEntity.clean();
                this.p6eVoucherEntity.clean();
                // 创建用户登录认证的缓存记录
                final String token = this.p6eUserEntity.createTokenCache();
                // 返回重定向 url
                return new P6eDefaultAuthKeyValue(
                        generateUrl(p6eAuthKeyValue.getRedirectUri(),
                        P6eAuthEntity.create(GeneratorUtil.uuid(), token).getKey()),
                        token
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成回调的 url 的地址
     * @param url url 地址
     * @param code 认证的记号
     * @return 生成回调的 url 的地址
     */
    private static String generateUrl(String url, String code) {
        final int index = url.indexOf("?");
        if (index > -1) {
            if (url.length() > index) {
                return url.substring(0, index) + "?code=" + code + "&" + url.substring(index + 1);
            } else {
                return url.substring(0, index) + "?code=" + code;
            }
        } else {
            return url + "?code=" + code;
        }
    }
}
