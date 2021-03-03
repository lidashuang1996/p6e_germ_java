package com.p6e.germ.oauth2.application;

import com.p6e.germ.common.utils.P6eJsonUtil;
import com.p6e.germ.common.utils.P6eSpringUtil;
import com.p6e.germ.oauth2.domain.entity.P6eLogEntity;
import com.p6e.germ.oauth2.model.db.P6eOauth2LogDb;
import com.p6e.germ.oauth2.model.dto.P6eLogDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * @author lidashuang
 * @version 1.0
 */
@SpringBootTest
public class P6eLogDataServiceTest {

    @Autowired
    public P6eLogDataServiceTest(ApplicationContext applicationContext)  {
        P6eSpringUtil.init(applicationContext);
    }

    @Test
    void createTest() {
        final P6eOauth2LogDb p6eOauth2LogDb = new P6eOauth2LogDb();
        p6eOauth2LogDb.setCid(1);
        p6eOauth2LogDb.setUid(2);
        p6eOauth2LogDb.setType("789");
        System.out.println(P6eJsonUtil.toJson(new P6eLogEntity(p6eOauth2LogDb).create()));
    }

    @Test
    void getTest() {
        final P6eLogDataDto p6eLogDataDto = new P6eLogDataDto();
        p6eLogDataDto.setId(1);
        System.out.println(P6eJsonUtil.toJson(P6eApplication.logData.get(p6eLogDataDto)));
    }

    @Test
    void selectTest() {
        System.out.println(P6eJsonUtil.toJson(P6eApplication.logData.select(null)));
    }
}
