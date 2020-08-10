package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.TreeMultimap;

/* renamed from: de.javakaffee.kryoserializers.guava.TreeMultimapSerializer */
public class TreeMultimapSerializer extends MultimapSerializerBase<Comparable, Comparable, TreeMultimap<Comparable, Comparable>> {
    private static final boolean DOES_NOT_ACCEPT_NULL = true;
    private static final boolean IMMUTABLE = false;

    public TreeMultimapSerializer() {
        super(true, false);
    }

    public void write(Kryo kryo, Output output, TreeMultimap<Comparable, Comparable> treeMultimap) {
        writeMultimap(kryo, output, treeMultimap);
    }

    public TreeMultimap<Comparable, Comparable> read(Kryo kryo, Input input, Class<TreeMultimap<Comparable, Comparable>> cls) {
        TreeMultimap<Comparable, Comparable> create = TreeMultimap.create();
        readMultimap(kryo, input, create);
        return create;
    }

    public static void registerSerializers(Kryo kryo) {
        kryo.register(TreeMultimap.class, (Serializer) new TreeMultimapSerializer());
    }
}
