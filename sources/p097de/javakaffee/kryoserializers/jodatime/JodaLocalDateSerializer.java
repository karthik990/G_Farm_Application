package p097de.javakaffee.kryoserializers.jodatime;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import org.apache.http.HttpStatus;
import org.joda.time.LocalDate;

/* renamed from: de.javakaffee.kryoserializers.jodatime.JodaLocalDateSerializer */
public class JodaLocalDateSerializer extends Serializer<LocalDate> {
    public JodaLocalDateSerializer() {
        setImmutable(true);
    }

    public LocalDate read(Kryo kryo, Input input, Class<LocalDate> cls) {
        int readInt = input.readInt(true);
        return new LocalDate(readInt / HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE, (readInt % HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE) / 32, readInt % 32, IdentifiableChronology.readChronology(input));
    }

    public void write(Kryo kryo, Output output, LocalDate localDate) {
        output.writeInt((localDate.getYear() * 13 * 32) + (localDate.getMonthOfYear() * 32) + localDate.getDayOfMonth(), true);
        String chronologyId = IdentifiableChronology.getChronologyId(localDate.getChronology());
        if (chronologyId == null) {
            chronologyId = "";
        }
        output.writeString(chronologyId);
    }
}
