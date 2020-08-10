package com.google.firebase.firestore.model;

import com.google.firebase.firestore.model.value.FieldValue;
import com.google.firebase.firestore.model.value.ObjectValue;
import java.util.Comparator;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class Document extends MaybeDocument {
    private static final Comparator<Document> KEY_COMPARATOR = new Comparator<Document>() {
        public int compare(Document document, Document document2) {
            return document.getKey().compareTo(document2.getKey());
        }
    };
    private final ObjectValue data;
    private final DocumentState documentState;
    private final com.google.firestore.p050v1.Document proto;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum DocumentState {
        LOCAL_MUTATIONS,
        COMMITTED_MUTATIONS,
        SYNCED
    }

    public static Comparator<Document> keyComparator() {
        return KEY_COMPARATOR;
    }

    @Nullable
    public com.google.firestore.p050v1.Document getProto() {
        return this.proto;
    }

    public Document(DocumentKey documentKey, SnapshotVersion snapshotVersion, ObjectValue objectValue, DocumentState documentState2) {
        super(documentKey, snapshotVersion);
        this.data = objectValue;
        this.documentState = documentState2;
        this.proto = null;
    }

    public Document(DocumentKey documentKey, SnapshotVersion snapshotVersion, ObjectValue objectValue, DocumentState documentState2, com.google.firestore.p050v1.Document document) {
        super(documentKey, snapshotVersion);
        this.data = objectValue;
        this.documentState = documentState2;
        this.proto = document;
    }

    public ObjectValue getData() {
        return this.data;
    }

    @Nullable
    public FieldValue getField(FieldPath fieldPath) {
        return this.data.get(fieldPath);
    }

    @Nullable
    public Object getFieldValue(FieldPath fieldPath) {
        FieldValue field = getField(fieldPath);
        if (field == null) {
            return null;
        }
        return field.value();
    }

    public boolean hasLocalMutations() {
        return this.documentState.equals(DocumentState.LOCAL_MUTATIONS);
    }

    public boolean hasCommittedMutations() {
        return this.documentState.equals(DocumentState.COMMITTED_MUTATIONS);
    }

    public boolean hasPendingWrites() {
        return hasLocalMutations() || hasCommittedMutations();
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Document document = (Document) obj;
        if (!getVersion().equals(document.getVersion()) || !getKey().equals(document.getKey()) || !this.documentState.equals(document.documentState) || !this.data.equals(document.data)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (((((getKey().hashCode() * 31) + this.data.hashCode()) * 31) + getVersion().hashCode()) * 31) + this.documentState.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Document{key=");
        sb.append(getKey());
        sb.append(", data=");
        sb.append(this.data);
        sb.append(", version=");
        sb.append(getVersion());
        sb.append(", documentState=");
        sb.append(this.documentState.name());
        sb.append('}');
        return sb.toString();
    }
}
