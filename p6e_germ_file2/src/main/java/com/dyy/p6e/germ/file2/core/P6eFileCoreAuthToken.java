package com.dyy.p6e.germ.file2.core;

import com.dyy.p6e.germ.file2.model.P6eResultConfig;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreAuthToken extends P6eFileCoreAuth {

    public static final String AUTH_BASIC = "Basic";

    public static final String AUTH_QUERY_PARAM = "authentication";

    public static final List<String> AUTH_TOKEN_LIST = new ArrayList<>();

    public void addAuthToken(String... tokens) {
        AUTH_TOKEN_LIST.addAll(Arrays.asList(tokens));
    }

    @Override
    public Function<String, Mono<String>> execute(ServerHttpRequest request) {
        final List<String> list = new ArrayList<>();
        final List<String> pList = request.getQueryParams().get(AUTH_QUERY_PARAM);
        if (pList != null) {
            list.addAll(pList);
        }
        final List<String> hList = request.getHeaders().get(AUTH_QUERY_PARAM);
        if (hList != null) {
            list.addAll(hList);
        }
        boolean authStatus = false;
        for (final String token : AUTH_TOKEN_LIST) {
            if (list.contains(AUTH_BASIC + " " + token)) {
                authStatus = true;
                break;
            }
        }
        final boolean finalAuthStatus = authStatus;
        return s -> Mono.just(finalAuthStatus ? s : P6eResultConfig.AUTH_NO_EXISTENCE);
    }
}
