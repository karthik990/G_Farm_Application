package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.braintreepayments.api.models.PostalAddressParser;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.p052io.FeedException;
import java.util.List;
import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.RSS10Generator */
public class RSS10Generator extends RSS090Generator {
    private static final Namespace RSS_NS = Namespace.getNamespace(RSS_URI);
    private static final String RSS_URI = "http://purl.org/rss/1.0/";

    /* access modifiers changed from: protected */
    public void checkItemsConstraints(Element element) throws FeedException {
    }

    public RSS10Generator() {
        super("rss_1.0");
    }

    protected RSS10Generator(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public Namespace getFeedNamespace() {
        return RSS_NS;
    }

    /* access modifiers changed from: protected */
    public void populateChannel(Channel channel, Element element) {
        super.populateChannel(channel, element);
        String uri = channel.getUri();
        if (uri != null) {
            element.setAttribute("about", uri, getRDFNamespace());
        }
        List<Item> items = channel.getItems();
        if (!items.isEmpty()) {
            Element element2 = new Element("items", getFeedNamespace());
            Element element3 = new Element("Seq", getRDFNamespace());
            for (Item item : items) {
                Element element4 = new Element("li", getRDFNamespace());
                String uri2 = item.getUri();
                if (uri2 != null) {
                    element4.setAttribute("resource", uri2, getRDFNamespace());
                }
                element3.addContent((Content) element4);
            }
            element2.addContent((Content) element3);
            element.addContent((Content) element2);
        }
    }

    /* access modifiers changed from: protected */
    public void populateItem(Item item, Element element, int i) {
        super.populateItem(item, element, i);
        String link = item.getLink();
        String uri = item.getUri();
        String str = "about";
        if (uri != null) {
            element.setAttribute(str, uri, getRDFNamespace());
        } else if (link != null) {
            element.setAttribute(str, link, getRDFNamespace());
        }
        Description description = item.getDescription();
        if (description != null) {
            element.addContent((Content) generateSimpleElement(Constants.RESPONSE_DESCRIPTION, description.getValue()));
        }
        if (item.getModule(getContentNamespace().getURI()) == null && item.getContent() != null) {
            Element element2 = new Element("encoded", getContentNamespace());
            element2.addContent(item.getContent().getValue());
            element.addContent((Content) element2);
        }
    }

    /* access modifiers changed from: protected */
    public void checkChannelConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, -1);
        checkNotNullAndLength(element, Constants.RESPONSE_DESCRIPTION, 0, -1);
        checkNotNullAndLength(element, "link", 0, -1);
    }

    /* access modifiers changed from: protected */
    public void checkImageConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, -1);
        checkNotNullAndLength(element, "url", 0, -1);
        checkNotNullAndLength(element, "link", 0, -1);
    }

    /* access modifiers changed from: protected */
    public void checkTextInputConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, -1);
        checkNotNullAndLength(element, Constants.RESPONSE_DESCRIPTION, 0, -1);
        checkNotNullAndLength(element, PostalAddressParser.USER_ADDRESS_NAME_KEY, 0, -1);
        checkNotNullAndLength(element, "link", 0, -1);
    }

    /* access modifiers changed from: protected */
    public void checkItemConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, -1);
        checkNotNullAndLength(element, "link", 0, -1);
    }
}
