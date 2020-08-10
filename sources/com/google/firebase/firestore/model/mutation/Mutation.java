package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.util.Assert;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public abstract class Mutation {
    private final DocumentKey key;
    private final Precondition precondition;

    @Nullable
    public abstract MaybeDocument applyToLocalView(@Nullable MaybeDocument maybeDocument, @Nullable MaybeDocument maybeDocument2, Timestamp timestamp);

    public abstract MaybeDocument applyToRemoteDocument(@Nullable MaybeDocument maybeDocument, MutationResult mutationResult);

    @Nullable
    public abstract FieldMask getFieldMask();

    public abstract boolean isIdempotent();

    Mutation(DocumentKey documentKey, Precondition precondition2) {
        this.key = documentKey;
        this.precondition = precondition2;
    }

    public DocumentKey getKey() {
        return this.key;
    }

    public Precondition getPrecondition() {
        return this.precondition;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasSameKeyAndPrecondition(Mutation mutation) {
        return this.key.equals(mutation.key) && this.precondition.equals(mutation.precondition);
    }

    /* access modifiers changed from: 0000 */
    public int keyAndPreconditionHashCode() {
        return (getKey().hashCode() * 31) + this.precondition.hashCode();
    }

    /* access modifiers changed from: 0000 */
    public String keyAndPreconditionToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("key=");
        sb.append(this.key);
        sb.append(", precondition=");
        sb.append(this.precondition);
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public void verifyKeyMatches(@Nullable MaybeDocument maybeDocument) {
        if (maybeDocument != null) {
            Assert.hardAssert(maybeDocument.getKey().equals(getKey()), "Can only apply a mutation to a document with the same key", new Object[0]);
        }
    }

    static SnapshotVersion getPostMutationVersion(@Nullable MaybeDocument maybeDocument) {
        if (maybeDocument instanceof Document) {
            return maybeDocument.getVersion();
        }
        return SnapshotVersion.NONE;
    }
}
