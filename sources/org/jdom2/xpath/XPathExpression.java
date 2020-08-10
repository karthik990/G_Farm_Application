package org.jdom2.xpath;

import java.util.List;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;

public interface XPathExpression<T> extends Cloneable {
    XPathExpression<T> clone();

    XPathDiagnostic<T> diagnose(Object obj, boolean z);

    List<T> evaluate(Object obj);

    T evaluateFirst(Object obj);

    String getExpression();

    Filter<T> getFilter();

    Namespace getNamespace(String str);

    Namespace[] getNamespaces();

    Object getVariable(String str);

    Object getVariable(String str, Namespace namespace);

    Object setVariable(String str, Object obj);

    Object setVariable(String str, Namespace namespace, Object obj);
}
