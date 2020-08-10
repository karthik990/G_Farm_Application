package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Item;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.jdom2.Content;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS093Generator */
public class RSS093Generator extends RSS092Generator {
    public RSS093Generator() {
        this("rss_0.93", "0.93");
    }

    protected RSS093Generator(String str, String str2) {
        super(str, str2);
    }

    /* access modifiers changed from: protected */
    public void populateItem(Item item, Element element, int i) {
        super.populateItem(item, element, i);
        Date pubDate = item.getPubDate();
        if (pubDate != null) {
            element.addContent((Content) generateSimpleElement("pubDate", DateParser.formatRFC822(pubDate, Locale.US)));
        }
        Date expirationDate = item.getExpirationDate();
        if (expirationDate != null) {
            element.addContent((Content) generateSimpleElement("expirationDate", DateParser.formatRFC822(expirationDate, Locale.US)));
        }
    }

    /* access modifiers changed from: protected */
    public int getNumberOfEnclosures(List<Enclosure> list) {
        return list.size();
    }
}
