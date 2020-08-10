package com.mobiroller.models.response;

import java.io.Serializable;

public class UserProfileElement implements Serializable, Comparable<UserProfileElement> {

    /* renamed from: id */
    public String f2188id;
    public boolean isActive;
    public boolean mandotory;
    public int orderIndex;
    public String selections;
    public String subType;
    public String title;
    public String type;
    public String value;

    public UserProfileElement(String str, String str2, String str3, String str4) {
        this.title = str;
        this.type = str2;
        this.subType = str3;
        this.value = str4;
    }

    public int compareTo(UserProfileElement userProfileElement) {
        return this.orderIndex - userProfileElement.orderIndex;
    }
}
