package p043io.netty.handler.codec.serialization;

/* renamed from: io.netty.handler.codec.serialization.ClassResolver */
public interface ClassResolver {
    Class<?> resolve(String str) throws ClassNotFoundException;
}
