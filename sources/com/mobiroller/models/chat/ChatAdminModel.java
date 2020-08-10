package com.mobiroller.models.chat;

import android.content.Intent;
import java.io.Serializable;

public class ChatAdminModel implements Serializable {
    private String description;
    private String extra;
    private int icon;
    private String iconUrl;
    private Intent intent;
    private String title;

    public ChatAdminModel(String str, String str2, String str3, Intent intent2, String str4) {
        this.title = str;
        this.description = str2;
        this.intent = intent2;
        this.iconUrl = str3;
        this.extra = str4;
    }

    public ChatAdminModel(String str, String str2, int i, Intent intent2) {
        this.title = str;
        this.description = str2;
        this.icon = i;
        this.intent = intent2;
    }

    public ChatAdminModel(String str, String str2, String str3, Intent intent2) {
        this.title = str;
        this.description = str2;
        this.iconUrl = str3;
        this.intent = intent2;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String str) {
        this.iconUrl = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public int getIcon() {
        return this.icon;
    }

    public void setIcon(int i) {
        this.icon = i;
    }

    public Intent getIntent() {
        return this.intent;
    }

    public void setIntent(Intent intent2) {
        this.intent = intent2;
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String str) {
        this.extra = str;
    }
}
