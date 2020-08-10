package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public final class zzad extends zzan {
    private static boolean zzvo;
    private Info zzvp;
    private final zzcv zzvq;
    private String zzvr;
    private boolean zzvs = false;
    private final Object zzvt = new Object();

    zzad(zzap zzap) {
        super(zzap);
        this.zzvq = new zzcv(zzap.zzcn());
    }

    /* access modifiers changed from: protected */
    public final void zzaw() {
    }

    public final boolean zzbw() {
        zzdb();
        Info zzce = zzce();
        if (zzce == null || zzce.isLimitAdTrackingEnabled()) {
            return false;
        }
        return true;
    }

    public final String zzcd() {
        zzdb();
        Info zzce = zzce();
        String id = zzce != null ? zzce.getId() : null;
        if (TextUtils.isEmpty(id)) {
            return null;
        }
        return id;
    }

    private final synchronized Info zzce() {
        if (this.zzvq.zzj(1000)) {
            this.zzvq.start();
            Info zzcf = zzcf();
            if (zza(this.zzvp, zzcf)) {
                this.zzvp = zzcf;
            } else {
                zzu("Failed to reset client id on adid change. Not using adid");
                this.zzvp = new Info("", false);
            }
        }
        return this.zzvp;
    }

    private final boolean zza(Info info, Info info2) {
        String str = null;
        CharSequence id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        String zzeh = zzcw().zzeh();
        synchronized (this.zzvt) {
            if (!this.zzvs) {
                this.zzvr = zzcg();
                this.zzvs = true;
            } else if (TextUtils.isEmpty(this.zzvr)) {
                if (info != null) {
                    str = info.getId();
                }
                if (str == null) {
                    String valueOf = String.valueOf(id);
                    String valueOf2 = String.valueOf(zzeh);
                    boolean zzp = zzp(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
                    return zzp;
                }
                String valueOf3 = String.valueOf(str);
                String valueOf4 = String.valueOf(zzeh);
                this.zzvr = zzo(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3));
            }
            String valueOf5 = String.valueOf(id);
            String valueOf6 = String.valueOf(zzeh);
            String zzo = zzo(valueOf6.length() != 0 ? valueOf5.concat(valueOf6) : new String(valueOf5));
            if (TextUtils.isEmpty(zzo)) {
                return false;
            }
            if (zzo.equals(this.zzvr)) {
                return true;
            }
            if (!TextUtils.isEmpty(this.zzvr)) {
                zzq("Resetting the client id because Advertising Id changed.");
                zzeh = zzcw().zzei();
                zza("New client Id", zzeh);
            }
            String valueOf7 = String.valueOf(id);
            String valueOf8 = String.valueOf(zzeh);
            boolean zzp2 = zzp(valueOf8.length() != 0 ? valueOf7.concat(valueOf8) : new String(valueOf7));
            return zzp2;
        }
    }

    private final Info zzcf() {
        try {
            return AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException unused) {
            zzt("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
            return null;
        } catch (Exception e) {
            if (!zzvo) {
                zzvo = true;
                zzd("Error getting advertiser id", e);
            }
            return null;
        }
    }

    private static String zzo(String str) {
        MessageDigest zzai = zzcz.zzai(MessageDigestAlgorithms.MD5);
        if (zzai == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzai.digest(str.getBytes()))});
    }

    private final boolean zzp(String str) {
        try {
            String zzo = zzo(str);
            zzq("Storing hashed adid.");
            FileOutputStream openFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            openFileOutput.write(zzo.getBytes());
            openFileOutput.close();
            this.zzvr = zzo;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    private final String zzcg() {
        String str = "gaClientIdData";
        String str2 = null;
        try {
            FileInputStream openFileInput = getContext().openFileInput(str);
            byte[] bArr = new byte[128];
            int read = openFileInput.read(bArr, 0, 128);
            if (openFileInput.available() > 0) {
                zzt("Hash file seems corrupted, deleting it.");
                openFileInput.close();
                getContext().deleteFile(str);
                return null;
            } else if (read <= 0) {
                zzq("Hash file is empty.");
                openFileInput.close();
                return null;
            } else {
                String str3 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                } catch (FileNotFoundException unused) {
                } catch (IOException e) {
                    e = e;
                    str2 = str3;
                    zzd("Error reading Hash file, deleting it", e);
                    getContext().deleteFile(str);
                    return str2;
                }
                return str3;
            }
        } catch (FileNotFoundException unused2) {
            return null;
        } catch (IOException e2) {
            e = e2;
            zzd("Error reading Hash file, deleting it", e);
            getContext().deleteFile(str);
            return str2;
        }
    }
}
