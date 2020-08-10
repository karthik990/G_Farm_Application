package p097de.javakaffee.kryoserializers.guava;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.util.NavigableSet;
import java.util.TreeSet;

/* renamed from: de.javakaffee.kryoserializers.guava.UnmodifiableNavigableSetSerializer */
public class UnmodifiableNavigableSetSerializer extends Serializer<NavigableSet<?>> {
    Field delegate;

    public UnmodifiableNavigableSetSerializer() {
        String str = "Issues reflectively writing UnmodifiableNavigableSet";
        super(false);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(Sets.class.getCanonicalName());
            sb.append("$UnmodifiableNavigableSet");
            this.delegate = Class.forName(sb.toString()).getDeclaredField("delegate");
            this.delegate.setAccessible(true);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(str, e);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException(str, e2);
        } catch (SecurityException e3) {
            throw new RuntimeException(str, e3);
        } catch (NoSuchFieldException e4) {
            throw new RuntimeException(str, e4);
        }
    }

    /* access modifiers changed from: protected */
    public Object getDelegateFromUnmodifiableNavigableSet(NavigableSet<?> navigableSet) {
        String str = "Issues reflectively writing UnmodifiableNavigableSet";
        try {
            return this.delegate.get(navigableSet);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(str, e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(str, e2);
        }
    }

    public void write(Kryo kryo, Output output, NavigableSet<?> navigableSet) {
        kryo.writeClassAndObject(output, getDelegateFromUnmodifiableNavigableSet(navigableSet));
    }

    public NavigableSet<?> read(Kryo kryo, Input input, Class<NavigableSet<?>> cls) {
        return Sets.unmodifiableNavigableSet((NavigableSet) kryo.readClassAndObject(input));
    }

    public NavigableSet<?> copy(Kryo kryo, NavigableSet<?> navigableSet) {
        return Sets.unmodifiableNavigableSet((NavigableSet) kryo.copy(getDelegateFromUnmodifiableNavigableSet(navigableSet)));
    }

    public static void registerSerializers(Kryo kryo) {
        kryo.register(Sets.unmodifiableNavigableSet(new TreeSet()).getClass(), (Serializer) new UnmodifiableNavigableSetSerializer());
    }
}
