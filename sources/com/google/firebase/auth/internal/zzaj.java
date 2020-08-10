package com.google.firebase.auth.internal;

final class zzaj implements Runnable {
    private final /* synthetic */ FederatedSignInActivity zzui;

    zzaj(FederatedSignInActivity federatedSignInActivity) {
        this.zzui = federatedSignInActivity;
    }

    public final void run() {
        this.zzui.zzfm();
        FederatedSignInActivity.zzul = null;
    }
}
