package org.graylog2.gelfclient.encoder;

import java.util.List;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.Unpooled;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.handler.codec.MessageToMessageEncoder;

public class GelfTcpFrameDelimiterEncoder extends MessageToMessageEncoder<ByteBuf> {
    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(Unpooled.copiedBuffer(byteBuf, Unpooled.wrappedBuffer(new byte[]{0})));
    }
}
