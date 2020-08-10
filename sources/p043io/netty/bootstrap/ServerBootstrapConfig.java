package p043io.netty.bootstrap;

import java.util.Map;
import p043io.netty.channel.ChannelHandler;
import p043io.netty.channel.ChannelOption;
import p043io.netty.channel.EventLoopGroup;
import p043io.netty.channel.ServerChannel;
import p043io.netty.util.AttributeKey;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.bootstrap.ServerBootstrapConfig */
public final class ServerBootstrapConfig extends AbstractBootstrapConfig<ServerBootstrap, ServerChannel> {
    ServerBootstrapConfig(ServerBootstrap serverBootstrap) {
        super(serverBootstrap);
    }

    public EventLoopGroup childGroup() {
        return ((ServerBootstrap) this.bootstrap).childGroup();
    }

    public ChannelHandler childHandler() {
        return ((ServerBootstrap) this.bootstrap).childHandler();
    }

    public Map<ChannelOption<?>, Object> childOptions() {
        return ((ServerBootstrap) this.bootstrap).childOptions();
    }

    public Map<AttributeKey<?>, Object> childAttrs() {
        return ((ServerBootstrap) this.bootstrap).childAttrs();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.setLength(sb.length() - 1);
        String str = ", ";
        sb.append(str);
        EventLoopGroup childGroup = childGroup();
        if (childGroup != null) {
            sb.append("childGroup: ");
            sb.append(StringUtil.simpleClassName((Object) childGroup));
            sb.append(str);
        }
        Map childOptions = childOptions();
        if (!childOptions.isEmpty()) {
            sb.append("childOptions: ");
            sb.append(childOptions);
            sb.append(str);
        }
        Map childAttrs = childAttrs();
        if (!childAttrs.isEmpty()) {
            sb.append("childAttrs: ");
            sb.append(childAttrs);
            sb.append(str);
        }
        ChannelHandler childHandler = childHandler();
        if (childHandler != null) {
            sb.append("childHandler: ");
            sb.append(childHandler);
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
