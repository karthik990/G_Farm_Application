package com.rometools.rome.feed.atom;

import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.utils.Lists;
import java.util.Date;
import java.util.List;

public class Feed extends WireFeed {
    private static final long serialVersionUID = 1;
    private List<Link> alternateLinks;
    private List<SyndPerson> authors;
    private List<Category> categories;
    private List<SyndPerson> contributors;
    private List<Entry> entries;
    private Generator generator;
    private String icon;

    /* renamed from: id */
    private String f2360id;
    private Content info;
    private String language;
    private String logo;
    private List<Module> modules;
    private List<Link> otherLinks;
    private String rights;
    private Content subtitle;
    private Content title;
    private Date updated;
    private String xmlBase;

    public Feed() {
    }

    public Feed(String str) {
        super(str);
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String str) {
        this.language = str;
    }

    public String getTitle() {
        Content content = this.title;
        if (content != null) {
            return content.getValue();
        }
        return null;
    }

    public void setTitle(String str) {
        if (this.title == null) {
            this.title = new Content();
        }
        this.title.setValue(str);
    }

    public Content getTitleEx() {
        return this.title;
    }

    public void setTitleEx(Content content) {
        this.title = content;
    }

    public List<Link> getAlternateLinks() {
        List<Link> createWhenNull = Lists.createWhenNull(this.alternateLinks);
        this.alternateLinks = createWhenNull;
        return createWhenNull;
    }

    public void setAlternateLinks(List<Link> list) {
        this.alternateLinks = list;
    }

    public List<Link> getOtherLinks() {
        List<Link> createWhenNull = Lists.createWhenNull(this.otherLinks);
        this.otherLinks = createWhenNull;
        return createWhenNull;
    }

    public void setOtherLinks(List<Link> list) {
        this.otherLinks = list;
    }

    public List<SyndPerson> getAuthors() {
        List<SyndPerson> createWhenNull = Lists.createWhenNull(this.authors);
        this.authors = createWhenNull;
        return createWhenNull;
    }

    public void setAuthors(List<SyndPerson> list) {
        this.authors = list;
    }

    public List<SyndPerson> getContributors() {
        List<SyndPerson> createWhenNull = Lists.createWhenNull(this.contributors);
        this.contributors = createWhenNull;
        return createWhenNull;
    }

    public void setContributors(List<SyndPerson> list) {
        this.contributors = list;
    }

    public Content getTagline() {
        return this.subtitle;
    }

    public void setTagline(Content content) {
        this.subtitle = content;
    }

    public String getId() {
        return this.f2360id;
    }

    public void setId(String str) {
        this.f2360id = str;
    }

    public Generator getGenerator() {
        return this.generator;
    }

    public void setGenerator(Generator generator2) {
        this.generator = generator2;
    }

    public String getCopyright() {
        return this.rights;
    }

    public void setCopyright(String str) {
        this.rights = str;
    }

    public Content getInfo() {
        return this.info;
    }

    public void setInfo(Content content) {
        this.info = content;
    }

    public Date getModified() {
        return this.updated;
    }

    public void setModified(Date date) {
        this.updated = date;
    }

    public List<Entry> getEntries() {
        List<Entry> createWhenNull = Lists.createWhenNull(this.entries);
        this.entries = createWhenNull;
        return createWhenNull;
    }

    public void setEntries(List<Entry> list) {
        this.entries = list;
    }

    public List<Module> getModules() {
        List<Module> createWhenNull = Lists.createWhenNull(this.modules);
        this.modules = createWhenNull;
        return createWhenNull;
    }

    public void setModules(List<Module> list) {
        this.modules = list;
    }

    public Module getModule(String str) {
        return ModuleUtils.getModule(this.modules, str);
    }

    public List<Category> getCategories() {
        List<Category> createWhenNull = Lists.createWhenNull(this.categories);
        this.categories = createWhenNull;
        return createWhenNull;
    }

    public void setCategories(List<Category> list) {
        this.categories = list;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String str) {
        this.logo = str;
    }

    public String getRights() {
        return this.rights;
    }

    public void setRights(String str) {
        this.rights = str;
    }

    public Content getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(Content content) {
        this.subtitle = content;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date date) {
        this.updated = date;
    }

    public String getXmlBase() {
        return this.xmlBase;
    }

    public void setXmlBase(String str) {
        this.xmlBase = str;
    }
}
