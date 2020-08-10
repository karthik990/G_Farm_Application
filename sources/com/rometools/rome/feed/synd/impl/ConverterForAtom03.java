package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.atom.Person;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.rome.feed.synd.Converter;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEnclosureImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImageImpl;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndLinkImpl;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.rome.feed.synd.SyndPersonImpl;
import com.rometools.utils.Alternatives;
import com.rometools.utils.Lists;
import com.rometools.utils.Strings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConverterForAtom03 implements Converter {
    private final String type;

    public ConverterForAtom03() {
        this("atom_0.3");
    }

    protected ConverterForAtom03(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public void copyInto(WireFeed wireFeed, SyndFeed syndFeed) {
        Feed feed = (Feed) wireFeed;
        syndFeed.setModules(ModuleUtils.cloneModules(feed.getModules()));
        List foreignMarkup = wireFeed.getForeignMarkup();
        if (Lists.isNotEmpty(foreignMarkup)) {
            syndFeed.setForeignMarkup(foreignMarkup);
        }
        syndFeed.setEncoding(feed.getEncoding());
        syndFeed.setStyleSheet(feed.getStyleSheet());
        String logo = feed.getLogo();
        String icon = feed.getIcon();
        if (logo != null) {
            SyndImageImpl syndImageImpl = new SyndImageImpl();
            syndImageImpl.setUrl(logo);
            syndFeed.setImage(syndImageImpl);
        } else if (icon != null) {
            SyndImageImpl syndImageImpl2 = new SyndImageImpl();
            syndImageImpl2.setUrl(icon);
            syndFeed.setImage(syndImageImpl2);
        }
        syndFeed.setUri(feed.getId());
        syndFeed.setTitle(feed.getTitle());
        List alternateLinks = feed.getAlternateLinks();
        if (Lists.isNotEmpty(alternateLinks)) {
            syndFeed.setLink(((Link) alternateLinks.get(0)).getHrefResolved());
        }
        ArrayList arrayList = new ArrayList();
        if (Lists.isNotEmpty(alternateLinks)) {
            arrayList.addAll(createSyndLinks(alternateLinks));
        }
        List otherLinks = feed.getOtherLinks();
        if (Lists.isNotEmpty(otherLinks)) {
            arrayList.addAll(createSyndLinks(otherLinks));
        }
        syndFeed.setLinks(arrayList);
        Content tagline = feed.getTagline();
        if (tagline != null) {
            syndFeed.setDescription(tagline.getValue());
        }
        List entries = feed.getEntries();
        if (Lists.isNotEmpty(entries)) {
            syndFeed.setEntries(createSyndEntries(entries, syndFeed.isPreservingWireFeed()));
        }
        String language = feed.getLanguage();
        if (language != null) {
            syndFeed.setLanguage(language);
        }
        List authors = feed.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            syndFeed.setAuthors(createSyndPersons(authors));
        }
        String copyright = feed.getCopyright();
        if (copyright != null) {
            syndFeed.setCopyright(copyright);
        }
        Date modified = feed.getModified();
        if (modified != null) {
            syndFeed.setPublishedDate(modified);
        }
    }

    /* access modifiers changed from: protected */
    public List<SyndLink> createSyndLinks(List<Link> list) {
        ArrayList arrayList = new ArrayList();
        for (Link link : list) {
            if (!link.getRel().equals("enclosure")) {
                arrayList.add(createSyndLink(link));
            }
        }
        return arrayList;
    }

    public SyndLink createSyndLink(Link link) {
        SyndLinkImpl syndLinkImpl = new SyndLinkImpl();
        syndLinkImpl.setRel(link.getRel());
        syndLinkImpl.setType(link.getType());
        syndLinkImpl.setHref(link.getHrefResolved());
        syndLinkImpl.setTitle(link.getTitle());
        return syndLinkImpl;
    }

    /* access modifiers changed from: protected */
    public List<SyndEntry> createSyndEntries(List<Entry> list, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (Entry createSyndEntry : list) {
            arrayList.add(createSyndEntry(createSyndEntry, z));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public SyndEntry createSyndEntry(Entry entry, boolean z) {
        SyndEntryImpl syndEntryImpl = new SyndEntryImpl();
        if (z) {
            syndEntryImpl.setWireEntry(entry);
        }
        syndEntryImpl.setModules(ModuleUtils.cloneModules(entry.getModules()));
        List foreignMarkup = entry.getForeignMarkup();
        if (Lists.isNotEmpty(foreignMarkup)) {
            syndEntryImpl.setForeignMarkup(foreignMarkup);
        }
        syndEntryImpl.setTitle(entry.getTitle());
        List alternateLinks = entry.getAlternateLinks();
        if (Lists.sizeIs(alternateLinks, 1)) {
            syndEntryImpl.setLink(((Link) alternateLinks.get(0)).getHrefResolved());
        }
        ArrayList arrayList = new ArrayList();
        List<Link> otherLinks = entry.getOtherLinks();
        if (Lists.isNotEmpty(otherLinks)) {
            for (Link link : otherLinks) {
                if ("enclosure".equals(link.getRel())) {
                    arrayList.add(createSyndEnclosure(entry, link));
                }
            }
        }
        syndEntryImpl.setEnclosures(arrayList);
        ArrayList arrayList2 = new ArrayList();
        if (Lists.isNotEmpty(alternateLinks)) {
            arrayList2.addAll(createSyndLinks(alternateLinks));
        }
        if (Lists.isNotEmpty(otherLinks)) {
            arrayList2.addAll(createSyndLinks(otherLinks));
        }
        syndEntryImpl.setLinks(arrayList2);
        String id = entry.getId();
        if (id != null) {
            syndEntryImpl.setUri(id);
        } else {
            syndEntryImpl.setUri(syndEntryImpl.getLink());
        }
        Content summary = entry.getSummary();
        if (summary == null) {
            List contents = entry.getContents();
            if (Lists.isNotEmpty(contents)) {
                Content content = (Content) contents.get(0);
            }
        } else {
            SyndContentImpl syndContentImpl = new SyndContentImpl();
            syndContentImpl.setType(summary.getType());
            syndContentImpl.setValue(summary.getValue());
            syndEntryImpl.setDescription(syndContentImpl);
        }
        List<Content> contents2 = entry.getContents();
        if (Lists.isNotEmpty(contents2)) {
            ArrayList arrayList3 = new ArrayList();
            for (Content content2 : contents2) {
                SyndContentImpl syndContentImpl2 = new SyndContentImpl();
                syndContentImpl2.setType(content2.getType());
                syndContentImpl2.setValue(content2.getValue());
                syndContentImpl2.setMode(content2.getMode());
                arrayList3.add(syndContentImpl2);
            }
            syndEntryImpl.setContents(arrayList3);
        }
        List authors = entry.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            syndEntryImpl.setAuthors(createSyndPersons(authors));
            syndEntryImpl.setAuthor(((SyndPerson) syndEntryImpl.getAuthors().get(0)).getName());
        }
        Date modified = entry.getModified();
        if (modified == null) {
            modified = (Date) Alternatives.firstNotNull(entry.getIssued(), entry.getCreated());
        }
        if (modified != null) {
            syndEntryImpl.setPublishedDate(modified);
        }
        return syndEntryImpl;
    }

    public SyndEnclosure createSyndEnclosure(Entry entry, Link link) {
        SyndEnclosureImpl syndEnclosureImpl = new SyndEnclosureImpl();
        syndEnclosureImpl.setUrl(link.getHrefResolved());
        syndEnclosureImpl.setType(link.getType());
        syndEnclosureImpl.setLength(link.getLength());
        return syndEnclosureImpl;
    }

    public WireFeed createRealFeed(SyndFeed syndFeed) {
        Feed feed = new Feed(getType());
        feed.setModules(ModuleUtils.cloneModules(syndFeed.getModules()));
        feed.setEncoding(syndFeed.getEncoding());
        feed.setStyleSheet(syndFeed.getStyleSheet());
        feed.setId(syndFeed.getUri());
        SyndContent titleEx = syndFeed.getTitleEx();
        if (titleEx != null) {
            Content content = new Content();
            String type2 = titleEx.getType();
            if (type2 != null) {
                content.setType(type2);
            }
            String mode = titleEx.getMode();
            if (mode != null) {
                content.setMode(mode);
            }
            content.setValue(titleEx.getValue());
            feed.setTitleEx(content);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<SyndLink> links = syndFeed.getLinks();
        String str = "alternate";
        if (links != null) {
            for (SyndLink createAtomLink : links) {
                Link createAtomLink2 = createAtomLink(createAtomLink);
                String rel = createAtomLink2.getRel();
                if (Strings.isBlank(rel) || str.equals(rel)) {
                    arrayList.add(createAtomLink2);
                } else {
                    arrayList2.add(createAtomLink2);
                }
            }
        }
        String link = syndFeed.getLink();
        if (arrayList.isEmpty() && link != null) {
            Link link2 = new Link();
            link2.setRel(str);
            link2.setHref(link);
            arrayList.add(link2);
        }
        if (!arrayList.isEmpty()) {
            feed.setAlternateLinks(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            feed.setOtherLinks(arrayList2);
        }
        String description = syndFeed.getDescription();
        if (description != null) {
            Content content2 = new Content();
            content2.setValue(description);
            feed.setTagline(content2);
        }
        feed.setLanguage(syndFeed.getLanguage());
        List authors = syndFeed.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            feed.setAuthors(createAtomPersons(authors));
        }
        feed.setCopyright(syndFeed.getCopyright());
        feed.setModified(syndFeed.getPublishedDate());
        List entries = syndFeed.getEntries();
        if (entries != null) {
            feed.setEntries(createAtomEntries(entries));
        }
        return feed;
    }

    protected static List<SyndPerson> createAtomPersons(List<SyndPerson> list) {
        ArrayList arrayList = new ArrayList();
        for (SyndPerson syndPerson : list) {
            Person person = new Person();
            person.setName(syndPerson.getName());
            person.setUri(syndPerson.getUri());
            person.setEmail(syndPerson.getEmail());
            person.setModules(syndPerson.getModules());
            arrayList.add(person);
        }
        return arrayList;
    }

    protected static List<SyndPerson> createSyndPersons(List<SyndPerson> list) {
        ArrayList arrayList = new ArrayList();
        for (SyndPerson syndPerson : list) {
            SyndPersonImpl syndPersonImpl = new SyndPersonImpl();
            syndPersonImpl.setName(syndPerson.getName());
            syndPersonImpl.setUri(syndPerson.getUri());
            syndPersonImpl.setEmail(syndPerson.getEmail());
            syndPersonImpl.setModules(syndPerson.getModules());
            arrayList.add(syndPersonImpl);
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public List<Entry> createAtomEntries(List<SyndEntry> list) {
        ArrayList arrayList = new ArrayList();
        for (SyndEntry createAtomEntry : list) {
            arrayList.add(createAtomEntry(createAtomEntry));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Entry createAtomEntry(SyndEntry syndEntry) {
        Entry entry = new Entry();
        entry.setModules(ModuleUtils.cloneModules(syndEntry.getModules()));
        entry.setId(syndEntry.getUri());
        SyndContent titleEx = syndEntry.getTitleEx();
        if (titleEx != null) {
            Content content = new Content();
            String type2 = titleEx.getType();
            if (type2 != null) {
                content.setType(type2);
            }
            String mode = titleEx.getMode();
            if (mode != null) {
                content.setMode(mode);
            }
            content.setValue(titleEx.getValue());
            entry.setTitleEx(content);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List<SyndLink> links = syndEntry.getLinks();
        String str = "alternate";
        if (links != null) {
            for (SyndLink createAtomLink : links) {
                Link createAtomLink2 = createAtomLink(createAtomLink);
                String rel = createAtomLink2.getRel();
                if (Strings.isBlank(rel) || str.equals(rel)) {
                    arrayList.add(createAtomLink2);
                } else {
                    arrayList2.add(createAtomLink2);
                }
            }
        }
        String link = syndEntry.getLink();
        if (arrayList.isEmpty() && link != null) {
            Link link2 = new Link();
            link2.setRel(str);
            link2.setHref(link);
            arrayList.add(link2);
        }
        List<SyndEnclosure> enclosures = syndEntry.getEnclosures();
        if (enclosures != null) {
            for (SyndEnclosure createAtomEnclosure : enclosures) {
                arrayList2.add(createAtomEnclosure(createAtomEnclosure));
            }
        }
        if (!arrayList.isEmpty()) {
            entry.setAlternateLinks(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            entry.setOtherLinks(arrayList2);
        }
        SyndContent description = syndEntry.getDescription();
        if (description != null) {
            Content content2 = new Content();
            content2.setType(description.getType());
            content2.setValue(description.getValue());
            content2.setMode(Content.ESCAPED);
            entry.setSummary(content2);
        }
        List<SyndContent> contents = syndEntry.getContents();
        if (!contents.isEmpty()) {
            ArrayList arrayList3 = new ArrayList();
            for (SyndContent syndContent : contents) {
                Content content3 = new Content();
                content3.setType(syndContent.getType());
                content3.setValue(syndContent.getValue());
                content3.setMode(syndContent.getMode());
                arrayList3.add(content3);
            }
            entry.setContents(arrayList3);
        }
        List authors = syndEntry.getAuthors();
        String author = syndEntry.getAuthor();
        if (Lists.isNotEmpty(authors)) {
            entry.setAuthors(createAtomPersons(authors));
        } else if (author != null) {
            Person person = new Person();
            person.setName(author);
            ArrayList arrayList4 = new ArrayList();
            arrayList4.add(person);
            entry.setAuthors(arrayList4);
        }
        entry.setModified(syndEntry.getPublishedDate());
        entry.setIssued(syndEntry.getPublishedDate());
        return entry;
    }

    public Link createAtomLink(SyndLink syndLink) {
        Link link = new Link();
        link.setRel(syndLink.getRel());
        link.setType(syndLink.getType());
        link.setHref(syndLink.getHref());
        link.setTitle(syndLink.getTitle());
        return link;
    }

    public Link createAtomEnclosure(SyndEnclosure syndEnclosure) {
        Link link = new Link();
        link.setRel("enclosure");
        link.setType(syndEnclosure.getType());
        link.setHref(syndEnclosure.getUrl());
        link.setLength(syndEnclosure.getLength());
        return link;
    }
}
