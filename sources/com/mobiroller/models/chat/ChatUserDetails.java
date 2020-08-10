package com.mobiroller.models.chat;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.PropertyName;
import com.mobiroller.constants.ChatConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatUserDetails implements Serializable, Comparable<ChatUserDetails> {
    @PropertyName("blockedByList")
    public HashMap<String, String> blockedByUserList = new HashMap<>();
    @PropertyName("blockedList")
    public HashMap<String, String> blockedUserList = new HashMap<>();
    @PropertyName("ft")
    public String firebaseToken;
    @PropertyName("meta")
    public List<ChatMetaModel> metaModelList = new ArrayList();
    @PropertyName("roles")
    public ChatRolesModel roles = new ChatRolesModel();
    @PropertyName("info")
    public ChatInfoModel userInfo = new ChatInfoModel();

    @Exclude
    public List<ChatMetaModel> getMetaModelList() {
        return this.metaModelList;
    }

    @Exclude
    public ChatRolesModel getRoles() {
        return this.roles;
    }

    @Exclude
    public ChatInfoModel getUserInfo() {
        return this.userInfo;
    }

    @Exclude
    public String getFirebaseToken() {
        return this.firebaseToken;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0043 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0044  */
    @com.google.firebase.database.Exclude
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int compareTo(com.mobiroller.models.chat.ChatUserDetails r5) {
        /*
            r4 = this;
            com.mobiroller.models.chat.ChatInfoModel r0 = r4.userInfo
            java.lang.String r0 = r0.isOnline
            r1 = 0
            java.lang.String r2 = "n"
            if (r0 == 0) goto L_0x002a
            com.mobiroller.models.chat.ChatInfoModel r0 = r4.userInfo
            java.lang.String r0 = r0.isOnline
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x002a
            com.mobiroller.models.chat.ChatInfoModel r0 = r5.getUserInfo()
            java.lang.String r0 = r0.isOnline
            if (r0 == 0) goto L_0x0028
            com.mobiroller.models.chat.ChatInfoModel r0 = r5.getUserInfo()
            java.lang.String r0 = r0.isOnline
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0028
            goto L_0x0040
        L_0x0028:
            r0 = -1
            goto L_0x0041
        L_0x002a:
            com.mobiroller.models.chat.ChatInfoModel r0 = r5.getUserInfo()
            java.lang.String r0 = r0.isOnline
            if (r0 == 0) goto L_0x0040
            com.mobiroller.models.chat.ChatInfoModel r0 = r5.getUserInfo()
            java.lang.String r0 = r0.isOnline
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x0040
            r0 = 1
            goto L_0x0041
        L_0x0040:
            r0 = 0
        L_0x0041:
            if (r0 == 0) goto L_0x0044
            return r0
        L_0x0044:
            java.util.Locale r0 = new java.util.Locale
            java.lang.String r2 = "tr"
            java.lang.String r3 = "TR"
            r0.<init>(r2, r3)
            java.text.Collator r0 = java.text.Collator.getInstance(r0)
            r0.setStrength(r1)
            com.mobiroller.models.chat.ChatInfoModel r1 = r4.getUserInfo()
            java.lang.String r1 = r1.name
            com.mobiroller.models.chat.ChatInfoModel r5 = r5.getUserInfo()
            java.lang.String r5 = r5.name
            int r5 = r0.compare(r1, r5)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.models.chat.ChatUserDetails.compareTo(com.mobiroller.models.chat.ChatUserDetails):int");
    }

    @Exclude
    public boolean isChatAdmin() {
        if (getRoles().chatPermissionIdString == null) {
            return false;
        }
        return getRoles().chatPermissionIdString.equals(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE);
    }

    @Exclude
    public boolean isChatMod() {
        if (getRoles().chatPermissionIdString == null) {
            return false;
        }
        return getRoles().chatPermissionIdString.equals(ExifInterface.GPS_MEASUREMENT_2D);
    }

    @Exclude
    public boolean isSuperUser() {
        return getRoles().superUser;
    }

    @Exclude
    public boolean isAuthorizedUser() {
        return isSuperUser() || isChatMod() || isChatAdmin();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ChatUserDetails{metaModelList=");
        sb.append(this.metaModelList);
        sb.append(", roles=");
        sb.append(this.roles.toString());
        sb.append(", userInfo=");
        sb.append(this.userInfo.toString());
        sb.append(", firebaseToken='");
        sb.append(this.firebaseToken);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Exclude
    public void parseSnapshot(DataSnapshot dataSnapshot) {
        String str = ChatConstants.ARG_USERS_META;
        if (dataSnapshot.hasChild(str)) {
            this.metaModelList = (List) dataSnapshot.child(str).getValue((GenericTypeIndicator<T>) new GenericTypeIndicator<List<ChatMetaModel>>() {
            });
        }
        String str2 = ChatConstants.ARG_USERS_FIREBASE_TOKEN;
        if (dataSnapshot.hasChild(str2)) {
            this.firebaseToken = (String) dataSnapshot.child(str2).getValue(String.class);
        }
        String str3 = ChatConstants.ARG_USER_INFO;
        if (dataSnapshot.hasChild(str3)) {
            this.userInfo = new ChatInfoModel();
            String str4 = "o";
            String str5 = "n";
            if (dataSnapshot.child(str3).hasChild(str4)) {
                try {
                    this.userInfo.isOnline = String.valueOf(dataSnapshot.child(str3).child(str4).getValue(String.class));
                } catch (Exception unused) {
                    if (((Boolean) dataSnapshot.child(str3).child(str4).getValue(Boolean.class)).booleanValue()) {
                        this.userInfo.isOnline = str5;
                    } else {
                        this.userInfo.isOnline = "f";
                    }
                }
            }
            DataSnapshot child = dataSnapshot.child(str3);
            String str6 = ChatConstants.ARG_USER_INFO_EMAIL;
            String str7 = "";
            if (child.hasChild(str6)) {
                try {
                    this.userInfo.email = String.valueOf(dataSnapshot.child(str3).child(str6).getValue(String.class));
                } catch (Exception e) {
                    e.printStackTrace();
                    this.userInfo.email = str7;
                }
            }
            String str8 = "i";
            if (dataSnapshot.child(str3).hasChild(str8)) {
                try {
                    this.userInfo.imageUrl = String.valueOf(dataSnapshot.child(str3).child(str8).getValue(String.class));
                } catch (Exception e2) {
                    e2.printStackTrace();
                    this.userInfo.imageUrl = str7;
                }
            }
            String str9 = "ib";
            if (dataSnapshot.child(str3).hasChild(str9)) {
                try {
                    this.userInfo.isBanned = ((Boolean) dataSnapshot.child(str3).child(str9).getValue(Boolean.class)).booleanValue();
                } catch (Exception e3) {
                    e3.printStackTrace();
                    this.userInfo.isBanned = false;
                }
            }
            if (dataSnapshot.child(str3).hasChild(str5)) {
                try {
                    this.userInfo.name = (String) dataSnapshot.child(str3).child(str5).getValue(String.class);
                } catch (Exception e4) {
                    e4.printStackTrace();
                    this.userInfo.name = str7;
                }
            }
            DataSnapshot child2 = dataSnapshot.child(str3);
            String str10 = ChatConstants.ARG_USER_INFO_UID;
            if (child2.hasChild(str10)) {
                try {
                    this.userInfo.uid = (String) dataSnapshot.child(str3).child(str10).getValue(String.class);
                } catch (Exception e5) {
                    e5.printStackTrace();
                    this.userInfo.uid = str7;
                }
            }
            String str11 = "un";
            if (dataSnapshot.child(str3).hasChild(str11)) {
                try {
                    this.userInfo.username = (String) dataSnapshot.child(str3).child(str11).getValue(String.class);
                } catch (Exception e6) {
                    e6.printStackTrace();
                    this.userInfo.username = str7;
                }
            }
            if (dataSnapshot.child(str3).hasChild("s")) {
                try {
                    this.userInfo.status = (String) dataSnapshot.child(str3).child("s").getValue(String.class);
                } catch (Exception e7) {
                    e7.printStackTrace();
                    this.userInfo.status = str7;
                }
            }
        }
        String str12 = "roles";
        if (dataSnapshot.hasChild(str12)) {
            this.roles = new ChatRolesModel();
            DataSnapshot child3 = dataSnapshot.child(str12);
            String str13 = ChatConstants.ARG_USERS_ROLES_CHAT_PERMISSION_ID;
            if (child3.hasChild(str13)) {
                try {
                    this.roles.chatPermissionIdString = String.valueOf(dataSnapshot.child(str12).child(str13).getValue(Long.class));
                } catch (Exception unused2) {
                    this.roles.chatPermissionIdString = String.valueOf(dataSnapshot.child(str12).child(str13).getValue(String.class));
                }
            }
            String str14 = "cris";
            if (dataSnapshot.child(str12).hasChild(str14)) {
                try {
                    this.roles.chatPermissionIdString = String.valueOf(dataSnapshot.child(str12).child(str14).getValue(Long.class));
                } catch (Exception unused3) {
                    this.roles.chatRoleIdString = String.valueOf(dataSnapshot.child(str12).child(str14).getValue(String.class));
                }
            }
            DataSnapshot child4 = dataSnapshot.child(str12);
            String str15 = ChatConstants.ARG_USERS_ROLES_SUPER_USER;
            if (child4.hasChild(str15)) {
                this.roles.superUser = ((Boolean) dataSnapshot.child(str12).child(str15).getValue(Boolean.class)).booleanValue();
            }
        }
    }
}
