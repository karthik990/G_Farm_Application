package p043io.netty.util;

/* renamed from: io.netty.util.ReferenceCounted */
public interface ReferenceCounted {
    int refCnt();

    boolean release();

    boolean release(int i);

    ReferenceCounted retain();

    ReferenceCounted retain(int i);

    ReferenceCounted touch();

    ReferenceCounted touch(Object obj);
}
