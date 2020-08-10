package com.rometools.rome.p052io.impl;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.constants.ChatConstants;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Generator;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.rome.p052io.FeedException;
import com.rometools.utils.Lists;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;

/* renamed from: com.rometools.rome.io.impl.Atom03Generator */
public class Atom03Generator extends BaseWireFeedGenerator {
    private static final String ATOM_03_URI = "http://purl.org/atom/ns#";
    private static final Namespace ATOM_NS = Namespace.getNamespace(ATOM_03_URI);
    private final String version;

    /* access modifiers changed from: protected */
    public void checkEntriesConstraints(Element element) throws FeedException {
    }

    /* access modifiers changed from: protected */
    public void checkEntryConstraints(Element element) throws FeedException {
    }

    /* access modifiers changed from: protected */
    public void checkFeedHeaderConstraints(Element element) throws FeedException {
    }

    public Atom03Generator() {
        this("atom_0.3", "0.3");
    }

    protected Atom03Generator(String str, String str2) {
        super(str);
        this.version = str2;
    }

    /* access modifiers changed from: protected */
    public String getVersion() {
        return this.version;
    }

    /* access modifiers changed from: protected */
    public Namespace getFeedNamespace() {
        return ATOM_NS;
    }

    public Document generate(WireFeed wireFeed) throws FeedException {
        Feed feed = (Feed) wireFeed;
        Element createRootElement = createRootElement(feed);
        populateFeed(feed, createRootElement);
        purgeUnusedNamespaceDeclarations(createRootElement);
        return createDocument(createRootElement);
    }

    /* access modifiers changed from: protected */
    public Document createDocument(Element element) {
        return new Document(element);
    }

    /* access modifiers changed from: protected */
    public Element createRootElement(Feed feed) {
        Element element = new Element("feed", getFeedNamespace());
        element.addNamespaceDeclaration(getFeedNamespace());
        element.setAttribute(new Attribute(ClientCookie.VERSION_ATTR, getVersion()));
        generateModuleNamespaceDefs(element);
        return element;
    }

    /* access modifiers changed from: protected */
    public void populateFeed(Feed feed, Element element) throws FeedException {
        addFeed(feed, element);
        addEntries(feed, element);
    }

    /* access modifiers changed from: protected */
    public void addFeed(Feed feed, Element element) throws FeedException {
        populateFeedHeader(feed, element);
        checkFeedHeaderConstraints(element);
        generateFeedModules(feed.getModules(), element);
        generateForeignMarkup(element, feed.getForeignMarkup());
    }

    /* access modifiers changed from: protected */
    public void addEntries(Feed feed, Element element) throws FeedException {
        for (Entry addEntry : feed.getEntries()) {
            addEntry(addEntry, element);
        }
        checkEntriesConstraints(element);
    }

    /* access modifiers changed from: protected */
    public void addEntry(Entry entry, Element element) throws FeedException {
        Element element2 = new Element("entry", getFeedNamespace());
        populateEntry(entry, element2);
        checkEntryConstraints(element2);
        generateItemModules(entry.getModules(), element2);
        element.addContent((Content) element2);
    }

