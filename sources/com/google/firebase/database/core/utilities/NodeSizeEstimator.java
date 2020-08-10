package com.google.firebase.database.core.utilities;

import com.google.firebase.database.snapshot.BooleanNode;
import com.google.firebase.database.snapshot.DoubleNode;
import com.google.firebase.database.snapshot.LeafNode;
import com.google.firebase.database.snapshot.LongNode;
import com.google.firebase.database.snapshot.StringNode;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class NodeSizeEstimator {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int LEAF_PRIORITY_OVERHEAD = 24;

    private static long estimateLeafNodeSize(LeafNode<?> leafNode) {
        long j = 8;
        if (!(leafNode instanceof DoubleNode) && !(leafNode instanceof LongNode)) {
            if (leafNode instanceof BooleanNode) {
                j = 4;
            } else if (leafNode instanceof StringNode) {
                j = 2 + ((long) ((String) leafNode.getValue()).length());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown leaf node type: ");
                sb.append(leafNode.getClass());
                throw new IllegalArgumentException(sb.toString());
            }
        }
        if (leafNode.getPriority().isEmpty()) {
            return j;
        }
        return j + 24 + estimateLeafNodeSize((LeafNode) leafNode.getPriority());
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.firebase.database.snapshot.Node, code=com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, for r8v0, types: [com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, com.google.firebase.database.snapshot.Node] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long estimateSerializedNodeSize(com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode> r8) {
        /*
            boolean r0 = r8.isEmpty()
            r1 = 4
            if (r0 == 0) goto L_0x0009
            return r1
        L_0x0009:
            boolean r0 = r8.isLeafNode()
            if (r0 == 0) goto L_0x0016
            com.google.firebase.database.snapshot.LeafNode r8 = (com.google.firebase.database.snapshot.LeafNode) r8
            long r0 = estimateLeafNodeSize(r8)
            return r0
        L_0x0016:
            r3 = 1
            java.util.Iterator r0 = r8.iterator()
        L_0x001c:
            boolean r5 = r0.hasNext()
            if (r5 == 0) goto L_0x0041
            java.lang.Object r5 = r0.next()
            com.google.firebase.database.snapshot.NamedNode r5 = (com.google.firebase.database.snapshot.NamedNode) r5
            com.google.firebase.database.snapshot.ChildKey r6 = r5.getName()
            java.lang.String r6 = r6.asString()
            int r6 = r6.length()
            long r6 = (long) r6
            long r3 = r3 + r6
            long r3 = r3 + r1
            com.google.firebase.database.snapshot.Node r5 = r5.getNode()
            long r5 = estimateSerializedNodeSize(r5)
            long r3 = r3 + r5
            goto L_0x001c
        L_0x0041:
            com.google.firebase.database.snapshot.Node r0 = r8.getPriority()
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0059
            r0 = 12
            long r3 = r3 + r0
            com.google.firebase.database.snapshot.Node r8 = r8.getPriority()
            com.google.firebase.database.snapshot.LeafNode r8 = (com.google.firebase.database.snapshot.LeafNode) r8
            long r0 = estimateLeafNodeSize(r8)
            long r3 = r3 + r0
        L_0x0059:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.utilities.NodeSizeEstimator.estimateSerializedNodeSize(com.google.firebase.database.snapshot.Node):long");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.firebase.database.snapshot.Node, code=com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, for r2v0, types: [com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, com.google.firebase.database.snapshot.Node] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int nodeCount(com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode> r2) {
        /*
            boolean r0 = r2.isEmpty()
            r1 = 0
            if (r0 == 0) goto L_0x0008
            return r1
        L_0x0008:
            boolean r0 = r2.isLeafNode()
            if (r0 == 0) goto L_0x0010
            r2 = 1
            return r2
        L_0x0010:
            java.util.Iterator r2 = r2.iterator()
        L_0x0014:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x002a
            java.lang.Object r0 = r2.next()
            com.google.firebase.database.snapshot.NamedNode r0 = (com.google.firebase.database.snapshot.NamedNode) r0
            com.google.firebase.database.snapshot.Node r0 = r0.getNode()
            int r0 = nodeCount(r0)
            int r1 = r1 + r0
            goto L_0x0014
        L_0x002a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.utilities.NodeSizeEstimator.nodeCount(com.google.firebase.database.snapshot.Node):int");
    }
}
