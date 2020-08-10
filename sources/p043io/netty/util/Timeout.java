package p043io.netty.util;

/* renamed from: io.netty.util.Timeout */
public interface Timeout {
    boolean cancel();

    boolean isCancelled();

    boolean isExpired();

    TimerTask task();

    Timer timer();
}
