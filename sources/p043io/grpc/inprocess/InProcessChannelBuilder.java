package p043io.grpc.inprocess;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.common.base.Preconditions;
import java.net.SocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import p043io.grpc.internal.AbstractManagedChannelImplBuilder;
import p043io.grpc.internal.ClientTransportFactory;
import p043io.grpc.internal.ClientTransportFactory.ClientTransportOptions;
import p043io.grpc.internal.ConnectionClientTransport;
import p043io.grpc.internal.GrpcUtil;
import p043io.grpc.internal.SharedResourceHolder;

/* renamed from: io.grpc.inprocess.InProcessChannelBuilder */
public final class InProcessChannelBuilder extends AbstractManagedChannelImplBuilder<InProcessChannelBuilder> {
    private final String name;
    private ScheduledExecutorService scheduledExecutorService;

    /* renamed from: io.grpc.inprocess.InProcessChannelBuilder$InProcessClientTransportFactory */
    static final class InProcessClientTransportFactory implements ClientTransportFactory {
        private boolean closed;
        private final String name;
        private final ScheduledExecutorService timerService;
        private final boolean useSharedTimer;

        private InProcessClientTransportFactory(String str, @Nullable ScheduledExecutorService scheduledExecutorService) {
            this.name = str;
            this.useSharedTimer = scheduledExecutorService == null;
            if (this.useSharedTimer) {
                scheduledExecutorService = (ScheduledExecutorService) SharedResourceHolder.get(GrpcUtil.TIMER_SERVICE);
            }
            this.timerService = scheduledExecutorService;
        }

        public ConnectionClientTransport newClientTransport(SocketAddress socketAddress, ClientTransportOptions clientTransportOptions) {
            if (!this.closed) {
                return new InProcessTransport(this.name, clientTransportOptions.getAuthority(), clientTransportOptions.getUserAgent());
            }
            throw new IllegalStateException("The transport factory is closed.");
        }

        public ScheduledExecutorService getScheduledExecutorService() {
            return this.timerService;
        }

        public void close() {
            if (!this.closed) {
                this.closed = true;
                if (this.useSharedTimer) {
                    SharedResourceHolder.release(GrpcUtil.TIMER_SERVICE, this.timerService);
                }
            }
        }
    }

    public InProcessChannelBuilder keepAliveTime(long j, TimeUnit timeUnit) {
        return this;
    }

    public InProcessChannelBuilder keepAliveTimeout(long j, TimeUnit timeUnit) {
        return this;
    }

    public InProcessChannelBuilder keepAliveWithoutCalls(boolean z) {
        return this;
    }

    public InProcessChannelBuilder usePlaintext() {
        return this;
    }

    @Deprecated
    public InProcessChannelBuilder usePlaintext(boolean z) {
        return this;
    }

    public InProcessChannelBuilder useTransportSecurity() {
        return this;
    }

    public static InProcessChannelBuilder forName(String str) {
        return new InProcessChannelBuilder(str);
    }

    public static InProcessChannelBuilder forTarget(String str) {
        throw new UnsupportedOperationException("call forName() instead");
    }

    public static InProcessChannelBuilder forAddress(String str, int i) {
        throw new UnsupportedOperationException("call forName() instead");
    }

    private InProcessChannelBuilder(String str) {
        super(new InProcessSocketAddress(str), "localhost");
        this.name = (String) Preconditions.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
        setStatsRecordStartedRpcs(false);
        setStatsRecordFinishedRpcs(false);
    }

    public final InProcessChannelBuilder maxInboundMessageSize(int i) {
        return (InProcessChannelBuilder) super.maxInboundMessageSize(i);
    }

    public InProcessChannelBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService2) {
        this.scheduledExecutorService = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService2, "scheduledExecutorService");
        return this;
    }

    /* access modifiers changed from: protected */
    public ClientTransportFactory buildTransportFactory() {
        return new InProcessClientTransportFactory(this.name, this.scheduledExecutorService);
    }
}
