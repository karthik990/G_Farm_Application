package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.factories.PseudoSerializerFactory;
import com.esotericsoftware.kryo.factories.ReflectionSerializerFactory;
import com.esotericsoftware.kryo.factories.SerializerFactory;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.ClosureSerializer.Closure;
import com.esotericsoftware.kryo.serializers.CollectionSerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.BooleanArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.ByteArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.CharArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.DoubleArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.FloatArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.IntArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.LongArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.ObjectArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.ShortArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultArraySerializers.StringArraySerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.BigDecimalSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.BigIntegerSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.BooleanSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.ByteSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CalendarSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CharSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CharsetSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.ClassSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsEmptyListSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsEmptyMapSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsEmptySetSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsSingletonListSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsSingletonMapSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CollectionsSingletonSetSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.CurrencySerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.DateSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.DoubleSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.EnumSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.EnumSetSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.FloatSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.IntSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.KryoSerializableSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.LocaleSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.LongSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.ShortSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringBufferSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringBuilderSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.TimeZoneSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.TreeMapSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.TreeSetSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.URLSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers.VoidSerializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.esotericsoftware.kryo.serializers.FieldSerializerConfig;
import com.esotericsoftware.kryo.serializers.GenericsResolver;
import com.esotericsoftware.kryo.serializers.MapSerializer;
import com.esotericsoftware.kryo.serializers.OptionalSerializers;
import com.esotericsoftware.kryo.serializers.TaggedFieldSerializerConfig;
import com.esotericsoftware.kryo.serializers.TimeSerializers;
import com.esotericsoftware.kryo.util.DefaultClassResolver;
import com.esotericsoftware.kryo.util.DefaultStreamFactory;
import com.esotericsoftware.kryo.util.IdentityMap;
import com.esotericsoftware.kryo.util.IntArray;
import com.esotericsoftware.kryo.util.MapReferenceResolver;
import com.esotericsoftware.kryo.util.ObjectMap;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.minlog.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Currency;
import java.util.Date;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.TreeSet;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.strategy.InstantiatorStrategy;

public class Kryo {
    public static final byte NOT_NULL = 1;
    private static final int NO_REF = -2;
    public static final byte NULL = 0;
    private static final int REF = -1;
    private boolean autoReset;
    private ClassLoader classLoader;
    private final ClassResolver classResolver;
    private ObjectMap context;
    private int copyDepth;
    private boolean copyReferences;
    private boolean copyShallow;
    private SerializerFactory defaultSerializer;
    private final ArrayList<DefaultSerializerEntry> defaultSerializers;
    private int depth;
    private FieldSerializerConfig fieldSerializerConfig;
    private GenericsResolver genericsResolver;
    private ObjectMap graphContext;
    private final int lowPriorityDefaultSerializerCount;
    private int maxDepth;
    private Object needsCopyReference;
    private int nextRegisterID;
    private IdentityMap originalToCopy;
    private Object readObject;
    private final IntArray readReferenceIds;
    private ReferenceResolver referenceResolver;
    private boolean references;
    private boolean registrationRequired;
    private InstantiatorStrategy strategy;
    private StreamFactory streamFactory;
    private TaggedFieldSerializerConfig taggedFieldSerializerConfig;
    private volatile Thread thread;
    private boolean warnUnregisteredClasses;

    public static class DefaultInstantiatorStrategy implements InstantiatorStrategy {
        private InstantiatorStrategy fallbackStrategy;

        public DefaultInstantiatorStrategy() {
        }

        public DefaultInstantiatorStrategy(InstantiatorStrategy instantiatorStrategy) {
            this.fallbackStrategy = instantiatorStrategy;
        }

        public void setFallbackInstantiatorStrategy(InstantiatorStrategy instantiatorStrategy) {
            this.fallbackStrategy = instantiatorStrategy;
        }

