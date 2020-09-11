package com.p6e.germ.security.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 该类为 model dto 参数对象
 * dto 对象为 service 参数和返回对象
 * @author LiDaShuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class P6eBaseParamDto implements Serializable {
    /**
     * 分页数据
     */
    private Integer page;

    /**
     * 长度数据
     */
    private Integer size;

    /**
     * 搜索内容
     */
    private String search;
}
