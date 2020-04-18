package com.dyy.p6e.germ.file.model;

public class P6eGermFileResultModel {
    private Long fileSize;
    private String fileName;
    private String fileBigPath;
    private String fileSmallPath;

    public P6eGermFileResultModel() {
    }

    public P6eGermFileResultModel(Long fileSize, String fileName, String fileBigPath, String fileSmallPath) {
        this.fileSize = fileSize;
        this.fileName = fileName;
        this.fileBigPath = fileBigPath;
        this.fileSmallPath = fileSmallPath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileBigPath() {
        return fileBigPath;
    }

    public void setFileBigPath(String fileBigPath) {
        this.fileBigPath = fileBigPath;
    }

    public String getFileSmallPath() {
        return fileSmallPath;
    }

    public void setFileSmallPath(String fileSmallPath) {
        this.fileSmallPath = fileSmallPath;
    }

    @Override
    public String toString() {
        return "{" + "\"fileSize\":" +
                fileSize +
                ",\"fileName\":\"" +
                fileName + '\"' +
                ",\"fileBigPath\":\"" +
                fileBigPath + '\"' +
                ",\"fileSmallPath\":\"" +
                fileSmallPath + '\"' +
                '}';
    }
}