        public InstantiatorStrategy getFallbackInstantiatorStrategy() {
            return this.fallbackStrategy;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(2:18|19) */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            r0 = r4.getDeclaredConstructor(null);
            r0.setAccessible(true);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0042, code lost:
            r0 = r3.fallbackStrategy;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x0045, code lost:
            if (r0 == null) goto L_0x0047;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x004b, code lost:
            if (r4.isMemberClass() == false) goto L_0x0073;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0058, code lost:
            r1 = new java.lang.StringBuilder();
            r1.append("Class cannot be created (non-static member class): ");
            r1.append(com.esotericsoftware.kryo.util.Util.className(r4));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x0072, code lost:
            throw new com.esotericsoftware.kryo.KryoException(r1.toString());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x0073, code lost:
            r1 = new java.lang.StringBuilder();
            r1.append("Class cannot be created (missing no-arg constructor): ");
            r1.append(com.esotericsoftware.kryo.util.Util.className(r4));
            r0 = new java.lang.StringBuilder(r1.toString());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0097, code lost:
            if (r4.getSimpleName().equals("") != false) goto L_0x0099;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0099, code lost:
            r0.append("\n\tThis is an anonymous class, which is not serializable by default in Kryo. Possible solutions: ");
            r0.append("1. Remove uses of anonymous classes, including double brace initialization, from the containing ");
            r0.append("class. This is the safest solution, as anonymous classes don't have predictable names for serialization.");
            r0.append("\n\t2. Register a FieldSerializer for the containing class and call ");
            r0.append("FieldSerializer#setIgnoreSyntheticFields(false) on it. This is not safe but may be sufficient temporarily. ");
            r0.append("Use at your own risk.");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:0x00c0, code lost:
            throw new com.esotericsoftware.kryo.KryoException(r0.toString());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c5, code lost:
            return r0.newInstantiatorOf(r4);
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x0033 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public org.objenesis.instantiator.ObjectInstantiator newInstantiatorOf(final java.lang.Class r4) {
            /*
                r3 = this;
                boolean r0 = com.esotericsoftware.kryo.util.Util.IS_ANDROID
                r1 = 1
                if (r0 != 0) goto L_0x002a
                java.lang.Class r0 = r4.getEnclosingClass()
                if (r0 == 0) goto L_0x001d
                boolean r0 = r4.isMemberClass()
                if (r0 == 0) goto L_0x001d
                int r0 = r4.getModifiers()
                boolean r0 = java.lang.reflect.Modifier.isStatic(r0)
                if (r0 != 0) goto L_0x001d
                r0 = 1
                goto L_0x001e
            L_0x001d:
                r0 = 0
            L_0x001e:
                if (r0 != 0) goto L_0x002a
                com.esotericsoftware.reflectasm.ConstructorAccess r0 = com.esotericsoftware.reflectasm.ConstructorAccess.get(r4)     // Catch:{ Exception -> 0x002a }
                com.esotericsoftware.kryo.Kryo$DefaultInstantiatorStrategy$1 r2 = new com.esotericsoftware.kryo.Kryo$DefaultInstantiatorStrategy$1     // Catch:{ Exception -> 0x002a }
                r2.<init>(r0, r4)     // Catch:{ Exception -> 0x002a }
                return r2
            L_0x002a:
                r0 = 0
                r2 = r0
                java.lang.Class[] r2 = (java.lang.Class[]) r2     // Catch:{ Exception -> 0x0033 }
                java.lang.reflect.Constructor r0 = r4.getConstructor(r2)     // Catch:{ Exception -> 0x0033 }
                goto L_0x003c
            L_0x0033:
                java.lang.Class[] r0 = (java.lang.Class[]) r0     // Catch:{ Exception -> 0x0042 }
                java.lang.reflect.Constructor r0 = r4.getDeclaredConstructor(r0)     // Catch:{ Exception -> 0x0042 }
                r0.setAccessible(r1)     // Catch:{ Exception -> 0x0042 }
            L_0x003c:
                com.esotericsoftware.kryo.Kryo$DefaultInstantiatorStrategy$2 r1 = new com.esotericsoftware.kryo.Kryo$DefaultInstantiatorStrategy$2     // Catch:{ Exception -> 0x0042 }
                r1.<init>(r0, r4)     // Catch:{ Exception -> 0x0042 }
                return r1
            L_0x0042:
                org.objenesis.strategy.InstantiatorStrategy r0 = r3.fallbackStrategy
                if (r0 != 0) goto L_0x00c1
                boolean r0 = r4.isMemberClass()
                if (r0 == 0) goto L_0x0073
                int r0 = r4.getModifiers()
                boolean r0 = java.lang.reflect.Modifier.isStatic(r0)
                if (r0 == 0) goto L_0x0058
                goto L_0x0073
            L_0x0058:
                com.esotericsoftware.kryo.KryoException r0 = new com.esotericsoftware.kryo.KryoException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Class cannot be created (non-static member class): "
                r1.append(r2)
                java.lang.String r4 = com.esotericsoftware.kryo.util.Util.className(r4)
                r1.append(r4)
                java.lang.String r4 = r1.toString()
                r0.<init>(r4)
                throw r0
            L_0x0073:
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Class cannot be created (missing no-arg constructor): "
                r1.append(r2)
                java.lang.String r2 = com.esotericsoftware.kryo.util.Util.className(r4)
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                java.lang.String r4 = r4.getSimpleName()
                java.lang.String r1 = ""
                boolean r4 = r4.equals(r1)
                if (r4 == 0) goto L_0x00b7
                java.lang.String r4 = "\n\tThis is an anonymous class, which is not serializable by default in Kryo. Possible solutions: "
                r0.append(r4)
                java.lang.String r4 = "1. Remove uses of anonymous classes, including double brace initialization, from the containing "
                r0.append(r4)
                java.lang.String r4 = "class. This is the safest solution, as anonymous classes don't have predictable names for serialization."
                r0.append(r4)
                java.lang.String r4 = "\n\t2. Register a FieldSerializer for the containing class and call "
                r0.append(r4)
                java.lang.String r4 = "FieldSerializer#setIgnoreSyntheticFields(false) on it. This is not safe but may be sufficient temporarily. "
                r0.append(r4)
                java.lang.String r4 = "Use at your own risk."
                r0.append(r4)
            L_0x00b7:
                com.esotericsoftware.kryo.KryoException r4 = new com.esotericsoftware.kryo.KryoException
                java.lang.String r0 = r0.toString()
                r4.<init>(r0)
                throw r4
            L_0x00c1:
                org.objenesis.instantiator.ObjectInstantiator r4 = r0.newInstantiatorOf(r4)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy.newInstantiatorOf(java.lang.Class):org.objenesis.instantiator.ObjectInstantiator");
        }
    }

    static final class DefaultSerializerEntry {
        final SerializerFactory serializerFactory;
        final Class type;

        DefaultSerializerEntry(Class cls, SerializerFactory serializerFactory2) {
            this.type = cls;
            this.serializerFactory = serializerFactory2;
        }
    }

    public Kryo() {
        this(new DefaultClassResolver(), new MapReferenceResolver(), new DefaultStreamFactory());
    }

    public Kryo(ReferenceResolver referenceResolver2) {
        this(new DefaultClassResolver(), referenceResolver2, new DefaultStreamFactory());
    }

    public Kryo(ClassResolver classResolver2, ReferenceResolver referenceResolver2) {
        this(classResolver2, referenceResolver2, new DefaultStreamFactory());
    }

    public Kryo(ClassResolver classResolver2, ReferenceResolver referenceResolver2, StreamFactory streamFactory2) {
        this.defaultSerializer = new ReflectionSerializerFactory(FieldSerializer.class);
        this.defaultSerializers = new ArrayList<>(33);
        this.classLoader = getClass().getClassLoader();
        this.strategy = new DefaultInstantiatorStrategy();
        this.maxDepth = Integer.MAX_VALUE;
        this.autoReset = true;
        this.readReferenceIds = new IntArray(0);
        this.copyReferences = true;
        this.genericsResolver = new GenericsResolver();
        this.fieldSerializerConfig = new FieldSerializerConfig();
        this.taggedFieldSerializerConfig = new TaggedFieldSerializerConfig();
        if (classResolver2 != null) {
            this.classResolver = classResolver2;
            classResolver2.setKryo(this);
            this.streamFactory = streamFactory2;
            streamFactory2.setKryo(this);
            this.referenceResolver = referenceResolver2;
            if (referenceResolver2 != null) {
                referenceResolver2.setKryo(this);
                this.references = true;
            }
            addDefaultSerializer(byte[].class, ByteArraySerializer.class);
            addDefaultSerializer(char[].class, CharArraySerializer.class);
            addDefaultSerializer(short[].class, ShortArraySerializer.class);
            addDefaultSerializer(int[].class, IntArraySerializer.class);
            addDefaultSerializer(long[].class, LongArraySerializer.class);
            addDefaultSerializer(float[].class, FloatArraySerializer.class);
            addDefaultSerializer(double[].class, DoubleArraySerializer.class);
            addDefaultSerializer(boolean[].class, BooleanArraySerializer.class);
            addDefaultSerializer(String[].class, StringArraySerializer.class);
            addDefaultSerializer(Object[].class, ObjectArraySerializer.class);
            addDefaultSerializer(KryoSerializable.class, KryoSerializableSerializer.class);
            addDefaultSerializer(BigInteger.class, BigIntegerSerializer.class);
            addDefaultSerializer(BigDecimal.class, BigDecimalSerializer.class);
            addDefaultSerializer(Class.class, ClassSerializer.class);
            addDefaultSerializer(Date.class, DateSerializer.class);
            addDefaultSerializer(Enum.class, EnumSerializer.class);
            addDefaultSerializer(EnumSet.class, EnumSetSerializer.class);
            addDefaultSerializer(Currency.class, CurrencySerializer.class);
            addDefaultSerializer(StringBuffer.class, StringBufferSerializer.class);
            addDefaultSerializer(StringBuilder.class, StringBuilderSerializer.class);
            addDefaultSerializer(Collections.EMPTY_LIST.getClass(), CollectionsEmptyListSerializer.class);
            addDefaultSerializer(Collections.EMPTY_MAP.getClass(), CollectionsEmptyMapSerializer.class);
            addDefaultSerializer(Collections.EMPTY_SET.getClass(), CollectionsEmptySetSerializer.class);
            addDefaultSerializer(Collections.singletonList(null).getClass(), CollectionsSingletonListSerializer.class);
            addDefaultSerializer(Collections.singletonMap(null, null).getClass(), CollectionsSingletonMapSerializer.class);
            addDefaultSerializer(Collections.singleton(null).getClass(), CollectionsSingletonSetSerializer.class);
            addDefaultSerializer(TreeSet.class, TreeSetSerializer.class);
            addDefaultSerializer(Collection.class, CollectionSerializer.class);
            addDefaultSerializer(TreeMap.class, TreeMapSerializer.class);
            addDefaultSerializer(Map.class, MapSerializer.class);
            addDefaultSerializer(TimeZone.class, TimeZoneSerializer.class);
            addDefaultSerializer(Calendar.class, CalendarSerializer.class);
            addDefaultSerializer(Locale.class, LocaleSerializer.class);
            addDefaultSerializer(Charset.class, CharsetSerializer.class);
            addDefaultSerializer(URL.class, URLSerializer.class);
            OptionalSerializers.addDefaultSerializers(this);
            TimeSerializers.addDefaultSerializers(this);
            this.lowPriorityDefaultSerializerCount = this.defaultSerializers.size();
            register(Integer.TYPE, (Serializer) new IntSerializer());
            register(String.class, (Serializer) new StringSerializer());
            register(Float.TYPE, (Serializer) new FloatSerializer());
            register(Boolean.TYPE, (Serializer) new BooleanSerializer());
            register(Byte.TYPE, (Serializer) new ByteSerializer());
            register(Character.TYPE, (Serializer) new CharSerializer());
            register(Short.TYPE, (Serializer) new ShortSerializer());
            register(Long.TYPE, (Serializer) new LongSerializer());
            register(Double.TYPE, (Serializer) new DoubleSerializer());
            register(Void.TYPE, (Serializer) new VoidSerializer());
            return;
        }
        throw new IllegalArgumentException("classResolver cannot be null.");
    }

    public void setDefaultSerializer(SerializerFactory serializerFactory) {
        if (serializerFactory != null) {
            this.defaultSerializer = serializerFactory;
            return;
        }
        throw new IllegalArgumentException("serializer cannot be null.");
    }

    public void setDefaultSerializer(Class<? extends Serializer> cls) {
        if (cls != null) {
            this.defaultSerializer = new ReflectionSerializerFactory(cls);
            return;
        }
        throw new IllegalArgumentException("serializer cannot be null.");
    }

    public void addDefaultSerializer(Class cls, Serializer serializer) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (serializer != null) {
            DefaultSerializerEntry defaultSerializerEntry = new DefaultSerializerEntry(cls, new PseudoSerializerFactory(serializer));
            ArrayList<DefaultSerializerEntry> arrayList = this.defaultSerializers;
            arrayList.add(arrayList.size() - this.lowPriorityDefaultSerializerCount, defaultSerializerEntry);
        } else {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
    }

    public void addDefaultSerializer(Class cls, SerializerFactory serializerFactory) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (serializerFactory != null) {
            DefaultSerializerEntry defaultSerializerEntry = new DefaultSerializerEntry(cls, serializerFactory);
            ArrayList<DefaultSerializerEntry> arrayList = this.defaultSerializers;
            arrayList.add(arrayList.size() - this.lowPriorityDefaultSerializerCount, defaultSerializerEntry);
        } else {
            throw new IllegalArgumentException("serializerFactory cannot be null.");
        }
    }

    public void addDefaultSerializer(Class cls, Class<? extends Serializer> cls2) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (cls2 != null) {
            DefaultSerializerEntry defaultSerializerEntry = new DefaultSerializerEntry(cls, new ReflectionSerializerFactory(cls2));
            ArrayList<DefaultSerializerEntry> arrayList = this.defaultSerializers;
            arrayList.add(arrayList.size() - this.lowPriorityDefaultSerializerCount, defaultSerializerEntry);
        } else {
            throw new IllegalArgumentException("serializerClass cannot be null.");
        }
    }

    public Serializer getDefaultSerializer(Class cls) {
        if (cls != null) {
            Serializer defaultSerializerForAnnotatedType = getDefaultSerializerForAnnotatedType(cls);
            if (defaultSerializerForAnnotatedType != null) {
                return defaultSerializerForAnnotatedType;
            }
            int size = this.defaultSerializers.size();
            for (int i = 0; i < size; i++) {
                DefaultSerializerEntry defaultSerializerEntry = (DefaultSerializerEntry) this.defaultSerializers.get(i);
                if (defaultSerializerEntry.type.isAssignableFrom(cls)) {
                    return defaultSerializerEntry.serializerFactory.makeSerializer(this, cls);
                }
            }
            return newDefaultSerializer(cls);
        }
        throw new IllegalArgumentException("type cannot be null.");
    }

    /* access modifiers changed from: protected */
    public Serializer getDefaultSerializerForAnnotatedType(Class cls) {
        if (cls.isAnnotationPresent(DefaultSerializer.class)) {
            return ReflectionSerializerFactory.makeSerializer(this, ((DefaultSerializer) cls.getAnnotation(DefaultSerializer.class)).value(), cls);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public Serializer newDefaultSerializer(Class cls) {
        return this.defaultSerializer.makeSerializer(this, cls);
    }

    public Registration register(Class cls) {
        Registration registration = this.classResolver.getRegistration(cls);
        if (registration != null) {
            return registration;
        }
        return register(cls, getDefaultSerializer(cls));
    }

    public Registration register(Class cls, int i) {
        Registration registration = this.classResolver.getRegistration(cls);
        if (registration != null) {
            return registration;
        }
        return register(cls, getDefaultSerializer(cls), i);
    }

    public Registration register(Class cls, Serializer serializer) {
        Registration registration = this.classResolver.getRegistration(cls);
        if (registration == null) {
            return this.classResolver.register(new Registration(cls, serializer, getNextRegistrationId()));
        }
        registration.setSerializer(serializer);
        return registration;
    }

    public Registration register(Class cls, Serializer serializer, int i) {
        if (i >= 0) {
            return register(new Registration(cls, serializer, i));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("id must be >= 0: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public Registration register(Registration registration) {
        int id = registration.getId();
        if (id >= 0) {
            Registration registration2 = getRegistration(registration.getId());
            if (!(!Log.DEBUG || registration2 == null || registration2.getType() == registration.getType())) {
                StringBuilder sb = new StringBuilder();
                sb.append("An existing registration with a different type already uses ID: ");
                sb.append(registration.getId());
                sb.append("\nExisting registration: ");
                sb.append(registration2);
                sb.append("\nis now overwritten with: ");
                sb.append(registration);
                Log.debug(sb.toString());
            }
            return this.classResolver.register(registration);
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("id must be > 0: ");
        sb2.append(id);
        throw new IllegalArgumentException(sb2.toString());
    }

    public int getNextRegistrationId() {
        while (true) {
            int i = this.nextRegisterID;
            if (i == -2) {
                throw new KryoException("No registration IDs are available.");
            } else if (this.classResolver.getRegistration(i) == null) {
                return this.nextRegisterID;
            } else {
                this.nextRegisterID++;
            }
        }
    }

    public Registration getRegistration(Class cls) {
        if (cls != null) {
            Registration registration = this.classResolver.getRegistration(cls);
            if (registration != null) {
                return registration;
            }
            if (Proxy.isProxyClass(cls)) {
                registration = getRegistration(InvocationHandler.class);
            } else if (!cls.isEnum() && Enum.class.isAssignableFrom(cls)) {
                registration = getRegistration(cls.getEnclosingClass());
            } else if (EnumSet.class.isAssignableFrom(cls)) {
                registration = this.classResolver.getRegistration(EnumSet.class);
            } else if (isClosure(cls)) {
                registration = this.classResolver.getRegistration(Closure.class);
            }
            if (registration != null) {
                return registration;
            }
            if (!this.registrationRequired) {
                if (this.warnUnregisteredClasses) {
                    Log.warn(unregisteredClassMessage(cls));
                }
                return this.classResolver.registerImplicit(cls);
            }
            throw new IllegalArgumentException(unregisteredClassMessage(cls));
        }
        throw new IllegalArgumentException("type cannot be null.");
    }

    /* access modifiers changed from: protected */
    public String unregisteredClassMessage(Class cls) {
        StringBuilder sb = new StringBuilder();
        sb.append("Class is not registered: ");
        sb.append(Util.className(cls));
        sb.append("\nNote: To register this class use: kryo.register(");
        sb.append(Util.className(cls));
        sb.append(".class);");
        return sb.toString();
    }

    public Registration getRegistration(int i) {
        return this.classResolver.getRegistration(i);
    }

    public Serializer getSerializer(Class cls) {
        return getRegistration(cls).getSerializer();
    }

    public Registration writeClass(Output output, Class cls) {
        if (output != null) {
            try {
                Registration writeClass = this.classResolver.writeClass(output, cls);
                return writeClass;
            } finally {
                if (this.depth == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("output cannot be null.");
        }
    }

    public void writeObject(Output output, Object obj) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        } else if (obj != null) {
            beginObject();
            try {
                if (!this.references || !writeReferenceOrNull(output, obj, false)) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Write", obj);
                    }
                    getRegistration(obj.getClass()).getSerializer().write(this, output, obj);
                    int i = this.depth - 1;
                    this.depth = i;
                    if (i == 0 && this.autoReset) {
                        reset();
                    }
                    return;
                }
                getRegistration(obj.getClass()).getSerializer().setGenerics(this, null);
            } finally {
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("object cannot be null.");
        }
    }

    public void writeObject(Output output, Object obj, Serializer serializer) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        } else if (obj == null) {
            throw new IllegalArgumentException("object cannot be null.");
        } else if (serializer != null) {
            beginObject();
            try {
                if (!this.references || !writeReferenceOrNull(output, obj, false)) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Write", obj);
                    }
                    serializer.write(this, output, obj);
                    int i = this.depth - 1;
                    this.depth = i;
                    if (i == 0 && this.autoReset) {
                        reset();
                    }
                    return;
                }
                serializer.setGenerics(this, null);
            } finally {
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
    }

    public void writeObjectOrNull(Output output, Object obj, Class cls) {
        if (output != null) {
            beginObject();
            try {
                Serializer serializer = getRegistration(cls).getSerializer();
                String str = "Write";
                if (this.references) {
                    if (writeReferenceOrNull(output, obj, true)) {
                        serializer.setGenerics(this, null);
                        return;
                    }
                } else if (!serializer.getAcceptsNull()) {
                    if (obj == null) {
                        if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                            Util.log(str, obj);
                        }
                        output.writeByte(0);
                        int i = this.depth - 1;
                        this.depth = i;
                        if (i == 0 && this.autoReset) {
                            reset();
                        }
                        return;
                    }
                    output.writeByte(1);
                }
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log(str, obj);
                }
                serializer.write(this, output, obj);
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
            } finally {
                int i3 = this.depth - 1;
                this.depth = i3;
                if (i3 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("output cannot be null.");
        }
    }

    public void writeObjectOrNull(Output output, Object obj, Serializer serializer) {
        if (output == null) {
            throw new IllegalArgumentException("output cannot be null.");
        } else if (serializer != null) {
            beginObject();
            try {
                String str = "Write";
                if (this.references) {
                    if (writeReferenceOrNull(output, obj, true)) {
                        serializer.setGenerics(this, null);
                        return;
                    }
                } else if (!serializer.getAcceptsNull()) {
                    if (obj == null) {
                        if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                            Util.log(str, null);
                        }
                        output.writeByte(0);
                        int i = this.depth - 1;
                        this.depth = i;
                        if (i == 0 && this.autoReset) {
                            reset();
                        }
                        return;
                    }
                    output.writeByte(1);
                }
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log(str, obj);
                }
                serializer.write(this, output, obj);
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
            } finally {
                int i3 = this.depth - 1;
                this.depth = i3;
                if (i3 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
    }

    public void writeClassAndObject(Output output, Object obj) {
        if (output != null) {
            beginObject();
            if (obj == null) {
                try {
                    writeClass(output, null);
                } finally {
                    int i = this.depth - 1;
                    this.depth = i;
                    if (i == 0 && this.autoReset) {
                        reset();
                    }
                }
            } else {
                Registration writeClass = writeClass(output, obj.getClass());
                if (!this.references || !writeReferenceOrNull(output, obj, false)) {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log("Write", obj);
                    }
                    writeClass.getSerializer().write(this, output, obj);
                    int i2 = this.depth - 1;
                    this.depth = i2;
                    if (i2 == 0 && this.autoReset) {
                        reset();
                    }
                    return;
                }
                writeClass.getSerializer().setGenerics(this, null);
                int i3 = this.depth - 1;
                this.depth = i3;
                if (i3 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("output cannot be null.");
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean writeReferenceOrNull(Output output, Object obj, boolean z) {
        if (obj == null) {
            if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                Util.log("Write", null);
            }
            output.writeVarInt(0, true);
            return true;
        } else if (!this.referenceResolver.useReferences(obj.getClass())) {
            if (z) {
                output.writeVarInt(1, true);
            }
            return false;
        } else {
            int writtenId = this.referenceResolver.getWrittenId(obj);
            String str = ": ";
            String str2 = "kryo";
            if (writtenId != -1) {
                if (Log.DEBUG) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Write object reference ");
                    sb.append(writtenId);
                    sb.append(str);
                    sb.append(Util.string(obj));
                    Log.debug(str2, sb.toString());
                }
                output.writeVarInt(writtenId + 2, true);
                return true;
            }
            int addWrittenObject = this.referenceResolver.addWrittenObject(obj);
            output.writeVarInt(1, true);
            if (Log.TRACE) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Write initial object reference ");
                sb2.append(addWrittenObject);
                sb2.append(str);
                sb2.append(Util.string(obj));
                Log.trace(str2, sb2.toString());
            }
            return false;
        }
    }

    public Registration readClass(Input input) {
        if (input != null) {
            try {
                return this.classResolver.readClass(input);
            } finally {
                if (this.depth == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("input cannot be null.");
        }
    }

    public <T> T readObject(Input input, Class<T> cls) {
        T t;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        } else if (cls != null) {
            beginObject();
            try {
                if (this.references) {
                    int readReferenceOrNull = readReferenceOrNull(input, cls, false);
                    if (readReferenceOrNull == -1) {
                        return this.readObject;
                    }
                    t = getRegistration((Class) cls).getSerializer().read(this, input, cls);
                    if (readReferenceOrNull == this.readReferenceIds.size) {
                        reference(t);
                    }
                } else {
                    t = getRegistration((Class) cls).getSerializer().read(this, input, cls);
                }
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log("Read", t);
                }
                int i = this.depth - 1;
                this.depth = i;
                if (i == 0 && this.autoReset) {
                    reset();
                }
                return t;
            } finally {
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("type cannot be null.");
        }
    }

    public <T> T readObject(Input input, Class<T> cls, Serializer serializer) {
        T t;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        } else if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (serializer != null) {
            beginObject();
            try {
                if (this.references) {
                    int readReferenceOrNull = readReferenceOrNull(input, cls, false);
                    if (readReferenceOrNull == -1) {
                        return this.readObject;
                    }
                    t = serializer.read(this, input, cls);
                    if (readReferenceOrNull == this.readReferenceIds.size) {
                        reference(t);
                    }
                } else {
                    t = serializer.read(this, input, cls);
                }
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log("Read", t);
                }
                int i = this.depth - 1;
                this.depth = i;
                if (i == 0 && this.autoReset) {
                    reset();
                }
                return t;
            } finally {
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
    }

    public <T> T readObjectOrNull(Input input, Class<T> cls) {
        T t;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        } else if (cls != null) {
            beginObject();
            try {
                String str = "Read";
                if (this.references) {
                    int readReferenceOrNull = readReferenceOrNull(input, cls, true);
                    if (readReferenceOrNull == -1) {
                        return this.readObject;
                    }
                    t = getRegistration((Class) cls).getSerializer().read(this, input, cls);
                    if (readReferenceOrNull == this.readReferenceIds.size) {
                        reference(t);
                    }
                } else {
                    Serializer serializer = getRegistration((Class) cls).getSerializer();
                    if (serializer.getAcceptsNull() || input.readByte() != 0) {
                        t = serializer.read(this, input, cls);
                    } else {
                        if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                            Util.log(str, null);
                        }
                        int i = this.depth - 1;
                        this.depth = i;
                        if (i == 0 && this.autoReset) {
                            reset();
                        }
                        return null;
                    }
                }
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log(str, t);
                }
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
                return t;
            } finally {
                int i3 = this.depth - 1;
                this.depth = i3;
                if (i3 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("type cannot be null.");
        }
    }

    public <T> T readObjectOrNull(Input input, Class<T> cls, Serializer serializer) {
        T t;
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null.");
        } else if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (serializer != null) {
            beginObject();
            try {
                String str = "Read";
                if (this.references) {
                    int readReferenceOrNull = readReferenceOrNull(input, cls, true);
                    if (readReferenceOrNull == -1) {
                        return this.readObject;
                    }
                    t = serializer.read(this, input, cls);
                    if (readReferenceOrNull == this.readReferenceIds.size) {
                        reference(t);
                    }
                } else if (serializer.getAcceptsNull() || input.readByte() != 0) {
                    t = serializer.read(this, input, cls);
                } else {
                    if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                        Util.log(str, null);
                    }
                    int i = this.depth - 1;
                    this.depth = i;
                    if (i == 0 && this.autoReset) {
                        reset();
                    }
                    return null;
                }
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log(str, t);
                }
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
                return t;
            } finally {
                int i3 = this.depth - 1;
                this.depth = i3;
                if (i3 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("serializer cannot be null.");
        }
    }

    public Object readClassAndObject(Input input) {
        Object obj;
        if (input != null) {
            beginObject();
            try {
                Registration readClass = readClass(input);
                if (readClass == null) {
                    return null;
                }
                Class type = readClass.getType();
                if (this.references) {
                    readClass.getSerializer().setGenerics(this, null);
                    int readReferenceOrNull = readReferenceOrNull(input, type, false);
                    if (readReferenceOrNull == -1) {
                        Object obj2 = this.readObject;
                        int i = this.depth - 1;
                        this.depth = i;
                        if (i == 0 && this.autoReset) {
                            reset();
                        }
                        return obj2;
                    }
                    obj = readClass.getSerializer().read(this, input, type);
                    if (readReferenceOrNull == this.readReferenceIds.size) {
                        reference(obj);
                    }
                } else {
                    obj = readClass.getSerializer().read(this, input, type);
                }
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log("Read", obj);
                }
                int i2 = this.depth - 1;
                this.depth = i2;
                if (i2 == 0 && this.autoReset) {
                    reset();
                }
                return obj;
            } finally {
                int i3 = this.depth - 1;
                this.depth = i3;
                if (i3 == 0 && this.autoReset) {
                    reset();
                }
            }
        } else {
            throw new IllegalArgumentException("input cannot be null.");
        }
    }

    /* access modifiers changed from: 0000 */
    public int readReferenceOrNull(Input input, Class cls, boolean z) {
        int i;
        if (cls.isPrimitive()) {
            cls = Util.getWrapperClass(cls);
        }
        boolean useReferences = this.referenceResolver.useReferences(cls);
        if (z) {
            i = input.readVarInt(true);
            if (i == 0) {
                if (Log.TRACE || (Log.DEBUG && this.depth == 1)) {
                    Util.log("Read", null);
                }
                this.readObject = null;
                return -1;
            } else if (!useReferences) {
                this.readReferenceIds.add(-2);
                return this.readReferenceIds.size;
            }
        } else if (!useReferences) {
            this.readReferenceIds.add(-2);
            return this.readReferenceIds.size;
        } else {
            i = input.readVarInt(true);
        }
        String str = ": ";
        String str2 = "kryo";
        if (i == 1) {
            int nextReadId = this.referenceResolver.nextReadId(cls);
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Read initial object reference ");
                sb.append(nextReadId);
                sb.append(str);
                sb.append(Util.className(cls));
                Log.trace(str2, sb.toString());
            }
            this.readReferenceIds.add(nextReadId);
            return this.readReferenceIds.size;
        }
        int i2 = i - 2;
        this.readObject = this.referenceResolver.getReadObject(cls, i2);
        if (Log.DEBUG) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Read object reference ");
            sb2.append(i2);
            sb2.append(str);
            sb2.append(Util.string(this.readObject));
            Log.debug(str2, sb2.toString());
        }
        return -1;
    }

    public void reference(Object obj) {
        if (this.copyDepth > 0) {
            Object obj2 = this.needsCopyReference;
            if (obj2 == null) {
                return;
            }
            if (obj != null) {
                this.originalToCopy.put(obj2, obj);
                this.needsCopyReference = null;
                return;
            }
            throw new IllegalArgumentException("object cannot be null.");
        } else if (this.references && obj != null) {
            int pop = this.readReferenceIds.pop();
            if (pop != -2) {
                this.referenceResolver.setReadObject(pop, obj);
            }
        }
    }

    public void reset() {
        this.depth = 0;
        ObjectMap objectMap = this.graphContext;
        if (objectMap != null) {
            objectMap.clear();
        }
        this.classResolver.reset();
        if (this.references) {
            this.referenceResolver.reset();
            this.readObject = null;
        }
        this.copyDepth = 0;
        IdentityMap identityMap = this.originalToCopy;
        if (identityMap != null) {
            identityMap.clear(2048);
        }
        if (Log.TRACE) {
            Log.trace("kryo", "Object graph complete.");
        }
    }

    public <T> T copy(T t) {
        T t2;
        if (t == null) {
            return null;
        }
        if (this.copyShallow) {
            return t;
        }
        this.copyDepth++;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            T t3 = this.originalToCopy.get(t);
            if (t3 != null) {
                return t3;
            }
            if (this.copyReferences) {
                this.needsCopyReference = t;
            }
            if (t instanceof KryoCopyable) {
                t2 = ((KryoCopyable) t).copy(this);
            } else {
                t2 = getSerializer(t.getClass()).copy(this, t);
            }
            if (this.needsCopyReference != null) {
                reference(t2);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Copy", t2);
            }
            int i = this.copyDepth - 1;
            this.copyDepth = i;
            if (i == 0) {
                reset();
            }
            return t2;
        } finally {
            int i2 = this.copyDepth - 1;
            this.copyDepth = i2;
            if (i2 == 0) {
                reset();
            }
        }
    }

