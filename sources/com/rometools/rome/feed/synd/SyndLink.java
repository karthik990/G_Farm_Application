package com.rometools.rome.feed.synd;

public interface SyndLink {
    Object clone() throws CloneNotSupportedException;

    boolean equals(Object obj);

    String getHref();

    String getHreflang();

    long getLength();

    String getRel();

    String getTitle();

    String getType();

    int hashCode();

    void setHref(String str);

    void setHreflang(String str);

    void setLength(long j);

    void setRel(String str);

    void setTitle(String str);

    void setType(String str);

    String toString();
}
