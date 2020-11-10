package com.dyy.p6e.germ.file2.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreFlow {

    private static final Logger LOGGER = LoggerFactory.getLogger(P6eFileCoreFlow.class);
    /**
     * 缓冲区的大小
     */
    private static final int BUFFER_SIZE = 2048;

    public byte[] read(final String filePath, final DataBuffer dataBuffer) throws IOException {
        // 验证文件路径是否合法
        final File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("[ FilePath ==> " + filePath + " ] file does not exist.");
        }
        if (!file.isFile()) {
            throw new IOException("[ FilePath ==> " + filePath + " ] file path is not a file.");
        }
        int cIndex = 0;
        final byte[] cache = new byte[Integer.parseInt(String.valueOf(file.length()))];
        FileChannel fileChannel = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileChannel = fileInputStream.getChannel();
            int nGet;
            final byte[] byteArray = new byte[BUFFER_SIZE];
            final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
            // 循环读取文件的内容到直接缓冲区，直到文件读取完毕
            while(fileChannel.read(byteBuffer) != -1){
                byteBuffer.flip();
                // 循环读取数据直到缓冲区的数据读取完成
                while (byteBuffer.hasRemaining()) {
                    nGet = Math.min(byteBuffer.remaining(), BUFFER_SIZE);
                    // read bytes from disk
                    byteBuffer.get(byteArray, 0, nGet);
                    dataBuffer.write(byteArray);
                    // copy data to cache
                    System.arraycopy(byteArray, 0, cache, cIndex, nGet);
                    // cIndex icr
                    cIndex += nGet;
                }
                byteBuffer.clear();
            }
        } finally {
            try {
                if (fileChannel != null) {
                    fileChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cache;
    }

    public void write(final FilePart filePart, final File file) {
        // 验证文件路径是否合法
        if (!file.getParentFile().exists()) {
            LOGGER.info("[ FilePath ==> " + file.getPath() + " ] " +
                    "folder path create ? ==> " + file.getParentFile().mkdirs());
        }
        filePart.transferTo(file);
    }

}
