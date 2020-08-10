package com.mobiroller.models;

import java.io.Serializable;

public class IntroModel implements Serializable {
    private String activeLanguages;
    private String introMessage;
    private String introMessageId;
    private String introMessageScreenID;
    private String introMessageScreenType;
    public String introMessageSubScreenType;
    private String updateDate;

    public String getIntroMessage() {
        return this.introMessage;
    }

    public void setIntroMessage(String str) {
        this.introMessage = str;
    }

    public String getIntroMessageId() {
        return this.introMessageId;
    }

    public void setIntroMessageId(String str) {
        this.introMessageId = str;
    }

    public String getIntroMessageScreenType() {
        return this.introMessageScreenType;
    }

    public void setIntroMessageScreenType(String str) {
        this.introMessageScreenType = str;
    }

    public String getIntroMessageScreenID() {
        return this.introMessageScreenID;
    }

    public void setIntroMessageScreenID(String str) {
        this.introMessageScreenID = str;
    }

    public String getUpdateDate() {
        String str = this.updateDate;
        return str == null ? "" : str;
    }

    public void setUpdateDate(String str) {
        this.updateDate = str;
    }

    public String getActiveLanguages() {
        return this.activeLanguages;
    }

    public void setActiveLanguages(String str) {
        this.activeLanguages = str;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IntroModel)) {
            return false;
        }
        return getUpdateDate().equalsIgnoreCase(((IntroModel) obj).getUpdateDate());
    }
}
