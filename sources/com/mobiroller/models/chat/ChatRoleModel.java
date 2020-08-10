package com.mobiroller.models.chat;

import java.io.Serializable;

public class ChatRoleModel implements Serializable {
    private String chatRoleDescription;
    private String chatRoleName;

    /* renamed from: id */
    private int f2174id;
    private String idString;
    private boolean isIncognito;
    private String permissionTypeID;
    private int rank;
    private String ribbonImage;

    public ChatRoleModel(String str, String str2, String str3, int i, boolean z, int i2, String str4) {
        this.chatRoleName = str;
        this.chatRoleDescription = str2;
        this.ribbonImage = str3;
        this.permissionTypeID = String.valueOf(i);
        this.isIncognito = z;
        this.rank = i2;
        this.idString = str4;
        this.f2174id = 0;
    }

    public String getPermissionTypeID() {
        return this.permissionTypeID;
    }

    public void setPermissionTypeID(String str) {
        this.permissionTypeID = str;
    }

    public String getId() {
        return this.idString;
    }

    public void setId(String str) {
        this.idString = str;
    }

    public ChatRoleModel() {
    }

    public String getChatRoleName() {
        return this.chatRoleName;
    }

    public void setChatRoleName(String str) {
        this.chatRoleName = str;
    }

    public String getChatRoleDescription() {
        return this.chatRoleDescription;
    }

    public void setChatRoleDescription(String str) {
        this.chatRoleDescription = str;
    }

    public String getRibbonImage() {
        return this.ribbonImage;
    }

    public void setRibbonImage(String str) {
        this.ribbonImage = str;
    }

    public String getPermissionType() {
        return this.permissionTypeID;
    }

    public void setPermissionType(String str) {
        this.permissionTypeID = str;
    }

    public boolean isIncognito() {
        return this.isIncognito;
    }

    public void setIncognito(boolean z) {
        this.isIncognito = z;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int i) {
        this.rank = i;
    }

    public String toString() {
        return this.chatRoleName;
    }
}
