package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.NullValue;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class NullFilter extends Filter {
    private final FieldPath fieldPath;

    public NullFilter(FieldPath fieldPath2) {
        this.fieldPath = fieldPath2;
    }

    public FieldPath getField() {
        return this.fieldPath;
    }

    public boolean matches(Document document) {
        FieldValue field = document.getField(this.fieldPath);
        return field != null && field.equals(NullValue.nullValue());
    }

    public String getCanonicalId() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.fieldPath.canonicalString());
        sb.append(" IS NULL");
        return sb.toString();
    }

    public String toString() {
        return getCanonicalId();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof NullFilter)) {
            return false;
        }
        return this.fieldPath.equals(((NullFilter) obj).fieldPath);
    }

    public int hashCode() {
        return 1147 + this.fieldPath.hashCode();
    }
}
