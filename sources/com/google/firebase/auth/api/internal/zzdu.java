package com.google.firebase.auth.api.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.firebase_auth.zzdz;
import com.google.android.gms.internal.firebase_auth.zzeb;
import com.google.android.gms.internal.firebase_auth.zzec;
import com.google.android.gms.internal.firebase_auth.zzem;
import com.google.android.gms.internal.firebase_auth.zzes;
import com.google.android.gms.internal.firebase_auth.zzfd;
import com.google.firebase.auth.PhoneAuthCredential;

public interface zzdu extends IInterface {
    void onFailure(Status status) throws RemoteException;

    void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) throws RemoteException;

    void zza(Status status, PhoneAuthCredential phoneAuthCredential) throws RemoteException;

    void zza(zzdz zzdz) throws RemoteException;

    void zza(zzeb zzeb) throws RemoteException;

    void zza(zzec zzec) throws RemoteException;

    void zza(zzes zzes, zzem zzem) throws RemoteException;

    void zza(zzfd zzfd) throws RemoteException;

    void zzb(zzes zzes) throws RemoteException;

    void zzby(String str) throws RemoteException;

    void zzbz(String str) throws RemoteException;

    void zzca(String str) throws RemoteException;

    void zzdy() throws RemoteException;

    void zzdz() throws RemoteException;

    void zzea() throws RemoteException;
}
