package com.dyy.p6e.germ.file2.controller;

import com.dyy.p6e.germ.file2.config.P6eConfig;
import com.dyy.p6e.germ.file2.controller.support.P6eBaseController;
import com.dyy.p6e.germ.file2.core.P6eFileCoreContext;
import com.dyy.p6e.germ.file2.core.P6eFileCoreFactory;
import com.dyy.p6e.germ.file2.model.P6eResultConfig;
import com.dyy.p6e.germ.file2.model.P6eResultModel;
import com.dyy.p6e.germ.file2.utils.P6eFileUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import javax.annotation.Resource;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    private static final String CONTENT_LENGTH_HEADER = "Content-Length";

    @GetMapping("/operation/list/**")
    public Mono<Void> list(Integer start, Integer end,
                           final ServerHttpRequest request,
                           final ServerHttpResponse response) {
        try {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            final String baseFilePath = p6eConfig.getFile().getBaseFilePath();
            final String filePath = P6eFileUtil.folderPathFormat(request.getPath().value());
            if (filePath == null) {
                return response.writeWith(Mono.just(P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION))
                        .flatMap((Function<P6eResultModel, Mono<DataBuffer>>) modal ->
                                Mono.just(new DefaultDataBufferFactory().allocateBuffer().write(modal.toBytes()))));
            } else {
                // 创建 Mono 对象
                Mono<String> mono = Mono.just(baseFilePath + "/" + filePath);
                // 读取配置文件数据
                if (p6eConfig.getFile().getUpload().getAuth().isStatus()) {
                    // 添加认证模块
                    mono = mono.flatMap(P6eFileCoreFactory.auth(request, "UPLOAD"));
                }
                return response.writeWith(
                        mono.flatMap(s -> {
                            final DataBuffer dataBuffer = new DefaultDataBufferFactory().allocateBuffer(2048);
                            if (P6eResultConfig.AUTH_NO_EXISTENCE.equals(s)) {
                                LOGGER.info(logBaseInfo(request) + P6eResultConfig.AUTH_NO_EXISTENCE + ".");
                                return Mono.just(dataBuffer.write(
                                        P6eResultModel.build(P6eResultConfig.ERROR_AUTH_NO_EXISTENCE).toBytes()));
                            } else {
                                // 删除路径的固定前缀
                                s = s.substring(15);
                                // 需要读取的数据长度配置
                                int pStart = 0, pEnd = -1;
                                if (start != null) {
                                    pStart = start;
                                }
                                if (end != null) {
                                    pEnd = end;
                                }
                                final List<P6eFileCoreContext.Pocket> list = P6eFileCoreFactory.list(s, pStart, pEnd);
                                LOGGER.info(logBaseInfo(request) + "FOLDER: " + s + ", SUCCESS.");
                                return Mono.just(dataBuffer.write(P6eResultModel.build(P6eResultConfig.SUCCESS, list).toBytes()));
                            }
                        }));
            }
        } catch (Exception e) {
            LOGGER.error(logBaseInfo(request) + " ==> " + e.getMessage());
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response.writeWith(Mono.just(new DefaultDataBufferFactory().allocateBuffer()
                    .write(P6eResultModel.build(P6eResultConfig.ERROR_AUTH_NO_EXISTENCE).toBytes())));
        }
    }

    @PostMapping("/operation/upload/**")
    public Mono<Void> upload(@RequestPart("file") FilePart filePart,
                             final ServerHttpRequest request, final ServerHttpResponse response) {
        try {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            final String baseFilePath = p6eConfig.getFile().getBaseFilePath();
            final long uploadMaxSize = p6eConfig.getFile().getUpload().getMaxSize();
            final String[] uploadSuffixes = p6eConfig.getFile().getUpload().getSuffixes();
            // 读取请求的大小
            long fSize = 0;
            String fName = filePart.filename();
            final List<String> sizeList = request.getHeaders().get(CONTENT_LENGTH_HEADER);
            if (sizeList != null && sizeList.size() > 0) {
                fSize = Long.parseLong(sizeList.get(0));
            }
            if (fSize == 0 || fSize > uploadMaxSize) {
                return response.writeWith(Mono.just(P6eResultModel.build(P6eResultConfig.ERROR_FILE_SIZE_EXCEPTION))
                        .flatMap((Function<P6eResultModel, Mono<DataBuffer>>) modal ->
                                Mono.just(new DefaultDataBufferFactory().allocateBuffer().write(modal.toBytes()))));
            }
            if (P6eFileUtil.filePathFormat(fName, uploadSuffixes) == null) {
                return response.writeWith(Mono.just(P6eResultModel.build(P6eResultConfig.ERROR_FILE_SUFFIX_EXCEPTION))
                        .flatMap((Function<P6eResultModel, Mono<DataBuffer>>) modal ->
                                Mono.just(new DefaultDataBufferFactory().allocateBuffer().write(modal.toBytes()))));
            }
            final String filePath = P6eFileUtil.folderPathFormat(request.getPath().value());
            if (filePath == null) {
                return response.writeWith(Mono.just(P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION))
                        .flatMap((Function<P6eResultModel, Mono<DataBuffer>>) modal ->
                                Mono.just(new DefaultDataBufferFactory().allocateBuffer().write(modal.toBytes()))));
            } else {
                // 创建 Mono 对象
                Mono<String> mono = Mono.just(baseFilePath + "/" + filePath);
                // 读取配置文件数据
                if (p6eConfig.getFile().getUpload().getAuth().isStatus()) {
                    // 添加认证模块
                    mono = mono.flatMap(P6eFileCoreFactory.auth(request, "UPLOAD"));
                }
                final long ffSize = fSize;
                return response.writeWith(mono.flatMap(s -> {
                    final DataBuffer dataBuffer = new DefaultDataBufferFactory().allocateBuffer(2048);
                    if (P6eResultConfig.AUTH_NO_EXISTENCE.equals(s)) {
                        LOGGER.info(logBaseInfo(request) + P6eResultConfig.AUTH_NO_EXISTENCE + ".");
                        return Mono.just(dataBuffer.write(P6eResultModel.build(P6eResultConfig.ERROR_AUTH_NO_EXISTENCE).toBytes()));
                    } else {
                        // 下载文件到文件目录
                        final File file = new File(P6eFileUtil.filePathRename(s.substring(17)));
                        // 写入文件
                        P6eFileCoreFactory.write(filePart, file);
                        return Mono.just(file)
                                .flatMap((Function<File, Mono<DataBuffer>>) f -> {
                                    // 判断写入的文件长度跟读取的文件长度是否大（用户可以修改提交的头里面长度）
                                    if (f.length() > ffSize) {
                                        LOGGER.info(logBaseInfo(request) + "[** path: "
                                                + file.getPath() + ", size: " + file.length() + " **] => "
                                                + "file size error. file delete ? " + f.delete());
                                        return Mono.just(dataBuffer.write(
                                                P6eResultModel.build(P6eResultConfig.ERROR_FILE_SIZE_EXCEPTION).toBytes()));
                                    } else {
                                        final Map<String, Object> result = new HashMap<>(2);
                                        result.put("size", f.length());
                                        result.put("name", f.getName());
                                        LOGGER.info(logBaseInfo(request) + "[** path: "
                                                + file.getPath() + ", size: " + file.length() + " **] => success.");
                                        return Mono.just(dataBuffer.write(
                                                P6eResultModel.build(P6eResultConfig.SUCCESS, result).toBytes()));
                                    }
                                });
                    }
                }));
            }
        } catch (Exception e) {
            LOGGER.error(logBaseInfo(request) + " ==> " + e.getMessage());
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            return response.writeWith(Mono.just(new DefaultDataBufferFactory().allocateBuffer()
                    .write(P6eResultModel.build(P6eResultConfig.ERROR_AUTH_NO_EXISTENCE).toBytes())));
        }
    }
}
