package com.mobiroller.models;

import com.mobiroller.models.chat.ChatRoleModel;
import java.io.Serializable;

public class MChatRoleModel implements Serializable {
    private int chatPermissionID;
    private boolean isIncognito;
    private String ribbonImage;
    private String roleDescription;
    private String roleID;
    private String roleName;
    private int roleRank;

    public MChatRoleModel(String str, int i, String str2, String str3, String str4, boolean z) {
        this.roleID = str;
        this.chatPermissionID = i;
        this.roleName = str2;
        this.roleDescription = str3;
        this.ribbonImage = str4;
        this.isIncognito = z;
    }

    public ChatRoleModel getChatRoleModel() {
        if (this.ribbonImage == null) {
            this.ribbonImage = "";
        }
        ChatRoleModel chatRoleModel = new ChatRoleModel(this.roleName, this.roleDescription, this.ribbonImage, this.chatPermissionID, this.isIncognito, this.roleRank, this.roleID);
        return chatRoleModel;
    }
}
