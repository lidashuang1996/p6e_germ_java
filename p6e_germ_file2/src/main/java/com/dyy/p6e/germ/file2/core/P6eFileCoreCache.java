package com.dyy.p6e.germ.file2.core;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eFileCoreCache {

    /** 缓存最大的空间长度 */
    private static final long MAX_CACHE_SIZE = 1024 * 1024 * 30;

    /** 缓存数据对象 */
    private final Map<String, Model> CACHE = new HashMap<>();

    public byte[] getCache(String key) {
        final Model model= CACHE.get(key);
        if (model != null) {
            return model.incr().data;
        } else {
            return null;
        }
    }

    public synchronized void clear() {
        Model.clear();
        CACHE.clear();
    }

    public synchronized void delCache(String key) {
        final Model model= CACHE.get(key);
        if (model != null) {
            Model.decrTotalSize(model.size);
            CACHE.remove(key);
        }
    }

    public synchronized void setCache(String key, byte[] value) {
        final Model model = new Model(value);
        if (MAX_CACHE_SIZE >= model.size + Model.getTotalSize()) {
            Model.incrTotalSize(model.size);
            CACHE.put(key, new Model(value));
        }
    }

    /**
     * 缓存淘汰机制
     */
    private void eliminate() {

    }

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
            return data.substring(data.length() - len, data.length());
        }
    }
    /**
     * info 方法
     * @return 返回数据的内容
     */
    public String info() {
        final int size = 60;
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\r\n");
        stringBuilder.append("|")
                .append(printing("P6E-CACHE-DATA", 58))
                .append("|")
                .append("\r\n");
        for (int i = 0; i < size; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\r\n");
        stringBuilder.append("|")
                .append(printing("MAX_CACHE_SIZE: " + MAX_CACHE_SIZE, 31))
                .append("|")
                .append(printing("TOTAL_SIZE: " + Model.getTotalSize(), 26))
                .append("|")
                .append("\r\n");
        for (int i = 0; i < size; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\r\n");
        stringBuilder.append("|")
                .append(printing("key", 20)).append("|")
                .append(printing("size", 10)).append("|")
                .append(printing("date", 15)).append("|")
                .append(printing("count", 10)).append("|")
                .append("\r\n");
        for (String key : CACHE.keySet()) {
            for (int i = 0; i < size; i++) {
                stringBuilder.append("-");
            }
            stringBuilder.append("\r\n");
            stringBuilder.append("|")
                    .append(printing(key, 20)).append("|")
                    .append(printing(String.valueOf(CACHE.get(key).size), 10)).append("|")
                    .append(printing(String.valueOf(CACHE.get(key).date), 15)).append("|")
                    .append(printing(String.valueOf(CACHE.get(key).count), 10)).append("|")
                    .append("\r\n");
        }
        for (int i = 0; i < size; i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    /**
     * 缓存的数据模型
     */
    private static class Model {
        private long count;
        private final long size;
        private final long date;
        private final byte[] data;
        public Model(byte[] data) {
            this.count = 0;
            this.data = data;
            this.size = data.length;
            this.date = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        }
        public synchronized Model incr() {
            ++ count;
            return this;
        }

        private static long TOTAL_SIZE = 0;
        private static long getTotalSize() {
            return TOTAL_SIZE;
        }
        private synchronized static void clear() {
            TOTAL_SIZE = 0;
        }
        private synchronized static long incrTotalSize(long size) {
            TOTAL_SIZE += size;
            return getTotalSize();
        }
        private synchronized static long decrTotalSize(long size) {
            TOTAL_SIZE -= size;
            return getTotalSize();
        }
    }


}
