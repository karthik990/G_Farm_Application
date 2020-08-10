package org.jdom2;

import java.util.ArrayList;
import java.util.Map;

public class UncheckedJDOMFactory extends DefaultJDOMFactory {
    public Element element(int i, int i2, String str, Namespace namespace) {
        Element element = new Element();
        element.name = str;
        if (namespace == null) {
            namespace = Namespace.NO_NAMESPACE;
        }
        element.namespace = namespace;
        return element;
    }

    public Element element(int i, int i2, String str) {
        Element element = new Element();
        element.name = str;
        element.namespace = Namespace.NO_NAMESPACE;
        return element;
    }

    public Element element(int i, int i2, String str, String str2) {
        return element(str, Namespace.getNamespace("", str2));
    }

    public Element element(int i, int i2, String str, String str2, String str3) {
        return element(str, Namespace.getNamespace(str2, str3));
    }

    public Attribute attribute(String str, String str2, Namespace namespace) {
        Attribute attribute = new Attribute();
        attribute.name = str;
        attribute.value = str2;
        if (namespace == null) {
            namespace = Namespace.NO_NAMESPACE;
        }
        attribute.namespace = namespace;
        return attribute;
    }

    @Deprecated
    public Attribute attribute(String str, String str2, int i, Namespace namespace) {
        return attribute(str, str2, AttributeType.byIndex(i), namespace);
    }

    public Attribute attribute(String str, String str2, AttributeType attributeType, Namespace namespace) {
        Attribute attribute = new Attribute();
        attribute.name = str;
        attribute.type = attributeType;
        attribute.value = str2;
        if (namespace == null) {
            namespace = Namespace.NO_NAMESPACE;
        }
        attribute.namespace = namespace;
        return attribute;
    }

    public Attribute attribute(String str, String str2) {
        Attribute attribute = new Attribute();
        attribute.name = str;
        attribute.value = str2;
        attribute.namespace = Namespace.NO_NAMESPACE;
        return attribute;
    }

    @Deprecated
    public Attribute attribute(String str, String str2, int i) {
        return attribute(str, str2, AttributeType.byIndex(i));
    }

    public Attribute attribute(String str, String str2, AttributeType attributeType) {
        Attribute attribute = new Attribute();
        attribute.name = str;
        attribute.type = attributeType;
        attribute.value = str2;
        attribute.namespace = Namespace.NO_NAMESPACE;
        return attribute;
    }

    public Text text(int i, int i2, String str) {
        Text text = new Text();
        text.value = str;
        return text;
    }

    public CDATA cdata(int i, int i2, String str) {
        CDATA cdata = new CDATA();
        cdata.value = str;
        return cdata;
    }

    public Comment comment(int i, int i2, String str) {
        Comment comment = new Comment();
        comment.text = str;
        return comment;
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str, Map<String, String> map) {
        ProcessingInstruction processingInstruction = new ProcessingInstruction();
        processingInstruction.target = str;
        processingInstruction.setData(map);
        return processingInstruction;
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str, String str2) {
        ProcessingInstruction processingInstruction = new ProcessingInstruction();
        processingInstruction.target = str;
        processingInstruction.setData(str2);
        return processingInstruction;
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str) {
        ProcessingInstruction processingInstruction = new ProcessingInstruction();
        processingInstruction.target = str;
        processingInstruction.rawData = "";
        return processingInstruction;
    }

    public EntityRef entityRef(int i, int i2, String str) {
        EntityRef entityRef = new EntityRef();
        entityRef.name = str;
        return entityRef;
    }

    public EntityRef entityRef(int i, int i2, String str, String str2) {
        EntityRef entityRef = new EntityRef();
        entityRef.name = str;
        entityRef.systemID = str2;
        return entityRef;
    }

    public EntityRef entityRef(int i, int i2, String str, String str2, String str3) {
        EntityRef entityRef = new EntityRef();
        entityRef.name = str;
        entityRef.publicID = str2;
        entityRef.systemID = str3;
        return entityRef;
    }

    public DocType docType(int i, int i2, String str, String str2, String str3) {
        DocType docType = new DocType();
        docType.elementName = str;
        docType.publicID = str2;
        docType.systemID = str3;
        return docType;
    }

    public DocType docType(int i, int i2, String str, String str2) {
        return docType(str, (String) null, str2);
    }

    public DocType docType(int i, int i2, String str) {
        return docType(str, (String) null, (String) null);
    }

    public Document document(Element element, DocType docType, String str) {
        Document document = new Document();
        if (docType != null) {
            addContent(document, docType);
        }
        if (element != null) {
            addContent(document, element);
        }
        if (str != null) {
            document.baseURI = str;
        }
        return document;
    }

    public Document document(Element element, DocType docType) {
        return document(element, docType, null);
    }

    public Document document(Element element) {
        return document(element, null, null);
    }

    public void addContent(Parent parent, Content content) {
        if (parent instanceof Element) {
            ((Element) parent).content.uncheckedAddContent(content);
        } else {
            ((Document) parent).content.uncheckedAddContent(content);
        }
    }

    public void setAttribute(Element element, Attribute attribute) {
        element.getAttributeList().uncheckedAddAttribute(attribute);
    }

    public void addNamespaceDeclaration(Element element, Namespace namespace) {
        if (element.additionalNamespaces == null) {
            element.additionalNamespaces = new ArrayList(5);
        }
        element.additionalNamespaces.add(namespace);
    }

    public void setRoot(Document document, Element element) {
        document.content.uncheckedAddContent(element);
    }
}
