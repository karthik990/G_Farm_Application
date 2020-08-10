package org.jdom2.output.support;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.http.client.config.CookieSpecs;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.Namespace;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.Verifier;
import org.jdom2.output.Format;
import org.jdom2.output.Format.TextMode;
import org.jdom2.util.NamespaceStack;

public abstract class AbstractStAXStreamProcessor extends AbstractOutputProcessor implements StAXStreamProcessor {
    public void process(XMLStreamWriter xMLStreamWriter, Format format, Document document) throws XMLStreamException {
        printDocument(xMLStreamWriter, new FormatStack(format), new NamespaceStack(), document);
        xMLStreamWriter.flush();
    }

    public void process(XMLStreamWriter xMLStreamWriter, Format format, DocType docType) throws XMLStreamException {
        printDocType(xMLStreamWriter, new FormatStack(format), docType);
        xMLStreamWriter.flush();
    }

    public void process(XMLStreamWriter xMLStreamWriter, Format format, Element element) throws XMLStreamException {
        printElement(xMLStreamWriter, new FormatStack(format), new NamespaceStack(), element);
        xMLStreamWriter.flush();
    }

    public void process(XMLStreamWriter xMLStreamWriter, Format format, List<? extends Content> list) throws XMLStreamException {
        FormatStack formatStack = new FormatStack(format);
        printContent(xMLStreamWriter, formatStack, new NamespaceStack(), buildWalker(formatStack, list, false));
        xMLStreamWriter.flush();
    }

    public void process(XMLStreamWriter xMLStreamWriter, Format format, CDATA cdata) throws XMLStreamException {
        List singletonList = Collections.singletonList(cdata);
        FormatStack formatStack = new FormatStack(format);
        Walker buildWalker = buildWalker(formatStack, singletonList, false);
        if (buildWalker.hasNext()) {
            Content next = buildWalker.next();
            if (next == null) {
                printCDATA(xMLStreamWriter, formatStack, new CDATA(buildWalker.text()));
            } else if (next.getCType() == CType.CDATA) {
                printCDATA(xMLStreamWriter, formatStack, (CDATA) next);
            }
        }
        xMLStreamWriter.flush();
    }

    public void process(XMLStreamWriter xMLStreamWriter, Format format, Text text) throws XMLStreamException {
        List singletonList = Collections.singletonList(text);
        FormatStack formatStack = new FormatStack(format);
        Walker buildWalker = buildWalker(formatStack, singletonList, false);
        if (buildWalker.hasNext()) {
            Content next = buildWalker.next();
            if (next == null) {
                printText(xMLStreamWriter, formatStack, new Text(buildWalker.text()));
            } else if (next.getCType() == CType.Text) {
                printText(xMLStreamWriter, formatStack, (Text) next);
            }
        }
        xMLStreamWriter.flush();
    }

    public void process(XMLStreamWriter xMLStreamWriter, Format format, Comment comment) throws XMLStreamException {
        printComment(xMLStreamWriter, new FormatStack(format), comment);
        xMLStreamWriter.flush();
    }

    public void process(XMLStreamWriter xMLStreamWriter, Format format, ProcessingInstruction processingInstruction) throws XMLStreamException {
        FormatStack formatStack = new FormatStack(format);
        formatStack.setIgnoreTrAXEscapingPIs(true);
        printProcessingInstruction(xMLStreamWriter, formatStack, processingInstruction);
        xMLStreamWriter.flush();
    }

    public void process(XMLStreamWriter xMLStreamWriter, Format format, EntityRef entityRef) throws XMLStreamException {
        printEntityRef(xMLStreamWriter, new FormatStack(format), entityRef);
        xMLStreamWriter.flush();
    }

    /* access modifiers changed from: protected */
    public void printDocument(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, NamespaceStack namespaceStack, Document document) throws XMLStreamException {
        if (formatStack.isOmitDeclaration()) {
            xMLStreamWriter.writeStartDocument();
            if (formatStack.getLineSeparator() != null) {
                xMLStreamWriter.writeCharacters(formatStack.getLineSeparator());
            }
        } else {
            String str = "1.0";
            if (formatStack.isOmitEncoding()) {
                xMLStreamWriter.writeStartDocument(str);
                if (formatStack.getLineSeparator() != null) {
                    xMLStreamWriter.writeCharacters(formatStack.getLineSeparator());
                }
            } else {
                xMLStreamWriter.writeStartDocument(formatStack.getEncoding(), str);
                if (formatStack.getLineSeparator() != null) {
                    xMLStreamWriter.writeCharacters(formatStack.getLineSeparator());
                }
            }
        }
        List content = document.hasRootElement() ? document.getContent() : new ArrayList(document.getContentSize());
        if (content.isEmpty()) {
            int contentSize = document.getContentSize();
            for (int i = 0; i < contentSize; i++) {
                content.add(document.getContent(i));
            }
        }
        Walker buildWalker = buildWalker(formatStack, content, false);
        if (buildWalker.hasNext()) {
            while (buildWalker.hasNext()) {
                Content next = buildWalker.next();
                if (next == null) {
                    String text = buildWalker.text();
                    if (text != null && Verifier.isAllXMLWhitespace(text) && !buildWalker.isCDATA()) {
                        xMLStreamWriter.writeCharacters(text);
                    }
                } else {
                    int i2 = C61431.$SwitchMap$org$jdom2$Content$CType[next.getCType().ordinal()];
                    if (i2 == 1) {
                        printComment(xMLStreamWriter, formatStack, (Comment) next);
                    } else if (i2 == 2) {
                        printDocType(xMLStreamWriter, formatStack, (DocType) next);
                    } else if (i2 == 3) {
                        printElement(xMLStreamWriter, formatStack, namespaceStack, (Element) next);
                    } else if (i2 == 4) {
                        printProcessingInstruction(xMLStreamWriter, formatStack, (ProcessingInstruction) next);
                    } else if (i2 == 5) {
                        String text2 = ((Text) next).getText();
                        if (text2 != null && Verifier.isAllXMLWhitespace(text2)) {
                            xMLStreamWriter.writeCharacters(text2);
                        }
                    }
                }
            }
            if (formatStack.getLineSeparator() != null) {
                xMLStreamWriter.writeCharacters(formatStack.getLineSeparator());
            }
        }
        xMLStreamWriter.writeEndDocument();
    }

