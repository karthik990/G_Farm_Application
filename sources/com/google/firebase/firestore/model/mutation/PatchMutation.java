package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.Document.DocumentState;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.FieldPath;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.UnknownDocument;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.util.Assert;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class PatchMutation extends Mutation {
    private final FieldMask mask;
    private final ObjectValue value;

    public boolean isIdempotent() {
        return true;
    }

    public PatchMutation(DocumentKey documentKey, ObjectValue objectValue, FieldMask fieldMask, Precondition precondition) {
        super(documentKey, precondition);
        this.value = objectValue;
        this.mask = fieldMask;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PatchMutation patchMutation = (PatchMutation) obj;
        if (!hasSameKeyAndPrecondition(patchMutation) || !this.value.equals(patchMutation.value)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (keyAndPreconditionHashCode() * 31) + this.value.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PatchMutation{");
        sb.append(keyAndPreconditionToString());
        sb.append(", mask=");
        sb.append(this.mask);
        sb.append(", value=");
        sb.append(this.value);
        sb.append("}");
        return sb.toString();
    }

    public ObjectValue getValue() {
        return this.value;
    }

    public FieldMask getMask() {
        return this.mask;
    }

    public MaybeDocument applyToRemoteDocument(@Nullable MaybeDocument maybeDocument, MutationResult mutationResult) {
        verifyKeyMatches(maybeDocument);
        Assert.hardAssert(mutationResult.getTransformResults() == null, "Transform results received by PatchMutation.", new Object[0]);
        if (!getPrecondition().isValidFor(maybeDocument)) {
            return new UnknownDocument(getKey(), mutationResult.getVersion());
        }
        return new Document(getKey(), mutationResult.getVersion(), patchDocument(maybeDocument), DocumentState.COMMITTED_MUTATIONS);
    }

    @Nullable
    public MaybeDocument applyToLocalView(@Nullable MaybeDocument maybeDocument, @Nullable MaybeDocument maybeDocument2, Timestamp timestamp) {
        verifyKeyMatches(maybeDocument);
        if (!getPrecondition().isValidFor(maybeDocument)) {
            return maybeDocument;
        }
        return new Document(getKey(), getPostMutationVersion(maybeDocument), patchDocument(maybeDocument), DocumentState.LOCAL_MUTATIONS);
    }

    @Nullable
    public FieldMask getFieldMask() {
        return this.mask;
    }

    private ObjectValue patchDocument(@Nullable MaybeDocument maybeDocument) {
        ObjectValue objectValue;
        if (maybeDocument instanceof Document) {
            objectValue = ((Document) maybeDocument).getData();
        } else {
            objectValue = ObjectValue.emptyObject();
        }
        return patchObject(objectValue);
    }

    private ObjectValue patchObject(ObjectValue objectValue) {
        for (FieldPath fieldPath : this.mask.getMask()) {
            if (!fieldPath.isEmpty()) {
                FieldValue fieldValue = this.value.get(fieldPath);
                if (fieldValue == null) {
                    objectValue = objectValue.delete(fieldPath);
                } else {
                    objectValue = objectValue.set(fieldPath, fieldValue);
                }
            }
        }
        return objectValue;
    }
}
