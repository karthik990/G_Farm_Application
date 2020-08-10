package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.factories.SerializerFactory;
import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.minlog.Log;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/* renamed from: de.javakaffee.kryoserializers.FieldAnnotationAwareSerializer */
public class FieldAnnotationAwareSerializer<T> extends FieldSerializer<T> {
    private final boolean disregarding;
    private final Set<Class<? extends Annotation>> marked;

    /* renamed from: de.javakaffee.kryoserializers.FieldAnnotationAwareSerializer$Factory */
    public static class Factory implements SerializerFactory {
        private final boolean disregarding;
        private final Collection<Class<? extends Annotation>> marked;

        public Factory(Collection<Class<? extends Annotation>> collection, boolean z) {
            this.marked = collection;
            this.disregarding = z;
        }

        public Serializer<?> makeSerializer(Kryo kryo, Class<?> cls) {
            return new FieldAnnotationAwareSerializer(kryo, cls, this.marked, this.disregarding);
        }
    }

    public FieldAnnotationAwareSerializer(Kryo kryo, Class<?> cls, Collection<Class<? extends Annotation>> collection, boolean z) {
        super(kryo, cls);
        this.disregarding = z;
        this.marked = new HashSet(collection);
        rebuildCachedFields();
    }

    /* access modifiers changed from: protected */
    public void rebuildCachedFields() {
        if (this.marked != null) {
            super.rebuildCachedFields();
            removeFields();
        }
    }

    private void removeFields() {
        CachedField[] fields;
        for (CachedField cachedField : getFields()) {
            Field field = cachedField.getField();
            if (isRemove(field)) {
                if (Log.TRACE) {
                    Object[] objArr = new Object[2];
                    objArr[0] = this.disregarding ? "without" : "with";
                    objArr[1] = cachedField;
                    Log.trace("kryo", String.format("Ignoring field %s tag: %s", objArr));
                }
                super.removeField(field.getName());
            }
        }
    }

    private boolean isRemove(Field field) {
        return (!isMarked(field)) ^ this.disregarding;
    }

    private boolean isMarked(Field field) {
        for (Annotation annotationType : field.getAnnotations()) {
            if (this.marked.contains(annotationType.annotationType())) {
                return true;
            }
        }
        return false;
    }

    public boolean addAnnotation(Class<? extends Annotation> cls) {
        if (!this.disregarding || !this.marked.add(cls)) {
            return false;
        }
        initializeCachedFields();
        return true;
    }

    public boolean removeAnnotation(Class<? extends Annotation> cls) {
        if (this.disregarding || !this.marked.remove(cls)) {
            return false;
        }
        initializeCachedFields();
        return true;
    }
}
