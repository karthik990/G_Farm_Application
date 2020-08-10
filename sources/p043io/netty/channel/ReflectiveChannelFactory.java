package p043io.netty.channel;

import p043io.netty.channel.Channel;
import p043io.netty.util.internal.StringUtil;

/* renamed from: io.netty.channel.ReflectiveChannelFactory */
public class ReflectiveChannelFactory<T extends Channel> implements ChannelFactory<T> {
    private final Class<? extends T> clazz;

    public ReflectiveChannelFactory(Class<? extends T> cls) {
        if (cls != null) {
            this.clazz = cls;
            return;
        }
        throw new NullPointerException("clazz");
    }

    public T newChannel() {
        try {
            return (Channel) this.clazz.newInstance();
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to create Channel from class ");
            sb.append(this.clazz);
            throw new ChannelException(sb.toString(), th);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.simpleClassName(this.clazz));
        sb.append(".class");
        return sb.toString();
    }
}
