package com.google.firebase.firestore.util;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import com.google.firebase.firestore.auth.CredentialsProvider;
import com.google.firebase.firestore.model.DatabaseId;
import com.google.firebase.firestore.remote.FirestoreCallCredentials;
import com.google.firestore.p050v1.FirestoreGrpc;
import com.google.firestore.p050v1.FirestoreGrpc.FirestoreStub;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import p043io.grpc.CallOptions;
import p043io.grpc.ClientCall;
import p043io.grpc.ClientCall.Listener;
import p043io.grpc.ManagedChannel;
import p043io.grpc.Metadata;
import p043io.grpc.Metadata.Key;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.Status;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class FirestoreChannel {
    private static final Key<String> RESOURCE_PREFIX_HEADER = Key.m3992of("google-cloud-resource-prefix", Metadata.ASCII_STRING_MARSHALLER);
    private static final Key<String> X_GOOG_API_CLIENT_HEADER = Key.m3992of("x-goog-api-client", Metadata.ASCII_STRING_MARSHALLER);
    private static final String X_GOOG_API_CLIENT_VALUE = "gl-java/ fire/19.0.0 grpc/";
    /* access modifiers changed from: private */
    public final AsyncQueue asyncQueue;
    private final CallOptions callOptions;
    private final ManagedChannel channel;
    private final CredentialsProvider credentialsProvider;
    private final String resourcePrefixValue;

    public FirestoreChannel(AsyncQueue asyncQueue2, CredentialsProvider credentialsProvider2, ManagedChannel managedChannel, DatabaseId databaseId) {
        this.asyncQueue = asyncQueue2;
        this.credentialsProvider = credentialsProvider2;
        FirestoreStub firestoreStub = (FirestoreStub) FirestoreGrpc.newStub(managedChannel).withCallCredentials(new FirestoreCallCredentials(credentialsProvider2));
        this.channel = managedChannel;
        this.callOptions = firestoreStub.getCallOptions();
        this.resourcePrefixValue = String.format("projects/%s/databases/%s", new Object[]{databaseId.getProjectId(), databaseId.getDatabaseId()});
    }

    public void shutdown() {
        Class<FirestoreChannel> cls = FirestoreChannel.class;
        this.channel.shutdown();
        try {
            if (!this.channel.awaitTermination(1, TimeUnit.SECONDS)) {
                Logger.debug(cls.getSimpleName(), "Unable to gracefully shutdown the gRPC ManagedChannel. Will attempt an immediate shutdown.", new Object[0]);
                this.channel.shutdownNow();
                if (!this.channel.awaitTermination(60, TimeUnit.SECONDS)) {
                    Logger.warn(cls.getSimpleName(), "Unable to forcefully shutdown the gRPC ManagedChannel.", new Object[0]);
                }
            }
        } catch (InterruptedException unused) {
            this.channel.shutdownNow();
            Logger.warn(cls.getSimpleName(), "Interrupted while shutting down the gRPC Managed Channel", new Object[0]);
            Thread.currentThread().interrupt();
        }
    }

    public <ReqT, RespT> ClientCall<ReqT, RespT> runBidiStreamingRpc(MethodDescriptor<ReqT, RespT> methodDescriptor, final IncomingStreamObserver<RespT> incomingStreamObserver) {
        final ClientCall<ReqT, RespT> newCall = this.channel.newCall(methodDescriptor, this.callOptions);
        newCall.start(new Listener<RespT>() {
            public void onHeaders(Metadata metadata) {
                try {
                    incomingStreamObserver.onHeaders(metadata);
                } catch (Throwable th) {
                    FirestoreChannel.this.asyncQueue.panic(th);
                }
            }

            public void onMessage(RespT respt) {
                try {
                    incomingStreamObserver.onNext(respt);
                    newCall.request(1);
                } catch (Throwable th) {
                    FirestoreChannel.this.asyncQueue.panic(th);
                }
            }

            public void onClose(Status status, Metadata metadata) {
                try {
                    incomingStreamObserver.onClose(status);
                } catch (Throwable th) {
                    FirestoreChannel.this.asyncQueue.panic(th);
                }
            }

            public void onReady() {
                try {
                    incomingStreamObserver.onReady();
                } catch (Throwable th) {
                    FirestoreChannel.this.asyncQueue.panic(th);
                }
            }
        }, requestHeaders());
        newCall.request(1);
        return newCall;
    }

    public <ReqT, RespT> Task<List<RespT>> runStreamingResponseRpc(MethodDescriptor<ReqT, RespT> methodDescriptor, ReqT reqt) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        final ClientCall newCall = this.channel.newCall(methodDescriptor, this.callOptions);
        final ArrayList arrayList = new ArrayList();
        newCall.start(new Listener<RespT>() {
            public void onMessage(RespT respt) {
                arrayList.add(respt);
                newCall.request(1);
            }

            public void onClose(Status status, Metadata metadata) {
                if (status.isOk()) {
                    taskCompletionSource.setResult(arrayList);
                } else {
                    taskCompletionSource.setException(Util.exceptionFromStatus(status));
                }
            }
        }, requestHeaders());
        newCall.request(1);
        newCall.sendMessage(reqt);
        newCall.halfClose();
        return taskCompletionSource.getTask();
    }

    public <ReqT, RespT> Task<RespT> runRpc(MethodDescriptor<ReqT, RespT> methodDescriptor, ReqT reqt) {
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        ClientCall newCall = this.channel.newCall(methodDescriptor, this.callOptions);
        newCall.start(new Listener<RespT>() {
            public void onMessage(RespT respt) {
                taskCompletionSource.setResult(respt);
            }

            public void onClose(Status status, Metadata metadata) {
                if (!status.isOk()) {
                    taskCompletionSource.setException(Util.exceptionFromStatus(status));
                } else if (!taskCompletionSource.getTask().isComplete()) {
                    taskCompletionSource.setException(new FirebaseFirestoreException("Received onClose with status OK, but no message.", Code.INTERNAL));
                }
            }
        }, requestHeaders());
        newCall.request(2);
        newCall.sendMessage(reqt);
        newCall.halfClose();
        return taskCompletionSource.getTask();
    }

    public void invalidateToken() {
        this.credentialsProvider.invalidateToken();
    }

    private Metadata requestHeaders() {
        Metadata metadata = new Metadata();
        metadata.put(X_GOOG_API_CLIENT_HEADER, X_GOOG_API_CLIENT_VALUE);
        metadata.put(RESOURCE_PREFIX_HEADER, this.resourcePrefixValue);
        return metadata;
    }
}
