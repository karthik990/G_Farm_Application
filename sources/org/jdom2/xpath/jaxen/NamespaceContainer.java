package org.jdom2.xpath.jaxen;

import org.jdom2.Element;
import org.jdom2.Namespace;

final class NamespaceContainer {
    private final Element emt;

    /* renamed from: ns */
    private final Namespace f4555ns;

    public NamespaceContainer(Namespace namespace, Element element) {
        this.f4555ns = namespace;
        this.emt = element;
    }

    public Namespace getNamespace() {
        return this.f4555ns;
    }

    public Element getParentElement() {
        return this.emt;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f4555ns.getPrefix());
        sb.append("=");
        sb.append(this.f4555ns.getURI());
        return sb.toString();
    }
}
