package com.google.firebase.firestore.remote;

import com.google.firebase.firestore.remote.Stream.State;
import com.google.firebase.firestore.remote.Stream.StreamCallback;
import com.google.firebase.firestore.util.Assert;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.AsyncQueue.DelayedTask;
import com.google.firebase.firestore.util.AsyncQueue.TimerId;
import com.google.firebase.firestore.util.ExponentialBackoff;
import com.google.firebase.firestore.util.FirestoreChannel;
import com.google.firebase.firestore.util.IncomingStreamObserver;
import com.google.firebase.firestore.util.Logger;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import p043io.grpc.ClientCall;
import p043io.grpc.Metadata;
import p043io.grpc.Metadata.Key;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.Status;
import p043io.grpc.Status.Code;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
abstract class AbstractStream<ReqT, RespT, CallbackT extends StreamCallback> implements Stream<CallbackT> {
    private static final double BACKOFF_FACTOR = 1.5d;
    private static final long BACKOFF_INITIAL_DELAY_MS = TimeUnit.SECONDS.toMillis(1);
    private static final long BACKOFF_MAX_DELAY_MS = TimeUnit.MINUTES.toMillis(1);
    private static final long IDLE_TIMEOUT_MS = TimeUnit.MINUTES.toMillis(1);
    final ExponentialBackoff backoff;
    private ClientCall<ReqT, RespT> call;
    /* access modifiers changed from: private */
    public long closeCount = 0;
    private final FirestoreChannel firestoreChannel;
    private final IdleTimeoutRunnable idleTimeoutRunnable;
    private DelayedTask idleTimer;
    private final TimerId idleTimerId;
    final CallbackT listener;
    private final MethodDescriptor<ReqT, RespT> methodDescriptor;
    private State state = State.Initial;
    /* access modifiers changed from: private */
    public final AsyncQueue workerQueue;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    class CloseGuardedRunner {
        private final long initialCloseCount;

        CloseGuardedRunner(long j) {
            this.initialCloseCount = j;
        }

        /* access modifiers changed from: 0000 */
        public void run(Runnable runnable) {
            AbstractStream.this.workerQueue.verifyIsCurrentThread();
            if (AbstractStream.this.closeCount == this.initialCloseCount) {
                runnable.run();
                return;
            }
            Logger.debug(AbstractStream.this.getClass().getSimpleName(), "stream callback skipped by CloseGuardedRunner.", new Object[0]);
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    class IdleTimeoutRunnable implements Runnable {
        IdleTimeoutRunnable() {
        }

        public void run() {
            AbstractStream.this.handleIdleCloseTimer();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    class StreamObserver implements IncomingStreamObserver<RespT> {
        private final CloseGuardedRunner dispatcher;

        StreamObserver(CloseGuardedRunner closeGuardedRunner) {
            this.dispatcher = closeGuardedRunner;
        }

        public void onHeaders(Metadata metadata) {
            this.dispatcher.run(AbstractStream$StreamObserver$$Lambda$1.lambdaFactory$(this, metadata));
        }

        static /* synthetic */ void lambda$onHeaders$0(StreamObserver streamObserver, Metadata metadata) {
            if (Logger.isDebugEnabled()) {
                HashMap hashMap = new HashMap();
                for (String str : metadata.keys()) {
                    if (Datastore.WHITE_LISTED_HEADERS.contains(str.toLowerCase(Locale.ENGLISH))) {
                        hashMap.put(str, (String) metadata.get(Key.m3992of(str, Metadata.ASCII_STRING_MARSHALLER)));
                    }
                }
                if (!hashMap.isEmpty()) {
                    Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream received headers: %s", Integer.valueOf(System.identityHashCode(AbstractStream.this)), hashMap);
                }
            }
        }

        public void onNext(RespT respt) {
            this.dispatcher.run(AbstractStream$StreamObserver$$Lambda$2.lambdaFactory$(this, respt));
        }

        static /* synthetic */ void lambda$onNext$1(StreamObserver streamObserver, Object obj) {
            if (Logger.isDebugEnabled()) {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream received: %s", Integer.valueOf(System.identityHashCode(AbstractStream.this)), obj);
            }
            AbstractStream.this.onNext(obj);
        }

        public void onReady() {
            this.dispatcher.run(AbstractStream$StreamObserver$$Lambda$3.lambdaFactory$(this));
        }

        public void onClose(Status status) {
            this.dispatcher.run(AbstractStream$StreamObserver$$Lambda$4.lambdaFactory$(this, status));
        }

        static /* synthetic */ void lambda$onClose$3(StreamObserver streamObserver, Status status) {
            if (status.isOk()) {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream closed.", Integer.valueOf(System.identityHashCode(AbstractStream.this)));
            } else {
                Logger.debug(AbstractStream.this.getClass().getSimpleName(), "(%x) Stream closed with status: %s.", Integer.valueOf(System.identityHashCode(AbstractStream.this)), status);
            }
            AbstractStream.this.handleServerClose(status);
        }
    }

    public abstract void onNext(RespT respt);

    /* access modifiers changed from: protected */
    public void tearDown() {
    }

    AbstractStream(FirestoreChannel firestoreChannel2, MethodDescriptor<ReqT, RespT> methodDescriptor2, AsyncQueue asyncQueue, TimerId timerId, TimerId timerId2, CallbackT callbackt) {
        this.firestoreChannel = firestoreChannel2;
        this.methodDescriptor = methodDescriptor2;
        this.workerQueue = asyncQueue;
        this.idleTimerId = timerId2;
        this.listener = callbackt;
        this.idleTimeoutRunnable = new IdleTimeoutRunnable<>();
        ExponentialBackoff exponentialBackoff = new ExponentialBackoff(asyncQueue, timerId, BACKOFF_INITIAL_DELAY_MS, 1.5d, BACKOFF_MAX_DELAY_MS);
        this.backoff = exponentialBackoff;
    }

    public boolean isStarted() {
        this.workerQueue.verifyIsCurrentThread();
        return this.state == State.Starting || this.state == State.Open || this.state == State.Backoff;
    }

    public boolean isOpen() {
        this.workerQueue.verifyIsCurrentThread();
        return this.state == State.Open;
    }

    public void start() {
        this.workerQueue.verifyIsCurrentThread();
        boolean z = true;
        Assert.hardAssert(this.call == null, "Last call still set", new Object[0]);
        Assert.hardAssert(this.idleTimer == null, "Idle timer still set", new Object[0]);
        if (this.state == State.Error) {
            performBackoff();
            return;
        }
        if (this.state != State.Initial) {
            z = false;
        }
        Assert.hardAssert(z, "Already started", new Object[0]);
        CloseGuardedRunner closeGuardedRunner = new CloseGuardedRunner(this.closeCount);
        this.call = this.firestoreChannel.runBidiStreamingRpc(this.methodDescriptor, new StreamObserver(closeGuardedRunner));
        this.state = State.Starting;
        this.workerQueue.enqueueAndForget(AbstractStream$$Lambda$1.lambdaFactory$(this, closeGuardedRunner));
    }

    static /* synthetic */ void lambda$start$0(AbstractStream abstractStream) {
        abstractStream.state = State.Open;
        abstractStream.listener.onOpen();
    }

    private void close(State state2, Status status) {
        Assert.hardAssert(isStarted(), "Only started streams should be closed.", new Object[0]);
        Assert.hardAssert(state2 == State.Error || status.equals(Status.f3691OK), "Can't provide an error when not in an error state.", new Object[0]);
        this.workerQueue.verifyIsCurrentThread();
        cancelIdleCheck();
        this.backoff.cancel();
        this.closeCount++;
        Code code = status.getCode();
        if (code == Code.OK) {
            this.backoff.reset();
        } else if (code == Code.RESOURCE_EXHAUSTED) {
            Logger.debug(getClass().getSimpleName(), "(%x) Using maximum backoff delay to prevent overloading the backend.", Integer.valueOf(System.identityHashCode(this)));
            this.backoff.resetToMax();
        } else if (code == Code.UNAUTHENTICATED) {
            this.firestoreChannel.invalidateToken();
        }
        if (state2 != State.Error) {
            Logger.debug(getClass().getSimpleName(), "(%x) Performing stream teardown", Integer.valueOf(System.identityHashCode(this)));
            tearDown();
        }
        if (this.call != null) {
            if (status.isOk()) {
                Logger.debug(getClass().getSimpleName(), "(%x) Closing stream client-side", Integer.valueOf(System.identityHashCode(this)));
                this.call.halfClose();
            }
            this.call = null;
        }
        this.state = state2;
        this.listener.onClose(status);
    }

    public void stop() {
        if (isStarted()) {
            close(State.Initial, Status.f3691OK);
        }
    }

    public void inhibitBackoff() {
        Assert.hardAssert(!isStarted(), "Can only inhibit backoff after in a stopped state", new Object[0]);
        this.workerQueue.verifyIsCurrentThread();
        this.state = State.Initial;
        this.backoff.reset();
    }

    /* access modifiers changed from: protected */
    public void writeRequest(ReqT reqt) {
        this.workerQueue.verifyIsCurrentThread();
        Logger.debug(getClass().getSimpleName(), "(%x) Stream sending: %s", Integer.valueOf(System.identityHashCode(this)), reqt);
        cancelIdleCheck();
        this.call.sendMessage(reqt);
    }

    /* access modifiers changed from: private */
    public void handleIdleCloseTimer() {
        if (isOpen()) {
            close(State.Initial, Status.f3691OK);
        }
    }

    /* access modifiers changed from: 0000 */
    public void handleServerClose(Status status) {
        Assert.hardAssert(isStarted(), "Can't handle server close on non-started stream!", new Object[0]);
        close(State.Error, status);
    }

    private void performBackoff() {
        Assert.hardAssert(this.state == State.Error, "Should only perform backoff in an error state", new Object[0]);
        this.state = State.Backoff;
        this.backoff.backoffAndRun(AbstractStream$$Lambda$2.lambdaFactory$(this));
    }

    static /* synthetic */ void lambda$performBackoff$2(AbstractStream abstractStream) {
        Assert.hardAssert(abstractStream.state == State.Backoff, "State should still be backoff but was %s", abstractStream.state);
        abstractStream.state = State.Initial;
        abstractStream.start();
        Assert.hardAssert(abstractStream.isStarted(), "Stream should have started", new Object[0]);
    }

    /* access modifiers changed from: 0000 */
    public void markIdle() {
        if (isOpen() && this.idleTimer == null) {
            this.idleTimer = this.workerQueue.enqueueAfterDelay(this.idleTimerId, IDLE_TIMEOUT_MS, this.idleTimeoutRunnable);
        }
    }

    private void cancelIdleCheck() {
        DelayedTask delayedTask = this.idleTimer;
        if (delayedTask != null) {
            delayedTask.cancel();
            this.idleTimer = null;
        }
    }
}
