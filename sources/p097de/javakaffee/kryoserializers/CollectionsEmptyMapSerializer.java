package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.util.Collections;
import java.util.Map;

/* renamed from: de.javakaffee.kryoserializers.CollectionsEmptyMapSerializer */
public class CollectionsEmptyMapSerializer extends Serializer<Map<?, ?>> {
    public void write(Kryo kryo, Output output, Map<?, ?> map) {
    }

    public CollectionsEmptyMapSerializer() {
        setImmutable(true);
    }

    public Map<?, ?> read(Kryo kryo, Input input, Class<Map<?, ?>> cls) {
        return Collections.EMPTY_MAP;
    }
}
