package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.util.concurrent.FastThreadLocal;

/* renamed from: io.netty.handler.codec.marshalling.ThreadLocalUnmarshallerProvider */
public class ThreadLocalUnmarshallerProvider implements UnmarshallerProvider {
    private final MarshallingConfiguration config;
    private final MarshallerFactory factory;
    private final FastThreadLocal<Unmarshaller> unmarshallers = new FastThreadLocal<>();

    public ThreadLocalUnmarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        this.factory = marshallerFactory;
        this.config = marshallingConfiguration;
    }

    public Unmarshaller getUnmarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        Unmarshaller unmarshaller = (Unmarshaller) this.unmarshallers.get();
        if (unmarshaller != null) {
            return unmarshaller;
        }
        Unmarshaller createUnmarshaller = this.factory.createUnmarshaller(this.config);
        this.unmarshallers.set(createUnmarshaller);
        return createUnmarshaller;
    }
}
