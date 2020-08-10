package com.mobiroller.models.chat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;
import com.mobiroller.constants.ChatConstants;
import java.io.Serializable;

public class ChatUserModel implements Serializable, Comparable<ChatUserModel> {
    @PropertyName("cris")
    public String chatRoleIdString;
    @PropertyName("fn")
    public String fullName;
    @PropertyName("i")
    public String imageUrl;
    @PropertyName("ib")
    public boolean isBanned;
    @PropertyName("o")
    public String isOnline;
    @Exclude
    public boolean isSelected;
    @Exclude
    public String lastMessage;
    @PropertyName("n")
    public String name;
    @PropertyName("ris")
    public String roleString;
    @PropertyName("s")
    public String status;
    @Exclude
    public String uid;
    @PropertyName("un")
    public String username;

    @Exclude
    public String getName() {
        String str = this.fullName;
        if (str == null || str.isEmpty()) {
            return this.name;
        }
        return this.fullName;
    }

    public ChatUserModel() {
        String str = "";
        this.roleString = str;
        this.chatRoleIdString = str;
    }

    @Exclude
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserModel{uid='");
        sb.append(this.uid);
        sb.append('\'');
        sb.append(", name='");
        sb.append(this.name);
        sb.append('\'');
        sb.append(", imageUrl='");
        sb.append(this.imageUrl);
        sb.append('\'');
        sb.append(", chatRoleIdString='");
        sb.append(this.chatRoleIdString);
        sb.append('\'');
        sb.append(", roleString='");
        sb.append(this.roleString);
        sb.append('\'');
        sb.append(", isBanned=");
        sb.append(this.isBanned);
        sb.append(", isOnline=");
        sb.append(this.isOnline);
        sb.append(", lastMessage='");
        sb.append(this.lastMessage);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0029 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002a  */
    @com.google.firebase.database.Exclude
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compareTo(com.mobiroller.models.chat.ChatUserModel r5) {
        /*
            r4 = this;
            java.lang.String r0 = r4.isOnline
            r1 = 0
            java.lang.String r2 = "n"
            if (r0 == 0) goto L_0x001a
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x001a
            java.lang.String r0 = r5.isOnline
            if (r0 == 0) goto L_0x0018
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0018
            goto L_0x0026
        L_0x0018:
            r0 = -1
            goto L_0x0027
        L_0x001a:
            java.lang.String r0 = r5.isOnline
            if (r0 == 0) goto L_0x0026
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0026
            r0 = 1
            goto L_0x0027
        L_0x0026:
            r0 = 0
        L_0x0027:
            if (r0 == 0) goto L_0x002a
            return r0
        L_0x002a:
            java.util.Locale r0 = new java.util.Locale
            java.lang.String r2 = "tr"
            java.lang.String r3 = "TR"
            r0.<init>(r2, r3)
            java.text.Collator r0 = java.text.Collator.getInstance(r0)
            r0.setStrength(r1)
            java.lang.String r1 = r4.name
            java.lang.String r5 = r5.name
            int r5 = r0.compare(r1, r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.models.chat.ChatUserModel.compareTo(com.mobiroller.models.chat.ChatUserModel):int");
    }

    @Exclude
    public void parseSnapshot(DataSnapshot dataSnapshot) {
        String str = "cris";
        if (dataSnapshot.hasChild(str)) {
            this.chatRoleIdString = String.valueOf(dataSnapshot.child(str).getValue(Long.class));
        }
        String str2 = "un";
        if (dataSnapshot.hasChild(str2)) {
            this.username = (String) dataSnapshot.child(str2).getValue(String.class);
        }
        String str3 = "n";
        if (dataSnapshot.hasChild(str3)) {
            this.name = (String) dataSnapshot.child(str3).getValue(String.class);
        }
        String str4 = ChatConstants.ARG_USER_LIST_NAME_V1;
        if (dataSnapshot.hasChild(str4)) {
            this.fullName = (String) dataSnapshot.child(str4).getValue(String.class);
        }
        String str5 = "o";
        if (dataSnapshot.hasChild(str5)) {
            this.isOnline = (String) dataSnapshot.child(str5).getValue(String.class);
        }
        String str6 = "i";
        if (dataSnapshot.hasChild(str6)) {
            this.imageUrl = (String) dataSnapshot.child(str6).getValue(String.class);
        }
    }
}
