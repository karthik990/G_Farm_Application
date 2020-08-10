package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.value.FieldValue;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public interface TransformOperation {
    FieldValue applyToLocalView(FieldValue fieldValue, Timestamp timestamp);

    FieldValue applyToRemoteDocument(FieldValue fieldValue, FieldValue fieldValue2);

    boolean isIdempotent();
}
