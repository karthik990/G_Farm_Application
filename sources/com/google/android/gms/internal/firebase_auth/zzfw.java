package com.google.android.gms.internal.firebase_auth;

public enum zzfw implements zzhw {
    OOB_REQ_TYPE_UNSPECIFIED(0),
    PASSWORD_RESET(1),
    OLD_EMAIL_AGREE(2),
    NEW_EMAIL_ACCEPT(3),
    VERIFY_EMAIL(4),
    RECOVER_EMAIL(5),
    EMAIL_SIGNIN(6),
    VERIFY_AND_CHANGE_EMAIL(7);
    
    private static final zzhv<zzfw> zzfq = null;
    private final int value;

    public final int zzbq() {
        return this.value;
    }

    public static zzfw zzk(int i) {
        switch (i) {
            case 0:
                return OOB_REQ_TYPE_UNSPECIFIED;
            case 1:
                return PASSWORD_RESET;
            case 2:
                return OLD_EMAIL_AGREE;
            case 3:
                return NEW_EMAIL_ACCEPT;
            case 4:
                return VERIFY_EMAIL;
            case 5:
                return RECOVER_EMAIL;
            case 6:
                return EMAIL_SIGNIN;
            case 7:
                return VERIFY_AND_CHANGE_EMAIL;
            default:
                return null;
        }
    }

    public static zzhy zzbr() {
        return zzfy.zzfs;
    }

    private zzfw(int i) {
        this.value = i;
    }

    static {
        zzfq = new zzfv();
    }
}
