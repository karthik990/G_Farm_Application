package org.jdom2.filter;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class ElementFilter extends AbstractFilter<Element> {
    private static final long serialVersionUID = 200;
    private String name;
    private Namespace namespace;

    public ElementFilter() {
    }

    public ElementFilter(String str) {
        this.name = str;
    }

    public ElementFilter(Namespace namespace2) {
        this.namespace = namespace2;
    }

    public ElementFilter(String str, Namespace namespace2) {
        this.name = str;
        this.namespace = namespace2;
    }

    public Element filter(Object obj) {
        if (!(obj instanceof Element)) {
            return null;
        }
        Element element = (Element) obj;
        String str = this.name;
        if (str == null) {
            Namespace namespace2 = this.namespace;
            if (namespace2 == null) {
                return element;
            }
            if (!namespace2.equals(element.getNamespace())) {
                element = null;
            }
            return element;
        } else if (!str.equals(element.getName())) {
            return null;
        } else {
            Namespace namespace3 = this.namespace;
            if (namespace3 == null) {
                return element;
            }
            if (!namespace3.equals(element.getNamespace())) {
                element = null;
            }
            return element;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ElementFilter)) {
            return false;
        }
        ElementFilter elementFilter = (ElementFilter) obj;
        String str = this.name;
        if (str == null ? elementFilter.name != null : !str.equals(elementFilter.name)) {
            return false;
        }
        Namespace namespace2 = this.namespace;
        Namespace namespace3 = elementFilter.namespace;
        return namespace2 == null ? namespace3 == null : namespace2.equals(namespace3);
    }

    public int hashCode() {
        String str = this.name;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 29;
        Namespace namespace2 = this.namespace;
        if (namespace2 != null) {
            i = namespace2.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ElementFilter: Name ");
        String str = this.name;
        if (str == null) {
            str = "*any*";
        }
        sb.append(str);
        sb.append(" with Namespace ");
        sb.append(this.namespace);
        sb.append("]");
        return sb.toString();
    }
}
