package p043io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/* renamed from: io.grpc.internal.ChannelExecutor */
class ChannelExecutor {
    private static final Logger log = Logger.getLogger(ChannelExecutor.class.getName());
    private boolean draining;
    private final Object lock = new Object();
    private final Queue<Runnable> queue = new ArrayDeque();

    ChannelExecutor() {
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r3.run();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0024, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0025, code lost:
        handleUncaughtThrowable(r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void drain() {
        /*
            r4 = this;
            r0 = 0
            r1 = 0
        L_0x0002:
            java.lang.Object r2 = r4.lock
            monitor-enter(r2)
            r3 = 1
            if (r1 != 0) goto L_0x0011
            boolean r1 = r4.draining     // Catch:{ all -> 0x0029 }
            if (r1 == 0) goto L_0x000e
            monitor-exit(r2)     // Catch:{ all -> 0x0029 }
            return
        L_0x000e:
            r4.draining = r3     // Catch:{ all -> 0x0029 }
            r1 = 1
        L_0x0011:
            java.util.Queue<java.lang.Runnable> r3 = r4.queue     // Catch:{ all -> 0x0029 }
            java.lang.Object r3 = r3.poll()     // Catch:{ all -> 0x0029 }
            java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch:{ all -> 0x0029 }
            if (r3 != 0) goto L_0x001f
            r4.draining = r0     // Catch:{ all -> 0x0029 }
            monitor-exit(r2)     // Catch:{ all -> 0x0029 }
            return
        L_0x001f:
            monitor-exit(r2)     // Catch:{ all -> 0x0029 }
            r3.run()     // Catch:{ all -> 0x0024 }
            goto L_0x0002
        L_0x0024:
            r2 = move-exception
            r4.handleUncaughtThrowable(r2)
            goto L_0x0002
        L_0x0029:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0029 }
            goto L_0x002d
        L_0x002c:
            throw r0
        L_0x002d:
            goto L_0x002c
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.ChannelExecutor.drain():void");
    }

    /* access modifiers changed from: 0000 */
    public final ChannelExecutor executeLater(Runnable runnable) {
        synchronized (this.lock) {
            this.queue.add(Preconditions.checkNotNull(runnable, "runnable is null"));
        }
        return this;
    }

    /* access modifiers changed from: 0000 */
    public final int numPendingTasks() {
        int size;
        synchronized (this.lock) {
            size = this.queue.size();
        }
        return size;
    }

    /* access modifiers changed from: 0000 */
    public void handleUncaughtThrowable(Throwable th) {
        log.log(Level.WARNING, "Runnable threw exception in ChannelExecutor", th);
    }
}
