package com.p6e.germ.oauth2.domain.keyvalue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class P6ePushKeyValue {

    /**
     * 验证码的 KEY
     */
    private Integer uid;

    /**
     * 验证码的 KEY
     */
    private String key;

    /**
     * 生成的验证码
     */
    private String code;

    /**
     * 类型
     */
    private String type;
}
