package com.flurry.sdk;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.flurry.sdk.C1939k.C1940a;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* renamed from: com.flurry.sdk.ai */
public final class C1540ai {

    /* renamed from: a */
    byte[] f500a;

    /* renamed from: b */
    private C1542aj f501b;

    /* renamed from: c */
    private int f502c;

    /* renamed from: d */
    private final C1939k<byte[]> f503d;

    /* renamed from: e */
    private C1941l<C1536ah> f504e;

    C1540ai() {
        this.f501b = null;
        this.f502c = 0;
        this.f500a = null;
        this.f504e = null;
        this.f503d = new C1939k<>(new C1718do());
        this.f501b = new C1542aj();
    }

    /* renamed from: a */
    public final void mo16254a() {
        if (this.f504e == null) {
            this.f504e = new C1941l<>(m432c(), "installationNum", 1, new C1729dv<C1536ah>() {
                /* renamed from: a */
                public final C1724ds<C1536ah> mo16258a(int i) {
                    return new C1537a();
                }
            });
            byte[] a = mo16256a(m433d());
            if (a != null && VERSION.SDK_INT >= 23) {
                C1732dy.m864b(m432c());
                mo16255a(a, C1940a.CRYPTO_ALGO_PADDING_7);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final boolean mo16255a(byte[] bArr, C1940a aVar) {
        C1536ah ahVar;
        try {
            C1732dy.m864b(m432c());
            byte[] e = m434e();
            byte[] a = this.f503d.mo16526a(bArr, mo16257b(), new IvParameterSpec(e), aVar);
            if (a != null) {
                ahVar = new C1536ah(a, e, true, aVar.ordinal());
            } else {
                ahVar = new C1536ah(bArr, new byte[0], false, aVar.ordinal());
            }
            this.f504e.mo16528a(ahVar);
            return true;
        } catch (IOException e2) {
            StringBuilder sb = new StringBuilder("Error while generating UUID");
            sb.append(e2.getMessage());
            C1685cy.m755a(5, "InstallationIdProvider", sb.toString(), e2);
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public final byte[] mo16256a(Key key) {
        try {
            C1536ah ahVar = (C1536ah) this.f504e.mo16527a();
            if (ahVar == null) {
                return null;
            }
            if (!ahVar.f494a) {
                return ahVar.f496c;
            }
            byte[] bArr = ahVar.f495b;
            byte[] bArr2 = ahVar.f496c;
            C1940a a = C1940a.m1219a(ahVar.f497d);
            if (bArr == null || bArr2 == null) {
                return null;
            }
            return (byte[]) this.f503d.mo16525a(bArr2, key, new IvParameterSpec(bArr), a);
        } catch (IOException unused) {
            C1685cy.m754a(5, "InstallationIdProvider", "Error while reading Android Install Id. Deleting file.");
            return null;
        }
    }

    /* renamed from: c */
    private static File m432c() {
        StringBuilder sb = new StringBuilder();
        sb.append(C1732dy.m863b().getPath());
        sb.append(File.separator);
        sb.append("installationNum");
        return new File(sb.toString());
    }

    /* renamed from: d */
    private static SecretKey m433d() {
        C1598bi a = C1598bi.m531a();
        StringBuilder sb = new StringBuilder("Getting legacy apikey: ");
        sb.append(a.f665b);
        C1685cy.m754a(3, "APIKeyProvider", sb.toString());
        String str = a.f665b;
        String a2 = C1731dx.m857a(C1576b.m502a());
        try {
            return new SecretKeySpec(SecretKeyFactory.getInstance("PBEWithSHA256And256BitAES-CBC-BC").generateSecret(new PBEKeySpec(str.toCharArray(), ByteBuffer.allocate(8).putLong(!TextUtils.isEmpty(a2) ? C1734dz.m877e(a2) : Long.MIN_VALUE).array(), 1000, 256)).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            C1685cy.m755a(4, "InstallationIdProvider", "Error in generate secret key", e);
            return null;
        }
    }

    /* renamed from: e */
    private static byte[] m434e() {
        try {
            byte[] bArr = new byte[16];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(bArr);
            return bArr;
        } catch (NoSuchAlgorithmException e) {
            C1685cy.m755a(4, "InstallationIdProvider", "Error in generating iv", e);
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: b */
    public final Key mo16257b() {
        if (VERSION.SDK_INT < 23) {
            return m433d();
        }
        return this.f501b.mo16259a();
    }
}