    /* access modifiers changed from: protected */
    public void printDocType(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, DocType docType) throws XMLStreamException {
        boolean z;
        String publicID = docType.getPublicID();
        String systemID = docType.getSystemID();
        String internalSubset = docType.getInternalSubset();
        StringWriter stringWriter = new StringWriter();
        stringWriter.write("<!DOCTYPE ");
        stringWriter.write(docType.getElementName());
        String str = "\"";
        if (publicID != null) {
            stringWriter.write(" PUBLIC \"");
            stringWriter.write(publicID);
            stringWriter.write(str);
            z = true;
        } else {
            z = false;
        }
        if (systemID != null) {
            if (!z) {
                stringWriter.write(" SYSTEM");
            }
            stringWriter.write(" \"");
            stringWriter.write(systemID);
            stringWriter.write(str);
        }
        if (internalSubset != null && !internalSubset.equals("")) {
            stringWriter.write(" [");
            stringWriter.write(formatStack.getLineSeparator());
            stringWriter.write(docType.getInternalSubset());
            stringWriter.write("]");
        }
        stringWriter.write(">");
        xMLStreamWriter.writeDTD(stringWriter.toString());
    }

    /* access modifiers changed from: protected */
    public void printProcessingInstruction(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, ProcessingInstruction processingInstruction) throws XMLStreamException {
        String target = processingInstruction.getTarget();
        String data = processingInstruction.getData();
        if (data == null || data.trim().length() <= 0) {
            xMLStreamWriter.writeProcessingInstruction(target);
        } else {
            xMLStreamWriter.writeProcessingInstruction(target, data);
        }
    }

    /* access modifiers changed from: protected */
    public void printComment(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, Comment comment) throws XMLStreamException {
        xMLStreamWriter.writeComment(comment.getText());
    }

    /* access modifiers changed from: protected */
    public void printEntityRef(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, EntityRef entityRef) throws XMLStreamException {
        xMLStreamWriter.writeEntityRef(entityRef.getName());
    }

    /* access modifiers changed from: protected */
    public void printCDATA(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, CDATA cdata) throws XMLStreamException {
        xMLStreamWriter.writeCData(cdata.getText());
    }

    /* access modifiers changed from: protected */
    public void printText(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, Text text) throws XMLStreamException {
        xMLStreamWriter.writeCharacters(text.getText());
    }

