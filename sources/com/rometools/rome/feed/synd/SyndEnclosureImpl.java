package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;

public class SyndEnclosureImpl implements Serializable, SyndEnclosure {
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final long serialVersionUID = 1;
    private long length;
    private final ObjectBean objBean = new ObjectBean(SyndEnclosure.class, this);
    private String type;
    private String url;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put("url", String.class);
        hashMap.put("type", String.class);
        hashMap.put("length", Long.TYPE);
        COPY_FROM_HELPER = new CopyFromHelper(SyndEnclosure.class, hashMap, Collections.emptyMap());
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

    public Class<SyndEnclosure> getInterface() {
        return SyndEnclosure.class;
    }

    public void copyFrom(CopyFrom copyFrom) {
        COPY_FROM_HELPER.copy(this, copyFrom);
    }
}
