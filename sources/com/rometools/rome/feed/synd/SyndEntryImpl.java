package com.rometools.rome.feed.synd;

import com.anjlab.android.iab.p020v3.Constants;
import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.DCModule;
import com.rometools.rome.feed.module.DCModuleImpl;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.SyModule;
import com.rometools.rome.feed.module.SyModuleImpl;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.rome.feed.synd.impl.URINormalizer;
import com.rometools.utils.Dates;
import com.rometools.utils.Lists;
import com.rometools.utils.Strings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jdom2.Element;

public class SyndEntryImpl implements Serializable, SyndEntry {
    public static final Set<String> CONVENIENCE_PROPERTIES = Collections.unmodifiableSet(IGNORE_PROPERTIES);
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final Set<String> IGNORE_PROPERTIES = new HashSet();
    private static final long serialVersionUID = 1;
    private List<SyndPerson> authors;
    private List<SyndCategory> categories;
    private String comments;
    private List<SyndContent> contents;
    private List<SyndPerson> contributors;
    private SyndContent description;
    private List<SyndEnclosure> enclosures;
    private List<Element> foreignMarkup;
    private String link;
    private List<SyndLink> links;
    private List<Module> modules;
    private final ObjectBean objBean;
    private SyndFeed source;
    private SyndContent title;
    private Date updatedDate;
    private String uri;
    private Object wireEntry;

    static {
        IGNORE_PROPERTIES.add("publishedDate");
        IGNORE_PROPERTIES.add("author");
        HashMap hashMap = new HashMap();
        String str = "uri";
        hashMap.put(str, String.class);
        hashMap.put("title", String.class);
        hashMap.put("link", String.class);
        hashMap.put(str, String.class);
        hashMap.put(Constants.RESPONSE_DESCRIPTION, SyndContent.class);
        hashMap.put("contents", SyndContent.class);
        hashMap.put("enclosures", SyndEnclosure.class);
        hashMap.put("modules", Module.class);
        hashMap.put("categories", SyndCategory.class);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(SyndContent.class, SyndContentImpl.class);
        hashMap2.put(SyndEnclosure.class, SyndEnclosureImpl.class);
        hashMap2.put(SyndCategory.class, SyndCategoryImpl.class);
        hashMap2.put(DCModule.class, DCModuleImpl.class);
        hashMap2.put(SyModule.class, SyModuleImpl.class);
        COPY_FROM_HELPER = new CopyFromHelper(SyndEntry.class, hashMap, hashMap2);
    }

    protected SyndEntryImpl(Class<?> cls, Set<String> set) {
        this.categories = new ArrayList();
        this.objBean = new ObjectBean(cls, this, set);
    }

