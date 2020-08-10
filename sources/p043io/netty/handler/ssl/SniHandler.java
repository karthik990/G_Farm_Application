package p043io.netty.handler.ssl;

import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.DecoderException;
import p043io.netty.util.AsyncMapping;
import p043io.netty.util.DomainNameMapping;
import p043io.netty.util.Mapping;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.concurrent.Future;
import p043io.netty.util.concurrent.Promise;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.handler.ssl.SniHandler */
public class SniHandler extends AbstractSniHandler<SslContext> {
    private static final Selection EMPTY_SELECTION = new Selection(null, null);
    protected final AsyncMapping<String, SslContext> mapping;
    private volatile Selection selection;

    /* renamed from: io.netty.handler.ssl.SniHandler$AsyncMappingAdapter */
    private static final class AsyncMappingAdapter implements AsyncMapping<String, SslContext> {
        private final Mapping<? super String, ? extends SslContext> mapping;

        private AsyncMappingAdapter(Mapping<? super String, ? extends SslContext> mapping2) {
            this.mapping = (Mapping) ObjectUtil.checkNotNull(mapping2, "mapping");
        }

        public Future<SslContext> map(String str, Promise<SslContext> promise) {
            try {
                return promise.setSuccess((SslContext) this.mapping.map(str));
            } catch (Throwable th) {
                return promise.setFailure(th);
            }
        }
    }

    /* renamed from: io.netty.handler.ssl.SniHandler$Selection */
    private static final class Selection {
        final SslContext context;
        final String hostname;

        Selection(SslContext sslContext, String str) {
            this.context = sslContext;
            this.hostname = str;
        }
    }

    public SniHandler(Mapping<? super String, ? extends SslContext> mapping2) {
        this((AsyncMapping<? super String, ? extends SslContext>) new AsyncMappingAdapter<Object,Object>(mapping2));
    }

    public SniHandler(DomainNameMapping<? extends SslContext> domainNameMapping) {
        this((Mapping<? super String, ? extends SslContext>) domainNameMapping);
    }

    public SniHandler(AsyncMapping<? super String, ? extends SslContext> asyncMapping) {
        this.selection = EMPTY_SELECTION;
        this.mapping = (AsyncMapping) ObjectUtil.checkNotNull(asyncMapping, "mapping");
    }

    public String hostname() {
        return this.selection.hostname;
    }

    public SslContext sslContext() {
        return this.selection.context;
    }

    /* access modifiers changed from: protected */
    public Future<SslContext> lookup(ChannelHandlerContext channelHandlerContext, String str) throws Exception {
        return this.mapping.map(str, channelHandlerContext.executor().newPromise());
    }

    /* access modifiers changed from: protected */
    public final void onLookupComplete(ChannelHandlerContext channelHandlerContext, String str, Future<SslContext> future) throws Exception {
        if (future.isSuccess()) {
            SslContext sslContext = (SslContext) future.getNow();
            this.selection = new Selection(sslContext, str);
            try {
                replaceHandler(channelHandlerContext, str, sslContext);
            } catch (Throwable th) {
                this.selection = EMPTY_SELECTION;
                PlatformDependent.throwException(th);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("failed to get the SslContext for ");
            sb.append(str);
            throw new DecoderException(sb.toString(), future.cause());
        }
    }

    /* access modifiers changed from: protected */
    public void replaceHandler(ChannelHandlerContext channelHandlerContext, String str, SslContext sslContext) throws Exception {
        SslHandler sslHandler = null;
        try {
            sslHandler = sslContext.newHandler(channelHandlerContext.alloc());
            channelHandlerContext.pipeline().replace((ChannelHandler) this, SslHandler.class.getName(), (ChannelHandler) sslHandler);
        } catch (Throwable th) {
            if (sslHandler != null) {
                ReferenceCountUtil.safeRelease(sslHandler.engine());
            }
            throw th;
        }
    }
}
