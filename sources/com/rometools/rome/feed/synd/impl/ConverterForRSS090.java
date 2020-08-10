package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.rss.Source;
import com.rometools.rome.feed.synd.Converter;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.feed.synd.SyndImageImpl;
import com.rometools.rome.feed.synd.SyndLink;
import java.util.ArrayList;
import java.util.List;

public class ConverterForRSS090 implements Converter {
    private final String type;

    public ConverterForRSS090() {
        this("rss_0.9");
    }

    protected ConverterForRSS090(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public void copyInto(WireFeed wireFeed, SyndFeed syndFeed) {
        syndFeed.setModules(ModuleUtils.cloneModules(wireFeed.getModules()));
        List foreignMarkup = wireFeed.getForeignMarkup();
        if (!foreignMarkup.isEmpty()) {
            syndFeed.setForeignMarkup(foreignMarkup);
        }
        syndFeed.setStyleSheet(wireFeed.getStyleSheet());
        syndFeed.setEncoding(wireFeed.getEncoding());
        Channel channel = (Channel) wireFeed;
        syndFeed.setTitle(channel.getTitle());
        syndFeed.setLink(channel.getLink());
        syndFeed.setDescription(channel.getDescription());
        Image image = channel.getImage();
        if (image != null) {
            syndFeed.setImage(createSyndImage(image));
        }
        List items = channel.getItems();
        if (items != null) {
            syndFeed.setEntries(createSyndEntries(items, syndFeed.isPreservingWireFeed()));
        }
    }

    /* access modifiers changed from: protected */
    public SyndImage createSyndImage(Image image) {
        SyndImageImpl syndImageImpl = new SyndImageImpl();
        syndImageImpl.setTitle(image.getTitle());
        syndImageImpl.setUrl(image.getUrl());
        syndImageImpl.setLink(image.getLink());
        syndImageImpl.setWidth(image.getWidth());
        syndImageImpl.setHeight(image.getHeight());
        return syndImageImpl;
    }

    /* access modifiers changed from: protected */
    public List<SyndEntry> createSyndEntries(List<Item> list, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (Item createSyndEntry : list) {
            arrayList.add(createSyndEntry(createSyndEntry, z));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public SyndEntry createSyndEntry(Item item, boolean z) {
        SyndEntryImpl syndEntryImpl = new SyndEntryImpl();
        if (z) {
            syndEntryImpl.setWireEntry(item);
        }
        syndEntryImpl.setModules(ModuleUtils.cloneModules(item.getModules()));
        List foreignMarkup = item.getForeignMarkup();
        if (!foreignMarkup.isEmpty()) {
            syndEntryImpl.setForeignMarkup(foreignMarkup);
        }
        syndEntryImpl.setUri(item.getUri());
        syndEntryImpl.setLink(item.getLink());
        syndEntryImpl.setTitle(item.getTitle());
        syndEntryImpl.setLink(item.getLink());
        syndEntryImpl.setSource(createSource(item.getSource()));
        return syndEntryImpl;
    }

    /* access modifiers changed from: protected */
    public SyndFeed createSource(Source source) {
        if (source == null) {
            return null;
        }
        SyndFeedImpl syndFeedImpl = new SyndFeedImpl();
        syndFeedImpl.setLink(source.getUrl());
        syndFeedImpl.setUri(source.getUrl());
        syndFeedImpl.setTitle(source.getValue());
        return syndFeedImpl;
    }

    public WireFeed createRealFeed(SyndFeed syndFeed) {
        return createRealFeed(getType(), syndFeed);
    }

    /* access modifiers changed from: protected */
    public WireFeed createRealFeed(String str, SyndFeed syndFeed) {
        Channel channel = new Channel(str);
        channel.setModules(ModuleUtils.cloneModules(syndFeed.getModules()));
        channel.setStyleSheet(syndFeed.getStyleSheet());
        channel.setEncoding(syndFeed.getEncoding());
        channel.setTitle(syndFeed.getTitle());
        String link = syndFeed.getLink();
        List links = syndFeed.getLinks();
        if (link != null) {
            channel.setLink(link);
        } else if (!links.isEmpty()) {
            channel.setLink(((SyndLink) links.get(0)).getHref());
        }
        channel.setDescription(syndFeed.getDescription());
        SyndImage image = syndFeed.getImage();
        if (image != null) {
            channel.setImage(createRSSImage(image));
        }
        List entries = syndFeed.getEntries();
        if (entries != null) {
            channel.setItems(createRSSItems(entries));
        }
        List foreignMarkup = syndFeed.getForeignMarkup();
        if (!foreignMarkup.isEmpty()) {
            channel.setForeignMarkup(foreignMarkup);
        }
        return channel;
    }

    /* access modifiers changed from: protected */
    public Image createRSSImage(SyndImage syndImage) {
        Image image = new Image();
        image.setTitle(syndImage.getTitle());
        image.setUrl(syndImage.getUrl());
        image.setLink(syndImage.getLink());
        image.setHeight(syndImage.getHeight());
        image.setWidth(syndImage.getWidth());
        return image;
    }

    /* access modifiers changed from: protected */
    public List<Item> createRSSItems(List<SyndEntry> list) {
        ArrayList arrayList = new ArrayList();
        for (SyndEntry createRSSItem : list) {
            arrayList.add(createRSSItem(createRSSItem));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Item createRSSItem(SyndEntry syndEntry) {
        Item item = new Item();
        item.setModules(ModuleUtils.cloneModules(syndEntry.getModules()));
        item.setTitle(syndEntry.getTitle());
        item.setLink(syndEntry.getLink());
        List foreignMarkup = syndEntry.getForeignMarkup();
        if (!foreignMarkup.isEmpty()) {
            item.setForeignMarkup(foreignMarkup);
        }
        item.setSource(createSource(syndEntry.getSource()));
        String uri = syndEntry.getUri();
        if (uri != null) {
            item.setUri(uri);
        }
        return item;
    }

    /* access modifiers changed from: protected */
    public Source createSource(SyndFeed syndFeed) {
        if (syndFeed == null) {
            return null;
        }
        Source source = new Source();
        source.setUrl(syndFeed.getUri());
        source.setValue(syndFeed.getTitle());
        return source;
    }
}
