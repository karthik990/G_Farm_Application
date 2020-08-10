package com.google.firebase.database.core;

import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class WriteTreeRef {
    private final Path treePath;
    private final WriteTree writeTree;

    public WriteTreeRef(Path path, WriteTree writeTree2) {
        this.treePath = path;
        this.writeTree = writeTree2;
    }

    public Node calcCompleteEventCache(Node node) {
        return calcCompleteEventCache(node, Collections.emptyList());
    }

    public Node calcCompleteEventCache(Node node, List<Long> list) {
        return calcCompleteEventCache(node, list, false);
    }

    public Node calcCompleteEventCache(Node node, List<Long> list, boolean z) {
        return this.writeTree.calcCompleteEventCache(this.treePath, node, list, z);
    }

    public Node calcCompleteEventChildren(Node node) {
        return this.writeTree.calcCompleteEventChildren(this.treePath, node);
    }

    public Node calcEventCacheAfterServerOverwrite(Path path, Node node, Node node2) {
        return this.writeTree.calcEventCacheAfterServerOverwrite(this.treePath, path, node, node2);
    }

    public Node shadowingWrite(Path path) {
        return this.writeTree.shadowingWrite(this.treePath.child(path));
    }

    public NamedNode calcNextNodeAfterPost(Node node, NamedNode namedNode, boolean z, Index index) {
        return this.writeTree.calcNextNodeAfterPost(this.treePath, node, namedNode, z, index);
    }

    public Node calcCompleteChild(ChildKey childKey, CacheNode cacheNode) {
        return this.writeTree.calcCompleteChild(this.treePath, childKey, cacheNode);
    }

    public WriteTreeRef child(ChildKey childKey) {
        return new WriteTreeRef(this.treePath.child(childKey), this.writeTree);
    }
}
