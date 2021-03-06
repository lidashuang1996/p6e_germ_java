package com.p6e.germ.security.model.db;

import com.p6e.germ.oauth2.model.base.P6eBaseDb;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eOauth2UserDb  extends P6eBaseDb implements Serializable {
    private Integer id;
    private Integer status;
    private String avatar;
    private String name;
    private String nickname;
    private String sex;
    private String birthday;
    private String role;

    private String email;
    private String phone;

    private String account;
    private String password;
}
