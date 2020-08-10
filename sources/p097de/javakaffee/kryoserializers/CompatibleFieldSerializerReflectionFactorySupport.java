package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;

/* renamed from: de.javakaffee.kryoserializers.CompatibleFieldSerializerReflectionFactorySupport */
public class CompatibleFieldSerializerReflectionFactorySupport extends CompatibleFieldSerializer<Object> {
    public CompatibleFieldSerializerReflectionFactorySupport(Kryo kryo, Class<?> cls) {
        super(kryo, cls);
    }

    public Object create(Kryo kryo, Input input, Class cls) {
        return KryoReflectionFactorySupport.newInstanceFromReflectionFactory(cls);
    }
}
