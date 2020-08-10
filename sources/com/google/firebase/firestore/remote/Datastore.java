package com.google.firebase.firestore.remote;

import android.content.Context;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.core.DatabaseInfo;
import com.google.firebase.firestore.model.DocumentKey;
import com.google.firebase.firestore.model.MaybeDocument;
import com.google.firebase.firestore.model.SnapshotVersion;
import com.google.firebase.firestore.model.mutation.Mutation;
import com.google.firebase.firestore.model.mutation.MutationResult;
import com.google.firebase.firestore.remote.WriteStream.Callback;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.FirestoreChannel;
import com.google.firebase.firestore.util.Supplier;
import com.google.firestore.p050v1.BatchGetDocumentsRequest;
import com.google.firestore.p050v1.CommitRequest;
import com.google.firestore.p050v1.CommitRequest.Builder;
import com.google.firestore.p050v1.CommitResponse;
import com.google.firestore.p050v1.FirestoreGrpc;
import com.mobiroller.constants.YoutubeConstants.YoutubeRequestParams;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import p043io.grpc.ManagedChannelBuilder;
import p043io.grpc.Status;
import p043io.grpc.android.AndroidChannelBuilder;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Datastore {
    public static final Set<String> WHITE_LISTED_HEADERS = new HashSet(Arrays.asList(new String[]{YoutubeRequestParams.req_search_order, "x-google-backends", "x-google-netmon-label", "x-google-service", "x-google-gfe-request-trace"}));
    private static Supplier<ManagedChannelBuilder<?>> overrideChannelBuilderSupplier;
    private final FirestoreChannel channel;
    private final DatabaseInfo databaseInfo;
    private final RemoteSerializer serializer;
    private final AsyncQueue workerQueue;

    public static void overrideChannelBuilder(Supplier<ManagedChannelBuilder<?>> supplier) {
        overrideChannelBuilderSupplier = supplier;
    }

    public Datastore(DatabaseInfo databaseInfo2, AsyncQueue asyncQueue, CredentialsProvider credentialsProvider, Context context) {
        ManagedChannelBuilder managedChannelBuilder;
        this.databaseInfo = databaseInfo2;
        this.workerQueue = asyncQueue;
        this.serializer = new RemoteSerializer(databaseInfo2.getDatabaseId());
        Supplier<ManagedChannelBuilder<?>> supplier = overrideChannelBuilderSupplier;
        if (supplier != null) {
            managedChannelBuilder = (ManagedChannelBuilder) supplier.get();
        } else {
            managedChannelBuilder = ManagedChannelBuilder.forTarget(databaseInfo2.getHost());
            if (!databaseInfo2.isSslEnabled()) {
                managedChannelBuilder.usePlaintext();
            }
        }
        managedChannelBuilder.keepAliveTime(30, TimeUnit.SECONDS);
        managedChannelBuilder.executor(asyncQueue.getExecutor());
        this.channel = new FirestoreChannel(asyncQueue, credentialsProvider, AndroidChannelBuilder.fromBuilder(managedChannelBuilder).context(context).build(), databaseInfo2.getDatabaseId());
    }

    /* access modifiers changed from: 0000 */
    public void shutdown() {
        this.channel.shutdown();
    }

    /* access modifiers changed from: 0000 */
    public AsyncQueue getWorkerQueue() {
        return this.workerQueue;
    }

    /* access modifiers changed from: 0000 */
    public DatabaseInfo getDatabaseInfo() {
        return this.databaseInfo;
    }

    /* access modifiers changed from: 0000 */
    public WatchStream createWatchStream(Callback callback) {
        return new WatchStream(this.channel, this.workerQueue, this.serializer, callback);
    }

    /* access modifiers changed from: 0000 */
    public WriteStream createWriteStream(Callback callback) {
        return new WriteStream(this.channel, this.workerQueue, this.serializer, callback);
    }

    public Task<List<MutationResult>> commit(List<Mutation> list) {
        Builder newBuilder = CommitRequest.newBuilder();
        newBuilder.setDatabase(this.serializer.databaseName());
        for (Mutation encodeMutation : list) {
            newBuilder.addWrites(this.serializer.encodeMutation(encodeMutation));
        }
        return this.channel.runRpc(FirestoreGrpc.getCommitMethod(), (CommitRequest) newBuilder.build()).continueWith(this.workerQueue.getExecutor(), Datastore$$Lambda$1.lambdaFactory$(this));
    }

    static /* synthetic */ List lambda$commit$0(Datastore datastore, Task task) throws Exception {
        if (!task.isSuccessful()) {
            if ((task.getException() instanceof FirebaseFirestoreException) && ((FirebaseFirestoreException) task.getException()).getCode() == Code.UNAUTHENTICATED) {
                datastore.channel.invalidateToken();
            }
            throw task.getException();
        }
        CommitResponse commitResponse = (CommitResponse) task.getResult();
        SnapshotVersion decodeVersion = datastore.serializer.decodeVersion(commitResponse.getCommitTime());
        int writeResultsCount = commitResponse.getWriteResultsCount();
        ArrayList arrayList = new ArrayList(writeResultsCount);
        for (int i = 0; i < writeResultsCount; i++) {
            arrayList.add(datastore.serializer.decodeMutationResult(commitResponse.getWriteResults(i), decodeVersion));
        }
        return arrayList;
    }

    public Task<List<MaybeDocument>> lookup(List<DocumentKey> list) {
        BatchGetDocumentsRequest.Builder newBuilder = BatchGetDocumentsRequest.newBuilder();
        newBuilder.setDatabase(this.serializer.databaseName());
        for (DocumentKey encodeKey : list) {
            newBuilder.addDocuments(this.serializer.encodeKey(encodeKey));
        }
        return this.channel.runStreamingResponseRpc(FirestoreGrpc.getBatchGetDocumentsMethod(), (BatchGetDocumentsRequest) newBuilder.build()).continueWith(this.workerQueue.getExecutor(), Datastore$$Lambda$2.lambdaFactory$(this, list));
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.List, code=java.util.List<com.google.firebase.firestore.model.DocumentKey>, for r4v0, types: [java.util.List, java.util.List<com.google.firebase.firestore.model.DocumentKey>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.util.List lambda$lookup$1(com.google.firebase.firestore.remote.Datastore r3, java.util.List<com.google.firebase.firestore.model.DocumentKey> r4, com.google.android.gms.tasks.Task r5) throws java.lang.Exception {
        /*
            boolean r0 = r5.isSuccessful()
            if (r0 != 0) goto L_0x0021
            java.lang.Exception r0 = r5.getException()
            boolean r0 = r0 instanceof com.google.firebase.firestore.FirebaseFirestoreException
            if (r0 == 0) goto L_0x0021
            java.lang.Exception r0 = r5.getException()
            com.google.firebase.firestore.FirebaseFirestoreException r0 = (com.google.firebase.firestore.FirebaseFirestoreException) r0
            com.google.firebase.firestore.FirebaseFirestoreException$Code r0 = r0.getCode()
            com.google.firebase.firestore.FirebaseFirestoreException$Code r1 = com.google.firebase.firestore.FirebaseFirestoreException.Code.UNAUTHENTICATED
            if (r0 != r1) goto L_0x0021
            com.google.firebase.firestore.util.FirestoreChannel r0 = r3.channel
            r0.invalidateToken()
        L_0x0021:
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            java.lang.Object r5 = r5.getResult()
            java.util.List r5 = (java.util.List) r5
            java.util.Iterator r5 = r5.iterator()
        L_0x0030:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L_0x004a
            java.lang.Object r1 = r5.next()
            com.google.firestore.v1.BatchGetDocumentsResponse r1 = (com.google.firestore.p050v1.BatchGetDocumentsResponse) r1
            com.google.firebase.firestore.remote.RemoteSerializer r2 = r3.serializer
            com.google.firebase.firestore.model.MaybeDocument r1 = r2.decodeMaybeDocument(r1)
            com.google.firebase.firestore.model.DocumentKey r2 = r1.getKey()
            r0.put(r2, r1)
            goto L_0x0030
        L_0x004a:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.util.Iterator r4 = r4.iterator()
        L_0x0053:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0069
            java.lang.Object r5 = r4.next()
            com.google.firebase.firestore.model.DocumentKey r5 = (com.google.firebase.firestore.model.DocumentKey) r5
            java.lang.Object r5 = r0.get(r5)
            com.google.firebase.firestore.model.MaybeDocument r5 = (com.google.firebase.firestore.model.MaybeDocument) r5
            r3.add(r5)
            goto L_0x0053
        L_0x0069:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.firestore.remote.Datastore.lambda$lookup$1(com.google.firebase.firestore.remote.Datastore, java.util.List, com.google.android.gms.tasks.Task):java.util.List");
    }

    public static boolean isPermanentError(Status status) {
        switch (status.getCode()) {
            case OK:
                throw new IllegalArgumentException("Treated status OK as error");
            case CANCELLED:
            case UNKNOWN:
            case DEADLINE_EXCEEDED:
            case RESOURCE_EXHAUSTED:
            case INTERNAL:
            case UNAVAILABLE:
            case UNAUTHENTICATED:
                return false;
            case INVALID_ARGUMENT:
            case NOT_FOUND:
            case ALREADY_EXISTS:
            case PERMISSION_DENIED:
            case FAILED_PRECONDITION:
            case ABORTED:
            case OUT_OF_RANGE:
            case UNIMPLEMENTED:
            case DATA_LOSS:
                return true;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Unknown gRPC status code: ");
                sb.append(status.getCode());
                throw new IllegalArgumentException(sb.toString());
        }
    }

    public static boolean isPermanentWriteError(Status status) {
        return isPermanentError(status) && !status.getCode().equals(Status.Code.ABORTED);
    }
}
