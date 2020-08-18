package com.p6e.germ.oauth2.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @author lidashuang
 * @version 1.0
 */
@Data
@Component
public class P6eOauth2Config {

    private final P6eOauth2AdminConfig admin = new P6eOauth2AdminConfig();

    @Data
    public static class P6eOauth2AdminConfig {
        /**
         * 管理员是否账号注册状态
         * -1 -- 禁用添加账号的操作
         * 0 -- 认证后添加
         * 1 -- 暴露地址随便注册
         */
        private int registerStatus = 0;
    }
}
