package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.util.Collections;
import java.util.List;

/* renamed from: de.javakaffee.kryoserializers.CollectionsSingletonListSerializer */
public class CollectionsSingletonListSerializer extends Serializer<List<?>> {
    public CollectionsSingletonListSerializer() {
        setImmutable(true);
    }

    public List<?> read(Kryo kryo, Input input, Class<List<?>> cls) {
        return Collections.singletonList(kryo.readClassAndObject(input));
    }

    public void write(Kryo kryo, Output output, List<?> list) {
        kryo.writeClassAndObject(output, list.get(0));
    }
}
