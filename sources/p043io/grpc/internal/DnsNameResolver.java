package p043io.grpc.internal;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.base.Verify;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p043io.grpc.Attributes;
import p043io.grpc.EquivalentAddressGroup;
import p043io.grpc.NameResolver;
import p043io.grpc.NameResolver.Factory;
import p043io.grpc.NameResolver.Listener;
import p043io.grpc.internal.SharedResourceHolder.Resource;

/* renamed from: io.grpc.internal.DnsNameResolver */
final class DnsNameResolver extends NameResolver {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String GRPCLB_NAME_PREFIX = "_grpclb._tcp.";
    private static final String JNDI_LOCALHOST_PROPERTY;
    private static final String JNDI_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_jndi", "true");
    private static final String JNDI_SRV_PROPERTY;
    private static final String JNDI_TXT_PROPERTY;
    private static final String SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY = "clientHostname";
    private static final String SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY = "clientLanguage";
    private static final Set<String> SERVICE_CONFIG_CHOICE_KEYS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY, SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY, SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY})));
    private static final String SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY = "percentage";
    private static final String SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY = "serviceConfig";
    private static final String SERVICE_CONFIG_NAME_PREFIX = "_grpc_config.";
    static final String SERVICE_CONFIG_PREFIX = "_grpc_config=";
    static boolean enableJndi = Boolean.parseBoolean(JNDI_PROPERTY);
    static boolean enableJndiLocalhost = Boolean.parseBoolean(JNDI_LOCALHOST_PROPERTY);
    static boolean enableSrv = Boolean.parseBoolean(JNDI_SRV_PROPERTY);
    static boolean enableTxt = Boolean.parseBoolean(JNDI_TXT_PROPERTY);
    private static String localHostname;
    /* access modifiers changed from: private */
    public static final Logger logger;
    private static final ResourceResolverFactory resourceResolverFactory;
    /* access modifiers changed from: private */
    public volatile AddressResolver addressResolver = JdkAddressResolver.INSTANCE;
    private final String authority;
    private ExecutorService executor;
    private final Resource<ExecutorService> executorResource;
    /* access modifiers changed from: private */
    public final String host;
    /* access modifiers changed from: private */
    public Listener listener;
    /* access modifiers changed from: private */
    public final int port;
    final ProxyDetector proxyDetector;
    /* access modifiers changed from: private */
    public final Random random = new Random();
    private final Runnable resolutionRunnable = new Runnable() {
        /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
            r2 = java.net.InetSocketAddress.createUnresolved(p043io.grpc.internal.DnsNameResolver.access$300(r12.this$0), p043io.grpc.internal.DnsNameResolver.access$400(r12.this$0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            r4 = r12.this$0.proxyDetector.proxyFor(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0033, code lost:
            if (r4 == null) goto L_0x0055;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
            r1.onAddresses(java.util.Collections.singletonList(new p043io.grpc.EquivalentAddressGroup((java.net.SocketAddress) new p043io.grpc.internal.ProxySocketAddress(r2, r4))), p043io.grpc.Attributes.EMPTY);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0048, code lost:
            r1 = r12.this$0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x004a, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
            p043io.grpc.internal.DnsNameResolver.access$202(r12.this$0, false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0050, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0051, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            r4 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0064, code lost:
            if (p043io.grpc.internal.DnsNameResolver.shouldUseJndi(p043io.grpc.internal.DnsNameResolver.enableJndi, p043io.grpc.internal.DnsNameResolver.enableJndiLocalhost, p043io.grpc.internal.DnsNameResolver.access$300(r12.this$0)) == false) goto L_0x006d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0066, code lost:
            r2 = p043io.grpc.internal.DnsNameResolver.access$500(r12.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x006d, code lost:
            r2 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x006e, code lost:
            r2 = p043io.grpc.internal.DnsNameResolver.resolveAll(p043io.grpc.internal.DnsNameResolver.access$600(r12.this$0), r2, p043io.grpc.internal.DnsNameResolver.enableSrv, p043io.grpc.internal.DnsNameResolver.enableTxt, p043io.grpc.internal.DnsNameResolver.access$300(r12.this$0));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
            r5 = new java.util.ArrayList();
            r6 = r2.addresses.iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x0091, code lost:
            if (r6.hasNext() == false) goto L_0x00ad;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x0093, code lost:
            r5.add(new p043io.grpc.EquivalentAddressGroup((java.net.SocketAddress) new java.net.InetSocketAddress((java.net.InetAddress) r6.next(), p043io.grpc.internal.DnsNameResolver.access$400(r12.this$0))));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00ad, code lost:
            r5.addAll(r2.balancerAddresses);
            r6 = p043io.grpc.Attributes.newBuilder();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00bc, code lost:
            if (r2.txtRecords.isEmpty() != false) goto L_0x0116;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
            r2 = p043io.grpc.internal.DnsNameResolver.parseTxtResults(r2.txtRecords).iterator();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:0x00cc, code lost:
            if (r2.hasNext() == false) goto L_0x010e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ce, code lost:
            r3 = (java.util.Map) r2.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:0x00e2, code lost:
            r4 = p043io.grpc.internal.DnsNameResolver.maybeChooseServiceConfig(r3, p043io.grpc.internal.DnsNameResolver.access$700(r12.this$0), p043io.grpc.internal.DnsNameResolver.access$800());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:0x00e4, code lost:
            r7 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
            r8 = p043io.grpc.internal.DnsNameResolver.access$900();
            r9 = java.util.logging.Level.WARNING;
            r10 = new java.lang.StringBuilder();
            r10.append("Bad service config choice ");
            r10.append(r3);
            r8.log(r9, r10.toString(), r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0102, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:?, code lost:
            p043io.grpc.internal.DnsNameResolver.access$900().log(java.util.logging.Level.WARNING, "Can't parse service Configs", r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:0x0116, code lost:
            p043io.grpc.internal.DnsNameResolver.access$900().log(java.util.logging.Level.FINE, "No TXT records found for {0}", new java.lang.Object[]{p043io.grpc.internal.DnsNameResolver.access$300(r12.this$0)});
         */
        /* JADX WARNING: Code restructure failed: missing block: B:67:0x013f, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:69:?, code lost:
            r3 = p043io.grpc.Status.UNAVAILABLE;
            r4 = new java.lang.StringBuilder();
            r4.append("Unable to resolve host ");
            r4.append(p043io.grpc.internal.DnsNameResolver.access$300(r12.this$0));
            r1.onError(r3.withDescription(r4.toString()).withCause(r2));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:71:0x0166, code lost:
            monitor-enter(r12.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:73:?, code lost:
            p043io.grpc.internal.DnsNameResolver.access$202(r12.this$0, false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:75:0x016d, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:79:0x0171, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:81:?, code lost:
            r3 = p043io.grpc.Status.UNAVAILABLE;
            r4 = new java.lang.StringBuilder();
            r4.append("Unable to resolve host ");
            r4.append(p043io.grpc.internal.DnsNameResolver.access$300(r12.this$0));
            r1.onError(r3.withDescription(r4.toString()).withCause(r2));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:83:0x0198, code lost:
            monitor-enter(r12.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:85:?, code lost:
            p043io.grpc.internal.DnsNameResolver.access$202(r12.this$0, false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:87:0x019f, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:91:0x01a3, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:93:0x01a6, code lost:
            monitor-enter(r12.this$0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:95:?, code lost:
            p043io.grpc.internal.DnsNameResolver.access$202(r12.this$0, false);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:97:0x01ad, code lost:
            throw r1;
         */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x0110 A[Catch:{ RuntimeException -> 0x0102, all -> 0x01a3 }] */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x0135 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r12 = this;
                io.grpc.internal.DnsNameResolver r0 = p043io.grpc.internal.DnsNameResolver.this
                monitor-enter(r0)
                io.grpc.internal.DnsNameResolver r1 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01b1 }
                boolean r1 = r1.shutdown     // Catch:{ all -> 0x01b1 }
                if (r1 == 0) goto L_0x000d
                monitor-exit(r0)     // Catch:{ all -> 0x01b1 }
                return
            L_0x000d:
                io.grpc.internal.DnsNameResolver r1 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01b1 }
                io.grpc.NameResolver$Listener r1 = r1.listener     // Catch:{ all -> 0x01b1 }
                io.grpc.internal.DnsNameResolver r2 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01b1 }
                r3 = 1
                r2.resolving = r3     // Catch:{ all -> 0x01b1 }
                monitor-exit(r0)     // Catch:{ all -> 0x01b1 }
                r0 = 0
                io.grpc.internal.DnsNameResolver r2 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01a3 }
                java.lang.String r2 = r2.host     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r4 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01a3 }
                int r4 = r4.port     // Catch:{ all -> 0x01a3 }
                java.net.InetSocketAddress r2 = java.net.InetSocketAddress.createUnresolved(r2, r4)     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r4 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ IOException -> 0x0171 }
                io.grpc.internal.ProxyDetector r4 = r4.proxyDetector     // Catch:{ IOException -> 0x0171 }
                io.grpc.internal.ProxyParameters r4 = r4.proxyFor(r2)     // Catch:{ IOException -> 0x0171 }
                if (r4 == 0) goto L_0x0055
                io.grpc.EquivalentAddressGroup r3 = new io.grpc.EquivalentAddressGroup     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.ProxySocketAddress r5 = new io.grpc.internal.ProxySocketAddress     // Catch:{ all -> 0x01a3 }
                r5.<init>(r2, r4)     // Catch:{ all -> 0x01a3 }
                r3.<init>(r5)     // Catch:{ all -> 0x01a3 }
                java.util.List r2 = java.util.Collections.singletonList(r3)     // Catch:{ all -> 0x01a3 }
                io.grpc.Attributes r3 = p043io.grpc.Attributes.EMPTY     // Catch:{ all -> 0x01a3 }
                r1.onAddresses(r2, r3)     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r1 = p043io.grpc.internal.DnsNameResolver.this
                monitor-enter(r1)
                io.grpc.internal.DnsNameResolver r2 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x0052 }
                r2.resolving = r0     // Catch:{ all -> 0x0052 }
                monitor-exit(r1)     // Catch:{ all -> 0x0052 }
                return
            L_0x0052:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0052 }
                throw r0
            L_0x0055:
                boolean r2 = p043io.grpc.internal.DnsNameResolver.enableJndi     // Catch:{ Exception -> 0x013f }
                boolean r4 = p043io.grpc.internal.DnsNameResolver.enableJndiLocalhost     // Catch:{ Exception -> 0x013f }
                io.grpc.internal.DnsNameResolver r5 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ Exception -> 0x013f }
                java.lang.String r5 = r5.host     // Catch:{ Exception -> 0x013f }
                boolean r2 = p043io.grpc.internal.DnsNameResolver.shouldUseJndi(r2, r4, r5)     // Catch:{ Exception -> 0x013f }
                r4 = 0
                if (r2 == 0) goto L_0x006d
                io.grpc.internal.DnsNameResolver r2 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ Exception -> 0x013f }
                io.grpc.internal.DnsNameResolver$ResourceResolver r2 = r2.getResourceResolver()     // Catch:{ Exception -> 0x013f }
                goto L_0x006e
            L_0x006d:
                r2 = r4
            L_0x006e:
                io.grpc.internal.DnsNameResolver r5 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ Exception -> 0x013f }
                io.grpc.internal.DnsNameResolver$AddressResolver r5 = r5.addressResolver     // Catch:{ Exception -> 0x013f }
                boolean r6 = p043io.grpc.internal.DnsNameResolver.enableSrv     // Catch:{ Exception -> 0x013f }
                boolean r7 = p043io.grpc.internal.DnsNameResolver.enableTxt     // Catch:{ Exception -> 0x013f }
                io.grpc.internal.DnsNameResolver r8 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ Exception -> 0x013f }
                java.lang.String r8 = r8.host     // Catch:{ Exception -> 0x013f }
                io.grpc.internal.DnsNameResolver$ResolutionResults r2 = p043io.grpc.internal.DnsNameResolver.resolveAll(r5, r2, r6, r7, r8)     // Catch:{ Exception -> 0x013f }
                java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ all -> 0x01a3 }
                r5.<init>()     // Catch:{ all -> 0x01a3 }
                java.util.List<? extends java.net.InetAddress> r6 = r2.addresses     // Catch:{ all -> 0x01a3 }
                java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x01a3 }
            L_0x008d:
                boolean r7 = r6.hasNext()     // Catch:{ all -> 0x01a3 }
                if (r7 == 0) goto L_0x00ad
                java.lang.Object r7 = r6.next()     // Catch:{ all -> 0x01a3 }
                java.net.InetAddress r7 = (java.net.InetAddress) r7     // Catch:{ all -> 0x01a3 }
                io.grpc.EquivalentAddressGroup r8 = new io.grpc.EquivalentAddressGroup     // Catch:{ all -> 0x01a3 }
                java.net.InetSocketAddress r9 = new java.net.InetSocketAddress     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r10 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01a3 }
                int r10 = r10.port     // Catch:{ all -> 0x01a3 }
                r9.<init>(r7, r10)     // Catch:{ all -> 0x01a3 }
                r8.<init>(r9)     // Catch:{ all -> 0x01a3 }
                r5.add(r8)     // Catch:{ all -> 0x01a3 }
                goto L_0x008d
            L_0x00ad:
                java.util.List<io.grpc.EquivalentAddressGroup> r6 = r2.balancerAddresses     // Catch:{ all -> 0x01a3 }
                r5.addAll(r6)     // Catch:{ all -> 0x01a3 }
                io.grpc.Attributes$Builder r6 = p043io.grpc.Attributes.newBuilder()     // Catch:{ all -> 0x01a3 }
                java.util.List<java.lang.String> r7 = r2.txtRecords     // Catch:{ all -> 0x01a3 }
                boolean r7 = r7.isEmpty()     // Catch:{ all -> 0x01a3 }
                if (r7 != 0) goto L_0x0116
                java.util.List<java.lang.String> r2 = r2.txtRecords     // Catch:{ RuntimeException -> 0x0102 }
                java.util.List r2 = p043io.grpc.internal.DnsNameResolver.parseTxtResults(r2)     // Catch:{ RuntimeException -> 0x0102 }
                java.util.Iterator r2 = r2.iterator()     // Catch:{ RuntimeException -> 0x0102 }
            L_0x00c8:
                boolean r3 = r2.hasNext()     // Catch:{ RuntimeException -> 0x0102 }
                if (r3 == 0) goto L_0x010e
                java.lang.Object r3 = r2.next()     // Catch:{ RuntimeException -> 0x0102 }
                java.util.Map r3 = (java.util.Map) r3     // Catch:{ RuntimeException -> 0x0102 }
                io.grpc.internal.DnsNameResolver r7 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ RuntimeException -> 0x00e4 }
                java.util.Random r7 = r7.random     // Catch:{ RuntimeException -> 0x00e4 }
                java.lang.String r8 = p043io.grpc.internal.DnsNameResolver.getLocalHostname()     // Catch:{ RuntimeException -> 0x00e4 }
                java.util.Map r3 = p043io.grpc.internal.DnsNameResolver.maybeChooseServiceConfig(r3, r7, r8)     // Catch:{ RuntimeException -> 0x00e4 }
                r4 = r3
                goto L_0x00ff
            L_0x00e4:
                r7 = move-exception
                java.util.logging.Logger r8 = p043io.grpc.internal.DnsNameResolver.logger     // Catch:{ RuntimeException -> 0x0102 }
                java.util.logging.Level r9 = java.util.logging.Level.WARNING     // Catch:{ RuntimeException -> 0x0102 }
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ RuntimeException -> 0x0102 }
                r10.<init>()     // Catch:{ RuntimeException -> 0x0102 }
                java.lang.String r11 = "Bad service config choice "
                r10.append(r11)     // Catch:{ RuntimeException -> 0x0102 }
                r10.append(r3)     // Catch:{ RuntimeException -> 0x0102 }
                java.lang.String r3 = r10.toString()     // Catch:{ RuntimeException -> 0x0102 }
                r8.log(r9, r3, r7)     // Catch:{ RuntimeException -> 0x0102 }
            L_0x00ff:
                if (r4 == 0) goto L_0x00c8
                goto L_0x010e
            L_0x0102:
                r2 = move-exception
                java.util.logging.Logger r3 = p043io.grpc.internal.DnsNameResolver.logger     // Catch:{ all -> 0x01a3 }
                java.util.logging.Level r7 = java.util.logging.Level.WARNING     // Catch:{ all -> 0x01a3 }
                java.lang.String r8 = "Can't parse service Configs"
                r3.log(r7, r8, r2)     // Catch:{ all -> 0x01a3 }
            L_0x010e:
                if (r4 == 0) goto L_0x012b
                io.grpc.Attributes$Key<java.util.Map<java.lang.String, java.lang.Object>> r2 = p043io.grpc.internal.GrpcAttributes.NAME_RESOLVER_SERVICE_CONFIG     // Catch:{ all -> 0x01a3 }
                r6.set(r2, r4)     // Catch:{ all -> 0x01a3 }
                goto L_0x012b
            L_0x0116:
                java.util.logging.Logger r2 = p043io.grpc.internal.DnsNameResolver.logger     // Catch:{ all -> 0x01a3 }
                java.util.logging.Level r4 = java.util.logging.Level.FINE     // Catch:{ all -> 0x01a3 }
                java.lang.String r7 = "No TXT records found for {0}"
                java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r8 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01a3 }
                java.lang.String r8 = r8.host     // Catch:{ all -> 0x01a3 }
                r3[r0] = r8     // Catch:{ all -> 0x01a3 }
                r2.log(r4, r7, r3)     // Catch:{ all -> 0x01a3 }
            L_0x012b:
                io.grpc.Attributes r2 = r6.build()     // Catch:{ all -> 0x01a3 }
                r1.onAddresses(r5, r2)     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r1 = p043io.grpc.internal.DnsNameResolver.this
                monitor-enter(r1)
                io.grpc.internal.DnsNameResolver r2 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x013c }
                r2.resolving = r0     // Catch:{ all -> 0x013c }
                monitor-exit(r1)     // Catch:{ all -> 0x013c }
                return
            L_0x013c:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x013c }
                throw r0
            L_0x013f:
                r2 = move-exception
                io.grpc.Status r3 = p043io.grpc.Status.UNAVAILABLE     // Catch:{ all -> 0x01a3 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a3 }
                r4.<init>()     // Catch:{ all -> 0x01a3 }
                java.lang.String r5 = "Unable to resolve host "
                r4.append(r5)     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r5 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01a3 }
                java.lang.String r5 = r5.host     // Catch:{ all -> 0x01a3 }
                r4.append(r5)     // Catch:{ all -> 0x01a3 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01a3 }
                io.grpc.Status r3 = r3.withDescription(r4)     // Catch:{ all -> 0x01a3 }
                io.grpc.Status r2 = r3.withCause(r2)     // Catch:{ all -> 0x01a3 }
                r1.onError(r2)     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r1 = p043io.grpc.internal.DnsNameResolver.this
                monitor-enter(r1)
                io.grpc.internal.DnsNameResolver r2 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x016e }
                r2.resolving = r0     // Catch:{ all -> 0x016e }
                monitor-exit(r1)     // Catch:{ all -> 0x016e }
                return
            L_0x016e:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x016e }
                throw r0
            L_0x0171:
                r2 = move-exception
                io.grpc.Status r3 = p043io.grpc.Status.UNAVAILABLE     // Catch:{ all -> 0x01a3 }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01a3 }
                r4.<init>()     // Catch:{ all -> 0x01a3 }
                java.lang.String r5 = "Unable to resolve host "
                r4.append(r5)     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r5 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01a3 }
                java.lang.String r5 = r5.host     // Catch:{ all -> 0x01a3 }
                r4.append(r5)     // Catch:{ all -> 0x01a3 }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01a3 }
                io.grpc.Status r3 = r3.withDescription(r4)     // Catch:{ all -> 0x01a3 }
                io.grpc.Status r2 = r3.withCause(r2)     // Catch:{ all -> 0x01a3 }
                r1.onError(r2)     // Catch:{ all -> 0x01a3 }
                io.grpc.internal.DnsNameResolver r1 = p043io.grpc.internal.DnsNameResolver.this
                monitor-enter(r1)
                io.grpc.internal.DnsNameResolver r2 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01a0 }
                r2.resolving = r0     // Catch:{ all -> 0x01a0 }
                monitor-exit(r1)     // Catch:{ all -> 0x01a0 }
                return
            L_0x01a0:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x01a0 }
                throw r0
            L_0x01a3:
                r1 = move-exception
                io.grpc.internal.DnsNameResolver r2 = p043io.grpc.internal.DnsNameResolver.this
                monitor-enter(r2)
                io.grpc.internal.DnsNameResolver r3 = p043io.grpc.internal.DnsNameResolver.this     // Catch:{ all -> 0x01ae }
                r3.resolving = r0     // Catch:{ all -> 0x01ae }
                monitor-exit(r2)     // Catch:{ all -> 0x01ae }
                throw r1
            L_0x01ae:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x01ae }
                throw r0
            L_0x01b1:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x01b1 }
                goto L_0x01b5
            L_0x01b4:
                throw r1
            L_0x01b5:
                goto L_0x01b4
            */
            throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.DnsNameResolver.C54331.run():void");
        }
    };
    /* access modifiers changed from: private */
    public boolean resolving;
    private final AtomicReference<ResourceResolver> resourceResolver = new AtomicReference<>();
    /* access modifiers changed from: private */
    public boolean shutdown;

    /* renamed from: io.grpc.internal.DnsNameResolver$AddressResolver */
    interface AddressResolver {
        List<InetAddress> resolveAddress(String str) throws Exception;
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$JdkAddressResolver */
    private enum JdkAddressResolver implements AddressResolver {
        INSTANCE;

        public List<InetAddress> resolveAddress(String str) throws UnknownHostException {
            return Collections.unmodifiableList(Arrays.asList(InetAddress.getAllByName(str)));
        }
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$ResolutionResults */
    static final class ResolutionResults {
        final List<? extends InetAddress> addresses;
        final List<EquivalentAddressGroup> balancerAddresses;
        final List<String> txtRecords;

        ResolutionResults(List<? extends InetAddress> list, List<String> list2, List<EquivalentAddressGroup> list3) {
            this.addresses = Collections.unmodifiableList((List) Preconditions.checkNotNull(list, "addresses"));
            this.txtRecords = Collections.unmodifiableList((List) Preconditions.checkNotNull(list2, "txtRecords"));
            this.balancerAddresses = Collections.unmodifiableList((List) Preconditions.checkNotNull(list3, "balancerAddresses"));
        }
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$ResourceResolver */
    interface ResourceResolver {
        List<EquivalentAddressGroup> resolveSrv(AddressResolver addressResolver, String str) throws Exception;

        List<String> resolveTxt(String str) throws Exception;
    }

    /* renamed from: io.grpc.internal.DnsNameResolver$ResourceResolverFactory */
    interface ResourceResolverFactory {
        @Nullable
        ResourceResolver newResourceResolver();

        @Nullable
        Throwable unavailabilityCause();
    }

    static {
        Class<DnsNameResolver> cls = DnsNameResolver.class;
        logger = Logger.getLogger(cls.getName());
        String str = "false";
        JNDI_LOCALHOST_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_jndi_localhost", str);
        JNDI_SRV_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_grpclb", str);
        JNDI_TXT_PROPERTY = System.getProperty("io.grpc.internal.DnsNameResolverProvider.enable_service_config", str);
        resourceResolverFactory = getResourceResolverFactory(cls.getClassLoader());
    }

    DnsNameResolver(@Nullable String str, String str2, Attributes attributes, Resource<ExecutorService> resource, ProxyDetector proxyDetector2) {
        this.executorResource = resource;
        StringBuilder sb = new StringBuilder();
        sb.append("//");
        sb.append((String) Preconditions.checkNotNull(str2, PostalAddressParser.USER_ADDRESS_NAME_KEY));
        URI create = URI.create(sb.toString());
        Preconditions.checkArgument(create.getHost() != null, "Invalid DNS name: %s", (Object) str2);
        this.authority = (String) Preconditions.checkNotNull(create.getAuthority(), "nameUri (%s) doesn't have an authority", (Object) create);
        this.host = create.getHost();
        if (create.getPort() == -1) {
            Integer num = (Integer) attributes.get(Factory.PARAMS_DEFAULT_PORT);
            if (num != null) {
                this.port = num.intValue();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("name '");
                sb2.append(str2);
                sb2.append("' doesn't contain a port, and default port is not set in params");
                throw new IllegalArgumentException(sb2.toString());
            }
        } else {
            this.port = create.getPort();
        }
        this.proxyDetector = proxyDetector2;
    }

    public final String getServiceAuthority() {
        return this.authority;
    }

    public final synchronized void start(Listener listener2) {
        Preconditions.checkState(this.listener == null, "already started");
        this.executor = (ExecutorService) SharedResourceHolder.get(this.executorResource);
        this.listener = (Listener) Preconditions.checkNotNull(listener2, CastExtraArgs.LISTENER);
        resolve();
    }

    public final synchronized void refresh() {
        Preconditions.checkState(this.listener != null, "not started");
        resolve();
    }

    private void resolve() {
        if (!this.resolving && !this.shutdown) {
            this.executor.execute(this.resolutionRunnable);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void shutdown() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.shutdown     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            r0 = 1
            r2.shutdown = r0     // Catch:{ all -> 0x001c }
            java.util.concurrent.ExecutorService r0 = r2.executor     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x001a
            io.grpc.internal.SharedResourceHolder$Resource<java.util.concurrent.ExecutorService> r0 = r2.executorResource     // Catch:{ all -> 0x001c }
            java.util.concurrent.ExecutorService r1 = r2.executor     // Catch:{ all -> 0x001c }
            java.lang.Object r0 = p043io.grpc.internal.SharedResourceHolder.release(r0, r1)     // Catch:{ all -> 0x001c }
            java.util.concurrent.ExecutorService r0 = (java.util.concurrent.ExecutorService) r0     // Catch:{ all -> 0x001c }
            r2.executor = r0     // Catch:{ all -> 0x001c }
        L_0x001a:
            monitor-exit(r2)
            return
        L_0x001c:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p043io.grpc.internal.DnsNameResolver.shutdown():void");
    }

    /* access modifiers changed from: 0000 */
    public final int getPort() {
        return this.port;
    }

    static ResolutionResults resolveAll(AddressResolver addressResolver2, @Nullable ResourceResolver resourceResolver2, boolean z, boolean z2, String str) {
        Throwable e;
        List emptyList = Collections.emptyList();
        List emptyList2 = Collections.emptyList();
        List emptyList3 = Collections.emptyList();
        Throwable th = null;
        try {
            emptyList = addressResolver2.resolveAddress(str);
            e = null;
        } catch (Exception e2) {
            e = e2;
        }
        if (resourceResolver2 != null) {
            if (z) {
                try {
                    StringBuilder sb = new StringBuilder();
                    sb.append(GRPCLB_NAME_PREFIX);
                    sb.append(str);
                    emptyList2 = resourceResolver2.resolveSrv(addressResolver2, sb.toString());
                } catch (Exception e3) {
                    e = e3;
                }
            }
            e = null;
            if (z2) {
                boolean z3 = false;
                boolean z4 = !z || e != null;
                if (e != null && z4) {
                    z3 = true;
                }
                if (!z3) {
                    try {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(SERVICE_CONFIG_NAME_PREFIX);
                        sb2.append(str);
                        emptyList3 = resourceResolver2.resolveTxt(sb2.toString());
                    } catch (Exception e4) {
                        th = e4;
                    }
                }
            }
        } else {
            e = null;
        }
        String str2 = "ServiceConfig resolution failure";
        String str3 = "Balancer resolution failure";
        String str4 = "Address resolution failure";
        if (e != null) {
            if (e == null) {
                try {
                    if (!emptyList2.isEmpty()) {
                    }
                } catch (Throwable th2) {
                    if (e != null) {
                        logger.log(Level.FINE, str4, e);
                    }
                    if (e != null) {
                        logger.log(Level.FINE, str3, e);
                    }
                    if (th != null) {
                        logger.log(Level.FINE, str2, th);
                    }
                    throw th2;
                }
            }
            Throwables.throwIfUnchecked(e);
            throw new RuntimeException(e);
        }
        if (e != null) {
            logger.log(Level.FINE, str4, e);
        }
        if (e != null) {
            logger.log(Level.FINE, str3, e);
        }
        if (th != null) {
            logger.log(Level.FINE, str2, th);
        }
        return new ResolutionResults(emptyList, emptyList3, emptyList2);
    }

    static List<Map<String, Object>> parseTxtResults(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            if (str.startsWith(SERVICE_CONFIG_PREFIX)) {
                try {
                    Object parse = JsonParser.parse(str.substring(13));
                    if (parse instanceof List) {
                        List<Object> list2 = (List) parse;
                        for (Object obj : list2) {
                            if (!(obj instanceof Map)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("wrong element type ");
                                sb.append(parse);
                                throw new IOException(sb.toString());
                            }
                        }
                        arrayList.addAll(list2);
                    } else {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("wrong type ");
                        sb2.append(parse);
                        throw new IOException(sb2.toString());
                    }
                } catch (IOException e) {
                    Logger logger2 = logger;
                    Level level = Level.WARNING;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Bad service config: ");
                    sb3.append(str);
                    logger2.log(level, sb3.toString(), e);
                }
            } else {
                logger.log(Level.FINE, "Ignoring non service config {0}", new Object[]{str});
            }
        }
        return arrayList;
    }

    @Nullable
    private static final Double getPercentageFromChoice(Map<String, Object> map) {
        String str = SERVICE_CONFIG_CHOICE_PERCENTAGE_KEY;
        if (!map.containsKey(str)) {
            return null;
        }
        return ServiceConfigUtil.getDouble(map, str);
    }

    @Nullable
    private static final List<String> getClientLanguagesFromChoice(Map<String, Object> map) {
        String str = SERVICE_CONFIG_CHOICE_CLIENT_LANGUAGE_KEY;
        if (!map.containsKey(str)) {
            return null;
        }
        return ServiceConfigUtil.checkStringList(ServiceConfigUtil.getList(map, str));
    }

    @Nullable
    private static final List<String> getHostnamesFromChoice(Map<String, Object> map) {
        String str = SERVICE_CONFIG_CHOICE_CLIENT_HOSTNAME_KEY;
        if (!map.containsKey(str)) {
            return null;
        }
        return ServiceConfigUtil.checkStringList(ServiceConfigUtil.getList(map, str));
    }

    @Nullable
    static Map<String, Object> maybeChooseServiceConfig(Map<String, Object> map, Random random2, String str) {
        boolean z;
        for (Entry entry : map.entrySet()) {
            Verify.verify(SERVICE_CONFIG_CHOICE_KEYS.contains(entry.getKey()), "Bad key: %s", (Object) entry);
        }
        List clientLanguagesFromChoice = getClientLanguagesFromChoice(map);
        boolean z2 = true;
        if (clientLanguagesFromChoice != null && !clientLanguagesFromChoice.isEmpty()) {
            Iterator it = clientLanguagesFromChoice.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                if ("java".equalsIgnoreCase((String) it.next())) {
                    z = true;
                    break;
                }
            }
            if (!z) {
                return null;
            }
        }
        Double percentageFromChoice = getPercentageFromChoice(map);
        if (percentageFromChoice != null) {
            int intValue = percentageFromChoice.intValue();
            Verify.verify(intValue >= 0 && intValue <= 100, "Bad percentage: %s", (Object) percentageFromChoice);
            if (random2.nextInt(100) >= intValue) {
                return null;
            }
        }
        List hostnamesFromChoice = getHostnamesFromChoice(map);
        if (hostnamesFromChoice != null && !hostnamesFromChoice.isEmpty()) {
            Iterator it2 = hostnamesFromChoice.iterator();
            while (true) {
                if (it2.hasNext()) {
                    if (((String) it2.next()).equals(str)) {
                        break;
                    }
                } else {
                    z2 = false;
                    break;
                }
            }
            if (!z2) {
                return null;
            }
        }
        return ServiceConfigUtil.getObject(map, SERVICE_CONFIG_CHOICE_SERVICE_CONFIG_KEY);
    }

    /* access modifiers changed from: 0000 */
    public void setAddressResolver(AddressResolver addressResolver2) {
        this.addressResolver = addressResolver2;
    }

    /* access modifiers changed from: private */
    @Nullable
    public ResourceResolver getResourceResolver() {
        ResourceResolver resourceResolver2 = (ResourceResolver) this.resourceResolver.get();
        if (resourceResolver2 != null) {
            return resourceResolver2;
        }
        ResourceResolverFactory resourceResolverFactory2 = resourceResolverFactory;
        return resourceResolverFactory2 != null ? resourceResolverFactory2.newResourceResolver() : resourceResolver2;
    }

    @Nullable
    static ResourceResolverFactory getResourceResolverFactory(ClassLoader classLoader) {
        try {
            try {
                try {
                    ResourceResolverFactory resourceResolverFactory2 = (ResourceResolverFactory) Class.forName("io.grpc.internal.JndiResourceResolverFactory", true, classLoader).asSubclass(ResourceResolverFactory.class).getConstructor(new Class[0]).newInstance(new Object[0]);
                    if (resourceResolverFactory2.unavailabilityCause() != null) {
                        logger.log(Level.FINE, "JndiResourceResolverFactory not available, skipping.", resourceResolverFactory2.unavailabilityCause());
                    }
                    return resourceResolverFactory2;
                } catch (Exception e) {
                    logger.log(Level.FINE, "Can't construct JndiResourceResolverFactory, skipping.", e);
                    return null;
                }
            } catch (Exception e2) {
                logger.log(Level.FINE, "Can't find JndiResourceResolverFactory ctor, skipping.", e2);
                return null;
            }
        } catch (ClassNotFoundException e3) {
            logger.log(Level.FINE, "Unable to find JndiResourceResolverFactory, skipping.", e3);
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static String getLocalHostname() {
        if (localHostname == null) {
            try {
                localHostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return localHostname;
    }

    static boolean shouldUseJndi(boolean z, boolean z2, String str) {
        if (!z) {
            return false;
        }
        if ("localhost".equalsIgnoreCase(str)) {
            return z2;
        }
        if (str.contains(":")) {
            return false;
        }
        boolean z3 = true;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt != '.') {
                z3 &= charAt >= '0' && charAt <= '9';
            }
        }
        return true ^ z3;
    }
}
