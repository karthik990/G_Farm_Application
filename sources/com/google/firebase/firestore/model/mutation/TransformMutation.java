package com.google.firebase.firestore.model.mutation;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.Document.DocumentState;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.UnknownDocument;
import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class TransformMutation extends Mutation {
    private final List<FieldTransform> fieldTransforms;

    public TransformMutation(DocumentKey documentKey, List<FieldTransform> list) {
        super(documentKey, Precondition.exists(true));
        this.fieldTransforms = list;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TransformMutation transformMutation = (TransformMutation) obj;
        if (!hasSameKeyAndPrecondition(transformMutation) || !this.fieldTransforms.equals(transformMutation.fieldTransforms)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (keyAndPreconditionHashCode() * 31) + this.fieldTransforms.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TransformMutation{");
        sb.append(keyAndPreconditionToString());
        sb.append(", fieldTransforms=");
        sb.append(this.fieldTransforms);
        sb.append("}");
        return sb.toString();
    }

    public List<FieldTransform> getFieldTransforms() {
        return this.fieldTransforms;
    }

    public MaybeDocument applyToRemoteDocument(@Nullable MaybeDocument maybeDocument, MutationResult mutationResult) {
        verifyKeyMatches(maybeDocument);
        Assert.hardAssert(mutationResult.getTransformResults() != null, "Transform results missing for TransformMutation.", new Object[0]);
        if (!getPrecondition().isValidFor(maybeDocument)) {
            return new UnknownDocument(getKey(), mutationResult.getVersion());
        }
        Document requireDocument = requireDocument(maybeDocument);
        return new Document(getKey(), mutationResult.getVersion(), transformObject(requireDocument.getData(), serverTransformResults(requireDocument, mutationResult.getTransformResults())), DocumentState.COMMITTED_MUTATIONS);
    }

    @Nullable
    public MaybeDocument applyToLocalView(@Nullable MaybeDocument maybeDocument, @Nullable MaybeDocument maybeDocument2, Timestamp timestamp) {
        verifyKeyMatches(maybeDocument);
        if (!getPrecondition().isValidFor(maybeDocument)) {
            return maybeDocument;
        }
        Document requireDocument = requireDocument(maybeDocument);
        return new Document(getKey(), requireDocument.getVersion(), transformObject(requireDocument.getData(), localTransformResults(timestamp, maybeDocument2)), DocumentState.LOCAL_MUTATIONS);
    }

    public FieldMask getFieldMask() {
        HashSet hashSet = new HashSet();
        for (FieldTransform fieldPath : this.fieldTransforms) {
            hashSet.add(fieldPath.getFieldPath());
        }
        return FieldMask.fromSet(hashSet);
    }

    public boolean isIdempotent() {
        for (FieldTransform isIdempotent : this.fieldTransforms) {
            if (!isIdempotent.isIdempotent()) {
                return false;
            }
        }
        return true;
    }

    private Document requireDocument(@Nullable MaybeDocument maybeDocument) {
        Assert.hardAssert(maybeDocument instanceof Document, "Unknown MaybeDocument type %s", maybeDocument);
        Document document = (Document) maybeDocument;
        Assert.hardAssert(document.getKey().equals(getKey()), "Can only transform a document with the same key", new Object[0]);
        return document;
    }

    private List<FieldValue> serverTransformResults(@Nullable MaybeDocument maybeDocument, List<FieldValue> list) {
        ArrayList arrayList = new ArrayList(this.fieldTransforms.size());
        Assert.hardAssert(this.fieldTransforms.size() == list.size(), "server transform count (%d) should match field transform count (%d)", Integer.valueOf(list.size()), Integer.valueOf(this.fieldTransforms.size()));
        for (int i = 0; i < list.size(); i++) {
            FieldTransform fieldTransform = (FieldTransform) this.fieldTransforms.get(i);
            TransformOperation operation = fieldTransform.getOperation();
            FieldValue fieldValue = null;
            if (maybeDocument instanceof Document) {
                fieldValue = ((Document) maybeDocument).getField(fieldTransform.getFieldPath());
            }
            arrayList.add(operation.applyToRemoteDocument(fieldValue, (FieldValue) list.get(i)));
        }
        return arrayList;
    }

    private List<FieldValue> localTransformResults(Timestamp timestamp, @Nullable MaybeDocument maybeDocument) {
        ArrayList arrayList = new ArrayList(this.fieldTransforms.size());
        for (FieldTransform fieldTransform : this.fieldTransforms) {
            TransformOperation operation = fieldTransform.getOperation();
            FieldValue fieldValue = null;
            if (maybeDocument instanceof Document) {
                fieldValue = ((Document) maybeDocument).getField(fieldTransform.getFieldPath());
            }
            arrayList.add(operation.applyToLocalView(fieldValue, timestamp));
        }
        return arrayList;
    }

    private ObjectValue transformObject(ObjectValue objectValue, List<FieldValue> list) {
        Assert.hardAssert(list.size() == this.fieldTransforms.size(), "Transform results length mismatch.", new Object[0]);
        for (int i = 0; i < this.fieldTransforms.size(); i++) {
            objectValue = objectValue.set(((FieldTransform) this.fieldTransforms.get(i)).getFieldPath(), (FieldValue) list.get(i));
        }
        return objectValue;
    }
}
