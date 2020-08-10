package com.mobiroller.models;

import java.util.List;

public class RoleModel {
    List<MChatRoleModel> chatRoles;
    String updateDate;

    public RoleModel(String str, List<MChatRoleModel> list) {
        this.updateDate = str;
        this.chatRoles = list;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(String str) {
        this.updateDate = str;
    }

    public List<MChatRoleModel> getChatRoles() {
        return this.chatRoles;
    }

    public void setChatRoles(List<MChatRoleModel> list) {
        this.chatRoles = list;
    }
}
