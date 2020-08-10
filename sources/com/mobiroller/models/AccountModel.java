package com.mobiroller.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class AccountModel implements Serializable {
    private String accountName;
    private ImageModel logo;
    @SerializedName("package")
    private String packageName;
    private String title;

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String str) {
        this.accountName = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public ImageModel getLogo() {
        return this.logo;
    }

    public void setLogo(ImageModel imageModel) {
        this.logo = imageModel;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccountModel{accountName='");
        sb.append(this.accountName);
        sb.append('\'');
        sb.append(", title='");
        sb.append(this.title);
        sb.append('\'');
        sb.append(", logo=");
        sb.append(this.logo);
        sb.append('}');
        return sb.toString();
    }
}
