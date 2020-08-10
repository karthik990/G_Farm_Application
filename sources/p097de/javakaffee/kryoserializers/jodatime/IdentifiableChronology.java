package p097de.javakaffee.kryoserializers.jodatime;

import com.esotericsoftware.kryo.p035io.Input;
import org.joda.time.Chronology;
import org.joda.time.chrono.BuddhistChronology;
import org.joda.time.chrono.CopticChronology;
import org.joda.time.chrono.EthiopicChronology;
import org.joda.time.chrono.GJChronology;
import org.joda.time.chrono.GregorianChronology;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;
import org.joda.time.chrono.JulianChronology;

/* renamed from: de.javakaffee.kryoserializers.jodatime.IdentifiableChronology */
enum IdentifiableChronology {
    ISO(null, ISOChronology.getInstance()),
    COPTIC(r4, CopticChronology.getInstance()),
    ETHIOPIC(r5, EthiopicChronology.getInstance()),
    GREGORIAN(r6, GregorianChronology.getInstance()),
    JULIAN(r7, JulianChronology.getInstance()),
    ISLAMIC(r8, IslamicChronology.getInstance()),
    BUDDHIST(r9, BuddhistChronology.getInstance()),
    f3687GJ(r10, GJChronology.getInstance());
    
    private final Chronology _chronology;
    private final String _id;

    private IdentifiableChronology(String str, Chronology chronology) {
        this._id = str;
        this._chronology = chronology;
    }

    public String getId() {
        return this._id;
    }

    public static String getIdByChronology(Class<? extends Chronology> cls) throws IllegalArgumentException {
        IdentifiableChronology[] values;
        for (IdentifiableChronology identifiableChronology : values()) {
            if (cls.equals(identifiableChronology._chronology.getClass())) {
                return identifiableChronology._id;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Chronology not supported: ");
        sb.append(cls.getSimpleName());
        throw new IllegalArgumentException(sb.toString());
    }

    public static Chronology valueOfId(String str) throws IllegalArgumentException {
        IdentifiableChronology[] values;
        if (str == null) {
            return ISO._chronology;
        }
        for (IdentifiableChronology identifiableChronology : values()) {
            if (str.equals(identifiableChronology._id)) {
                return identifiableChronology._chronology;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("No chronology found for id ");
        sb.append(str);
        throw new IllegalArgumentException(sb.toString());
    }

    static Chronology readChronology(Input input) {
        String readString = input.readString();
        if ("".equals(readString)) {
            readString = null;
        }
        return valueOfId(readString);
    }

    static String getChronologyId(Chronology chronology) {
        return getIdByChronology(chronology.getClass());
    }
}
