package com.p6e.germ.common.config;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSecurityConfig {

    /** 白名单 */
    private String[] whiteList = new String[0];

    /** 黑名单 */
    private String[] blackList = new String[0];

    public String[] getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String[] whiteList) {
        this.whiteList = whiteList;
    }

    public String[] getBlackList() {
        return blackList;
    }

    public void setBlackList(String[] blackList) {
        this.blackList = blackList;
    }

}
