package com.p6e.germ.oauth2.model.db;

import com.p6e.germ.oauth2.model.base.P6eBaseDb;
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class P6eAdminUserDb extends P6eBaseDb implements Serializable {

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


    private String account;
    private String password;

}
