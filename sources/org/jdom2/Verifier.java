package org.jdom2;

import com.fasterxml.jackson.core.base.GeneratorBase;
import com.google.common.base.Ascii;
import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.Opcodes;
import org.objenesis.instantiator.basic.ClassDefinitionUtils;

public final class Verifier {
    private static final int CHARCNT = 65536;
    private static final byte[] CHARFLAGS = buildBitFlags();
    private static final int[] LENCONST = {9, 2, 2, 1, 18, 1, 1, 2, 9, 2, 1, 10, 1, 2, 1, 1, 2, 26, 4, 1, 1, 26, 3, 1, 56, 1, 8, 23, 1, 31, 1, 58, 2, 11, 2, 8, 1, 53, 1, 68, 9, 36, 3, 2, 4, 30, 56, 89, 18, 7, 14, 2, 46, 70, 26, 2, 36, 1, 1, 3, 1, 1, 1, 20, 1, 44, 1, 7, 3, 1, 1, 1, 1, 1, 1, 1, 1, 18, 13, 12, 1, 66, 1, 12, 1, 36, 1, 4, 9, 53, 2, 2, 2, 2, 3, 28, 2, 8, 2, 2, 55, 38, 2, 1, 7, 38, 10, 17, 1, 23, 1, 3, 1, 1, 1, 2, 1, 1, 11, 27, 5, 3, 46, 26, 5, 1, 10, 8, 13, 10, 6, 1, 71, 2, 5, 1, 15, 1, 4, 1, 1, 15, 2, 2, 1, 4, 2, 10, 519, 3, 1, 53, 2, 1, 1, 16, 3, 4, 3, 10, 2, 2, 10, 17, 3, 1, 8, 2, 2, 2, 22, 1, 7, 1, 1, 3, 4, 2, 1, 1, 7, 2, 2, 2, 3, 9, 1, 4, 2, 1, 3, 2, 2, 10, 2, 16, 1, 2, 6, 4, 2, 2, 22, 1, 7, 1, 2, 1, 2, 1, 2, 2, 1, 1, 5, 4, 2, 2, 3, 11, 4, 1, 1, 7, 10, 2, 3, 12, 3, 1, 7, 1, 1, 1, 3, 1, 22, 1, 7, 1, 2, 1, 5, 2, 1, 1, 8, 1, 3, 1, 3, 18, 1, 5, 10, 17, 3, 1, 8, 2, 2, 2, 22, 1, 7, 1, 2, 2, 4, 2, 1, 1, 6, 3, 2, 2, 3, 8, 2, 4, 2, 1, 3, 4, 10, 18, 2, 1, 6, 3, 3, 1, 4, 3, 2, 1, 1, 1, 2, 3, 2, 3, 3, 3, 8, 1, 3, 4, 5, 3, 3, 1, 4, 9, 1, 15, 9, 17, 3, 1, 8, 1, 3, 1, 23, 1, 10, 1, 5, 4, 7, 1, 3, 1, 4, 7, 2, 9, 2, 4, 10, 18, 2, 1, 8, 1, 3, 1, 23, 1, 10, 1, 5, 4, 7, 1, 3, 1, 4, 7, 2, 7, 1, 1, 2, 4, 10, 18, 2, 1, 8, 1, 3, 1, 23, 1, 16, 4, 6, 2, 3, 1, 4, 9, 1, 8, 2, 4, 10, Opcodes.I2B, 46, 1, 1, 1, 2, 7, 5, 6, 1, 8, 1, 10, 39, 2, 1, 1, 2, 2, 1, 1, 2, 1, 6, 4, 1, 7, 1, 3, 1, 1, 1, 1, 2, 2, 1, 2, 1, 1, 1, 2, 6, 1, 2, 1, 2, 5, 1, 1, 1, 6, 2, 10, 62, 2, 6, 10, 11, 1, 1, 1, 1, 1, 4, 2, 8, 1, 33, 7, 20, 1, 6, 4, 6, 1, 1, 1, 21, 3, 7, 1, 1, 230, 38, 10, 39, 9, 1, 1, 2, 1, 3, 1, 1, 1, 2, 1, 5, 41, 1, 1, 1, 1, 1, 11, 1, 1, 1, 1, 1, 3, 2, 3, 1, 5, 3, 1, 1, 1, 1, 1, 1, 1, 1, 3, 2, 3, 2, 1, 1, 40, 1, 9, 1, 2, 1, 2, 2, 7, 2, 1, 1, 1, 7, 40, 1, 4, 1, 8, 1, 3078, Opcodes.IFGE, 4, 90, 6, 22, 2, 6, 2, 38, 2, 6, 2, 8, 1, 1, 1, 1, 1, 1, 1, 31, 2, 53, 1, 7, 1, 1, 3, 3, 1, 7, 3, 4, 2, 6, 4, 13, 5, 3, 1, 7, 211, 13, 4, 1, 68, 1, 3, 2, 2, 1, 81, 3, 3714, 1, 1, 1, 25, 9, 6, 1, 5, 11, 84, 4, 2, 2, 2, 2, 90, 1, 3, 6, 40, 7379, 20902, 3162, 11172, 92, 2048, 8190, 2};
    private static final byte MASKURICHAR = 64;
    private static final byte MASKXMLCHARACTER = 1;
    private static final byte MASKXMLCOMBINING = 32;
    private static final byte MASKXMLDIGIT = 16;
    private static final byte MASKXMLLETTER = 2;
    private static final byte MASKXMLLETTERORDIGIT = 18;
    private static final byte MASKXMLNAMECHAR = 8;
    private static final byte MASKXMLSTARTCHAR = 4;
    private static final byte[] VALCONST = {0, 1, 0, 1, 0, 1, 65, 1, 65, 73, 65, ClassDefinitionUtils.OPS_dup, 65, 1, 65, 1, 65, 79, 1, 77, 1, 79, 1, 65, 1, 9, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 9, 1, 41, 1, 41, 1, Ascii.f1875SI, 9, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 9, Ascii.f1875SI, 41, 1, Ascii.f1867EM, 1, 41, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 41, Ascii.f1875SI, 41, 1, 41, 1, Ascii.f1867EM, 1, 41, 1, Ascii.f1875SI, 1, 41, Ascii.f1875SI, 41, 1, 41, 1, Ascii.f1875SI, 41, 1, Ascii.f1867EM, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 41, 1, Ascii.f1867EM, Ascii.f1875SI, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1867EM, 41, Ascii.f1875SI, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, Ascii.f1875SI, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1867EM, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, Ascii.f1875SI, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1867EM, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1867EM, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1867EM, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1867EM, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1867EM, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 41, Ascii.f1875SI, 41, 1, Ascii.f1875SI, 9, 41, 1, Ascii.f1867EM, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 41, Ascii.f1875SI, 41, 1, 41, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 9, 1, 41, 1, Ascii.f1867EM, 1, 41, 1, Ascii.f1867EM, 1, 41, 1, 41, 1, 41, 1, 41, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 41, 1, 41, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 9, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 41, 1, 9, 1, Ascii.f1875SI, 1, 41, 1, 9, 1, Ascii.f1875SI, 1, 9, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, Ascii.f1875SI, 1, 0, 1, 0};

