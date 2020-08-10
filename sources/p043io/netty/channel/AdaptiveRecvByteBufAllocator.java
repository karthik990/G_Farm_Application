package p043io.netty.channel;

import java.util.ArrayList;
import p043io.netty.channel.DefaultMaxMessagesRecvByteBufAllocator.MaxMessageHandle;
import p043io.netty.channel.RecvByteBufAllocator.Handle;

/* renamed from: io.netty.channel.AdaptiveRecvByteBufAllocator */
public class AdaptiveRecvByteBufAllocator extends DefaultMaxMessagesRecvByteBufAllocator {
    @Deprecated
    public static final AdaptiveRecvByteBufAllocator DEFAULT = new AdaptiveRecvByteBufAllocator();
    static final int DEFAULT_INITIAL = 1024;
    static final int DEFAULT_MAXIMUM = 65536;
    static final int DEFAULT_MINIMUM = 64;
    private static final int INDEX_DECREMENT = 1;
    private static final int INDEX_INCREMENT = 4;
    /* access modifiers changed from: private */
    public static final int[] SIZE_TABLE;
    private final int initial;
    private final int maxIndex;
    private final int minIndex;

    /* renamed from: io.netty.channel.AdaptiveRecvByteBufAllocator$HandleImpl */
    private final class HandleImpl extends MaxMessageHandle {
        private boolean decreaseNow;
        private int index;
        private final int maxIndex;
        private final int minIndex;
        private int nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];

        public HandleImpl(int i, int i2, int i3) {
            super();
            this.minIndex = i;
            this.maxIndex = i2;
            this.index = AdaptiveRecvByteBufAllocator.getSizeTableIndex(i3);
        }

        public int guess() {
            return this.nextReceiveBufferSize;
        }

        private void record(int i) {
            if (i <= AdaptiveRecvByteBufAllocator.SIZE_TABLE[Math.max(0, (this.index - 1) - 1)]) {
                if (this.decreaseNow) {
                    this.index = Math.max(this.index - 1, this.minIndex);
                    this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
                    this.decreaseNow = false;
                    return;
                }
                this.decreaseNow = true;
            } else if (i >= this.nextReceiveBufferSize) {
                this.index = Math.min(this.index + 4, this.maxIndex);
                this.nextReceiveBufferSize = AdaptiveRecvByteBufAllocator.SIZE_TABLE[this.index];
                this.decreaseNow = false;
            }
        }

        public void readComplete() {
            record(totalBytesRead());
        }
    }

    static {
        int i;
        ArrayList arrayList = new ArrayList();
        int i2 = 16;
        while (true) {
            if (i2 >= 512) {
                break;
            }
            arrayList.add(Integer.valueOf(i2));
            i2 += 16;
        }
        for (i = 512; i > 0; i <<= 1) {
            arrayList.add(Integer.valueOf(i));
        }
        SIZE_TABLE = new int[arrayList.size()];
        int i3 = 0;
        while (true) {
            int[] iArr = SIZE_TABLE;
            if (i3 < iArr.length) {
                iArr[i3] = ((Integer) arrayList.get(i3)).intValue();
                i3++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public static int getSizeTableIndex(int i) {
        int length = SIZE_TABLE.length - 1;
        int i2 = 0;
        while (length >= i2) {
            if (length == i2) {
                return length;
            }
            int i3 = (i2 + length) >>> 1;
            int[] iArr = SIZE_TABLE;
            int i4 = iArr[i3];
            int i5 = i3 + 1;
            if (i > iArr[i5]) {
                i2 = i5;
            } else if (i >= i4) {
                return i == i4 ? i3 : i5;
            } else {
                length = i3 - 1;
            }
        }
        return i2;
    }

    public AdaptiveRecvByteBufAllocator() {
        this(64, 1024, 65536);
    }

    public AdaptiveRecvByteBufAllocator(int i, int i2, int i3) {
        if (i <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("minimum: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 < i) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("initial: ");
            sb2.append(i2);
            throw new IllegalArgumentException(sb2.toString());
        } else if (i3 >= i2) {
            int sizeTableIndex = getSizeTableIndex(i);
            if (SIZE_TABLE[sizeTableIndex] < i) {
                this.minIndex = sizeTableIndex + 1;
            } else {
                this.minIndex = sizeTableIndex;
            }
            int sizeTableIndex2 = getSizeTableIndex(i3);
            if (SIZE_TABLE[sizeTableIndex2] > i3) {
                this.maxIndex = sizeTableIndex2 - 1;
            } else {
                this.maxIndex = sizeTableIndex2;
            }
            this.initial = i2;
        } else {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("maximum: ");
            sb3.append(i3);
            throw new IllegalArgumentException(sb3.toString());
        }
    }

    public Handle newHandle() {
        return new HandleImpl(this.minIndex, this.maxIndex, this.initial);
    }
}
