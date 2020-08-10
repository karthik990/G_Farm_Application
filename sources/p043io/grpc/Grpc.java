package p043io.grpc;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.SocketAddress;
import javax.net.ssl.SSLSession;
import p043io.grpc.Attributes.Key;

/* renamed from: io.grpc.Grpc */
public final class Grpc {
    public static final Key<SocketAddress> TRANSPORT_ATTR_LOCAL_ADDR = Key.create("local-addr");
    public static final Key<SocketAddress> TRANSPORT_ATTR_REMOTE_ADDR = Key.create("remote-addr");
    public static final Key<SSLSession> TRANSPORT_ATTR_SSL_SESSION = Key.create("ssl-session");

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: io.grpc.Grpc$TransportAttr */
    public @interface TransportAttr {
    }

    private Grpc() {
    }
}
