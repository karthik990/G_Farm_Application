package com.rometools.rome.feed.synd;

import com.anjlab.android.iab.p020v3.Constants;
import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

public class SyndImageImpl implements Serializable, SyndImage {
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final long serialVersionUID = 1;
    private String description;
    private Integer height;
    private String link;
    private final ObjectBean objBean = new ObjectBean(SyndImage.class, this);
    private String title;
    private String url;
    private Integer width;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("title", String.class);
        hashMap.put("url", String.class);
        hashMap.put("link", String.class);
        hashMap.put(SettingsJsonConstants.ICON_WIDTH_KEY, Integer.class);
        hashMap.put(SettingsJsonConstants.ICON_HEIGHT_KEY, Integer.class);
        hashMap.put(Constants.RESPONSE_DESCRIPTION, String.class);
        COPY_FROM_HELPER = new CopyFromHelper(SyndImage.class, hashMap, Collections.emptyMap());
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        return this.objBean.equals(obj);
    }

    public int hashCode() {
        return this.objBean.hashCode();
    }

    public String toString() {
        return this.objBean.toString();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer num) {
        this.width = num;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer num) {
        this.height = num;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public Class<SyndImage> getInterface() {
        return SyndImage.class;
    }

    public void copyFrom(CopyFrom copyFrom) {
        COPY_FROM_HELPER.copy(this, copyFrom);
    }
}
