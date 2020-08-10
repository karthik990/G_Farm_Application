package org.jdom2;

public enum AttributeType {
    UNDECLARED,
    CDATA,
    ID,
    IDREF,
    IDREFS,
    ENTITY,
    ENTITIES,
    NMTOKEN,
    NMTOKENS,
    NOTATION,
    ENUMERATION;

    @Deprecated
    public static final AttributeType byIndex(int i) {
        String str = "No such AttributeType ";
        if (i < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(i);
            throw new IllegalDataException(sb.toString());
        } else if (i < values().length) {
            return values()[i];
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(i);
            sb2.append(", max is ");
            sb2.append(values().length - 1);
            throw new IllegalDataException(sb2.toString());
        }
    }

    public static final AttributeType getAttributeType(String str) {
        if (str == null) {
            return UNDECLARED;
        }
        try {
            return valueOf(str);
        } catch (IllegalArgumentException unused) {
            if (str.length() <= 0 || str.trim().charAt(0) != '(') {
                return UNDECLARED;
            }
            return ENUMERATION;
        }
    }
}
