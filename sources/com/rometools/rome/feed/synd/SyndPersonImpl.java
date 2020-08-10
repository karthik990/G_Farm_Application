package com.rometools.rome.feed.synd;

import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.module.impl.ModuleUtils;
import com.rometools.utils.Lists;
import java.io.Serializable;
import java.util.List;

public class SyndPersonImpl implements Serializable, SyndPerson {
    private static final long serialVersionUID = 1;
    private String email;
    private List<Module> modules;
    private String name;
    private final ObjectBean objBean = new ObjectBean(SyndPerson.class, this);
    private String uri;

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SyndPersonImpl)) {
            return false;
        }
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
        return ModuleUtils.getModule(getModules(), str);
    }
}
