package org.jdom2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.jdom2.Content.CType;
import org.jdom2.filter.ElementFilter;
import org.jdom2.filter.Filter;
import org.jdom2.util.IteratorIterable;

public class Element extends Content implements Parent {
    private static final int INITIAL_ARRAY_SIZE = 5;
    private static final long serialVersionUID = 200;
    transient List<Namespace> additionalNamespaces;
    transient AttributeList attributes;
    transient ContentList content;
    protected String name;
    protected Namespace namespace;

    protected Element() {
        super(CType.Element);
        this.additionalNamespaces = null;
        this.attributes = null;
        this.content = new ContentList(this);
    }

    public Element(String str, Namespace namespace2) {
        super(CType.Element);
        this.additionalNamespaces = null;
        this.attributes = null;
        this.content = new ContentList(this);
        setName(str);
        setNamespace(namespace2);
    }

    public Element(String str) {
        this(str, (Namespace) null);
    }

    public Element(String str, String str2) {
        this(str, Namespace.getNamespace("", str2));
    }

    public Element(String str, String str2, String str3) {
        this(str, Namespace.getNamespace(str2, str3));
    }

    public String getName() {
        return this.name;
    }

    public Element setName(String str) {
        String checkElementName = Verifier.checkElementName(str);
        if (checkElementName == null) {
            this.name = str;
            return this;
        }
        throw new IllegalNameException(str, "element", checkElementName);
    }

    public Namespace getNamespace() {
        return this.namespace;
    }

    public Element setNamespace(Namespace namespace2) {
        if (namespace2 == null) {
            namespace2 = Namespace.NO_NAMESPACE;
        }
        if (this.additionalNamespaces != null) {
            String checkNamespaceCollision = Verifier.checkNamespaceCollision(namespace2, getAdditionalNamespaces());
            if (checkNamespaceCollision != null) {
                throw new IllegalAddException(this, namespace2, checkNamespaceCollision);
            }
        }
        if (hasAttributes()) {
            for (Attribute checkNamespaceCollision2 : getAttributes()) {
                String checkNamespaceCollision3 = Verifier.checkNamespaceCollision(namespace2, checkNamespaceCollision2);
                if (checkNamespaceCollision3 != null) {
                    throw new IllegalAddException(this, namespace2, checkNamespaceCollision3);
                }
            }
        }
        this.namespace = namespace2;
        return this;
    }

    public String getNamespacePrefix() {
        return this.namespace.getPrefix();
    }

    public String getNamespaceURI() {
        return this.namespace.getURI();
    }

    public Namespace getNamespace(String str) {
        if (str == null) {
            return null;
        }
        if ("xml".equals(str)) {
            return Namespace.XML_NAMESPACE;
        }
        if (str.equals(getNamespacePrefix())) {
            return getNamespace();
        }
        if (this.additionalNamespaces != null) {
            for (int i = 0; i < this.additionalNamespaces.size(); i++) {
                Namespace namespace2 = (Namespace) this.additionalNamespaces.get(i);
                if (str.equals(namespace2.getPrefix())) {
                    return namespace2;
                }
            }
        }
        AttributeList attributeList = this.attributes;
        if (attributeList != null) {
            Iterator it = attributeList.iterator();
            while (it.hasNext()) {
                Attribute attribute = (Attribute) it.next();
                if (str.equals(attribute.getNamespacePrefix())) {
                    return attribute.getNamespace();
                }
            }
        }
        if (this.parent instanceof Element) {
            return ((Element) this.parent).getNamespace(str);
        }
        return null;
    }

    public String getQualifiedName() {
        if ("".equals(this.namespace.getPrefix())) {
            return getName();
        }
        StringBuilder sb = new StringBuilder(this.namespace.getPrefix());
        sb.append(':');
        sb.append(this.name);
        return sb.toString();
    }

    public boolean addNamespaceDeclaration(Namespace namespace2) {
        if (this.additionalNamespaces == null) {
            this.additionalNamespaces = new ArrayList(5);
        }
        for (Namespace namespace3 : this.additionalNamespaces) {
            if (namespace3 == namespace2) {
                return false;
            }
        }
        String checkNamespaceCollision = Verifier.checkNamespaceCollision(namespace2, this);
        if (checkNamespaceCollision == null) {
            return this.additionalNamespaces.add(namespace2);
        }
        throw new IllegalAddException(this, namespace2, checkNamespaceCollision);
    }

