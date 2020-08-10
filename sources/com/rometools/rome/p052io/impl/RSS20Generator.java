package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.rometools.rome.feed.rss.Category;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import org.jdom2.Content;
import org.jdom2.Element;

/* renamed from: com.rometools.rome.io.impl.RSS20Generator */
public class RSS20Generator extends RSS094Generator {
    public RSS20Generator() {
        this("rss_2.0", "2.0");
    }

    protected RSS20Generator(String str, String str2) {
        super(str, str2);
    }

    /* access modifiers changed from: protected */
    public void populateChannel(Channel channel, Element element) {
        super.populateChannel(channel, element);
        String generator = channel.getGenerator();
        if (generator != null) {
            element.addContent((Content) generateSimpleElement("generator", generator));
        }
        int ttl = channel.getTtl();
        if (ttl > -1) {
            element.addContent((Content) generateSimpleElement("ttl", String.valueOf(ttl)));
        }
        for (Category generateCategoryElement : channel.getCategories()) {
            element.addContent((Content) generateCategoryElement(generateCategoryElement));
        }
        generateForeignMarkup(element, channel.getForeignMarkup());
    }

    public void populateItem(Item item, Element element, int i) {
        super.populateItem(item, element, i);
        Element child = element.getChild(Constants.RESPONSE_DESCRIPTION, getFeedNamespace());
        if (child != null) {
            child.removeAttribute("type");
        }
        String author = item.getAuthor();
        if (author != null) {
            element.addContent((Content) generateSimpleElement("author", author));
        }
        String comments = item.getComments();
        if (comments != null) {
            element.addContent((Content) generateSimpleElement("comments", comments));
        }
        Guid guid = item.getGuid();
        if (guid != null) {
            Element generateSimpleElement = generateSimpleElement("guid", guid.getValue());
            if (!guid.isPermaLink()) {
                generateSimpleElement.setAttribute("isPermaLink", "false");
            }
            element.addContent((Content) generateSimpleElement);
        }
    }
}
