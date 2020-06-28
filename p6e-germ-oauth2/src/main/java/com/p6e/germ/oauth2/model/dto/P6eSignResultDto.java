package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @version 1.0
 * @auther: lidashuang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class P6eSignResultDto extends P6eBaseResultDto implements Serializable {

    private Integer id;
    private Integer status;
    private String avatar;
    private String name;
    private String nickname;
    private String sex;
    private String birthday;
    private String role;


}
