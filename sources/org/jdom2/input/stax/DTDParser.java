package org.jdom2.input.stax;

import com.braintreepayments.api.models.PostalAddressParser;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.DocType;
import org.jdom2.JDOMException;
import org.jdom2.JDOMFactory;

public class DTDParser {
    private static final String metapattern = " os <!DOCTYPE ms ( name )( ms ((SYSTEM ms  id )|(PUBLIC ms  id ( ms  id )?)))?( os \\[( internal )\\])? os > os ";
    private static final Pattern pattern = buildPattern(populatePatterns(), metapattern);

    private static final boolean isWhite(char c) {
        return c == ' ' || c == 9 || c == 10 || c == 13;
    }

    private static final HashMap<String, String> populatePatterns() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(PostalAddressParser.USER_ADDRESS_NAME_KEY, "[^ \\n\\r\\t\\[>]+");
        hashMap.put("ms", "[ \\n\\r\\t]+");
        hashMap.put("os", "[ \\n\\r\\t]*");
        hashMap.put(TtmlNode.ATTR_ID, "(('([^']*)')|(\"([^\"]*)\"))");
        hashMap.put("internal", ".*");
        return hashMap;
    }

    private static final Pattern buildPattern(HashMap<String, String> hashMap, String str) {
        Matcher matcher = Pattern.compile(" (\\w+) ").matcher(str);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            String str2 = (String) hashMap.get(matcher.group(1));
            sb.append(str.substring(i, matcher.start()));
            sb.append(str2);
            i = matcher.end();
        }
        sb.append(str.substring(i));
        return Pattern.compile(sb.toString(), 32);
    }

    private static final String getGroup(Matcher matcher, int... iArr) {
        for (int group : iArr) {
            String group2 = matcher.group(group);
            if (group2 != null) {
                return group2;
            }
        }
        return null;
    }

    private static String formatInternal(String str) {
        char[] charArray;
        StringBuilder sb = new StringBuilder(str.length());
        char c = ' ';
        boolean z = true;
        for (char c2 : str.toCharArray()) {
            if (c == ' ') {
                if (!isWhite(c2)) {
                    if (c2 == '\'' || c2 == '\"') {
                        c = c2;
                    } else if (c2 == '<') {
                        sb.append("  ");
                    }
                    if (c2 == '>') {
                        if (z) {
                            sb.setCharAt(sb.length() - 1, c2);
                        } else {
                            sb.append(c2);
                        }
                        sb.append(10);
                    } else {
                        sb.append(c2);
                        z = false;
                    }
                } else if (!z) {
                    sb.append(' ');
                }
                z = true;
            } else {
                if (c2 == c) {
                    c = ' ';
                }
                sb.append(c2);
            }
        }
        return sb.toString();
    }

    public static DocType parse(String str, JDOMFactory jDOMFactory) throws JDOMException {
        DocType docType;
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            String group = matcher.group(1);
            String group2 = getGroup(matcher, 7, 9, 19, 21);
            String group3 = getGroup(matcher, 13, 15);
            String group4 = getGroup(matcher, 23);
            if (group3 != null) {
                docType = jDOMFactory.docType(group, group3, group2);
            } else if (group2 != null) {
                docType = jDOMFactory.docType(group, group2);
            } else {
                docType = jDOMFactory.docType(group);
            }
            if (group4 != null) {
                docType.setInternalSubset(formatInternal(group4));
            }
            return docType;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Doctype input does not appear to be valid: ");
        sb.append(str);
        throw new JDOMException(sb.toString());
    }

    private DTDParser() {
    }
}
