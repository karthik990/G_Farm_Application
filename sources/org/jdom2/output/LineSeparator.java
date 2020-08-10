package org.jdom2.output;

import com.mobiroller.constants.Constants;
import org.jdom2.JDOMConstants;
import org.jdom2.internal.SystemProperty;

public enum LineSeparator {
    CRNL(r2),
    NL(r3),
    CR("\r"),
    DOS(r2),
    UNIX(r3),
    SYSTEM(SystemProperty.get("line.separator", r2)),
    NONE(null),
    DEFAULT(getDefaultLineSeparator());
    
    private final String value;

    private static String getDefaultLineSeparator() {
        String str = "DEFAULT";
        String str2 = SystemProperty.get(JDOMConstants.JDOM2_PROPERTY_LINE_SEPARATOR, str);
        String str3 = "\r\n";
        if (str.equals(str2)) {
            return str3;
        }
        if ("SYSTEM".equals(str2)) {
            return System.getProperty("line.separator");
        }
        if ("CRNL".equals(str2)) {
            return str3;
        }
        boolean equals = "NL".equals(str2);
        String str4 = Constants.NEW_LINE;
        if (equals) {
            return str4;
        }
        if ("CR".equals(str2)) {
            return "\r";
        }
        if ("DOS".equals(str2)) {
            return str3;
        }
        if ("UNIX".equals(str2)) {
            return str4;
        }
        if ("NONE".equals(str2)) {
            return null;
        }
        return str2;
    }

    private LineSeparator(String str) {
        this.value = str;
    }

    public String value() {
        return this.value;
    }
}
