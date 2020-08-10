package p043io.netty.handler.codec.marshalling;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.util.Attribute;
import p043io.netty.util.AttributeKey;

/* renamed from: io.netty.handler.codec.marshalling.ContextBoundUnmarshallerProvider */
public class ContextBoundUnmarshallerProvider extends DefaultUnmarshallerProvider {
    private static final AttributeKey<Unmarshaller> UNMARSHALLER = AttributeKey.valueOf(ContextBoundUnmarshallerProvider.class, "UNMARSHALLER");

    public ContextBoundUnmarshallerProvider(MarshallerFactory marshallerFactory, MarshallingConfiguration marshallingConfiguration) {
        super(marshallerFactory, marshallingConfiguration);
    }

    public Unmarshaller getUnmarshaller(ChannelHandlerContext channelHandlerContext) throws Exception {
        Attribute attr = channelHandlerContext.attr(UNMARSHALLER);
        Unmarshaller unmarshaller = (Unmarshaller) attr.get();
        if (unmarshaller != null) {
            return unmarshaller;
        }
        Unmarshaller unmarshaller2 = super.getUnmarshaller(channelHandlerContext);
        attr.set(unmarshaller2);
        return unmarshaller2;
    }
}
