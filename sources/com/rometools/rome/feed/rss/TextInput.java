package com.rometools.rome.feed.rss;

import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;

public class TextInput implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private String description;
    private String link;
    private String name;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String title;

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TextInput)) {
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }
}
