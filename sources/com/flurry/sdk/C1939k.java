package com.flurry.sdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/* renamed from: com.flurry.sdk.k */
public final class C1939k<ObjectType> {

    /* renamed from: a */
    private C1724ds<ObjectType> f1397a;

    /* renamed from: com.flurry.sdk.k$a */
    public enum C1940a {
        NONE(""),
        CRYPTO_ALGO_PADDING_7("AES/CBC/PKCS7Padding"),
        CRYPTO_ALGO_PADDING_5("AES/CBC/PKCS5Padding");
        

        /* renamed from: d */
        String f1402d;

        private C1940a(String str) {
            this.f1402d = str;
        }

        /* renamed from: a */
        public static C1940a m1219a(int i) {
            C1940a[] values;
            for (C1940a aVar : values()) {
                if (aVar.ordinal() == i) {
                    return aVar;
                }
            }
            return NONE;
        }
    }

    public C1939k(C1724ds<ObjectType> dsVar) {
        this.f1397a = dsVar;
    }

    /* renamed from: a */
    public final byte[] mo16526a(ObjectType objecttype, Key key, IvParameterSpec ivParameterSpec, C1940a aVar) throws IOException {
        String str = "FlurryCrypto";
        if (objecttype == null || key == null || aVar == null) {
            C1685cy.m754a(5, str, "Cannot encrypt, invalid params.");
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.f1397a.mo16251a(byteArrayOutputStream, objecttype);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        try {
            Cipher instance = Cipher.getInstance(aVar.f1402d);
            instance.init(1, key, ivParameterSpec);
            return instance.doFinal(byteArray);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            StringBuilder sb = new StringBuilder("Error in encrypt ");
            sb.append(e.getMessage());
            C1685cy.m754a(5, str, sb.toString());
            return null;
        }
    }

    /* renamed from: a */
    public final ObjectType mo16525a(byte[] bArr, Key key, IvParameterSpec ivParameterSpec, C1940a aVar) throws IOException {
        String str = "FlurryCrypto";
        if (bArr == null || key == null || aVar == null) {
            C1685cy.m754a(5, str, "Cannot decrypt, invalid params.");
            return null;
        }
        try {
            Cipher instance = Cipher.getInstance(aVar.f1402d);
            instance.init(2, key, ivParameterSpec);
            return this.f1397a.mo16250a(new ByteArrayInputStream(instance.doFinal(bArr)));
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            StringBuilder sb = new StringBuilder("Error in decrypt ");
            sb.append(e.getMessage());
            C1685cy.m754a(5, str, sb.toString());
            return null;
        }
    }
}
