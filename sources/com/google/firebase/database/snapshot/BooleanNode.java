package com.google.firebase.database.snapshot;

import com.google.firebase.database.snapshot.Node.HashVersion;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class BooleanNode extends LeafNode<BooleanNode> {
    private final boolean value;

    public BooleanNode(Boolean bool, Node node) {
        super(node);
        this.value = bool.booleanValue();
    }

    public Object getValue() {
        return Boolean.valueOf(this.value);
    }

    public String getHashRepresentation(HashVersion hashVersion) {
        StringBuilder sb = new StringBuilder();
        sb.append(getPriorityHash(hashVersion));
        sb.append("boolean:");
        sb.append(this.value);
        return sb.toString();
    }

    public BooleanNode updatePriority(Node node) {
        return new BooleanNode(Boolean.valueOf(this.value), node);
    }

    /* access modifiers changed from: protected */
    public LeafType getLeafType() {
        return LeafType.Boolean;
    }

    /* access modifiers changed from: protected */
    public int compareLeafValues(BooleanNode booleanNode) {
        boolean z = this.value;
        if (z == booleanNode.value) {
            return 0;
        }
        return z ? 1 : -1;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof BooleanNode)) {
            return false;
        }
        BooleanNode booleanNode = (BooleanNode) obj;
        if (this.value == booleanNode.value && this.priority.equals(booleanNode.priority)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        boolean z = this.value;
        return (z ? 1 : 0) + this.priority.hashCode();
    }
}
