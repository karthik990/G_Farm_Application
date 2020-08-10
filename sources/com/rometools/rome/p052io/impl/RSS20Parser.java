package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.rss.Description;
import org.apache.http.cookie.ClientCookie;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS20Parser */
public class RSS20Parser extends RSS094Parser {
    /* access modifiers changed from: protected */
    public String getRSSVersion() {
        return "2.0";
    }

    /* access modifiers changed from: protected */
    public boolean isHourFormat24(Element element) {
        return false;
    }

    public RSS20Parser() {
        this("rss_2.0");
    }

    protected RSS20Parser(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public Description parseItemDescription(Element element, Element element2) {
        return super.parseItemDescription(element, element2);
    }

    public boolean isMyType(Document document) {
        return rootElementMatches(document) && (versionMatches(document) || versionAbsent(document));
    }

    private boolean rootElementMatches(Document document) {
        return document.getRootElement().getName().equals("rss");
    }

    private boolean versionMatches(Document document) {
        Attribute attribute = document.getRootElement().getAttribute(ClientCookie.VERSION_ATTR);
        return attribute != null && attribute.getValue().trim().startsWith(getRSSVersion());
    }

    private boolean versionAbsent(Document document) {
        return document.getRootElement().getAttribute(ClientCookie.VERSION_ATTR) == null;
    }
}
