package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.WireFeed;

public interface Converter {
    void copyInto(WireFeed wireFeed, SyndFeed syndFeed);

    WireFeed createRealFeed(SyndFeed syndFeed);

    String getType();
}
