package com.rometools.rome.p052io;

import com.rometools.rome.feed.WireFeed;
import java.util.Locale;
import org.jdom2.Document;

/* renamed from: com.rometools.rome.io.WireFeedParser */
public interface WireFeedParser {
    String getType();

    boolean isMyType(Document document);

    WireFeed parse(Document document, boolean z, Locale locale) throws IllegalArgumentException, FeedException;
}
