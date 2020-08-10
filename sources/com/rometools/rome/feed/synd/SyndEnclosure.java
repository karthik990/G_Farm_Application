package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.CopyFrom;

public interface SyndEnclosure extends Cloneable, CopyFrom {
    long getLength();

    String getType();

    String getUrl();

    void setLength(long j);

    void setType(String str);

    void setUrl(String str);
}