    public void removeNamespaceDeclaration(Namespace namespace2) {
        List<Namespace> list = this.additionalNamespaces;
        if (list != null) {
            list.remove(namespace2);
        }
    }

    public List<Namespace> getAdditionalNamespaces() {
        List<Namespace> list = this.additionalNamespaces;
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }

    public String getValue() {
        StringBuilder sb = new StringBuilder();
        for (Content content2 : getContent()) {
            if ((content2 instanceof Element) || (content2 instanceof Text)) {
                sb.append(content2.getValue());
            }
        }
        return sb.toString();
    }

    public boolean isRootElement() {
        return this.parent instanceof Document;
    }

    public int getContentSize() {
        return this.content.size();
    }

    public int indexOf(Content content2) {
        return this.content.indexOf(content2);
    }

    public String getText() {
        String str = "";
        if (this.content.size() == 0) {
            return str;
        }
        if (this.content.size() == 1) {
            Content content2 = this.content.get(0);
            return content2 instanceof Text ? ((Text) content2).getText() : str;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (int i = 0; i < this.content.size(); i++) {
            Content content3 = this.content.get(i);
            if (content3 instanceof Text) {
                sb.append(((Text) content3).getText());
                z = true;
            }
        }
        if (!z) {
            return str;
        }
        return sb.toString();
    }

    public String getTextTrim() {
        return getText().trim();
    }

    public String getTextNormalize() {
        return Text.normalizeString(getText());
    }

    public String getChildText(String str) {
        Element child = getChild(str);
        if (child == null) {
            return null;
        }
        return child.getText();
    }

    public String getChildTextTrim(String str) {
        Element child = getChild(str);
        if (child == null) {
            return null;
        }
        return child.getTextTrim();
    }

    public String getChildTextNormalize(String str) {
        Element child = getChild(str);
        if (child == null) {
            return null;
        }
        return child.getTextNormalize();
    }

    public String getChildText(String str, Namespace namespace2) {
        Element child = getChild(str, namespace2);
        if (child == null) {
            return null;
        }
        return child.getText();
    }

    public String getChildTextTrim(String str, Namespace namespace2) {
        Element child = getChild(str, namespace2);
        if (child == null) {
            return null;
        }
        return child.getTextTrim();
    }

    public String getChildTextNormalize(String str, Namespace namespace2) {
        Element child = getChild(str, namespace2);
        if (child == null) {
            return null;
        }
        return child.getTextNormalize();
    }

    public Element setText(String str) {
        this.content.clear();
        if (str != null) {
            addContent((Content) new Text(str));
        }
        return this;
    }

    public boolean coalesceText(boolean z) {
        Iterator descendants = z ? getDescendants() : this.content.iterator();
        boolean z2 = false;
        while (true) {
            Text text = null;
            while (true) {
                if (!descendants.hasNext()) {
                    return z2;
                }
                Content content2 = (Content) descendants.next();
                if (content2.getCType() == CType.Text) {
                    Text text2 = (Text) content2;
                    boolean z3 = true;
                    if ("".equals(text2.getValue())) {
                        descendants.remove();
                    } else if (text == null || text.getParent() != text2.getParent()) {
                        z3 = z2;
                        text = text2;
                    } else {
                        text.append(text2.getValue());
                        descendants.remove();
                    }
                    z2 = z3;
                }
            }
        }
    }

    public List<Content> getContent() {
        return this.content;
    }

    public <E extends Content> List<E> getContent(Filter<E> filter) {
        return this.content.getView(filter);
    }

    public List<Content> removeContent() {
        ArrayList arrayList = new ArrayList(this.content);
        this.content.clear();
        return arrayList;
    }

    public <F extends Content> List<F> removeContent(Filter<F> filter) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.content.getView(filter).iterator();
        while (it.hasNext()) {
            arrayList.add((Content) it.next());
            it.remove();
        }
        return arrayList;
    }

    public Element setContent(Collection<? extends Content> collection) {
        this.content.clearAndSet(collection);
        return this;
    }

    public Element setContent(int i, Content content2) {
        this.content.set(i, content2);
        return this;
    }

    public Parent setContent(int i, Collection<? extends Content> collection) {
        this.content.remove(i);
        this.content.addAll(i, collection);
        return this;
    }

    public Element addContent(String str) {
        return addContent((Content) new Text(str));
    }

    public Element addContent(Content content2) {
        this.content.add(content2);
        return this;
    }

