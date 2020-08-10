package com.mobiroller.helpers;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class AesBase64Wrapper {
    private static String COMBINED_STRING = "5J4Pjqzzmf4j9NTx";

    /* renamed from: IV */
    private static String f1503IV = "kd25gbk4wdcksc49";
    private static String SALT = "E56634A68AD9353D";

    public String encryptAndEncode(String str) {
        try {
            return getString(Base64.encodeBase64(getCipher(1).doFinal(getBytes(str))));
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    public String decodeAndDecrypt(String str) throws Exception {
        return new String(getCipher(2).doFinal(Base64.decodeBase64(getBytes(str))));
    }

    private String getString(byte[] bArr) throws UnsupportedEncodingException {
        return new String(bArr, "UTF-8");
    }

    private byte[] getBytes(String str) throws UnsupportedEncodingException {
        return str.getBytes("UTF-8");
    }

    private Cipher getCipher(int i) throws Exception {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(i, generateKey(), new IvParameterSpec(getBytes(f1503IV)));
        return instance;
    }

    public static String decryptKey(String str) {
        try {
            return new AesBase64Wrapper().decodeAndDecrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Key generateKey() throws Exception {
        return new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(COMBINED_STRING.toCharArray(), getBytes(SALT), 65536, 128)).getEncoded(), "AES");
    }
}
