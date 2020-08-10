package p105se.simbio.encryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import third.part.android.util.Base64;

/* renamed from: se.simbio.encryption.Encryption */
public class Encryption {
    private final Builder mBuilder;

    /* renamed from: se.simbio.encryption.Encryption$Builder */
    public static class Builder {
        private String mAlgorithm;
        private int mBase64Mode;
        private String mCharsetName;
        private String mDigestAlgorithm;
        private int mIterationCount;
        private byte[] mIv;
        private IvParameterSpec mIvParameterSpec;
        private String mKey;
        private String mKeyAlgorithm;
        private int mKeyLength;
        private String mSalt;
        private String mSecretKeyType;
        private SecureRandom mSecureRandom;
        private String mSecureRandomAlgorithm;

        public static Builder getDefaultBuilder(String str, String str2, byte[] bArr) {
            return new Builder().setIv(bArr).setKey(str).setSalt(str2).setKeyLength(128).setKeyAlgorithm("AES").setCharsetName("UTF8").setIterationCount(1).setDigestAlgorithm("SHA1").setBase64Mode(0).setAlgorithm("AES/CBC/PKCS5Padding").setSecureRandomAlgorithm("SHA1PRNG").setSecretKeyType("PBKDF2WithHmacSHA1");
        }

        public Encryption build() throws NoSuchAlgorithmException {
            setSecureRandom(SecureRandom.getInstance(getSecureRandomAlgorithm()));
            setIvParameterSpec(new IvParameterSpec(getIv()));
            return new Encryption(this);
        }

        /* access modifiers changed from: private */
        public String getCharsetName() {
            return this.mCharsetName;
        }

        public Builder setCharsetName(String str) {
            this.mCharsetName = str;
            return this;
        }

        /* access modifiers changed from: private */
        public String getAlgorithm() {
            return this.mAlgorithm;
        }

        public Builder setAlgorithm(String str) {
            this.mAlgorithm = str;
            return this;
        }

        /* access modifiers changed from: private */
        public String getKeyAlgorithm() {
            return this.mKeyAlgorithm;
        }

        public Builder setKeyAlgorithm(String str) {
            this.mKeyAlgorithm = str;
            return this;
        }

        /* access modifiers changed from: private */
        public int getBase64Mode() {
            return this.mBase64Mode;
        }

        public Builder setBase64Mode(int i) {
            this.mBase64Mode = i;
            return this;
        }

        /* access modifiers changed from: private */
        public String getSecretKeyType() {
            return this.mSecretKeyType;
        }

        public Builder setSecretKeyType(String str) {
            this.mSecretKeyType = str;
            return this;
        }

        /* access modifiers changed from: private */
        public String getSalt() {
            return this.mSalt;
        }

        public Builder setSalt(String str) {
            this.mSalt = str;
            return this;
        }

        /* access modifiers changed from: private */
        public String getKey() {
            return this.mKey;
        }

        public Builder setKey(String str) {
            this.mKey = str;
            return this;
        }

        /* access modifiers changed from: private */
        public int getKeyLength() {
            return this.mKeyLength;
        }

        public Builder setKeyLength(int i) {
            this.mKeyLength = i;
            return this;
        }

        /* access modifiers changed from: private */
        public int getIterationCount() {
            return this.mIterationCount;
        }

        public Builder setIterationCount(int i) {
            this.mIterationCount = i;
            return this;
        }

        private String getSecureRandomAlgorithm() {
            return this.mSecureRandomAlgorithm;
        }

        public Builder setSecureRandomAlgorithm(String str) {
            this.mSecureRandomAlgorithm = str;
            return this;
        }

        private byte[] getIv() {
            return this.mIv;
        }

        public Builder setIv(byte[] bArr) {
            this.mIv = bArr;
            return this;
        }

        /* access modifiers changed from: private */
        public SecureRandom getSecureRandom() {
            return this.mSecureRandom;
        }

        public Builder setSecureRandom(SecureRandom secureRandom) {
            this.mSecureRandom = secureRandom;
            return this;
        }

        /* access modifiers changed from: private */
        public IvParameterSpec getIvParameterSpec() {
            return this.mIvParameterSpec;
        }

        public Builder setIvParameterSpec(IvParameterSpec ivParameterSpec) {
            this.mIvParameterSpec = ivParameterSpec;
            return this;
        }

        /* access modifiers changed from: private */
        public String getDigestAlgorithm() {
            return this.mDigestAlgorithm;
        }

        public Builder setDigestAlgorithm(String str) {
            this.mDigestAlgorithm = str;
            return this;
        }
    }

    /* renamed from: se.simbio.encryption.Encryption$Callback */
    public interface Callback {
        void onError(Exception exc);

        void onSuccess(String str);
    }

    private Encryption(Builder builder) {
        this.mBuilder = builder;
    }

    public static Encryption getDefault(String str, String str2, byte[] bArr) {
        try {
            return Builder.getDefaultBuilder(str, str2, bArr).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String encrypt(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException {
        if (str == null) {
            return null;
        }
        SecretKey secretKey = getSecretKey(hashTheKey(this.mBuilder.getKey()));
        byte[] bytes = str.getBytes(this.mBuilder.getCharsetName());
        Cipher instance = Cipher.getInstance(this.mBuilder.getAlgorithm());
        instance.init(1, secretKey, this.mBuilder.getIvParameterSpec(), this.mBuilder.getSecureRandom());
        return Base64.encodeToString(instance.doFinal(bytes), this.mBuilder.getBase64Mode());
    }

    public String encryptOrNull(String str) {
        try {
            return encrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void encryptAsync(final String str, final Callback callback) {
        if (callback != null) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        String encrypt = Encryption.this.encrypt(str);
                        if (encrypt == null) {
                            callback.onError(new Exception("Encrypt return null, it normally occurs when you send a null data"));
                        }
                        callback.onSuccess(encrypt);
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }).start();
        }
    }

    public String decrypt(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if (str == null) {
            return null;
        }
        byte[] decode = Base64.decode(str, this.mBuilder.getBase64Mode());
        SecretKey secretKey = getSecretKey(hashTheKey(this.mBuilder.getKey()));
        Cipher instance = Cipher.getInstance(this.mBuilder.getAlgorithm());
        instance.init(2, secretKey, this.mBuilder.getIvParameterSpec(), this.mBuilder.getSecureRandom());
        return new String(instance.doFinal(decode));
    }

    public String decryptOrNull(String str) {
        try {
            return decrypt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void decryptAsync(final String str, final Callback callback) {
        if (callback != null) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        String decrypt = Encryption.this.decrypt(str);
                        if (decrypt == null) {
                            callback.onError(new Exception("Decrypt return null, it normally occurs when you send a null data"));
                        }
                        callback.onSuccess(decrypt);
                    } catch (Exception e) {
                        callback.onError(e);
                    }
                }
            }).start();
        }
    }

    private SecretKey getSecretKey(char[] cArr) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        return new SecretKeySpec(SecretKeyFactory.getInstance(this.mBuilder.getSecretKeyType()).generateSecret(new PBEKeySpec(cArr, this.mBuilder.getSalt().getBytes(this.mBuilder.getCharsetName()), this.mBuilder.getIterationCount(), this.mBuilder.getKeyLength())).getEncoded(), this.mBuilder.getKeyAlgorithm());
    }

    private char[] hashTheKey(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(this.mBuilder.getDigestAlgorithm());
        instance.update(str.getBytes(this.mBuilder.getCharsetName()));
        return Base64.encodeToString(instance.digest(), 1).toCharArray();
    }
}
