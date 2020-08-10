package p043io.netty.channel;

import java.util.Map;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.util.internal.InternalThreadLocalMap;

/* renamed from: io.netty.channel.ChannelHandlerAdapter */
public abstract class ChannelHandlerAdapter implements ChannelHandler {
    boolean added;

    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    /* access modifiers changed from: protected */
    public void ensureNotSharable() {
        if (isSharable()) {
            StringBuilder sb = new StringBuilder();
            sb.append("ChannelHandler ");
            sb.append(getClass().getName());
            sb.append(" is not allowed to be shared");
            throw new IllegalStateException(sb.toString());
        }
    }

    public boolean isSharable() {
        Class cls = getClass();
        Map handlerSharableCache = InternalThreadLocalMap.get().handlerSharableCache();
        Boolean bool = (Boolean) handlerSharableCache.get(cls);
        if (bool == null) {
            bool = Boolean.valueOf(cls.isAnnotationPresent(Sharable.class));
            handlerSharableCache.put(cls, bool);
        }
        return bool.booleanValue();
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        channelHandlerContext.fireExceptionCaught(th);
    }
}
