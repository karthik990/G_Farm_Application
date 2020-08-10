package com.rometools.rome.feed.module;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;

public class DCSubjectImpl implements Cloneable, Serializable, DCSubject {
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final long serialVersionUID = 1;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String taxonomyUri;
    private String value;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("taxonomyUri", String.class);
        hashMap.put(Param.VALUE, String.class);
        COPY_FROM_HELPER = new CopyFromHelper(DCSubject.class, hashMap, Collections.emptyMap());
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DCSubjectImpl)) {
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

    public String getTaxonomyUri() {
        return this.taxonomyUri;
    }

    public void setTaxonomyUri(String str) {
        this.taxonomyUri = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public Class<DCSubject> getInterface() {
        return DCSubject.class;
    }

    public void copyFrom(CopyFrom copyFrom) {
        COPY_FROM_HELPER.copy(this, copyFrom);
    }
}
