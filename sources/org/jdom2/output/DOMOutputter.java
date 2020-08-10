package org.jdom2.output;

import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.EntityRef;
import org.jdom2.JDOMException;
import org.jdom2.adapters.DOMAdapter;
import org.jdom2.adapters.JAXPDOMAdapter;
import org.jdom2.internal.ReflectionConstructor;
import org.jdom2.output.support.AbstractDOMOutputProcessor;
import org.jdom2.output.support.DOMOutputProcessor;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public class DOMOutputter {
    private static final DOMAdapter DEFAULT_ADAPTER = new JAXPDOMAdapter();
    private static final DOMOutputProcessor DEFAULT_PROCESSOR = new DefaultDOMOutputProcessor();
    private DOMAdapter adapter;
    private Format format;
    private DOMOutputProcessor processor;

    private static final class DefaultDOMOutputProcessor extends AbstractDOMOutputProcessor {
        private DefaultDOMOutputProcessor() {
        }
    }

    @Deprecated
    public boolean getForceNamespaceAware() {
        return true;
    }

    @Deprecated
    public void setForceNamespaceAware(boolean z) {
    }

    public DOMOutputter() {
        this(null, null, null);
    }

    public DOMOutputter(DOMOutputProcessor dOMOutputProcessor) {
        this(null, null, dOMOutputProcessor);
    }

    public DOMOutputter(DOMAdapter dOMAdapter, Format format2, DOMOutputProcessor dOMOutputProcessor) {
        if (dOMAdapter == null) {
            dOMAdapter = DEFAULT_ADAPTER;
        }
        this.adapter = dOMAdapter;
        if (format2 == null) {
            format2 = Format.getRawFormat();
        }
        this.format = format2;
        if (dOMOutputProcessor == null) {
            dOMOutputProcessor = DEFAULT_PROCESSOR;
        }
        this.processor = dOMOutputProcessor;
    }

    @Deprecated
    public DOMOutputter(String str) {
        if (str == null) {
            this.adapter = DEFAULT_ADAPTER;
        } else {
            this.adapter = (DOMAdapter) ReflectionConstructor.construct(str, DOMAdapter.class);
        }
    }

    public DOMOutputter(DOMAdapter dOMAdapter) {
        if (dOMAdapter == null) {
            dOMAdapter = DEFAULT_ADAPTER;
        }
        this.adapter = dOMAdapter;
    }

    public DOMAdapter getDOMAdapter() {
        return this.adapter;
    }

    public void setDOMAdapter(DOMAdapter dOMAdapter) {
        if (dOMAdapter == null) {
            dOMAdapter = DEFAULT_ADAPTER;
        }
        this.adapter = dOMAdapter;
    }

    public Format getFormat() {
        return this.format;
    }

    public void setFormat(Format format2) {
        if (format2 == null) {
            format2 = Format.getRawFormat();
        }
        this.format = format2;
    }

    public DOMOutputProcessor getDOMOutputProcessor() {
        return this.processor;
    }

    public void setDOMOutputProcessor(DOMOutputProcessor dOMOutputProcessor) {
        if (dOMOutputProcessor == null) {
            dOMOutputProcessor = DEFAULT_PROCESSOR;
        }
        this.processor = dOMOutputProcessor;
    }

    public Document output(org.jdom2.Document document) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(document.getDocType()), this.format, document);
    }

    public DocumentType output(DocType docType) throws JDOMException {
        return this.adapter.createDocument(docType).getDoctype();
    }

    public Element output(org.jdom2.Element element) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(), this.format, element);
    }

    public Text output(org.jdom2.Text text) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(), this.format, text);
    }

    public CDATASection output(CDATA cdata) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(), this.format, cdata);
    }

    public ProcessingInstruction output(org.jdom2.ProcessingInstruction processingInstruction) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(), this.format, processingInstruction);
    }

    public Comment output(org.jdom2.Comment comment) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(), this.format, comment);
    }

    public EntityReference output(EntityRef entityRef) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(), this.format, entityRef);
    }

    public Attr output(Attribute attribute) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(), this.format, attribute);
    }

    public List<Node> output(List<? extends Content> list) throws JDOMException {
        return this.processor.process(this.adapter.createDocument(), this.format, list);
    }

    public Element output(Document document, org.jdom2.Element element) throws JDOMException {
        return this.processor.process(document, this.format, element);
    }

    public Text output(Document document, org.jdom2.Text text) throws JDOMException {
        return this.processor.process(document, this.format, text);
    }

    public CDATASection output(Document document, CDATA cdata) throws JDOMException {
        return this.processor.process(document, this.format, cdata);
    }

    public ProcessingInstruction output(Document document, org.jdom2.ProcessingInstruction processingInstruction) throws JDOMException {
        return this.processor.process(document, this.format, processingInstruction);
    }

    public Comment output(Document document, org.jdom2.Comment comment) throws JDOMException {
        return this.processor.process(document, this.format, comment);
    }

    public EntityReference output(Document document, EntityRef entityRef) throws JDOMException {
        return this.processor.process(document, this.format, entityRef);
    }

    public Attr output(Document document, Attribute attribute) throws JDOMException {
        return this.processor.process(document, this.format, attribute);
    }

    public List<Node> output(Document document, List<? extends Content> list) throws JDOMException {
        return this.processor.process(document, this.format, list);
    }
}
