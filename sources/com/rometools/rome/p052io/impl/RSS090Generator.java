package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.rss.TextInput;
import com.rometools.rome.p052io.FeedException;
import java.util.List;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.RSS090Generator */
public class RSS090Generator extends BaseWireFeedGenerator {
    private static final Namespace CONTENT_NS = Namespace.getNamespace(Param.CONTENT, CONTENT_URI);
    private static final String CONTENT_URI = "http://purl.org/rss/1.0/modules/content/";
    private static final Namespace RDF_NS = Namespace.getNamespace("rdf", RDF_URI);
    private static final String RDF_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final Namespace RSS_NS = Namespace.getNamespace(RSS_URI);
    private static final String RSS_URI = "http://my.netscape.com/rdf/simple/0.9/";

    /* access modifiers changed from: protected */
    public String getTextInputLabel() {
        return "textInput";
    }

    public RSS090Generator() {
        this("rss_0.9");
    }

    protected RSS090Generator(String str) {
        super(str);
    }

    public Document generate(WireFeed wireFeed) throws FeedException {
        Channel channel = (Channel) wireFeed;
        Element createRootElement = createRootElement(channel);
        populateFeed(channel, createRootElement);
        purgeUnusedNamespaceDeclarations(createRootElement);
        return createDocument(createRootElement);
    }

    /* access modifiers changed from: protected */
    public Namespace getFeedNamespace() {
        return RSS_NS;
    }

    /* access modifiers changed from: protected */
    public Namespace getRDFNamespace() {
        return RDF_NS;
    }

    /* access modifiers changed from: protected */
    public Namespace getContentNamespace() {
        return CONTENT_NS;
    }

    /* access modifiers changed from: protected */
    public Document createDocument(Element element) {
        return new Document(element);
    }

    /* access modifiers changed from: protected */
    public Element createRootElement(Channel channel) {
        Element element = new Element("RDF", getRDFNamespace());
        element.addNamespaceDeclaration(getFeedNamespace());
        element.addNamespaceDeclaration(getRDFNamespace());
        element.addNamespaceDeclaration(getContentNamespace());
        generateModuleNamespaceDefs(element);
        return element;
    }

    /* access modifiers changed from: protected */
    public void populateFeed(Channel channel, Element element) throws FeedException {
        addChannel(channel, element);
        addImage(channel, element);
        addTextInput(channel, element);
        addItems(channel, element);
        generateForeignMarkup(element, channel.getForeignMarkup());
    }

    /* access modifiers changed from: protected */
    public void addChannel(Channel channel, Element element) throws FeedException {
        Element element2 = new Element("channel", getFeedNamespace());
        populateChannel(channel, element2);
        checkChannelConstraints(element2);
        element.addContent((Content) element2);
        generateFeedModules(channel.getModules(), element2);
    }

    /* access modifiers changed from: protected */
    public void populateChannel(Channel channel, Element element) {
        String title = channel.getTitle();
        if (title != null) {
            element.addContent((Content) generateSimpleElement("title", title));
        }
        String link = channel.getLink();
        if (link != null) {
            element.addContent((Content) generateSimpleElement("link", link));
        }
        String description = channel.getDescription();
        if (description != null) {
            element.addContent((Content) generateSimpleElement(Constants.RESPONSE_DESCRIPTION, description));
        }
    }

