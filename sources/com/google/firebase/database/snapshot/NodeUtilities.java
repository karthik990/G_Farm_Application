package com.google.firebase.database.snapshot;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.core.ServerValues;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class NodeUtilities {
    public static Node NodeFromJSON(Object obj) throws DatabaseException {
        return NodeFromJSON(obj, PriorityUtilities.NullPriority());
    }

    public static Node NodeFromJSON(Object obj, Node node) throws DatabaseException {
        HashMap hashMap;
        String str = ".value";
        String str2 = ".priority";
        try {
            if (obj instanceof Map) {
                Map map = (Map) obj;
                if (map.containsKey(str2)) {
                    node = PriorityUtilities.parsePriority(map.get(str2));
                }
                if (map.containsKey(str)) {
                    obj = map.get(str);
                }
            }
            if (obj == null) {
                return EmptyNode.Empty();
            }
            if (obj instanceof String) {
                return new StringNode((String) obj, node);
            }
            if (obj instanceof Long) {
                return new LongNode((Long) obj, node);
            }
            if (obj instanceof Integer) {
                return new LongNode(Long.valueOf((long) ((Integer) obj).intValue()), node);
            }
            if (obj instanceof Double) {
                return new DoubleNode((Double) obj, node);
            }
            if (obj instanceof Boolean) {
                return new BooleanNode((Boolean) obj, node);
            }
            if (!(obj instanceof Map)) {
                if (!(obj instanceof List)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Failed to parse node with class ");
                    sb.append(obj.getClass().toString());
                    throw new DatabaseException(sb.toString());
                }
            }
            if (obj instanceof Map) {
                Map map2 = (Map) obj;
                if (map2.containsKey(ServerValues.NAME_SUBKEY_SERVERVALUE)) {
                    return new DeferredValueNode(map2, node);
                }
                hashMap = new HashMap(map2.size());
                for (String str3 : map2.keySet()) {
                    if (!str3.startsWith(".")) {
                        Node NodeFromJSON = NodeFromJSON(map2.get(str3));
                        if (!NodeFromJSON.isEmpty()) {
                            hashMap.put(ChildKey.fromString(str3), NodeFromJSON);
                        }
                    }
                }
            } else {
                List list = (List) obj;
                hashMap = new HashMap(list.size());
                for (int i = 0; i < list.size(); i++) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("");
                    sb2.append(i);
                    String sb3 = sb2.toString();
                    Node NodeFromJSON2 = NodeFromJSON(list.get(i));
                    if (!NodeFromJSON2.isEmpty()) {
                        hashMap.put(ChildKey.fromString(sb3), NodeFromJSON2);
                    }
                }
            }
            if (hashMap.isEmpty()) {
                return EmptyNode.Empty();
            }
            return new ChildrenNode(Builder.fromMap(hashMap, ChildrenNode.NAME_ONLY_COMPARATOR), node);
        } catch (ClassCastException e) {
            throw new DatabaseException("Failed to parse node", e);
        }
    }

    public static int nameAndPriorityCompare(ChildKey childKey, Node node, ChildKey childKey2, Node node2) {
        int compareTo = node.compareTo(node2);
        if (compareTo != 0) {
            return compareTo;
        }
        return childKey.compareTo(childKey2);
    }
}
