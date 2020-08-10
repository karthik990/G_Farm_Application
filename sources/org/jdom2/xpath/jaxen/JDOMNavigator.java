package org.jdom2.xpath.jaxen;

import java.util.HashMap;
import java.util.List;
import org.jaxen.NamespaceContext;
import org.jdom2.Namespace;
import org.jdom2.NamespaceAware;

final class JDOMNavigator extends JDOMCoreNavigator implements NamespaceContext {
    private static final long serialVersionUID = 200;
    private final HashMap<String, String> nsFromContext = new HashMap<>();
    private final HashMap<String, String> nsFromUser = new HashMap<>();

    JDOMNavigator() {
    }

    /* access modifiers changed from: 0000 */
    public void reset() {
        super.reset();
        this.nsFromContext.clear();
    }

    /* access modifiers changed from: 0000 */
    public void setContext(Object obj) {
        this.nsFromContext.clear();
        List<Namespace> list = obj instanceof NamespaceAware ? ((NamespaceAware) obj).getNamespacesInScope() : obj instanceof NamespaceContainer ? ((NamespaceContainer) obj).getParentElement().getNamespacesInScope() : null;
        if (list != null) {
            for (Namespace namespace : list) {
                this.nsFromContext.put(namespace.getPrefix(), namespace.getURI());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void includeNamespace(Namespace namespace) {
        this.nsFromUser.put(namespace.getPrefix(), namespace.getURI());
    }

    public String translateNamespacePrefixToUri(String str) {
        if (str == null) {
            return null;
        }
        String str2 = (String) this.nsFromUser.get(str);
        if (str2 != null) {
            return str2;
        }
        return (String) this.nsFromContext.get(str);
    }
}
