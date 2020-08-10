package p043io.grpc.inprocess;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import p043io.grpc.InternalChannelz.SocketStats;
import p043io.grpc.InternalInstrumented;
import p043io.grpc.ServerStreamTracer.Factory;
import p043io.grpc.internal.InternalServer;
import p043io.grpc.internal.ObjectPool;
import p043io.grpc.internal.ServerListener;
import p043io.grpc.internal.ServerTransportListener;

/* renamed from: io.grpc.inprocess.InProcessServer */
final class InProcessServer implements InternalServer {
    private static final ConcurrentMap<String, InProcessServer> registry = new ConcurrentHashMap();
    private ServerListener listener;
    private final String name;
    private ScheduledExecutorService scheduler;
    private final ObjectPool<ScheduledExecutorService> schedulerPool;
    private boolean shutdown;
    private final List<Factory> streamTracerFactories;

    public int getPort() {
        return -1;
    }

    static InProcessServer findServer(String str) {
        return (InProcessServer) registry.get(str);
    }

    InProcessServer(String str, ObjectPool<ScheduledExecutorService> objectPool, List<Factory> list) {
        this.name = str;
        this.schedulerPool = objectPool;
        this.streamTracerFactories = Collections.unmodifiableList((List) Preconditions.checkNotNull(list, "streamTracerFactories"));
    }

    public void start(ServerListener serverListener) throws IOException {
        this.listener = serverListener;
        this.scheduler = (ScheduledExecutorService) this.schedulerPool.getObject();
        if (registry.putIfAbsent(this.name, this) != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("name already registered: ");
            sb.append(this.name);
            throw new IOException(sb.toString());
        }
    }

    public List<InternalInstrumented<SocketStats>> getListenSockets() {
        return Collections.emptyList();
    }

    public void shutdown() {
        if (registry.remove(this.name, this)) {
            this.scheduler = (ScheduledExecutorService) this.schedulerPool.returnObject(this.scheduler);
            synchronized (this) {
                this.shutdown = true;
                this.listener.serverShutdown();
            }
            return;
        }
        throw new AssertionError();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object) this).add(PostalAddressParser.USER_ADDRESS_NAME_KEY, (Object) this.name).toString();
    }

    /* access modifiers changed from: 0000 */
    public synchronized ServerTransportListener register(InProcessTransport inProcessTransport) {
        if (this.shutdown) {
            return null;
        }
        return this.listener.transportCreated(inProcessTransport);
    }

    /* access modifiers changed from: 0000 */
    public ObjectPool<ScheduledExecutorService> getScheduledExecutorServicePool() {
        return this.schedulerPool;
    }

    /* access modifiers changed from: 0000 */
    public List<Factory> getStreamTracerFactories() {
        return this.streamTracerFactories;
    }
}
