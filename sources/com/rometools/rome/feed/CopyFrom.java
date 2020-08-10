package com.rometools.rome.feed;

public interface CopyFrom {
    void copyFrom(CopyFrom copyFrom);

    Class<? extends CopyFrom> getInterface();
}
