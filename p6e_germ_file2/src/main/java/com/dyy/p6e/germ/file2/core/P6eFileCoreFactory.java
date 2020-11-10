package com.dyy.p6e.germ.file2.core;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author lidashuang
 * @version 1.0
 */
public final class P6eFileCoreFactory {
    private static P6eFileCoreAuth P6E_FILE_CORE_AUTH = new P6eFileCoreAuth();
    private static P6eFileCoreManage P6E_FILE_CORE_MANAGE = new P6eFileCoreManage();
    private static P6eFileCoreContext P6E_FILE_CORE_CONTEXT = new P6eFileCoreContext();
    private static P6eFileCoreJurisdiction P6E_FILE_CORE_JURISDICTION = new P6eFileCoreJurisdiction();

    public static void initCoreAuth(final P6eFileCoreAuth auth) {
        P6E_FILE_CORE_AUTH = auth;
    }

    public static void initCoreManage(final P6eFileCoreManage manage) {
        P6E_FILE_CORE_MANAGE = manage;
    }

    public static void initCoreContext(final P6eFileCoreContext context) {
        P6E_FILE_CORE_CONTEXT = context;
    }

    public static void initCoreJurisdiction(final P6eFileCoreJurisdiction jurisdiction) {
        P6E_FILE_CORE_JURISDICTION = jurisdiction;
    }

    public static Function<String, Mono<String>> auth(ServerHttpRequest request) {
        return P6E_FILE_CORE_AUTH.execute(request);
    }

    public static Function<String, Mono<String>> jurisdiction(ServerHttpRequest request) {
        return P6E_FILE_CORE_JURISDICTION.execute(request);
    }

    public static byte[] read(String filePath, DataBuffer dataBuffer) throws IOException {
        return P6E_FILE_CORE_CONTEXT.read(filePath, dataBuffer);
    }

    public static byte[] read(String filePath, DataBuffer dataBuffer, boolean isCache) throws IOException {
        return P6E_FILE_CORE_CONTEXT.read(filePath, dataBuffer, isCache);
    }

    public static void write(final FilePart filePart, final File file) {
        P6E_FILE_CORE_CONTEXT.write(filePart, file);
    }

    public static void addManageAuthToken(String... tokes) {
        P6E_FILE_CORE_MANAGE.addAuthToken(tokes);
    }

    public static Function<String, Mono<String>> manageAuth(ServerHttpRequest request) {
        return P6E_FILE_CORE_MANAGE.auth(request);
    }

    public static List<P6eFileCoreManage.Pocket> manageList(String folderPath) {
        try {
            return P6E_FILE_CORE_MANAGE.list(folderPath);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static List<P6eFileCoreManage.Pocket> manageList(String folderPath, int start, int end) {
        try {
            return P6E_FILE_CORE_MANAGE.list(folderPath, start, end);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
