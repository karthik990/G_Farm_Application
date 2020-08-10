package com.google.firebase.firestore.remote;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class ExistenceFilter {
    private final int count;

    public ExistenceFilter(int i) {
        this.count = i;
    }

    public int getCount() {
        return this.count;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExistenceFilter{count=");
        sb.append(this.count);
        sb.append('}');
        return sb.toString();
    }
}
