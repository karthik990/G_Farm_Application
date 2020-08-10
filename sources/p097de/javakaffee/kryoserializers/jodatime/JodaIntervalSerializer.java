package p097de.javakaffee.kryoserializers.jodatime;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import org.joda.time.Interval;

/* renamed from: de.javakaffee.kryoserializers.jodatime.JodaIntervalSerializer */
public class JodaIntervalSerializer extends Serializer<Interval> {
    public JodaIntervalSerializer() {
        setImmutable(true);
    }

    public Interval read(Kryo kryo, Input input, Class<Interval> cls) {
        Interval interval = new Interval(input.readLong(true), input.readLong(true), IdentifiableChronology.readChronology(input));
        return interval;
    }

    public void write(Kryo kryo, Output output, Interval interval) {
        long startMillis = interval.getStartMillis();
        long endMillis = interval.getEndMillis();
        String chronologyId = IdentifiableChronology.getChronologyId(interval.getChronology());
        output.writeLong(startMillis, true);
        output.writeLong(endMillis, true);
        if (chronologyId == null) {
            chronologyId = "";
        }
        output.writeString(chronologyId);
    }
}
