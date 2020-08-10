package com.rometools.rome.feed.rss;

import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;

public class Guid implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private boolean permaLink;
    private String value;

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Guid)) {
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

    public boolean isPermaLink() {
        return this.permaLink;
    }

    public void setPermaLink(boolean z) {
        this.permaLink = z;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
