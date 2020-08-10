package org.jdom2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jdom2.filter.Filter;
import org.jdom2.util.IteratorIterable;

public class Document extends CloneBase implements Parent {
    private static final long serialVersionUID = 200;
    protected String baseURI;
    transient ContentList content;
    private transient HashMap<String, Object> propertyMap;

    public final boolean equals(Object obj) {
        return obj == this;
    }

    public Document getDocument() {
        return this;
    }

    public Parent getParent() {
        return null;
    }

    public Document() {
        this.content = new ContentList(this);
        this.baseURI = null;
        this.propertyMap = null;
    }

    public Document(Element element, DocType docType, String str) {
        this.content = new ContentList(this);
        this.baseURI = null;
        this.propertyMap = null;
        if (element != null) {
            setRootElement(element);
        }
        if (docType != null) {
            setDocType(docType);
        }
        if (str != null) {
            setBaseURI(str);
        }
    }

    public Document(Element element, DocType docType) {
        this(element, docType, null);
    }

    public Document(Element element) {
        this(element, null, null);
    }

    public Document(List<? extends Content> list) {
        this.content = new ContentList(this);
        this.baseURI = null;
        this.propertyMap = null;
        setContent((Collection<? extends Content>) list);
    }

    public int getContentSize() {
        return this.content.size();
    }

    public int indexOf(Content content2) {
        return this.content.indexOf(content2);
    }

    public boolean hasRootElement() {
        return this.content.indexOfFirstElement() >= 0;
    }

    public Element getRootElement() {
        int indexOfFirstElement = this.content.indexOfFirstElement();
        if (indexOfFirstElement >= 0) {
            return (Element) this.content.get(indexOfFirstElement);
        }
        throw new IllegalStateException("Root element not set");
    }

    public Document setRootElement(Element element) {
        int indexOfFirstElement = this.content.indexOfFirstElement();
        if (indexOfFirstElement < 0) {
            this.content.add(element);
        } else {
            this.content.set(indexOfFirstElement, (Content) element);
        }
        return this;
    }

    public Element detachRootElement() {
        int indexOfFirstElement = this.content.indexOfFirstElement();
        if (indexOfFirstElement < 0) {
            return null;
        }
        return (Element) removeContent(indexOfFirstElement);
    }

    public DocType getDocType() {
        int indexOfDocType = this.content.indexOfDocType();
        if (indexOfDocType < 0) {
            return null;
        }
        return (DocType) this.content.get(indexOfDocType);
    }

    public Document setDocType(DocType docType) {
        if (docType == null) {
            int indexOfDocType = this.content.indexOfDocType();
            if (indexOfDocType >= 0) {
                this.content.remove(indexOfDocType);
            }
            return this;
        } else if (docType.getParent() == null) {
            int indexOfDocType2 = this.content.indexOfDocType();
            if (indexOfDocType2 < 0) {
                this.content.add(0, (Content) docType);
            } else {
                this.content.set(indexOfDocType2, (Content) docType);
            }
            return this;
        } else {
            throw new IllegalAddException(docType, "The DocType already is attached to a document");
        }
    }

    public Document addContent(Content content2) {
        this.content.add(content2);
        return this;
    }

    public Document addContent(Collection<? extends Content> collection) {
        this.content.addAll(collection);
        return this;
    }

    public Document addContent(int i, Content content2) {
        this.content.add(i, content2);
        return this;
    }

