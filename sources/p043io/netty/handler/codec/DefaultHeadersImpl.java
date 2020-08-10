package p043io.netty.handler.codec;

import p043io.netty.handler.codec.DefaultHeaders.NameValidator;
import p043io.netty.util.HashingStrategy;

/* renamed from: io.netty.handler.codec.DefaultHeadersImpl */
public final class DefaultHeadersImpl<K, V> extends DefaultHeaders<K, V, DefaultHeadersImpl<K, V>> {
    public DefaultHeadersImpl(HashingStrategy<K> hashingStrategy, ValueConverter<V> valueConverter, NameValidator<K> nameValidator) {
        super(hashingStrategy, valueConverter, nameValidator);
    }
}
