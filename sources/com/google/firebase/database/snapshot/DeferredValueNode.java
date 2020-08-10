package com.google.firebase.database.snapshot;

import com.google.firebase.database.snapshot.Node.HashVersion;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class DeferredValueNode extends LeafNode<DeferredValueNode> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Map<Object, Object> value;

    /* access modifiers changed from: protected */
    public int compareLeafValues(DeferredValueNode deferredValueNode) {
        return 0;
    }

    public DeferredValueNode(Map<Object, Object> map, Node node) {
        super(node);
        this.value = map;
    }

    public Object getValue() {
        return this.value;
    }

    public String getHashRepresentation(HashVersion hashVersion) {
        StringBuilder sb = new StringBuilder();
        sb.append(getPriorityHash(hashVersion));
        sb.append("deferredValue:");
        sb.append(this.value);
        return sb.toString();
    }

    public DeferredValueNode updatePriority(Node node) {
        return new DeferredValueNode(this.value, node);
    }

    /* access modifiers changed from: protected */
    public LeafType getLeafType() {
        return LeafType.DeferredValue;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof DeferredValueNode)) {
            return false;
        }
        DeferredValueNode deferredValueNode = (DeferredValueNode) obj;
        if (this.value.equals(deferredValueNode.value) && this.priority.equals(deferredValueNode.priority)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.value.hashCode() + this.priority.hashCode();
    }
}
