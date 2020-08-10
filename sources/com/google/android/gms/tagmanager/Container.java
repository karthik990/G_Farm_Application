package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.gtm.zzi;
import com.google.android.gms.internal.gtm.zzj;
import com.google.android.gms.internal.gtm.zzk;
import com.google.android.gms.internal.gtm.zzl;
import com.google.android.gms.internal.gtm.zzor;
import com.google.android.gms.internal.gtm.zzov;
import com.google.android.gms.internal.gtm.zzoz;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private final String zzaec;
    private final DataLayer zzaed;
    private zzfb zzaee;
    private Map<String, FunctionCallMacroCallback> zzaef = new HashMap();
    private Map<String, FunctionCallTagCallback> zzaeg = new HashMap();
    private volatile long zzaeh;
    private volatile String zzaei = "";
    private final Context zzrm;

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    class zza implements zzan {
        private zza() {
        }

        public final Object zza(String str, Map<String, Object> map) {
            FunctionCallMacroCallback zzal = Container.this.zzal(str);
            if (zzal == null) {
                return null;
            }
            return zzal.getValue(str, map);
        }
    }

    class zzb implements zzan {
        private zzb() {
        }

        public final Object zza(String str, Map<String, Object> map) {
            FunctionCallTagCallback zzam = Container.this.zzam(str);
            if (zzam != null) {
                zzam.execute(str, map);
            }
            return zzgj.zzkb();
        }
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzov zzov) {
        this.zzrm = context;
        this.zzaed = dataLayer;
        this.zzaec = str;
        this.zzaeh = 0;
        zza(zzov);
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzk zzk) {
        this.zzrm = context;
        this.zzaed = dataLayer;
        this.zzaec = str;
        this.zzaeh = j;
        zzi zzi = zzk.zzqk;
        if (zzi != null) {
            try {
                zza(zzor.zza(zzi));
            } catch (zzoz e) {
                String valueOf = String.valueOf(zzi);
                String zzoz = e.toString();
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46 + String.valueOf(zzoz).length());
                sb.append("Not loading resource: ");
                sb.append(valueOf);
                sb.append(" because it is invalid: ");
                sb.append(zzoz);
                zzdi.zzav(sb.toString());
            }
            if (zzk.zzqj != null) {
                zzj[] zzjArr = zzk.zzqj;
                ArrayList arrayList = new ArrayList();
                for (zzj add : zzjArr) {
                    arrayList.add(add);
                }
                zzhb().zze(arrayList);
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    public String getContainerId() {
        return this.zzaec;
    }

    public boolean getBoolean(String str) {
        zzfb zzhb = zzhb();
        if (zzhb == null) {
            zzdi.zzav("getBoolean called for closed container.");
            return zzgj.zzjz().booleanValue();
        }
        try {
            return zzgj.zzg((zzl) zzhb.zzbj(str).getObject()).booleanValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 66);
            sb.append("Calling getBoolean() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdi.zzav(sb.toString());
            return zzgj.zzjz().booleanValue();
        }
    }

    public double getDouble(String str) {
        zzfb zzhb = zzhb();
        if (zzhb == null) {
            zzdi.zzav("getDouble called for closed container.");
            return zzgj.zzjy().doubleValue();
        }
        try {
            return zzgj.zzf((zzl) zzhb.zzbj(str).getObject()).doubleValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 65);
            sb.append("Calling getDouble() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdi.zzav(sb.toString());
            return zzgj.zzjy().doubleValue();
        }
    }

    public long getLong(String str) {
        zzfb zzhb = zzhb();
        if (zzhb == null) {
            zzdi.zzav("getLong called for closed container.");
            return zzgj.zzjx().longValue();
        }
        try {
            return zzgj.zze((zzl) zzhb.zzbj(str).getObject()).longValue();
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 63);
            sb.append("Calling getLong() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdi.zzav(sb.toString());
            return zzgj.zzjx().longValue();
        }
    }

    public String getString(String str) {
        zzfb zzhb = zzhb();
        if (zzhb == null) {
            zzdi.zzav("getString called for closed container.");
            return zzgj.zzkb();
        }
        try {
            return zzgj.zzc((zzl) zzhb.zzbj(str).getObject());
        } catch (Exception e) {
            String message = e.getMessage();
            StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 65);
            sb.append("Calling getString() threw an exception: ");
            sb.append(message);
            sb.append(" Returning default value.");
            zzdi.zzav(sb.toString());
            return zzgj.zzkb();
        }
    }

    public long getLastRefreshTime() {
        return this.zzaeh;
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    public void registerFunctionCallMacroCallback(String str, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback != null) {
            synchronized (this.zzaef) {
                this.zzaef.put(str, functionCallMacroCallback);
            }
            return;
        }
        throw new NullPointerException("Macro handler must be non-null");
    }

    public void unregisterFunctionCallMacroCallback(String str) {
        synchronized (this.zzaef) {
            this.zzaef.remove(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public final FunctionCallMacroCallback zzal(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.zzaef) {
            functionCallMacroCallback = (FunctionCallMacroCallback) this.zzaef.get(str);
        }
        return functionCallMacroCallback;
    }

    public void registerFunctionCallTagCallback(String str, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback != null) {
            synchronized (this.zzaeg) {
                this.zzaeg.put(str, functionCallTagCallback);
            }
            return;
        }
        throw new NullPointerException("Tag callback must be non-null");
    }

    public void unregisterFunctionCallTagCallback(String str) {
        synchronized (this.zzaeg) {
            this.zzaeg.remove(str);
        }
    }

    public final FunctionCallTagCallback zzam(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.zzaeg) {
            functionCallTagCallback = (FunctionCallTagCallback) this.zzaeg.get(str);
        }
        return functionCallTagCallback;
    }

    public final void zzan(String str) {
        zzhb().zzan(str);
    }

    public final String zzha() {
        return this.zzaei;
    }

    private final void zza(zzov zzov) {
        this.zzaei = zzov.getVersion();
        String str = this.zzaei;
        zzeh.zziy().zziz().equals(zza.CONTAINER_DEBUG);
        zzov zzov2 = zzov;
        zzfb zzfb = new zzfb(this.zzrm, zzov2, this.zzaed, new zza(), new zzb(), new zzdq());
        zza(zzfb);
        if (getBoolean("_gtm.loadEventEnabled")) {
            String str2 = "gtm.load";
            this.zzaed.pushEvent(str2, DataLayer.mapOf("gtm.id", this.zzaec));
        }
    }

    private final synchronized void zza(zzfb zzfb) {
        this.zzaee = zzfb;
    }

    private final synchronized zzfb zzhb() {
        return this.zzaee;
    }

    /* access modifiers changed from: 0000 */
    public final void release() {
        this.zzaee = null;
    }
}
