package p043io.grpc.okhttp.internal;

import java.io.IOException;
import p043io.netty.handler.ssl.ApplicationProtocolNames;

/* renamed from: io.grpc.okhttp.internal.Protocol */
public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1(ApplicationProtocolNames.HTTP_1_1),
    SPDY_3(ApplicationProtocolNames.SPDY_3_1),
    HTTP_2(ApplicationProtocolNames.HTTP_2),
    GRPC_EXP("grpc-exp");
    
    private final String protocol;

    private Protocol(String str) {
        this.protocol = str;
    }

    public static Protocol get(String str) throws IOException {
        if (str.equals(HTTP_1_0.protocol)) {
            return HTTP_1_0;
        }
        if (str.equals(HTTP_1_1.protocol)) {
            return HTTP_1_1;
        }
        if (str.equals(HTTP_2.protocol)) {
            return HTTP_2;
        }
        if (str.equals(GRPC_EXP.protocol)) {
            return GRPC_EXP;
        }
        if (str.equals(SPDY_3.protocol)) {
            return SPDY_3;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unexpected protocol: ");
        sb.append(str);
        throw new IOException(sb.toString());
    }

    public String toString() {
        return this.protocol;
    }
}
