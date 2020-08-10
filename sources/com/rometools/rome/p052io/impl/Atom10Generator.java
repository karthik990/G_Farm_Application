package com.rometools.rome.p052io.impl;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.atom.Category;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Generator;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.rome.p052io.FeedException;
import com.rometools.rome.p052io.WireFeedOutput;
import com.rometools.utils.Lists;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.protocol.HTTP;
import org.jdom2.Attribute;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.rometools.rome.io.impl.Atom10Generator */
public class Atom10Generator extends BaseWireFeedGenerator {
    private static final String ATOM_10_URI = "http://www.w3.org/2005/Atom";
    private static final Namespace ATOM_NS = Namespace.getNamespace(ATOM_10_URI);
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

    public Atom10Generator() {
        this("atom_1.0", "1.0");
    }

    protected Atom10Generator(String str, String str2) {
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
        String xmlBase = feed.getXmlBase();
        if (xmlBase != null) {
            element.setAttribute("base", xmlBase, Namespace.XML_NAMESPACE);
        }
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
        generateForeignMarkup(element, feed.getForeignMarkup());
        checkFeedHeaderConstraints(element);
        generateFeedModules(feed.getModules(), element);
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
        String xmlBase = entry.getXmlBase();
        if (xmlBase != null) {
            element2.setAttribute("base", xmlBase, Namespace.XML_NAMESPACE);
        }
        populateEntry(entry, element2);
        generateForeignMarkup(element2, entry.getForeignMarkup());
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
        List<Link> alternateLinks = feed.getAlternateLinks();
        if (alternateLinks != null) {
            for (Link generateLinkElement : alternateLinks) {
                element.addContent((Content) generateLinkElement(generateLinkElement));
            }
        }
        List<Link> otherLinks = feed.getOtherLinks();
        if (otherLinks != null) {
            for (Link generateLinkElement2 : otherLinks) {
                element.addContent((Content) generateLinkElement(generateLinkElement2));
            }
        }
        List<Category> categories = feed.getCategories();
        if (categories != null) {
            for (Category generateCategoryElement : categories) {
                element.addContent((Content) generateCategoryElement(generateCategoryElement));
            }
        }
        List<SyndPerson> authors = feed.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            for (SyndPerson syndPerson : authors) {
                Element element3 = new Element("author", getFeedNamespace());
                fillPersonElement(element3, syndPerson);
                element.addContent((Content) element3);
            }
        }
        List<SyndPerson> contributors = feed.getContributors();
        if (Lists.isNotEmpty(contributors)) {
            for (SyndPerson syndPerson2 : contributors) {
                Element element4 = new Element("contributor", getFeedNamespace());
                fillPersonElement(element4, syndPerson2);
                element.addContent((Content) element4);
            }
        }
        com.rometools.rome.feed.atom.Content subtitle = feed.getSubtitle();
        if (subtitle != null) {
            Element element5 = new Element("subtitle", getFeedNamespace());
            fillContentElement(element5, subtitle);
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
        String rights = feed.getRights();
        if (rights != null) {
            element.addContent((Content) generateSimpleElement("rights", rights));
        }
        String icon = feed.getIcon();
        if (icon != null) {
            element.addContent((Content) generateSimpleElement(SettingsJsonConstants.APP_ICON_KEY, icon));
        }
        String logo = feed.getLogo();
        if (logo != null) {
            element.addContent((Content) generateSimpleElement("logo", logo));
        }
        Date updated = feed.getUpdated();
        if (updated != null) {
            Element element6 = new Element("updated", getFeedNamespace());
            element6.addContent(DateParser.formatW3CDateTime(updated, Locale.US));
            element.addContent((Content) element6);
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
        List<Link> alternateLinks = entry.getAlternateLinks();
        if (alternateLinks != null) {
            for (Link generateLinkElement : alternateLinks) {
                element.addContent((Content) generateLinkElement(generateLinkElement));
            }
        }
        List<Link> otherLinks = entry.getOtherLinks();
        if (otherLinks != null) {
            for (Link generateLinkElement2 : otherLinks) {
                element.addContent((Content) generateLinkElement(generateLinkElement2));
            }
        }
        List<Category> categories = entry.getCategories();
        if (categories != null) {
            for (Category generateCategoryElement : categories) {
                element.addContent((Content) generateCategoryElement(generateCategoryElement));
            }
        }
        List<SyndPerson> authors = entry.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            for (SyndPerson syndPerson : authors) {
                Element element3 = new Element("author", getFeedNamespace());
                fillPersonElement(element3, syndPerson);
                element.addContent((Content) element3);
            }
        }
        List<SyndPerson> contributors = entry.getContributors();
        if (Lists.isNotEmpty(contributors)) {
            for (SyndPerson syndPerson2 : contributors) {
                Element element4 = new Element("contributor", getFeedNamespace());
                fillPersonElement(element4, syndPerson2);
                element.addContent((Content) element4);
            }
        }
        String id = entry.getId();
        if (id != null) {
            element.addContent((Content) generateSimpleElement(TtmlNode.ATTR_ID, id));
        }
        Date updated = entry.getUpdated();
        if (updated != null) {
            Element element5 = new Element("updated", getFeedNamespace());
            element5.addContent(DateParser.formatW3CDateTime(updated, Locale.US));
            element.addContent((Content) element5);
        }
        Date published = entry.getPublished();
        if (published != null) {
            Element element6 = new Element("published", getFeedNamespace());
            element6.addContent(DateParser.formatW3CDateTime(published, Locale.US));
            element.addContent((Content) element6);
        }
        List contents = entry.getContents();
        if (Lists.isNotEmpty(contents)) {
            Element element7 = new Element(Param.CONTENT, getFeedNamespace());
            fillContentElement(element7, (com.rometools.rome.feed.atom.Content) contents.get(0));
            element.addContent((Content) element7);
        }
        com.rometools.rome.feed.atom.Content summary = entry.getSummary();
        if (summary != null) {
            Element element8 = new Element("summary", getFeedNamespace());
            fillContentElement(element8, summary);
            element.addContent((Content) element8);
        }
        Feed source = entry.getSource();
        if (source != null) {
            Element element9 = new Element(Param.SOURCE, getFeedNamespace());
            populateFeedHeader(source, element9);
            element.addContent((Content) element9);
        }
        String rights = entry.getRights();
        if (rights != null) {
            element.addContent((Content) generateSimpleElement("rights", rights));
        }
    }

