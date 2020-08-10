package org.jdom2.input;

import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import org.jdom2.AttributeType;
import org.jdom2.Content;
import org.jdom2.DefaultJDOMFactory;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;
import org.jdom2.Namespace;
import org.jdom2.ProcessingInstruction;
import org.jdom2.input.stax.DTDParser;

public class StAXEventBuilder {
    private JDOMFactory factory = new DefaultJDOMFactory();

    private static final Document process(JDOMFactory jDOMFactory, XMLEventReader xMLEventReader) throws JDOMException {
        Element element = null;
        try {
            Document document = jDOMFactory.document(null);
            StartDocument peek = xMLEventReader.peek();
            if (7 == peek.getEventType()) {
                while (peek.getEventType() != 8) {
                    if (peek.isStartDocument()) {
                        document.setBaseURI(peek.getLocation().getSystemId());
                        document.setProperty("ENCODING_SCHEME", peek.getCharacterEncodingScheme());
                        document.setProperty("STANDALONE", String.valueOf(peek.isStandalone()));
                    } else if (peek instanceof DTD) {
                        document.setDocType(DTDParser.parse(((DTD) peek).getDocumentTypeDeclaration(), jDOMFactory));
                    } else if (peek.isStartElement()) {
                        Element processElement = processElement(jDOMFactory, peek.asStartElement());
                        if (element == null) {
                            document.setRootElement(processElement);
                            DocType docType = document.getDocType();
                            if (docType != null) {
                                docType.setElementName(processElement.getName());
                            }
                        } else {
                            element.addContent((Content) processElement);
                        }
                        element = processElement;
                    } else if (!peek.isCharacters() || element == null) {
                        if (peek instanceof Comment) {
                            org.jdom2.Comment comment = jDOMFactory.comment(((Comment) peek).getText());
                            if (element == null) {
                                document.addContent((Content) comment);
                            } else {
                                element.addContent((Content) comment);
                            }
                        } else if (peek.isEntityReference()) {
                            element.addContent((Content) jDOMFactory.entityRef(((EntityReference) peek).getName()));
                        } else if (peek.isProcessingInstruction()) {
                            ProcessingInstruction processingInstruction = jDOMFactory.processingInstruction(((javax.xml.stream.events.ProcessingInstruction) peek).getTarget(), ((javax.xml.stream.events.ProcessingInstruction) peek).getData());
                            if (element == null) {
                                document.addContent((Content) processingInstruction);
                            } else {
                                element.addContent((Content) processingInstruction);
                            }
                        } else if (peek.isEndElement()) {
                            element = element.getParentElement();
                        }
                    } else if (peek.asCharacters().isCData()) {
                        element.addContent((Content) jDOMFactory.cdata(((Characters) peek).getData()));
                    } else {
                        element.addContent((Content) jDOMFactory.text(((Characters) peek).getData()));
                    }
                    if (!xMLEventReader.hasNext()) {
                        break;
                    }
                    peek = xMLEventReader.nextEvent();
                }
                return document;
            }
            throw new JDOMException("JDOM requires that XMLStreamReaders are at their beginning when being processed.");
        } catch (XMLStreamException e) {
            throw new JDOMException("Unable to process XMLStream. See Cause.", e);
        }
    }

    private static final Element processElement(JDOMFactory jDOMFactory, StartElement startElement) {
        QName name = startElement.getName();
        Element element = jDOMFactory.element(name.getLocalPart(), Namespace.getNamespace(name.getPrefix(), name.getNamespaceURI()));
        Iterator attributes = startElement.getAttributes();
        while (attributes.hasNext()) {
            Attribute attribute = (Attribute) attributes.next();
            QName name2 = attribute.getName();
            jDOMFactory.setAttribute(element, jDOMFactory.attribute(name2.getLocalPart(), attribute.getValue(), AttributeType.getAttributeType(attribute.getDTDType()), Namespace.getNamespace(name2.getPrefix(), name2.getNamespaceURI())));
        }
        Iterator namespaces = startElement.getNamespaces();
        while (namespaces.hasNext()) {
            javax.xml.stream.events.Namespace namespace = (javax.xml.stream.events.Namespace) namespaces.next();
            element.addNamespaceDeclaration(Namespace.getNamespace(namespace.getPrefix(), namespace.getNamespaceURI()));
        }
        return element;
    }

    public JDOMFactory getFactory() {
        return this.factory;
    }

    public void setFactory(JDOMFactory jDOMFactory) {
        this.factory = jDOMFactory;
    }

    public Document build(XMLEventReader xMLEventReader) throws JDOMException {
        return process(this.factory, xMLEventReader);
    }
}
