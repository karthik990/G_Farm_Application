package com.rometools.rome.p052io.impl;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.atom.Category;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Generator;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.atom.Person;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.rome.p052io.FeedException;
import com.rometools.rome.p052io.WireFeedInput;
import com.rometools.rome.p052io.WireFeedOutput;
import com.rometools.utils.Lists;
import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.http.cookie.ClientCookie;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.Parent;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import p043io.fabric.sdk.android.services.settings.SettingsJsonConstants;

/* renamed from: com.rometools.rome.io.impl.Atom10Parser */
public class Atom10Parser extends BaseWireFeedParser {
    private static final Namespace ATOM_10_NS = Namespace.getNamespace(ATOM_10_URI);
    private static final String ATOM_10_URI = "http://www.w3.org/2005/Atom";
    static Pattern absoluteURIPattern = Pattern.compile("^[a-z0-9]*:.*$");
    private static boolean resolveURIs = false;

    /* access modifiers changed from: protected */
    public void validateFeed(Document document) throws FeedException {
    }

    public static void setResolveURIs(boolean z) {
        resolveURIs = z;
    }

    public static boolean getResolveURIs() {
        return resolveURIs;
    }

    public Atom10Parser() {
        this("atom_1.0");
    }

    protected Atom10Parser(String str) {
        super(str, ATOM_10_NS);
    }

    /* access modifiers changed from: protected */
    public Namespace getAtomNamespace() {
        return ATOM_10_NS;
    }

    public boolean isMyType(Document document) {
        Namespace namespace = document.getRootElement().getNamespace();
        return namespace != null && namespace.equals(getAtomNamespace());
    }

    public WireFeed parse(Document document, boolean z, Locale locale) throws IllegalArgumentException, FeedException {
        if (z) {
            validateFeed(document);
        }
        return parseFeed(document.getRootElement(), locale);
    }

    /* access modifiers changed from: protected */
    public WireFeed parseFeed(Element element, Locale locale) throws FeedException {
        try {
            String findBaseURI = findBaseURI(element);
            Feed parseFeedMetadata = parseFeedMetadata(findBaseURI, element, locale);
            parseFeedMetadata.setStyleSheet(getStyleSheet(element.getDocument()));
            String attributeValue = element.getAttributeValue("base", Namespace.XML_NAMESPACE);
            if (attributeValue != null) {
                parseFeedMetadata.setXmlBase(attributeValue);
            }
            parseFeedMetadata.setModules(parseFeedModules(element, locale));
            List children = element.getChildren("entry", getAtomNamespace());
            if (!children.isEmpty()) {
                parseFeedMetadata.setEntries(parseEntries(parseFeedMetadata, findBaseURI, children, locale));
            }
            List extractForeignMarkup = extractForeignMarkup(element, parseFeedMetadata, getAtomNamespace());
            if (!extractForeignMarkup.isEmpty()) {
                parseFeedMetadata.setForeignMarkup(extractForeignMarkup);
            }
            return parseFeedMetadata;
        } catch (Exception e) {
            throw new FeedException("ERROR while finding base URI of feed", e);
        }
    }

