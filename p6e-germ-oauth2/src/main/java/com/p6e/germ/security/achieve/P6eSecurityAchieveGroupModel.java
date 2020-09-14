package com.p6e.germ.security.achieve;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSecurityAchieveGroupModel {
    private Integer id;
    private String name;
    private String describe;
    private Integer weight;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescribe() {
        return describe;
    }

    public Integer getWeight() {
        return weight;
    }

    public boolean check(P6eSecurityAchieveGroupModel model) {
        if (model == null) {
            return false;
        } else {
            return this.checkId(model.getId());
        }
    }

    public boolean checkId(Integer id) {
        return this.getId().equals(id);
    }
}
