package com.dyy.p6e.germ.file2.config;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eConfigFile implements Serializable {
    /** 基础的文件路径 */
    private String baseFilePath = "";

    /** 核心上下文 */
    private Manage context = new Manage();

    /** 上传页面的配置文件对象 */
    private Upload upload = new Upload();

    /** 下载页面的配置文件对象 */
    private Download download = new Download();

    public String getBaseFilePath() {
        return baseFilePath;
    }

    public void setBaseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    public Upload getUpload() {
        return upload;
    }

    public void setUpload(Upload upload) {
        this.upload = upload;
    }

    public Download getDownload() {
        return download;
    }

    public void setDownload(Download download) {
        this.download = download;
    }

    public Manage getContext() {
        return context;
    }

    public void setContext(Manage context) {
        this.context = context;
    }

    /**
     * 文件下载的配置
     */
    public static class Download {
        /** 认证 */
        private Manage auth = new Manage();
        /** 权限 */
        private Manage jurisdiction = new Manage();
        /** 是否缓存 */
        private Cache cache = new Cache();
        /** 允许浏览器直接打开的文件后缀 */
        private Open[] open = new Open[] {
                new Open("jpg", "image/jpeg"),
                new Open("png", "image/png"),
                new Open("gif", "image/gif"),
                new Open("jpeg", "image/jpeg"),
                new Open("ico", "image/x-icon"),
        };
        /** 允许下载的文件后缀 */
        private String[] suffixes = new String[] { "jpg", "png", "gif", "jpeg", "ico" };

        public Manage getAuth() {
            return auth;
        }

        public void setAuth(Manage auth) {
            this.auth = auth;
        }

        public Manage getJurisdiction() {
            return jurisdiction;
        }

        public void setJurisdiction(Manage jurisdiction) {
            this.jurisdiction = jurisdiction;
        }

        public Cache getCache() {
            return cache;
        }

        public void setCache(Cache cache) {
            this.cache = cache;
        }

        public Open[] getOpen() {
            return open;
        }

        public void setOpen(Open[] open) {
            this.open = open;
        }

        public String[] getSuffixes() {
            return suffixes;
        }

        public void setSuffixes(String[] suffixes) {
            this.suffixes = suffixes;
        }

        public static class Open {
            private String suffix;
            private String type;

            public Open() {
            }

            public Open(String suffix, String type) {
                this.suffix = suffix;
                this.type = type;
            }

            public String getSuffix() {
                return suffix;
            }

            public void setSuffix(String suffix) {
                this.suffix = suffix;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class Cache {
            /** 是否开启缓存 */
            private boolean status = true;
            /** 缓存的最大值 */
            private long maxSize = 1024 * 1024;
            /** 缓存的总值 */
            private long totalSize = 1024 * 1024 * 30;
            /** 允许缓存的文件后缀 */
            private String[] suffixes = new String[] { "jpg", "png", "gif", "jpeg", "ico" };

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public long getMaxSize() {
                return maxSize;
            }

            public void setMaxSize(long maxSize) {
                this.maxSize = maxSize;
            }

            public long getTotalSize() {
                return totalSize;
            }

            public void setTotalSize(long totalSize) {
                this.totalSize = totalSize;
            }

            public String[] getSuffixes() {
                return suffixes;
            }

            public void setSuffixes(String[] suffixes) {
                this.suffixes = suffixes;
            }
        }
    }

    /**
     * 文件上传的配置
     */
    public static class Upload {
        /** 是否认证 */
        private Manage auth = new Manage();
        /** 允许上传的文件大小的最大值 */
        private long maxSize = 1024 * 1024 * 3;
        /** 允许上传的文件后缀 */
        private String[] suffixes = new String[] { "jpg", "png", "gif", "jpeg", "ico" };

        public Manage getAuth() {
            return auth;
        }

        public void setAuth(Manage auth) {
            this.auth = auth;
        }

        public long getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(long maxSize) {
            this.maxSize = maxSize;
        }

        public String[] getSuffixes() {
            return suffixes;
        }

        public void setSuffixes(String[] suffixes) {
            this.suffixes = suffixes;
        }
    }

    public static class Manage {
        private String path;
        private boolean status = false;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }

}