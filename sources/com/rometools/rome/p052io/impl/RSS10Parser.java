package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import java.util.Locale;
import org.apache.http.protocol.HTTP;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.RSS10Parser */
public class RSS10Parser extends RSS090Parser {
    private static final Namespace RSS_NS = Namespace.getNamespace(RSS_URI);
    private static final String RSS_URI = "http://purl.org/rss/1.0/";

    public RSS10Parser() {
        this("rss_1.0", RSS_NS);
    }

    protected RSS10Parser(String str, Namespace namespace) {
        super(str, namespace);
    }

    public boolean isMyType(Document document) {
        Element rootElement = document.getRootElement();
        Namespace namespace = rootElement.getNamespace();
        if (namespace != null && namespace.equals(getRDFNamespace())) {
            if (rootElement.getChild("channel", getRSSNamespace()) != null) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public Namespace getRSSNamespace() {
        return Namespace.getNamespace(RSS_URI);
    }

    /* access modifiers changed from: protected */
    public Item parseItem(Element element, Element element2, Locale locale) {
        Item parseItem = super.parseItem(element, element2, locale);
        Element child = element2.getChild(Constants.RESPONSE_DESCRIPTION, getRSSNamespace());
        if (child != null) {
            parseItem.setDescription(parseItemDescription(element, child));
        }
        Element child2 = element2.getChild("encoded", getContentNamespace());
        if (child2 != null) {
            Content content = new Content();
            content.setType("html");
            content.setValue(child2.getText());
            parseItem.setContent(content);
        }
        String attributeValue = element2.getAttributeValue("about", getRDFNamespace());
        if (attributeValue != null) {
            parseItem.setUri(attributeValue);
        }
        return parseItem;
    }

    /* access modifiers changed from: protected */
    public WireFeed parseChannel(Element element, Locale locale) {
        Channel channel = (Channel) super.parseChannel(element, locale);
        String str = "about";
        String attributeValue = element.getChild("channel", getRSSNamespace()).getAttributeValue(str, getRDFNamespace());
        if (attributeValue != null) {
            channel.setUri(attributeValue);
        }
        return channel;
    }

    /* access modifiers changed from: protected */
    public Description parseItemDescription(Element element, Element element2) {
        Description description = new Description();
        description.setType(HTTP.PLAIN_TEXT_TYPE);
        description.setValue(element2.getText());
        return description;
    }
}
