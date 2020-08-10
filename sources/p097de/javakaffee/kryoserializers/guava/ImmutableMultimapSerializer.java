package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* renamed from: de.javakaffee.kryoserializers.guava.ImmutableMultimapSerializer */
public class ImmutableMultimapSerializer extends Serializer<ImmutableMultimap<Object, Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = true;
    private static final boolean IMMUTABLE = true;

    public ImmutableMultimapSerializer() {
        super(true, true);
    }

    public void write(Kryo kryo, Output output, ImmutableMultimap<Object, Object> immutableMultimap) {
        kryo.writeObject(output, ImmutableMap.copyOf((Map<? extends K, ? extends V>) immutableMultimap.asMap()));
    }

    public ImmutableMultimap<Object, Object> read(Kryo kryo, Input input, Class<ImmutableMultimap<Object, Object>> cls) {
        Set<Entry> entrySet = ((Map) kryo.readObject(input, ImmutableMap.class)).entrySet();
        Builder builder = ImmutableMultimap.builder();
        for (Entry entry : entrySet) {
            builder.putAll(entry.getKey(), (Iterable) entry.getValue());
        }
        return builder.build();
    }

    public static void registerSerializers(Kryo kryo) {
        if (!(kryo.getSerializer(ImmutableList.class) instanceof ImmutableListSerializer)) {
            ImmutableListSerializer.registerSerializers(kryo);
        }
        if (!(kryo.getSerializer(ImmutableMap.class) instanceof ImmutableMapSerializer)) {
            ImmutableMapSerializer.registerSerializers(kryo);
        }
        ImmutableMultimapSerializer immutableMultimapSerializer = new ImmutableMultimapSerializer();
        kryo.register(ImmutableMultimap.class, (Serializer) immutableMultimapSerializer);
        kryo.register(ImmutableMultimap.m1908of().getClass(), (Serializer) immutableMultimapSerializer);
        Object obj = new Object();
        kryo.register(ImmutableMultimap.m1909of(obj, obj).getClass(), (Serializer) immutableMultimapSerializer);
    }
}
