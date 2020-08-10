package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.util.Collections;
import java.util.Set;

/* renamed from: de.javakaffee.kryoserializers.CollectionsEmptySetSerializer */
public class CollectionsEmptySetSerializer extends Serializer<Set<?>> {
    public void write(Kryo kryo, Output output, Set<?> set) {
    }

    public CollectionsEmptySetSerializer() {
        setImmutable(true);
    }

    public Set<?> read(Kryo kryo, Input input, Class<Set<?>> cls) {
        return Collections.EMPTY_SET;
    }
}
