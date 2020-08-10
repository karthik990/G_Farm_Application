package com.google.firebase.database.core;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.core.operation.Operation;
import com.google.firebase.database.core.persistence.PersistenceManager;
import com.google.firebase.database.core.utilities.Pair;
import com.google.firebase.database.core.view.CacheNode;
import com.google.firebase.database.core.view.Change;
import com.google.firebase.database.core.view.DataEvent;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.database.core.view.Event.EventType;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.core.view.View;
import com.google.firebase.database.core.view.View.OperationResult;
import com.google.firebase.database.core.view.ViewCache;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.NamedNode;
import com.google.firebase.database.snapshot.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class SyncPoint {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final PersistenceManager persistenceManager;
    private final Map<QueryParams, View> views = new HashMap();

    public SyncPoint(PersistenceManager persistenceManager2) {
        this.persistenceManager = persistenceManager2;
    }

    public boolean isEmpty() {
        return this.views.isEmpty();
    }

    private List<DataEvent> applyOperationToView(View view, Operation operation, WriteTreeRef writeTreeRef, Node node) {
        OperationResult applyOperation = view.applyOperation(operation, writeTreeRef, node);
        if (!view.getQuery().loadsAllData()) {
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            for (Change change : applyOperation.changes) {
                EventType eventType = change.getEventType();
                if (eventType == EventType.CHILD_ADDED) {
                    hashSet2.add(change.getChildKey());
                } else if (eventType == EventType.CHILD_REMOVED) {
                    hashSet.add(change.getChildKey());
                }
            }
            if (!hashSet2.isEmpty() || !hashSet.isEmpty()) {
                this.persistenceManager.updateTrackedQueryKeys(view.getQuery(), hashSet2, hashSet);
            }
        }
        return applyOperation.events;
    }

    public List<DataEvent> applyOperation(Operation operation, WriteTreeRef writeTreeRef, Node node) {
        QueryParams queryParams = operation.getSource().getQueryParams();
        if (queryParams != null) {
            return applyOperationToView((View) this.views.get(queryParams), operation, writeTreeRef, node);
        }
        ArrayList arrayList = new ArrayList();
        for (Entry value : this.views.entrySet()) {
            arrayList.addAll(applyOperationToView((View) value.getValue(), operation, writeTreeRef, node));
        }
        return arrayList;
    }

    public List<DataEvent> addEventRegistration(EventRegistration eventRegistration, WriteTreeRef writeTreeRef, CacheNode cacheNode) {
        boolean z;
        QuerySpec querySpec = eventRegistration.getQuerySpec();
        View view = (View) this.views.get(querySpec.getParams());
        if (view == null) {
            Node calcCompleteEventCache = writeTreeRef.calcCompleteEventCache(cacheNode.isFullyInitialized() ? cacheNode.getNode() : null);
            if (calcCompleteEventCache != null) {
                z = true;
            } else {
                calcCompleteEventCache = writeTreeRef.calcCompleteEventChildren(cacheNode.getNode());
                z = false;
            }
            view = new View(querySpec, new ViewCache(new CacheNode(IndexedNode.from(calcCompleteEventCache, querySpec.getIndex()), z, false), cacheNode));
            if (!querySpec.loadsAllData()) {
                HashSet hashSet = new HashSet();
                for (NamedNode name : view.getEventCache()) {
                    hashSet.add(name.getName());
                }
                this.persistenceManager.setTrackedQueryKeys(querySpec, hashSet);
            }
            this.views.put(querySpec.getParams(), view);
        }
        view.addEventRegistration(eventRegistration);
        return view.getInitialEvents(eventRegistration);
    }

    public Pair<List<QuerySpec>, List<Event>> removeEventRegistration(QuerySpec querySpec, EventRegistration eventRegistration, DatabaseError databaseError) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean hasCompleteView = hasCompleteView();
        if (querySpec.isDefault()) {
            Iterator it = this.views.entrySet().iterator();
            while (it.hasNext()) {
                View view = (View) ((Entry) it.next()).getValue();
                arrayList2.addAll(view.removeEventRegistration(eventRegistration, databaseError));
                if (view.isEmpty()) {
                    it.remove();
                    if (!view.getQuery().loadsAllData()) {
                        arrayList.add(view.getQuery());
                    }
                }
            }
        } else {
            View view2 = (View) this.views.get(querySpec.getParams());
            if (view2 != null) {
                arrayList2.addAll(view2.removeEventRegistration(eventRegistration, databaseError));
                if (view2.isEmpty()) {
                    this.views.remove(querySpec.getParams());
                    if (!view2.getQuery().loadsAllData()) {
                        arrayList.add(view2.getQuery());
                    }
                }
            }
        }
        if (hasCompleteView && !hasCompleteView()) {
            arrayList.add(QuerySpec.defaultQueryAtPath(querySpec.getPath()));
        }
        return new Pair<>(arrayList, arrayList2);
    }

    public List<View> getQueryViews() {
        ArrayList arrayList = new ArrayList();
        for (Entry value : this.views.entrySet()) {
            View view = (View) value.getValue();
            if (!view.getQuery().loadsAllData()) {
                arrayList.add(view);
            }
        }
        return arrayList;
    }

    public Node getCompleteServerCache(Path path) {
        for (View view : this.views.values()) {
            if (view.getCompleteServerCache(path) != null) {
                return view.getCompleteServerCache(path);
            }
        }
        return null;
    }

    public View viewForQuery(QuerySpec querySpec) {
        if (querySpec.loadsAllData()) {
            return getCompleteView();
        }
        return (View) this.views.get(querySpec.getParams());
    }

    public boolean viewExistsForQuery(QuerySpec querySpec) {
        return viewForQuery(querySpec) != null;
    }

    public boolean hasCompleteView() {
        return getCompleteView() != null;
    }

    public View getCompleteView() {
        for (Entry value : this.views.entrySet()) {
            View view = (View) value.getValue();
            if (view.getQuery().loadsAllData()) {
                return view;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public Map<QueryParams, View> getViews() {
        return this.views;
    }
}
