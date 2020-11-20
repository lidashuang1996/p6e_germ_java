package com.p6e.germ.oauth2.infrastructure.repository.db;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eUserDb extends P6eBaseDb implements Serializable {
    private Integer id;
    private String email;
    private String phone;
    private String password;
    private String qq;
    private String weChat;
}