    /* access modifiers changed from: protected */
    public void checkNotNullAndLength(Element element, String str, int i, int i2) throws FeedException {
        if (element.getChild(str, getFeedNamespace()) != null) {
            checkLength(element, str, i, i2);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Invalid ");
        sb.append(getType());
        sb.append(" feed, missing ");
        sb.append(element.getName());
        sb.append(" ");
        sb.append(str);
        throw new FeedException(sb.toString());
    }

    /* access modifiers changed from: protected */
    public void checkLength(Element element, String str, int i, int i2) throws FeedException {
        Element child = element.getChild(str, getFeedNamespace());
        if (child != null) {
            String str2 = " length";
            String str3 = " ";
            String str4 = " feed, ";
            String str5 = "Invalid ";
            if (i > 0 && child.getText().length() < i) {
                StringBuilder sb = new StringBuilder();
                sb.append(str5);
                sb.append(getType());
                sb.append(str4);
                sb.append(element.getName());
                sb.append(str3);
                sb.append(str);
                sb.append("short of ");
                sb.append(i);
                sb.append(str2);
                throw new FeedException(sb.toString());
            } else if (i2 > -1 && child.getText().length() > i2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str5);
                sb2.append(getType());
                sb2.append(str4);
                sb2.append(element.getName());
                sb2.append(str3);
                sb2.append(str);
                sb2.append("exceeds ");
                sb2.append(i2);
                sb2.append(str2);
                throw new FeedException(sb2.toString());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addImage(Channel channel, Element element) throws FeedException {
        Image image = channel.getImage();
        if (image != null) {
            Element element2 = new Element(TtmlNode.TAG_IMAGE, getFeedNamespace());
            populateImage(image, element2);
            checkImageConstraints(element2);
            element.addContent((Content) element2);
        }
    }

    /* access modifiers changed from: protected */
    public void populateImage(Image image, Element element) {
        String title = image.getTitle();
        if (title != null) {
            element.addContent((Content) generateSimpleElement("title", title));
        }
        String url = image.getUrl();
        if (url != null) {
            element.addContent((Content) generateSimpleElement("url", url));
        }
        String link = image.getLink();
        if (link != null) {
            element.addContent((Content) generateSimpleElement("link", link));
        }
    }

    /* access modifiers changed from: protected */
    public void addTextInput(Channel channel, Element element) throws FeedException {
        TextInput textInput = channel.getTextInput();
        if (textInput != null) {
            Element element2 = new Element(getTextInputLabel(), getFeedNamespace());
            populateTextInput(textInput, element2);
            checkTextInputConstraints(element2);
            element.addContent((Content) element2);
        }
    }

    /* access modifiers changed from: protected */
    public void populateTextInput(TextInput textInput, Element element) {
        String title = textInput.getTitle();
        if (title != null) {
            element.addContent((Content) generateSimpleElement("title", title));
        }
        String description = textInput.getDescription();
        if (description != null) {
            element.addContent((Content) generateSimpleElement(Constants.RESPONSE_DESCRIPTION, description));
        }
        String name = textInput.getName();
        if (name != null) {
            element.addContent((Content) generateSimpleElement(PostalAddressParser.USER_ADDRESS_NAME_KEY, name));
        }
        String link = textInput.getLink();
        if (link != null) {
            element.addContent((Content) generateSimpleElement("link", link));
        }
    }

    /* access modifiers changed from: protected */
    public void addItems(Channel channel, Element element) throws FeedException {
        List items = channel.getItems();
        for (int i = 0; i < items.size(); i++) {
            addItem((Item) items.get(i), element, i);
        }
        checkItemsConstraints(element);
    }

    /* access modifiers changed from: protected */
    public void addItem(Item item, Element element, int i) throws FeedException {
        Element element2 = new Element("item", getFeedNamespace());
        populateItem(item, element2, i);
        checkItemConstraints(element2);
        generateItemModules(item.getModules(), element2);
        element.addContent((Content) element2);
    }

    /* access modifiers changed from: protected */
    public void populateItem(Item item, Element element, int i) {
        String title = item.getTitle();
        if (title != null) {
            element.addContent((Content) generateSimpleElement("title", title));
        }
        String link = item.getLink();
        if (link != null) {
            element.addContent((Content) generateSimpleElement("link", link));
        }
        generateForeignMarkup(element, item.getForeignMarkup());
    }

    /* access modifiers changed from: protected */
    public Element generateSimpleElement(String str, String str2) {
        Element element = new Element(str, getFeedNamespace());
        element.addContent(str2);
        return element;
    }

    /* access modifiers changed from: protected */
    public void checkChannelConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, 40);
        checkNotNullAndLength(element, Constants.RESPONSE_DESCRIPTION, 0, 500);
        checkNotNullAndLength(element, "link", 0, 500);
    }

    /* access modifiers changed from: protected */
    public void checkImageConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, 40);
        checkNotNullAndLength(element, "url", 0, 500);
        checkNotNullAndLength(element, "link", 0, 500);
    }

    /* access modifiers changed from: protected */
    public void checkTextInputConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, 40);
        checkNotNullAndLength(element, Constants.RESPONSE_DESCRIPTION, 0, 100);
        checkNotNullAndLength(element, PostalAddressParser.USER_ADDRESS_NAME_KEY, 0, 500);
        checkNotNullAndLength(element, "link", 0, 500);
    }

    /* access modifiers changed from: protected */
    public void checkItemsConstraints(Element element) throws FeedException {
        int size = element.getChildren("item", getFeedNamespace()).size();
        if (size < 1 || size > 15) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid ");
            sb.append(getType());
            sb.append(" feed, item count is ");
            sb.append(size);
            sb.append(" it must be between 1 an 15");
            throw new FeedException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public void checkItemConstraints(Element element) throws FeedException {
        checkNotNullAndLength(element, "title", 0, 100);
        checkNotNullAndLength(element, "link", 0, 500);
    }
}
