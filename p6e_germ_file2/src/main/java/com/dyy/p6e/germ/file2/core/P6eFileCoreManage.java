package com.dyy.p6e.germ.file2.core;

import com.dyy.p6e.germ.file2.model.P6eResultConfig;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreManage {

    public static final String AUTH_BASIC = "Basic";

    public static final String AUTH_QUERY_PARAM = "authentication";

    public static final List<String> AUTH_TOKEN_LIST = new ArrayList<>();

    public P6eFileCoreManage() { }

    public P6eFileCoreManage(String... tokens) {
        AUTH_TOKEN_LIST.addAll(Arrays.asList(tokens));
    }

    public void addAuthToken(String... tokens) {
        AUTH_TOKEN_LIST.addAll(Arrays.asList(tokens));
    }

    public Function<String, Mono<String>> auth(ServerHttpRequest request) {
        final List<String> authList = new ArrayList<>();
        final List<String> authParam = request.getQueryParams().get(AUTH_QUERY_PARAM);
        if (authParam != null) {
            authList.addAll(authParam);
        }
        final List<String> authHeader = request.getHeaders().get(AUTH_QUERY_PARAM);
        if (authHeader != null) {
            authList.addAll(authHeader);
        }
        boolean authStatus = false;
        for (final String token : AUTH_TOKEN_LIST) {
            if (authList.contains(AUTH_BASIC + " " + token)) {
                authStatus = true;
                break;
            }
        }
        final boolean finalAuthStatus = authStatus;
        return s -> Mono.just(finalAuthStatus ? s : P6eResultConfig.AUTH_NO_EXISTENCE);
    }

    public List<Pocket> list(String folderPath) throws IOException {
        return list(folderPath, 0, -1);
    }


    public List<Pocket> list(String folderPath, int start, int end) throws IOException {
        // 验证文件夹路径是否合法
        final File file = new File(folderPath);
        if (!file.exists()) {
            throw new IOException("[ FolderPath ==> " + folderPath + " ] folder does not exist.");
        }
        if (!file.isDirectory()) {
            throw new IOException("[ FolderPath ==> " + folderPath + " ] folder path is not a folder.");
        }
        final File[] files = file.listFiles();
        final List<Pocket> result = new ArrayList<>();
        if (files != null) {
            for (int i = start; i < (end >= 0 ? Math.min(end, files.length) : files.length); i++) {
                final File f = files[i];
                result.add(new Pocket(f.getName(), (f.isFile() ? Pocket.FILE_TYPE : Pocket.FOLDER_TYPE), f.length()));
            }
        }
        return result;
    }

    public static class Pocket {
        public static final String FILE_TYPE = "FILE_TYPE";
        public static final String FOLDER_TYPE = "FOLDER_TYPE";

        private String name;
        private String type;
        private long size;

        public Pocket() {
        }

        public Pocket(String name, String type, long size) {
            this.name = name;
            this.type = type;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }
    }

}
