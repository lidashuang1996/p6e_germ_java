package com.dyy.p6e.germ.file2.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件操作的常用的工具类
 * 1. 对 URL 数据编码数据
 * 2. 文件路径格式化
 * 3. 文件夹路径格式化
 * 4. 文件路径重命名（UUID）
 * 5. 文件路径重命名（自动定义）
 * 6. 文件路径中获取文件名称
 * @author lidashuang
 * @version 1.0
 */
public final class P6eFileUtil {

    /**
     * URL 编码数据
     * @param url 待编码的 URL
     * @return 编码后的数据
     */
    private static String decodeUrl(String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

    /**
     * 文件路径格式化
     * @param filePath 文件路径
     * @param suffixes 格式化文件后缀数组
     * @return 格式化后的文件路径
     */
    public static String filePathFormat(String filePath, String[] suffixes) {
        final String[] filePathBlock = decodeUrl(filePath).split("/");
        if (filePathBlock.length > 0) {
            final StringBuilder result = new StringBuilder();
            // 遍历文件块
            for (int i = 0; i < filePathBlock.length; i++) {
                String block = filePathBlock[i];
                // 替换掉敏感字符
                block = block
                        .replaceAll("\\\\", "")
                        .replaceAll("\\.\\.", "")
                        .replaceAll("\\.\\\\", "")
                        .replaceAll("\\.\\.\\\\", "");
                // 过滤掉无意义的字符
                if (block.length() > 0 && !block.endsWith(".")) {
                    // 判断是不是最后一个文件块
                    if (i + 1 == filePathBlock.length) {
                        boolean status = true;
                        // 获取文件后缀名称
                        for (int j = block.length() - 1; j >= 0; j--) {
                            if (j - 1 >= 0
                                    && j + 1 < block.length()
                                    && block.charAt(j - 1) != '.'
                                    && block.charAt(j) == '.') {
                                final String s = block.substring(j + 1).toLowerCase();
                                // 比对后缀是否在给出的文件后缀数组中
                                for (String suffix : suffixes) {
                                    if (s.equals(suffix.toLowerCase())) {
                                        status = false;
                                        result.append(block).append("/");
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (status) {
                            return null;
                        }
                    } else {
                        result.append(block).append("/");
                    }
                }
            }
            if (result.length() > 0) {
                return result.substring(0, result.length() - 1);
            }
        }
        return null;
    }

    /**
     * 文件夹路径格式化
     * @param folderPath 文件夹路径
     * @return 格式化后的文件夹路径
     */
    public static String folderPathFormat(String folderPath) {
        final String[] folderPathBlock = decodeUrl(folderPath).split("/");
        if (folderPathBlock.length > 0) {
            final StringBuilder result = new StringBuilder();
            // 遍历文件块
            for (String block : folderPathBlock) {
                // 替换掉敏感字符
                block = block
                        .replaceAll("\\\\", "")
                        .replaceAll("\\.\\.", "")
                        .replaceAll("\\.\\\\", "")
                        .replaceAll("\\.\\.\\\\", "");
                // 过滤掉无意义的字符
                if (block.length() > 0 && !block.endsWith(".")) {
                    result.append(block).append("/");
                }
            }
            return result.toString();
        }
        return null;
    }

    /**
     * 文件路径中对文件重命名（重命名采用 UUID 替换）
     * @param filePath 文件路径
     * @return 重命名后的文件路径
     */
    public static String filePathRename(String filePath) {
        return filePathRename(filePath, UUID.randomUUID().toString().replaceAll("-", ""));
    }

    /**
     * 文件路径中对文件重命名
     * @param filePath 文件路径
     * @param rename 重命名名称
     * @return 重命名后的文件路径
     */
    public static String filePathRename(String filePath, String rename) {
        int start = 0, end = 0;
        for (int i = filePath.length() - 1; i >= 0; i--) {
            if (filePath.charAt(i) == '.') {
                end = i;
            }
            if (filePath.charAt(i) == '/') {
                start = i;
                break;
            }
        }
        if (end > start) {
            return filePath.substring(0, start) + (start > 0 ? "/" : "") + rename + filePath.substring(end);
        } else {
            return null;
        }
    }

    /**
     * 文件路径中获取文件名称
     * @param filePath 文件路径
     * @return 文件名称
     */
    public static String getFileName(String filePath) {
        int index = filePath.lastIndexOf("/");
        String fileName = index > 0 ? filePath.substring(index) : filePath;
        try {
            return URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return fileName;
        }
    }

}
