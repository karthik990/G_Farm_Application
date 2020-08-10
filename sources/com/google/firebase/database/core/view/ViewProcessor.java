package com.google.firebase.database.core.view;

import com.google.firebase.database.core.CompoundWrite;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.WriteTreeRef;
import com.google.firebase.database.core.operation.AckUserWrite;
import com.google.firebase.database.core.operation.Merge;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.Operation.OperationType;
import com.google.firebase.database.core.operation.Overwrite;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.view.filter.ChildChangeAccumulator;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.ChildrenNode;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.KeyIndex;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class ViewProcessor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static CompleteChildSource NO_COMPLETE_SOURCE = new CompleteChildSource() {
        public NamedNode getChildAfterChild(Index index, NamedNode namedNode, boolean z) {
            return null;
        }

        public Node getCompleteChild(ChildKey childKey) {
            return null;
        }
    };
    private final NodeFilter filter;

    /* renamed from: com.google.firebase.database.core.view.ViewProcessor$2 */
    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    static /* synthetic */ class C36212 {

        /* renamed from: $SwitchMap$com$google$firebase$database$core$operation$Operation$OperationType */
        static final /* synthetic */ int[] f2013xc0d7b136 = new int[OperationType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                com.google.firebase.database.core.operation.Operation$OperationType[] r0 = com.google.firebase.database.core.operation.Operation.OperationType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f2013xc0d7b136 = r0
                int[] r0 = f2013xc0d7b136     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.firebase.database.core.operation.Operation$OperationType r1 = com.google.firebase.database.core.operation.Operation.OperationType.Overwrite     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f2013xc0d7b136     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.firebase.database.core.operation.Operation$OperationType r1 = com.google.firebase.database.core.operation.Operation.OperationType.Merge     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f2013xc0d7b136     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.firebase.database.core.operation.Operation$OperationType r1 = com.google.firebase.database.core.operation.Operation.OperationType.AckUserWrite     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f2013xc0d7b136     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.firebase.database.core.operation.Operation$OperationType r1 = com.google.firebase.database.core.operation.Operation.OperationType.ListenComplete     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.view.ViewProcessor.C36212.<clinit>():void");
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public static class ProcessorResult {
        public final List<Change> changes;
        public final ViewCache viewCache;

        public ProcessorResult(ViewCache viewCache2, List<Change> list) {
            this.viewCache = viewCache2;
            this.changes = list;
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class WriteTreeCompleteChildSource implements CompleteChildSource {
        private final Node optCompleteServerCache;
        private final ViewCache viewCache;
        private final WriteTreeRef writes;

        public WriteTreeCompleteChildSource(WriteTreeRef writeTreeRef, ViewCache viewCache2, Node node) {
            this.writes = writeTreeRef;
            this.viewCache = viewCache2;
            this.optCompleteServerCache = node;
        }

        public Node getCompleteChild(ChildKey childKey) {
            CacheNode cacheNode;
            CacheNode eventCache = this.viewCache.getEventCache();
            if (eventCache.isCompleteForChild(childKey)) {
                return eventCache.getNode().getImmediateChild(childKey);
            }
            Node node = this.optCompleteServerCache;
            if (node != null) {
                cacheNode = new CacheNode(IndexedNode.from(node, KeyIndex.getInstance()), true, false);
            } else {
                cacheNode = this.viewCache.getServerCache();
            }
            return this.writes.calcCompleteChild(childKey, cacheNode);
        }

        public NamedNode getChildAfterChild(Index index, NamedNode namedNode, boolean z) {
            Node node = this.optCompleteServerCache;
            if (node == null) {
                node = this.viewCache.getCompleteServerSnap();
            }
            return this.writes.calcNextNodeAfterPost(node, namedNode, z, index);
        }
    }

    public ViewProcessor(NodeFilter nodeFilter) {
        this.filter = nodeFilter;
    }

    public ProcessorResult applyOperation(ViewCache viewCache, Operation operation, WriteTreeRef writeTreeRef, Node node) {
        ViewCache viewCache2;
        ChildChangeAccumulator childChangeAccumulator = new ChildChangeAccumulator();
        int i = C36212.f2013xc0d7b136[operation.getType().ordinal()];
        if (i == 1) {
            Overwrite overwrite = (Overwrite) operation;
            if (overwrite.getSource().isFromUser()) {
                viewCache2 = applyUserOverwrite(viewCache, overwrite.getPath(), overwrite.getSnapshot(), writeTreeRef, node, childChangeAccumulator);
            } else {
                viewCache2 = applyServerOverwrite(viewCache, overwrite.getPath(), overwrite.getSnapshot(), writeTreeRef, node, overwrite.getSource().isTagged() || (viewCache.getServerCache().isFiltered() && !overwrite.getPath().isEmpty()), childChangeAccumulator);
            }
        } else if (i == 2) {
            Merge merge = (Merge) operation;
            if (merge.getSource().isFromUser()) {
                viewCache2 = applyUserMerge(viewCache, merge.getPath(), merge.getChildren(), writeTreeRef, node, childChangeAccumulator);
            } else {
                viewCache2 = applyServerMerge(viewCache, merge.getPath(), merge.getChildren(), writeTreeRef, node, merge.getSource().isTagged() || viewCache.getServerCache().isFiltered(), childChangeAccumulator);
            }
        } else if (i == 3) {
            AckUserWrite ackUserWrite = (AckUserWrite) operation;
            if (!ackUserWrite.isRevert()) {
                viewCache2 = ackUserWrite(viewCache, ackUserWrite.getPath(), ackUserWrite.getAffectedTree(), writeTreeRef, node, childChangeAccumulator);
            } else {
                viewCache2 = revertUserWrite(viewCache, ackUserWrite.getPath(), writeTreeRef, node, childChangeAccumulator);
            }
        } else if (i == 4) {
            viewCache2 = listenComplete(viewCache, operation.getPath(), writeTreeRef, node, childChangeAccumulator);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown operation: ");
            sb.append(operation.getType());
            throw new AssertionError(sb.toString());
        }
        ArrayList arrayList = new ArrayList(childChangeAccumulator.getChanges());
        maybeAddValueEvent(viewCache, viewCache2, arrayList);
        return new ProcessorResult(viewCache2, arrayList);
    }

    private void maybeAddValueEvent(ViewCache viewCache, ViewCache viewCache2, List<Change> list) {
        CacheNode eventCache = viewCache2.getEventCache();
        if (eventCache.isFullyInitialized()) {
            boolean z = eventCache.getNode().isLeafNode() || eventCache.getNode().isEmpty();
            if (!list.isEmpty() || !viewCache.getEventCache().isFullyInitialized() || ((z && !eventCache.getNode().equals(viewCache.getCompleteEventSnap())) || !eventCache.getNode().getPriority().equals(viewCache.getCompleteEventSnap().getPriority()))) {
                list.add(Change.valueChange(eventCache.getIndexedNode()));
            }
        }
    }

    private ViewCache generateEventCacheAfterServerEvent(ViewCache viewCache, Path path, WriteTreeRef writeTreeRef, CompleteChildSource completeChildSource, ChildChangeAccumulator childChangeAccumulator) {
        IndexedNode indexedNode;
        Node node;
        Node node2;
        CacheNode eventCache = viewCache.getEventCache();
        if (writeTreeRef.shadowingWrite(path) != null) {
            return viewCache;
        }
        if (path.isEmpty()) {
            if (viewCache.getServerCache().isFiltered()) {
                Node completeServerSnap = viewCache.getCompleteServerSnap();
                if (!(completeServerSnap instanceof ChildrenNode)) {
                    completeServerSnap = EmptyNode.Empty();
                }
                node2 = writeTreeRef.calcCompleteEventChildren(completeServerSnap);
            } else {
                node2 = writeTreeRef.calcCompleteEventCache(viewCache.getCompleteServerSnap());
            }
            indexedNode = this.filter.updateFullNode(viewCache.getEventCache().getIndexedNode(), IndexedNode.from(node2, this.filter.getIndex()), childChangeAccumulator);
        } else {
            ChildKey front = path.getFront();
            if (front.isPriorityChildName()) {
                Node calcEventCacheAfterServerOverwrite = writeTreeRef.calcEventCacheAfterServerOverwrite(path, eventCache.getNode(), viewCache.getServerCache().getNode());
                if (calcEventCacheAfterServerOverwrite != null) {
                    indexedNode = this.filter.updatePriority(eventCache.getIndexedNode(), calcEventCacheAfterServerOverwrite);
                } else {
                    indexedNode = eventCache.getIndexedNode();
                }
            } else {
                Path popFront = path.popFront();
                if (eventCache.isCompleteForChild(front)) {
                    Node calcEventCacheAfterServerOverwrite2 = writeTreeRef.calcEventCacheAfterServerOverwrite(path, eventCache.getNode(), viewCache.getServerCache().getNode());
                    if (calcEventCacheAfterServerOverwrite2 != null) {
                        node = eventCache.getNode().getImmediateChild(front).updateChild(popFront, calcEventCacheAfterServerOverwrite2);
                    } else {
                        node = eventCache.getNode().getImmediateChild(front);
                    }
                } else {
                    node = writeTreeRef.calcCompleteChild(front, viewCache.getServerCache());
                }
                Node node3 = node;
                if (node3 != null) {
                    indexedNode = this.filter.updateChild(eventCache.getIndexedNode(), front, node3, popFront, completeChildSource, childChangeAccumulator);
                } else {
                    indexedNode = eventCache.getIndexedNode();
                }
            }
        }
        return viewCache.updateEventSnap(indexedNode, eventCache.isFullyInitialized() || path.isEmpty(), this.filter.filtersNodes());
    }

    private ViewCache applyServerOverwrite(ViewCache viewCache, Path path, Node node, WriteTreeRef writeTreeRef, Node node2, boolean z, ChildChangeAccumulator childChangeAccumulator) {
        IndexedNode indexedNode;
        ViewCache viewCache2 = viewCache;
        Node node3 = node;
        CacheNode serverCache = viewCache.getServerCache();
        NodeFilter nodeFilter = this.filter;
        if (!z) {
            nodeFilter = nodeFilter.getIndexedFilter();
        }
        boolean z2 = true;
        if (path.isEmpty()) {
            indexedNode = nodeFilter.updateFullNode(serverCache.getIndexedNode(), IndexedNode.from(node3, nodeFilter.getIndex()), null);
        } else if (!nodeFilter.filtersNodes() || serverCache.isFiltered()) {
            ChildKey front = path.getFront();
            if (!serverCache.isCompleteForPath(path) && path.size() > 1) {
                return viewCache2;
            }
            Path popFront = path.popFront();
            Node updateChild = serverCache.getNode().getImmediateChild(front).updateChild(popFront, node3);
            if (front.isPriorityChildName()) {
                indexedNode = nodeFilter.updatePriority(serverCache.getIndexedNode(), updateChild);
            } else {
                indexedNode = nodeFilter.updateChild(serverCache.getIndexedNode(), front, updateChild, popFront, NO_COMPLETE_SOURCE, null);
            }
            if (!serverCache.isFullyInitialized() && !path.isEmpty()) {
                z2 = false;
            }
            ViewCache updateServerSnap = viewCache.updateServerSnap(indexedNode, z2, nodeFilter.filtersNodes());
            WriteTreeRef writeTreeRef2 = writeTreeRef;
            return generateEventCacheAfterServerEvent(updateServerSnap, path, writeTreeRef2, new WriteTreeCompleteChildSource(writeTreeRef2, updateServerSnap, node2), childChangeAccumulator);
        } else {
            ChildKey front2 = path.getFront();
            indexedNode = nodeFilter.updateFullNode(serverCache.getIndexedNode(), serverCache.getIndexedNode().updateChild(front2, serverCache.getNode().getImmediateChild(front2).updateChild(path.popFront(), node3)), null);
        }
        Path path2 = path;
        z2 = false;
        ViewCache updateServerSnap2 = viewCache.updateServerSnap(indexedNode, z2, nodeFilter.filtersNodes());
        WriteTreeRef writeTreeRef22 = writeTreeRef;
        return generateEventCacheAfterServerEvent(updateServerSnap2, path, writeTreeRef22, new WriteTreeCompleteChildSource(writeTreeRef22, updateServerSnap2, node2), childChangeAccumulator);
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.firebase.database.core.view.ViewCache applyUserOverwrite(com.google.firebase.database.core.view.ViewCache r9, com.google.firebase.database.core.Path r10, com.google.firebase.database.snapshot.Node r11, com.google.firebase.database.core.WriteTreeRef r12, com.google.firebase.database.snapshot.Node r13, com.google.firebase.database.core.view.filter.ChildChangeAccumulator r14) {
        /*
            r8 = this;
            com.google.firebase.database.core.view.CacheNode r0 = r9.getEventCache()
            com.google.firebase.database.core.view.ViewProcessor$WriteTreeCompleteChildSource r6 = new com.google.firebase.database.core.view.ViewProcessor$WriteTreeCompleteChildSource
            r6.<init>(r12, r9, r13)
            boolean r12 = r10.isEmpty()
            if (r12 == 0) goto L_0x0034
            com.google.firebase.database.core.view.filter.NodeFilter r10 = r8.filter
            com.google.firebase.database.snapshot.Index r10 = r10.getIndex()
            com.google.firebase.database.snapshot.IndexedNode r10 = com.google.firebase.database.snapshot.IndexedNode.from(r11, r10)
            com.google.firebase.database.core.view.filter.NodeFilter r11 = r8.filter
            com.google.firebase.database.core.view.CacheNode r12 = r9.getEventCache()
            com.google.firebase.database.snapshot.IndexedNode r12 = r12.getIndexedNode()
            com.google.firebase.database.snapshot.IndexedNode r10 = r11.updateFullNode(r12, r10, r14)
            r11 = 1
            com.google.firebase.database.core.view.filter.NodeFilter r12 = r8.filter
            boolean r12 = r12.filtersNodes()
            com.google.firebase.database.core.view.ViewCache r9 = r9.updateEventSnap(r10, r11, r12)
            goto L_0x00b6
        L_0x0034:
            com.google.firebase.database.snapshot.ChildKey r3 = r10.getFront()
            boolean r12 = r3.isPriorityChildName()
            if (r12 == 0) goto L_0x0059
            com.google.firebase.database.core.view.filter.NodeFilter r10 = r8.filter
            com.google.firebase.database.core.view.CacheNode r12 = r9.getEventCache()
            com.google.firebase.database.snapshot.IndexedNode r12 = r12.getIndexedNode()
            com.google.firebase.database.snapshot.IndexedNode r10 = r10.updatePriority(r12, r11)
            boolean r11 = r0.isFullyInitialized()
            boolean r12 = r0.isFiltered()
            com.google.firebase.database.core.view.ViewCache r9 = r9.updateEventSnap(r10, r11, r12)
            goto L_0x00b6
        L_0x0059:
            com.google.firebase.database.core.Path r5 = r10.popFront()
            com.google.firebase.database.snapshot.Node r10 = r0.getNode()
            com.google.firebase.database.snapshot.Node r10 = r10.getImmediateChild(r3)
            boolean r12 = r5.isEmpty()
            if (r12 == 0) goto L_0x006d
        L_0x006b:
            r4 = r11
            goto L_0x0097
        L_0x006d:
            com.google.firebase.database.snapshot.Node r12 = r6.getCompleteChild(r3)
            if (r12 == 0) goto L_0x0092
            com.google.firebase.database.snapshot.ChildKey r13 = r5.getBack()
            boolean r13 = r13.isPriorityChildName()
            if (r13 == 0) goto L_0x008d
            com.google.firebase.database.core.Path r13 = r5.getParent()
            com.google.firebase.database.snapshot.Node r13 = r12.getChild(r13)
            boolean r13 = r13.isEmpty()
            if (r13 == 0) goto L_0x008d
            r4 = r12
            goto L_0x0097
        L_0x008d:
            com.google.firebase.database.snapshot.Node r11 = r12.updateChild(r5, r11)
            goto L_0x006b
        L_0x0092:
            com.google.firebase.database.snapshot.EmptyNode r11 = com.google.firebase.database.snapshot.EmptyNode.Empty()
            goto L_0x006b
        L_0x0097:
            boolean r10 = r10.equals(r4)
            if (r10 != 0) goto L_0x00b6
            com.google.firebase.database.core.view.filter.NodeFilter r1 = r8.filter
            com.google.firebase.database.snapshot.IndexedNode r2 = r0.getIndexedNode()
            r7 = r14
            com.google.firebase.database.snapshot.IndexedNode r10 = r1.updateChild(r2, r3, r4, r5, r6, r7)
            boolean r11 = r0.isFullyInitialized()
            com.google.firebase.database.core.view.filter.NodeFilter r12 = r8.filter
            boolean r12 = r12.filtersNodes()
            com.google.firebase.database.core.view.ViewCache r9 = r9.updateEventSnap(r10, r11, r12)
        L_0x00b6:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.database.core.view.ViewProcessor.applyUserOverwrite(com.google.firebase.database.core.view.ViewCache, com.google.firebase.database.core.Path, com.google.firebase.database.snapshot.Node, com.google.firebase.database.core.WriteTreeRef, com.google.firebase.database.snapshot.Node, com.google.firebase.database.core.view.filter.ChildChangeAccumulator):com.google.firebase.database.core.view.ViewCache");
    }

    private static boolean cacheHasChild(ViewCache viewCache, ChildKey childKey) {
        return viewCache.getEventCache().isCompleteForChild(childKey);
    }

    private ViewCache applyUserMerge(ViewCache viewCache, Path path, CompoundWrite compoundWrite, WriteTreeRef writeTreeRef, Node node, ChildChangeAccumulator childChangeAccumulator) {
        ViewCache viewCache2 = viewCache;
        Path path2 = path;
        Iterator it = compoundWrite.iterator();
        ViewCache viewCache3 = viewCache2;
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            Path child = path.child((Path) entry.getKey());
            if (cacheHasChild(viewCache, child.getFront())) {
                viewCache3 = applyUserOverwrite(viewCache3, child, (Node) entry.getValue(), writeTreeRef, node, childChangeAccumulator);
            }
        }
        Iterator it2 = compoundWrite.iterator();
        ViewCache viewCache4 = viewCache3;
        while (it2.hasNext()) {
            Entry entry2 = (Entry) it2.next();
            Path child2 = path.child((Path) entry2.getKey());
            if (!cacheHasChild(viewCache, child2.getFront())) {
                viewCache4 = applyUserOverwrite(viewCache4, child2, (Node) entry2.getValue(), writeTreeRef, node, childChangeAccumulator);
            }
        }
        return viewCache4;
    }

    private ViewCache applyServerMerge(ViewCache viewCache, Path path, CompoundWrite compoundWrite, WriteTreeRef writeTreeRef, Node node, boolean z, ChildChangeAccumulator childChangeAccumulator) {
        CompoundWrite compoundWrite2;
        if (viewCache.getServerCache().getNode().isEmpty() && !viewCache.getServerCache().isFullyInitialized()) {
            return viewCache;
        }
        if (path.isEmpty()) {
            compoundWrite2 = compoundWrite;
        } else {
            compoundWrite2 = CompoundWrite.emptyWrite().addWrites(path, compoundWrite);
        }
        Node node2 = viewCache.getServerCache().getNode();
        Map childCompoundWrites = compoundWrite2.childCompoundWrites();
        ViewCache viewCache2 = viewCache;
        for (Entry entry : childCompoundWrites.entrySet()) {
            ChildKey childKey = (ChildKey) entry.getKey();
            if (node2.hasChild(childKey)) {
                viewCache2 = applyServerOverwrite(viewCache2, new Path(childKey), ((CompoundWrite) entry.getValue()).apply(node2.getImmediateChild(childKey)), writeTreeRef, node, z, childChangeAccumulator);
            }
        }
        ViewCache viewCache3 = viewCache2;
        for (Entry entry2 : childCompoundWrites.entrySet()) {
            ChildKey childKey2 = (ChildKey) entry2.getKey();
            boolean z2 = !viewCache.getServerCache().isCompleteForChild(childKey2) && ((CompoundWrite) entry2.getValue()).rootWrite() == null;
            if (!node2.hasChild(childKey2) && !z2) {
                viewCache3 = applyServerOverwrite(viewCache3, new Path(childKey2), ((CompoundWrite) entry2.getValue()).apply(node2.getImmediateChild(childKey2)), writeTreeRef, node, z, childChangeAccumulator);
            }
        }
        return viewCache3;
    }

    private ViewCache ackUserWrite(ViewCache viewCache, Path path, ImmutableTree<Boolean> immutableTree, WriteTreeRef writeTreeRef, Node node, ChildChangeAccumulator childChangeAccumulator) {
        if (writeTreeRef.shadowingWrite(path) != null) {
            return viewCache;
        }
        boolean isFiltered = viewCache.getServerCache().isFiltered();
        CacheNode serverCache = viewCache.getServerCache();
        if (immutableTree.getValue() == null) {
            CompoundWrite emptyWrite = CompoundWrite.emptyWrite();
            Iterator it = immutableTree.iterator();
            CompoundWrite compoundWrite = emptyWrite;
            while (it.hasNext()) {
                Path path2 = (Path) ((Entry) it.next()).getKey();
                Path child = path.child(path2);
                if (serverCache.isCompleteForPath(child)) {
                    compoundWrite = compoundWrite.addWrite(path2, serverCache.getNode().getChild(child));
                }
            }
            return applyServerMerge(viewCache, path, compoundWrite, writeTreeRef, node, isFiltered, childChangeAccumulator);
        } else if ((!path.isEmpty() || !serverCache.isFullyInitialized()) && !serverCache.isCompleteForPath(path)) {
            if (path.isEmpty()) {
                CompoundWrite emptyWrite2 = CompoundWrite.emptyWrite();
                CompoundWrite compoundWrite2 = emptyWrite2;
                for (NamedNode namedNode : serverCache.getNode()) {
                    compoundWrite2 = compoundWrite2.addWrite(namedNode.getName(), namedNode.getNode());
                }
                viewCache = applyServerMerge(viewCache, path, compoundWrite2, writeTreeRef, node, isFiltered, childChangeAccumulator);
            }
            return viewCache;
        } else {
            return applyServerOverwrite(viewCache, path, serverCache.getNode().getChild(path), writeTreeRef, node, isFiltered, childChangeAccumulator);
        }
    }

    public ViewCache revertUserWrite(ViewCache viewCache, Path path, WriteTreeRef writeTreeRef, Node node, ChildChangeAccumulator childChangeAccumulator) {
        Node node2;
        if (writeTreeRef.shadowingWrite(path) != null) {
            return viewCache;
        }
        WriteTreeCompleteChildSource writeTreeCompleteChildSource = new WriteTreeCompleteChildSource(writeTreeRef, viewCache, node);
        IndexedNode indexedNode = viewCache.getEventCache().getIndexedNode();
        if (path.isEmpty() || path.getFront().isPriorityChildName()) {
            if (viewCache.getServerCache().isFullyInitialized()) {
                node2 = writeTreeRef.calcCompleteEventCache(viewCache.getCompleteServerSnap());
            } else {
                node2 = writeTreeRef.calcCompleteEventChildren(viewCache.getServerCache().getNode());
            }
            indexedNode = this.filter.updateFullNode(indexedNode, IndexedNode.from(node2, this.filter.getIndex()), childChangeAccumulator);
        } else {
            ChildKey front = path.getFront();
            Node calcCompleteChild = writeTreeRef.calcCompleteChild(front, viewCache.getServerCache());
            if (calcCompleteChild == null && viewCache.getServerCache().isCompleteForChild(front)) {
                calcCompleteChild = indexedNode.getNode().getImmediateChild(front);
            }
            Node node3 = calcCompleteChild;
            if (node3 != null) {
                indexedNode = this.filter.updateChild(indexedNode, front, node3, path.popFront(), writeTreeCompleteChildSource, childChangeAccumulator);
            } else if (node3 == null && viewCache.getEventCache().getNode().hasChild(front)) {
                indexedNode = this.filter.updateChild(indexedNode, front, EmptyNode.Empty(), path.popFront(), writeTreeCompleteChildSource, childChangeAccumulator);
            }
            if (indexedNode.getNode().isEmpty() && viewCache.getServerCache().isFullyInitialized()) {
                Node calcCompleteEventCache = writeTreeRef.calcCompleteEventCache(viewCache.getCompleteServerSnap());
                if (calcCompleteEventCache.isLeafNode()) {
                    indexedNode = this.filter.updateFullNode(indexedNode, IndexedNode.from(calcCompleteEventCache, this.filter.getIndex()), childChangeAccumulator);
                }
            }
        }
        return viewCache.updateEventSnap(indexedNode, viewCache.getServerCache().isFullyInitialized() || writeTreeRef.shadowingWrite(Path.getEmptyPath()) != null, this.filter.filtersNodes());
    }

    private ViewCache listenComplete(ViewCache viewCache, Path path, WriteTreeRef writeTreeRef, Node node, ChildChangeAccumulator childChangeAccumulator) {
        CacheNode serverCache = viewCache.getServerCache();
        return generateEventCacheAfterServerEvent(viewCache.updateServerSnap(serverCache.getIndexedNode(), serverCache.isFullyInitialized() || path.isEmpty(), serverCache.isFiltered()), path, writeTreeRef, NO_COMPLETE_SOURCE, childChangeAccumulator);
    }
}
