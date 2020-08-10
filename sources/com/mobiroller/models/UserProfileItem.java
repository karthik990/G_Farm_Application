package com.mobiroller.models;

import com.google.firebase.database.Exclude;
import com.mobiroller.models.chat.ChatMetaModel;
import java.io.Serializable;

public class UserProfileItem implements Serializable, Comparable<UserProfileItem> {
    private String aveUserProfileID;
    private String aveUserProfileItemID;
    private String enteredValue;
    private boolean isActive;
    @Exclude
    private String items;
    private boolean mandotory;
    private int orderIndex;
    private String specialName;
    private String title;
    private String type;
    private int valueSetIndex;

    public UserProfileItem() {
    }

    public UserProfileItem(String str, String str2, String str3, String str4) {
        this.title = str;
        this.type = str2;
        this.specialName = str3;
        this.enteredValue = str4;
    }

    public String getEnteredValue() {
        return this.enteredValue;
    }

    public void setEnteredValue(String str) {
        this.enteredValue = str;
    }

    public int getValueSetIndex() {
        return this.valueSetIndex;
    }

    public void setValueSetIndex(int i) {
        this.valueSetIndex = i;
    }

    @Exclude
    public String getItems() {
        return this.items;
    }

    @Exclude
    public void setItems(String str) {
        this.items = str;
    }

    public String getAveUserProfileItemID() {
        return this.aveUserProfileItemID;
    }

    public void setAveUserProfileItemID(String str) {
        this.aveUserProfileItemID = str;
    }

    public String getAveUserProfileID() {
        return this.aveUserProfileID;
    }

    public void setAveUserProfileID(String str) {
        this.aveUserProfileID = str;
    }

    public int getOrderIndex() {
        return this.orderIndex;
    }

    public void setOrderIndex(int i) {
        this.orderIndex = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getSpecialName() {
        return this.specialName;
    }

    public void setSpecialName(String str) {
        this.specialName = str;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean z) {
        this.isActive = z;
    }

    public boolean isMandotory() {
        return this.mandotory;
    }

    public void setMandotory(boolean z) {
        this.mandotory = z;
    }

    public int compareTo(UserProfileItem userProfileItem) {
        return this.orderIndex - userProfileItem.orderIndex;
    }

    public ChatMetaModel getChatValues() {
        return new ChatMetaModel(this.specialName, this.enteredValue, this.title);
    }
}
