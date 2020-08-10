package com.paypal.android.sdk.onetouch.core.enums;

import p043io.fabric.sdk.android.services.common.IdManager;

public enum Protocol {
    v0(IdManager.DEFAULT_VERSION_NAME),
    v1("1.0"),
    v2("2.0"),
    v3("3.0");
    
    private final String mVersion;

    private Protocol(String str) {
        this.mVersion = str;
    }

    public String getVersion() {
        return this.mVersion;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.paypal.android.sdk.onetouch.core.enums.Protocol getProtocol(java.lang.String r4) {
        /*
            int r0 = r4.hashCode()
            r1 = 3
            r2 = 2
            r3 = 1
            switch(r0) {
                case 48: goto L_0x0029;
                case 49: goto L_0x001f;
                case 50: goto L_0x0015;
                case 51: goto L_0x000b;
                default: goto L_0x000a;
            }
        L_0x000a:
            goto L_0x0033
        L_0x000b:
            java.lang.String r0 = "3"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0033
            r4 = 3
            goto L_0x0034
        L_0x0015:
            java.lang.String r0 = "2"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0033
            r4 = 2
            goto L_0x0034
        L_0x001f:
            java.lang.String r0 = "1"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0033
            r4 = 1
            goto L_0x0034
        L_0x0029:
            java.lang.String r0 = "0"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x0033
            r4 = 0
            goto L_0x0034
        L_0x0033:
            r4 = -1
        L_0x0034:
            if (r4 == 0) goto L_0x004d
            if (r4 == r3) goto L_0x004a
            if (r4 == r2) goto L_0x0047
            if (r4 != r1) goto L_0x003f
            com.paypal.android.sdk.onetouch.core.enums.Protocol r4 = v3
            return r4
        L_0x003f:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "invalid protocol"
            r4.<init>(r0)
            throw r4
        L_0x0047:
            com.paypal.android.sdk.onetouch.core.enums.Protocol r4 = v2
            return r4
        L_0x004a:
            com.paypal.android.sdk.onetouch.core.enums.Protocol r4 = v1
            return r4
        L_0x004d:
            com.paypal.android.sdk.onetouch.core.enums.Protocol r4 = v0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.paypal.android.sdk.onetouch.core.enums.Protocol.getProtocol(java.lang.String):com.paypal.android.sdk.onetouch.core.enums.Protocol");
    }
}
