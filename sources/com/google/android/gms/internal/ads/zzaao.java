package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.RemoteCreator;
import com.google.android.gms.dynamic.RemoteCreator.RemoteCreatorException;

@zzadh
public final class zzaao extends RemoteCreator<zzaas> {
    public zzaao() {
        super("com.google.android.gms.ads.AdOverlayCreatorImpl");
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.ads.internal.overlay.client.IAdOverlayCreator");
        return queryLocalInterface instanceof zzaas ? (zzaas) queryLocalInterface : new zzaat(iBinder);
    }

    public final zzaap zze(Activity activity) {
        String str = "Could not create remote AdOverlay.";
        try {
            IBinder zzp = ((zzaas) getRemoteCreatorInstance(activity)).zzp(ObjectWrapper.wrap(activity));
            if (zzp == null) {
                return null;
            }
            IInterface queryLocalInterface = zzp.queryLocalInterface("com.google.android.gms.ads.internal.overlay.client.IAdOverlay");
            return queryLocalInterface instanceof zzaap ? (zzaap) queryLocalInterface : new zzaar(zzp);
        } catch (RemoteException e) {
            zzane.zzc(str, e);
            return null;
        } catch (RemoteCreatorException e2) {
            zzane.zzc(str, e2);
            return null;
        }
    }
}
