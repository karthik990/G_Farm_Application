package com.google.firebase.database.core.view.filter;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.filter.NodeFilter.CompleteChildSource;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.Index;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.PriorityUtilities;
import java.util.Iterator;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class RangedFilter implements NodeFilter {
    private final NamedNode endPost;
    private final Index index;
    private final IndexedFilter indexedFilter;
    private final NamedNode startPost;

    public boolean filtersNodes() {
        return true;
    }

    public IndexedNode updatePriority(IndexedNode indexedNode, Node node) {
        return indexedNode;
    }

    public RangedFilter(QueryParams queryParams) {
        this.indexedFilter = new IndexedFilter(queryParams.getIndex());
        this.index = queryParams.getIndex();
        this.startPost = getStartPost(queryParams);
        this.endPost = getEndPost(queryParams);
    }

    public NamedNode getStartPost() {
        return this.startPost;
    }

    public NamedNode getEndPost() {
        return this.endPost;
    }

    private static NamedNode getStartPost(QueryParams queryParams) {
        if (!queryParams.hasStart()) {
            return queryParams.getIndex().minPost();
        }
        return queryParams.getIndex().makePost(queryParams.getIndexStartName(), queryParams.getIndexStartValue());
    }

    private static NamedNode getEndPost(QueryParams queryParams) {
        if (!queryParams.hasEnd()) {
            return queryParams.getIndex().maxPost();
        }
        return queryParams.getIndex().makePost(queryParams.getIndexEndName(), queryParams.getIndexEndValue());
    }

    public boolean matches(NamedNode namedNode) {
        return this.index.compare(getStartPost(), namedNode) <= 0 && this.index.compare(namedNode, getEndPost()) <= 0;
    }

    public IndexedNode updateChild(IndexedNode indexedNode, ChildKey childKey, Node node, Path path, CompleteChildSource completeChildSource, ChildChangeAccumulator childChangeAccumulator) {
        if (!matches(new NamedNode(childKey, node))) {
            node = EmptyNode.Empty();
        }
        return this.indexedFilter.updateChild(indexedNode, childKey, node, path, completeChildSource, childChangeAccumulator);
    }

    public IndexedNode updateFullNode(IndexedNode indexedNode, IndexedNode indexedNode2, ChildChangeAccumulator childChangeAccumulator) {
        IndexedNode indexedNode3;
        if (indexedNode2.getNode().isLeafNode()) {
            indexedNode3 = IndexedNode.from(EmptyNode.Empty(), this.index);
        } else {
            IndexedNode updatePriority = indexedNode2.updatePriority(PriorityUtilities.NullPriority());
            Iterator it = indexedNode2.iterator();
            while (it.hasNext()) {
                NamedNode namedNode = (NamedNode) it.next();
                if (!matches(namedNode)) {
                    updatePriority = updatePriority.updateChild(namedNode.getName(), EmptyNode.Empty());
                }
            }
            indexedNode3 = updatePriority;
        }
        return this.indexedFilter.updateFullNode(indexedNode, indexedNode3, childChangeAccumulator);
    }

    public NodeFilter getIndexedFilter() {
        return this.indexedFilter;
    }

    public Index getIndex() {
        return this.index;
    }
}
