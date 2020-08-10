package com.mobiroller.models;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginModel implements Serializable {
    private ArrayList<AccountModel> data;
    private String message;
    private boolean status;

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean z) {
        this.status = z;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public ArrayList<AccountModel> getData() {
        return this.data;
    }

    public void setData(ArrayList<AccountModel> arrayList) {
        this.data = arrayList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LoginModel{status=");
        sb.append(this.status);
        sb.append(", message='");
        sb.append(this.message);
        sb.append('\'');
        sb.append(", data=");
        sb.append(this.data);
        sb.append('}');
        return sb.toString();
    }
}
