package com.mobiroller.models;

import java.util.ArrayList;

public class PagedGridModel {
    private int color;
    private int[] idList;
    private Boolean[] loginActiveList;
    private ArrayList<NavigationItemModel> navItems;
    private int numOfColumns;
    private int numOfRows;
    private int stParam;
    private String[] typeList;

    public PagedGridModel() {
    }

    public PagedGridModel(ArrayList<NavigationItemModel> arrayList, int i, int i2, int[] iArr, String[] strArr, Boolean[] boolArr, int i3, int i4) {
        this.navItems = arrayList;
        this.numOfRows = i;
        this.numOfColumns = i2;
        this.idList = iArr;
        this.typeList = strArr;
        this.loginActiveList = boolArr;
        this.stParam = i3;
        this.color = i4;
    }

    public PagedGridModel(ArrayList<NavigationItemModel> arrayList, int i, int i2, int i3, int i4) {
        this.navItems = arrayList;
        this.numOfRows = i;
        this.numOfColumns = i2;
        this.stParam = i3;
        this.color = i4;
    }

    public int getElementPerPage() {
        return this.numOfColumns * this.numOfRows;
    }

    public ArrayList<NavigationItemModel> getNavItems() {
        ArrayList<NavigationItemModel> arrayList = this.navItems;
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList<NavigationItemModel> arrayList2 = new ArrayList<>();
        this.navItems = arrayList2;
        return arrayList2;
    }

    public void setNavItems(ArrayList<NavigationItemModel> arrayList) {
        this.navItems = arrayList;
    }

    public int getNumOfRows() {
        return this.numOfRows;
    }

    public void setNumOfRows(int i) {
        this.numOfRows = i;
    }

    public int getNumOfColumns() {
        return this.numOfColumns;
    }

    public void setNumOfColumns(int i) {
        this.numOfColumns = i;
    }

    public int[] getIdList() {
        return this.idList;
    }

    public void setIdList(int[] iArr) {
        this.idList = iArr;
    }

    public String[] getTypeList() {
        return this.typeList;
    }

    public void setTypeList(String[] strArr) {
        this.typeList = strArr;
    }

    public Boolean[] getLoginActiveList() {
        return this.loginActiveList;
    }

    public void setLoginActiveList(Boolean[] boolArr) {
        this.loginActiveList = boolArr;
    }

    public int getStParam() {
        return this.stParam;
    }

    public void setStParam(int i) {
        this.stParam = i;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }
}
