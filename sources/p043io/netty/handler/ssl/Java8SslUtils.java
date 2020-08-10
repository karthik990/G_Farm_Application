package p043io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLParameters;

/* renamed from: io.netty.handler.ssl.Java8SslUtils */
final class Java8SslUtils {
    private Java8SslUtils() {
    }

    static List<String> getSniHostNames(SSLParameters sSLParameters) {
        List<SNIServerName> serverNames = sSLParameters.getServerNames();
        if (serverNames == null || serverNames.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(serverNames.size());
        for (SNIServerName sNIServerName : serverNames) {
            if (sNIServerName instanceof SNIHostName) {
                arrayList.add(((SNIHostName) sNIServerName).getAsciiName());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Only ");
                sb.append(SNIHostName.class.getName());
                sb.append(" instances are supported, but found: ");
                sb.append(sNIServerName);
                throw new IllegalArgumentException(sb.toString());
            }
        }
        return arrayList;
    }

    static void setSniHostNames(SSLParameters sSLParameters, List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (String sNIHostName : list) {
            arrayList.add(new SNIHostName(sNIHostName));
        }
        sSLParameters.setServerNames(arrayList);
    }

    static boolean getUseCipherSuitesOrder(SSLParameters sSLParameters) {
        return sSLParameters.getUseCipherSuitesOrder();
    }

    static void setUseCipherSuitesOrder(SSLParameters sSLParameters, boolean z) {
        sSLParameters.setUseCipherSuitesOrder(z);
    }

    static void setSNIMatchers(SSLParameters sSLParameters, Collection<?> collection) {
        sSLParameters.setSNIMatchers(collection);
    }

    static boolean checkSniHostnameMatch(Collection<?> collection, String str) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        SNIHostName sNIHostName = new SNIHostName(str);
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            SNIMatcher sNIMatcher = (SNIMatcher) it.next();
            if (sNIMatcher.getType() == 0 && sNIMatcher.matches(sNIHostName)) {
                return true;
            }
        }
        return false;
    }
}
