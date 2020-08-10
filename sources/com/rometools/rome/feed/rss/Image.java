package com.rometools.rome.feed.rss;

import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;

public class Image implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private String description;
    private Integer height;
    private String link;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String title;
    private String url;
    private Integer width;

    public Image() {
        Integer valueOf = Integer.valueOf(-1);
        this.width = valueOf;
        this.height = valueOf;
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Image)) {
            return false;
        }
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

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }
}
