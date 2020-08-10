package org.jdom2.filter;

import org.jdom2.Attribute;
import org.jdom2.CDATA;
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.EntityRef;
import org.jdom2.Namespace;
import org.jdom2.ProcessingInstruction;
import org.jdom2.Text;

public final class Filters {
    private static final Filter<Attribute> fattribute = new AttributeFilter();
    private static final Filter<Boolean> fboolean = new ClassFilter(Boolean.class);
    private static final Filter<CDATA> fcdata = new ClassFilter(CDATA.class);
    private static final Filter<Comment> fcomment = new ClassFilter(Comment.class);
    private static final Filter<Content> fcontent = new ClassFilter(Content.class);
    private static final Filter<DocType> fdoctype = new ClassFilter(DocType.class);
    private static final Filter<Document> fdocument = new ClassFilter(Document.class);
    private static final Filter<Double> fdouble = new ClassFilter(Double.class);
    private static final Filter<Element> felement = new ClassFilter(Element.class);
    private static final Filter<EntityRef> fentityref = new ClassFilter(EntityRef.class);
    private static final Filter<Object> fpassthrough = new PassThroughFilter();
    private static final Filter<ProcessingInstruction> fpi = new ClassFilter(ProcessingInstruction.class);
    private static final Filter<String> fstring = new ClassFilter(String.class);
    private static final Filter<Text> ftext = new ClassFilter(Text.class);
    private static final Filter<Text> ftextonly = new TextOnlyFilter();

    private Filters() {
    }

    public static final Filter<Content> content() {
        return fcontent;
    }

    public static final Filter<Attribute> attribute() {
        return fattribute;
    }

    public static final Filter<Attribute> attribute(String str) {
        return new AttributeFilter(str);
    }

    public static final Filter<Attribute> attribute(String str, Namespace namespace) {
        return new AttributeFilter(str, namespace);
    }

    public static final Filter<Attribute> attribute(Namespace namespace) {
        return new AttributeFilter(namespace);
    }

    public static final Filter<Comment> comment() {
        return fcomment;
    }

    public static final Filter<CDATA> cdata() {
        return fcdata;
    }

    public static final Filter<DocType> doctype() {
        return fdoctype;
    }

    public static final Filter<EntityRef> entityref() {
        return fentityref;
    }

    public static final Filter<Element> element() {
        return felement;
    }

    public static final Filter<Document> document() {
        return fdocument;
    }

    public static final Filter<Element> element(String str) {
        return new ElementFilter(str, Namespace.NO_NAMESPACE);
    }

    public static final Filter<Element> element(String str, Namespace namespace) {
        return new ElementFilter(str, namespace);
    }

    public static final Filter<Element> element(Namespace namespace) {
        return new ElementFilter(null, namespace);
    }

    public static final Filter<ProcessingInstruction> processinginstruction() {
        return fpi;
    }

    public static final Filter<Text> text() {
        return ftext;
    }

    public static final Filter<Text> textOnly() {
        return ftextonly;
    }

    public static final Filter<Boolean> fboolean() {
        return fboolean;
    }

    public static final Filter<String> fstring() {
        return fstring;
    }

    public static final Filter<Double> fdouble() {
        return fdouble;
    }

    public static final <F> Filter<F> fclass(Class<F> cls) {
        return new ClassFilter(cls);
    }

    public static final Filter<Object> fpassthrough() {
        return fpassthrough;
    }
}
