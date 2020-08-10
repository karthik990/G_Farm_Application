package com.mobiroller.models.response;

import java.io.Serializable;
import java.util.List;

public class UserLoginResponse implements Serializable {
    public boolean changePassword;
    public String communityPermissionTypeId;
    public String communityRoleId;
    public String email;

    /* renamed from: id */
    public String f1505id;
    public boolean isBanned;
    public List<UserProfileElement> profileValues;
    public String roleId;
    public String sessionToken;
}
