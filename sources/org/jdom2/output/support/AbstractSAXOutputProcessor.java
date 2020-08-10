package org.jdom2.output.support;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.http.client.config.CookieSpecs;
import org.jdom2.Attribute;
import org.jdom2.AttributeType;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.JDOMConstants;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.output.Format;
import org.jdom2.output.Format.TextMode;
import org.jdom2.output.XMLOutputter;
import org.jdom2.util.NamespaceStack;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class AbstractSAXOutputProcessor extends AbstractOutputProcessor implements SAXOutputProcessor {

    /* renamed from: org.jdom2.output.support.AbstractSAXOutputProcessor$1 */
    static /* synthetic */ class C61411 {
        static final /* synthetic */ int[] $SwitchMap$org$jdom2$AttributeType = new int[AttributeType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(18:0|(2:1|2)|3|5|6|7|8|9|10|11|12|13|14|15|16|17|18|20) */
        /* JADX WARNING: Can't wrap try/catch for region: R(19:0|1|2|3|5|6|7|8|9|10|11|12|13|14|15|16|17|18|20) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0048 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0053 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x005e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0027 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0032 */
        static {
            /*
                org.jdom2.AttributeType[] r0 = org.jdom2.AttributeType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$jdom2$AttributeType = r0
                r0 = 1
                int[] r1 = $SwitchMap$org$jdom2$AttributeType     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.jdom2.AttributeType r2 = org.jdom2.AttributeType.UNDECLARED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                org.jdom2.Content$CType[] r1 = org.jdom2.Content.CType.values()
                int r1 = r1.length
                int[] r1 = new int[r1]
                $SwitchMap$org$jdom2$Content$CType = r1
                int[] r1 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0027 }
                org.jdom2.Content$CType r2 = org.jdom2.Content.CType.Comment     // Catch:{ NoSuchFieldError -> 0x0027 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0027 }
                r1[r2] = r0     // Catch:{ NoSuchFieldError -> 0x0027 }
            L_0x0027:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0032 }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.DocType     // Catch:{ NoSuchFieldError -> 0x0032 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0032 }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0032 }
            L_0x0032:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x003d }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.Element     // Catch:{ NoSuchFieldError -> 0x003d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003d }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003d }
            L_0x003d:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0048 }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.ProcessingInstruction     // Catch:{ NoSuchFieldError -> 0x0048 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0048 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0048 }
            L_0x0048:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0053 }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.CDATA     // Catch:{ NoSuchFieldError -> 0x0053 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0053 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0053 }
            L_0x0053:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x005e }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.EntityRef     // Catch:{ NoSuchFieldError -> 0x005e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x005e }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x005e }
            L_0x005e:
                int[] r0 = $SwitchMap$org$jdom2$Content$CType     // Catch:{ NoSuchFieldError -> 0x0069 }
                org.jdom2.Content$CType r1 = org.jdom2.Content.CType.Text     // Catch:{ NoSuchFieldError -> 0x0069 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0069 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0069 }
            L_0x0069:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.jdom2.output.support.AbstractSAXOutputProcessor.C61411.<clinit>():void");
        }
    }

    private static void locate(SAXTarget sAXTarget) {
        sAXTarget.getContentHandler().setDocumentLocator(sAXTarget.getLocator());
    }

    public void process(SAXTarget sAXTarget, Format format, Document document) throws JDOMException {
        try {
            locate(sAXTarget);
            printDocument(sAXTarget, new FormatStack(format), new NamespaceStack(), document);
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the Document: ", e);
        }
    }

    public void process(SAXTarget sAXTarget, Format format, DocType docType) throws JDOMException {
        try {
            locate(sAXTarget);
            printDocType(sAXTarget, new FormatStack(format), docType);
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the DocType: ", e);
        }
    }

    public void process(SAXTarget sAXTarget, Format format, Element element) throws JDOMException {
        try {
            locate(sAXTarget);
            printElement(sAXTarget, new FormatStack(format), new NamespaceStack(), element);
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the Element: ", e);
        }
    }

    public void process(SAXTarget sAXTarget, Format format, List<? extends Content> list) throws JDOMException {
        try {
            locate(sAXTarget);
            FormatStack formatStack = new FormatStack(format);
            printContent(sAXTarget, formatStack, new NamespaceStack(), buildWalker(formatStack, list, false));
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the List: ", e);
        }
    }

    public void process(SAXTarget sAXTarget, Format format, CDATA cdata) throws JDOMException {
        try {
            locate(sAXTarget);
            List singletonList = Collections.singletonList(cdata);
            FormatStack formatStack = new FormatStack(format);
            printContent(sAXTarget, formatStack, new NamespaceStack(), buildWalker(formatStack, singletonList, false));
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the CDATA: ", e);
        }
    }

    public void process(SAXTarget sAXTarget, Format format, Text text) throws JDOMException {
        try {
            locate(sAXTarget);
            List singletonList = Collections.singletonList(text);
            FormatStack formatStack = new FormatStack(format);
            printContent(sAXTarget, formatStack, new NamespaceStack(), buildWalker(formatStack, singletonList, false));
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the Text: ", e);
        }
    }

    public void process(SAXTarget sAXTarget, Format format, Comment comment) throws JDOMException {
        try {
            locate(sAXTarget);
            printComment(sAXTarget, new FormatStack(format), comment);
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the Comment: ", e);
        }
    }

    public void process(SAXTarget sAXTarget, Format format, ProcessingInstruction processingInstruction) throws JDOMException {
        try {
            locate(sAXTarget);
            printProcessingInstruction(sAXTarget, new FormatStack(format), processingInstruction);
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the ProcessingInstruction: ", e);
        }
    }

    public void process(SAXTarget sAXTarget, Format format, EntityRef entityRef) throws JDOMException {
        try {
            locate(sAXTarget);
            printEntityRef(sAXTarget, new FormatStack(format), entityRef);
        } catch (SAXException e) {
            throw new JDOMException("Encountered a SAX exception processing the EntityRef: ", e);
        }
    }

    public void processAsDocument(SAXTarget sAXTarget, Format format, List<? extends Content> list) throws JDOMException {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    locate(sAXTarget);
                    sAXTarget.getContentHandler().startDocument();
                    FormatStack formatStack = new FormatStack(format);
                    if (sAXTarget.isReportDTDEvents()) {
                        Iterator it = list.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Content content = (Content) it.next();
                            if (content instanceof DocType) {
                                printDocType(sAXTarget, formatStack, (DocType) content);
                                break;
                            }
                        }
                    }
                    printContent(sAXTarget, formatStack, new NamespaceStack(), buildWalker(formatStack, list, false));
                    sAXTarget.getContentHandler().endDocument();
                }
            } catch (SAXException e) {
                throw new JDOMException("Encountered a SAX exception processing the List: ", e);
            }
        }
    }

    public void processAsDocument(SAXTarget sAXTarget, Format format, Element element) throws JDOMException {
        if (element != null) {
            try {
                locate(sAXTarget);
                sAXTarget.getContentHandler().startDocument();
                printElement(sAXTarget, new FormatStack(format), new NamespaceStack(), element);
                sAXTarget.getContentHandler().endDocument();
            } catch (SAXException e) {
                throw new JDOMException("Encountered a SAX exception processing the Element: ", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void printDocument(SAXTarget sAXTarget, FormatStack formatStack, NamespaceStack namespaceStack, Document document) throws SAXException {
        if (document != null) {
            sAXTarget.getContentHandler().startDocument();
            if (sAXTarget.isReportDTDEvents()) {
                printDocType(sAXTarget, formatStack, document.getDocType());
            }
            int contentSize = document.getContentSize();
            if (contentSize > 0) {
                for (int i = 0; i < contentSize; i++) {
                    Content content = document.getContent(i);
                    sAXTarget.getLocator().setNode(content);
                    int i2 = C61411.$SwitchMap$org$jdom2$Content$CType[content.getCType().ordinal()];
                    if (i2 == 1) {
                        printComment(sAXTarget, formatStack, (Comment) content);
                    } else if (i2 != 2) {
                        if (i2 == 3) {
                            printElement(sAXTarget, formatStack, namespaceStack, (Element) content);
                        } else if (i2 == 4) {
                            printProcessingInstruction(sAXTarget, formatStack, (ProcessingInstruction) content);
                        }
                    }
                }
            }
            sAXTarget.getContentHandler().endDocument();
        }
    }

    /* access modifiers changed from: protected */
    public void printDocType(SAXTarget sAXTarget, FormatStack formatStack, DocType docType) throws SAXException {
        DTDHandler dTDHandler = sAXTarget.getDTDHandler();
        DeclHandler declHandler = sAXTarget.getDeclHandler();
        if (docType == null) {
            return;
        }
        if (dTDHandler != null || declHandler != null) {
            try {
                createDTDParser(sAXTarget).parse(new InputSource(new StringReader(new XMLOutputter().outputString(docType))));
            } catch (SAXParseException unused) {
            } catch (IOException e) {
                throw new SAXException("DTD parsing error", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void printProcessingInstruction(SAXTarget sAXTarget, FormatStack formatStack, ProcessingInstruction processingInstruction) throws SAXException {
        sAXTarget.getContentHandler().processingInstruction(processingInstruction.getTarget(), processingInstruction.getData());
    }

    /* access modifiers changed from: protected */
    public void printComment(SAXTarget sAXTarget, FormatStack formatStack, Comment comment) throws SAXException {
        if (sAXTarget.getLexicalHandler() != null) {
            char[] charArray = comment.getText().toCharArray();
            sAXTarget.getLexicalHandler().comment(charArray, 0, charArray.length);
        }
    }

    /* access modifiers changed from: protected */
    public void printEntityRef(SAXTarget sAXTarget, FormatStack formatStack, EntityRef entityRef) throws SAXException {
        sAXTarget.getContentHandler().skippedEntity(entityRef.getName());
    }

    /* access modifiers changed from: protected */
    public void printCDATA(SAXTarget sAXTarget, FormatStack formatStack, CDATA cdata) throws SAXException {
        LexicalHandler lexicalHandler = sAXTarget.getLexicalHandler();
        char[] charArray = cdata.getText().toCharArray();
        if (lexicalHandler != null) {
            lexicalHandler.startCDATA();
            sAXTarget.getContentHandler().characters(charArray, 0, charArray.length);
            lexicalHandler.endCDATA();
            return;
        }
        sAXTarget.getContentHandler().characters(charArray, 0, charArray.length);
    }

    /* access modifiers changed from: protected */
    public void printText(SAXTarget sAXTarget, FormatStack formatStack, Text text) throws SAXException {
        char[] charArray = text.getText().toCharArray();
        sAXTarget.getContentHandler().characters(charArray, 0, charArray.length);
    }

    /* access modifiers changed from: protected */
    public void printElement(SAXTarget sAXTarget, FormatStack formatStack, NamespaceStack namespaceStack, Element element) throws SAXException {
        ContentHandler contentHandler = sAXTarget.getContentHandler();
        Object node = sAXTarget.getLocator().getNode();
        namespaceStack.push(element);
        try {
            sAXTarget.getLocator().setNode(element);
            AttributesImpl attributesImpl = new AttributesImpl();
            for (Namespace namespace : namespaceStack.addedForward()) {
                contentHandler.startPrefixMapping(namespace.getPrefix(), namespace.getURI());
                if (sAXTarget.isDeclareNamespaces()) {
                    if (namespace.getPrefix().equals("")) {
                        String uri = namespace.getURI();
                        attributesImpl.addAttribute("", "", JDOMConstants.NS_PREFIX_XMLNS, "CDATA", uri);
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append("xmlns:");
                        sb.append(namespace.getPrefix());
                        String uri2 = namespace.getURI();
                        attributesImpl.addAttribute("", "", sb.toString(), "CDATA", uri2);
                    }
                }
            }
            if (element.hasAttributes()) {
                for (Attribute attribute : element.getAttributes()) {
                    if (attribute.isSpecified() || !formatStack.isSpecifiedAttributesOnly()) {
                        attributesImpl.addAttribute(attribute.getNamespaceURI(), attribute.getName(), attribute.getQualifiedName(), getAttributeTypeName(attribute.getAttributeType()), attribute.getValue());
                    }
                }
            }
            contentHandler.startElement(element.getNamespaceURI(), element.getName(), element.getQualifiedName(), attributesImpl);
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
                        printText(sAXTarget, formatStack, new Text(formatStack.getPadBetween()));
                    }
                    printContent(sAXTarget, formatStack, namespaceStack, buildWalker);
                    if (!buildWalker.isAllText() && formatStack.getPadLast() != null) {
                        printText(sAXTarget, formatStack, new Text(formatStack.getPadLast()));
                    }
                }
                formatStack.pop();
            }
            sAXTarget.getContentHandler().endElement(element.getNamespaceURI(), element.getName(), element.getQualifiedName());
            for (Namespace prefix : namespaceStack.addedReverse()) {
                contentHandler.endPrefixMapping(prefix.getPrefix());
            }
            namespaceStack.pop();
            sAXTarget.getLocator().setNode(node);
        } catch (Throwable th) {
            namespaceStack.pop();
            sAXTarget.getLocator().setNode(node);
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void printContent(SAXTarget sAXTarget, FormatStack formatStack, NamespaceStack namespaceStack, Walker walker) throws SAXException {
        while (walker.hasNext()) {
            Content next = walker.next();
            if (next != null) {
                switch (next.getCType()) {
                    case Comment:
                        printComment(sAXTarget, formatStack, (Comment) next);
                        break;
                    case Element:
                        printElement(sAXTarget, formatStack, namespaceStack, (Element) next);
                        break;
                    case ProcessingInstruction:
                        printProcessingInstruction(sAXTarget, formatStack, (ProcessingInstruction) next);
                        break;
                    case CDATA:
                        printCDATA(sAXTarget, formatStack, (CDATA) next);
                        break;
                    case EntityRef:
                        printEntityRef(sAXTarget, formatStack, (EntityRef) next);
                        break;
                    case Text:
                        printText(sAXTarget, formatStack, (Text) next);
                        break;
                }
            } else {
                String text = walker.text();
                if (walker.isCDATA()) {
                    printCDATA(sAXTarget, formatStack, new CDATA(text));
                } else {
                    printText(sAXTarget, formatStack, new Text(text));
                }
            }
        }
    }

    private static String getAttributeTypeName(AttributeType attributeType) {
        return C61411.$SwitchMap$org$jdom2$AttributeType[attributeType.ordinal()] != 1 ? attributeType.name() : "CDATA";
    }

    /* access modifiers changed from: protected */
    public XMLReader createParser() throws Exception {
        XMLReader xMLReader = null;
        try {
            Class cls = Class.forName("javax.xml.parsers.SAXParserFactory");
            Object invoke = cls.getMethod("newSAXParser", new Class[0]).invoke(cls.getMethod("newInstance", new Class[0]).invoke(null, new Object[0]), new Object[0]);
            xMLReader = (XMLReader) invoke.getClass().getMethod("getXMLReader", new Class[0]).invoke(invoke, new Object[0]);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
        }
        return xMLReader == null ? XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser") : xMLReader;
    }

    private XMLReader createDTDParser(SAXTarget sAXTarget) throws SAXException {
        try {
            XMLReader createParser = createParser();
            if (sAXTarget.getDTDHandler() != null) {
                createParser.setDTDHandler(sAXTarget.getDTDHandler());
            }
            if (sAXTarget.getEntityResolver() != null) {
                createParser.setEntityResolver(sAXTarget.getEntityResolver());
            }
            if (sAXTarget.getLexicalHandler() != null) {
                try {
                    createParser.setProperty(JDOMConstants.SAX_PROPERTY_LEXICAL_HANDLER, sAXTarget.getLexicalHandler());
                } catch (SAXException unused) {
                    try {
                        createParser.setProperty(JDOMConstants.SAX_PROPERTY_LEXICAL_HANDLER_ALT, sAXTarget.getLexicalHandler());
                    } catch (SAXException unused2) {
                    }
                }
            }
            if (sAXTarget.getDeclHandler() != null) {
                try {
                    createParser.setProperty(JDOMConstants.SAX_PROPERTY_DECLARATION_HANDLER, sAXTarget.getDeclHandler());
                } catch (SAXException unused3) {
                    try {
                        createParser.setProperty(JDOMConstants.SAX_PROPERTY_DECLARATION_HANDLER_ALT, sAXTarget.getDeclHandler());
                    } catch (SAXException unused4) {
                    }
                }
            }
            createParser.setErrorHandler(new DefaultHandler());
            return createParser;
        } catch (Exception e) {
            throw new SAXException("Error in SAX parser allocation", e);
        }
    }
}
