package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/* renamed from: de.javakaffee.kryoserializers.guava.ImmutableMapSerializer */
public class ImmutableMapSerializer extends Serializer<ImmutableMap<Object, ? extends Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = true;
    private static final boolean IMMUTABLE = true;

    /* renamed from: de.javakaffee.kryoserializers.guava.ImmutableMapSerializer$DummyEnum */
    private enum DummyEnum {
        VALUE1,
        VALUE2
    }

    public ImmutableMapSerializer() {
        super(true, true);
    }

    public void write(Kryo kryo, Output output, ImmutableMap<Object, ? extends Object> immutableMap) {
        kryo.writeObject(output, Maps.newHashMap(immutableMap));
    }

    public ImmutableMap<Object, Object> read(Kryo kryo, Input input, Class<ImmutableMap<Object, ? extends Object>> cls) {
        return ImmutableMap.copyOf((Map) kryo.readObject(input, HashMap.class));
    }

    public static void registerSerializers(Kryo kryo) {
        ImmutableMapSerializer immutableMapSerializer = new ImmutableMapSerializer();
        kryo.register(ImmutableMap.class, (Serializer) immutableMapSerializer);
        kryo.register(ImmutableMap.m1902of().getClass(), (Serializer) immutableMapSerializer);
        Object obj = new Object();
        Object obj2 = new Object();
        kryo.register(ImmutableMap.m1903of(obj, obj).getClass(), (Serializer) immutableMapSerializer);
        kryo.register(ImmutableMap.m1904of(obj, obj, obj2, obj2).getClass(), (Serializer) immutableMapSerializer);
        EnumMap enumMap = new EnumMap(DummyEnum.class);
        for (DummyEnum put : DummyEnum.values()) {
            enumMap.put(put, obj);
        }
        kryo.register(ImmutableMap.copyOf((Map<? extends K, ? extends V>) enumMap).getClass(), (Serializer) immutableMapSerializer);
    }
}