    private Feed parseFeedMetadata(String str, Element element, Locale locale) {
        Feed feed = new Feed(getType());
        Element child = element.getChild("title", getAtomNamespace());
        String str2 = "type";
        if (child != null) {
            Content content = new Content();
            content.setValue(parseTextConstructToString(child));
            content.setType(getAttributeValue(child, str2));
            feed.setTitleEx(content);
        }
        List children = element.getChildren("link", getAtomNamespace());
        feed.setAlternateLinks(parseAlternateLinks(feed, null, str, children));
        feed.setOtherLinks(parseOtherLinks(feed, null, str, children));
        feed.setCategories(parseCategories(str, element.getChildren("category", getAtomNamespace())));
        List children2 = element.getChildren("author", getAtomNamespace());
        if (!children2.isEmpty()) {
            feed.setAuthors(parsePersons(str, children2, locale));
        }
        List children3 = element.getChildren("contributor", getAtomNamespace());
        if (!children3.isEmpty()) {
            feed.setContributors(parsePersons(str, children3, locale));
        }
        Element child2 = element.getChild("subtitle", getAtomNamespace());
        if (child2 != null) {
            Content content2 = new Content();
            content2.setValue(parseTextConstructToString(child2));
            content2.setType(getAttributeValue(child2, str2));
            feed.setSubtitle(content2);
        }
        Element child3 = element.getChild(TtmlNode.ATTR_ID, getAtomNamespace());
        if (child3 != null) {
            feed.setId(child3.getText());
        }
        Element child4 = element.getChild("generator", getAtomNamespace());
        if (child4 != null) {
            Generator generator = new Generator();
            generator.setValue(child4.getText());
            String attributeValue = getAttributeValue(child4, "uri");
            if (attributeValue != null) {
                generator.setUrl(attributeValue);
            }
            String attributeValue2 = getAttributeValue(child4, ClientCookie.VERSION_ATTR);
            if (attributeValue2 != null) {
                generator.setVersion(attributeValue2);
            }
            feed.setGenerator(generator);
        }
        Element child5 = element.getChild("rights", getAtomNamespace());
        if (child5 != null) {
            feed.setRights(parseTextConstructToString(child5));
        }
        Element child6 = element.getChild(SettingsJsonConstants.APP_ICON_KEY, getAtomNamespace());
        if (child6 != null) {
            feed.setIcon(child6.getText());
        }
        Element child7 = element.getChild("logo", getAtomNamespace());
        if (child7 != null) {
            feed.setLogo(child7.getText());
        }
        Element child8 = element.getChild("updated", getAtomNamespace());
        if (child8 != null) {
            feed.setUpdated(DateParser.parseDate(child8.getText(), locale));
        }
        return feed;
    }

    private Link parseLink(Feed feed, Entry entry, String str, Element element) {
        Link link = new Link();
        String attributeValue = getAttributeValue(element, "rel");
        if (attributeValue != null) {
            link.setRel(attributeValue);
        }
        String attributeValue2 = getAttributeValue(element, "type");
        if (attributeValue2 != null) {
            link.setType(attributeValue2);
        }
        String attributeValue3 = getAttributeValue(element, "href");
        if (attributeValue3 != null) {
            link.setHref(attributeValue3);
            if (isRelativeURI(attributeValue3)) {
                link.setHrefResolved(resolveURI(str, element, attributeValue3));
            }
        }
        String attributeValue4 = getAttributeValue(element, "title");
        if (attributeValue4 != null) {
            link.setTitle(attributeValue4);
        }
        String attributeValue5 = getAttributeValue(element, "hreflang");
        if (attributeValue5 != null) {
            link.setHreflang(attributeValue5);
        }
        String attributeValue6 = getAttributeValue(element, "length");
        if (attributeValue6 != null) {
            Long parseLong = NumberParser.parseLong(attributeValue6);
            if (parseLong != null) {
                link.setLength(parseLong.longValue());
            }
        }
        return link;
    }

    private List<Link> parseAlternateLinks(Feed feed, Entry entry, String str, List<Element> list) {
        ArrayList arrayList = new ArrayList();
        for (Element parseLink : list) {
            Link parseLink2 = parseLink(feed, entry, str, parseLink);
            if (parseLink2.getRel() != null) {
                if (!"".equals(parseLink2.getRel().trim())) {
                    if (!"alternate".equals(parseLink2.getRel())) {
                    }
                }
            }
            arrayList.add(parseLink2);
        }
        return Lists.emptyToNull(arrayList);
    }

