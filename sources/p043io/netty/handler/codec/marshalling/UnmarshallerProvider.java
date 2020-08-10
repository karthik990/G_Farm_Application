package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.Unmarshaller;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.marshalling.UnmarshallerProvider */
public interface UnmarshallerProvider {
    Unmarshaller getUnmarshaller(ChannelHandlerContext channelHandlerContext) throws Exception;
}
