package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public interface Node extends Comparable<Node>, Iterable<NamedNode> {
    public static final ChildrenNode MAX_NODE = new ChildrenNode() {
        public int compareTo(Node node) {
            return node == this ? 0 : 1;
        }

        public boolean equals(Object obj) {
            return obj == this;
        }

        public Node getPriority() {
            return this;
        }

        public boolean hasChild(ChildKey childKey) {
            return false;
        }

        public boolean isEmpty() {
            return false;
        }

        public String toString() {
            return "<Max Node>";
        }

        public Node getImmediateChild(ChildKey childKey) {
            if (childKey.isPriorityChildName()) {
                return getPriority();
            }
            return EmptyNode.Empty();
        }
    };

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public enum HashVersion {
        V1,
        V2
    }

    Node getChild(Path path);

    int getChildCount();

    String getHash();

    String getHashRepresentation(HashVersion hashVersion);

    Node getImmediateChild(ChildKey childKey);

    ChildKey getPredecessorChildKey(ChildKey childKey);

    Node getPriority();

    ChildKey getSuccessorChildKey(ChildKey childKey);

    Object getValue();

    Object getValue(boolean z);

    boolean hasChild(ChildKey childKey);

    boolean isEmpty();

    boolean isLeafNode();

    Iterator<NamedNode> reverseIterator();

    Node updateChild(Path path, Node node);

    Node updateImmediateChild(ChildKey childKey, Node node);

    Node updatePriority(Node node);
}
