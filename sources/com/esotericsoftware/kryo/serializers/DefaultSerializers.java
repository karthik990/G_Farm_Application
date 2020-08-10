package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.util.Util;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.Date;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;

public class DefaultSerializers {

    public static class BigDecimalSerializer extends Serializer<BigDecimal> {
        private final BigIntegerSerializer bigIntegerSerializer = new BigIntegerSerializer();

        public BigDecimalSerializer() {
            setAcceptsNull(true);
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, BigDecimal bigDecimal) {
            if (bigDecimal == null) {
                output.writeVarInt(0, true);
            } else if (bigDecimal == BigDecimal.ZERO) {
                this.bigIntegerSerializer.write(kryo, output, BigInteger.ZERO);
                output.writeInt(0, false);
            } else {
                this.bigIntegerSerializer.write(kryo, output, bigDecimal.unscaledValue());
                output.writeInt(bigDecimal.scale(), false);
            }
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(6:8|9|(2:11|12)|13|14|15) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0030 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.math.BigDecimal read(com.esotericsoftware.kryo.Kryo r6, com.esotericsoftware.kryo.p035io.Input r7, java.lang.Class<java.math.BigDecimal> r8) {
            /*
                r5 = this;
                com.esotericsoftware.kryo.serializers.DefaultSerializers$BigIntegerSerializer r0 = r5.bigIntegerSerializer
                java.lang.Class<java.math.BigInteger> r1 = java.math.BigInteger.class
                java.math.BigInteger r6 = r0.read(r6, r7, r1)
                if (r6 != 0) goto L_0x000c
                r6 = 0
                return r6
            L_0x000c:
                r0 = 0
                int r7 = r7.readInt(r0)
                java.lang.Class<java.math.BigDecimal> r1 = java.math.BigDecimal.class
                if (r8 == r1) goto L_0x0048
                if (r8 == 0) goto L_0x0048
                r1 = 2
                java.lang.Class[] r2 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x0041 }
                java.lang.Class<java.math.BigInteger> r3 = java.math.BigInteger.class
                r2[r0] = r3     // Catch:{ Exception -> 0x0041 }
                java.lang.Class r3 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x0041 }
                r4 = 1
                r2[r4] = r3     // Catch:{ Exception -> 0x0041 }
                java.lang.reflect.Constructor r8 = r8.getConstructor(r2)     // Catch:{ Exception -> 0x0041 }
                boolean r2 = r8.isAccessible()     // Catch:{ Exception -> 0x0041 }
                if (r2 != 0) goto L_0x0030
                r8.setAccessible(r4)     // Catch:{ SecurityException -> 0x0030 }
            L_0x0030:
                java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x0041 }
                r1[r0] = r6     // Catch:{ Exception -> 0x0041 }
                java.lang.Integer r6 = java.lang.Integer.valueOf(r7)     // Catch:{ Exception -> 0x0041 }
                r1[r4] = r6     // Catch:{ Exception -> 0x0041 }
                java.lang.Object r6 = r8.newInstance(r1)     // Catch:{ Exception -> 0x0041 }
                java.math.BigDecimal r6 = (java.math.BigDecimal) r6     // Catch:{ Exception -> 0x0041 }
                return r6
            L_0x0041:
                r6 = move-exception
                com.esotericsoftware.kryo.KryoException r7 = new com.esotericsoftware.kryo.KryoException
                r7.<init>(r6)
                throw r7
            L_0x0048:
                java.math.BigInteger r8 = java.math.BigInteger.ZERO
                if (r6 != r8) goto L_0x0051
                if (r7 != 0) goto L_0x0051
                java.math.BigDecimal r6 = java.math.BigDecimal.ZERO
                return r6
            L_0x0051:
                java.math.BigDecimal r8 = new java.math.BigDecimal
                r8.<init>(r6, r7)
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.serializers.DefaultSerializers.BigDecimalSerializer.read(com.esotericsoftware.kryo.Kryo, com.esotericsoftware.kryo.io.Input, java.lang.Class):java.math.BigDecimal");
        }
    }

    public static class BigIntegerSerializer extends Serializer<BigInteger> {
        public BigIntegerSerializer() {
            setImmutable(true);
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, BigInteger bigInteger) {
            if (bigInteger == null) {
                output.writeVarInt(0, true);
            } else if (bigInteger == BigInteger.ZERO) {
                output.writeVarInt(2, true);
                output.writeByte(0);
            } else {
                byte[] byteArray = bigInteger.toByteArray();
                output.writeVarInt(byteArray.length + 1, true);
                output.writeBytes(byteArray);
            }
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(6:7|8|(2:10|11)|12|13|14) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0029 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.math.BigInteger read(com.esotericsoftware.kryo.Kryo r4, com.esotericsoftware.kryo.p035io.Input r5, java.lang.Class<java.math.BigInteger> r6) {
            /*
                r3 = this;
                r4 = 1
                int r0 = r5.readVarInt(r4)
                if (r0 != 0) goto L_0x0009
                r4 = 0
                return r4
            L_0x0009:
                int r1 = r0 + -1
                byte[] r5 = r5.readBytes(r1)
                java.lang.Class<java.math.BigInteger> r1 = java.math.BigInteger.class
                r2 = 0
                if (r6 == r1) goto L_0x003b
                if (r6 == 0) goto L_0x003b
                java.lang.Class[] r0 = new java.lang.Class[r4]     // Catch:{ Exception -> 0x0034 }
                java.lang.Class<byte[]> r1 = byte[].class
                r0[r2] = r1     // Catch:{ Exception -> 0x0034 }
                java.lang.reflect.Constructor r6 = r6.getConstructor(r0)     // Catch:{ Exception -> 0x0034 }
                boolean r0 = r6.isAccessible()     // Catch:{ Exception -> 0x0034 }
                if (r0 != 0) goto L_0x0029
                r6.setAccessible(r4)     // Catch:{ SecurityException -> 0x0029 }
            L_0x0029:
                java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x0034 }
                r4[r2] = r5     // Catch:{ Exception -> 0x0034 }
                java.lang.Object r4 = r6.newInstance(r4)     // Catch:{ Exception -> 0x0034 }
                java.math.BigInteger r4 = (java.math.BigInteger) r4     // Catch:{ Exception -> 0x0034 }
                return r4
            L_0x0034:
                r4 = move-exception
                com.esotericsoftware.kryo.KryoException r5 = new com.esotericsoftware.kryo.KryoException
                r5.<init>(r4)
                throw r5
            L_0x003b:
                r6 = 2
                if (r0 != r6) goto L_0x0052
                byte r6 = r5[r2]
                if (r6 == 0) goto L_0x004f
                if (r6 == r4) goto L_0x004c
                r4 = 10
                if (r6 == r4) goto L_0x0049
                goto L_0x0052
            L_0x0049:
                java.math.BigInteger r4 = java.math.BigInteger.TEN
                return r4
            L_0x004c:
                java.math.BigInteger r4 = java.math.BigInteger.ONE
                return r4
            L_0x004f:
                java.math.BigInteger r4 = java.math.BigInteger.ZERO
                return r4
            L_0x0052:
                java.math.BigInteger r4 = new java.math.BigInteger
                r4.<init>(r5)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.serializers.DefaultSerializers.BigIntegerSerializer.read(com.esotericsoftware.kryo.Kryo, com.esotericsoftware.kryo.io.Input, java.lang.Class):java.math.BigInteger");
        }
    }

    public static class BooleanSerializer extends Serializer<Boolean> {
        public BooleanSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Boolean bool) {
            output.writeBoolean(bool.booleanValue());
        }

        public Boolean read(Kryo kryo, Input input, Class<Boolean> cls) {
            return Boolean.valueOf(input.readBoolean());
        }
    }

    public static class ByteSerializer extends Serializer<Byte> {
        public ByteSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Byte b) {
            output.writeByte(b.byteValue());
        }

        public Byte read(Kryo kryo, Input input, Class<Byte> cls) {
            return Byte.valueOf(input.readByte());
        }
    }

    public static class CalendarSerializer extends Serializer<Calendar> {
        private static final long DEFAULT_GREGORIAN_CUTOVER = -12219292800000L;
        TimeZoneSerializer timeZoneSerializer = new TimeZoneSerializer();

        public void write(Kryo kryo, Output output, Calendar calendar) {
            this.timeZoneSerializer.write(kryo, output, calendar.getTimeZone());
            output.writeLong(calendar.getTimeInMillis(), true);
            output.writeBoolean(calendar.isLenient());
            output.writeInt(calendar.getFirstDayOfWeek(), true);
            output.writeInt(calendar.getMinimalDaysInFirstWeek(), true);
            if (calendar instanceof GregorianCalendar) {
                output.writeLong(((GregorianCalendar) calendar).getGregorianChange().getTime(), false);
            } else {
                output.writeLong(DEFAULT_GREGORIAN_CUTOVER, false);
            }
        }

        public Calendar read(Kryo kryo, Input input, Class<Calendar> cls) {
            Calendar instance = Calendar.getInstance(this.timeZoneSerializer.read(kryo, input, TimeZone.class));
            instance.setTimeInMillis(input.readLong(true));
            instance.setLenient(input.readBoolean());
            instance.setFirstDayOfWeek(input.readInt(true));
            instance.setMinimalDaysInFirstWeek(input.readInt(true));
            long readLong = input.readLong(false);
            if (readLong != DEFAULT_GREGORIAN_CUTOVER && (instance instanceof GregorianCalendar)) {
                ((GregorianCalendar) instance).setGregorianChange(new Date(readLong));
            }
            return instance;
        }

        public Calendar copy(Kryo kryo, Calendar calendar) {
            return (Calendar) calendar.clone();
        }
    }

    public static class CharSerializer extends Serializer<Character> {
        public CharSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Character ch) {
            output.writeChar(ch.charValue());
        }

        public Character read(Kryo kryo, Input input, Class<Character> cls) {
            return Character.valueOf(input.readChar());
        }
    }

    public static class CharsetSerializer extends Serializer<Charset> {
        public CharsetSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Charset charset) {
            output.writeString(charset.name());
        }

        public Charset read(Kryo kryo, Input input, Class<Charset> cls) {
            return Charset.forName(input.readString());
        }
    }

    public static class ClassSerializer extends Serializer<Class> {
        public ClassSerializer() {
            setImmutable(true);
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, Class cls) {
            kryo.writeClass(output, cls);
            output.writeByte((cls == null || !cls.isPrimitive()) ? 0 : 1);
        }

        public Class read(Kryo kryo, Input input, Class<Class> cls) {
            Registration readClass = kryo.readClass(input);
            int read = input.read();
            Class type = readClass != null ? readClass.getType() : null;
            return (type == null || !type.isPrimitive() || read == 1) ? type : Util.getWrapperClass(type);
        }
    }

    public static class CollectionsEmptyListSerializer extends Serializer {
        public void write(Kryo kryo, Output output, Object obj) {
        }

        public CollectionsEmptyListSerializer() {
            setImmutable(true);
        }

        public Object read(Kryo kryo, Input input, Class cls) {
            return Collections.EMPTY_LIST;
        }
    }

    public static class CollectionsEmptyMapSerializer extends Serializer {
        public void write(Kryo kryo, Output output, Object obj) {
        }

        public CollectionsEmptyMapSerializer() {
            setImmutable(true);
        }

        public Object read(Kryo kryo, Input input, Class cls) {
            return Collections.EMPTY_MAP;
        }
    }

    public static class CollectionsEmptySetSerializer extends Serializer {
        public void write(Kryo kryo, Output output, Object obj) {
        }

        public CollectionsEmptySetSerializer() {
            setImmutable(true);
        }

        public Object read(Kryo kryo, Input input, Class cls) {
            return Collections.EMPTY_SET;
        }
    }

    public static class CollectionsSingletonListSerializer extends Serializer<List> {
        public CollectionsSingletonListSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, List list) {
            kryo.writeClassAndObject(output, list.get(0));
        }

        public List read(Kryo kryo, Input input, Class cls) {
            return Collections.singletonList(kryo.readClassAndObject(input));
        }
    }

    public static class CollectionsSingletonMapSerializer extends Serializer<Map> {
        public CollectionsSingletonMapSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Map map) {
            Entry entry = (Entry) map.entrySet().iterator().next();
            kryo.writeClassAndObject(output, entry.getKey());
            kryo.writeClassAndObject(output, entry.getValue());
        }

        public Map read(Kryo kryo, Input input, Class cls) {
            return Collections.singletonMap(kryo.readClassAndObject(input), kryo.readClassAndObject(input));
        }
    }

    public static class CollectionsSingletonSetSerializer extends Serializer<Set> {
        public CollectionsSingletonSetSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Set set) {
            kryo.writeClassAndObject(output, set.iterator().next());
        }

        public Set read(Kryo kryo, Input input, Class cls) {
            return Collections.singleton(kryo.readClassAndObject(input));
        }
    }

    public static class CurrencySerializer extends Serializer<Currency> {
        public CurrencySerializer() {
            setImmutable(true);
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, Currency currency) {
            output.writeString(currency == null ? null : currency.getCurrencyCode());
        }

        public Currency read(Kryo kryo, Input input, Class<Currency> cls) {
            String readString = input.readString();
            if (readString == null) {
                return null;
            }
            return Currency.getInstance(readString);
        }
    }

    public static class DateSerializer extends Serializer<Date> {
        /* JADX WARNING: Can't wrap try/catch for region: R(7:15|16|17|(2:19|20)|21|22|23) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x003a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private java.util.Date create(com.esotericsoftware.kryo.Kryo r5, java.lang.Class<? extends java.util.Date> r6, long r7) throws com.esotericsoftware.kryo.KryoException {
            /*
                r4 = this;
                java.lang.Class<java.util.Date> r0 = java.util.Date.class
                if (r6 == r0) goto L_0x0053
                if (r6 != 0) goto L_0x0007
                goto L_0x0053
            L_0x0007:
                java.lang.Class<java.sql.Timestamp> r0 = java.sql.Timestamp.class
                if (r6 != r0) goto L_0x0011
                java.sql.Timestamp r5 = new java.sql.Timestamp
                r5.<init>(r7)
                return r5
            L_0x0011:
                java.lang.Class<java.sql.Date> r0 = java.sql.Date.class
                if (r6 != r0) goto L_0x001b
                java.sql.Date r5 = new java.sql.Date
                r5.<init>(r7)
                return r5
            L_0x001b:
                java.lang.Class<java.sql.Time> r0 = java.sql.Time.class
                if (r6 != r0) goto L_0x0025
                java.sql.Time r5 = new java.sql.Time
                r5.<init>(r7)
                return r5
            L_0x0025:
                r0 = 1
                java.lang.Class[] r1 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x0049 }
                java.lang.Class r2 = java.lang.Long.TYPE     // Catch:{ Exception -> 0x0049 }
                r3 = 0
                r1[r3] = r2     // Catch:{ Exception -> 0x0049 }
                java.lang.reflect.Constructor r1 = r6.getConstructor(r1)     // Catch:{ Exception -> 0x0049 }
                boolean r2 = r1.isAccessible()     // Catch:{ Exception -> 0x0049 }
                if (r2 != 0) goto L_0x003a
                r1.setAccessible(r0)     // Catch:{ SecurityException -> 0x003a }
            L_0x003a:
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0049 }
                java.lang.Long r2 = java.lang.Long.valueOf(r7)     // Catch:{ Exception -> 0x0049 }
                r0[r3] = r2     // Catch:{ Exception -> 0x0049 }
                java.lang.Object r0 = r1.newInstance(r0)     // Catch:{ Exception -> 0x0049 }
                java.util.Date r0 = (java.util.Date) r0     // Catch:{ Exception -> 0x0049 }
                return r0
            L_0x0049:
                java.lang.Object r5 = r5.newInstance(r6)
                java.util.Date r5 = (java.util.Date) r5
                r5.setTime(r7)
                return r5
            L_0x0053:
                java.util.Date r5 = new java.util.Date
                r5.<init>(r7)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.serializers.DefaultSerializers.DateSerializer.create(com.esotericsoftware.kryo.Kryo, java.lang.Class, long):java.util.Date");
        }

        public void write(Kryo kryo, Output output, Date date) {
            output.writeLong(date.getTime(), true);
        }

        public Date read(Kryo kryo, Input input, Class<Date> cls) {
            return create(kryo, cls, input.readLong(true));
        }

        public Date copy(Kryo kryo, Date date) {
            return create(kryo, date.getClass(), date.getTime());
        }
    }

    public static class DoubleSerializer extends Serializer<Double> {
        public DoubleSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Double d) {
            output.writeDouble(d.doubleValue());
        }

        public Double read(Kryo kryo, Input input, Class<Double> cls) {
            return Double.valueOf(input.readDouble());
        }
    }

    public static class EnumSerializer extends Serializer<Enum> {
        private Object[] enumConstants;

        public EnumSerializer(Class<? extends Enum> cls) {
            setImmutable(true);
            setAcceptsNull(true);
            this.enumConstants = cls.getEnumConstants();
            if (this.enumConstants == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("The type must be an enum: ");
                sb.append(cls);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        public void write(Kryo kryo, Output output, Enum enumR) {
            if (enumR == null) {
                output.writeVarInt(0, true);
            } else {
                output.writeVarInt(enumR.ordinal() + 1, true);
            }
        }

        public Enum read(Kryo kryo, Input input, Class<Enum> cls) {
            int readVarInt = input.readVarInt(true);
            if (readVarInt == 0) {
                return null;
            }
            int i = readVarInt - 1;
            if (i >= 0) {
                Object[] objArr = this.enumConstants;
                if (i <= objArr.length - 1) {
                    return (Enum) objArr[i];
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid ordinal for enum \"");
            sb.append(cls.getName());
            sb.append("\": ");
            sb.append(i);
            throw new KryoException(sb.toString());
        }
    }

    public static class EnumSetSerializer extends Serializer<EnumSet> {
        public void write(Kryo kryo, Output output, EnumSet enumSet) {
            Serializer serializer;
            if (enumSet.isEmpty()) {
                EnumSet complementOf = EnumSet.complementOf(enumSet);
                if (!complementOf.isEmpty()) {
                    serializer = kryo.writeClass(output, complementOf.iterator().next().getClass()).getSerializer();
                } else {
                    throw new KryoException("An EnumSet must have a defined Enum to be serialized.");
                }
            } else {
                serializer = kryo.writeClass(output, enumSet.iterator().next().getClass()).getSerializer();
            }
            output.writeInt(enumSet.size(), true);
            Iterator it = enumSet.iterator();
            while (it.hasNext()) {
                serializer.write(kryo, output, it.next());
            }
        }

        public EnumSet read(Kryo kryo, Input input, Class<EnumSet> cls) {
            Registration readClass = kryo.readClass(input);
            EnumSet noneOf = EnumSet.noneOf(readClass.getType());
            Serializer serializer = readClass.getSerializer();
            int readInt = input.readInt(true);
            for (int i = 0; i < readInt; i++) {
                noneOf.add(serializer.read(kryo, input, null));
            }
            return noneOf;
        }

        public EnumSet copy(Kryo kryo, EnumSet enumSet) {
            return EnumSet.copyOf(enumSet);
        }
    }

    public static class FloatSerializer extends Serializer<Float> {
        public FloatSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Float f) {
            output.writeFloat(f.floatValue());
        }

        public Float read(Kryo kryo, Input input, Class<Float> cls) {
            return Float.valueOf(input.readFloat());
        }
    }

    public static class IntSerializer extends Serializer<Integer> {
        public IntSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Integer num) {
            output.writeInt(num.intValue(), false);
        }

        public Integer read(Kryo kryo, Input input, Class<Integer> cls) {
            return Integer.valueOf(input.readInt(false));
        }
    }

    public static class KryoSerializableSerializer extends Serializer<KryoSerializable> {
        public void write(Kryo kryo, Output output, KryoSerializable kryoSerializable) {
            kryoSerializable.write(kryo, output);
        }

        public KryoSerializable read(Kryo kryo, Input input, Class<KryoSerializable> cls) {
            KryoSerializable kryoSerializable = (KryoSerializable) kryo.newInstance(cls);
            kryo.reference(kryoSerializable);
            kryoSerializable.read(kryo, input);
            return kryoSerializable;
        }
    }

    public static class LocaleSerializer extends Serializer<Locale> {
        public static final Locale SPAIN;
        public static final Locale SPANISH;

        public LocaleSerializer() {
            setImmutable(true);
        }

        static {
            String str = "es";
            String str2 = "";
            SPANISH = new Locale(str, str2, str2);
            SPAIN = new Locale(str, "ES", str2);
        }

        /* access modifiers changed from: protected */
        public Locale create(String str, String str2, String str3) {
            Locale locale = Locale.getDefault();
            if (isSameLocale(locale, str, str2, str3)) {
                return locale;
            }
            if (locale != Locale.US && isSameLocale(Locale.US, str, str2, str3)) {
                return Locale.US;
            }
            if (isSameLocale(Locale.ENGLISH, str, str2, str3)) {
                return Locale.ENGLISH;
            }
            if (isSameLocale(Locale.GERMAN, str, str2, str3)) {
                return Locale.GERMAN;
            }
            if (isSameLocale(SPANISH, str, str2, str3)) {
                return SPANISH;
            }
            if (isSameLocale(Locale.FRENCH, str, str2, str3)) {
                return Locale.FRENCH;
            }
            if (isSameLocale(Locale.ITALIAN, str, str2, str3)) {
                return Locale.ITALIAN;
            }
            if (isSameLocale(Locale.JAPANESE, str, str2, str3)) {
                return Locale.JAPANESE;
            }
            if (isSameLocale(Locale.KOREAN, str, str2, str3)) {
                return Locale.KOREAN;
            }
            if (isSameLocale(Locale.SIMPLIFIED_CHINESE, str, str2, str3)) {
                return Locale.SIMPLIFIED_CHINESE;
            }
            if (isSameLocale(Locale.CHINESE, str, str2, str3)) {
                return Locale.CHINESE;
            }
            if (isSameLocale(Locale.TRADITIONAL_CHINESE, str, str2, str3)) {
                return Locale.TRADITIONAL_CHINESE;
            }
            if (isSameLocale(Locale.UK, str, str2, str3)) {
                return Locale.UK;
            }
            if (isSameLocale(Locale.GERMANY, str, str2, str3)) {
                return Locale.GERMANY;
            }
            if (isSameLocale(SPAIN, str, str2, str3)) {
                return SPAIN;
            }
            if (isSameLocale(Locale.FRANCE, str, str2, str3)) {
                return Locale.FRANCE;
            }
            if (isSameLocale(Locale.ITALY, str, str2, str3)) {
                return Locale.ITALY;
            }
            if (isSameLocale(Locale.JAPAN, str, str2, str3)) {
                return Locale.JAPAN;
            }
            if (isSameLocale(Locale.KOREA, str, str2, str3)) {
                return Locale.KOREA;
            }
            if (isSameLocale(Locale.CANADA, str, str2, str3)) {
                return Locale.CANADA;
            }
            if (isSameLocale(Locale.CANADA_FRENCH, str, str2, str3)) {
                return Locale.CANADA_FRENCH;
            }
            return new Locale(str, str2, str3);
        }

        public void write(Kryo kryo, Output output, Locale locale) {
            output.writeAscii(locale.getLanguage());
            output.writeAscii(locale.getCountry());
            output.writeString(locale.getVariant());
        }

        public Locale read(Kryo kryo, Input input, Class<Locale> cls) {
            return create(input.readString(), input.readString(), input.readString());
        }

        protected static boolean isSameLocale(Locale locale, String str, String str2, String str3) {
            try {
                if (!locale.getLanguage().equals(str) || !locale.getCountry().equals(str2) || !locale.getVariant().equals(str3)) {
                    return false;
                }
                return true;
            } catch (NullPointerException unused) {
                return false;
            }
        }
    }

    public static class LongSerializer extends Serializer<Long> {
        public LongSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Long l) {
            output.writeLong(l.longValue(), false);
        }

        public Long read(Kryo kryo, Input input, Class<Long> cls) {
            return Long.valueOf(input.readLong(false));
        }
    }

