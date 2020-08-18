package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.Data;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAdminSignResultDto extends P6eBaseResultDto {
    private String account;
    private String password;

    private Integer id;
    private Integer status;
    private String avatar;
    private String name;
    private String nickname;
    private Integer sex;
    private String birthday;
    private Integer role;
    private String email;
    private String phone;

    private String token;
    private String refreshToken;
    private Long timestamp;
}
