package com.p6e.germ.common.config;

/**
 * 数据配置文件
 * @author lidashuang
 * @version 1.0
 */
public class P6eDatabaseConfig {
    /** 默认的查询页码配置 */
    private int defaultPage = 1;
    /** 默认的查询长度配置 */
    private int defaultSize = 16;
    /** 默认的查询最大长度配置 */
    private int defaultMaxSize = 300;

    public int getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getDefaultMaxSize() {
        return defaultMaxSize;
    }

    public void setDefaultMaxSize(int defaultMaxSize) {
        this.defaultMaxSize = defaultMaxSize;
    }
}
