package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.util.concurrent.FastThreadLocal;

/* renamed from: io.netty.handler.codec.marshalling.ThreadLocalMarshallerProvider */
public class ThreadLocalMarshallerProvider implements MarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;
    private final FastThreadLocal<Marshaller> marshallers = new FastThreadLocal<>();

    public ThreadLocalMarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        this.factory = marshallerFactory;
        this.config = marshallingConfiguration;
    }

    public Marshaller getMarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        Marshaller marshaller = (Marshaller) this.marshallers.get();
        if (marshaller != null) {
            return marshaller;
        }
        Marshaller createMarshaller = this.factory.createMarshaller(this.config);
        this.marshallers.set(createMarshaller);
        return createMarshaller;
    }
}
