package com.google.firebase.firestore.local;

import com.google.firebase.database.collection.ImmutableSortedSet;
import com.google.firebase.firestore.model.DocumentKey;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class ReferenceSet {
    private ImmutableSortedSet<DocumentReference> referencesByKey = new ImmutableSortedSet<>(Collections.emptyList(), DocumentReference.BY_KEY);
    private ImmutableSortedSet<DocumentReference> referencesByTarget = new ImmutableSortedSet<>(Collections.emptyList(), DocumentReference.BY_TARGET);

    public boolean isEmpty() {
        return this.referencesByKey.isEmpty();
    }

    public void addReference(DocumentKey documentKey, int i) {
        DocumentReference documentReference = new DocumentReference(documentKey, i);
        this.referencesByKey = this.referencesByKey.insert(documentReference);
        this.referencesByTarget = this.referencesByTarget.insert(documentReference);
    }

    public void addReferences(ImmutableSortedSet<DocumentKey> immutableSortedSet, int i) {
        Iterator it = immutableSortedSet.iterator();
        while (it.hasNext()) {
            addReference((DocumentKey) it.next(), i);
        }
    }

    public void removeReference(DocumentKey documentKey, int i) {
        removeReference(new DocumentReference(documentKey, i));
    }

    public void removeReferences(ImmutableSortedSet<DocumentKey> immutableSortedSet, int i) {
        Iterator it = immutableSortedSet.iterator();
        while (it.hasNext()) {
            removeReference((DocumentKey) it.next(), i);
        }
    }

    public ImmutableSortedSet<DocumentKey> removeReferencesForId(int i) {
        Iterator iteratorFrom = this.referencesByTarget.iteratorFrom(new DocumentReference(DocumentKey.empty(), i));
        ImmutableSortedSet<DocumentKey> emptyKeySet = DocumentKey.emptyKeySet();
        while (iteratorFrom.hasNext()) {
            DocumentReference documentReference = (DocumentReference) iteratorFrom.next();
            if (documentReference.getId() != i) {
                break;
            }
            emptyKeySet = emptyKeySet.insert(documentReference.getKey());
            removeReference(documentReference);
        }
        return emptyKeySet;
    }

    public void removeAllReferences() {
        Iterator it = this.referencesByKey.iterator();
        while (it.hasNext()) {
            removeReference((DocumentReference) it.next());
        }
    }

    private void removeReference(DocumentReference documentReference) {
        this.referencesByKey = this.referencesByKey.remove(documentReference);
        this.referencesByTarget = this.referencesByTarget.remove(documentReference);
    }

    public ImmutableSortedSet<DocumentKey> referencesForId(int i) {
        Iterator iteratorFrom = this.referencesByTarget.iteratorFrom(new DocumentReference(DocumentKey.empty(), i));
        ImmutableSortedSet<DocumentKey> emptyKeySet = DocumentKey.emptyKeySet();
        while (iteratorFrom.hasNext()) {
            DocumentReference documentReference = (DocumentReference) iteratorFrom.next();
            if (documentReference.getId() != i) {
                break;
            }
            emptyKeySet = emptyKeySet.insert(documentReference.getKey());
        }
        return emptyKeySet;
    }

    public boolean containsKey(DocumentKey documentKey) {
        Iterator iteratorFrom = this.referencesByKey.iteratorFrom(new DocumentReference(documentKey, 0));
        if (!iteratorFrom.hasNext()) {
            return false;
        }
        return ((DocumentReference) iteratorFrom.next()).getKey().equals(documentKey);
    }
}
