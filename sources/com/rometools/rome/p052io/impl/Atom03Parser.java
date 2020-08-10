package com.rometools.rome.p052io.impl;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.mobiroller.constants.ChatConstants;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Generator;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.atom.Person;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.rome.p052io.FeedException;
import com.rometools.utils.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.protocol.HTTP;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.output.XMLOutputter;

/* renamed from: com.rometools.rome.io.impl.Atom03Parser */
public class Atom03Parser extends BaseWireFeedParser {
    private static final Namespace ATOM_03_NS = Namespace.getNamespace(ATOM_03_URI);
    private static final String ATOM_03_URI = "http://purl.org/atom/ns#";

    /* access modifiers changed from: protected */
    public void validateFeed(Document document) throws FeedException {
    }

    public Atom03Parser() {
        this("atom_0.3", ATOM_03_NS);
    }

    protected Atom03Parser(String str, Namespace namespace) {
        super(str, namespace);
    }

    /* access modifiers changed from: protected */
    public Namespace getAtomNamespace() {
        return ATOM_03_NS;
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
    public WireFeed parseFeed(Element element, Locale locale) {
        String type = getType();
        String styleSheet = getStyleSheet(element.getDocument());
        Feed feed = new Feed(type);
        feed.setStyleSheet(styleSheet);
        Element child = element.getChild("title", getAtomNamespace());
        if (child != null) {
            feed.setTitleEx(parseContent(child));
        }
        List children = element.getChildren("link", getAtomNamespace());
        feed.setAlternateLinks(parseAlternateLinks(children));
        feed.setOtherLinks(parseOtherLinks(children));
        Element child2 = element.getChild("author", getAtomNamespace());
        if (child2 != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(parsePerson(child2));
            feed.setAuthors(arrayList);
        }
        List children2 = element.getChildren("contributor", getAtomNamespace());
        if (!children2.isEmpty()) {
            feed.setContributors(parsePersons(children2));
        }
        Element child3 = element.getChild("tagline", getAtomNamespace());
        if (child3 != null) {
            feed.setTagline(parseContent(child3));
        }
        Element child4 = element.getChild(TtmlNode.ATTR_ID, getAtomNamespace());
        if (child4 != null) {
            feed.setId(child4.getText());
        }
        Element child5 = element.getChild("generator", getAtomNamespace());
        if (child5 != null) {
            Generator generator = new Generator();
            generator.setValue(child5.getText());
            String attributeValue = getAttributeValue(child5, "url");
            if (attributeValue != null) {
                generator.setUrl(attributeValue);
            }
            String attributeValue2 = getAttributeValue(child5, ClientCookie.VERSION_ATTR);
            if (attributeValue2 != null) {
                generator.setVersion(attributeValue2);
            }
            feed.setGenerator(generator);
        }
        Element child6 = element.getChild("copyright", getAtomNamespace());
        if (child6 != null) {
            feed.setCopyright(child6.getText());
        }
        Element child7 = element.getChild(ChatConstants.ARG_USER_INFO, getAtomNamespace());
        if (child7 != null) {
            feed.setInfo(parseContent(child7));
        }
        Element child8 = element.getChild("modified", getAtomNamespace());
        if (child8 != null) {
            feed.setModified(DateParser.parseDate(child8.getText(), locale));
        }
        feed.setModules(parseFeedModules(element, locale));
        List children3 = element.getChildren("entry", getAtomNamespace());
        if (!children3.isEmpty()) {
            feed.setEntries(parseEntries(children3, locale));
        }
        List extractForeignMarkup = extractForeignMarkup(element, feed, getAtomNamespace());
        if (!extractForeignMarkup.isEmpty()) {
            feed.setForeignMarkup(extractForeignMarkup);
        }
        return feed;
    }

    private Link parseLink(Element element) {
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
        }
        return link;
    }

    private List<Link> parseLinks(List<Element> list, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (Element element : list) {
            String attributeValue = getAttributeValue(element, "rel");
            String str = "alternate";
            if (z) {
                if (str.equals(attributeValue)) {
                    arrayList.add(parseLink(element));
                }
            } else if (!str.equals(attributeValue)) {
                arrayList.add(parseLink(element));
            }
        }
        return Lists.emptyToNull(arrayList);
    }

    private List<Link> parseAlternateLinks(List<Element> list) {
        return parseLinks(list, true);
    }

