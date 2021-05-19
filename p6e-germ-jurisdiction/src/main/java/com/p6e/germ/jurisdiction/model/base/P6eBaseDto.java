package com.p6e.germ.jurisdiction.model.base;

import com.p6e.germ.jurisdiction.model.P6eErrorModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class P6eBaseDto implements Serializable {
    private P6eErrorModel error;
    private String errorContent;
}
