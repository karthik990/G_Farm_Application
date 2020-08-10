package org.graylog2.gelfclient.transport;

import org.graylog2.gelfclient.GelfConfiguration;
import org.graylog2.gelfclient.encoder.GelfMessageJsonEncoder;
import org.graylog2.gelfclient.encoder.GelfTcpFrameDelimiterEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import p043io.netty.bootstrap.Bootstrap;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.channel.ChannelFuture;
import p043io.netty.channel.ChannelFutureListener;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelInitializer;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.channel.SimpleChannelInboundHandler;
import p043io.netty.channel.socket.SocketChannel;
import p043io.netty.channel.socket.nio.NioSocketChannel;
import p043io.netty.handler.ssl.SslContext;
import p043io.netty.handler.ssl.SslContextBuilder;
import p043io.netty.handler.ssl.util.InsecureTrustManagerFactory;

public class GelfTcpTransport extends AbstractGelfTransport {
    /* access modifiers changed from: private */
    public static final Logger LOG = LoggerFactory.getLogger(GelfTcpTransport.class);

    public GelfTcpTransport(GelfConfiguration gelfConfiguration) {
        super(gelfConfiguration);
    }

    /* access modifiers changed from: protected */
    public void createBootstrap(EventLoopGroup eventLoopGroup) {
        Bootstrap bootstrap = new Bootstrap();
        final GelfSenderThread gelfSenderThread = new GelfSenderThread(this.queue, this.config.getMaxInflightSends());
        ((Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) ((Bootstrap) bootstrap.group(eventLoopGroup)).channel(NioSocketChannel.class)).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Integer.valueOf(this.config.getConnectTimeout()))).option(ChannelOption.TCP_NODELAY, Boolean.valueOf(this.config.isTcpNoDelay()))).option(ChannelOption.SO_KEEPALIVE, Boolean.valueOf(this.config.isTcpKeepAlive()))).remoteAddress(this.config.getRemoteAddress()).handler(new ChannelInitializer<SocketChannel>() {
            /* access modifiers changed from: protected */
            public void initChannel(SocketChannel socketChannel) throws Exception {
                SslContext sslContext;
                if (GelfTcpTransport.this.config.isTlsEnabled()) {
                    GelfTcpTransport.LOG.debug("TLS enabled.");
                    if (!GelfTcpTransport.this.config.isTlsCertVerificationEnabled()) {
                        GelfTcpTransport.LOG.debug("TLS certificate verification disabled!");
                        sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                    } else if (GelfTcpTransport.this.config.getTlsTrustCertChainFile() != null) {
                        GelfTcpTransport.LOG.debug("TLS certificate chain file: {}", (Object) GelfTcpTransport.this.config.getTlsTrustCertChainFile());
                        sslContext = SslContextBuilder.forClient().trustManager(GelfTcpTransport.this.config.getTlsTrustCertChainFile()).build();
                    } else {
                        sslContext = SslContextBuilder.forClient().build();
                    }
                    socketChannel.pipeline().addLast(sslContext.newHandler(socketChannel.alloc()));
                }
                socketChannel.pipeline().addLast(new GelfTcpFrameDelimiterEncoder());
                socketChannel.pipeline().addLast(new GelfMessageJsonEncoder());
                socketChannel.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                    /* access modifiers changed from: protected */
                    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                    }

                    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
                        gelfSenderThread.start(channelHandlerContext.channel());
                    }

                    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                        GelfTcpTransport.LOG.info("Channel disconnected!");
                        gelfSenderThread.stop();
                        GelfTcpTransport.this.scheduleReconnect(channelHandlerContext.channel().eventLoop());
                    }

                    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
                        GelfTcpTransport.LOG.error("Exception caught", th);
                    }
                });
            }
        });
        if (this.config.getSendBufferSize() != -1) {
            bootstrap.option(ChannelOption.SO_SNDBUF, Integer.valueOf(this.config.getSendBufferSize()));
        }
        bootstrap.connect().addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    GelfTcpTransport.LOG.debug("Connected!");
                    return;
                }
                GelfTcpTransport.LOG.error("Connection failed: {}", (Object) channelFuture.cause().getMessage());
                GelfTcpTransport.this.scheduleReconnect(channelFuture.channel().eventLoop());
            }
        });
    }
}
