package com.rometools.rome.p052io.impl;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import java.util.Locale;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS094Parser */
public class RSS094Parser extends RSS093Parser {
    /* access modifiers changed from: protected */
    public String getRSSVersion() {
        return "0.94";
    }

    public RSS094Parser() {
        this("rss_0.94");
    }

    protected RSS094Parser(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public WireFeed parseChannel(Element element, Locale locale) {
        Channel channel = (Channel) super.parseChannel(element, locale);
        Element child = element.getChild("channel", getRSSNamespace());
        channel.setCategories(parseCategories(child.getChildren("category", getRSSNamespace())));
        Element child2 = child.getChild("ttl", getRSSNamespace());
        if (!(child2 == null || child2.getText() == null)) {
            Integer parseInt = NumberParser.parseInt(child2.getText());
            if (parseInt != null) {
                channel.setTtl(parseInt.intValue());
            }
        }
        return channel;
    }

    public Item parseItem(Element element, Element element2, Locale locale) {
        Item parseItem = super.parseItem(element, element2, locale);
        parseItem.setExpirationDate(null);
        Element child = element2.getChild("author", getRSSNamespace());
        if (child != null) {
            parseItem.setAuthor(child.getText());
        }
        Element child2 = element2.getChild("guid", getRSSNamespace());
        if (child2 != null) {
            Guid guid = new Guid();
            String attributeValue = child2.getAttributeValue("isPermaLink");
            if (attributeValue != null) {
                guid.setPermaLink(attributeValue.equalsIgnoreCase("true"));
            }
            guid.setValue(child2.getText());
            parseItem.setGuid(guid);
        }
        Element child3 = element2.getChild("comments", getRSSNamespace());
        if (child3 != null) {
            parseItem.setComments(child3.getText());
        }
        return parseItem;
    }
}
