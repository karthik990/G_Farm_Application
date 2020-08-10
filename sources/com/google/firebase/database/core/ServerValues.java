package com.google.firebase.database.core;

import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.firebase.database.core.SparseSnapshotTree.SparseSnapshotTreeVisitor;
import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.ChildrenNode.ChildVisitor;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ServerValues {
    public static final String NAME_SUBKEY_SERVERVALUE = ".sv";

    public static Map<String, Object> generateServerValues(Clock clock) {
        HashMap hashMap = new HashMap();
        hashMap.put(Param.TIMESTAMP, Long.valueOf(clock.millis()));
        return hashMap;
    }

    public static Object resolveDeferredValue(Object obj, Map<String, Object> map) {
        if (!(obj instanceof Map)) {
            return obj;
        }
        Map map2 = (Map) obj;
        String str = NAME_SUBKEY_SERVERVALUE;
        if (!map2.containsKey(str)) {
            return obj;
        }
        String str2 = (String) map2.get(str);
        return map.containsKey(str2) ? map.get(str2) : obj;
    }

    public static SparseSnapshotTree resolveDeferredValueTree(SparseSnapshotTree sparseSnapshotTree, final Map<String, Object> map) {
        final SparseSnapshotTree sparseSnapshotTree2 = new SparseSnapshotTree();
        sparseSnapshotTree.forEachTree(new Path(""), new SparseSnapshotTreeVisitor() {
            public void visitTree(Path path, Node node) {
                SparseSnapshotTree.this.remember(path, ServerValues.resolveDeferredValueSnapshot(node, map));
            }
        });
        return sparseSnapshotTree2;
    }

    public static Node resolveDeferredValueSnapshot(Node node, final Map<String, Object> map) {
        Object value = node.getPriority().getValue();
        if (value instanceof Map) {
            Map map2 = (Map) value;
            String str = NAME_SUBKEY_SERVERVALUE;
            if (map2.containsKey(str)) {
                value = map.get((String) map2.get(str));
            }
        }
        Node parsePriority = PriorityUtilities.parsePriority(value);
        if (node.isLeafNode()) {
            Object resolveDeferredValue = resolveDeferredValue(node.getValue(), map);
            if (!resolveDeferredValue.equals(node.getValue()) || !parsePriority.equals(node.getPriority())) {
                return NodeUtilities.NodeFromJSON(resolveDeferredValue, parsePriority);
            }
            return node;
        } else if (node.isEmpty()) {
            return node;
        } else {
            ChildrenNode childrenNode = (ChildrenNode) node;
            final SnapshotHolder snapshotHolder = new SnapshotHolder(childrenNode);
            childrenNode.forEachChild(new ChildVisitor() {
                public void visitChild(ChildKey childKey, Node node) {
                    Node resolveDeferredValueSnapshot = ServerValues.resolveDeferredValueSnapshot(node, map);
                    if (resolveDeferredValueSnapshot != node) {
                        snapshotHolder.update(new Path(childKey.asString()), resolveDeferredValueSnapshot);
                    }
                }
            });
            if (!snapshotHolder.getRootNode().getPriority().equals(parsePriority)) {
                return snapshotHolder.getRootNode().updatePriority(parsePriority);
            }
            return snapshotHolder.getRootNode();
        }
    }

    public static CompoundWrite resolveDeferredValueMerge(CompoundWrite compoundWrite, Map<String, Object> map) {
        CompoundWrite emptyWrite = CompoundWrite.emptyWrite();
        Iterator it = compoundWrite.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            emptyWrite = emptyWrite.addWrite((Path) entry.getKey(), resolveDeferredValueSnapshot((Node) entry.getValue(), map));
        }
        return emptyWrite;
    }
}
