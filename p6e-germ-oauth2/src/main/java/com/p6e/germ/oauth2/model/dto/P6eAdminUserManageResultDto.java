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
public class P6eAdminUserManageResultDto extends P6eBaseResultDto implements Serializable {
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
}
