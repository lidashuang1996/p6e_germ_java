package com.dyy.p6e.germ.file2.utils;

/**
 * 文件操作的常用的工具类
 * 1. 文件路径格式化
 * @author lidashuang
 * @version 1.0
 */
public final class P6eFileUtil {

    /**
     * 文件路径格式化
     * @param filePath 文件路径
     * @param suffixList 格式化文件后缀数组
     * @return 格式化后的文件路径
     */
    public static String filePathFormat(String filePath, String[] suffixList) {
        final String[] filePathBlock = filePath.split("/");
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
                                for (String suffix : suffixList) {
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

}
