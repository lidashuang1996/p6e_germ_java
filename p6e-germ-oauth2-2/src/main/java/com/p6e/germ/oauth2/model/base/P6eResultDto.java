package com.p6e.germ.oauth2.model.base;

import com.p6e.germ.oauth2.model.P6eModel;
import com.p6e.germ.oauth2.model.db.P6eBaseDb;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
public class P6eResultDto<T> implements Serializable {
    private P6eModel.Error error;
    private List<T> list;
    private Long total;
    private Integer page;
    private Integer size;

    public void initPaging(P6eBaseDb db) {
        this.setPage(db.getPage());
        this.setSize(db.getSize());
    }
}
