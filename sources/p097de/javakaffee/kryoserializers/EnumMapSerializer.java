package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.minlog.Log;
import java.lang.reflect.Field;
import java.util.EnumMap;
import java.util.Map.Entry;

/* renamed from: de.javakaffee.kryoserializers.EnumMapSerializer */
public class EnumMapSerializer extends Serializer<EnumMap<? extends Enum<?>, ?>> {
    private static final Object FAKE_REFERENCE = new Object();
    private static final Field TYPE_FIELD;

    static {
        try {
            TYPE_FIELD = EnumMap.class.getDeclaredField("keyType");
            TYPE_FIELD.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException("The EnumMap class seems to have changed, could not access expected field.", e);
        }
    }

    public EnumMap<? extends Enum<?>, ?> copy(Kryo kryo, EnumMap<? extends Enum<?>, ?> enumMap) {
        EnumMap<? extends Enum<?>, ?> enumMap2 = new EnumMap<>(enumMap);
        for (Entry entry : enumMap.entrySet()) {
            enumMap2.put((Enum) entry.getKey(), kryo.copy(entry.getValue()));
        }
        return enumMap2;
    }

    private EnumMap<? extends Enum<?>, ?> create(Kryo kryo, Input input, Class<EnumMap<? extends Enum<?>, ?>> cls) {
        return new EnumMap<>(kryo.readClass(input).getType());
    }

    public EnumMap<? extends Enum<?>, ?> read(Kryo kryo, Input input, Class<EnumMap<? extends Enum<?>, ?>> cls) {
        kryo.reference(FAKE_REFERENCE);
        EnumMap<? extends Enum<?>, ?> create = create(kryo, input, cls);
        Enum[] enumArr = (Enum[]) getKeyType(create).getEnumConstants();
        int readInt = input.readInt(true);
        for (int i = 0; i < readInt; i++) {
            create.put(enumArr[input.readInt(true)], kryo.readClassAndObject(input));
        }
        return create;
    }

    public void write(Kryo kryo, Output output, EnumMap<? extends Enum<?>, ?> enumMap) {
        kryo.writeClass(output, getKeyType(enumMap));
        output.writeInt(enumMap.size(), true);
        for (Entry entry : enumMap.entrySet()) {
            output.writeInt(((Enum) entry.getKey()).ordinal(), true);
            kryo.writeClassAndObject(output, entry.getValue());
        }
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Wrote EnumMap: ");
            sb.append(enumMap);
            Log.trace("kryo", sb.toString());
        }
    }

    private Class<Enum<?>> getKeyType(EnumMap<?, ?> enumMap) {
        try {
            return (Class) TYPE_FIELD.get(enumMap);
        } catch (Exception e) {
            throw new RuntimeException("Could not access keys field.", e);
        }
    }
}
