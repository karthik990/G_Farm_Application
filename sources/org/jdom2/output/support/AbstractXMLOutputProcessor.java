package org.jdom2.output.support;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.text.Typography;
import org.apache.http.client.config.CookieSpecs;
import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.Namespace;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.Verifier;
import org.jdom2.output.Format;
import org.jdom2.output.Format.TextMode;
import org.jdom2.util.NamespaceStack;

public abstract class AbstractXMLOutputProcessor extends AbstractOutputProcessor implements XMLOutputProcessor {
    protected static final String CDATAPOST = "]]>";
    protected static final String CDATAPRE = "<![CDATA[";

    public void process(Writer writer, Format format, Document document) throws IOException {
        printDocument(writer, new FormatStack(format), new NamespaceStack(), document);
        writer.flush();
    }

    public void process(Writer writer, Format format, DocType docType) throws IOException {
        printDocType(writer, new FormatStack(format), docType);
        writer.flush();
    }

    public void process(Writer writer, Format format, Element element) throws IOException {
        printElement(writer, new FormatStack(format), new NamespaceStack(), element);
        writer.flush();
    }

    public void process(Writer writer, Format format, List<? extends Content> list) throws IOException {
        FormatStack formatStack = new FormatStack(format);
        printContent(writer, formatStack, new NamespaceStack(), buildWalker(formatStack, list, true));
        writer.flush();
    }

    public void process(Writer writer, Format format, CDATA cdata) throws IOException {
        List singletonList = Collections.singletonList(cdata);
        FormatStack formatStack = new FormatStack(format);
        Walker buildWalker = buildWalker(formatStack, singletonList, true);
        if (buildWalker.hasNext()) {
            printContent(writer, formatStack, new NamespaceStack(), buildWalker);
        }
        writer.flush();
    }

    public void process(Writer writer, Format format, Text text) throws IOException {
        List singletonList = Collections.singletonList(text);
        FormatStack formatStack = new FormatStack(format);
        Walker buildWalker = buildWalker(formatStack, singletonList, true);
        if (buildWalker.hasNext()) {
            printContent(writer, formatStack, new NamespaceStack(), buildWalker);
        }
        writer.flush();
    }

    public void process(Writer writer, Format format, Comment comment) throws IOException {
        printComment(writer, new FormatStack(format), comment);
        writer.flush();
    }

    public void process(Writer writer, Format format, ProcessingInstruction processingInstruction) throws IOException {
        FormatStack formatStack = new FormatStack(format);
        formatStack.setIgnoreTrAXEscapingPIs(true);
        printProcessingInstruction(writer, formatStack, processingInstruction);
        writer.flush();
    }

    public void process(Writer writer, Format format, EntityRef entityRef) throws IOException {
        printEntityRef(writer, new FormatStack(format), entityRef);
        writer.flush();
    }

    /* access modifiers changed from: protected */
    public void write(Writer writer, String str) throws IOException {
        if (str != null) {
            writer.write(str);
        }
    }

    /* access modifiers changed from: protected */
    public void write(Writer writer, char c) throws IOException {
        writer.write(c);
    }

    /* access modifiers changed from: protected */
    public void attributeEscapedEntitiesFilter(Writer writer, FormatStack formatStack, String str) throws IOException {
        if (!formatStack.getEscapeOutput()) {
            write(writer, str);
        } else {
            write(writer, Format.escapeAttribute(formatStack.getEscapeStrategy(), str));
        }
    }

    /* access modifiers changed from: protected */
    public void textRaw(Writer writer, String str) throws IOException {
        write(writer, str);
    }

    /* access modifiers changed from: protected */
    public void textRaw(Writer writer, char c) throws IOException {
        write(writer, c);
    }

    /* access modifiers changed from: protected */
    public void textEntityRef(Writer writer, String str) throws IOException {
        textRaw(writer, (char) Typography.amp);
        textRaw(writer, str);
        textRaw(writer, ';');
    }

    /* access modifiers changed from: protected */
    public void textCDATA(Writer writer, String str) throws IOException {
        textRaw(writer, CDATAPRE);
        textRaw(writer, str);
        textRaw(writer, CDATAPOST);
    }

