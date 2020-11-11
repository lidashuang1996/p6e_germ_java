package com.dyy.p6e.germ.file2.filter;

import com.dyy.p6e.germ.file2.controller.support.P6eBaseController;
import com.dyy.p6e.germ.file2.model.P6eResultConfig;
import com.dyy.p6e.germ.file2.model.P6eResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * 通过过滤器处理全局抛出的异常
 * @author lidashuang
 * @version 1.0
 */
@Component
public class P6eExceptionHandlerFilter implements WebFilter {

    /** 注入日志对象 */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eExceptionHandlerFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).onErrorResume((Function<Throwable, Mono<Void>>) throwable -> {
            final ServerHttpRequest serverHttpRequest = exchange.getRequest();
            final ServerHttpResponse serverHttpResponse = exchange.getResponse();
            throwable.printStackTrace();
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            LOGGER.error(P6eBaseController.logBaseInfo(serverHttpRequest) + " " + throwable.getMessage());
            // 对拦截的异常进行分别处理
            if (throwable instanceof MethodNotAllowedException) {
                return serverHttpResponse.writeWith(Mono.just(new DefaultDataBufferFactory()
                        .allocateBuffer().write(P6eResultModel.build(P6eResultConfig.ERROR_405_ERROR_METHOD_NOT_ALLOWED).toBytes())));
            } else if (throwable instanceof ServerWebInputException) {
                return serverHttpResponse.writeWith(Mono.just(new DefaultDataBufferFactory()
                        .allocateBuffer().write(P6eResultModel.build(P6eResultConfig.ERROR_400_ERROR_PARAM_EXCEPTION).toBytes())));
            } else if (throwable instanceof ResponseStatusException) {
                return serverHttpResponse.writeWith(Mono.just(new DefaultDataBufferFactory()
                        .allocateBuffer().write(P6eResultModel.build(P6eResultConfig.ERROR_404_ERROR_NOT_FOUND).toBytes())));
            } else {
                return serverHttpResponse.writeWith(Mono.just(new DefaultDataBufferFactory()
                        .allocateBuffer().write(P6eResultModel.build(P6eResultConfig.ERROR_SERVICE_INSIDE).toBytes())));
            }
        });
    }
}
