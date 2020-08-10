package com.google.firebase.database.core;

import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class CompoundWrite implements Iterable<Entry<Path, Node>> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final CompoundWrite EMPTY = new CompoundWrite(new ImmutableTree(null));
    private final ImmutableTree<Node> writeTree;

    private CompoundWrite(ImmutableTree<Node> immutableTree) {
        this.writeTree = immutableTree;
    }

    public static CompoundWrite emptyWrite() {
        return EMPTY;
    }

    public static CompoundWrite fromValue(Map<String, Object> map) {
        ImmutableTree emptyInstance = ImmutableTree.emptyInstance();
        for (Entry entry : map.entrySet()) {
            emptyInstance = emptyInstance.setTree(new Path((String) entry.getKey()), new ImmutableTree(NodeUtilities.NodeFromJSON(entry.getValue())));
        }
        return new CompoundWrite(emptyInstance);
    }

    public static CompoundWrite fromChildMerge(Map<ChildKey, Node> map) {
        ImmutableTree emptyInstance = ImmutableTree.emptyInstance();
        for (Entry entry : map.entrySet()) {
            emptyInstance = emptyInstance.setTree(new Path((ChildKey) entry.getKey()), new ImmutableTree((Node) entry.getValue()));
        }
        return new CompoundWrite(emptyInstance);
    }

    public static CompoundWrite fromPathMerge(Map<Path, Node> map) {
        ImmutableTree emptyInstance = ImmutableTree.emptyInstance();
        for (Entry entry : map.entrySet()) {
            emptyInstance = emptyInstance.setTree((Path) entry.getKey(), new ImmutableTree((Node) entry.getValue()));
        }
        return new CompoundWrite(emptyInstance);
    }

    public CompoundWrite addWrite(Path path, Node node) {
        if (path.isEmpty()) {
            return new CompoundWrite(new ImmutableTree(node));
        }
        Path findRootMostPathWithValue = this.writeTree.findRootMostPathWithValue(path);
        if (findRootMostPathWithValue != null) {
            Path relative = Path.getRelative(findRootMostPathWithValue, path);
            Node node2 = (Node) this.writeTree.get(findRootMostPathWithValue);
            ChildKey back = relative.getBack();
            if (back != null && back.isPriorityChildName() && node2.getChild(relative.getParent()).isEmpty()) {
                return this;
            }
            return new CompoundWrite(this.writeTree.set(findRootMostPathWithValue, node2.updateChild(relative, node)));
        }
        return new CompoundWrite(this.writeTree.setTree(path, new ImmutableTree(node)));
    }

    public CompoundWrite addWrite(ChildKey childKey, Node node) {
        return addWrite(new Path(childKey), node);
    }

    public CompoundWrite addWrites(final Path path, CompoundWrite compoundWrite) {
        return (CompoundWrite) compoundWrite.writeTree.fold(this, new TreeVisitor<Node, CompoundWrite>() {
            public CompoundWrite onNodeValue(Path path, Node node, CompoundWrite compoundWrite) {
                return compoundWrite.addWrite(path.child(path), node);
            }
        });
    }

    public CompoundWrite removeWrite(Path path) {
        if (path.isEmpty()) {
            return EMPTY;
        }
        return new CompoundWrite(this.writeTree.setTree(path, ImmutableTree.emptyInstance()));
    }

    public boolean hasCompleteWrite(Path path) {
        return getCompleteNode(path) != null;
    }

    public Node rootWrite() {
        return (Node) this.writeTree.getValue();
    }

    public Node getCompleteNode(Path path) {
        Path findRootMostPathWithValue = this.writeTree.findRootMostPathWithValue(path);
        if (findRootMostPathWithValue != null) {
            return ((Node) this.writeTree.get(findRootMostPathWithValue)).getChild(Path.getRelative(findRootMostPathWithValue, path));
        }
        return null;
    }

    public List<NamedNode> getCompleteChildren() {
        ArrayList arrayList = new ArrayList();
        if (this.writeTree.getValue() != null) {
            for (NamedNode namedNode : (Node) this.writeTree.getValue()) {
                arrayList.add(new NamedNode(namedNode.getName(), namedNode.getNode()));
            }
        } else {
            Iterator it = this.writeTree.getChildren().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                ImmutableTree immutableTree = (ImmutableTree) entry.getValue();
                if (immutableTree.getValue() != null) {
                    arrayList.add(new NamedNode((ChildKey) entry.getKey(), (Node) immutableTree.getValue()));
                }
            }
        }
        return arrayList;
    }

    public CompoundWrite childCompoundWrite(Path path) {
        if (path.isEmpty()) {
            return this;
        }
        Node completeNode = getCompleteNode(path);
        if (completeNode != null) {
            return new CompoundWrite(new ImmutableTree(completeNode));
        }
        return new CompoundWrite(this.writeTree.subtree(path));
    }

    public Map<ChildKey, CompoundWrite> childCompoundWrites() {
        HashMap hashMap = new HashMap();
        Iterator it = this.writeTree.getChildren().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            hashMap.put((ChildKey) entry.getKey(), new CompoundWrite((ImmutableTree) entry.getValue()));
        }
        return hashMap;
    }

    public boolean isEmpty() {
        return this.writeTree.isEmpty();
    }

    private Node applySubtreeWrite(Path path, ImmutableTree<Node> immutableTree, Node node) {
        if (immutableTree.getValue() != null) {
            return node.updateChild(path, (Node) immutableTree.getValue());
        }
        Node node2 = null;
        Iterator it = immutableTree.getChildren().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            ImmutableTree immutableTree2 = (ImmutableTree) entry.getValue();
            ChildKey childKey = (ChildKey) entry.getKey();
            if (childKey.isPriorityChildName()) {
                node2 = (Node) immutableTree2.getValue();
            } else {
                node = applySubtreeWrite(path.child(childKey), immutableTree2, node);
            }
        }
        if (!node.getChild(path).isEmpty() && node2 != null) {
            node = node.updateChild(path.child(ChildKey.getPriorityKey()), node2);
        }
        return node;
    }

    public Node apply(Node node) {
        return applySubtreeWrite(Path.getEmptyPath(), this.writeTree, node);
    }

    public Map<String, Object> getValue(final boolean z) {
        final HashMap hashMap = new HashMap();
        this.writeTree.foreach(new TreeVisitor<Node, Void>() {
            public Void onNodeValue(Path path, Node node, Void voidR) {
                hashMap.put(path.wireFormat(), node.getValue(z));
                return null;
            }
        });
        return hashMap;
    }

    public Iterator<Entry<Path, Node>> iterator() {
        return this.writeTree.iterator();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        return ((CompoundWrite) obj).getValue(true).equals(getValue(true));
    }

    public int hashCode() {
        return getValue(true).hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CompoundWrite{");
        sb.append(getValue(true).toString());
        sb.append("}");
        return sb.toString();
    }
}
