package p043io.netty.util;

import java.util.concurrent.atomic.AtomicLong;
import p043io.netty.util.AbstractConstant;

/* renamed from: io.netty.util.AbstractConstant */
public abstract class AbstractConstant<T extends AbstractConstant<T>> implements Constant<T> {
    private static final AtomicLong uniqueIdGenerator = new AtomicLong();

    /* renamed from: id */
    private final int f3743id;
    private final String name;
    private final long uniquifier = uniqueIdGenerator.getAndIncrement();

    protected AbstractConstant(int i, String str) {
        this.f3743id = i;
        this.name = str;
    }

    public final String name() {
        return this.name;
    }

    /* renamed from: id */
    public final int mo67945id() {
        return this.f3743id;
    }

    public final String toString() {
        return name();
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=T, code=io.netty.util.AbstractConstant, for r5v0, types: [T, io.netty.util.AbstractConstant] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int compareTo(p043io.netty.util.AbstractConstant r5) {
        /*
            r4 = this;
            if (r4 != r5) goto L_0x0004
            r5 = 0
            return r5
        L_0x0004:
            int r0 = r4.hashCode()
            int r1 = r5.hashCode()
            int r0 = r0 - r1
            if (r0 == 0) goto L_0x0010
            return r0
        L_0x0010:
            long r0 = r4.uniquifier
            long r2 = r5.uniquifier
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 >= 0) goto L_0x001a
            r5 = -1
            return r5
        L_0x001a:
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 <= 0) goto L_0x0020
            r5 = 1
            return r5
        L_0x0020:
            java.lang.Error r5 = new java.lang.Error
            java.lang.String r0 = "failed to compare two different constants"
            r5.<init>(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.netty.util.AbstractConstant.compareTo(io.netty.util.AbstractConstant):int");
    }
}
