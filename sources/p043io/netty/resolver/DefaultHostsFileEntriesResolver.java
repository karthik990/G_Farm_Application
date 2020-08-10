package p043io.netty.resolver;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.Locale;
import java.util.Map;

/* renamed from: io.netty.resolver.DefaultHostsFileEntriesResolver */
public final class DefaultHostsFileEntriesResolver implements HostsFileEntriesResolver {
    private final Map<String, Inet4Address> inet4Entries;
    private final Map<String, Inet6Address> inet6Entries;

    /* renamed from: io.netty.resolver.DefaultHostsFileEntriesResolver$1 */
    static /* synthetic */ class C57681 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$resolver$ResolvedAddressTypes = new int[ResolvedAddressTypes.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                io.netty.resolver.ResolvedAddressTypes[] r0 = p043io.netty.resolver.ResolvedAddressTypes.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$io$netty$resolver$ResolvedAddressTypes = r0
                int[] r0 = $SwitchMap$io$netty$resolver$ResolvedAddressTypes     // Catch:{ NoSuchFieldError -> 0x0014 }
                io.netty.resolver.ResolvedAddressTypes r1 = p043io.netty.resolver.ResolvedAddressTypes.IPV4_ONLY     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$io$netty$resolver$ResolvedAddressTypes     // Catch:{ NoSuchFieldError -> 0x001f }
                io.netty.resolver.ResolvedAddressTypes r1 = p043io.netty.resolver.ResolvedAddressTypes.IPV6_ONLY     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$io$netty$resolver$ResolvedAddressTypes     // Catch:{ NoSuchFieldError -> 0x002a }
                io.netty.resolver.ResolvedAddressTypes r1 = p043io.netty.resolver.ResolvedAddressTypes.IPV4_PREFERRED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$io$netty$resolver$ResolvedAddressTypes     // Catch:{ NoSuchFieldError -> 0x0035 }
                io.netty.resolver.ResolvedAddressTypes r1 = p043io.netty.resolver.ResolvedAddressTypes.IPV6_PREFERRED     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.netty.resolver.DefaultHostsFileEntriesResolver.C57681.<clinit>():void");
        }
    }

    public DefaultHostsFileEntriesResolver() {
        this(HostsFileParser.parseSilently());
    }

    DefaultHostsFileEntriesResolver(HostsFileEntries hostsFileEntries) {
        this.inet4Entries = hostsFileEntries.inet4Entries();
        this.inet6Entries = hostsFileEntries.inet6Entries();
    }

    public InetAddress address(String str, ResolvedAddressTypes resolvedAddressTypes) {
        String normalize = normalize(str);
        int i = C57681.$SwitchMap$io$netty$resolver$ResolvedAddressTypes[resolvedAddressTypes.ordinal()];
        if (i == 1) {
            return (InetAddress) this.inet4Entries.get(normalize);
        }
        if (i == 2) {
            return (InetAddress) this.inet6Entries.get(normalize);
        }
        if (i == 3) {
            InetAddress inetAddress = (Inet4Address) this.inet4Entries.get(normalize);
            if (inetAddress == null) {
                inetAddress = (InetAddress) this.inet6Entries.get(normalize);
            }
            return inetAddress;
        } else if (i == 4) {
            InetAddress inetAddress2 = (Inet6Address) this.inet6Entries.get(normalize);
            if (inetAddress2 == null) {
                inetAddress2 = (InetAddress) this.inet4Entries.get(normalize);
            }
            return inetAddress2;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unknown ResolvedAddressTypes ");
            sb.append(resolvedAddressTypes);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public String normalize(String str) {
        return str.toLowerCase(Locale.ENGLISH);
    }
}
