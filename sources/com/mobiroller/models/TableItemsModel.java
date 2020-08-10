package com.mobiroller.models;

import java.io.Serializable;
import java.util.ArrayList;

public class TableItemsModel implements Serializable {
    private String accountScreenID;
    private String align;
    private String answer;
    private String duration;
    private String fileURL;

    /* renamed from: id */
    private String f2170id;
    private ImageModel imageName;
    private boolean isLoginActive;
    private String items;
    private String lineCount;
    private String linkURL;
    private String mandatory;
    private String orderIndex;
    private String phoneNumber;
    private String question;
    private String ratingLevel;
    private ArrayList<String> roles;
    public String screenSubType;
    private String screenType;
    private String subtitle;
    private String thumb;
    private String title;
    private String type;
    private String updateDate;
    private String value;
    private String youtubeURL;

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(String str) {
        this.updateDate = str;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String str) {
        this.question = str;
    }

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String str) {
        this.answer = str;
    }

    public String getOrderIndex() {
        return this.orderIndex;
    }

    public void setOrderIndex(String str) {
        this.orderIndex = str;
    }

    public String getItems() {
        return this.items;
    }

    public void setItems(String str) {
        this.items = str;
    }

    public String getYoutubeURL() {
        return this.youtubeURL;
    }

    public void setYoutubeURL(String str) {
        this.youtubeURL = str;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String str) {
        this.subtitle = str;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String str) {
        this.thumb = str;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setDuration(String str) {
        this.duration = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String str) {
        this.phoneNumber = str;
    }

    public String getId() {
        return this.f2170id;
    }

    public void setId(String str) {
        this.f2170id = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getValue() {
        String str = this.value;
        return str == null ? "" : str;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String str) {
        this.align = str;
    }

    public String getRatingLevel() {
        return this.ratingLevel;
    }

    public void setRatingLevel(String str) {
        this.ratingLevel = str;
    }

    public String getLineCount() {
        return this.lineCount;
    }

    public void setLineCount(String str) {
        this.lineCount = str;
    }

    public String getMandatory() {
        return this.mandatory;
    }

    public void setMandatory(String str) {
        this.mandatory = str;
    }

    public String getFileURL() {
        String str = this.linkURL;
        if (str != null) {
            return str;
        }
        return this.fileURL;
    }

    public void setFileURL(String str) {
        this.fileURL = str;
    }

    public String getLinkURL() {
        return this.linkURL;
    }

    public void setLinkURL(String str) {
        this.linkURL = str;
    }

    public ImageModel getImageName() {
        return this.imageName;
    }

    public void setImageName(ImageModel imageModel) {
        this.imageName = imageModel;
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
