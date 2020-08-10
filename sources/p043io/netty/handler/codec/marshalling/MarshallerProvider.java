package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.Marshaller;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.marshalling.MarshallerProvider */
public interface MarshallerProvider {
    Marshaller getMarshaller(ChannelHandlerContext channelHandlerContext) throws Exception;
}
