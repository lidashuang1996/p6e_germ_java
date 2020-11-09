package com.dyy.p6e.germ.file2.controller;

import com.dyy.p6e.germ.file2.config.P6eConfig;
import com.dyy.p6e.germ.file2.controller.support.P6eBaseController;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/")
public class P6eFileOperationController extends P6eBaseController {

    /**
     * 注入配置文件对象
     */
    @Resource
    private P6eConfig p6eConfig;

    @GetMapping("/operation/list/**")
    public Mono<Void> list(final ServerHttpRequest request, final ServerHttpResponse response) {
        return Mono.just("11111").then();
    }

    @GetMapping("/operation/upload")
    public Mono<Void> upload(final ServerHttpRequest request, final ServerHttpResponse response) {
        return Mono.just("11111").then();
    }

}
