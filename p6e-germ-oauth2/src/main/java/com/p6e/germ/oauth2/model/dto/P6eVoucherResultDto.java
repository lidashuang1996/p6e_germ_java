package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eVoucherResultDto extends P6eBaseResultDto implements Serializable {
    /** 凭证 */
    private String voucher;
    /** 公钥 */
    private String publicKey;

    public P6eVoucherResultDto(String voucher, String publicKey) {
        this.voucher = voucher;
        this.publicKey = publicKey;
    }

    /** 解密后的内容 */
    private String content;

    public P6eVoucherResultDto(String content) {
        this.content = content;
    }
}
