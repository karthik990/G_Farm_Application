package org.jdom2.output.support;

import java.io.IOException;
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
import org.jdom2.output.Format;

public interface XMLOutputProcessor {
    void process(Writer writer, Format format, List<? extends Content> list) throws IOException;

    void process(Writer writer, Format format, CDATA cdata) throws IOException;

    void process(Writer writer, Format format, Comment comment) throws IOException;

    void process(Writer writer, Format format, DocType docType) throws IOException;

    void process(Writer writer, Format format, Document document) throws IOException;

    void process(Writer writer, Format format, Element element) throws IOException;

    void process(Writer writer, Format format, EntityRef entityRef) throws IOException;

    void process(Writer writer, Format format, ProcessingInstruction processingInstruction) throws IOException;

    void process(Writer writer, Format format, Text text) throws IOException;
}
