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
 * 对资源进行下载或者查看操作接口
 * @author lidashuang
 * @version 1.0
 */
@RestController
@RequestMapping("/")
public class P6eFileController extends P6eBaseController {

    /** 操作为下载的类型 */
    private static final String DOWNLOAD_TYPE = "DOWNLOAD";

    /**
     * 注入配置文件对象
     */
    @Resource
    private P6eConfig p6eConfig;

    /**
     * 执行下载的文件的接口
     * @param request 请求数据 http 对象
     * @param response 返回数据 http 对象
     * @return 正常返回数据
     */
    @GetMapping("/download/**")
    public Mono<Void> download(final ServerHttpRequest request, final ServerHttpResponse response) {
        return resource(request, response, DOWNLOAD_TYPE);
    }

    /**
     * 执行下载的文件或者查看文件的接口
     * @param request 请求数据 http 对象
     * @param response 返回数据 http 对象
     * @param type 操作的类型
     * @return 正常返回数据
     */
    @GetMapping("/resource/**")
    public Mono<Void> resource(final ServerHttpRequest request,
                               final ServerHttpResponse response, String type) {
        try {
            // 读取类型数据
            final String mType = type == null ? null : type.toUpperCase();
            final String baseFilePath = p6eConfig.getFile().getBaseFilePath();
            final P6eConfigFile.Download downloadConfig = p6eConfig.getFile().getDownload();
            final String[] downloadSuffixes = downloadConfig.getSuffixes();
            final P6eConfigFile.Download.Open[] openSuffixes = downloadConfig.getOpen();
            // 读取文件路径
            final String filePath = P6eFileUtil.filePathFormat(request.getPath().value(), downloadSuffixes);
            if (filePath == null) {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return response.writeWith(Mono.just(
                        new DefaultDataBufferFactory().allocateBuffer().write(
                                P6eResultModel.build(P6eResultConfig.ERROR_PARAM_EXCEPTION).toBytes())));
            } else {
                Mono<String> mono = Mono.just(baseFilePath + "/" + filePath);
                if (downloadConfig.getAuth().isStatus()) {
                    mono = mono.flatMap(P6eFileCoreFactory.auth(request, "DOWNLOAD"));
                }
                if (downloadConfig.getJurisdiction().isStatus()) {
                    mono = mono.flatMap(P6eFileCoreFactory.jurisdiction(request));
                }
                return response.writeWith(mono.publishOn(Schedulers.parallel())
                        .flatMap((Function<String, Mono<DataBuffer>>) s -> {
                            final DataBuffer dataBuffer = new DefaultDataBufferFactory().allocateBuffer(2048);
                            switch (s) {
                                case P6eResultConfig.AUTH_NO_EXISTENCE:
                                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                    LOGGER.info(logBaseInfo(request) + P6eResultConfig.AUTH_NO_EXISTENCE + ".");
                                    return Mono.just(dataBuffer.write(P6eResultModel.build(
                                            P6eResultConfig.ERROR_AUTH_NO_EXISTENCE).toBytes()));
                                case P6eResultConfig.JURISDICTION_NO_EXISTENCE:
                                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                                    LOGGER.info(logBaseInfo(request) + P6eResultConfig.JURISDICTION_NO_EXISTENCE + ".");
                                    return Mono.just(dataBuffer.write(P6eResultModel.build(
                                            P6eResultConfig.ERROR_JURISDICTION_NO_EXISTENCE).toBytes()));
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
                                        // 写入下载数据
                                        if (P6eFileCoreFactory.read(
                                                s, dataBuffer, downloadConfig.getCache().isStatus()) == null) {
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
