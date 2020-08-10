package com.rometools.rome.feed;

import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.Extendable;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.utils.Lists;
import java.io.Serializable;
import java.util.List;
import org.jdom2.Element;

public abstract class WireFeed implements Cloneable, Serializable, Extendable {
    private static final long serialVersionUID = 1;
    private String encoding;
    private String feedType;
    private List<Element> foreignMarkup;
    private List<Module> modules;
    private final ObjectBean objBean;
    private String styleSheet;

    protected WireFeed() {
        this.objBean = new ObjectBean(getClass(), this);
    }

    protected WireFeed(String str) {
        this();
        this.feedType = str;
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof WireFeed)) {
            return false;
        }
        List foreignMarkup2 = getForeignMarkup();
        setForeignMarkup(((WireFeed) obj).getForeignMarkup());
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

    public void setFeedType(String str) {
        this.feedType = str;
    }

    public String getFeedType() {
        return this.feedType;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String str) {
        this.encoding = str;
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

    public List<Element> getForeignMarkup() {
        List<Element> createWhenNull = Lists.createWhenNull(this.foreignMarkup);
        this.foreignMarkup = createWhenNull;
        return createWhenNull;
    }

    public void setForeignMarkup(List<Element> list) {
        this.foreignMarkup = list;
    }

    public String getStyleSheet() {
        return this.styleSheet;
    }

    public void setStyleSheet(String str) {
        this.styleSheet = str;
    }
}
