package org.jdom2.filter;

import org.jdom2.Attribute;
import org.jdom2.Namespace;

public class AttributeFilter extends AbstractFilter<Attribute> {
    private static final long serialVersionUID = 200;
    private final String name;
    private final Namespace namespace;

    public AttributeFilter() {
        this(null, null);
    }

    public AttributeFilter(String str) {
        this(str, null);
    }

    public AttributeFilter(Namespace namespace2) {
        this(null, namespace2);
    }

    public AttributeFilter(String str, Namespace namespace2) {
        this.name = str;
        this.namespace = namespace2;
    }

    public Attribute filter(Object obj) {
        if (!(obj instanceof Attribute)) {
            return null;
        }
        Attribute attribute = (Attribute) obj;
        String str = this.name;
        if (str == null) {
            Namespace namespace2 = this.namespace;
            if (namespace2 == null) {
                return attribute;
            }
            if (!namespace2.equals(attribute.getNamespace())) {
                attribute = null;
            }
            return attribute;
        } else if (!str.equals(attribute.getName())) {
            return null;
        } else {
            Namespace namespace3 = this.namespace;
            if (namespace3 == null) {
                return attribute;
            }
            if (!namespace3.equals(attribute.getNamespace())) {
                attribute = null;
            }
            return attribute;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AttributeFilter)) {
            return false;
        }
        AttributeFilter attributeFilter = (AttributeFilter) obj;
        String str = this.name;
        if (str == null ? attributeFilter.name != null : !str.equals(attributeFilter.name)) {
            return false;
        }
        Namespace namespace2 = this.namespace;
        Namespace namespace3 = attributeFilter.namespace;
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
}
