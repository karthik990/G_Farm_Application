package org.jdom2.input;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jdom2.AttributeType;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DefaultJDOMFactory;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;
import org.jdom2.Namespace;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.Verifier;
import org.jdom2.input.stax.DTDParser;
import org.jdom2.input.stax.StAXFilter;

public class StAXStreamBuilder {
    private JDOMFactory builderfactory = new DefaultJDOMFactory();

    private static final Document process(JDOMFactory jDOMFactory, XMLStreamReader xMLStreamReader) throws JDOMException {
        try {
            int eventType = xMLStreamReader.getEventType();
            if (7 == eventType) {
                Document document = jDOMFactory.document(null);
                while (eventType != 8) {
                    switch (eventType) {
                        case 1:
                            document.setRootElement(processElementFragment(jDOMFactory, xMLStreamReader));
                            break;
                        case 2:
                            throw new JDOMException("Unexpected XMLStream event at Document level: END_ELEMENT");
                        case 3:
                            document.addContent((Content) jDOMFactory.processingInstruction(xMLStreamReader.getPITarget(), xMLStreamReader.getPIData()));
                            break;
                        case 4:
                            String text = xMLStreamReader.getText();
                            if (Verifier.isAllXMLWhitespace(text)) {
                                break;
                            } else {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Unexpected XMLStream event at Document level: CHARACTERS (");
                                sb.append(text);
                                sb.append(")");
                                throw new JDOMException(sb.toString());
                            }
                        case 5:
                            document.addContent((Content) jDOMFactory.comment(xMLStreamReader.getText()));
                            break;
                        case 6:
                            break;
                        case 7:
                            document.setBaseURI(xMLStreamReader.getLocation().getSystemId());
                            document.setProperty("ENCODING_SCHEME", xMLStreamReader.getCharacterEncodingScheme());
                            document.setProperty("STANDALONE", String.valueOf(xMLStreamReader.isStandalone()));
                            document.setProperty("ENCODING", xMLStreamReader.getEncoding());
                            break;
                        case 9:
                            throw new JDOMException("Unexpected XMLStream event at Document level: ENTITY_REFERENCE");
                        case 11:
                            document.setDocType(DTDParser.parse(xMLStreamReader.getText(), jDOMFactory));
                            break;
                        case 12:
                            throw new JDOMException("Unexpected XMLStream event at Document level: CDATA");
                        default:
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("Unexpected XMLStream event ");
                            sb2.append(eventType);
                            throw new JDOMException(sb2.toString());
                    }
                    if (xMLStreamReader.hasNext()) {
                        eventType = xMLStreamReader.next();
                    } else {
                        throw new JDOMException("Unexpected end-of-XMLStreamReader");
                    }
                }
                return document;
            }
            throw new JDOMException("JDOM requires that XMLStreamReaders are at their beginning when being processed.");
        } catch (XMLStreamException e) {
            throw new JDOMException("Unable to process XMLStream. See Cause.", e);
        }
    }

    private List<Content> processFragments(JDOMFactory jDOMFactory, XMLStreamReader xMLStreamReader, StAXFilter stAXFilter) throws JDOMException {
        if (7 == xMLStreamReader.getEventType()) {
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (xMLStreamReader.hasNext()) {
                try {
                    int next = xMLStreamReader.next();
                    if (next == 8) {
                        return arrayList;
                    }
                    switch (next) {
                        case 1:
                            QName name = xMLStreamReader.getName();
                            if (!stAXFilter.includeElement(i, name.getLocalPart(), Namespace.getNamespace(name.getPrefix(), name.getNamespaceURI()))) {
                                int i2 = i + 1;
                                while (i2 > i && xMLStreamReader.hasNext()) {
                                    int next2 = xMLStreamReader.next();
                                    if (next2 == 1) {
                                        i2++;
                                    } else if (next2 == 2) {
                                        i2--;
                                    }
                                }
                                i = i2;
                                break;
                            } else {
                                arrayList.add(processPrunableElement(jDOMFactory, xMLStreamReader, i, stAXFilter));
                                break;
                            }
                        case 2:
                            throw new JDOMException("Illegal state for XMLStreamReader. Cannot get XML Fragment for state END_ELEMENT");
                        case 3:
                            if (!stAXFilter.includeProcessingInstruction(i, xMLStreamReader.getPITarget())) {
                                break;
                            } else {
                                arrayList.add(jDOMFactory.processingInstruction(xMLStreamReader.getPITarget(), xMLStreamReader.getPIData()));
                                break;
                            }
                        case 4:
                        case 6:
                            String includeText = stAXFilter.includeText(i, xMLStreamReader.getText());
                            if (includeText == null) {
                                break;
                            } else {
                                arrayList.add(jDOMFactory.text(includeText));
                                break;
                            }
                        case 5:
                            String includeComment = stAXFilter.includeComment(i, xMLStreamReader.getText());
                            if (includeComment == null) {
                                break;
                            } else {
                                arrayList.add(jDOMFactory.comment(includeComment));
                                break;
                            }
                        case 7:
                            throw new JDOMException("Illegal state for XMLStreamReader. Cannot get XML Fragment for state START_DOCUMENT");
                        case 8:
                            throw new JDOMException("Illegal state for XMLStreamReader. Cannot get XML Fragment for state END_DOCUMENT");
                        case 9:
                            if (!stAXFilter.includeEntityRef(i, xMLStreamReader.getLocalName())) {
                                break;
                            } else {
                                arrayList.add(jDOMFactory.entityRef(xMLStreamReader.getLocalName()));
                                break;
                            }
                        case 11:
                            if (!stAXFilter.includeDocType()) {
                                break;
                            } else {
                                arrayList.add(DTDParser.parse(xMLStreamReader.getText(), jDOMFactory));
                                break;
                            }
                        case 12:
                            String includeCDATA = stAXFilter.includeCDATA(i, xMLStreamReader.getText());
                            if (includeCDATA == null) {
                                break;
                            } else {
                                arrayList.add(jDOMFactory.cdata(includeCDATA));
                                break;
                            }
                        default:
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unexpected XMLStream event ");
                            sb.append(xMLStreamReader.getEventType());
                            throw new JDOMException(sb.toString());
                    }
                } catch (XMLStreamException e) {
                    throw new JDOMException("Unable to process fragments from XMLStreamReader.", e);
                }
            }
            return arrayList;
        }
        throw new JDOMException("JDOM requires that XMLStreamReaders are at their beginning when being processed.");
    }

    private static final Element processPrunableElement(JDOMFactory jDOMFactory, XMLStreamReader xMLStreamReader, int i, StAXFilter stAXFilter) throws XMLStreamException, JDOMException {
        if (1 == xMLStreamReader.getEventType()) {
            Element processElement = processElement(jDOMFactory, xMLStreamReader);
            int i2 = i + 1;
            Element element = processElement;
            while (i2 > i && xMLStreamReader.hasNext()) {
                int next = xMLStreamReader.next();
                if (next != 9) {
                    if (next != 12) {
                        switch (next) {
                            case 1:
                                QName name = xMLStreamReader.getName();
                                if (stAXFilter.pruneElement(i2, name.getLocalPart(), Namespace.getNamespace(name.getPrefix(), name.getNamespaceURI()))) {
                                    int i3 = i2 + 1;
                                    while (i3 > i2 && xMLStreamReader.hasNext()) {
                                        int next2 = xMLStreamReader.next();
                                        if (next2 != 8) {
                                            if (next2 == 1) {
                                                i3++;
                                            } else if (next2 == 2) {
                                                i3--;
                                            }
                                        }
                                    }
                                    i2 = i3;
                                    break;
                                } else {
                                    Element processElement2 = processElement(jDOMFactory, xMLStreamReader);
                                    element.addContent((Content) processElement2);
                                    i2++;
                                    element = processElement2;
                                    break;
                                }
                                break;
                            case 2:
                                element = element.getParentElement();
                                i2--;
                                break;
                            case 3:
                                if (stAXFilter.pruneProcessingInstruction(i2, xMLStreamReader.getPITarget())) {
                                    break;
                                } else {
                                    element.addContent((Content) jDOMFactory.processingInstruction(xMLStreamReader.getPITarget(), xMLStreamReader.getPIData()));
                                    break;
                                }
                            case 4:
                            case 6:
                                String pruneText = stAXFilter.pruneText(i2, xMLStreamReader.getText());
                                if (pruneText == null) {
                                    break;
                                } else {
                                    element.addContent((Content) jDOMFactory.text(pruneText));
                                    break;
                                }
                            case 5:
                                String pruneComment = stAXFilter.pruneComment(i2, xMLStreamReader.getText());
                                if (pruneComment == null) {
                                    break;
                                } else {
                                    element.addContent((Content) jDOMFactory.comment(pruneComment));
                                    break;
                                }
                            default:
                                StringBuilder sb = new StringBuilder();
                                sb.append("Unexpected XMLStream event ");
                                sb.append(xMLStreamReader.getEventType());
                                throw new JDOMException(sb.toString());
                        }
                    } else {
                        String pruneCDATA = stAXFilter.pruneCDATA(i2, xMLStreamReader.getText());
                        if (pruneCDATA != null) {
                            element.addContent((Content) jDOMFactory.cdata(pruneCDATA));
                        }
                    }
                } else if (!stAXFilter.pruneEntityRef(i2, xMLStreamReader.getLocalName())) {
                    element.addContent((Content) jDOMFactory.entityRef(xMLStreamReader.getLocalName()));
                }
            }
            return processElement;
        }
        throw new JDOMException("JDOM requires that the XMLStreamReader is at the START_ELEMENT state when retrieving an Element Fragment.");
    }

    private static final Content processFragment(JDOMFactory jDOMFactory, XMLStreamReader xMLStreamReader) throws JDOMException {
        try {
            switch (xMLStreamReader.getEventType()) {
                case 1:
                    Element processElementFragment = processElementFragment(jDOMFactory, xMLStreamReader);
                    xMLStreamReader.next();
                    return processElementFragment;
                case 2:
                    throw new JDOMException("Illegal state for XMLStreamReader. Cannot get XML Fragment for state END_ELEMENT");
                case 3:
                    ProcessingInstruction processingInstruction = jDOMFactory.processingInstruction(xMLStreamReader.getPITarget(), xMLStreamReader.getPIData());
                    xMLStreamReader.next();
                    return processingInstruction;
                case 4:
                case 6:
                    Text text = jDOMFactory.text(xMLStreamReader.getText());
                    xMLStreamReader.next();
                    return text;
                case 5:
                    Comment comment = jDOMFactory.comment(xMLStreamReader.getText());
                    xMLStreamReader.next();
                    return comment;
                case 7:
                    throw new JDOMException("Illegal state for XMLStreamReader. Cannot get XML Fragment for state START_DOCUMENT");
                case 8:
                    throw new JDOMException("Illegal state for XMLStreamReader. Cannot get XML Fragment for state END_DOCUMENT");
                case 9:
                    EntityRef entityRef = jDOMFactory.entityRef(xMLStreamReader.getLocalName());
                    xMLStreamReader.next();
                    return entityRef;
                case 11:
                    DocType parse = DTDParser.parse(xMLStreamReader.getText(), jDOMFactory);
                    xMLStreamReader.next();
                    return parse;
                case 12:
                    CDATA cdata = jDOMFactory.cdata(xMLStreamReader.getText());
                    xMLStreamReader.next();
                    return cdata;
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("Unexpected XMLStream event ");
                    sb.append(xMLStreamReader.getEventType());
                    throw new JDOMException(sb.toString());
            }
        } catch (XMLStreamException e) {
            throw new JDOMException("Unable to process XMLStream. See Cause.", e);
        }
    }

    private static final Element processElementFragment(JDOMFactory jDOMFactory, XMLStreamReader xMLStreamReader) throws XMLStreamException, JDOMException {
        int i = 1;
        if (1 == xMLStreamReader.getEventType()) {
            Element processElement = processElement(jDOMFactory, xMLStreamReader);
            Element element = processElement;
            while (i > 0 && xMLStreamReader.hasNext()) {
                int next = xMLStreamReader.next();
                if (next == 9) {
                    element.addContent((Content) jDOMFactory.entityRef(xMLStreamReader.getLocalName()));
                } else if (next != 12) {
                    switch (next) {
                        case 1:
                            Element processElement2 = processElement(jDOMFactory, xMLStreamReader);
                            element.addContent((Content) processElement2);
                            i++;
                            element = processElement2;
                            break;
                        case 2:
                            element = element.getParentElement();
                            i--;
                            break;
                        case 3:
                            element.addContent((Content) jDOMFactory.processingInstruction(xMLStreamReader.getPITarget(), xMLStreamReader.getPIData()));
                            break;
                        case 4:
                        case 6:
                            element.addContent((Content) jDOMFactory.text(xMLStreamReader.getText()));
                            break;
                        case 5:
                            element.addContent((Content) jDOMFactory.comment(xMLStreamReader.getText()));
                            break;
                        default:
                            StringBuilder sb = new StringBuilder();
                            sb.append("Unexpected XMLStream event ");
                            sb.append(xMLStreamReader.getEventType());
                            throw new JDOMException(sb.toString());
                    }
                } else {
                    element.addContent((Content) jDOMFactory.cdata(xMLStreamReader.getText()));
                }
            }
            return processElement;
        }
        throw new JDOMException("JDOM requires that the XMLStreamReader is at the START_ELEMENT state when retrieving an Element Fragment.");
    }

    private static final Element processElement(JDOMFactory jDOMFactory, XMLStreamReader xMLStreamReader) {
        Element element = jDOMFactory.element(xMLStreamReader.getLocalName(), Namespace.getNamespace(xMLStreamReader.getPrefix(), xMLStreamReader.getNamespaceURI()));
        int attributeCount = xMLStreamReader.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            jDOMFactory.setAttribute(element, jDOMFactory.attribute(xMLStreamReader.getAttributeLocalName(i), xMLStreamReader.getAttributeValue(i), AttributeType.getAttributeType(xMLStreamReader.getAttributeType(i)), Namespace.getNamespace(xMLStreamReader.getAttributePrefix(i), xMLStreamReader.getAttributeNamespace(i))));
        }
        int namespaceCount = xMLStreamReader.getNamespaceCount();
        for (int i2 = 0; i2 < namespaceCount; i2++) {
            element.addNamespaceDeclaration(Namespace.getNamespace(xMLStreamReader.getNamespacePrefix(i2), xMLStreamReader.getNamespaceURI(i2)));
        }
        return element;
    }

    public JDOMFactory getFactory() {
        return this.builderfactory;
    }

    public void setFactory(JDOMFactory jDOMFactory) {
        this.builderfactory = jDOMFactory;
    }

    public Document build(XMLStreamReader xMLStreamReader) throws JDOMException {
        return process(this.builderfactory, xMLStreamReader);
    }

    public List<Content> buildFragments(XMLStreamReader xMLStreamReader, StAXFilter stAXFilter) throws JDOMException {
        return processFragments(this.builderfactory, xMLStreamReader, stAXFilter);
    }

    public Content fragment(XMLStreamReader xMLStreamReader) throws JDOMException {
        return processFragment(this.builderfactory, xMLStreamReader);
    }
}
