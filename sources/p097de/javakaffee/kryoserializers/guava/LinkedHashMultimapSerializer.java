package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.LinkedHashMultimap;

/* renamed from: de.javakaffee.kryoserializers.guava.LinkedHashMultimapSerializer */
public class LinkedHashMultimapSerializer extends MultimapSerializerBase<Object, Object, LinkedHashMultimap<Object, Object>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = false;
    private static final boolean IMMUTABLE = false;

    public LinkedHashMultimapSerializer() {
        super(false, false);
    }

    public void write(Kryo kryo, Output output, LinkedHashMultimap<Object, Object> linkedHashMultimap) {
        writeMultimap(kryo, output, linkedHashMultimap);
    }

    public LinkedHashMultimap<Object, Object> read(Kryo kryo, Input input, Class<LinkedHashMultimap<Object, Object>> cls) {
        LinkedHashMultimap<Object, Object> create = LinkedHashMultimap.create();
        readMultimap(kryo, input, create);
        return create;
    }

    public static void registerSerializers(Kryo kryo) {
        kryo.register(LinkedHashMultimap.class, (Serializer) new LinkedHashMultimapSerializer());
    }
}
