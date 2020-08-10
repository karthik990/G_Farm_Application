package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.Node.HashVersion;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public abstract class LeafNode<T extends LeafNode> implements Node {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private String lazyHash;
    protected final Node priority;

    /* renamed from: com.google.firebase.database.snapshot.LeafNode$1 */
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static /* synthetic */ class C36271 {

        /* renamed from: $SwitchMap$com$google$firebase$database$snapshot$Node$HashVersion */
        static final /* synthetic */ int[] f2014x2aed15f4 = new int[HashVersion.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                com.google.firebase.database.snapshot.Node$HashVersion[] r0 = com.google.firebase.database.snapshot.Node.HashVersion.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2014x2aed15f4 = r0
                int[] r0 = f2014x2aed15f4     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.database.snapshot.Node$HashVersion r1 = com.google.firebase.database.snapshot.Node.HashVersion.V1     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2014x2aed15f4     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.database.snapshot.Node$HashVersion r1 = com.google.firebase.database.snapshot.Node.HashVersion.V2     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.snapshot.LeafNode.C36271.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    protected enum LeafType {
        DeferredValue,
        Boolean,
        Number,
        String
    }

    /* access modifiers changed from: protected */
    public abstract int compareLeafValues(T t);

    public abstract boolean equals(Object obj);

    public int getChildCount() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public abstract LeafType getLeafType();

    public ChildKey getPredecessorChildKey(ChildKey childKey) {
        return null;
    }

    public ChildKey getSuccessorChildKey(ChildKey childKey) {
        return null;
    }

    public boolean hasChild(ChildKey childKey) {
        return false;
    }

    public abstract int hashCode();

    public boolean isEmpty() {
        return false;
    }

    public boolean isLeafNode() {
        return true;
    }

    LeafNode(Node node) {
        this.priority = node;
    }

    public Node getPriority() {
        return this.priority;
    }

    public Node getChild(Path path) {
        if (path.isEmpty()) {
            return this;
        }
        if (path.getFront().isPriorityChildName()) {
            return this.priority;
        }
        return EmptyNode.Empty();
    }

    public Node updateChild(Path path, Node node) {
        ChildKey front = path.getFront();
        if (front == null) {
            return node;
        }
        if (!node.isEmpty() || front.isPriorityChildName()) {
            return updateImmediateChild(front, EmptyNode.Empty().updateChild(path.popFront(), node));
        }
        return this;
    }

    public Node getImmediateChild(ChildKey childKey) {
        if (childKey.isPriorityChildName()) {
            return this.priority;
        }
        return EmptyNode.Empty();
    }

    public Object getValue(boolean z) {
        if (!z || this.priority.isEmpty()) {
            return getValue();
        }
        HashMap hashMap = new HashMap();
        hashMap.put(".value", getValue());
        hashMap.put(".priority", this.priority.getValue());
        return hashMap;
    }

    public Node updateImmediateChild(ChildKey childKey, Node node) {
        if (childKey.isPriorityChildName()) {
            return updatePriority(node);
        }
        if (node.isEmpty()) {
            return this;
        }
        return EmptyNode.Empty().updateImmediateChild(childKey, node).updatePriority(this.priority);
    }

    public String getHash() {
        if (this.lazyHash == null) {
            this.lazyHash = Utilities.sha1HexDigest(getHashRepresentation(HashVersion.V1));
        }
        return this.lazyHash;
    }

    /* access modifiers changed from: protected */
    public String getPriorityHash(HashVersion hashVersion) {
        int i = C36271.f2014x2aed15f4[hashVersion.ordinal()];
        if (i != 1 && i != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown hash version: ");
            sb.append(hashVersion);
            throw new IllegalArgumentException(sb.toString());
        } else if (this.priority.isEmpty()) {
            return "";
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("priority:");
            sb2.append(this.priority.getHashRepresentation(hashVersion));
            sb2.append(":");
            return sb2.toString();
        }
    }

    public Iterator<NamedNode> iterator() {
        return Collections.emptyList().iterator();
    }

    public Iterator<NamedNode> reverseIterator() {
        return Collections.emptyList().iterator();
    }

    private static int compareLongDoubleNodes(LongNode longNode, DoubleNode doubleNode) {
        return Double.valueOf((double) ((Long) longNode.getValue()).longValue()).compareTo((Double) doubleNode.getValue());
    }

    public int compareTo(Node node) {
        if (node.isEmpty()) {
            return 1;
        }
        if (node instanceof ChildrenNode) {
            return -1;
        }
        if ((this instanceof LongNode) && (node instanceof DoubleNode)) {
            return compareLongDoubleNodes((LongNode) this, (DoubleNode) node);
        }
        if (!(this instanceof DoubleNode) || !(node instanceof LongNode)) {
            return leafCompare((LeafNode) node);
        }
        return compareLongDoubleNodes((LongNode) node, (DoubleNode) this) * -1;
    }

    /* access modifiers changed from: protected */
    public int leafCompare(LeafNode<?> leafNode) {
        LeafType leafType = getLeafType();
        LeafType leafType2 = leafNode.getLeafType();
        if (leafType.equals(leafType2)) {
            return compareLeafValues(leafNode);
        }
        return leafType.compareTo(leafType2);
    }

    public String toString() {
        String obj = getValue(true).toString();
        if (obj.length() <= 100) {
            return obj;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(obj.substring(0, 100));
        sb.append("...");
        return sb.toString();
    }
}
