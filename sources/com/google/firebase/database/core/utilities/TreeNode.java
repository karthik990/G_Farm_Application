package com.google.firebase.database.core.utilities;

import com.google.firebase.database.snapshot.ChildKey;
import com.mobiroller.constants.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class TreeNode<T> {
    public Map<ChildKey, TreeNode<T>> children = new HashMap();
    public T value;

    /* access modifiers changed from: 0000 */
    public String toString(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("<value>: ");
        sb.append(this.value);
        String str2 = Constants.NEW_LINE;
        sb.append(str2);
        String sb2 = sb.toString();
        if (this.children.isEmpty()) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(str);
            sb3.append("<empty>");
            return sb3.toString();
        }
        for (Entry entry : this.children.entrySet()) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb2);
            sb4.append(str);
            sb4.append(entry.getKey());
            sb4.append(":\n");
            TreeNode treeNode = (TreeNode) entry.getValue();
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str);
            sb5.append("\t");
            sb4.append(treeNode.toString(sb5.toString()));
            sb4.append(str2);
            sb2 = sb4.toString();
        }
        return sb2;
    }
}
