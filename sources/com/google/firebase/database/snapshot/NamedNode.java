package com.google.firebase.database.snapshot;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class NamedNode {
    private static final NamedNode MAX_NODE = new NamedNode(ChildKey.getMaxName(), Node.MAX_NODE);
    private static final NamedNode MIN_NODE = new NamedNode(ChildKey.getMinName(), EmptyNode.Empty());
    private final ChildKey name;
    private final Node node;

    public static NamedNode getMinNode() {
        return MIN_NODE;
    }

    public static NamedNode getMaxNode() {
        return MAX_NODE;
    }

    public NamedNode(ChildKey childKey, Node node2) {
        this.name = childKey;
        this.node = node2;
    }

    public ChildKey getName() {
        return this.name;
    }

    public Node getNode() {
        return this.node;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NamedNode{name=");
        sb.append(this.name);
        sb.append(", node=");
        sb.append(this.node);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NamedNode namedNode = (NamedNode) obj;
        return this.name.equals(namedNode.name) && this.node.equals(namedNode.node);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.node.hashCode();
    }
}
