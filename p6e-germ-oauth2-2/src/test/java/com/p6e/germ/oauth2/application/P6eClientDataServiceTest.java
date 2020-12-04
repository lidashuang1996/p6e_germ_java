package com.p6e.germ.oauth2.application;

import com.p6e.germ.oauth2.infrastructure.utils.JsonUtil;
import com.p6e.germ.oauth2.infrastructure.utils.P6eSpringUtil;
import com.p6e.germ.oauth2.model.dto.P6eClientDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@SpringBootTest
public class P6eClientDataServiceTest {


    @Autowired
    public P6eClientDataServiceTest(ApplicationContext applicationContext) {
        P6eSpringUtil.init(applicationContext);
    }

    @Test
    void createTest() {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        p6eClientDataDto.setStatus(0);
        p6eClientDataDto.setIcon("123.jpg");
        p6eClientDataDto.setName("TEST");
        p6eClientDataDto.setScope("all");
        p6eClientDataDto.setRedirectUri("http://127.0.0.1:9900");
        p6eClientDataDto.setDescribe("我是测试");
        p6eClientDataDto.setLimitingRule("1,0,0,0");
        System.out.println(JsonUtil.toJson(P6eApplication.clientData.create(p6eClientDataDto)));
    }

    @Test
    void deleteTest() {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        p6eClientDataDto.setId(5);
        System.out.println(JsonUtil.toJson(P6eApplication.clientData.delete(p6eClientDataDto)));
    }

    @Test
    void getTest() {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        p6eClientDataDto.setId(5);
        P6eApplication.clientData.get(p6eClientDataDto);
        System.out.println(JsonUtil.toJson(P6eApplication.clientData.get(p6eClientDataDto)));
    }

    @Test
    void updateTest() {
        final P6eClientDataDto p6eClientDataDto = new P6eClientDataDto();
        p6eClientDataDto.setId(5);
        p6eClientDataDto.setName("xxxxxxxxxxxxxx");
        System.out.println(JsonUtil.toJson(P6eApplication.clientData.update(p6eClientDataDto)));
    }

}
