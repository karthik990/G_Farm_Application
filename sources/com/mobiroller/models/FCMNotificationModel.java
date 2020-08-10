package com.mobiroller.models;

import android.content.Context;
import android.os.Build.VERSION;
import com.mobiroller.mobi942763453128.R;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FCMNotificationModel implements Serializable {
    private String accountScreenID;
    private Action action = new Action();
    private String alert;
    private String bigPictureUrl;
    private String body;
    private List<Button> buttons = new ArrayList();
    private String category;
    private String color;

    /* renamed from: id */
    private String f2167id;
    private String largeIcon;
    private String priority;
    public boolean saveNotification = true;
    private String screenType;
    private String smallIcon;
    private String title;

    public static class Action implements Serializable {
        private String displayType;

        /* renamed from: id */
        private String f2168id;
        private String inAppUrl;
        private String packageName;
        private String screenSubType;
        private String screenType;
        private String type;
        private String url;
        private String webUrl;

        public Action() {
        }

        public Action(String str, String str2, String str3) {
            this.f2168id = str;
            this.type = str2;
            this.screenType = str3;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String str) {
            this.type = str;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String str) {
            this.url = str;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String str) {
            this.packageName = str;
        }

        public String getId() {
            return this.f2168id;
        }

        public void setId(String str) {
            this.f2168id = str;
        }

        public String getDisplayType() {
            return this.displayType;
        }

        public void setDisplayType(String str) {
            this.displayType = str;
        }

        public String getWebUrl() {
            return this.webUrl;
        }

        public void setWebUrl(String str) {
            this.webUrl = str;
        }

        public String getInAppUrl() {
            return this.inAppUrl;
        }

        public void setInAppUrl(String str) {
            this.inAppUrl = str;
        }

        public String getScreenSubType() {
            return this.screenSubType;
        }

        public void setScreenSubType(String str) {
            this.screenSubType = str;
        }

        public String getScreenType() {
            return this.screenType;
        }

        public void setScreenType(String str) {
            this.screenType = str;
        }
    }

    public class Button implements Serializable {
        private Action action;
        private String text;

        public Button() {
        }

        public String getText() {
            return this.text;
        }

        public void setText(String str) {
            this.text = str;
        }

        public Action getAction() {
            return this.action;
        }

        public void setAction(Action action2) {
            this.action = action2;
        }
    }

    public FCMNotificationModel() {
        String str = "";
        this.f2167id = str;
        this.title = str;
        this.body = str;
        this.alert = str;
        this.accountScreenID = str;
        this.screenType = str;
        this.category = str;
        this.bigPictureUrl = str;
        this.smallIcon = str;
        this.priority = str;
        this.largeIcon = str;
        this.color = str;
    }

    public String getPriority() {
        String str = "";
        if (this.priority.equals(str)) {
            return str;
        }
        try {
            int parseInt = Integer.parseInt(this.priority);
            if (parseInt > 5 || parseInt < 0) {
                return str;
            }
            if (VERSION.SDK_INT >= 26) {
                return this.priority;
            }
            return String.valueOf(Integer.parseInt(this.priority) - 3);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public void setPriority(String str) {
        this.priority = str;
    }

    public String getId() {
        if (this.f2167id.equals("")) {
            this.f2167id = String.valueOf(getRandomInt());
        }
        return this.f2167id;
    }

    private int getRandomInt() {
        return new Random().nextInt(1234557) + 10;
    }

    public void setId(String str) {
        this.f2167id = str;
    }

    public String getTitle(Context context) {
        if (this.title.equals("")) {
            this.title = context.getString(R.string.app_name);
        }
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getBody() {
        String str = "";
        if (this.body.equals(str) && !this.alert.equals(str)) {
            this.body = this.alert;
        }
        return this.body;
    }

    public void setBody(String str) {
        this.body = str;
    }

    public String getAlert() {
        return this.alert;
    }

    public void setAlert(String str) {
        this.alert = str;
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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String str) {
        this.category = str;
    }

    public String getBigPictureUrl() {
        return this.bigPictureUrl;
    }

    public void setBigPictureUrl(String str) {
        this.bigPictureUrl = str;
    }

    public String getSmallIcon() {
        return this.smallIcon;
    }

    public void setSmallIcon(String str) {
        this.smallIcon = str;
    }

    public String getLargeIcon() {
        return this.largeIcon;
    }

    public void setLargeIcon(String str) {
        this.largeIcon = str;
    }

    public List<Button> getButtons() {
        return this.buttons;
    }

    public void setButtons(List<Button> list) {
        this.buttons = list;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action2) {
        this.action = action2;
    }

    public String getTitle() {
        return this.title;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String str) {
        this.color = str;
    }
}
