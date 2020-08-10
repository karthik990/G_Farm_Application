package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.minlog.Log;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.Iterator;

/* renamed from: de.javakaffee.kryoserializers.EnumSetSerializer */
public class EnumSetSerializer extends Serializer<EnumSet<? extends Enum<?>>> {
    private static final Field TYPE_FIELD;

    static {
        try {
            TYPE_FIELD = EnumSet.class.getDeclaredField("elementType");
            TYPE_FIELD.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException("The EnumSet class seems to have changed, could not access expected field.", e);
        }
    }

    public EnumSet<? extends Enum<?>> copy(Kryo kryo, EnumSet<? extends Enum<?>> enumSet) {
        return enumSet.clone();
    }

    public EnumSet read(Kryo kryo, Input input, Class<EnumSet<? extends Enum<?>>> cls) {
        Class type = kryo.readClass(input).getType();
        EnumSet noneOf = EnumSet.noneOf(type);
        int readInt = input.readInt(true);
        Enum[] enumArr = (Enum[]) type.getEnumConstants();
        for (int i = 0; i < readInt; i++) {
            noneOf.add(enumArr[input.readInt(true)]);
        }
        return noneOf;
    }

    public void write(Kryo kryo, Output output, EnumSet<? extends Enum<?>> enumSet) {
        kryo.writeClass(output, getElementType(enumSet));
        output.writeInt(enumSet.size(), true);
        Iterator it = enumSet.iterator();
        while (it.hasNext()) {
            output.writeInt(((Enum) it.next()).ordinal(), true);
        }
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Wrote EnumSet: ");
            sb.append(enumSet);
            Log.trace("kryo", sb.toString());
        }
    }

    private Class<? extends Enum<?>> getElementType(EnumSet<? extends Enum<?>> enumSet) {
        try {
            return (Class) TYPE_FIELD.get(enumSet);
        } catch (Exception e) {
            throw new RuntimeException("Could not access keys field.", e);
        }
    }
}
