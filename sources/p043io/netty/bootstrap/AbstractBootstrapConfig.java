package p043io.netty.bootstrap;

import java.net.SocketAddress;
import java.util.Map;
import p043io.netty.bootstrap.AbstractBootstrap;
import p043io.netty.channel.Channel;
import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.bootstrap.AbstractBootstrapConfig */
public abstract class AbstractBootstrapConfig<B extends AbstractBootstrap<B, C>, C extends Channel> {
    protected final B bootstrap;

    protected AbstractBootstrapConfig(B b) {
        this.bootstrap = (AbstractBootstrap) ObjectUtil.checkNotNull(b, "bootstrap");
    }

    public final SocketAddress localAddress() {
        return this.bootstrap.localAddress();
    }

    public final ChannelFactory<? extends C> channelFactory() {
        return this.bootstrap.channelFactory();
    }

    public final ChannelHandler handler() {
        return this.bootstrap.handler();
    }

    public final Map<ChannelOption<?>, Object> options() {
        return this.bootstrap.options();
    }

    public final Map<AttributeKey<?>, Object> attrs() {
        return this.bootstrap.attrs();
    }

    public final EventLoopGroup group() {
        return this.bootstrap.group();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName((Object) this));
        sb.append('(');
        EventLoopGroup group = group();
        String str = ", ";
        if (group != null) {
            sb.append("group: ");
            sb.append(StringUtil.simpleClassName((Object) group));
            sb.append(str);
        }
        ChannelFactory channelFactory = channelFactory();
        if (channelFactory != null) {
            sb.append("channelFactory: ");
            sb.append(channelFactory);
            sb.append(str);
        }
        SocketAddress localAddress = localAddress();
        if (localAddress != null) {
            sb.append("localAddress: ");
            sb.append(localAddress);
            sb.append(str);
        }
        Map options = options();
        if (!options.isEmpty()) {
            sb.append("options: ");
            sb.append(options);
            sb.append(str);
        }
        Map attrs = attrs();
        if (!attrs.isEmpty()) {
            sb.append("attrs: ");
            sb.append(attrs);
            sb.append(str);
        }
        ChannelHandler handler = handler();
        if (handler != null) {
            sb.append("handler: ");
            sb.append(handler);
            sb.append(str);
        }
        if (sb.charAt(sb.length() - 1) == '(') {
            sb.append(')');
        } else {
            sb.setCharAt(sb.length() - 2, ')');
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
