package org.jdom2.output.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.http.client.config.CookieSpecs;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.EntityRef;
import org.jdom2.JDOMConstants;
import org.jdom2.Namespace;
import org.jdom2.output.Format;
import org.jdom2.output.Format.TextMode;
import org.jdom2.util.NamespaceStack;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public abstract class AbstractDOMOutputProcessor extends AbstractOutputProcessor implements DOMOutputProcessor {
    private static String getXmlnsTagFor(Namespace namespace) {
        boolean equals = namespace.getPrefix().equals("");
        String str = JDOMConstants.NS_PREFIX_XMLNS;
        if (equals) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(":");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(namespace.getPrefix());
        return sb3.toString();
    }

    public Document process(Document document, Format format, org.jdom2.Document document2) {
        return printDocument(new FormatStack(format), new NamespaceStack(), document, document2);
    }

    public Element process(Document document, Format format, org.jdom2.Element element) {
        return printElement(new FormatStack(format), new NamespaceStack(), document, element);
    }

    public List<Node> process(Document document, Format format, List<? extends Content> list) {
        ArrayList arrayList = new ArrayList(list.size());
        FormatStack formatStack = new FormatStack(format);
        NamespaceStack namespaceStack = new NamespaceStack();
        for (Content content : list) {
            formatStack.push();
            try {
                Node helperContentDispatcher = helperContentDispatcher(formatStack, namespaceStack, document, content);
                if (helperContentDispatcher != null) {
                    arrayList.add(helperContentDispatcher);
                }
            } finally {
                formatStack.pop();
            }
        }
        return arrayList;
    }

    public CDATASection process(Document document, Format format, CDATA cdata) {
        List singletonList = Collections.singletonList(cdata);
        FormatStack formatStack = new FormatStack(format);
        Walker buildWalker = buildWalker(formatStack, singletonList, false);
        if (buildWalker.hasNext()) {
            Content next = buildWalker.next();
            if (next == null) {
                return printCDATA(formatStack, document, new CDATA(buildWalker.text()));
            }
            if (next.getCType() == CType.CDATA) {
                return printCDATA(formatStack, document, (CDATA) next);
            }
        }
        return null;
    }

    public Text process(Document document, Format format, org.jdom2.Text text) {
        List singletonList = Collections.singletonList(text);
        FormatStack formatStack = new FormatStack(format);
        Walker buildWalker = buildWalker(formatStack, singletonList, false);
        if (buildWalker.hasNext()) {
            Content next = buildWalker.next();
            if (next == null) {
                return printText(formatStack, document, new org.jdom2.Text(buildWalker.text()));
            }
            if (next.getCType() == CType.Text) {
                return printText(formatStack, document, (org.jdom2.Text) next);
            }
        }
        return null;
    }

    public Comment process(Document document, Format format, org.jdom2.Comment comment) {
        return printComment(new FormatStack(format), document, comment);
    }

    public ProcessingInstruction process(Document document, Format format, org.jdom2.ProcessingInstruction processingInstruction) {
        return printProcessingInstruction(new FormatStack(format), document, processingInstruction);
    }

    public EntityReference process(Document document, Format format, EntityRef entityRef) {
        return printEntityRef(new FormatStack(format), document, entityRef);
    }

    public Attr process(Document document, Format format, Attribute attribute) {
        return printAttribute(new FormatStack(format), document, attribute);
    }

    /* access modifiers changed from: protected */
    public Document printDocument(FormatStack formatStack, NamespaceStack namespaceStack, Document document, org.jdom2.Document document2) {
        if (!formatStack.isOmitDeclaration()) {
            document.setXmlVersion("1.0");
        }
        int contentSize = document2.getContentSize();
        if (contentSize > 0) {
            for (int i = 0; i < contentSize; i++) {
                Content content = document2.getContent(i);
                Node node = null;
                int i2 = C61371.$SwitchMap$org$jdom2$Content$CType[content.getCType().ordinal()];
                if (i2 == 1) {
                    node = printComment(formatStack, document, (org.jdom2.Comment) content);
                } else if (i2 != 2) {
                    if (i2 == 3) {
                        node = printElement(formatStack, namespaceStack, document, (org.jdom2.Element) content);
                    } else if (i2 == 4) {
                        node = printProcessingInstruction(formatStack, document, (org.jdom2.ProcessingInstruction) content);
                    }
                }
                if (node != null) {
                    document.appendChild(node);
                }
            }
        }
        return document;
    }

    /* access modifiers changed from: protected */
    public ProcessingInstruction printProcessingInstruction(FormatStack formatStack, Document document, org.jdom2.ProcessingInstruction processingInstruction) {
        String target = processingInstruction.getTarget();
        String data = processingInstruction.getData();
        if (data == null || data.trim().length() == 0) {
            data = "";
        }
        return document.createProcessingInstruction(target, data);
    }

    /* access modifiers changed from: protected */
    public Comment printComment(FormatStack formatStack, Document document, org.jdom2.Comment comment) {
        return document.createComment(comment.getText());
    }

    /* access modifiers changed from: protected */
    public EntityReference printEntityRef(FormatStack formatStack, Document document, EntityRef entityRef) {
        return document.createEntityReference(entityRef.getName());
    }

    /* access modifiers changed from: protected */
    public CDATASection printCDATA(FormatStack formatStack, Document document, CDATA cdata) {
        return document.createCDATASection(cdata.getText());
    }

    /* access modifiers changed from: protected */
    public Text printText(FormatStack formatStack, Document document, org.jdom2.Text text) {
        return document.createTextNode(text.getText());
    }

    /* access modifiers changed from: protected */
    public Attr printAttribute(FormatStack formatStack, Document document, Attribute attribute) {
        if (!attribute.isSpecified() && formatStack.isSpecifiedAttributesOnly()) {
            return null;
        }
        Attr createAttributeNS = document.createAttributeNS(attribute.getNamespaceURI(), attribute.getQualifiedName());
        createAttributeNS.setValue(attribute.getValue());
        return createAttributeNS;
    }

    /* access modifiers changed from: protected */
    public Element printElement(FormatStack formatStack, NamespaceStack namespaceStack, Document document, org.jdom2.Element element) {
        namespaceStack.push(element);
        try {
            TextMode textMode = formatStack.getTextMode();
            String attributeValue = element.getAttributeValue("space", Namespace.XML_NAMESPACE);
            if (CookieSpecs.DEFAULT.equals(attributeValue)) {
                textMode = formatStack.getDefaultMode();
            } else if ("preserve".equals(attributeValue)) {
                textMode = TextMode.PRESERVE;
            }
            Element createElementNS = document.createElementNS(element.getNamespaceURI(), element.getQualifiedName());
            for (Namespace namespace : namespaceStack.addedForward()) {
                if (namespace != Namespace.XML_NAMESPACE) {
                    createElementNS.setAttributeNS(JDOMConstants.NS_URI_XMLNS, getXmlnsTagFor(namespace), namespace.getURI());
                }
            }
            if (element.hasAttributes()) {
                for (Attribute printAttribute : element.getAttributes()) {
                    Attr printAttribute2 = printAttribute(formatStack, document, printAttribute);
                    if (printAttribute2 != null) {
                        createElementNS.setAttributeNodeNS(printAttribute2);
                    }
                }
            }
            List content = element.getContent();
            if (!content.isEmpty()) {
                formatStack.push();
                formatStack.setTextMode(textMode);
                Walker buildWalker = buildWalker(formatStack, content, false);
                if (!buildWalker.isAllText() && formatStack.getPadBetween() != null) {
                    createElementNS.appendChild(document.createTextNode(formatStack.getPadBetween()));
                }
                printContent(formatStack, namespaceStack, document, createElementNS, buildWalker);
                if (!buildWalker.isAllText() && formatStack.getPadLast() != null) {
                    createElementNS.appendChild(document.createTextNode(formatStack.getPadLast()));
                }
                formatStack.pop();
            }
            namespaceStack.pop();
            return createElementNS;
        } catch (Throwable th) {
            namespaceStack.pop();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void printContent(FormatStack formatStack, NamespaceStack namespaceStack, Document document, Node node, Walker walker) {
        Node node2;
        while (walker.hasNext()) {
            Content next = walker.next();
            if (next == null) {
                String text = walker.text();
                if (walker.isCDATA()) {
                    node2 = printCDATA(formatStack, document, new CDATA(text));
                } else {
                    node2 = printText(formatStack, document, new org.jdom2.Text(text));
                }
            } else {
                node2 = helperContentDispatcher(formatStack, namespaceStack, document, next);
            }
            if (node2 != null) {
                node.appendChild(node2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Node helperContentDispatcher(FormatStack formatStack, NamespaceStack namespaceStack, Document document, Content content) {
        switch (content.getCType()) {
            case Comment:
                return printComment(formatStack, document, (org.jdom2.Comment) content);
            case DocType:
                return null;
            case Element:
                return printElement(formatStack, namespaceStack, document, (org.jdom2.Element) content);
            case ProcessingInstruction:
                return printProcessingInstruction(formatStack, document, (org.jdom2.ProcessingInstruction) content);
            case CDATA:
                return printCDATA(formatStack, document, (CDATA) content);
            case EntityRef:
                return printEntityRef(formatStack, document, (EntityRef) content);
            case Text:
                return printText(formatStack, document, (org.jdom2.Text) content);
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected Content ");
                sb.append(content.getCType());
                throw new IllegalStateException(sb.toString());
        }
    }
}
