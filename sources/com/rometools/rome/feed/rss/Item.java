package com.rometools.rome.feed.rss;

import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.Extendable;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.utils.Dates;
import com.rometools.utils.Lists;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.jdom2.Element;

public class Item implements Cloneable, Serializable, Extendable {
    private static final long serialVersionUID = 1;
    private String author;
    private List<Category> categories;
    private String comments;
    private Content content;
    private Description description;
    private List<Enclosure> enclosures;
    private Date expirationDate;
    private List<Element> foreignMarkup;
    private Guid guid;
    private String link;
    private List<Module> modules;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private Date pubDate;
    private Source source;
    private String title;
    private String uri;

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Item)) {
            return false;
        }
        List foreignMarkup2 = getForeignMarkup();
        setForeignMarkup(((Item) obj).getForeignMarkup());
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

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getLink() {
        return this.link;
    }

    public void setLink(String str) {
        this.link = str;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    public Description getDescription() {
        return this.description;
    }

    public void setDescription(Description description2) {
        this.description = description2;
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content2) {
        this.content = content2;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source2) {
        this.source = source2;
    }

    public List<Enclosure> getEnclosures() {
        List<Enclosure> createWhenNull = Lists.createWhenNull(this.enclosures);
        this.enclosures = createWhenNull;
        return createWhenNull;
    }

    public void setEnclosures(List<Enclosure> list) {
        this.enclosures = list;
    }

    public List<Category> getCategories() {
        List<Category> createWhenNull = Lists.createWhenNull(this.categories);
        this.categories = createWhenNull;
        return createWhenNull;
    }

    public void setCategories(List<Category> list) {
        this.categories = list;
    }

    public Guid getGuid() {
        return this.guid;
    }

    public void setGuid(Guid guid2) {
        this.guid = guid2;
    }

    public String getComments() {
        return this.comments;
    }

    public void setComments(String str) {
        this.comments = str;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String str) {
        this.author = str;
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

    public Date getPubDate() {
        return Dates.copy(this.pubDate);
    }

    public void setPubDate(Date date) {
        this.pubDate = Dates.copy(date);
    }

    public Date getExpirationDate() {
        return Dates.copy(this.expirationDate);
    }

    public void setExpirationDate(Date date) {
        this.expirationDate = Dates.copy(date);
    }

    public List<Element> getForeignMarkup() {
        List<Element> createWhenNull = Lists.createWhenNull(this.foreignMarkup);
        this.foreignMarkup = createWhenNull;
        return createWhenNull;
    }

    public void setForeignMarkup(List<Element> list) {
        this.foreignMarkup = list;
    }
}
