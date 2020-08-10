package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import java.util.ArrayList;
import java.util.Collection;

/* renamed from: de.javakaffee.kryoserializers.CopyForIterateCollectionSerializer */
public class CopyForIterateCollectionSerializer extends CollectionSerializer {
    public void write(Kryo kryo, Output output, Collection collection) {
        super.write(kryo, output, (Collection) new ArrayList(collection));
    }
}
