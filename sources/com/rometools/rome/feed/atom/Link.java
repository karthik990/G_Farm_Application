package com.rometools.rome.feed.atom;

import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.utils.Alternatives;
import java.io.Serializable;

public class Link implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private String href;
    private String hrefResolved;
    private String hreflang;
    private long length;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String rel = "alternate";
    private String title;
    private String type;

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

    public String getRel() {
        return this.rel;
    }

    public void setRel(String str) {
        this.rel = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String str) {
        this.href = str;
    }

    public void setHrefResolved(String str) {
        this.hrefResolved = str;
    }

    public String getHrefResolved() {
        return (String) Alternatives.firstNotNull(this.hrefResolved, this.href);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getHreflang() {
        return this.hreflang;
    }

    public void setHreflang(String str) {
        this.hreflang = str;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long j) {
        this.length = j;
    }
}
