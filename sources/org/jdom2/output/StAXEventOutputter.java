package org.jdom2.output;

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
import org.jdom2.output.support.AbstractStAXEventProcessor;
import org.jdom2.output.support.StAXEventProcessor;

public final class StAXEventOutputter implements Cloneable {
    private static final XMLEventFactory DEFAULTEVENTFACTORY = XMLEventFactory.newInstance();
    private static final DefaultStAXEventProcessor DEFAULTPROCESSOR = new DefaultStAXEventProcessor();
    private XMLEventFactory myEventFactory;
    private Format myFormat;
    private StAXEventProcessor myProcessor;

    private static final class DefaultStAXEventProcessor extends AbstractStAXEventProcessor {
        private DefaultStAXEventProcessor() {
        }
    }

    public StAXEventOutputter(Format format, StAXEventProcessor stAXEventProcessor, XMLEventFactory xMLEventFactory) {
        this.myFormat = null;
        this.myProcessor = null;
        this.myEventFactory = null;
        this.myFormat = format == null ? Format.getRawFormat() : format.clone();
        if (stAXEventProcessor == null) {
            stAXEventProcessor = DEFAULTPROCESSOR;
        }
        this.myProcessor = stAXEventProcessor;
        if (xMLEventFactory == null) {
            xMLEventFactory = DEFAULTEVENTFACTORY;
        }
        this.myEventFactory = xMLEventFactory;
    }

    public StAXEventOutputter() {
        this(null, null, null);
    }

    public StAXEventOutputter(Format format) {
        this(format, null, null);
    }

    public StAXEventOutputter(StAXEventProcessor stAXEventProcessor) {
        this(null, stAXEventProcessor, null);
    }

    public StAXEventOutputter(XMLEventFactory xMLEventFactory) {
        this(null, null, xMLEventFactory);
    }

    public void setFormat(Format format) {
        this.myFormat = format.clone();
    }

    public Format getFormat() {
        return this.myFormat;
    }

    public StAXEventProcessor getStAXStream() {
        return this.myProcessor;
    }

    public void setStAXEventProcessor(StAXEventProcessor stAXEventProcessor) {
        this.myProcessor = stAXEventProcessor;
    }

    public XMLEventFactory getEventFactory() {
        return this.myEventFactory;
    }

    public void setEventFactory(XMLEventFactory xMLEventFactory) {
        this.myEventFactory = xMLEventFactory;
    }

    public final void output(Document document, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, document);
    }

    public final void output(DocType docType, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, docType);
    }

    public final void output(Element element, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, element);
    }

    public final void outputElementContent(Element element, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, element.getContent());
    }

    public final void output(List<? extends Content> list, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, list);
    }

    public final void output(CDATA cdata, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, cdata);
    }

    public final void output(Text text, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, text);
    }

    public final void output(Comment comment, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, comment);
    }

    public final void output(ProcessingInstruction processingInstruction, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, processingInstruction);
    }

    public final void output(EntityRef entityRef, XMLEventConsumer xMLEventConsumer) throws XMLStreamException {
        this.myProcessor.process(xMLEventConsumer, this.myFormat, this.myEventFactory, entityRef);
    }

    public StAXEventOutputter clone() {
        try {
            return (StAXEventOutputter) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StAXStreamOutputter[omitDeclaration = ");
        sb.append(this.myFormat.omitDeclaration);
        String str = ", ";
        sb.append(str);
        sb.append("encoding = ");
        sb.append(this.myFormat.encoding);
        sb.append(str);
        sb.append("omitEncoding = ");
        sb.append(this.myFormat.omitEncoding);
        sb.append(str);
        sb.append("indent = '");
        sb.append(this.myFormat.indent);
        sb.append("'");
        sb.append(str);
        sb.append("expandEmptyElements = ");
        sb.append(this.myFormat.expandEmptyElements);
        sb.append(str);
        sb.append("lineSeparator = '");
        char[] charArray = this.myFormat.lineSeparator.toCharArray();
        int length = charArray.length;
        int i = 0;
        while (true) {
            String str2 = "]";
            if (i < length) {
                char c = charArray[i];
                if (c == 9) {
                    sb.append("\\t");
                } else if (c == 10) {
                    sb.append("\\n");
                } else if (c != 13) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("[");
                    sb2.append(c);
                    sb2.append(str2);
                    sb.append(sb2.toString());
                } else {
                    sb.append("\\r");
                }
                i++;
            } else {
                sb.append("', ");
                sb.append("textMode = ");
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.myFormat.mode);
                sb3.append(str2);
                sb.append(sb3.toString());
                return sb.toString();
            }
        }
    }
}
