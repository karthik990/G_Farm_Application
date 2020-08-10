package p043io.grpc.internal;

import com.google.common.base.Preconditions;
import java.net.URI;
import p043io.grpc.Attributes;
import p043io.grpc.NameResolverProvider;

/* renamed from: io.grpc.internal.DnsNameResolverProvider */
public final class DnsNameResolverProvider extends NameResolverProvider {
    private static final String SCHEME = "dns";

    public String getDefaultScheme() {
        return SCHEME;
    }

    /* access modifiers changed from: protected */
    public boolean isAvailable() {
        return true;
    }

    /* access modifiers changed from: protected */
    public int priority() {
        return 5;
    }

    public DnsNameResolver newNameResolver(URI uri, Attributes attributes) {
        if (!SCHEME.equals(uri.getScheme())) {
            return null;
        }
        String str = (String) Preconditions.checkNotNull(uri.getPath(), "targetPath");
        Preconditions.checkArgument(str.startsWith("/"), "the path component (%s) of the target (%s) must start with '/'", (Object) str, (Object) uri);
        DnsNameResolver dnsNameResolver = new DnsNameResolver(uri.getAuthority(), str.substring(1), attributes, GrpcUtil.SHARED_CHANNEL_EXECUTOR, GrpcUtil.getDefaultProxyDetector());
        return dnsNameResolver;
    }
}
