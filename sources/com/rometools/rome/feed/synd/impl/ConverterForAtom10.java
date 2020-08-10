package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.atom.Category;
import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.atom.Person;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.rome.feed.synd.Converter;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndCategoryImpl;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEnclosureImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.feed.synd.SyndImageImpl;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndLinkImpl;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.utils.Lists;
import com.rometools.utils.Strings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConverterForAtom10 implements Converter {
    private final String type;

    public ConverterForAtom10() {
        this("atom_1.0");
    }

    protected ConverterForAtom10(String str) {
        this.type = str;
    }

    public String getType() {
        return this.type;
    }

    public void copyInto(WireFeed wireFeed, SyndFeed syndFeed) {
        Feed feed = (Feed) wireFeed;
        syndFeed.setModules(ModuleUtils.cloneModules(feed.getModules()));
        List foreignMarkup = wireFeed.getForeignMarkup();
        if (!foreignMarkup.isEmpty()) {
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
        Content titleEx = feed.getTitleEx();
        if (titleEx != null) {
            SyndContentImpl syndContentImpl = new SyndContentImpl();
            syndContentImpl.setType(titleEx.getType());
            syndContentImpl.setValue(titleEx.getValue());
            syndFeed.setTitleEx(syndContentImpl);
        }
        Content subtitle = feed.getSubtitle();
        if (subtitle != null) {
            SyndContentImpl syndContentImpl2 = new SyndContentImpl();
            syndContentImpl2.setType(subtitle.getType());
            syndContentImpl2.setValue(subtitle.getValue());
            syndFeed.setDescriptionEx(syndContentImpl2);
        }
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
        List entries = feed.getEntries();
        if (entries != null) {
            syndFeed.setEntries(createSyndEntries(feed, entries, syndFeed.isPreservingWireFeed()));
        }
        List authors = feed.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            syndFeed.setAuthors(ConverterForAtom03.createSyndPersons(authors));
        }
        List contributors = feed.getContributors();
        if (Lists.isNotEmpty(contributors)) {
            syndFeed.setContributors(ConverterForAtom03.createSyndPersons(contributors));
        }
        String rights = feed.getRights();
        if (rights != null) {
            syndFeed.setCopyright(rights);
        }
        Date updated = feed.getUpdated();
        if (updated != null) {
            syndFeed.setPublishedDate(updated);
        }
    }

    /* access modifiers changed from: protected */
    public List<SyndLink> createSyndLinks(List<Link> list) {
        ArrayList arrayList = new ArrayList();
        for (Link createSyndLink : list) {
            arrayList.add(createSyndLink(createSyndLink));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public List<SyndEntry> createSyndEntries(Feed feed, List<Entry> list, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (Entry createSyndEntry : list) {
            arrayList.add(createSyndEntry(feed, createSyndEntry, z));
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public SyndEntry createSyndEntry(Feed feed, Entry entry, boolean z) {
        SyndEntryImpl syndEntryImpl = new SyndEntryImpl();
        if (z) {
            syndEntryImpl.setWireEntry(entry);
        }
        syndEntryImpl.setModules(ModuleUtils.cloneModules(entry.getModules()));
        List foreignMarkup = entry.getForeignMarkup();
        if (!foreignMarkup.isEmpty()) {
            syndEntryImpl.setForeignMarkup(foreignMarkup);
        }
        Content titleEx = entry.getTitleEx();
        if (titleEx != null) {
            syndEntryImpl.setTitleEx(createSyndContent(titleEx));
        }
        Content summary = entry.getSummary();
        if (summary != null) {
            syndEntryImpl.setDescription(createSyndContent(summary));
        }
        List<Content> contents = entry.getContents();
        if (Lists.isNotEmpty(contents)) {
            ArrayList arrayList = new ArrayList();
            for (Content createSyndContent : contents) {
                arrayList.add(createSyndContent(createSyndContent));
            }
            syndEntryImpl.setContents(arrayList);
        }
        List authors = entry.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            syndEntryImpl.setAuthors(ConverterForAtom03.createSyndPersons(authors));
            syndEntryImpl.setAuthor(((SyndPerson) syndEntryImpl.getAuthors().get(0)).getName());
        }
        List contributors = entry.getContributors();
        if (Lists.isNotEmpty(contributors)) {
            syndEntryImpl.setContributors(ConverterForAtom03.createSyndPersons(contributors));
        }
        Date published = entry.getPublished();
        if (published != null) {
            syndEntryImpl.setPublishedDate(published);
        }
        Date updated = entry.getUpdated();
        if (updated != null) {
            syndEntryImpl.setUpdatedDate(updated);
        }
        List<Category> categories = entry.getCategories();
        if (categories != null) {
            ArrayList arrayList2 = new ArrayList();
            for (Category category : categories) {
                SyndCategoryImpl syndCategoryImpl = new SyndCategoryImpl();
                syndCategoryImpl.setName(category.getTerm());
                syndCategoryImpl.setTaxonomyUri(category.getSchemeResolved());
                arrayList2.add(syndCategoryImpl);
            }
            syndEntryImpl.setCategories(arrayList2);
        }
        List alternateLinks = entry.getAlternateLinks();
        if (Lists.isNotEmpty(alternateLinks)) {
            syndEntryImpl.setLink(((Link) alternateLinks.get(0)).getHrefResolved());
        }
        ArrayList arrayList3 = new ArrayList();
        List<Link> otherLinks = entry.getOtherLinks();
        if (Lists.isNotEmpty(otherLinks)) {
            for (Link link : otherLinks) {
                if ("enclosure".equals(link.getRel())) {
                    arrayList3.add(createSyndEnclosure(feed, entry, link));
                }
            }
        }
        syndEntryImpl.setEnclosures(arrayList3);
        ArrayList arrayList4 = new ArrayList();
        if (Lists.isNotEmpty(alternateLinks)) {
            arrayList4.addAll(createSyndLinks(alternateLinks));
        }
        if (Lists.isNotEmpty(otherLinks)) {
            arrayList4.addAll(createSyndLinks(otherLinks));
        }
        syndEntryImpl.setLinks(arrayList4);
        if (entry.getId() != null) {
            syndEntryImpl.setUri(entry.getId());
        } else {
            syndEntryImpl.setUri(syndEntryImpl.getLink());
        }
        Feed source = entry.getSource();
        if (source != null) {
            syndEntryImpl.setSource(new SyndFeedImpl(source));
        }
        return syndEntryImpl;
    }

    public SyndEnclosure createSyndEnclosure(Feed feed, Entry entry, Link link) {
        SyndEnclosureImpl syndEnclosureImpl = new SyndEnclosureImpl();
        syndEnclosureImpl.setUrl(link.getHrefResolved());
        syndEnclosureImpl.setType(link.getType());
        syndEnclosureImpl.setLength(link.getLength());
        return syndEnclosureImpl;
    }

    public Link createAtomEnclosure(SyndEnclosure syndEnclosure) {
        Link link = new Link();
        link.setRel("enclosure");
        link.setType(syndEnclosure.getType());
        link.setHref(syndEnclosure.getUrl());
        link.setLength(syndEnclosure.getLength());
        return link;
    }

    public SyndLink createSyndLink(Link link) {
        SyndLinkImpl syndLinkImpl = new SyndLinkImpl();
        syndLinkImpl.setRel(link.getRel());
        syndLinkImpl.setType(link.getType());
        syndLinkImpl.setHref(link.getHrefResolved());
        syndLinkImpl.setHreflang(link.getHreflang());
        syndLinkImpl.setLength(link.getLength());
        syndLinkImpl.setTitle(link.getTitle());
        return syndLinkImpl;
    }

    public Link createAtomLink(SyndLink syndLink) {
        Link link = new Link();
        link.setRel(syndLink.getRel());
        link.setType(syndLink.getType());
        link.setHref(syndLink.getHref());
        link.setHreflang(syndLink.getHreflang());
        link.setLength(syndLink.getLength());
        link.setTitle(syndLink.getTitle());
        return link;
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
            content.setType(titleEx.getType());
            content.setValue(titleEx.getValue());
            feed.setTitleEx(content);
        }
        SyndContent descriptionEx = syndFeed.getDescriptionEx();
        if (descriptionEx != null) {
            Content content2 = new Content();
            content2.setType(descriptionEx.getType());
            content2.setValue(descriptionEx.getValue());
            feed.setSubtitle(content2);
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
        if (arrayList.isEmpty() && syndFeed.getLink() != null) {
            Link link = new Link();
            link.setRel(str);
            link.setHref(syndFeed.getLink());
            arrayList.add(link);
        }
        if (!arrayList.isEmpty()) {
            feed.setAlternateLinks(arrayList);
        }
        if (!arrayList2.isEmpty()) {
            feed.setOtherLinks(arrayList2);
        }
        List<SyndCategory> categories = syndFeed.getCategories();
        ArrayList arrayList3 = new ArrayList();
        if (categories != null) {
            for (SyndCategory syndCategory : categories) {
                Category category = new Category();
                category.setTerm(syndCategory.getName());
                category.setScheme(syndCategory.getTaxonomyUri());
                arrayList3.add(category);
            }
        }
        if (!arrayList3.isEmpty()) {
            feed.setCategories(arrayList3);
        }
        List authors = syndFeed.getAuthors();
        if (Lists.isNotEmpty(authors)) {
            feed.setAuthors(ConverterForAtom03.createAtomPersons(authors));
        }
        List contributors = syndFeed.getContributors();
        if (Lists.isNotEmpty(contributors)) {
            feed.setContributors(ConverterForAtom03.createAtomPersons(contributors));
        }
        feed.setRights(syndFeed.getCopyright());
        feed.setUpdated(syndFeed.getPublishedDate());
        List entries = syndFeed.getEntries();
        if (entries != null) {
            feed.setEntries(createAtomEntries(entries));
        }
        List foreignMarkup = syndFeed.getForeignMarkup();
        if (!foreignMarkup.isEmpty()) {
            feed.setForeignMarkup(foreignMarkup);
        }
        return feed;
    }

    /* access modifiers changed from: protected */
    public SyndContent createSyndContent(Content content) {
        SyndContentImpl syndContentImpl = new SyndContentImpl();
        syndContentImpl.setType(content.getType());
        syndContentImpl.setValue(content.getValue());
        return syndContentImpl;
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
    public Content createAtomContent(SyndContent syndContent) {
        Content content = new Content();
        content.setType(syndContent.getType());
        content.setValue(syndContent.getValue());
        return content;
    }

    /* access modifiers changed from: protected */
    public List<Content> createAtomContents(List<SyndContent> list) {
        ArrayList arrayList = new ArrayList();
        for (SyndContent createAtomContent : list) {
            arrayList.add(createAtomContent(createAtomContent));
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
            content.setType(titleEx.getType());
            content.setValue(titleEx.getValue());
            entry.setTitleEx(content);
        }
        SyndContent description = syndEntry.getDescription();
        if (description != null) {
            Content content2 = new Content();
            content2.setType(description.getType());
            content2.setValue(description.getValue());
            entry.setSummary(content2);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean z = false;
        List<SyndLink> links = syndEntry.getLinks();
        List<SyndEnclosure> enclosures = syndEntry.getEnclosures();
        String str = "alternate";
        if (links != null) {
            for (SyndLink syndLink : links) {
                Link createAtomLink = createAtomLink(syndLink);
                String rel = syndLink.getRel();
                if (rel != null && "enclosure".equals(rel)) {
                    z = true;
                }
                if (Strings.isBlank(createAtomLink.getRel()) || str.equals(rel)) {
                    arrayList.add(createAtomLink);
                } else {
                    arrayList2.add(createAtomLink);
                }
            }
        }
        if (arrayList.isEmpty() && syndEntry.getLink() != null) {
            Link link = new Link();
            link.setRel(str);
            link.setHref(syndEntry.getLink());
            arrayList.add(link);
        }
        if (enclosures != null && !z) {
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
        List<SyndCategory> categories = syndEntry.getCategories();
        ArrayList arrayList3 = new ArrayList();
        if (categories != null) {
            for (SyndCategory syndCategory : categories) {
                Category category = new Category();
                category.setTerm(syndCategory.getName());
                category.setScheme(syndCategory.getTaxonomyUri());
                arrayList3.add(category);
            }
        }
        if (!arrayList3.isEmpty()) {
            entry.setCategories(arrayList3);
        }
        entry.setContents(createAtomContents(syndEntry.getContents()));
        List authors = syndEntry.getAuthors();
        String author = syndEntry.getAuthor();
        if (Lists.isNotEmpty(authors)) {
            entry.setAuthors(ConverterForAtom03.createAtomPersons(authors));
        } else if (author != null) {
            Person person = new Person();
            person.setName(author);
            ArrayList arrayList4 = new ArrayList();
            arrayList4.add(person);
            entry.setAuthors(arrayList4);
        }
        List contributors = syndEntry.getContributors();
        if (Lists.isNotEmpty(contributors)) {
            entry.setContributors(ConverterForAtom03.createAtomPersons(contributors));
        }
        entry.setPublished(syndEntry.getPublishedDate());
        if (syndEntry.getUpdatedDate() != null) {
            entry.setUpdated(syndEntry.getUpdatedDate());
        } else {
            entry.setUpdated(syndEntry.getPublishedDate());
        }
        List foreignMarkup = syndEntry.getForeignMarkup();
        if (!foreignMarkup.isEmpty()) {
            entry.setForeignMarkup(foreignMarkup);
        }
        SyndFeed source = syndEntry.getSource();
        if (source != null) {
            entry.setSource((Feed) source.createWireFeed(getType()));
        }
        return entry;
    }
}
