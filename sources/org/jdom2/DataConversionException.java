package org.jdom2;

public class DataConversionException extends JDOMException {
    private static final long serialVersionUID = 200;

    public DataConversionException(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("The XML construct ");
        sb.append(str);
        sb.append(" could not be converted to a ");
        sb.append(str2);
        super(sb.toString());
    }
}
