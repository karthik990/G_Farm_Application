package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.DoubleValue;
import com.google.firebase.firestore.model.value.FieldValue;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class NaNFilter extends Filter {
    private final FieldPath fieldPath;

    public NaNFilter(FieldPath fieldPath2) {
        this.fieldPath = fieldPath2;
    }

    public FieldPath getField() {
        return this.fieldPath;
    }

    public boolean matches(Document document) {
        FieldValue field = document.getField(this.fieldPath);
        return field != null && field.equals(DoubleValue.NaN);
    }

    public String getCanonicalId() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.fieldPath.canonicalString());
        sb.append(" IS NaN");
        return sb.toString();
    }

    public String toString() {
        return getCanonicalId();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof NaNFilter)) {
            return false;
        }
        return this.fieldPath.equals(((NaNFilter) obj).fieldPath);
    }

    public int hashCode() {
        return 1271 + this.fieldPath.hashCode();
    }
}
