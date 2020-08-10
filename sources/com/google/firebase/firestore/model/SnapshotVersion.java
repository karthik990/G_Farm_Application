package com.google.firebase.firestore.model;

import com.google.firebase.Timestamp;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class SnapshotVersion implements Comparable<SnapshotVersion> {
    public static final SnapshotVersion NONE = new SnapshotVersion(new Timestamp(0, 0));
    private final Timestamp timestamp;

    public SnapshotVersion(Timestamp timestamp2) {
        this.timestamp = timestamp2;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    public int compareTo(SnapshotVersion snapshotVersion) {
        return this.timestamp.compareTo(snapshotVersion.timestamp);
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SnapshotVersion)) {
            return false;
        }
        if (compareTo((SnapshotVersion) obj) != 0) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return getTimestamp().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SnapshotVersion(seconds=");
        sb.append(this.timestamp.getSeconds());
        sb.append(", nanos=");
        sb.append(this.timestamp.getNanoseconds());
        sb.append(")");
        return sb.toString();
    }
}
