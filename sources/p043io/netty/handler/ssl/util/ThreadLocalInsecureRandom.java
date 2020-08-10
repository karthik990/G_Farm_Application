package p043io.netty.handler.ssl.util;

import java.security.SecureRandom;
import java.util.Random;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.handler.ssl.util.ThreadLocalInsecureRandom */
final class ThreadLocalInsecureRandom extends SecureRandom {
    private static final SecureRandom INSTANCE = new ThreadLocalInsecureRandom();
    private static final long serialVersionUID = -8209473337192526191L;

    public String getAlgorithm() {
        return "insecure";
    }

    public void setSeed(long j) {
    }

    public void setSeed(byte[] bArr) {
    }

    static SecureRandom current() {
        return INSTANCE;
    }

    private ThreadLocalInsecureRandom() {
    }

    public void nextBytes(byte[] bArr) {
        random().nextBytes(bArr);
    }

    public byte[] generateSeed(int i) {
        byte[] bArr = new byte[i];
        random().nextBytes(bArr);
        return bArr;
    }

    public int nextInt() {
        return random().nextInt();
    }

    public int nextInt(int i) {
        return random().nextInt(i);
    }

    public boolean nextBoolean() {
        return random().nextBoolean();
    }

    public long nextLong() {
        return random().nextLong();
    }

    public float nextFloat() {
        return random().nextFloat();
    }

    public double nextDouble() {
        return random().nextDouble();
    }

    public double nextGaussian() {
        return random().nextGaussian();
    }

    private static Random random() {
        return PlatformDependent.threadLocalRandom();
    }
}
