package com.p6e.germ.oauth2.model.dto;

import com.p6e.germ.oauth2.model.base.P6eBaseResultDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true) // 使用父类的属性
public class P6eListResultDto<T> extends P6eBaseResultDto implements Serializable {
    private List<T> list;
    private Integer page;
    private Integer size;
    private Integer total;
}
