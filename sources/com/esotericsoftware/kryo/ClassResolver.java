package com.esotericsoftware.kryo;

import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;

public interface ClassResolver {
    Registration getRegistration(int i);

    Registration getRegistration(Class cls);

    Registration readClass(Input input);

    Registration register(Registration registration);

    Registration registerImplicit(Class cls);

    void reset();

    void setKryo(Kryo kryo);

    Registration writeClass(Output output, Class cls);
}
