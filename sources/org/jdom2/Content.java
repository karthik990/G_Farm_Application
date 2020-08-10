package org.jdom2;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public abstract class Content extends CloneBase implements Serializable, NamespaceAware {
    private static final long serialVersionUID = 200;
    protected final CType ctype;
    protected transient Parent parent = null;

    public enum CType {
        Comment,
        Element,
        ProcessingInstruction,
        EntityRef,
        Text,
        CDATA,
        DocType
    }

    public final boolean equals(Object obj) {
        return obj == this;
    }

    public abstract String getValue();

    protected Content(CType cType) {
        this.ctype = cType;
    }

    public final CType getCType() {
        return this.ctype;
    }

    public Content detach() {
        Parent parent2 = this.parent;
        if (parent2 != null) {
            parent2.removeContent(this);
        }
        return this;
    }

    public Parent getParent() {
        return this.parent;
    }

    public final Element getParentElement() {
        Parent parent2 = getParent();
        if (!(parent2 instanceof Element)) {
            parent2 = null;
        }
        return (Element) parent2;
    }

    /* access modifiers changed from: protected */
    public Content setParent(Parent parent2) {
        this.parent = parent2;
        return this;
    }

    public Document getDocument() {
        Parent parent2 = this.parent;
        if (parent2 == null) {
            return null;
        }
        return parent2.getDocument();
    }

    public Content clone() {
        Content content = (Content) super.clone();
        content.parent = null;
        return content;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public List<Namespace> getNamespacesInScope() {
        Element parentElement = getParentElement();
        if (parentElement == null) {
            return Collections.singletonList(Namespace.XML_NAMESPACE);
        }
        return parentElement.getNamespacesInScope();
    }

    public List<Namespace> getNamespacesIntroduced() {
        return Collections.emptyList();
    }

    public List<Namespace> getNamespacesInherited() {
        return getNamespacesInScope();
    }
}
