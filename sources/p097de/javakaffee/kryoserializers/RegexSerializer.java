package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.util.regex.Pattern;

/* renamed from: de.javakaffee.kryoserializers.RegexSerializer */
public class RegexSerializer extends Serializer<Pattern> {
    public RegexSerializer() {
        setImmutable(true);
    }

    public void write(Kryo kryo, Output output, Pattern pattern) {
        output.writeString(pattern.pattern());
        output.writeInt(pattern.flags(), true);
    }

    public Pattern read(Kryo kryo, Input input, Class<Pattern> cls) {
        return Pattern.compile(input.readString(), input.readInt(true));
    }
}
