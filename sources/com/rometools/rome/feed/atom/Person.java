package com.rometools.rome.feed.atom;

import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.Extendable;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.rome.feed.synd.SyndPerson;
import com.rometools.utils.Alternatives;
import com.rometools.utils.Lists;
import java.io.Serializable;
import java.util.List;

public class Person implements SyndPerson, Cloneable, Serializable, Extendable {
    private static final long serialVersionUID = 1;
    private String email;
    private List<Module> modules;
    private String name;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String uri;
    private String uriResolved;

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        return this.objBean.equals(obj);
    }

    public int hashCode() {
        return this.objBean.hashCode();
    }

    public String toString() {
        return this.objBean.toString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getUrl() {
        return this.uri;
    }

    public void setUrl(String str) {
        this.uri = str;
    }

    public void setUriResolved(String str) {
        this.uriResolved = str;
    }

    public String getUriResolved(String str) {
        return (String) Alternatives.firstNotNull(this.uriResolved, this.uri);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = str;
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
}
