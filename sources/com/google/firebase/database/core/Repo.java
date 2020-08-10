package com.google.firebase.database.core;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.InternalHelpers;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.Transaction.Handler;
import com.google.firebase.database.Transaction.Result;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.connection.HostInfo;
import com.google.firebase.database.connection.ListenHashProvider;
import com.google.firebase.database.connection.PersistentConnection;
import com.google.firebase.database.connection.PersistentConnection.Delegate;
import com.google.firebase.database.connection.RangeMerge;
import com.google.firebase.database.connection.RequestResultCallback;
import com.google.firebase.database.core.AuthTokenProvider.TokenChangeListener;
import com.google.firebase.database.core.SparseSnapshotTree.SparseSnapshotTreeVisitor;
import com.google.firebase.database.core.SyncTree.CompletionListener;
import com.google.firebase.database.core.SyncTree.ListenProvider;
import com.google.firebase.database.core.persistence.NoopPersistenceManager;
import com.google.firebase.database.core.persistence.PersistenceManager;
import com.google.firebase.database.core.utilities.DefaultClock;
import com.google.firebase.database.core.utilities.DefaultRunLoop;
import com.google.firebase.database.core.utilities.OffsetClock;
import com.google.firebase.database.core.utilities.Tree;
import com.google.firebase.database.core.utilities.Tree.TreeFilter;
import com.google.firebase.database.core.utilities.Tree.TreeVisitor;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.database.core.view.Event;
import com.google.firebase.database.core.view.EventRaiser;
import com.google.firebase.database.core.view.QuerySpec;
import com.google.firebase.database.logging.LogWrapper;
import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.EmptyNode;
import com.google.firebase.database.snapshot.IndexedNode;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: com.google.firebase:firebase-database@@17.0.0 */
public class Repo implements Delegate {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String INTERRUPT_REASON = "repo_interrupt";
    private static final int TRANSACTION_MAX_RETRIES = 25;
    private static final String TRANSACTION_OVERRIDE_BY_SET = "overriddenBySet";
    private static final String TRANSACTION_TOO_MANY_RETRIES = "maxretries";
    /* access modifiers changed from: private */
    public PersistentConnection connection;
    private final Context ctx;
    private final LogWrapper dataLogger;
    public long dataUpdateCount = 0;
    private FirebaseDatabase database;
    private final EventRaiser eventRaiser;
    private boolean hijackHash = false;
    /* access modifiers changed from: private */
    public SnapshotHolder infoData;
    /* access modifiers changed from: private */
    public SyncTree infoSyncTree;
    private boolean loggedTransactionPersistenceWarning = false;
    private long nextWriteId = 1;
    /* access modifiers changed from: private */
    public SparseSnapshotTree onDisconnect;
    /* access modifiers changed from: private */
    public final LogWrapper operationLogger;
    private final RepoInfo repoInfo;
    /* access modifiers changed from: private */
    public final OffsetClock serverClock = new OffsetClock(new DefaultClock(), 0);
    /* access modifiers changed from: private */
    public SyncTree serverSyncTree;
    private final LogWrapper transactionLogger;
    private long transactionOrder = 0;
    /* access modifiers changed from: private */
    public Tree<List<TransactionData>> transactionQueueTree;

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private static class TransactionData implements Comparable<TransactionData> {
        /* access modifiers changed from: private */
        public DatabaseError abortReason;
        /* access modifiers changed from: private */
        public boolean applyLocally;
        /* access modifiers changed from: private */
        public Node currentInputSnapshot;
        /* access modifiers changed from: private */
        public Node currentOutputSnapshotRaw;
        /* access modifiers changed from: private */
        public Node currentOutputSnapshotResolved;
        /* access modifiers changed from: private */
        public long currentWriteId;
        /* access modifiers changed from: private */
        public Handler handler;
        private long order;
        /* access modifiers changed from: private */
        public ValueEventListener outstandingListener;
        /* access modifiers changed from: private */
        public Path path;
        /* access modifiers changed from: private */
        public int retryCount;
        /* access modifiers changed from: private */
        public TransactionStatus status;

        private TransactionData(Path path2, Handler handler2, ValueEventListener valueEventListener, TransactionStatus transactionStatus, boolean z, long j) {
            this.path = path2;
            this.handler = handler2;
            this.outstandingListener = valueEventListener;
            this.status = transactionStatus;
            this.retryCount = 0;
            this.applyLocally = z;
            this.order = j;
            this.abortReason = null;
            this.currentInputSnapshot = null;
            this.currentOutputSnapshotRaw = null;
            this.currentOutputSnapshotResolved = null;
        }

        public int compareTo(TransactionData transactionData) {
            long j = this.order;
            long j2 = transactionData.order;
            if (j < j2) {
                return -1;
            }
            return j == j2 ? 0 : 1;
        }
    }

    /* compiled from: com.google.firebase:firebase-database@@17.0.0 */
    private enum TransactionStatus {
        INITIALIZING,
        RUN,
        SENT,
        COMPLETED,
        SENT_NEEDS_ABORT,
        NEEDS_ABORT
    }

    Repo(RepoInfo repoInfo2, Context context, FirebaseDatabase firebaseDatabase) {
        this.repoInfo = repoInfo2;
        this.ctx = context;
        this.database = firebaseDatabase;
        this.operationLogger = this.ctx.getLogger("RepoOperation");
        this.transactionLogger = this.ctx.getLogger("Transaction");
        this.dataLogger = this.ctx.getLogger("DataOperation");
        this.eventRaiser = new EventRaiser(this.ctx);
        scheduleNow(new Runnable() {
            public void run() {
                Repo.this.deferredInitialization();
            }
        });
    }

