package com.p6e.germ.oauth2.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class P6eWeChatLoginDto implements Serializable {
    private String code;
    private String state;
}
