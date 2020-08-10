package p043io.netty.handler.ssl;

import java.security.AlgorithmConstraints;
import javax.net.ssl.SSLParameters;

/* renamed from: io.netty.handler.ssl.Java7SslParametersUtils */
final class Java7SslParametersUtils {
    private Java7SslParametersUtils() {
    }

    static void setAlgorithmConstraints(SSLParameters sSLParameters, Object obj) {
        sSLParameters.setAlgorithmConstraints((AlgorithmConstraints) obj);
    }
}
