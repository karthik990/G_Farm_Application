package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelInboundHandlerAdapter;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.TypeParameterMatcher;

/* renamed from: io.netty.handler.codec.MessageToMessageDecoder */
public abstract class MessageToMessageDecoder<I> extends ChannelInboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    /* access modifiers changed from: protected */
    public abstract void decode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception;

    protected MessageToMessageDecoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageDecoder.class, "I");
    }

    protected MessageToMessageDecoder(Class<? extends I> cls) {
        this.matcher = TypeParameterMatcher.get(cls);
    }

    public boolean acceptInboundMessage(Object obj) throws Exception {
        return this.matcher.match(obj);
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        CodecOutputList newInstance = CodecOutputList.newInstance();
        int i = 0;
        try {
            if (acceptInboundMessage(obj)) {
                decode(channelHandlerContext, obj, newInstance);
                ReferenceCountUtil.release(obj);
            } else {
                newInstance.add(obj);
            }
            int size = newInstance.size();
            while (i < size) {
                channelHandlerContext.fireChannelRead(newInstance.getUnsafe(i));
                i++;
            }
            newInstance.recycle();
        } catch (DecoderException e) {
            throw e;
        } catch (Exception e2) {
            try {
                throw new DecoderException((Throwable) e2);
            } catch (Throwable th) {
                int size2 = newInstance.size();
                while (i < size2) {
                    channelHandlerContext.fireChannelRead(newInstance.getUnsafe(i));
                    i++;
                }
                newInstance.recycle();
                throw th;
            }
        } catch (Throwable th2) {
            ReferenceCountUtil.release(obj);
            throw th2;
        }
    }
}
