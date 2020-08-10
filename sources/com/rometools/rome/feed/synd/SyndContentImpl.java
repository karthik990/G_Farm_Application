package com.rometools.rome.feed.synd;

import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;

public class SyndContentImpl implements Serializable, SyndContent {
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final long serialVersionUID = 1;
    private String mode;
    private final ObjectBean objBean = new ObjectBean(SyndContent.class, this);
    private String type;
    private String value;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("type", String.class);
        hashMap.put(Param.VALUE, String.class);
        COPY_FROM_HELPER = new CopyFromHelper(SyndContent.class, hashMap, Collections.emptyMap());
    }

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

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String str) {
        this.mode = str;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public Class<SyndContent> getInterface() {
        return SyndContent.class;
    }

    public void copyFrom(CopyFrom copyFrom) {
        COPY_FROM_HELPER.copy(this, copyFrom);
    }
}
