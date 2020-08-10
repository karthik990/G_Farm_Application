package org.jdom2.xpath.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jdom2.Namespace;
import org.jdom2.Verifier;
import org.jdom2.filter.Filter;
import org.jdom2.xpath.XPathDiagnostic;
import org.jdom2.xpath.XPathExpression;

public abstract class AbstractXPathCompiled<T> implements XPathExpression<T> {
    private static final NamespaceComparator NSSORT = new NamespaceComparator();
    private final Filter<T> xfilter;
    private final Map<String, Namespace> xnamespaces = new HashMap();
    private final String xquery;
    private Map<String, Map<String, Object>> xvariables = new HashMap();

    private static final class NamespaceComparator implements Comparator<Namespace> {
        private NamespaceComparator() {
        }

        public int compare(Namespace namespace, Namespace namespace2) {
            return namespace.getPrefix().compareTo(namespace2.getPrefix());
        }
    }

    /* access modifiers changed from: protected */
    public abstract List<?> evaluateRawAll(Object obj);

    /* access modifiers changed from: protected */
    public abstract Object evaluateRawFirst(Object obj);

    private static final String getPrefixForURI(String str, Namespace[] namespaceArr) {
        for (Namespace namespace : namespaceArr) {
            if (namespace.getURI().equals(str)) {
                return namespace.getPrefix();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No namespace defined with URI ");
        sb.append(str);
        throw new IllegalStateException(sb.toString());
    }

    public AbstractXPathCompiled(String str, Filter<T> filter, Map<String, Object> map, Namespace[] namespaceArr) {
        String str2;
        String str3;
        if (str == null) {
            throw new NullPointerException("Null query");
        } else if (filter != null) {
            this.xnamespaces.put(Namespace.NO_NAMESPACE.getPrefix(), Namespace.NO_NAMESPACE);
            if (namespaceArr != null) {
                int length = namespaceArr.length;
                int i = 0;
                while (i < length) {
                    Namespace namespace = namespaceArr[i];
                    if (namespace != null) {
                        Namespace namespace2 = (Namespace) this.xnamespaces.put(namespace.getPrefix(), namespace);
                        if (namespace2 == null || namespace2 == namespace) {
                            i++;
                        } else if (namespace2 == Namespace.NO_NAMESPACE) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("The default (no prefix) Namespace URI for XPath queries is always '' and it cannot be redefined to '");
                            sb.append(namespace.getURI());
                            sb.append("'.");
                            throw new IllegalArgumentException(sb.toString());
                        } else {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("A Namespace with the prefix '");
                            sb2.append(namespace.getPrefix());
                            sb2.append("' has already been declared.");
                            throw new IllegalArgumentException(sb2.toString());
                        }
                    } else {
                        throw new NullPointerException("Null namespace");
                    }
                }
            }
            if (map != null) {
                for (Entry entry : map.entrySet()) {
                    String str4 = (String) entry.getKey();
                    if (str4 != null) {
                        int indexOf = str4.indexOf(58);
                        if (indexOf < 0) {
                            str2 = "";
                        } else {
                            str2 = str4.substring(0, indexOf);
                        }
                        if (indexOf < 0) {
                            str3 = str4;
                        } else {
                            str3 = str4.substring(indexOf + 1);
                        }
                        String checkNamespacePrefix = Verifier.checkNamespacePrefix(str2);
                        String str5 = " is illegal: ";
                        String str6 = "Prefix '";
                        String str7 = "' for variable ";
                        if (checkNamespacePrefix == null) {
                            String checkXMLName = Verifier.checkXMLName(str3);
                            if (checkXMLName == null) {
                                Namespace namespace3 = (Namespace) this.xnamespaces.get(str2);
                                if (namespace3 != null) {
                                    Map map2 = (Map) this.xvariables.get(namespace3.getURI());
                                    if (map2 == null) {
                                        map2 = new HashMap();
                                        this.xvariables.put(namespace3.getURI(), map2);
                                    }
                                    if (map2.put(str3, entry.getValue()) != null) {
                                        StringBuilder sb3 = new StringBuilder();
                                        sb3.append("Variable with name ");
                                        sb3.append((String) entry.getKey());
                                        sb3.append("' has already been defined.");
                                        throw new IllegalArgumentException(sb3.toString());
                                    }
                                } else {
                                    StringBuilder sb4 = new StringBuilder();
                                    sb4.append(str6);
                                    sb4.append(str2);
                                    sb4.append(str7);
                                    sb4.append(str4);
                                    sb4.append(" has not been assigned a Namespace.");
                                    throw new IllegalArgumentException(sb4.toString());
                                }
                            } else {
                                StringBuilder sb5 = new StringBuilder();
                                sb5.append("Variable name '");
                                sb5.append(str3);
                                sb5.append(str7);
                                sb5.append(str4);
                                sb5.append(str5);
                                sb5.append(checkXMLName);
                                throw new IllegalArgumentException(sb5.toString());
                            }
                        } else {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(str6);
                            sb6.append(str2);
                            sb6.append(str7);
                            sb6.append(str4);
                            sb6.append(str5);
                            sb6.append(checkNamespacePrefix);
                            throw new IllegalArgumentException(sb6.toString());
                        }
                    } else {
                        throw new NullPointerException("Variable with a null name");
                    }
                }
            }
            this.xquery = str;
            this.xfilter = filter;
        } else {
            throw new NullPointerException("Null filter");
        }
    }

    public XPathExpression<T> clone() {
        try {
            AbstractXPathCompiled abstractXPathCompiled = (AbstractXPathCompiled) super.clone();
            HashMap hashMap = new HashMap();
            for (Entry entry : this.xvariables.entrySet()) {
                HashMap hashMap2 = new HashMap();
                for (Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    hashMap2.put(entry2.getKey(), entry2.getValue());
                }
                hashMap.put(entry.getKey(), hashMap2);
            }
            abstractXPathCompiled.xvariables = hashMap;
            return abstractXPathCompiled;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Should never be getting a CloneNotSupportedException!", e);
        }
    }

    public final String getExpression() {
        return this.xquery;
    }

    public final Namespace getNamespace(String str) {
        Namespace namespace = (Namespace) this.xnamespaces.get(str);
        if (namespace != null) {
            return namespace;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Namespace with prefix '");
        sb.append(str);
        sb.append("' has not been declared.");
        throw new IllegalArgumentException(sb.toString());
    }

    public Namespace[] getNamespaces() {
        Namespace[] namespaceArr = (Namespace[]) this.xnamespaces.values().toArray(new Namespace[this.xnamespaces.size()]);
        Arrays.sort(namespaceArr, NSSORT);
        return namespaceArr;
    }

    public final Object getVariable(String str, Namespace namespace) {
        Map map = (Map) this.xvariables.get(namespace == null ? "" : namespace.getURI());
        String str2 = "' has not been declared.";
        String str3 = "' in namespace '";
        String str4 = "Variable with name '";
        if (map != null) {
            Object obj = map.get(str);
            if (obj != null) {
                return obj;
            }
            if (map.containsKey(str)) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str4);
            sb.append(str);
            sb.append(str3);
            sb.append(namespace.getURI());
            sb.append(str2);
            throw new IllegalArgumentException(sb.toString());
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str4);
        sb2.append(str);
        sb2.append(str3);
        sb2.append(namespace.getURI());
        sb2.append(str2);
        throw new IllegalArgumentException(sb2.toString());
    }

    public Object getVariable(String str) {
        if (str != null) {
            int indexOf = str.indexOf(58);
            if (indexOf >= 0) {
                return getVariable(str.substring(indexOf + 1), getNamespace(str.substring(0, indexOf)));
            }
            return getVariable(str, Namespace.NO_NAMESPACE);
        }
        throw new NullPointerException("Cannot get variable value for null qname");
    }

    public Object setVariable(String str, Namespace namespace, Object obj) {
        Object variable = getVariable(str, namespace);
        ((Map) this.xvariables.get(namespace.getURI())).put(str, obj);
        return variable;
    }

    public Object setVariable(String str, Object obj) {
        if (str != null) {
            int indexOf = str.indexOf(58);
            if (indexOf >= 0) {
                return setVariable(str.substring(indexOf + 1), getNamespace(str.substring(0, indexOf)), obj);
            }
            return setVariable(str, Namespace.NO_NAMESPACE, obj);
        }
        throw new NullPointerException("Cannot get variable value for null qname");
    }

    /* access modifiers changed from: protected */
    public Map<String, Object> getVariables() {
        HashMap hashMap = new HashMap();
        Namespace[] namespaces = getNamespaces();
        for (Entry entry : this.xvariables.entrySet()) {
            String prefixForURI = getPrefixForURI((String) entry.getKey(), namespaces);
            for (Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                if ("".equals(prefixForURI)) {
                    hashMap.put(entry2.getKey(), entry2.getValue());
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(prefixForURI);
                    sb.append(":");
                    sb.append((String) entry2.getKey());
                    hashMap.put(sb.toString(), entry2.getValue());
                }
            }
        }
        return hashMap;
    }

    public final Filter<T> getFilter() {
        return this.xfilter;
    }

    public List<T> evaluate(Object obj) {
        return this.xfilter.filter(evaluateRawAll(obj));
    }

    public T evaluateFirst(Object obj) {
        Object evaluateRawFirst = evaluateRawFirst(obj);
        if (evaluateRawFirst == null) {
            return null;
        }
        return this.xfilter.filter(evaluateRawFirst);
    }

    public XPathDiagnostic<T> diagnose(Object obj, boolean z) {
        return new XPathDiagnosticImpl(obj, this, z ? Collections.singletonList(evaluateRawFirst(obj)) : evaluateRawAll(obj), z);
    }

    public String toString() {
        int size = this.xnamespaces.size();
        int i = 0;
        for (Map size2 : this.xvariables.values()) {
            i += size2.size();
        }
        return String.format("[XPathExpression: %d namespaces and %d variables for query %s]", new Object[]{Integer.valueOf(size), Integer.valueOf(i), getExpression()});
    }
}
