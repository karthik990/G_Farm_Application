package p097de.javakaffee.kryoserializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.io.PrintStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/* renamed from: de.javakaffee.kryoserializers.JdkProxySerializer */
public class JdkProxySerializer extends Serializer<Object> {
    public Object read(Kryo kryo, Input input, Class<Object> cls) {
        InvocationHandler invocationHandler = (InvocationHandler) kryo.readClassAndObject(input);
        Class[] clsArr = (Class[]) kryo.readObject(input, Class[].class);
        ClassLoader classLoader = kryo.getClassLoader();
        try {
            return Proxy.newProxyInstance(classLoader, clsArr, invocationHandler);
        } catch (RuntimeException e) {
            PrintStream printStream = System.err;
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getName());
            sb.append(".read:\nCould not create proxy using classLoader ");
            sb.append(classLoader);
            sb.append(", have invocationhandler.classloader: ");
            sb.append(invocationHandler.getClass().getClassLoader());
            sb.append(" have contextclassloader: ");
            sb.append(Thread.currentThread().getContextClassLoader());
            printStream.println(sb.toString());
            throw e;
        }
    }

    public void write(Kryo kryo, Output output, Object obj) {
        kryo.writeClassAndObject(output, Proxy.getInvocationHandler(obj));
        kryo.writeObject(output, obj.getClass().getInterfaces());
    }

    public Object copy(Kryo kryo, Object obj) {
        return Proxy.newProxyInstance(kryo.getClassLoader(), obj.getClass().getInterfaces(), Proxy.getInvocationHandler(obj));
    }
}
