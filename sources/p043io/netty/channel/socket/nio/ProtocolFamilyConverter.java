package p043io.netty.channel.socket.nio;

import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;
import p043io.netty.channel.socket.InternetProtocolFamily;

/* renamed from: io.netty.channel.socket.nio.ProtocolFamilyConverter */
final class ProtocolFamilyConverter {

    /* renamed from: io.netty.channel.socket.nio.ProtocolFamilyConverter$1 */
    static /* synthetic */ class C56591 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$channel$socket$InternetProtocolFamily = new int[InternetProtocolFamily.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                io.netty.channel.socket.InternetProtocolFamily[] r0 = p043io.netty.channel.socket.InternetProtocolFamily.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$channel$socket$InternetProtocolFamily = r0
                int[] r0 = $SwitchMap$io$netty$channel$socket$InternetProtocolFamily     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.channel.socket.InternetProtocolFamily r1 = p043io.netty.channel.socket.InternetProtocolFamily.IPv4     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$channel$socket$InternetProtocolFamily     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.channel.socket.InternetProtocolFamily r1 = p043io.netty.channel.socket.InternetProtocolFamily.IPv6     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.channel.socket.nio.ProtocolFamilyConverter.C56591.<clinit>():void");
        }
    }

    private ProtocolFamilyConverter() {
    }

    public static ProtocolFamily convert(InternetProtocolFamily internetProtocolFamily) {
        int i = C56591.$SwitchMap$io$netty$channel$socket$InternetProtocolFamily[internetProtocolFamily.ordinal()];
        if (i == 1) {
            return StandardProtocolFamily.INET;
        }
        if (i == 2) {
            return StandardProtocolFamily.INET6;
        }
        throw new IllegalArgumentException();
    }
}
