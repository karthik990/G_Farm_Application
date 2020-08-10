package com.rometools.rome.feed.module;

import com.rometools.rome.feed.CopyFrom;

public interface DCSubject extends Cloneable, CopyFrom {
    Object clone() throws CloneNotSupportedException;

    String getTaxonomyUri();

    String getValue();

    void setTaxonomyUri(String str);

    void setValue(String str);
}
