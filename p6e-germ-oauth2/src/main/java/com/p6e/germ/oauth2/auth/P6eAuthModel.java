package com.p6e.germ.oauth2.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * 认证数据模型
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eAuthModel implements Serializable {
    private Integer id;
    private Integer status;
    private String avatar;
    private String name;
    private String nickname;
    private String sex;
    private String birthday;
    private String role;
}
