package com.google.firebase.firestore.core;

import com.google.firebase.firestore.model.Document;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class DocumentViewChange {
    private final Document document;
    private final Type type;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public enum Type {
        REMOVED,
        ADDED,
        MODIFIED,
        METADATA
    }

    public static DocumentViewChange create(Type type2, Document document2) {
        return new DocumentViewChange(type2, document2);
    }

    private DocumentViewChange(Type type2, Document document2) {
        this.type = type2;
        this.document = document2;
    }

    public Document getDocument() {
        return this.document;
    }

    public Type getType() {
        return this.type;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DocumentViewChange)) {
            return false;
        }
        DocumentViewChange documentViewChange = (DocumentViewChange) obj;
        if (this.type.equals(documentViewChange.type) && this.document.equals(documentViewChange.document)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return ((1891 + this.type.hashCode()) * 31) + this.document.hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DocumentViewChange(");
        sb.append(this.document);
        sb.append(",");
        sb.append(this.type);
        sb.append(")");
        return sb.toString();
    }
}