    public static int decodeSurrogatePair(char c, char c2) {
        return ((c - GeneratorBase.SURR1_FIRST) * 1024) + 65536 + (c2 - GeneratorBase.SURR2_FIRST);
    }

    public static boolean isHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        if (c < 'A' || c > 'F') {
            return c >= 'a' && c <= 'f';
        }
        return true;
    }

    public static boolean isHighSurrogate(char c) {
        return 54 == (c >>> 10);
    }

    public static boolean isLowSurrogate(char c) {
        return 55 == (c >>> 10);
    }

    public static boolean isXMLExtender(char c) {
        if (c < 182) {
            return false;
        }
        if (c == 183 || c == 720 || c == 721 || c == 903 || c == 1600 || c == 3654 || c == 3782 || c == 12293) {
            return true;
        }
        if (c < 12337) {
            return false;
        }
        if (c <= 12341) {
            return true;
        }
        if (c < 12445) {
            return false;
        }
        if (c <= 12446) {
            return true;
        }
        return c >= 12540 && c <= 12542;
    }

    public static boolean isXMLPublicIDCharacter(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if (c < '?' || c > 'Z') {
            return (c >= '\'' && c <= ';') || c == ' ' || c == '!' || c == '=' || c == '#' || c == '$' || c == '_' || c == '%' || c == 10 || c == 13 || c == 9;
        }
        return true;
    }

    public static boolean isXMLWhitespace(char c) {
        return c == ' ' || c == 10 || c == 9 || c == 13;
    }

    private static final byte[] buildBitFlags() {
        byte[] bArr = new byte[65536];
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr2 = VALCONST;
            if (i >= bArr2.length) {
                return bArr;
            }
            byte b = bArr2[i];
            int i3 = LENCONST[i];
            while (true) {
                i3--;
                if (i3 < 0) {
                    break;
                }
                int i4 = i2 + 1;
                bArr[i2] = b;
                i2 = i4;
            }
            i++;
        }
    }

    private Verifier() {
    }

    private static final String checkJDOMName(String str) {
        if (str == null) {
            return "XML names cannot be null";
        }
        if (str.length() == 0) {
            return "XML names cannot be empty";
        }
        String str2 = "\"";
        String str3 = "XML name '";
        if ((CHARFLAGS[str.charAt(0)] & 4) == 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str3);
            sb.append(str);
            sb.append("' cannot begin with the character \"");
            sb.append(str.charAt(0));
            sb.append(str2);
            return sb.toString();
        }
        for (int length = str.length() - 1; length >= 1; length--) {
            if (((byte) (CHARFLAGS[str.charAt(length)] & 8)) == 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str3);
                sb2.append(str);
                sb2.append("' cannot contain the character \"");
                sb2.append(str.charAt(length));
                sb2.append(str2);
                return sb2.toString();
            }
        }
        return null;
    }

    public static String checkElementName(String str) {
        return checkJDOMName(str);
    }

    public static String checkAttributeName(String str) {
        if (JDOMConstants.NS_PREFIX_XMLNS.equals(str)) {
            return "An Attribute name may not be \"xmlns\"; use the Namespace class to manage namespaces";
        }
        return checkJDOMName(str);
    }

    public static String checkCharacterData(String str) {
        if (str == null) {
            return "A null is not a legal XML value";
        }
        int length = str.length();
        int i = 0;
        while (i < length) {
            while (CHARFLAGS[str.charAt(i)] != 0) {
                i++;
                if (i == length) {
                    return null;
                }
            }
            if (isHighSurrogate(str.charAt(i))) {
                int i2 = i + 1;
                if (i2 >= length) {
                    return String.format("Truncated Surrogate Pair 0x%04x????", new Object[]{Integer.valueOf(str.charAt(i2 - 1))});
                } else if (isLowSurrogate(str.charAt(i2))) {
                    int i3 = i2 - 1;
                    if (!isXMLCharacter(decodeSurrogatePair(str.charAt(i3), str.charAt(i2)))) {
                        return String.format("0x%06x is not a legal XML character", new Object[]{Integer.valueOf(decodeSurrogatePair(str.charAt(i3), str.charAt(i2)))});
                    }
                    i = i2 + 1;
                } else {
                    return String.format("Illegal Surrogate Pair 0x%04x%04x", new Object[]{Integer.valueOf(str.charAt(i2 - 1)), Integer.valueOf(str.charAt(i2))});
                }
            } else {
                return String.format("0x%04x is not a legal XML character", new Object[]{Integer.valueOf(str.charAt(i))});
            }
        }
        return null;
    }

    public static String checkCDATASection(String str) {
        String checkCharacterData = checkCharacterData(str);
        if (checkCharacterData != null) {
            return checkCharacterData;
        }
        if (str.indexOf("]]>") != -1) {
            return "CDATA cannot internally contain a CDATA ending delimiter (]]>)";
        }
        return null;
    }

    public static String checkNamespacePrefix(String str) {
        if (str == null || str.equals("") || checkJDOMName(str) == null) {
            return null;
        }
        return checkJDOMName(str);
    }

    public static String checkNamespaceURI(String str) {
        if (str != null && !str.equals("")) {
            char charAt = str.charAt(0);
            if (Character.isDigit(charAt)) {
                return "Namespace URIs cannot begin with a number";
            }
            if (charAt == '$') {
                return "Namespace URIs cannot begin with a dollar sign ($)";
            }
            if (charAt == '-') {
                return "Namespace URIs cannot begin with a hyphen (-)";
            }
            if (isXMLWhitespace(charAt)) {
                return "Namespace URIs cannot begin with white-space";
            }
        }
        return null;
    }

    public static String checkNamespaceCollision(Namespace namespace, Namespace namespace2) {
        String prefix = namespace.getPrefix();
        String uri = namespace.getURI();
        String prefix2 = namespace2.getPrefix();
        String uri2 = namespace2.getURI();
        if (!prefix.equals(prefix2) || uri.equals(uri2)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("The namespace prefix \"");
        sb.append(prefix);
        sb.append("\" collides");
        return sb.toString();
    }

    public static String checkNamespaceCollision(Attribute attribute, Element element) {
        return checkNamespaceCollision(attribute, element, -1);
    }

    public static String checkNamespaceCollision(Attribute attribute, Element element, int i) {
        Namespace namespace = attribute.getNamespace();
        if ("".equals(namespace.getPrefix())) {
            return null;
        }
        return checkNamespaceCollision(namespace, element, i);
    }

    public static String checkNamespaceCollision(Namespace namespace, Element element) {
        return checkNamespaceCollision(namespace, element, -1);
    }

    public static String checkNamespaceCollision(Namespace namespace, Element element, int i) {
        String checkNamespaceCollision = checkNamespaceCollision(namespace, element.getNamespace());
        if (checkNamespaceCollision != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(checkNamespaceCollision);
            sb.append(" with the element namespace prefix");
            return sb.toString();
        }
        if (element.hasAdditionalNamespaces()) {
            String checkNamespaceCollision2 = checkNamespaceCollision(namespace, element.getAdditionalNamespaces());
            if (checkNamespaceCollision2 != null) {
                return checkNamespaceCollision2;
            }
        }
        if (element.hasAttributes()) {
            String checkNamespaceCollision3 = checkNamespaceCollision(namespace, element.getAttributes(), i);
            if (checkNamespaceCollision3 != null) {
                return checkNamespaceCollision3;
            }
        }
        return null;
    }

    public static String checkNamespaceCollision(Namespace namespace, Attribute attribute) {
        if (attribute.getNamespace().equals(Namespace.NO_NAMESPACE)) {
            return null;
        }
        String checkNamespaceCollision = checkNamespaceCollision(namespace, attribute.getNamespace());
        if (checkNamespaceCollision == null) {
            return checkNamespaceCollision;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(checkNamespaceCollision);
        sb.append(" with an attribute namespace prefix on the element");
        return sb.toString();
    }

    public static String checkNamespaceCollision(Namespace namespace, List<?> list) {
        return checkNamespaceCollision(namespace, list, -1);
    }

    public static String checkNamespaceCollision(Namespace namespace, List<?> list, int i) {
        String str = null;
        if (list == null) {
            return null;
        }
        Iterator it = list.iterator();
        int i2 = -1;
        while (str == null && it.hasNext()) {
            Object next = it.next();
            i2++;
            if (next instanceof Attribute) {
                if (i2 != i) {
                    str = checkNamespaceCollision(namespace, (Attribute) next);
                }
            } else if (next instanceof Element) {
                str = checkNamespaceCollision(namespace, (Element) next);
            } else if (next instanceof Namespace) {
                str = checkNamespaceCollision(namespace, (Namespace) next);
                if (str != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append(" with an additional namespace declared by the element");
                    str = sb.toString();
                }
            }
        }
        return str;
    }

    public static String checkProcessingInstructionTarget(String str) {
        String checkXMLName = checkXMLName(str);
        if (checkXMLName != null) {
            return checkXMLName;
        }
        if (str.indexOf(":") != -1) {
            return "Processing instruction targets cannot contain colons";
        }
        if (str.equalsIgnoreCase("xml")) {
            return "Processing instructions cannot have a target of \"xml\" in any combination of case. (Note that the \"<?xml ... ?>\" declaration at the beginning of a document is not a processing instruction and should not be added as one; it is written automatically during output, e.g. by XMLOutputter.)";
        }
        return null;
    }

    public static String checkProcessingInstructionData(String str) {
        String checkCharacterData = checkCharacterData(str);
        return (checkCharacterData != null || str.indexOf("?>") < 0) ? checkCharacterData : "Processing instructions cannot contain the string \"?>\"";
    }

    public static String checkCommentData(String str) {
        String checkCharacterData = checkCharacterData(str);
        if (checkCharacterData != null) {
            return checkCharacterData;
        }
        if (str.indexOf("--") != -1) {
            return "Comments cannot contain double hyphens (--)";
        }
        if (str.endsWith("-")) {
            return "Comment data cannot end with a hyphen.";
        }
        return null;
    }

    public static String checkPublicID(String str) {
        String str2 = null;
        if (str == null) {
            return null;
        }
        int i = 0;
        while (true) {
            if (i >= str.length()) {
                break;
            }
            char charAt = str.charAt(i);
            if (!isXMLPublicIDCharacter(charAt)) {
                StringBuilder sb = new StringBuilder();
                sb.append(charAt);
                sb.append(" is not a legal character in public IDs");
                str2 = sb.toString();
                break;
            }
            i++;
        }
        return str2;
    }

    public static String checkSystemLiteral(String str) {
        String str2;
        if (str == null) {
            return null;
        }
        if (str.indexOf(39) == -1 || str.indexOf(34) == -1) {
            str2 = checkCharacterData(str);
        } else {
            str2 = "System literals cannot simultaneously contain both single and double quotes.";
        }
        return str2;
    }

    public static String checkXMLName(String str) {
        if (str == null) {
            return "XML names cannot be null";
        }
        int length = str.length();
        if (length == 0) {
            return "XML names cannot be empty";
        }
        String str2 = "\"";
        if (!isXMLNameStartCharacter(str.charAt(0))) {
            StringBuilder sb = new StringBuilder();
            sb.append("XML names cannot begin with the character \"");
            sb.append(str.charAt(0));
            sb.append(str2);
            return sb.toString();
        }
        for (int i = 1; i < length; i++) {
            if (!isXMLNameCharacter(str.charAt(i))) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("XML names cannot contain the character \"");
                sb2.append(str.charAt(i));
                sb2.append(str2);
                return sb2.toString();
            }
        }
        return null;
    }

    public static String checkURI(String str) {
        String str2 = "Percent signs in URIs must be followed by exactly two hexadecimal digits.";
        if (str != null && !str.equals("")) {
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (!isURICharacter(charAt)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("0x");
                    sb.append(Integer.toHexString(charAt));
                    String sb2 = sb.toString();
                    if (charAt <= 9) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("0x0");
                        sb3.append(Integer.toHexString(charAt));
                        sb2 = sb3.toString();
                    }
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("URIs cannot contain ");
                    sb4.append(sb2);
                    return sb4.toString();
                }
                if (charAt == '%') {
                    try {
                        char charAt2 = str.charAt(i + 1);
                        char charAt3 = str.charAt(i + 2);
                        if (isHexDigit(charAt2) && isHexDigit(charAt3)) {
                        }
                    } catch (StringIndexOutOfBoundsException unused) {
                    }
                    return str2;
                }
            }
        }
        return null;
    }

    public static boolean isURICharacter(char c) {
        return ((byte) (CHARFLAGS[c] & 64)) != 0;
    }

    public static boolean isXMLCharacter(int i) {
        boolean z = false;
        if (i >= 65536) {
            if (i <= 1114111) {
                z = true;
            }
            return z;
        }
        if (((byte) (CHARFLAGS[i] & 1)) != 0) {
            z = true;
        }
        return z;
    }

    public static boolean isXMLNameCharacter(char c) {
        return ((byte) (CHARFLAGS[c] & 8)) != 0 || c == ':';
    }

    public static boolean isXMLNameStartCharacter(char c) {
        return ((byte) (CHARFLAGS[c] & 4)) != 0 || c == ':';
    }

    public static boolean isXMLLetterOrDigit(char c) {
        return ((byte) (CHARFLAGS[c] & 18)) != 0;
    }

    public static boolean isXMLLetter(char c) {
        return ((byte) (CHARFLAGS[c] & 2)) != 0;
    }

    public static boolean isXMLCombiningChar(char c) {
        return ((byte) (CHARFLAGS[c] & 32)) != 0;
    }

    public static boolean isXMLDigit(char c) {
        return ((byte) (CHARFLAGS[c] & 16)) != 0;
    }

    public static final boolean isAllXMLWhitespace(String str) {
        int length = str.length();
        do {
            length--;
            if (length < 0) {
                return true;
            }
        } while (isXMLWhitespace(str.charAt(length)));
        return false;
    }
}
