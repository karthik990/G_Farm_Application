package com.rometools.rome.feed.synd;

import com.anjlab.android.iab.p020v3.Constants;
import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.impl.CopyFromHelper;
import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.DCModule;
import com.rometools.rome.feed.module.DCModuleImpl;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.SyModule;
import com.rometools.rome.feed.module.SyModuleImpl;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.rome.feed.synd.impl.Converters;
import com.rometools.rome.feed.synd.impl.URINormalizer;
import com.rometools.utils.Lists;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jdom2.Element;

public class SyndFeedImpl implements Serializable, SyndFeed {
    public static final Set<String> CONVENIENCE_PROPERTIES = Collections.unmodifiableSet(IGNORE_PROPERTIES);
    private static final Converters CONVERTERS = new Converters();
    private static final CopyFromHelper COPY_FROM_HELPER;
    private static final Set<String> IGNORE_PROPERTIES = new HashSet();
    private static final long serialVersionUID = 1;
    private List<SyndPerson> authors;
    private List<SyndPerson> contributors;
    private SyndContent description;
    private String docs;
    private String encoding;
    private List<SyndEntry> entries;
    private String feedType;
    private List<Element> foreignMarkup;
    private String generator;
    private SyndImage image;
    private String link;
    private List<SyndLink> links;
    private String managingEditor;
    private List<Module> modules;
    private final ObjectBean objBean;
    private boolean preserveWireFeed;
    private String styleSheet;
    private SyndContent title;
    private String uri;
    private String webMaster;
    private WireFeed wireFeed;

    static {
        IGNORE_PROPERTIES.add("publishedDate");
        IGNORE_PROPERTIES.add("author");
        IGNORE_PROPERTIES.add("copyright");
        String str = "categories";
        IGNORE_PROPERTIES.add(str);
        IGNORE_PROPERTIES.add("language");
        HashMap hashMap = new HashMap();
        hashMap.put("feedType", String.class);
        hashMap.put("encoding", String.class);
        hashMap.put("uri", String.class);
        hashMap.put("title", String.class);
        hashMap.put("link", String.class);
        hashMap.put(Constants.RESPONSE_DESCRIPTION, String.class);
        hashMap.put(TtmlNode.TAG_IMAGE, SyndImage.class);
        hashMap.put("entries", SyndEntry.class);
        hashMap.put("modules", Module.class);
        hashMap.put(str, SyndCategory.class);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(SyndEntry.class, SyndEntryImpl.class);
        hashMap2.put(SyndImage.class, SyndImageImpl.class);
        hashMap2.put(SyndCategory.class, SyndCategoryImpl.class);
        hashMap2.put(DCModule.class, DCModuleImpl.class);
        hashMap2.put(SyModule.class, SyModuleImpl.class);
        COPY_FROM_HELPER = new CopyFromHelper(SyndFeed.class, hashMap, hashMap2);
    }

    public List<String> getSupportedFeedTypes() {
        return CONVERTERS.getSupportedFeedTypes();
    }

    protected SyndFeedImpl(Class<?> cls, Set<String> set) {
        this.wireFeed = null;
        this.preserveWireFeed = false;
        this.objBean = new ObjectBean(cls, this, set);
    }

    public SyndFeedImpl() {
        this(null);
    }

    public SyndFeedImpl(WireFeed wireFeed2) {
        this(wireFeed2, false);
    }

