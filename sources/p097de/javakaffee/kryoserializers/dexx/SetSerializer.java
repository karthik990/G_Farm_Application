package p097de.javakaffee.kryoserializers.dexx;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.github.andrewoma.dexx.collection.Builder;
import com.github.andrewoma.dexx.collection.Set;
import com.github.andrewoma.dexx.collection.Sets;

/* renamed from: de.javakaffee.kryoserializers.dexx.SetSerializer */
public class SetSerializer extends Serializer<Set<Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = true;

    public SetSerializer() {
        super(false, true);
    }

    public void write(Kryo kryo, Output output, Set<Object> set) {
        output.writeInt(set.size(), true);
        for (Object writeClassAndObject : set) {
            kryo.writeClassAndObject(output, writeClassAndObject);
        }
    }

    public Set<Object> read(Kryo kryo, Input input, Class<Set<Object>> cls) {
        int readInt = input.readInt(true);
        Builder builder = Sets.builder();
        for (int i = 0; i < readInt; i++) {
            builder.add(kryo.readClassAndObject(input));
        }
        return (Set) builder.build();
    }

    public static void registerSerializers(Kryo kryo) {
        Kryo kryo2 = kryo;
        SetSerializer setSerializer = new SetSerializer();
        kryo2.register(Set.class, (Serializer) setSerializer);
        kryo2.register(Sets.of().getClass(), (Serializer) setSerializer);
        Integer valueOf = Integer.valueOf(1);
        kryo2.register(Sets.of(valueOf).getClass(), (Serializer) setSerializer);
        Integer valueOf2 = Integer.valueOf(2);
        Integer valueOf3 = Integer.valueOf(3);
        kryo2.register(Sets.of(valueOf, valueOf2, valueOf3).getClass(), (Serializer) setSerializer);
        Integer valueOf4 = Integer.valueOf(4);
        kryo2.register(Sets.of(valueOf, valueOf2, valueOf3, valueOf4).getClass(), (Serializer) setSerializer);
        Integer valueOf5 = Integer.valueOf(5);
        kryo2.register(Sets.of(valueOf, valueOf2, valueOf3, valueOf4, valueOf5).getClass(), (Serializer) setSerializer);
        Integer num = valueOf2;
        Integer num2 = valueOf3;
        Integer num3 = valueOf4;
        Integer num4 = valueOf5;
        Integer valueOf6 = Integer.valueOf(6);
        kryo2.register(Sets.of(valueOf, num, num2, num3, num4, valueOf6).getClass(), (Serializer) setSerializer);
        Integer valueOf7 = Integer.valueOf(7);
        kryo2.register(Sets.of(valueOf, num, num2, num3, num4, valueOf6, valueOf7).getClass(), (Serializer) setSerializer);
        kryo2.register(Sets.of(valueOf, num, num2, num3, num4, valueOf6, valueOf7, Integer.valueOf(8)).getClass(), (Serializer) setSerializer);
        Integer num5 = valueOf5;
        kryo2.register(Sets.of(valueOf, num, num2, num3, num4, valueOf6, valueOf7, Integer.valueOf(8), Integer.valueOf(9)).getClass(), (Serializer) setSerializer);
        Integer num6 = num5;
        Integer num7 = valueOf4;
        kryo2.register(Sets.of(valueOf, num, num2, num3, num6, valueOf6, valueOf7, Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10)).getClass(), (Serializer) setSerializer);
        kryo2.register(Sets.of(valueOf, valueOf2, num2, num7, num6, valueOf6, valueOf7, Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(10), new Integer[]{Integer.valueOf(11)}).getClass(), (Serializer) setSerializer);
    }
}
