package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;

public class BlowfishSerializer extends Serializer {
    private static SecretKeySpec keySpec;
    private final Serializer serializer;

    public BlowfishSerializer(Serializer serializer2, byte[] bArr) {
        this.serializer = serializer2;
        keySpec = new SecretKeySpec(bArr, "Blowfish");
    }

    public void write(Kryo kryo, Output output, Object obj) {
        CipherOutputStream cipherOutputStream = new CipherOutputStream(output, getCipher(1));
        C12991 r4 = new Output(cipherOutputStream, 256) {
            public void close() throws KryoException {
            }
        };
        this.serializer.write(kryo, r4, obj);
        r4.flush();
        try {
            cipherOutputStream.close();
        } catch (IOException e) {
            throw new KryoException((Throwable) e);
        }
    }

    public Object read(Kryo kryo, Input input, Class cls) {
        return this.serializer.read(kryo, new Input(new CipherInputStream(input, getCipher(2)), 256), cls);
    }

    public Object copy(Kryo kryo, Object obj) {
        return this.serializer.copy(kryo, obj);
    }

    private static Cipher getCipher(int i) {
        try {
            Cipher instance = Cipher.getInstance("Blowfish");
            instance.init(i, keySpec);
            return instance;
        } catch (Exception e) {
            throw new KryoException((Throwable) e);
        }
    }
}
