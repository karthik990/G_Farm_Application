package com.google.firebase.database.core.view;

import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ViewCache {
    private final CacheNode eventSnap;
    private final CacheNode serverSnap;

    public ViewCache(CacheNode cacheNode, CacheNode cacheNode2) {
        this.eventSnap = cacheNode;
        this.serverSnap = cacheNode2;
    }

    public ViewCache updateEventSnap(IndexedNode indexedNode, boolean z, boolean z2) {
        return new ViewCache(new CacheNode(indexedNode, z, z2), this.serverSnap);
    }

    public ViewCache updateServerSnap(IndexedNode indexedNode, boolean z, boolean z2) {
        return new ViewCache(this.eventSnap, new CacheNode(indexedNode, z, z2));
    }

    public CacheNode getEventCache() {
        return this.eventSnap;
    }

    public Node getCompleteEventSnap() {
        if (this.eventSnap.isFullyInitialized()) {
            return this.eventSnap.getNode();
        }
        return null;
    }

    public CacheNode getServerCache() {
        return this.serverSnap;
    }

    public Node getCompleteServerSnap() {
        if (this.serverSnap.isFullyInitialized()) {
            return this.serverSnap.getNode();
        }
        return null;
    }
}