    private List<Link> parseOtherLinks(Feed feed, Entry entry, String str, List<Element> list) {
        ArrayList arrayList = new ArrayList();
        for (Element parseLink : list) {
            Link parseLink2 = parseLink(feed, entry, str, parseLink);
            if (!"alternate".equals(parseLink2.getRel())) {
                arrayList.add(parseLink2);
            }
        }
        return Lists.emptyToNull(arrayList);
    }

    private Person parsePerson(String str, Element element, Locale locale) {
        Person person = new Person();
        Element child = element.getChild(PostalAddressParser.USER_ADDRESS_NAME_KEY, getAtomNamespace());
        if (child != null) {
            person.setName(child.getText());
        }
        Element child2 = element.getChild("uri", getAtomNamespace());
        if (child2 != null) {
            person.setUri(child2.getText());
            if (isRelativeURI(child2.getText())) {
                person.setUriResolved(resolveURI(str, element, child2.getText()));
            }
        }
        Element child3 = element.getChild("email", getAtomNamespace());
        if (child3 != null) {
            person.setEmail(child3.getText());
        }
        person.setModules(parsePersonModules(element, locale));
        return person;
    }

    private List<SyndPerson> parsePersons(String str, List<Element> list, Locale locale) {
        ArrayList arrayList = new ArrayList();
        for (Element parsePerson : list) {
            arrayList.add(parsePerson(str, parsePerson, locale));
        }
        return Lists.emptyToNull(arrayList);
    }

    private Content parseContent(Element element) {
        String parseTextConstructToString = parseTextConstructToString(element);
        String attributeValue = getAttributeValue(element, "src");
        String attributeValue2 = getAttributeValue(element, "type");
        Content content = new Content();
        content.setSrc(attributeValue);
        content.setType(attributeValue2);
        content.setValue(parseTextConstructToString);
        return content;
    }

    private String parseTextConstructToString(Element element) {
        String attributeValue = getAttributeValue(element, "type");
        if (attributeValue == null) {
            attributeValue = "text";
        }
        if (!attributeValue.equals(Content.XHTML) && attributeValue.indexOf("/xml") == -1 && attributeValue.indexOf("+xml") == -1) {
            return element.getText();
        }
        XMLOutputter xMLOutputter = new XMLOutputter();
        List<org.jdom2.Content> content = element.getContent();
        for (org.jdom2.Content content2 : content) {
            if (content2 instanceof Element) {
                Element element2 = (Element) content2;
                if (element2.getNamespace().equals(getAtomNamespace())) {
                    element2.setNamespace(Namespace.NO_NAMESPACE);
                }
            }
        }
        return xMLOutputter.outputString(content);
    }

    /* access modifiers changed from: protected */
    public List<Entry> parseEntries(Feed feed, String str, List<Element> list, Locale locale) {
        ArrayList arrayList = new ArrayList();
        for (Element parseEntry : list) {
            arrayList.add(parseEntry(feed, parseEntry, str, locale));
        }
        return Lists.emptyToNull(arrayList);
    }

