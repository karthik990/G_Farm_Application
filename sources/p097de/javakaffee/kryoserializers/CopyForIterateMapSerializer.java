package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/* renamed from: de.javakaffee.kryoserializers.CopyForIterateMapSerializer */
public class CopyForIterateMapSerializer extends MapSerializer {
    public void write(Kryo kryo, Output output, Map map) {
        Map map2;
        if (map instanceof LinkedHashMap) {
            map2 = new LinkedHashMap(map);
        } else {
            map2 = new HashMap(map);
        }
        super.write(kryo, output, map2);
    }
}
