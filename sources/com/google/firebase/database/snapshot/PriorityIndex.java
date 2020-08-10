package com.google.firebase.database.snapshot;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class PriorityIndex extends Index {
    private static final PriorityIndex INSTANCE = new PriorityIndex();

    public int hashCode() {
        return 3155577;
    }

    public String toString() {
        return "PriorityIndex";
    }

    public static PriorityIndex getInstance() {
        return INSTANCE;
    }

    private PriorityIndex() {
    }

    public int compare(NamedNode namedNode, NamedNode namedNode2) {
        return NodeUtilities.nameAndPriorityCompare(namedNode.getName(), namedNode.getNode().getPriority(), namedNode2.getName(), namedNode2.getNode().getPriority());
    }

    public boolean isDefinedOn(Node node) {
        return !node.getPriority().isEmpty();
    }

    public NamedNode makePost(ChildKey childKey, Node node) {
        return new NamedNode(childKey, new StringNode("[PRIORITY-POST]", node));
    }

    public NamedNode maxPost() {
        return makePost(ChildKey.getMaxName(), Node.MAX_NODE);
    }

    public String getQueryDefinition() {
        throw new IllegalArgumentException("Can't get query definition on priority index!");
    }

    public boolean equals(Object obj) {
        return obj instanceof PriorityIndex;
    }
}
