package com.google.firebase.firestore.model.value;

import com.google.firebase.Timestamp;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class TimestampValue extends FieldValue {
    private final Timestamp internalValue;

    public int typeOrder() {
        return 3;
    }

    TimestampValue(Timestamp timestamp) {
        this.internalValue = timestamp;
    }

    public Object value() {
        return this.internalValue;
    }

    public Object value(FieldValueOptions fieldValueOptions) {
        if (fieldValueOptions.areTimestampsInSnapshotsEnabled()) {
            return this.internalValue;
        }
        return this.internalValue.toDate();
    }

    public Timestamp getInternalValue() {
        return this.internalValue;
    }

    public String toString() {
        return this.internalValue.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof TimestampValue) && this.internalValue.equals(((TimestampValue) obj).internalValue);
    }

    public int hashCode() {
        return this.internalValue.hashCode();
    }

    public int compareTo(FieldValue fieldValue) {
        if (fieldValue instanceof TimestampValue) {
            return this.internalValue.compareTo(((TimestampValue) fieldValue).internalValue);
        }
        if (fieldValue instanceof ServerTimestampValue) {
            return -1;
        }
        return defaultCompareTo(fieldValue);
    }

    public static TimestampValue valueOf(Timestamp timestamp) {
        return new TimestampValue(timestamp);
    }
}