    /* access modifiers changed from: private */
    public void deferredInitialization() {
        this.connection = this.ctx.newPersistentConnection(new HostInfo(this.repoInfo.host, this.repoInfo.namespace, this.repoInfo.secure), this);
        this.ctx.getAuthTokenProvider().addTokenChangeListener(((DefaultRunLoop) this.ctx.getRunLoop()).getExecutorService(), new TokenChangeListener() {
            public void onTokenChange() {
                Repo.this.operationLogger.debug("Auth token changed, triggering auth token refresh", new Object[0]);
                Repo.this.connection.refreshAuthToken();
            }

            public void onTokenChange(String str) {
                Repo.this.operationLogger.debug("Auth token changed, triggering auth token refresh", new Object[0]);
                Repo.this.connection.refreshAuthToken(str);
            }
        });
        this.connection.initialize();
        PersistenceManager persistenceManager = this.ctx.getPersistenceManager(this.repoInfo.host);
        this.infoData = new SnapshotHolder();
        this.onDisconnect = new SparseSnapshotTree();
        this.transactionQueueTree = new Tree<>();
        this.infoSyncTree = new SyncTree(this.ctx, new NoopPersistenceManager(), new ListenProvider() {
            public void stopListening(QuerySpec querySpec, Tag tag) {
            }

            public void startListening(final QuerySpec querySpec, Tag tag, ListenHashProvider listenHashProvider, final CompletionListener completionListener) {
                Repo.this.scheduleNow(new Runnable() {
                    public void run() {
                        Node node = Repo.this.infoData.getNode(querySpec.getPath());
                        if (!node.isEmpty()) {
                            Repo.this.postEvents(Repo.this.infoSyncTree.applyServerOverwrite(querySpec.getPath(), node));
                            completionListener.onListenComplete(null);
                        }
                    }
                });
            }
        });
        this.serverSyncTree = new SyncTree(this.ctx, persistenceManager, new ListenProvider() {
            public void startListening(QuerySpec querySpec, Tag tag, ListenHashProvider listenHashProvider, final CompletionListener completionListener) {
                Repo.this.connection.listen(querySpec.getPath().asList(), querySpec.getParams().getWireProtocolParams(), listenHashProvider, tag != null ? Long.valueOf(tag.getTagNumber()) : null, new RequestResultCallback() {
                    public void onRequestResult(String str, String str2) {
                        Repo.this.postEvents(completionListener.onListenComplete(Repo.fromErrorCode(str, str2)));
                    }
                });
            }

            public void stopListening(QuerySpec querySpec, Tag tag) {
                Repo.this.connection.unlisten(querySpec.getPath().asList(), querySpec.getParams().getWireProtocolParams());
            }
        });
        restoreWrites(persistenceManager);
        ChildKey childKey = Constants.DOT_INFO_AUTHENTICATED;
        Boolean valueOf = Boolean.valueOf(false);
        updateInfo(childKey, valueOf);
        updateInfo(Constants.DOT_INFO_CONNECTED, valueOf);
    }

    private void restoreWrites(PersistenceManager persistenceManager) {
        List<UserWriteRecord> loadUserWrites = persistenceManager.loadUserWrites();
        Map generateServerValues = ServerValues.generateServerValues(this.serverClock);
        long j = Long.MIN_VALUE;
        for (final UserWriteRecord userWriteRecord : loadUserWrites) {
            C35675 r4 = new RequestResultCallback() {
                public void onRequestResult(String str, String str2) {
                    DatabaseError access$600 = Repo.fromErrorCode(str, str2);
                    Repo.this.warnIfWriteFailed("Persisted write", userWriteRecord.getPath(), access$600);
                    Repo.this.ackWriteAndRerunTransactions(userWriteRecord.getWriteId(), userWriteRecord.getPath(), access$600);
                }
            };
            if (j < userWriteRecord.getWriteId()) {
                j = userWriteRecord.getWriteId();
                this.nextWriteId = userWriteRecord.getWriteId() + 1;
                if (userWriteRecord.isOverwrite()) {
                    if (this.operationLogger.logsDebug()) {
                        LogWrapper logWrapper = this.operationLogger;
                        StringBuilder sb = new StringBuilder();
                        sb.append("Restoring overwrite with id ");
                        sb.append(userWriteRecord.getWriteId());
                        logWrapper.debug(sb.toString(), new Object[0]);
                    }
                    this.connection.put(userWriteRecord.getPath().asList(), userWriteRecord.getOverwrite().getValue(true), r4);
                    this.serverSyncTree.applyUserOverwrite(userWriteRecord.getPath(), userWriteRecord.getOverwrite(), ServerValues.resolveDeferredValueSnapshot(userWriteRecord.getOverwrite(), generateServerValues), userWriteRecord.getWriteId(), true, false);
                } else {
                    if (this.operationLogger.logsDebug()) {
                        LogWrapper logWrapper2 = this.operationLogger;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Restoring merge with id ");
                        sb2.append(userWriteRecord.getWriteId());
                        logWrapper2.debug(sb2.toString(), new Object[0]);
                    }
                    this.connection.merge(userWriteRecord.getPath().asList(), userWriteRecord.getMerge().getValue(true), r4);
                    this.serverSyncTree.applyUserMerge(userWriteRecord.getPath(), userWriteRecord.getMerge(), ServerValues.resolveDeferredValueMerge(userWriteRecord.getMerge(), generateServerValues), userWriteRecord.getWriteId(), false);
                }
            } else {
                throw new IllegalStateException("Write ids were not in order.");
            }
        }
    }

