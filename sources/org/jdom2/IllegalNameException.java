package org.jdom2;

public class IllegalNameException extends IllegalArgumentException {
    private static final long serialVersionUID = 200;

    IllegalNameException(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("The name \"");
        sb.append(str);
        sb.append("\" is not legal for JDOM/XML ");
        sb.append(str2);
        sb.append("s: ");
        sb.append(str3);
        sb.append(".");
        super(sb.toString());
    }

    IllegalNameException(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("The name \"");
        sb.append(str);
        sb.append("\" is not legal for JDOM/XML ");
        sb.append(str2);
        sb.append("s.");
        super(sb.toString());
    }

    public IllegalNameException(String str) {
        super(str);
    }
}