    /* access modifiers changed from: protected */
    public void printDocument(Writer writer, FormatStack formatStack, NamespaceStack namespaceStack, Document document) throws IOException {
        List content = document.hasRootElement() ? document.getContent() : new ArrayList(document.getContentSize());
        if (content.isEmpty()) {
            int contentSize = document.getContentSize();
            for (int i = 0; i < contentSize; i++) {
                content.add(document.getContent(i));
            }
        }
        printDeclaration(writer, formatStack);
        Walker buildWalker = buildWalker(formatStack, content, true);
        if (buildWalker.hasNext()) {
            while (buildWalker.hasNext()) {
                Content next = buildWalker.next();
                if (next == null) {
                    String text = buildWalker.text();
                    if (text != null && Verifier.isAllXMLWhitespace(text) && !buildWalker.isCDATA()) {
                        write(writer, text);
                    }
                } else {
                    int i2 = C61441.$SwitchMap$org$jdom2$Content$CType[next.getCType().ordinal()];
                    if (i2 == 1) {
                        printComment(writer, formatStack, (Comment) next);
                    } else if (i2 == 2) {
                        printDocType(writer, formatStack, (DocType) next);
                    } else if (i2 == 3) {
                        printElement(writer, formatStack, namespaceStack, (Element) next);
                    } else if (i2 == 4) {
                        printProcessingInstruction(writer, formatStack, (ProcessingInstruction) next);
                    } else if (i2 == 5) {
                        String text2 = ((Text) next).getText();
                        if (text2 != null && Verifier.isAllXMLWhitespace(text2)) {
                            write(writer, text2);
                        }
                    }
                }
            }
            if (formatStack.getLineSeparator() != null) {
                write(writer, formatStack.getLineSeparator());
            }
        }
    }

    /* access modifiers changed from: protected */
    public void printDeclaration(Writer writer, FormatStack formatStack) throws IOException {
        if (!formatStack.isOmitDeclaration()) {
            if (formatStack.isOmitEncoding()) {
                write(writer, "<?xml version=\"1.0\"?>");
            } else {
                write(writer, "<?xml version=\"1.0\"");
                write(writer, " encoding=\"");
                write(writer, formatStack.getEncoding());
                write(writer, "\"?>");
            }
            write(writer, formatStack.getLineSeparator());
        }
    }

