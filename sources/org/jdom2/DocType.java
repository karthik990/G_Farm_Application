package org.jdom2;

import org.jdom2.Content.CType;
import org.jdom2.output.XMLOutputter;

public class DocType extends Content {
    private static final long serialVersionUID = 200;
    protected String elementName;
    protected String internalSubset;
    protected String publicID;
    protected String systemID;

    public String getValue() {
        return "";
    }

    protected DocType() {
        super(CType.DocType);
    }

    public DocType(String str, String str2, String str3) {
        super(CType.DocType);
        setElementName(str);
        setPublicID(str2);
        setSystemID(str3);
    }

    public DocType(String str, String str2) {
        this(str, null, str2);
    }

    public DocType(String str) {
        this(str, null, null);
    }

    public String getElementName() {
        return this.elementName;
    }

    public DocType setElementName(String str) {
        String checkXMLName = Verifier.checkXMLName(str);
        if (checkXMLName == null) {
            this.elementName = str;
            return this;
        }
        throw new IllegalNameException(str, "DocType", checkXMLName);
    }

    public String getPublicID() {
        return this.publicID;
    }

    public DocType setPublicID(String str) {
        String checkPublicID = Verifier.checkPublicID(str);
        if (checkPublicID == null) {
            this.publicID = str;
            return this;
        }
        throw new IllegalDataException(str, "DocType", checkPublicID);
    }

    public String getSystemID() {
        return this.systemID;
    }

    public DocType setSystemID(String str) {
        String checkSystemLiteral = Verifier.checkSystemLiteral(str);
        if (checkSystemLiteral == null) {
            this.systemID = str;
            return this;
        }
        throw new IllegalDataException(str, "DocType", checkSystemLiteral);
    }

    public void setInternalSubset(String str) {
        this.internalSubset = str;
    }

    public String getInternalSubset() {
        return this.internalSubset;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[DocType: ");
        sb.append(new XMLOutputter().outputString(this));
        sb.append("]");
        return sb.toString();
    }

    public DocType clone() {
        return (DocType) super.clone();
    }

    public DocType detach() {
        return (DocType) super.detach();
    }

    /* access modifiers changed from: protected */
    public DocType setParent(Parent parent) {
        return (DocType) super.setParent(parent);
    }

    public Document getParent() {
        return (Document) super.getParent();
    }
}
