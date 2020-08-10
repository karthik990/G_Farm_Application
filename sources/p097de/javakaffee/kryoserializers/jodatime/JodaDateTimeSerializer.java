package p097de.javakaffee.kryoserializers.jodatime;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/* renamed from: de.javakaffee.kryoserializers.jodatime.JodaDateTimeSerializer */
public class JodaDateTimeSerializer extends Serializer<DateTime> {
    static final String CHRONOLOGY = "ch";
    static final String DATE_TIME = "dt";
    static final String MILLIS = "millis";
    static final String TIME_ZONE = "tz";

    public JodaDateTimeSerializer() {
        setImmutable(true);
    }

    public DateTime read(Kryo kryo, Input input, Class<DateTime> cls) {
        return new DateTime(input.readLong(true), IdentifiableChronology.readChronology(input).withZone(readTimeZone(input)));
    }

    public void write(Kryo kryo, Output output, DateTime dateTime) {
        output.writeLong(dateTime.getMillis(), true);
        String chronologyId = IdentifiableChronology.getChronologyId(dateTime.getChronology());
        if (chronologyId == null) {
            chronologyId = "";
        }
        output.writeString(chronologyId);
        output.writeString(dateTime.getZone().getID());
    }

    private DateTimeZone readTimeZone(Input input) {
        String readString = input.readString();
        if ("".equals(readString)) {
            return DateTimeZone.getDefault();
        }
        return DateTimeZone.forID(readString);
    }
}