    public SyndFeedImpl(WireFeed wireFeed2, boolean z) {
        this(SyndFeed.class, IGNORE_PROPERTIES);
        if (z) {
            this.wireFeed = wireFeed2;
            this.preserveWireFeed = z;
        }
        if (wireFeed2 != null) {
            this.feedType = wireFeed2.getFeedType();
            Converter converter = CONVERTERS.getConverter(this.feedType);
            if (converter != null) {
                converter.copyInto(wireFeed2, this);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid feed type [");
            sb.append(this.feedType);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof SyndFeedImpl)) {
            return false;
        }
        List foreignMarkup2 = getForeignMarkup();
        setForeignMarkup(((SyndFeedImpl) obj).getForeignMarkup());
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

    public WireFeed createWireFeed() {
        return createWireFeed(this.feedType);
    }

    public WireFeed createWireFeed(String str) {
        if (str != null) {
            Converter converter = CONVERTERS.getConverter(str);
            if (converter != null) {
                return converter.createRealFeed(this);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid feed type [");
            sb.append(str);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        }
        throw new IllegalArgumentException("Feed type cannot be null");
    }

    public WireFeed originalWireFeed() {
        return this.wireFeed;
    }

    public String getFeedType() {
        return this.feedType;
    }

    public void setFeedType(String str) {
        this.feedType = str;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String str) {
        this.encoding = str;
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

    public String getDescription() {
        SyndContent syndContent = this.description;
        if (syndContent != null) {
            return syndContent.getValue();
        }
        return null;
    }

    public void setDescription(String str) {
        if (this.description == null) {
            this.description = new SyndContentImpl();
        }
        this.description.setValue(str);
    }

    public SyndContent getDescriptionEx() {
        return this.description;
    }

    public void setDescriptionEx(SyndContent syndContent) {
        this.description = syndContent;
    }

    public Date getPublishedDate() {
        return getDCModule().getDate();
    }

    public void setPublishedDate(Date date) {
        getDCModule().setDate(date);
    }

    public String getCopyright() {
        return getDCModule().getRights();
    }

    public void setCopyright(String str) {
        getDCModule().setRights(str);
    }

    public SyndImage getImage() {
        return this.image;
    }

    public void setImage(SyndImage syndImage) {
        this.image = syndImage;
    }

    public List<SyndCategory> getCategories() {
        return new SyndCategoryListFacade(getDCModule().getSubjects());
    }

    public void setCategories(List<SyndCategory> list) {
        getDCModule().setSubjects(SyndCategoryListFacade.convertElementsSyndCategoryToSubject(list));
    }

    public List<SyndEntry> getEntries() {
        List<SyndEntry> createWhenNull = Lists.createWhenNull(this.entries);
        this.entries = createWhenNull;
        return createWhenNull;
    }

    public void setEntries(List<SyndEntry> list) {
        this.entries = list;
    }

    public String getLanguage() {
        return getDCModule().getLanguage();
    }

    public void setLanguage(String str) {
        getDCModule().setLanguage(str);
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

    public Class<SyndFeed> getInterface() {
        return SyndFeed.class;
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

    public List<SyndPerson> getAuthors() {
        List<SyndPerson> createWhenNull = Lists.createWhenNull(this.authors);
        this.authors = createWhenNull;
        return createWhenNull;
    }

    public void setAuthors(List<SyndPerson> list) {
        this.authors = list;
    }

    public String getAuthor() {
        return getDCModule().getCreator();
    }

    public void setAuthor(String str) {
        getDCModule().setCreator(str);
    }

    public List<SyndPerson> getContributors() {
        List<SyndPerson> createWhenNull = Lists.createWhenNull(this.contributors);
        this.contributors = createWhenNull;
        return createWhenNull;
    }

    public void setContributors(List<SyndPerson> list) {
        this.contributors = list;
    }

    public List<Element> getForeignMarkup() {
        List<Element> createWhenNull = Lists.createWhenNull(this.foreignMarkup);
        this.foreignMarkup = createWhenNull;
        return createWhenNull;
    }

    public void setForeignMarkup(List<Element> list) {
        this.foreignMarkup = list;
    }

    public boolean isPreservingWireFeed() {
        return this.preserveWireFeed;
    }

    public String getDocs() {
        return this.docs;
    }

    public void setDocs(String str) {
        this.docs = str;
    }

    public String getGenerator() {
        return this.generator;
    }

    public void setGenerator(String str) {
        this.generator = str;
    }

    public String getManagingEditor() {
        return this.managingEditor;
    }

    public void setManagingEditor(String str) {
        this.managingEditor = str;
    }

    public String getWebMaster() {
        return this.webMaster;
    }

    public void setWebMaster(String str) {
        this.webMaster = str;
    }

    public String getStyleSheet() {
        return this.styleSheet;
    }

    public void setStyleSheet(String str) {
        this.styleSheet = str;
    }
}