    public static class ShortSerializer extends Serializer<Short> {
        public ShortSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, Short sh) {
            output.writeShort(sh.shortValue());
        }

        public Short read(Kryo kryo, Input input, Class<Short> cls) {
            return Short.valueOf(input.readShort());
        }
    }

    public static class StringBufferSerializer extends Serializer<StringBuffer> {
        public StringBufferSerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, StringBuffer stringBuffer) {
            output.writeString((CharSequence) stringBuffer);
        }

        public StringBuffer read(Kryo kryo, Input input, Class<StringBuffer> cls) {
            String readString = input.readString();
            if (readString == null) {
                return null;
            }
            return new StringBuffer(readString);
        }

        public StringBuffer copy(Kryo kryo, StringBuffer stringBuffer) {
            return new StringBuffer(stringBuffer);
        }
    }

    public static class StringBuilderSerializer extends Serializer<StringBuilder> {
        public StringBuilderSerializer() {
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, StringBuilder sb) {
            output.writeString((CharSequence) sb);
        }

        public StringBuilder read(Kryo kryo, Input input, Class<StringBuilder> cls) {
            return input.readStringBuilder();
        }

        public StringBuilder copy(Kryo kryo, StringBuilder sb) {
            return new StringBuilder(sb);
        }
    }

    public static class StringSerializer extends Serializer<String> {
        public StringSerializer() {
            setImmutable(true);
            setAcceptsNull(true);
        }

        public void write(Kryo kryo, Output output, String str) {
            output.writeString(str);
        }

        public String read(Kryo kryo, Input input, Class<String> cls) {
            return input.readString();
        }
    }

    public static class TimeZoneSerializer extends Serializer<TimeZone> {
        public TimeZoneSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, TimeZone timeZone) {
            output.writeString(timeZone.getID());
        }

        public TimeZone read(Kryo kryo, Input input, Class<TimeZone> cls) {
            return TimeZone.getTimeZone(input.readString());
        }
    }

    public static class TreeMapSerializer extends MapSerializer {
        public void write(Kryo kryo, Output output, Map map) {
            kryo.writeClassAndObject(output, ((TreeMap) map).comparator());
            super.write(kryo, output, map);
        }

        /* access modifiers changed from: protected */
        public Map create(Kryo kryo, Input input, Class<Map> cls) {
            return createTreeMap(cls, (Comparator) kryo.readClassAndObject(input));
        }

        /* access modifiers changed from: protected */
        public Map createCopy(Kryo kryo, Map map) {
            return createTreeMap(map.getClass(), ((TreeMap) map).comparator());
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(7:3|4|5|(2:7|8)|9|10|11) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x001b */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private java.util.TreeMap createTreeMap(java.lang.Class<? extends java.util.Map> r5, java.util.Comparator r6) {
            /*
                r4 = this;
                java.lang.Class<java.util.TreeMap> r0 = java.util.TreeMap.class
                if (r5 == r0) goto L_0x002d
                if (r5 == 0) goto L_0x002d
                r0 = 1
                java.lang.Class[] r1 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x0026 }
                java.lang.Class<java.util.Comparator> r2 = java.util.Comparator.class
                r3 = 0
                r1[r3] = r2     // Catch:{ Exception -> 0x0026 }
                java.lang.reflect.Constructor r5 = r5.getConstructor(r1)     // Catch:{ Exception -> 0x0026 }
                boolean r1 = r5.isAccessible()     // Catch:{ Exception -> 0x0026 }
                if (r1 != 0) goto L_0x001b
                r5.setAccessible(r0)     // Catch:{ SecurityException -> 0x001b }
            L_0x001b:
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0026 }
                r0[r3] = r6     // Catch:{ Exception -> 0x0026 }
                java.lang.Object r5 = r5.newInstance(r0)     // Catch:{ Exception -> 0x0026 }
                java.util.TreeMap r5 = (java.util.TreeMap) r5     // Catch:{ Exception -> 0x0026 }
                return r5
            L_0x0026:
                r5 = move-exception
                com.esotericsoftware.kryo.KryoException r6 = new com.esotericsoftware.kryo.KryoException
                r6.<init>(r5)
                throw r6
            L_0x002d:
                java.util.TreeMap r5 = new java.util.TreeMap
                r5.<init>(r6)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.serializers.DefaultSerializers.TreeMapSerializer.createTreeMap(java.lang.Class, java.util.Comparator):java.util.TreeMap");
        }
    }

    public static class TreeSetSerializer extends CollectionSerializer {
        public void write(Kryo kryo, Output output, Collection collection) {
            kryo.writeClassAndObject(output, ((TreeSet) collection).comparator());
            super.write(kryo, output, collection);
        }

        /* access modifiers changed from: protected */
        public TreeSet create(Kryo kryo, Input input, Class<Collection> cls) {
            return createTreeSet(cls, (Comparator) kryo.readClassAndObject(input));
        }

        /* access modifiers changed from: protected */
        public TreeSet createCopy(Kryo kryo, Collection collection) {
            return createTreeSet(collection.getClass(), ((TreeSet) collection).comparator());
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(7:3|4|5|(2:7|8)|9|10|11) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x001b */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private java.util.TreeSet createTreeSet(java.lang.Class<? extends java.util.Collection> r5, java.util.Comparator r6) {
            /*
                r4 = this;
                java.lang.Class<java.util.TreeSet> r0 = java.util.TreeSet.class
                if (r5 == r0) goto L_0x002d
                if (r5 == 0) goto L_0x002d
                r0 = 1
                java.lang.Class[] r1 = new java.lang.Class[r0]     // Catch:{ Exception -> 0x0026 }
                java.lang.Class<java.util.Comparator> r2 = java.util.Comparator.class
                r3 = 0
                r1[r3] = r2     // Catch:{ Exception -> 0x0026 }
                java.lang.reflect.Constructor r5 = r5.getConstructor(r1)     // Catch:{ Exception -> 0x0026 }
                boolean r1 = r5.isAccessible()     // Catch:{ Exception -> 0x0026 }
                if (r1 != 0) goto L_0x001b
                r5.setAccessible(r0)     // Catch:{ SecurityException -> 0x001b }
            L_0x001b:
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ Exception -> 0x0026 }
                r0[r3] = r6     // Catch:{ Exception -> 0x0026 }
                java.lang.Object r5 = r5.newInstance(r0)     // Catch:{ Exception -> 0x0026 }
                java.util.TreeSet r5 = (java.util.TreeSet) r5     // Catch:{ Exception -> 0x0026 }
                return r5
            L_0x0026:
                r5 = move-exception
                com.esotericsoftware.kryo.KryoException r6 = new com.esotericsoftware.kryo.KryoException
                r6.<init>(r5)
                throw r6
            L_0x002d:
                java.util.TreeSet r5 = new java.util.TreeSet
                r5.<init>(r6)
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.serializers.DefaultSerializers.TreeSetSerializer.createTreeSet(java.lang.Class, java.util.Comparator):java.util.TreeSet");
        }
    }

    public static class URLSerializer extends Serializer<URL> {
        public URLSerializer() {
            setImmutable(true);
        }

        public void write(Kryo kryo, Output output, URL url) {
            output.writeString(url.toExternalForm());
        }

        public URL read(Kryo kryo, Input input, Class<URL> cls) {
            try {
                return new URL(input.readString());
            } catch (MalformedURLException e) {
                throw new KryoException((Throwable) e);
            }
        }
    }

    public static class VoidSerializer extends Serializer {
        public Object read(Kryo kryo, Input input, Class cls) {
            return null;
        }

        public void write(Kryo kryo, Output output, Object obj) {
        }

        public VoidSerializer() {
            setImmutable(true);
        }
    }
}
