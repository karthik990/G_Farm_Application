package com.google.firebase.database.core.utilities;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.snapshot.ChildKey;
import com.mobiroller.constants.Constants;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Tree<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ChildKey name;
    private TreeNode<T> node;
    private Tree<T> parent;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface TreeFilter<T> {
        boolean filterTreeNode(Tree<T> tree);
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public interface TreeVisitor<T> {
        void visitTree(Tree<T> tree);
    }

    public Tree(ChildKey childKey, Tree<T> tree, TreeNode<T> treeNode) {
        this.name = childKey;
        this.parent = tree;
        this.node = treeNode;
    }

    public Tree() {
        this(null, null, new TreeNode());
    }

    public TreeNode<T> lastNodeOnPath(Path path) {
        TreeNode<T> treeNode = this.node;
        ChildKey front = path.getFront();
        while (front != null) {
            TreeNode<T> treeNode2 = treeNode.children.containsKey(front) ? (TreeNode) treeNode.children.get(front) : null;
            if (treeNode2 == null) {
                return treeNode;
            }
            path = path.popFront();
            TreeNode<T> treeNode3 = treeNode2;
            front = path.getFront();
            treeNode = treeNode3;
        }
        return treeNode;
    }

    public Tree<T> subTree(Path path) {
        ChildKey front = path.getFront();
        Path path2 = path;
        Tree tree = this;
        while (front != null) {
            Tree tree2 = new Tree(front, tree, tree.node.children.containsKey(front) ? (TreeNode) tree.node.children.get(front) : new TreeNode());
            path2 = path2.popFront();
            front = path2.getFront();
            tree = tree2;
        }
        return tree;
    }

    public T getValue() {
        return this.node.value;
    }

    public void setValue(T t) {
        this.node.value = t;
        updateParents();
    }

    public Tree<T> getParent() {
        return this.parent;
    }

    public ChildKey getName() {
        return this.name;
    }

    public Path getPath() {
        Path path;
        Tree<T> tree = this.parent;
        if (tree != null) {
            return tree.getPath().child(this.name);
        }
        ChildKey childKey = this.name;
        if (childKey != null) {
            path = new Path(childKey);
        } else {
            path = Path.getEmptyPath();
        }
        return path;
    }

    public boolean hasChildren() {
        return !this.node.children.isEmpty();
    }

    public boolean isEmpty() {
        return this.node.value == null && this.node.children.isEmpty();
    }

    public void forEachDescendant(TreeVisitor<T> treeVisitor) {
        forEachDescendant(treeVisitor, false, false);
    }

    public void forEachDescendant(TreeVisitor<T> treeVisitor, boolean z) {
        forEachDescendant(treeVisitor, z, false);
    }

    public void forEachDescendant(final TreeVisitor<T> treeVisitor, boolean z, final boolean z2) {
        if (z && !z2) {
            treeVisitor.visitTree(this);
        }
        forEachChild(new TreeVisitor<T>() {
            public void visitTree(Tree<T> tree) {
                tree.forEachDescendant(treeVisitor, true, z2);
            }
        });
        if (z && z2) {
            treeVisitor.visitTree(this);
        }
    }

    public boolean forEachAncestor(TreeFilter<T> treeFilter) {
        return forEachAncestor(treeFilter, false);
    }

    public boolean forEachAncestor(TreeFilter<T> treeFilter, boolean z) {
        for (Tree tree = z ? this : this.parent; tree != null; tree = tree.parent) {
            if (treeFilter.filterTreeNode(tree)) {
                return true;
            }
        }
        return false;
    }

    public void forEachChild(TreeVisitor<T> treeVisitor) {
        Object[] array = this.node.children.entrySet().toArray();
        for (Object obj : array) {
            Entry entry = (Entry) obj;
            treeVisitor.visitTree(new Tree((ChildKey) entry.getKey(), this, (TreeNode) entry.getValue()));
        }
    }

    private void updateParents() {
        Tree<T> tree = this.parent;
        if (tree != null) {
            tree.updateChild(this.name, this);
        }
    }

    private void updateChild(ChildKey childKey, Tree<T> tree) {
        boolean isEmpty = tree.isEmpty();
        boolean containsKey = this.node.children.containsKey(childKey);
        if (isEmpty && containsKey) {
            this.node.children.remove(childKey);
            updateParents();
        } else if (!isEmpty && !containsKey) {
            this.node.children.put(childKey, tree.node);
            updateParents();
        }
    }

    public String toString() {
        return toString("");
    }

    /* access modifiers changed from: 0000 */
    public String toString(String str) {
        ChildKey childKey = this.name;
        String asString = childKey == null ? "<anon>" : childKey.asString();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(asString);
        sb.append(Constants.NEW_LINE);
        TreeNode<T> treeNode = this.node;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("\t");
        sb.append(treeNode.toString(sb2.toString()));
        return sb.toString();
    }
}
