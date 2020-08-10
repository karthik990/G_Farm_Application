package com.rometools.rome.feed.rss;

import com.rometools.rome.feed.impl.ObjectBean;
import java.io.Serializable;

public class Cloud implements Cloneable, Serializable {
    private static final long serialVersionUID = 1;
    private String domain;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String path;
    private int port;
    private String protocol;
    private String registerProcedure;

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

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String str) {
        this.domain = str;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public String getRegisterProcedure() {
        return this.registerProcedure;
    }

    public void setRegisterProcedure(String str) {
        this.registerProcedure = str;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String str) {
        this.protocol = str;
    }
}
