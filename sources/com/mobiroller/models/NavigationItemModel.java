package com.mobiroller.models;

import java.io.Serializable;
import java.util.ArrayList;

public class NavigationItemModel implements Serializable {
    private String accountScreenID;
    private ImageModel iconImage;
    private boolean isLoginActive;
    private ArrayList<String> roles;
    public String screenSubtype;
    private String screenType;
    private String title;
    private String updateDate;

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(String str) {
        this.updateDate = str;
    }

    public String getAccountScreenID() {
        return this.accountScreenID;
    }

    public void setAccountScreenID(String str) {
        this.accountScreenID = str;
    }

    public String getScreenType() {
        return this.screenType;
    }

    public void setScreenType(String str) {
        this.screenType = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public ImageModel getIconImage() {
        return this.iconImage;
    }

    public void setIconImage(ImageModel imageModel) {
        this.iconImage = imageModel;
    }

    public boolean isLoginActive() {
        return this.isLoginActive;
    }

    public void setLoginActive(boolean z) {
        this.isLoginActive = z;
    }

    public ArrayList<String> getRoles() {
        return this.roles;
    }

    public void setRoles(ArrayList<String> arrayList) {
        this.roles = arrayList;
    }
}
