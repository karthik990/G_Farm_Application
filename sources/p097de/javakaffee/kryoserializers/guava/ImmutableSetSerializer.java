package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;

/* renamed from: de.javakaffee.kryoserializers.guava.ImmutableSetSerializer */
public class ImmutableSetSerializer extends Serializer<ImmutableSet<Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = true;

    /* renamed from: de.javakaffee.kryoserializers.guava.ImmutableSetSerializer$SomeEnum */
    private enum SomeEnum {
        A,
        B,
        C
    }

    public ImmutableSetSerializer() {
        super(false, true);
    }

    public void write(Kryo kryo, Output output, ImmutableSet<Object> immutableSet) {
        output.writeInt(immutableSet.size(), true);
        UnmodifiableIterator it = immutableSet.iterator();
        while (it.hasNext()) {
            kryo.writeClassAndObject(output, it.next());
        }
    }

    public ImmutableSet<Object> read(Kryo kryo, Input input, Class<ImmutableSet<Object>> cls) {
        int readInt = input.readInt(true);
        Builder builder = ImmutableSet.builder();
        for (int i = 0; i < readInt; i++) {
            builder.add(kryo.readClassAndObject(input));
        }
        return builder.build();
    }

    public static void registerSerializers(Kryo kryo) {
        ImmutableSetSerializer immutableSetSerializer = new ImmutableSetSerializer();
        kryo.register(ImmutableSet.class, (Serializer) immutableSetSerializer);
        kryo.register(ImmutableSet.m1925of().getClass(), (Serializer) immutableSetSerializer);
        Integer valueOf = Integer.valueOf(1);
        kryo.register(ImmutableSet.m1926of(valueOf).getClass(), (Serializer) immutableSetSerializer);
        kryo.register(ImmutableSet.m1928of(valueOf, Integer.valueOf(2), Integer.valueOf(3)).getClass(), (Serializer) immutableSetSerializer);
        kryo.register(Sets.immutableEnumSet(SomeEnum.A, SomeEnum.B, SomeEnum.C).getClass(), (Serializer) immutableSetSerializer);
    }
}
