package com.paypal.android.sdk.onetouch.core.encryption;

import com.paypal.android.sdk.onetouch.core.exception.InvalidEncryptionDataException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Deprecated
public class OtcCrypto {
    private static final String AES_CTR_ALGO = "AES/CTR/NoPadding";
    private static final int AES_KEY_SIZE = 16;
    private static final int DIGEST_SIZE = 32;
    private static final int ENCRYPTION_KEY_SIZE = 32;
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final int MAX_RSA_ENCRYPTABLE_BYTES = 214;
    private static final int NONCE_SIZE = 16;
    private static final String RSA_ALGO = "RSA/ECB/OAEPWithSHA1AndMGF1Padding";

    private byte[] dataDigest(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, InvalidKeyException {
        String str = HMAC_SHA256;
        Mac instance = Mac.getInstance(str);
        instance.init(new SecretKeySpec(bArr2, str));
        return instance.doFinal(bArr);
    }

    public byte[] generateRandom256BitKey() {
        return EncryptionUtils.generateRandomData(32);
    }

    public byte[] encryptRSAData(byte[] bArr, Certificate certificate) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidEncryptionDataException {
        if (bArr.length <= MAX_RSA_ENCRYPTABLE_BYTES) {
            PublicKey publicKey = certificate.getPublicKey();
            Cipher instance = Cipher.getInstance(RSA_ALGO);
            instance.init(1, publicKey);
            return instance.doFinal(bArr);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Data is too large for public key encryption: ");
        sb.append(bArr.length);
        sb.append(" > ");
        sb.append(MAX_RSA_ENCRYPTABLE_BYTES);
        throw new InvalidEncryptionDataException(sb.toString());
    }

    public byte[] decryptAESCTRData(byte[] bArr, byte[] bArr2) throws IllegalBlockSizeException, InvalidKeyException, NoSuchAlgorithmException, IllegalArgumentException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, InvalidEncryptionDataException {
        if (bArr.length >= 48) {
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArr2, 0, bArr3, 0, 16);
            byte[] bArr4 = new byte[16];
            System.arraycopy(bArr2, 16, bArr4, 0, 16);
            byte[] bArr5 = new byte[32];
            System.arraycopy(bArr, 0, bArr5, 0, 32);
            byte[] bArr6 = new byte[(bArr.length - 32)];
            System.arraycopy(bArr, 32, bArr6, 0, bArr.length - 32);
            if (EncryptionUtils.isEqual(dataDigest(bArr6, bArr4), bArr5)) {
                byte[] bArr7 = new byte[16];
                System.arraycopy(bArr6, 0, bArr7, 0, 16);
                IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr7);
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArr3, "AES");
                Cipher instance = Cipher.getInstance(AES_CTR_ALGO);
                instance.init(2, secretKeySpec, ivParameterSpec);
                return instance.doFinal(bArr6, 16, bArr6.length - 16);
            }
            throw new IllegalArgumentException("Signature mismatch");
        }
        throw new InvalidEncryptionDataException("data is too small");
    }
}
