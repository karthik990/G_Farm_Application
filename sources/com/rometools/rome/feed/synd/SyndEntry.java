package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.module.Extendable;
import com.rometools.rome.feed.module.Module;
import java.util.Date;
import java.util.List;
import org.jdom2.Element;

public interface SyndEntry extends Cloneable, CopyFrom, Extendable {
    Object clone() throws CloneNotSupportedException;

    SyndLink findRelatedLink(String str);

    String getAuthor();

    List<SyndPerson> getAuthors();

    List<SyndCategory> getCategories();

    String getComments();

    List<SyndContent> getContents();

    List<SyndPerson> getContributors();

    SyndContent getDescription();

    List<SyndEnclosure> getEnclosures();

    List<Element> getForeignMarkup();

    String getLink();

    List<SyndLink> getLinks();

    Module getModule(String str);

    List<Module> getModules();

    Date getPublishedDate();

    SyndFeed getSource();

    String getTitle();

    SyndContent getTitleEx();

    Date getUpdatedDate();

    String getUri();

    Object getWireEntry();

    void setAuthor(String str);

    void setAuthors(List<SyndPerson> list);

    void setCategories(List<SyndCategory> list);

    void setComments(String str);

    void setContents(List<SyndContent> list);

    void setContributors(List<SyndPerson> list);

    void setDescription(SyndContent syndContent);

    void setEnclosures(List<SyndEnclosure> list);

    void setForeignMarkup(List<Element> list);

    void setLink(String str);

    void setLinks(List<SyndLink> list);

    void setModules(List<Module> list);

    void setPublishedDate(Date date);

    void setSource(SyndFeed syndFeed);

    void setTitle(String str);

    void setTitleEx(SyndContent syndContent);

    void setUpdatedDate(Date date);

    void setUri(String str);
}
