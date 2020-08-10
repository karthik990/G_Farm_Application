package org.jdom2;

import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class Attribute extends CloneBase implements NamespaceAware, Serializable, Cloneable {
    public static final AttributeType CDATA_TYPE = AttributeType.CDATA;
    public static final AttributeType ENTITIES_TYPE = AttributeType.ENTITIES;
    public static final AttributeType ENTITY_TYPE = AttributeType.ENTITY;
    public static final AttributeType ENUMERATED_TYPE = AttributeType.ENUMERATION;
    public static final AttributeType IDREFS_TYPE = AttributeType.IDREFS;
    public static final AttributeType IDREF_TYPE = AttributeType.IDREF;
    public static final AttributeType ID_TYPE = AttributeType.ID;
    public static final AttributeType NMTOKENS_TYPE = AttributeType.NMTOKENS;
    public static final AttributeType NMTOKEN_TYPE = AttributeType.NMTOKEN;
    public static final AttributeType NOTATION_TYPE = AttributeType.NOTATION;
    public static final AttributeType UNDECLARED_TYPE = AttributeType.UNDECLARED;
    private static final long serialVersionUID = 200;
    protected String name;
    protected Namespace namespace;
    protected transient Element parent;
    protected boolean specified;
    protected AttributeType type;
    protected String value;

    protected Attribute() {
        this.type = AttributeType.UNDECLARED;
        this.specified = true;
    }

    public Attribute(String str, String str2, Namespace namespace2) {
        this(str, str2, AttributeType.UNDECLARED, namespace2);
    }

    @Deprecated
    public Attribute(String str, String str2, int i, Namespace namespace2) {
        this(str, str2, AttributeType.byIndex(i), namespace2);
    }

    public Attribute(String str, String str2, AttributeType attributeType, Namespace namespace2) {
        this.type = AttributeType.UNDECLARED;
        this.specified = true;
        setName(str);
        setValue(str2);
        setAttributeType(attributeType);
        setNamespace(namespace2);
    }

    public Attribute(String str, String str2) {
        this(str, str2, AttributeType.UNDECLARED, Namespace.NO_NAMESPACE);
    }

    public Attribute(String str, String str2, AttributeType attributeType) {
        this(str, str2, attributeType, Namespace.NO_NAMESPACE);
    }

    @Deprecated
    public Attribute(String str, String str2, int i) {
        this(str, str2, i, Namespace.NO_NAMESPACE);
    }

    public Element getParent() {
        return this.parent;
    }

    public Document getDocument() {
        Element element = this.parent;
        if (element == null) {
            return null;
        }
        return element.getDocument();
    }

    public String getName() {
        return this.name;
    }

    public Attribute setName(String str) {
        if (str != null) {
            String checkAttributeName = Verifier.checkAttributeName(str);
            if (checkAttributeName == null) {
                this.name = str;
                this.specified = true;
                return this;
            }
            throw new IllegalNameException(str, "attribute", checkAttributeName);
        }
        throw new NullPointerException("Can not set a null name for an Attribute.");
    }

    public String getQualifiedName() {
        String prefix = this.namespace.getPrefix();
        if ("".equals(prefix)) {
            return getName();
        }
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(':');
        sb.append(getName());
        return sb.toString();
    }

    public String getNamespacePrefix() {
        return this.namespace.getPrefix();
    }

    public String getNamespaceURI() {
        return this.namespace.getURI();
    }

    public Namespace getNamespace() {
        return this.namespace;
    }

    public Attribute setNamespace(Namespace namespace2) {
        if (namespace2 == null) {
            namespace2 = Namespace.NO_NAMESPACE;
        }
        if (namespace2 != Namespace.NO_NAMESPACE) {
            String str = "";
            if (str.equals(namespace2.getPrefix())) {
                throw new IllegalNameException(str, "attribute namespace", "An attribute namespace without a prefix can only be the NO_NAMESPACE namespace");
            }
        }
        this.namespace = namespace2;
        this.specified = true;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public Attribute setValue(String str) {
        if (str != null) {
            String checkCharacterData = Verifier.checkCharacterData(str);
            if (checkCharacterData == null) {
                this.value = str;
                this.specified = true;
                return this;
            }
            throw new IllegalDataException(str, "attribute", checkCharacterData);
        }
        throw new NullPointerException("Can not set a null value for an Attribute");
    }

    public AttributeType getAttributeType() {
        return this.type;
    }

    public Attribute setAttributeType(AttributeType attributeType) {
        if (attributeType == null) {
            attributeType = AttributeType.UNDECLARED;
        }
        this.type = attributeType;
        this.specified = true;
        return this;
    }

    @Deprecated
    public Attribute setAttributeType(int i) {
        setAttributeType(AttributeType.byIndex(i));
        return this;
    }

    public boolean isSpecified() {
        return this.specified;
    }

    public void setSpecified(boolean z) {
        this.specified = z;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Attribute: ");
        sb.append(getQualifiedName());
        sb.append("=\"");
        sb.append(this.value);
        sb.append("\"");
        sb.append("]");
        return sb.toString();
    }

    public Attribute clone() {
        Attribute attribute = (Attribute) super.clone();
        attribute.parent = null;
        return attribute;
    }

    public Attribute detach() {
        Element element = this.parent;
        if (element != null) {
            element.removeAttribute(this);
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public Attribute setParent(Element element) {
        this.parent = element;
        return this;
    }

    public int getIntValue() throws DataConversionException {
        try {
            return Integer.parseInt(this.value.trim());
        } catch (NumberFormatException unused) {
            throw new DataConversionException(this.name, "int");
        }
    }

    public long getLongValue() throws DataConversionException {
        try {
            return Long.parseLong(this.value.trim());
        } catch (NumberFormatException unused) {
            throw new DataConversionException(this.name, "long");
        }
    }

    public float getFloatValue() throws DataConversionException {
        try {
            return Float.valueOf(this.value.trim()).floatValue();
        } catch (NumberFormatException unused) {
            throw new DataConversionException(this.name, "float");
        }
    }

    public double getDoubleValue() throws DataConversionException {
        try {
            return Double.valueOf(this.value.trim()).doubleValue();
        } catch (NumberFormatException unused) {
            String trim = this.value.trim();
            if ("INF".equals(trim)) {
                return Double.POSITIVE_INFINITY;
            }
            if ("-INF".equals(trim)) {
                return Double.NEGATIVE_INFINITY;
            }
            throw new DataConversionException(this.name, "double");
        }
    }

    public boolean getBooleanValue() throws DataConversionException {
        String trim = this.value.trim();
        if (trim.equalsIgnoreCase("true") || trim.equalsIgnoreCase("on") || trim.equalsIgnoreCase(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE) || trim.equalsIgnoreCase("yes")) {
            return true;
        }
        if (trim.equalsIgnoreCase("false") || trim.equalsIgnoreCase("off") || trim.equalsIgnoreCase("0") || trim.equalsIgnoreCase("no")) {
            return false;
        }
        throw new DataConversionException(this.name, "boolean");
    }

    public List<Namespace> getNamespacesInScope() {
        if (getParent() != null) {
            return orderFirst(getNamespace(), getParent().getNamespacesInScope());
        }
        ArrayList arrayList = new ArrayList(3);
        arrayList.add(getNamespace());
        arrayList.add(Namespace.XML_NAMESPACE);
        return Collections.unmodifiableList(arrayList);
    }

    public List<Namespace> getNamespacesIntroduced() {
        if (getParent() == null) {
            return Collections.singletonList(getNamespace());
        }
        return Collections.emptyList();
    }

    public List<Namespace> getNamespacesInherited() {
        if (getParent() == null) {
            return Collections.singletonList(Namespace.XML_NAMESPACE);
        }
        return orderFirst(getNamespace(), getParent().getNamespacesInScope());
    }

    private static final List<Namespace> orderFirst(Namespace namespace2, List<Namespace> list) {
        if (list.get(0) == namespace2) {
            return list;
        }
        TreeMap treeMap = new TreeMap();
        for (Namespace namespace3 : list) {
            if (namespace3 != namespace2) {
                treeMap.put(namespace3.getPrefix(), namespace3);
            }
        }
        ArrayList arrayList = new ArrayList(treeMap.size() + 1);
        arrayList.add(namespace2);
        arrayList.addAll(treeMap.values());
        return Collections.unmodifiableList(arrayList);
    }
}
