package org.jdom2.located;

import java.util.Map;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.DefaultJDOMFactory;
import org.jdom2.DocType;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.Namespace;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;

public class LocatedJDOMFactory extends DefaultJDOMFactory {
    public CDATA cdata(int i, int i2, String str) {
        LocatedCDATA locatedCDATA = new LocatedCDATA(str);
        locatedCDATA.setLine(i);
        locatedCDATA.setColumn(i2);
        return locatedCDATA;
    }

    public Text text(int i, int i2, String str) {
        LocatedText locatedText = new LocatedText(str);
        locatedText.setLine(i);
        locatedText.setColumn(i2);
        return locatedText;
    }

    public Comment comment(int i, int i2, String str) {
        LocatedComment locatedComment = new LocatedComment(str);
        locatedComment.setLine(i);
        locatedComment.setColumn(i2);
        return locatedComment;
    }

    public DocType docType(int i, int i2, String str, String str2, String str3) {
        LocatedDocType locatedDocType = new LocatedDocType(str, str2, str3);
        locatedDocType.setLine(i);
        locatedDocType.setColumn(i2);
        return locatedDocType;
    }

    public DocType docType(int i, int i2, String str, String str2) {
        LocatedDocType locatedDocType = new LocatedDocType(str, str2);
        locatedDocType.setLine(i);
        locatedDocType.setColumn(i2);
        return locatedDocType;
    }

    public DocType docType(int i, int i2, String str) {
        LocatedDocType locatedDocType = new LocatedDocType(str);
        locatedDocType.setLine(i);
        locatedDocType.setColumn(i2);
        return locatedDocType;
    }

    public Element element(int i, int i2, String str, Namespace namespace) {
        LocatedElement locatedElement = new LocatedElement(str, namespace);
        locatedElement.setLine(i);
        locatedElement.setColumn(i2);
        return locatedElement;
    }

    public Element element(int i, int i2, String str) {
        LocatedElement locatedElement = new LocatedElement(str);
        locatedElement.setLine(i);
        locatedElement.setColumn(i2);
        return locatedElement;
    }

    public Element element(int i, int i2, String str, String str2) {
        LocatedElement locatedElement = new LocatedElement(str, str2);
        locatedElement.setLine(i);
        locatedElement.setColumn(i2);
        return locatedElement;
    }

    public Element element(int i, int i2, String str, String str2, String str3) {
        LocatedElement locatedElement = new LocatedElement(str, str2, str3);
        locatedElement.setLine(i);
        locatedElement.setColumn(i2);
        return locatedElement;
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str) {
        LocatedProcessingInstruction locatedProcessingInstruction = new LocatedProcessingInstruction(str);
        locatedProcessingInstruction.setLine(i);
        locatedProcessingInstruction.setColumn(i2);
        return locatedProcessingInstruction;
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str, Map<String, String> map) {
        LocatedProcessingInstruction locatedProcessingInstruction = new LocatedProcessingInstruction(str, map);
        locatedProcessingInstruction.setLine(i);
        locatedProcessingInstruction.setColumn(i2);
        return locatedProcessingInstruction;
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str, String str2) {
        LocatedProcessingInstruction locatedProcessingInstruction = new LocatedProcessingInstruction(str, str2);
        locatedProcessingInstruction.setLine(i);
        locatedProcessingInstruction.setColumn(i2);
        return locatedProcessingInstruction;
    }

    public EntityRef entityRef(int i, int i2, String str) {
        LocatedEntityRef locatedEntityRef = new LocatedEntityRef(str);
        locatedEntityRef.setLine(i);
        locatedEntityRef.setColumn(i2);
        return locatedEntityRef;
    }

    public EntityRef entityRef(int i, int i2, String str, String str2, String str3) {
        LocatedEntityRef locatedEntityRef = new LocatedEntityRef(str, str2, str3);
        locatedEntityRef.setLine(i);
        locatedEntityRef.setColumn(i2);
        return locatedEntityRef;
    }

    public EntityRef entityRef(int i, int i2, String str, String str2) {
        LocatedEntityRef locatedEntityRef = new LocatedEntityRef(str, str2);
        locatedEntityRef.setLine(i);
        locatedEntityRef.setColumn(i2);
        return locatedEntityRef;
    }
}
