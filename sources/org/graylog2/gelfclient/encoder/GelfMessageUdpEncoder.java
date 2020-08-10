package org.graylog2.gelfclient.encoder;

import java.net.InetSocketAddress;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.socket.DatagramPacket;
import p043io.netty.handler.codec.MessageToMessageEncoder;

@Sharable
public class GelfMessageUdpEncoder extends MessageToMessageEncoder<ByteBuf> {
    private static final Logger LOG = LoggerFactory.getLogger(GelfMessageUdpEncoder.class);
    private final InetSocketAddress remoteAddress;

    public GelfMessageUdpEncoder(InetSocketAddress inetSocketAddress) {
        this.remoteAddress = inetSocketAddress;
    }

    /* access modifiers changed from: protected */
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(new DatagramPacket(byteBuf.retain(), this.remoteAddress));
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        LOG.error("UDP encoding error", th);
    }
}
