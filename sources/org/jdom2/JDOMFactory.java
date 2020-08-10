package org.jdom2;

import java.util.Map;

public interface JDOMFactory {
    void addContent(Parent parent, Content content);

    void addNamespaceDeclaration(Element element, Namespace namespace);

    Attribute attribute(String str, String str2);

    @Deprecated
    Attribute attribute(String str, String str2, int i);

    @Deprecated
    Attribute attribute(String str, String str2, int i, Namespace namespace);

    Attribute attribute(String str, String str2, AttributeType attributeType);

    Attribute attribute(String str, String str2, AttributeType attributeType, Namespace namespace);

    Attribute attribute(String str, String str2, Namespace namespace);

    CDATA cdata(int i, int i2, String str);

    CDATA cdata(String str);

    Comment comment(int i, int i2, String str);

    Comment comment(String str);

    DocType docType(int i, int i2, String str);

    DocType docType(int i, int i2, String str, String str2);

    DocType docType(int i, int i2, String str, String str2, String str3);

    DocType docType(String str);

    DocType docType(String str, String str2);

    DocType docType(String str, String str2, String str3);

    Document document(Element element);

    Document document(Element element, DocType docType);

    Document document(Element element, DocType docType, String str);

    Element element(int i, int i2, String str);

    Element element(int i, int i2, String str, String str2);

    Element element(int i, int i2, String str, String str2, String str3);

    Element element(int i, int i2, String str, Namespace namespace);

    Element element(String str);

    Element element(String str, String str2);

    Element element(String str, String str2, String str3);

    Element element(String str, Namespace namespace);

    EntityRef entityRef(int i, int i2, String str);

    EntityRef entityRef(int i, int i2, String str, String str2);

    EntityRef entityRef(int i, int i2, String str, String str2, String str3);

    EntityRef entityRef(String str);

    EntityRef entityRef(String str, String str2);

    EntityRef entityRef(String str, String str2, String str3);

    ProcessingInstruction processingInstruction(int i, int i2, String str);

    ProcessingInstruction processingInstruction(int i, int i2, String str, String str2);

    ProcessingInstruction processingInstruction(int i, int i2, String str, Map<String, String> map);

    ProcessingInstruction processingInstruction(String str);

    ProcessingInstruction processingInstruction(String str, String str2);

    ProcessingInstruction processingInstruction(String str, Map<String, String> map);

    void setAttribute(Element element, Attribute attribute);

    void setRoot(Document document, Element element);

    Text text(int i, int i2, String str);

    Text text(String str);
}
