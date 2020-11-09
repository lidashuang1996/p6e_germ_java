package com.dyy.p6e.germ.file2.config;

import java.io.Serializable;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eConfigFile implements Serializable {
    private String baseFilePath = "";
    private String[] downloadFileSuffixList = new String[] { "jpg", "png", "gif", "jpeg" };

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
}