package p043io.grpc.internal;

import java.util.Map;
import p043io.grpc.Attributes.Key;
import p043io.grpc.CallCredentials;
import p043io.grpc.SecurityLevel;

/* renamed from: io.grpc.internal.GrpcAttributes */
public final class GrpcAttributes {
    public static final Key<String> ATTR_LB_ADDR_AUTHORITY = Key.create("io.grpc.grpclb.lbAddrAuthority");
    public static final Key<Boolean> ATTR_LB_PROVIDED_BACKEND = Key.create("io.grpc.grpclb.lbProvidedBackend");
    public static final Key<SecurityLevel> ATTR_SECURITY_LEVEL = CallCredentials.ATTR_SECURITY_LEVEL;
    public static final Key<Map<String, Object>> NAME_RESOLVER_SERVICE_CONFIG = Key.create("service-config");

    private GrpcAttributes() {
    }
}
