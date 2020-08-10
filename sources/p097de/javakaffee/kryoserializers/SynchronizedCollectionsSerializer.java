package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/* renamed from: de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer */
public class SynchronizedCollectionsSerializer extends Serializer<Object> {
    /* access modifiers changed from: private */
    public static final Field SOURCE_COLLECTION_FIELD;
    /* access modifiers changed from: private */
    public static final Field SOURCE_MAP_FIELD;

    /* renamed from: de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer$SynchronizedCollection */
    private enum SynchronizedCollection {
        COLLECTION(Collections.synchronizedCollection(Arrays.asList(new String[]{""})).getClass(), SynchronizedCollectionsSerializer.SOURCE_COLLECTION_FIELD) {
            public Object create(Object obj) {
                return Collections.synchronizedCollection((Collection) obj);
            }
        },
        RANDOM_ACCESS_LIST(Collections.synchronizedList(new ArrayList()).getClass(), SynchronizedCollectionsSerializer.SOURCE_COLLECTION_FIELD) {
            public Object create(Object obj) {
                return Collections.synchronizedList((List) obj);
            }
        },
        LIST(Collections.synchronizedList(new LinkedList()).getClass(), SynchronizedCollectionsSerializer.SOURCE_COLLECTION_FIELD) {
            public Object create(Object obj) {
                return Collections.synchronizedList((List) obj);
            }
        },
        SET(Collections.synchronizedSet(new HashSet()).getClass(), SynchronizedCollectionsSerializer.SOURCE_COLLECTION_FIELD) {
            public Object create(Object obj) {
                return Collections.synchronizedSet((Set) obj);
            }
        },
        SORTED_SET(Collections.synchronizedSortedSet(new TreeSet()).getClass(), SynchronizedCollectionsSerializer.SOURCE_COLLECTION_FIELD) {
            public Object create(Object obj) {
                return Collections.synchronizedSortedSet((SortedSet) obj);
            }
        },
        MAP(Collections.synchronizedMap(new HashMap()).getClass(), SynchronizedCollectionsSerializer.SOURCE_MAP_FIELD) {
            public Object create(Object obj) {
                return Collections.synchronizedMap((Map) obj);
            }
        },
        SORTED_MAP(Collections.synchronizedSortedMap(new TreeMap()).getClass(), SynchronizedCollectionsSerializer.SOURCE_MAP_FIELD) {
            public Object create(Object obj) {
                return Collections.synchronizedSortedMap((SortedMap) obj);
            }
        };
        
        /* access modifiers changed from: private */
        public final Field sourceCollectionField;
        /* access modifiers changed from: private */
        public final Class<?> type;

        public abstract Object create(Object obj);

        private SynchronizedCollection(Class<?> cls, Field field) {
            this.type = cls;
            this.sourceCollectionField = field;
        }

        static SynchronizedCollection valueOfType(Class<?> cls) {
            SynchronizedCollection[] values;
            for (SynchronizedCollection synchronizedCollection : values()) {
                if (synchronizedCollection.type.equals(cls)) {
                    return synchronizedCollection;
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("The type ");
            sb.append(cls);
            sb.append(" is not supported.");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    static {
        try {
            SOURCE_COLLECTION_FIELD = Class.forName("java.util.Collections$SynchronizedCollection").getDeclaredField("c");
            SOURCE_COLLECTION_FIELD.setAccessible(true);
            SOURCE_MAP_FIELD = Class.forName("java.util.Collections$SynchronizedMap").getDeclaredField("m");
            SOURCE_MAP_FIELD.setAccessible(true);
        } catch (Exception e) {
            throw new RuntimeException("Could not access source collection field in java.util.Collections$SynchronizedCollection.", e);
        }
    }

    public Object read(Kryo kryo, Input input, Class<Object> cls) {
        return SynchronizedCollection.values()[input.readInt(true)].create(kryo.readClassAndObject(input));
    }

    public void write(Kryo kryo, Output output, Object obj) {
        try {
            SynchronizedCollection valueOfType = SynchronizedCollection.valueOfType(obj.getClass());
            output.writeInt(valueOfType.ordinal(), true);
            kryo.writeClassAndObject(output, valueOfType.sourceCollectionField.get(obj));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public Object copy(Kryo kryo, Object obj) {
        try {
            SynchronizedCollection valueOfType = SynchronizedCollection.valueOfType(obj.getClass());
            return valueOfType.create(kryo.copy(valueOfType.sourceCollectionField.get(obj)));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static void registerSerializers(Kryo kryo) {
        SynchronizedCollectionsSerializer synchronizedCollectionsSerializer = new SynchronizedCollectionsSerializer();
        SynchronizedCollection.values();
        for (SynchronizedCollection access$400 : SynchronizedCollection.values()) {
            kryo.register(access$400.type, (Serializer) synchronizedCollectionsSerializer);
        }
    }
}
