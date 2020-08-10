package p043io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* renamed from: io.netty.handler.ssl.IdentityCipherSuiteFilter */
public final class IdentityCipherSuiteFilter implements CipherSuiteFilter {
    public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter();

    private IdentityCipherSuiteFilter() {
    }

    public String[] filterCipherSuites(Iterable<String> iterable, List<String> list, Set<String> set) {
        if (iterable == null) {
            return (String[]) list.toArray(new String[list.size()]);
        }
        ArrayList arrayList = new ArrayList(set.size());
        for (String str : iterable) {
            if (str == null) {
                break;
            }
            arrayList.add(str);
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