    public Element addContent(Collection<? extends Content> collection) {
        this.content.addAll(collection);
        return this;
    }

    public Element addContent(int i, Content content2) {
        this.content.add(i, content2);
        return this;
    }

    public Element addContent(int i, Collection<? extends Content> collection) {
        this.content.addAll(i, collection);
        return this;
    }

    public List<Content> cloneContent() {
        int contentSize = getContentSize();
        ArrayList arrayList = new ArrayList(contentSize);
        for (int i = 0; i < contentSize; i++) {
            arrayList.add(getContent(i).clone());
        }
        return arrayList;
    }

    public Content getContent(int i) {
        return this.content.get(i);
    }

    public boolean removeContent(Content content2) {
        return this.content.remove(content2);
    }

    public Content removeContent(int i) {
        return this.content.remove(i);
    }

    public Element setContent(Content content2) {
        this.content.clear();
        this.content.add(content2);
        return this;
    }

    public boolean isAncestor(Element element) {
        for (Parent parent = element.getParent(); parent instanceof Element; parent = parent.getParent()) {
            if (parent == this) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAttributes() {
        AttributeList attributeList = this.attributes;
        return attributeList != null && !attributeList.isEmpty();
    }

    public boolean hasAdditionalNamespaces() {
        List<Namespace> list = this.additionalNamespaces;
        return list != null && !list.isEmpty();
    }

    /* access modifiers changed from: 0000 */
    public AttributeList getAttributeList() {
        if (this.attributes == null) {
            this.attributes = new AttributeList(this);
        }
        return this.attributes;
    }

    public List<Attribute> getAttributes() {
        return getAttributeList();
    }

    public Attribute getAttribute(String str) {
        return getAttribute(str, Namespace.NO_NAMESPACE);
    }

    public Attribute getAttribute(String str, Namespace namespace2) {
        if (this.attributes == null) {
            return null;
        }
        return getAttributeList().get(str, namespace2);
    }

    public String getAttributeValue(String str) {
        if (this.attributes == null) {
            return null;
        }
        return getAttributeValue(str, Namespace.NO_NAMESPACE);
    }

    public String getAttributeValue(String str, String str2) {
        if (this.attributes == null) {
            return str2;
        }
        return getAttributeValue(str, Namespace.NO_NAMESPACE, str2);
    }

    public String getAttributeValue(String str, Namespace namespace2) {
        if (this.attributes == null) {
            return null;
        }
        return getAttributeValue(str, namespace2, null);
    }

    public String getAttributeValue(String str, Namespace namespace2, String str2) {
        if (this.attributes == null) {
            return str2;
        }
        Attribute attribute = getAttributeList().get(str, namespace2);
        if (attribute == null) {
            return str2;
        }
        return attribute.getValue();
    }

    public Element setAttributes(Collection<? extends Attribute> collection) {
        getAttributeList().clearAndSet(collection);
        return this;
    }

    public Element setAttribute(String str, String str2) {
        Attribute attribute = getAttribute(str);
        if (attribute == null) {
            setAttribute(new Attribute(str, str2));
        } else {
            attribute.setValue(str2);
        }
        return this;
    }

    public Element setAttribute(String str, String str2, Namespace namespace2) {
        Attribute attribute = getAttribute(str, namespace2);
        if (attribute == null) {
            setAttribute(new Attribute(str, str2, namespace2));
        } else {
            attribute.setValue(str2);
        }
        return this;
    }

    public Element setAttribute(Attribute attribute) {
        getAttributeList().add(attribute);
        return this;
    }

    public boolean removeAttribute(String str) {
        return removeAttribute(str, Namespace.NO_NAMESPACE);
    }

    public boolean removeAttribute(String str, Namespace namespace2) {
        if (this.attributes == null) {
            return false;
        }
        return getAttributeList().remove(str, namespace2);
    }

    public boolean removeAttribute(Attribute attribute) {
        if (this.attributes == null) {
            return false;
        }
        return getAttributeList().remove(attribute);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("[Element: <");
        sb.append(getQualifiedName());
        String namespaceURI = getNamespaceURI();
        if (!"".equals(namespaceURI)) {
            sb.append(" [Namespace: ");
            sb.append(namespaceURI);
            sb.append("]");
        }
        sb.append("/>]");
        return sb.toString();
    }

    public Element clone() {
        Element element = (Element) super.clone();
        element.content = new ContentList(element);
        element.attributes = this.attributes == null ? null : new AttributeList(element);
        if (this.attributes != null) {
            for (int i = 0; i < this.attributes.size(); i++) {
                element.attributes.add(this.attributes.get(i).clone());
            }
        }
        List<Namespace> list = this.additionalNamespaces;
        if (list != null) {
            element.additionalNamespaces = new ArrayList(list);
        }
        for (int i2 = 0; i2 < this.content.size(); i2++) {
            element.content.add(this.content.get(i2).clone());
        }
        return element;
    }

    public IteratorIterable<Content> getDescendants() {
        return new DescendantIterator(this);
    }

    public <F extends Content> IteratorIterable<F> getDescendants(Filter<F> filter) {
        return new FilterIterator(new DescendantIterator(this), filter);
    }

    public List<Element> getChildren() {
        return this.content.getView(new ElementFilter());
    }

    public List<Element> getChildren(String str) {
        return getChildren(str, Namespace.NO_NAMESPACE);
    }

    public List<Element> getChildren(String str, Namespace namespace2) {
        return this.content.getView(new ElementFilter(str, namespace2));
    }

    public Element getChild(String str, Namespace namespace2) {
        Iterator it = this.content.getView(new ElementFilter(str, namespace2)).iterator();
        if (it.hasNext()) {
            return (Element) it.next();
        }
        return null;
    }

    public Element getChild(String str) {
        return getChild(str, Namespace.NO_NAMESPACE);
    }

    public boolean removeChild(String str) {
        return removeChild(str, Namespace.NO_NAMESPACE);
    }

    public boolean removeChild(String str, Namespace namespace2) {
        Iterator it = this.content.getView(new ElementFilter(str, namespace2)).iterator();
        if (!it.hasNext()) {
            return false;
        }
        it.next();
        it.remove();
        return true;
    }

    public boolean removeChildren(String str) {
        return removeChildren(str, Namespace.NO_NAMESPACE);
    }

    public boolean removeChildren(String str, Namespace namespace2) {
        Iterator it = this.content.getView(new ElementFilter(str, namespace2)).iterator();
        boolean z = false;
        while (it.hasNext()) {
            it.next();
            it.remove();
            z = true;
        }
        return z;
    }

    public List<Namespace> getNamespacesInScope() {
        TreeMap treeMap = new TreeMap();
        treeMap.put(Namespace.XML_NAMESPACE.getPrefix(), Namespace.XML_NAMESPACE);
        treeMap.put(getNamespacePrefix(), getNamespace());
        if (this.additionalNamespaces != null) {
            for (Namespace namespace2 : getAdditionalNamespaces()) {
                if (!treeMap.containsKey(namespace2.getPrefix())) {
                    treeMap.put(namespace2.getPrefix(), namespace2);
                }
            }
        }
        if (this.attributes != null) {
            for (Attribute namespace3 : getAttributes()) {
                Namespace namespace4 = namespace3.getNamespace();
                if (!treeMap.containsKey(namespace4.getPrefix())) {
                    treeMap.put(namespace4.getPrefix(), namespace4);
                }
            }
        }
        Element parentElement = getParentElement();
        if (parentElement != null) {
            for (Namespace namespace5 : parentElement.getNamespacesInScope()) {
                if (!treeMap.containsKey(namespace5.getPrefix())) {
                    treeMap.put(namespace5.getPrefix(), namespace5);
                }
            }
        }
        if (parentElement == null && !treeMap.containsKey("")) {
            treeMap.put(Namespace.NO_NAMESPACE.getPrefix(), Namespace.NO_NAMESPACE);
        }
        ArrayList arrayList = new ArrayList(treeMap.size());
        arrayList.add(getNamespace());
        treeMap.remove(getNamespacePrefix());
        arrayList.addAll(treeMap.values());
        return Collections.unmodifiableList(arrayList);
    }

    public List<Namespace> getNamespacesInherited() {
        if (getParentElement() == null) {
            ArrayList arrayList = new ArrayList(getNamespacesInScope());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Namespace namespace2 = (Namespace) it.next();
                if (!(namespace2 == Namespace.NO_NAMESPACE || namespace2 == Namespace.XML_NAMESPACE)) {
                    it.remove();
                }
            }
            return Collections.unmodifiableList(arrayList);
        }
        HashMap hashMap = new HashMap();
        for (Namespace namespace3 : getParentElement().getNamespacesInScope()) {
            hashMap.put(namespace3.getPrefix(), namespace3);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Namespace namespace4 : getNamespacesInScope()) {
            if (namespace4 == hashMap.get(namespace4.getPrefix())) {
                arrayList2.add(namespace4);
            }
        }
        return Collections.unmodifiableList(arrayList2);
    }

    public List<Namespace> getNamespacesIntroduced() {
        if (getParentElement() == null) {
            ArrayList arrayList = new ArrayList(getNamespacesInScope());
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Namespace namespace2 = (Namespace) it.next();
                if (namespace2 == Namespace.XML_NAMESPACE || namespace2 == Namespace.NO_NAMESPACE) {
                    it.remove();
                }
            }
            return Collections.unmodifiableList(arrayList);
        }
        HashMap hashMap = new HashMap();
        for (Namespace namespace3 : getParentElement().getNamespacesInScope()) {
            hashMap.put(namespace3.getPrefix(), namespace3);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Namespace namespace4 : getNamespacesInScope()) {
            if (!hashMap.containsKey(namespace4.getPrefix()) || namespace4 != hashMap.get(namespace4.getPrefix())) {
                arrayList2.add(namespace4);
            }
        }
        return Collections.unmodifiableList(arrayList2);
    }

