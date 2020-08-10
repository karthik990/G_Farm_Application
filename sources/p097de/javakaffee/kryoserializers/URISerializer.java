package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.net.URI;

/* renamed from: de.javakaffee.kryoserializers.URISerializer */
public class URISerializer extends Serializer<URI> {
    public URISerializer() {
        setImmutable(true);
    }

    public void write(Kryo kryo, Output output, URI uri) {
        output.writeString(uri.toString());
    }

    public URI read(Kryo kryo, Input input, Class<URI> cls) {
        return URI.create(input.readString());
    }
}
