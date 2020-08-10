package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.Document.DocumentState;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.util.Assert;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class SetMutation extends Mutation {
    private final ObjectValue value;

    @Nullable
    public FieldMask getFieldMask() {
        return null;
    }

    public boolean isIdempotent() {
        return true;
    }

    public SetMutation(DocumentKey documentKey, ObjectValue objectValue, Precondition precondition) {
        super(documentKey, precondition);
        this.value = objectValue;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SetMutation setMutation = (SetMutation) obj;
        if (!hasSameKeyAndPrecondition(setMutation) || !this.value.equals(setMutation.value)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (keyAndPreconditionHashCode() * 31) + this.value.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SetMutation{");
        sb.append(keyAndPreconditionToString());
        sb.append(", value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public MaybeDocument applyToRemoteDocument(@Nullable MaybeDocument maybeDocument, MutationResult mutationResult) {
        verifyKeyMatches(maybeDocument);
        Assert.hardAssert(mutationResult.getTransformResults() == null, "Transform results received by SetMutation.", new Object[0]);
        return new Document(getKey(), mutationResult.getVersion(), this.value, DocumentState.COMMITTED_MUTATIONS);
    }

    @Nullable
    public MaybeDocument applyToLocalView(@Nullable MaybeDocument maybeDocument, @Nullable MaybeDocument maybeDocument2, Timestamp timestamp) {
        verifyKeyMatches(maybeDocument);
        if (!getPrecondition().isValidFor(maybeDocument)) {
            return maybeDocument;
        }
        return new Document(getKey(), getPostMutationVersion(maybeDocument), this.value, DocumentState.LOCAL_MUTATIONS);
    }

    public ObjectValue getValue() {
        return this.value;
    }
}
