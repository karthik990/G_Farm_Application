package com.rometools.rome.feed.rss;

import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;

public class Enclosure implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private long length;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String type;
    private String url;

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Enclosure)) {
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

    public long getLength() {
        return this.length;
    }

    public void setLength(long j) {
        this.length = j;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }
}
