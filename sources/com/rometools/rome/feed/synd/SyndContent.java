package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.CopyFrom;

public interface SyndContent extends Cloneable, CopyFrom {
    Object clone() throws CloneNotSupportedException;

    String getMode();

    String getType();

    String getValue();

    void setMode(String str);

    void setType(String str);

    void setValue(String str);
}
