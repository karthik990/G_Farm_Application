package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/* renamed from: de.javakaffee.kryoserializers.GregorianCalendarSerializer */
public class GregorianCalendarSerializer extends Serializer<GregorianCalendar> {
    private final Field _zoneField;

    public GregorianCalendarSerializer() {
        try {
            this._zoneField = Calendar.class.getDeclaredField("zone");
            this._zoneField.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public GregorianCalendar read(Kryo kryo, Input input, Class<GregorianCalendar> cls) {
        Calendar instance = GregorianCalendar.getInstance();
        instance.setTimeInMillis(input.readLong(true));
        instance.setLenient(input.readBoolean());
        instance.setFirstDayOfWeek(input.readInt(true));
        instance.setMinimalDaysInFirstWeek(input.readInt(true));
        String readString = input.readString();
        if (!getTimeZone(instance).getID().equals(readString)) {
            instance.setTimeZone(TimeZone.getTimeZone(readString));
        }
        return (GregorianCalendar) instance;
    }

    public void write(Kryo kryo, Output output, GregorianCalendar gregorianCalendar) {
        output.writeLong(gregorianCalendar.getTimeInMillis(), true);
        output.writeBoolean(gregorianCalendar.isLenient());
        output.writeInt(gregorianCalendar.getFirstDayOfWeek(), true);
        output.writeInt(gregorianCalendar.getMinimalDaysInFirstWeek(), true);
        output.writeString(getTimeZone(gregorianCalendar).getID());
    }

    public GregorianCalendar copy(Kryo kryo, GregorianCalendar gregorianCalendar) {
        return (GregorianCalendar) gregorianCalendar.clone();
    }

    private TimeZone getTimeZone(Calendar calendar) {
        try {
            return (TimeZone) this._zoneField.get(calendar);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
