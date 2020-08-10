package org.jdom2.xpath.jaxen;

import java.util.Map;
import org.jdom2.Namespace;
import org.jdom2.filter.Filter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

public class JaxenXPathFactory extends XPathFactory {
    public <T> XPathExpression<T> compile(String str, Filter<T> filter, Map<String, Object> map, Namespace... namespaceArr) {
        return new JaxenCompiled(str, filter, map, namespaceArr);
    }
}
