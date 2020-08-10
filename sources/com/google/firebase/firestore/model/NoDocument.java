package com.google.firebase.firestore.model;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class NoDocument extends MaybeDocument {
    private boolean hasCommittedMutations;

    public NoDocument(DocumentKey documentKey, SnapshotVersion snapshotVersion, boolean z) {
        super(documentKey, snapshotVersion);
        this.hasCommittedMutations = z;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NoDocument noDocument = (NoDocument) obj;
        if (this.hasCommittedMutations != noDocument.hasCommittedMutations || !getVersion().equals(noDocument.getVersion()) || !getKey().equals(noDocument.getKey())) {
            z = false;
        }
        return z;
    }

    public boolean hasPendingWrites() {
        return hasCommittedMutations();
    }

    public boolean hasCommittedMutations() {
        return this.hasCommittedMutations;
    }

    public int hashCode() {
        return (((getKey().hashCode() * 31) + (this.hasCommittedMutations ? 1 : 0)) * 31) + getVersion().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NoDocument{key=");
        sb.append(getKey());
        sb.append(", version=");
        sb.append(getVersion());
        sb.append(", hasCommittedMutations=");
        sb.append(hasCommittedMutations());
        sb.append("}");
        return sb.toString();
    }
}
