package com.rometools.rome.feed.atom;

import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.utils.Alternatives;
import java.io.Serializable;

public class Category implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private String label;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String scheme;
    private String schemeResolved;
    private String term;

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Category)) {
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

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String str) {
        this.label = str;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String str) {
        this.scheme = str;
    }

    public void setSchemeResolved(String str) {
        this.schemeResolved = str;
    }

    public String getSchemeResolved() {
        return (String) Alternatives.firstNotNull(this.schemeResolved, this.scheme);
    }

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String str) {
        this.term = str;
    }
}
