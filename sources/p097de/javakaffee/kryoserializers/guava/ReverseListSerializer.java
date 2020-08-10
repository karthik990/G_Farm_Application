package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/* renamed from: de.javakaffee.kryoserializers.guava.ReverseListSerializer */
public abstract class ReverseListSerializer extends Serializer<List<Object>> {
    /* access modifiers changed from: private */
    public static final CollectionSerializer serializer = new CollectionSerializer();

    /* renamed from: de.javakaffee.kryoserializers.guava.ReverseListSerializer$RandomAccessReverseList */
    private static class RandomAccessReverseList extends ReverseListSerializer {
        private RandomAccessReverseList() {
        }

        public /* bridge */ /* synthetic */ Object copy(Kryo kryo, Object obj) {
            return ReverseListSerializer.super.copy(kryo, (List) obj);
        }

        public /* bridge */ /* synthetic */ void write(Kryo kryo, Output output, Object obj) {
            ReverseListSerializer.super.write(kryo, output, (List) obj);
        }

        public List<Object> read(Kryo kryo, Input input, Class<List<Object>> cls) {
            return Lists.reverse((List) ReverseListSerializer.serializer.read(kryo, input, ArrayList.class));
        }
    }

    /* renamed from: de.javakaffee.kryoserializers.guava.ReverseListSerializer$ReverseList */
    private static class ReverseList extends ReverseListSerializer {
        private ReverseList() {
        }

        public /* bridge */ /* synthetic */ Object copy(Kryo kryo, Object obj) {
            return ReverseListSerializer.super.copy(kryo, (List) obj);
        }

        public /* bridge */ /* synthetic */ void write(Kryo kryo, Output output, Object obj) {
            ReverseListSerializer.super.write(kryo, output, (List) obj);
        }

        public List<Object> read(Kryo kryo, Input input, Class<List<Object>> cls) {
            return Lists.reverse((List) ReverseListSerializer.serializer.read(kryo, input, LinkedList.class));
        }
    }

    public void write(Kryo kryo, Output output, List<Object> list) {
        serializer.write(kryo, output, (Collection) Lists.reverse(list));
    }

    public List<Object> copy(Kryo kryo, List<Object> list) {
        return Lists.reverse((List) serializer.copy(kryo, (Collection) Lists.reverse(list)));
    }

    public static void registerSerializers(Kryo kryo) {
        kryo.register(Lists.reverse(Lists.newLinkedList()).getClass(), (Serializer) forReverseList());
        kryo.register(Lists.reverse(Lists.newArrayList()).getClass(), (Serializer) forRandomAccessReverseList());
    }

    public static ReverseListSerializer forReverseList() {
        return new ReverseList();
    }

    public static ReverseListSerializer forRandomAccessReverseList() {
        return new RandomAccessReverseList();
    }
}
