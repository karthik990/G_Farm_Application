package com.google.firebase.database.snapshot;

import com.google.firebase.database.core.Path;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class RangeMerge {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final Path optExclusiveStart;
    private final Path optInclusiveEnd;
    private final Node snap;

    public RangeMerge(Path path, Path path2, Node node) {
        this.optExclusiveStart = path;
        this.optInclusiveEnd = path2;
        this.snap = node;
    }

    public RangeMerge(com.google.firebase.database.connection.RangeMerge rangeMerge) {
        List optExclusiveStart2 = rangeMerge.getOptExclusiveStart();
        Path path = null;
        this.optExclusiveStart = optExclusiveStart2 != null ? new Path(optExclusiveStart2) : null;
        List optInclusiveEnd2 = rangeMerge.getOptInclusiveEnd();
        if (optInclusiveEnd2 != null) {
            path = new Path(optInclusiveEnd2);
        }
        this.optInclusiveEnd = path;
        this.snap = NodeUtilities.NodeFromJSON(rangeMerge.getSnap());
    }

    public Node applyTo(Node node) {
        return updateRangeInNode(Path.getEmptyPath(), node, this.snap);
    }

    /* access modifiers changed from: 0000 */
    public Path getStart() {
        return this.optExclusiveStart;
    }

    /* access modifiers changed from: 0000 */
    public Path getEnd() {
        return this.optInclusiveEnd;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.firebase.database.snapshot.Node, code=com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, for r10v0, types: [com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, com.google.firebase.database.snapshot.Node] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.firebase.database.snapshot.Node, code=com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, for r9v0, types: [com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, com.google.firebase.database.snapshot.Node] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.firebase.database.snapshot.Node updateRangeInNode(com.google.firebase.database.core.Path r8, com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode> r9, com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode> r10) {
        /*
            r7 = this;
            com.google.firebase.database.core.Path r0 = r7.optExclusiveStart
            r1 = 1
            if (r0 != 0) goto L_0x0007
            r0 = 1
            goto L_0x000b
        L_0x0007:
            int r0 = r8.compareTo(r0)
        L_0x000b:
            com.google.firebase.database.core.Path r2 = r7.optInclusiveEnd
            if (r2 != 0) goto L_0x0011
            r2 = -1
            goto L_0x0015
        L_0x0011:
            int r2 = r8.compareTo(r2)
        L_0x0015:
            com.google.firebase.database.core.Path r3 = r7.optExclusiveStart
            r4 = 0
            if (r3 == 0) goto L_0x0022
            boolean r3 = r8.contains(r3)
            if (r3 == 0) goto L_0x0022
            r3 = 1
            goto L_0x0023
        L_0x0022:
            r3 = 0
        L_0x0023:
            com.google.firebase.database.core.Path r5 = r7.optInclusiveEnd
            if (r5 == 0) goto L_0x002e
            boolean r5 = r8.contains(r5)
            if (r5 == 0) goto L_0x002e
            r4 = 1
        L_0x002e:
            if (r0 <= 0) goto L_0x0035
            if (r2 >= 0) goto L_0x0035
            if (r4 != 0) goto L_0x0035
            return r10
        L_0x0035:
            if (r0 <= 0) goto L_0x0040
            if (r4 == 0) goto L_0x0040
            boolean r5 = r10.isLeafNode()
            if (r5 == 0) goto L_0x0040
            return r10
        L_0x0040:
            if (r0 <= 0) goto L_0x0050
            if (r2 != 0) goto L_0x0050
            boolean r8 = r9.isLeafNode()
            if (r8 == 0) goto L_0x004f
            com.google.firebase.database.snapshot.EmptyNode r8 = com.google.firebase.database.snapshot.EmptyNode.Empty()
            return r8
        L_0x004f:
            return r9
        L_0x0050:
            if (r3 != 0) goto L_0x0056
            if (r4 == 0) goto L_0x0055
            goto L_0x0056
        L_0x0055:
            return r9
        L_0x0056:
            java.util.HashSet r0 = new java.util.HashSet
            r0.<init>()
            java.util.Iterator r2 = r9.iterator()
        L_0x005f:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0073
            java.lang.Object r3 = r2.next()
            com.google.firebase.database.snapshot.NamedNode r3 = (com.google.firebase.database.snapshot.NamedNode) r3
            com.google.firebase.database.snapshot.ChildKey r3 = r3.getName()
            r0.add(r3)
            goto L_0x005f
        L_0x0073:
            java.util.Iterator r2 = r10.iterator()
        L_0x0077:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x008b
            java.lang.Object r3 = r2.next()
            com.google.firebase.database.snapshot.NamedNode r3 = (com.google.firebase.database.snapshot.NamedNode) r3
            com.google.firebase.database.snapshot.ChildKey r3 = r3.getName()
            r0.add(r3)
            goto L_0x0077
        L_0x008b:
            java.util.ArrayList r2 = new java.util.ArrayList
            int r3 = r0.size()
            int r3 = r3 + r1
            r2.<init>(r3)
            r2.addAll(r0)
            com.google.firebase.database.snapshot.Node r0 = r10.getPriority()
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x00ac
            com.google.firebase.database.snapshot.Node r0 = r9.getPriority()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x00b3
        L_0x00ac:
            com.google.firebase.database.snapshot.ChildKey r0 = com.google.firebase.database.snapshot.ChildKey.getPriorityKey()
            r2.add(r0)
        L_0x00b3:
            java.util.Iterator r0 = r2.iterator()
            r1 = r9
        L_0x00b8:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x00df
            java.lang.Object r2 = r0.next()
            com.google.firebase.database.snapshot.ChildKey r2 = (com.google.firebase.database.snapshot.ChildKey) r2
            com.google.firebase.database.snapshot.Node r3 = r9.getImmediateChild(r2)
            com.google.firebase.database.core.Path r4 = r8.child(r2)
            com.google.firebase.database.snapshot.Node r5 = r9.getImmediateChild(r2)
            com.google.firebase.database.snapshot.Node r6 = r10.getImmediateChild(r2)
            com.google.firebase.database.snapshot.Node r4 = r7.updateRangeInNode(r4, r5, r6)
            if (r4 == r3) goto L_0x00b8
            com.google.firebase.database.snapshot.Node r1 = r1.updateImmediateChild(r2, r4)
            goto L_0x00b8
        L_0x00df:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.snapshot.RangeMerge.updateRangeInNode(com.google.firebase.database.core.Path, com.google.firebase.database.snapshot.Node, com.google.firebase.database.snapshot.Node):com.google.firebase.database.snapshot.Node");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RangeMerge{optExclusiveStart=");
        sb.append(this.optExclusiveStart);
        sb.append(", optInclusiveEnd=");
        sb.append(this.optInclusiveEnd);
        sb.append(", snap=");
        sb.append(this.snap);
        sb.append('}');
        return sb.toString();
    }
}
