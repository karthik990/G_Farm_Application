package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndEntry;
import java.util.Date;

public class ConverterForRSS093 extends ConverterForRSS092 {
    public ConverterForRSS093() {
        this("rss_0.93");
    }

    protected ConverterForRSS093(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public SyndEntry createSyndEntry(Item item, boolean z) {
        SyndEntry createSyndEntry = super.createSyndEntry(item, z);
        Date pubDate = item.getPubDate();
        Date publishedDate = createSyndEntry.getPublishedDate();
        if (pubDate != null && publishedDate == null) {
            createSyndEntry.setPublishedDate(pubDate);
        }
        return createSyndEntry;
    }

    /* access modifiers changed from: protected */
    public Item createRSSItem(SyndEntry syndEntry) {
        Item createRSSItem = super.createRSSItem(syndEntry);
        createRSSItem.setPubDate(syndEntry.getPublishedDate());
        return createRSSItem;
    }
}
