package p043io.netty.channel.group;

import p043io.netty.channel.Channel;
import p043io.netty.channel.ServerChannel;

/* renamed from: io.netty.channel.group.ChannelMatchers */
public final class ChannelMatchers {
    private static final ChannelMatcher ALL_MATCHER = new ChannelMatcher() {
        public boolean matches(Channel channel) {
            return true;
        }
    };
    private static final ChannelMatcher NON_SERVER_CHANNEL_MATCHER = isNotInstanceOf(ServerChannel.class);
    private static final ChannelMatcher SERVER_CHANNEL_MATCHER = isInstanceOf(ServerChannel.class);

    /* renamed from: io.netty.channel.group.ChannelMatchers$ClassMatcher */
    private static final class ClassMatcher implements ChannelMatcher {
        private final Class<? extends Channel> clazz;

        ClassMatcher(Class<? extends Channel> cls) {
            this.clazz = cls;
        }

        public boolean matches(Channel channel) {
            return this.clazz.isInstance(channel);
        }
    }

    /* renamed from: io.netty.channel.group.ChannelMatchers$CompositeMatcher */
    private static final class CompositeMatcher implements ChannelMatcher {
        private final ChannelMatcher[] matchers;

        CompositeMatcher(ChannelMatcher... channelMatcherArr) {
            this.matchers = channelMatcherArr;
        }

        public boolean matches(Channel channel) {
            for (ChannelMatcher matches : this.matchers) {
                if (!matches.matches(channel)) {
                    return false;
                }
            }
            return true;
        }
    }

    /* renamed from: io.netty.channel.group.ChannelMatchers$InstanceMatcher */
    private static final class InstanceMatcher implements ChannelMatcher {
        private final Channel channel;

        InstanceMatcher(Channel channel2) {
            this.channel = channel2;
        }

        public boolean matches(Channel channel2) {
            return this.channel == channel2;
        }
    }

    /* renamed from: io.netty.channel.group.ChannelMatchers$InvertMatcher */
    private static final class InvertMatcher implements ChannelMatcher {
        private final ChannelMatcher matcher;

        InvertMatcher(ChannelMatcher channelMatcher) {
            this.matcher = channelMatcher;
        }

        public boolean matches(Channel channel) {
            return !this.matcher.matches(channel);
        }
    }

    private ChannelMatchers() {
    }

    public static ChannelMatcher all() {
        return ALL_MATCHER;
    }

    public static ChannelMatcher isNot(Channel channel) {
        return invert(m3999is(channel));
    }

    /* renamed from: is */
    public static ChannelMatcher m3999is(Channel channel) {
        return new InstanceMatcher(channel);
    }

    public static ChannelMatcher isInstanceOf(Class<? extends Channel> cls) {
        return new ClassMatcher(cls);
    }

    public static ChannelMatcher isNotInstanceOf(Class<? extends Channel> cls) {
        return invert(isInstanceOf(cls));
    }

    public static ChannelMatcher isServerChannel() {
        return SERVER_CHANNEL_MATCHER;
    }

    public static ChannelMatcher isNonServerChannel() {
        return NON_SERVER_CHANNEL_MATCHER;
    }

    public static ChannelMatcher invert(ChannelMatcher channelMatcher) {
        return new InvertMatcher(channelMatcher);
    }

    public static ChannelMatcher compose(ChannelMatcher... channelMatcherArr) {
        if (channelMatcherArr.length < 1) {
            throw new IllegalArgumentException("matchers must at least contain one element");
        } else if (channelMatcherArr.length == 1) {
            return channelMatcherArr[0];
        } else {
            return new CompositeMatcher(channelMatcherArr);
        }
    }
}
