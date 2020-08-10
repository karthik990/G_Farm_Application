package org.jdom2;

public class IllegalDataException extends IllegalArgumentException {
    private static final long serialVersionUID = 200;

    IllegalDataException(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("The data \"");
        sb.append(str);
        sb.append("\" is not legal for a JDOM ");
        sb.append(str2);
        sb.append(": ");
        sb.append(str3);
        sb.append(".");
        super(sb.toString());
    }

    IllegalDataException(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("The data \"");
        sb.append(str);
        sb.append("\" is not legal for a JDOM ");
        sb.append(str2);
        sb.append(".");
        super(sb.toString());
    }

    public IllegalDataException(String str) {
        super(str);
    }
}
