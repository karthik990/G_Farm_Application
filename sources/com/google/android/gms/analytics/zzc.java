package com.google.android.gms.analytics;

import android.content.BroadcastReceiver.PendingResult;

final class zzc implements Runnable {
    private final /* synthetic */ PendingResult zzrj;

    zzc(CampaignTrackingReceiver campaignTrackingReceiver, PendingResult pendingResult) {
        this.zzrj = pendingResult;
    }

    public final void run() {
        PendingResult pendingResult = this.zzrj;
        if (pendingResult != null) {
            pendingResult.finish();
        }
    }
}
