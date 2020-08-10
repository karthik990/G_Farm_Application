package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.value.DoubleValue;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.NullValue;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public abstract class Filter {

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Operator {
        LESS_THAN("<"),
        LESS_THAN_OR_EQUAL("<="),
        EQUAL("=="),
        GREATER_THAN(">"),
        GREATER_THAN_OR_EQUAL(">="),
        ARRAY_CONTAINS("array_contains");
        
        private final String text;

        private Operator(String str) {
            this.text = str;
        }

        public String toString() {
            return this.text;
        }
    }

    public abstract String getCanonicalId();

    public abstract FieldPath getField();

    public abstract boolean matches(Document document);

    public static Filter create(FieldPath fieldPath, Operator operator, FieldValue fieldValue) {
        if (fieldValue.equals(NullValue.nullValue())) {
            if (operator == Operator.EQUAL) {
                return new NullFilter(fieldPath);
            }
            throw new IllegalArgumentException("Invalid Query. You can only perform equality comparisons on null (via whereEqualTo()).");
        } else if (!fieldValue.equals(DoubleValue.NaN)) {
            return new RelationFilter(fieldPath, operator, fieldValue);
        } else {
            if (operator == Operator.EQUAL) {
                return new NaNFilter(fieldPath);
            }
            throw new IllegalArgumentException("Invalid Query. You can only perform equality comparisons on NaN (via whereEqualTo()).");
        }
    }
}
