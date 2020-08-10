package org.jdom2;

import org.jdom2.Content.CType;

public class CDATA extends Text {
    private static final long serialVersionUID = 200;

    protected CDATA() {
        super(CType.CDATA);
    }

    public CDATA(String str) {
        super(CType.CDATA);
        setText(str);
    }

    public CDATA setText(String str) {
        String str2 = "";
        if (str == null || str2.equals(str)) {
            this.value = str2;
            return this;
        }
        String checkCDATASection = Verifier.checkCDATASection(str);
        if (checkCDATASection == null) {
            this.value = str;
            return this;
        }
        throw new IllegalDataException(str, "CDATA section", checkCDATASection);
    }

    public void append(String str) {
        String str2;
        if (str != null) {
            String str3 = "";
            if (!str3.equals(str)) {
                if (this.value == str3) {
                    str2 = str;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.value);
                    sb.append(str);
                    str2 = sb.toString();
                }
                String checkCDATASection = Verifier.checkCDATASection(str2);
                if (checkCDATASection == null) {
                    this.value = str2;
                    return;
                }
                throw new IllegalDataException(str, "CDATA section", checkCDATASection);
            }
        }
    }

    public void append(Text text) {
        if (text != null) {
            append(text.getText());
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append("[CDATA: ");
        sb.append(getText());
        sb.append("]");
        return sb.toString();
    }

    public CDATA clone() {
        return (CDATA) super.clone();
    }

    public CDATA detach() {
        return (CDATA) super.detach();
    }

    /* access modifiers changed from: protected */
    public CDATA setParent(Parent parent) {
        return (CDATA) super.setParent(parent);
    }
}
