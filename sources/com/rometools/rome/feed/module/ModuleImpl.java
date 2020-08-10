package com.rometools.rome.feed.module;

import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;

public abstract class ModuleImpl implements Cloneable, Serializable, Module {
    private static final long serialVersionUID = 1;
    private final ObjectBean objBean;
    private final String uri;

    protected ModuleImpl(Class<?> cls, String str) {
        this.objBean = new ObjectBean(cls, this);
        this.uri = str;
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ModuleImpl)) {
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

    public String getUri() {
        return this.uri;
    }
}
