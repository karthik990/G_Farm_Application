package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.ServerTimestampValue;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class ServerTimestampOperation implements TransformOperation {
    private static final ServerTimestampOperation SHARED_INSTANCE = new ServerTimestampOperation();

    public FieldValue applyToRemoteDocument(FieldValue fieldValue, FieldValue fieldValue2) {
        return fieldValue2;
    }

    public boolean isIdempotent() {
        return true;
    }

    private ServerTimestampOperation() {
    }

    public static ServerTimestampOperation getInstance() {
        return SHARED_INSTANCE;
    }

    public FieldValue applyToLocalView(FieldValue fieldValue, Timestamp timestamp) {
        return new ServerTimestampValue(timestamp, fieldValue);
    }
}
