package com.dyy.p6e.germ.file2.controller;

import com.dyy.p6e.germ.file2.config.P6eConfig;
import com.dyy.p6e.germ.file2.controller.support.P6eBaseController;
import com.dyy.p6e.germ.file2.core.P6eFileCoreFactory;
import com.dyy.p6e.germ.file2.model.P6eResultConfig;
import com.dyy.p6e.germ.file2.model.P6eResultModel;
import com.dyy.p6e.germ.file2.utils.P6eFileUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.function.Function;

/**
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/")
public class P6eFileController extends P6eBaseController {

    /**
     * 注入配置文件对象
     */
    @Resource
    private P6eConfig p6eConfig;

    /**
     */
    private static final String JPG_SUFFIX = ".jpg";
    private static final String PNG_SUFFIX = ".png";
    private static final String GIF_SUFFIX = ".gif";
    private static final String JPEG_SUFFIX = ".jpeg";

    /**
     * 路由所有以 resource 开头的路径地址
     * @return
     */
    @SuppressWarnings("all")
    @GetMapping("/resource/**")
    public Mono<Void> getResource(final ServerHttpRequest request, final ServerHttpResponse response) {
        try {
            final String baseFilePath = p6eConfig.getFile().getBaseFilePath();
            final String[] downloadFileSuffixList = p6eConfig.getFile().getDownloadFileSuffixList();
            final String filePath = P6eFileUtil.filePathFormat(request.getPath().value(), downloadFileSuffixList);
            if (filePath == null) {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return response.writeWith(Mono.just(P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION))
                        .flatMap((Function<P6eResultModel, Mono<DataBuffer>>) modal ->
                                Mono.just(new DefaultDataBufferFactory().allocateBuffer().write(modal.toBytes()))));
            } else {
                return response.writeWith(Mono.just(baseFilePath + "/" + filePath)
                        .flatMap(P6eFileCoreFactory.auth(request))
                        .flatMap(P6eFileCoreFactory.jurisdiction(request))
                        .publishOn(Schedulers.parallel())
                        .flatMap((Function<String, Mono<DataBuffer>>) s -> {
                            final DataBuffer dataBuffer = new DefaultDataBufferFactory().allocateBuffer(2048);
                            switch (s) {
                                case P6eResultConfig.AUTH_NO_EXISTENCE:
                                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                    LOGGER.info(logBaseInfo(request) + P6eResultConfig.AUTH_NO_EXISTENCE + ".");
                                    return Mono.just(dataBuffer.write(
                                            P6eResultModel.build(P6eResultConfig.ERROR_AUTH_NO_EXISTENCE).toBytes()));
                                case P6eResultConfig.JURISDICTION_NO_EXISTENCE:
                                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                    LOGGER.info(logBaseInfo(request) + P6eResultConfig.JURISDICTION_NO_EXISTENCE + ".");
                                    return Mono.just(dataBuffer.write(
                                            P6eResultModel.build(P6eResultConfig.ERROR_JURISDICTION_NO_EXISTENCE).toBytes()));
                                default:
                                    // 删除路径的固定前缀
                                    s = s.substring(9);
                                    try {
                                        if (s.endsWith(JPG_SUFFIX)
                                                || s.endsWith(JPEG_SUFFIX)) {
                                            response.getHeaders().setContentType(MediaType.IMAGE_JPEG);
                                        } else if (s.endsWith(PNG_SUFFIX)) {
                                            response.getHeaders().setContentType(MediaType.IMAGE_PNG);
                                        } else if (s.endsWith(GIF_SUFFIX)) {
                                            response.getHeaders().setContentType(MediaType.IMAGE_GIF);
                                        }
                                        return Mono.just(dataBuffer);
                                    } finally {
                                        try {
                                            P6eFileCoreFactory.read(s, dataBuffer);
                                            LOGGER.info(logBaseInfo(request) + "FILE: " + s + ", SUCCESS.");
                                        } catch (IOException e) {
                                            LOGGER.error(logBaseInfo(request) + "FILE: " + s + ", " + e.getMessage());
                                        }
                                    }
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
