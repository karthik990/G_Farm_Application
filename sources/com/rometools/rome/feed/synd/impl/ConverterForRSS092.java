package com.rometools.rome.feed.synd.impl;

import com.rometools.rome.feed.rss.Category;
import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndCategoryImpl;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEnclosureImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class ConverterForRSS092 extends ConverterForRSS091Userland {
    public ConverterForRSS092() {
        this("rss_0.92");
    }

    protected ConverterForRSS092(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public SyndEntry createSyndEntry(Item item, boolean z) {
        SyndEntry createSyndEntry = super.createSyndEntry(item, z);
        List categories = item.getCategories();
        if (!categories.isEmpty()) {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            linkedHashSet.addAll(createSyndCategories(categories));
            linkedHashSet.addAll(createSyndEntry.getCategories());
            createSyndEntry.setCategories(new ArrayList(linkedHashSet));
        }
        List enclosures = item.getEnclosures();
        if (!enclosures.isEmpty()) {
            createSyndEntry.setEnclosures(createSyndEnclosures(enclosures));
        }
        return createSyndEntry;
    }

    /* access modifiers changed from: protected */
    public List<SyndCategory> createSyndCategories(List<Category> list) {
        ArrayList arrayList = new ArrayList();
        for (Category category : list) {
            SyndCategoryImpl syndCategoryImpl = new SyndCategoryImpl();
            syndCategoryImpl.setTaxonomyUri(category.getDomain());
            syndCategoryImpl.setName(category.getValue());
            arrayList.add(syndCategoryImpl);
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public List<SyndEnclosure> createSyndEnclosures(List<Enclosure> list) {
        ArrayList arrayList = new ArrayList();
        for (Enclosure enclosure : list) {
            SyndEnclosureImpl syndEnclosureImpl = new SyndEnclosureImpl();
            syndEnclosureImpl.setUrl(enclosure.getUrl());
            syndEnclosureImpl.setType(enclosure.getType());
            syndEnclosureImpl.setLength(enclosure.getLength());
            arrayList.add(syndEnclosureImpl);
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public Item createRSSItem(SyndEntry syndEntry) {
        Item createRSSItem = super.createRSSItem(syndEntry);
        List categories = syndEntry.getCategories();
        if (!categories.isEmpty()) {
            createRSSItem.setCategories(createRSSCategories(categories));
        }
        List enclosures = syndEntry.getEnclosures();
        if (!enclosures.isEmpty()) {
            createRSSItem.setEnclosures(createEnclosures(enclosures));
        }
        return createRSSItem;
    }

    /* access modifiers changed from: protected */
    public List<Category> createRSSCategories(List<SyndCategory> list) {
        ArrayList arrayList = new ArrayList();
        for (SyndCategory syndCategory : list) {
            Category category = new Category();
            category.setDomain(syndCategory.getTaxonomyUri());
            category.setValue(syndCategory.getName());
            arrayList.add(category);
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public List<Enclosure> createEnclosures(List<SyndEnclosure> list) {
        ArrayList arrayList = new ArrayList();
        for (SyndEnclosure syndEnclosure : list) {
            Enclosure enclosure = new Enclosure();
            enclosure.setUrl(syndEnclosure.getUrl());
            enclosure.setType(syndEnclosure.getType());
            enclosure.setLength(syndEnclosure.getLength());
            arrayList.add(enclosure);
        }
        return arrayList;
    }
}