    /* access modifiers changed from: protected */
    public Entry parseEntry(Feed feed, Element element, String str, Locale locale) {
        Entry entry = new Entry();
        String attributeValue = element.getAttributeValue("base", Namespace.XML_NAMESPACE);
        if (attributeValue != null) {
            entry.setXmlBase(attributeValue);
        }
        Element child = element.getChild("title", getAtomNamespace());
        if (child != null) {
            Content content = new Content();
            content.setValue(parseTextConstructToString(child));
            content.setType(getAttributeValue(child, "type"));
            entry.setTitleEx(content);
        }
        List children = element.getChildren("link", getAtomNamespace());
        entry.setAlternateLinks(parseAlternateLinks(feed, entry, str, children));
        entry.setOtherLinks(parseOtherLinks(feed, entry, str, children));
        List children2 = element.getChildren("author", getAtomNamespace());
        if (!children2.isEmpty()) {
            entry.setAuthors(parsePersons(str, children2, locale));
        }
        List children3 = element.getChildren("contributor", getAtomNamespace());
        if (!children3.isEmpty()) {
            entry.setContributors(parsePersons(str, children3, locale));
        }
        Element child2 = element.getChild(TtmlNode.ATTR_ID, getAtomNamespace());
        if (child2 != null) {
            entry.setId(child2.getText());
        }
        Element child3 = element.getChild("updated", getAtomNamespace());
        if (child3 != null) {
            entry.setUpdated(DateParser.parseDate(child3.getText(), locale));
        }
        Element child4 = element.getChild("published", getAtomNamespace());
        if (child4 != null) {
            entry.setPublished(DateParser.parseDate(child4.getText(), locale));
        }
        Element child5 = element.getChild("summary", getAtomNamespace());
        if (child5 != null) {
            entry.setSummary(parseContent(child5));
        }
        Element child6 = element.getChild(Param.CONTENT, getAtomNamespace());
        if (child6 != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(parseContent(child6));
            entry.setContents(arrayList);
        }
        Element child7 = element.getChild("rights", getAtomNamespace());
        if (child7 != null) {
            entry.setRights(child7.getText());
        }
        entry.setCategories(parseCategories(str, element.getChildren("category", getAtomNamespace())));
        Element child8 = element.getChild(Param.SOURCE, getAtomNamespace());
        if (child8 != null) {
            entry.setSource(parseFeedMetadata(str, child8, locale));
        }
        entry.setModules(parseItemModules(element, locale));
        List extractForeignMarkup = extractForeignMarkup(element, entry, getAtomNamespace());
        if (!extractForeignMarkup.isEmpty()) {
            entry.setForeignMarkup(extractForeignMarkup);
        }
        return entry;
    }

    private List<Category> parseCategories(String str, List<Element> list) {
        ArrayList arrayList = new ArrayList();
        for (Element parseCategory : list) {
            arrayList.add(parseCategory(str, parseCategory));
        }
        return Lists.emptyToNull(arrayList);
    }

    private Category parseCategory(String str, Element element) {
        Category category = new Category();
        String attributeValue = getAttributeValue(element, Param.TERM);
        if (attributeValue != null) {
            category.setTerm(attributeValue);
        }
        String attributeValue2 = getAttributeValue(element, "scheme");
        if (attributeValue2 != null) {
            category.setScheme(attributeValue2);
            if (isRelativeURI(attributeValue2)) {
                category.setSchemeResolved(resolveURI(str, element, attributeValue2));
            }
        }
        String attributeValue3 = getAttributeValue(element, "label");
        if (attributeValue3 != null) {
            category.setLabel(attributeValue3);
        }
        return category;
    }

    public static boolean isAbsoluteURI(String str) {
        return absoluteURIPattern.matcher(str).find();
    }

    public static boolean isRelativeURI(String str) {
        return !isAbsoluteURI(str);
    }

    public static String resolveURI(String str, Parent parent, String str2) {
        if (!resolveURIs) {
            return str2;
        }
        if (isRelativeURI(str2)) {
            if (".".equals(str2) || "./".equals(str2)) {
                str2 = "";
            }
            String str3 = "/";
            String str4 = "//";
            if (str2.startsWith(str3) && str != null) {
                String str5 = null;
                int indexOf = str.indexOf(str3, str.indexOf(str4) + 2);
                if (indexOf != -1) {
                    str5 = str.substring(0, indexOf);
                }
                return formURI(str5, str2);
            } else if (parent != null && (parent instanceof Element)) {
                String attributeValue = ((Element) parent).getAttributeValue("base", Namespace.XML_NAMESPACE);
                if (attributeValue == null || attributeValue.trim().length() <= 0) {
                    return resolveURI(str, parent.getParent(), str2);
                }
                if (!isAbsoluteURI(attributeValue)) {
                    Parent parent2 = parent.getParent();
                    StringBuilder sb = new StringBuilder();
                    sb.append(stripTrailingSlash(attributeValue));
                    sb.append(str3);
                    sb.append(stripStartingSlash(str2));
                    return resolveURI(str, parent2, sb.toString());
                } else if (str2.startsWith(str3)) {
                    int indexOf2 = attributeValue.indexOf(str3, attributeValue.indexOf(str4) + 2);
                    if (indexOf2 != -1) {
                        attributeValue = attributeValue.substring(0, indexOf2);
                    }
                    return formURI(attributeValue, str2);
                } else {
                    if (!attributeValue.endsWith(str3)) {
                        attributeValue = attributeValue.substring(0, attributeValue.lastIndexOf(str3));
                    }
                    return formURI(attributeValue, str2);
                }
            } else if (parent == null || (parent instanceof Document)) {
                return formURI(str, str2);
            }
        }
        return str2;
    }

