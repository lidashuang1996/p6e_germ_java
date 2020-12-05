package com.p6e.germ.oauth2.model.base;

import com.p6e.germ.oauth2.model.P6eModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eResultDto<T> implements Serializable {
    /** 错误的描述 */
    private P6eModel.Error error;

    /** 多条查询的数据内容 */
    private List<T> list;
    /** 总数 */
    private Long total;
    /** 页码 */
    private Integer page;
    /** 长度 */
    private Integer size;

    /**
     * 初始化 页码/ 长度 数据
     * @param db DB 数据对象
     */
    public void initPaging(P6eBaseDb db) {
        this.setPage(db.getPage());
        this.setSize(db.getSize());
    }
}
