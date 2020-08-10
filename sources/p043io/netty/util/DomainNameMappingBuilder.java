package p043io.netty.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.objectweb.asm.signature.SignatureVisitor;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.util.DomainNameMappingBuilder */
public final class DomainNameMappingBuilder<V> {
    private final V defaultValue;
    private final Map<String, V> map;

    /* renamed from: io.netty.util.DomainNameMappingBuilder$ImmutableDomainNameMapping */
    private static final class ImmutableDomainNameMapping<V> extends DomainNameMapping<V> {
        private static final int REPR_CONST_PART_LENGTH = 46;
        private static final String REPR_HEADER = "ImmutableDomainNameMapping(default: ";
        private static final String REPR_MAP_CLOSING = "})";
        private static final String REPR_MAP_OPENING = ", map: {";
        private final String[] domainNamePatterns;
        private final Map<String, V> map;
        private final V[] values;

        private ImmutableDomainNameMapping(V v, Map<String, V> map2) {
            super(null, v);
            Set<Entry> entrySet = map2.entrySet();
            int size = entrySet.size();
            this.domainNamePatterns = new String[size];
            this.values = (Object[]) new Object[size];
            LinkedHashMap linkedHashMap = new LinkedHashMap(map2.size());
            int i = 0;
            for (Entry entry : entrySet) {
                String normalizeHostname = normalizeHostname((String) entry.getKey());
                V value = entry.getValue();
                this.domainNamePatterns[i] = normalizeHostname;
                this.values[i] = value;
                linkedHashMap.put(normalizeHostname, value);
                i++;
            }
            this.map = Collections.unmodifiableMap(linkedHashMap);
        }

        @Deprecated
        public DomainNameMapping<V> add(String str, V v) {
            throw new UnsupportedOperationException("Immutable DomainNameMapping does not support modification after initial creation");
        }

        public V map(String str) {
            if (str != null) {
                String normalizeHostname = normalizeHostname(str);
                int length = this.domainNamePatterns.length;
                for (int i = 0; i < length; i++) {
                    if (matches(this.domainNamePatterns[i], normalizeHostname)) {
                        return this.values[i];
                    }
                }
            }
            return this.defaultValue;
        }

        public Map<String, V> asMap() {
            return this.map;
        }

        public String toString() {
            String obj = this.defaultValue.toString();
            String[] strArr = this.domainNamePatterns;
            int length = strArr.length;
            String str = REPR_MAP_CLOSING;
            String str2 = REPR_MAP_OPENING;
            String str3 = REPR_HEADER;
            if (length == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append(obj);
                sb.append(str2);
                sb.append(str);
                return sb.toString();
            }
            String str4 = strArr[0];
            String obj2 = this.values[0].toString();
            StringBuilder sb2 = new StringBuilder(estimateBufferSize(obj.length(), length, str4.length() + obj2.length() + 3));
            sb2.append(str3);
            sb2.append(obj);
            sb2.append(str2);
            appendMapping(sb2, str4, obj2);
            for (int i = 1; i < length; i++) {
                sb2.append(", ");
                appendMapping(sb2, i);
            }
            sb2.append(str);
            return sb2.toString();
        }

        private static int estimateBufferSize(int i, int i2, int i3) {
            int i4 = REPR_CONST_PART_LENGTH + i;
            double d = (double) (i3 * i2);
            Double.isNaN(d);
            return i4 + ((int) (d * 1.1d));
        }

        private StringBuilder appendMapping(StringBuilder sb, int i) {
            return appendMapping(sb, this.domainNamePatterns[i], this.values[i].toString());
        }

        private static StringBuilder appendMapping(StringBuilder sb, String str, String str2) {
            sb.append(str);
            sb.append(SignatureVisitor.INSTANCEOF);
            sb.append(str2);
            return sb;
        }
    }

    public DomainNameMappingBuilder(V v) {
        this(4, v);
    }

    public DomainNameMappingBuilder(int i, V v) {
        this.defaultValue = ObjectUtil.checkNotNull(v, "defaultValue");
        this.map = new LinkedHashMap(i);
    }

    public DomainNameMappingBuilder<V> add(String str, V v) {
        this.map.put(ObjectUtil.checkNotNull(str, "hostname"), ObjectUtil.checkNotNull(v, "output"));
        return this;
    }

    public DomainNameMapping<V> build() {
        return new ImmutableDomainNameMapping(this.defaultValue, this.map);
    }
}
