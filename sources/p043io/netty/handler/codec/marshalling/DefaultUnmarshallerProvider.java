package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider */
public class DefaultUnmarshallerProvider implements UnmarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;

    public DefaultUnmarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        this.factory = marshallerFactory;
        this.config = marshallingConfiguration;
    }

    public Unmarshaller getUnmarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.factory.createUnmarshaller(this.config);
    }
}
