package p043io.paperdb.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import java.util.ArrayList;
import java.util.Collection;

/* renamed from: io.paperdb.serializer.NoArgCollectionSerializer */
public class NoArgCollectionSerializer extends CollectionSerializer {
    /* access modifiers changed from: protected */
    public Collection create(Kryo kryo, Input input, Class<Collection> cls) {
        return new ArrayList();
    }
}
