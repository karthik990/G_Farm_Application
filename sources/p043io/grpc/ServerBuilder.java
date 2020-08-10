package p043io.grpc;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import p043io.grpc.ServerBuilder;
import p043io.grpc.ServerStreamTracer.Factory;

/* renamed from: io.grpc.ServerBuilder */
public abstract class ServerBuilder<T extends ServerBuilder<T>> {
    private T thisT() {
        return this;
    }

    public abstract T addService(BindableService bindableService);

    public abstract T addService(ServerServiceDefinition serverServiceDefinition);

    public abstract Server build();

    public abstract T compressorRegistry(@Nullable CompressorRegistry compressorRegistry);

    public abstract T decompressorRegistry(@Nullable DecompressorRegistry decompressorRegistry);

    public abstract T directExecutor();

    public abstract T executor(@Nullable Executor executor);

    public abstract T fallbackHandlerRegistry(@Nullable HandlerRegistry handlerRegistry);

    public abstract T useTransportSecurity(File file, File file2);

    public static ServerBuilder<?> forPort(int i) {
        return ServerProvider.provider().builderForPort(i);
    }

    public T intercept(ServerInterceptor serverInterceptor) {
        throw new UnsupportedOperationException();
    }

    public T addTransportFilter(ServerTransportFilter serverTransportFilter) {
        throw new UnsupportedOperationException();
    }

    public T addStreamTracerFactory(Factory factory) {
        throw new UnsupportedOperationException();
    }

    public T useTransportSecurity(InputStream inputStream, InputStream inputStream2) {
        throw new UnsupportedOperationException();
    }

    public T handshakeTimeout(long j, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    public T maxInboundMessageSize(int i) {
        Preconditions.checkArgument(i >= 0, "bytes must be >= 0");
        return thisT();
    }

    public T setBinaryLog(BinaryLog binaryLog) {
        throw new UnsupportedOperationException();
    }
}
