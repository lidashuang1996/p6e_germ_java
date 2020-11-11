package com.dyy.p6e.germ.file2.core;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 权限对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreJurisdiction {

    /**
     * 权限的执行
     * error --> P6eResultConfig.JURISDICTION_NO_EXISTENCE --> JURISDICTION_NO_EXISTENCE
     * @param request HTTP 请求对象
     * @return 方法
     */
    public <T> Function<T, Mono<T>> execute(ServerHttpRequest request) {
        return Mono::just;
    }
}
