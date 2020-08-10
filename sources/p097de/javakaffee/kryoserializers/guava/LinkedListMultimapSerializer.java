package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.LinkedListMultimap;

/* renamed from: de.javakaffee.kryoserializers.guava.LinkedListMultimapSerializer */
public class LinkedListMultimapSerializer extends MultimapSerializerBase<Object, Object, LinkedListMultimap<Object, Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = false;

    public LinkedListMultimapSerializer() {
        super(false, false);
    }

    public void write(Kryo kryo, Output output, LinkedListMultimap<Object, Object> linkedListMultimap) {
        writeMultimap(kryo, output, linkedListMultimap);
    }

    public LinkedListMultimap<Object, Object> read(Kryo kryo, Input input, Class<LinkedListMultimap<Object, Object>> cls) {
        LinkedListMultimap<Object, Object> create = LinkedListMultimap.create();
        readMultimap(kryo, input, create);
        return create;
    }

    public static void registerSerializers(Kryo kryo) {
        kryo.register(LinkedListMultimap.class, (Serializer) new LinkedListMultimapSerializer());
    }
}
