package org.jdom2.xpath;

import java.util.List;

public interface XPathDiagnostic<T> {
    Object getContext();

    List<Object> getFilteredResults();

    List<Object> getRawResults();

    List<T> getResult();

    XPathExpression<T> getXPathExpression();

    boolean isFirstOnly();
}
