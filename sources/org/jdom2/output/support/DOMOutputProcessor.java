package org.jdom2.output.support;

import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Content;
import org.jdom2.EntityRef;
import org.jdom2.output.Format;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

public interface DOMOutputProcessor {
    List<Node> process(Document document, Format format, List<? extends Content> list);

    Attr process(Document document, Format format, Attribute attribute);

    CDATASection process(Document document, Format format, CDATA cdata);

    Comment process(Document document, Format format, org.jdom2.Comment comment);

    Document process(Document document, Format format, org.jdom2.Document document2);

    Element process(Document document, Format format, org.jdom2.Element element);

    EntityReference process(Document document, Format format, EntityRef entityRef);

    ProcessingInstruction process(Document document, Format format, org.jdom2.ProcessingInstruction processingInstruction);

    Text process(Document document, Format format, org.jdom2.Text text);
}
