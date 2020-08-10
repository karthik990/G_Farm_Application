package com.google.firebase.firestore.local;

import android.util.SparseArray;
import com.google.firebase.firestore.util.AsyncQueue;
import com.google.firebase.firestore.util.AsyncQueue.DelayedTask;
import com.google.firebase.firestore.util.AsyncQueue.TimerId;
import com.google.firebase.firestore.util.Logger;
import java.util.Comparator;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class LruGarbageCollector {
    /* access modifiers changed from: private */
    public static final long INITIAL_GC_DELAY_MS = TimeUnit.MINUTES.toMillis(1);
    /* access modifiers changed from: private */
    public static final long REGULAR_GC_DELAY_MS = TimeUnit.MINUTES.toMillis(5);
    private final LruDelegate delegate;
    /* access modifiers changed from: private */
    public final Params params;

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class Params {
        private static final long COLLECTION_DISABLED = -1;
        private static final long DEFAULT_CACHE_SIZE_BYTES = 104857600;
        private static final int DEFAULT_COLLECTION_PERCENTILE = 10;
        private static final int DEFAULT_MAX_SEQUENCE_NUMBERS_TO_COLLECT = 1000;
        final int maximumSequenceNumbersToCollect;
        final long minBytesThreshold;
        final int percentileToCollect;

        public static Params Default() {
            return new Params(DEFAULT_CACHE_SIZE_BYTES, 10, 1000);
        }

        public static Params Disabled() {
            return new Params(-1, 0, 0);
        }

        public static Params WithCacheSizeBytes(long j) {
            return new Params(j, 10, 1000);
        }

        Params(long j, int i, int i2) {
            this.minBytesThreshold = j;
            this.percentileToCollect = i;
            this.maximumSequenceNumbersToCollect = i2;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public static class Results {
        private final int documentsRemoved;
        private final boolean hasRun;
        private final int sequenceNumbersCollected;
        private final int targetsRemoved;

        static Results DidNotRun() {
            return new Results(false, 0, 0, 0);
        }

        Results(boolean z, int i, int i2, int i3) {
            this.hasRun = z;
            this.sequenceNumbersCollected = i;
            this.targetsRemoved = i2;
            this.documentsRemoved = i3;
        }

        public boolean hasRun() {
            return this.hasRun;
        }

        public int getSequenceNumbersCollected() {
            return this.sequenceNumbersCollected;
        }

        public int getTargetsRemoved() {
            return this.targetsRemoved;
        }

        public int getDocumentsRemoved() {
            return this.documentsRemoved;
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    private static class RollingSequenceNumberBuffer {
        private static final Comparator<Long> COMPARATOR = LruGarbageCollector$RollingSequenceNumberBuffer$$Lambda$1.lambdaFactory$();
        private final int maxElements;
        private final PriorityQueue<Long> queue;

        RollingSequenceNumberBuffer(int i) {
            this.maxElements = i;
            this.queue = new PriorityQueue<>(i, COMPARATOR);
        }

        /* access modifiers changed from: 0000 */
        public void addElement(Long l) {
            if (this.queue.size() < this.maxElements) {
                this.queue.add(l);
                return;
            }
            if (l.longValue() < ((Long) this.queue.peek()).longValue()) {
                this.queue.poll();
                this.queue.add(l);
            }
        }

        /* access modifiers changed from: 0000 */
        public long getMaxValue() {
            return ((Long) this.queue.peek()).longValue();
        }
    }

    /* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
    public class Scheduler {
        private final AsyncQueue asyncQueue;
        private DelayedTask gcTask;
        private boolean hasRun = false;
        private final LocalStore localStore;

        public Scheduler(AsyncQueue asyncQueue2, LocalStore localStore2) {
            this.asyncQueue = asyncQueue2;
            this.localStore = localStore2;
        }

        public void start() {
            if (LruGarbageCollector.this.params.minBytesThreshold != -1) {
                scheduleGC();
            }
        }

        public void stop() {
            DelayedTask delayedTask = this.gcTask;
            if (delayedTask != null) {
                delayedTask.cancel();
            }
        }

        private void scheduleGC() {
            this.gcTask = this.asyncQueue.enqueueAfterDelay(TimerId.GARBAGE_COLLECTION, this.hasRun ? LruGarbageCollector.REGULAR_GC_DELAY_MS : LruGarbageCollector.INITIAL_GC_DELAY_MS, LruGarbageCollector$Scheduler$$Lambda$1.lambdaFactory$(this));
        }

        static /* synthetic */ void lambda$scheduleGC$0(Scheduler scheduler) {
            scheduler.localStore.collectGarbage(LruGarbageCollector.this);
            scheduler.hasRun = true;
            scheduler.scheduleGC();
        }
    }

    LruGarbageCollector(LruDelegate lruDelegate, Params params2) {
        this.delegate = lruDelegate;
        this.params = params2;
    }

    public Scheduler newScheduler(AsyncQueue asyncQueue, LocalStore localStore) {
        return new Scheduler(asyncQueue, localStore);
    }

    /* access modifiers changed from: 0000 */
    public int calculateQueryCount(int i) {
        return (int) ((((float) i) / 100.0f) * ((float) this.delegate.getSequenceNumberCount()));
    }

    /* access modifiers changed from: 0000 */
    public long getNthSequenceNumber(int i) {
        if (i == 0) {
            return -1;
        }
        RollingSequenceNumberBuffer rollingSequenceNumberBuffer = new RollingSequenceNumberBuffer(i);
        this.delegate.forEachTarget(LruGarbageCollector$$Lambda$1.lambdaFactory$(rollingSequenceNumberBuffer));
        LruDelegate lruDelegate = this.delegate;
        rollingSequenceNumberBuffer.getClass();
        lruDelegate.forEachOrphanedDocumentSequenceNumber(LruGarbageCollector$$Lambda$2.lambdaFactory$(rollingSequenceNumberBuffer));
        return rollingSequenceNumberBuffer.getMaxValue();
    }

    /* access modifiers changed from: 0000 */
    public int removeTargets(long j, SparseArray<?> sparseArray) {
        return this.delegate.removeTargets(j, sparseArray);
    }

    /* access modifiers changed from: 0000 */
    public int removeOrphanedDocuments(long j) {
        return this.delegate.removeOrphanedDocuments(j);
    }

    /* access modifiers changed from: 0000 */
    public Results collect(SparseArray<?> sparseArray) {
        String str = "LruGarbageCollector";
        if (this.params.minBytesThreshold == -1) {
            Logger.debug(str, "Garbage collection skipped; disabled", new Object[0]);
            return Results.DidNotRun();
        }
        long byteSize = getByteSize();
        if (byteSize >= this.params.minBytesThreshold) {
            return runGarbageCollection(sparseArray);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Garbage collection skipped; Cache size ");
        sb.append(byteSize);
        sb.append(" is lower than threshold ");
        sb.append(this.params.minBytesThreshold);
        Logger.debug(str, sb.toString(), new Object[0]);
        return Results.DidNotRun();
    }

    private Results runGarbageCollection(SparseArray<?> sparseArray) {
        long currentTimeMillis = System.currentTimeMillis();
        int calculateQueryCount = calculateQueryCount(this.params.percentileToCollect);
        String str = "LruGarbageCollector";
        if (calculateQueryCount > this.params.maximumSequenceNumbersToCollect) {
            StringBuilder sb = new StringBuilder();
            sb.append("Capping sequence numbers to collect down to the maximum of ");
            sb.append(this.params.maximumSequenceNumbersToCollect);
            sb.append(" from ");
            sb.append(calculateQueryCount);
            Logger.debug(str, sb.toString(), new Object[0]);
            calculateQueryCount = this.params.maximumSequenceNumbersToCollect;
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        long nthSequenceNumber = getNthSequenceNumber(calculateQueryCount);
        long currentTimeMillis3 = System.currentTimeMillis();
        int removeTargets = removeTargets(nthSequenceNumber, sparseArray);
        long currentTimeMillis4 = System.currentTimeMillis();
        int removeOrphanedDocuments = removeOrphanedDocuments(nthSequenceNumber);
        long currentTimeMillis5 = System.currentTimeMillis();
        if (Logger.isDebugEnabled()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("LRU Garbage Collection:\n");
            sb2.append("\tCounted targets in ");
            String str2 = str;
            sb2.append(currentTimeMillis2 - currentTimeMillis);
            sb2.append("ms\n");
            String sb3 = sb2.toString();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb3);
            sb4.append(String.format(Locale.ROOT, "\tDetermined least recently used %d sequence numbers in %dms\n", new Object[]{Integer.valueOf(calculateQueryCount), Long.valueOf(currentTimeMillis3 - currentTimeMillis2)}));
            String sb5 = sb4.toString();
            StringBuilder sb6 = new StringBuilder();
            sb6.append(sb5);
            sb6.append(String.format(Locale.ROOT, "\tRemoved %d targets in %dms\n", new Object[]{Integer.valueOf(removeTargets), Long.valueOf(currentTimeMillis4 - currentTimeMillis3)}));
            String sb7 = sb6.toString();
            StringBuilder sb8 = new StringBuilder();
            sb8.append(sb7);
            sb8.append(String.format(Locale.ROOT, "\tRemoved %d documents in %dms\n", new Object[]{Integer.valueOf(removeOrphanedDocuments), Long.valueOf(currentTimeMillis5 - currentTimeMillis4)}));
            String sb9 = sb8.toString();
            StringBuilder sb10 = new StringBuilder();
            sb10.append(sb9);
            sb10.append(String.format(Locale.ROOT, "Total Duration: %dms", new Object[]{Long.valueOf(currentTimeMillis5 - currentTimeMillis)}));
            Logger.debug(str2, sb10.toString(), new Object[0]);
        }
        return new Results(true, calculateQueryCount, removeTargets, removeOrphanedDocuments);
    }

    /* access modifiers changed from: 0000 */
    public long getByteSize() {
        return this.delegate.getByteSize();
    }
}
