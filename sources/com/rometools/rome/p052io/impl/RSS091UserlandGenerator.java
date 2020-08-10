package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.braintreepayments.api.models.PostalAddressParser;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.p052io.FeedException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.rometools.rome.io.impl.RSS091UserlandGenerator */
public class RSS091UserlandGenerator extends RSS090Generator {
    private final String version;

    /* access modifiers changed from: protected */
    public boolean isHourFormat24() {
        return true;
    }

    public RSS091UserlandGenerator() {
        this("rss_0.91U", "0.91");
    }

    protected RSS091UserlandGenerator(String str, String str2) {
        super(str);
        this.version = str2;
    }

    /* access modifiers changed from: protected */
    public Namespace getFeedNamespace() {
        return Namespace.NO_NAMESPACE;
    }

    /* access modifiers changed from: protected */
    public String getVersion() {
        return this.version;
    }

    /* access modifiers changed from: protected */
    public void addChannel(Channel channel, Element element) throws FeedException {
        super.addChannel(channel, element);
        Element child = element.getChild("channel", getFeedNamespace());
        addImage(channel, child);
        addTextInput(channel, child);
        addItems(channel, child);
    }

    /* access modifiers changed from: protected */
    public void checkChannelConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 1, 100);
        checkNotNullAndLength(element, Constants.RESPONSE_DESCRIPTION, 1, 500);
        checkNotNullAndLength(element, "link", 1, 500);
        checkNotNullAndLength(element, "language", 2, 5);
        checkLength(element, "rating", 20, 500);
        checkLength(element, "copyright", 1, 100);
        checkLength(element, "pubDate", 1, 100);
        checkLength(element, "lastBuildDate", 1, 100);
        checkLength(element, "docs", 1, 500);
        checkLength(element, "managingEditor", 1, 100);
        checkLength(element, "webMaster", 1, 100);
        Element child = element.getChild("skipHours");
        if (child != null) {
            for (Element text : child.getChildren()) {
                int parseInt = Integer.parseInt(text.getText().trim());
                String str = "Invalid hour value ";
                if (isHourFormat24()) {
                    if (parseInt < 1 || parseInt > 24) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(parseInt);
                        sb.append(", it must be between 1 and 24");
                        throw new FeedException(sb.toString());
                    }
                } else if (parseInt < 0 || parseInt > 23) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(str);
                    sb2.append(parseInt);
                    sb2.append(", it must be between 0 and 23");
                    throw new FeedException(sb2.toString());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void checkImageConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 1, 100);
        checkNotNullAndLength(element, "url", 1, 500);
        checkLength(element, "link", 1, 500);
        String str = SettingsJsonConstants.ICON_WIDTH_KEY;
        checkLength(element, str, 1, 3);
        checkLength(element, str, 1, 3);
        checkLength(element, Constants.RESPONSE_DESCRIPTION, 1, 100);
    }

    /* access modifiers changed from: protected */
    public void checkItemConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 1, 100);
        checkNotNullAndLength(element, "link", 1, 500);
        checkLength(element, Constants.RESPONSE_DESCRIPTION, 1, 500);
    }

    /* access modifiers changed from: protected */
    public void checkTextInputConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 1, 100);
        checkNotNullAndLength(element, Constants.RESPONSE_DESCRIPTION, 1, 500);
        checkNotNullAndLength(element, PostalAddressParser.USER_ADDRESS_NAME_KEY, 1, 20);
        checkNotNullAndLength(element, "link", 1, 500);
    }

    /* access modifiers changed from: protected */
    public Document createDocument(Element element) {
        return new Document(element);
    }

    /* access modifiers changed from: protected */
    public Element createRootElement(Channel channel) {
        Element element = new Element("rss", getFeedNamespace());
        element.setAttribute(new Attribute(ClientCookie.VERSION_ATTR, getVersion()));
        element.addNamespaceDeclaration(getContentNamespace());
        generateModuleNamespaceDefs(element);
        return element;
    }

    /* access modifiers changed from: protected */
    public Element generateSkipDaysElement(List<String> list) {
        Element element = new Element("skipDays");
        for (String str : list) {
            element.addContent((Content) generateSimpleElement("day", str.toString()));
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public Element generateSkipHoursElement(List<Integer> list) {
        Element element = new Element("skipHours", getFeedNamespace());
        for (Integer num : list) {
            element.addContent((Content) generateSimpleElement("hour", num.toString()));
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public void populateChannel(Channel channel, Element element) {
        super.populateChannel(channel, element);
        String language = channel.getLanguage();
        if (language != null) {
            element.addContent((Content) generateSimpleElement("language", language));
        }
        String rating = channel.getRating();
        if (rating != null) {
            element.addContent((Content) generateSimpleElement("rating", rating));
        }
        String copyright = channel.getCopyright();
        if (copyright != null) {
            element.addContent((Content) generateSimpleElement("copyright", copyright));
        }
        Date pubDate = channel.getPubDate();
        if (pubDate != null) {
            element.addContent((Content) generateSimpleElement("pubDate", DateParser.formatRFC822(pubDate, Locale.US)));
        }
        Date lastBuildDate = channel.getLastBuildDate();
        if (lastBuildDate != null) {
            element.addContent((Content) generateSimpleElement("lastBuildDate", DateParser.formatRFC822(lastBuildDate, Locale.US)));
        }
        String docs = channel.getDocs();
        if (docs != null) {
            element.addContent((Content) generateSimpleElement("docs", docs));
        }
        String managingEditor = channel.getManagingEditor();
        if (managingEditor != null) {
            element.addContent((Content) generateSimpleElement("managingEditor", managingEditor));
        }
        String webMaster = channel.getWebMaster();
        if (webMaster != null) {
            element.addContent((Content) generateSimpleElement("webMaster", webMaster));
        }
        List skipHours = channel.getSkipHours();
        if (skipHours != null && !skipHours.isEmpty()) {
            element.addContent((Content) generateSkipHoursElement(skipHours));
        }
        List skipDays = channel.getSkipDays();
        if (skipDays != null && !skipDays.isEmpty()) {
            element.addContent((Content) generateSkipDaysElement(skipDays));
        }
    }

    /* access modifiers changed from: protected */
    public void populateFeed(Channel channel, Element element) throws FeedException {
        addChannel(channel, element);
    }

    /* access modifiers changed from: protected */
    public void populateImage(Image image, Element element) {
        super.populateImage(image, element);
        Integer width = image.getWidth();
        if (width != null) {
            element.addContent((Content) generateSimpleElement(SettingsJsonConstants.ICON_WIDTH_KEY, String.valueOf(width)));
        }
        Integer height = image.getHeight();
        if (height != null) {
            element.addContent((Content) generateSimpleElement(SettingsJsonConstants.ICON_HEIGHT_KEY, String.valueOf(height)));
        }
        String description = image.getDescription();
        if (description != null) {
            element.addContent((Content) generateSimpleElement(Constants.RESPONSE_DESCRIPTION, description));
        }
    }

    /* access modifiers changed from: protected */
    public void populateItem(Item item, Element element, int i) {
        super.populateItem(item, element, i);
        Description description = item.getDescription();
        if (description != null) {
            element.addContent((Content) generateSimpleElement(Constants.RESPONSE_DESCRIPTION, description.getValue()));
        }
        Namespace contentNamespace = getContentNamespace();
        com.rometools.rome.feed.rss.Content content = item.getContent();
        if (item.getModule(contentNamespace.getURI()) == null && content != null) {
            Element element2 = new Element("encoded", contentNamespace);
            element2.addContent(content.getValue());
            element.addContent((Content) element2);
        }
    }
}
