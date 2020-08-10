package com.rometools.rome.feed.atom;

import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.Extendable;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.utils.Dates;
import com.rometools.utils.Lists;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.jdom2.Element;

public class Entry implements Cloneable, Serializable, Extendable {
    private static final long serialVersionUID = 1;
    private List<Link> alternateLinks;
    private List<SyndPerson> authors;
    private List<Category> categories;
    private List<Content> contents;
    private List<SyndPerson> contributors;
    private Date created;
    private List<Element> foreignMarkup;

    /* renamed from: id */
    private String f2359id;
    private List<Module> modules;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private List<Link> otherLinks;
    private Date published;
    private String rights;
    private Feed source;
    private Content summary;
    private Content title;
    private Date updated;
    private String xmlBase;

    public void setAlternateLinks(List<Link> list) {
        this.alternateLinks = list;
    }

    public List<Link> getAlternateLinks() {
        List<Link> createWhenNull = Lists.createWhenNull(this.alternateLinks);
        this.alternateLinks = createWhenNull;
        return createWhenNull;
    }

    public void setAuthors(List<SyndPerson> list) {
        this.authors = list;
    }

    public List<SyndPerson> getAuthors() {
        List<SyndPerson> createWhenNull = Lists.createWhenNull(this.authors);
        this.authors = createWhenNull;
        return createWhenNull;
    }

    public void setCategories(List<Category> list) {
        this.categories = list;
    }

    public List<Category> getCategories() {
        List<Category> createWhenNull = Lists.createWhenNull(this.categories);
        this.categories = createWhenNull;
        return createWhenNull;
    }

    public void setContents(List<Content> list) {
        this.contents = list;
    }

    public List<Content> getContents() {
        List<Content> createWhenNull = Lists.createWhenNull(this.contents);
        this.contents = createWhenNull;
        return createWhenNull;
    }

    public void setContributors(List<SyndPerson> list) {
        this.contributors = list;
    }

    public List<SyndPerson> getContributors() {
        List<SyndPerson> createWhenNull = Lists.createWhenNull(this.contributors);
        this.contributors = createWhenNull;
        return createWhenNull;
    }

    public void setCreated(Date date) {
        this.created = Dates.copy(date);
    }

    public Date getCreated() {
        return Dates.copy(this.created);
    }

    public void setForeignMarkup(List<Element> list) {
        this.foreignMarkup = list;
    }

    public List<Element> getForeignMarkup() {
        List<Element> createWhenNull = Lists.createWhenNull(this.foreignMarkup);
        this.foreignMarkup = createWhenNull;
        return createWhenNull;
    }

    public void setId(String str) {
        this.f2359id = str;
    }

    public String getId() {
        return this.f2359id;
    }

    public void setIssued(Date date) {
        this.published = Dates.copy(date);
    }

    public Date getIssued() {
        return Dates.copy(this.published);
    }

    public boolean isMediaEntry() {
        for (Link rel : getOtherLinks()) {
            if ("edit-media".equals(rel.getRel())) {
                return true;
            }
        }
        return false;
    }

    public void setModified(Date date) {
        this.updated = Dates.copy(date);
    }

    public Date getModified() {
        return Dates.copy(this.updated);
    }

    public Module getModule(String str) {
        return ModuleUtils.getModule(this.modules, str);
    }

    public void setModules(List<Module> list) {
        this.modules = list;
    }

    public List<Module> getModules() {
        List<Module> createWhenNull = Lists.createWhenNull(this.modules);
        this.modules = createWhenNull;
        return createWhenNull;
    }

    public void setOtherLinks(List<Link> list) {
        this.otherLinks = list;
    }

    public List<Link> getOtherLinks() {
        List<Link> createWhenNull = Lists.createWhenNull(this.otherLinks);
        this.otherLinks = createWhenNull;
        return createWhenNull;
    }

    public void setPublished(Date date) {
        this.published = Dates.copy(date);
    }

    public Date getPublished() {
        return Dates.copy(this.published);
    }

    public void setRights(String str) {
        this.rights = str;
    }

    public String getRights() {
        return this.rights;
    }

    public void setSource(Feed feed) {
        this.source = feed;
    }

    public Feed getSource() {
        return this.source;
    }

    public void setSummary(Content content) {
        this.summary = content;
    }

    public Content getSummary() {
        return this.summary;
    }

    public void setTitle(String str) {
        if (this.title == null) {
            this.title = new Content();
        }
        this.title.setValue(str);
    }

    public String getTitle() {
        Content content = this.title;
        if (content != null) {
            return content.getValue();
        }
        return null;
    }

    public void setTitleEx(Content content) {
        this.title = content;
    }

    public Content getTitleEx() {
        return this.title;
    }

    public void setUpdated(Date date) {
        this.updated = Dates.copy(date);
    }

    public Date getUpdated() {
        return Dates.copy(this.updated);
    }

    public void setXmlBase(String str) {
        this.xmlBase = str;
    }

    public String getXmlBase() {
        return this.xmlBase;
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Entry)) {
            return false;
        }
        List foreignMarkup2 = getForeignMarkup();
        setForeignMarkup(((Entry) obj).getForeignMarkup());
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

    public Link findRelatedLink(String str) {
        for (Link link : this.otherLinks) {
            if (str.equals(link.getRel())) {
                return link;
            }
        }
        return null;
    }
}
