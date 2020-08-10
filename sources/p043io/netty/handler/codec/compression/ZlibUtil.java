package p043io.netty.handler.codec.compression;

import com.jcraft.jzlib.Deflater;
import com.jcraft.jzlib.Inflater;
import com.jcraft.jzlib.JZlib;
import com.jcraft.jzlib.JZlib.WrapperType;

/* renamed from: io.netty.handler.codec.compression.ZlibUtil */
final class ZlibUtil {

    /* renamed from: io.netty.handler.codec.compression.ZlibUtil$1 */
    static /* synthetic */ class C56991 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = new int[ZlibWrapper.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                io.netty.handler.codec.compression.ZlibWrapper[] r0 = p043io.netty.handler.codec.compression.ZlibWrapper.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper = r0
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.handler.codec.compression.ZlibWrapper r1 = p043io.netty.handler.codec.compression.ZlibWrapper.NONE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.handler.codec.compression.ZlibWrapper r1 = p043io.netty.handler.codec.compression.ZlibWrapper.ZLIB     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.handler.codec.compression.ZlibWrapper r1 = p043io.netty.handler.codec.compression.ZlibWrapper.GZIP     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$io$netty$handler$codec$compression$ZlibWrapper     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.handler.codec.compression.ZlibWrapper r1 = p043io.netty.handler.codec.compression.ZlibWrapper.ZLIB_OR_NONE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.handler.codec.compression.ZlibUtil.C56991.<clinit>():void");
        }
    }

    static void fail(Inflater inflater, String str, int i) {
        throw inflaterException(inflater, str, i);
    }

    static void fail(Deflater deflater, String str, int i) {
        throw deflaterException(deflater, str, i);
    }

    static DecompressionException inflaterException(Inflater inflater, String str, int i) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" (");
        sb.append(i);
        sb.append(')');
        if (inflater.msg != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(": ");
            sb2.append(inflater.msg);
            str2 = sb2.toString();
        } else {
            str2 = "";
        }
        sb.append(str2);
        return new DecompressionException(sb.toString());
    }

    static CompressionException deflaterException(Deflater deflater, String str, int i) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(" (");
        sb.append(i);
        sb.append(')');
        if (deflater.msg != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(": ");
            sb2.append(deflater.msg);
            str2 = sb2.toString();
        } else {
            str2 = "";
        }
        sb.append(str2);
        return new CompressionException(sb.toString());
    }

    static WrapperType convertWrapperType(ZlibWrapper zlibWrapper) {
        int i = C56991.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[zlibWrapper.ordinal()];
        if (i == 1) {
            return JZlib.W_NONE;
        }
        if (i == 2) {
            return JZlib.W_ZLIB;
        }
        if (i == 3) {
            return JZlib.W_GZIP;
        }
        if (i == 4) {
            return JZlib.W_ANY;
        }
        throw new Error();
    }

    static int wrapperOverhead(ZlibWrapper zlibWrapper) {
        int i = C56991.$SwitchMap$io$netty$handler$codec$compression$ZlibWrapper[zlibWrapper.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 10;
        }
        if (i == 4) {
            return 2;
        }
        throw new Error();
    }

    private ZlibUtil() {
    }
}
