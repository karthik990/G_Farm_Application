package com.rometools.rome.feed.rss;

import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;

public class Content implements Cloneable, Serializable {
    public static final String HTML = "html";
    public static final String TEXT = "text";
    private static final long serialVersionUID = 1;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String type;
    private String value;

    public void setType(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getValue() {
        return this.value;
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Content)) {
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
}
