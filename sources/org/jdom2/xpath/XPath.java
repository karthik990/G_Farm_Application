package org.jdom2.xpath;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;

@Deprecated
public abstract class XPath implements Serializable {
    private static final String DEFAULT_XPATH_CLASS = "org.jdom2.xpath.jaxen.JDOMXPath";
    public static final String JDOM_OBJECT_MODEL_URI = "http://jdom.org/jaxp/xpath/jdom";
    private static final String XPATH_CLASS_PROPERTY = "org.jdom2.xpath.class";
    private static Constructor<? extends XPath> constructor = null;
    private static final long serialVersionUID = 200;

    private static final class XPathString implements Serializable {
        private static final long serialVersionUID = 200;
        private String xPath = null;

        public XPathString(String str) {
            this.xPath = str;
        }

        private Object readResolve() throws ObjectStreamException {
            try {
                return XPath.newInstance(this.xPath);
            } catch (JDOMException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Can't create XPath object for expression \"");
                sb.append(this.xPath);
                sb.append("\": ");
                sb.append(e.toString());
                throw new InvalidObjectException(sb.toString());
            }
        }
    }

    public abstract void addNamespace(Namespace namespace);

    public abstract String getXPath();

    public abstract Number numberValueOf(Object obj) throws JDOMException;

    public abstract List<?> selectNodes(Object obj) throws JDOMException;

    public abstract Object selectSingleNode(Object obj) throws JDOMException;

    public abstract void setVariable(String str, Object obj);

    public abstract String valueOf(Object obj) throws JDOMException;

    /* JADX WARNING: Can't wrap try/catch for region: R(6:4|5|6|7|8|(1:10)(2:11|12)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x000c */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0018 A[Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }] */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x001c A[Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.jdom2.xpath.XPath newInstance(java.lang.String r3) throws org.jdom2.JDOMException {
        /*
            java.lang.String r0 = "org.jdom2.xpath.jaxen.JDOMXPath"
            java.lang.reflect.Constructor<? extends org.jdom2.xpath.XPath> r1 = constructor     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            if (r1 != 0) goto L_0x0038
            java.lang.String r1 = "org.jdom2.xpath.class"
            java.lang.String r0 = org.jdom2.internal.SystemProperty.get(r1, r0)     // Catch:{ SecurityException -> 0x000c }
        L_0x000c:
            java.lang.Class r1 = java.lang.Class.forName(r0)     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            java.lang.Class<org.jdom2.xpath.XPath> r2 = org.jdom2.xpath.XPath.class
            boolean r2 = r2.isAssignableFrom(r1)     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            if (r2 == 0) goto L_0x001c
            setXPathClass(r1)     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            goto L_0x0038
        L_0x001c:
            org.jdom2.JDOMException r3 = new org.jdom2.JDOMException     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            r1.<init>()     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            java.lang.String r2 = "Unable to create a JDOMXPath from class '"
            r1.append(r2)     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            r1.append(r0)     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            java.lang.String r0 = "'."
            r1.append(r0)     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            java.lang.String r0 = r1.toString()     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            r3.<init>(r0)     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            throw r3     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
        L_0x0038:
            java.lang.reflect.Constructor<? extends org.jdom2.xpath.XPath> r0 = constructor     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            r1 = 1
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            r2 = 0
            r1[r2] = r3     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            java.lang.Object r3 = r0.newInstance(r1)     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            org.jdom2.xpath.XPath r3 = (org.jdom2.xpath.XPath) r3     // Catch:{ JDOMException -> 0x0069, InvocationTargetException -> 0x0052, Exception -> 0x0047 }
            return r3
        L_0x0047:
            r3 = move-exception
            org.jdom2.JDOMException r0 = new org.jdom2.JDOMException
            java.lang.String r1 = r3.toString()
            r0.<init>(r1, r3)
            throw r0
        L_0x0052:
            r3 = move-exception
            java.lang.Throwable r3 = r3.getTargetException()
            boolean r0 = r3 instanceof org.jdom2.JDOMException
            if (r0 == 0) goto L_0x005e
            org.jdom2.JDOMException r3 = (org.jdom2.JDOMException) r3
            goto L_0x0068
        L_0x005e:
            org.jdom2.JDOMException r0 = new org.jdom2.JDOMException
            java.lang.String r1 = r3.toString()
            r0.<init>(r1, r3)
            r3 = r0
        L_0x0068:
            throw r3
        L_0x0069:
            r3 = move-exception
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jdom2.xpath.XPath.newInstance(java.lang.String):org.jdom2.xpath.XPath");
    }

    public static void setXPathClass(Class<? extends XPath> cls) throws JDOMException {
        if (cls != null) {
            try {
                if (!XPath.class.isAssignableFrom(cls) || Modifier.isAbstract(cls.getModifiers())) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cls.getName());
                    sb.append(" is not a concrete JDOM XPath implementation");
                    throw new JDOMException(sb.toString());
                }
                constructor = cls.getConstructor(new Class[]{String.class});
            } catch (JDOMException e) {
                throw e;
            } catch (Exception e2) {
                throw new JDOMException(e2.toString(), e2);
            }
        } else {
            throw new IllegalArgumentException("aClass");
        }
    }

    public void addNamespace(String str, String str2) {
        addNamespace(Namespace.getNamespace(str, str2));
    }

    public static List<?> selectNodes(Object obj, String str) throws JDOMException {
        return newInstance(str).selectNodes(obj);
    }

    public static Object selectSingleNode(Object obj, String str) throws JDOMException {
        return newInstance(str).selectSingleNode(obj);
    }

    /* access modifiers changed from: protected */
    public final Object writeReplace() throws ObjectStreamException {
        return new XPathString(getXPath());
    }
}
