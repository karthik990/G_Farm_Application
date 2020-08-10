package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.Node.HashVersion;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DoubleNode extends LeafNode<DoubleNode> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Double value;

    public DoubleNode(Double d, Node node) {
        super(node);
        this.value = d;
    }

    public Object getValue() {
        return this.value;
    }

    public String getHashRepresentation(HashVersion hashVersion) {
        String priorityHash = getPriorityHash(hashVersion);
        StringBuilder sb = new StringBuilder();
        sb.append(priorityHash);
        sb.append("number:");
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append(Utilities.doubleToHashString(this.value.doubleValue()));
        return sb3.toString();
    }

    public DoubleNode updatePriority(Node node) {
        return new DoubleNode(this.value, node);
    }

    /* access modifiers changed from: protected */
    public LeafType getLeafType() {
        return LeafType.Number;
    }

    /* access modifiers changed from: protected */
    public int compareLeafValues(DoubleNode doubleNode) {
        return this.value.compareTo(doubleNode.value);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DoubleNode)) {
            return false;
        }
        DoubleNode doubleNode = (DoubleNode) obj;
        if (this.value.equals(doubleNode.value) && this.priority.equals(doubleNode.priority)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
