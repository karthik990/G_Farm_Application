package com.mobiroller.models.chat;

import com.google.firebase.database.Exclude;
import com.mobiroller.models.UserProfileItem;
import java.io.Serializable;
import java.util.List;

public class UserModel implements Serializable, Comparable<UserModel> {
    public String birthday;
    public int chatPermissionID = 0;
    public String chatPermissionIDString;
    public int chatRoleId;
    public String chatRoleIdString;
    public String email;
    public String firebaseToken;
    public String genderString;
    @Exclude
    private List<String> groupList;
    public String imageUrl;
    public boolean isBanned;
    public boolean isOnline;
    @Exclude
    public boolean isSelected;
    @Exclude
    public String lastMessage;
    public String latitude;
    public String longitude;
    public String name;
    public String role;
    public String status;
    public boolean superUser;
    public String uid;
    public List<UserProfileItem> userProfileItems;
    public String username;
    public String version;

    public List<String> getGroupList() {
        return this.groupList;
    }

    public boolean isSuperUser() {
        return this.superUser;
    }

    public void setSuperUser(boolean z) {
        this.superUser = z;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public void setGroupList(List<String> list) {
        this.groupList = list;
    }

    public int getChatPermissionID() {
        return this.chatPermissionID;
    }

    public void setChatPermissionID(int i) {
        this.chatPermissionID = i;
    }

    public List<UserProfileItem> getUserProfileItems() {
        return this.userProfileItems;
    }

    public void setUserProfileItems(List<UserProfileItem> list) {
        this.userProfileItems = list;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String str) {
        this.role = str;
    }

    public boolean getIsOnline() {
        return this.isOnline;
    }

    public void setIsOnline(boolean z) {
        this.isOnline = z;
    }

    public UserModel() {
    }

    public UserModel(String str, String str2, String str3, String str4, String str5) {
        this.uid = str;
        this.name = str2;
        this.email = str3;
        this.firebaseToken = str4;
        this.imageUrl = str5;
    }

    public UserModel(String str, String str2, String str3) {
        this.uid = str;
        this.name = str2;
        this.imageUrl = str3;
    }

    public UserModel(String str, String str2) {
        this.uid = str;
        this.name = str2;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getFirebaseToken() {
        return this.firebaseToken;
    }

    public void setFirebaseToken(String str) {
        this.firebaseToken = str;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String str) {
        this.imageUrl = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String str) {
        this.latitude = str;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }

    public boolean getIsBanned() {
        return this.isBanned;
    }

    public void setIsBanned(boolean z) {
        this.isBanned = z;
    }

    public String getGenderString() {
        return this.genderString;
    }

    public void setGenderString(String str) {
        this.genderString = str;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String str) {
        this.birthday = str;
    }

    public int getChatRoleId() {
        return this.chatRoleId;
    }

    public void setChatRoleId(int i) {
        this.chatRoleId = i;
    }

    public String getChatRoleIdString() {
        return this.chatRoleIdString;
    }

    public void setChatRoleIdString(String str) {
        this.chatRoleIdString = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserModel{uid='");
        sb.append(this.uid);
        sb.append('\'');
        sb.append(", name='");
        sb.append(this.name);
        sb.append('\'');
        sb.append(", email='");
        sb.append(this.email);
        sb.append('\'');
        sb.append(", firebaseToken='");
        sb.append(this.firebaseToken);
        sb.append('\'');
        sb.append(", imageUrl='");
        sb.append(this.imageUrl);
        sb.append('\'');
        sb.append(", latitude='");
        sb.append(this.latitude);
        sb.append('\'');
        sb.append(", longitude='");
        sb.append(this.longitude);
        sb.append('\'');
        sb.append(", getIsBanned=");
        sb.append(this.isBanned);
        sb.append(", gender=");
        sb.append(this.genderString);
        sb.append(", birthday='");
        sb.append(this.birthday);
        sb.append('\'');
        sb.append(", isOnline=");
        sb.append(this.isOnline);
        sb.append(", lastMessage='");
        sb.append(this.lastMessage);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001b A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x001c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compareTo(com.mobiroller.models.chat.UserModel r5) {
        /*
            r4 = this;
            boolean r0 = r4.getIsOnline()
            r1 = 0
            if (r0 == 0) goto L_0x0010
            boolean r0 = r5.getIsOnline()
            if (r0 == 0) goto L_0x000e
            goto L_0x0018
        L_0x000e:
            r0 = -1
            goto L_0x0019
        L_0x0010:
            boolean r0 = r5.getIsOnline()
            if (r0 == 0) goto L_0x0018
            r0 = 1
            goto L_0x0019
        L_0x0018:
            r0 = 0
        L_0x0019:
            if (r0 == 0) goto L_0x001c
            return r0
        L_0x001c:
            java.util.Locale r0 = new java.util.Locale
            java.lang.String r2 = "tr"
            java.lang.String r3 = "TR"
            r0.<init>(r2, r3)
            java.text.Collator r0 = java.text.Collator.getInstance(r0)
            r0.setStrength(r1)
            java.lang.String r1 = r4.getName()
            java.lang.String r5 = r5.getName()
            int r5 = r0.compare(r1, r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.models.chat.UserModel.compareTo(com.mobiroller.models.chat.UserModel):int");
    }

    @Exclude
    public boolean isChatAdmin() {
        return this.chatPermissionID == 1;
    }

    @Exclude
    public boolean isChatMod() {
        return this.chatPermissionID == 2;
    }

    @Exclude
    public boolean isChatUserDefinedRole() {
        int i = this.chatPermissionID;
        return (i == 1 || i == 2 || i == -1) ? false : true;
    }
}
