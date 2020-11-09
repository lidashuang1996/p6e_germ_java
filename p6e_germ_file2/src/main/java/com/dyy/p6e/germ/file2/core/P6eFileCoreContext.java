package com.dyy.p6e.germ.file2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;

import java.io.IOException;

/**
 * 读取文件，写入文件
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreContext {

    /**
     * 注入的日志对象
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(P6eFileCoreContext.class);

    private final P6eFileCoreFlow p6eFileCoreFlow;
    private final P6eFileCoreCache p6eFileCoreCache;

    public P6eFileCoreContext() {
        this(new P6eFileCoreFlow(), new P6eFileCoreCache());
    }

    public P6eFileCoreContext(P6eFileCoreFlow p6eFileCoreFlow, P6eFileCoreCache p6eFileCoreCache) {
        this.p6eFileCoreFlow = p6eFileCoreFlow;
        this.p6eFileCoreCache = p6eFileCoreCache;
    }

    public byte[] read(String filePath, DataBuffer dataBuffer) throws IOException {
        return read(filePath, dataBuffer, true);
    }

    public byte[] read(String filePath, DataBuffer dataBuffer, boolean isCache) throws IOException {
        // 查询是否存在缓存数据
        if (isCache) {
            final byte[] bytes = p6eFileCoreCache.getCache(filePath);
            if (bytes != null) {
                dataBuffer.write(bytes);
                LOGGER.info("[ " + filePath + " ] ==> read cache file success, file size ( " + bytes.length + " ).");
                LOGGER.info("\r\n" + p6eFileCoreCache.info());
                return bytes;
            }
        }
        // 去文件中查找文件数据
        final byte[] bytes = p6eFileCoreFlow.read(filePath, dataBuffer);
        LOGGER.info("[ " + filePath + " ] ==> read file success.");
        if (isCache && bytes != null) {
            p6eFileCoreCache.setCache(filePath, bytes);
            LOGGER.info("CACHE ==> [ " + filePath + " ] ==> read file success, file size ( " + bytes.length + " ).");
        }
        LOGGER.info("\r\n" + p6eFileCoreCache.info());
        return bytes;
    }



    public void write(String filePath) {

    }

}
