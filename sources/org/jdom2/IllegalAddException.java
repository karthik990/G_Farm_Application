package org.jdom2;

public class IllegalAddException extends IllegalArgumentException {
    private static final long serialVersionUID = 200;

    IllegalAddException(Element element, Attribute attribute, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The attribute \"");
        sb.append(attribute.getQualifiedName());
        sb.append("\" could not be added to the element \"");
        sb.append(element.getQualifiedName());
        sb.append("\": ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Element element, Element element2, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The element \"");
        sb.append(element2.getQualifiedName());
        sb.append("\" could not be added as a child of \"");
        sb.append(element.getQualifiedName());
        sb.append("\": ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Element element, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The element \"");
        sb.append(element.getQualifiedName());
        sb.append("\" could not be added as the root of the document: ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Element element, ProcessingInstruction processingInstruction, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The PI \"");
        sb.append(processingInstruction.getTarget());
        sb.append("\" could not be added as content to \"");
        sb.append(element.getQualifiedName());
        sb.append("\": ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(ProcessingInstruction processingInstruction, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The PI \"");
        sb.append(processingInstruction.getTarget());
        sb.append("\" could not be added to the top level of the document: ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Element element, Comment comment, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The comment \"");
        sb.append(comment.getText());
        sb.append("\" could not be added as content to \"");
        sb.append(element.getQualifiedName());
        sb.append("\": ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Element element, CDATA cdata, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The CDATA \"");
        sb.append(cdata.getText());
        sb.append("\" could not be added as content to \"");
        sb.append(element.getQualifiedName());
        sb.append("\": ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Element element, Text text, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The Text \"");
        sb.append(text.getText());
        sb.append("\" could not be added as content to \"");
        sb.append(element.getQualifiedName());
        sb.append("\": ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Comment comment, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The comment \"");
        sb.append(comment.getText());
        sb.append("\" could not be added to the top level of the document: ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Element element, EntityRef entityRef, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The entity reference\"");
        sb.append(entityRef.getName());
        sb.append("\" could not be added as content to \"");
        sb.append(element.getQualifiedName());
        sb.append("\": ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(Element element, Namespace namespace, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The namespace xmlns");
        String str2 = "=";
        if (!namespace.getPrefix().equals("")) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(":");
            sb2.append(namespace.getPrefix());
            sb2.append(str2);
            str2 = sb2.toString();
        }
        sb.append(str2);
        sb.append("\"");
        sb.append(namespace.getURI());
        sb.append("\" could not be added as a namespace to \"");
        sb.append(element.getQualifiedName());
        sb.append("\": ");
        sb.append(str);
        super(sb.toString());
    }

    IllegalAddException(DocType docType, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("The DOCTYPE ");
        sb.append(docType.toString());
        sb.append(" could not be added to the document: ");
        sb.append(str);
        super(sb.toString());
    }

    public IllegalAddException(String str) {
        super(str);
    }
}
