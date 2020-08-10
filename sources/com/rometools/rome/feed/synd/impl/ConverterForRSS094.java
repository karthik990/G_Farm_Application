package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.module.DCModule;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndLinkImpl;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.utils.Lists;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ConverterForRSS094 extends ConverterForRSS093 {
    public ConverterForRSS094() {
        this("rss_0.94");
    }

    protected ConverterForRSS094(String str) {
        super(str);
    }

    public void copyInto(WireFeed wireFeed, SyndFeed syndFeed) {
        Channel channel = (Channel) wireFeed;
        super.copyInto(channel, syndFeed);
        List categories = channel.getCategories();
        if (!categories.isEmpty()) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            linkedHashSet.addAll(createSyndCategories(categories));
            linkedHashSet.addAll(syndFeed.getCategories());
            syndFeed.setCategories(new ArrayList(linkedHashSet));
        }
    }

    /* access modifiers changed from: protected */
    public SyndEntry createSyndEntry(Item item, boolean z) {
        SyndEntry createSyndEntry = super.createSyndEntry(item, z);
        String author = item.getAuthor();
        if (author != null) {
            List creators = ((DCModule) createSyndEntry.getModule(DCModule.URI)).getCreators();
            if (!creators.contains(author)) {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                linkedHashSet.addAll(creators);
                linkedHashSet.add(author);
                creators.clear();
                creators.addAll(linkedHashSet);
            }
        }
        Guid guid = item.getGuid();
        String link = item.getLink();
        if (guid != null) {
            String value = guid.getValue();
            createSyndEntry.setUri(value);
            if (link == null && guid.isPermaLink()) {
                createSyndEntry.setLink(value);
            }
        } else {
            createSyndEntry.setUri(link);
        }
        if (item.getComments() != null) {
            SyndLinkImpl syndLinkImpl = new SyndLinkImpl();
            syndLinkImpl.setRel("comments");
            syndLinkImpl.setHref(item.getComments());
            syndLinkImpl.setType("text/html");
        }
        return createSyndEntry;
    }

    /* access modifiers changed from: protected */
    public WireFeed createRealFeed(String str, SyndFeed syndFeed) {
        Channel channel = (Channel) super.createRealFeed(str, syndFeed);
        List categories = syndFeed.getCategories();
        if (!categories.isEmpty()) {
            channel.setCategories(createRSSCategories(categories));
        }
        return channel;
    }

    /* access modifiers changed from: protected */
    public Item createRSSItem(SyndEntry syndEntry) {
        Item createRSSItem = super.createRSSItem(syndEntry);
        List authors = syndEntry.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            createRSSItem.setAuthor(((SyndPerson) authors.get(0)).getEmail());
        }
        Guid guid = null;
        String uri = syndEntry.getUri();
        String link = syndEntry.getLink();
        if (uri != null) {
            guid = new Guid();
            guid.setPermaLink(false);
            guid.setValue(uri);
        } else if (link != null) {
            guid = new Guid();
            guid.setPermaLink(true);
            guid.setValue(link);
        }
        createRSSItem.setGuid(guid);
        SyndLink findRelatedLink = syndEntry.findRelatedLink("comments");
        if (findRelatedLink != null && (findRelatedLink.getType() == null || findRelatedLink.getType().endsWith("html"))) {
            createRSSItem.setComments(findRelatedLink.getHref());
        }
        return createRSSItem;
    }
}
