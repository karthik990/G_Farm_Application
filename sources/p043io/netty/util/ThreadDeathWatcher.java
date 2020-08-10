package p043io.netty.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import p043io.netty.util.concurrent.DefaultThreadFactory;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.ThreadDeathWatcher */
public final class ThreadDeathWatcher {
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadDeathWatcher.class);
    /* access modifiers changed from: private */
    public static final Queue<Entry> pendingEntries = new ConcurrentLinkedQueue();
    /* access modifiers changed from: private */
    public static final AtomicBoolean started = new AtomicBoolean();
    static final ThreadFactory threadFactory;
    private static final Watcher watcher = new Watcher();
    private static volatile Thread watcherThread;

    /* renamed from: io.netty.util.ThreadDeathWatcher$Entry */
    private static final class Entry {
        final boolean isWatch;
        final Runnable task;
        final Thread thread;

        Entry(Thread thread2, Runnable runnable, boolean z) {
            this.thread = thread2;
            this.task = runnable;
            this.isWatch = z;
        }

        public int hashCode() {
            return this.thread.hashCode() ^ this.task.hashCode();
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            if (!(this.thread == entry.thread && this.task == entry.task)) {
                z = false;
            }
            return z;
        }
    }

    /* renamed from: io.netty.util.ThreadDeathWatcher$Watcher */
    private static final class Watcher implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final List<Entry> watchees;

        static {
            Class<ThreadDeathWatcher> cls = ThreadDeathWatcher.class;
        }

        private Watcher() {
            this.watchees = new ArrayList();
        }

        public void run() {
            while (true) {
                fetchWatchees();
                notifyWatchees();
                fetchWatchees();
                notifyWatchees();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException unused) {
                }
                if (this.watchees.isEmpty() && ThreadDeathWatcher.pendingEntries.isEmpty()) {
                    ThreadDeathWatcher.started.compareAndSet(true, false);
                    if (ThreadDeathWatcher.pendingEntries.isEmpty() || !ThreadDeathWatcher.started.compareAndSet(false, true)) {
                        return;
                    }
                }
            }
        }

        private void fetchWatchees() {
            while (true) {
                Entry entry = (Entry) ThreadDeathWatcher.pendingEntries.poll();
                if (entry != null) {
                    if (entry.isWatch) {
                        this.watchees.add(entry);
                    } else {
                        this.watchees.remove(entry);
                    }
                } else {
                    return;
                }
            }
        }

        private void notifyWatchees() {
            List<Entry> list = this.watchees;
            int i = 0;
            while (i < list.size()) {
                Entry entry = (Entry) list.get(i);
                if (!entry.thread.isAlive()) {
                    list.remove(i);
                    try {
                        entry.task.run();
                    } catch (Throwable th) {
                        ThreadDeathWatcher.logger.warn("Thread death watcher task raised an exception:", th);
                    }
                } else {
                    i++;
                }
            }
        }
    }

    static {
        String str = SystemPropertyUtil.get("io.netty.serviceThreadPrefix");
        String str2 = "threadDeathWatcher";
        if (!StringUtil.isNullOrEmpty(str)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(str2);
            str2 = sb.toString();
        }
        threadFactory = new DefaultThreadFactory(str2, true, 1, null);
    }

    public static void watch(Thread thread, Runnable runnable) {
        if (thread == null) {
            throw new NullPointerException("thread");
        } else if (runnable == null) {
            throw new NullPointerException("task");
        } else if (thread.isAlive()) {
            schedule(thread, runnable, true);
        } else {
            throw new IllegalArgumentException("thread must be alive.");
        }
    }

    public static void unwatch(Thread thread, Runnable runnable) {
        if (thread == null) {
            throw new NullPointerException("thread");
        } else if (runnable != null) {
            schedule(thread, runnable, false);
        } else {
            throw new NullPointerException("task");
        }
    }

    private static void schedule(Thread thread, Runnable runnable, boolean z) {
        pendingEntries.add(new Entry(thread, runnable, z));
        if (started.compareAndSet(false, true)) {
            Thread newThread = threadFactory.newThread(watcher);
            newThread.start();
            watcherThread = newThread;
        }
    }

    public static boolean awaitInactivity(long j, TimeUnit timeUnit) throws InterruptedException {
        if (timeUnit != null) {
            Thread thread = watcherThread;
            if (thread == null) {
                return true;
            }
            thread.join(timeUnit.toMillis(j));
            return !thread.isAlive();
        }
        throw new NullPointerException("unit");
    }

    private ThreadDeathWatcher() {
    }
}
