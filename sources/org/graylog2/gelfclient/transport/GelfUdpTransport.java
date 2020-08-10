package org.graylog2.gelfclient.transport;

import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.encoder.GelfCompressionEncoder;
import org.graylog2.gelfclient.encoder.GelfMessageChunkEncoder;
import org.graylog2.gelfclient.encoder.GelfMessageJsonEncoder;
import org.graylog2.gelfclient.encoder.GelfMessageUdpEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import p043io.netty.bootstrap.Bootstrap;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelInitializer;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.channel.SimpleChannelInboundHandler;
import p043io.netty.channel.socket.DatagramPacket;
import p043io.netty.channel.socket.nio.NioDatagramChannel;

public class GelfUdpTransport extends AbstractGelfTransport {
    /* access modifiers changed from: private */
    public static final Logger LOG = LoggerFactory.getLogger(GelfUdpTransport.class);

    public GelfUdpTransport(GelfConfiguration gelfConfiguration) {
        super(gelfConfiguration);
    }

    /* access modifiers changed from: protected */
    public void createBootstrap(EventLoopGroup eventLoopGroup) {
        Bootstrap bootstrap = new Bootstrap();
        final GelfSenderThread gelfSenderThread = new GelfSenderThread(this.queue, this.config.getMaxInflightSends());
        ((Bootstrap) ((Bootstrap) bootstrap.group(eventLoopGroup)).channel(NioDatagramChannel.class)).handler(new ChannelInitializer<Channel>() {
            /* access modifiers changed from: protected */
            public void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new GelfMessageUdpEncoder(GelfUdpTransport.this.config.getRemoteAddress()));
                channel.pipeline().addLast(new GelfMessageChunkEncoder());
                channel.pipeline().addLast(new GelfCompressionEncoder());
                channel.pipeline().addLast(new GelfMessageJsonEncoder());
                channel.pipeline().addLast(new SimpleChannelInboundHandler<DatagramPacket>() {
                    /* access modifiers changed from: protected */
                    public void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
                    }

                    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
                        gelfSenderThread.start(channelHandlerContext.channel());
                    }

                    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                        gelfSenderThread.stop();
                    }

                    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
                        GelfUdpTransport.LOG.error("Exception caught", th);
                    }
                });
            }
        });
        if (this.config.getSendBufferSize() != -1) {
            bootstrap.option(ChannelOption.SO_SNDBUF, Integer.valueOf(this.config.getSendBufferSize()));
        }
        bootstrap.bind(0);
    }
}
