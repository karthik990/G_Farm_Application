package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.android.gms.ads.internal.gmsg.zzv;
import com.google.android.gms.ads.internal.zzbv;
import java.lang.ref.WeakReference;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONException;
import org.json.JSONObject;

@zzadh
@ParametersAreNonnullByDefault
public final class zzok implements OnClickListener {
    private final zzacm zzaad;
    /* access modifiers changed from: private */
    public zzro zzbhm;
    private zzv zzbhn;
    String zzbho;
    Long zzbhp;
    WeakReference<View> zzbhq;

    public zzok(zzacm zzacm) {
        this.zzaad = zzacm;
    }

    private final void zzjx() {
        this.zzbho = null;
        this.zzbhp = null;
        WeakReference<View> weakReference = this.zzbhq;
        if (weakReference != null) {
            View view = (View) weakReference.get();
            this.zzbhq = null;
            if (view != null) {
                view.setClickable(false);
                view.setOnClickListener(null);
            }
        }
    }

    public final void cancelUnconfirmedClick() {
        if (this.zzbhm != null && this.zzbhp != null) {
            zzjx();
            try {
                this.zzbhm.onUnconfirmedClickCancelled();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onClick(View view) {
        WeakReference<View> weakReference = this.zzbhq;
        if (weakReference != null && weakReference.get() == view) {
            if (!(this.zzbho == null || this.zzbhp == null)) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(TtmlNode.ATTR_ID, this.zzbho);
                    jSONObject.put("time_interval", zzbv.zzer().currentTimeMillis() - this.zzbhp.longValue());
                    jSONObject.put("messageType", "onePointFiveClick");
                    this.zzaad.zza("sendMessageToNativeJs", jSONObject);
                } catch (JSONException e) {
                    zzakb.zzb("Unable to dispatch sendMessageToNativeJs event", e);
                }
            }
            zzjx();
        }
    }

    public final void zza(zzro zzro) {
        this.zzbhm = zzro;
        zzv zzv = this.zzbhn;
        String str = "/unconfirmedClick";
        if (zzv != null) {
            this.zzaad.zzb(str, zzv);
        }
        this.zzbhn = new zzol(this);
        this.zzaad.zza(str, this.zzbhn);
    }

    public final zzro zzjw() {
        return this.zzbhm;
    }
}
