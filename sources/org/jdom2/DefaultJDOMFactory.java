package org.jdom2;

import java.util.Map;

public class DefaultJDOMFactory implements JDOMFactory {
    public Attribute attribute(String str, String str2, Namespace namespace) {
        return new Attribute(str, str2, namespace);
    }

    @Deprecated
    public Attribute attribute(String str, String str2, int i, Namespace namespace) {
        return new Attribute(str, str2, AttributeType.byIndex(i), namespace);
    }

    public Attribute attribute(String str, String str2, AttributeType attributeType, Namespace namespace) {
        return new Attribute(str, str2, attributeType, namespace);
    }

    public Attribute attribute(String str, String str2) {
        return new Attribute(str, str2);
    }

    @Deprecated
    public Attribute attribute(String str, String str2, int i) {
        return new Attribute(str, str2, i);
    }

    public Attribute attribute(String str, String str2, AttributeType attributeType) {
        return new Attribute(str, str2, attributeType);
    }

    public final CDATA cdata(String str) {
        return cdata(-1, -1, str);
    }

    public CDATA cdata(int i, int i2, String str) {
        return new CDATA(str);
    }

    public final Text text(String str) {
        return text(-1, -1, str);
    }

    public Text text(int i, int i2, String str) {
        return new Text(str);
    }

    public final Comment comment(String str) {
        return comment(-1, -1, str);
    }

    public Comment comment(int i, int i2, String str) {
        return new Comment(str);
    }

    public final DocType docType(String str, String str2, String str3) {
        return docType(-1, -1, str, str2, str3);
    }

    public DocType docType(int i, int i2, String str, String str2, String str3) {
        return new DocType(str, str2, str3);
    }

    public final DocType docType(String str, String str2) {
        return docType(-1, -1, str, str2);
    }

    public DocType docType(int i, int i2, String str, String str2) {
        return new DocType(str, str2);
    }

    public final DocType docType(String str) {
        return docType(-1, -1, str);
    }

    public DocType docType(int i, int i2, String str) {
        return new DocType(str);
    }

    public Document document(Element element, DocType docType) {
        return new Document(element, docType);
    }

    public Document document(Element element, DocType docType, String str) {
        return new Document(element, docType, str);
    }

    public Document document(Element element) {
        return new Document(element);
    }

    public final Element element(String str, Namespace namespace) {
        return element(-1, -1, str, namespace);
    }

    public Element element(int i, int i2, String str, Namespace namespace) {
        return new Element(str, namespace);
    }

    public final Element element(String str) {
        return element(-1, -1, str);
    }

    public Element element(int i, int i2, String str) {
        return new Element(str);
    }

    public final Element element(String str, String str2) {
        return element(-1, -1, str, str2);
    }

    public Element element(int i, int i2, String str, String str2) {
        return new Element(str, str2);
    }

    public final Element element(String str, String str2, String str3) {
        return element(-1, -1, str, str2, str3);
    }

    public Element element(int i, int i2, String str, String str2, String str3) {
        return new Element(str, str2, str3);
    }

    public final ProcessingInstruction processingInstruction(String str) {
        return processingInstruction(-1, -1, str);
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str) {
        return new ProcessingInstruction(str);
    }

    public final ProcessingInstruction processingInstruction(String str, Map<String, String> map) {
        return processingInstruction(-1, -1, str, map);
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str, Map<String, String> map) {
        return new ProcessingInstruction(str, map);
    }

    public final ProcessingInstruction processingInstruction(String str, String str2) {
        return processingInstruction(-1, -1, str, str2);
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str, String str2) {
        return new ProcessingInstruction(str, str2);
    }

    public final EntityRef entityRef(String str) {
        return entityRef(-1, -1, str);
    }

    public EntityRef entityRef(int i, int i2, String str) {
        return new EntityRef(str);
    }

    public final EntityRef entityRef(String str, String str2, String str3) {
        return entityRef(-1, -1, str, str2, str3);
    }

    public EntityRef entityRef(int i, int i2, String str, String str2, String str3) {
        return new EntityRef(str, str2, str3);
    }

    public final EntityRef entityRef(String str, String str2) {
        return entityRef(-1, -1, str, str2);
    }

    public EntityRef entityRef(int i, int i2, String str, String str2) {
        return new EntityRef(str, str2);
    }

    public void addContent(Parent parent, Content content) {
        if (parent instanceof Document) {
            ((Document) parent).addContent(content);
        } else {
            ((Element) parent).addContent(content);
        }
    }

    public void setAttribute(Element element, Attribute attribute) {
        element.setAttribute(attribute);
    }

    public void addNamespaceDeclaration(Element element, Namespace namespace) {
        element.addNamespaceDeclaration(namespace);
    }

    public void setRoot(Document document, Element element) {
        document.setRootElement(element);
    }
}
