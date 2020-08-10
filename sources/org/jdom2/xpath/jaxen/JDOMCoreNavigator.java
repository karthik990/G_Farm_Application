package org.jdom2.xpath.jaxen;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import org.jaxen.BaseXPath;
import org.jaxen.DefaultNavigator;
import org.jaxen.FunctionCallException;
import org.jaxen.JaxenConstants;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.XPath;
import org.jaxen.saxpath.SAXPathException;
import org.jaxen.util.SingleObjectIterator;
import org.jdom2.Attribute;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.Parent;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;
import org.jdom2.input.SAXBuilder;

class JDOMCoreNavigator extends DefaultNavigator {
    private static final long serialVersionUID = 200;
    private transient IdentityHashMap<Element, NamespaceContainer[]> emtnsmap = new IdentityHashMap<>();

    JDOMCoreNavigator() {
    }

    /* access modifiers changed from: 0000 */
    public void reset() {
        this.emtnsmap.clear();
    }

    public final XPath parseXPath(String str) throws SAXPathException {
        return new BaseXPath(str, this);
    }

    public final Object getDocument(String str) throws FunctionCallException {
        try {
            return new SAXBuilder().build(str);
        } catch (JDOMException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to parse ");
            sb.append(str);
            throw new FunctionCallException(sb.toString(), e);
        } catch (IOException e2) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to access ");
            sb2.append(str);
            throw new FunctionCallException(sb2.toString(), e2);
        }
    }

    public final boolean isText(Object obj) {
        return obj instanceof Text;
    }

    public final boolean isProcessingInstruction(Object obj) {
        return obj instanceof ProcessingInstruction;
    }

    public final boolean isNamespace(Object obj) {
        return obj instanceof NamespaceContainer;
    }

    public final boolean isElement(Object obj) {
        return obj instanceof Element;
    }

    public final boolean isDocument(Object obj) {
        return obj instanceof Document;
    }

    public final boolean isComment(Object obj) {
        return obj instanceof Comment;
    }

    public final boolean isAttribute(Object obj) {
        return obj instanceof Attribute;
    }

    public final String getTextStringValue(Object obj) {
        return ((Text) obj).getText();
    }

    public final String getNamespaceStringValue(Object obj) {
        return ((NamespaceContainer) obj).getNamespace().getURI();
    }

    public final String getNamespacePrefix(Object obj) {
        return ((NamespaceContainer) obj).getNamespace().getPrefix();
    }

    private final void recurseElementText(Element element, StringBuilder sb) {
        for (Content content : element.getContent()) {
            if (content instanceof Element) {
                recurseElementText((Element) content, sb);
            } else if (content instanceof Text) {
                sb.append(((Text) content).getText());
            }
        }
    }

    public final String getElementStringValue(Object obj) {
        StringBuilder sb = new StringBuilder();
        recurseElementText((Element) obj, sb);
        return sb.toString();
    }

    public final String getElementQName(Object obj) {
        Element element = (Element) obj;
        if (element.getNamespace().getPrefix().length() == 0) {
            return element.getName();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(element.getNamespacePrefix());
        sb.append(":");
        sb.append(element.getName());
        return sb.toString();
    }

    public final String getElementNamespaceUri(Object obj) {
        return ((Element) obj).getNamespaceURI();
    }

    public final String getElementName(Object obj) {
        return ((Element) obj).getName();
    }

    public final String getCommentStringValue(Object obj) {
        return ((Comment) obj).getValue();
    }

    public final String getAttributeStringValue(Object obj) {
        return ((Attribute) obj).getValue();
    }

    public final String getAttributeQName(Object obj) {
        Attribute attribute = (Attribute) obj;
        if (attribute.getNamespacePrefix().length() == 0) {
            return attribute.getName();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(attribute.getNamespacePrefix());
        sb.append(":");
        sb.append(attribute.getName());
        return sb.toString();
    }

    public final String getAttributeNamespaceUri(Object obj) {
        return ((Attribute) obj).getNamespaceURI();
    }

    public final String getAttributeName(Object obj) {
        return ((Attribute) obj).getName();
    }

    public final String getProcessingInstructionTarget(Object obj) {
        return ((ProcessingInstruction) obj).getTarget();
    }

    public final String getProcessingInstructionData(Object obj) {
        return ((ProcessingInstruction) obj).getData();
    }

    public final Object getDocumentNode(Object obj) {
        if (obj instanceof Document) {
            return obj;
        }
        if (obj instanceof NamespaceContainer) {
            return ((NamespaceContainer) obj).getParentElement().getDocument();
        }
        if (obj instanceof Attribute) {
            return ((Attribute) obj).getDocument();
        }
        return ((Content) obj).getDocument();
    }

    public final Object getParentNode(Object obj) throws UnsupportedAxisException {
        if (obj instanceof Document) {
            return null;
        }
        if (obj instanceof NamespaceContainer) {
            return ((NamespaceContainer) obj).getParentElement();
        }
        if (obj instanceof Content) {
            return ((Content) obj).getParent();
        }
        if (obj instanceof Attribute) {
            return ((Attribute) obj).getParent();
        }
        return null;
    }

    public final Iterator<?> getAttributeAxisIterator(Object obj) throws UnsupportedAxisException {
        if (isElement(obj)) {
            Element element = (Element) obj;
            if (element.hasAttributes()) {
                return element.getAttributes().iterator();
            }
        }
        return JaxenConstants.EMPTY_ITERATOR;
    }

    public final Iterator<?> getChildAxisIterator(Object obj) throws UnsupportedAxisException {
        if (obj instanceof Parent) {
            return ((Parent) obj).getContent().iterator();
        }
        return JaxenConstants.EMPTY_ITERATOR;
    }

    public final Iterator<?> getNamespaceAxisIterator(Object obj) throws UnsupportedAxisException {
        if (!isElement(obj)) {
            return JaxenConstants.EMPTY_ITERATOR;
        }
        NamespaceContainer[] namespaceContainerArr = (NamespaceContainer[]) this.emtnsmap.get(obj);
        if (namespaceContainerArr == null) {
            Element element = (Element) obj;
            List<Namespace> namespacesInScope = element.getNamespacesInScope();
            NamespaceContainer[] namespaceContainerArr2 = new NamespaceContainer[namespacesInScope.size()];
            int i = 0;
            for (Namespace namespaceContainer : namespacesInScope) {
                int i2 = i + 1;
                namespaceContainerArr2[i] = new NamespaceContainer(namespaceContainer, element);
                i = i2;
            }
            this.emtnsmap.put(element, namespaceContainerArr2);
            namespaceContainerArr = namespaceContainerArr2;
        }
        return Arrays.asList(namespaceContainerArr).iterator();
    }

    public final Iterator<?> getParentAxisIterator(Object obj) throws UnsupportedAxisException {
        Object obj2 = obj instanceof Content ? ((Content) obj).getParent() : obj instanceof NamespaceContainer ? ((NamespaceContainer) obj).getParentElement() : obj instanceof Attribute ? ((Attribute) obj).getParent() : null;
        if (obj2 != null) {
            return new SingleObjectIterator(obj2);
        }
        return JaxenConstants.EMPTY_ITERATOR;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.emtnsmap = new IdentityHashMap<>();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }
}