    /* access modifiers changed from: protected */
    public Element generateCategoryElement(Category category) {
        Element element = new Element("category", getFeedNamespace());
        String term = category.getTerm();
        if (term != null) {
            element.setAttribute(new Attribute(Param.TERM, term));
        }
        String label = category.getLabel();
        if (label != null) {
            element.setAttribute(new Attribute("label", label));
        }
        String scheme = category.getScheme();
        if (scheme != null) {
            element.setAttribute(new Attribute("scheme", scheme));
        }
        return element;
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
        String hreflang = link.getHreflang();
        if (hreflang != null) {
            element.setAttribute(new Attribute("hreflang", hreflang));
        }
        String title = link.getTitle();
        if (title != null) {
            element.setAttribute(new Attribute("title", title));
        }
        if (link.getLength() != 0) {
            element.setAttribute(new Attribute("length", Long.toString(link.getLength())));
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
            element.addContent((Content) generateSimpleElement("uri", uri));
        }
        String email = syndPerson.getEmail();
        if (email != null) {
            element.addContent((Content) generateSimpleElement("email", email));
        }
        generatePersonModules(syndPerson.getModules(), element);
    }

    /* access modifiers changed from: protected */
    public Element generateTagLineElement(com.rometools.rome.feed.atom.Content content) {
        Element element = new Element("subtitle", getFeedNamespace());
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
        String str = com.rometools.rome.feed.atom.Content.XHTML;
        if (type != null) {
            if (HTTP.PLAIN_TEXT_TYPE.equals(type)) {
                type = "text";
            } else if ("text/html".equals(type)) {
                type = "html";
            } else if ("application/xhtml+xml".equals(type)) {
                type = str;
            }
            element.setAttribute(new Attribute("type", type));
        }
        String src = content.getSrc();
        if (src != null) {
            element.setAttribute(new Attribute("src", src));
        }
        String value = content.getValue();
        if (value == null) {
            return;
        }
        if (type == null || (!type.equals(str) && type.indexOf("/xml") == -1 && type.indexOf("+xml") == -1)) {
            element.addContent(value);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer("<tmpdoc>");
        stringBuffer.append(value);
        stringBuffer.append("</tmpdoc>");
        try {
            element.addContent((Collection<? extends Content>) new SAXBuilder().build((Reader) new StringReader(stringBuffer.toString())).getRootElement().removeContent());
        } catch (Exception e) {
            throw new FeedException("Invalid XML", e);
        }
    }

    /* access modifiers changed from: protected */
    public Element generateGeneratorElement(Generator generator) {
        Element element = new Element("generator", getFeedNamespace());
        String url = generator.getUrl();
        if (url != null) {
            element.setAttribute(new Attribute("uri", url));
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

    public static void serializeEntry(Entry entry, Writer writer) throws IllegalArgumentException, FeedException, IOException {
        ArrayList arrayList = new ArrayList();
        arrayList.add(entry);
        Feed feed = new Feed();
        feed.setFeedType("atom_1.0");
        feed.setEntries(arrayList);
        new XMLOutputter().output((Element) new WireFeedOutput().outputJDom(feed).getRootElement().getChildren().get(0), writer);
    }
}
