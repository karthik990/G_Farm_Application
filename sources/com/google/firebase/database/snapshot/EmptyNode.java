package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.Node.HashVersion;
import java.util.Collections;
import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class EmptyNode extends ChildrenNode implements Node {
    private static final EmptyNode empty = new EmptyNode();

    public Node getChild(Path path) {
        return this;
    }

    public int getChildCount() {
        return 0;
    }

    public String getHash() {
        return "";
    }

    public String getHashRepresentation(HashVersion hashVersion) {
        return "";
    }

    public Node getImmediateChild(ChildKey childKey) {
        return this;
    }

    public ChildKey getPredecessorChildKey(ChildKey childKey) {
        return null;
    }

    public Node getPriority() {
        return this;
    }

    public ChildKey getSuccessorChildKey(ChildKey childKey) {
        return null;
    }

    public Object getValue() {
        return null;
    }

    public Object getValue(boolean z) {
        return null;
    }

    public boolean hasChild(ChildKey childKey) {
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean isLeafNode() {
        return false;
    }

    public String toString() {
        return "<Empty Node>";
    }

    public EmptyNode updatePriority(Node node) {
        return this;
    }

    private EmptyNode() {
    }

    public static EmptyNode Empty() {
        return empty;
    }

    public Node updateImmediateChild(ChildKey childKey, Node node) {
        if (!node.isEmpty() && !childKey.isPriorityChildName()) {
            return new ChildrenNode().updateImmediateChild(childKey, node);
        }
        return this;
    }

    public Node updateChild(Path path, Node node) {
        if (path.isEmpty()) {
            return node;
        }
        ChildKey front = path.getFront();
        return updateImmediateChild(front, getImmediateChild(front).updateChild(path.popFront(), node));
    }

    public Iterator<NamedNode> iterator() {
        return Collections.emptyList().iterator();
    }

    public Iterator<NamedNode> reverseIterator() {
        return Collections.emptyList().iterator();
    }

    public int compareTo(Node node) {
        return node.isEmpty() ? 0 : -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        if (getPriority().equals(r3.getPriority()) != false) goto L_0x0022;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            boolean r0 = r3 instanceof com.google.firebase.database.snapshot.EmptyNode
            r1 = 1
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            boolean r0 = r3 instanceof com.google.firebase.database.snapshot.Node
            if (r0 == 0) goto L_0x0021
            com.google.firebase.database.snapshot.Node r3 = (com.google.firebase.database.snapshot.Node) r3
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x0021
            com.google.firebase.database.snapshot.Node r0 = r2.getPriority()
            com.google.firebase.database.snapshot.Node r3 = r3.getPriority()
            boolean r3 = r0.equals(r3)
            if (r3 == 0) goto L_0x0021
            goto L_0x0022
        L_0x0021:
            r1 = 0
        L_0x0022:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.snapshot.EmptyNode.equals(java.lang.Object):boolean");
    }
}
