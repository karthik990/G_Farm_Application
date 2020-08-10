package com.rometools.rome.p052io.impl;

import com.anjlab.android.iab.p020v3.Constants;
import com.braintreepayments.api.models.PostalAddressParser;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.rss.TextInput;
import com.rometools.rome.p052io.FeedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/* renamed from: com.rometools.rome.io.impl.RSS090Parser */
public class RSS090Parser extends BaseWireFeedParser {
    private static final Namespace CONTENT_NS = Namespace.getNamespace(CONTENT_URI);
    private static final String CONTENT_URI = "http://purl.org/rss/1.0/modules/content/";
    private static final Namespace RDF_NS = Namespace.getNamespace(RDF_URI);
    private static final String RDF_URI = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    private static final Namespace RSS_NS = Namespace.getNamespace(RSS_URI);
    private static final String RSS_URI = "http://my.netscape.com/rdf/simple/0.9/";

    /* access modifiers changed from: protected */
    public void validateFeed(Document document) throws FeedException {
    }

    public RSS090Parser() {
        this("rss_0.9", RSS_NS);
    }

    protected RSS090Parser(String str, Namespace namespace) {
        super(str, namespace);
    }

    public boolean isMyType(Document document) {
        Element rootElement = document.getRootElement();
        Namespace namespace = rootElement.getNamespace();
        List<Namespace> additionalNamespaces = rootElement.getAdditionalNamespaces();
        if (!(namespace == null || !namespace.equals(getRDFNamespace()) || additionalNamespaces == null)) {
            for (Namespace equals : additionalNamespaces) {
                if (getRSSNamespace().equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    public WireFeed parse(Document document, boolean z, Locale locale) throws IllegalArgumentException, FeedException {
        if (z) {
            validateFeed(document);
        }
        return parseChannel(document.getRootElement(), locale);
    }

    /* access modifiers changed from: protected */
    public Namespace getRSSNamespace() {
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
    public WireFeed parseChannel(Element element, Locale locale) {
        Channel channel = new Channel(getType());
        channel.setStyleSheet(getStyleSheet(element.getDocument()));
        Element child = element.getChild("channel", getRSSNamespace());
        Element child2 = child.getChild("title", getRSSNamespace());
        if (child2 != null) {
            channel.setTitle(child2.getText());
        }
        Element child3 = child.getChild("link", getRSSNamespace());
        if (child3 != null) {
            channel.setLink(child3.getText());
        }
        Element child4 = child.getChild(Constants.RESPONSE_DESCRIPTION, getRSSNamespace());
        if (child4 != null) {
            channel.setDescription(child4.getText());
        }
        channel.setImage(parseImage(element));
        channel.setTextInput(parseTextInput(element));
        ArrayList arrayList = new ArrayList();
        List parseFeedModules = parseFeedModules(element, locale);
        List parseFeedModules2 = parseFeedModules(child, locale);
        if (parseFeedModules != null) {
            arrayList.addAll(parseFeedModules);
        }
        if (parseFeedModules2 != null) {
            arrayList.addAll(parseFeedModules2);
        }
        channel.setModules(arrayList);
        channel.setItems(parseItems(element, locale));
        List extractForeignMarkup = extractForeignMarkup(child, channel, getRSSNamespace());
        if (!extractForeignMarkup.isEmpty()) {
            channel.setForeignMarkup(extractForeignMarkup);
        }
        return channel;
    }

    /* access modifiers changed from: protected */
    public List<Element> getItems(Element element) {
        return element.getChildren("item", getRSSNamespace());
    }

    /* access modifiers changed from: protected */
    public Element getImage(Element element) {
        return element.getChild(TtmlNode.TAG_IMAGE, getRSSNamespace());
    }

    /* access modifiers changed from: protected */
    public Element getTextInput(Element element) {
        return element.getChild("textinput", getRSSNamespace());
    }

    /* access modifiers changed from: protected */
    public Image parseImage(Element element) {
        Element image = getImage(element);
        if (image == null) {
            return null;
        }
        Image image2 = new Image();
        Element child = image.getChild("title", getRSSNamespace());
        if (child != null) {
            image2.setTitle(child.getText());
        }
        Element child2 = image.getChild("url", getRSSNamespace());
        if (child2 != null) {
            image2.setUrl(child2.getText());
        }
        Element child3 = image.getChild("link", getRSSNamespace());
        if (child3 == null) {
            return image2;
        }
        image2.setLink(child3.getText());
        return image2;
    }

    /* access modifiers changed from: protected */
    public List<Item> parseItems(Element element, Locale locale) {
        ArrayList arrayList = new ArrayList();
        for (Element parseItem : getItems(element)) {
            arrayList.add(parseItem(element, parseItem, locale));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Item parseItem(Element element, Element element2, Locale locale) {
        Item item = new Item();
        Element child = element2.getChild("title", getRSSNamespace());
        if (child != null) {
            item.setTitle(child.getText());
        }
        Element child2 = element2.getChild("link", getRSSNamespace());
        if (child2 != null) {
            item.setLink(child2.getText());
            item.setUri(child2.getText());
        }
        item.setModules(parseItemModules(element2, locale));
        List extractForeignMarkup = extractForeignMarkup(element2, item, getRSSNamespace());
        Iterator it = extractForeignMarkup.iterator();
        while (it.hasNext()) {
            Element element3 = (Element) it.next();
            Namespace namespace = element3.getNamespace();
            String name = element3.getName();
            if (getContentNamespace().equals(namespace) && name.equals("encoded")) {
                it.remove();
            }
        }
        if (!extractForeignMarkup.isEmpty()) {
            item.setForeignMarkup(extractForeignMarkup);
        }
        return item;
    }

    /* access modifiers changed from: protected */
    public TextInput parseTextInput(Element element) {
        Element textInput = getTextInput(element);
        if (textInput == null) {
            return null;
        }
        TextInput textInput2 = new TextInput();
        Element child = textInput.getChild("title", getRSSNamespace());
        if (child != null) {
            textInput2.setTitle(child.getText());
        }
        Element child2 = textInput.getChild(Constants.RESPONSE_DESCRIPTION, getRSSNamespace());
        if (child2 != null) {
            textInput2.setDescription(child2.getText());
        }
        Element child3 = textInput.getChild(PostalAddressParser.USER_ADDRESS_NAME_KEY, getRSSNamespace());
        if (child3 != null) {
            textInput2.setName(child3.getText());
        }
        Element child4 = textInput.getChild("link", getRSSNamespace());
        if (child4 == null) {
            return textInput2;
        }
        textInput2.setLink(child4.getText());
        return textInput2;
    }
}
