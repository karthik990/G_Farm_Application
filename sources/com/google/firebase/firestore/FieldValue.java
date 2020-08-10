package com.google.firebase.firestore;

import java.util.Arrays;
import java.util.List;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public abstract class FieldValue {
    private static final DeleteFieldValue DELETE_INSTANCE = new DeleteFieldValue();
    private static final ServerTimestampFieldValue SERVER_TIMESTAMP_INSTANCE = new ServerTimestampFieldValue();

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class ArrayRemoveFieldValue extends FieldValue {
        private final List<Object> elements;

        /* access modifiers changed from: 0000 */
        public String getMethodName() {
            return "FieldValue.arrayRemove";
        }

        ArrayRemoveFieldValue(List<Object> list) {
            this.elements = list;
        }

        /* access modifiers changed from: 0000 */
        public List<Object> getElements() {
            return this.elements;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class ArrayUnionFieldValue extends FieldValue {
        private final List<Object> elements;

        /* access modifiers changed from: 0000 */
        public String getMethodName() {
            return "FieldValue.arrayUnion";
        }

        ArrayUnionFieldValue(List<Object> list) {
            this.elements = list;
        }

        /* access modifiers changed from: 0000 */
        public List<Object> getElements() {
            return this.elements;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class DeleteFieldValue extends FieldValue {
        /* access modifiers changed from: 0000 */
        public String getMethodName() {
            return "FieldValue.delete";
        }

        DeleteFieldValue() {
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class NumericIncrementFieldValue extends FieldValue {
        private final Number operand;

        /* access modifiers changed from: 0000 */
        public String getMethodName() {
            return "FieldValue.increment";
        }

        NumericIncrementFieldValue(Number number) {
            this.operand = number;
        }

        /* access modifiers changed from: 0000 */
        public Number getOperand() {
            return this.operand;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    static class ServerTimestampFieldValue extends FieldValue {
        /* access modifiers changed from: 0000 */
        public String getMethodName() {
            return "FieldValue.serverTimestamp";
        }

        ServerTimestampFieldValue() {
        }
    }

    /* access modifiers changed from: 0000 */
    public abstract String getMethodName();

    FieldValue() {
    }

    public static FieldValue delete() {
        return DELETE_INSTANCE;
    }

    public static FieldValue serverTimestamp() {
        return SERVER_TIMESTAMP_INSTANCE;
    }

    public static FieldValue arrayUnion(Object... objArr) {
        return new ArrayUnionFieldValue(Arrays.asList(objArr));
    }

    public static FieldValue arrayRemove(Object... objArr) {
        return new ArrayRemoveFieldValue(Arrays.asList(objArr));
    }

    public static FieldValue increment(long j) {
        return new NumericIncrementFieldValue(Long.valueOf(j));
    }

    public static FieldValue increment(double d) {
        return new NumericIncrementFieldValue(Double.valueOf(d));
    }
}
