package p043io.netty.util;

import java.util.Locale;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.SystemPropertyUtil;

/* renamed from: io.netty.util.NettyRuntime */
public final class NettyRuntime {
    private static final AvailableProcessorsHolder holder = new AvailableProcessorsHolder();

    /* renamed from: io.netty.util.NettyRuntime$AvailableProcessorsHolder */
    static class AvailableProcessorsHolder {
        private int availableProcessors;

        AvailableProcessorsHolder() {
        }

        /* access modifiers changed from: 0000 */
        public synchronized void setAvailableProcessors(int i) {
            ObjectUtil.checkPositive(i, "availableProcessors");
            if (this.availableProcessors == 0) {
                this.availableProcessors = i;
            } else {
                throw new IllegalStateException(String.format(Locale.ROOT, "availableProcessors is already set to [%d], rejecting [%d]", new Object[]{Integer.valueOf(this.availableProcessors), Integer.valueOf(i)}));
            }
        }

        /* access modifiers changed from: 0000 */
        public synchronized int availableProcessors() {
            if (this.availableProcessors == 0) {
                setAvailableProcessors(SystemPropertyUtil.getInt("io.netty.availableProcessors", Runtime.getRuntime().availableProcessors()));
            }
            return this.availableProcessors;
        }
    }

    public static void setAvailableProcessors(int i) {
        holder.setAvailableProcessors(i);
    }

    public static int availableProcessors() {
        return holder.availableProcessors();
    }

    private NettyRuntime() {
    }
}
