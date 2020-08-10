package p043io.netty.util.internal;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.lang.Thread.UncaughtExceptionHandler;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.internal.ThreadLocalRandom */
public final class ThreadLocalRandom extends Random {
    private static final long addend = 11;
    private static volatile long initialSeedUniquifier = 0;
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(ThreadLocalRandom.class);
    private static final long mask = 281474976710655L;
    private static final long multiplier = 25214903917L;
    /* access modifiers changed from: private */
    public static volatile long seedGeneratorEndTime = 0;
    private static final long seedGeneratorStartTime;
    private static final Thread seedGeneratorThread;
    /* access modifiers changed from: private */
    public static final BlockingQueue<Long> seedQueue;
    private static final AtomicLong seedUniquifier = new AtomicLong();
    private static final long serialVersionUID = -5851777807851030925L;
    boolean initialized = true;
    private long pad0;
    private long pad1;
    private long pad2;
    private long pad3;
    private long pad4;
    private long pad5;
    private long pad6;
    private long pad7;
    private long rnd;

    private static long mix64(long j) {
        long j2 = (j ^ (j >>> 33)) * -49064778989728563L;
        long j3 = (j2 ^ (j2 >>> 33)) * -4265267296055464877L;
        return j3 ^ (j3 >>> 33);
    }

