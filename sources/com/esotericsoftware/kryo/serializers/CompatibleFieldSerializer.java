package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.InputChunked;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.p035io.OutputChunked;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.kryo.util.ObjectMap;
import com.esotericsoftware.minlog.Log;

public class CompatibleFieldSerializer<T> extends FieldSerializer<T> {
    private static final int THRESHOLD_BINARY_SEARCH = 32;

    public CompatibleFieldSerializer(Kryo kryo, Class cls) {
        super(kryo, cls);
    }

    public void write(Kryo kryo, Output output, T t) {
        CachedField[] fields = getFields();
        ObjectMap graphContext = kryo.getGraphContext();
        if (!graphContext.containsKey(this)) {
            graphContext.put(this, null);
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Write ");
                sb.append(fields.length);
                sb.append(" field names.");
                Log.trace("kryo", sb.toString());
            }
            output.writeVarInt(fields.length, true);
            for (CachedField cachedFieldName : fields) {
                output.writeString(getCachedFieldName(cachedFieldName));
            }
        }
        OutputChunked outputChunked = new OutputChunked(output, 1024);
        for (CachedField write : fields) {
            write.write(outputChunked, t);
            outputChunked.endChunks();
        }
    }

    public T read(Kryo kryo, Input input, Class<T> cls) {
        Input input2 = input;
        T create = create(kryo, input, cls);
        kryo.reference(create);
        ObjectMap graphContext = kryo.getGraphContext();
        CachedField[] cachedFieldArr = (CachedField[]) graphContext.get(this);
        String str = "kryo";
        if (cachedFieldArr == null) {
            int readVarInt = input2.readVarInt(true);
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Read ");
                sb.append(readVarInt);
                sb.append(" field names.");
                Log.trace(str, sb.toString());
            }
            String[] strArr = new String[readVarInt];
            for (int i = 0; i < readVarInt; i++) {
                strArr[i] = input.readString();
            }
            CachedField[] cachedFieldArr2 = new CachedField[readVarInt];
            CachedField[] fields = getFields();
            String str2 = "Ignore obsolete field: ";
            if (readVarInt < 32) {
                for (int i2 = 0; i2 < readVarInt; i2++) {
                    String str3 = strArr[i2];
                    int length = fields.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 < length) {
                            if (getCachedFieldName(fields[i3]).equals(str3)) {
                                cachedFieldArr2[i2] = fields[i3];
                                break;
                            }
                            i3++;
                        } else if (Log.TRACE) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(str2);
                            sb2.append(str3);
                            Log.trace(str, sb2.toString());
                        }
                    }
                }
            } else {
                int length2 = fields.length > readVarInt ? fields.length : readVarInt;
                for (int i4 = 0; i4 < readVarInt; i4++) {
                    String str4 = strArr[i4];
                    int i5 = length2 - 1;
                    int i6 = 0;
                    while (true) {
                        if (i6 <= i5) {
                            int i7 = (i6 + i5) >>> 1;
                            int compareTo = str4.compareTo(getCachedFieldName(fields[i7]));
                            if (compareTo >= 0) {
                                if (compareTo <= 0) {
                                    cachedFieldArr2[i4] = fields[i7];
                                    break;
                                }
                                i6 = i7 + 1;
                            } else {
                                i5 = i7 - 1;
                            }
                        } else if (Log.TRACE) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str2);
                            sb3.append(str4);
                            Log.trace(str, sb3.toString());
                        }
                    }
                }
            }
            graphContext.put(this, cachedFieldArr2);
            cachedFieldArr = cachedFieldArr2;
        }
        InputChunked inputChunked = new InputChunked(input2, 1024);
        boolean z = getGenerics() != null;
        for (CachedField cachedField : cachedFieldArr) {
            if (cachedField != null && z) {
                cachedField = getField(getCachedFieldName(cachedField));
            }
            if (cachedField == null) {
                if (Log.TRACE) {
                    Log.trace(str, "Skip obsolete field.");
                }
                inputChunked.nextChunks();
            } else {
                cachedField.read(inputChunked, create);
                inputChunked.nextChunks();
            }
        }
        return create;
    }
}