    private List<Link> parseOtherLinks(List<Element> list) {
        return parseLinks(list, false);
    }

    private Person parsePerson(Element element) {
        Person person = new Person();
        Element child = element.getChild(PostalAddressParser.USER_ADDRESS_NAME_KEY, getAtomNamespace());
        if (child != null) {
            person.setName(child.getText());
        }
        Element child2 = element.getChild("url", getAtomNamespace());
        if (child2 != null) {
            person.setUrl(child2.getText());
        }
        Element child3 = element.getChild("email", getAtomNamespace());
        if (child3 != null) {
            person.setEmail(child3.getText());
        }
        return person;
    }

    private List<SyndPerson> parsePersons(List<Element> list) {
        ArrayList arrayList = new ArrayList();
        for (Element parsePerson : list) {
            arrayList.add(parsePerson(parsePerson));
        }
        return Lists.emptyToNull(arrayList);
    }

    private Content parseContent(Element element) {
        String str;
        String attributeValue = getAttributeValue(element, "type");
        if (attributeValue == null) {
            attributeValue = HTTP.PLAIN_TEXT_TYPE;
        }
        String attributeValue2 = getAttributeValue(element, "mode");
        String str2 = "xml";
        if (attributeValue2 == null) {
            attributeValue2 = str2;
        }
        if (attributeValue2.equals(Content.ESCAPED)) {
            str = element.getText();
        } else if (attributeValue2.equals(Content.BASE64)) {
            str = Base64.decode(element.getText());
        } else if (attributeValue2.equals(str2)) {
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
            str = xMLOutputter.outputString(content);
        } else {
            str = null;
        }
        Content content3 = new Content();
        content3.setType(attributeValue);
        content3.setMode(attributeValue2);
        content3.setValue(str);
        return content3;
    }

    private List<Entry> parseEntries(List<Element> list, Locale locale) {
        ArrayList arrayList = new ArrayList();
        for (Element parseEntry : list) {
            arrayList.add(parseEntry(parseEntry, locale));
        }
        return Lists.emptyToNull(arrayList);
    }

    private Entry parseEntry(Element element, Locale locale) {
        Entry entry = new Entry();
        Element child = element.getChild("title", getAtomNamespace());
        if (child != null) {
            entry.setTitleEx(parseContent(child));
        }
        List children = element.getChildren("link", getAtomNamespace());
        entry.setAlternateLinks(parseAlternateLinks(children));
        entry.setOtherLinks(parseOtherLinks(children));
        Element child2 = element.getChild("author", getAtomNamespace());
        if (child2 != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(parsePerson(child2));
            entry.setAuthors(arrayList);
        }
        List children2 = element.getChildren("contributor", getAtomNamespace());
        if (!children2.isEmpty()) {
            entry.setContributors(parsePersons(children2));
        }
        Element child3 = element.getChild(TtmlNode.ATTR_ID, getAtomNamespace());
        if (child3 != null) {
            entry.setId(child3.getText());
        }
        Element child4 = element.getChild("modified", getAtomNamespace());
        if (child4 != null) {
            entry.setModified(DateParser.parseDate(child4.getText(), locale));
        }
        Element child5 = element.getChild("issued", getAtomNamespace());
        if (child5 != null) {
            entry.setIssued(DateParser.parseDate(child5.getText(), locale));
        }
        Element child6 = element.getChild("created", getAtomNamespace());
        if (child6 != null) {
            entry.setCreated(DateParser.parseDate(child6.getText(), locale));
        }
        Element child7 = element.getChild("summary", getAtomNamespace());
        if (child7 != null) {
            entry.setSummary(parseContent(child7));
        }
        List<Element> children3 = element.getChildren(Param.CONTENT, getAtomNamespace());
        if (!children3.isEmpty()) {
            ArrayList arrayList2 = new ArrayList();
            for (Element parseContent : children3) {
                arrayList2.add(parseContent(parseContent));
            }
            entry.setContents(arrayList2);
        }
        entry.setModules(parseItemModules(element, locale));
        List extractForeignMarkup = extractForeignMarkup(element, entry, getAtomNamespace());
        if (!extractForeignMarkup.isEmpty()) {
            entry.setForeignMarkup(extractForeignMarkup);
        }
        return entry;
    }
}
