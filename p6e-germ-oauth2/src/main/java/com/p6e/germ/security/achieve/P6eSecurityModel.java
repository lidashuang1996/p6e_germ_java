package com.p6e.germ.security.achieve;

import java.io.Serializable;
import java.util.List;

/**
 * @author lidashuang
 * @version 1.0
 */
public class P6eSecurityModel implements Serializable {

    private List<P6eSecurityAchieveGroupModel> groupModelList;
    private List<P6eSecurityAchieveJurisdictionModel> jurisdictionModelList;

    public List<P6eSecurityAchieveGroupModel> getGroupModelList() {
        return groupModelList;
    }

    public void setGroupModelList(List<P6eSecurityAchieveGroupModel> groupModelList) {
        this.groupModelList = groupModelList;
    }

    public List<P6eSecurityAchieveJurisdictionModel> getJurisdictionModelList() {
        return jurisdictionModelList;
    }

    public void setJurisdictionModelList(List<P6eSecurityAchieveJurisdictionModel> jurisdictionModelList) {
        this.jurisdictionModelList = jurisdictionModelList;
    }

    public boolean checkGroup(final Integer id) {
        for (P6eSecurityAchieveGroupModel model : groupModelList) {
            if (model.checkId(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkJurisdiction(final String code) {
        for (P6eSecurityAchieveJurisdictionModel model : jurisdictionModelList) {
            if (model.checkCode(code)) {
                return true;
            }
        }
        return false;
    }
}
