package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.util.Collections;
import java.util.Set;

/* renamed from: de.javakaffee.kryoserializers.CollectionsSingletonSetSerializer */
public class CollectionsSingletonSetSerializer extends Serializer<Set<?>> {
    public CollectionsSingletonSetSerializer() {
        setImmutable(true);
    }

    public Set<?> read(Kryo kryo, Input input, Class<Set<?>> cls) {
        return Collections.singleton(kryo.readClassAndObject(input));
    }

    public void write(Kryo kryo, Output output, Set<?> set) {
        kryo.writeClassAndObject(output, set.iterator().next());
    }
}
