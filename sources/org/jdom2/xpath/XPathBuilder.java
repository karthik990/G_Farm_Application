package org.jdom2.xpath;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;

public class XPathBuilder<T> {
    private final String expression;
    private final Filter<T> filter;
    private Map<String, Namespace> namespaces;
    private Map<String, Object> variables;

    public XPathBuilder(String str, Filter<T> filter2) {
        if (str == null) {
            throw new NullPointerException("Null expression");
        } else if (filter2 != null) {
            this.filter = filter2;
            this.expression = str;
        } else {
            throw new NullPointerException("Null filter");
        }
    }

    public boolean setVariable(String str, Object obj) {
        if (str != null) {
            if (this.variables == null) {
                this.variables = new HashMap();
            }
            return this.variables.put(str, obj) == null;
        }
        throw new NullPointerException("Null variable name");
    }

    public boolean setNamespace(String str, String str2) {
        if (str == null) {
            throw new NullPointerException("Null prefix");
        } else if (str2 != null) {
            return setNamespace(Namespace.getNamespace(str, str2));
        } else {
            throw new NullPointerException("Null URI");
        }
    }

    public boolean setNamespace(Namespace namespace) {
        if (namespace != null) {
            boolean z = false;
            if (!"".equals(namespace.getPrefix())) {
                if (this.namespaces == null) {
                    this.namespaces = new HashMap();
                }
                if (this.namespaces.put(namespace.getPrefix(), namespace) == null) {
                    z = true;
                }
                return z;
            } else if (Namespace.NO_NAMESPACE == namespace) {
                return false;
            } else {
                throw new IllegalArgumentException("Cannot set a Namespace URI in XPath for the \"\" prefix.");
            }
        } else {
            throw new NullPointerException("Null Namespace");
        }
    }

    public boolean setNamespaces(Collection<Namespace> collection) {
        if (collection != null) {
            boolean z = false;
            for (Namespace namespace : collection) {
                if (setNamespace(namespace)) {
                    z = true;
                }
            }
            return z;
        }
        throw new NullPointerException("Null namespaces Collection");
    }

    public Object getVariable(String str) {
        if (str != null) {
            Map<String, Object> map = this.variables;
            if (map == null) {
                return null;
            }
            return map.get(str);
        }
        throw new NullPointerException("Null qname");
    }

    public Namespace getNamespace(String str) {
        if (str == null) {
            throw new NullPointerException("Null prefix");
        } else if ("".equals(str)) {
            return Namespace.NO_NAMESPACE;
        } else {
            Map<String, Namespace> map = this.namespaces;
            if (map == null) {
                return null;
            }
            return (Namespace) map.get(str);
        }
    }

    public Filter<T> getFilter() {
        return this.filter;
    }

    public String getExpression() {
        return this.expression;
    }

    public XPathExpression<T> compileWith(XPathFactory xPathFactory) {
        Map<String, Namespace> map = this.namespaces;
        if (map == null) {
            return xPathFactory.compile(this.expression, this.filter, this.variables, new Namespace[0]);
        }
        return xPathFactory.compile(this.expression, this.filter, this.variables, (Namespace[]) map.values().toArray(new Namespace[this.namespaces.size()]));
    }
}
