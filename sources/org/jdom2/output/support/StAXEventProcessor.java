package org.jdom2.output.support;

import java.util.List;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.util.XMLEventConsumer;
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

public interface StAXEventProcessor {
    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, List<? extends Content> list) throws XMLStreamException;

    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, CDATA cdata) throws XMLStreamException;

    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, Comment comment) throws XMLStreamException;

    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, DocType docType) throws XMLStreamException;

    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, Document document) throws XMLStreamException;

    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, Element element) throws XMLStreamException;

    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, EntityRef entityRef) throws XMLStreamException;

    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, ProcessingInstruction processingInstruction) throws XMLStreamException;

    void process(XMLEventConsumer xMLEventConsumer, Format format, XMLEventFactory xMLEventFactory, Text text) throws XMLStreamException;
}
