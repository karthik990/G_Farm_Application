package com.google.firebase.database.core.view;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.core.EventRegistration;
import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.WriteTreeRef;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.operation.Operation.OperationType;
import com.google.firebase.database.core.view.ViewProcessor.ProcessorResult;
import com.google.firebase.database.core.view.filter.IndexedFilter;
import com.google.firebase.database.core.view.filter.NodeFilter;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class View {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final EventGenerator eventGenerator;
    private final List<EventRegistration> eventRegistrations = new ArrayList();
    private final ViewProcessor processor;
    private final QuerySpec query;
    private ViewCache viewCache;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    public static class OperationResult {
        public final List<Change> changes;
        public final List<DataEvent> events;

        public OperationResult(List<DataEvent> list, List<Change> list2) {
            this.events = list;
            this.changes = list2;
        }
    }

    public View(QuerySpec querySpec, ViewCache viewCache2) {
        this.query = querySpec;
        IndexedFilter indexedFilter = new IndexedFilter(querySpec.getIndex());
        NodeFilter nodeFilter = querySpec.getParams().getNodeFilter();
        this.processor = new ViewProcessor(nodeFilter);
        CacheNode serverCache = viewCache2.getServerCache();
        CacheNode eventCache = viewCache2.getEventCache();
        IndexedNode from = IndexedNode.from(EmptyNode.Empty(), querySpec.getIndex());
        IndexedNode updateFullNode = indexedFilter.updateFullNode(from, serverCache.getIndexedNode(), null);
        IndexedNode updateFullNode2 = nodeFilter.updateFullNode(from, eventCache.getIndexedNode(), null);
        this.viewCache = new ViewCache(new CacheNode(updateFullNode2, eventCache.isFullyInitialized(), nodeFilter.filtersNodes()), new CacheNode(updateFullNode, serverCache.isFullyInitialized(), indexedFilter.filtersNodes()));
        this.eventGenerator = new EventGenerator(querySpec);
    }

    public QuerySpec getQuery() {
        return this.query;
    }

    public Node getCompleteNode() {
        return this.viewCache.getCompleteEventSnap();
    }

    public Node getServerCache() {
        return this.viewCache.getServerCache().getNode();
    }

    public Node getEventCache() {
        return this.viewCache.getEventCache().getNode();
    }

    public Node getCompleteServerCache(Path path) {
        Node completeServerSnap = this.viewCache.getCompleteServerSnap();
        if (completeServerSnap == null || (!this.query.loadsAllData() && (path.isEmpty() || completeServerSnap.getImmediateChild(path.getFront()).isEmpty()))) {
            return null;
        }
        return completeServerSnap.getChild(path);
    }

    public boolean isEmpty() {
        return this.eventRegistrations.isEmpty();
    }

    public void addEventRegistration(EventRegistration eventRegistration) {
        this.eventRegistrations.add(eventRegistration);
    }

    public List<Event> removeEventRegistration(EventRegistration eventRegistration, DatabaseError databaseError) {
        List<Event> list;
        if (databaseError != null) {
            list = new ArrayList<>();
            Path path = this.query.getPath();
            for (EventRegistration cancelEvent : this.eventRegistrations) {
                list.add(new CancelEvent(cancelEvent, databaseError, path));
            }
        } else {
            list = Collections.emptyList();
        }
        if (eventRegistration != null) {
            int i = 0;
            int i2 = -1;
            while (true) {
                if (i >= this.eventRegistrations.size()) {
                    i = i2;
                    break;
                }
                EventRegistration eventRegistration2 = (EventRegistration) this.eventRegistrations.get(i);
                if (eventRegistration2.isSameListener(eventRegistration)) {
                    if (eventRegistration2.isZombied()) {
                        break;
                    }
                    i2 = i;
                }
                i++;
            }
            if (i != -1) {
                EventRegistration eventRegistration3 = (EventRegistration) this.eventRegistrations.get(i);
                this.eventRegistrations.remove(i);
                eventRegistration3.zombify();
            }
        } else {
            for (EventRegistration zombify : this.eventRegistrations) {
                zombify.zombify();
            }
            this.eventRegistrations.clear();
        }
        return list;
    }

    public OperationResult applyOperation(Operation operation, WriteTreeRef writeTreeRef, Node node) {
        if (operation.getType() == OperationType.Merge) {
            QueryParams queryParams = operation.getSource().getQueryParams();
        }
        ProcessorResult applyOperation = this.processor.applyOperation(this.viewCache, operation, writeTreeRef, node);
        this.viewCache = applyOperation.viewCache;
        return new OperationResult(generateEventsForChanges(applyOperation.changes, applyOperation.viewCache.getEventCache().getIndexedNode(), null), applyOperation.changes);
    }

    public List<DataEvent> getInitialEvents(EventRegistration eventRegistration) {
        CacheNode eventCache = this.viewCache.getEventCache();
        ArrayList arrayList = new ArrayList();
        for (NamedNode namedNode : eventCache.getNode()) {
            arrayList.add(Change.childAddedChange(namedNode.getName(), namedNode.getNode()));
        }
        if (eventCache.isFullyInitialized()) {
            arrayList.add(Change.valueChange(eventCache.getIndexedNode()));
        }
        return generateEventsForChanges(arrayList, eventCache.getIndexedNode(), eventRegistration);
    }

    private List<DataEvent> generateEventsForChanges(List<Change> list, IndexedNode indexedNode, EventRegistration eventRegistration) {
        List<EventRegistration> list2;
        if (eventRegistration == null) {
            list2 = this.eventRegistrations;
        } else {
            list2 = Arrays.asList(new EventRegistration[]{eventRegistration});
        }
        return this.eventGenerator.generateEventsForChanges(list, indexedNode, list2);
    }

    /* access modifiers changed from: 0000 */
    public List<EventRegistration> getEventRegistrations() {
        return this.eventRegistrations;
    }
}
