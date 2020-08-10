package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.CopyFrom;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.module.Extendable;
import com.rometools.rome.feed.module.Module;
import java.util.Date;
import java.util.List;
import org.jdom2.Element;

public interface SyndFeed extends Cloneable, CopyFrom, Extendable {
    Object clone() throws CloneNotSupportedException;

    WireFeed createWireFeed();

    WireFeed createWireFeed(String str);

    String getAuthor();

    List<SyndPerson> getAuthors();

    List<SyndCategory> getCategories();

    List<SyndPerson> getContributors();

    String getCopyright();

    String getDescription();

    SyndContent getDescriptionEx();

    String getDocs();

    String getEncoding();

    List<SyndEntry> getEntries();

    String getFeedType();

    List<Element> getForeignMarkup();

    String getGenerator();

    SyndImage getImage();

    String getLanguage();

    String getLink();

    List<SyndLink> getLinks();

    String getManagingEditor();

    Module getModule(String str);

    List<Module> getModules();

    Date getPublishedDate();

    String getStyleSheet();

    List<String> getSupportedFeedTypes();

    String getTitle();

    SyndContent getTitleEx();

    String getUri();

    String getWebMaster();

    boolean isPreservingWireFeed();

    WireFeed originalWireFeed();

    void setAuthor(String str);

    void setAuthors(List<SyndPerson> list);

    void setCategories(List<SyndCategory> list);

    void setContributors(List<SyndPerson> list);

    void setCopyright(String str);

    void setDescription(String str);

    void setDescriptionEx(SyndContent syndContent);

    void setDocs(String str);

    void setEncoding(String str);

    void setEntries(List<SyndEntry> list);

    void setFeedType(String str);

    void setForeignMarkup(List<Element> list);

    void setGenerator(String str);

    void setImage(SyndImage syndImage);

    void setLanguage(String str);

    void setLink(String str);

    void setLinks(List<SyndLink> list);

    void setManagingEditor(String str);

    void setModules(List<Module> list);

    void setPublishedDate(Date date);

    void setStyleSheet(String str);

    void setTitle(String str);

    void setTitleEx(SyndContent syndContent);

    void setUri(String str);

    void setWebMaster(String str);
}
