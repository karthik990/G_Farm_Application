package p043io.netty.handler.logging;

import com.braintreepayments.api.models.PostalAddressParser;
import com.google.api.client.http.HttpMethods;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.net.SocketAddress;
import p043io.netty.buffer.ByteBuf;
import p043io.netty.buffer.ByteBufHolder;
import p043io.netty.buffer.ByteBufUtil;
import p043io.netty.channel.ChannelDuplexHandler;
import p043io.netty.channel.ChannelHandler.Sharable;
import p043io.netty.channel.ChannelHandlerContext;
import p043io.netty.channel.ChannelPromise;
import p043io.netty.util.internal.StringUtil;
import p043io.netty.util.internal.logging.InternalLogLevel;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

@Sharable
/* renamed from: io.netty.handler.logging.LoggingHandler */
public class LoggingHandler extends ChannelDuplexHandler {
    private static final LogLevel DEFAULT_LEVEL = LogLevel.DEBUG;
    protected final InternalLogLevel internalLevel;
    private final LogLevel level;
    protected final InternalLogger logger;

    public LoggingHandler() {
        this(DEFAULT_LEVEL);
    }

    public LoggingHandler(LogLevel logLevel) {
        if (logLevel != null) {
            this.logger = InternalLoggerFactory.getInstance(getClass());
            this.level = logLevel;
            this.internalLevel = logLevel.toInternalLevel();
            return;
        }
        throw new NullPointerException(Param.LEVEL);
    }

    public LoggingHandler(Class<?> cls) {
        this(cls, DEFAULT_LEVEL);
    }

    public LoggingHandler(Class<?> cls, LogLevel logLevel) {
        if (cls == null) {
            throw new NullPointerException("clazz");
        } else if (logLevel != null) {
            this.logger = InternalLoggerFactory.getInstance(cls);
            this.level = logLevel;
            this.internalLevel = logLevel.toInternalLevel();
        } else {
            throw new NullPointerException(Param.LEVEL);
        }
    }

    public LoggingHandler(String str) {
        this(str, DEFAULT_LEVEL);
    }

    public LoggingHandler(String str, LogLevel logLevel) {
        if (str == null) {
            throw new NullPointerException(PostalAddressParser.USER_ADDRESS_NAME_KEY);
        } else if (logLevel != null) {
            this.logger = InternalLoggerFactory.getInstance(str);
            this.level = logLevel;
            this.internalLevel = logLevel.toInternalLevel();
        } else {
            throw new NullPointerException(Param.LEVEL);
        }
    }

    public LogLevel level() {
        return this.level;
    }