    public FirebaseDatabase getDatabase() {
        return this.database;
    }

    public String toString() {
        return this.repoInfo.toString();
    }

    public RepoInfo getRepoInfo() {
        return this.repoInfo;
    }

    public void scheduleNow(Runnable runnable) {
        this.ctx.requireStarted();
        this.ctx.getRunLoop().scheduleNow(runnable);
    }

    public void postEvent(Runnable runnable) {
        this.ctx.requireStarted();
        this.ctx.getEventTarget().postEvent(runnable);
    }

    /* access modifiers changed from: private */
    public void postEvents(List<? extends Event> list) {
        if (!list.isEmpty()) {
            this.eventRaiser.raiseEvents(list);
        }
    }

    public long getServerTime() {
        return this.serverClock.millis();
    }

    /* access modifiers changed from: 0000 */
    public boolean hasListeners() {
        return !this.infoSyncTree.isEmpty() || !this.serverSyncTree.isEmpty();
    }

    public void onDataUpdate(List<String> list, Object obj, boolean z, Long l) {
        List list2;
        Path path = new Path(list);
        String str = "onDataUpdate: ";
        if (this.operationLogger.logsDebug()) {
            LogWrapper logWrapper = this.operationLogger;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(path);
            logWrapper.debug(sb.toString(), new Object[0]);
        }
        if (this.dataLogger.logsDebug()) {
            LogWrapper logWrapper2 = this.operationLogger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(path);
            sb2.append(" ");
            sb2.append(obj);
            logWrapper2.debug(sb2.toString(), new Object[0]);
        }
        this.dataUpdateCount++;
        if (l != null) {
            try {
                Tag tag = new Tag(l.longValue());
                if (z) {
                    HashMap hashMap = new HashMap();
                    for (Entry entry : ((Map) obj).entrySet()) {
                        hashMap.put(new Path((String) entry.getKey()), NodeUtilities.NodeFromJSON(entry.getValue()));
                    }
                    list2 = this.serverSyncTree.applyTaggedQueryMerge(path, hashMap, tag);
                } else {
                    list2 = this.serverSyncTree.applyTaggedQueryOverwrite(path, NodeUtilities.NodeFromJSON(obj), tag);
                }
            } catch (DatabaseException e) {
                this.operationLogger.error("FIREBASE INTERNAL ERROR", e);
                return;
            }
        } else if (z) {
            HashMap hashMap2 = new HashMap();
            for (Entry entry2 : ((Map) obj).entrySet()) {
                hashMap2.put(new Path((String) entry2.getKey()), NodeUtilities.NodeFromJSON(entry2.getValue()));
            }
            list2 = this.serverSyncTree.applyServerMerge(path, hashMap2);
        } else {
            list2 = this.serverSyncTree.applyServerOverwrite(path, NodeUtilities.NodeFromJSON(obj));
        }
        if (list2.size() > 0) {
            rerunTransactions(path);
        }
        postEvents(list2);
    }

    public void onRangeMergeUpdate(List<String> list, List<RangeMerge> list2, Long l) {
        List list3;
        Path path = new Path(list);
        String str = "onRangeMergeUpdate: ";
        if (this.operationLogger.logsDebug()) {
            LogWrapper logWrapper = this.operationLogger;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(path);
            logWrapper.debug(sb.toString(), new Object[0]);
        }
        if (this.dataLogger.logsDebug()) {
            LogWrapper logWrapper2 = this.operationLogger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(path);
            sb2.append(" ");
            sb2.append(list2);
            logWrapper2.debug(sb2.toString(), new Object[0]);
        }
        this.dataUpdateCount++;
        ArrayList arrayList = new ArrayList(list2.size());
        for (RangeMerge rangeMerge : list2) {
            arrayList.add(new com.google.firebase.database.snapshot.RangeMerge(rangeMerge));
        }
        if (l != null) {
            list3 = this.serverSyncTree.applyTaggedRangeMerges(path, arrayList, new Tag(l.longValue()));
        } else {
            list3 = this.serverSyncTree.applyServerRangeMerges(path, arrayList);
        }
        if (list3.size() > 0) {
            rerunTransactions(path);
        }
        postEvents(list3);
    }

