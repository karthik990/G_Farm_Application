package com.google.firebase.firestore;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class FieldPath {
    private static final FieldPath DOCUMENT_ID_INSTANCE = new FieldPath(com.google.firebase.firestore.model.FieldPath.KEY_PATH);
    private static final Pattern RESERVED = Pattern.compile("[~*/\\[\\]]");
    private final com.google.firebase.firestore.model.FieldPath internalPath;

    private FieldPath(List<String> list) {
        this.internalPath = com.google.firebase.firestore.model.FieldPath.fromSegments(list);
    }

    private FieldPath(com.google.firebase.firestore.model.FieldPath fieldPath) {
        this.internalPath = fieldPath;
    }

    /* access modifiers changed from: 0000 */
    public com.google.firebase.firestore.model.FieldPath getInternalPath() {
        return this.internalPath;
    }

    /* renamed from: of */
    public static FieldPath m2027of(String... strArr) {
        Preconditions.checkArgument(strArr.length > 0, "Invalid field path. Provided path must not be empty.");
        int i = 0;
        while (i < strArr.length) {
            boolean z = strArr[i] != null && !strArr[i].isEmpty();
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid field name at argument ");
            i++;
            sb.append(i);
            sb.append(". Field names must not be null or empty.");
            Preconditions.checkArgument(z, sb.toString());
        }
        return new FieldPath(Arrays.asList(strArr));
    }

    public static FieldPath documentId() {
        return DOCUMENT_ID_INSTANCE;
    }

    static FieldPath fromDotSeparatedPath(String str) {
        Preconditions.checkNotNull(str, "Provided field path must not be null.");
        boolean z = !RESERVED.matcher(str).find();
        StringBuilder sb = new StringBuilder();
        String str2 = "Invalid field path (";
        sb.append(str2);
        sb.append(str);
        sb.append("). Paths must not contain '~', '*', '/', '[', or ']'");
        Preconditions.checkArgument(z, sb.toString());
        try {
            return m2027of(str.split("\\.", -1));
        } catch (IllegalArgumentException unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str2);
            sb2.append(str);
            sb2.append("). Paths must not be empty, begin with '.', end with '.', or contain '..'");
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public String toString() {
        return this.internalPath.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.internalPath.equals(((FieldPath) obj).internalPath);
    }

    public int hashCode() {
        return this.internalPath.hashCode();
    }
}
