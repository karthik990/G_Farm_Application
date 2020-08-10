package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.HashMultimap;

/* renamed from: de.javakaffee.kryoserializers.guava.HashMultimapSerializer */
public class HashMultimapSerializer extends MultimapSerializerBase<Object, Object, HashMultimap<Object, Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = false;

    public HashMultimapSerializer() {
        super(false, false);
    }

    public void write(Kryo kryo, Output output, HashMultimap<Object, Object> hashMultimap) {
        writeMultimap(kryo, output, hashMultimap);
    }

    public HashMultimap<Object, Object> read(Kryo kryo, Input input, Class<HashMultimap<Object, Object>> cls) {
        HashMultimap<Object, Object> create = HashMultimap.create();
        readMultimap(kryo, input, create);
        return create;
    }

    public static void registerSerializers(Kryo kryo) {
        kryo.register(HashMultimap.class, (Serializer) new HashMultimapSerializer());
    }
}
