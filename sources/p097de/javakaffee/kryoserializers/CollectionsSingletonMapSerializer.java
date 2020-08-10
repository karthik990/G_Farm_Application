package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: de.javakaffee.kryoserializers.CollectionsSingletonMapSerializer */
public class CollectionsSingletonMapSerializer extends Serializer<Map<?, ?>> {
    public CollectionsSingletonMapSerializer() {
        setImmutable(true);
    }

    public Map<?, ?> read(Kryo kryo, Input input, Class<Map<?, ?>> cls) {
        return Collections.singletonMap(kryo.readClassAndObject(input), kryo.readClassAndObject(input));
    }

    public void write(Kryo kryo, Output output, Map<?, ?> map) {
        Entry entry = (Entry) map.entrySet().iterator().next();
        kryo.writeClassAndObject(output, entry.getKey());
        kryo.writeClassAndObject(output, entry.getValue());
    }
}