    /* access modifiers changed from: protected */
    public void populateFeedHeader(Feed feed, Element element) throws FeedException {
        com.rometools.rome.feed.atom.Content titleEx = feed.getTitleEx();
        if (titleEx != null) {
            Element element2 = new Element("title", getFeedNamespace());
            fillContentElement(element2, titleEx);
            element.addContent((Content) element2);
        }
        for (Link generateLinkElement : feed.getAlternateLinks()) {
            element.addContent((Content) generateLinkElement(generateLinkElement));
        }
        for (Link generateLinkElement2 : feed.getOtherLinks()) {
            element.addContent((Content) generateLinkElement(generateLinkElement2));
        }
        List authors = feed.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            Element element3 = new Element("author", getFeedNamespace());
            fillPersonElement(element3, (SyndPerson) authors.get(0));
            element.addContent((Content) element3);
        }
        for (SyndPerson syndPerson : feed.getContributors()) {
            Element element4 = new Element("contributor", getFeedNamespace());
            fillPersonElement(element4, syndPerson);
            element.addContent((Content) element4);
        }
        com.rometools.rome.feed.atom.Content tagline = feed.getTagline();
        if (tagline != null) {
            Element element5 = new Element("tagline", getFeedNamespace());
            fillContentElement(element5, tagline);
            element.addContent((Content) element5);
        }
        String id = feed.getId();
        if (id != null) {
            element.addContent((Content) generateSimpleElement(TtmlNode.ATTR_ID, id));
        }
        Generator generator = feed.getGenerator();
        if (generator != null) {
            element.addContent((Content) generateGeneratorElement(generator));
        }
        String copyright = feed.getCopyright();
        if (copyright != null) {
            element.addContent((Content) generateSimpleElement("copyright", copyright));
        }
        com.rometools.rome.feed.atom.Content info = feed.getInfo();
        if (info != null) {
            Element element6 = new Element(ChatConstants.ARG_USER_INFO, getFeedNamespace());
            fillContentElement(element6, info);
            element.addContent((Content) element6);
        }
        Date modified = feed.getModified();
        if (modified != null) {
            Element element7 = new Element("modified", getFeedNamespace());
            element7.addContent(DateParser.formatW3CDateTime(modified, Locale.US));
            element.addContent((Content) element7);
        }
    }

    /* access modifiers changed from: protected */
    public void populateEntry(Entry entry, Element element) throws FeedException {
        com.rometools.rome.feed.atom.Content titleEx = entry.getTitleEx();
        if (titleEx != null) {
            Element element2 = new Element("title", getFeedNamespace());
            fillContentElement(element2, titleEx);
            element.addContent((Content) element2);
        }
        for (Link generateLinkElement : entry.getAlternateLinks()) {
            element.addContent((Content) generateLinkElement(generateLinkElement));
        }
        for (Link generateLinkElement2 : entry.getOtherLinks()) {
            element.addContent((Content) generateLinkElement(generateLinkElement2));
        }
        List authors = entry.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            Element element3 = new Element("author", getFeedNamespace());
            fillPersonElement(element3, (SyndPerson) authors.get(0));
            element.addContent((Content) element3);
        }
        for (SyndPerson syndPerson : entry.getContributors()) {
            Element element4 = new Element("contributor", getFeedNamespace());
            fillPersonElement(element4, syndPerson);
            element.addContent((Content) element4);
        }
        String id = entry.getId();
        if (id != null) {
            element.addContent((Content) generateSimpleElement(TtmlNode.ATTR_ID, id));
        }
        Date modified = entry.getModified();
        if (modified != null) {
            Element element5 = new Element("modified", getFeedNamespace());
            element5.addContent(DateParser.formatW3CDateTime(modified, Locale.US));
            element.addContent((Content) element5);
        }
        Date issued = entry.getIssued();
        if (issued != null) {
            Element element6 = new Element("issued", getFeedNamespace());
            element6.addContent(DateParser.formatW3CDateTime(issued, Locale.US));
            element.addContent((Content) element6);
        }
        Date created = entry.getCreated();
        if (created != null) {
            Element element7 = new Element("created", getFeedNamespace());
            element7.addContent(DateParser.formatW3CDateTime(created, Locale.US));
            element.addContent((Content) element7);
        }
        com.rometools.rome.feed.atom.Content summary = entry.getSummary();
        if (summary != null) {
            Element element8 = new Element("summary", getFeedNamespace());
            fillContentElement(element8, summary);
            element.addContent((Content) element8);
        }
        for (com.rometools.rome.feed.atom.Content content : entry.getContents()) {
            Element element9 = new Element(Param.CONTENT, getFeedNamespace());
            fillContentElement(element9, content);
            element.addContent((Content) element9);
        }
        generateForeignMarkup(element, entry.getForeignMarkup());
    }

    /* access modifiers changed from: protected */
    public Element generateLinkElement(Link link) {
        Element element = new Element("link", getFeedNamespace());
        String rel = link.getRel();
        if (rel != null) {
            element.setAttribute(new Attribute("rel", rel));
        }
        String type = link.getType();
        if (type != null) {
            element.setAttribute(new Attribute("type", type));
        }
        String href = link.getHref();
        if (href != null) {
            element.setAttribute(new Attribute("href", href));
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public void fillPersonElement(Element element, SyndPerson syndPerson) {
        String name = syndPerson.getName();
        if (name != null) {
            element.addContent((Content) generateSimpleElement(PostalAddressParser.USER_ADDRESS_NAME_KEY, name));
        }
        String uri = syndPerson.getUri();
        if (uri != null) {
            element.addContent((Content) generateSimpleElement("url", uri));
        }
        String email = syndPerson.getEmail();
        if (email != null) {
            element.addContent((Content) generateSimpleElement("email", email));
        }
    }

    /* access modifiers changed from: protected */
    public Element generateTagLineElement(com.rometools.rome.feed.atom.Content content) {
        Element element = new Element("tagline", getFeedNamespace());
        String type = content.getType();
        if (type != null) {
            element.setAttribute(new Attribute("type", type));
        }
        String value = content.getValue();
        if (value != null) {
            element.addContent(value);
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public void fillContentElement(Element element, com.rometools.rome.feed.atom.Content content) throws FeedException {
        String type = content.getType();
        if (type != null) {
            element.setAttribute(new Attribute("type", type));
        }
        String mode = content.getMode();
        if (mode != null) {
            element.setAttribute(new Attribute("mode", mode));
        }
        String value = content.getValue();
        if (value == null) {
            return;
        }
        if (mode == null || mode.equals(com.rometools.rome.feed.atom.Content.ESCAPED)) {
            element.addContent(value);
        } else if (mode.equals(com.rometools.rome.feed.atom.Content.BASE64)) {
            element.addContent(Base64.encode(value));
        } else if (mode.equals("xml")) {
            StringBuffer stringBuffer = new StringBuffer("<tmpdoc>");
            stringBuffer.append(value);
            stringBuffer.append("</tmpdoc>");
            try {
                element.addContent((Collection<? extends Content>) new SAXBuilder().build((Reader) new StringReader(stringBuffer.toString())).getRootElement().removeContent());
            } catch (Exception e) {
                throw new FeedException("Invalid XML", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Element generateGeneratorElement(Generator generator) {
        Element element = new Element("generator", getFeedNamespace());
        String url = generator.getUrl();
        if (url != null) {
            element.setAttribute(new Attribute("url", url));
        }
        String version2 = generator.getVersion();
        if (version2 != null) {
            element.setAttribute(new Attribute(ClientCookie.VERSION_ATTR, version2));
        }
        String value = generator.getValue();
        if (value != null) {
            element.addContent(value);
        }
        return element;
    }

    /* access modifiers changed from: protected */
    public Element generateSimpleElement(String str, String str2) {
        Element element = new Element(str, getFeedNamespace());
        element.addContent(str2);
        return element;
    }
}
