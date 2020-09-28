package com.p6e.germ.security.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 认证的模型对象
 * 自定义的认证的模型对象需要继承 P6eAuthModel 对象
 * @author lidashuang
 * @version 1.0
 */
public class P6eAuthModel extends com.p6e.germ.starter.oauth2.P6eAuthModel implements Serializable {
    private Integer id;
    private Integer status;
    private String avatar;
    private String name;
    private String nickname;
    private String sex;
    private String birthday;
    private String role;
    private String token;
    private String refreshToken;
    private Long expirationTime;
    private List<Map<String, String>> groupList;
    private List<Map<String, String>> jurisdictionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Map<String, String>> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Map<String, String>> groupList) {
        this.groupList = groupList;
    }

    public List<Map<String, String>> getJurisdictionList() {
        return jurisdictionList;
    }

    public void setJurisdictionList(List<Map<String, String>> jurisdictionList) {
        this.jurisdictionList = jurisdictionList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
