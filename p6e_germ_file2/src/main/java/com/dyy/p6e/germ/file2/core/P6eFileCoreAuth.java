package com.dyy.p6e.germ.file2.core;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 认证对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreAuth {

    /**
     * 认证的执行
     * error --> P6eResultConfig.AUTH_NO_EXISTENCE --> AUTH_NO_EXISTENCE
     * @param request HTTP 请求对象
     * @return 方法
     */
    public Function<String, Mono<String>> execute(ServerHttpRequest request) {
        return Mono::just;
    }

}
