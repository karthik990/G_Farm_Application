package org.jdom2.input.sax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom2.Attribute;
import org.jdom2.AttributeType;
import org.jdom2.Comment;
import org.jdom2.DefaultJDOMFactory;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMConstants;
import org.jdom2.JDOMFactory;
import org.jdom2.Namespace;
import org.jdom2.Parent;
import org.jdom2.ProcessingInstruction;
import org.xml.sax.Attributes;
import org.xml.sax.DTDHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.Attributes2;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class SAXHandler extends DefaultHandler implements LexicalHandler, DeclHandler, DTDHandler {
    private boolean atRoot;
    private Document currentDocument;
    private Element currentElement;
    private Locator currentLocator;
    private final List<Namespace> declaredNamespaces;
    private int entityDepth;
    private boolean expand;
    private final Map<String, String[]> externalEntities;
    private final JDOMFactory factory;
    private boolean ignoringBoundaryWhite;
    private boolean ignoringWhite;
    private boolean inCDATA;
    private boolean inDTD;
    private boolean inInternalSubset;
    private final StringBuilder internalSubset;
    private int lastcol;
    private int lastline;
    private boolean previousCDATA;
    private boolean suppress;
    private final TextBuffer textBuffer;

    /* access modifiers changed from: protected */
    public void resetSubCLass() {
    }

    public SAXHandler() {
        this(null);
    }

    public SAXHandler(JDOMFactory jDOMFactory) {
        this.declaredNamespaces = new ArrayList(32);
        this.internalSubset = new StringBuilder();
        this.textBuffer = new TextBuffer();
        this.externalEntities = new HashMap();
        this.currentDocument = null;
        this.currentElement = null;
        this.currentLocator = null;
        this.atRoot = true;
        this.inDTD = false;
        this.inInternalSubset = false;
        this.previousCDATA = false;
        this.inCDATA = false;
        this.expand = true;
        this.suppress = false;
        this.entityDepth = 0;
        this.ignoringWhite = false;
        this.ignoringBoundaryWhite = false;
        this.lastline = 0;
        this.lastcol = 0;
        if (jDOMFactory == null) {
            jDOMFactory = new DefaultJDOMFactory();
        }
        this.factory = jDOMFactory;
        reset();
    }

    public final void reset() {
        this.currentLocator = null;
        this.currentDocument = this.factory.document(null);
        this.currentElement = null;
        this.atRoot = true;
        this.inDTD = false;
        this.inInternalSubset = false;
        this.previousCDATA = false;
        this.inCDATA = false;
        this.expand = true;
        this.suppress = false;
        this.entityDepth = 0;
        this.declaredNamespaces.clear();
        this.internalSubset.setLength(0);
        this.textBuffer.clear();
        this.externalEntities.clear();
        this.ignoringWhite = false;
        this.ignoringBoundaryWhite = false;
        resetSubCLass();
    }

    /* access modifiers changed from: protected */
    public void pushElement(Element element) {
        if (this.atRoot) {
            this.currentDocument.setRootElement(element);
            this.atRoot = false;
        } else {
            this.factory.addContent(this.currentElement, element);
        }
        this.currentElement = element;
    }

    public Document getDocument() {
        return this.currentDocument;
    }

    public JDOMFactory getFactory() {
        return this.factory;
    }

    public void setExpandEntities(boolean z) {
        this.expand = z;
    }

    public boolean getExpandEntities() {
        return this.expand;
    }

    public void setIgnoringElementContentWhitespace(boolean z) {
        this.ignoringWhite = z;
    }

    public void setIgnoringBoundaryWhitespace(boolean z) {
        this.ignoringBoundaryWhite = z;
    }

    public boolean getIgnoringBoundaryWhitespace() {
        return this.ignoringBoundaryWhite;
    }

    public boolean getIgnoringElementContentWhitespace() {
        return this.ignoringWhite;
    }

    public void startDocument() {
        Locator locator = this.currentLocator;
        if (locator != null) {
            this.currentDocument.setBaseURI(locator.getSystemId());
        }
    }

    public void externalEntityDecl(String str, String str2, String str3) throws SAXException {
        this.externalEntities.put(str, new String[]{str2, str3});
        if (this.inInternalSubset) {
            StringBuilder sb = this.internalSubset;
            sb.append("  <!ENTITY ");
            sb.append(str);
            appendExternalId(str2, str3);
            this.internalSubset.append(">\n");
        }
    }

    public void attributeDecl(String str, String str2, String str3, String str4, String str5) {
        if (this.inInternalSubset) {
            StringBuilder sb = this.internalSubset;
            sb.append("  <!ATTLIST ");
            sb.append(str);
            sb.append(' ');
            sb.append(str2);
            sb.append(' ');
            sb.append(str3);
            sb.append(' ');
            if (str4 != null) {
                this.internalSubset.append(str4);
            } else {
                StringBuilder sb2 = this.internalSubset;
                sb2.append('\"');
                sb2.append(str5);
                sb2.append('\"');
            }
            if (str4 != null && str4.equals("#FIXED")) {
                StringBuilder sb3 = this.internalSubset;
                sb3.append(" \"");
                sb3.append(str5);
                sb3.append('\"');
            }
            this.internalSubset.append(">\n");
        }
    }

    public void elementDecl(String str, String str2) {
        if (this.inInternalSubset) {
            StringBuilder sb = this.internalSubset;
            sb.append("  <!ELEMENT ");
            sb.append(str);
            sb.append(' ');
            sb.append(str2);
            sb.append(">\n");
        }
    }

    public void internalEntityDecl(String str, String str2) {
        if (this.inInternalSubset) {
            this.internalSubset.append("  <!ENTITY ");
            if (str.startsWith("%")) {
                StringBuilder sb = this.internalSubset;
                sb.append("% ");
                sb.append(str.substring(1));
            } else {
                this.internalSubset.append(str);
            }
            StringBuilder sb2 = this.internalSubset;
            sb2.append(" \"");
            sb2.append(str2);
            sb2.append("\">\n");
        }
    }

    public void processingInstruction(String str, String str2) throws SAXException {
        if (!this.suppress) {
            flushCharacters();
            Locator locator = this.currentLocator;
            ProcessingInstruction processingInstruction = locator == null ? this.factory.processingInstruction(str, str2) : this.factory.processingInstruction(locator.getLineNumber(), this.currentLocator.getColumnNumber(), str, str2);
            if (this.atRoot) {
                this.factory.addContent(this.currentDocument, processingInstruction);
            } else {
                this.factory.addContent(getCurrentElement(), processingInstruction);
            }
        }
    }

    public void skippedEntity(String str) throws SAXException {
        if (!str.startsWith("%")) {
            flushCharacters();
            Locator locator = this.currentLocator;
            this.factory.addContent(getCurrentElement(), locator == null ? this.factory.entityRef(str) : this.factory.entityRef(locator.getLineNumber(), this.currentLocator.getColumnNumber(), str));
        }
    }

    public void startPrefixMapping(String str, String str2) throws SAXException {
        if (!this.suppress) {
            this.declaredNamespaces.add(Namespace.getNamespace(str, str2));
        }
    }

    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        String str4;
        String str5;
        String str6;
        String str7 = str2;
        String str8 = str3;
        Attributes attributes2 = attributes;
        if (!this.suppress) {
            String str9 = "";
            int i = 58;
            if (!str9.equals(str8)) {
                int indexOf = str8.indexOf(58);
                str4 = indexOf > 0 ? str8.substring(0, indexOf) : str9;
                if (str7 == null || str7.equals(str9)) {
                    str7 = str8.substring(indexOf + 1);
                }
                str5 = str7;
            } else {
                str5 = str7;
                str4 = str9;
            }
            Namespace namespace = Namespace.getNamespace(str4, str);
            Locator locator = this.currentLocator;
            Element element = locator == null ? this.factory.element(str5, namespace) : this.factory.element(locator.getLineNumber(), this.currentLocator.getColumnNumber(), str5, namespace);
            if (this.declaredNamespaces.size() > 0) {
                transferNamespaces(element);
            }
            flushCharacters();
            if (this.atRoot) {
                this.factory.setRoot(this.currentDocument, element);
                this.atRoot = false;
            } else {
                this.factory.addContent(getCurrentElement(), element);
            }
            this.currentElement = element;
            int length = attributes.getLength();
            int i2 = 0;
            while (i2 < length) {
                String localName = attributes2.getLocalName(i2);
                String qName = attributes2.getQName(i2);
                boolean isSpecified = attributes2 instanceof Attributes2 ? ((Attributes2) attributes2).isSpecified(i2) : true;
                boolean equals = qName.equals(str9);
                String str10 = JDOMConstants.NS_PREFIX_XMLNS;
                if (!equals) {
                    if (!qName.startsWith("xmlns:") && !qName.equals(str10)) {
                        int indexOf2 = qName.indexOf(i);
                        str6 = indexOf2 > 0 ? qName.substring(0, indexOf2) : str9;
                        if (str9.equals(localName)) {
                            localName = qName.substring(indexOf2 + 1);
                        }
                    }
                    i2++;
                    i = 58;
                } else {
                    str6 = str9;
                }
                AttributeType attributeType = AttributeType.getAttributeType(attributes2.getType(i2));
                String value = attributes2.getValue(i2);
                String uri = attributes2.getURI(i2);
                if (!str10.equals(localName) && !str10.equals(str6) && !JDOMConstants.NS_URI_XMLNS.equals(uri)) {
                    if (!str9.equals(uri) && str9.equals(str6)) {
                        HashMap hashMap = new HashMap();
                        Iterator it = element.getNamespacesInScope().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Namespace namespace2 = (Namespace) it.next();
                            if (namespace2.getPrefix().length() > 0 && namespace2.getURI().equals(uri)) {
                                str6 = namespace2.getPrefix();
                                break;
                            }
                            hashMap.put(namespace2.getPrefix(), namespace2);
                        }
                        if (str9.equals(str6)) {
                            StringBuilder sb = new StringBuilder();
                            String str11 = "attns";
                            sb.append(str11);
                            sb.append(0);
                            String sb2 = sb.toString();
                            int i3 = 0;
                            while (hashMap.containsKey(str6)) {
                                i3++;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append(str11);
                                sb3.append(i3);
                                sb2 = sb3.toString();
                            }
                        }
                    }
                    Attribute attribute = this.factory.attribute(localName, value, attributeType, Namespace.getNamespace(str6, uri));
                    if (!isSpecified) {
                        attribute.setSpecified(false);
                    }
                    this.factory.setAttribute(element, attribute);
                    i2++;
                    i = 58;
                }
                i2++;
                i = 58;
            }
        }
    }

    private void transferNamespaces(Element element) {
        for (Namespace namespace : this.declaredNamespaces) {
            if (namespace != element.getNamespace()) {
                element.addNamespaceDeclaration(namespace);
            }
        }
        this.declaredNamespaces.clear();
    }

    public void characters(char[] cArr, int i, int i2) throws SAXException {
        if (this.suppress) {
            return;
        }
        if (i2 != 0 || this.inCDATA) {
            if (this.previousCDATA != this.inCDATA) {
                flushCharacters();
            }
            this.textBuffer.append(cArr, i, i2);
            Locator locator = this.currentLocator;
            if (locator != null) {
                this.lastline = locator.getLineNumber();
                this.lastcol = this.currentLocator.getColumnNumber();
            }
        }
    }

    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        if (!this.ignoringWhite) {
            characters(cArr, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    public void flushCharacters() throws SAXException {
        if (!this.ignoringBoundaryWhite) {
            flushCharacters(this.textBuffer.toString());
        } else if (!this.textBuffer.isAllWhitespace()) {
            flushCharacters(this.textBuffer.toString());
        }
        this.textBuffer.clear();
    }

    /* access modifiers changed from: protected */
    public void flushCharacters(String str) throws SAXException {
        if (str.length() == 0) {
            boolean z = this.inCDATA;
            if (!z) {
                this.previousCDATA = z;
                return;
            }
        }
        if (this.previousCDATA) {
            this.factory.addContent(getCurrentElement(), this.currentLocator == null ? this.factory.cdata(str) : this.factory.cdata(this.lastline, this.lastcol, str));
        } else {
            this.factory.addContent(getCurrentElement(), this.currentLocator == null ? this.factory.text(str) : this.factory.text(this.lastline, this.lastcol, str));
        }
        this.previousCDATA = this.inCDATA;
    }

    public void endElement(String str, String str2, String str3) throws SAXException {
        if (!this.suppress) {
            flushCharacters();
            if (!this.atRoot) {
                Parent parent = this.currentElement.getParent();
                if (parent instanceof Document) {
                    this.atRoot = true;
                } else {
                    this.currentElement = (Element) parent;
                }
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Ill-formed XML document (missing opening tag for ");
            sb.append(str2);
            sb.append(")");
            throw new SAXException(sb.toString());
        }
    }

    public void startDTD(String str, String str2, String str3) throws SAXException {
        DocType docType;
        flushCharacters();
        Locator locator = this.currentLocator;
        if (locator == null) {
            docType = this.factory.docType(str, str2, str3);
        } else {
            docType = this.factory.docType(locator.getLineNumber(), this.currentLocator.getColumnNumber(), str, str2, str3);
        }
        this.factory.addContent(this.currentDocument, docType);
        this.inDTD = true;
        this.inInternalSubset = true;
    }

    public void endDTD() {
        this.currentDocument.getDocType().setInternalSubset(this.internalSubset.toString());
        this.inDTD = false;
        this.inInternalSubset = false;
    }

    public void startEntity(String str) throws SAXException {
        String str2;
        String str3;
        this.entityDepth++;
        if (!this.expand && this.entityDepth <= 1) {
            if (str.equals("[dtd]")) {
                this.inInternalSubset = false;
            } else if (!this.inDTD && !str.equals("amp") && !str.equals("lt") && !str.equals("gt") && !str.equals("apos") && !str.equals("quot") && !this.expand) {
                String[] strArr = (String[]) this.externalEntities.get(str);
                if (strArr != null) {
                    String str4 = strArr[0];
                    str2 = strArr[1];
                    str3 = str4;
                } else {
                    str3 = null;
                    str2 = null;
                }
                if (!this.atRoot) {
                    flushCharacters();
                    Locator locator = this.currentLocator;
                    this.factory.addContent(getCurrentElement(), locator == null ? this.factory.entityRef(str, str3, str2) : this.factory.entityRef(locator.getLineNumber(), this.currentLocator.getColumnNumber(), str, str3, str2));
                }
                this.suppress = true;
            }
        }
    }

    public void endEntity(String str) throws SAXException {
        this.entityDepth--;
        if (this.entityDepth == 0) {
            this.suppress = false;
        }
        if (str.equals("[dtd]")) {
            this.inInternalSubset = true;
        }
    }

    public void startCDATA() {
        if (!this.suppress) {
            this.inCDATA = true;
        }
    }

    public void endCDATA() throws SAXException {
        if (!this.suppress) {
            this.previousCDATA = true;
            flushCharacters();
            this.previousCDATA = false;
            this.inCDATA = false;
        }
    }

    public void comment(char[] cArr, int i, int i2) throws SAXException {
        if (!this.suppress) {
            flushCharacters();
            String str = new String(cArr, i, i2);
            if (!this.inDTD || !this.inInternalSubset || this.expand) {
                if (!this.inDTD && !str.equals("")) {
                    Locator locator = this.currentLocator;
                    Comment comment = locator == null ? this.factory.comment(str) : this.factory.comment(locator.getLineNumber(), this.currentLocator.getColumnNumber(), str);
                    if (this.atRoot) {
                        this.factory.addContent(this.currentDocument, comment);
                    } else {
                        this.factory.addContent(getCurrentElement(), comment);
                    }
                }
                return;
            }
            StringBuilder sb = this.internalSubset;
            sb.append("  <!--");
            sb.append(str);
            sb.append("-->\n");
        }
    }

    public void notationDecl(String str, String str2, String str3) throws SAXException {
        if (this.inInternalSubset) {
            StringBuilder sb = this.internalSubset;
            sb.append("  <!NOTATION ");
            sb.append(str);
            appendExternalId(str2, str3);
            this.internalSubset.append(">\n");
        }
    }

    public void unparsedEntityDecl(String str, String str2, String str3, String str4) {
        if (this.inInternalSubset) {
            StringBuilder sb = this.internalSubset;
            sb.append("  <!ENTITY ");
            sb.append(str);
            appendExternalId(str2, str3);
            StringBuilder sb2 = this.internalSubset;
            sb2.append(" NDATA ");
            sb2.append(str4);
            this.internalSubset.append(">\n");
        }
    }

    private void appendExternalId(String str, String str2) {
        if (str != null) {
            StringBuilder sb = this.internalSubset;
            sb.append(" PUBLIC \"");
            sb.append(str);
            sb.append('\"');
        }
        if (str2 != null) {
            if (str == null) {
                this.internalSubset.append(" SYSTEM ");
            } else {
                this.internalSubset.append(' ');
            }
            StringBuilder sb2 = this.internalSubset;
            sb2.append('\"');
            sb2.append(str2);
            sb2.append('\"');
        }
    }

    public Element getCurrentElement() throws SAXException {
        Element element = this.currentElement;
        if (element != null) {
            return element;
        }
        throw new SAXException("Ill-formed XML document (multiple root elements detected)");
    }

    public void setDocumentLocator(Locator locator) {
        this.currentLocator = locator;
    }

    public Locator getDocumentLocator() {
        return this.currentLocator;
    }
}
