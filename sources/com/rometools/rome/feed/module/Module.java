package com.rometools.rome.feed.module;

import com.rometools.rome.feed.CopyFrom;
import java.io.Serializable;

public interface Module extends Cloneable, CopyFrom, Serializable {
    Object clone() throws CloneNotSupportedException;

    String getUri();
}
