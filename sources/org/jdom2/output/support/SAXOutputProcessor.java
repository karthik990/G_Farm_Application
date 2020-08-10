package org.jdom2.output.support;

import java.util.List;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.JDOMException;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.output.Format;

public interface SAXOutputProcessor {
    void process(SAXTarget sAXTarget, Format format, List<? extends Content> list) throws JDOMException;

    void process(SAXTarget sAXTarget, Format format, CDATA cdata) throws JDOMException;

    void process(SAXTarget sAXTarget, Format format, Comment comment) throws JDOMException;

    void process(SAXTarget sAXTarget, Format format, DocType docType) throws JDOMException;

    void process(SAXTarget sAXTarget, Format format, Document document) throws JDOMException;

    void process(SAXTarget sAXTarget, Format format, Element element) throws JDOMException;

    void process(SAXTarget sAXTarget, Format format, EntityRef entityRef) throws JDOMException;

    void process(SAXTarget sAXTarget, Format format, ProcessingInstruction processingInstruction) throws JDOMException;

    void process(SAXTarget sAXTarget, Format format, Text text) throws JDOMException;

    void processAsDocument(SAXTarget sAXTarget, Format format, List<? extends Content> list) throws JDOMException;

    void processAsDocument(SAXTarget sAXTarget, Format format, Element element) throws JDOMException;
}
