package com.google.firebase.firestore;

import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class SnapshotMetadata {
    private final boolean hasPendingWrites;
    private final boolean isFromCache;

    SnapshotMetadata(boolean z, boolean z2) {
        this.hasPendingWrites = z;
        this.isFromCache = z2;
    }

    public boolean hasPendingWrites() {
        return this.hasPendingWrites;
    }

    public boolean isFromCache() {
        return this.isFromCache;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SnapshotMetadata)) {
            return false;
        }
        SnapshotMetadata snapshotMetadata = (SnapshotMetadata) obj;
        if (!(this.hasPendingWrites == snapshotMetadata.hasPendingWrites && this.isFromCache == snapshotMetadata.isFromCache)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return ((this.hasPendingWrites ? 1 : 0) * true) + (this.isFromCache ? 1 : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SnapshotMetadata{hasPendingWrites=");
        sb.append(this.hasPendingWrites);
        sb.append(", isFromCache=");
        sb.append(this.isFromCache);
        sb.append('}');
        return sb.toString();
    }
}
