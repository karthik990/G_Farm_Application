package org.jdom2;

public class IllegalTargetException extends IllegalArgumentException {
    private static final long serialVersionUID = 200;

    IllegalTargetException(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("The target \"");
        sb.append(str);
        sb.append("\" is not legal for JDOM/XML Processing Instructions: ");
        sb.append(str2);
        sb.append(".");
        super(sb.toString());
    }

    public IllegalTargetException(String str) {
        super(str);
    }
}
