package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import p043io.netty.channel.ChannelHandlerContext;

/* renamed from: io.netty.handler.codec.marshalling.DefaultMarshallerProvider */
public class DefaultMarshallerProvider implements MarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;

    public DefaultMarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        this.factory = marshallerFactory;
        this.config = marshallingConfiguration;
    }

    public Marshaller getMarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        return this.factory.createMarshaller(this.config);
    }
}
