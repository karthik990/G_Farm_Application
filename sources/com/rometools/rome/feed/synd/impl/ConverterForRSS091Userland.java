package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.module.DCModule;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.utils.Lists;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

public class ConverterForRSS091Userland extends ConverterForRSS090 {
    public ConverterForRSS091Userland() {
        this("rss_0.91U");
    }

    protected ConverterForRSS091Userland(String str) {
        super(str);
    }

    public void copyInto(WireFeed wireFeed, SyndFeed syndFeed) {
        Channel channel = (Channel) wireFeed;
        super.copyInto(channel, syndFeed);
        syndFeed.setLanguage(channel.getLanguage());
        syndFeed.setCopyright(channel.getCopyright());
        syndFeed.setDocs(channel.getDocs());
        syndFeed.setManagingEditor(channel.getManagingEditor());
        syndFeed.setWebMaster(channel.getWebMaster());
        syndFeed.setGenerator(channel.getGenerator());
        Date pubDate = channel.getPubDate();
        if (pubDate != null) {
            syndFeed.setPublishedDate(pubDate);
        } else if (channel.getLastBuildDate() != null) {
            syndFeed.setPublishedDate(channel.getLastBuildDate());
        }
        String managingEditor = channel.getManagingEditor();
        if (managingEditor != null) {
            List creators = ((DCModule) syndFeed.getModule(DCModule.URI)).getCreators();
            if (!creators.contains(managingEditor)) {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                linkedHashSet.addAll(creators);
                linkedHashSet.add(managingEditor);
                creators.clear();
                creators.addAll(linkedHashSet);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Description createItemDescription(SyndContent syndContent) {
        Description description = new Description();
        description.setValue(syndContent.getValue());
        description.setType(syndContent.getType());
        return description;
    }

    /* access modifiers changed from: protected */
    public Image createRSSImage(SyndImage syndImage) {
        Image createRSSImage = super.createRSSImage(syndImage);
        createRSSImage.setDescription(syndImage.getDescription());
        return createRSSImage;
    }

    /* access modifiers changed from: protected */
    public Item createRSSItem(SyndEntry syndEntry) {
        Item createRSSItem = super.createRSSItem(syndEntry);
        createRSSItem.setComments(syndEntry.getComments());
        SyndContent description = syndEntry.getDescription();
        if (description != null) {
            createRSSItem.setDescription(createItemDescription(description));
        }
        List contents = syndEntry.getContents();
        if (Lists.isNotEmpty(contents)) {
            SyndContent syndContent = (SyndContent) contents.get(0);
            Content content = new Content();
            content.setValue(syndContent.getValue());
            content.setType(syndContent.getType());
            createRSSItem.setContent(content);
        }
        return createRSSItem;
    }

    /* access modifiers changed from: protected */
    public WireFeed createRealFeed(String str, SyndFeed syndFeed) {
        Channel channel = (Channel) super.createRealFeed(str, syndFeed);
        channel.setLanguage(syndFeed.getLanguage());
        channel.setCopyright(syndFeed.getCopyright());
        channel.setPubDate(syndFeed.getPublishedDate());
        channel.setDocs(syndFeed.getDocs());
        channel.setManagingEditor(syndFeed.getManagingEditor());
        channel.setWebMaster(syndFeed.getWebMaster());
        channel.setGenerator(syndFeed.getGenerator());
        List authors = syndFeed.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            channel.setManagingEditor(((SyndPerson) authors.get(0)).getName());
        }
        return channel;
    }

    /* access modifiers changed from: protected */
    public SyndEntry createSyndEntry(Item item, boolean z) {
        SyndEntry createSyndEntry = super.createSyndEntry(item, z);
        Description description = item.getDescription();
        createSyndEntry.setComments(item.getComments());
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
    public SyndImage createSyndImage(Image image) {
        SyndImage createSyndImage = super.createSyndImage(image);
        createSyndImage.setDescription(image.getDescription());
        return createSyndImage;
    }
}
