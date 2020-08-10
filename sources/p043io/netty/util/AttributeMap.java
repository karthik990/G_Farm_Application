package p043io.netty.util;

/* renamed from: io.netty.util.AttributeMap */
public interface AttributeMap {
    <T> Attribute<T> attr(AttributeKey<T> attributeKey);

    <T> boolean hasAttr(AttributeKey<T> attributeKey);
}
