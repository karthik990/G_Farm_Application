package p043io.opencensus.trace;

import java.util.Random;
import javax.annotation.Nullable;
import p043io.opencensus.internal.C5887Utils;

/* renamed from: io.opencensus.trace.TraceId */
public final class TraceId implements Comparable<TraceId> {
    private static final int BASE16_SIZE = 32;
    public static final TraceId INVALID = new TraceId(0, 0);
    private static final long INVALID_ID = 0;
    public static final int SIZE = 16;
    private final long idHi;
    private final long idLo;

    private TraceId(long j, long j2) {
        this.idHi = j;
        this.idLo = j2;
    }

    public static TraceId fromBytes(byte[] bArr) {
        C5887Utils.checkNotNull(bArr, "src");
        C5887Utils.checkArgument(bArr.length == 16, "Invalid size: expected %s, got %s", Integer.valueOf(16), Integer.valueOf(bArr.length));
        return fromBytes(bArr, 0);
    }

    public static TraceId fromBytes(byte[] bArr, int i) {
        C5887Utils.checkNotNull(bArr, "src");
        return new TraceId(BigendianEncoding.longFromByteArray(bArr, i), BigendianEncoding.longFromByteArray(bArr, i + 8));
    }

    public static TraceId fromLowerBase16(CharSequence charSequence) {
        C5887Utils.checkNotNull(charSequence, "src");
        C5887Utils.checkArgument(charSequence.length() == 32, "Invalid size: expected %s, got %s", Integer.valueOf(32), Integer.valueOf(charSequence.length()));
        return fromLowerBase16(charSequence, 0);
    }

    public static TraceId fromLowerBase16(CharSequence charSequence, int i) {
        C5887Utils.checkNotNull(charSequence, "src");
        return new TraceId(BigendianEncoding.longFromBase16String(charSequence, i), BigendianEncoding.longFromBase16String(charSequence, i + 16));
    }

    public static TraceId generateRandomId(Random random) {
        long nextLong;
        long nextLong2;
        do {
            nextLong = random.nextLong();
            nextLong2 = random.nextLong();
            if (nextLong != 0) {
                break;
            }
        } while (nextLong2 == 0);
        return new TraceId(nextLong, nextLong2);
    }

    public byte[] getBytes() {
        byte[] bArr = new byte[16];
        BigendianEncoding.longToByteArray(this.idHi, bArr, 0);
        BigendianEncoding.longToByteArray(this.idLo, bArr, 8);
        return bArr;
    }

    public void copyBytesTo(byte[] bArr, int i) {
        BigendianEncoding.longToByteArray(this.idHi, bArr, i);
        BigendianEncoding.longToByteArray(this.idLo, bArr, i + 8);
    }

    public void copyLowerBase16To(char[] cArr, int i) {
        BigendianEncoding.longToBase16String(this.idHi, cArr, i);
        BigendianEncoding.longToBase16String(this.idLo, cArr, i + 16);
    }

    public boolean isValid() {
        return (this.idHi == 0 && this.idLo == 0) ? false : true;
    }

    public String toLowerBase16() {
        char[] cArr = new char[32];
        copyLowerBase16To(cArr, 0);
        return new String(cArr);
    }

    public long getLowerLong() {
        long j = this.idHi;
        return j < 0 ? -j : j;
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TraceId)) {
            return false;
        }
        TraceId traceId = (TraceId) obj;
        if (!(this.idHi == traceId.idHi && this.idLo == traceId.idLo)) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        long j = this.idHi;
        int i = (((int) (j ^ (j >>> 32))) + 31) * 31;
        long j2 = this.idLo;
        return i + ((int) (j2 ^ (j2 >>> 32)));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TraceId{traceId=");
        sb.append(toLowerBase16());
        sb.append("}");
        return sb.toString();
    }

    public int compareTo(TraceId traceId) {
        long j = this.idHi;
        long j2 = traceId.idHi;
        int i = -1;
        if (j == j2) {
            long j3 = this.idLo;
            long j4 = traceId.idLo;
            if (j3 == j4) {
                return 0;
            }
            if (j3 >= j4) {
                i = 1;
            }
            return i;
        }
        if (j >= j2) {
            i = 1;
        }
        return i;
    }
}
