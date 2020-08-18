package com.p6e.germ.oauth2.model.vo;

import com.p6e.germ.oauth2.model.base.P6eBaseParamVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 管理员登录需要的 VO 层
 * @author lidashuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eAdminSignParamVo extends P6eBaseParamVo implements Serializable {
    /** 账号 */
    private String account;
    /** 密码 */
    private String password;
    /** token */
    private String token;
}
