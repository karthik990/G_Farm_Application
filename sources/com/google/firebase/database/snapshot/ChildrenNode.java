package com.google.firebase.database.snapshot;

import com.google.firebase.database.collection.ImmutableSortedMap;
import com.google.firebase.database.collection.ImmutableSortedMap.Builder;
import com.google.firebase.database.collection.LLRBNode.NodeVisitor;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.snapshot.Node.HashVersion;
import com.mobiroller.constants.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ChildrenNode implements Node {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static Comparator<ChildKey> NAME_ONLY_COMPARATOR = new Comparator<ChildKey>() {
        public int compare(ChildKey childKey, ChildKey childKey2) {
            return childKey.compareTo(childKey2);
        }
    };
    private final ImmutableSortedMap<ChildKey, Node> children;
    private String lazyHash;
    private final Node priority;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public static abstract class ChildVisitor extends NodeVisitor<ChildKey, Node> {
        public abstract void visitChild(ChildKey childKey, Node node);

        public void visitEntry(ChildKey childKey, Node node) {
            visitChild(childKey, node);
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class NamedNodeIterator implements Iterator<NamedNode> {
        private final Iterator<Entry<ChildKey, Node>> iterator;

        public NamedNodeIterator(Iterator<Entry<ChildKey, Node>> it) {
            this.iterator = it;
        }

        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        public NamedNode next() {
            Entry entry = (Entry) this.iterator.next();
            return new NamedNode((ChildKey) entry.getKey(), (Node) entry.getValue());
        }

        public void remove() {
            this.iterator.remove();
        }
    }

    public boolean isLeafNode() {
        return false;
    }

    protected ChildrenNode() {
        this.lazyHash = null;
        this.children = Builder.emptyMap(NAME_ONLY_COMPARATOR);
        this.priority = PriorityUtilities.NullPriority();
    }

    protected ChildrenNode(ImmutableSortedMap<ChildKey, Node> immutableSortedMap, Node node) {
        this.lazyHash = null;
        if (!immutableSortedMap.isEmpty() || node.isEmpty()) {
            this.priority = node;
            this.children = immutableSortedMap;
            return;
        }
        throw new IllegalArgumentException("Can't create empty ChildrenNode with priority!");
    }

    public boolean hasChild(ChildKey childKey) {
        return !getImmediateChild(childKey).isEmpty();
    }

    public boolean isEmpty() {
        return this.children.isEmpty();
    }

    public int getChildCount() {
        return this.children.size();
    }

    public Object getValue() {
        return getValue(false);
    }

    public Object getValue(boolean z) {
        if (isEmpty()) {
            return null;
        }
        HashMap hashMap = new HashMap();
        Iterator it = this.children.iterator();
        int i = 0;
        boolean z2 = true;
        int i2 = 0;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            String asString = ((ChildKey) entry.getKey()).asString();
            hashMap.put(asString, ((Node) entry.getValue()).getValue(z));
            i++;
            if (z2) {
                if (asString.length() <= 1 || asString.charAt(0) != '0') {
                    Integer tryParseInt = Utilities.tryParseInt(asString);
                    if (tryParseInt != null && tryParseInt.intValue() >= 0) {
                        if (tryParseInt.intValue() > i2) {
                            i2 = tryParseInt.intValue();
                        }
                    }
                }
                z2 = false;
            }
        }
        if (z || !z2 || i2 >= i * 2) {
            if (z && !this.priority.isEmpty()) {
                hashMap.put(".priority", this.priority.getValue());
            }
            return hashMap;
        }
        ArrayList arrayList = new ArrayList(i2 + 1);
        for (int i3 = 0; i3 <= i2; i3++) {
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(i3);
            arrayList.add(hashMap.get(sb.toString()));
        }
        return arrayList;
    }

    public ChildKey getPredecessorChildKey(ChildKey childKey) {
        return (ChildKey) this.children.getPredecessorKey(childKey);
    }

    public ChildKey getSuccessorChildKey(ChildKey childKey) {
        return (ChildKey) this.children.getSuccessorKey(childKey);
    }

    public String getHashRepresentation(HashVersion hashVersion) {
        boolean z;
        if (hashVersion == HashVersion.V1) {
            StringBuilder sb = new StringBuilder();
            String str = ":";
            if (!this.priority.isEmpty()) {
                sb.append("priority:");
                sb.append(this.priority.getHashRepresentation(HashVersion.V1));
                sb.append(str);
            }
            ArrayList<NamedNode> arrayList = new ArrayList<>();
            Iterator it = iterator();
            loop0:
            while (true) {
                z = false;
                while (true) {
                    if (!it.hasNext()) {
                        break loop0;
                    }
                    NamedNode namedNode = (NamedNode) it.next();
                    arrayList.add(namedNode);
                    if (z || !namedNode.getNode().getPriority().isEmpty()) {
                        z = true;
                    }
                }
            }
            if (z) {
                Collections.sort(arrayList, PriorityIndex.getInstance());
            }
            for (NamedNode namedNode2 : arrayList) {
                String hash = namedNode2.getNode().getHash();
                if (!hash.equals("")) {
                    sb.append(str);
                    sb.append(namedNode2.getName().asString());
                    sb.append(str);
                    sb.append(hash);
                }
            }
            return sb.toString();
        }
        throw new IllegalArgumentException("Hashes on children nodes only supported for V1");
    }

    public String getHash() {
        if (this.lazyHash == null) {
            String hashRepresentation = getHashRepresentation(HashVersion.V1);
            this.lazyHash = hashRepresentation.isEmpty() ? "" : Utilities.sha1HexDigest(hashRepresentation);
        }
        return this.lazyHash;
    }

    public Node getPriority() {
        return this.priority;
    }

    public Node updatePriority(Node node) {
        if (this.children.isEmpty()) {
            return EmptyNode.Empty();
        }
        return new ChildrenNode(this.children, node);
    }

    public Node getImmediateChild(ChildKey childKey) {
        if (childKey.isPriorityChildName() && !this.priority.isEmpty()) {
            return this.priority;
        }
        if (this.children.containsKey(childKey)) {
            return (Node) this.children.get(childKey);
        }
        return EmptyNode.Empty();
    }

    public Node getChild(Path path) {
        ChildKey front = path.getFront();
        if (front == null) {
            return this;
        }
        return getImmediateChild(front).getChild(path.popFront());
    }

    public void forEachChild(ChildVisitor childVisitor) {
        forEachChild(childVisitor, false);
    }

    public void forEachChild(final ChildVisitor childVisitor, boolean z) {
        if (!z || getPriority().isEmpty()) {
            this.children.inOrderTraversal(childVisitor);
        } else {
            this.children.inOrderTraversal(new NodeVisitor<ChildKey, Node>() {
                boolean passedPriorityKey = false;

                public void visitEntry(ChildKey childKey, Node node) {
                    if (!this.passedPriorityKey && childKey.compareTo(ChildKey.getPriorityKey()) > 0) {
                        this.passedPriorityKey = true;
                        childVisitor.visitChild(ChildKey.getPriorityKey(), ChildrenNode.this.getPriority());
                    }
                    childVisitor.visitChild(childKey, node);
                }
            });
        }
    }

    public ChildKey getFirstChildKey() {
        return (ChildKey) this.children.getMinKey();
    }

    public ChildKey getLastChildKey() {
        return (ChildKey) this.children.getMaxKey();
    }

    public Node updateChild(Path path, Node node) {
        ChildKey front = path.getFront();
        if (front == null) {
            return node;
        }
        if (front.isPriorityChildName()) {
            return updatePriority(node);
        }
        return updateImmediateChild(front, getImmediateChild(front).updateChild(path.popFront(), node));
    }

    public Iterator<NamedNode> iterator() {
        return new NamedNodeIterator(this.children.iterator());
    }

    public Iterator<NamedNode> reverseIterator() {
        return new NamedNodeIterator(this.children.reverseIterator());
    }

    public Node updateImmediateChild(ChildKey childKey, Node node) {
        if (childKey.isPriorityChildName()) {
            return updatePriority(node);
        }
        ImmutableSortedMap<ChildKey, Node> immutableSortedMap = this.children;
        if (immutableSortedMap.containsKey(childKey)) {
            immutableSortedMap = immutableSortedMap.remove(childKey);
        }
        if (!node.isEmpty()) {
            immutableSortedMap = immutableSortedMap.insert(childKey, node);
        }
        if (immutableSortedMap.isEmpty()) {
            return EmptyNode.Empty();
        }
        return new ChildrenNode(immutableSortedMap, this.priority);
    }

    public int compareTo(Node node) {
        if (isEmpty()) {
            return node.isEmpty() ? 0 : -1;
        }
        if (!node.isLeafNode() && !node.isEmpty()) {
            return node == Node.MAX_NODE ? -1 : 0;
        }
        return 1;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ChildrenNode)) {
            return false;
        }
        ChildrenNode childrenNode = (ChildrenNode) obj;
        if (!getPriority().equals(childrenNode.getPriority()) || this.children.size() != childrenNode.children.size()) {
            return false;
        }
        Iterator it = this.children.iterator();
        Iterator it2 = childrenNode.children.iterator();
        while (it.hasNext() && it2.hasNext()) {
            Entry entry = (Entry) it.next();
            Entry entry2 = (Entry) it2.next();
            if (((ChildKey) entry.getKey()).equals(entry2.getKey())) {
                if (!((Node) entry.getValue()).equals(entry2.getValue())) {
                }
            }
            return false;
        }
        if (!it.hasNext() && !it2.hasNext()) {
            return true;
        }
        throw new IllegalStateException("Something went wrong internally.");
    }

    public int hashCode() {
        Iterator it = iterator();
        int i = 0;
        while (it.hasNext()) {
            NamedNode namedNode = (NamedNode) it.next();
            i = (((i * 31) + namedNode.getName().hashCode()) * 17) + namedNode.getNode().hashCode();
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb, 0);
        return sb.toString();
    }

    private static void addIndentation(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(" ");
        }
    }

    private void toString(StringBuilder sb, int i) {
        String str;
        if (!this.children.isEmpty() || !this.priority.isEmpty()) {
            sb.append("{\n");
            Iterator it = this.children.iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                str = Constants.NEW_LINE;
                if (!hasNext) {
                    break;
                }
                Entry entry = (Entry) it.next();
                int i2 = i + 2;
                addIndentation(sb, i2);
                sb.append(((ChildKey) entry.getKey()).asString());
                sb.append("=");
                if (entry.getValue() instanceof ChildrenNode) {
                    ((ChildrenNode) entry.getValue()).toString(sb, i2);
                } else {
                    sb.append(((Node) entry.getValue()).toString());
                }
                sb.append(str);
            }
            if (!this.priority.isEmpty()) {
                addIndentation(sb, i + 2);
                sb.append(".priority=");
                sb.append(this.priority.toString());
                sb.append(str);
            }
            addIndentation(sb, i);
            sb.append("}");
            return;
        }
        sb.append("{ }");
    }
}
