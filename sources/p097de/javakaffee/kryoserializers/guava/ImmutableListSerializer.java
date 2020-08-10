package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;

/* renamed from: de.javakaffee.kryoserializers.guava.ImmutableListSerializer */
public class ImmutableListSerializer extends Serializer<ImmutableList<Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = true;

    public ImmutableListSerializer() {
        super(false, true);
    }

    public void write(Kryo kryo, Output output, ImmutableList<Object> immutableList) {
        output.writeInt(immutableList.size(), true);
        UnmodifiableIterator it = immutableList.iterator();
        while (it.hasNext()) {
            kryo.writeClassAndObject(output, it.next());
        }
    }

    public ImmutableList<Object> read(Kryo kryo, Input input, Class<ImmutableList<Object>> cls) {
        int readInt = input.readInt(true);
        Object[] objArr = new Object[readInt];
        for (int i = 0; i < readInt; i++) {
            objArr[i] = kryo.readClassAndObject(input);
        }
        return ImmutableList.copyOf((E[]) objArr);
    }

    public static void registerSerializers(Kryo kryo) {
        ImmutableListSerializer immutableListSerializer = new ImmutableListSerializer();
        kryo.register(ImmutableList.class, (Serializer) immutableListSerializer);
        kryo.register(ImmutableList.m1883of().getClass(), (Serializer) immutableListSerializer);
        Integer valueOf = Integer.valueOf(1);
        kryo.register(ImmutableList.m1884of(valueOf).getClass(), (Serializer) immutableListSerializer);
        Integer valueOf2 = Integer.valueOf(2);
        Integer valueOf3 = Integer.valueOf(3);
        kryo.register(ImmutableList.m1886of(valueOf, valueOf2, valueOf3).subList(1, 2).getClass(), (Serializer) immutableListSerializer);
        kryo.register(ImmutableList.m1883of().reverse().getClass(), (Serializer) immutableListSerializer);
        kryo.register(Lists.charactersOf("KryoRocks").getClass(), (Serializer) immutableListSerializer);
        HashBasedTable create = HashBasedTable.create();
        create.put(valueOf, valueOf2, valueOf3);
        create.put(Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(6));
        kryo.register(ImmutableTable.copyOf((Table<? extends R, ? extends C, ? extends V>) create).values().getClass(), (Serializer) immutableListSerializer);
    }
}
