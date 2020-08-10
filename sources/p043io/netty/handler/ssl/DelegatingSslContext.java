package p043io.netty.handler.ssl;

import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;
import p043io.netty.buffer.ByteBufAllocator;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.handler.ssl.DelegatingSslContext */
public abstract class DelegatingSslContext extends SslContext {
    private final SslContext ctx;

    /* access modifiers changed from: protected */
    public abstract void initEngine(SSLEngine sSLEngine);

    protected DelegatingSslContext(SslContext sslContext) {
        this.ctx = (SslContext) ObjectUtil.checkNotNull(sslContext, "ctx");
    }

    public final boolean isClient() {
        return this.ctx.isClient();
    }

    public final List<String> cipherSuites() {
        return this.ctx.cipherSuites();
    }

    public final long sessionCacheSize() {
        return this.ctx.sessionCacheSize();
    }

    public final long sessionTimeout() {
        return this.ctx.sessionTimeout();
    }

    public final ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.ctx.applicationProtocolNegotiator();
    }

    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator) {
        SSLEngine newEngine = this.ctx.newEngine(byteBufAllocator);
        initEngine(newEngine);
        return newEngine;
    }

    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String str, int i) {
        SSLEngine newEngine = this.ctx.newEngine(byteBufAllocator, str, i);
        initEngine(newEngine);
        return newEngine;
    }

    public final SSLSessionContext sessionContext() {
        return this.ctx.sessionContext();
    }
}
