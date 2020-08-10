package com.rometools.rome.feed.impl;

import java.io.Serializable;
import java.util.Set;

public class ObjectBean implements Serializable, Cloneable {
    private static final long serialVersionUID = 1;
    private final CloneableBean cloneableBean;
    private final EqualsBean equalsBean;
    private final ToStringBean toStringBean;

    public ObjectBean(Class<?> cls, Object obj) {
        this(cls, obj, null);
    }

    public ObjectBean(Class<?> cls, Object obj, Set<String> set) {
        this.equalsBean = new EqualsBean(cls, obj);
        this.toStringBean = new ToStringBean(cls, obj);
        this.cloneableBean = new CloneableBean(obj, set);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.cloneableBean.beanClone();
    }

    public boolean equals(Object obj) {
        return this.equalsBean.beanEquals(obj);
    }

    public int hashCode() {
        return this.equalsBean.beanHashCode();
    }

    public String toString() {
        return this.toStringBean.toString();
    }
}
