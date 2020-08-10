package p043io.netty.util;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.SystemPropertyUtil;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.util.ResourceLeakDetectorFactory */
public abstract class ResourceLeakDetectorFactory {
    private static volatile ResourceLeakDetectorFactory factoryInstance = new DefaultResourceLeakDetectorFactory();
    /* access modifiers changed from: private */
    public static final InternalLogger logger = InternalLoggerFactory.getInstance(ResourceLeakDetectorFactory.class);

    /* renamed from: io.netty.util.ResourceLeakDetectorFactory$DefaultResourceLeakDetectorFactory */
    private static final class DefaultResourceLeakDetectorFactory extends ResourceLeakDetectorFactory {
        private final Constructor<?> customClassConstructor;
        private final Constructor<?> obsoleteCustomClassConstructor;

        DefaultResourceLeakDetectorFactory() {
            String str;
            try {
                str = (String) AccessController.doPrivileged(new PrivilegedAction<String>() {
                    public String run() {
                        return SystemPropertyUtil.get("io.netty.customResourceLeakDetector");
                    }
                });
            } catch (Throwable th) {
                ResourceLeakDetectorFactory.logger.error("Could not access System property: io.netty.customResourceLeakDetector", th);
                str = null;
            }
            if (str == null) {
                this.customClassConstructor = null;
                this.obsoleteCustomClassConstructor = null;
                return;
            }
            this.obsoleteCustomClassConstructor = obsoleteCustomClassConstructor(str);
            this.customClassConstructor = customClassConstructor(str);
        }

        private static Constructor<?> obsoleteCustomClassConstructor(String str) {
            try {
                Class cls = Class.forName(str, true, PlatformDependent.getSystemClassLoader());
                if (ResourceLeakDetector.class.isAssignableFrom(cls)) {
                    return cls.getConstructor(new Class[]{Class.class, Integer.TYPE, Long.TYPE});
                }
                ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", (Object) str);
                return null;
            } catch (Throwable th) {
                ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", str, th);
            }
        }

        private static Constructor<?> customClassConstructor(String str) {
            try {
                Class cls = Class.forName(str, true, PlatformDependent.getSystemClassLoader());
                if (ResourceLeakDetector.class.isAssignableFrom(cls)) {
                    return cls.getConstructor(new Class[]{Class.class, Integer.TYPE});
                }
                ResourceLeakDetectorFactory.logger.error("Class {} does not inherit from ResourceLeakDetector.", (Object) str);
                return null;
            } catch (Throwable th) {
                ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector class provided: {}", str, th);
            }
        }

        public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> cls, int i, long j) {
            Constructor<?> constructor = this.obsoleteCustomClassConstructor;
            if (constructor != null) {
                try {
                    ResourceLeakDetector<T> resourceLeakDetector = (ResourceLeakDetector) constructor.newInstance(new Object[]{cls, Integer.valueOf(i), Long.valueOf(j)});
                    ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", (Object) this.obsoleteCustomClassConstructor.getDeclaringClass().getName());
                    return resourceLeakDetector;
                } catch (Throwable th) {
                    ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.obsoleteCustomClassConstructor.getDeclaringClass().getName(), cls, th);
                }
            }
            ResourceLeakDetector<T> resourceLeakDetector2 = new ResourceLeakDetector<>(cls, i, j);
            ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", (Object) resourceLeakDetector2);
            return resourceLeakDetector2;
        }

        public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> cls, int i) {
            Constructor<?> constructor = this.customClassConstructor;
            if (constructor != null) {
                try {
                    ResourceLeakDetector<T> resourceLeakDetector = (ResourceLeakDetector) constructor.newInstance(new Object[]{cls, Integer.valueOf(i)});
                    ResourceLeakDetectorFactory.logger.debug("Loaded custom ResourceLeakDetector: {}", (Object) this.customClassConstructor.getDeclaringClass().getName());
                    return resourceLeakDetector;
                } catch (Throwable th) {
                    ResourceLeakDetectorFactory.logger.error("Could not load custom resource leak detector provided: {} with the given resource: {}", this.customClassConstructor.getDeclaringClass().getName(), cls, th);
                }
            }
            ResourceLeakDetector<T> resourceLeakDetector2 = new ResourceLeakDetector<>(cls, i);
            ResourceLeakDetectorFactory.logger.debug("Loaded default ResourceLeakDetector: {}", (Object) resourceLeakDetector2);
            return resourceLeakDetector2;
        }
    }

    @Deprecated
    public abstract <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> cls, int i, long j);

    public static ResourceLeakDetectorFactory instance() {
        return factoryInstance;
    }

    public static void setResourceLeakDetectorFactory(ResourceLeakDetectorFactory resourceLeakDetectorFactory) {
        factoryInstance = (ResourceLeakDetectorFactory) ObjectUtil.checkNotNull(resourceLeakDetectorFactory, "factory");
    }

    public final <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> cls) {
        return newResourceLeakDetector(cls, 128);
    }

    public <T> ResourceLeakDetector<T> newResourceLeakDetector(Class<T> cls, int i) {
        return newResourceLeakDetector(cls, 128, Long.MAX_VALUE);
    }
}
