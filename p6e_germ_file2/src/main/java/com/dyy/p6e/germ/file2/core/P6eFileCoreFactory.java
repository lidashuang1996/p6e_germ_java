package com.dyy.p6e.germ.file2.core;

import com.dyy.p6e.germ.file2.config.P6eConfigFile;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.List;
import java.util.function.Function;

/**
 * 沟通上下文的核心工厂类
 * @author lidashuang
 * @version 1.0
 */
public final class P6eFileCoreFactory {

    /** 配置文件 */
    public static P6eConfigFile CONFIG = null;
    /** 文件核心上下文 */
    private static P6eFileCoreContext P6E_FILE_CORE_CONTEXT = new P6eFileCoreContext();
    /** 文件上传认证判断类   */
    private static P6eFileCoreAuth P6E_FILE_CORE_UPLOAD_AUTH = new P6eFileCoreAuth();
    /** 文件下载认证判断类   */
    private static P6eFileCoreAuth P6E_FILE_CORE_DOWNLOAD_AUTH = new P6eFileCoreAuth();
    /** 文件下载权限判断类 */
    private static P6eFileCoreJurisdiction P6E_FILE_CORE_JURISDICTION = new P6eFileCoreJurisdiction();

    public static void init(P6eConfigFile config) {
        // 配置文件初始化
        CONFIG = config;
        // 初始化自定义的方法
        final String cPath = config.getContext().getPath();
        final String uAuthPath = config.getUpload().getAuth().getPath();
        final String dAuthPath = config.getDownload().getAuth().getPath();
        final String dJurisdictionPath = config.getDownload().getJurisdiction().getPath();
        if (cPath != null) {
            try {
                final Class<?> aClass = Class.forName(cPath);
                if (aClass == P6eFileCoreContext.class) {
                    P6eFileCoreFactory.initCoreContext((P6eFileCoreContext) aClass.newInstance());
                }
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (uAuthPath != null) {
            try {
                final Class<?> aClass = Class.forName(uAuthPath);
                if (aClass == P6eFileCoreAuth.class) {
                    P6eFileCoreFactory.initCoreUploadAuth((P6eFileCoreAuth) aClass.newInstance());
                }
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (dAuthPath != null) {
            try {
                final Class<?> aClass = Class.forName(dAuthPath);
                if (aClass == P6eFileCoreAuth.class) {
                    P6eFileCoreFactory.initCoreDownloadAuth((P6eFileCoreAuth) aClass.newInstance());
                }
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (dJurisdictionPath != null) {
            try {
                final Class<?> aClass = Class.forName(dJurisdictionPath);
                if (aClass == P6eFileCoreJurisdiction.class) {
                    P6eFileCoreFactory.initCoreDownloadJurisdiction((P6eFileCoreJurisdiction) aClass.newInstance());
                }
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 初始化文件下载认证判断类
     * @param auth 文件下载认证判断类
     */
    public static void initCoreUploadAuth(final P6eFileCoreAuth auth) {
        P6E_FILE_CORE_UPLOAD_AUTH = auth;
    }

    /**
     * 初始化文件上传认证判断类
     * @param auth 文件上传认证判断类
     */
    public static void initCoreDownloadAuth(final P6eFileCoreAuth auth) {
        P6E_FILE_CORE_DOWNLOAD_AUTH = auth;
    }

    /**
     * 初始化文件核心上下文
     * @param context 文件核心上下文
     */
    public static void initCoreContext(final P6eFileCoreContext context) {
        P6E_FILE_CORE_CONTEXT = context;
    }

    /**
     * 初始化文件下载权限判断类
     * @param jurisdiction 文件下载权限判断类
     */
    public static void initCoreDownloadJurisdiction(final P6eFileCoreJurisdiction jurisdiction) {
        P6E_FILE_CORE_JURISDICTION = jurisdiction;
    }

    /**
     * 认证
     * @param request 请求对象
     * @return 方法
     */
    public static Function<String, Mono<String>> auth(ServerHttpRequest request, String type) {
        switch (type) {
            case "UPLOAD":
                return P6E_FILE_CORE_UPLOAD_AUTH.execute(request);
            case "DOWNLOAD":
                return P6E_FILE_CORE_DOWNLOAD_AUTH.execute(request);
            default:
                return Mono::just;
        }
    }

    /**
     * 权限
     * @param request 请求对象
     * @return 方法
     */
    public static Function<String, Mono<String>> jurisdiction(ServerHttpRequest request) {
        return P6E_FILE_CORE_JURISDICTION.execute(request);
    }

    /**
     * 读取文件
     * @param filePath 文件路径
     * @param dataBuffer 数据缓冲区
     * @param isCache 是否缓存
     * @return 读取的文件 bytes
     */
    public static byte[] read(String filePath, DataBuffer dataBuffer, boolean isCache) {
        return P6E_FILE_CORE_CONTEXT.read(filePath, dataBuffer, isCache);
    }

    /**
     * 写入文件
     * @param filePart 数据缓冲
     * @param file 文件对象
     */
    public static void write(final FilePart filePart, final File file) {
        P6E_FILE_CORE_CONTEXT.write(filePart, file);
    }

    /**
     * 文件列表
     * @param folderPath 文件路径
     * @param start 开始的位置
     * @param end 结束的位置
     * @return 查询的结果内容
     */
    public static List<P6eFileCoreContext.Pocket> list(String folderPath, int start, int end) {
        return P6E_FILE_CORE_CONTEXT.list(folderPath, start, end);
    }
}
