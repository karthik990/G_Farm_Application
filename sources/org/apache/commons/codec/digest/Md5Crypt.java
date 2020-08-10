package org.apache.commons.codec.digest;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;

public class Md5Crypt {
    static final String APR1_PREFIX = "$apr1$";
    private static final int BLOCKSIZE = 16;
    static final String MD5_PREFIX = "$1$";
    private static final int ROUNDS = 1000;

    public static String apr1Crypt(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(APR1_PREFIX);
        sb.append(B64.getRandomSalt(8));
        return apr1Crypt(bArr, sb.toString());
    }

    public static String apr1Crypt(byte[] bArr, String str) {
        String str2 = APR1_PREFIX;
        if (str != null && !str.startsWith(str2)) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(str);
            str = sb.toString();
        }
        return md5Crypt(bArr, str, str2);
    }

    public static String apr1Crypt(String str) {
        return apr1Crypt(str.getBytes(Charsets.UTF_8));
    }

    public static String apr1Crypt(String str, String str2) {
        return apr1Crypt(str.getBytes(Charsets.UTF_8), str2);
    }

    public static String md5Crypt(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(MD5_PREFIX);
        sb.append(B64.getRandomSalt(8));
        return md5Crypt(bArr, sb.toString());
    }

    public static String md5Crypt(byte[] bArr, String str) {
        return md5Crypt(bArr, str, MD5_PREFIX);
    }

    public static String md5Crypt(byte[] bArr, String str, String str2) {
        String str3;
        int length = bArr.length;
        String str4 = "$";
        if (str == null) {
            str3 = B64.getRandomSalt(8);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("^");
            sb.append(str2.replace(str4, "\\$"));
            sb.append("([\\.\\/a-zA-Z0-9]{1,8}).*");
            Matcher matcher = Pattern.compile(sb.toString()).matcher(str);
            if (matcher.find()) {
                str3 = matcher.group(1);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Invalid salt value: ");
                sb2.append(str);
                throw new IllegalArgumentException(sb2.toString());
            }
        }
        byte[] bytes = str3.getBytes(Charsets.UTF_8);
        MessageDigest md5Digest = DigestUtils.getMd5Digest();
        md5Digest.update(bArr);
        md5Digest.update(str2.getBytes(Charsets.UTF_8));
        md5Digest.update(bytes);
        MessageDigest md5Digest2 = DigestUtils.getMd5Digest();
        md5Digest2.update(bArr);
        md5Digest2.update(bytes);
        md5Digest2.update(bArr);
        byte[] digest = md5Digest2.digest();
        int i = length;
        while (true) {
            int i2 = 16;
            if (i <= 0) {
                break;
            }
            if (i <= 16) {
                i2 = i;
            }
            md5Digest.update(digest, 0, i2);
            i -= 16;
        }
        Arrays.fill(digest, 0);
        while (length > 0) {
            if ((length & 1) == 1) {
                md5Digest.update(digest[0]);
            } else {
                md5Digest.update(bArr[0]);
            }
            length >>= 1;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str2);
        sb3.append(str3);
        sb3.append(str4);
        StringBuilder sb4 = new StringBuilder(sb3.toString());
        byte[] digest2 = md5Digest.digest();
        for (int i3 = 0; i3 < 1000; i3++) {
            md5Digest2 = DigestUtils.getMd5Digest();
            int i4 = i3 & 1;
            if (i4 != 0) {
                md5Digest2.update(bArr);
            } else {
                md5Digest2.update(digest2, 0, 16);
            }
            if (i3 % 3 != 0) {
                md5Digest2.update(bytes);
            }
            if (i3 % 7 != 0) {
                md5Digest2.update(bArr);
            }
            if (i4 != 0) {
                md5Digest2.update(digest2, 0, 16);
            } else {
                md5Digest2.update(bArr);
            }
            digest2 = md5Digest2.digest();
        }
        B64.b64from24bit(digest2[0], digest2[6], digest2[12], 4, sb4);
        B64.b64from24bit(digest2[1], digest2[7], digest2[13], 4, sb4);
        B64.b64from24bit(digest2[2], digest2[8], digest2[14], 4, sb4);
        B64.b64from24bit(digest2[3], digest2[9], digest2[15], 4, sb4);
        B64.b64from24bit(digest2[4], digest2[10], digest2[5], 4, sb4);
        B64.b64from24bit(0, 0, digest2[11], 2, sb4);
        md5Digest.reset();
        md5Digest2.reset();
        Arrays.fill(bArr, 0);
        Arrays.fill(bytes, 0);
        Arrays.fill(digest2, 0);
        return sb4.toString();
    }
}
