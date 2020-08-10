package com.google.firebase.database.core.view;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class CacheNode {
    private final boolean filtered;
    private final boolean fullyInitialized;
    private final IndexedNode indexedNode;

    public CacheNode(IndexedNode indexedNode2, boolean z, boolean z2) {
        this.indexedNode = indexedNode2;
        this.fullyInitialized = z;
        this.filtered = z2;
    }

    public boolean isFullyInitialized() {
        return this.fullyInitialized;
    }

    public boolean isFiltered() {
        return this.filtered;
    }

    public boolean isCompleteForPath(Path path) {
        if (!path.isEmpty()) {
            return isCompleteForChild(path.getFront());
        }
        return isFullyInitialized() && !this.filtered;
    }

    public boolean isCompleteForChild(ChildKey childKey) {
        return (isFullyInitialized() && !this.filtered) || this.indexedNode.getNode().hasChild(childKey);
    }

    public Node getNode() {
        return this.indexedNode.getNode();
    }

    public IndexedNode getIndexedNode() {
        return this.indexedNode;
    }
}
