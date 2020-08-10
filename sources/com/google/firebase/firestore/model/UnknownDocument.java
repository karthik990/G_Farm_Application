package com.google.firebase.firestore.model;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class UnknownDocument extends MaybeDocument {
    public boolean hasPendingWrites() {
        return true;
    }

    public UnknownDocument(DocumentKey documentKey, SnapshotVersion snapshotVersion) {
        super(documentKey, snapshotVersion);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UnknownDocument unknownDocument = (UnknownDocument) obj;
        if (!getVersion().equals(unknownDocument.getVersion()) || !getKey().equals(unknownDocument.getKey())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (getKey().hashCode() * 31) + getVersion().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UnknownDocument{key=");
        sb.append(getKey());
        sb.append(", version=");
        sb.append(getVersion());
        sb.append('}');
        return sb.toString();
    }
}
