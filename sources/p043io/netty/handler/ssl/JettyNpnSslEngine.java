package p043io.netty.handler.ssl;

import java.util.LinkedHashSet;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLException;
import org.eclipse.jetty.npn.NextProtoNego;
import org.eclipse.jetty.npn.NextProtoNego.ClientProvider;
import org.eclipse.jetty.npn.NextProtoNego.ServerProvider;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener;
import p043io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;

/* renamed from: io.netty.handler.ssl.JettyNpnSslEngine */
final class JettyNpnSslEngine extends JdkSslEngine {
    private static boolean available;

    static boolean isAvailable() {
        updateAvailability();
        return available;
    }

    private static void updateAvailability() {
        if (!available) {
            try {
                Class.forName("sun.security.ssl.NextProtoNegoExtension", true, null);
                available = true;
            } catch (Exception unused) {
            }
        }
    }

    JettyNpnSslEngine(SSLEngine sSLEngine, final JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, boolean z) {
        super(sSLEngine);
        ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "applicationNegotiator");
        if (z) {
            final ProtocolSelectionListener protocolSelectionListener = (ProtocolSelectionListener) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolListenerFactory().newListener(this, jdkApplicationProtocolNegotiator.protocols()), "protocolListener");
            NextProtoNego.put(sSLEngine, new ServerProvider() {
                public void unsupported() {
                    protocolSelectionListener.unsupported();
                }

                public List<String> protocols() {
                    return jdkApplicationProtocolNegotiator.protocols();
                }

                public void protocolSelected(String str) {
                    try {
                        protocolSelectionListener.selected(str);
                    } catch (Throwable th) {
                        PlatformDependent.throwException(th);
                    }
                }
            });
            return;
        }
        final ProtocolSelector protocolSelector = (ProtocolSelector) ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator.protocolSelectorFactory().newSelector(this, new LinkedHashSet(jdkApplicationProtocolNegotiator.protocols())), "protocolSelector");
        NextProtoNego.put(sSLEngine, new ClientProvider() {
            public boolean supports() {
                return true;
            }

            public void unsupported() {
                protocolSelector.unsupported();
            }

            public String selectProtocol(List<String> list) {
                try {
                    return protocolSelector.select(list);
                } catch (Throwable th) {
                    PlatformDependent.throwException(th);
                    return null;
                }
            }
        });
    }

    public void closeInbound() throws SSLException {
        NextProtoNego.remove(getWrappedEngine());
        super.closeInbound();
    }

    public void closeOutbound() {
        NextProtoNego.remove(getWrappedEngine());
        super.closeOutbound();
    }
}
