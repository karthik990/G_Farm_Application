package com.mobiroller.models;

import java.util.ArrayList;

public class RssSliderHeaderModel {
    private ArrayList<Object> dataList;

    public RssSliderHeaderModel(ArrayList<Object> arrayList) {
        this.dataList = arrayList;
    }

    public ArrayList<Object> getDataList() {
        return this.dataList;
    }

    public void setDataList(ArrayList<Object> arrayList) {
        this.dataList = arrayList;
    }
}