    public SyndEntryImpl() {
        this(SyndEntry.class, IGNORE_PROPERTIES);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SyndEntryImpl)) {
            return false;
        }
        List foreignMarkup2 = getForeignMarkup();
        setForeignMarkup(((SyndEntryImpl) obj).getForeignMarkup());
        boolean equals = this.objBean.equals(obj);
        setForeignMarkup(foreignMarkup2);
        return equals;
    }

    public int hashCode() {
        return this.objBean.hashCode();
    }

    public String toString() {
        return this.objBean.toString();
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = URINormalizer.normalize(str);
    }

    public String getTitle() {
        SyndContent syndContent = this.title;
        if (syndContent != null) {
            return syndContent.getValue();
        }
        return null;
    }

    public void setTitle(String str) {
        if (this.title == null) {
            this.title = new SyndContentImpl();
        }
        this.title.setValue(str);
    }

    public SyndContent getTitleEx() {
        return this.title;
    }

    public void setTitleEx(SyndContent syndContent) {
        this.title = syndContent;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public SyndContent getDescription() {
        return this.description;
    }

    public void setDescription(SyndContent syndContent) {
        this.description = syndContent;
    }

    public List<SyndContent> getContents() {
        List<SyndContent> createWhenNull = Lists.createWhenNull(this.contents);
        this.contents = createWhenNull;
        return createWhenNull;
    }

    public void setContents(List<SyndContent> list) {
        this.contents = list;
    }

    public List<SyndEnclosure> getEnclosures() {
        List<SyndEnclosure> createWhenNull = Lists.createWhenNull(this.enclosures);
        this.enclosures = createWhenNull;
        return createWhenNull;
    }

    public void setEnclosures(List<SyndEnclosure> list) {
        this.enclosures = list;
    }

    public Date getPublishedDate() {
        return getDCModule().getDate();
    }

    public void setPublishedDate(Date date) {
        getDCModule().setDate(date);
    }

    public List<SyndCategory> getCategories() {
        return this.categories;
    }

    public void setCategories(List<SyndCategory> list) {
        this.categories = list;
    }

    public List<Module> getModules() {
        this.modules = Lists.createWhenNull(this.modules);
        if (ModuleUtils.getModule(this.modules, DCModule.URI) == null) {
            this.modules.add(new DCModuleImpl());
        }
        return this.modules;
    }

    public void setModules(List<Module> list) {
        this.modules = list;
    }

    public Module getModule(String str) {
        return ModuleUtils.getModule(getModules(), str);
    }

    private DCModule getDCModule() {
        return (DCModule) getModule(DCModule.URI);
    }

    public Class<SyndEntry> getInterface() {
        return SyndEntry.class;
    }

    public void copyFrom(CopyFrom copyFrom) {
        COPY_FROM_HELPER.copy(this, copyFrom);
    }

    public List<SyndLink> getLinks() {
        List<SyndLink> createWhenNull = Lists.createWhenNull(this.links);
        this.links = createWhenNull;
        return createWhenNull;
    }

    public void setLinks(List<SyndLink> list) {
        this.links = list;
    }

    public Date getUpdatedDate() {
        return Dates.copy(this.updatedDate);
    }

    public void setUpdatedDate(Date date) {
        this.updatedDate = new Date(date.getTime());
    }

    public List<SyndPerson> getAuthors() {
        List<SyndPerson> createWhenNull = Lists.createWhenNull(this.authors);
        this.authors = createWhenNull;
        return createWhenNull;
    }

    public void setAuthors(List<SyndPerson> list) {
        this.authors = list;
    }

    public String getAuthor() {
        String str;
        if (Lists.isNotEmpty(this.authors)) {
            str = ((SyndPerson) this.authors.get(0)).getName();
        } else {
            str = getDCModule().getCreator();
        }
        return str == null ? "" : str;
    }

    public void setAuthor(String str) {
        if (Strings.isEmpty(getDCModule().getCreator())) {
            getDCModule().setCreator(str);
        }
    }

    public List<SyndPerson> getContributors() {
        List<SyndPerson> createWhenNull = Lists.createWhenNull(this.contributors);
        this.contributors = createWhenNull;
        return createWhenNull;
    }

    public void setContributors(List<SyndPerson> list) {
        this.contributors = list;
    }

    public SyndFeed getSource() {
        return this.source;
    }

    public void setSource(SyndFeed syndFeed) {
        this.source = syndFeed;
    }

    public List<Element> getForeignMarkup() {
        List<Element> createWhenNull = Lists.createWhenNull(this.foreignMarkup);
        this.foreignMarkup = createWhenNull;
        return createWhenNull;
    }

    public void setForeignMarkup(List<Element> list) {
        this.foreignMarkup = list;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String str) {
        this.comments = str;
    }

    public Object getWireEntry() {
        return this.wireEntry;
    }

    public void setWireEntry(Object obj) {
        this.wireEntry = obj;
    }

    public SyndLink findRelatedLink(String str) {
        for (SyndLink syndLink : getLinks()) {
            if (str.equals(syndLink.getRel())) {
                return syndLink;
            }
        }
        return null;
    }
}