    public Document addContent(int i, Collection<? extends Content> collection) {
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

    public List<Content> getContent() {
        if (hasRootElement()) {
            return this.content;
        }
        throw new IllegalStateException("Root element not set");
    }

    public <F extends Content> List<F> getContent(Filter<F> filter) {
        if (hasRootElement()) {
            return this.content.getView(filter);
        }
        throw new IllegalStateException("Root element not set");
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

    public Document setContent(Collection<? extends Content> collection) {
        this.content.clearAndSet(collection);
        return this;
    }

    public final void setBaseURI(String str) {
        this.baseURI = str;
    }

    public final String getBaseURI() {
        return this.baseURI;
    }

    public Document setContent(int i, Content content2) {
        this.content.set(i, content2);
        return this;
    }

    public Document setContent(int i, Collection<? extends Content> collection) {
        this.content.remove(i);
        this.content.addAll(i, collection);
        return this;
    }

    public boolean removeContent(Content content2) {
        return this.content.remove(content2);
    }

    public Content removeContent(int i) {
        return this.content.remove(i);
    }

    public Document setContent(Content content2) {
        this.content.clear();
        this.content.add(content2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Document: ");
        DocType docType = getDocType();
        if (docType != null) {
            sb.append(docType.toString());
            sb.append(", ");
        } else {
            sb.append(" No DOCTYPE declaration, ");
        }
        Element rootElement = hasRootElement() ? getRootElement() : null;
        if (rootElement != null) {
            sb.append("Root is ");
            sb.append(rootElement.toString());
        } else {
            sb.append(" No root element");
        }
        sb.append("]");
        return sb.toString();
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public Document clone() {
        Document document = (Document) super.clone();
        document.content = new ContentList(document);
        for (int i = 0; i < this.content.size(); i++) {
            Content content2 = this.content.get(i);
            if (content2 instanceof Element) {
                document.content.add(((Element) content2).clone());
            } else if (content2 instanceof Comment) {
                document.content.add(((Comment) content2).clone());
            } else if (content2 instanceof ProcessingInstruction) {
                document.content.add(((ProcessingInstruction) content2).clone());
            } else if (content2 instanceof DocType) {
                document.content.add(((DocType) content2).clone());
            }
        }
        return document;
    }

    public IteratorIterable<Content> getDescendants() {
        return new DescendantIterator(this);
    }

    public <F extends Content> IteratorIterable<F> getDescendants(Filter<F> filter) {
        return new FilterIterator(new DescendantIterator(this), filter);
    }

    public void setProperty(String str, Object obj) {
        if (this.propertyMap == null) {
            this.propertyMap = new HashMap<>();
        }
        this.propertyMap.put(str, obj);
    }

    public Object getProperty(String str) {
        HashMap<String, Object> hashMap = this.propertyMap;
        if (hashMap == null) {
            return null;
        }
        return hashMap.get(str);
    }

    public void canContainContent(Content content2, int i, boolean z) {
        if (content2 instanceof Element) {
            int indexOfFirstElement = this.content.indexOfFirstElement();
            if (z && indexOfFirstElement == i) {
                return;
            }
            if (indexOfFirstElement >= 0) {
                throw new IllegalAddException("Cannot add a second root element, only one is allowed");
            } else if (this.content.indexOfDocType() >= i) {
                throw new IllegalAddException("A root element cannot be added before the DocType");
            }
        }
        if (content2 instanceof DocType) {
            int indexOfDocType = this.content.indexOfDocType();
            if (z && indexOfDocType == i) {
                return;
            }
            if (indexOfDocType < 0) {
                int indexOfFirstElement2 = this.content.indexOfFirstElement();
                if (indexOfFirstElement2 != -1 && indexOfFirstElement2 < i) {
                    throw new IllegalAddException("A DocType cannot be added after the root element");
                }
            } else {
                throw new IllegalAddException("Cannot add a second doctype, only one is allowed");
            }
        }
        if (content2 instanceof CDATA) {
            throw new IllegalAddException("A CDATA is not allowed at the document root");
        } else if (content2 instanceof Text) {
            throw new IllegalAddException("A Text is not allowed at the document root");
        } else if (content2 instanceof EntityRef) {
            throw new IllegalAddException("An EntityRef is not allowed at the document root");
        }
    }

    public List<Namespace> getNamespacesInScope() {
        return Collections.unmodifiableList(Arrays.asList(new Namespace[]{Namespace.NO_NAMESPACE, Namespace.XML_NAMESPACE}));
    }

    public List<Namespace> getNamespacesIntroduced() {
        return Collections.unmodifiableList(Arrays.asList(new Namespace[]{Namespace.NO_NAMESPACE, Namespace.XML_NAMESPACE}));
    }

    public List<Namespace> getNamespacesInherited() {
        return Collections.emptyList();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int size = this.content.size();
        objectOutputStream.writeInt(size);
        for (int i = 0; i < size; i++) {
            objectOutputStream.writeObject(getContent(i));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.content = new ContentList(this);
        int readInt = objectInputStream.readInt();
        while (true) {
            readInt--;
            if (readInt >= 0) {
                addContent((Content) objectInputStream.readObject());
            } else {
                return;
            }
        }
    }
}
