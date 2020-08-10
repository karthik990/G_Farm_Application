package p043io.netty.handler.codec.protobuf;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
/* renamed from: io.netty.handler.codec.protobuf.ProtobufDecoder */
public class ProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {
    private static final boolean HAS_PARSER;
    private final ExtensionRegistryLite extensionRegistry;
    private final MessageLite prototype;

    static {
        boolean z = false;
        try {
            MessageLite.class.getDeclaredMethod("getParserForType", new Class[0]);
            z = true;
        } catch (Throwable unused) {
        }
        HAS_PARSER = z;
    }

    public ProtobufDecoder(MessageLite messageLite) {
        this(messageLite, (ExtensionRegistry) null);
    }

    public ProtobufDecoder(MessageLite messageLite, ExtensionRegistry extensionRegistry2) {
        this(messageLite, (ExtensionRegistryLite) extensionRegistry2);
    }

    public ProtobufDecoder(MessageLite messageLite, ExtensionRegistryLite extensionRegistryLite) {
        if (messageLite != null) {
            this.prototype = messageLite.getDefaultInstanceForType();
            this.extensionRegistry = extensionRegistryLite;
            return;
        }
        throw new NullPointerException("prototype");
    }

    /* access modifiers changed from: protected */
    public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bArr;
        int readableBytes = byteBuf.readableBytes();
        int i = 0;
        if (byteBuf.hasArray()) {
            bArr = byteBuf.array();
            i = byteBuf.arrayOffset() + byteBuf.readerIndex();
        } else {
            bArr = new byte[readableBytes];
            byteBuf.getBytes(byteBuf.readerIndex(), bArr, 0, readableBytes);
        }
        if (this.extensionRegistry == null) {
            if (HAS_PARSER) {
                list.add(this.prototype.getParserForType().parseFrom(bArr, i, readableBytes));
            } else {
                list.add(this.prototype.newBuilderForType().mergeFrom(bArr, i, readableBytes).build());
            }
        } else if (HAS_PARSER) {
            list.add(this.prototype.getParserForType().parseFrom(bArr, i, readableBytes, this.extensionRegistry));
        } else {
            list.add(this.prototype.newBuilderForType().mergeFrom(bArr, i, readableBytes, this.extensionRegistry).build());
        }
    }
}
