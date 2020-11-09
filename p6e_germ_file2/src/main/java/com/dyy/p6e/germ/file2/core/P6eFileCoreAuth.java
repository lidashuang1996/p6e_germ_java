package com.dyy.p6e.germ.file2.core;

import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreAuth {

    public Function<String, Mono<String>> execute(ServerHttpRequest request) {
        return Mono::just;
    }

}
