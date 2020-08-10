package com.google.firebase.auth.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.firebase_auth.zza;
import com.google.android.gms.internal.firebase_auth.zzbn;
import com.google.android.gms.internal.firebase_auth.zzbp;
import com.google.android.gms.internal.firebase_auth.zzbr;
import com.google.android.gms.internal.firebase_auth.zzbt;
import com.google.android.gms.internal.firebase_auth.zzbv;
import com.google.android.gms.internal.firebase_auth.zzbx;
import com.google.android.gms.internal.firebase_auth.zzbz;
import com.google.android.gms.internal.firebase_auth.zzcb;
import com.google.android.gms.internal.firebase_auth.zzcd;
import com.google.android.gms.internal.firebase_auth.zzcf;
import com.google.android.gms.internal.firebase_auth.zzch;
import com.google.android.gms.internal.firebase_auth.zzcj;
import com.google.android.gms.internal.firebase_auth.zzcl;
import com.google.android.gms.internal.firebase_auth.zzcn;
import com.google.android.gms.internal.firebase_auth.zzcp;
import com.google.android.gms.internal.firebase_auth.zzcr;
import com.google.android.gms.internal.firebase_auth.zzct;
import com.google.android.gms.internal.firebase_auth.zzcv;
import com.google.android.gms.internal.firebase_auth.zzcx;
import com.google.android.gms.internal.firebase_auth.zzcz;
import com.google.android.gms.internal.firebase_auth.zzd;
import com.google.android.gms.internal.firebase_auth.zzdb;
import com.google.android.gms.internal.firebase_auth.zzdd;
import com.google.android.gms.internal.firebase_auth.zzdf;
import com.google.android.gms.internal.firebase_auth.zzdh;
import com.google.android.gms.internal.firebase_auth.zzdj;
import com.google.android.gms.internal.firebase_auth.zzdl;
import com.google.android.gms.internal.firebase_auth.zzdn;
import com.google.android.gms.internal.firebase_auth.zzdp;
import com.google.android.gms.internal.firebase_auth.zzdr;
import com.google.android.gms.internal.firebase_auth.zzdt;
import com.google.android.gms.internal.firebase_auth.zzdv;
import com.google.android.gms.internal.firebase_auth.zzdx;
import com.google.android.gms.internal.firebase_auth.zzfe;
import com.google.android.gms.internal.firebase_auth.zzfm;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import org.objectweb.asm.Opcodes;

public abstract class zzdy extends zza implements zzdz {
    public zzdy() {
        super("com.google.firebase.auth.api.internal.IFirebaseAuthService");
    }

