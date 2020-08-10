package org.jdom2;

import org.jdom2.Content.CType;

public class EntityRef extends Content {
    private static final long serialVersionUID = 200;
    protected String name;
    protected String publicID;
    protected String systemID;

    public String getValue() {
        return "";
    }

    protected EntityRef() {
        super(CType.EntityRef);
    }

    public EntityRef(String str) {
        this(str, null, null);
    }

    public EntityRef(String str, String str2) {
        this(str, null, str2);
    }

    public EntityRef(String str, String str2, String str3) {
        super(CType.EntityRef);
        setName(str);
        setPublicID(str2);
        setSystemID(str3);
    }

    public String getName() {
        return this.name;
    }

    public String getPublicID() {
        return this.publicID;
    }

    public String getSystemID() {
        return this.systemID;
    }

    public EntityRef setName(String str) {
        String checkXMLName = Verifier.checkXMLName(str);
        if (checkXMLName == null) {
            this.name = str;
            return this;
        }
        throw new IllegalNameException(str, "EntityRef", checkXMLName);
    }

    public EntityRef setPublicID(String str) {
        String checkPublicID = Verifier.checkPublicID(str);
        if (checkPublicID == null) {
            this.publicID = str;
            return this;
        }
        throw new IllegalDataException(str, "EntityRef", checkPublicID);
    }

    public EntityRef setSystemID(String str) {
        String checkSystemLiteral = Verifier.checkSystemLiteral(str);
        if (checkSystemLiteral == null) {
            this.systemID = str;
            return this;
        }
        throw new IllegalDataException(str, "EntityRef", checkSystemLiteral);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[EntityRef: ");
        sb.append("&");
        sb.append(this.name);
        sb.append(";");
        sb.append("]");
        return sb.toString();
    }

    public EntityRef detach() {
        return (EntityRef) super.detach();
    }

    /* access modifiers changed from: protected */
    public EntityRef setParent(Parent parent) {
        return (EntityRef) super.setParent(parent);
    }

    public Element getParent() {
        return (Element) super.getParent();
    }

    public EntityRef clone() {
        return (EntityRef) super.clone();
    }
}
