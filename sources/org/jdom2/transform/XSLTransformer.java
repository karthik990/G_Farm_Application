package org.jdom2.transform;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.JDOMFactory;
import org.xml.sax.EntityResolver;

public class XSLTransformer {
    private JDOMFactory factory;
    private Templates templates;

    private XSLTransformer(Source source) throws XSLTransformException {
        this.factory = null;
        try {
            this.templates = TransformerFactory.newInstance().newTemplates(source);
        } catch (TransformerException e) {
            throw new XSLTransformException("Could not construct XSLTransformer", e);
        }
    }

    public XSLTransformer(String str) throws XSLTransformException {
        this((Source) new StreamSource(str));
    }

    public XSLTransformer(InputStream inputStream) throws XSLTransformException {
        this((Source) new StreamSource(inputStream));
    }

    public XSLTransformer(Reader reader) throws XSLTransformException {
        this((Source) new StreamSource(reader));
    }

    public XSLTransformer(File file) throws XSLTransformException {
        this((Source) new StreamSource(file));
    }

    public XSLTransformer(Document document) throws XSLTransformException {
        this((Source) new JDOMSource(document));
    }

    public List<Content> transform(List<Content> list) throws XSLTransformException {
        JDOMSource jDOMSource = new JDOMSource(list);
        JDOMResult jDOMResult = new JDOMResult();
        jDOMResult.setFactory(this.factory);
        try {
            this.templates.newTransformer().transform(jDOMSource, jDOMResult);
            return jDOMResult.getResult();
        } catch (TransformerException e) {
            throw new XSLTransformException("Could not perform transformation", e);
        }
    }

    public Document transform(Document document) throws XSLTransformException {
        return transform(document, null);
    }

    public Document transform(Document document, EntityResolver entityResolver) throws XSLTransformException {
        JDOMSource jDOMSource = new JDOMSource(document, entityResolver);
        JDOMResult jDOMResult = new JDOMResult();
        jDOMResult.setFactory(this.factory);
        try {
            this.templates.newTransformer().transform(jDOMSource, jDOMResult);
            return jDOMResult.getDocument();
        } catch (TransformerException e) {
            throw new XSLTransformException("Could not perform transformation", e);
        }
    }

    public void setFactory(JDOMFactory jDOMFactory) {
        this.factory = jDOMFactory;
    }

    public JDOMFactory getFactory() {
        return this.factory;
    }
}
