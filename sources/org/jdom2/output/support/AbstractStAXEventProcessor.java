package org.jdom2.output.support;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.util.XMLEventConsumer;
import org.apache.http.client.config.CookieSpecs;
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

public abstract class AbstractStAXEventProcessor extends AbstractOutputProcessor implements StAXEventProcessor {

    private static final class AttIterator implements Iterator<Attribute> {
        private final XMLEventFactory fac;
        private final Iterator<org.jdom2.Attribute> source;

        public AttIterator(Iterator<org.jdom2.Attribute> it, XMLEventFactory xMLEventFactory, boolean z) {
            if (z) {
                it = specified(it);
            }
            this.source = it;
            this.fac = xMLEventFactory;
        }

        private Iterator<org.jdom2.Attribute> specified(Iterator<org.jdom2.Attribute> it) {
            Iterator<org.jdom2.Attribute> it2 = null;
            if (it == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            while (it.hasNext()) {
                org.jdom2.Attribute attribute = (org.jdom2.Attribute) it.next();
                if (attribute.isSpecified()) {
                    arrayList.add(attribute);
                }
            }
            if (!arrayList.isEmpty()) {
                it2 = arrayList.iterator();
            }
            return it2;
        }

        public boolean hasNext() {
            Iterator<org.jdom2.Attribute> it = this.source;
            return it != null && it.hasNext();
        }

        public Attribute next() {
            org.jdom2.Attribute attribute = (org.jdom2.Attribute) this.source.next();
            Namespace namespace = attribute.getNamespace();
            if (namespace == Namespace.NO_NAMESPACE) {
                return this.fac.createAttribute(attribute.getName(), attribute.getValue());
            }
            return this.fac.createAttribute(namespace.getPrefix(), namespace.getURI(), attribute.getName(), attribute.getValue());
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove attributes");
        }
    }

    private static final class NSIterator implements Iterator<javax.xml.stream.events.Namespace> {
        private final XMLEventFactory fac;
        private final Iterator<Namespace> source;

        public NSIterator(Iterator<Namespace> it, XMLEventFactory xMLEventFactory) {
            this.source = it;
            this.fac = xMLEventFactory;
        }

        public boolean hasNext() {
            return this.source.hasNext();
        }

        public javax.xml.stream.events.Namespace next() {
            Namespace namespace = (Namespace) this.source.next();
            return this.fac.createNamespace(namespace.getPrefix(), namespace.getURI());
        }

        public void remove() {
            throw new UnsupportedOperationException("Cannot remove Namespaces");
        }
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, Document document) throws XMLStreamException {
        printDocument(xMLEventConsumer, new FormatStack(format), new NamespaceStack(), xMLEventFactory, document);
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, DocType docType) throws XMLStreamException {
        printDocType(xMLEventConsumer, new FormatStack(format), xMLEventFactory, docType);
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, Element element) throws XMLStreamException {
        printElement(xMLEventConsumer, new FormatStack(format), new NamespaceStack(), xMLEventFactory, element);
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, List<? extends Content> list) throws XMLStreamException {
        XMLEventConsumer xMLEventConsumer2 = xMLEventConsumer;
        printContent(xMLEventConsumer2, new FormatStack(format), new NamespaceStack(), xMLEventFactory, buildWalker(new FormatStack(format), list, false));
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, CDATA cdata) throws XMLStreamException {
        List singletonList = Collections.singletonList(cdata);
        FormatStack formatStack = new FormatStack(format);
        Walker buildWalker = buildWalker(formatStack, singletonList, false);
        if (buildWalker.hasNext()) {
            Content next = buildWalker.next();
            if (next == null) {
                printCDATA(xMLEventConsumer, formatStack, xMLEventFactory, new CDATA(buildWalker.text()));
            } else if (next.getCType() == CType.CDATA) {
                printCDATA(xMLEventConsumer, formatStack, xMLEventFactory, (CDATA) next);
            }
        }
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, Text text) throws XMLStreamException {
        List singletonList = Collections.singletonList(text);
        FormatStack formatStack = new FormatStack(format);
        Walker buildWalker = buildWalker(formatStack, singletonList, false);
        if (buildWalker.hasNext()) {
            Content next = buildWalker.next();
            if (next == null) {
                printText(xMLEventConsumer, formatStack, xMLEventFactory, new Text(buildWalker.text()));
            } else if (next.getCType() == CType.Text) {
                printText(xMLEventConsumer, formatStack, xMLEventFactory, (Text) next);
            }
        }
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, Comment comment) throws XMLStreamException {
        printComment(xMLEventConsumer, new FormatStack(format), xMLEventFactory, comment);
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, ProcessingInstruction processingInstruction) throws XMLStreamException {
        FormatStack formatStack = new FormatStack(format);
        formatStack.setIgnoreTrAXEscapingPIs(true);
        printProcessingInstruction(xMLEventConsumer, formatStack, xMLEventFactory, processingInstruction);
    }

