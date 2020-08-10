package p043io.grpc;

import java.util.concurrent.atomic.AtomicLong;

/* renamed from: io.grpc.InternalLogId */
public final class InternalLogId {
    private static final AtomicLong idAlloc = new AtomicLong();

    /* renamed from: id */
    private final long f3690id;
    private final String tag;

    public static InternalLogId allocate(String str) {
        return new InternalLogId(str, getNextId());
    }

    static long getNextId() {
        return idAlloc.incrementAndGet();
    }

    protected InternalLogId(String str, long j) {
        this.tag = str;
        this.f3690id = j;
    }

    public long getId() {
        return this.f3690id;
    }

    public String getTag() {
        return this.tag;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.tag);
        sb.append("-");
        sb.append(this.f3690id);
        return sb.toString();
    }
}
