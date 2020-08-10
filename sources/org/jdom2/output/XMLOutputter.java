package org.jdom2.output;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.output.support.AbstractXMLOutputProcessor;
import org.jdom2.output.support.FormatStack;
import org.jdom2.output.support.XMLOutputProcessor;

public final class XMLOutputter implements Cloneable {
    private static final DefaultXMLProcessor DEFAULTPROCESSOR = new DefaultXMLProcessor();
    private Format myFormat;
    private XMLOutputProcessor myProcessor;

    private static final class DefaultXMLProcessor extends AbstractXMLOutputProcessor {
        private DefaultXMLProcessor() {
        }

        public String escapeAttributeEntities(String str, Format format) {
            StringWriter stringWriter = new StringWriter();
            try {
                super.attributeEscapedEntitiesFilter(stringWriter, new FormatStack(format), str);
            } catch (IOException unused) {
            }
            return stringWriter.toString();
        }

        public final String escapeElementEntities(String str, Format format) {
            return Format.escapeText(format.getEscapeStrategy(), format.getLineSeparator(), str);
        }
    }

    private static final Writer makeWriter(OutputStream outputStream, Format format) throws UnsupportedEncodingException {
        return new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(outputStream), format.getEncoding()));
    }

    public XMLOutputter(Format format, XMLOutputProcessor xMLOutputProcessor) {
        this.myFormat = null;
        this.myProcessor = null;
        this.myFormat = format == null ? Format.getRawFormat() : format.clone();
        if (xMLOutputProcessor == null) {
            xMLOutputProcessor = DEFAULTPROCESSOR;
        }
        this.myProcessor = xMLOutputProcessor;
    }

    public XMLOutputter() {
        this(null, null);
    }

    public XMLOutputter(XMLOutputter xMLOutputter) {
        this(xMLOutputter.myFormat, null);
    }

    public XMLOutputter(Format format) {
        this(format, null);
    }

    public XMLOutputter(XMLOutputProcessor xMLOutputProcessor) {
        this(null, xMLOutputProcessor);
    }

    public void setFormat(Format format) {
        this.myFormat = format.clone();
    }

    public Format getFormat() {
        return this.myFormat;
    }

    public XMLOutputProcessor getXMLOutputProcessor() {
        return this.myProcessor;
    }

    public void setXMLOutputProcessor(XMLOutputProcessor xMLOutputProcessor) {
        this.myProcessor = xMLOutputProcessor;
    }

    public final void output(Document document, OutputStream outputStream) throws IOException {
        output(document, makeWriter(outputStream, this.myFormat));
    }

    public final void output(DocType docType, OutputStream outputStream) throws IOException {
        output(docType, makeWriter(outputStream, this.myFormat));
    }

    public final void output(Element element, OutputStream outputStream) throws IOException {
        output(element, makeWriter(outputStream, this.myFormat));
    }

    public final void outputElementContent(Element element, OutputStream outputStream) throws IOException {
        outputElementContent(element, makeWriter(outputStream, this.myFormat));
    }

    public final void output(List<? extends Content> list, OutputStream outputStream) throws IOException {
        output(list, makeWriter(outputStream, this.myFormat));
    }

    public final void output(CDATA cdata, OutputStream outputStream) throws IOException {
        output(cdata, makeWriter(outputStream, this.myFormat));
    }

    public final void output(Text text, OutputStream outputStream) throws IOException {
        output(text, makeWriter(outputStream, this.myFormat));
    }

    public final void output(Comment comment, OutputStream outputStream) throws IOException {
        output(comment, makeWriter(outputStream, this.myFormat));
    }

    public final void output(ProcessingInstruction processingInstruction, OutputStream outputStream) throws IOException {
        output(processingInstruction, makeWriter(outputStream, this.myFormat));
    }

    public void output(EntityRef entityRef, OutputStream outputStream) throws IOException {
        output(entityRef, makeWriter(outputStream, this.myFormat));
    }

    public final String outputString(Document document) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(document, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(DocType docType) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(docType, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(Element element) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(element, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(List<? extends Content> list) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(list, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(CDATA cdata) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(cdata, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(Text text) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(text, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(Comment comment) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(comment, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(ProcessingInstruction processingInstruction) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(processingInstruction, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputString(EntityRef entityRef) {
        StringWriter stringWriter = new StringWriter();
        try {
            output(entityRef, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final String outputElementContentString(Element element) {
        StringWriter stringWriter = new StringWriter();
        try {
            outputElementContent(element, (Writer) stringWriter);
        } catch (IOException unused) {
        }
        return stringWriter.toString();
    }

    public final void output(Document document, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, document);
        writer.flush();
    }

    public final void output(DocType docType, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, docType);
        writer.flush();
    }

    public final void output(Element element, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, element);
        writer.flush();
    }

    public final void outputElementContent(Element element, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, element.getContent());
        writer.flush();
    }

    public final void output(List<? extends Content> list, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, list);
        writer.flush();
    }

    public final void output(CDATA cdata, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, cdata);
        writer.flush();
    }

    public final void output(Text text, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, text);
        writer.flush();
    }

    public final void output(Comment comment, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, comment);
        writer.flush();
    }

    public final void output(ProcessingInstruction processingInstruction, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, processingInstruction);
        writer.flush();
    }

    public final void output(EntityRef entityRef, Writer writer) throws IOException {
        this.myProcessor.process(writer, this.myFormat, entityRef);
        writer.flush();
    }

    public String escapeAttributeEntities(String str) {
        return DEFAULTPROCESSOR.escapeAttributeEntities(str, this.myFormat);
    }

    public String escapeElementEntities(String str) {
        return DEFAULTPROCESSOR.escapeElementEntities(str, this.myFormat);
    }

    public XMLOutputter clone() {
        try {
            return (XMLOutputter) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.toString());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("XMLOutputter[omitDeclaration = ");
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
