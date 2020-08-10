package com.google.firebase.database.snapshot;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ValueIndex extends Index {
    private static final ValueIndex INSTANCE = new ValueIndex();

    public String getQueryDefinition() {
        return ".value";
    }

    public int hashCode() {
        return 4;
    }

    public boolean isDefinedOn(Node node) {
        return true;
    }

    public String toString() {
        return "ValueIndex";
    }

    private ValueIndex() {
    }

    public static ValueIndex getInstance() {
        return INSTANCE;
    }

    public NamedNode makePost(ChildKey childKey, Node node) {
        return new NamedNode(childKey, node);
    }

    public NamedNode maxPost() {
        return new NamedNode(ChildKey.getMaxName(), Node.MAX_NODE);
    }

    public int compare(NamedNode namedNode, NamedNode namedNode2) {
        int compareTo = namedNode.getNode().compareTo(namedNode2.getNode());
        return compareTo == 0 ? namedNode.getName().compareTo(namedNode2.getName()) : compareTo;
    }

    public boolean equals(Object obj) {
        return obj instanceof ValueIndex;
    }
}