    /* access modifiers changed from: 0000 */
    public void callOnComplete(final DatabaseReference.CompletionListener completionListener, final DatabaseError databaseError, Path path) {
        final DatabaseReference databaseReference;
        if (completionListener != null) {
            ChildKey back = path.getBack();
            if (back == null || !back.isPriorityChildName()) {
                databaseReference = InternalHelpers.createReference(this, path);
            } else {
                databaseReference = InternalHelpers.createReference(this, path.getParent());
            }
            postEvent(new Runnable() {
                public void run() {
                    completionListener.onComplete(databaseError, databaseReference);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void ackWriteAndRerunTransactions(long j, Path path, DatabaseError databaseError) {
        if (databaseError == null || databaseError.getCode() != -25) {
            List ackUserWrite = this.serverSyncTree.ackUserWrite(j, !(databaseError == null), true, this.serverClock);
            if (ackUserWrite.size() > 0) {
                rerunTransactions(path);
            }
            postEvents(ackUserWrite);
        }
    }

    public void setValue(Path path, Node node, DatabaseReference.CompletionListener completionListener) {
        String str = "set: ";
        if (this.operationLogger.logsDebug()) {
            LogWrapper logWrapper = this.operationLogger;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(path);
            logWrapper.debug(sb.toString(), new Object[0]);
        }
        if (this.dataLogger.logsDebug()) {
            LogWrapper logWrapper2 = this.dataLogger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(path);
            sb2.append(" ");
            sb2.append(node);
            logWrapper2.debug(sb2.toString(), new Object[0]);
        }
        Node resolveDeferredValueSnapshot = ServerValues.resolveDeferredValueSnapshot(node, ServerValues.generateServerValues(this.serverClock));
        long nextWriteId2 = getNextWriteId();
        postEvents(this.serverSyncTree.applyUserOverwrite(path, node, resolveDeferredValueSnapshot, nextWriteId2, true, true));
        PersistentConnection persistentConnection = this.connection;
        List asList = path.asList();
        Object value = node.getValue(true);
        final Path path2 = path;
        final long j = nextWriteId2;
        final DatabaseReference.CompletionListener completionListener2 = completionListener;
        C35697 r5 = new RequestResultCallback() {
            public void onRequestResult(String str, String str2) {
                DatabaseError access$600 = Repo.fromErrorCode(str, str2);
                Repo.this.warnIfWriteFailed("setValue", path2, access$600);
                Repo.this.ackWriteAndRerunTransactions(j, path2, access$600);
                Repo.this.callOnComplete(completionListener2, access$600, path2);
            }
        };
        persistentConnection.put(asList, value, r5);
        rerunTransactions(abortTransactions(path, -9));
    }

    public void updateChildren(Path path, CompoundWrite compoundWrite, DatabaseReference.CompletionListener completionListener, Map<String, Object> map) {
        String str = "update: ";
        if (this.operationLogger.logsDebug()) {
            LogWrapper logWrapper = this.operationLogger;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(path);
            logWrapper.debug(sb.toString(), new Object[0]);
        }
        if (this.dataLogger.logsDebug()) {
            LogWrapper logWrapper2 = this.dataLogger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(path);
            sb2.append(" ");
            sb2.append(map);
            logWrapper2.debug(sb2.toString(), new Object[0]);
        }
        if (compoundWrite.isEmpty()) {
            if (this.operationLogger.logsDebug()) {
                this.operationLogger.debug("update called with no changes. No-op", new Object[0]);
            }
            callOnComplete(completionListener, null, path);
            return;
        }
        CompoundWrite resolveDeferredValueMerge = ServerValues.resolveDeferredValueMerge(compoundWrite, ServerValues.generateServerValues(this.serverClock));
        final long nextWriteId2 = getNextWriteId();
        postEvents(this.serverSyncTree.applyUserMerge(path, compoundWrite, resolveDeferredValueMerge, nextWriteId2, true));
        PersistentConnection persistentConnection = this.connection;
        List asList = path.asList();
        final Path path2 = path;
        final DatabaseReference.CompletionListener completionListener2 = completionListener;
        C35708 r5 = new RequestResultCallback() {
            public void onRequestResult(String str, String str2) {
                DatabaseError access$600 = Repo.fromErrorCode(str, str2);
                Repo.this.warnIfWriteFailed("updateChildren", path2, access$600);
                Repo.this.ackWriteAndRerunTransactions(nextWriteId2, path2, access$600);
                Repo.this.callOnComplete(completionListener2, access$600, path2);
            }
        };
        persistentConnection.merge(asList, map, r5);
        Iterator it = compoundWrite.iterator();
        while (it.hasNext()) {
            rerunTransactions(abortTransactions(path.child((Path) ((Entry) it.next()).getKey()), -9));
        }
    }

    public void purgeOutstandingWrites() {
        if (this.operationLogger.logsDebug()) {
            this.operationLogger.debug("Purging writes", new Object[0]);
        }
        postEvents(this.serverSyncTree.removeAllWrites());
        abortTransactions(Path.getEmptyPath(), -25);
        this.connection.purgeOutstandingWrites();
    }

    public void removeEventCallback(EventRegistration eventRegistration) {
        List list;
        if (Constants.DOT_INFO.equals(eventRegistration.getQuerySpec().getPath().getFront())) {
            list = this.infoSyncTree.removeEventRegistration(eventRegistration);
        } else {
            list = this.serverSyncTree.removeEventRegistration(eventRegistration);
        }
        postEvents(list);
    }

    public void onDisconnectSetValue(final Path path, final Node node, final DatabaseReference.CompletionListener completionListener) {
        this.connection.onDisconnectPut(path.asList(), node.getValue(true), new RequestResultCallback() {
            public void onRequestResult(String str, String str2) {
                DatabaseError access$600 = Repo.fromErrorCode(str, str2);
                Repo.this.warnIfWriteFailed("onDisconnect().setValue", path, access$600);
                if (access$600 == null) {
                    Repo.this.onDisconnect.remember(path, node);
                }
                Repo.this.callOnComplete(completionListener, access$600, path);
            }
        });
    }

    public void onDisconnectUpdate(final Path path, final Map<Path, Node> map, final DatabaseReference.CompletionListener completionListener, Map<String, Object> map2) {
        this.connection.onDisconnectMerge(path.asList(), map2, new RequestResultCallback() {
            public void onRequestResult(String str, String str2) {
                DatabaseError access$600 = Repo.fromErrorCode(str, str2);
                Repo.this.warnIfWriteFailed("onDisconnect().updateChildren", path, access$600);
                if (access$600 == null) {
                    for (Entry entry : map.entrySet()) {
                        Repo.this.onDisconnect.remember(path.child((Path) entry.getKey()), (Node) entry.getValue());
                    }
                }
                Repo.this.callOnComplete(completionListener, access$600, path);
            }
        });
    }

    public void onDisconnectCancel(final Path path, final DatabaseReference.CompletionListener completionListener) {
        this.connection.onDisconnectCancel(path.asList(), new RequestResultCallback() {
            public void onRequestResult(String str, String str2) {
                DatabaseError access$600 = Repo.fromErrorCode(str, str2);
                if (access$600 == null) {
                    Repo.this.onDisconnect.forget(path);
                }
                Repo.this.callOnComplete(completionListener, access$600, path);
            }
        });
    }

    public void onConnect() {
        onServerInfoUpdate(Constants.DOT_INFO_CONNECTED, Boolean.valueOf(true));
    }

    public void onDisconnect() {
        onServerInfoUpdate(Constants.DOT_INFO_CONNECTED, Boolean.valueOf(false));
        runOnDisconnectEvents();
    }

    public void onAuthStatus(boolean z) {
        onServerInfoUpdate(Constants.DOT_INFO_AUTHENTICATED, Boolean.valueOf(z));
    }

    public void onServerInfoUpdate(ChildKey childKey, Object obj) {
        updateInfo(childKey, obj);
    }

    public void onServerInfoUpdate(Map<String, Object> map) {
        for (Entry entry : map.entrySet()) {
            updateInfo(ChildKey.fromString((String) entry.getKey()), entry.getValue());
        }
    }

    /* access modifiers changed from: 0000 */
    public void interrupt() {
        this.connection.interrupt(INTERRUPT_REASON);
    }

    /* access modifiers changed from: 0000 */
    public void resume() {
        this.connection.resume(INTERRUPT_REASON);
    }

    public void addEventCallback(EventRegistration eventRegistration) {
        List list;
        ChildKey front = eventRegistration.getQuerySpec().getPath().getFront();
        if (front == null || !front.equals(Constants.DOT_INFO)) {
            list = this.serverSyncTree.addEventRegistration(eventRegistration);
        } else {
            list = this.infoSyncTree.addEventRegistration(eventRegistration);
        }
        postEvents(list);
    }

    public void keepSynced(QuerySpec querySpec, boolean z) {
        this.serverSyncTree.keepSynced(querySpec, z);
    }

    /* access modifiers changed from: 0000 */
    public PersistentConnection getConnection() {
        return this.connection;
    }

    private void updateInfo(ChildKey childKey, Object obj) {
        if (childKey.equals(Constants.DOT_INFO_SERVERTIME_OFFSET)) {
            this.serverClock.setOffset(((Long) obj).longValue());
        }
        Path path = new Path(Constants.DOT_INFO, childKey);
        try {
            Node NodeFromJSON = NodeUtilities.NodeFromJSON(obj);
            this.infoData.update(path, NodeFromJSON);
            postEvents(this.infoSyncTree.applyServerOverwrite(path, NodeFromJSON));
        } catch (DatabaseException e) {
            this.operationLogger.error("Failed to parse info update", e);
        }
    }

    private long getNextWriteId() {
        long j = this.nextWriteId;
        this.nextWriteId = 1 + j;
        return j;
    }

    private void runOnDisconnectEvents() {
        SparseSnapshotTree resolveDeferredValueTree = ServerValues.resolveDeferredValueTree(this.onDisconnect, ServerValues.generateServerValues(this.serverClock));
        final ArrayList arrayList = new ArrayList();
        resolveDeferredValueTree.forEachTree(Path.getEmptyPath(), new SparseSnapshotTreeVisitor() {
            public void visitTree(Path path, Node node) {
                arrayList.addAll(Repo.this.serverSyncTree.applyServerOverwrite(path, node));
                Repo.this.rerunTransactions(Repo.this.abortTransactions(path, -9));
            }
        });
        this.onDisconnect = new SparseSnapshotTree();
        postEvents(arrayList);
    }

    /* access modifiers changed from: private */
    public void warnIfWriteFailed(String str, Path path, DatabaseError databaseError) {
        if (databaseError != null && databaseError.getCode() != -1 && databaseError.getCode() != -25) {
            LogWrapper logWrapper = this.operationLogger;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" at ");
            sb.append(path.toString());
            sb.append(" failed: ");
            sb.append(databaseError.toString());
            logWrapper.warn(sb.toString());
        }
    }

    public void startTransaction(Path path, final Handler handler, boolean z) {
        final DatabaseError databaseError;
        Result result;
        String str = "transaction: ";
        if (this.operationLogger.logsDebug()) {
            LogWrapper logWrapper = this.operationLogger;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(path);
            logWrapper.debug(sb.toString(), new Object[0]);
        }
        if (this.dataLogger.logsDebug()) {
            LogWrapper logWrapper2 = this.operationLogger;
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(path);
            logWrapper2.debug(sb2.toString(), new Object[0]);
        }
        if (this.ctx.isPersistenceEnabled() && !this.loggedTransactionPersistenceWarning) {
            this.loggedTransactionPersistenceWarning = true;
            this.transactionLogger.info("runTransaction() usage detected while persistence is enabled. Please be aware that transactions *will not* be persisted across database restarts.  See https://www.firebase.com/docs/android/guide/offline-capabilities.html#section-handling-transactions-offline for more details.");
        }
        DatabaseReference createReference = InternalHelpers.createReference(this, path);
        C355013 r4 = new ValueEventListener() {
            public void onCancelled(DatabaseError databaseError) {
            }

            public void onDataChange(DataSnapshot dataSnapshot) {
            }
        };
        addEventCallback(new ValueEventRegistration(this, r4, createReference.getSpec()));
        TransactionData transactionData = new TransactionData(path, handler, r4, TransactionStatus.INITIALIZING, z, nextTransactionOrder());
        Node latestState = getLatestState(path);
        transactionData.currentInputSnapshot = latestState;
        try {
            result = handler.doTransaction(InternalHelpers.createMutableData(latestState));
            if (result != null) {
                databaseError = null;
                if (!result.isSuccess()) {
                    transactionData.currentOutputSnapshotRaw = null;
                    transactionData.currentOutputSnapshotResolved = null;
                    final DataSnapshot createDataSnapshot = InternalHelpers.createDataSnapshot(createReference, IndexedNode.from(transactionData.currentInputSnapshot));
                    postEvent(new Runnable() {
                        public void run() {
                            handler.onComplete(databaseError, false, createDataSnapshot);
                        }
                    });
                    return;
                }
                transactionData.status = TransactionStatus.RUN;
                Tree subTree = this.transactionQueueTree.subTree(path);
                List list = (List) subTree.getValue();
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(transactionData);
                subTree.setValue(list);
                Map generateServerValues = ServerValues.generateServerValues(this.serverClock);
                Node node = result.getNode();
                Node resolveDeferredValueSnapshot = ServerValues.resolveDeferredValueSnapshot(node, generateServerValues);
                transactionData.currentOutputSnapshotRaw = node;
                transactionData.currentOutputSnapshotResolved = resolveDeferredValueSnapshot;
                transactionData.currentWriteId = getNextWriteId();
                postEvents(this.serverSyncTree.applyUserOverwrite(path, node, resolveDeferredValueSnapshot, transactionData.currentWriteId, z, false));
                sendAllReadyTransactions();
                return;
            }
            throw new NullPointerException("Transaction returned null as result");
        } catch (Throwable th) {
            this.operationLogger.error("Caught Throwable.", th);
            DatabaseError fromException = DatabaseError.fromException(th);
            databaseError = fromException;
            result = Transaction.abort();
        }
    }

    private Node getLatestState(Path path) {
        return getLatestState(path, new ArrayList());
    }

    private Node getLatestState(Path path, List<Long> list) {
        Node calcCompleteEventCache = this.serverSyncTree.calcCompleteEventCache(path, list);
        return calcCompleteEventCache == null ? EmptyNode.Empty() : calcCompleteEventCache;
    }

    public void setHijackHash(boolean z) {
        this.hijackHash = z;
    }

    /* access modifiers changed from: private */
    public void sendAllReadyTransactions() {
        Tree<List<TransactionData>> tree = this.transactionQueueTree;
        pruneCompletedTransactions(tree);
        sendReadyTransactions(tree);
    }

    /* access modifiers changed from: private */
    public void sendReadyTransactions(Tree<List<TransactionData>> tree) {
        if (((List) tree.getValue()) != null) {
            List buildTransactionQueue = buildTransactionQueue(tree);
            Boolean valueOf = Boolean.valueOf(true);
            Iterator it = buildTransactionQueue.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((TransactionData) it.next()).status != TransactionStatus.RUN) {
                        valueOf = Boolean.valueOf(false);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (valueOf.booleanValue()) {
                sendTransactionQueue(buildTransactionQueue, tree.getPath());
            }
        } else if (tree.hasChildren()) {
            tree.forEachChild(new TreeVisitor<List<TransactionData>>() {
                public void visitTree(Tree<List<TransactionData>> tree) {
                    Repo.this.sendReadyTransactions(tree);
                }
            });
        }
    }

    private void sendTransactionQueue(final List<TransactionData> list, final Path path) {
        ArrayList arrayList = new ArrayList();
        for (TransactionData access$1800 : list) {
            arrayList.add(Long.valueOf(access$1800.currentWriteId));
        }
        Node latestState = getLatestState(path, arrayList);
        String hash = !this.hijackHash ? latestState.getHash() : "badhash";
        for (TransactionData transactionData : list) {
            transactionData.status = TransactionStatus.SENT;
            transactionData.retryCount = transactionData.retryCount + 1;
            latestState = latestState.updateChild(Path.getRelative(path, transactionData.path), transactionData.currentOutputSnapshotRaw);
        }
        this.connection.compareAndPut(path.asList(), latestState.getValue(true), hash, new RequestResultCallback() {
            public void onRequestResult(String str, String str2) {
                DatabaseError access$600 = Repo.fromErrorCode(str, str2);
                Repo.this.warnIfWriteFailed("Transaction", path, access$600);
                ArrayList arrayList = new ArrayList();
                if (access$600 == null) {
                    ArrayList arrayList2 = new ArrayList();
                    for (final TransactionData transactionData : list) {
                        transactionData.status = TransactionStatus.COMPLETED;
                        arrayList.addAll(Repo.this.serverSyncTree.ackUserWrite(transactionData.currentWriteId, false, false, Repo.this.serverClock));
                        final DataSnapshot createDataSnapshot = InternalHelpers.createDataSnapshot(InternalHelpers.createReference(this, transactionData.path), IndexedNode.from(transactionData.currentOutputSnapshotResolved));
                        arrayList2.add(new Runnable() {
                            public void run() {
                                transactionData.handler.onComplete(null, true, createDataSnapshot);
                            }
                        });
                        Repo repo = Repo.this;
                        repo.removeEventCallback(new ValueEventRegistration(repo, transactionData.outstandingListener, QuerySpec.defaultQueryAtPath(transactionData.path)));
                    }
                    Repo repo2 = Repo.this;
                    repo2.pruneCompletedTransactions(repo2.transactionQueueTree.subTree(path));
                    Repo.this.sendAllReadyTransactions();
                    this.postEvents(arrayList);
                    for (int i = 0; i < arrayList2.size(); i++) {
                        Repo.this.postEvent((Runnable) arrayList2.get(i));
                    }
                    return;
                }
                if (access$600.getCode() == -1) {
                    for (TransactionData transactionData2 : list) {
                        if (transactionData2.status == TransactionStatus.SENT_NEEDS_ABORT) {
                            transactionData2.status = TransactionStatus.NEEDS_ABORT;
                        } else {
                            transactionData2.status = TransactionStatus.RUN;
                        }
                    }
                } else {
                    for (TransactionData transactionData3 : list) {
                        transactionData3.status = TransactionStatus.NEEDS_ABORT;
                        transactionData3.abortReason = access$600;
                    }
                }
                Repo.this.rerunTransactions(path);
            }
        });
    }

    /* access modifiers changed from: private */
    public void pruneCompletedTransactions(Tree<List<TransactionData>> tree) {
        List list = (List) tree.getValue();
        if (list != null) {
            int i = 0;
            while (i < list.size()) {
                if (((TransactionData) list.get(i)).status == TransactionStatus.COMPLETED) {
                    list.remove(i);
                } else {
                    i++;
                }
            }
            if (list.size() > 0) {
                tree.setValue(list);
            } else {
                tree.setValue(null);
            }
        }
        tree.forEachChild(new TreeVisitor<List<TransactionData>>() {
            public void visitTree(Tree<List<TransactionData>> tree) {
                Repo.this.pruneCompletedTransactions(tree);
            }
        });
    }

    private long nextTransactionOrder() {
        long j = this.transactionOrder;
        this.transactionOrder = 1 + j;
        return j;
    }

    /* access modifiers changed from: private */
    public Path rerunTransactions(Path path) {
        Tree ancestorTransactionNode = getAncestorTransactionNode(path);
        Path path2 = ancestorTransactionNode.getPath();
        rerunTransactionQueue(buildTransactionQueue(ancestorTransactionNode), path2);
        return path2;
    }

    private void rerunTransactionQueue(List<TransactionData> list, Path path) {
        int i;
        DatabaseError databaseError;
        Result result;
        if (!list.isEmpty()) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (TransactionData access$1800 : list) {
                arrayList2.add(Long.valueOf(access$1800.currentWriteId));
            }
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                final TransactionData transactionData = (TransactionData) it.next();
                Path.getRelative(path, transactionData.path);
                ArrayList arrayList3 = new ArrayList();
                final DatabaseError databaseError2 = null;
                boolean z = true;
                if (transactionData.status == TransactionStatus.NEEDS_ABORT) {
                    databaseError2 = transactionData.abortReason;
                    if (databaseError2.getCode() != -25) {
                        arrayList3.addAll(this.serverSyncTree.ackUserWrite(transactionData.currentWriteId, true, false, this.serverClock));
                    }
                } else {
                    if (transactionData.status == TransactionStatus.RUN) {
                        if (transactionData.retryCount >= 25) {
                            databaseError2 = DatabaseError.fromStatus(TRANSACTION_TOO_MANY_RETRIES);
                            arrayList3.addAll(this.serverSyncTree.ackUserWrite(transactionData.currentWriteId, true, false, this.serverClock));
                        } else {
                            Node latestState = getLatestState(transactionData.path, arrayList2);
                            transactionData.currentInputSnapshot = latestState;
                            try {
                                result = transactionData.handler.doTransaction(InternalHelpers.createMutableData(latestState));
                                databaseError = null;
                            } catch (Throwable th) {
                                this.operationLogger.error("Caught Throwable.", th);
                                DatabaseError fromException = DatabaseError.fromException(th);
                                databaseError = fromException;
                                result = Transaction.abort();
                            }
                            if (result.isSuccess()) {
                                Long valueOf = Long.valueOf(transactionData.currentWriteId);
                                Map generateServerValues = ServerValues.generateServerValues(this.serverClock);
                                Node node = result.getNode();
                                Node resolveDeferredValueSnapshot = ServerValues.resolveDeferredValueSnapshot(node, generateServerValues);
                                transactionData.currentOutputSnapshotRaw = node;
                                transactionData.currentOutputSnapshotResolved = resolveDeferredValueSnapshot;
                                transactionData.currentWriteId = getNextWriteId();
                                arrayList2.remove(valueOf);
                                arrayList3.addAll(this.serverSyncTree.applyUserOverwrite(transactionData.path, node, resolveDeferredValueSnapshot, transactionData.currentWriteId, transactionData.applyLocally, false));
                                arrayList3.addAll(this.serverSyncTree.ackUserWrite(valueOf.longValue(), true, false, this.serverClock));
                            } else {
                                arrayList3.addAll(this.serverSyncTree.ackUserWrite(transactionData.currentWriteId, true, false, this.serverClock));
                                databaseError2 = databaseError;
                            }
                        }
                    }
                    z = false;
                }
                postEvents(arrayList3);
                if (z) {
                    transactionData.status = TransactionStatus.COMPLETED;
                    final DataSnapshot createDataSnapshot = InternalHelpers.createDataSnapshot(InternalHelpers.createReference(this, transactionData.path), IndexedNode.from(transactionData.currentInputSnapshot));
                    scheduleNow(new Runnable() {
                        public void run() {
                            Repo repo = Repo.this;
                            repo.removeEventCallback(new ValueEventRegistration(repo, transactionData.outstandingListener, QuerySpec.defaultQueryAtPath(transactionData.path)));
                        }
                    });
                    arrayList.add(new Runnable() {
                        public void run() {
                            transactionData.handler.onComplete(databaseError2, false, createDataSnapshot);
                        }
                    });
                }
            }
            pruneCompletedTransactions(this.transactionQueueTree);
            for (i = 0; i < arrayList.size(); i++) {
                postEvent((Runnable) arrayList.get(i));
            }
            sendAllReadyTransactions();
        }
    }

    private Tree<List<TransactionData>> getAncestorTransactionNode(Path path) {
        Tree<List<TransactionData>> tree = this.transactionQueueTree;
        while (!path.isEmpty() && tree.getValue() == null) {
            tree = tree.subTree(new Path(path.getFront()));
            path = path.popFront();
        }
        return tree;
    }

    private List<TransactionData> buildTransactionQueue(Tree<List<TransactionData>> tree) {
        ArrayList arrayList = new ArrayList();
        aggregateTransactionQueues(arrayList, tree);
        Collections.sort(arrayList);
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void aggregateTransactionQueues(final List<TransactionData> list, Tree<List<TransactionData>> tree) {
        List list2 = (List) tree.getValue();
        if (list2 != null) {
            list.addAll(list2);
        }
        tree.forEachChild(new TreeVisitor<List<TransactionData>>() {
            public void visitTree(Tree<List<TransactionData>> tree) {
                Repo.this.aggregateTransactionQueues(list, tree);
            }
        });
    }

    /* access modifiers changed from: private */
    public Path abortTransactions(Path path, final int i) {
        Path path2 = getAncestorTransactionNode(path).getPath();
        if (this.transactionLogger.logsDebug()) {
            LogWrapper logWrapper = this.operationLogger;
            StringBuilder sb = new StringBuilder();
            sb.append("Aborting transactions for path: ");
            sb.append(path);
            sb.append(". Affected: ");
            sb.append(path2);
            logWrapper.debug(sb.toString(), new Object[0]);
        }
        Tree subTree = this.transactionQueueTree.subTree(path);
        subTree.forEachAncestor(new TreeFilter<List<TransactionData>>() {
            public boolean filterTreeNode(Tree<List<TransactionData>> tree) {
                Repo.this.abortTransactionsAtNode(tree, i);
                return false;
            }
        });
        abortTransactionsAtNode(subTree, i);
        subTree.forEachDescendant(new TreeVisitor<List<TransactionData>>() {
            public void visitTree(Tree<List<TransactionData>> tree) {
                Repo.this.abortTransactionsAtNode(tree, i);
            }
        });
        return path2;
    }

    /* access modifiers changed from: private */
    public void abortTransactionsAtNode(Tree<List<TransactionData>> tree, int i) {
        final DatabaseError databaseError;
        Tree<List<TransactionData>> tree2 = tree;
        int i2 = i;
        List list = (List) tree.getValue();
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            ArrayList<Runnable> arrayList2 = new ArrayList<>();
            String str = "Unknown transaction abort reason: ";
            if (i2 == -9) {
                databaseError = DatabaseError.fromStatus(TRANSACTION_OVERRIDE_BY_SET);
            } else {
                boolean z = i2 == -25;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(i2);
                Utilities.hardAssert(z, sb.toString());
                databaseError = DatabaseError.fromCode(-25);
            }
            int i3 = -1;
            for (int i4 = 0; i4 < list.size(); i4++) {
                final TransactionData transactionData = (TransactionData) list.get(i4);
                if (transactionData.status != TransactionStatus.SENT_NEEDS_ABORT) {
                    if (transactionData.status == TransactionStatus.SENT) {
                        transactionData.status = TransactionStatus.SENT_NEEDS_ABORT;
                        transactionData.abortReason = databaseError;
                        i3 = i4;
                    } else {
                        removeEventCallback(new ValueEventRegistration(this, transactionData.outstandingListener, QuerySpec.defaultQueryAtPath(transactionData.path)));
                        if (i2 == -9) {
                            arrayList.addAll(this.serverSyncTree.ackUserWrite(transactionData.currentWriteId, true, false, this.serverClock));
                        } else {
                            boolean z2 = i2 == -25;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str);
                            sb2.append(i2);
                            Utilities.hardAssert(z2, sb2.toString());
                        }
                        arrayList2.add(new Runnable() {
                            public void run() {
                                transactionData.handler.onComplete(databaseError, false, null);
                            }
                        });
                    }
                }
            }
            if (i3 == -1) {
                tree2.setValue(null);
            } else {
                tree2.setValue(list.subList(0, i3 + 1));
            }
            postEvents(arrayList);
            for (Runnable postEvent : arrayList2) {
                postEvent(postEvent);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public SyncTree getServerSyncTree() {
        return this.serverSyncTree;
    }

    /* access modifiers changed from: 0000 */
    public SyncTree getInfoSyncTree() {
        return this.infoSyncTree;
    }

    /* access modifiers changed from: private */
    public static DatabaseError fromErrorCode(String str, String str2) {
        if (str != null) {
            return DatabaseError.fromStatus(str, str2);
        }
        return null;
    }
}
