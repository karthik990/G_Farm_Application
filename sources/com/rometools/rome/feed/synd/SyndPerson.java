package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.module.Extendable;

public interface SyndPerson extends Cloneable, Extendable {
    Object clone() throws CloneNotSupportedException;

    String getEmail();

    String getName();

    String getUri();

    void setEmail(String str);

    void setName(String str);

    void setUri(String str);
}
