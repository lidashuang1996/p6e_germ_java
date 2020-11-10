package com.dyy.p6e.germ.file2.controller;

import com.dyy.p6e.germ.file2.config.P6eConfig;
import com.dyy.p6e.germ.file2.config.P6eConfigFile;
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

    private static final String DOWNLOAD_TYPE = "DOWNLOAD";

    @GetMapping("/download/**")
    public Mono<Void> download(final ServerHttpRequest request, final ServerHttpResponse response) {
        return resource(request, response, DOWNLOAD_TYPE);
    }

    /**
     * 路由所有以 resource 开头的路径地址
     */
    @GetMapping("/resource/**")
    public Mono<Void> resource(final ServerHttpRequest request, final ServerHttpResponse response, String type) {
        try {
            final String mType = type == null ? null : type.toUpperCase();
            final String baseFilePath = p6eConfig.getFile().getBaseFilePath();
            final String[] downloadSuffixes = p6eConfig.getFile().getDownload().getSuffixes();
            final P6eConfigFile.Download.Open[] openSuffixes = p6eConfig.getFile().getDownload().getOpen();
            final String filePath = P6eFileUtil.filePathFormat(request.getPath().value(), downloadSuffixes);
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
                                        P6eConfigFile.Download.Open open = null;
                                        for (P6eConfigFile.Download.Open openSuffix : openSuffixes) {
                                            if (s.endsWith(openSuffix.getSuffix())) {
                                                open = openSuffix;
                                                break;
                                            }
                                        }
                                        if (DOWNLOAD_TYPE.equals(mType) || open == null) {
                                            response.getHeaders().setContentType(MediaType.valueOf("application/force-download"));
                                            response.getHeaders().add("Content-Disposition",
                                                    "attachment;fileName=" + P6eFileUtil.getFileName(s));
                                        } else {
                                            response.getHeaders().setContentType(MediaType.valueOf(open.getType()));
                                        }
                                        return Mono.just(dataBuffer);
                                    } finally {
                                        if (P6eFileCoreFactory.read(s, dataBuffer) == null) {
                                            LOGGER.info(logBaseInfo(request) + "FILE: " + s + ", ERROR.");
                                        } else {
                                            LOGGER.info(logBaseInfo(request) + "FILE: " + s + ", SUCCESS.");
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
