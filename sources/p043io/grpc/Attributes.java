package p043io.grpc;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

/* renamed from: io.grpc.Attributes */
public final class Attributes {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Attributes EMPTY = new Attributes(Collections.emptyMap());
    /* access modifiers changed from: private */
    public final Map<Key<?>, Object> data;

    /* renamed from: io.grpc.Attributes$Builder */
    public static final class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Attributes base;
        private Map<Key<?>, Object> newdata;

        static {
            Class<Attributes> cls = Attributes.class;
        }

        private Builder(Attributes attributes) {
            this.base = attributes;
        }

        private Map<Key<?>, Object> data(int i) {
            if (this.newdata == null) {
                this.newdata = new IdentityHashMap(i);
            }
            return this.newdata;
        }

        public <T> Builder set(Key<T> key, T t) {
            data(1).put(key, t);
            return this;
        }

        public <T> Builder setAll(Attributes attributes) {
            data(attributes.data.size()).putAll(attributes.data);
            return this;
        }

        public Attributes build() {
            if (this.newdata != null) {
                for (Entry entry : this.base.data.entrySet()) {
                    if (!this.newdata.containsKey(entry.getKey())) {
                        this.newdata.put(entry.getKey(), entry.getValue());
                    }
                }
                this.base = new Attributes(this.newdata);
                this.newdata = null;
            }
            return this.base;
        }
    }

    /* renamed from: io.grpc.Attributes$Key */
    public static final class Key<T> {
        private final String debugString;

        private Key(String str) {
            this.debugString = str;
        }

        public String toString() {
            return this.debugString;
        }

        @Deprecated
        /* renamed from: of */
        public static <T> Key<T> m3990of(String str) {
            return new Key<>(str);
        }

        public static <T> Key<T> create(String str) {
            return new Key<>(str);
        }
    }

    private Attributes(Map<Key<?>, Object> map) {
        this.data = map;
    }

    @Nullable
    public <T> T get(Key<T> key) {
        return this.data.get(key);
    }

    @Deprecated
    public Set<Key<?>> keys() {
        return Collections.unmodifiableSet(this.data.keySet());
    }

    /* access modifiers changed from: 0000 */
    public Set<Key<?>> keysForTest() {
        return Collections.unmodifiableSet(this.data.keySet());
    }

    @Deprecated
    public static Builder newBuilder(Attributes attributes) {
        Preconditions.checkNotNull(attributes, "base");
        return new Builder();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder();
    }

    public String toString() {
        return this.data.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Attributes attributes = (Attributes) obj;
        if (this.data.size() != attributes.data.size()) {
            return false;
        }
        for (Entry entry : this.data.entrySet()) {
            if (!attributes.data.containsKey(entry.getKey())) {
                return false;
            }
            if (!Objects.equal(entry.getValue(), attributes.data.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        for (Entry entry : this.data.entrySet()) {
            i += Objects.hashCode(entry.getKey(), entry.getValue());
        }
        return i;
    }
}
