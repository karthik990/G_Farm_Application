package com.google.firebase.firestore.core;

import com.google.firebase.firestore.core.DocumentViewChange.Type;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.util.Assert;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class DocumentViewChangeSet {
    private final TreeMap<DocumentKey, DocumentViewChange> changes = new TreeMap<>();

    public void addChange(DocumentViewChange documentViewChange) {
        DocumentKey key = documentViewChange.getDocument().getKey();
        DocumentViewChange documentViewChange2 = (DocumentViewChange) this.changes.get(key);
        if (documentViewChange2 == null) {
            this.changes.put(key, documentViewChange);
            return;
        }
        Type type = documentViewChange2.getType();
        Type type2 = documentViewChange.getType();
        if (type2 != Type.ADDED && type == Type.METADATA) {
            this.changes.put(key, documentViewChange);
        } else if (type2 == Type.METADATA && type != Type.REMOVED) {
            this.changes.put(key, DocumentViewChange.create(type, documentViewChange.getDocument()));
        } else if (type2 == Type.MODIFIED && type == Type.MODIFIED) {
            this.changes.put(key, DocumentViewChange.create(Type.MODIFIED, documentViewChange.getDocument()));
        } else if (type2 == Type.MODIFIED && type == Type.ADDED) {
            this.changes.put(key, DocumentViewChange.create(Type.ADDED, documentViewChange.getDocument()));
        } else if (type2 == Type.REMOVED && type == Type.ADDED) {
            this.changes.remove(key);
        } else if (type2 == Type.REMOVED && type == Type.MODIFIED) {
            this.changes.put(key, DocumentViewChange.create(Type.REMOVED, documentViewChange2.getDocument()));
        } else if (type2 == Type.ADDED && type == Type.REMOVED) {
            this.changes.put(key, DocumentViewChange.create(Type.MODIFIED, documentViewChange.getDocument()));
        } else {
            throw Assert.fail("Unsupported combination of changes %s after %s", type2, type);
        }
    }

    /* access modifiers changed from: 0000 */
    public List<DocumentViewChange> getChanges() {
        return new ArrayList(this.changes.values());
    }
}
