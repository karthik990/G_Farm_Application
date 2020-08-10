package com.google.firebase.database.core;

import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.ChildrenNode.ChildVisitor;
import com.google.firebase.database.snapshot.Node;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
class SparseSnapshotTree {
    private Map<ChildKey, SparseSnapshotTree> children = null;
    private Node value = null;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface SparseSnapshotChildVisitor {
        void visitChild(ChildKey childKey, SparseSnapshotTree sparseSnapshotTree);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface SparseSnapshotTreeVisitor {
        void visitTree(Path path, Node node);
    }

    public void remember(Path path, Node node) {
        if (path.isEmpty()) {
            this.value = node;
            this.children = null;
            return;
        }
        Node node2 = this.value;
        if (node2 != null) {
            this.value = node2.updateChild(path, node);
            return;
        }
        if (this.children == null) {
            this.children = new HashMap();
        }
        ChildKey front = path.getFront();
        if (!this.children.containsKey(front)) {
            this.children.put(front, new SparseSnapshotTree());
        }
        ((SparseSnapshotTree) this.children.get(front)).remember(path.popFront(), node);
    }

    public boolean forget(final Path path) {
        if (path.isEmpty()) {
            this.value = null;
            this.children = null;
            return true;
        }
        Node node = this.value;
        if (node != null) {
            if (node.isLeafNode()) {
                return false;
            }
            ChildrenNode childrenNode = (ChildrenNode) this.value;
            this.value = null;
            childrenNode.forEachChild(new ChildVisitor() {
                public void visitChild(ChildKey childKey, Node node) {
                    SparseSnapshotTree.this.remember(path.child(childKey), node);
                }
            });
            return forget(path);
        } else if (this.children == null) {
            return true;
        } else {
            ChildKey front = path.getFront();
            Path popFront = path.popFront();
            if (this.children.containsKey(front) && ((SparseSnapshotTree) this.children.get(front)).forget(popFront)) {
                this.children.remove(front);
            }
            if (!this.children.isEmpty()) {
                return false;
            }
            this.children = null;
            return true;
        }
    }

    public void forEachTree(final Path path, final SparseSnapshotTreeVisitor sparseSnapshotTreeVisitor) {
        Node node = this.value;
        if (node != null) {
            sparseSnapshotTreeVisitor.visitTree(path, node);
        } else {
            forEachChild(new SparseSnapshotChildVisitor() {
                public void visitChild(ChildKey childKey, SparseSnapshotTree sparseSnapshotTree) {
                    sparseSnapshotTree.forEachTree(path.child(childKey), sparseSnapshotTreeVisitor);
                }
            });
        }
    }

    public void forEachChild(SparseSnapshotChildVisitor sparseSnapshotChildVisitor) {
        Map<ChildKey, SparseSnapshotTree> map = this.children;
        if (map != null) {
            for (Entry entry : map.entrySet()) {
                sparseSnapshotChildVisitor.visitChild((ChildKey) entry.getKey(), (SparseSnapshotTree) entry.getValue());
            }
        }
    }
}
