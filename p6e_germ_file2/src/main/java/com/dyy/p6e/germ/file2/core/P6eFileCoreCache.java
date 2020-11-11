package com.dyy.p6e.germ.file2.core;

import com.dyy.p6e.germ.file2.config.P6eConfigFile;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 缓存数据的对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreCache {

    /** 缓存数据对象 */
    private final Map<String, Model> CACHE = new HashMap<>();

    /**
     * 缓存清除
     */
    public synchronized void clear() {
        CACHE.clear();
        Model.clear();
    }

    /**
     * 获取缓存
     * @param key 缓存的 KEY
     * @return 缓存的 VALUE
     */
    public byte[] getCache(String key) {
        final Model model= CACHE.get(key);
        if (model != null) {
            return model.incr().refreshDate().data;
        } else {
            return null;
        }
    }

    /**
     * 删除指定的缓存
     * @param key 缓存的 KEY
     */
    public synchronized void delCache(String key) {
        final Model model= CACHE.get(key);
        if (model != null) {
            CACHE.remove(key);
            Model.decrTotalSize(model.size);
        }
    }

    /**
     * 添加缓存
     * @param key 缓存的 KEY
     * @param value 缓存的 VALUE
     */
    public synchronized void setCache(String key, byte[] value) {
        if (P6eFileCoreFactory.CONFIG != null) {
            final Model model = new Model(value);
            final P6eConfigFile.Download.Cache cache = P6eFileCoreFactory.CONFIG.getDownload().getCache();
            if (cache.getMaxSize() >= model.size) {
                if (cache.getTotalSize() >= model.size + Model.getTotalSize()) {
                    Model.incrTotalSize(model.size);
                    CACHE.put(key, model);
                    // 触发淘汰机制
                    eliminate();
                }
            }
        }
    }

    /**
     * 缓存淘汰机制
     */
    private void eliminate() {
        // 淘汰因子
        final double factor = 0.8;
        final long timestamp = 10800000;
        final P6eConfigFile.Download.Cache cache = P6eFileCoreFactory.CONFIG.getDownload().getCache();
        if (cache.getTotalSize() * factor < Model.getTotalSize()) {
            // 时间淘汰
            final long currentTimestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            final List<Model> valueList = CACHE.values().stream().filter(
                    s -> currentTimestamp > s.date + timestamp).collect(Collectors.toList());
            for (Model model : valueList) {
                for (String key : CACHE.keySet()) {
                    if (CACHE.get(key) == model) {
                        delCache(key);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 打印数据方法
     * @param data 数据内容
     * @param len 长度
     * @return 处理后的数据内容
     */
    private String printing (String data, int len) {
        if (data.length() <= len) {
            int index = 0;
            final int pf = 2;
            final StringBuilder stringBuilder = new StringBuilder();
            for (; index < (len - data.length()) / pf; index++) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(data);
            for (; index + data.length() < len; index++) {
                stringBuilder.append(" ");
            }
            return stringBuilder.toString();
        } else {
            return data.substring(data.length() - len);
        }
    }

    /**
     * 打印换行数据长度
     * @param size 长度
     * @return 处理后的数据内容
     */
    private String printingDivision (int size) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }


    /**
     * info 方法
     * @return 返回数据的内容
     */
    public String info() {
        final int size = 130;
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(printingDivision(size));
        stringBuilder.append("|")
                .append(printing("P6E-CACHE-DATA", 128))
                .append("|")
                .append("\r\n");
        stringBuilder.append(printingDivision(size));
        stringBuilder.append("|")
                .append(printing("MAX_CACHE_SIZE: "
                        + P6eFileCoreFactory.CONFIG.getDownload().getCache().getTotalSize(), 90))
                .append("|")
                .append(printing("CURRENT_SIZE: " + Model.getTotalSize(), 37))
                .append("|")
                .append("\r\n");
        stringBuilder.append(printingDivision(size));
        stringBuilder.append("|")
                .append(printing("key", 90)).append("|")
                .append(printing("size", 10)).append("|")
                .append(printing("date", 15)).append("|")
                .append(printing("count", 10)).append("|")
                .append("\r\n");
        for (String key : CACHE.keySet()) {
            stringBuilder.append(printingDivision(size));
            stringBuilder.append("|")
                    .append(printing(key, 90)).append("|")
                    .append(printing(String.valueOf(CACHE.get(key).size), 10)).append("|")
                    .append(printing(String.valueOf(CACHE.get(key).date), 15)).append("|")
                    .append(printing(String.valueOf(CACHE.get(key).count), 10)).append("|")
                    .append("\r\n");
        }
        stringBuilder.append(printingDivision(size));
        return stringBuilder.toString();
    }

    /**
     * 缓存的数据模型
     */
    private static class Model {

        /**
         * 缓存的统计
         */
        private static long TOTAL_SIZE = 0;

        /**
         * 获取缓存的大小
         * @return 大小的内容
         */
        private static long getTotalSize() {
            return TOTAL_SIZE;
        }

        /**
         * 缓存的大小归零
         */
        private synchronized static void clear() {
            TOTAL_SIZE = 0;
        }

        /**
         * 缓存的大小自增
         * @param size 大小
         * @return 缓存的大小
         */
        private synchronized static long incrTotalSize(long size) {
            TOTAL_SIZE += size;
            return getTotalSize();
        }

        /**
         * 缓存的大小自减
         * @param size 大小
         * @return 缓存的大小
         */
        private synchronized static long decrTotalSize(long size) {
            TOTAL_SIZE -= size;
            return getTotalSize();
        }


        /** 调用次数 */
        private long count;
        /** 文件时间 */
        private long date;
        /** 文件长度 */
        private final long size;
        /** 文件数据 */
        private final byte[] data;

        /**
         * 构造方法初始化
         * @param data 文件内容
         */
        public Model(byte[] data) {
            this.count = 0;
            this.data = data;
            this.size = data.length;
            this.date = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        }

        public synchronized Model refreshDate() {
            this.date = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
            return this;
        }

        /**
         * 文件调用次数自增
         * @return 自增模型
         */
        public synchronized Model incr() {
            ++ count;
            return this;
        }
    }
}
