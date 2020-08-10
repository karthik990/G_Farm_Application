package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class PathIndex extends Index {
    private final Path indexPath;

    public PathIndex(Path path) {
        if (path.size() != 1 || !path.getFront().isPriorityChildName()) {
            this.indexPath = path;
            return;
        }
        throw new IllegalArgumentException("Can't create PathIndex with '.priority' as key. Please use PriorityIndex instead!");
    }

    public boolean isDefinedOn(Node node) {
        return !node.getChild(this.indexPath).isEmpty();
    }

    public int compare(NamedNode namedNode, NamedNode namedNode2) {
        int compareTo = namedNode.getNode().getChild(this.indexPath).compareTo(namedNode2.getNode().getChild(this.indexPath));
        return compareTo == 0 ? namedNode.getName().compareTo(namedNode2.getName()) : compareTo;
    }

    public NamedNode makePost(ChildKey childKey, Node node) {
        return new NamedNode(childKey, EmptyNode.Empty().updateChild(this.indexPath, node));
    }

    public NamedNode maxPost() {
        return new NamedNode(ChildKey.getMaxName(), EmptyNode.Empty().updateChild(this.indexPath, Node.MAX_NODE));
    }

    public String getQueryDefinition() {
        return this.indexPath.wireFormat();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.indexPath.equals(((PathIndex) obj).indexPath);
    }

    public int hashCode() {
        return this.indexPath.hashCode();
    }
}
