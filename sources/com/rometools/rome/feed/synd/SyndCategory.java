package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.CopyFrom;

public interface SyndCategory extends Cloneable, CopyFrom {
    Object clone() throws CloneNotSupportedException;

    String getName();

    String getTaxonomyUri();

    void setName(String str);

    void setTaxonomyUri(String str);
}
