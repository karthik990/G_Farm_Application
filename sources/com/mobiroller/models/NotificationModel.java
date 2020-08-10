package com.mobiroller.models;

import com.mobiroller.models.FCMNotificationModel.Action;
import java.io.Serializable;
import java.util.Date;

public class NotificationModel implements Serializable {
    private long date = new Date().getTime();
    public String displayType;
    public String inAppUrl;
    private String message;
    public String notificationType;
    public String packageName;
    private boolean read = false;
    private String screenId;
    private String screenType;
    public String subScreenType;
    public String title;
    private String uniqueId;
    public String url;
    public String webURL;

    public NotificationModel(String str, String str2, String str3, String str4, String str5) {
        this.message = str;
        this.screenId = str3;
        this.screenType = str4;
        this.subScreenType = str5;
        this.notificationType = str2;
    }

    public NotificationModel() {
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public void setUniqueId(String str) {
        this.uniqueId = str;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public boolean isRead() {
        return this.read;
    }

    public void setRead() {
        this.read = true;
    }

    public long getDate() {
        return this.date;
    }

    public void setDate(long j) {
        this.date = j;
    }

    public String getScreenId() {
        return this.screenId;
    }

    public void setScreenId(String str) {
        this.screenId = str;
    }

    public String getScreenType() {
        return this.screenType;
    }

    public void setScreenType(String str) {
        this.screenType = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NotificationModel{uniqueId='");
        sb.append(this.uniqueId);
        sb.append('\'');
        sb.append(", message='");
        sb.append(this.message);
        sb.append('\'');
        sb.append(", read=");
        sb.append(this.read);
        sb.append(", date=");
        sb.append(this.date);
        sb.append(", screenId='");
        sb.append(this.screenId);
        sb.append('\'');
        sb.append(", screenType='");
        sb.append(this.screenType);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Action getAction() {
        Action action = new Action();
        String str = this.displayType;
        if (str != null) {
            action.setDisplayType(str);
        }
        String str2 = this.inAppUrl;
        if (str2 != null) {
            action.setInAppUrl(str2);
        }
        String str3 = this.packageName;
        if (str3 != null) {
            action.setPackageName(str3);
        }
        String str4 = this.screenType;
        if (str4 != null) {
            action.setScreenType(str4);
        }
        String str5 = this.subScreenType;
        if (str5 != null) {
            action.setScreenSubType(str5);
        }
        String str6 = this.notificationType;
        if (str6 != null) {
            action.setType(str6);
        }
        String str7 = this.url;
        if (str7 != null) {
            action.setUrl(str7);
        }
        String str8 = this.webURL;
        if (str8 != null) {
            action.setWebUrl(str8);
        }
        String str9 = this.screenId;
        if (str9 != null) {
            action.setId(str9);
        }
        return action;
    }
}
