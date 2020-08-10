package p097de.javakaffee.kryoserializers.wicket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.minlog.Log;
import java.lang.reflect.Field;
import java.util.Map.Entry;
import org.apache.wicket.util.collections.MiniMap;

/* renamed from: de.javakaffee.kryoserializers.wicket.MiniMapSerializer */
public class MiniMapSerializer extends Serializer<MiniMap<Object, Object>> {
    private static final Field KEYS_FIELD;

    static {
        try {
            KEYS_FIELD = MiniMap.class.getDeclaredField("keys");
            KEYS_FIELD.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException("The MiniMap seems to have changed, could not access expected field.", e);
        }
    }

    private int getMaxEntries(MiniMap<?, ?> miniMap) {
        try {
            return ((Object[]) KEYS_FIELD.get(miniMap)).length;
        } catch (Exception e) {
            throw new RuntimeException("Could not access keys field.", e);
        }
    }

    public void write(Kryo kryo, Output output, MiniMap<Object, Object> miniMap) {
        output.writeInt(getMaxEntries(miniMap), true);
        output.writeInt(miniMap.size(), true);
        for (Entry entry : miniMap.entrySet()) {
            kryo.writeClassAndObject(output, entry.getKey());
            kryo.writeClassAndObject(output, entry.getValue());
        }
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Wrote map: ");
            sb.append(miniMap);
            Log.trace("kryo", sb.toString());
        }
    }

    public MiniMap<Object, Object> read(Kryo kryo, Input input, Class<MiniMap<Object, Object>> cls) {
        MiniMap<Object, Object> miniMap = new MiniMap<>(input.readInt(true));
        int readInt = input.readInt(true);
        for (int i = 0; i < readInt; i++) {
            miniMap.put(kryo.readClassAndObject(input), kryo.readClassAndObject(input));
        }
        return miniMap;
    }
}
