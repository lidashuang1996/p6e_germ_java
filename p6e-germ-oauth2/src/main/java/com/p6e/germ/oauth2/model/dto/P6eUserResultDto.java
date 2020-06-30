package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eUserResultDto extends P6eBaseResultDto implements Serializable {
    private Integer id;
    private Integer status;
    private String avatar;
    private String name;
    private String nickname;
    private String sex;
    private String birthday;
    private String role;

    private String oid;
}
