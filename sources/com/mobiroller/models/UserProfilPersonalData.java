package com.mobiroller.models;

import java.io.Serializable;
import java.util.List;

public class UserProfilPersonalData implements Serializable {
    private List<UserProfileItem> data;

    public UserProfilPersonalData(List<UserProfileItem> list) {
        this.data = list;
    }

    public List<UserProfileItem> getData() {
        return this.data;
    }

    public void setData(List<UserProfileItem> list) {
        this.data = list;
    }
}
