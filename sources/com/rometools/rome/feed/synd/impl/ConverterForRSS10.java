package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.utils.Lists;
import java.util.ArrayList;
import java.util.List;

public class ConverterForRSS10 extends ConverterForRSS090 {
    public ConverterForRSS10() {
        this("rss_1.0");
    }

    protected ConverterForRSS10(String str) {
        super(str);
    }

    public void copyInto(WireFeed wireFeed, SyndFeed syndFeed) {
        Channel channel = (Channel) wireFeed;
        super.copyInto(channel, syndFeed);
        String uri = channel.getUri();
        if (uri != null) {
            syndFeed.setUri(uri);
        } else {
            syndFeed.setUri(channel.getLink());
        }
    }

    /* access modifiers changed from: protected */
    public SyndEntry createSyndEntry(Item item, boolean z) {
        SyndEntry createSyndEntry = super.createSyndEntry(item, z);
        Description description = item.getDescription();
        if (description != null) {
            SyndContentImpl syndContentImpl = new SyndContentImpl();
            syndContentImpl.setType(description.getType());
            syndContentImpl.setValue(description.getValue());
            createSyndEntry.setDescription(syndContentImpl);
        }
        Content content = item.getContent();
        if (content != null) {
            SyndContentImpl syndContentImpl2 = new SyndContentImpl();
            syndContentImpl2.setType(content.getType());
            syndContentImpl2.setValue(content.getValue());
            ArrayList arrayList = new ArrayList();
            arrayList.add(syndContentImpl2);
            createSyndEntry.setContents(arrayList);
        }
        return createSyndEntry;
    }

    /* access modifiers changed from: protected */
    public WireFeed createRealFeed(String str, SyndFeed syndFeed) {
        Channel channel = (Channel) super.createRealFeed(str, syndFeed);
        String uri = syndFeed.getUri();
        if (uri != null) {
            channel.setUri(uri);
        } else {
            channel.setUri(syndFeed.getLink());
        }
        return channel;
    }

    /* access modifiers changed from: protected */
    public Item createRSSItem(SyndEntry syndEntry) {
        Item createRSSItem = super.createRSSItem(syndEntry);
        SyndContent description = syndEntry.getDescription();
        if (description != null) {
            createRSSItem.setDescription(createItemDescription(description));
        }
        List contents = syndEntry.getContents();
        if (Lists.isNotEmpty(contents)) {
            createRSSItem.setContent(createItemContent((SyndContent) contents.get(0)));
        }
        String uri = syndEntry.getUri();
        if (uri != null) {
            createRSSItem.setUri(uri);
        }
        return createRSSItem;
    }

    /* access modifiers changed from: protected */
    public Description createItemDescription(SyndContent syndContent) {
        Description description = new Description();
        description.setValue(syndContent.getValue());
        description.setType(syndContent.getType());
        return description;
    }

    /* access modifiers changed from: protected */
    public Content createItemContent(SyndContent syndContent) {
        Content content = new Content();
        content.setValue(syndContent.getValue());
        content.setType(syndContent.getType());
        return content;
    }
}
