package p097de.javakaffee.kryoserializers.jodatime;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import org.apache.http.HttpStatus;
import org.joda.time.LocalDateTime;

/* renamed from: de.javakaffee.kryoserializers.jodatime.JodaLocalDateTimeSerializer */
public class JodaLocalDateTimeSerializer extends Serializer<LocalDateTime> {
    public JodaLocalDateTimeSerializer() {
        setImmutable(true);
    }

    public LocalDateTime read(Kryo kryo, Input input, Class<LocalDateTime> cls) {
        long readLong = input.readLong(true);
        int i = (int) (readLong / 86400000);
        int i2 = (int) (readLong % 86400000);
        LocalDateTime localDateTime = new LocalDateTime(i / HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE, (i % HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE) / 32, i % 32, i2 / 3600000, (i2 % 3600000) / 60000, (i2 % 60000) / 1000, i2 % 1000, IdentifiableChronology.readChronology(input));
        return localDateTime;
    }

    public void write(Kryo kryo, Output output, LocalDateTime localDateTime) {
        output.writeLong((((long) ((localDateTime.getYear() * 13 * 32) + (localDateTime.getMonthOfYear() * 32) + localDateTime.getDayOfMonth())) * 86400000) + ((long) localDateTime.getMillisOfDay()), true);
        String chronologyId = IdentifiableChronology.getChronologyId(localDateTime.getChronology());
        if (chronologyId == null) {
            chronologyId = "";
        }
        output.writeString(chronologyId);
    }
}
