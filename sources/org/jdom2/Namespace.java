package org.jdom2;

import java.io.InvalidObjectException;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Namespace implements Serializable {
    public static final Namespace NO_NAMESPACE;
    private static final Namespace XMLNS_NAMESPACE = new Namespace(JDOMConstants.NS_PREFIX_XMLNS, JDOMConstants.NS_URI_XMLNS);
    public static final Namespace XML_NAMESPACE = new Namespace("xml", JDOMConstants.NS_URI_XML);
    private static final ConcurrentMap<String, ConcurrentMap<String, Namespace>> namespacemap = new ConcurrentHashMap(512, 0.75f, 64);
    private static final long serialVersionUID = 200;
    private final transient String prefix;
    private final transient String uri;

    private static final class NamespaceSerializationProxy implements Serializable {
        private static final long serialVersionUID = 200;
        private final String pprefix;
        private final String puri;

        public NamespaceSerializationProxy(String str, String str2) {
            this.pprefix = str;
            this.puri = str2;
        }

        private Object readResolve() {
            return Namespace.getNamespace(this.pprefix, this.puri);
        }
    }

    static {
        String str = "";
        NO_NAMESPACE = new Namespace(str, str);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put(NO_NAMESPACE.getPrefix(), NO_NAMESPACE);
        namespacemap.put(NO_NAMESPACE.getURI(), concurrentHashMap);
        ConcurrentHashMap concurrentHashMap2 = new ConcurrentHashMap();
        concurrentHashMap2.put(XML_NAMESPACE.getPrefix(), XML_NAMESPACE);
        namespacemap.put(XML_NAMESPACE.getURI(), concurrentHashMap2);
        ConcurrentHashMap concurrentHashMap3 = new ConcurrentHashMap();
        concurrentHashMap3.put(XMLNS_NAMESPACE.getPrefix(), XMLNS_NAMESPACE);
        namespacemap.put(XMLNS_NAMESPACE.getURI(), concurrentHashMap3);
    }

    public static Namespace getNamespace(String str, String str2) {
        String str3 = "Namespace URIs must be non-null and non-empty Strings";
        String str4 = "namespace";
        String str5 = "";
        if (str2 != null) {
            ConcurrentMap concurrentMap = (ConcurrentMap) namespacemap.get(str2);
            String str6 = "Namespace URI";
            if (concurrentMap == null) {
                String checkNamespaceURI = Verifier.checkNamespaceURI(str2);
                if (checkNamespaceURI == null) {
                    concurrentMap = new ConcurrentHashMap();
                    ConcurrentMap concurrentMap2 = (ConcurrentMap) namespacemap.putIfAbsent(str2, concurrentMap);
                    if (concurrentMap2 != null) {
                        concurrentMap = concurrentMap2;
                    }
                } else {
                    throw new IllegalNameException(str2, str6, checkNamespaceURI);
                }
            }
            Namespace namespace = (Namespace) concurrentMap.get(str == null ? str5 : str);
            if (namespace != null) {
                return namespace;
            }
            if (str5.equals(str2)) {
                throw new IllegalNameException(str5, str4, str3);
            } else if (JDOMConstants.NS_URI_XML.equals(str2)) {
                throw new IllegalNameException(str2, str6, "The http://www.w3.org/XML/1998/namespace must be bound to only the 'xml' prefix.");
            } else if (!JDOMConstants.NS_URI_XMLNS.equals(str2)) {
                if (str == null) {
                    str = str5;
                }
                String str7 = "Namespace prefix";
                if ("xml".equals(str)) {
                    throw new IllegalNameException(str2, str7, "The prefix xml (any case) can only be bound to only the 'http://www.w3.org/XML/1998/namespace' uri.");
                } else if (!JDOMConstants.NS_PREFIX_XMLNS.equals(str)) {
                    String checkNamespacePrefix = Verifier.checkNamespacePrefix(str);
                    if (checkNamespacePrefix == null) {
                        Namespace namespace2 = new Namespace(str, str2);
                        Namespace namespace3 = (Namespace) concurrentMap.putIfAbsent(str, namespace2);
                        if (namespace3 == null) {
                            namespace3 = namespace2;
                        }
                        return namespace3;
                    }
                    throw new IllegalNameException(str, str7, checkNamespacePrefix);
                } else {
                    throw new IllegalNameException(str2, str7, "The prefix xmlns (any case) can only be bound to only the 'http://www.w3.org/2000/xmlns/' uri.");
                }
            } else {
                throw new IllegalNameException(str2, str6, "The http://www.w3.org/2000/xmlns/ must be bound to only the 'xmlns' prefix.");
            }
        } else if (str == null || str5.equals(str)) {
            return NO_NAMESPACE;
        } else {
            throw new IllegalNameException(str5, str4, str3);
        }
    }

    public static Namespace getNamespace(String str) {
        return getNamespace("", str);
    }

    private Namespace(String str, String str2) {
        this.prefix = str;
        this.uri = str2;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getURI() {
        return this.uri;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Namespace) {
            return this.uri.equals(((Namespace) obj).uri);
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Namespace: prefix \"");
        sb.append(this.prefix);
        sb.append("\" is mapped to URI \"");
        sb.append(this.uri);
        sb.append("\"]");
        return sb.toString();
    }

    public int hashCode() {
        return this.uri.hashCode();
    }

    private Object writeReplace() {
        return new NamespaceSerializationProxy(this.prefix, this.uri);
    }

    private Object readResolve() throws InvalidObjectException {
        throw new InvalidObjectException("Namespace is serialized through a proxy");
    }
}
