package com.p6e.germ.oauth2.context.controller.support.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class P6eQqLoginParam implements Serializable {
    private String mark;
    private String display;
}
