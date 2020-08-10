package p043io.grpc.inprocess;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.common.base.Preconditions;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import p043io.grpc.ServerStreamTracer.Factory;
import p043io.grpc.internal.AbstractServerImplBuilder;
import p043io.grpc.internal.FixedObjectPool;
import p043io.grpc.internal.GrpcUtil;
import p043io.grpc.internal.ObjectPool;
import p043io.grpc.internal.SharedResourcePool;

/* renamed from: io.grpc.inprocess.InProcessServerBuilder */
public final class InProcessServerBuilder extends AbstractServerImplBuilder<InProcessServerBuilder> {
    private final String name;
    private ObjectPool<ScheduledExecutorService> schedulerPool = SharedResourcePool.forResource(GrpcUtil.TIMER_SERVICE);

    public static InProcessServerBuilder forName(String str) {
        return new InProcessServerBuilder(str);
    }

    public static InProcessServerBuilder forPort(int i) {
        throw new UnsupportedOperationException("call forName() instead");
    }

    public static String generateName() {
        return UUID.randomUUID().toString();
    }

    private InProcessServerBuilder(String str) {
        this.name = (String) Preconditions.checkNotNull(str, PostalAddressParser.USER_ADDRESS_NAME_KEY);
        setStatsRecordStartedRpcs(false);
        setStatsRecordFinishedRpcs(false);
        handshakeTimeout(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    public InProcessServerBuilder scheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        this.schedulerPool = new FixedObjectPool(Preconditions.checkNotNull(scheduledExecutorService, "scheduledExecutorService"));
        return this;
    }

    /* access modifiers changed from: protected */
    public InProcessServer buildTransportServer(List<Factory> list) {
        return new InProcessServer(this.name, this.schedulerPool, list);
    }

    public InProcessServerBuilder useTransportSecurity(File file, File file2) {
        throw new UnsupportedOperationException("TLS not supported in InProcessServer");
    }
}
