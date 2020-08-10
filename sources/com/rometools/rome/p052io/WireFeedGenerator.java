package com.rometools.rome.p052io;

import com.rometools.rome.feed.WireFeed;
import org.jdom2.Document;

/* renamed from: com.rometools.rome.io.WireFeedGenerator */
public interface WireFeedGenerator {
    Document generate(WireFeed wireFeed) throws IllegalArgumentException, FeedException;

    String getType();
}
