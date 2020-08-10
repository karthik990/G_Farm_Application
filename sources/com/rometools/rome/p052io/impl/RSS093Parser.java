package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.rometools.rome.feed.rss.Item;
import java.util.Locale;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS093Parser */
public class RSS093Parser extends RSS092Parser {
    /* access modifiers changed from: protected */
    public String getRSSVersion() {
        return "0.93";
    }

    public RSS093Parser() {
        this("rss_0.93");
    }

    protected RSS093Parser(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public Item parseItem(Element element, Element element2, Locale locale) {
        Item parseItem = super.parseItem(element, element2, locale);
        Element child = element2.getChild("pubDate", getRSSNamespace());
        if (child != null) {
            parseItem.setPubDate(DateParser.parseDate(child.getText(), locale));
        }
        Element child2 = element2.getChild("expirationDate", getRSSNamespace());
        if (child2 != null) {
            parseItem.setExpirationDate(DateParser.parseDate(child2.getText(), locale));
        }
        Element child3 = element2.getChild(Constants.RESPONSE_DESCRIPTION, getRSSNamespace());
        if (child3 != null) {
            String attributeValue = child3.getAttributeValue("type");
            if (attributeValue != null) {
                parseItem.getDescription().setType(attributeValue);
            }
        }
        return parseItem;
    }
}
