package org.jdom2.xpath.jaxen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jaxen.BaseXPath;
import org.jaxen.JaxenException;
import org.jaxen.NamespaceContext;
import org.jaxen.UnresolvableException;
import org.jaxen.VariableContext;
import org.jaxen.XPath;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;
import org.jdom2.xpath.util.AbstractXPathCompiled;

class JaxenCompiled<T> extends AbstractXPathCompiled<T> implements NamespaceContext, VariableContext {
    private final JDOM2Navigator navigator;
    private final XPath xPath;

    private static final Object unWrapNS(Object obj) {
        return obj instanceof NamespaceContainer ? ((NamespaceContainer) obj).getNamespace() : obj;
    }

    private static final List<Object> unWrap(List<?> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (Object unWrapNS : list) {
            arrayList.add(unWrapNS(unWrapNS));
        }
        return arrayList;
    }

    public JaxenCompiled(String str, Filter<T> filter, Map<String, Object> map, Namespace[] namespaceArr) {
        super(str, filter, map, namespaceArr);
        this.navigator = new JDOM2Navigator();
        try {
            this.xPath = new BaseXPath(str, this.navigator);
            this.xPath.setNamespaceContext(this);
            this.xPath.setVariableContext(this);
        } catch (JaxenException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to compile '");
            sb.append(str);
            sb.append("'. See Cause.");
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    private JaxenCompiled(JaxenCompiled<T> jaxenCompiled) {
        this(jaxenCompiled.getExpression(), jaxenCompiled.getFilter(), jaxenCompiled.getVariables(), jaxenCompiled.getNamespaces());
    }

    public String translateNamespacePrefixToUri(String str) {
        return getNamespace(str).getURI();
    }

    public Object getVariableValue(String str, String str2, String str3) throws UnresolvableException {
        String str4 = "";
        if (str == null) {
            str = str4;
        }
        if (str2 == null) {
            str2 = str4;
        }
        try {
            if (str4.equals(str)) {
                str = getNamespace(str2).getURI();
            }
            return getVariable(str3, Namespace.getNamespace(str));
        } catch (IllegalArgumentException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to resolve variable ");
            sb.append(str3);
            sb.append(" in namespace '");
            sb.append(str);
            sb.append("' to a vaulue.");
            throw new UnresolvableException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    public List<?> evaluateRawAll(Object obj) {
        try {
            return unWrap(this.xPath.selectNodes(obj));
        } catch (JaxenException e) {
            throw new IllegalStateException("Unable to evaluate expression. See cause", e);
        }
    }

    /* access modifiers changed from: protected */
    public Object evaluateRawFirst(Object obj) {
        try {
            return unWrapNS(this.xPath.selectSingleNode(obj));
        } catch (JaxenException e) {
            throw new IllegalStateException("Unable to evaluate expression. See cause", e);
        }
    }

    public JaxenCompiled<T> clone() {
        return new JaxenCompiled<>(this);
    }
}
