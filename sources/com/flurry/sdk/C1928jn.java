package com.flurry.sdk;

import org.objectweb.asm.Opcodes;

/* renamed from: com.flurry.sdk.jn */
public enum C1928jn {
    UNKNOWN(0),
    FLUSH_FRAME(1),
    FRAME_COUNTER(2),
    SESSION_ID(128),
    APP_STATE(Opcodes.IINC),
    APP_INFO(133),
    ANALYTICS_EVENT(134),
    ANALYTICS_ERROR(Opcodes.L2F),
    DEVICE_PROPERTIES(139),
    REPORTED_ID(140),
    SESSION_INFO(Opcodes.F2D),
    SERVER_COOKIES(Opcodes.D2I),
    DYNAMIC_SESSION_INFO(Opcodes.D2L),
    REFERRER(Opcodes.I2B),
    USER_ID(Opcodes.I2C),
    SESSION_ORIGIN(Opcodes.I2S),
    LOCALE(Opcodes.LCMP),
    NETWORK(Opcodes.FCMPL),
    LOCATION(Opcodes.FCMPG),
    PAGE_VIEW(Opcodes.DCMPG),
    SESSION_PROPERTIES(Opcodes.IFEQ),
    LAUNCH_OPTIONS(Opcodes.IFLT),
    APP_ORIENTATION(Opcodes.IFGE),
    SESSION_PROPERTIES_PARAMS(Opcodes.IFGT),
    NOTIFICATION(Opcodes.IFLE),
    ORIGIN_ATTRIBUTE(Opcodes.IF_ICMPNE),
    TIMEZONE(Opcodes.IF_ICMPGE),
    VARIANT_IDS(Opcodes.IF_ICMPGT),
    REPORTING(Opcodes.IF_ICMPLE),
    PREVIOUS_SUCCESSFUL_REPORT(Opcodes.IF_ACMPNE),
    NUM_ERRORS(Opcodes.GOTO),
    GENDER(Opcodes.JSR),
    BIRTHDATE(Opcodes.RET),
    EVENTS_SUMMARY(Opcodes.TABLESWITCH),
    USER_PROPERTY(Opcodes.LOOKUPSWITCH),
    CONSENT(172),
    CCPA_OPTOUT(Opcodes.FRETURN),
    CCPA_DELETION(Opcodes.DRETURN),
    EOF(Opcodes.ARRAYLENGTH);
    

    /* renamed from: N */
    public final int f1386N;

    private C1928jn(int i) {
        this.f1386N = i;
    }
}
