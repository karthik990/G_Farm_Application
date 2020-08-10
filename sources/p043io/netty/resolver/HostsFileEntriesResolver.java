package p043io.netty.resolver;

import java.net.InetAddress;

/* renamed from: io.netty.resolver.HostsFileEntriesResolver */
public interface HostsFileEntriesResolver {
    public static final HostsFileEntriesResolver DEFAULT = new DefaultHostsFileEntriesResolver();

    InetAddress address(String str, ResolvedAddressTypes resolvedAddressTypes);
}
