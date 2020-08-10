package p043io.grpc.internal;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.core.JsonPointer;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import org.apache.http.HttpStatus;
import p043io.grpc.CallOptions;
import p043io.grpc.ClientStreamTracer.Factory;
import p043io.grpc.InternalChannelz.SocketStats;
import p043io.grpc.InternalLogId;
import p043io.grpc.InternalMetadata;
import p043io.grpc.InternalMetadata.TrustedAsciiMarshaller;
import p043io.grpc.LoadBalancer.PickResult;
import p043io.grpc.LoadBalancer.Subchannel;
import p043io.grpc.Metadata;
import p043io.grpc.Metadata.AsciiMarshaller;
import p043io.grpc.Metadata.Key;
import p043io.grpc.MethodDescriptor;
import p043io.grpc.Status;
import p043io.grpc.Status.Code;
import p043io.grpc.internal.ClientStreamListener.RpcProgress;
import p043io.grpc.internal.ClientTransport.PingCallback;
import p043io.grpc.internal.SharedResourceHolder.Resource;
import p043io.grpc.internal.StreamListener.MessageProducer;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.grpc.internal.GrpcUtil */
public final class GrpcUtil {
    public static final Splitter ACCEPT_ENCODING_SPLITTER = Splitter.m1787on((char) StringUtil.COMMA).trimResults();
    public static final String CONTENT_ACCEPT_ENCODING = "accept-encoding";
    public static final Key<byte[]> CONTENT_ACCEPT_ENCODING_KEY = InternalMetadata.keyOf(CONTENT_ACCEPT_ENCODING, (TrustedAsciiMarshaller<T>) new AcceptEncodingMarshaller<T>());
    public static final String CONTENT_ENCODING = "content-encoding";
    public static final Key<String> CONTENT_ENCODING_KEY = Key.m3992of(CONTENT_ENCODING, Metadata.ASCII_STRING_MARSHALLER);
    public static final String CONTENT_TYPE_GRPC = "application/grpc";
    public static final Key<String> CONTENT_TYPE_KEY = Key.m3992of("content-type", Metadata.ASCII_STRING_MARSHALLER);
    public static final long DEFAULT_KEEPALIVE_TIMEOUT_NANOS = TimeUnit.SECONDS.toNanos(20);
    public static final long DEFAULT_KEEPALIVE_TIME_NANOS = TimeUnit.MINUTES.toNanos(1);
    public static final int DEFAULT_MAX_HEADER_LIST_SIZE = 8192;
    public static final int DEFAULT_MAX_MESSAGE_SIZE = 4194304;
    public static final int DEFAULT_PORT_PLAINTEXT = 80;
    public static final int DEFAULT_PORT_SSL = 443;
    public static final ProxyDetector DEFAULT_PROXY_DETECTOR = new ProxyDetectorImpl();
    public static final long DEFAULT_SERVER_KEEPALIVE_TIMEOUT_NANOS = TimeUnit.SECONDS.toNanos(20);
    public static final long DEFAULT_SERVER_KEEPALIVE_TIME_NANOS = TimeUnit.HOURS.toNanos(2);
    public static final String HTTP_METHOD = "POST";
    private static final String IMPLEMENTATION_VERSION = "1.16.1";
    public static final boolean IS_RESTRICTED_APPENGINE;
    public static final long KEEPALIVE_TIME_NANOS_DISABLED = Long.MAX_VALUE;
    public static final String MESSAGE_ACCEPT_ENCODING = "grpc-accept-encoding";
    public static final Key<byte[]> MESSAGE_ACCEPT_ENCODING_KEY = InternalMetadata.keyOf(MESSAGE_ACCEPT_ENCODING, (TrustedAsciiMarshaller<T>) new AcceptEncodingMarshaller<T>());
    public static final String MESSAGE_ENCODING = "grpc-encoding";
    public static final Key<String> MESSAGE_ENCODING_KEY = Key.m3992of(MESSAGE_ENCODING, Metadata.ASCII_STRING_MARSHALLER);
    public static final ProxyDetector NOOP_PROXY_DETECTOR = new ProxyDetector() {
        @Nullable
        public ProxyParameters proxyFor(SocketAddress socketAddress) {
            return null;
        }
    };
    public static final long SERVER_KEEPALIVE_TIME_NANOS_DISABLED = Long.MAX_VALUE;
    public static final Resource<ExecutorService> SHARED_CHANNEL_EXECUTOR = new Resource<ExecutorService>() {
        private static final String NAME = "grpc-default-executor";

        public String toString() {
            return NAME;
        }

        public ExecutorService create() {
            return Executors.newCachedThreadPool(GrpcUtil.getThreadFactory("grpc-default-executor-%d", true));
        }

        public void close(ExecutorService executorService) {
            executorService.shutdown();
        }
    };
    public static final Supplier<Stopwatch> STOPWATCH_SUPPLIER = new Supplier<Stopwatch>() {
        public Stopwatch get() {
            return Stopwatch.createUnstarted();
        }
    };
    public static final Key<String> TE_HEADER = Key.m3992of("te", Metadata.ASCII_STRING_MARSHALLER);
    public static final String TE_TRAILERS = "trailers";
    public static final String TIMEOUT = "grpc-timeout";
    public static final Key<Long> TIMEOUT_KEY = Key.m3992of(TIMEOUT, (AsciiMarshaller<T>) new TimeoutMarshaller<T>());
    public static final Resource<ScheduledExecutorService> TIMER_SERVICE = new Resource<ScheduledExecutorService>() {
        public ScheduledExecutorService create() {
            ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(1, GrpcUtil.getThreadFactory("grpc-timer-%d", true));
            try {
                newScheduledThreadPool.getClass().getMethod("setRemoveOnCancelPolicy", new Class[]{Boolean.TYPE}).invoke(newScheduledThreadPool, new Object[]{Boolean.valueOf(true)});
            } catch (NoSuchMethodException unused) {
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
            return newScheduledThreadPool;
        }

        public void close(ScheduledExecutorService scheduledExecutorService) {
            scheduledExecutorService.shutdown();
        }
    };
    public static final Key<String> USER_AGENT_KEY = Key.m3992of("user-agent", Metadata.ASCII_STRING_MARSHALLER);
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    private static final Logger log = Logger.getLogger(GrpcUtil.class.getName());

    /* renamed from: io.grpc.internal.GrpcUtil$AcceptEncodingMarshaller */
    private static final class AcceptEncodingMarshaller implements TrustedAsciiMarshaller<byte[]> {
        public byte[] parseAsciiString(byte[] bArr) {
            return bArr;
        }

        public byte[] toAsciiString(byte[] bArr) {
            return bArr;
        }

        private AcceptEncodingMarshaller() {
        }
    }

    /* renamed from: io.grpc.internal.GrpcUtil$Http2Error */
    public enum Http2Error {
        NO_ERROR(0, Status.UNAVAILABLE),
        PROTOCOL_ERROR(1, Status.INTERNAL),
        INTERNAL_ERROR(2, Status.INTERNAL),
        FLOW_CONTROL_ERROR(3, Status.INTERNAL),
        SETTINGS_TIMEOUT(4, Status.INTERNAL),
        STREAM_CLOSED(5, Status.INTERNAL),
        FRAME_SIZE_ERROR(6, Status.INTERNAL),
        REFUSED_STREAM(7, Status.UNAVAILABLE),
        CANCEL(8, Status.CANCELLED),
        COMPRESSION_ERROR(9, Status.INTERNAL),
        CONNECT_ERROR(10, Status.INTERNAL),
        ENHANCE_YOUR_CALM(11, Status.RESOURCE_EXHAUSTED.withDescription("Bandwidth exhausted")),
        INADEQUATE_SECURITY(12, Status.PERMISSION_DENIED.withDescription("Permission denied as protocol is not secure enough to call")),
        HTTP_1_1_REQUIRED(13, Status.UNKNOWN);
        
        private static final Http2Error[] codeMap = null;
        private final int code;
        private final Status status;

        static {
            codeMap = buildHttp2CodeMap();
        }

        private static Http2Error[] buildHttp2CodeMap() {
            Http2Error[] values = values();
            Http2Error[] http2ErrorArr = new Http2Error[(((int) values[values.length - 1].code()) + 1)];
            for (Http2Error http2Error : values) {
                http2ErrorArr[(int) http2Error.code()] = http2Error;
            }
            return http2ErrorArr;
        }

        private Http2Error(int i, Status status2) {
            this.code = i;
            StringBuilder sb = new StringBuilder();
            sb.append("HTTP/2 error code: ");
            sb.append(name());
            this.status = status2.augmentDescription(sb.toString());
        }

        public long code() {
            return (long) this.code;
        }

        public Status status() {
            return this.status;
        }

        public static Http2Error forCode(long j) {
            Http2Error[] http2ErrorArr = codeMap;
            if (j >= ((long) http2ErrorArr.length) || j < 0) {
                return null;
            }
            return http2ErrorArr[(int) j];
        }

        public static Status statusForCode(long j) {
            Http2Error forCode = forCode(j);
            if (forCode != null) {
                return forCode.status();
            }
            Status fromCodeValue = Status.fromCodeValue(INTERNAL_ERROR.status().getCode().value());
            StringBuilder sb = new StringBuilder();
            sb.append("Unrecognized HTTP/2 error code: ");
            sb.append(j);
            return fromCodeValue.withDescription(sb.toString());
        }
    }

    /* renamed from: io.grpc.internal.GrpcUtil$TimeoutMarshaller */
    static class TimeoutMarshaller implements AsciiMarshaller<Long> {
        TimeoutMarshaller() {
        }

        public String toAsciiString(Long l) {
            TimeUnit timeUnit = TimeUnit.NANOSECONDS;
            if (l.longValue() < 0) {
                throw new IllegalArgumentException("Timeout too small");
            } else if (l.longValue() < 100000000) {
                StringBuilder sb = new StringBuilder();
                sb.append(l);
                sb.append("n");
                return sb.toString();
            } else if (l.longValue() < 100000000000L) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(timeUnit.toMicros(l.longValue()));
                sb2.append("u");
                return sb2.toString();
            } else if (l.longValue() < 100000000000000L) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(timeUnit.toMillis(l.longValue()));
                sb3.append("m");
                return sb3.toString();
            } else if (l.longValue() < 100000000000000000L) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(timeUnit.toSeconds(l.longValue()));
                sb4.append(ExifInterface.LATITUDE_SOUTH);
                return sb4.toString();
            } else if (l.longValue() < 6000000000000000000L) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(timeUnit.toMinutes(l.longValue()));
                sb5.append("M");
                return sb5.toString();
            } else {
                StringBuilder sb6 = new StringBuilder();
                sb6.append(timeUnit.toHours(l.longValue()));
                sb6.append("H");
                return sb6.toString();
            }
        }

        public Long parseAsciiString(String str) {
            Preconditions.checkArgument(str.length() > 0, "empty timeout");
            Preconditions.checkArgument(str.length() <= 9, "bad timeout format");
            long parseLong = Long.parseLong(str.substring(0, str.length() - 1));
            char charAt = str.charAt(str.length() - 1);
            if (charAt == 'H') {
                return Long.valueOf(TimeUnit.HOURS.toNanos(parseLong));
            }
            if (charAt == 'M') {
                return Long.valueOf(TimeUnit.MINUTES.toNanos(parseLong));
            }
            if (charAt == 'S') {
                return Long.valueOf(TimeUnit.SECONDS.toNanos(parseLong));
            }
            if (charAt == 'u') {
                return Long.valueOf(TimeUnit.MICROSECONDS.toNanos(parseLong));
            }
            if (charAt == 'm') {
                return Long.valueOf(TimeUnit.MILLISECONDS.toNanos(parseLong));
            }
            if (charAt == 'n') {
                return Long.valueOf(parseLong);
            }
            throw new IllegalArgumentException(String.format("Invalid timeout unit: %s", new Object[]{Character.valueOf(charAt)}));
        }
    }

    static {
        boolean z;
        if (System.getProperty("com.google.appengine.runtime.environment") != null) {
            if ("1.7".equals(System.getProperty("java.specification.version"))) {
                z = true;
                IS_RESTRICTED_APPENGINE = z;
            }
        }
        z = false;
        IS_RESTRICTED_APPENGINE = z;
    }

    public static ProxyDetector getDefaultProxyDetector() {
        if (IS_RESTRICTED_APPENGINE) {
            return NOOP_PROXY_DETECTOR;
        }
        return DEFAULT_PROXY_DETECTOR;
    }

    public static Status httpStatusToGrpcStatus(int i) {
        Status status = httpStatusToGrpcCode(i).toStatus();
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP status code ");
        sb.append(i);
        return status.withDescription(sb.toString());
    }

    private static Code httpStatusToGrpcCode(int i) {
        if (i >= 100 && i < 200) {
            return Code.INTERNAL;
        }
        if (i != 400) {
            if (i == 401) {
                return Code.UNAUTHENTICATED;
            }
            if (i == 403) {
                return Code.PERMISSION_DENIED;
            }
            if (i == 404) {
                return Code.UNIMPLEMENTED;
            }
            if (i != 429) {
                if (i != 431) {
                    switch (i) {
                        case 502:
                        case 503:
                        case HttpStatus.SC_GATEWAY_TIMEOUT /*504*/:
                            break;
                        default:
                            return Code.UNKNOWN;
                    }
                }
            }
            return Code.UNAVAILABLE;
        }
        return Code.INTERNAL;
    }

    public static boolean isGrpcContentType(String str) {
        boolean z = false;
        if (str == null || 16 > str.length()) {
            return false;
        }
        String lowerCase = str.toLowerCase();
        if (!lowerCase.startsWith(CONTENT_TYPE_GRPC)) {
            return false;
        }
        if (lowerCase.length() == 16) {
            return true;
        }
        char charAt = lowerCase.charAt(16);
        if (charAt == '+' || charAt == ';') {
            z = true;
        }
        return z;
    }

    public static String getGrpcUserAgent(String str, @Nullable String str2) {
        StringBuilder sb = new StringBuilder();
        if (str2 != null) {
            sb.append(str2);
            sb.append(' ');
        }
        sb.append("grpc-java-");
        sb.append(str);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(IMPLEMENTATION_VERSION);
        return sb.toString();
    }

    public static URI authorityToUri(String str) {
        Preconditions.checkNotNull(str, "authority");
        try {
            URI uri = new URI(null, str, null, null, null);
            return uri;
        } catch (URISyntaxException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid authority: ");
            sb.append(str);
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    public static String checkAuthority(String str) {
        URI authorityToUri = authorityToUri(str);
        boolean z = true;
        Preconditions.checkArgument(authorityToUri.getHost() != null, "No host in authority '%s'", (Object) str);
        if (authorityToUri.getUserInfo() != null) {
            z = false;
        }
        Preconditions.checkArgument(z, "Userinfo must not be present on authority: '%s'", (Object) str);
        return str;
    }

    public static String authorityFromHostAndPort(String str, int i) {
        try {
            URI uri = new URI(null, null, str, i, null, null, null);
            return uri.getAuthority();
        } catch (URISyntaxException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid host or port: ");
            sb.append(str);
            sb.append(" ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    public static ThreadFactory getThreadFactory(String str, boolean z) {
        if (IS_RESTRICTED_APPENGINE) {
            return MoreExecutors.platformThreadFactory();
        }
        return new ThreadFactoryBuilder().setDaemon(z).setNameFormat(str).build();
    }

    public static String getHost(InetSocketAddress inetSocketAddress) {
        try {
            return (String) InetSocketAddress.class.getMethod("getHostString", new Class[0]).invoke(inetSocketAddress, new Object[0]);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
            return inetSocketAddress.getHostName();
        }
    }

    @Nullable
    static ClientTransport getTransportFromPickResult(PickResult pickResult, boolean z) {
        Subchannel subchannel = pickResult.getSubchannel();
        final ClientTransport obtainActiveTransport = subchannel != null ? ((AbstractSubchannel) subchannel).obtainActiveTransport() : null;
        if (obtainActiveTransport != null) {
            final Factory streamTracerFactory = pickResult.getStreamTracerFactory();
            if (streamTracerFactory == null) {
                return obtainActiveTransport;
            }
            return new ClientTransport() {
                public ClientStream newStream(MethodDescriptor<?, ?> methodDescriptor, Metadata metadata, CallOptions callOptions) {
                    return ClientTransport.this.newStream(methodDescriptor, metadata, callOptions.withStreamTracerFactory(streamTracerFactory));
                }

                public void ping(PingCallback pingCallback, Executor executor) {
                    ClientTransport.this.ping(pingCallback, executor);
                }

                public InternalLogId getLogId() {
                    return ClientTransport.this.getLogId();
                }

                public ListenableFuture<SocketStats> getStats() {
                    return ClientTransport.this.getStats();
                }
            };
        }
        if (!pickResult.getStatus().isOk()) {
            if (pickResult.isDrop()) {
                return new FailingClientTransport(pickResult.getStatus(), RpcProgress.DROPPED);
            }
            if (!z) {
                return new FailingClientTransport(pickResult.getStatus(), RpcProgress.PROCESSED);
            }
        }
        return null;
    }

    static void closeQuietly(MessageProducer messageProducer) {
        while (true) {
            InputStream next = messageProducer.next();
            if (next != null) {
                closeQuietly(next);
            } else {
                return;
            }
        }
    }

    public static void closeQuietly(@Nullable InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.log(Level.WARNING, "exception caught in closeQuietly", e);
            }
        }
    }

    static <T> boolean iterableContains(Iterable<T> iterable, T t) {
        if (iterable instanceof Collection) {
            try {
                return ((Collection) iterable).contains(t);
            } catch (ClassCastException | NullPointerException unused) {
                return false;
            }
        } else {
            for (T equal : iterable) {
                if (Objects.equal(equal, t)) {
                    return true;
                }
            }
            return false;
        }
    }

    private GrpcUtil() {
    }
}
