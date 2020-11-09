package com.dyy.p6e.germ.file2.controller;

import com.dyy.p6e.germ.file2.config.P6eConfig;
import com.dyy.p6e.germ.file2.controller.support.P6eBaseController;
import com.dyy.p6e.germ.file2.core.P6eFileCoreFactory;
import com.dyy.p6e.germ.file2.core.P6eFileCoreManage;
import com.dyy.p6e.germ.file2.model.P6eResultConfig;
import com.dyy.p6e.germ.file2.model.P6eResultModel;
import com.dyy.p6e.germ.file2.utils.P6eFileUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.List;
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
                return response.writeWith(Mono.just(baseFilePath + "/" + filePath)
                        .flatMap(P6eFileCoreFactory.manageAuth(request))
                        .flatMap(s -> {
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
                                final List<P6eFileCoreManage.Pocket> list = P6eFileCoreFactory.manageList(s, pStart, pEnd);
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
    public Mono<Void> upload(final ServerHttpRequest request, final ServerHttpResponse response) {
        return Mono.just("11111").then();
    }

}
