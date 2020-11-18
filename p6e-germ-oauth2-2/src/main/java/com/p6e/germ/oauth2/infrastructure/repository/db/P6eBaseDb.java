package com.p6e.germ.oauth2.infrastructure.repository.db;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class P6eBaseDb {
    private Integer page;
    private Integer size;
}
