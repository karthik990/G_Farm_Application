package com.google.firebase.firestore.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class FieldPath extends BasePath<FieldPath> {
    public static final FieldPath EMPTY_PATH = new FieldPath(Collections.emptyList());
    public static final FieldPath KEY_PATH = fromSingleSegment(DocumentKey.KEY_FIELD_NAME);

    private FieldPath(List<String> list) {
        super(list);
    }

    public static FieldPath fromSingleSegment(String str) {
        return new FieldPath(Collections.singletonList(str));
    }

    public static FieldPath fromSegments(List<String> list) {
        return list.isEmpty() ? EMPTY_PATH : new FieldPath(list);
    }

    /* access modifiers changed from: 0000 */
    public FieldPath createPathWithSegments(List<String> list) {
        return new FieldPath(list);
    }

    public static FieldPath fromServerFormat(String str) {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        boolean z = false;
        while (true) {
            String str2 = "). Paths must not be empty, begin with '.', end with '.', or contain '..'";
            String str3 = "Invalid field path (";
            if (i < str.length()) {
                char charAt = str.charAt(i);
                if (charAt == '\\') {
                    i++;
                    if (i != str.length()) {
                        sb.append(str.charAt(i));
                    } else {
                        throw new IllegalArgumentException("Trailing escape character is not allowed");
                    }
                } else if (charAt == '.') {
                    if (!z) {
                        String sb2 = sb.toString();
                        if (!sb2.isEmpty()) {
                            StringBuilder sb3 = new StringBuilder();
                            arrayList.add(sb2);
                            sb = sb3;
                        } else {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append(str3);
                            sb4.append(str);
                            sb4.append(str2);
                            throw new IllegalArgumentException(sb4.toString());
                        }
                    } else {
                        sb.append(charAt);
                    }
                } else if (charAt == '`') {
                    z = !z;
                } else {
                    sb.append(charAt);
                }
                i++;
            } else {
                String sb5 = sb.toString();
                if (!sb5.isEmpty()) {
                    arrayList.add(sb5);
                    return new FieldPath(arrayList);
                }
                StringBuilder sb6 = new StringBuilder();
                sb6.append(str3);
                sb6.append(str);
                sb6.append(str2);
                throw new IllegalArgumentException(sb6.toString());
            }
        }
    }

    private static boolean isValidIdentifier(String str) {
        if (str.isEmpty()) {
            return false;
        }
        char charAt = str.charAt(0);
        if (charAt != '_' && ((charAt < 'a' || charAt > 'z') && (charAt < 'A' || charAt > 'Z'))) {
            return false;
        }
        for (int i = 1; i < str.length(); i++) {
            char charAt2 = str.charAt(i);
            if (charAt2 != '_' && ((charAt2 < 'a' || charAt2 > 'z') && ((charAt2 < 'A' || charAt2 > 'Z') && (charAt2 < '0' || charAt2 > '9')))) {
                return false;
            }
        }
        return true;
    }

    public String canonicalString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.segments.size(); i++) {
            if (i > 0) {
                sb.append(".");
            }
            String replace = ((String) this.segments.get(i)).replace("\\", "\\\\").replace("`", "\\`");
            if (!isValidIdentifier(replace)) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append('`');
                sb2.append(replace);
                sb2.append('`');
                replace = sb2.toString();
            }
            sb.append(replace);
        }
        return sb.toString();
    }

    public boolean isKeyField() {
        return equals(KEY_PATH);
    }
}