    public void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, EntityRef entityRef) throws XMLStreamException {
        printEntityRef(xMLEventConsumer, new FormatStack(format), xMLEventFactory, entityRef);
    }

    /* access modifiers changed from: protected */
    public void printDocument(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, NamespaceStack namespaceStack, XMLEventFactory xMLEventFactory, Document document) throws XMLStreamException {
        if (formatStack.isOmitDeclaration()) {
            xMLEventConsumer.add(xMLEventFactory.createStartDocument(null, null));
        } else {
            String str = "1.0";
            if (formatStack.isOmitEncoding()) {
                xMLEventConsumer.add(xMLEventFactory.createStartDocument(null, str));
                if (formatStack.getLineSeparator() != null) {
                    xMLEventConsumer.add(xMLEventFactory.createCharacters(formatStack.getLineSeparator()));
                }
            } else {
                xMLEventConsumer.add(xMLEventFactory.createStartDocument(formatStack.getEncoding(), str));
                if (formatStack.getLineSeparator() != null) {
                    xMLEventConsumer.add(xMLEventFactory.createCharacters(formatStack.getLineSeparator()));
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
                        xMLEventConsumer.add(xMLEventFactory.createCharacters(text));
                    }
                } else {
                    int i2 = C61421.$SwitchMap$org$jdom2$Content$CType[next.getCType().ordinal()];
                    if (i2 == 1) {
                        printComment(xMLEventConsumer, formatStack, xMLEventFactory, (Comment) next);
                    } else if (i2 == 2) {
                        printDocType(xMLEventConsumer, formatStack, xMLEventFactory, (DocType) next);
                    } else if (i2 == 3) {
                        printElement(xMLEventConsumer, formatStack, namespaceStack, xMLEventFactory, (Element) next);
                    } else if (i2 == 4) {
                        printProcessingInstruction(xMLEventConsumer, formatStack, xMLEventFactory, (ProcessingInstruction) next);
                    }
                }
            }
            if (formatStack.getLineSeparator() != null) {
                xMLEventConsumer.add(xMLEventFactory.createCharacters(formatStack.getLineSeparator()));
            }
        }
        xMLEventConsumer.add(xMLEventFactory.createEndDocument());
    }

    /* access modifiers changed from: protected */
    public void printDocType(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, XMLEventFactory xMLEventFactory, DocType docType) throws XMLStreamException {
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
        xMLEventConsumer.add(xMLEventFactory.createDTD(stringWriter.toString()));
    }

    /* access modifiers changed from: protected */
    public void printProcessingInstruction(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, XMLEventFactory xMLEventFactory, ProcessingInstruction processingInstruction) throws XMLStreamException {
        String target = processingInstruction.getTarget();
        String data = processingInstruction.getData();
        if (data == null || data.trim().length() <= 0) {
            xMLEventConsumer.add(xMLEventFactory.createProcessingInstruction(target, ""));
        } else {
            xMLEventConsumer.add(xMLEventFactory.createProcessingInstruction(target, data));
        }
    }

    /* access modifiers changed from: protected */
    public void printComment(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, XMLEventFactory xMLEventFactory, Comment comment) throws XMLStreamException {
        xMLEventConsumer.add(xMLEventFactory.createComment(comment.getText()));
    }

    /* access modifiers changed from: protected */
    public void printEntityRef(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, XMLEventFactory xMLEventFactory, EntityRef entityRef) throws XMLStreamException {
        xMLEventConsumer.add(xMLEventFactory.createEntityReference(entityRef.getName(), null));
    }

    /* access modifiers changed from: protected */
    public void printCDATA(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, XMLEventFactory xMLEventFactory, CDATA cdata) throws XMLStreamException {
        xMLEventConsumer.add(xMLEventFactory.createCData(cdata.getText()));
    }

    /* access modifiers changed from: protected */
    public void printText(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, XMLEventFactory xMLEventFactory, Text text) throws XMLStreamException {
        xMLEventConsumer.add(xMLEventFactory.createCharacters(text.getText()));
    }

    /* access modifiers changed from: protected */
    public void printElement(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, NamespaceStack namespaceStack, XMLEventFactory xMLEventFactory, Element element) throws XMLStreamException {
        namespaceStack.push(element);
        try {
            Namespace namespace = element.getNamespace();
            Iterator it = element.hasAttributes() ? element.getAttributes().iterator() : null;
            if (namespace == Namespace.NO_NAMESPACE) {
                xMLEventConsumer.add(xMLEventFactory.createStartElement("", "", element.getName(), new AttIterator(it, xMLEventFactory, formatStack.isSpecifiedAttributesOnly()), new NSIterator(namespaceStack.addedForward().iterator(), xMLEventFactory)));
            } else if ("".equals(namespace.getPrefix())) {
                xMLEventConsumer.add(xMLEventFactory.createStartElement("", namespace.getURI(), element.getName(), new AttIterator(it, xMLEventFactory, formatStack.isSpecifiedAttributesOnly()), new NSIterator(namespaceStack.addedForward().iterator(), xMLEventFactory)));
            } else {
                xMLEventConsumer.add(xMLEventFactory.createStartElement(namespace.getPrefix(), namespace.getURI(), element.getName(), new AttIterator(it, xMLEventFactory, formatStack.isSpecifiedAttributesOnly()), new NSIterator(namespaceStack.addedForward().iterator(), xMLEventFactory)));
            }
            List content = element.getContent();
            if (!content.isEmpty()) {
                TextMode textMode = formatStack.getTextMode();
                String attributeValue = element.getAttributeValue("space", Namespace.XML_NAMESPACE);
                if (CookieSpecs.DEFAULT.equals(attributeValue)) {
                    textMode = formatStack.getDefaultMode();
                } else if ("preserve".equals(attributeValue)) {
                    textMode = TextMode.PRESERVE;
                }
                formatStack.push();
                formatStack.setTextMode(textMode);
                Walker buildWalker = buildWalker(formatStack, content, false);
                if (buildWalker.hasNext()) {
                    if (!buildWalker.isAllText() && formatStack.getPadBetween() != null) {
                        printText(xMLEventConsumer, formatStack, xMLEventFactory, new Text(formatStack.getPadBetween()));
                    }
                    printContent(xMLEventConsumer, formatStack, namespaceStack, xMLEventFactory, buildWalker);
                    if (!buildWalker.isAllText() && formatStack.getPadLast() != null) {
                        printText(xMLEventConsumer, formatStack, xMLEventFactory, new Text(formatStack.getPadLast()));
                    }
                }
                formatStack.pop();
            }
            xMLEventConsumer.add(xMLEventFactory.createEndElement(element.getNamespacePrefix(), element.getNamespaceURI(), element.getName(), new NSIterator(namespaceStack.addedReverse().iterator(), xMLEventFactory)));
            namespaceStack.pop();
        } catch (Throwable th) {
            namespaceStack.pop();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void printContent(XMLEventConsumer xMLEventConsumer, FormatStack formatStack, NamespaceStack namespaceStack, XMLEventFactory xMLEventFactory, Walker walker) throws XMLStreamException {
        while (walker.hasNext()) {
            Content next = walker.next();
            if (next != null) {
                switch (next.getCType()) {
                    case Comment:
                        printComment(xMLEventConsumer, formatStack, xMLEventFactory, (Comment) next);
                        break;
                    case DocType:
                        printDocType(xMLEventConsumer, formatStack, xMLEventFactory, (DocType) next);
                        break;
                    case Element:
                        printElement(xMLEventConsumer, formatStack, namespaceStack, xMLEventFactory, (Element) next);
                        break;
                    case ProcessingInstruction:
                        printProcessingInstruction(xMLEventConsumer, formatStack, xMLEventFactory, (ProcessingInstruction) next);
                        break;
                    case CDATA:
                        printCDATA(xMLEventConsumer, formatStack, xMLEventFactory, (CDATA) next);
                        break;
                    case EntityRef:
                        printEntityRef(xMLEventConsumer, formatStack, xMLEventFactory, (EntityRef) next);
                        break;
                    case Text:
                        printText(xMLEventConsumer, formatStack, xMLEventFactory, (Text) next);
                        break;
                    default:
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unexpected Content ");
                        sb.append(next.getCType());
                        throw new IllegalStateException(sb.toString());
                }
            } else if (walker.isCDATA()) {
                printCDATA(xMLEventConsumer, formatStack, xMLEventFactory, new CDATA(walker.text()));
            } else {
                printText(xMLEventConsumer, formatStack, xMLEventFactory, new Text(walker.text()));
            }
        }
    }
}
