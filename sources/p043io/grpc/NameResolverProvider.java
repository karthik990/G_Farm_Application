package p043io.grpc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import p043io.grpc.Attributes.Key;
import p043io.grpc.NameResolver.Factory;
import p043io.grpc.ServiceProviders.PriorityAccessor;

/* renamed from: io.grpc.NameResolverProvider */
public abstract class NameResolverProvider extends Factory {
    static final Iterable<Class<?>> HARDCODED_CLASSES = getHardCodedClasses();
    public static final Key<Integer> PARAMS_DEFAULT_PORT = Factory.PARAMS_DEFAULT_PORT;
    private static final Factory factory = new NameResolverFactory(providers);
    private static final Logger logger;
    private static final List<NameResolverProvider> providers;

    /* renamed from: io.grpc.NameResolverProvider$NameResolverFactory */
    private static final class NameResolverFactory extends Factory {
        private final List<NameResolverProvider> providers;

        NameResolverFactory(List<NameResolverProvider> list) {
            this.providers = Collections.unmodifiableList(new ArrayList(list));
        }

        @Nullable
        public NameResolver newNameResolver(URI uri, Attributes attributes) {
            checkForProviders();
            for (NameResolverProvider newNameResolver : this.providers) {
                NameResolver newNameResolver2 = newNameResolver.newNameResolver(uri, attributes);
                if (newNameResolver2 != null) {
                    return newNameResolver2;
                }
            }
            return null;
        }

        public String getDefaultScheme() {
            checkForProviders();
            return ((NameResolverProvider) this.providers.get(0)).getDefaultScheme();
        }

        private void checkForProviders() {
            if (this.providers.isEmpty()) {
                throw new RuntimeException("No NameResolverProviders found via ServiceLoader, including for DNS. This is probably due to a broken build. If using ProGuard, check your configuration");
            }
        }
    }

    /* renamed from: io.grpc.NameResolverProvider$NameResolverPriorityAccessor */
    private static final class NameResolverPriorityAccessor implements PriorityAccessor<NameResolverProvider> {
        NameResolverPriorityAccessor() {
        }

        public boolean isAvailable(NameResolverProvider nameResolverProvider) {
            return nameResolverProvider.isAvailable();
        }

        public int getPriority(NameResolverProvider nameResolverProvider) {
            return nameResolverProvider.priority();
        }
    }

    /* access modifiers changed from: protected */
    public abstract boolean isAvailable();

    /* access modifiers changed from: protected */
    public abstract int priority();

    static {
        Class<NameResolverProvider> cls = NameResolverProvider.class;
        logger = Logger.getLogger(cls.getName());
        providers = ServiceProviders.loadAll(cls, HARDCODED_CLASSES, cls.getClassLoader(), new NameResolverPriorityAccessor());
    }

    public static List<NameResolverProvider> providers() {
        return providers;
    }

    public static Factory asFactory() {
        return factory;
    }

    static Factory asFactory(List<NameResolverProvider> list) {
        return new NameResolverFactory(list);
    }

    static final List<Class<?>> getHardCodedClasses() {
        try {
            return Collections.singletonList(Class.forName("io.grpc.internal.DnsNameResolverProvider"));
        } catch (ClassNotFoundException e) {
            logger.log(Level.FINE, "Unable to find DNS NameResolver", e);
            return Collections.emptyList();
        }
    }
}