    /* access modifiers changed from: protected */
    public void printDocType(Writer writer, FormatStack formatStack, DocType docType) throws IOException {
        boolean z;
        String publicID = docType.getPublicID();
        String systemID = docType.getSystemID();
        String internalSubset = docType.getInternalSubset();
        write(writer, "<!DOCTYPE ");
        write(writer, docType.getElementName());
        String str = "\"";
        if (publicID != null) {
            write(writer, " PUBLIC \"");
            write(writer, publicID);
            write(writer, str);
            z = true;
        } else {
            z = false;
        }
        if (systemID != null) {
            if (!z) {
                write(writer, " SYSTEM");
            }
            write(writer, " \"");
            write(writer, systemID);
            write(writer, str);
        }
        if (internalSubset != null && !internalSubset.equals("")) {
            write(writer, " [");
            write(writer, formatStack.getLineSeparator());
            write(writer, docType.getInternalSubset());
            write(writer, "]");
        }
        write(writer, ">");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0027  */
    /* JADX WARNING: Removed duplicated region for block: B:15:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void printProcessingInstruction(java.io.Writer r5, org.jdom2.output.support.FormatStack r6, org.jdom2.ProcessingInstruction r7) throws java.io.IOException {
        /*
            r4 = this;
            java.lang.String r0 = r7.getTarget()
            boolean r1 = r6.isIgnoreTrAXEscapingPIs()
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x0024
            java.lang.String r1 = "javax.xml.transform.disable-output-escaping"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0018
            r6.setEscapeOutput(r2)
            goto L_0x0025
        L_0x0018:
            java.lang.String r1 = "javax.xml.transform.enable-output-escaping"
            boolean r1 = r0.equals(r1)
            if (r1 == 0) goto L_0x0024
            r6.setEscapeOutput(r3)
            goto L_0x0025
        L_0x0024:
            r3 = 0
        L_0x0025:
            if (r3 != 0) goto L_0x0052
            java.lang.String r6 = r7.getData()
            java.lang.String r7 = ""
            boolean r7 = r7.equals(r6)
            java.lang.String r1 = "?>"
            java.lang.String r2 = "<?"
            if (r7 != 0) goto L_0x0049
            r4.write(r5, r2)
            r4.write(r5, r0)
            java.lang.String r7 = " "
            r4.write(r5, r7)
            r4.write(r5, r6)
            r4.write(r5, r1)
            goto L_0x0052
        L_0x0049:
            r4.write(r5, r2)
            r4.write(r5, r0)
            r4.write(r5, r1)
        L_0x0052:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jdom2.output.support.AbstractXMLOutputProcessor.printProcessingInstruction(java.io.Writer, org.jdom2.output.support.FormatStack, org.jdom2.ProcessingInstruction):void");
    }

    /* access modifiers changed from: protected */
    public void printComment(Writer writer, FormatStack formatStack, Comment comment) throws IOException {
        write(writer, "<!--");
        write(writer, comment.getText());
        write(writer, "-->");
    }

    /* access modifiers changed from: protected */
    public void printEntityRef(Writer writer, FormatStack formatStack, EntityRef entityRef) throws IOException {
        textEntityRef(writer, entityRef.getName());
    }

    /* access modifiers changed from: protected */
    public void printCDATA(Writer writer, FormatStack formatStack, CDATA cdata) throws IOException {
        textCDATA(writer, cdata.getText());
    }

    /* access modifiers changed from: protected */
    public void printText(Writer writer, FormatStack formatStack, Text text) throws IOException {
        if (formatStack.getEscapeOutput()) {
            textRaw(writer, Format.escapeText(formatStack.getEscapeStrategy(), formatStack.getLineSeparator(), text.getText()));
        } else {
            textRaw(writer, text.getText());
        }
    }

    /* access modifiers changed from: protected */
    public void printElement(Writer writer, FormatStack formatStack, NamespaceStack namespaceStack, Element element) throws IOException {
        namespaceStack.push(element);
        try {
            List content = element.getContent();
            write(writer, "<");
            write(writer, element.getQualifiedName());
            for (Namespace printNamespace : namespaceStack.addedForward()) {
                printNamespace(writer, formatStack, printNamespace);
            }
            if (element.hasAttributes()) {
                for (Attribute printAttribute : element.getAttributes()) {
                    printAttribute(writer, formatStack, printAttribute);
                }
            }
            String str = "></";
            String str2 = " />";
            String str3 = ">";
            if (content.isEmpty()) {
                if (formatStack.isExpandEmptyElements()) {
                    write(writer, str);
                    write(writer, element.getQualifiedName());
                    write(writer, str3);
                } else {
                    write(writer, str2);
                }
                namespaceStack.pop();
                return;
            }
            formatStack.push();
            String attributeValue = element.getAttributeValue("space", Namespace.XML_NAMESPACE);
            if (CookieSpecs.DEFAULT.equals(attributeValue)) {
                formatStack.setTextMode(formatStack.getDefaultMode());
            } else if ("preserve".equals(attributeValue)) {
                formatStack.setTextMode(TextMode.PRESERVE);
            }
            Walker buildWalker = buildWalker(formatStack, content, true);
            if (!buildWalker.hasNext()) {
                if (formatStack.isExpandEmptyElements()) {
                    write(writer, str);
                    write(writer, element.getQualifiedName());
                    write(writer, str3);
                } else {
                    write(writer, str2);
                }
                formatStack.pop();
                namespaceStack.pop();
                return;
            }
            write(writer, str3);
            if (!buildWalker.isAllText()) {
                textRaw(writer, formatStack.getPadBetween());
            }
            printContent(writer, formatStack, namespaceStack, buildWalker);
            if (!buildWalker.isAllText()) {
                textRaw(writer, formatStack.getPadLast());
            }
            write(writer, "</");
            write(writer, element.getQualifiedName());
            write(writer, str3);
            formatStack.pop();
            namespaceStack.pop();
        } catch (Throwable th) {
            namespaceStack.pop();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void printContent(Writer writer, FormatStack formatStack, NamespaceStack namespaceStack, Walker walker) throws IOException {
        while (walker.hasNext()) {
            Content next = walker.next();
            if (next != null) {
                switch (next.getCType()) {
                    case Comment:
                        printComment(writer, formatStack, (Comment) next);
                        break;
                    case DocType:
                        printDocType(writer, formatStack, (DocType) next);
                        break;
                    case Element:
                        printElement(writer, formatStack, namespaceStack, (Element) next);
                        break;
                    case ProcessingInstruction:
                        printProcessingInstruction(writer, formatStack, (ProcessingInstruction) next);
                        break;
                    case Text:
                        printText(writer, formatStack, (Text) next);
                        break;
                    case CDATA:
                        printCDATA(writer, formatStack, (CDATA) next);
                        break;
                    case EntityRef:
                        printEntityRef(writer, formatStack, (EntityRef) next);
                        break;
                }
            } else {
                String text = walker.text();
                if (walker.isCDATA()) {
                    textCDATA(writer, text);
                } else {
                    textRaw(writer, text);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void printNamespace(Writer writer, FormatStack formatStack, Namespace namespace) throws IOException {
        String prefix = namespace.getPrefix();
        String uri = namespace.getURI();
        write(writer, " xmlns");
        if (!prefix.equals("")) {
            write(writer, ":");
            write(writer, prefix);
        }
        write(writer, "=\"");
        attributeEscapedEntitiesFilter(writer, formatStack, uri);
        write(writer, "\"");
    }

    /* access modifiers changed from: protected */
    public void printAttribute(Writer writer, FormatStack formatStack, Attribute attribute) throws IOException {
        if (attribute.isSpecified() || !formatStack.isSpecifiedAttributesOnly()) {
            write(writer, " ");
            write(writer, attribute.getQualifiedName());
            write(writer, "=");
            String str = "\"";
            write(writer, str);
            attributeEscapedEntitiesFilter(writer, formatStack, attribute.getValue());
            write(writer, str);
        }
    }
}