    public void channelRegistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "REGISTERED"));
        }
        channelHandlerContext.fireChannelRegistered();
    }

    public void channelUnregistered(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "UNREGISTERED"));
        }
        channelHandlerContext.fireChannelUnregistered();
    }

    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "ACTIVE"));
        }
        channelHandlerContext.fireChannelActive();
    }

    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "INACTIVE"));
        }
        channelHandlerContext.fireChannelInactive();
    }

    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "EXCEPTION", th), th);
        }
        channelHandlerContext.fireExceptionCaught(th);
    }

    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "USER_EVENT", obj));
        }
        channelHandlerContext.fireUserEventTriggered(obj);
    }

    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "BIND", socketAddress));
        }
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, HttpMethods.CONNECT, socketAddress, socketAddress2));
        }
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "DISCONNECT"));
        }
        channelHandlerContext.disconnect(channelPromise);
    }

    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "CLOSE"));
        }
        channelHandlerContext.close(channelPromise);
    }

    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "DEREGISTER"));
        }
        channelHandlerContext.deregister(channelPromise);
    }

    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "READ COMPLETE"));
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "READ", obj));
        }
        channelHandlerContext.fireChannelRead(obj);
    }

    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "WRITE", obj));
        }
        channelHandlerContext.write(obj, channelPromise);
    }

    public void channelWritabilityChanged(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "WRITABILITY CHANGED"));
        }
        channelHandlerContext.fireChannelWritabilityChanged();
    }

    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.logger.isEnabled(this.internalLevel)) {
            this.logger.log(this.internalLevel, format(channelHandlerContext, "FLUSH"));
        }
        channelHandlerContext.flush();
    }

    /* access modifiers changed from: protected */
    public String format(ChannelHandlerContext channelHandlerContext, String str) {
        String obj = channelHandlerContext.channel().toString();
        StringBuilder sb = new StringBuilder(obj.length() + 1 + str.length());
        sb.append(obj);
        sb.append(' ');
        sb.append(str);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public String format(ChannelHandlerContext channelHandlerContext, String str, Object obj) {
        if (obj instanceof ByteBuf) {
            return formatByteBuf(channelHandlerContext, str, (ByteBuf) obj);
        }
        if (obj instanceof ByteBufHolder) {
            return formatByteBufHolder(channelHandlerContext, str, (ByteBufHolder) obj);
        }
        return formatSimple(channelHandlerContext, str, obj);
    }

    /* access modifiers changed from: protected */
    public String format(ChannelHandlerContext channelHandlerContext, String str, Object obj, Object obj2) {
        if (obj2 == null) {
            return formatSimple(channelHandlerContext, str, obj);
        }
        String obj3 = channelHandlerContext.channel().toString();
        String valueOf = String.valueOf(obj);
        String obj4 = obj2.toString();
        StringBuilder sb = new StringBuilder();
        sb.append(obj3.length() + 1);
        sb.append(str);
        sb.append(2);
        sb.append(valueOf.length());
        sb.append(2);
        sb.append(obj4.length());
        StringBuilder sb2 = new StringBuilder(sb.toString());
        sb2.append(obj3);
        sb2.append(' ');
        sb2.append(str);
        sb2.append(": ");
        sb2.append(valueOf);
        sb2.append(", ");
        sb2.append(obj4);
        return sb2.toString();
    }

    private static String formatByteBuf(ChannelHandlerContext channelHandlerContext, String str, ByteBuf byteBuf) {
        String obj = channelHandlerContext.channel().toString();
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes == 0) {
            StringBuilder sb = new StringBuilder(obj.length() + 1 + str.length() + 4);
            sb.append(obj);
            sb.append(' ');
            sb.append(str);
            sb.append(": 0B");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder(obj.length() + 1 + str.length() + 2 + 10 + 1 + 2 + (((readableBytes / 16) + (readableBytes % 15 == 0 ? 0 : 1) + 4) * 80));
        sb2.append(obj);
        sb2.append(' ');
        sb2.append(str);
        sb2.append(": ");
        sb2.append(readableBytes);
        sb2.append('B');
        sb2.append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(sb2, byteBuf);
        return sb2.toString();
    }

    private static String formatByteBufHolder(ChannelHandlerContext channelHandlerContext, String str, ByteBufHolder byteBufHolder) {
        String obj = channelHandlerContext.channel().toString();
        String obj2 = byteBufHolder.toString();
        ByteBuf content = byteBufHolder.content();
        int readableBytes = content.readableBytes();
        String str2 = ", ";
        if (readableBytes == 0) {
            StringBuilder sb = new StringBuilder(obj.length() + 1 + str.length() + 2 + obj2.length() + 4);
            sb.append(obj);
            sb.append(' ');
            sb.append(str);
            sb.append(str2);
            sb.append(obj2);
            sb.append(", 0B");
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder(obj.length() + 1 + str.length() + 2 + obj2.length() + 2 + 10 + 1 + 2 + (((readableBytes / 16) + (readableBytes % 15 == 0 ? 0 : 1) + 4) * 80));
        sb2.append(obj);
        sb2.append(' ');
        sb2.append(str);
        sb2.append(": ");
        sb2.append(obj2);
        sb2.append(str2);
        sb2.append(readableBytes);
        sb2.append('B');
        sb2.append(StringUtil.NEWLINE);
        ByteBufUtil.appendPrettyHexDump(sb2, content);
        return sb2.toString();
    }

    private static String formatSimple(ChannelHandlerContext channelHandlerContext, String str, Object obj) {
        String obj2 = channelHandlerContext.channel().toString();
        String valueOf = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(obj2.length() + 1 + str.length() + 2 + valueOf.length());
        sb.append(obj2);
        sb.append(' ');
        sb.append(str);
        sb.append(": ");
        sb.append(valueOf);
        return sb.toString();
    }
}
