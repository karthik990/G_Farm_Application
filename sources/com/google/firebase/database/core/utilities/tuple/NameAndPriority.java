package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class NameAndPriority implements Comparable<NameAndPriority> {
    private ChildKey name;
    private Node priority;

    public NameAndPriority(ChildKey childKey, Node node) {
        this.name = childKey;
        this.priority = node;
    }

    public ChildKey getName() {
        return this.name;
    }

    public Node getPriority() {
        return this.priority;
    }

    public int compareTo(NameAndPriority nameAndPriority) {
        return NodeUtilities.nameAndPriorityCompare(this.name, this.priority, nameAndPriority.name, nameAndPriority.priority);
    }
}
