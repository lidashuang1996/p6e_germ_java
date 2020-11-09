package com.dyy.p6e.germ.file2.core;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eFileCoreFactory {
    private static P6eFileCoreAuth P6E_FILE_CORE_AUTH = new P6eFileCoreAuth();
    private static P6eFileCoreContext P6E_FILE_CORE_CONTEXT = new P6eFileCoreContext();
    private static P6eFileCoreJurisdiction P6E_FILE_CORE_JURISDICTION = new P6eFileCoreJurisdiction();

    public static void initCoreAuth(final P6eFileCoreAuth auth) {
        P6E_FILE_CORE_AUTH = auth;
    }

    public static void initCoreContext(final P6eFileCoreContext context) {
        P6E_FILE_CORE_CONTEXT = context;
    }

    public static void initCoreJurisdiction(final P6eFileCoreJurisdiction jurisdiction) {
        P6E_FILE_CORE_JURISDICTION = jurisdiction;
    }

    public static <T> Function<T, Mono<T>> auth(ServerHttpRequest request) {
        return P6E_FILE_CORE_AUTH.execute(request);
    }

    public static <T> Function<T, Mono<T>> jurisdiction(ServerHttpRequest request) {
        return P6E_FILE_CORE_JURISDICTION.execute(request);
    }

    public static byte[] read(String filePath, DataBuffer dataBuffer) throws IOException {
        return P6E_FILE_CORE_CONTEXT.read(filePath, dataBuffer);
    }

    public static byte[] read(String filePath, DataBuffer dataBuffer, boolean isCache) throws IOException {
        return P6E_FILE_CORE_CONTEXT.read(filePath, dataBuffer, isCache);
    }
}
