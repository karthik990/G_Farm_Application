package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableSortedSet.Builder;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Comparator;

/* renamed from: de.javakaffee.kryoserializers.guava.ImmutableSortedSetSerializer */
public class ImmutableSortedSetSerializer extends Serializer<ImmutableSortedSet<Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = true;

    public ImmutableSortedSetSerializer() {
        super(false, true);
    }

    public void write(Kryo kryo, Output output, ImmutableSortedSet<Object> immutableSortedSet) {
        kryo.writeClassAndObject(output, immutableSortedSet.comparator());
        output.writeInt(immutableSortedSet.size(), true);
        UnmodifiableIterator it = immutableSortedSet.iterator();
        while (it.hasNext()) {
            kryo.writeClassAndObject(output, it.next());
        }
    }

    public ImmutableSortedSet<Object> read(Kryo kryo, Input input, Class<ImmutableSortedSet<Object>> cls) {
        Builder orderedBy = ImmutableSortedSet.orderedBy((Comparator) kryo.readClassAndObject(input));
        int readInt = input.readInt(true);
        for (int i = 0; i < readInt; i++) {
            orderedBy.add(kryo.readClassAndObject(input));
        }
        return orderedBy.build();
    }

    public static void registerSerializers(Kryo kryo) {
        ImmutableSortedSetSerializer immutableSortedSetSerializer = new ImmutableSortedSetSerializer();
        kryo.register(ImmutableSortedSet.class, (Serializer) immutableSortedSetSerializer);
        kryo.register(ImmutableSortedSet.m1963of().getClass(), (Serializer) immutableSortedSetSerializer);
        kryo.register(ImmutableSortedSet.m1964of("").getClass(), (Serializer) immutableSortedSetSerializer);
        kryo.register(ImmutableSortedSet.m1963of().descendingSet().getClass(), (Serializer) immutableSortedSetSerializer);
    }
}