    /* access modifiers changed from: protected */
    public final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzdu zzdu = null;
        String str = "com.google.firebase.auth.api.internal.IFirebaseAuthCallbacks";
        switch (i) {
            case 1:
                String readString = parcel.readString();
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface(str);
                    if (queryLocalInterface instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface;
                    } else {
                        zzdu = new zzdw(readStrongBinder);
                    }
                }
                zza(readString, zzdu);
                break;
            case 2:
                String readString2 = parcel.readString();
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 != null) {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface(str);
                    if (queryLocalInterface2 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface2;
                    } else {
                        zzdu = new zzdw(readStrongBinder2);
                    }
                }
                zzb(readString2, zzdu);
                break;
            case 3:
                zzfm zzfm = (zzfm) zzd.zza(parcel, zzfm.CREATOR);
                IBinder readStrongBinder3 = parcel.readStrongBinder();
                if (readStrongBinder3 != null) {
                    IInterface queryLocalInterface3 = readStrongBinder3.queryLocalInterface(str);
                    if (queryLocalInterface3 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface3;
                    } else {
                        zzdu = new zzdw(readStrongBinder3);
                    }
                }
                zza(zzfm, zzdu);
                break;
            case 4:
                String readString3 = parcel.readString();
                UserProfileChangeRequest userProfileChangeRequest = (UserProfileChangeRequest) zzd.zza(parcel, UserProfileChangeRequest.CREATOR);
                IBinder readStrongBinder4 = parcel.readStrongBinder();
                if (readStrongBinder4 != null) {
                    IInterface queryLocalInterface4 = readStrongBinder4.queryLocalInterface(str);
                    if (queryLocalInterface4 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface4;
                    } else {
                        zzdu = new zzdw(readStrongBinder4);
                    }
                }
                zza(readString3, userProfileChangeRequest, zzdu);
                break;
            case 5:
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                IBinder readStrongBinder5 = parcel.readStrongBinder();
                if (readStrongBinder5 != null) {
                    IInterface queryLocalInterface5 = readStrongBinder5.queryLocalInterface(str);
                    if (queryLocalInterface5 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface5;
                    } else {
                        zzdu = new zzdw(readStrongBinder5);
                    }
                }
                zza(readString4, readString5, zzdu);
                break;
            case 6:
                String readString6 = parcel.readString();
                String readString7 = parcel.readString();
                IBinder readStrongBinder6 = parcel.readStrongBinder();
                if (readStrongBinder6 != null) {
                    IInterface queryLocalInterface6 = readStrongBinder6.queryLocalInterface(str);
                    if (queryLocalInterface6 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface6;
                    } else {
                        zzdu = new zzdw(readStrongBinder6);
                    }
                }
                zzb(readString6, readString7, zzdu);
                break;
            case 7:
                String readString8 = parcel.readString();
                String readString9 = parcel.readString();
                IBinder readStrongBinder7 = parcel.readStrongBinder();
                if (readStrongBinder7 != null) {
                    IInterface queryLocalInterface7 = readStrongBinder7.queryLocalInterface(str);
                    if (queryLocalInterface7 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface7;
                    } else {
                        zzdu = new zzdw(readStrongBinder7);
                    }
                }
                zzc(readString8, readString9, zzdu);
                break;
            case 8:
                String readString10 = parcel.readString();
                String readString11 = parcel.readString();
                IBinder readStrongBinder8 = parcel.readStrongBinder();
                if (readStrongBinder8 != null) {
                    IInterface queryLocalInterface8 = readStrongBinder8.queryLocalInterface(str);
                    if (queryLocalInterface8 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface8;
                    } else {
                        zzdu = new zzdw(readStrongBinder8);
                    }
                }
                zzd(readString10, readString11, zzdu);
                break;
            case 9:
                String readString12 = parcel.readString();
                IBinder readStrongBinder9 = parcel.readStrongBinder();
                if (readStrongBinder9 != null) {
                    IInterface queryLocalInterface9 = readStrongBinder9.queryLocalInterface(str);
                    if (queryLocalInterface9 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface9;
                    } else {
                        zzdu = new zzdw(readStrongBinder9);
                    }
                }
                zzc(readString12, zzdu);
                break;
            case 10:
                String readString13 = parcel.readString();
                IBinder readStrongBinder10 = parcel.readStrongBinder();
                if (readStrongBinder10 != null) {
                    IInterface queryLocalInterface10 = readStrongBinder10.queryLocalInterface(str);
                    if (queryLocalInterface10 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface10;
                    } else {
                        zzdu = new zzdw(readStrongBinder10);
                    }
                }
                zzd(readString13, zzdu);
                break;
            case 11:
                String readString14 = parcel.readString();
                String readString15 = parcel.readString();
                String readString16 = parcel.readString();
                IBinder readStrongBinder11 = parcel.readStrongBinder();
                if (readStrongBinder11 != null) {
                    IInterface queryLocalInterface11 = readStrongBinder11.queryLocalInterface(str);
                    if (queryLocalInterface11 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface11;
                    } else {
                        zzdu = new zzdw(readStrongBinder11);
                    }
                }
                zza(readString14, readString15, readString16, zzdu);
                break;
            case 12:
                String readString17 = parcel.readString();
                zzfm zzfm2 = (zzfm) zzd.zza(parcel, zzfm.CREATOR);
                IBinder readStrongBinder12 = parcel.readStrongBinder();
                if (readStrongBinder12 != null) {
                    IInterface queryLocalInterface12 = readStrongBinder12.queryLocalInterface(str);
                    if (queryLocalInterface12 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface12;
                    } else {
                        zzdu = new zzdw(readStrongBinder12);
                    }
                }
                zza(readString17, zzfm2, zzdu);
                break;
            case 13:
                String readString18 = parcel.readString();
                IBinder readStrongBinder13 = parcel.readStrongBinder();
                if (readStrongBinder13 != null) {
                    IInterface queryLocalInterface13 = readStrongBinder13.queryLocalInterface(str);
                    if (queryLocalInterface13 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface13;
                    } else {
                        zzdu = new zzdw(readStrongBinder13);
                    }
                }
                zze(readString18, zzdu);
                break;
            case 14:
                String readString19 = parcel.readString();
                String readString20 = parcel.readString();
                IBinder readStrongBinder14 = parcel.readStrongBinder();
                if (readStrongBinder14 != null) {
                    IInterface queryLocalInterface14 = readStrongBinder14.queryLocalInterface(str);
                    if (queryLocalInterface14 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface14;
                    } else {
                        zzdu = new zzdw(readStrongBinder14);
                    }
                }
                zze(readString19, readString20, zzdu);
                break;
            case 15:
                String readString21 = parcel.readString();
                IBinder readStrongBinder15 = parcel.readStrongBinder();
                if (readStrongBinder15 != null) {
                    IInterface queryLocalInterface15 = readStrongBinder15.queryLocalInterface(str);
                    if (queryLocalInterface15 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface15;
                    } else {
                        zzdu = new zzdw(readStrongBinder15);
                    }
                }
                zzf(readString21, zzdu);
                break;
            case 16:
                IBinder readStrongBinder16 = parcel.readStrongBinder();
                if (readStrongBinder16 != null) {
                    IInterface queryLocalInterface16 = readStrongBinder16.queryLocalInterface(str);
                    if (queryLocalInterface16 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface16;
                    } else {
                        zzdu = new zzdw(readStrongBinder16);
                    }
                }
                zza(zzdu);
                break;
            case 17:
                String readString22 = parcel.readString();
                IBinder readStrongBinder17 = parcel.readStrongBinder();
                if (readStrongBinder17 != null) {
                    IInterface queryLocalInterface17 = readStrongBinder17.queryLocalInterface(str);
                    if (queryLocalInterface17 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface17;
                    } else {
                        zzdu = new zzdw(readStrongBinder17);
                    }
                }
                zzg(readString22, zzdu);
                break;
            case 18:
                String readString23 = parcel.readString();
                IBinder readStrongBinder18 = parcel.readStrongBinder();
                if (readStrongBinder18 != null) {
                    IInterface queryLocalInterface18 = readStrongBinder18.queryLocalInterface(str);
                    if (queryLocalInterface18 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface18;
                    } else {
                        zzdu = new zzdw(readStrongBinder18);
                    }
                }
                zzh(readString23, zzdu);
                break;
            case 19:
                String readString24 = parcel.readString();
                IBinder readStrongBinder19 = parcel.readStrongBinder();
                if (readStrongBinder19 != null) {
                    IInterface queryLocalInterface19 = readStrongBinder19.queryLocalInterface(str);
                    if (queryLocalInterface19 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface19;
                    } else {
                        zzdu = new zzdw(readStrongBinder19);
                    }
                }
                zzi(readString24, zzdu);
                break;
            case 20:
                String readString25 = parcel.readString();
                IBinder readStrongBinder20 = parcel.readStrongBinder();
                if (readStrongBinder20 != null) {
                    IInterface queryLocalInterface20 = readStrongBinder20.queryLocalInterface(str);
                    if (queryLocalInterface20 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface20;
                    } else {
                        zzdu = new zzdw(readStrongBinder20);
                    }
                }
                zzj(readString25, zzdu);
                break;
            case 21:
                String readString26 = parcel.readString();
                String readString27 = parcel.readString();
                IBinder readStrongBinder21 = parcel.readStrongBinder();
                if (readStrongBinder21 != null) {
                    IInterface queryLocalInterface21 = readStrongBinder21.queryLocalInterface(str);
                    if (queryLocalInterface21 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface21;
                    } else {
                        zzdu = new zzdw(readStrongBinder21);
                    }
                }
                zzf(readString26, readString27, zzdu);
                break;
            case 22:
                zzfe zzfe = (zzfe) zzd.zza(parcel, zzfe.CREATOR);
                IBinder readStrongBinder22 = parcel.readStrongBinder();
                if (readStrongBinder22 != null) {
                    IInterface queryLocalInterface22 = readStrongBinder22.queryLocalInterface(str);
                    if (queryLocalInterface22 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface22;
                    } else {
                        zzdu = new zzdw(readStrongBinder22);
                    }
                }
                zza(zzfe, zzdu);
                break;
            case 23:
                PhoneAuthCredential phoneAuthCredential = (PhoneAuthCredential) zzd.zza(parcel, PhoneAuthCredential.CREATOR);
                IBinder readStrongBinder23 = parcel.readStrongBinder();
                if (readStrongBinder23 != null) {
                    IInterface queryLocalInterface23 = readStrongBinder23.queryLocalInterface(str);
                    if (queryLocalInterface23 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface23;
                    } else {
                        zzdu = new zzdw(readStrongBinder23);
                    }
                }
                zza(phoneAuthCredential, zzdu);
                break;
            case 24:
                String readString28 = parcel.readString();
                PhoneAuthCredential phoneAuthCredential2 = (PhoneAuthCredential) zzd.zza(parcel, PhoneAuthCredential.CREATOR);
                IBinder readStrongBinder24 = parcel.readStrongBinder();
                if (readStrongBinder24 != null) {
                    IInterface queryLocalInterface24 = readStrongBinder24.queryLocalInterface(str);
                    if (queryLocalInterface24 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface24;
                    } else {
                        zzdu = new zzdw(readStrongBinder24);
                    }
                }
                zza(readString28, phoneAuthCredential2, zzdu);
                break;
            case 25:
                String readString29 = parcel.readString();
                ActionCodeSettings actionCodeSettings = (ActionCodeSettings) zzd.zza(parcel, ActionCodeSettings.CREATOR);
                IBinder readStrongBinder25 = parcel.readStrongBinder();
                if (readStrongBinder25 != null) {
                    IInterface queryLocalInterface25 = readStrongBinder25.queryLocalInterface(str);
                    if (queryLocalInterface25 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface25;
                    } else {
                        zzdu = new zzdw(readStrongBinder25);
                    }
                }
                zza(readString29, actionCodeSettings, zzdu);
                break;
            case 26:
                String readString30 = parcel.readString();
                ActionCodeSettings actionCodeSettings2 = (ActionCodeSettings) zzd.zza(parcel, ActionCodeSettings.CREATOR);
                IBinder readStrongBinder26 = parcel.readStrongBinder();
                if (readStrongBinder26 != null) {
                    IInterface queryLocalInterface26 = readStrongBinder26.queryLocalInterface(str);
                    if (queryLocalInterface26 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface26;
                    } else {
                        zzdu = new zzdw(readStrongBinder26);
                    }
                }
                zzb(readString30, actionCodeSettings2, zzdu);
                break;
            case 27:
                String readString31 = parcel.readString();
                IBinder readStrongBinder27 = parcel.readStrongBinder();
                if (readStrongBinder27 != null) {
                    IInterface queryLocalInterface27 = readStrongBinder27.queryLocalInterface(str);
                    if (queryLocalInterface27 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface27;
                    } else {
                        zzdu = new zzdw(readStrongBinder27);
                    }
                }
                zzk(readString31, zzdu);
                break;
            case 28:
                String readString32 = parcel.readString();
                ActionCodeSettings actionCodeSettings3 = (ActionCodeSettings) zzd.zza(parcel, ActionCodeSettings.CREATOR);
                IBinder readStrongBinder28 = parcel.readStrongBinder();
                if (readStrongBinder28 != null) {
                    IInterface queryLocalInterface28 = readStrongBinder28.queryLocalInterface(str);
                    if (queryLocalInterface28 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface28;
                    } else {
                        zzdu = new zzdw(readStrongBinder28);
                    }
                }
                zzc(readString32, actionCodeSettings3, zzdu);
                break;
            case 29:
                EmailAuthCredential emailAuthCredential = (EmailAuthCredential) zzd.zza(parcel, EmailAuthCredential.CREATOR);
                IBinder readStrongBinder29 = parcel.readStrongBinder();
                if (readStrongBinder29 != null) {
                    IInterface queryLocalInterface29 = readStrongBinder29.queryLocalInterface(str);
                    if (queryLocalInterface29 instanceof zzdu) {
                        zzdu = (zzdu) queryLocalInterface29;
                    } else {
                        zzdu = new zzdw(readStrongBinder29);
                    }
                }
                zza(emailAuthCredential, zzdu);
                break;
            default:
                switch (i) {
                    case 101:
                        zzcf zzcf = (zzcf) zzd.zza(parcel, zzcf.CREATOR);
                        IBinder readStrongBinder30 = parcel.readStrongBinder();
                        if (readStrongBinder30 != null) {
                            IInterface queryLocalInterface30 = readStrongBinder30.queryLocalInterface(str);
                            if (queryLocalInterface30 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface30;
                            } else {
                                zzdu = new zzdw(readStrongBinder30);
                            }
                        }
                        zza(zzcf, zzdu);
                        break;
                    case 102:
                        zzdd zzdd = (zzdd) zzd.zza(parcel, zzdd.CREATOR);
                        IBinder readStrongBinder31 = parcel.readStrongBinder();
                        if (readStrongBinder31 != null) {
                            IInterface queryLocalInterface31 = readStrongBinder31.queryLocalInterface(str);
                            if (queryLocalInterface31 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface31;
                            } else {
                                zzdu = new zzdw(readStrongBinder31);
                            }
                        }
                        zza(zzdd, zzdu);
                        break;
                    case 103:
                        zzdb zzdb = (zzdb) zzd.zza(parcel, zzdb.CREATOR);
                        IBinder readStrongBinder32 = parcel.readStrongBinder();
                        if (readStrongBinder32 != null) {
                            IInterface queryLocalInterface32 = readStrongBinder32.queryLocalInterface(str);
                            if (queryLocalInterface32 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface32;
                            } else {
                                zzdu = new zzdw(readStrongBinder32);
                            }
                        }
                        zza(zzdb, zzdu);
                        break;
                    case 104:
                        zzdv zzdv = (zzdv) zzd.zza(parcel, zzdv.CREATOR);
                        IBinder readStrongBinder33 = parcel.readStrongBinder();
                        if (readStrongBinder33 != null) {
                            IInterface queryLocalInterface33 = readStrongBinder33.queryLocalInterface(str);
                            if (queryLocalInterface33 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface33;
                            } else {
                                zzdu = new zzdw(readStrongBinder33);
                            }
                        }
                        zza(zzdv, zzdu);
                        break;
                    case 105:
                        zzbp zzbp = (zzbp) zzd.zza(parcel, zzbp.CREATOR);
                        IBinder readStrongBinder34 = parcel.readStrongBinder();
                        if (readStrongBinder34 != null) {
                            IInterface queryLocalInterface34 = readStrongBinder34.queryLocalInterface(str);
                            if (queryLocalInterface34 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface34;
                            } else {
                                zzdu = new zzdw(readStrongBinder34);
                            }
                        }
                        zza(zzbp, zzdu);
                        break;
                    case 106:
                        zzbr zzbr = (zzbr) zzd.zza(parcel, zzbr.CREATOR);
                        IBinder readStrongBinder35 = parcel.readStrongBinder();
                        if (readStrongBinder35 != null) {
                            IInterface queryLocalInterface35 = readStrongBinder35.queryLocalInterface(str);
                            if (queryLocalInterface35 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface35;
                            } else {
                                zzdu = new zzdw(readStrongBinder35);
                            }
                        }
                        zza(zzbr, zzdu);
                        break;
                    case 107:
                        zzbx zzbx = (zzbx) zzd.zza(parcel, zzbx.CREATOR);
                        IBinder readStrongBinder36 = parcel.readStrongBinder();
                        if (readStrongBinder36 != null) {
                            IInterface queryLocalInterface36 = readStrongBinder36.queryLocalInterface(str);
                            if (queryLocalInterface36 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface36;
                            } else {
                                zzdu = new zzdw(readStrongBinder36);
                            }
                        }
                        zza(zzbx, zzdu);
                        break;
                    case 108:
                        zzdf zzdf = (zzdf) zzd.zza(parcel, zzdf.CREATOR);
                        IBinder readStrongBinder37 = parcel.readStrongBinder();
                        if (readStrongBinder37 != null) {
                            IInterface queryLocalInterface37 = readStrongBinder37.queryLocalInterface(str);
                            if (queryLocalInterface37 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface37;
                            } else {
                                zzdu = new zzdw(readStrongBinder37);
                            }
                        }
                        zza(zzdf, zzdu);
                        break;
                    case 109:
                        zzch zzch = (zzch) zzd.zza(parcel, zzch.CREATOR);
                        IBinder readStrongBinder38 = parcel.readStrongBinder();
                        if (readStrongBinder38 != null) {
                            IInterface queryLocalInterface38 = readStrongBinder38.queryLocalInterface(str);
                            if (queryLocalInterface38 instanceof zzdu) {
                                zzdu = (zzdu) queryLocalInterface38;
                            } else {
                                zzdu = new zzdw(readStrongBinder38);
                            }
                        }
                        zza(zzch, zzdu);
                        break;
                    default:
                        switch (i) {
                            case 111:
                                zzcj zzcj = (zzcj) zzd.zza(parcel, zzcj.CREATOR);
                                IBinder readStrongBinder39 = parcel.readStrongBinder();
                                if (readStrongBinder39 != null) {
                                    IInterface queryLocalInterface39 = readStrongBinder39.queryLocalInterface(str);
                                    if (queryLocalInterface39 instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface39;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder39);
                                    }
                                }
                                zza(zzcj, zzdu);
                                break;
                            case 112:
                                zzcl zzcl = (zzcl) zzd.zza(parcel, zzcl.CREATOR);
                                IBinder readStrongBinder40 = parcel.readStrongBinder();
                                if (readStrongBinder40 != null) {
                                    IInterface queryLocalInterface40 = readStrongBinder40.queryLocalInterface(str);
                                    if (queryLocalInterface40 instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface40;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder40);
                                    }
                                }
                                zza(zzcl, zzdu);
                                break;
                            case 113:
                                zzdr zzdr = (zzdr) zzd.zza(parcel, zzdr.CREATOR);
                                IBinder readStrongBinder41 = parcel.readStrongBinder();
                                if (readStrongBinder41 != null) {
                                    IInterface queryLocalInterface41 = readStrongBinder41.queryLocalInterface(str);
                                    if (queryLocalInterface41 instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface41;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder41);
                                    }
                                }
                                zza(zzdr, zzdu);
                                break;
                            case 114:
                                zzdt zzdt = (zzdt) zzd.zza(parcel, zzdt.CREATOR);
                                IBinder readStrongBinder42 = parcel.readStrongBinder();
                                if (readStrongBinder42 != null) {
                                    IInterface queryLocalInterface42 = readStrongBinder42.queryLocalInterface(str);
                                    if (queryLocalInterface42 instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface42;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder42);
                                    }
                                }
                                zza(zzdt, zzdu);
                                break;
                            case 115:
                                zzcp zzcp = (zzcp) zzd.zza(parcel, zzcp.CREATOR);
                                IBinder readStrongBinder43 = parcel.readStrongBinder();
                                if (readStrongBinder43 != null) {
                                    IInterface queryLocalInterface43 = readStrongBinder43.queryLocalInterface(str);
                                    if (queryLocalInterface43 instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface43;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder43);
                                    }
                                }
                                zza(zzcp, zzdu);
                                break;
                            case 116:
                                zzcz zzcz = (zzcz) zzd.zza(parcel, zzcz.CREATOR);
                                IBinder readStrongBinder44 = parcel.readStrongBinder();
                                if (readStrongBinder44 != null) {
                                    IInterface queryLocalInterface44 = readStrongBinder44.queryLocalInterface(str);
                                    if (queryLocalInterface44 instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface44;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder44);
                                    }
                                }
                                zza(zzcz, zzdu);
                                break;
                            case 117:
                                zzbz zzbz = (zzbz) zzd.zza(parcel, zzbz.CREATOR);
                                IBinder readStrongBinder45 = parcel.readStrongBinder();
                                if (readStrongBinder45 != null) {
                                    IInterface queryLocalInterface45 = readStrongBinder45.queryLocalInterface(str);
                                    if (queryLocalInterface45 instanceof zzdu) {
                                        zzdu = (zzdu) queryLocalInterface45;
                                    } else {
                                        zzdu = new zzdw(readStrongBinder45);
                                    }
                                }
                                zza(zzbz, zzdu);
                                break;
                            default:
                                switch (i) {
                                    case 119:
                                        zzbt zzbt = (zzbt) zzd.zza(parcel, zzbt.CREATOR);
                                        IBinder readStrongBinder46 = parcel.readStrongBinder();
                                        if (readStrongBinder46 != null) {
                                            IInterface queryLocalInterface46 = readStrongBinder46.queryLocalInterface(str);
                                            if (queryLocalInterface46 instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface46;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder46);
                                            }
                                        }
                                        zza(zzbt, zzdu);
                                        break;
                                    case 120:
                                        zzbn zzbn = (zzbn) zzd.zza(parcel, zzbn.CREATOR);
                                        IBinder readStrongBinder47 = parcel.readStrongBinder();
                                        if (readStrongBinder47 != null) {
                                            IInterface queryLocalInterface47 = readStrongBinder47.queryLocalInterface(str);
                                            if (queryLocalInterface47 instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface47;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder47);
                                            }
                                        }
                                        zza(zzbn, zzdu);
                                        break;
                                    case 121:
                                        zzbv zzbv = (zzbv) zzd.zza(parcel, zzbv.CREATOR);
                                        IBinder readStrongBinder48 = parcel.readStrongBinder();
                                        if (readStrongBinder48 != null) {
                                            IInterface queryLocalInterface48 = readStrongBinder48.queryLocalInterface(str);
                                            if (queryLocalInterface48 instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface48;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder48);
                                            }
                                        }
                                        zza(zzbv, zzdu);
                                        break;
                                    case 122:
                                        zzcv zzcv = (zzcv) zzd.zza(parcel, zzcv.CREATOR);
                                        IBinder readStrongBinder49 = parcel.readStrongBinder();
                                        if (readStrongBinder49 != null) {
                                            IInterface queryLocalInterface49 = readStrongBinder49.queryLocalInterface(str);
                                            if (queryLocalInterface49 instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface49;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder49);
                                            }
                                        }
                                        zza(zzcv, zzdu);
                                        break;
                                    case 123:
                                        zzdj zzdj = (zzdj) zzd.zza(parcel, zzdj.CREATOR);
                                        IBinder readStrongBinder50 = parcel.readStrongBinder();
                                        if (readStrongBinder50 != null) {
                                            IInterface queryLocalInterface50 = readStrongBinder50.queryLocalInterface(str);
                                            if (queryLocalInterface50 instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface50;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder50);
                                            }
                                        }
                                        zza(zzdj, zzdu);
                                        break;
                                    case 124:
                                        zzcn zzcn = (zzcn) zzd.zza(parcel, zzcn.CREATOR);
                                        IBinder readStrongBinder51 = parcel.readStrongBinder();
                                        if (readStrongBinder51 != null) {
                                            IInterface queryLocalInterface51 = readStrongBinder51.queryLocalInterface(str);
                                            if (queryLocalInterface51 instanceof zzdu) {
                                                zzdu = (zzdu) queryLocalInterface51;
                                            } else {
                                                zzdu = new zzdw(readStrongBinder51);
                                            }
                                        }
                                        zza(zzcn, zzdu);
                                        break;
                                    default:
                                        switch (i) {
                                            case Opcodes.IAND /*126*/:
                                                zzcr zzcr = (zzcr) zzd.zza(parcel, zzcr.CREATOR);
                                                IBinder readStrongBinder52 = parcel.readStrongBinder();
                                                if (readStrongBinder52 != null) {
                                                    IInterface queryLocalInterface52 = readStrongBinder52.queryLocalInterface(str);
                                                    if (queryLocalInterface52 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface52;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder52);
                                                    }
                                                }
                                                zza(zzcr, zzdu);
                                                break;
                                            case Opcodes.LAND /*127*/:
                                                zzcx zzcx = (zzcx) zzd.zza(parcel, zzcx.CREATOR);
                                                IBinder readStrongBinder53 = parcel.readStrongBinder();
                                                if (readStrongBinder53 != null) {
                                                    IInterface queryLocalInterface53 = readStrongBinder53.queryLocalInterface(str);
                                                    if (queryLocalInterface53 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface53;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder53);
                                                    }
                                                }
                                                zza(zzcx, zzdu);
                                                break;
                                            case 128:
                                                zzct zzct = (zzct) zzd.zza(parcel, zzct.CREATOR);
                                                IBinder readStrongBinder54 = parcel.readStrongBinder();
                                                if (readStrongBinder54 != null) {
                                                    IInterface queryLocalInterface54 = readStrongBinder54.queryLocalInterface(str);
                                                    if (queryLocalInterface54 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface54;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder54);
                                                    }
                                                }
                                                zza(zzct, zzdu);
                                                break;
                                            case 129:
                                                zzdh zzdh = (zzdh) zzd.zza(parcel, zzdh.CREATOR);
                                                IBinder readStrongBinder55 = parcel.readStrongBinder();
                                                if (readStrongBinder55 != null) {
                                                    IInterface queryLocalInterface55 = readStrongBinder55.queryLocalInterface(str);
                                                    if (queryLocalInterface55 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface55;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder55);
                                                    }
                                                }
                                                zza(zzdh, zzdu);
                                                break;
                                            case 130:
                                                zzdl zzdl = (zzdl) zzd.zza(parcel, zzdl.CREATOR);
                                                IBinder readStrongBinder56 = parcel.readStrongBinder();
                                                if (readStrongBinder56 != null) {
                                                    IInterface queryLocalInterface56 = readStrongBinder56.queryLocalInterface(str);
                                                    if (queryLocalInterface56 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface56;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder56);
                                                    }
                                                }
                                                zza(zzdl, zzdu);
                                                break;
                                            case Opcodes.LXOR /*131*/:
                                                zzdp zzdp = (zzdp) zzd.zza(parcel, zzdp.CREATOR);
                                                IBinder readStrongBinder57 = parcel.readStrongBinder();
                                                if (readStrongBinder57 != null) {
                                                    IInterface queryLocalInterface57 = readStrongBinder57.queryLocalInterface(str);
                                                    if (queryLocalInterface57 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface57;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder57);
                                                    }
                                                }
                                                zza(zzdp, zzdu);
                                                break;
                                            case Opcodes.IINC /*132*/:
                                                zzcb zzcb = (zzcb) zzd.zza(parcel, zzcb.CREATOR);
                                                IBinder readStrongBinder58 = parcel.readStrongBinder();
                                                if (readStrongBinder58 != null) {
                                                    IInterface queryLocalInterface58 = readStrongBinder58.queryLocalInterface(str);
                                                    if (queryLocalInterface58 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface58;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder58);
                                                    }
                                                }
                                                zza(zzcb, zzdu);
                                                break;
                                            case 133:
                                                zzdn zzdn = (zzdn) zzd.zza(parcel, zzdn.CREATOR);
                                                IBinder readStrongBinder59 = parcel.readStrongBinder();
                                                if (readStrongBinder59 != null) {
                                                    IInterface queryLocalInterface59 = readStrongBinder59.queryLocalInterface(str);
                                                    if (queryLocalInterface59 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface59;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder59);
                                                    }
                                                }
                                                zza(zzdn, zzdu);
                                                break;
                                            case 134:
                                                zzcd zzcd = (zzcd) zzd.zza(parcel, zzcd.CREATOR);
                                                IBinder readStrongBinder60 = parcel.readStrongBinder();
                                                if (readStrongBinder60 != null) {
                                                    IInterface queryLocalInterface60 = readStrongBinder60.queryLocalInterface(str);
                                                    if (queryLocalInterface60 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface60;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder60);
                                                    }
                                                }
                                                zza(zzcd, zzdu);
                                                break;
                                            case 135:
                                                zzdx zzdx = (zzdx) zzd.zza(parcel, zzdx.CREATOR);
                                                IBinder readStrongBinder61 = parcel.readStrongBinder();
                                                if (readStrongBinder61 != null) {
                                                    IInterface queryLocalInterface61 = readStrongBinder61.queryLocalInterface(str);
                                                    if (queryLocalInterface61 instanceof zzdu) {
                                                        zzdu = (zzdu) queryLocalInterface61;
                                                    } else {
                                                        zzdu = new zzdw(readStrongBinder61);
                                                    }
                                                }
                                                zza(zzdx, zzdu);
                                                break;
                                            default:
                                                return false;
                                        }
                                }
                        }
                }
        }
        parcel2.writeNoException();
        return true;
    }
}
