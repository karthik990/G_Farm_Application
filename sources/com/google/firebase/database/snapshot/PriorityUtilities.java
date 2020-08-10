package com.google.firebase.database.snapshot;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.core.Path;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class PriorityUtilities {
    public static Node NullPriority() {
        return EmptyNode.Empty();
    }

    public static boolean isValidPriority(Node node) {
        return node.getPriority().isEmpty() && (node.isEmpty() || (node instanceof DoubleNode) || (node instanceof StringNode) || (node instanceof DeferredValueNode));
    }

    public static Node parsePriority(Object obj) {
        return parsePriority(null, obj);
    }

    public static Node parsePriority(Path path, Object obj) {
        String str;
        Node NodeFromJSON = NodeUtilities.NodeFromJSON(obj);
        if (NodeFromJSON instanceof LongNode) {
            NodeFromJSON = new DoubleNode(Double.valueOf((double) ((Long) NodeFromJSON.getValue()).longValue()), NullPriority());
        }
        if (isValidPriority(NodeFromJSON)) {
            return NodeFromJSON;
        }
        StringBuilder sb = new StringBuilder();
        if (path != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Path '");
            sb2.append(path);
            sb2.append("'");
            str = sb2.toString();
        } else {
            str = "Node";
        }
        sb.append(str);
        sb.append(" contains invalid priority: Must be a string, double, ServerValue, or null");
        throw new DatabaseException(sb.toString());
    }
}
