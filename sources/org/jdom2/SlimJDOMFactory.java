package org.jdom2;

import java.util.Map;

public class SlimJDOMFactory extends DefaultJDOMFactory {
    private StringBin cache;
    private final boolean cachetext;

    public SlimJDOMFactory() {
        this(true);
    }

    public SlimJDOMFactory(boolean z) {
        this.cache = new StringBin();
        this.cachetext = z;
    }

    public void clearCache() {
        this.cache = new StringBin();
    }

    public Attribute attribute(String str, String str2, Namespace namespace) {
        String reuse = this.cache.reuse(str);
        if (this.cachetext) {
            str2 = this.cache.reuse(str2);
        }
        return super.attribute(reuse, str2, namespace);
    }

    @Deprecated
    public Attribute attribute(String str, String str2, int i, Namespace namespace) {
        String reuse = this.cache.reuse(str);
        if (this.cachetext) {
            str2 = this.cache.reuse(str2);
        }
        return super.attribute(reuse, str2, i, namespace);
    }

    public Attribute attribute(String str, String str2, AttributeType attributeType, Namespace namespace) {
        String reuse = this.cache.reuse(str);
        if (this.cachetext) {
            str2 = this.cache.reuse(str2);
        }
        return super.attribute(reuse, str2, attributeType, namespace);
    }

    public Attribute attribute(String str, String str2) {
        String reuse = this.cache.reuse(str);
        if (this.cachetext) {
            str2 = this.cache.reuse(str2);
        }
        return super.attribute(reuse, str2);
    }

    @Deprecated
    public Attribute attribute(String str, String str2, int i) {
        String reuse = this.cache.reuse(str);
        if (this.cachetext) {
            str2 = this.cache.reuse(str2);
        }
        return super.attribute(reuse, str2, i);
    }

    public Attribute attribute(String str, String str2, AttributeType attributeType) {
        String reuse = this.cache.reuse(str);
        if (this.cachetext) {
            str2 = this.cache.reuse(str2);
        }
        return super.attribute(reuse, str2, attributeType);
    }

    public CDATA cdata(int i, int i2, String str) {
        if (this.cachetext) {
            str = this.cache.reuse(str);
        }
        return super.cdata(i, i2, str);
    }

    public Text text(int i, int i2, String str) {
        if (this.cachetext) {
            str = this.cache.reuse(str);
        }
        return super.text(i, i2, str);
    }

    public Comment comment(int i, int i2, String str) {
        if (this.cachetext) {
            str = this.cache.reuse(str);
        }
        return super.comment(i, i2, str);
    }

    public DocType docType(int i, int i2, String str, String str2, String str3) {
        return super.docType(i, i2, this.cache.reuse(str), str2, str3);
    }

    public DocType docType(int i, int i2, String str, String str2) {
        return super.docType(i, i2, this.cache.reuse(str), str2);
    }

    public DocType docType(int i, int i2, String str) {
        return super.docType(i, i2, this.cache.reuse(str));
    }

    public Element element(int i, int i2, String str, Namespace namespace) {
        return super.element(i, i2, this.cache.reuse(str), namespace);
    }

    public Element element(int i, int i2, String str) {
        return super.element(i, i2, this.cache.reuse(str));
    }

    public Element element(int i, int i2, String str, String str2) {
        return super.element(i, i2, this.cache.reuse(str), str2);
    }

    public Element element(int i, int i2, String str, String str2, String str3) {
        return super.element(i, i2, this.cache.reuse(str), str2, str3);
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str, Map<String, String> map) {
        return super.processingInstruction(i, i2, this.cache.reuse(str), map);
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str, String str2) {
        return super.processingInstruction(i, i2, this.cache.reuse(str), str2);
    }

    public ProcessingInstruction processingInstruction(int i, int i2, String str) {
        return super.processingInstruction(i, i2, this.cache.reuse(str));
    }

    public EntityRef entityRef(int i, int i2, String str) {
        return super.entityRef(i, i2, this.cache.reuse(str));
    }

    public EntityRef entityRef(int i, int i2, String str, String str2, String str3) {
        return super.entityRef(i, i2, this.cache.reuse(str), str2, str3);
    }

    public EntityRef entityRef(int i, int i2, String str, String str2) {
        return super.entityRef(i, i2, this.cache.reuse(str), str2);
    }
}