    public <T> T copy(T t, Serializer serializer) {
        T t2;
        if (t == null) {
            return null;
        }
        if (this.copyShallow) {
            return t;
        }
        this.copyDepth++;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            T t3 = this.originalToCopy.get(t);
            if (t3 != null) {
                return t3;
            }
            if (this.copyReferences) {
                this.needsCopyReference = t;
            }
            if (t instanceof KryoCopyable) {
                t2 = ((KryoCopyable) t).copy(this);
            } else {
                t2 = serializer.copy(this, t);
            }
            if (this.needsCopyReference != null) {
                reference(t2);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Copy", t2);
            }
            int i = this.copyDepth - 1;
            this.copyDepth = i;
            if (i == 0) {
                reset();
            }
            return t2;
        } finally {
            int i2 = this.copyDepth - 1;
            this.copyDepth = i2;
            if (i2 == 0) {
                reset();
            }
        }
    }

    public <T> T copyShallow(T t) {
        T t2;
        if (t == null) {
            return null;
        }
        this.copyDepth++;
        this.copyShallow = true;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            T t3 = this.originalToCopy.get(t);
            if (t3 != null) {
                return t3;
            }
            if (this.copyReferences) {
                this.needsCopyReference = t;
            }
            if (t instanceof KryoCopyable) {
                t2 = ((KryoCopyable) t).copy(this);
            } else {
                t2 = getSerializer(t.getClass()).copy(this, t);
            }
            if (this.needsCopyReference != null) {
                reference(t2);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Shallow copy", t2);
            }
            this.copyShallow = false;
            int i = this.copyDepth - 1;
            this.copyDepth = i;
            if (i == 0) {
                reset();
            }
            return t2;
        } finally {
            this.copyShallow = false;
            int i2 = this.copyDepth - 1;
            this.copyDepth = i2;
            if (i2 == 0) {
                reset();
            }
        }
    }

    public <T> T copyShallow(T t, Serializer serializer) {
        T t2;
        if (t == null) {
            return null;
        }
        this.copyDepth++;
        this.copyShallow = true;
        try {
            if (this.originalToCopy == null) {
                this.originalToCopy = new IdentityMap();
            }
            T t3 = this.originalToCopy.get(t);
            if (t3 != null) {
                return t3;
            }
            if (this.copyReferences) {
                this.needsCopyReference = t;
            }
            if (t instanceof KryoCopyable) {
                t2 = ((KryoCopyable) t).copy(this);
            } else {
                t2 = serializer.copy(this, t);
            }
            if (this.needsCopyReference != null) {
                reference(t2);
            }
            if (Log.TRACE || (Log.DEBUG && this.copyDepth == 1)) {
                Util.log("Shallow copy", t2);
            }
            this.copyShallow = false;
            int i = this.copyDepth - 1;
            this.copyDepth = i;
            if (i == 0) {
                reset();
            }
            return t2;
        } finally {
            this.copyShallow = false;
            int i2 = this.copyDepth - 1;
            this.copyDepth = i2;
            if (i2 == 0) {
                reset();
            }
        }
    }

    private void beginObject() {
        if (Log.DEBUG) {
            if (this.depth == 0) {
                this.thread = Thread.currentThread();
            } else if (this.thread != Thread.currentThread()) {
                throw new ConcurrentModificationException("Kryo must not be accessed concurrently by multiple threads.");
            }
        }
        int i = this.depth;
        if (i != this.maxDepth) {
            this.depth = i + 1;
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Max depth exceeded: ");
        sb.append(this.depth);
        throw new KryoException(sb.toString());
    }

    public ClassResolver getClassResolver() {
        return this.classResolver;
    }

    public ReferenceResolver getReferenceResolver() {
        return this.referenceResolver;
    }

    public void setClassLoader(ClassLoader classLoader2) {
        if (classLoader2 != null) {
            this.classLoader = classLoader2;
            return;
        }
        throw new IllegalArgumentException("classLoader cannot be null.");
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public void setRegistrationRequired(boolean z) {
        this.registrationRequired = z;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Registration required: ");
            sb.append(z);
            Log.trace("kryo", sb.toString());
        }
    }

    public boolean isRegistrationRequired() {
        return this.registrationRequired;
    }

    public void setWarnUnregisteredClasses(boolean z) {
        this.warnUnregisteredClasses = z;
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Warn unregistered classes: ");
            sb.append(z);
            Log.trace("kryo", sb.toString());
        }
    }

    public boolean isWarnUnregisteredClasses() {
        return this.warnUnregisteredClasses;
    }

    public boolean setReferences(boolean z) {
        if (z == this.references) {
            return z;
        }
        this.references = z;
        if (z && this.referenceResolver == null) {
            this.referenceResolver = new MapReferenceResolver();
        }
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("References: ");
            sb.append(z);
            Log.trace("kryo", sb.toString());
        }
        return !z;
    }

    public void setCopyReferences(boolean z) {
        this.copyReferences = z;
    }

    public FieldSerializerConfig getFieldSerializerConfig() {
        return this.fieldSerializerConfig;
    }

    public TaggedFieldSerializerConfig getTaggedFieldSerializerConfig() {
        return this.taggedFieldSerializerConfig;
    }

    public void setReferenceResolver(ReferenceResolver referenceResolver2) {
        if (referenceResolver2 != null) {
            this.references = true;
            this.referenceResolver = referenceResolver2;
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Reference resolver: ");
                sb.append(referenceResolver2.getClass().getName());
                Log.trace("kryo", sb.toString());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("referenceResolver cannot be null.");
    }

    public boolean getReferences() {
        return this.references;
    }

    public void setInstantiatorStrategy(InstantiatorStrategy instantiatorStrategy) {
        this.strategy = instantiatorStrategy;
    }

    public InstantiatorStrategy getInstantiatorStrategy() {
        return this.strategy;
    }

    /* access modifiers changed from: protected */
    public ObjectInstantiator newInstantiator(Class cls) {
        return this.strategy.newInstantiatorOf(cls);
    }

    public <T> T newInstance(Class<T> cls) {
        Registration registration = getRegistration((Class) cls);
        ObjectInstantiator instantiator = registration.getInstantiator();
        if (instantiator == null) {
            instantiator = newInstantiator(cls);
            registration.setInstantiator(instantiator);
        }
        return instantiator.newInstance();
    }

    public ObjectMap getContext() {
        if (this.context == null) {
            this.context = new ObjectMap();
        }
        return this.context;
    }

    public ObjectMap getGraphContext() {
        if (this.graphContext == null) {
            this.graphContext = new ObjectMap();
        }
        return this.graphContext;
    }

    public int getDepth() {
        return this.depth;
    }

    public IdentityMap getOriginalToCopyMap() {
        return this.originalToCopy;
    }

    public void setAutoReset(boolean z) {
        this.autoReset = z;
    }

    public void setMaxDepth(int i) {
        if (i > 0) {
            this.maxDepth = i;
            return;
        }
        throw new IllegalArgumentException("maxDepth must be > 0.");
    }

    public boolean isFinal(Class cls) {
        if (cls == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (cls.isArray()) {
            return Modifier.isFinal(Util.getElementClass(cls).getModifiers());
        } else {
            return Modifier.isFinal(cls.getModifiers());
        }
    }

    /* access modifiers changed from: protected */
    public boolean isClosure(Class cls) {
        if (cls != null) {
            return cls.getName().indexOf(47) >= 0;
        }
        throw new IllegalArgumentException("type cannot be null.");
    }

    public GenericsResolver getGenericsResolver() {
        return this.genericsResolver;
    }

    public StreamFactory getStreamFactory() {
        return this.streamFactory;
    }

    public void setStreamFactory(StreamFactory streamFactory2) {
        this.streamFactory = streamFactory2;
    }

    @Deprecated
    public void setAsmEnabled(boolean z) {
        this.fieldSerializerConfig.setUseAsm(z);
    }

    @Deprecated
    public boolean getAsmEnabled() {
        return this.fieldSerializerConfig.isUseAsm();
    }
}
