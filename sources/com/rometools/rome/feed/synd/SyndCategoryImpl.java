package com.rometools.rome.feed.synd;

import com.braintreepayments.api.models.PostalAddressParser;
import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.DCSubject;
import com.rometools.rome.feed.module.DCSubjectImpl;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;

public class SyndCategoryImpl implements Serializable, SyndCategory {
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final long serialVersionUID = 1;
    private final ObjectBean objBean;
    private final DCSubject subject;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, String.class);
        hashMap.put("taxonomyUri", String.class);
        COPY_FROM_HELPER = new CopyFromHelper(SyndCategory.class, hashMap, Collections.emptyMap());
    }

    SyndCategoryImpl(DCSubject dCSubject) {
        this.objBean = new ObjectBean(SyndCategory.class, this);
        this.subject = dCSubject;
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SyndCategoryImpl)) {
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

    /* access modifiers changed from: 0000 */
    public DCSubject getSubject() {
        return this.subject;
    }

    public SyndCategoryImpl() {
        this(new DCSubjectImpl());
    }

    public String getName() {
        return this.subject.getValue();
    }

    public void setName(String str) {
        this.subject.setValue(str);
    }

    public String getTaxonomyUri() {
        return this.subject.getTaxonomyUri();
    }

    public void setTaxonomyUri(String str) {
        this.subject.setTaxonomyUri(str);
    }

    public Class<? extends CopyFrom> getInterface() {
        return SyndCategory.class;
    }

    public void copyFrom(CopyFrom copyFrom) {
        COPY_FROM_HELPER.copy(this, copyFrom);
    }
}
