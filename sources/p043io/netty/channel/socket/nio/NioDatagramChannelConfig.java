package p043io.netty.channel.socket.nio;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.util.Enumeration;
import p043io.netty.channel.ChannelException;
import p043io.netty.channel.socket.DatagramChannelConfig;
import p043io.netty.channel.socket.DefaultDatagramChannelConfig;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.SocketUtils;

/* renamed from: io.netty.channel.socket.nio.NioDatagramChannelConfig */
class NioDatagramChannelConfig extends DefaultDatagramChannelConfig {
    private static final Method GET_OPTION;
    private static final Object IP_MULTICAST_IF;
    private static final Object IP_MULTICAST_LOOP;
    private static final Object IP_MULTICAST_TTL;
    private static final Method SET_OPTION;
    private final DatagramChannel javaChannel;

    static {
        Class cls;
        Class cls2;
        Object obj;
        Object obj2;
        Object obj3;
        Method method;
        Class cls3;
        ClassLoader classLoader = PlatformDependent.getClassLoader(DatagramChannel.class);
        Method method2 = null;
        try {
            cls = Class.forName("java.net.SocketOption", true, classLoader);
        } catch (Exception unused) {
            cls = null;
        }
        try {
            cls2 = Class.forName("java.net.StandardSocketOptions", true, classLoader);
        } catch (Exception unused2) {
            cls2 = null;
        }
        if (cls != null) {
            try {
                obj2 = cls2.getDeclaredField("IP_MULTICAST_TTL").get(null);
                try {
                    obj = cls2.getDeclaredField("IP_MULTICAST_IF").get(null);
                    try {
                        obj3 = cls2.getDeclaredField("IP_MULTICAST_LOOP").get(null);
                        try {
                            cls3 = Class.forName("java.nio.channels.NetworkChannel", true, classLoader);
                        } catch (Throwable unused3) {
                            cls3 = null;
                        }
                        if (cls3 == null) {
                            method = null;
                        } else {
                            try {
                                method2 = cls3.getDeclaredMethod("getOption", new Class[]{cls});
                                try {
                                    method = cls3.getDeclaredMethod("setOption", new Class[]{cls, Object.class});
                                } catch (Exception e) {
                                    throw new Error("cannot locate the setOption() method", e);
                                }
                            } catch (Exception e2) {
                                throw new Error("cannot locate the getOption() method", e2);
                            }
                        }
                    } catch (Exception e3) {
                        throw new Error("cannot locate the IP_MULTICAST_LOOP field", e3);
                    }
                } catch (Exception e4) {
                    throw new Error("cannot locate the IP_MULTICAST_IF field", e4);
                }
            } catch (Exception e5) {
                throw new Error("cannot locate the IP_MULTICAST_TTL field", e5);
            }
        } else {
            method = null;
            obj3 = null;
            obj2 = null;
            obj = null;
        }
        IP_MULTICAST_TTL = obj2;
        IP_MULTICAST_IF = obj;
        IP_MULTICAST_LOOP = obj3;
        GET_OPTION = method2;
        SET_OPTION = method;
    }

    NioDatagramChannelConfig(NioDatagramChannel nioDatagramChannel, DatagramChannel datagramChannel) {
        super(nioDatagramChannel, datagramChannel.socket());
        this.javaChannel = datagramChannel;
    }

    public int getTimeToLive() {
        return ((Integer) getOption0(IP_MULTICAST_TTL)).intValue();
    }

    public DatagramChannelConfig setTimeToLive(int i) {
        setOption0(IP_MULTICAST_TTL, Integer.valueOf(i));
        return this;
    }

    public InetAddress getInterface() {
        NetworkInterface networkInterface = getNetworkInterface();
        if (networkInterface == null) {
            return null;
        }
        Enumeration addressesFromNetworkInterface = SocketUtils.addressesFromNetworkInterface(networkInterface);
        if (addressesFromNetworkInterface.hasMoreElements()) {
            return (InetAddress) addressesFromNetworkInterface.nextElement();
        }
        return null;
    }

    public DatagramChannelConfig setInterface(InetAddress inetAddress) {
        try {
            setNetworkInterface(NetworkInterface.getByInetAddress(inetAddress));
            return this;
        } catch (SocketException e) {
            throw new ChannelException((Throwable) e);
        }
    }

    public NetworkInterface getNetworkInterface() {
        return (NetworkInterface) getOption0(IP_MULTICAST_IF);
    }

    public DatagramChannelConfig setNetworkInterface(NetworkInterface networkInterface) {
        setOption0(IP_MULTICAST_IF, networkInterface);
        return this;
    }

    public boolean isLoopbackModeDisabled() {
        return ((Boolean) getOption0(IP_MULTICAST_LOOP)).booleanValue();
    }

    public DatagramChannelConfig setLoopbackModeDisabled(boolean z) {
        setOption0(IP_MULTICAST_LOOP, Boolean.valueOf(z));
        return this;
    }

    public DatagramChannelConfig setAutoRead(boolean z) {
        super.setAutoRead(z);
        return this;
    }

    /* access modifiers changed from: protected */
    public void autoReadCleared() {
        ((NioDatagramChannel) this.channel).clearReadPending0();
    }

    private Object getOption0(Object obj) {
        Method method = GET_OPTION;
        if (method != null) {
            try {
                return method.invoke(this.javaChannel, new Object[]{obj});
            } catch (Exception e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void setOption0(Object obj, Object obj2) {
        Method method = SET_OPTION;
        if (method != null) {
            try {
                method.invoke(this.javaChannel, new Object[]{obj, obj2});
            } catch (Exception e) {
                throw new ChannelException((Throwable) e);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
