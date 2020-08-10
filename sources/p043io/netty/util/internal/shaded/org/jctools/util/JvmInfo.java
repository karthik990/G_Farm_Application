package p043io.netty.util.internal.shaded.org.jctools.util;

/* renamed from: io.netty.util.internal.shaded.org.jctools.util.JvmInfo */
public interface JvmInfo {
    public static final int CACHE_LINE_SIZE = Integer.getInteger("jctools.cacheLineSize", 64).intValue();
    public static final int CPUs = Runtime.getRuntime().availableProcessors();
    public static final int PAGE_SIZE = UnsafeAccess.UNSAFE.pageSize();
}
