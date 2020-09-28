package com.p6e.germ.oauth2.service;

import com.p6e.germ.oauth2.model.dto.P6eLogParamDto;
import com.p6e.germ.oauth2.model.dto.P6eLogResultDto;
import com.p6e.germ.oauth2.service.impl.P6eLogServiceImpl;
import com.p6e.germ.oauth2.utils.P6eJsonUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 日志的服务单元测试
 * @author lidashuang
 * @version 1.0
 */
@SpringBootTest
public class P6eLogServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(P6eLogServiceTest.class);

    @Resource
    private P6eLogService p6eLogService;

    @Test
    public void createTest() {
        P6eLogParamDto p6eLogParamDto = new P6eLogParamDto();
        p6eLogParamDto.setCid(1);
        p6eLogParamDto.setUid(3);
        p6eLogParamDto.setType(P6eLogServiceImpl.CID_TO_UID);
        P6eLogResultDto p6eLogResultDto = p6eLogService.create(p6eLogParamDto);
        if (p6eLogResultDto != null && p6eLogResultDto.getError() == null) {
            logger.info(P6eJsonUtil.toJson(p6eLogResultDto));
        } else {
            throw new RuntimeException(Objects.requireNonNull(p6eLogResultDto).getError());
        }
    }

}
