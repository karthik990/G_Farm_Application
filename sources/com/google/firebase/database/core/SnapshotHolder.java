package com.google.firebase.database.core;

import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Node;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class SnapshotHolder {
    private Node rootNode;

    SnapshotHolder() {
        this.rootNode = EmptyNode.Empty();
    }

    public SnapshotHolder(Node node) {
        this.rootNode = node;
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public Node getNode(Path path) {
        return this.rootNode.getChild(path);
    }

    public void update(Path path, Node node) {
        this.rootNode = this.rootNode.updateChild(path, node);
    }
}
