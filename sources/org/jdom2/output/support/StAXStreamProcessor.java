package org.jdom2.output.support;

import java.util.List;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.output.Format;

public interface StAXStreamProcessor {
    void process(XMLStreamWriter xMLStreamWriter, Format format, List<? extends Content> list) throws XMLStreamException;

    void process(XMLStreamWriter xMLStreamWriter, Format format, CDATA cdata) throws XMLStreamException;

    void process(XMLStreamWriter xMLStreamWriter, Format format, Comment comment) throws XMLStreamException;

    void process(XMLStreamWriter xMLStreamWriter, Format format, DocType docType) throws XMLStreamException;

    void process(XMLStreamWriter xMLStreamWriter, Format format, Document document) throws XMLStreamException;

    void process(XMLStreamWriter xMLStreamWriter, Format format, Element element) throws XMLStreamException;

    void process(XMLStreamWriter xMLStreamWriter, Format format, EntityRef entityRef) throws XMLStreamException;

    void process(XMLStreamWriter xMLStreamWriter, Format format, ProcessingInstruction processingInstruction) throws XMLStreamException;

    void process(XMLStreamWriter xMLStreamWriter, Format format, Text text) throws XMLStreamException;
}
