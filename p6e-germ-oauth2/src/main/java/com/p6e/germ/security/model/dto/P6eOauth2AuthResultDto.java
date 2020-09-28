package com.p6e.germ.security.model.dto;

import com.p6e.germ.security.model.base.P6eBaseResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class P6eOauth2AuthResultDto extends P6eBaseResultDto implements Serializable {
    private Integer id;
    private Integer status;
    private String avatar;
    private String name;
    private String nickname;
    private String sex;
    private String birthday;
    private String role;
    private String token;
    private String refreshToken;
    private Long expirationTime;
    private List<Map<String, String>> groupList;
    private List<Map<String, String>> jurisdictionList;
}
