package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.Comparator;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public abstract class Index implements Comparator<NamedNode> {
    public abstract String getQueryDefinition();

    public abstract boolean isDefinedOn(Node node);

    public abstract NamedNode makePost(ChildKey childKey, Node node);

    public abstract NamedNode maxPost();

    public boolean indexedValueChanged(Node node, Node node2) {
        return compare(new NamedNode(ChildKey.getMinName(), node), new NamedNode(ChildKey.getMinName(), node2)) != 0;
    }

    public NamedNode minPost() {
        return NamedNode.getMinNode();
    }

    public static Index fromQueryDefinition(String str) {
        if (str.equals(".value")) {
            return ValueIndex.getInstance();
        }
        if (str.equals(".key")) {
            return KeyIndex.getInstance();
        }
        if (!str.equals(".priority")) {
            return new PathIndex(new Path(str));
        }
        throw new IllegalStateException("queryDefinition shouldn't ever be .priority since it's the default");
    }

    public int compare(NamedNode namedNode, NamedNode namedNode2, boolean z) {
        if (z) {
            return compare(namedNode2, namedNode);
        }
        return compare(namedNode, namedNode2);
    }
}