    static {
        initialSeedUniquifier = SystemPropertyUtil.getLong("io.netty.initialSeedUniquifier", 0);
        if (initialSeedUniquifier != 0) {
            seedGeneratorThread = null;
            seedQueue = null;
            seedGeneratorStartTime = 0;
        } else if (SystemPropertyUtil.getBoolean("java.util.secureRandomSeed", false)) {
            seedQueue = new LinkedBlockingQueue();
            seedGeneratorStartTime = System.nanoTime();
            seedGeneratorThread = new Thread("initialSeedUniquifierGenerator") {
                public void run() {
                    byte[] generateSeed = new SecureRandom().generateSeed(8);
                    ThreadLocalRandom.seedGeneratorEndTime = System.nanoTime();
                    ThreadLocalRandom.seedQueue.add(Long.valueOf((((long) generateSeed[7]) & 255) | ((((long) generateSeed[0]) & 255) << 56) | ((((long) generateSeed[1]) & 255) << 48) | ((((long) generateSeed[2]) & 255) << 40) | ((((long) generateSeed[3]) & 255) << 32) | ((((long) generateSeed[4]) & 255) << 24) | ((((long) generateSeed[5]) & 255) << 16) | ((((long) generateSeed[6]) & 255) << 8)));
                }
            };
            seedGeneratorThread.setDaemon(true);
            seedGeneratorThread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
                public void uncaughtException(Thread thread, Throwable th) {
                    ThreadLocalRandom.logger.debug("An exception has been raised by {}", thread.getName(), th);
                }
            });
            seedGeneratorThread.start();
        } else {
            initialSeedUniquifier = mix64(System.currentTimeMillis()) ^ mix64(System.nanoTime());
            seedGeneratorThread = null;
            seedQueue = null;
            seedGeneratorStartTime = 0;
        }
    }

    public static void setInitialSeedUniquifier(long j) {
        initialSeedUniquifier = j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        seedGeneratorThread.interrupt();
        logger.warn("Failed to generate a seed from SecureRandom within {} seconds. Not enough entropy?", (java.lang.Object) java.lang.Long.valueOf(3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x008d, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long getInitialSeedUniquifier() {
        /*
            long r0 = initialSeedUniquifier
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L_0x0009
            return r0
        L_0x0009:
            java.lang.Class<io.netty.util.internal.ThreadLocalRandom> r0 = p043io.netty.util.internal.ThreadLocalRandom.class
            monitor-enter(r0)
            long r4 = initialSeedUniquifier     // Catch:{ all -> 0x008e }
            int r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            if (r1 == 0) goto L_0x0014
            monitor-exit(r0)     // Catch:{ all -> 0x008e }
            return r4
        L_0x0014:
            long r6 = seedGeneratorStartTime     // Catch:{ all -> 0x008e }
            java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.SECONDS     // Catch:{ all -> 0x008e }
            r8 = 3
            long r10 = r1.toNanos(r8)     // Catch:{ all -> 0x008e }
            long r6 = r6 + r10
            r1 = 0
        L_0x0020:
            long r10 = java.lang.System.nanoTime()     // Catch:{ all -> 0x008e }
            long r10 = r6 - r10
            int r12 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r12 > 0) goto L_0x0033
            java.util.concurrent.BlockingQueue<java.lang.Long> r12 = seedQueue     // Catch:{ InterruptedException -> 0x0059 }
            java.lang.Object r12 = r12.poll()     // Catch:{ InterruptedException -> 0x0059 }
            java.lang.Long r12 = (java.lang.Long) r12     // Catch:{ InterruptedException -> 0x0059 }
            goto L_0x003d
        L_0x0033:
            java.util.concurrent.BlockingQueue<java.lang.Long> r12 = seedQueue     // Catch:{ InterruptedException -> 0x0059 }
            java.util.concurrent.TimeUnit r13 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ InterruptedException -> 0x0059 }
            java.lang.Object r12 = r12.poll(r10, r13)     // Catch:{ InterruptedException -> 0x0059 }
            java.lang.Long r12 = (java.lang.Long) r12     // Catch:{ InterruptedException -> 0x0059 }
        L_0x003d:
            if (r12 == 0) goto L_0x0044
            long r4 = r12.longValue()     // Catch:{ InterruptedException -> 0x0059 }
            goto L_0x0061
        L_0x0044:
            int r12 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r12 > 0) goto L_0x0020
            java.lang.Thread r6 = seedGeneratorThread     // Catch:{ all -> 0x008e }
            r6.interrupt()     // Catch:{ all -> 0x008e }
            io.netty.util.internal.logging.InternalLogger r6 = logger     // Catch:{ all -> 0x008e }
            java.lang.String r7 = "Failed to generate a seed from SecureRandom within {} seconds. Not enough entropy?"
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch:{ all -> 0x008e }
            r6.warn(r7, r8)     // Catch:{ all -> 0x008e }
            goto L_0x0061
        L_0x0059:
            r1 = 1
            io.netty.util.internal.logging.InternalLogger r6 = logger     // Catch:{ all -> 0x008e }
            java.lang.String r7 = "Failed to generate a seed from SecureRandom due to an InterruptedException."
            r6.warn(r7)     // Catch:{ all -> 0x008e }
        L_0x0061:
            r6 = 3627065505421648153(0x3255ecdc33bae119, double:3.253008663204319E-66)
            long r4 = r4 ^ r6
            long r6 = java.lang.System.nanoTime()     // Catch:{ all -> 0x008e }
            long r6 = java.lang.Long.reverse(r6)     // Catch:{ all -> 0x008e }
            long r4 = r4 ^ r6
            initialSeedUniquifier = r4     // Catch:{ all -> 0x008e }
            if (r1 == 0) goto L_0x0080
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x008e }
            r1.interrupt()     // Catch:{ all -> 0x008e }
            java.lang.Thread r1 = seedGeneratorThread     // Catch:{ all -> 0x008e }
            r1.interrupt()     // Catch:{ all -> 0x008e }
        L_0x0080:
            long r6 = seedGeneratorEndTime     // Catch:{ all -> 0x008e }
            int r1 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r1 != 0) goto L_0x008c
            long r1 = java.lang.System.nanoTime()     // Catch:{ all -> 0x008e }
            seedGeneratorEndTime = r1     // Catch:{ all -> 0x008e }
        L_0x008c:
            monitor-exit(r0)     // Catch:{ all -> 0x008e }
            return r4
        L_0x008e:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x008e }
            goto L_0x0092
        L_0x0091:
            throw r1
        L_0x0092:
            goto L_0x0091
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.internal.ThreadLocalRandom.getInitialSeedUniquifier():long");
    }

    private static long newSeed() {
        long j;
        long j2;
        long j3;
        do {
            j = seedUniquifier.get();
            if (j != 0) {
                j2 = j;
            } else {
                j2 = getInitialSeedUniquifier();
            }
            j3 = 181783497276652981L * j2;
        } while (!seedUniquifier.compareAndSet(j, j3));
        if (j == 0 && logger.isDebugEnabled()) {
            if (seedGeneratorEndTime != 0) {
                logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x (took %d ms)", new Object[]{Long.valueOf(j2), Long.valueOf(TimeUnit.NANOSECONDS.toMillis(seedGeneratorEndTime - seedGeneratorStartTime))}));
            } else {
                logger.debug(String.format("-Dio.netty.initialSeedUniquifier: 0x%016x", new Object[]{Long.valueOf(j2)}));
            }
        }
        return System.nanoTime() ^ j3;
    }

    ThreadLocalRandom() {
        super(newSeed());
    }

    public static ThreadLocalRandom current() {
        return InternalThreadLocalMap.get().random();
    }

    public void setSeed(long j) {
        if (!this.initialized) {
            this.rnd = (j ^ multiplier) & mask;
            return;
        }
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public int next(int i) {
        this.rnd = ((this.rnd * multiplier) + addend) & mask;
        return (int) (this.rnd >>> (48 - i));
    }

    public int nextInt(int i, int i2) {
        if (i < i2) {
            return nextInt(i2 - i) + i;
        }
        throw new IllegalArgumentException();
    }

    public long nextLong(long j) {
        long j2 = 0;
        if (j > 0) {
            while (j >= 2147483647L) {
                int next = next(2);
                long j3 = j >>> 1;
                if ((next & 2) != 0) {
                    j3 = j - j3;
                }
                if ((next & 1) == 0) {
                    j2 += j - j3;
                }
                j = j3;
            }
            return j2 + ((long) nextInt((int) j));
        }
        throw new IllegalArgumentException("n must be positive");
    }

    public long nextLong(long j, long j2) {
        if (j < j2) {
            return nextLong(j2 - j) + j;
        }
        throw new IllegalArgumentException();
    }

    public double nextDouble(double d) {
        if (d > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            return nextDouble() * d;
        }
        throw new IllegalArgumentException("n must be positive");
    }

    public double nextDouble(double d, double d2) {
        if (d < d2) {
            return (nextDouble() * (d2 - d)) + d;
        }
        throw new IllegalArgumentException();
    }
}
