package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.ArrayListMultimap;

/* renamed from: de.javakaffee.kryoserializers.guava.ArrayListMultimapSerializer */
public class ArrayListMultimapSerializer extends MultimapSerializerBase<Object, Object, ArrayListMultimap<Object, Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = false;

    public ArrayListMultimapSerializer() {
        super(false, false);
    }

    public void write(Kryo kryo, Output output, ArrayListMultimap<Object, Object> arrayListMultimap) {
        writeMultimap(kryo, output, arrayListMultimap);
    }

    public ArrayListMultimap<Object, Object> read(Kryo kryo, Input input, Class<ArrayListMultimap<Object, Object>> cls) {
        ArrayListMultimap<Object, Object> create = ArrayListMultimap.create();
        readMultimap(kryo, input, create);
        return create;
    }

    public static void registerSerializers(Kryo kryo) {
        kryo.register(ArrayListMultimap.class, (Serializer) new ArrayListMultimapSerializer());
    }
}
