package org.jdom2;

import org.apache.http.cookie.ClientCookie;
import org.jdom2.Content.CType;
import org.jdom2.output.XMLOutputter;

public class Comment extends Content {
    private static final long serialVersionUID = 200;
    protected String text;

    protected Comment() {
        super(CType.Comment);
    }

    public Comment(String str) {
        super(CType.Comment);
        setText(str);
    }

    public String getValue() {
        return this.text;
    }

    public String getText() {
        return this.text;
    }

    public Comment setText(String str) {
        String checkCommentData = Verifier.checkCommentData(str);
        if (checkCommentData == null) {
            this.text = str;
            return this;
        }
        throw new IllegalDataException(str, ClientCookie.COMMENT_ATTR, checkCommentData);
    }

    public Comment clone() {
        return (Comment) super.clone();
    }

    public Comment detach() {
        return (Comment) super.detach();
    }

    /* access modifiers changed from: protected */
    public Comment setParent(Parent parent) {
        return (Comment) super.setParent(parent);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Comment: ");
        sb.append(new XMLOutputter().outputString(this));
        sb.append("]");
        return sb.toString();
    }
}
