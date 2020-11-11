package com.dyy.p6e.germ.file2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 文件流处理对象
     */
    private final P6eFileCoreFlow p6eFileCoreFlow;

    /**
     * 文件缓存处理对象
     */
    private final P6eFileCoreCache p6eFileCoreCache;

    /**
     * 构造方法初始化参数
     */
    public P6eFileCoreContext() {
        this(new P6eFileCoreFlow(), new P6eFileCoreCache());
    }

    /**
     * 自定义的实现的方法
     * @param p6eFileCoreFlow 文件流处理对象
     * @param p6eFileCoreCache 文件缓存处理对象
     */
    public P6eFileCoreContext(P6eFileCoreFlow p6eFileCoreFlow, P6eFileCoreCache p6eFileCoreCache) {
        this.p6eFileCoreFlow = p6eFileCoreFlow;
        this.p6eFileCoreCache = p6eFileCoreCache;
    }

    /**
     * 读取文件数据
     * @param filePath 文件路径
     * @param dataBuffer 缓冲区
     * @param isCache 是否缓存
     * @return 读取的文件 bytes
     */
    public byte[] read(String filePath, DataBuffer dataBuffer, boolean isCache) {
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

    /**
     * 写入文件数据的方法
     * @param filePart 文件流对象
     * @param file 文件对象
     */
    public void write(final FilePart filePart, final File file) {
        p6eFileCoreFlow.write(filePart, file);
    }

    /**
     * 读取文件夹下面的数据
     * @param folderPath 文件夹路径
     * @param start 开始
     * @param end 结束
     * @return 返回的列表数据的包装类型
     */
    public List<Pocket> list(String folderPath, int start, int end) {
        // 验证文件夹路径是否合法
        final File file = new File(folderPath);
        final List<Pocket> result = new ArrayList<>();
        if (!file.exists() || !file.isDirectory()) {
            return result;
        }
        final File[] files = file.listFiles();
        if (files != null) {
            for (int i = start; i < (end >= 0 ? Math.min(end, files.length) : files.length); i++) {
                final File f = files[i];
                result.add(new Pocket(f.getName(), (f.isFile() ? Pocket.FILE_TYPE : Pocket.FOLDER_TYPE), f.length()));
            }
        }
        return result;
    }

    /**
     * 列表数据的包装类型
     */
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
