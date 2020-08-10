package org.jdom2.output;

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
import org.jdom2.output.support.AbstractStAXStreamProcessor;
import org.jdom2.output.support.StAXStreamProcessor;

public final class StAXStreamOutputter implements Cloneable {
    private static final DefaultStAXStreamProcessor DEFAULTPROCESSOR = new DefaultStAXStreamProcessor();
    private Format myFormat;
    private StAXStreamProcessor myProcessor;

    private static final class DefaultStAXStreamProcessor extends AbstractStAXStreamProcessor {
        private DefaultStAXStreamProcessor() {
        }
    }

    public StAXStreamOutputter(Format format, StAXStreamProcessor stAXStreamProcessor) {
        this.myFormat = null;
        this.myProcessor = null;
        this.myFormat = format == null ? Format.getRawFormat() : format.clone();
        if (stAXStreamProcessor == null) {
            stAXStreamProcessor = DEFAULTPROCESSOR;
        }
        this.myProcessor = stAXStreamProcessor;
    }

    public StAXStreamOutputter() {
        this(null, null);
    }

    public StAXStreamOutputter(Format format) {
        this(format, null);
    }

    public StAXStreamOutputter(StAXStreamProcessor stAXStreamProcessor) {
        this(null, stAXStreamProcessor);
    }

    public void setFormat(Format format) {
        this.myFormat = format.clone();
    }

    public Format getFormat() {
        return this.myFormat;
    }

    public StAXStreamProcessor getStAXStream() {
        return this.myProcessor;
    }

    public void setStAXStreamProcessor(StAXStreamProcessor stAXStreamProcessor) {
        this.myProcessor = stAXStreamProcessor;
    }

    public final void output(Document document, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, document);
        xMLStreamWriter.flush();
    }

    public final void output(DocType docType, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, docType);
        xMLStreamWriter.flush();
    }

    public final void output(Element element, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, element);
        xMLStreamWriter.flush();
    }

    public final void outputElementContent(Element element, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, element.getContent());
        xMLStreamWriter.flush();
    }

    public final void output(List<? extends Content> list, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, list);
        xMLStreamWriter.flush();
    }

    public final void output(CDATA cdata, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, cdata);
        xMLStreamWriter.flush();
    }

    public final void output(Text text, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, text);
        xMLStreamWriter.flush();
    }

    public final void output(Comment comment, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, comment);
        xMLStreamWriter.flush();
    }

    public final void output(ProcessingInstruction processingInstruction, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, processingInstruction);
        xMLStreamWriter.flush();
    }

    public final void output(EntityRef entityRef, XMLStreamWriter xMLStreamWriter) throws XMLStreamException {
        this.myProcessor.process(xMLStreamWriter, this.myFormat, entityRef);
        xMLStreamWriter.flush();
    }

    public StAXStreamOutputter clone() {
        try {
            return (StAXStreamOutputter) super.clone();
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