    private String findBaseURI(Element element) throws MalformedURLException {
        String str = "self";
        if (findAtomLink(element, str) == null) {
            return null;
        }
        String findAtomLink = findAtomLink(element, str);
        if (".".equals(findAtomLink) || "./".equals(findAtomLink)) {
            findAtomLink = "";
        }
        String str2 = "/";
        if (findAtomLink.indexOf(str2) != -1) {
            findAtomLink = findAtomLink.substring(0, findAtomLink.lastIndexOf(str2));
        }
        return resolveURI(null, element, findAtomLink);
    }

    private String findAtomLink(Element element, String str) {
        List<Element> children = element.getChildren("link", ATOM_10_NS);
        if (children != null) {
            for (Element element2 : children) {
                Attribute attribute = getAttribute(element2, "rel");
                Attribute attribute2 = getAttribute(element2, "href");
                if ((attribute == null && "alternate".equals(str)) || (attribute != null && attribute.getValue().equals(str))) {
                    return attribute2.getValue();
                }
            }
        }
        return null;
    }

    private static String formURI(String str, String str2) {
        String stripTrailingSlash = stripTrailingSlash(str);
        String stripStartingSlash = stripStartingSlash(str2);
        String str3 = "..";
        String str4 = "/";
        if (stripStartingSlash.startsWith(str3)) {
            String str5 = stripStartingSlash;
            String str6 = stripTrailingSlash;
            for (String equals : stripStartingSlash.split(str4)) {
                if (str3.equals(equals)) {
                    int lastIndexOf = str6.lastIndexOf(str4);
                    if (lastIndexOf == -1) {
                        break;
                    }
                    str6 = str6.substring(0, lastIndexOf);
                    str5 = str5.substring(3, str5.length());
                }
            }
            stripTrailingSlash = str6;
            stripStartingSlash = str5;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(stripTrailingSlash);
        sb.append(str4);
        sb.append(stripStartingSlash);
        return sb.toString();
    }

    private static String stripStartingSlash(String str) {
        return (str == null || !str.startsWith("/")) ? str : str.substring(1, str.length());
    }

    private static String stripTrailingSlash(String str) {
        return (str == null || !str.endsWith("/")) ? str : str.substring(0, str.length() - 1);
    }

    public static Entry parseEntry(Reader reader, String str, Locale locale) throws JDOMException, IOException, IllegalArgumentException, FeedException {
        Element rootElement = new SAXBuilder().build(reader).getRootElement();
        rootElement.detach();
        Feed feed = new Feed();
        feed.setFeedType("atom_1.0");
        Document outputJDom = new WireFeedOutput().outputJDom(feed);
        outputJDom.getRootElement().addContent((org.jdom2.Content) rootElement);
        if (str != null) {
            outputJDom.getRootElement().setAttribute("base", str, Namespace.XML_NAMESPACE);
        }
        return (Entry) ((Feed) new WireFeedInput(false, locale).build(outputJDom)).getEntries().get(0);
    }
}
