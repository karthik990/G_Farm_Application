package com.google.firebase.firestore.model.mutation;

import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class FieldMask {
    private final Set<FieldPath> mask;

    public static FieldMask fromSet(Set<FieldPath> set) {
        return new FieldMask(set);
    }

    private FieldMask(Set<FieldPath> set) {
        this.mask = set;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.mask.equals(((FieldMask) obj).mask);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FieldMask{mask=");
        sb.append(this.mask.toString());
        sb.append("}");
        return sb.toString();
    }

    public boolean covers(FieldPath fieldPath) {
        for (FieldPath isPrefixOf : this.mask) {
            if (isPrefixOf.isPrefixOf(fieldPath)) {
                return true;
            }
        }
        return false;
    }

    public ObjectValue applyTo(ObjectValue objectValue) {
        ObjectValue emptyObject = ObjectValue.emptyObject();
        for (FieldPath fieldPath : this.mask) {
            if (fieldPath.isEmpty()) {
                return objectValue;
            }
            FieldValue fieldValue = objectValue.get(fieldPath);
            if (fieldValue != null) {
                emptyObject = emptyObject.set(fieldPath, fieldValue);
            }
        }
        return emptyObject;
    }

    public int hashCode() {
        return this.mask.hashCode();
    }

    public Set<FieldPath> getMask() {
        return this.mask;
    }
}
