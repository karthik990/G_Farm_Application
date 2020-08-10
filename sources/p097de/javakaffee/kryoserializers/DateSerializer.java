package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.lang.reflect.Constructor;
import java.util.Date;

/* renamed from: de.javakaffee.kryoserializers.DateSerializer */
public class DateSerializer extends Serializer<Date> {
    private final Constructor<? extends Date> _constructor;

    public DateSerializer(Class<? extends Date> cls) {
        try {
            this._constructor = cls.getConstructor(new Class[]{Long.TYPE});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Date read(Kryo kryo, Input input, Class<Date> cls) {
        try {
            return (Date) this._constructor.newInstance(new Object[]{Long.valueOf(input.readLong(true))});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void write(Kryo kryo, Output output, Date date) {
        output.writeLong(date.getTime(), true);
    }

    public Date copy(Kryo kryo, Date date) {
        return (Date) date.clone();
    }
}
