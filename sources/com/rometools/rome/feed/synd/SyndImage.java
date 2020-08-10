package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.CopyFrom;

public interface SyndImage extends Cloneable, CopyFrom {
    Object clone() throws CloneNotSupportedException;

    String getDescription();

    Integer getHeight();

    String getLink();

    String getTitle();

    String getUrl();

    Integer getWidth();

    void setDescription(String str);

    void setHeight(Integer num);

    void setLink(String str);

    void setTitle(String str);

    void setUrl(String str);

    void setWidth(Integer num);
}
