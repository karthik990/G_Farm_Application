package com.google.firebase.database.core;

import com.google.firebase.database.core.utilities.Predicate;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class WriteTree {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Predicate<UserWriteRecord> DEFAULT_FILTER = new Predicate<UserWriteRecord>() {
        public boolean evaluate(UserWriteRecord userWriteRecord) {
            return userWriteRecord.isVisible();
        }
    };
    private List<UserWriteRecord> allWrites = new ArrayList();
    private Long lastWriteId = Long.valueOf(-1);
    private CompoundWrite visibleWrites = CompoundWrite.emptyWrite();

    public WriteTreeRef childWrites(Path path) {
        return new WriteTreeRef(path, this);
    }

    public void addOverwrite(Path path, Node node, Long l, boolean z) {
        List<UserWriteRecord> list = this.allWrites;
        UserWriteRecord userWriteRecord = new UserWriteRecord(l.longValue(), path, node, z);
        list.add(userWriteRecord);
        if (z) {
            this.visibleWrites = this.visibleWrites.addWrite(path, node);
        }
        this.lastWriteId = l;
    }

    public void addMerge(Path path, CompoundWrite compoundWrite, Long l) {
        this.allWrites.add(new UserWriteRecord(l.longValue(), path, compoundWrite));
        this.visibleWrites = this.visibleWrites.addWrites(path, compoundWrite);
        this.lastWriteId = l;
    }

    public UserWriteRecord getWrite(long j) {
        for (UserWriteRecord userWriteRecord : this.allWrites) {
            if (userWriteRecord.getWriteId() == j) {
                return userWriteRecord;
            }
        }
        return null;
    }

    public List<UserWriteRecord> purgeAllWrites() {
        ArrayList arrayList = new ArrayList(this.allWrites);
        this.visibleWrites = CompoundWrite.emptyWrite();
        this.allWrites = new ArrayList();
        return arrayList;
    }

    public boolean removeWrite(long j) {
        UserWriteRecord userWriteRecord;
        Iterator it = this.allWrites.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                userWriteRecord = null;
                break;
            }
            userWriteRecord = (UserWriteRecord) it.next();
            if (userWriteRecord.getWriteId() == j) {
                break;
            }
            i++;
        }
        this.allWrites.remove(userWriteRecord);
        boolean isVisible = userWriteRecord.isVisible();
        int size = this.allWrites.size() - 1;
        boolean z = false;
        while (isVisible && size >= 0) {
            UserWriteRecord userWriteRecord2 = (UserWriteRecord) this.allWrites.get(size);
            if (userWriteRecord2.isVisible()) {
                if (size >= i && recordContainsPath(userWriteRecord2, userWriteRecord.getPath())) {
                    isVisible = false;
                } else if (userWriteRecord.getPath().contains(userWriteRecord2.getPath())) {
                    z = true;
                }
            }
            size--;
        }
        if (!isVisible) {
            return false;
        }
        if (z) {
            resetTree();
            return true;
        }
        if (userWriteRecord.isOverwrite()) {
            this.visibleWrites = this.visibleWrites.removeWrite(userWriteRecord.getPath());
        } else {
            Iterator it2 = userWriteRecord.getMerge().iterator();
            while (it2.hasNext()) {
                this.visibleWrites = this.visibleWrites.removeWrite(userWriteRecord.getPath().child((Path) ((Entry) it2.next()).getKey()));
            }
        }
        return true;
    }

    public Node getCompleteWriteData(Path path) {
        return this.visibleWrites.getCompleteNode(path);
    }

    public Node calcCompleteEventCache(Path path, Node node) {
        return calcCompleteEventCache(path, node, new ArrayList());
    }

    public Node calcCompleteEventCache(Path path, Node node, List<Long> list) {
        return calcCompleteEventCache(path, node, list, false);
    }

    public Node calcCompleteEventCache(final Path path, Node node, final List<Long> list, final boolean z) {
        if (!list.isEmpty() || z) {
            CompoundWrite childCompoundWrite = this.visibleWrites.childCompoundWrite(path);
            if (!z && childCompoundWrite.isEmpty()) {
                return node;
            }
            if (!z && node == null && !childCompoundWrite.hasCompleteWrite(Path.getEmptyPath())) {
                return null;
            }
            CompoundWrite layerTree = layerTree(this.allWrites, new Predicate<UserWriteRecord>() {
                public boolean evaluate(UserWriteRecord userWriteRecord) {
                    return (userWriteRecord.isVisible() || z) && !list.contains(Long.valueOf(userWriteRecord.getWriteId())) && (userWriteRecord.getPath().contains(path) || path.contains(userWriteRecord.getPath()));
                }
            }, path);
            if (node == null) {
                node = EmptyNode.Empty();
            }
            return layerTree.apply(node);
        }
        Node completeNode = this.visibleWrites.getCompleteNode(path);
        if (completeNode != null) {
            return completeNode;
        }
        CompoundWrite childCompoundWrite2 = this.visibleWrites.childCompoundWrite(path);
        if (childCompoundWrite2.isEmpty()) {
            return node;
        }
        if (node == null && !childCompoundWrite2.hasCompleteWrite(Path.getEmptyPath())) {
            return null;
        }
        if (node == null) {
            node = EmptyNode.Empty();
        }
        return childCompoundWrite2.apply(node);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.google.firebase.database.snapshot.Node, code=com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, for r8v0, types: [com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode>, com.google.firebase.database.snapshot.Node] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.firebase.database.snapshot.Node calcCompleteEventChildren(com.google.firebase.database.core.Path r7, com.google.firebase.database.snapshot.Node<com.google.firebase.database.snapshot.NamedNode> r8) {
        /*
            r6 = this;
            com.google.firebase.database.snapshot.EmptyNode r0 = com.google.firebase.database.snapshot.EmptyNode.Empty()
            com.google.firebase.database.core.CompoundWrite r1 = r6.visibleWrites
            com.google.firebase.database.snapshot.Node r1 = r1.getCompleteNode(r7)
            if (r1 == 0) goto L_0x0030
            boolean r7 = r1.isLeafNode()
            if (r7 != 0) goto L_0x002f
            java.util.Iterator r7 = r1.iterator()
        L_0x0016:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x002f
            java.lang.Object r8 = r7.next()
            com.google.firebase.database.snapshot.NamedNode r8 = (com.google.firebase.database.snapshot.NamedNode) r8
            com.google.firebase.database.snapshot.ChildKey r1 = r8.getName()
            com.google.firebase.database.snapshot.Node r8 = r8.getNode()
            com.google.firebase.database.snapshot.Node r0 = r0.updateImmediateChild(r1, r8)
            goto L_0x0016
        L_0x002f:
            return r0
        L_0x0030:
            com.google.firebase.database.core.CompoundWrite r1 = r6.visibleWrites
            com.google.firebase.database.core.CompoundWrite r7 = r1.childCompoundWrite(r7)
            java.util.Iterator r8 = r8.iterator()
        L_0x003a:
            boolean r1 = r8.hasNext()
            if (r1 == 0) goto L_0x006a
            java.lang.Object r1 = r8.next()
            com.google.firebase.database.snapshot.NamedNode r1 = (com.google.firebase.database.snapshot.NamedNode) r1
            com.google.firebase.database.core.Path r2 = new com.google.firebase.database.core.Path
            r3 = 1
            com.google.firebase.database.snapshot.ChildKey[] r3 = new com.google.firebase.database.snapshot.ChildKey[r3]
            r4 = 0
            com.google.firebase.database.snapshot.ChildKey r5 = r1.getName()
            r3[r4] = r5
            r2.<init>(r3)
            com.google.firebase.database.core.CompoundWrite r2 = r7.childCompoundWrite(r2)
            com.google.firebase.database.snapshot.Node r3 = r1.getNode()
            com.google.firebase.database.snapshot.Node r2 = r2.apply(r3)
            com.google.firebase.database.snapshot.ChildKey r1 = r1.getName()
            com.google.firebase.database.snapshot.Node r0 = r0.updateImmediateChild(r1, r2)
            goto L_0x003a
        L_0x006a:
            java.util.List r7 = r7.getCompleteChildren()
            java.util.Iterator r7 = r7.iterator()
        L_0x0072:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x008b
            java.lang.Object r8 = r7.next()
            com.google.firebase.database.snapshot.NamedNode r8 = (com.google.firebase.database.snapshot.NamedNode) r8
            com.google.firebase.database.snapshot.ChildKey r1 = r8.getName()
            com.google.firebase.database.snapshot.Node r8 = r8.getNode()
            com.google.firebase.database.snapshot.Node r0 = r0.updateImmediateChild(r1, r8)
            goto L_0x0072
        L_0x008b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.WriteTree.calcCompleteEventChildren(com.google.firebase.database.core.Path, com.google.firebase.database.snapshot.Node):com.google.firebase.database.snapshot.Node");
    }

    public Node calcEventCacheAfterServerOverwrite(Path path, Path path2, Node node, Node node2) {
        Path child = path.child(path2);
        if (this.visibleWrites.hasCompleteWrite(child)) {
            return null;
        }
        CompoundWrite childCompoundWrite = this.visibleWrites.childCompoundWrite(child);
        if (childCompoundWrite.isEmpty()) {
            return node2.getChild(path2);
        }
        return childCompoundWrite.apply(node2.getChild(path2));
    }

    public Node calcCompleteChild(Path path, ChildKey childKey, CacheNode cacheNode) {
        Path child = path.child(childKey);
        Node completeNode = this.visibleWrites.getCompleteNode(child);
        if (completeNode != null) {
            return completeNode;
        }
        if (cacheNode.isCompleteForChild(childKey)) {
            return this.visibleWrites.childCompoundWrite(child).apply(cacheNode.getNode().getImmediateChild(childKey));
        }
        return null;
    }

    public Node shadowingWrite(Path path) {
        return this.visibleWrites.getCompleteNode(path);
    }

    public NamedNode calcNextNodeAfterPost(Path path, Node node, NamedNode namedNode, boolean z, Index index) {
        CompoundWrite childCompoundWrite = this.visibleWrites.childCompoundWrite(path);
        Node<NamedNode> completeNode = childCompoundWrite.getCompleteNode(Path.getEmptyPath());
        NamedNode namedNode2 = null;
        if (completeNode == null) {
            if (node != null) {
                completeNode = childCompoundWrite.apply(node);
            }
            return namedNode2;
        }
        for (NamedNode namedNode3 : completeNode) {
            if (index.compare(namedNode3, namedNode, z) > 0 && (namedNode2 == null || index.compare(namedNode3, namedNode2, z) < 0)) {
                namedNode2 = namedNode3;
            }
        }
        return namedNode2;
    }

    private boolean recordContainsPath(UserWriteRecord userWriteRecord, Path path) {
        if (userWriteRecord.isOverwrite()) {
            return userWriteRecord.getPath().contains(path);
        }
        Iterator it = userWriteRecord.getMerge().iterator();
        while (it.hasNext()) {
            if (userWriteRecord.getPath().child((Path) ((Entry) it.next()).getKey()).contains(path)) {
                return true;
            }
        }
        return false;
    }

    private void resetTree() {
        this.visibleWrites = layerTree(this.allWrites, DEFAULT_FILTER, Path.getEmptyPath());
        if (this.allWrites.size() > 0) {
            List<UserWriteRecord> list = this.allWrites;
            this.lastWriteId = Long.valueOf(((UserWriteRecord) list.get(list.size() - 1)).getWriteId());
            return;
        }
        this.lastWriteId = Long.valueOf(-1);
    }

    private static CompoundWrite layerTree(List<UserWriteRecord> list, Predicate<UserWriteRecord> predicate, Path path) {
        CompoundWrite emptyWrite = CompoundWrite.emptyWrite();
        for (UserWriteRecord userWriteRecord : list) {
            if (predicate.evaluate(userWriteRecord)) {
                Path path2 = userWriteRecord.getPath();
                if (userWriteRecord.isOverwrite()) {
                    if (path.contains(path2)) {
                        emptyWrite = emptyWrite.addWrite(Path.getRelative(path, path2), userWriteRecord.getOverwrite());
                    } else if (path2.contains(path)) {
                        emptyWrite = emptyWrite.addWrite(Path.getEmptyPath(), userWriteRecord.getOverwrite().getChild(Path.getRelative(path2, path)));
                    }
                } else if (path.contains(path2)) {
                    emptyWrite = emptyWrite.addWrites(Path.getRelative(path, path2), userWriteRecord.getMerge());
                } else if (path2.contains(path)) {
                    Path relative = Path.getRelative(path2, path);
                    if (relative.isEmpty()) {
                        emptyWrite = emptyWrite.addWrites(Path.getEmptyPath(), userWriteRecord.getMerge());
                    } else {
                        Node completeNode = userWriteRecord.getMerge().getCompleteNode(relative);
                        if (completeNode != null) {
                            emptyWrite = emptyWrite.addWrite(Path.getEmptyPath(), completeNode);
                        }
                    }
                }
            }
        }
        return emptyWrite;
    }
}
