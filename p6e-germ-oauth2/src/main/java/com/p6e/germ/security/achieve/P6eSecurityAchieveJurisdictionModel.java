package com.p6e.germ.security.achieve;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSecurityAchieveJurisdictionModel {
    private Integer id;
    private String code;
    private String name;
    private String describe;
    private String content;

    P6eSecurityAchieveJurisdictionModel(Integer id, String code, String name, String describe, String content) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.describe = describe;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public String getContent() {
        return content;
    }

    public boolean check(P6eSecurityAchieveJurisdictionModel model) {
        if (model == null) {
            return false;
        } else {
            return this.checkId(model.getId()) && this.checkCode(model.getCode());
        }
    }

    public boolean checkId(Integer id) {
        return this.getId().equals(id);
    }

    public boolean checkCode(String code) {
        return this.getCode().equals(code);
    }
}
