package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.util.BitSet;

/* renamed from: de.javakaffee.kryoserializers.BitSetSerializer */
public class BitSetSerializer extends Serializer<BitSet> {
    public BitSet copy(Kryo kryo, BitSet bitSet) {
        BitSet bitSet2 = new BitSet();
        int length = bitSet.length();
        for (int i = 0; i < length; i++) {
            bitSet2.set(i, bitSet.get(i));
        }
        return bitSet2;
    }

    public void write(Kryo kryo, Output output, BitSet bitSet) {
        int length = bitSet.length();
        output.writeInt(length, true);
        for (int i = 0; i < length; i++) {
            output.writeBoolean(bitSet.get(i));
        }
    }

    public BitSet read(Kryo kryo, Input input, Class<BitSet> cls) {
        int readInt = input.readInt(true);
        BitSet bitSet = new BitSet(readInt);
        for (int i = 0; i < readInt; i++) {
            bitSet.set(i, input.readBoolean());
        }
        return bitSet;
    }
}
