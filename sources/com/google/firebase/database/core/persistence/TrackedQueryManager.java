package com.google.firebase.database.core.persistence;

import com.google.firebase.database.core.Path;
import com.google.firebase.database.core.utilities.Clock;
import com.google.firebase.database.core.utilities.ImmutableTree;
import com.google.firebase.database.core.utilities.ImmutableTree.TreeVisitor;
import com.google.firebase.database.core.utilities.Predicate;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.QueryParams;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class TrackedQueryManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Predicate<Map<QueryParams, TrackedQuery>> HAS_ACTIVE_DEFAULT_PREDICATE = new Predicate<Map<QueryParams, TrackedQuery>>() {
        public boolean evaluate(Map<QueryParams, TrackedQuery> map) {
            TrackedQuery trackedQuery = (TrackedQuery) map.get(QueryParams.DEFAULT_PARAMS);
            return trackedQuery != null && trackedQuery.active;
        }
    };
    private static final Predicate<Map<QueryParams, TrackedQuery>> HAS_DEFAULT_COMPLETE_PREDICATE = new Predicate<Map<QueryParams, TrackedQuery>>() {
        public boolean evaluate(Map<QueryParams, TrackedQuery> map) {
            TrackedQuery trackedQuery = (TrackedQuery) map.get(QueryParams.DEFAULT_PARAMS);
            return trackedQuery != null && trackedQuery.complete;
        }
    };
    /* access modifiers changed from: private */
    public static final Predicate<TrackedQuery> IS_QUERY_PRUNABLE_PREDICATE = new Predicate<TrackedQuery>() {
        public boolean evaluate(TrackedQuery trackedQuery) {
            return !trackedQuery.active;
        }
    };
    private static final Predicate<TrackedQuery> IS_QUERY_UNPRUNABLE_PREDICATE = new Predicate<TrackedQuery>() {
        public boolean evaluate(TrackedQuery trackedQuery) {
            return !TrackedQueryManager.IS_QUERY_PRUNABLE_PREDICATE.evaluate(trackedQuery);
        }
    };
    private final Clock clock;
    private long currentQueryId = 0;
    private final LogWrapper logger;
    private final PersistenceStorageEngine storageLayer;
    private ImmutableTree<Map<QueryParams, TrackedQuery>> trackedQueryTree;

    private static void assertValidTrackedQuery(QuerySpec querySpec) {
        Utilities.hardAssert(!querySpec.loadsAllData() || querySpec.isDefault(), "Can't have tracked non-default query that loads all data");
    }

    private static QuerySpec normalizeQuery(QuerySpec querySpec) {
        return querySpec.loadsAllData() ? QuerySpec.defaultQueryAtPath(querySpec.getPath()) : querySpec;
    }

    public TrackedQueryManager(PersistenceStorageEngine persistenceStorageEngine, LogWrapper logWrapper, Clock clock2) {
        this.storageLayer = persistenceStorageEngine;
        this.logger = logWrapper;
        this.clock = clock2;
        this.trackedQueryTree = new ImmutableTree<>(null);
        resetPreviouslyActiveTrackedQueries();
        for (TrackedQuery trackedQuery : this.storageLayer.loadTrackedQueries()) {
            this.currentQueryId = Math.max(trackedQuery.f2010id + 1, this.currentQueryId);
            cacheTrackedQuery(trackedQuery);
        }
    }

    private void resetPreviouslyActiveTrackedQueries() {
        try {
            this.storageLayer.beginTransaction();
            this.storageLayer.resetPreviouslyActiveTrackedQueries(this.clock.millis());
            this.storageLayer.setTransactionSuccessful();
        } finally {
            this.storageLayer.endTransaction();
        }
    }

    public TrackedQuery findTrackedQuery(QuerySpec querySpec) {
        QuerySpec normalizeQuery = normalizeQuery(querySpec);
        Map map = (Map) this.trackedQueryTree.get(normalizeQuery.getPath());
        if (map != null) {
            return (TrackedQuery) map.get(normalizeQuery.getParams());
        }
        return null;
    }

    public void removeTrackedQuery(QuerySpec querySpec) {
        QuerySpec normalizeQuery = normalizeQuery(querySpec);
        this.storageLayer.deleteTrackedQuery(findTrackedQuery(normalizeQuery).f2010id);
        Map map = (Map) this.trackedQueryTree.get(normalizeQuery.getPath());
        map.remove(normalizeQuery.getParams());
        if (map.isEmpty()) {
            this.trackedQueryTree = this.trackedQueryTree.remove(normalizeQuery.getPath());
        }
    }

    public void setQueryActive(QuerySpec querySpec) {
        setQueryActiveFlag(querySpec, true);
    }

    public void setQueryInactive(QuerySpec querySpec) {
        setQueryActiveFlag(querySpec, false);
    }

    private void setQueryActiveFlag(QuerySpec querySpec, boolean z) {
        TrackedQuery trackedQuery;
        QuerySpec normalizeQuery = normalizeQuery(querySpec);
        TrackedQuery findTrackedQuery = findTrackedQuery(normalizeQuery);
        long millis = this.clock.millis();
        if (findTrackedQuery != null) {
            trackedQuery = findTrackedQuery.updateLastUse(millis).setActiveState(z);
        } else {
            long j = this.currentQueryId;
            this.currentQueryId = 1 + j;
            trackedQuery = new TrackedQuery(j, normalizeQuery, millis, false, z);
        }
        saveTrackedQuery(trackedQuery);
    }

    public void setQueryCompleteIfExists(QuerySpec querySpec) {
        TrackedQuery findTrackedQuery = findTrackedQuery(normalizeQuery(querySpec));
        if (findTrackedQuery != null && !findTrackedQuery.complete) {
            saveTrackedQuery(findTrackedQuery.setComplete());
        }
    }

    public void setQueriesComplete(Path path) {
        this.trackedQueryTree.subtree(path).foreach(new TreeVisitor<Map<QueryParams, TrackedQuery>, Void>() {
            public Void onNodeValue(Path path, Map<QueryParams, TrackedQuery> map, Void voidR) {
                for (Entry value : map.entrySet()) {
                    TrackedQuery trackedQuery = (TrackedQuery) value.getValue();
                    if (!trackedQuery.complete) {
                        TrackedQueryManager.this.saveTrackedQuery(trackedQuery.setComplete());
                    }
                }
                return null;
            }
        });
    }

    public boolean isQueryComplete(QuerySpec querySpec) {
        boolean z = true;
        if (includedInDefaultCompleteQuery(querySpec.getPath())) {
            return true;
        }
        if (querySpec.loadsAllData()) {
            return false;
        }
        Map map = (Map) this.trackedQueryTree.get(querySpec.getPath());
        if (map == null || !map.containsKey(querySpec.getParams()) || !((TrackedQuery) map.get(querySpec.getParams())).complete) {
            z = false;
        }
        return z;
    }

    public PruneForest pruneOldQueries(CachePolicy cachePolicy) {
        List queriesMatching = getQueriesMatching(IS_QUERY_PRUNABLE_PREDICATE);
        long calculateCountToPrune = calculateCountToPrune(cachePolicy, (long) queriesMatching.size());
        PruneForest pruneForest = new PruneForest();
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper = this.logger;
            StringBuilder sb = new StringBuilder();
            sb.append("Pruning old queries.  Prunable: ");
            sb.append(queriesMatching.size());
            sb.append(" Count to prune: ");
            sb.append(calculateCountToPrune);
            logWrapper.debug(sb.toString(), new Object[0]);
        }
        Collections.sort(queriesMatching, new Comparator<TrackedQuery>() {
            public int compare(TrackedQuery trackedQuery, TrackedQuery trackedQuery2) {
                return Utilities.compareLongs(trackedQuery.lastUse, trackedQuery2.lastUse);
            }
        });
        PruneForest pruneForest2 = pruneForest;
        for (int i = 0; ((long) i) < calculateCountToPrune; i++) {
            TrackedQuery trackedQuery = (TrackedQuery) queriesMatching.get(i);
            pruneForest2 = pruneForest2.prune(trackedQuery.querySpec.getPath());
            removeTrackedQuery(trackedQuery.querySpec);
        }
        for (int i2 = (int) calculateCountToPrune; i2 < queriesMatching.size(); i2++) {
            pruneForest2 = pruneForest2.keep(((TrackedQuery) queriesMatching.get(i2)).querySpec.getPath());
        }
        List<TrackedQuery> queriesMatching2 = getQueriesMatching(IS_QUERY_UNPRUNABLE_PREDICATE);
        if (this.logger.logsDebug()) {
            LogWrapper logWrapper2 = this.logger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Unprunable queries: ");
            sb2.append(queriesMatching2.size());
            logWrapper2.debug(sb2.toString(), new Object[0]);
        }
        for (TrackedQuery trackedQuery2 : queriesMatching2) {
            pruneForest2 = pruneForest2.keep(trackedQuery2.querySpec.getPath());
        }
        return pruneForest2;
    }

    private static long calculateCountToPrune(CachePolicy cachePolicy, long j) {
        return j - Math.min((long) Math.floor((double) (((float) j) * (1.0f - cachePolicy.getPercentOfQueriesToPruneAtOnce()))), cachePolicy.getMaxNumberOfQueriesToKeep());
    }

    public Set<ChildKey> getKnownCompleteChildren(Path path) {
        HashSet hashSet = new HashSet();
        Set filteredQueryIdsAtPath = filteredQueryIdsAtPath(path);
        if (!filteredQueryIdsAtPath.isEmpty()) {
            hashSet.addAll(this.storageLayer.loadTrackedQueryKeys(filteredQueryIdsAtPath));
        }
        Iterator it = this.trackedQueryTree.subtree(path).getChildren().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            ChildKey childKey = (ChildKey) entry.getKey();
            ImmutableTree immutableTree = (ImmutableTree) entry.getValue();
            if (immutableTree.getValue() != null && HAS_DEFAULT_COMPLETE_PREDICATE.evaluate((Map) immutableTree.getValue())) {
                hashSet.add(childKey);
            }
        }
        return hashSet;
    }

    public void ensureCompleteTrackedQuery(Path path) {
        TrackedQuery trackedQuery;
        if (!includedInDefaultCompleteQuery(path)) {
            QuerySpec defaultQueryAtPath = QuerySpec.defaultQueryAtPath(path);
            TrackedQuery findTrackedQuery = findTrackedQuery(defaultQueryAtPath);
            if (findTrackedQuery == null) {
                long j = this.currentQueryId;
                this.currentQueryId = 1 + j;
                trackedQuery = new TrackedQuery(j, defaultQueryAtPath, this.clock.millis(), true, false);
            } else {
                trackedQuery = findTrackedQuery.setComplete();
            }
            saveTrackedQuery(trackedQuery);
        }
    }

    public boolean hasActiveDefaultQuery(Path path) {
        return this.trackedQueryTree.rootMostValueMatching(path, HAS_ACTIVE_DEFAULT_PREDICATE) != null;
    }

    public long countOfPrunableQueries() {
        return (long) getQueriesMatching(IS_QUERY_PRUNABLE_PREDICATE).size();
    }

    /* access modifiers changed from: 0000 */
    public void verifyCache() {
        List loadTrackedQueries = this.storageLayer.loadTrackedQueries();
        final ArrayList arrayList = new ArrayList();
        this.trackedQueryTree.foreach(new TreeVisitor<Map<QueryParams, TrackedQuery>, Void>() {
            public Void onNodeValue(Path path, Map<QueryParams, TrackedQuery> map, Void voidR) {
                for (TrackedQuery add : map.values()) {
                    arrayList.add(add);
                }
                return null;
            }
        });
        Collections.sort(arrayList, new Comparator<TrackedQuery>() {
            public int compare(TrackedQuery trackedQuery, TrackedQuery trackedQuery2) {
                return Utilities.compareLongs(trackedQuery.f2010id, trackedQuery2.f2010id);
            }
        });
        boolean equals = loadTrackedQueries.equals(arrayList);
        StringBuilder sb = new StringBuilder();
        sb.append("Tracked queries out of sync.  Tracked queries: ");
        sb.append(arrayList);
        sb.append(" Stored queries: ");
        sb.append(loadTrackedQueries);
        Utilities.hardAssert(equals, sb.toString());
    }

    private boolean includedInDefaultCompleteQuery(Path path) {
        return this.trackedQueryTree.findRootMostMatchingPath(path, HAS_DEFAULT_COMPLETE_PREDICATE) != null;
    }

    private Set<Long> filteredQueryIdsAtPath(Path path) {
        HashSet hashSet = new HashSet();
        Map map = (Map) this.trackedQueryTree.get(path);
        if (map != null) {
            for (TrackedQuery trackedQuery : map.values()) {
                if (!trackedQuery.querySpec.loadsAllData()) {
                    hashSet.add(Long.valueOf(trackedQuery.f2010id));
                }
            }
        }
        return hashSet;
    }

    private void cacheTrackedQuery(TrackedQuery trackedQuery) {
        assertValidTrackedQuery(trackedQuery.querySpec);
        Map map = (Map) this.trackedQueryTree.get(trackedQuery.querySpec.getPath());
        if (map == null) {
            map = new HashMap();
            this.trackedQueryTree = this.trackedQueryTree.set(trackedQuery.querySpec.getPath(), map);
        }
        TrackedQuery trackedQuery2 = (TrackedQuery) map.get(trackedQuery.querySpec.getParams());
        Utilities.hardAssert(trackedQuery2 == null || trackedQuery2.f2010id == trackedQuery.f2010id);
        map.put(trackedQuery.querySpec.getParams(), trackedQuery);
    }

    /* access modifiers changed from: private */
    public void saveTrackedQuery(TrackedQuery trackedQuery) {
        cacheTrackedQuery(trackedQuery);
        this.storageLayer.saveTrackedQuery(trackedQuery);
    }

    private List<TrackedQuery> getQueriesMatching(Predicate<TrackedQuery> predicate) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.trackedQueryTree.iterator();
        while (it.hasNext()) {
            for (TrackedQuery trackedQuery : ((Map) ((Entry) it.next()).getValue()).values()) {
                if (predicate.evaluate(trackedQuery)) {
                    arrayList.add(trackedQuery);
                }
            }
        }
        return arrayList;
    }
}
