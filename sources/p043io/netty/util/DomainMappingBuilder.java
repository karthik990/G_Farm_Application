package p043io.netty.util;

@Deprecated
/* renamed from: io.netty.util.DomainMappingBuilder */
public final class DomainMappingBuilder<V> {
    private final DomainNameMappingBuilder<V> builder;

    public DomainMappingBuilder(V v) {
        this.builder = new DomainNameMappingBuilder<>(v);
    }

    public DomainMappingBuilder(int i, V v) {
        this.builder = new DomainNameMappingBuilder<>(i, v);
    }

    public DomainMappingBuilder<V> add(String str, V v) {
        this.builder.add(str, v);
        return this;
    }

    public DomainNameMapping<V> build() {
        return this.builder.build();
    }
}
