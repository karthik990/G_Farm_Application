package p043io.netty.handler.codec;

import java.util.List;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelOutboundHandlerAdapter;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.util.ReferenceCountUtil;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.TypeParameterMatcher;

/* renamed from: io.netty.handler.codec.MessageToMessageEncoder */
public abstract class MessageToMessageEncoder<I> extends ChannelOutboundHandlerAdapter {
    private final TypeParameterMatcher matcher;

    /* access modifiers changed from: protected */
    public abstract void encode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception;

    protected MessageToMessageEncoder() {
        this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
    }

    protected MessageToMessageEncoder(Class<? extends I> cls) {
        this.matcher = TypeParameterMatcher.get(cls);
    }

    public boolean acceptOutboundMessage(Object obj) throws Exception {
        return this.matcher.match(obj);
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        ChannelPromise channelPromise2;
        ChannelPromise channelPromise3;
        CodecOutputList codecOutputList = null;
        int i = 0;
        boolean z = true;
        try {
            if (acceptOutboundMessage(obj)) {
                CodecOutputList newInstance = CodecOutputList.newInstance();
                try {
                    encode(channelHandlerContext, obj, newInstance);
                    ReferenceCountUtil.release(obj);
                    if (!newInstance.isEmpty()) {
                        codecOutputList = newInstance;
                    } else {
                        newInstance.recycle();
                        StringBuilder sb = new StringBuilder();
                        sb.append(StringUtil.simpleClassName((Object) this));
                        sb.append(" must produce at least one message.");
                        throw new EncoderException(sb.toString());
                    }
                } catch (EncoderException e) {
                    e = e;
                    CodecOutputList codecOutputList2 = newInstance;
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    codecOutputList = newInstance;
                    throw new EncoderException(th);
                }
            } else {
                channelHandlerContext.write(obj, channelPromise);
            }
            if (codecOutputList != null) {
                int size = codecOutputList.size() - 1;
                if (size == 0) {
                    channelHandlerContext.write(codecOutputList.get(0), channelPromise);
                } else if (size > 0) {
                    ChannelPromise voidPromise = channelHandlerContext.voidPromise();
                    if (channelPromise != voidPromise) {
                        z = false;
                    }
                    while (i < size) {
                        if (z) {
                            channelPromise3 = voidPromise;
                        } else {
                            channelPromise3 = channelHandlerContext.newPromise();
                        }
                        channelHandlerContext.write(codecOutputList.getUnsafe(i), channelPromise3);
                        i++;
                    }
                    channelHandlerContext.write(codecOutputList.getUnsafe(size), channelPromise);
                }
                codecOutputList.recycle();
            }
        } catch (EncoderException e2) {
            e = e2;
            throw e;
        } catch (Throwable th2) {
            if (codecOutputList != null) {
                int size2 = codecOutputList.size() - 1;
                if (size2 == 0) {
                    channelHandlerContext.write(codecOutputList.get(0), channelPromise);
                } else if (size2 > 0) {
                    ChannelPromise voidPromise2 = channelHandlerContext.voidPromise();
                    if (channelPromise != voidPromise2) {
                        z = false;
                    }
                    while (i < size2) {
                        if (z) {
                            channelPromise2 = voidPromise2;
                        } else {
                            channelPromise2 = channelHandlerContext.newPromise();
                        }
                        channelHandlerContext.write(codecOutputList.getUnsafe(i), channelPromise2);
                        i++;
                    }
                    channelHandlerContext.write(codecOutputList.getUnsafe(size2), channelPromise);
                }
                codecOutputList.recycle();
            }
            throw th2;
        }
    }
}
