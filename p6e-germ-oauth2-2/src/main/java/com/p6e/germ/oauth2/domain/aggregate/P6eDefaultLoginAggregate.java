package com.p6e.germ.oauth2.domain.aggregate;

import com.p6e.germ.oauth2.domain.entity.P6eMarkEntity;
import com.p6e.germ.oauth2.domain.entity.P6eUserEntity;
import com.p6e.germ.oauth2.domain.entity.P6eVoucherEntity;
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
        this.p6eMarkEntity = new P6eMarkEntity(mark);
        this.p6eUserEntity = new P6eUserEntity(account, password);
        this.p6eVoucherEntity = new P6eVoucherEntity(voucher);
    }


    public void verification() {
        // 密码
        // 账号密码
        // 记号内容
        // 写入自己的认证过的缓存
//        if (this.p6eUserEntity.defaultVerification(this.getAccount(), this.getPassword()) == null) {
//
//        }
        this.p6eMarkEntity.get();
    }

}
