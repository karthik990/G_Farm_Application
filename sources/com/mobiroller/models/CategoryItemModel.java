package com.mobiroller.models;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryItemModel implements Serializable {
    private String button;
    private String currency;
    private String itemCreateDate;
    private String itemDescription;
    private String itemLink;
    private String itemPrice;
    private String itemSubTitle;
    private String itemTitle;
    private ArrayList<String> tableImages;

    public String getItemTitle() {
        return this.itemTitle;
    }

    public void setItemTitle(String str) {
        this.itemTitle = str;
    }

    public String getItemSubTitle() {
        return this.itemSubTitle;
    }

    public void setItemSubTitle(String str) {
        this.itemSubTitle = str;
    }

    public String getItemDescription() {
        return this.itemDescription;
    }

    public void setItemDescription(String str) {
        this.itemDescription = str;
    }

    public String getItemLink() {
        return this.itemLink;
    }

    public void setItemLink(String str) {
        this.itemLink = str;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String str) {
        this.currency = str;
    }

    public String getItemCreateDate() {
        return this.itemCreateDate;
    }

    public void setItemCreateDate(String str) {
        this.itemCreateDate = str;
    }

    public String getItemPrice() {
        return this.itemPrice;
    }

    public void setItemPrice(String str) {
        this.itemPrice = str;
    }

    public ArrayList<String> getTableImages() {
        return this.tableImages;
    }

    public void setTableImages(ArrayList<String> arrayList) {
        this.tableImages = arrayList;
    }

    public String getButton() {
        return this.button;
    }

    public void setButton(String str) {
        this.button = str;
    }
}
