package com.google.firebase.firestore.model;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public final class DocumentSet implements Iterable<Document> {
    private final ImmutableSortedMap<DocumentKey, Document> keyIndex;
    private final ImmutableSortedSet<Document> sortedSet;

    public static DocumentSet emptySet(Comparator<Document> comparator) {
        return new DocumentSet(DocumentCollections.emptyDocumentMap(), new ImmutableSortedSet(Collections.emptyList(), DocumentSet$$Lambda$1.lambdaFactory$(comparator)));
    }

    static /* synthetic */ int lambda$emptySet$0(Comparator comparator, Document document, Document document2) {
        int compare = comparator.compare(document, document2);
        return compare == 0 ? Document.keyComparator().compare(document, document2) : compare;
    }

    private DocumentSet(ImmutableSortedMap<DocumentKey, Document> immutableSortedMap, ImmutableSortedSet<Document> immutableSortedSet) {
        this.keyIndex = immutableSortedMap;
        this.sortedSet = immutableSortedSet;
    }

    public int size() {
        return this.keyIndex.size();
    }

    public boolean isEmpty() {
        return this.keyIndex.isEmpty();
    }

    public boolean contains(DocumentKey documentKey) {
        return this.keyIndex.containsKey(documentKey);
    }

    @Nullable
    public Document getDocument(DocumentKey documentKey) {
        return (Document) this.keyIndex.get(documentKey);
    }

    @Nullable
    public Document getFirstDocument() {
        return (Document) this.sortedSet.getMinEntry();
    }

    @Nullable
    public Document getLastDocument() {
        return (Document) this.sortedSet.getMaxEntry();
    }

    @Nullable
    public Document getPredecessor(DocumentKey documentKey) {
        Document document = (Document) this.keyIndex.get(documentKey);
        if (document != null) {
            return (Document) this.sortedSet.getPredecessorEntry(document);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Key not contained in DocumentSet: ");
        sb.append(documentKey);
        throw new IllegalArgumentException(sb.toString());
    }

    public int indexOf(DocumentKey documentKey) {
        Document document = (Document) this.keyIndex.get(documentKey);
        if (document == null) {
            return -1;
        }
        return this.sortedSet.indexOf(document);
    }

    public DocumentSet add(Document document) {
        DocumentSet remove = remove(document.getKey());
        return new DocumentSet(remove.keyIndex.insert(document.getKey(), document), remove.sortedSet.insert(document));
    }

    public DocumentSet remove(DocumentKey documentKey) {
        Document document = (Document) this.keyIndex.get(documentKey);
        if (document == null) {
            return this;
        }
        return new DocumentSet(this.keyIndex.remove(documentKey), this.sortedSet.remove(document));
    }

    public List<Document> toList() {
        ArrayList arrayList = new ArrayList(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            arrayList.add((Document) it.next());
        }
        return arrayList;
    }

    public Iterator<Document> iterator() {
        return this.sortedSet.iterator();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DocumentSet documentSet = (DocumentSet) obj;
        if (size() != documentSet.size()) {
            return false;
        }
        Iterator it = iterator();
        Iterator it2 = documentSet.iterator();
        while (it.hasNext()) {
            if (!((Document) it.next()).equals((Document) it2.next())) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            i = (i * 31) + ((Document) it.next()).hashCode();
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator it = iterator();
        boolean z = true;
        while (it.hasNext()) {
            Document document = (Document) it.next();
            if (z) {
                z = false;
            } else {
                sb.append(", ");
            }
            sb.append(document);
        }
        sb.append("]");
        return sb.toString();
    }
}
