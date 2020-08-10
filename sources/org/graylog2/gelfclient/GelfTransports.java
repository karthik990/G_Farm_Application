package org.graylog2.gelfclient;

import org.graylog2.gelfclient.transport.GelfTcpTransport;
import org.graylog2.gelfclient.transport.GelfTransport;
import org.graylog2.gelfclient.transport.GelfUdpTransport;

public enum GelfTransports {
    TCP,
    UDP;

    /* renamed from: org.graylog2.gelfclient.GelfTransports$1 */
    static /* synthetic */ class C61161 {
        static final /* synthetic */ int[] $SwitchMap$org$graylog2$gelfclient$GelfTransports = null;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            /*
                org.graylog2.gelfclient.GelfTransports[] r0 = org.graylog2.gelfclient.GelfTransports.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$graylog2$gelfclient$GelfTransports = r0
                int[] r0 = $SwitchMap$org$graylog2$gelfclient$GelfTransports     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.graylog2.gelfclient.GelfTransports r1 = org.graylog2.gelfclient.GelfTransports.TCP     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$graylog2$gelfclient$GelfTransports     // Catch:{ NoSuchFieldError -> 0x001f }
                org.graylog2.gelfclient.GelfTransports r1 = org.graylog2.gelfclient.GelfTransports.UDP     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.graylog2.gelfclient.GelfTransports.C61161.<clinit>():void");
        }
    }

    public static GelfTransport create(GelfTransports gelfTransports, GelfConfiguration gelfConfiguration) {
        int i = C61161.$SwitchMap$org$graylog2$gelfclient$GelfTransports[gelfTransports.ordinal()];
        if (i == 1) {
            return new GelfTcpTransport(gelfConfiguration);
        }
        if (i == 2) {
            return new GelfUdpTransport(gelfConfiguration);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unsupported GELF transport: ");
        sb.append(gelfTransports);
        throw new IllegalArgumentException(sb.toString());
    }

    public static GelfTransport create(GelfConfiguration gelfConfiguration) {
        return create(gelfConfiguration.getTransport(), gelfConfiguration);
    }
}
