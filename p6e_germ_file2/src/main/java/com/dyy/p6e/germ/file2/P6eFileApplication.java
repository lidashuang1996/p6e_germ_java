package com.dyy.p6e.germ.file2;

import com.dyy.p6e.germ.file2.config.P6eConfig;
import com.dyy.p6e.germ.file2.core.P6eFileCoreFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author lidashuang
 * @version 1.0
 * 1. 文件认证
 * 2. 文件携带参数截取
 */
@SpringBootApplication
public class P6eFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(P6eFileApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(final WebServerApplicationContext applicationContext) {
        return args -> {
            // 读取配置文件对象
            final P6eConfig config = applicationContext.getBean(P6eConfig.class);
            // 添加 TOKEN 到管理令牌列表中
            P6eFileCoreFactory.addManageAuthToken(config.getFile().getManage().getTokens());
        };
    }

}
