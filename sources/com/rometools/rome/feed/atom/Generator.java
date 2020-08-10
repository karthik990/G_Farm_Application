package com.rometools.rome.feed.atom;

import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;

public class Generator implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String url;
    private String value;
    private String version;

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Generator)) {
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
