package p043io.netty.handler.codec.marshalling;

import java.util.List;
import org.jboss.marshalling.ByteInput;
import org.jboss.marshalling.Unmarshaller;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.ReplayingDecoder;
import p043io.netty.handler.codec.TooLongFrameException;

/* renamed from: io.netty.handler.codec.marshalling.CompatibleMarshallingDecoder */
public class CompatibleMarshallingDecoder extends ReplayingDecoder<Void> {
    private boolean discardingTooLongFrame;
    protected final int maxObjectSize;
    protected final UnmarshallerProvider provider;

    public CompatibleMarshallingDecoder(UnmarshallerProvider unmarshallerProvider, int i) {
        this.provider = unmarshallerProvider;
        this.maxObjectSize = i;
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.discardingTooLongFrame) {
            byteBuf.skipBytes(actualReadableBytes());
            checkpoint();
            return;
        }
        Unmarshaller unmarshaller = this.provider.getUnmarshaller(channelHandlerContext);
        ByteInput channelBufferByteInput = new ChannelBufferByteInput(byteBuf);
        int i = this.maxObjectSize;
        if (i != Integer.MAX_VALUE) {
            channelBufferByteInput = new LimitingByteInput(channelBufferByteInput, (long) i);
        }
        try {
            unmarshaller.start(channelBufferByteInput);
            Object readObject = unmarshaller.readObject();
            unmarshaller.finish();
            list.add(readObject);
            unmarshaller.close();
        } catch (TooBigObjectException unused) {
            this.discardingTooLongFrame = true;
            throw new TooLongFrameException();
        } catch (Throwable th) {
            unmarshaller.close();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes != 0) {
            if (readableBytes == 1 && byteBuf.getByte(byteBuf.readerIndex()) == 121) {
                byteBuf.skipBytes(1);
                return;
            }
            decode(channelHandlerContext, byteBuf, list);
        }
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (th instanceof TooLongFrameException) {
            channelHandlerContext.close();
        } else {
            super.exceptionCaught(channelHandlerContext, th);
        }
    }
}
