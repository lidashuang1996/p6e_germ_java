package com.p6e.germ.oauth2.model.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * 该类为 mode db 层对象
 * db 对象和数据库字段一一对应
 * @author LiDaShuang
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class P6eBaseDb implements Serializable {
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