    /* access modifiers changed from: protected */
    public void printElement(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, NamespaceStack namespaceStack, Element element) throws XMLStreamException {
        String str = "";
        ArrayList arrayList = new ArrayList();
        namespaceStack.push(element);
        for (Namespace namespace : namespaceStack.addedForward()) {
            arrayList.add(namespace.getPrefix());
            if (str.equals(namespace.getPrefix())) {
                xMLStreamWriter.setDefaultNamespace(namespace.getURI());
            } else {
                xMLStreamWriter.setPrefix(namespace.getPrefix(), namespace.getURI());
            }
        }
        List content = element.getContent();
        TextMode textMode = formatStack.getTextMode();
        boolean z = false;
        Walker walker = null;
        if (!content.isEmpty()) {
            String attributeValue = element.getAttributeValue("space", Namespace.XML_NAMESPACE);
            if (CookieSpecs.DEFAULT.equals(attributeValue)) {
                textMode = formatStack.getDefaultMode();
            } else if ("preserve".equals(attributeValue)) {
                textMode = TextMode.PRESERVE;
            }
            formatStack.push();
            try {
                formatStack.setTextMode(textMode);
                Walker buildWalker = buildWalker(formatStack, content, false);
                if (buildWalker.hasNext()) {
                    walker = buildWalker;
                }
                formatStack.pop();
            } catch (Throwable th) {
                namespaceStack.pop();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    Iterator it2 = namespaceStack.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        Namespace namespace2 = (Namespace) it2.next();
                        if (namespace2.getPrefix().equals(str2)) {
                            if (str.equals(namespace2.getPrefix())) {
                                xMLStreamWriter.setDefaultNamespace(namespace2.getURI());
                            } else {
                                xMLStreamWriter.setPrefix(namespace2.getPrefix(), namespace2.getURI());
                            }
                        }
                    }
                }
                throw th;
            }
        }
        if (walker != null || formatStack.isExpandEmptyElements()) {
            z = true;
        }
        Namespace namespace3 = element.getNamespace();
        if (z) {
            xMLStreamWriter.writeStartElement(namespace3.getPrefix(), element.getName(), namespace3.getURI());
            for (Namespace printNamespace : namespaceStack.addedForward()) {
                printNamespace(xMLStreamWriter, formatStack, printNamespace);
            }
            if (element.hasAttributes()) {
                for (Attribute printAttribute : element.getAttributes()) {
                    printAttribute(xMLStreamWriter, formatStack, printAttribute);
                }
            }
            xMLStreamWriter.writeCharacters(str);
            if (walker != null) {
                formatStack.push();
                formatStack.setTextMode(textMode);
                if (!walker.isAllText() && formatStack.getPadBetween() != null) {
                    printText(xMLStreamWriter, formatStack, new Text(formatStack.getPadBetween()));
                }
                printContent(xMLStreamWriter, formatStack, namespaceStack, walker);
                if (!walker.isAllText() && formatStack.getPadLast() != null) {
                    printText(xMLStreamWriter, formatStack, new Text(formatStack.getPadLast()));
                }
                formatStack.pop();
            }
            xMLStreamWriter.writeEndElement();
        } else {
            xMLStreamWriter.writeEmptyElement(namespace3.getPrefix(), element.getName(), namespace3.getURI());
            for (Namespace printNamespace2 : namespaceStack.addedForward()) {
                printNamespace(xMLStreamWriter, formatStack, printNamespace2);
            }
            for (Attribute printAttribute2 : element.getAttributes()) {
                printAttribute(xMLStreamWriter, formatStack, printAttribute2);
            }
            xMLStreamWriter.writeCharacters(str);
        }
        namespaceStack.pop();
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            String str3 = (String) it3.next();
            Iterator it4 = namespaceStack.iterator();
            while (true) {
                if (!it4.hasNext()) {
                    break;
                }
                Namespace namespace4 = (Namespace) it4.next();
                if (namespace4.getPrefix().equals(str3)) {
                    if (str.equals(namespace4.getPrefix())) {
                        xMLStreamWriter.setDefaultNamespace(namespace4.getURI());
                    } else {
                        xMLStreamWriter.setPrefix(namespace4.getPrefix(), namespace4.getURI());
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void printContent(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, NamespaceStack namespaceStack, Walker walker) throws XMLStreamException {
        while (walker.hasNext()) {
            Content next = walker.next();
            if (next != null) {
                switch (next.getCType()) {
                    case Comment:
                        printComment(xMLStreamWriter, formatStack, (Comment) next);
                        break;
                    case DocType:
                        printDocType(xMLStreamWriter, formatStack, (DocType) next);
                        break;
                    case Element:
                        printElement(xMLStreamWriter, formatStack, namespaceStack, (Element) next);
                        break;
                    case ProcessingInstruction:
                        printProcessingInstruction(xMLStreamWriter, formatStack, (ProcessingInstruction) next);
                        break;
                    case Text:
                        printText(xMLStreamWriter, formatStack, (Text) next);
                        break;
                    case CDATA:
                        printCDATA(xMLStreamWriter, formatStack, (CDATA) next);
                        break;
                    case EntityRef:
                        printEntityRef(xMLStreamWriter, formatStack, (EntityRef) next);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unexpected Content ");
                        sb.append(next.getCType());
                        throw new IllegalStateException(sb.toString());
                }
            } else if (walker.isCDATA()) {
                printCDATA(xMLStreamWriter, formatStack, new CDATA(walker.text()));
            } else {
                printText(xMLStreamWriter, formatStack, new Text(walker.text()));
            }
        }
    }

    /* access modifiers changed from: protected */
    public void printNamespace(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, Namespace namespace) throws XMLStreamException {
        xMLStreamWriter.writeNamespace(namespace.getPrefix(), namespace.getURI());
    }

    /* access modifiers changed from: protected */
    public void printAttribute(XMLStreamWriter xMLStreamWriter, FormatStack formatStack, Attribute attribute) throws XMLStreamException {
        if (attribute.isSpecified() || !formatStack.isSpecifiedAttributesOnly()) {
            Namespace namespace = attribute.getNamespace();
            if (namespace == Namespace.NO_NAMESPACE) {
                xMLStreamWriter.writeAttribute(attribute.getName(), attribute.getValue());
            } else {
                xMLStreamWriter.writeAttribute(namespace.getPrefix(), namespace.getURI(), attribute.getName(), attribute.getValue());
            }
        }
    }
}
