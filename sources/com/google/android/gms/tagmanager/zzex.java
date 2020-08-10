package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import com.google.android.gms.internal.gtm.zzdf;
import com.google.android.gms.internal.gtm.zzdi;
import com.google.android.gms.internal.gtm.zzi;
import com.google.android.gms.internal.gtm.zzop;
import com.google.android.gms.internal.gtm.zzor;
import com.google.android.gms.internal.gtm.zzov;
import com.google.android.gms.internal.gtm.zzoz;
import com.google.android.gms.internal.gtm.zzuv;
import com.google.android.gms.internal.gtm.zzuw;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

final class zzex implements zzah {
    private final String zzaec;
    private zzdh<zzop> zzajf;
    private final ExecutorService zzajm = zzdf.zzgp().zzr(zzdi.zzadg);
    private final Context zzrm;

    zzex(Context context, String str) {
        this.zzrm = context;
        this.zzaec = str;
    }

    public final void zza(zzdh<zzop> zzdh) {
        this.zzajf = zzdh;
    }

    public final void zzhk() {
        this.zzajm.execute(new zzey(this));
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007c, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r4.zzajf.zzs(com.google.android.gms.tagmanager.zzcz.zzahu);
        com.google.android.gms.tagmanager.zzdi.zzac("Failed to read the resource from disk. The resource is inconsistent");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x008e, code lost:
        com.google.android.gms.tagmanager.zzdi.zzac(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r4.zzajf.zzs(com.google.android.gms.tagmanager.zzcz.zzahu);
        com.google.android.gms.tagmanager.zzdi.zzac("Failed to read the resource from disk");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00a2, code lost:
        com.google.android.gms.tagmanager.zzdi.zzac(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00af, code lost:
        com.google.android.gms.tagmanager.zzdi.zzac(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00b2, code lost:
        throw r2;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:26:0x007e, B:32:0x0092] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x007e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:32:0x0092 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zzjd() {
        /*
            r4 = this;
            java.lang.String r0 = "Error closing stream for reading resource from disk"
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r1 = r4.zzajf
            if (r1 == 0) goto L_0x00c0
            r1.zzhj()
            java.lang.String r1 = "Attempting to load resource from disk"
            com.google.android.gms.tagmanager.zzdi.zzab(r1)
            com.google.android.gms.tagmanager.zzeh r1 = com.google.android.gms.tagmanager.zzeh.zziy()
            com.google.android.gms.tagmanager.zzeh$zza r1 = r1.zziz()
            com.google.android.gms.tagmanager.zzeh$zza r2 = com.google.android.gms.tagmanager.zzeh.zza.CONTAINER
            if (r1 == r2) goto L_0x0026
            com.google.android.gms.tagmanager.zzeh r1 = com.google.android.gms.tagmanager.zzeh.zziy()
            com.google.android.gms.tagmanager.zzeh$zza r1 = r1.zziz()
            com.google.android.gms.tagmanager.zzeh$zza r2 = com.google.android.gms.tagmanager.zzeh.zza.CONTAINER_DEBUG
            if (r1 != r2) goto L_0x003e
        L_0x0026:
            java.lang.String r1 = r4.zzaec
            com.google.android.gms.tagmanager.zzeh r2 = com.google.android.gms.tagmanager.zzeh.zziy()
            java.lang.String r2 = r2.getContainerId()
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x003e
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r0 = r4.zzajf
            int r1 = com.google.android.gms.tagmanager.zzcz.zzaht
            r0.zzs(r1)
            return
        L_0x003e:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ FileNotFoundException -> 0x00b3 }
            java.io.File r2 = r4.zzje()     // Catch:{ FileNotFoundException -> 0x00b3 }
            r1.<init>(r2)     // Catch:{ FileNotFoundException -> 0x00b3 }
            java.io.ByteArrayOutputStream r2 = new java.io.ByteArrayOutputStream     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            r2.<init>()     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            com.google.android.gms.internal.gtm.zzor.zza(r1, r2)     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            byte[] r2 = r2.toByteArray()     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            com.google.android.gms.internal.gtm.zzop r3 = new com.google.android.gms.internal.gtm.zzop     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            r3.<init>()     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            com.google.android.gms.internal.gtm.zzuw r2 = com.google.android.gms.internal.gtm.zzuw.zza(r3, r2)     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            com.google.android.gms.internal.gtm.zzop r2 = (com.google.android.gms.internal.gtm.zzop) r2     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            com.google.android.gms.internal.gtm.zzi r3 = r2.zzqk     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            if (r3 != 0) goto L_0x006f
            com.google.android.gms.internal.gtm.zzk r3 = r2.zzauy     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            if (r3 == 0) goto L_0x0067
            goto L_0x006f
        L_0x0067:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            java.lang.String r3 = "Resource and SupplementedResource are NULL."
            r2.<init>(r3)     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            throw r2     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
        L_0x006f:
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r3 = r4.zzajf     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            r3.zze(r2)     // Catch:{ IOException -> 0x0092, IllegalArgumentException -> 0x007e }
            r1.close()     // Catch:{ IOException -> 0x0078 }
            goto L_0x00a5
        L_0x0078:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)
            goto L_0x00a5
        L_0x007c:
            r2 = move-exception
            goto L_0x00ab
        L_0x007e:
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r2 = r4.zzajf     // Catch:{ all -> 0x007c }
            int r3 = com.google.android.gms.tagmanager.zzcz.zzahu     // Catch:{ all -> 0x007c }
            r2.zzs(r3)     // Catch:{ all -> 0x007c }
            java.lang.String r2 = "Failed to read the resource from disk. The resource is inconsistent"
            com.google.android.gms.tagmanager.zzdi.zzac(r2)     // Catch:{ all -> 0x007c }
            r1.close()     // Catch:{ IOException -> 0x008e }
            goto L_0x00a5
        L_0x008e:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)
            goto L_0x00a5
        L_0x0092:
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r2 = r4.zzajf     // Catch:{ all -> 0x007c }
            int r3 = com.google.android.gms.tagmanager.zzcz.zzahu     // Catch:{ all -> 0x007c }
            r2.zzs(r3)     // Catch:{ all -> 0x007c }
            java.lang.String r2 = "Failed to read the resource from disk"
            com.google.android.gms.tagmanager.zzdi.zzac(r2)     // Catch:{ all -> 0x007c }
            r1.close()     // Catch:{ IOException -> 0x00a2 }
            goto L_0x00a5
        L_0x00a2:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)
        L_0x00a5:
            java.lang.String r0 = "The Disk resource was successfully read."
            com.google.android.gms.tagmanager.zzdi.zzab(r0)
            return
        L_0x00ab:
            r1.close()     // Catch:{ IOException -> 0x00af }
            goto L_0x00b2
        L_0x00af:
            com.google.android.gms.tagmanager.zzdi.zzac(r0)
        L_0x00b2:
            throw r2
        L_0x00b3:
            java.lang.String r0 = "Failed to find the resource in the disk"
            com.google.android.gms.tagmanager.zzdi.zzax(r0)
            com.google.android.gms.tagmanager.zzdh<com.google.android.gms.internal.gtm.zzop> r0 = r4.zzajf
            int r1 = com.google.android.gms.tagmanager.zzcz.zzaht
            r0.zzs(r1)
            return
        L_0x00c0:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Callback must be set before execute"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzex.zzjd():void");
    }

    public final void zza(zzop zzop) {
        this.zzajm.execute(new zzez(this, zzop));
    }

    public final zzov zzt(int i) {
        try {
            InputStream openRawResource = this.zzrm.getResources().openRawResource(i);
            String resourceName = this.zzrm.getResources().getResourceName(i);
            StringBuilder sb = new StringBuilder(String.valueOf(resourceName).length() + 66);
            sb.append("Attempting to load a container from the resource ID ");
            sb.append(i);
            String str = " (";
            sb.append(str);
            sb.append(resourceName);
            String str2 = ")";
            sb.append(str2);
            zzdi.zzab(sb.toString());
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzor.zza(openRawResource, byteArrayOutputStream);
                zzov zza = zza(byteArrayOutputStream);
                if (zza == null) {
                    return zzb(byteArrayOutputStream.toByteArray());
                }
                zzdi.zzab("The container was successfully loaded from the resource (using JSON file format)");
                return zza;
            } catch (IOException unused) {
                String resourceName2 = this.zzrm.getResources().getResourceName(i);
                StringBuilder sb2 = new StringBuilder(String.valueOf(resourceName2).length() + 67);
                sb2.append("Error reading the default container with resource ID ");
                sb2.append(i);
                sb2.append(str);
                sb2.append(resourceName2);
                sb2.append(str2);
                zzdi.zzac(sb2.toString());
                return null;
            }
        } catch (NotFoundException unused2) {
            StringBuilder sb3 = new StringBuilder(98);
            sb3.append("Failed to load the container. No default container resource found with the resource ID ");
            sb3.append(i);
            zzdi.zzac(sb3.toString());
            return null;
        }
    }

    private static zzov zza(ByteArrayOutputStream byteArrayOutputStream) {
        try {
            return zzda.zzbf(byteArrayOutputStream.toString("UTF-8"));
        } catch (UnsupportedEncodingException unused) {
            zzdi.zzax("Failed to convert binary resource to string for JSON parsing; the file format is not UTF-8 format.");
            return null;
        } catch (JSONException unused2) {
            zzdi.zzac("Failed to extract the container from the resource file. Resource is a UTF-8 encoded string but doesn't contain a JSON container");
            return null;
        }
    }

    private static zzov zzb(byte[] bArr) {
        try {
            zzov zza = zzor.zza((zzi) zzuw.zza(new zzi(), bArr));
            if (zza != null) {
                zzdi.zzab("The container was successfully loaded from the resource (using binary file)");
            }
            return zza;
        } catch (zzuv unused) {
            zzdi.zzav("The resource file is corrupted. The container cannot be extracted from the binary file");
            return null;
        } catch (zzoz unused2) {
            zzdi.zzac("The resource file is invalid. The container from the binary file is invalid");
            return null;
        }
    }

    public final synchronized void release() {
        this.zzajm.shutdown();
    }

    /* access modifiers changed from: 0000 */
    public final boolean zzb(zzop zzop) {
        String str = "error closing stream for writing resource to disk";
        File zzje = zzje();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zzje);
            try {
                byte[] bArr = new byte[zzop.zzpe()];
                zzuw.zza(zzop, bArr, 0, bArr.length);
                fileOutputStream.write(bArr);
                try {
                } catch (IOException unused) {
                    zzdi.zzac(str);
                }
                return true;
            } catch (IOException unused2) {
                zzdi.zzac("Error writing resource to disk. Removing resource from disk.");
                zzje.delete();
                try {
                } catch (IOException unused3) {
                    zzdi.zzac(str);
                }
                return false;
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException unused4) {
                    zzdi.zzac(str);
                }
            }
        } catch (FileNotFoundException unused5) {
            zzdi.zzav("Error opening resource file for writing");
            return false;
        }
    }

    private final File zzje() {
        String valueOf = String.valueOf(this.zzaec);
        String str = "resource_";
        return new File(this.zzrm.getDir("google_tagmanager", 0), valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }
}
