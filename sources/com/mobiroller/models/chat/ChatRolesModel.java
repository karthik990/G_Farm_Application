package com.mobiroller.models.chat;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import java.io.Serializable;

public class ChatRolesModel implements Serializable {
    @PropertyName("cpis")
    public String chatPermissionIdString;
    @PropertyName("cri")
    public int chatRoleId;
    @PropertyName("cris")
    public String chatRoleIdString;
    @PropertyName("ri")
    public int roleId = 0;
    @PropertyName("ris")
    public String roleIdString;
    @PropertyName("su")
    public boolean superUser = false;

    public ChatRolesModel() {
    }

    public ChatRolesModel(String str, int i, String str2, int i2, String str3, boolean z) {
        this.chatPermissionIdString = str;
        this.chatRoleId = i;
        this.chatRoleIdString = str2;
        this.roleId = i2;
        this.roleIdString = str3;
        this.superUser = z;
    }

    @Exclude
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ChatRolesModel{chatPermissionIdString='");
        sb.append(this.chatPermissionIdString);
        sb.append('\'');
        sb.append(", chatRoleId=");
        sb.append(this.chatRoleId);
        sb.append(", chatRoleIdString='");
        sb.append(this.chatRoleIdString);
        sb.append('\'');
        sb.append(", roleId=");
        sb.append(this.roleId);
        sb.append(", roleIdString='");
        sb.append(this.roleIdString);
        sb.append('\'');
        sb.append(", superUser=");
        sb.append(this.superUser);
        sb.append('}');
        return sb.toString();
    }
}
