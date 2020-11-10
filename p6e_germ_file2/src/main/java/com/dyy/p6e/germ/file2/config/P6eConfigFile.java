package com.dyy.p6e.germ.file2.config;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eConfigFile implements Serializable {
    private String baseFilePath = "";
    private String[] downloadFileSuffixList = new String[] { "jpg", "png", "gif", "jpeg" };
    private String[] uploadFileSuffixList = new String[] { "jpg", "png", "gif", "jpeg" };

    private Upload upload = new Upload();
    private Manage manage = new Manage();

    public String getBaseFilePath() {
        return baseFilePath;
    }

    public void setBaseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    public String[] getDownloadFileSuffixList() {
        return downloadFileSuffixList;
    }

    public void setDownloadFileSuffixList(String[] downloadFileSuffixList) {
        this.downloadFileSuffixList = downloadFileSuffixList;
    }

    public String[] getUploadFileSuffixList() {
        return uploadFileSuffixList;
    }

    public void setUploadFileSuffixList(String[] uploadFileSuffixList) {
        this.uploadFileSuffixList = uploadFileSuffixList;
    }

    public Manage getManage() {
        return manage;
    }

    public void setManage(Manage manage) {
        this.manage = manage;
    }

    public Upload getUpload() {
        return upload;
    }

    public void setUpload(Upload upload) {
        this.upload = upload;
    }

    /**
     * 上🛏️的格式
     */
    public static class Upload {
        private boolean auth = false;
        private long maxSize = 1024 * 1024 * 3;
        private String[] suffixes = new String[] { "jpg", "png", "gif", "jpeg" };
        public String[] getSuffixes() {
            return suffixes;
        }

        public void setSuffixes(String[] suffixes) {
            this.suffixes = suffixes;
        }

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
    }

    /**
     * 管理里面需要的 Manage
     */
    public static class Manage {
        private String[] tokens = new String[0];

        public String[] getTokens() {
            return tokens;
        }

        public void setTokens(String[] tokens) {
            this.tokens = tokens;
        }
    }
}