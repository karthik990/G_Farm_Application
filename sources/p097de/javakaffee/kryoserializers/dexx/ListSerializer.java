package p097de.javakaffee.kryoserializers.dexx;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.github.andrewoma.dexx.collection.IndexedLists;
import com.github.andrewoma.dexx.collection.List;

/* renamed from: de.javakaffee.kryoserializers.dexx.ListSerializer */
public class ListSerializer extends Serializer<List> {
    private static final boolean DOES_NOT_ACCEPT_NULL = true;
    private static final boolean IMMUTABLE = true;

    public ListSerializer() {
        super(true, true);
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=com.github.andrewoma.dexx.collection.List, code=com.github.andrewoma.dexx.collection.List<java.lang.Object>, for r5v0, types: [com.github.andrewoma.dexx.collection.List, com.github.andrewoma.dexx.collection.List<java.lang.Object>] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.esotericsoftware.kryo.Kryo r3, com.esotericsoftware.kryo.p035io.Output r4, com.github.andrewoma.dexx.collection.List<java.lang.Object> r5) {
        /*
            r2 = this;
            int r0 = r5.size()
            r1 = 1
            r4.writeInt(r0, r1)
            java.util.Iterator r5 = r5.iterator()
        L_0x000c:
            boolean r0 = r5.hasNext()
            if (r0 == 0) goto L_0x001a
            java.lang.Object r0 = r5.next()
            r3.writeClassAndObject(r4, r0)
            goto L_0x000c
        L_0x001a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p097de.javakaffee.kryoserializers.dexx.ListSerializer.write(com.esotericsoftware.kryo.Kryo, com.esotericsoftware.kryo.io.Output, com.github.andrewoma.dexx.collection.List):void");
    }

    public List<Object> read(Kryo kryo, Input input, Class<List> cls) {
        int readInt = input.readInt(true);
        Object[] objArr = new Object[readInt];
        for (int i = 0; i < readInt; i++) {
            objArr[i] = kryo.readClassAndObject(input);
        }
        return IndexedLists.copyOf(objArr);
    }

    public static void registerSerializers(Kryo kryo) {
        Kryo kryo2 = kryo;
        ListSerializer listSerializer = new ListSerializer();
        kryo2.register(List.class, (Serializer) listSerializer);
        kryo2.register(IndexedLists.of().getClass(), (Serializer) listSerializer);
        Integer valueOf = Integer.valueOf(1);
        kryo2.register(IndexedLists.of(valueOf).getClass(), (Serializer) listSerializer);
        Integer valueOf2 = Integer.valueOf(2);
        kryo2.register(IndexedLists.of(valueOf, valueOf2).getClass(), (Serializer) listSerializer);
        Integer valueOf3 = Integer.valueOf(3);
        kryo2.register(IndexedLists.of(valueOf, valueOf2, valueOf3).getClass(), (Serializer) listSerializer);
        Integer valueOf4 = Integer.valueOf(4);
        kryo2.register(IndexedLists.of(valueOf, valueOf2, valueOf3, valueOf4).getClass(), (Serializer) listSerializer);
        Integer valueOf5 = Integer.valueOf(5);
        kryo2.register(IndexedLists.of(valueOf, valueOf2, valueOf3, valueOf4, valueOf5).getClass(), (Serializer) listSerializer);
        Integer num = valueOf2;
        Integer num2 = valueOf3;
        Integer num3 = valueOf4;
        Integer num4 = valueOf5;
        Integer valueOf6 = Integer.valueOf(6);
        kryo2.register(IndexedLists.of(valueOf, num, num2, num3, num4, valueOf6).getClass(), (Serializer) listSerializer);
        Integer valueOf7 = Integer.valueOf(7);
        kryo2.register(IndexedLists.of(valueOf, num, num2, num3, num4, valueOf6, valueOf7).getClass(), (Serializer) listSerializer);
        kryo2.register(IndexedLists.of(valueOf, num, num2, num3, num4, valueOf6, valueOf7, Integer.valueOf(8)).getClass(), (Serializer) listSerializer);
        kryo2.register(IndexedLists.of(valueOf, num, num2, num3, num4, valueOf6, valueOf7, Integer.valueOf(8), Integer.valueOf(9)).getClass(), (Serializer) listSerializer);
        kryo2.register(IndexedLists.of(valueOf, num, num2, num3, num4, valueOf6, valueOf7, Integer.valueOf(8), Integer.valueOf(10)).getClass(), (Serializer) listSerializer);
        kryo2.register(IndexedLists.of(valueOf, num, num2, num3, num4, valueOf6, valueOf7, Integer.valueOf(8), Integer.valueOf(10), Integer.valueOf(11)).getClass(), (Serializer) listSerializer);
    }
}
