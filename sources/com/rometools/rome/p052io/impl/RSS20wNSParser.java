package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.WireFeed;
import java.util.Locale;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.RSS20wNSParser */
public class RSS20wNSParser extends RSS20Parser {
    private static String RSS20_URI = "http://backend.userland.com/rss2";

    public RSS20wNSParser() {
        this("rss_2.0wNS");
    }

    protected RSS20wNSParser(String str) {
        super(str);
    }

    public boolean isMyType(Document document) {
        Namespace namespace = document.getRootElement().getNamespace();
        return namespace != null && namespace.equals(getRSSNamespace()) && super.isMyType(document);
    }

    /* access modifiers changed from: protected */
    public Namespace getRSSNamespace() {
        return Namespace.getNamespace(RSS20_URI);
    }

    /* access modifiers changed from: protected */
    public WireFeed parseChannel(Element element, Locale locale) {
        WireFeed parseChannel = super.parseChannel(element, locale);
        parseChannel.setFeedType("rss_2.0");
        return parseChannel;
    }
}
