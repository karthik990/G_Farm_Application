package com.rometools.rome.feed.atom;

import com.rometools.rome.feed.impl.ObjectBean;
import com.rometools.utils.Strings;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Content implements Cloneable, Serializable {
    public static final String BASE64 = "base64";
    public static final String ESCAPED = "escaped";
    public static final String HTML = "html";
    private static final Set<String> MODES = new HashSet();
    public static final String TEXT = "text";
    public static final String XHTML = "xhtml";
    public static final String XML = "xml";
    private static final long serialVersionUID = 1;
    private String mode;
    private final ObjectBean objBean = new ObjectBean(getClass(), this);
    private String src;
    private String type;
    private String value;

    static {
        MODES.add("xml");
        MODES.add(BASE64);
        MODES.add(ESCAPED);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.objBean.clone();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Content)) {
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

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String str) {
        this.mode = Strings.toLowerCase(str);
        if (str == null || !MODES.contains(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid mode [");
            sb.append(str);
            sb.append("]");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getSrc() {
        return this.src;
    }

    public void setSrc(String str) {
        this.src = str;
    }
}
