package com.dyy.p6e.germ.file2.config;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eConfigFile implements Serializable {
    /** 基础的文件路径 */
    private String baseFilePath = "";

    /** 上传页面的配置文件对象 */
    private Upload upload = new Upload();

    /** 管理页面的配置文件对象 */
    private Manage manage = new Manage();

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

    public Manage getManage() {
        return manage;
    }

    public void setManage(Manage manage) {
        this.manage = manage;
    }

    public Download getDownload() {
        return download;
    }

    public void setDownload(Download download) {
        this.download = download;
    }

    /**
     * 文件下载的配置
     */
    public static class Download {
        /** 是否认证 */
        private boolean auth = false;
        /** 是否权限 */
        private boolean jurisdiction = false;
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

        public boolean isAuth() {
            return auth;
        }

        public void setAuth(boolean auth) {
            this.auth = auth;
        }

        public boolean isJurisdiction() {
            return jurisdiction;
        }

        public void setJurisdiction(boolean jurisdiction) {
            this.jurisdiction = jurisdiction;
        }

        public String[] getSuffixes() {
            return suffixes;
        }

        public void setSuffixes(String[] suffixes) {
            this.suffixes = suffixes;
        }

        public Open[] getOpen() {
            return open;
        }

        public void setOpen(Open[] open) {
            this.open = open;
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
    }

    /**
     * 文件上传的配置
     */
    public static class Upload {
        /** 是否认证 */
        private boolean auth = false;
        /** 允许上传的文件大小的最大值 */
        private long maxSize = 1024 * 1024 * 3;
        /** 允许上传的文件后缀 */
        private String[] suffixes = new String[] { "jpg", "png", "gif", "jpeg", "ico" };

        public boolean isAuth() {
            return auth;
        }

        public void setAuth(boolean auth) {
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

    /**
     * 管理页面的配置
     */
    public static class Manage {
        /** 令牌盒子 */
        private String[] tokens = new String[0];

        public String[] getTokens() {
            return tokens;
        }

        public void setTokens(String[] tokens) {
            this.tokens = tokens;
        }
    }

}