    public Element detach() {
        return (Element) super.detach();
    }

    public void canContainContent(Content content2, int i, boolean z) throws IllegalAddException {
        if (content2 instanceof DocType) {
            throw new IllegalAddException("A DocType is not allowed except at the document level");
        }
    }

    public void sortContent(Comparator<? super Content> comparator) {
        this.content.sort(comparator);
    }

    public void sortChildren(Comparator<? super Element> comparator) {
        ((FilterList) getChildren()).sort(comparator);
    }

    public void sortAttributes(Comparator<? super Attribute> comparator) {
        AttributeList attributeList = this.attributes;
        if (attributeList != null) {
            attributeList.sort(comparator);
        }
    }

    public <E extends Content> void sortContent(Filter<E> filter, Comparator<? super E> comparator) {
        ((FilterList) getContent(filter)).sort(comparator);
    }

    private final URI resolve(String str, URI uri) throws URISyntaxException {
        if (str == null) {
            return uri;
        }
        URI uri2 = new URI(str);
        if (uri == null) {
            return uri2;
        }
        return uri2.resolve(uri);
    }

    public URI getXMLBaseURI() throws URISyntaxException {
        URI uri = null;
        for (Parent parent = this; parent != 0; parent = parent.getParent()) {
            if (parent instanceof Element) {
                uri = resolve(((Element) parent).getAttributeValue("base", Namespace.XML_NAMESPACE), uri);
            } else {
                uri = resolve(((Document) parent).getBaseURI(), uri);
            }
            if (uri != null && uri.isAbsolute()) {
                return uri;
            }
        }
        return uri;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        if (hasAdditionalNamespaces()) {
            int size = this.additionalNamespaces.size();
            objectOutputStream.writeInt(size);
            for (int i = 0; i < size; i++) {
                objectOutputStream.writeObject(this.additionalNamespaces.get(i));
            }
        } else {
            objectOutputStream.writeInt(0);
        }
        if (hasAttributes()) {
            int size2 = this.attributes.size();
            objectOutputStream.writeInt(size2);
            for (int i2 = 0; i2 < size2; i2++) {
                objectOutputStream.writeObject(this.attributes.get(i2));
            }
        } else {
            objectOutputStream.writeInt(0);
        }
        int size3 = this.content.size();
        objectOutputStream.writeInt(size3);
        for (int i3 = 0; i3 < size3; i3++) {
            objectOutputStream.writeObject(this.content.get(i3));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.content = new ContentList(this);
        int readInt = objectInputStream.readInt();
        while (true) {
            readInt--;
            if (readInt < 0) {
                break;
            }
            addNamespaceDeclaration((Namespace) objectInputStream.readObject());
        }
        int readInt2 = objectInputStream.readInt();
        while (true) {
            readInt2--;
            if (readInt2 < 0) {
                break;
            }
            setAttribute((Attribute) objectInputStream.readObject());
        }
        int readInt3 = objectInputStream.readInt();
        while (true) {
            readInt3--;
            if (readInt3 >= 0) {
                addContent((Content) objectInputStream.readObject());
            } else {
                return;
            }
        }
    }
}
