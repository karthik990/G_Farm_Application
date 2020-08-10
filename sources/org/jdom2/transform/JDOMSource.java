package org.jdom2.transform;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.sax.SAXSource;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.output.SAXOutputter;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class JDOMSource extends SAXSource {
    public static final String JDOM_FEATURE = "http://jdom.org/jdom2/transform/JDOMSource/feature";
    private EntityResolver resolver;
    private XMLReader xmlReader;

    private static class DocumentReader extends SAXOutputter implements XMLReader {
        public void parse(String str) throws SAXNotSupportedException {
            throw new SAXNotSupportedException("Only JDOM Documents are supported as input");
        }

        public void parse(InputSource inputSource) throws SAXException {
            if (inputSource instanceof JDOMInputSource) {
                try {
                    Document documentSource = ((JDOMInputSource) inputSource).getDocumentSource();
                    if (documentSource != null) {
                        output(documentSource);
                    } else {
                        output(((JDOMInputSource) inputSource).getListSource());
                    }
                } catch (JDOMException e) {
                    throw new SAXException(e.getMessage(), e);
                }
            } else {
                throw new SAXNotSupportedException("Only JDOM Documents are supported as input");
            }
        }
    }

    private static class JDOMInputSource extends InputSource {
        private Document docsource = null;
        private List<? extends Content> listsource = null;

        public JDOMInputSource(Document document) {
            this.docsource = document;
        }

        public JDOMInputSource(List<? extends Content> list) {
            this.listsource = list;
        }

        public Object getSource() {
            Document document = this.docsource;
            return document == null ? this.listsource : document;
        }

        public void setCharacterStream(Reader reader) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        public Reader getCharacterStream() {
            if (this.docsource != null) {
                return new StringReader(new XMLOutputter().outputString(this.docsource));
            }
            if (this.listsource != null) {
                return new StringReader(new XMLOutputter().outputString(this.listsource));
            }
            return null;
        }

        public void setByteStream(InputStream inputStream) throws UnsupportedOperationException {
            throw new UnsupportedOperationException();
        }

        public Document getDocumentSource() {
            return this.docsource;
        }

        public List<? extends Content> getListSource() {
            return this.listsource;
        }
    }

    public JDOMSource(Document document) {
        this(document, null);
    }

    public JDOMSource(List<? extends Content> list) {
        this.xmlReader = null;
        this.resolver = null;
        setNodes(list);
    }

    public JDOMSource(Element element) {
        this.xmlReader = null;
        this.resolver = null;
        ArrayList arrayList = new ArrayList();
        arrayList.add(element);
        setNodes(arrayList);
    }

    public JDOMSource(Document document, EntityResolver entityResolver) {
        this.xmlReader = null;
        this.resolver = null;
        setDocument(document);
        this.resolver = entityResolver;
        if (document != null && document.getBaseURI() != null) {
            super.setSystemId(document.getBaseURI());
        }
    }

    public void setDocument(Document document) {
        super.setInputSource(new JDOMInputSource(document));
    }

    public Document getDocument() {
        Object source = ((JDOMInputSource) getInputSource()).getSource();
        if (source instanceof Document) {
            return (Document) source;
        }
        return null;
    }

    public void setNodes(List<? extends Content> list) {
        super.setInputSource(new JDOMInputSource(list));
    }

    public List<? extends Content> getNodes() {
        return ((JDOMInputSource) getInputSource()).getListSource();
    }

    public void setInputSource(InputSource inputSource) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public void setXMLReader(XMLReader xMLReader) throws UnsupportedOperationException {
        if (xMLReader instanceof XMLFilter) {
            XMLReader xMLReader2 = xMLReader;
            while (true) {
                XMLFilter xMLFilter = (XMLFilter) xMLReader2;
                if (xMLFilter.getParent() instanceof XMLFilter) {
                    xMLReader2 = (XMLFilter) xMLFilter.getParent();
                } else {
                    xMLFilter.setParent(buildDocumentReader());
                    this.xmlReader = xMLReader;
                    return;
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public XMLReader getXMLReader() {
        if (this.xmlReader == null) {
            this.xmlReader = buildDocumentReader();
        }
        return this.xmlReader;
    }

    private XMLReader buildDocumentReader() {
        DocumentReader documentReader = new DocumentReader();
        EntityResolver entityResolver = this.resolver;
        if (entityResolver != null) {
            documentReader.setEntityResolver(entityResolver);
        }
        return documentReader;
    }
}
