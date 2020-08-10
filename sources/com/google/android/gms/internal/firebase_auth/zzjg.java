package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzhs.zzd;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import sun.misc.Unsafe;

final class zzjg<T> implements zzjs<T> {
    private static final int[] zzacv = new int[0];
    private static final Unsafe zzacw = zzkq.zzkt();
    private final zzjc zzacr;
    private final zzkk<?, ?> zzacs;
    private final boolean zzact;
    private final zzhh<?> zzacu;
    private final int[] zzacx;
    private final Object[] zzacy;
    private final int zzacz;
    private final int zzada;
    private final boolean zzadb;
    private final boolean zzadc;
    private final boolean zzadd;
    private final int[] zzade;
    private final int zzadf;
    private final int zzadg;
    private final zzjh zzadh;
    private final zzim zzadi;
    private final zziv zzadj;

    private zzjg(int[] iArr, Object[] objArr, int i, int i2, zzjc zzjc, boolean z, boolean z2, int[] iArr2, int i3, int i4, zzjh zzjh, zzim zzim, zzkk<?, ?> zzkk, zzhh<?> zzhh, zziv zziv) {
        this.zzacx = iArr;
        this.zzacy = objArr;
        this.zzacz = i;
        this.zzada = i2;
        this.zzadb = zzjc instanceof zzhs;
        this.zzadc = z;
        this.zzact = zzhh != null && zzhh.zzf(zzjc);
        this.zzadd = false;
        this.zzade = iArr2;
        this.zzadf = i3;
        this.zzadg = i4;
        this.zzadh = zzjh;
        this.zzadi = zzim;
        this.zzacs = zzkk;
        this.zzacu = zzhh;
        this.zzacr = zzjc;
        this.zzadj = zziv;
    }

    private static boolean zzbd(int i) {
        return (i & 536870912) != 0;
    }

    static <T> zzjg<T> zza(Class<T> cls, zzja zzja, zzjh zzjh, zzim zzim, zzkk<?, ?> zzkk, zzhh<?> zzhh, zziv zziv) {
        int i;
        int i2;
        int i3;
        int[] iArr;
        int i4;
        int i5;
        int i6;
        int i7;
        char c;
        int i8;
        int i9;
        String str;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        Class cls2;
        int i17;
        int i18;
        Field field;
        int i19;
        char charAt;
        int i20;
        char c2;
        Field field2;
        Field field3;
        int i21;
        char charAt2;
        int i22;
        char charAt3;
        int i23;
        char charAt4;
        int i24;
        int i25;
        int i26;
        int i27;
        int i28;
        int i29;
        char charAt5;
        int i30;
        char charAt6;
        int i31;
        char charAt7;
        int i32;
        char charAt8;
        char charAt9;
        char charAt10;
        char charAt11;
        char charAt12;
        char charAt13;
        char charAt14;
        zzja zzja2 = zzja;
        if (zzja2 instanceof zzjq) {
            zzjq zzjq = (zzjq) zzja2;
            char c3 = 0;
            boolean z = zzjq.zzjo() == zzd.zzaaw;
            String zzjw = zzjq.zzjw();
            int length = zzjw.length();
            char charAt15 = zzjw.charAt(0);
            if (charAt15 >= 55296) {
                char c4 = charAt15 & 8191;
                int i33 = 1;
                int i34 = 13;
                while (true) {
                    i = i33 + 1;
                    charAt14 = zzjw.charAt(i33);
                    if (charAt14 < 55296) {
                        break;
                    }
                    c4 |= (charAt14 & 8191) << i34;
                    i34 += 13;
                    i33 = i;
                }
                charAt15 = (charAt14 << i34) | c4;
            } else {
                i = 1;
            }
            int i35 = i + 1;
            char charAt16 = zzjw.charAt(i);
            if (charAt16 >= 55296) {
                char c5 = charAt16 & 8191;
                int i36 = 13;
                while (true) {
                    i2 = i35 + 1;
                    charAt13 = zzjw.charAt(i35);
                    if (charAt13 < 55296) {
                        break;
                    }
                    c5 |= (charAt13 & 8191) << i36;
                    i36 += 13;
                    i35 = i2;
                }
                charAt16 = c5 | (charAt13 << i36);
            } else {
                i2 = i35;
            }
            if (charAt16 == 0) {
                iArr = zzacv;
                c = 0;
                i7 = 0;
                i6 = 0;
                i5 = 0;
                i4 = 0;
                i3 = 0;
            } else {
                int i37 = i2 + 1;
                int charAt17 = zzjw.charAt(i2);
                if (charAt17 >= 55296) {
                    int i38 = charAt17 & 8191;
                    int i39 = 13;
                    while (true) {
                        i24 = i37 + 1;
                        charAt12 = zzjw.charAt(i37);
                        if (charAt12 < 55296) {
                            break;
                        }
                        i38 |= (charAt12 & 8191) << i39;
                        i39 += 13;
                        i37 = i24;
                    }
                    charAt17 = (charAt12 << i39) | i38;
                } else {
                    i24 = i37;
                }
                int i40 = i24 + 1;
                char charAt18 = zzjw.charAt(i24);
                if (charAt18 >= 55296) {
                    char c6 = charAt18 & 8191;
                    int i41 = 13;
                    while (true) {
                        i25 = i40 + 1;
                        charAt11 = zzjw.charAt(i40);
                        if (charAt11 < 55296) {
                            break;
                        }
                        c6 |= (charAt11 & 8191) << i41;
                        i41 += 13;
                        i40 = i25;
                    }
                    charAt18 = c6 | (charAt11 << i41);
                } else {
                    i25 = i40;
                }
                int i42 = i25 + 1;
                int charAt19 = zzjw.charAt(i25);
                if (charAt19 >= 55296) {
                    int i43 = charAt19 & 8191;
                    int i44 = 13;
                    while (true) {
                        i26 = i42 + 1;
                        charAt10 = zzjw.charAt(i42);
                        if (charAt10 < 55296) {
                            break;
                        }
                        i43 |= (charAt10 & 8191) << i44;
                        i44 += 13;
                        i42 = i26;
                    }
                    charAt19 = (charAt10 << i44) | i43;
                } else {
                    i26 = i42;
                }
                int i45 = i26 + 1;
                i5 = zzjw.charAt(i26);
                if (i5 >= 55296) {
                    int i46 = i5 & 8191;
                    int i47 = 13;
                    while (true) {
                        i27 = i45 + 1;
                        charAt9 = zzjw.charAt(i45);
                        if (charAt9 < 55296) {
                            break;
                        }
                        i46 |= (charAt9 & 8191) << i47;
                        i47 += 13;
                        i45 = i27;
                    }
                    i5 = (charAt9 << i47) | i46;
                } else {
                    i27 = i45;
                }
                int i48 = i27 + 1;
                i4 = zzjw.charAt(i27);
                if (i4 >= 55296) {
                    int i49 = i4 & 8191;
                    int i50 = 13;
                    while (true) {
                        i32 = i48 + 1;
                        charAt8 = zzjw.charAt(i48);
                        if (charAt8 < 55296) {
                            break;
                        }
                        i49 |= (charAt8 & 8191) << i50;
                        i50 += 13;
                        i48 = i32;
                    }
                    i4 = (charAt8 << i50) | i49;
                    i48 = i32;
                }
                int i51 = i48 + 1;
                c = zzjw.charAt(i48);
                if (c >= 55296) {
                    char c7 = c & 8191;
                    int i52 = 13;
                    while (true) {
                        i31 = i51 + 1;
                        charAt7 = zzjw.charAt(i51);
                        if (charAt7 < 55296) {
                            break;
                        }
                        c7 |= (charAt7 & 8191) << i52;
                        i52 += 13;
                        i51 = i31;
                    }
                    c = c7 | (charAt7 << i52);
                    i51 = i31;
                }
                int i53 = i51 + 1;
                char charAt20 = zzjw.charAt(i51);
                if (charAt20 >= 55296) {
                    int i54 = 13;
                    int i55 = i53;
                    int i56 = charAt20 & 8191;
                    int i57 = i55;
                    while (true) {
                        i30 = i57 + 1;
                        charAt6 = zzjw.charAt(i57);
                        if (charAt6 < 55296) {
                            break;
                        }
                        i56 |= (charAt6 & 8191) << i54;
                        i54 += 13;
                        i57 = i30;
                    }
                    charAt20 = i56 | (charAt6 << i54);
                    i28 = i30;
                } else {
                    i28 = i53;
                }
                int i58 = i28 + 1;
                c3 = zzjw.charAt(i28);
                if (c3 >= 55296) {
                    int i59 = 13;
                    int i60 = i58;
                    int i61 = c3 & 8191;
                    int i62 = i60;
                    while (true) {
                        i29 = i62 + 1;
                        charAt5 = zzjw.charAt(i62);
                        if (charAt5 < 55296) {
                            break;
                        }
                        i61 |= (charAt5 & 8191) << i59;
                        i59 += 13;
                        i62 = i29;
                    }
                    c3 = i61 | (charAt5 << i59);
                    i58 = i29;
                }
                iArr = new int[(c3 + c + charAt20)];
                i6 = (charAt17 << 1) + charAt18;
                int i63 = i58;
                i3 = charAt17;
                i7 = charAt19;
                i2 = i63;
            }
            Unsafe unsafe = zzacw;
            Object[] zzjx = zzjq.zzjx();
            Class cls3 = zzjq.zzjq().getClass();
            int i64 = i6;
            int[] iArr2 = new int[(i4 * 3)];
            Object[] objArr = new Object[(i4 << 1)];
            int i65 = c3 + c;
            int i66 = c3;
            int i67 = i65;
            int i68 = 0;
            int i69 = 0;
            while (i2 < length) {
                int i70 = i2 + 1;
                int charAt21 = zzjw.charAt(i2);
                char c8 = 55296;
                if (charAt21 >= 55296) {
                    int i71 = 13;
                    int i72 = i70;
                    int i73 = charAt21 & 8191;
                    int i74 = i72;
                    while (true) {
                        i23 = i74 + 1;
                        charAt4 = zzjw.charAt(i74);
                        if (charAt4 < c8) {
                            break;
                        }
                        i73 |= (charAt4 & 8191) << i71;
                        i71 += 13;
                        i74 = i23;
                        c8 = 55296;
                    }
                    charAt21 = i73 | (charAt4 << i71);
                    i8 = i23;
                } else {
                    i8 = i70;
                }
                int i75 = i8 + 1;
                char charAt22 = zzjw.charAt(i8);
                int i76 = length;
                char c9 = 55296;
                if (charAt22 >= 55296) {
                    int i77 = 13;
                    int i78 = i75;
                    int i79 = charAt22 & 8191;
                    int i80 = i78;
                    while (true) {
                        i22 = i80 + 1;
                        charAt3 = zzjw.charAt(i80);
                        if (charAt3 < c9) {
                            break;
                        }
                        i79 |= (charAt3 & 8191) << i77;
                        i77 += 13;
                        i80 = i22;
                        c9 = 55296;
                    }
                    charAt22 = i79 | (charAt3 << i77);
                    i9 = i22;
                } else {
                    i9 = i75;
                }
                int i81 = c3;
                char c10 = charAt22 & 255;
                boolean z2 = z;
                if ((charAt22 & 1024) != 0) {
                    int i82 = i68 + 1;
                    iArr[i68] = i69;
                    i68 = i82;
                }
                int i83 = i68;
                if (c10 >= '3') {
                    int i84 = i9 + 1;
                    char charAt23 = zzjw.charAt(i9);
                    char c11 = 55296;
                    if (charAt23 >= 55296) {
                        char c12 = charAt23 & 8191;
                        int i85 = 13;
                        while (true) {
                            i21 = i84 + 1;
                            charAt2 = zzjw.charAt(i84);
                            if (charAt2 < c11) {
                                break;
                            }
                            c12 |= (charAt2 & 8191) << i85;
                            i85 += 13;
                            i84 = i21;
                            c11 = 55296;
                        }
                        charAt23 = c12 | (charAt2 << i85);
                        i84 = i21;
                    }
                    int i86 = c10 - '3';
                    int i87 = i84;
                    if (i86 == 9 || i86 == 17) {
                        c2 = 1;
                        int i88 = i64 + 1;
                        objArr[((i69 / 3) << 1) + 1] = zzjx[i64];
                        i64 = i88;
                    } else {
                        if (i86 == 12 && (charAt15 & 1) == 1) {
                            int i89 = i64 + 1;
                            objArr[((i69 / 3) << 1) + 1] = zzjx[i64];
                            i64 = i89;
                        }
                        c2 = 1;
                    }
                    int i90 = charAt23 << c2;
                    Object obj = zzjx[i90];
                    if (obj instanceof Field) {
                        field2 = (Field) obj;
                    } else {
                        field2 = zza(cls3, (String) obj);
                        zzjx[i90] = field2;
                    }
                    int i91 = i7;
                    int objectFieldOffset = (int) unsafe.objectFieldOffset(field2);
                    int i92 = i90 + 1;
                    Object obj2 = zzjx[i92];
                    int i93 = objectFieldOffset;
                    if (obj2 instanceof Field) {
                        field3 = (Field) obj2;
                    } else {
                        field3 = zza(cls3, (String) obj2);
                        zzjx[i92] = field3;
                    }
                    str = zzjw;
                    i15 = (int) unsafe.objectFieldOffset(field3);
                    cls2 = cls3;
                    i11 = i64;
                    i14 = i93;
                    i16 = 0;
                    i10 = i91;
                    i12 = i5;
                    i13 = charAt21;
                    i18 = i87;
                } else {
                    int i94 = i7;
                    int i95 = i64 + 1;
                    Field zza = zza(cls3, (String) zzjx[i64]);
                    i12 = i5;
                    if (c10 == 9 || c10 == 17) {
                        i10 = i94;
                        objArr[((i69 / 3) << 1) + 1] = zza.getType();
                    } else {
                        if (c10 == 27 || c10 == '1') {
                            i10 = i94;
                            i20 = i95 + 1;
                            objArr[((i69 / 3) << 1) + 1] = zzjx[i95];
                        } else if (c10 == 12 || c10 == 30 || c10 == ',') {
                            i10 = i94;
                            if ((charAt15 & 1) == 1) {
                                i20 = i95 + 1;
                                objArr[((i69 / 3) << 1) + 1] = zzjx[i95];
                            }
                        } else if (c10 == '2') {
                            int i96 = i66 + 1;
                            iArr[i66] = i69;
                            int i97 = (i69 / 3) << 1;
                            int i98 = i95 + 1;
                            objArr[i97] = zzjx[i95];
                            if ((charAt22 & 2048) != 0) {
                                i95 = i98 + 1;
                                objArr[i97 + 1] = zzjx[i98];
                                i10 = i94;
                                i66 = i96;
                            } else {
                                i66 = i96;
                                i95 = i98;
                                i10 = i94;
                            }
                        } else {
                            i10 = i94;
                        }
                        i13 = charAt21;
                        i95 = i20;
                        i14 = (int) unsafe.objectFieldOffset(zza);
                        if ((charAt15 & 1) == 1 || c10 > 17) {
                            str = zzjw;
                            cls2 = cls3;
                            i11 = i95;
                            i17 = i9;
                            i16 = 0;
                            i15 = 0;
                        } else {
                            i17 = i9 + 1;
                            char charAt24 = zzjw.charAt(i9);
                            if (charAt24 >= 55296) {
                                char c13 = charAt24 & 8191;
                                int i99 = 13;
                                while (true) {
                                    i19 = i17 + 1;
                                    charAt = zzjw.charAt(i17);
                                    if (charAt < 55296) {
                                        break;
                                    }
                                    c13 |= (charAt & 8191) << i99;
                                    i99 += 13;
                                    i17 = i19;
                                }
                                charAt24 = c13 | (charAt << i99);
                                i17 = i19;
                            }
                            int i100 = (i3 << 1) + (charAt24 / ' ');
                            Object obj3 = zzjx[i100];
                            str = zzjw;
                            if (obj3 instanceof Field) {
                                field = (Field) obj3;
                            } else {
                                field = zza(cls3, (String) obj3);
                                zzjx[i100] = field;
                            }
                            cls2 = cls3;
                            i11 = i95;
                            i15 = (int) unsafe.objectFieldOffset(field);
                            i16 = charAt24 % ' ';
                        }
                        if (c10 >= 18 && c10 <= '1') {
                            int i101 = i67 + 1;
                            iArr[i67] = i14;
                            i67 = i101;
                        }
                        i18 = i17;
                    }
                    i13 = charAt21;
                    i14 = (int) unsafe.objectFieldOffset(zza);
                    if ((charAt15 & 1) == 1) {
                    }
                    str = zzjw;
                    cls2 = cls3;
                    i11 = i95;
                    i17 = i9;
                    i16 = 0;
                    i15 = 0;
                    int i1012 = i67 + 1;
                    iArr[i67] = i14;
                    i67 = i1012;
                    i18 = i17;
                }
                int i102 = i69 + 1;
                iArr2[i69] = i13;
                int i103 = i102 + 1;
                iArr2[i102] = (c10 << 20) | ((charAt22 & 256) != 0 ? 268435456 : 0) | ((charAt22 & 512) != 0 ? 536870912 : 0) | i14;
                i69 = i103 + 1;
                iArr2[i103] = (i16 << 20) | i15;
                cls3 = cls2;
                i5 = i12;
                c3 = i81;
                i64 = i11;
                length = i76;
                z = z2;
                i7 = i10;
                i68 = i83;
                zzjw = str;
            }
            boolean z3 = z;
            zzjg zzjg = new zzjg(iArr2, objArr, i7, i5, zzjq.zzjq(), z, false, iArr, c3, i65, zzjh, zzim, zzkk, zzhh, zziv);
            return zzjg;
        }
        int zzjo = ((zzkd) zzja2).zzjo();
        int i104 = zzd.zzaaw;
        throw new NoSuchMethodError();
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException unused) {
            Field[] declaredFields = cls.getDeclaredFields();
            for (Field field : declaredFields) {
                if (str.equals(field.getName())) {
                    return field;
                }
            }
            String name = cls.getName();
            String arrays = Arrays.toString(declaredFields);
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 40 + String.valueOf(name).length() + String.valueOf(arrays).length());
            sb.append("Field ");
            sb.append(str);
            sb.append(" for ");
            sb.append(name);
            sb.append(" not found. Known fields are ");
            sb.append(arrays);
            throw new RuntimeException(sb.toString());
        }
    }

    public final T newInstance() {
        return this.zzadh.newInstance(this.zzacr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x006a, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x007e, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0090, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00a4, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b6, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00c8, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00da, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00f0, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0106, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x011c, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x012e, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzm(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzm(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0140, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0154, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0165, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0178, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x018b, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6) == com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x01a4, code lost:
        if (java.lang.Float.floatToIntBits(com.google.android.gms.internal.firebase_auth.zzkq.zzn(r10, r6)) == java.lang.Float.floatToIntBits(com.google.android.gms.internal.firebase_auth.zzkq.zzn(r11, r6))) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01bf, code lost:
        if (java.lang.Double.doubleToLongBits(com.google.android.gms.internal.firebase_auth.zzkq.zzo(r10, r6)) == java.lang.Double.doubleToLongBits(com.google.android.gms.internal.firebase_auth.zzkq.zzo(r11, r6))) goto L_0x01c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01c1, code lost:
        r3 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0038, code lost:
        if (com.google.android.gms.internal.firebase_auth.zzju.zze(com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6), com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)) != false) goto L_0x01c2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(T r10, T r11) {
        /*
            r9 = this;
            int[] r0 = r9.zzacx
            int r0 = r0.length
            r1 = 0
            r2 = 0
        L_0x0005:
            r3 = 1
            if (r2 >= r0) goto L_0x01c9
            int r4 = r9.zzbb(r2)
            r5 = 1048575(0xfffff, float:1.469367E-39)
            r6 = r4 & r5
            long r6 = (long) r6
            r8 = 267386880(0xff00000, float:2.3665827E-29)
            r4 = r4 & r8
            int r4 = r4 >>> 20
            switch(r4) {
                case 0: goto L_0x01a7;
                case 1: goto L_0x018e;
                case 2: goto L_0x017b;
                case 3: goto L_0x0168;
                case 4: goto L_0x0157;
                case 5: goto L_0x0144;
                case 6: goto L_0x0132;
                case 7: goto L_0x0120;
                case 8: goto L_0x010a;
                case 9: goto L_0x00f4;
                case 10: goto L_0x00de;
                case 11: goto L_0x00cc;
                case 12: goto L_0x00ba;
                case 13: goto L_0x00a8;
                case 14: goto L_0x0094;
                case 15: goto L_0x0082;
                case 16: goto L_0x006e;
                case 17: goto L_0x0058;
                case 18: goto L_0x004a;
                case 19: goto L_0x004a;
                case 20: goto L_0x004a;
                case 21: goto L_0x004a;
                case 22: goto L_0x004a;
                case 23: goto L_0x004a;
                case 24: goto L_0x004a;
                case 25: goto L_0x004a;
                case 26: goto L_0x004a;
                case 27: goto L_0x004a;
                case 28: goto L_0x004a;
                case 29: goto L_0x004a;
                case 30: goto L_0x004a;
                case 31: goto L_0x004a;
                case 32: goto L_0x004a;
                case 33: goto L_0x004a;
                case 34: goto L_0x004a;
                case 35: goto L_0x004a;
                case 36: goto L_0x004a;
                case 37: goto L_0x004a;
                case 38: goto L_0x004a;
                case 39: goto L_0x004a;
                case 40: goto L_0x004a;
                case 41: goto L_0x004a;
                case 42: goto L_0x004a;
                case 43: goto L_0x004a;
                case 44: goto L_0x004a;
                case 45: goto L_0x004a;
                case 46: goto L_0x004a;
                case 47: goto L_0x004a;
                case 48: goto L_0x004a;
                case 49: goto L_0x004a;
                case 50: goto L_0x003c;
                case 51: goto L_0x001c;
                case 52: goto L_0x001c;
                case 53: goto L_0x001c;
                case 54: goto L_0x001c;
                case 55: goto L_0x001c;
                case 56: goto L_0x001c;
                case 57: goto L_0x001c;
                case 58: goto L_0x001c;
                case 59: goto L_0x001c;
                case 60: goto L_0x001c;
                case 61: goto L_0x001c;
                case 62: goto L_0x001c;
                case 63: goto L_0x001c;
                case 64: goto L_0x001c;
                case 65: goto L_0x001c;
                case 66: goto L_0x001c;
                case 67: goto L_0x001c;
                case 68: goto L_0x001c;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x01c2
        L_0x001c:
            int r4 = r9.zzbc(r2)
            r4 = r4 & r5
            long r4 = (long) r4
            int r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r4)
            int r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r4)
            if (r8 != r4) goto L_0x01c1
            java.lang.Object r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6)
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)
            boolean r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5)
            if (r4 != 0) goto L_0x01c2
            goto L_0x01c1
        L_0x003c:
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6)
            java.lang.Object r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)
            boolean r3 = com.google.android.gms.internal.firebase_auth.zzju.zze(r3, r4)
            goto L_0x01c2
        L_0x004a:
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6)
            java.lang.Object r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)
            boolean r3 = com.google.android.gms.internal.firebase_auth.zzju.zze(r3, r4)
            goto L_0x01c2
        L_0x0058:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            java.lang.Object r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6)
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)
            boolean r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5)
            if (r4 != 0) goto L_0x01c2
            goto L_0x01c1
        L_0x006e:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            long r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6)
            long r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x01c2
            goto L_0x01c1
        L_0x0082:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            int r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6)
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)
            if (r4 == r5) goto L_0x01c2
            goto L_0x01c1
        L_0x0094:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            long r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6)
            long r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x01c2
            goto L_0x01c1
        L_0x00a8:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            int r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6)
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)
            if (r4 == r5) goto L_0x01c2
            goto L_0x01c1
        L_0x00ba:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            int r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6)
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)
            if (r4 == r5) goto L_0x01c2
            goto L_0x01c1
        L_0x00cc:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            int r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6)
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)
            if (r4 == r5) goto L_0x01c2
            goto L_0x01c1
        L_0x00de:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            java.lang.Object r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6)
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)
            boolean r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5)
            if (r4 != 0) goto L_0x01c2
            goto L_0x01c1
        L_0x00f4:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            java.lang.Object r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6)
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)
            boolean r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5)
            if (r4 != 0) goto L_0x01c2
            goto L_0x01c1
        L_0x010a:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            java.lang.Object r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r10, r6)
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r11, r6)
            boolean r4 = com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r5)
            if (r4 != 0) goto L_0x01c2
            goto L_0x01c1
        L_0x0120:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            boolean r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r10, r6)
            boolean r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r11, r6)
            if (r4 == r5) goto L_0x01c2
            goto L_0x01c1
        L_0x0132:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            int r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6)
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)
            if (r4 == r5) goto L_0x01c2
            goto L_0x01c1
        L_0x0144:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            long r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6)
            long r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x01c2
            goto L_0x01c1
        L_0x0157:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            int r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r10, r6)
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r11, r6)
            if (r4 == r5) goto L_0x01c2
            goto L_0x01c1
        L_0x0168:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            long r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6)
            long r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x01c2
            goto L_0x01c1
        L_0x017b:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            long r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r10, r6)
            long r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r11, r6)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x01c2
            goto L_0x01c1
        L_0x018e:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            float r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r10, r6)
            int r4 = java.lang.Float.floatToIntBits(r4)
            float r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r11, r6)
            int r5 = java.lang.Float.floatToIntBits(r5)
            if (r4 == r5) goto L_0x01c2
            goto L_0x01c1
        L_0x01a7:
            boolean r4 = r9.zzc(r10, r11, r2)
            if (r4 == 0) goto L_0x01c1
            double r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r10, r6)
            long r4 = java.lang.Double.doubleToLongBits(r4)
            double r6 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r11, r6)
            long r6 = java.lang.Double.doubleToLongBits(r6)
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 == 0) goto L_0x01c2
        L_0x01c1:
            r3 = 0
        L_0x01c2:
            if (r3 != 0) goto L_0x01c5
            return r1
        L_0x01c5:
            int r2 = r2 + 3
            goto L_0x0005
        L_0x01c9:
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r0 = r9.zzacs
            java.lang.Object r0 = r0.zzs(r10)
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r2 = r9.zzacs
            java.lang.Object r2 = r2.zzs(r11)
            boolean r0 = r0.equals(r2)
            if (r0 != 0) goto L_0x01dc
            return r1
        L_0x01dc:
            boolean r0 = r9.zzact
            if (r0 == 0) goto L_0x01f1
            com.google.android.gms.internal.firebase_auth.zzhh<?> r0 = r9.zzacu
            com.google.android.gms.internal.firebase_auth.zzhi r10 = r0.zzd(r10)
            com.google.android.gms.internal.firebase_auth.zzhh<?> r0 = r9.zzacu
            com.google.android.gms.internal.firebase_auth.zzhi r11 = r0.zzd(r11)
            boolean r10 = r10.equals(r11)
            return r10
        L_0x01f1:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.equals(java.lang.Object, java.lang.Object):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01c3, code lost:
        r2 = (r2 * 53) + r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0227, code lost:
        r2 = r2 + r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0228, code lost:
        r1 = r1 + 3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int hashCode(T r9) {
        /*
            r8 = this;
            int[] r0 = r8.zzacx
            int r0 = r0.length
            r1 = 0
            r2 = 0
        L_0x0005:
            if (r1 >= r0) goto L_0x022c
            int r3 = r8.zzbb(r1)
            int[] r4 = r8.zzacx
            r4 = r4[r1]
            r5 = 1048575(0xfffff, float:1.469367E-39)
            r5 = r5 & r3
            long r5 = (long) r5
            r7 = 267386880(0xff00000, float:2.3665827E-29)
            r3 = r3 & r7
            int r3 = r3 >>> 20
            r7 = 37
            switch(r3) {
                case 0: goto L_0x0219;
                case 1: goto L_0x020e;
                case 2: goto L_0x0203;
                case 3: goto L_0x01f8;
                case 4: goto L_0x01f1;
                case 5: goto L_0x01e6;
                case 6: goto L_0x01df;
                case 7: goto L_0x01d4;
                case 8: goto L_0x01c7;
                case 9: goto L_0x01b9;
                case 10: goto L_0x01ad;
                case 11: goto L_0x01a5;
                case 12: goto L_0x019d;
                case 13: goto L_0x0195;
                case 14: goto L_0x0189;
                case 15: goto L_0x0181;
                case 16: goto L_0x0175;
                case 17: goto L_0x016a;
                case 18: goto L_0x015e;
                case 19: goto L_0x015e;
                case 20: goto L_0x015e;
                case 21: goto L_0x015e;
                case 22: goto L_0x015e;
                case 23: goto L_0x015e;
                case 24: goto L_0x015e;
                case 25: goto L_0x015e;
                case 26: goto L_0x015e;
                case 27: goto L_0x015e;
                case 28: goto L_0x015e;
                case 29: goto L_0x015e;
                case 30: goto L_0x015e;
                case 31: goto L_0x015e;
                case 32: goto L_0x015e;
                case 33: goto L_0x015e;
                case 34: goto L_0x015e;
                case 35: goto L_0x015e;
                case 36: goto L_0x015e;
                case 37: goto L_0x015e;
                case 38: goto L_0x015e;
                case 39: goto L_0x015e;
                case 40: goto L_0x015e;
                case 41: goto L_0x015e;
                case 42: goto L_0x015e;
                case 43: goto L_0x015e;
                case 44: goto L_0x015e;
                case 45: goto L_0x015e;
                case 46: goto L_0x015e;
                case 47: goto L_0x015e;
                case 48: goto L_0x015e;
                case 49: goto L_0x015e;
                case 50: goto L_0x0152;
                case 51: goto L_0x013c;
                case 52: goto L_0x012a;
                case 53: goto L_0x0118;
                case 54: goto L_0x0106;
                case 55: goto L_0x00f8;
                case 56: goto L_0x00e6;
                case 57: goto L_0x00d8;
                case 58: goto L_0x00c6;
                case 59: goto L_0x00b2;
                case 60: goto L_0x00a0;
                case 61: goto L_0x008e;
                case 62: goto L_0x0080;
                case 63: goto L_0x0072;
                case 64: goto L_0x0064;
                case 65: goto L_0x0052;
                case 66: goto L_0x0044;
                case 67: goto L_0x0032;
                case 68: goto L_0x0020;
                default: goto L_0x001e;
            }
        L_0x001e:
            goto L_0x0228
        L_0x0020:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            int r2 = r2 * 53
            int r3 = r3.hashCode()
            goto L_0x0227
        L_0x0032:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            long r3 = zzi(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x0044:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            int r3 = zzh(r9, r5)
            goto L_0x0227
        L_0x0052:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            long r3 = zzi(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x0064:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            int r3 = zzh(r9, r5)
            goto L_0x0227
        L_0x0072:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            int r3 = zzh(r9, r5)
            goto L_0x0227
        L_0x0080:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            int r3 = zzh(r9, r5)
            goto L_0x0227
        L_0x008e:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            int r3 = r3.hashCode()
            goto L_0x0227
        L_0x00a0:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            int r2 = r2 * 53
            int r3 = r3.hashCode()
            goto L_0x0227
        L_0x00b2:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            java.lang.String r3 = (java.lang.String) r3
            int r3 = r3.hashCode()
            goto L_0x0227
        L_0x00c6:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            boolean r3 = zzj(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzv(r3)
            goto L_0x0227
        L_0x00d8:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            int r3 = zzh(r9, r5)
            goto L_0x0227
        L_0x00e6:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            long r3 = zzi(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x00f8:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            int r3 = zzh(r9, r5)
            goto L_0x0227
        L_0x0106:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            long r3 = zzi(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x0118:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            long r3 = zzi(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x012a:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            float r3 = zzg(r9, r5)
            int r3 = java.lang.Float.floatToIntBits(r3)
            goto L_0x0227
        L_0x013c:
            boolean r3 = r8.zza((T) r9, r4, r1)
            if (r3 == 0) goto L_0x0228
            int r2 = r2 * 53
            double r3 = zzf(r9, r5)
            long r3 = java.lang.Double.doubleToLongBits(r3)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x0152:
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            int r3 = r3.hashCode()
            goto L_0x0227
        L_0x015e:
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            int r3 = r3.hashCode()
            goto L_0x0227
        L_0x016a:
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            if (r3 == 0) goto L_0x01c3
            int r7 = r3.hashCode()
            goto L_0x01c3
        L_0x0175:
            int r2 = r2 * 53
            long r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x0181:
            int r2 = r2 * 53
            int r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5)
            goto L_0x0227
        L_0x0189:
            int r2 = r2 * 53
            long r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x0195:
            int r2 = r2 * 53
            int r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5)
            goto L_0x0227
        L_0x019d:
            int r2 = r2 * 53
            int r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5)
            goto L_0x0227
        L_0x01a5:
            int r2 = r2 * 53
            int r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5)
            goto L_0x0227
        L_0x01ad:
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            int r3 = r3.hashCode()
            goto L_0x0227
        L_0x01b9:
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            if (r3 == 0) goto L_0x01c3
            int r7 = r3.hashCode()
        L_0x01c3:
            int r2 = r2 * 53
            int r2 = r2 + r7
            goto L_0x0228
        L_0x01c7:
            int r2 = r2 * 53
            java.lang.Object r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r9, r5)
            java.lang.String r3 = (java.lang.String) r3
            int r3 = r3.hashCode()
            goto L_0x0227
        L_0x01d4:
            int r2 = r2 * 53
            boolean r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzv(r3)
            goto L_0x0227
        L_0x01df:
            int r2 = r2 * 53
            int r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5)
            goto L_0x0227
        L_0x01e6:
            int r2 = r2 * 53
            long r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x01f1:
            int r2 = r2 * 53
            int r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r9, r5)
            goto L_0x0227
        L_0x01f8:
            int r2 = r2 * 53
            long r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x0203:
            int r2 = r2 * 53
            long r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r9, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
            goto L_0x0227
        L_0x020e:
            int r2 = r2 * 53
            float r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r9, r5)
            int r3 = java.lang.Float.floatToIntBits(r3)
            goto L_0x0227
        L_0x0219:
            int r2 = r2 * 53
            double r3 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r9, r5)
            long r3 = java.lang.Double.doubleToLongBits(r3)
            int r3 = com.google.android.gms.internal.firebase_auth.zzht.zzk(r3)
        L_0x0227:
            int r2 = r2 + r3
        L_0x0228:
            int r1 = r1 + 3
            goto L_0x0005
        L_0x022c:
            int r2 = r2 * 53
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r0 = r8.zzacs
            java.lang.Object r0 = r0.zzs(r9)
            int r0 = r0.hashCode()
            int r2 = r2 + r0
            boolean r0 = r8.zzact
            if (r0 == 0) goto L_0x024a
            int r2 = r2 * 53
            com.google.android.gms.internal.firebase_auth.zzhh<?> r0 = r8.zzacu
            com.google.android.gms.internal.firebase_auth.zzhi r9 = r0.zzd(r9)
            int r9 = r9.hashCode()
            int r2 = r2 + r9
        L_0x024a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.hashCode(java.lang.Object):int");
    }

    public final void zzd(T t, T t2) {
        if (t2 != null) {
            for (int i = 0; i < this.zzacx.length; i += 3) {
                int zzbb = zzbb(i);
                long j = (long) (1048575 & zzbb);
                int i2 = this.zzacx[i];
                switch ((zzbb & 267386880) >>> 20) {
                    case 0:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzo(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 1:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzn(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 2:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 3:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 4:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 5:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 6:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 7:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzm(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 8:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzp(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 9:
                        zza(t, t2, i);
                        break;
                    case 10:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzp(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 11:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 12:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 13:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 14:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 15:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zzb((Object) t, j, zzkq.zzk(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 16:
                        if (!zza(t2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzl(t2, j));
                            zzb(t, i);
                            break;
                        }
                    case 17:
                        zza(t, t2, i);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.zzadi.zza(t, t2, j);
                        break;
                    case 50:
                        zzju.zza(this.zzadj, t, t2, j);
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzp(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 60:
                        zzb(t, t2, i);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzkq.zza((Object) t, j, zzkq.zzp(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 68:
                        zzb(t, t2, i);
                        break;
                }
            }
            if (!this.zzadc) {
                zzju.zza(this.zzacs, t, t2);
                if (this.zzact) {
                    zzju.zza(this.zzacu, t, t2);
                    return;
                }
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    private final void zza(T t, T t2, int i) {
        long zzbb = (long) (zzbb(i) & 1048575);
        if (zza(t2, i)) {
            Object zzp = zzkq.zzp(t, zzbb);
            Object zzp2 = zzkq.zzp(t2, zzbb);
            if (zzp == null || zzp2 == null) {
                if (zzp2 != null) {
                    zzkq.zza((Object) t, zzbb, zzp2);
                    zzb(t, i);
                }
                return;
            }
            zzkq.zza((Object) t, zzbb, zzht.zzb(zzp, zzp2));
            zzb(t, i);
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zzbb = zzbb(i);
        int i2 = this.zzacx[i];
        long j = (long) (zzbb & 1048575);
        if (zza(t2, i2, i)) {
            Object zzp = zzkq.zzp(t, j);
            Object zzp2 = zzkq.zzp(t2, j);
            if (zzp == null || zzp2 == null) {
                if (zzp2 != null) {
                    zzkq.zza((Object) t, j, zzp2);
                    zzb(t, i2, i);
                }
                return;
            }
            zzkq.zza((Object) t, j, zzht.zzb(zzp, zzp2));
            zzb(t, i2, i);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:398:0x0834, code lost:
        r8 = (r8 + r9) + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:415:0x0900, code lost:
        r5 = r5 + r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:421:0x0915, code lost:
        r13 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:437:0x095a, code lost:
        r5 = r5 + r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:474:0x0a0b, code lost:
        r5 = r5 + r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:482:0x0a2e, code lost:
        r3 = r3 + 3;
        r9 = r13;
        r7 = 1048575;
        r8 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int zzq(T r20) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            boolean r2 = r0.zzadc
            r3 = 267386880(0xff00000, float:2.3665827E-29)
            r4 = 0
            r7 = 1048575(0xfffff, float:1.469367E-39)
            r8 = 1
            r9 = 0
            r11 = 0
            if (r2 == 0) goto L_0x04f2
            sun.misc.Unsafe r2 = zzacw
            r12 = 0
            r13 = 0
        L_0x0016:
            int[] r14 = r0.zzacx
            int r14 = r14.length
            if (r12 >= r14) goto L_0x04ea
            int r14 = r0.zzbb(r12)
            r15 = r14 & r3
            int r15 = r15 >>> 20
            int[] r3 = r0.zzacx
            r3 = r3[r12]
            r14 = r14 & r7
            long r5 = (long) r14
            com.google.android.gms.internal.firebase_auth.zzhn r14 = com.google.android.gms.internal.firebase_auth.zzhn.DOUBLE_LIST_PACKED
            int r14 = r14.mo30255id()
            if (r15 < r14) goto L_0x0041
            com.google.android.gms.internal.firebase_auth.zzhn r14 = com.google.android.gms.internal.firebase_auth.zzhn.SINT64_LIST_PACKED
            int r14 = r14.mo30255id()
            if (r15 > r14) goto L_0x0041
            int[] r14 = r0.zzacx
            int r17 = r12 + 2
            r14 = r14[r17]
            r14 = r14 & r7
            goto L_0x0042
        L_0x0041:
            r14 = 0
        L_0x0042:
            switch(r15) {
                case 0: goto L_0x04d6;
                case 1: goto L_0x04ca;
                case 2: goto L_0x04ba;
                case 3: goto L_0x04aa;
                case 4: goto L_0x049a;
                case 5: goto L_0x048e;
                case 6: goto L_0x0482;
                case 7: goto L_0x0476;
                case 8: goto L_0x0458;
                case 9: goto L_0x0444;
                case 10: goto L_0x0433;
                case 11: goto L_0x0424;
                case 12: goto L_0x0415;
                case 13: goto L_0x040a;
                case 14: goto L_0x03ff;
                case 15: goto L_0x03f0;
                case 16: goto L_0x03e1;
                case 17: goto L_0x03cc;
                case 18: goto L_0x03c1;
                case 19: goto L_0x03b8;
                case 20: goto L_0x03af;
                case 21: goto L_0x03a6;
                case 22: goto L_0x039d;
                case 23: goto L_0x0394;
                case 24: goto L_0x038b;
                case 25: goto L_0x0382;
                case 26: goto L_0x0379;
                case 27: goto L_0x036c;
                case 28: goto L_0x0363;
                case 29: goto L_0x035a;
                case 30: goto L_0x0350;
                case 31: goto L_0x0346;
                case 32: goto L_0x033c;
                case 33: goto L_0x0332;
                case 34: goto L_0x0328;
                case 35: goto L_0x0308;
                case 36: goto L_0x02eb;
                case 37: goto L_0x02ce;
                case 38: goto L_0x02b1;
                case 39: goto L_0x0293;
                case 40: goto L_0x0275;
                case 41: goto L_0x0257;
                case 42: goto L_0x0239;
                case 43: goto L_0x021b;
                case 44: goto L_0x01fd;
                case 45: goto L_0x01df;
                case 46: goto L_0x01c1;
                case 47: goto L_0x01a3;
                case 48: goto L_0x0185;
                case 49: goto L_0x0177;
                case 50: goto L_0x0167;
                case 51: goto L_0x0159;
                case 52: goto L_0x014d;
                case 53: goto L_0x013d;
                case 54: goto L_0x012d;
                case 55: goto L_0x011d;
                case 56: goto L_0x0111;
                case 57: goto L_0x0105;
                case 58: goto L_0x00f9;
                case 59: goto L_0x00db;
                case 60: goto L_0x00c7;
                case 61: goto L_0x00b5;
                case 62: goto L_0x00a5;
                case 63: goto L_0x0095;
                case 64: goto L_0x0089;
                case 65: goto L_0x007d;
                case 66: goto L_0x006d;
                case 67: goto L_0x005d;
                case 68: goto L_0x0047;
                default: goto L_0x0045;
            }
        L_0x0045:
            goto L_0x04e4
        L_0x0047:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            com.google.android.gms.internal.firebase_auth.zzjc r5 = (com.google.android.gms.internal.firebase_auth.zzjc) r5
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r0.zzay(r12)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5, r6)
            goto L_0x03c9
        L_0x005d:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            long r5 = zzi(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzf(r3, r5)
            goto L_0x03c9
        L_0x006d:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            int r5 = zzh(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzl(r3, r5)
            goto L_0x03c9
        L_0x007d:
            boolean r5 = r0.zza((T) r1, r3, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzh(r3, r9)
            goto L_0x03c9
        L_0x0089:
            boolean r5 = r0.zza((T) r1, r3, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzn(r3, r11)
            goto L_0x03c9
        L_0x0095:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            int r5 = zzh(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzo(r3, r5)
            goto L_0x03c9
        L_0x00a5:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            int r5 = zzh(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzk(r3, r5)
            goto L_0x03c9
        L_0x00b5:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            com.google.android.gms.internal.firebase_auth.zzgf r5 = (com.google.android.gms.internal.firebase_auth.zzgf) r5
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5)
            goto L_0x03c9
        L_0x00c7:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r0.zzay(r12)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r3, r5, r6)
            goto L_0x03c9
        L_0x00db:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            boolean r6 = r5 instanceof com.google.android.gms.internal.firebase_auth.zzgf
            if (r6 == 0) goto L_0x00f1
            com.google.android.gms.internal.firebase_auth.zzgf r5 = (com.google.android.gms.internal.firebase_auth.zzgf) r5
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5)
            goto L_0x03c9
        L_0x00f1:
            java.lang.String r5 = (java.lang.String) r5
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r5)
            goto L_0x03c9
        L_0x00f9:
            boolean r5 = r0.zza((T) r1, r3, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r3, r8)
            goto L_0x03c9
        L_0x0105:
            boolean r5 = r0.zza((T) r1, r3, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzm(r3, r11)
            goto L_0x03c9
        L_0x0111:
            boolean r5 = r0.zza((T) r1, r3, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzg(r3, r9)
            goto L_0x03c9
        L_0x011d:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            int r5 = zzh(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzj(r3, r5)
            goto L_0x03c9
        L_0x012d:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            long r5 = zzi(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zze(r3, r5)
            goto L_0x03c9
        L_0x013d:
            boolean r14 = r0.zza((T) r1, r3, r12)
            if (r14 == 0) goto L_0x04e4
            long r5 = zzi(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r3, r5)
            goto L_0x03c9
        L_0x014d:
            boolean r5 = r0.zza((T) r1, r3, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r4)
            goto L_0x03c9
        L_0x0159:
            boolean r5 = r0.zza((T) r1, r3, r12)
            if (r5 == 0) goto L_0x04e4
            r5 = 0
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r5)
            goto L_0x03c9
        L_0x0167:
            com.google.android.gms.internal.firebase_auth.zziv r14 = r0.zzadj
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            java.lang.Object r6 = r0.zzaz(r12)
            int r3 = r14.zzb(r3, r5, r6)
            goto L_0x03c9
        L_0x0177:
            java.util.List r5 = zze(r1, r5)
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r0.zzay(r12)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzd(r3, r5, r6)
            goto L_0x03c9
        L_0x0185:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzz(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x0199
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x0199:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x01a3:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzad(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x01b7
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x01b7:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x01c1:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x01d5
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x01d5:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x01df:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x01f3
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x01f3:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x01fd:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzaa(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x0211
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x0211:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x021b:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzac(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x022f
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x022f:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x0239:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzag(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x024d
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x024d:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x0257:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x026b
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x026b:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x0275:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x0289
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x0289:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x0293:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzab(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x02a7
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x02a7:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x02b1:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzy(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x02c5
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x02c5:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x02ce:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzx(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x02e2
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x02e2:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x02eb:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x02ff
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x02ff:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
            goto L_0x0324
        L_0x0308:
            java.lang.Object r5 = r2.getObject(r1, r5)
            java.util.List r5 = (java.util.List) r5
            int r5 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r5)
            if (r5 <= 0) goto L_0x04e4
            boolean r6 = r0.zzadd
            if (r6 == 0) goto L_0x031c
            long r14 = (long) r14
            r2.putInt(r1, r14, r5)
        L_0x031c:
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r3)
            int r6 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r5)
        L_0x0324:
            int r3 = r3 + r6
            int r3 = r3 + r5
            goto L_0x03c9
        L_0x0328:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzq(r3, r5, r11)
            goto L_0x03c9
        L_0x0332:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzu(r3, r5, r11)
            goto L_0x03c9
        L_0x033c:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r3, r5, r11)
            goto L_0x03c9
        L_0x0346:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r3, r5, r11)
            goto L_0x03c9
        L_0x0350:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzr(r3, r5, r11)
            goto L_0x03c9
        L_0x035a:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzt(r3, r5, r11)
            goto L_0x03c9
        L_0x0363:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzd(r3, r5)
            goto L_0x03c9
        L_0x036c:
            java.util.List r5 = zze(r1, r5)
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r0.zzay(r12)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r3, r5, r6)
            goto L_0x03c9
        L_0x0379:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r3, r5)
            goto L_0x03c9
        L_0x0382:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzx(r3, r5, r11)
            goto L_0x03c9
        L_0x038b:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r3, r5, r11)
            goto L_0x03c9
        L_0x0394:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r3, r5, r11)
            goto L_0x03c9
        L_0x039d:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzs(r3, r5, r11)
            goto L_0x03c9
        L_0x03a6:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzp(r3, r5, r11)
            goto L_0x03c9
        L_0x03af:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzo(r3, r5, r11)
            goto L_0x03c9
        L_0x03b8:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r3, r5, r11)
            goto L_0x03c9
        L_0x03c1:
            java.util.List r5 = zze(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r3, r5, r11)
        L_0x03c9:
            int r13 = r13 + r3
            goto L_0x04e4
        L_0x03cc:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            com.google.android.gms.internal.firebase_auth.zzjc r5 = (com.google.android.gms.internal.firebase_auth.zzjc) r5
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r0.zzay(r12)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5, r6)
            goto L_0x03c9
        L_0x03e1:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            long r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzf(r3, r5)
            goto L_0x03c9
        L_0x03f0:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzl(r3, r5)
            goto L_0x03c9
        L_0x03ff:
            boolean r5 = r0.zza((T) r1, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzh(r3, r9)
            goto L_0x03c9
        L_0x040a:
            boolean r5 = r0.zza((T) r1, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzn(r3, r11)
            goto L_0x03c9
        L_0x0415:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzo(r3, r5)
            goto L_0x03c9
        L_0x0424:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzk(r3, r5)
            goto L_0x03c9
        L_0x0433:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            com.google.android.gms.internal.firebase_auth.zzgf r5 = (com.google.android.gms.internal.firebase_auth.zzgf) r5
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5)
            goto L_0x03c9
        L_0x0444:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r0.zzay(r12)
            int r3 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r3, r5, r6)
            goto L_0x03c9
        L_0x0458:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r1, r5)
            boolean r6 = r5 instanceof com.google.android.gms.internal.firebase_auth.zzgf
            if (r6 == 0) goto L_0x046e
            com.google.android.gms.internal.firebase_auth.zzgf r5 = (com.google.android.gms.internal.firebase_auth.zzgf) r5
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r3, r5)
            goto L_0x03c9
        L_0x046e:
            java.lang.String r5 = (java.lang.String) r5
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r5)
            goto L_0x03c9
        L_0x0476:
            boolean r5 = r0.zza((T) r1, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r3, r8)
            goto L_0x03c9
        L_0x0482:
            boolean r5 = r0.zza((T) r1, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzm(r3, r11)
            goto L_0x03c9
        L_0x048e:
            boolean r5 = r0.zza((T) r1, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzg(r3, r9)
            goto L_0x03c9
        L_0x049a:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            int r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzj(r3, r5)
            goto L_0x03c9
        L_0x04aa:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            long r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zze(r3, r5)
            goto L_0x03c9
        L_0x04ba:
            boolean r14 = r0.zza((T) r1, r12)
            if (r14 == 0) goto L_0x04e4
            long r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r1, r5)
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r3, r5)
            goto L_0x03c9
        L_0x04ca:
            boolean r5 = r0.zza((T) r1, r12)
            if (r5 == 0) goto L_0x04e4
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r4)
            goto L_0x03c9
        L_0x04d6:
            boolean r5 = r0.zza((T) r1, r12)
            if (r5 == 0) goto L_0x04e4
            r5 = 0
            int r3 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r3, r5)
            goto L_0x03c9
        L_0x04e4:
            int r12 = r12 + 3
            r3 = 267386880(0xff00000, float:2.3665827E-29)
            goto L_0x0016
        L_0x04ea:
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r2 = r0.zzacs
            int r1 = zza(r2, (T) r1)
            int r13 = r13 + r1
            return r13
        L_0x04f2:
            sun.misc.Unsafe r2 = zzacw
            r3 = -1
            r3 = 0
            r5 = 0
            r6 = -1
            r12 = 0
        L_0x04f9:
            int[] r13 = r0.zzacx
            int r13 = r13.length
            if (r3 >= r13) goto L_0x0a39
            int r13 = r0.zzbb(r3)
            int[] r14 = r0.zzacx
            r15 = r14[r3]
            r16 = 267386880(0xff00000, float:2.3665827E-29)
            r17 = r13 & r16
            int r4 = r17 >>> 20
            r11 = 17
            if (r4 > r11) goto L_0x0525
            int r11 = r3 + 2
            r11 = r14[r11]
            r14 = r11 & r7
            int r18 = r11 >>> 20
            int r18 = r8 << r18
            if (r14 == r6) goto L_0x0522
            long r8 = (long) r14
            int r12 = r2.getInt(r1, r8)
            goto L_0x0523
        L_0x0522:
            r14 = r6
        L_0x0523:
            r6 = r14
            goto L_0x0545
        L_0x0525:
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x0542
            com.google.android.gms.internal.firebase_auth.zzhn r8 = com.google.android.gms.internal.firebase_auth.zzhn.DOUBLE_LIST_PACKED
            int r8 = r8.mo30255id()
            if (r4 < r8) goto L_0x0542
            com.google.android.gms.internal.firebase_auth.zzhn r8 = com.google.android.gms.internal.firebase_auth.zzhn.SINT64_LIST_PACKED
            int r8 = r8.mo30255id()
            if (r4 > r8) goto L_0x0542
            int[] r8 = r0.zzacx
            int r9 = r3 + 2
            r8 = r8[r9]
            r11 = r8 & r7
            goto L_0x0543
        L_0x0542:
            r11 = 0
        L_0x0543:
            r18 = 0
        L_0x0545:
            r8 = r13 & r7
            long r8 = (long) r8
            switch(r4) {
                case 0: goto L_0x0a1e;
                case 1: goto L_0x0a0d;
                case 2: goto L_0x09fb;
                case 3: goto L_0x09ea;
                case 4: goto L_0x09d9;
                case 5: goto L_0x09cc;
                case 6: goto L_0x09bf;
                case 7: goto L_0x09b3;
                case 8: goto L_0x0997;
                case 9: goto L_0x0985;
                case 10: goto L_0x0976;
                case 11: goto L_0x0969;
                case 12: goto L_0x095c;
                case 13: goto L_0x0951;
                case 14: goto L_0x0946;
                case 15: goto L_0x0939;
                case 16: goto L_0x092c;
                case 17: goto L_0x0919;
                case 18: goto L_0x0905;
                case 19: goto L_0x08f5;
                case 20: goto L_0x08e9;
                case 21: goto L_0x08dd;
                case 22: goto L_0x08d1;
                case 23: goto L_0x08c5;
                case 24: goto L_0x08b9;
                case 25: goto L_0x08ad;
                case 26: goto L_0x08a2;
                case 27: goto L_0x0892;
                case 28: goto L_0x0886;
                case 29: goto L_0x0879;
                case 30: goto L_0x086c;
                case 31: goto L_0x085f;
                case 32: goto L_0x0852;
                case 33: goto L_0x0845;
                case 34: goto L_0x0838;
                case 35: goto L_0x0818;
                case 36: goto L_0x07fb;
                case 37: goto L_0x07de;
                case 38: goto L_0x07c1;
                case 39: goto L_0x07a3;
                case 40: goto L_0x0785;
                case 41: goto L_0x0767;
                case 42: goto L_0x0749;
                case 43: goto L_0x072b;
                case 44: goto L_0x070d;
                case 45: goto L_0x06ef;
                case 46: goto L_0x06d1;
                case 47: goto L_0x06b3;
                case 48: goto L_0x0695;
                case 49: goto L_0x0685;
                case 50: goto L_0x0675;
                case 51: goto L_0x0667;
                case 52: goto L_0x065a;
                case 53: goto L_0x064a;
                case 54: goto L_0x063a;
                case 55: goto L_0x062a;
                case 56: goto L_0x061c;
                case 57: goto L_0x060f;
                case 58: goto L_0x0602;
                case 59: goto L_0x05e4;
                case 60: goto L_0x05d0;
                case 61: goto L_0x05be;
                case 62: goto L_0x05ae;
                case 63: goto L_0x059e;
                case 64: goto L_0x0591;
                case 65: goto L_0x0583;
                case 66: goto L_0x0573;
                case 67: goto L_0x0563;
                case 68: goto L_0x054d;
                default: goto L_0x054b;
            }
        L_0x054b:
            goto L_0x0911
        L_0x054d:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            java.lang.Object r4 = r2.getObject(r1, r8)
            com.google.android.gms.internal.firebase_auth.zzjc r4 = (com.google.android.gms.internal.firebase_auth.zzjc) r4
            com.google.android.gms.internal.firebase_auth.zzjs r8 = r0.zzay(r3)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4, r8)
            goto L_0x0910
        L_0x0563:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            long r8 = zzi(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzf(r15, r8)
            goto L_0x0910
        L_0x0573:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            int r4 = zzh(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzl(r15, r4)
            goto L_0x0910
        L_0x0583:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            r8 = 0
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzh(r15, r8)
            goto L_0x0910
        L_0x0591:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            r4 = 0
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzn(r15, r4)
            goto L_0x095a
        L_0x059e:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            int r4 = zzh(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzo(r15, r4)
            goto L_0x0910
        L_0x05ae:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            int r4 = zzh(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzk(r15, r4)
            goto L_0x0910
        L_0x05be:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            java.lang.Object r4 = r2.getObject(r1, r8)
            com.google.android.gms.internal.firebase_auth.zzgf r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4)
            goto L_0x0910
        L_0x05d0:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            java.lang.Object r4 = r2.getObject(r1, r8)
            com.google.android.gms.internal.firebase_auth.zzjs r8 = r0.zzay(r3)
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r15, r4, r8)
            goto L_0x0910
        L_0x05e4:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            java.lang.Object r4 = r2.getObject(r1, r8)
            boolean r8 = r4 instanceof com.google.android.gms.internal.firebase_auth.zzgf
            if (r8 == 0) goto L_0x05fa
            com.google.android.gms.internal.firebase_auth.zzgf r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4)
            goto L_0x0910
        L_0x05fa:
            java.lang.String r4 = (java.lang.String) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r4)
            goto L_0x0910
        L_0x0602:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            r4 = 1
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r15, r4)
            goto L_0x095a
        L_0x060f:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            r4 = 0
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzm(r15, r4)
            goto L_0x095a
        L_0x061c:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            r8 = 0
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzg(r15, r8)
            goto L_0x0910
        L_0x062a:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            int r4 = zzh(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzj(r15, r4)
            goto L_0x0910
        L_0x063a:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            long r8 = zzi(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zze(r15, r8)
            goto L_0x0910
        L_0x064a:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            long r8 = zzi(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r15, r8)
            goto L_0x0910
        L_0x065a:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            r4 = 0
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r4)
            goto L_0x095a
        L_0x0667:
            boolean r4 = r0.zza((T) r1, r15, r3)
            if (r4 == 0) goto L_0x0911
            r8 = 0
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r8)
            goto L_0x0910
        L_0x0675:
            com.google.android.gms.internal.firebase_auth.zziv r4 = r0.zzadj
            java.lang.Object r8 = r2.getObject(r1, r8)
            java.lang.Object r9 = r0.zzaz(r3)
            int r4 = r4.zzb(r15, r8, r9)
            goto L_0x0910
        L_0x0685:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            com.google.android.gms.internal.firebase_auth.zzjs r8 = r0.zzay(r3)
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzd(r15, r4, r8)
            goto L_0x0910
        L_0x0695:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzz(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x06a9
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x06a9:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x06b3:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzad(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x06c7
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x06c7:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x06d1:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x06e5
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x06e5:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x06ef:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x0703
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x0703:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x070d:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzaa(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x0721
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x0721:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x072b:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzac(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x073f
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x073f:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x0749:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzag(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x075d
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x075d:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x0767:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x077b
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x077b:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x0785:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x0799
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x0799:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x07a3:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzab(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x07b7
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x07b7:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x07c1:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzy(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x07d5
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x07d5:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x07de:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzx(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x07f2
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x07f2:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x07fb:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzae(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x080f
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x080f:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
            goto L_0x0834
        L_0x0818:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzaf(r4)
            if (r4 <= 0) goto L_0x0911
            boolean r8 = r0.zzadd
            if (r8 == 0) goto L_0x082c
            long r8 = (long) r11
            r2.putInt(r1, r8, r4)
        L_0x082c:
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzak(r15)
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzam(r4)
        L_0x0834:
            int r8 = r8 + r9
            int r8 = r8 + r4
            goto L_0x095a
        L_0x0838:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            r10 = 0
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzq(r15, r4, r10)
            goto L_0x0900
        L_0x0845:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzu(r15, r4, r10)
            goto L_0x0900
        L_0x0852:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r15, r4, r10)
            goto L_0x0900
        L_0x085f:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r15, r4, r10)
            goto L_0x0900
        L_0x086c:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzr(r15, r4, r10)
            goto L_0x0900
        L_0x0879:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzt(r15, r4, r10)
            goto L_0x0910
        L_0x0886:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzd(r15, r4)
            goto L_0x0910
        L_0x0892:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            com.google.android.gms.internal.firebase_auth.zzjs r8 = r0.zzay(r3)
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r15, r4, r8)
            goto L_0x0910
        L_0x08a2:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r15, r4)
            goto L_0x0910
        L_0x08ad:
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            r10 = 0
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzx(r15, r4, r10)
            goto L_0x0900
        L_0x08b9:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r15, r4, r10)
            goto L_0x0900
        L_0x08c5:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r15, r4, r10)
            goto L_0x0900
        L_0x08d1:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzs(r15, r4, r10)
            goto L_0x0900
        L_0x08dd:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzp(r15, r4, r10)
            goto L_0x0900
        L_0x08e9:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzo(r15, r4, r10)
            goto L_0x0900
        L_0x08f5:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzv(r15, r4, r10)
        L_0x0900:
            int r5 = r5 + r4
            r4 = 1
        L_0x0902:
            r7 = 0
            goto L_0x0915
        L_0x0905:
            r10 = 0
            java.lang.Object r4 = r2.getObject(r1, r8)
            java.util.List r4 = (java.util.List) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzw(r15, r4, r10)
        L_0x0910:
            int r5 = r5 + r4
        L_0x0911:
            r4 = 1
        L_0x0912:
            r7 = 0
            r10 = 0
        L_0x0915:
            r13 = 0
            goto L_0x0a2e
        L_0x0919:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            java.lang.Object r4 = r2.getObject(r1, r8)
            com.google.android.gms.internal.firebase_auth.zzjc r4 = (com.google.android.gms.internal.firebase_auth.zzjc) r4
            com.google.android.gms.internal.firebase_auth.zzjs r8 = r0.zzay(r3)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4, r8)
            goto L_0x0910
        L_0x092c:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            long r8 = r2.getLong(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzf(r15, r8)
            goto L_0x0910
        L_0x0939:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            int r4 = r2.getInt(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzl(r15, r4)
            goto L_0x0910
        L_0x0946:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            r8 = 0
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzh(r15, r8)
            goto L_0x0910
        L_0x0951:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            r4 = 0
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzn(r15, r4)
        L_0x095a:
            int r5 = r5 + r8
            goto L_0x0911
        L_0x095c:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            int r4 = r2.getInt(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzo(r15, r4)
            goto L_0x0910
        L_0x0969:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            int r4 = r2.getInt(r1, r8)
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzk(r15, r4)
            goto L_0x0910
        L_0x0976:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            java.lang.Object r4 = r2.getObject(r1, r8)
            com.google.android.gms.internal.firebase_auth.zzgf r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4)
            goto L_0x0910
        L_0x0985:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            java.lang.Object r4 = r2.getObject(r1, r8)
            com.google.android.gms.internal.firebase_auth.zzjs r8 = r0.zzay(r3)
            int r4 = com.google.android.gms.internal.firebase_auth.zzju.zzc(r15, r4, r8)
            goto L_0x0910
        L_0x0997:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            java.lang.Object r4 = r2.getObject(r1, r8)
            boolean r8 = r4 instanceof com.google.android.gms.internal.firebase_auth.zzgf
            if (r8 == 0) goto L_0x09ab
            com.google.android.gms.internal.firebase_auth.zzgf r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzc(r15, r4)
            goto L_0x0910
        L_0x09ab:
            java.lang.String r4 = (java.lang.String) r4
            int r4 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r4)
            goto L_0x0910
        L_0x09b3:
            r4 = r12 & r18
            if (r4 == 0) goto L_0x0911
            r4 = 1
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r15, r4)
            int r5 = r5 + r8
            goto L_0x0912
        L_0x09bf:
            r4 = 1
            r8 = r12 & r18
            r10 = 0
            if (r8 == 0) goto L_0x0902
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzm(r15, r10)
            int r5 = r5 + r8
            goto L_0x0902
        L_0x09cc:
            r4 = 1
            r10 = 0
            r8 = r12 & r18
            r13 = 0
            if (r8 == 0) goto L_0x0a1b
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzg(r15, r13)
            goto L_0x0a0b
        L_0x09d9:
            r4 = 1
            r10 = 0
            r13 = 0
            r11 = r12 & r18
            if (r11 == 0) goto L_0x0a1b
            int r8 = r2.getInt(r1, r8)
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzj(r15, r8)
            goto L_0x0a0b
        L_0x09ea:
            r4 = 1
            r10 = 0
            r13 = 0
            r11 = r12 & r18
            if (r11 == 0) goto L_0x0a1b
            long r8 = r2.getLong(r1, r8)
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zze(r15, r8)
            goto L_0x0a0b
        L_0x09fb:
            r4 = 1
            r10 = 0
            r13 = 0
            r11 = r12 & r18
            if (r11 == 0) goto L_0x0a1b
            long r8 = r2.getLong(r1, r8)
            int r8 = com.google.android.gms.internal.firebase_auth.zzha.zzd(r15, r8)
        L_0x0a0b:
            int r5 = r5 + r8
            goto L_0x0a1b
        L_0x0a0d:
            r4 = 1
            r10 = 0
            r13 = 0
            r8 = r12 & r18
            if (r8 == 0) goto L_0x0a1b
            r8 = 0
            int r9 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r8)
            int r5 = r5 + r9
        L_0x0a1b:
            r7 = 0
            goto L_0x0a2e
        L_0x0a1e:
            r4 = 1
            r8 = 0
            r10 = 0
            r13 = 0
            r9 = r12 & r18
            if (r9 == 0) goto L_0x0a1b
            r7 = 0
            int r11 = com.google.android.gms.internal.firebase_auth.zzha.zzb(r15, r7)
            int r5 = r5 + r11
        L_0x0a2e:
            int r3 = r3 + 3
            r9 = r13
            r4 = 0
            r7 = 1048575(0xfffff, float:1.469367E-39)
            r8 = 1
            r11 = 0
            goto L_0x04f9
        L_0x0a39:
            r10 = 0
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r2 = r0.zzacs
            int r2 = zza(r2, (T) r1)
            int r5 = r5 + r2
            boolean r2 = r0.zzact
            if (r2 == 0) goto L_0x0a93
            com.google.android.gms.internal.firebase_auth.zzhh<?> r2 = r0.zzacu
            com.google.android.gms.internal.firebase_auth.zzhi r1 = r2.zzd(r1)
            r2 = 0
        L_0x0a4c:
            com.google.android.gms.internal.firebase_auth.zzjt<FieldDescriptorType, java.lang.Object> r3 = r1.zzxh
            int r3 = r3.zzjy()
            if (r10 >= r3) goto L_0x0a6c
            com.google.android.gms.internal.firebase_auth.zzjt<FieldDescriptorType, java.lang.Object> r3 = r1.zzxh
            java.util.Map$Entry r3 = r3.zzbf(r10)
            java.lang.Object r4 = r3.getKey()
            com.google.android.gms.internal.firebase_auth.zzhk r4 = (com.google.android.gms.internal.firebase_auth.zzhk) r4
            java.lang.Object r3 = r3.getValue()
            int r3 = com.google.android.gms.internal.firebase_auth.zzhi.zzb(r4, r3)
            int r2 = r2 + r3
            int r10 = r10 + 1
            goto L_0x0a4c
        L_0x0a6c:
            com.google.android.gms.internal.firebase_auth.zzjt<FieldDescriptorType, java.lang.Object> r1 = r1.zzxh
            java.lang.Iterable r1 = r1.zzjz()
            java.util.Iterator r1 = r1.iterator()
        L_0x0a76:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0a92
            java.lang.Object r3 = r1.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            com.google.android.gms.internal.firebase_auth.zzhk r4 = (com.google.android.gms.internal.firebase_auth.zzhk) r4
            java.lang.Object r3 = r3.getValue()
            int r3 = com.google.android.gms.internal.firebase_auth.zzhi.zzb(r4, r3)
            int r2 = r2 + r3
            goto L_0x0a76
        L_0x0a92:
            int r5 = r5 + r2
        L_0x0a93:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.zzq(java.lang.Object):int");
    }

    private static <UT, UB> int zza(zzkk<UT, UB> zzkk, T t) {
        return zzkk.zzq(zzkk.zzs(t));
    }

    private static List<?> zze(Object obj, long j) {
        return (List) zzkq.zzp(obj, j);
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x003b  */
    /* JADX WARNING: Removed duplicated region for block: B:163:0x0513  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x0553  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x0a2b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r14, com.google.android.gms.internal.firebase_auth.zzlh r15) throws java.io.IOException {
        /*
            r13 = this;
            int r0 = r15.zzhl()
            int r1 = com.google.android.gms.internal.firebase_auth.zzhs.zzd.zzaaz
            r2 = 267386880(0xff00000, float:2.3665827E-29)
            r3 = 0
            r4 = 1
            r5 = 0
            r6 = 1048575(0xfffff, float:1.469367E-39)
            if (r0 != r1) goto L_0x0529
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r0 = r13.zzacs
            zza(r0, (T) r14, r15)
            boolean r0 = r13.zzact
            if (r0 == 0) goto L_0x0032
            com.google.android.gms.internal.firebase_auth.zzhh<?> r0 = r13.zzacu
            com.google.android.gms.internal.firebase_auth.zzhi r0 = r0.zzd(r14)
            com.google.android.gms.internal.firebase_auth.zzjt<FieldDescriptorType, java.lang.Object> r1 = r0.zzxh
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x0032
            java.util.Iterator r0 = r0.descendingIterator()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0034
        L_0x0032:
            r0 = r3
            r1 = r0
        L_0x0034:
            int[] r7 = r13.zzacx
            int r7 = r7.length
            int r7 = r7 + -3
        L_0x0039:
            if (r7 < 0) goto L_0x0511
            int r8 = r13.zzbb(r7)
            int[] r9 = r13.zzacx
            r9 = r9[r7]
        L_0x0043:
            if (r1 == 0) goto L_0x0061
            com.google.android.gms.internal.firebase_auth.zzhh<?> r10 = r13.zzacu
            int r10 = r10.zza(r1)
            if (r10 <= r9) goto L_0x0061
            com.google.android.gms.internal.firebase_auth.zzhh<?> r10 = r13.zzacu
            r10.zza(r15, r1)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x005f
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x0043
        L_0x005f:
            r1 = r3
            goto L_0x0043
        L_0x0061:
            r10 = r8 & r2
            int r10 = r10 >>> 20
            switch(r10) {
                case 0: goto L_0x04fe;
                case 1: goto L_0x04ee;
                case 2: goto L_0x04de;
                case 3: goto L_0x04ce;
                case 4: goto L_0x04be;
                case 5: goto L_0x04ae;
                case 6: goto L_0x049e;
                case 7: goto L_0x048d;
                case 8: goto L_0x047c;
                case 9: goto L_0x0467;
                case 10: goto L_0x0454;
                case 11: goto L_0x0443;
                case 12: goto L_0x0432;
                case 13: goto L_0x0421;
                case 14: goto L_0x0410;
                case 15: goto L_0x03ff;
                case 16: goto L_0x03ee;
                case 17: goto L_0x03d9;
                case 18: goto L_0x03c8;
                case 19: goto L_0x03b7;
                case 20: goto L_0x03a6;
                case 21: goto L_0x0395;
                case 22: goto L_0x0384;
                case 23: goto L_0x0373;
                case 24: goto L_0x0362;
                case 25: goto L_0x0351;
                case 26: goto L_0x0340;
                case 27: goto L_0x032b;
                case 28: goto L_0x031a;
                case 29: goto L_0x0309;
                case 30: goto L_0x02f8;
                case 31: goto L_0x02e7;
                case 32: goto L_0x02d6;
                case 33: goto L_0x02c5;
                case 34: goto L_0x02b4;
                case 35: goto L_0x02a3;
                case 36: goto L_0x0292;
                case 37: goto L_0x0281;
                case 38: goto L_0x0270;
                case 39: goto L_0x025f;
                case 40: goto L_0x024e;
                case 41: goto L_0x023d;
                case 42: goto L_0x022c;
                case 43: goto L_0x021b;
                case 44: goto L_0x020a;
                case 45: goto L_0x01f9;
                case 46: goto L_0x01e8;
                case 47: goto L_0x01d7;
                case 48: goto L_0x01c6;
                case 49: goto L_0x01b1;
                case 50: goto L_0x01a6;
                case 51: goto L_0x0195;
                case 52: goto L_0x0184;
                case 53: goto L_0x0173;
                case 54: goto L_0x0162;
                case 55: goto L_0x0151;
                case 56: goto L_0x0140;
                case 57: goto L_0x012f;
                case 58: goto L_0x011e;
                case 59: goto L_0x010d;
                case 60: goto L_0x00f8;
                case 61: goto L_0x00e5;
                case 62: goto L_0x00d4;
                case 63: goto L_0x00c3;
                case 64: goto L_0x00b2;
                case 65: goto L_0x00a1;
                case 66: goto L_0x0090;
                case 67: goto L_0x007f;
                case 68: goto L_0x006a;
                default: goto L_0x0068;
            }
        L_0x0068:
            goto L_0x050d
        L_0x006a:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzjs r10 = r13.zzay(r7)
            r15.zzb(r9, r8, r10)
            goto L_0x050d
        L_0x007f:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zzi(r14, r10)
            r15.zzb(r9, r10)
            goto L_0x050d
        L_0x0090:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzh(r14, r10)
            r15.zzh(r9, r8)
            goto L_0x050d
        L_0x00a1:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zzi(r14, r10)
            r15.zzj(r9, r10)
            goto L_0x050d
        L_0x00b2:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzh(r14, r10)
            r15.zzp(r9, r8)
            goto L_0x050d
        L_0x00c3:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzh(r14, r10)
            r15.zzq(r9, r8)
            goto L_0x050d
        L_0x00d4:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzh(r14, r10)
            r15.zzg(r9, r8)
            goto L_0x050d
        L_0x00e5:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzgf r8 = (com.google.android.gms.internal.firebase_auth.zzgf) r8
            r15.zza(r9, r8)
            goto L_0x050d
        L_0x00f8:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzjs r10 = r13.zzay(r7)
            r15.zza(r9, r8, r10)
            goto L_0x050d
        L_0x010d:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            zza(r9, r8, r15)
            goto L_0x050d
        L_0x011e:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            boolean r8 = zzj(r14, r10)
            r15.zzc(r9, r8)
            goto L_0x050d
        L_0x012f:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzh(r14, r10)
            r15.zzi(r9, r8)
            goto L_0x050d
        L_0x0140:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zzi(r14, r10)
            r15.zzc(r9, r10)
            goto L_0x050d
        L_0x0151:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = zzh(r14, r10)
            r15.zzf(r9, r8)
            goto L_0x050d
        L_0x0162:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zzi(r14, r10)
            r15.zza(r9, r10)
            goto L_0x050d
        L_0x0173:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = zzi(r14, r10)
            r15.zzi(r9, r10)
            goto L_0x050d
        L_0x0184:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            float r8 = zzg(r14, r10)
            r15.zza(r9, r8)
            goto L_0x050d
        L_0x0195:
            boolean r10 = r13.zza((T) r14, r9, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            double r10 = zzf(r14, r10)
            r15.zza(r9, r10)
            goto L_0x050d
        L_0x01a6:
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            r13.zza(r15, r9, r8, r7)
            goto L_0x050d
        L_0x01b1:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzjs r10 = r13.zzay(r7)
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r9, r8, r15, r10)
            goto L_0x050d
        L_0x01c6:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zze(r9, r8, r15, r4)
            goto L_0x050d
        L_0x01d7:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzj(r9, r8, r15, r4)
            goto L_0x050d
        L_0x01e8:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzg(r9, r8, r15, r4)
            goto L_0x050d
        L_0x01f9:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzl(r9, r8, r15, r4)
            goto L_0x050d
        L_0x020a:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzm(r9, r8, r15, r4)
            goto L_0x050d
        L_0x021b:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzi(r9, r8, r15, r4)
            goto L_0x050d
        L_0x022c:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzn(r9, r8, r15, r4)
            goto L_0x050d
        L_0x023d:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzk(r9, r8, r15, r4)
            goto L_0x050d
        L_0x024e:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzf(r9, r8, r15, r4)
            goto L_0x050d
        L_0x025f:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzh(r9, r8, r15, r4)
            goto L_0x050d
        L_0x0270:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzd(r9, r8, r15, r4)
            goto L_0x050d
        L_0x0281:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzc(r9, r8, r15, r4)
            goto L_0x050d
        L_0x0292:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r9, r8, r15, r4)
            goto L_0x050d
        L_0x02a3:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zza(r9, r8, r15, r4)
            goto L_0x050d
        L_0x02b4:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zze(r9, r8, r15, r5)
            goto L_0x050d
        L_0x02c5:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzj(r9, r8, r15, r5)
            goto L_0x050d
        L_0x02d6:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzg(r9, r8, r15, r5)
            goto L_0x050d
        L_0x02e7:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzl(r9, r8, r15, r5)
            goto L_0x050d
        L_0x02f8:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzm(r9, r8, r15, r5)
            goto L_0x050d
        L_0x0309:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzi(r9, r8, r15, r5)
            goto L_0x050d
        L_0x031a:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r9, r8, r15)
            goto L_0x050d
        L_0x032b:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzjs r10 = r13.zzay(r7)
            com.google.android.gms.internal.firebase_auth.zzju.zza(r9, r8, r15, r10)
            goto L_0x050d
        L_0x0340:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zza(r9, r8, r15)
            goto L_0x050d
        L_0x0351:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzn(r9, r8, r15, r5)
            goto L_0x050d
        L_0x0362:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzk(r9, r8, r15, r5)
            goto L_0x050d
        L_0x0373:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzf(r9, r8, r15, r5)
            goto L_0x050d
        L_0x0384:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzh(r9, r8, r15, r5)
            goto L_0x050d
        L_0x0395:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzd(r9, r8, r15, r5)
            goto L_0x050d
        L_0x03a6:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzc(r9, r8, r15, r5)
            goto L_0x050d
        L_0x03b7:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r9, r8, r15, r5)
            goto L_0x050d
        L_0x03c8:
            int[] r9 = r13.zzacx
            r9 = r9[r7]
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            java.util.List r8 = (java.util.List) r8
            com.google.android.gms.internal.firebase_auth.zzju.zza(r9, r8, r15, r5)
            goto L_0x050d
        L_0x03d9:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzjs r10 = r13.zzay(r7)
            r15.zzb(r9, r8, r10)
            goto L_0x050d
        L_0x03ee:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10)
            r15.zzb(r9, r10)
            goto L_0x050d
        L_0x03ff:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10)
            r15.zzh(r9, r8)
            goto L_0x050d
        L_0x0410:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10)
            r15.zzj(r9, r10)
            goto L_0x050d
        L_0x0421:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10)
            r15.zzp(r9, r8)
            goto L_0x050d
        L_0x0432:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10)
            r15.zzq(r9, r8)
            goto L_0x050d
        L_0x0443:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10)
            r15.zzg(r9, r8)
            goto L_0x050d
        L_0x0454:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzgf r8 = (com.google.android.gms.internal.firebase_auth.zzgf) r8
            r15.zza(r9, r8)
            goto L_0x050d
        L_0x0467:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            com.google.android.gms.internal.firebase_auth.zzjs r10 = r13.zzay(r7)
            r15.zza(r9, r8, r10)
            goto L_0x050d
        L_0x047c:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            java.lang.Object r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r10)
            zza(r9, r8, r15)
            goto L_0x050d
        L_0x048d:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            boolean r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r14, r10)
            r15.zzc(r9, r8)
            goto L_0x050d
        L_0x049e:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10)
            r15.zzi(r9, r8)
            goto L_0x050d
        L_0x04ae:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10)
            r15.zzc(r9, r10)
            goto L_0x050d
        L_0x04be:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            int r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r10)
            r15.zzf(r9, r8)
            goto L_0x050d
        L_0x04ce:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10)
            r15.zza(r9, r10)
            goto L_0x050d
        L_0x04de:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            long r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r10)
            r15.zzi(r9, r10)
            goto L_0x050d
        L_0x04ee:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            float r8 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r14, r10)
            r15.zza(r9, r8)
            goto L_0x050d
        L_0x04fe:
            boolean r10 = r13.zza((T) r14, r7)
            if (r10 == 0) goto L_0x050d
            r8 = r8 & r6
            long r10 = (long) r8
            double r10 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r14, r10)
            r15.zza(r9, r10)
        L_0x050d:
            int r7 = r7 + -3
            goto L_0x0039
        L_0x0511:
            if (r1 == 0) goto L_0x0528
            com.google.android.gms.internal.firebase_auth.zzhh<?> r14 = r13.zzacu
            r14.zza(r15, r1)
            boolean r14 = r0.hasNext()
            if (r14 == 0) goto L_0x0526
            java.lang.Object r14 = r0.next()
            java.util.Map$Entry r14 = (java.util.Map.Entry) r14
            r1 = r14
            goto L_0x0511
        L_0x0526:
            r1 = r3
            goto L_0x0511
        L_0x0528:
            return
        L_0x0529:
            boolean r0 = r13.zzadc
            if (r0 == 0) goto L_0x0a46
            boolean r0 = r13.zzact
            if (r0 == 0) goto L_0x054a
            com.google.android.gms.internal.firebase_auth.zzhh<?> r0 = r13.zzacu
            com.google.android.gms.internal.firebase_auth.zzhi r0 = r0.zzd(r14)
            com.google.android.gms.internal.firebase_auth.zzjt<FieldDescriptorType, java.lang.Object> r1 = r0.zzxh
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x054a
            java.util.Iterator r0 = r0.iterator()
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            goto L_0x054c
        L_0x054a:
            r0 = r3
            r1 = r0
        L_0x054c:
            int[] r7 = r13.zzacx
            int r7 = r7.length
            r8 = r1
            r1 = 0
        L_0x0551:
            if (r1 >= r7) goto L_0x0a29
            int r9 = r13.zzbb(r1)
            int[] r10 = r13.zzacx
            r10 = r10[r1]
        L_0x055b:
            if (r8 == 0) goto L_0x0579
            com.google.android.gms.internal.firebase_auth.zzhh<?> r11 = r13.zzacu
            int r11 = r11.zza(r8)
            if (r11 > r10) goto L_0x0579
            com.google.android.gms.internal.firebase_auth.zzhh<?> r11 = r13.zzacu
            r11.zza(r15, r8)
            boolean r8 = r0.hasNext()
            if (r8 == 0) goto L_0x0577
            java.lang.Object r8 = r0.next()
            java.util.Map$Entry r8 = (java.util.Map.Entry) r8
            goto L_0x055b
        L_0x0577:
            r8 = r3
            goto L_0x055b
        L_0x0579:
            r11 = r9 & r2
            int r11 = r11 >>> 20
            switch(r11) {
                case 0: goto L_0x0a16;
                case 1: goto L_0x0a06;
                case 2: goto L_0x09f6;
                case 3: goto L_0x09e6;
                case 4: goto L_0x09d6;
                case 5: goto L_0x09c6;
                case 6: goto L_0x09b6;
                case 7: goto L_0x09a5;
                case 8: goto L_0x0994;
                case 9: goto L_0x097f;
                case 10: goto L_0x096c;
                case 11: goto L_0x095b;
                case 12: goto L_0x094a;
                case 13: goto L_0x0939;
                case 14: goto L_0x0928;
                case 15: goto L_0x0917;
                case 16: goto L_0x0906;
                case 17: goto L_0x08f1;
                case 18: goto L_0x08e0;
                case 19: goto L_0x08cf;
                case 20: goto L_0x08be;
                case 21: goto L_0x08ad;
                case 22: goto L_0x089c;
                case 23: goto L_0x088b;
                case 24: goto L_0x087a;
                case 25: goto L_0x0869;
                case 26: goto L_0x0858;
                case 27: goto L_0x0843;
                case 28: goto L_0x0832;
                case 29: goto L_0x0821;
                case 30: goto L_0x0810;
                case 31: goto L_0x07ff;
                case 32: goto L_0x07ee;
                case 33: goto L_0x07dd;
                case 34: goto L_0x07cc;
                case 35: goto L_0x07bb;
                case 36: goto L_0x07aa;
                case 37: goto L_0x0799;
                case 38: goto L_0x0788;
                case 39: goto L_0x0777;
                case 40: goto L_0x0766;
                case 41: goto L_0x0755;
                case 42: goto L_0x0744;
                case 43: goto L_0x0733;
                case 44: goto L_0x0722;
                case 45: goto L_0x0711;
                case 46: goto L_0x0700;
                case 47: goto L_0x06ef;
                case 48: goto L_0x06de;
                case 49: goto L_0x06c9;
                case 50: goto L_0x06be;
                case 51: goto L_0x06ad;
                case 52: goto L_0x069c;
                case 53: goto L_0x068b;
                case 54: goto L_0x067a;
                case 55: goto L_0x0669;
                case 56: goto L_0x0658;
                case 57: goto L_0x0647;
                case 58: goto L_0x0636;
                case 59: goto L_0x0625;
                case 60: goto L_0x0610;
                case 61: goto L_0x05fd;
                case 62: goto L_0x05ec;
                case 63: goto L_0x05db;
                case 64: goto L_0x05ca;
                case 65: goto L_0x05b9;
                case 66: goto L_0x05a8;
                case 67: goto L_0x0597;
                case 68: goto L_0x0582;
                default: goto L_0x0580;
            }
        L_0x0580:
            goto L_0x0a25
        L_0x0582:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzjs r11 = r13.zzay(r1)
            r15.zzb(r10, r9, r11)
            goto L_0x0a25
        L_0x0597:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zzi(r14, r11)
            r15.zzb(r10, r11)
            goto L_0x0a25
        L_0x05a8:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzh(r14, r11)
            r15.zzh(r10, r9)
            goto L_0x0a25
        L_0x05b9:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zzi(r14, r11)
            r15.zzj(r10, r11)
            goto L_0x0a25
        L_0x05ca:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzh(r14, r11)
            r15.zzp(r10, r9)
            goto L_0x0a25
        L_0x05db:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzh(r14, r11)
            r15.zzq(r10, r9)
            goto L_0x0a25
        L_0x05ec:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzh(r14, r11)
            r15.zzg(r10, r9)
            goto L_0x0a25
        L_0x05fd:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzgf r9 = (com.google.android.gms.internal.firebase_auth.zzgf) r9
            r15.zza(r10, r9)
            goto L_0x0a25
        L_0x0610:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzjs r11 = r13.zzay(r1)
            r15.zza(r10, r9, r11)
            goto L_0x0a25
        L_0x0625:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            zza(r10, r9, r15)
            goto L_0x0a25
        L_0x0636:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            boolean r9 = zzj(r14, r11)
            r15.zzc(r10, r9)
            goto L_0x0a25
        L_0x0647:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzh(r14, r11)
            r15.zzi(r10, r9)
            goto L_0x0a25
        L_0x0658:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zzi(r14, r11)
            r15.zzc(r10, r11)
            goto L_0x0a25
        L_0x0669:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = zzh(r14, r11)
            r15.zzf(r10, r9)
            goto L_0x0a25
        L_0x067a:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zzi(r14, r11)
            r15.zza(r10, r11)
            goto L_0x0a25
        L_0x068b:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = zzi(r14, r11)
            r15.zzi(r10, r11)
            goto L_0x0a25
        L_0x069c:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            float r9 = zzg(r14, r11)
            r15.zza(r10, r9)
            goto L_0x0a25
        L_0x06ad:
            boolean r11 = r13.zza((T) r14, r10, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            double r11 = zzf(r14, r11)
            r15.zza(r10, r11)
            goto L_0x0a25
        L_0x06be:
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            r13.zza(r15, r10, r9, r1)
            goto L_0x0a25
        L_0x06c9:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzjs r11 = r13.zzay(r1)
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r10, r9, r15, r11)
            goto L_0x0a25
        L_0x06de:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zze(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x06ef:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzj(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0700:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzg(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0711:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzl(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0722:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzm(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0733:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzi(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0744:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzn(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0755:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzk(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0766:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzf(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0777:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzh(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0788:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzd(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x0799:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzc(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x07aa:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x07bb:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zza(r10, r9, r15, r4)
            goto L_0x0a25
        L_0x07cc:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zze(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x07dd:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzj(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x07ee:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzg(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x07ff:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzl(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x0810:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzm(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x0821:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzi(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x0832:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r10, r9, r15)
            goto L_0x0a25
        L_0x0843:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzjs r11 = r13.zzay(r1)
            com.google.android.gms.internal.firebase_auth.zzju.zza(r10, r9, r15, r11)
            goto L_0x0a25
        L_0x0858:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zza(r10, r9, r15)
            goto L_0x0a25
        L_0x0869:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzn(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x087a:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzk(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x088b:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzf(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x089c:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzh(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x08ad:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzd(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x08be:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzc(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x08cf:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x08e0:
            int[] r10 = r13.zzacx
            r10 = r10[r1]
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zza(r10, r9, r15, r5)
            goto L_0x0a25
        L_0x08f1:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzjs r11 = r13.zzay(r1)
            r15.zzb(r10, r9, r11)
            goto L_0x0a25
        L_0x0906:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11)
            r15.zzb(r10, r11)
            goto L_0x0a25
        L_0x0917:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11)
            r15.zzh(r10, r9)
            goto L_0x0a25
        L_0x0928:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11)
            r15.zzj(r10, r11)
            goto L_0x0a25
        L_0x0939:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11)
            r15.zzp(r10, r9)
            goto L_0x0a25
        L_0x094a:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11)
            r15.zzq(r10, r9)
            goto L_0x0a25
        L_0x095b:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11)
            r15.zzg(r10, r9)
            goto L_0x0a25
        L_0x096c:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzgf r9 = (com.google.android.gms.internal.firebase_auth.zzgf) r9
            r15.zza(r10, r9)
            goto L_0x0a25
        L_0x097f:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            com.google.android.gms.internal.firebase_auth.zzjs r11 = r13.zzay(r1)
            r15.zza(r10, r9, r11)
            goto L_0x0a25
        L_0x0994:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            java.lang.Object r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r14, r11)
            zza(r10, r9, r15)
            goto L_0x0a25
        L_0x09a5:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            boolean r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r14, r11)
            r15.zzc(r10, r9)
            goto L_0x0a25
        L_0x09b6:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11)
            r15.zzi(r10, r9)
            goto L_0x0a25
        L_0x09c6:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11)
            r15.zzc(r10, r11)
            goto L_0x0a25
        L_0x09d6:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            int r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzk(r14, r11)
            r15.zzf(r10, r9)
            goto L_0x0a25
        L_0x09e6:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11)
            r15.zza(r10, r11)
            goto L_0x0a25
        L_0x09f6:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            long r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzl(r14, r11)
            r15.zzi(r10, r11)
            goto L_0x0a25
        L_0x0a06:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            float r9 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r14, r11)
            r15.zza(r10, r9)
            goto L_0x0a25
        L_0x0a16:
            boolean r11 = r13.zza((T) r14, r1)
            if (r11 == 0) goto L_0x0a25
            r9 = r9 & r6
            long r11 = (long) r9
            double r11 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r14, r11)
            r15.zza(r10, r11)
        L_0x0a25:
            int r1 = r1 + 3
            goto L_0x0551
        L_0x0a29:
            if (r8 == 0) goto L_0x0a40
            com.google.android.gms.internal.firebase_auth.zzhh<?> r1 = r13.zzacu
            r1.zza(r15, r8)
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0a3e
            java.lang.Object r1 = r0.next()
            java.util.Map$Entry r1 = (java.util.Map.Entry) r1
            r8 = r1
            goto L_0x0a29
        L_0x0a3e:
            r8 = r3
            goto L_0x0a29
        L_0x0a40:
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r0 = r13.zzacs
            zza(r0, (T) r14, r15)
            return
        L_0x0a46:
            r13.zzb((T) r14, r15)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.zza(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzlh):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:172:0x04b3  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void zzb(T r19, com.google.android.gms.internal.firebase_auth.zzlh r20) throws java.io.IOException {
        /*
            r18 = this;
            r0 = r18
            r1 = r19
            r2 = r20
            boolean r3 = r0.zzact
            if (r3 == 0) goto L_0x0023
            com.google.android.gms.internal.firebase_auth.zzhh<?> r3 = r0.zzacu
            com.google.android.gms.internal.firebase_auth.zzhi r3 = r3.zzd(r1)
            com.google.android.gms.internal.firebase_auth.zzjt<FieldDescriptorType, java.lang.Object> r5 = r3.zzxh
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L_0x0023
            java.util.Iterator r3 = r3.iterator()
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            goto L_0x0025
        L_0x0023:
            r3 = 0
            r5 = 0
        L_0x0025:
            r6 = -1
            int[] r7 = r0.zzacx
            int r7 = r7.length
            sun.misc.Unsafe r8 = zzacw
            r10 = r5
            r5 = 0
            r11 = 0
        L_0x002e:
            if (r5 >= r7) goto L_0x04ad
            int r12 = r0.zzbb(r5)
            int[] r13 = r0.zzacx
            r14 = r13[r5]
            r15 = 267386880(0xff00000, float:2.3665827E-29)
            r15 = r15 & r12
            int r15 = r15 >>> 20
            boolean r4 = r0.zzadc
            r16 = 1048575(0xfffff, float:1.469367E-39)
            if (r4 != 0) goto L_0x0062
            r4 = 17
            if (r15 > r4) goto L_0x0062
            int r4 = r5 + 2
            r4 = r13[r4]
            r13 = r4 & r16
            r17 = r10
            if (r13 == r6) goto L_0x0058
            long r9 = (long) r13
            int r11 = r8.getInt(r1, r9)
            goto L_0x0059
        L_0x0058:
            r13 = r6
        L_0x0059:
            int r4 = r4 >>> 20
            r6 = 1
            int r9 = r6 << r4
            r6 = r13
            r10 = r17
            goto L_0x0067
        L_0x0062:
            r17 = r10
            r10 = r17
            r9 = 0
        L_0x0067:
            if (r10 == 0) goto L_0x0086
            com.google.android.gms.internal.firebase_auth.zzhh<?> r4 = r0.zzacu
            int r4 = r4.zza(r10)
            if (r4 > r14) goto L_0x0086
            com.google.android.gms.internal.firebase_auth.zzhh<?> r4 = r0.zzacu
            r4.zza(r2, r10)
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0084
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            r10 = r4
            goto L_0x0067
        L_0x0084:
            r10 = 0
            goto L_0x0067
        L_0x0086:
            r4 = r12 & r16
            long r12 = (long) r4
            switch(r15) {
                case 0: goto L_0x049d;
                case 1: goto L_0x0490;
                case 2: goto L_0x0483;
                case 3: goto L_0x0476;
                case 4: goto L_0x0469;
                case 5: goto L_0x045c;
                case 6: goto L_0x044f;
                case 7: goto L_0x0442;
                case 8: goto L_0x0434;
                case 9: goto L_0x0422;
                case 10: goto L_0x0412;
                case 11: goto L_0x0404;
                case 12: goto L_0x03f6;
                case 13: goto L_0x03e8;
                case 14: goto L_0x03da;
                case 15: goto L_0x03cc;
                case 16: goto L_0x03be;
                case 17: goto L_0x03ac;
                case 18: goto L_0x039c;
                case 19: goto L_0x038c;
                case 20: goto L_0x037c;
                case 21: goto L_0x036c;
                case 22: goto L_0x035c;
                case 23: goto L_0x034c;
                case 24: goto L_0x033c;
                case 25: goto L_0x032c;
                case 26: goto L_0x031d;
                case 27: goto L_0x030a;
                case 28: goto L_0x02fb;
                case 29: goto L_0x02eb;
                case 30: goto L_0x02db;
                case 31: goto L_0x02cb;
                case 32: goto L_0x02bb;
                case 33: goto L_0x02ab;
                case 34: goto L_0x029b;
                case 35: goto L_0x028b;
                case 36: goto L_0x027b;
                case 37: goto L_0x026b;
                case 38: goto L_0x025b;
                case 39: goto L_0x024b;
                case 40: goto L_0x023b;
                case 41: goto L_0x022b;
                case 42: goto L_0x021b;
                case 43: goto L_0x020b;
                case 44: goto L_0x01fb;
                case 45: goto L_0x01eb;
                case 46: goto L_0x01db;
                case 47: goto L_0x01cb;
                case 48: goto L_0x01bb;
                case 49: goto L_0x01a8;
                case 50: goto L_0x019f;
                case 51: goto L_0x0190;
                case 52: goto L_0x0181;
                case 53: goto L_0x0172;
                case 54: goto L_0x0163;
                case 55: goto L_0x0154;
                case 56: goto L_0x0145;
                case 57: goto L_0x0136;
                case 58: goto L_0x0127;
                case 59: goto L_0x0118;
                case 60: goto L_0x0105;
                case 61: goto L_0x00f5;
                case 62: goto L_0x00e7;
                case 63: goto L_0x00d9;
                case 64: goto L_0x00cb;
                case 65: goto L_0x00bd;
                case 66: goto L_0x00af;
                case 67: goto L_0x00a1;
                case 68: goto L_0x008f;
                default: goto L_0x008c;
            }
        L_0x008c:
            r15 = 0
            goto L_0x04a9
        L_0x008f:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            java.lang.Object r4 = r8.getObject(r1, r12)
            com.google.android.gms.internal.firebase_auth.zzjs r9 = r0.zzay(r5)
            r2.zzb(r14, r4, r9)
            goto L_0x008c
        L_0x00a1:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            long r12 = zzi(r1, r12)
            r2.zzb(r14, r12)
            goto L_0x008c
        L_0x00af:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            int r4 = zzh(r1, r12)
            r2.zzh(r14, r4)
            goto L_0x008c
        L_0x00bd:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            long r12 = zzi(r1, r12)
            r2.zzj(r14, r12)
            goto L_0x008c
        L_0x00cb:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            int r4 = zzh(r1, r12)
            r2.zzp(r14, r4)
            goto L_0x008c
        L_0x00d9:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            int r4 = zzh(r1, r12)
            r2.zzq(r14, r4)
            goto L_0x008c
        L_0x00e7:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            int r4 = zzh(r1, r12)
            r2.zzg(r14, r4)
            goto L_0x008c
        L_0x00f5:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            java.lang.Object r4 = r8.getObject(r1, r12)
            com.google.android.gms.internal.firebase_auth.zzgf r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4
            r2.zza(r14, r4)
            goto L_0x008c
        L_0x0105:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            java.lang.Object r4 = r8.getObject(r1, r12)
            com.google.android.gms.internal.firebase_auth.zzjs r9 = r0.zzay(r5)
            r2.zza(r14, r4, r9)
            goto L_0x008c
        L_0x0118:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            java.lang.Object r4 = r8.getObject(r1, r12)
            zza(r14, r4, r2)
            goto L_0x008c
        L_0x0127:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            boolean r4 = zzj(r1, r12)
            r2.zzc(r14, r4)
            goto L_0x008c
        L_0x0136:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            int r4 = zzh(r1, r12)
            r2.zzi(r14, r4)
            goto L_0x008c
        L_0x0145:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            long r12 = zzi(r1, r12)
            r2.zzc(r14, r12)
            goto L_0x008c
        L_0x0154:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            int r4 = zzh(r1, r12)
            r2.zzf(r14, r4)
            goto L_0x008c
        L_0x0163:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            long r12 = zzi(r1, r12)
            r2.zza(r14, r12)
            goto L_0x008c
        L_0x0172:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            long r12 = zzi(r1, r12)
            r2.zzi(r14, r12)
            goto L_0x008c
        L_0x0181:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            float r4 = zzg(r1, r12)
            r2.zza(r14, r4)
            goto L_0x008c
        L_0x0190:
            boolean r4 = r0.zza((T) r1, r14, r5)
            if (r4 == 0) goto L_0x008c
            double r12 = zzf(r1, r12)
            r2.zza(r14, r12)
            goto L_0x008c
        L_0x019f:
            java.lang.Object r4 = r8.getObject(r1, r12)
            r0.zza(r2, r14, r4, r5)
            goto L_0x008c
        L_0x01a8:
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzjs r12 = r0.zzay(r5)
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r4, r9, r2, r12)
            goto L_0x008c
        L_0x01bb:
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            r14 = 1
            com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r9, r2, r14)
            goto L_0x008c
        L_0x01cb:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzj(r4, r9, r2, r14)
            goto L_0x008c
        L_0x01db:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzg(r4, r9, r2, r14)
            goto L_0x008c
        L_0x01eb:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzl(r4, r9, r2, r14)
            goto L_0x008c
        L_0x01fb:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzm(r4, r9, r2, r14)
            goto L_0x008c
        L_0x020b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzi(r4, r9, r2, r14)
            goto L_0x008c
        L_0x021b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzn(r4, r9, r2, r14)
            goto L_0x008c
        L_0x022b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzk(r4, r9, r2, r14)
            goto L_0x008c
        L_0x023b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzf(r4, r9, r2, r14)
            goto L_0x008c
        L_0x024b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzh(r4, r9, r2, r14)
            goto L_0x008c
        L_0x025b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzd(r4, r9, r2, r14)
            goto L_0x008c
        L_0x026b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzc(r4, r9, r2, r14)
            goto L_0x008c
        L_0x027b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r4, r9, r2, r14)
            goto L_0x008c
        L_0x028b:
            r14 = 1
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r9, r2, r14)
            goto L_0x008c
        L_0x029b:
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            r14 = 0
            com.google.android.gms.internal.firebase_auth.zzju.zze(r4, r9, r2, r14)
            goto L_0x008c
        L_0x02ab:
            r14 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzj(r4, r9, r2, r14)
            goto L_0x008c
        L_0x02bb:
            r14 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzg(r4, r9, r2, r14)
            goto L_0x008c
        L_0x02cb:
            r14 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzl(r4, r9, r2, r14)
            goto L_0x008c
        L_0x02db:
            r14 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzm(r4, r9, r2, r14)
            goto L_0x008c
        L_0x02eb:
            r14 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzi(r4, r9, r2, r14)
            goto L_0x008c
        L_0x02fb:
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r4, r9, r2)
            goto L_0x008c
        L_0x030a:
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzjs r12 = r0.zzay(r5)
            com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r9, r2, r12)
            goto L_0x008c
        L_0x031d:
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r9, r2)
            goto L_0x008c
        L_0x032c:
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            r15 = 0
            com.google.android.gms.internal.firebase_auth.zzju.zzn(r4, r9, r2, r15)
            goto L_0x04a9
        L_0x033c:
            r15 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzk(r4, r9, r2, r15)
            goto L_0x04a9
        L_0x034c:
            r15 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzf(r4, r9, r2, r15)
            goto L_0x04a9
        L_0x035c:
            r15 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzh(r4, r9, r2, r15)
            goto L_0x04a9
        L_0x036c:
            r15 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzd(r4, r9, r2, r15)
            goto L_0x04a9
        L_0x037c:
            r15 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzc(r4, r9, r2, r15)
            goto L_0x04a9
        L_0x038c:
            r15 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zzb(r4, r9, r2, r15)
            goto L_0x04a9
        L_0x039c:
            r15 = 0
            int[] r4 = r0.zzacx
            r4 = r4[r5]
            java.lang.Object r9 = r8.getObject(r1, r12)
            java.util.List r9 = (java.util.List) r9
            com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r9, r2, r15)
            goto L_0x04a9
        L_0x03ac:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            java.lang.Object r4 = r8.getObject(r1, r12)
            com.google.android.gms.internal.firebase_auth.zzjs r9 = r0.zzay(r5)
            r2.zzb(r14, r4, r9)
            goto L_0x04a9
        L_0x03be:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            long r12 = r8.getLong(r1, r12)
            r2.zzb(r14, r12)
            goto L_0x04a9
        L_0x03cc:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            int r4 = r8.getInt(r1, r12)
            r2.zzh(r14, r4)
            goto L_0x04a9
        L_0x03da:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            long r12 = r8.getLong(r1, r12)
            r2.zzj(r14, r12)
            goto L_0x04a9
        L_0x03e8:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            int r4 = r8.getInt(r1, r12)
            r2.zzp(r14, r4)
            goto L_0x04a9
        L_0x03f6:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            int r4 = r8.getInt(r1, r12)
            r2.zzq(r14, r4)
            goto L_0x04a9
        L_0x0404:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            int r4 = r8.getInt(r1, r12)
            r2.zzg(r14, r4)
            goto L_0x04a9
        L_0x0412:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            java.lang.Object r4 = r8.getObject(r1, r12)
            com.google.android.gms.internal.firebase_auth.zzgf r4 = (com.google.android.gms.internal.firebase_auth.zzgf) r4
            r2.zza(r14, r4)
            goto L_0x04a9
        L_0x0422:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            java.lang.Object r4 = r8.getObject(r1, r12)
            com.google.android.gms.internal.firebase_auth.zzjs r9 = r0.zzay(r5)
            r2.zza(r14, r4, r9)
            goto L_0x04a9
        L_0x0434:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            java.lang.Object r4 = r8.getObject(r1, r12)
            zza(r14, r4, r2)
            goto L_0x04a9
        L_0x0442:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            boolean r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzm(r1, r12)
            r2.zzc(r14, r4)
            goto L_0x04a9
        L_0x044f:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            int r4 = r8.getInt(r1, r12)
            r2.zzi(r14, r4)
            goto L_0x04a9
        L_0x045c:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            long r12 = r8.getLong(r1, r12)
            r2.zzc(r14, r12)
            goto L_0x04a9
        L_0x0469:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            int r4 = r8.getInt(r1, r12)
            r2.zzf(r14, r4)
            goto L_0x04a9
        L_0x0476:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            long r12 = r8.getLong(r1, r12)
            r2.zza(r14, r12)
            goto L_0x04a9
        L_0x0483:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            long r12 = r8.getLong(r1, r12)
            r2.zzi(r14, r12)
            goto L_0x04a9
        L_0x0490:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            float r4 = com.google.android.gms.internal.firebase_auth.zzkq.zzn(r1, r12)
            r2.zza(r14, r4)
            goto L_0x04a9
        L_0x049d:
            r15 = 0
            r4 = r11 & r9
            if (r4 == 0) goto L_0x04a9
            double r12 = com.google.android.gms.internal.firebase_auth.zzkq.zzo(r1, r12)
            r2.zza(r14, r12)
        L_0x04a9:
            int r5 = r5 + 3
            goto L_0x002e
        L_0x04ad:
            r17 = r10
            r4 = r17
        L_0x04b1:
            if (r4 == 0) goto L_0x04c7
            com.google.android.gms.internal.firebase_auth.zzhh<?> r5 = r0.zzacu
            r5.zza(r2, r4)
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x04c5
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            goto L_0x04b1
        L_0x04c5:
            r4 = 0
            goto L_0x04b1
        L_0x04c7:
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r3 = r0.zzacs
            zza(r3, (T) r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.zzb(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzlh):void");
    }

    private final <K, V> void zza(zzlh zzlh, int i, Object obj, int i2) throws IOException {
        if (obj != null) {
            zzlh.zza(i, this.zzadj.zzn(zzaz(i2)), this.zzadj.zzj(obj));
        }
    }

    private static <UT, UB> void zza(zzkk<UT, UB> zzkk, T t, zzlh zzlh) throws IOException {
        zzkk.zza(zzkk.zzs(t), zzlh);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:164:?, code lost:
        r11.zza(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x05b1, code lost:
        if (r14 == null) goto L_0x05b3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x05b3, code lost:
        r14 = r11.zzt(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x05bc, code lost:
        if (r11.zza(r14, r0) == false) goto L_0x05be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x05be, code lost:
        r0 = r1.zzadf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x05c2, code lost:
        if (r0 < r1.zzadg) goto L_0x05c4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x05c4, code lost:
        r14 = zza((java.lang.Object) r2, r1.zzade[r0], (UB) r14, r11);
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x05cf, code lost:
        if (r14 != null) goto L_0x05d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x05d1, code lost:
        r11.zzg(r2, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x05d4, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:163:0x05ae */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r18, com.google.android.gms.internal.firebase_auth.zzjp r19, com.google.android.gms.internal.firebase_auth.zzhf r20) throws java.io.IOException {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            r0 = r19
            r10 = r20
            if (r10 == 0) goto L_0x05ed
            com.google.android.gms.internal.firebase_auth.zzkk<?, ?> r11 = r1.zzacs
            com.google.android.gms.internal.firebase_auth.zzhh<?> r12 = r1.zzacu
            r13 = 0
            r3 = r13
            r14 = r3
        L_0x0011:
            int r4 = r19.zzhg()     // Catch:{ all -> 0x05d5 }
            int r5 = r1.zzacz     // Catch:{ all -> 0x05d5 }
            r6 = -1
            if (r4 < r5) goto L_0x003e
            int r5 = r1.zzada     // Catch:{ all -> 0x05d5 }
            if (r4 > r5) goto L_0x003e
            r5 = 0
            int[] r7 = r1.zzacx     // Catch:{ all -> 0x05d5 }
            int r7 = r7.length     // Catch:{ all -> 0x05d5 }
            int r7 = r7 / 3
            int r7 = r7 + -1
        L_0x0026:
            if (r5 > r7) goto L_0x003e
            int r8 = r7 + r5
            int r8 = r8 >>> 1
            int r9 = r8 * 3
            int[] r15 = r1.zzacx     // Catch:{ all -> 0x05d5 }
            r15 = r15[r9]     // Catch:{ all -> 0x05d5 }
            if (r4 != r15) goto L_0x0036
            r6 = r9
            goto L_0x003e
        L_0x0036:
            if (r4 >= r15) goto L_0x003b
            int r7 = r8 + -1
            goto L_0x0026
        L_0x003b:
            int r5 = r8 + 1
            goto L_0x0026
        L_0x003e:
            if (r6 >= 0) goto L_0x00a6
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r4 != r5) goto L_0x005c
            int r0 = r1.zzadf
        L_0x0047:
            int r3 = r1.zzadg
            if (r0 >= r3) goto L_0x0056
            int[] r3 = r1.zzade
            r3 = r3[r0]
            java.lang.Object r14 = r1.zza(r2, r3, (UB) r14, r11)
            int r0 = r0 + 1
            goto L_0x0047
        L_0x0056:
            if (r14 == 0) goto L_0x005b
            r11.zzg(r2, r14)
        L_0x005b:
            return
        L_0x005c:
            boolean r5 = r1.zzact     // Catch:{ all -> 0x05d5 }
            if (r5 != 0) goto L_0x0062
            r5 = r13
            goto L_0x0069
        L_0x0062:
            com.google.android.gms.internal.firebase_auth.zzjc r5 = r1.zzacr     // Catch:{ all -> 0x05d5 }
            java.lang.Object r4 = r12.zza(r10, r5, r4)     // Catch:{ all -> 0x05d5 }
            r5 = r4
        L_0x0069:
            if (r5 == 0) goto L_0x0080
            if (r3 != 0) goto L_0x0071
            com.google.android.gms.internal.firebase_auth.zzhi r3 = r12.zze(r2)     // Catch:{ all -> 0x05d5 }
        L_0x0071:
            r15 = r3
            r3 = r12
            r4 = r19
            r6 = r20
            r7 = r15
            r8 = r14
            r9 = r11
            java.lang.Object r14 = r3.zza(r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x05d5 }
            r3 = r15
            goto L_0x0011
        L_0x0080:
            r11.zza(r0)     // Catch:{ all -> 0x05d5 }
            if (r14 != 0) goto L_0x0089
            java.lang.Object r14 = r11.zzt(r2)     // Catch:{ all -> 0x05d5 }
        L_0x0089:
            boolean r4 = r11.zza(r14, r0)     // Catch:{ all -> 0x05d5 }
            if (r4 != 0) goto L_0x0011
            int r0 = r1.zzadf
        L_0x0091:
            int r3 = r1.zzadg
            if (r0 >= r3) goto L_0x00a0
            int[] r3 = r1.zzade
            r3 = r3[r0]
            java.lang.Object r14 = r1.zza(r2, r3, (UB) r14, r11)
            int r0 = r0 + 1
            goto L_0x0091
        L_0x00a0:
            if (r14 == 0) goto L_0x00a5
            r11.zzg(r2, r14)
        L_0x00a5:
            return
        L_0x00a6:
            int r5 = r1.zzbb(r6)     // Catch:{ all -> 0x05d5 }
            r7 = 267386880(0xff00000, float:2.3665827E-29)
            r7 = r7 & r5
            int r7 = r7 >>> 20
            r8 = 1048575(0xfffff, float:1.469367E-39)
            switch(r7) {
                case 0: goto L_0x0582;
                case 1: goto L_0x0573;
                case 2: goto L_0x0564;
                case 3: goto L_0x0555;
                case 4: goto L_0x0546;
                case 5: goto L_0x0537;
                case 6: goto L_0x0528;
                case 7: goto L_0x0519;
                case 8: goto L_0x0511;
                case 9: goto L_0x04e0;
                case 10: goto L_0x04d1;
                case 11: goto L_0x04c2;
                case 12: goto L_0x04a0;
                case 13: goto L_0x0491;
                case 14: goto L_0x0482;
                case 15: goto L_0x0473;
                case 16: goto L_0x0464;
                case 17: goto L_0x0433;
                case 18: goto L_0x0426;
                case 19: goto L_0x0419;
                case 20: goto L_0x040c;
                case 21: goto L_0x03ff;
                case 22: goto L_0x03f2;
                case 23: goto L_0x03e5;
                case 24: goto L_0x03d8;
                case 25: goto L_0x03cb;
                case 26: goto L_0x03ab;
                case 27: goto L_0x039a;
                case 28: goto L_0x038d;
                case 29: goto L_0x0380;
                case 30: goto L_0x036b;
                case 31: goto L_0x035e;
                case 32: goto L_0x0351;
                case 33: goto L_0x0344;
                case 34: goto L_0x0337;
                case 35: goto L_0x032a;
                case 36: goto L_0x031d;
                case 37: goto L_0x0310;
                case 38: goto L_0x0303;
                case 39: goto L_0x02f6;
                case 40: goto L_0x02e9;
                case 41: goto L_0x02dc;
                case 42: goto L_0x02cf;
                case 43: goto L_0x02c2;
                case 44: goto L_0x02ad;
                case 45: goto L_0x02a0;
                case 46: goto L_0x0293;
                case 47: goto L_0x0286;
                case 48: goto L_0x0279;
                case 49: goto L_0x0267;
                case 50: goto L_0x0225;
                case 51: goto L_0x0213;
                case 52: goto L_0x0201;
                case 53: goto L_0x01ef;
                case 54: goto L_0x01dd;
                case 55: goto L_0x01cb;
                case 56: goto L_0x01b9;
                case 57: goto L_0x01a7;
                case 58: goto L_0x0195;
                case 59: goto L_0x018d;
                case 60: goto L_0x015c;
                case 61: goto L_0x014e;
                case 62: goto L_0x013c;
                case 63: goto L_0x0117;
                case 64: goto L_0x0105;
                case 65: goto L_0x00f3;
                case 66: goto L_0x00e1;
                case 67: goto L_0x00cf;
                case 68: goto L_0x00bd;
                default: goto L_0x00b5;
            }
        L_0x00b5:
            if (r14 != 0) goto L_0x0591
            java.lang.Object r14 = r11.zzkn()     // Catch:{ zzib -> 0x05ae }
            goto L_0x0591
        L_0x00bd:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzjs r5 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r5 = r0.zzb(r5, r10)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x00cf:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            long r15 = r19.zzgw()     // Catch:{ zzib -> 0x05ae }
            java.lang.Long r5 = java.lang.Long.valueOf(r15)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x00e1:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            int r5 = r19.zzgv()     // Catch:{ zzib -> 0x05ae }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x00f3:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            long r15 = r19.zzgu()     // Catch:{ zzib -> 0x05ae }
            java.lang.Long r5 = java.lang.Long.valueOf(r15)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0105:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            int r5 = r19.zzgt()     // Catch:{ zzib -> 0x05ae }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0117:
            int r7 = r19.zzgs()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzhy r9 = r1.zzba(r6)     // Catch:{ zzib -> 0x05ae }
            if (r9 == 0) goto L_0x012e
            boolean r9 = r9.zzd(r7)     // Catch:{ zzib -> 0x05ae }
            if (r9 == 0) goto L_0x0128
            goto L_0x012e
        L_0x0128:
            java.lang.Object r14 = com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r7, r14, r11)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x012e:
            r5 = r5 & r8
            long r8 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r7)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r8, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x013c:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            int r5 = r19.zzgr()     // Catch:{ zzib -> 0x05ae }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x014e:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzgf r5 = r19.zzgq()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x015c:
            boolean r7 = r1.zza((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            if (r7 == 0) goto L_0x0178
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r2, r7)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzjs r9 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r9 = r0.zza(r9, r10)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r5 = com.google.android.gms.internal.firebase_auth.zzht.zzb(r5, r9)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0188
        L_0x0178:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzjs r5 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r5 = r0.zza(r5, r10)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
        L_0x0188:
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x018d:
            r1.zza(r2, r5, r0)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0195:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            boolean r5 = r19.zzgo()     // Catch:{ zzib -> 0x05ae }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x01a7:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            int r5 = r19.zzgn()     // Catch:{ zzib -> 0x05ae }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x01b9:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            long r15 = r19.zzgm()     // Catch:{ zzib -> 0x05ae }
            java.lang.Long r5 = java.lang.Long.valueOf(r15)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x01cb:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            int r5 = r19.zzgl()     // Catch:{ zzib -> 0x05ae }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x01dd:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            long r15 = r19.zzgj()     // Catch:{ zzib -> 0x05ae }
            java.lang.Long r5 = java.lang.Long.valueOf(r15)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x01ef:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            long r15 = r19.zzgk()     // Catch:{ zzib -> 0x05ae }
            java.lang.Long r5 = java.lang.Long.valueOf(r15)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0201:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            float r5 = r19.readFloat()     // Catch:{ zzib -> 0x05ae }
            java.lang.Float r5 = java.lang.Float.valueOf(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0213:
            r5 = r5 & r8
            long r7 = (long) r5     // Catch:{ zzib -> 0x05ae }
            double r15 = r19.readDouble()     // Catch:{ zzib -> 0x05ae }
            java.lang.Double r5 = java.lang.Double.valueOf(r15)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r7, r5)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0225:
            java.lang.Object r4 = r1.zzaz(r6)     // Catch:{ zzib -> 0x05ae }
            int r5 = r1.zzbb(r6)     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r7 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r2, r5)     // Catch:{ zzib -> 0x05ae }
            if (r7 != 0) goto L_0x023f
            com.google.android.gms.internal.firebase_auth.zziv r7 = r1.zzadj     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r7 = r7.zzm(r4)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r5, r7)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0256
        L_0x023f:
            com.google.android.gms.internal.firebase_auth.zziv r8 = r1.zzadj     // Catch:{ zzib -> 0x05ae }
            boolean r8 = r8.zzk(r7)     // Catch:{ zzib -> 0x05ae }
            if (r8 == 0) goto L_0x0256
            com.google.android.gms.internal.firebase_auth.zziv r8 = r1.zzadj     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r8 = r8.zzm(r4)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zziv r9 = r1.zzadj     // Catch:{ zzib -> 0x05ae }
            r9.zzc(r8, r7)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r5, r8)     // Catch:{ zzib -> 0x05ae }
            r7 = r8
        L_0x0256:
            com.google.android.gms.internal.firebase_auth.zziv r5 = r1.zzadj     // Catch:{ zzib -> 0x05ae }
            java.util.Map r5 = r5.zzi(r7)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zziv r6 = r1.zzadj     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzit r4 = r6.zzn(r4)     // Catch:{ zzib -> 0x05ae }
            r0.zza(r5, r4, r10)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0267:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzim r7 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r7.zza(r2, r4)     // Catch:{ zzib -> 0x05ae }
            r0.zzb(r4, r6, r10)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0279:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzw(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0286:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzv(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0293:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzu(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x02a0:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzt(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x02ad:
            com.google.android.gms.internal.firebase_auth.zzim r7 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r8 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r5 = r7.zza(r2, r8)     // Catch:{ zzib -> 0x05ae }
            r0.zzs(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzhy r6 = r1.zzba(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r14 = com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r5, r6, r14, r11)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x02c2:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzr(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x02cf:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzo(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x02dc:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzn(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x02e9:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzm(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x02f6:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzl(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0303:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzj(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0310:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzk(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x031d:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzi(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x032a:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzh(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0337:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzw(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0344:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzv(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0351:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzu(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x035e:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzt(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x036b:
            com.google.android.gms.internal.firebase_auth.zzim r7 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r8 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r5 = r7.zza(r2, r8)     // Catch:{ zzib -> 0x05ae }
            r0.zzs(r5)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzhy r6 = r1.zzba(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r14 = com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r5, r6, r14, r11)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0380:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzr(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x038d:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzq(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x039a:
            com.google.android.gms.internal.firebase_auth.zzjs r4 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzim r7 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            java.util.List r5 = r7.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zza(r5, r4, r10)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x03ab:
            boolean r4 = zzbd(r5)     // Catch:{ zzib -> 0x05ae }
            if (r4 == 0) goto L_0x03be
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzp(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x03be:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.readStringList(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x03cb:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzo(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x03d8:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzn(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x03e5:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzm(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x03f2:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzl(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x03ff:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzj(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x040c:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzk(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0419:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzi(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0426:
            com.google.android.gms.internal.firebase_auth.zzim r4 = r1.zzadi     // Catch:{ zzib -> 0x05ae }
            r5 = r5 & r8
            long r5 = (long) r5     // Catch:{ zzib -> 0x05ae }
            java.util.List r4 = r4.zza(r2, r5)     // Catch:{ zzib -> 0x05ae }
            r0.zzh(r4)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0433:
            boolean r4 = r1.zza((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            if (r4 == 0) goto L_0x0451
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r7 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r2, r4)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r6 = r0.zzb(r6, r10)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r6 = com.google.android.gms.internal.firebase_auth.zzht.zzb(r7, r6)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0451:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzjs r7 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r7 = r0.zzb(r7, r10)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0464:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            long r7 = r19.zzgw()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0473:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            int r7 = r19.zzgv()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0482:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            long r7 = r19.zzgu()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0491:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            int r7 = r19.zzgt()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x04a0:
            int r7 = r19.zzgs()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzhy r9 = r1.zzba(r6)     // Catch:{ zzib -> 0x05ae }
            if (r9 == 0) goto L_0x04b7
            boolean r9 = r9.zzd(r7)     // Catch:{ zzib -> 0x05ae }
            if (r9 == 0) goto L_0x04b1
            goto L_0x04b7
        L_0x04b1:
            java.lang.Object r14 = com.google.android.gms.internal.firebase_auth.zzju.zza(r4, r7, r14, r11)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x04b7:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x04c2:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            int r7 = r19.zzgr()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x04d1:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzgf r7 = r19.zzgq()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x04e0:
            boolean r4 = r1.zza((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            if (r4 == 0) goto L_0x04fe
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r7 = com.google.android.gms.internal.firebase_auth.zzkq.zzp(r2, r4)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzjs r6 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r6 = r0.zza(r6, r10)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r6 = com.google.android.gms.internal.firebase_auth.zzht.zzb(r7, r6)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x04fe:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzjs r7 = r1.zzay(r6)     // Catch:{ zzib -> 0x05ae }
            java.lang.Object r7 = r0.zza(r7, r10)     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0511:
            r1.zza(r2, r5, r0)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0519:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            boolean r7 = r19.zzgo()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0528:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            int r7 = r19.zzgn()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0537:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            long r7 = r19.zzgm()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0546:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            int r7 = r19.zzgl()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zzb(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0555:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            long r7 = r19.zzgj()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0564:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            long r7 = r19.zzgk()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0573:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            float r7 = r19.readFloat()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0582:
            r4 = r5 & r8
            long r4 = (long) r4     // Catch:{ zzib -> 0x05ae }
            double r7 = r19.readDouble()     // Catch:{ zzib -> 0x05ae }
            com.google.android.gms.internal.firebase_auth.zzkq.zza(r2, r4, r7)     // Catch:{ zzib -> 0x05ae }
            r1.zzb((T) r2, r6)     // Catch:{ zzib -> 0x05ae }
            goto L_0x0011
        L_0x0591:
            boolean r4 = r11.zza(r14, r0)     // Catch:{ zzib -> 0x05ae }
            if (r4 != 0) goto L_0x0011
            int r0 = r1.zzadf
        L_0x0599:
            int r3 = r1.zzadg
            if (r0 >= r3) goto L_0x05a8
            int[] r3 = r1.zzade
            r3 = r3[r0]
            java.lang.Object r14 = r1.zza(r2, r3, (UB) r14, r11)
            int r0 = r0 + 1
            goto L_0x0599
        L_0x05a8:
            if (r14 == 0) goto L_0x05ad
            r11.zzg(r2, r14)
        L_0x05ad:
            return
        L_0x05ae:
            r11.zza(r0)     // Catch:{ all -> 0x05d5 }
            if (r14 != 0) goto L_0x05b8
            java.lang.Object r4 = r11.zzt(r2)     // Catch:{ all -> 0x05d5 }
            r14 = r4
        L_0x05b8:
            boolean r4 = r11.zza(r14, r0)     // Catch:{ all -> 0x05d5 }
            if (r4 != 0) goto L_0x0011
            int r0 = r1.zzadf
        L_0x05c0:
            int r3 = r1.zzadg
            if (r0 >= r3) goto L_0x05cf
            int[] r3 = r1.zzade
            r3 = r3[r0]
            java.lang.Object r14 = r1.zza(r2, r3, (UB) r14, r11)
            int r0 = r0 + 1
            goto L_0x05c0
        L_0x05cf:
            if (r14 == 0) goto L_0x05d4
            r11.zzg(r2, r14)
        L_0x05d4:
            return
        L_0x05d5:
            r0 = move-exception
            int r3 = r1.zzadf
        L_0x05d8:
            int r4 = r1.zzadg
            if (r3 >= r4) goto L_0x05e7
            int[] r4 = r1.zzade
            r4 = r4[r3]
            java.lang.Object r14 = r1.zza(r2, r4, (UB) r14, r11)
            int r3 = r3 + 1
            goto L_0x05d8
        L_0x05e7:
            if (r14 == 0) goto L_0x05ec
            r11.zzg(r2, r14)
        L_0x05ec:
            throw r0
        L_0x05ed:
            java.lang.NullPointerException r0 = new java.lang.NullPointerException
            r0.<init>()
            goto L_0x05f4
        L_0x05f3:
            throw r0
        L_0x05f4:
            goto L_0x05f3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.firebase_auth.zzjg.zza(java.lang.Object, com.google.android.gms.internal.firebase_auth.zzjp, com.google.android.gms.internal.firebase_auth.zzhf):void");
    }

    private final zzjs zzay(int i) {
        int i2 = (i / 3) << 1;
        zzjs zzjs = (zzjs) this.zzacy[i2];
        if (zzjs != null) {
            return zzjs;
        }
        zzjs zzf = zzjo.zzjv().zzf((Class) this.zzacy[i2 + 1]);
        this.zzacy[i2] = zzf;
        return zzf;
    }

    private final Object zzaz(int i) {
        return this.zzacy[(i / 3) << 1];
    }

    private final zzhy zzba(int i) {
        return (zzhy) this.zzacy[((i / 3) << 1) + 1];
    }

    public final void zzf(T t) {
        int i;
        int i2 = this.zzadf;
        while (true) {
            i = this.zzadg;
            if (i2 >= i) {
                break;
            }
            long zzbb = (long) (zzbb(this.zzade[i2]) & 1048575);
            Object zzp = zzkq.zzp(t, zzbb);
            if (zzp != null) {
                zzkq.zza((Object) t, zzbb, this.zzadj.zzl(zzp));
            }
            i2++;
        }
        int length = this.zzade.length;
        while (i < length) {
            this.zzadi.zzb(t, (long) this.zzade[i]);
            i++;
        }
        this.zzacs.zzf(t);
        if (this.zzact) {
            this.zzacu.zzf((Object) t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzkk<UT, UB> zzkk) {
        int i2 = this.zzacx[i];
        Object zzp = zzkq.zzp(obj, (long) (zzbb(i) & 1048575));
        if (zzp == null) {
            return ub;
        }
        zzhy zzba = zzba(i);
        if (zzba == null) {
            return ub;
        }
        return zza(i, i2, this.zzadj.zzi(zzp), zzba, ub, zzkk);
    }

    private final <K, V, UT, UB> UB zza(int i, int i2, Map<K, V> map, zzhy zzhy, UB ub, zzkk<UT, UB> zzkk) {
        zzit zzn = this.zzadj.zzn(zzaz(i));
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (!zzhy.zzd(((Integer) entry.getValue()).intValue())) {
                if (ub == null) {
                    ub = zzkk.zzkn();
                }
                zzgn zzr = zzgf.zzr(zziu.zza(zzn, entry.getKey(), entry.getValue()));
                try {
                    zziu.zza(zzr.zzgh(), zzn, entry.getKey(), entry.getValue());
                    zzkk.zza(ub, i2, zzr.zzgg());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    public final boolean zzp(T t) {
        int i;
        int i2 = 0;
        int i3 = -1;
        int i4 = 0;
        while (true) {
            boolean z = true;
            if (i2 >= this.zzadf) {
                return !this.zzact || this.zzacu.zzd(t).isInitialized();
            }
            int i5 = this.zzade[i2];
            int i6 = this.zzacx[i5];
            int zzbb = zzbb(i5);
            if (!this.zzadc) {
                int i7 = this.zzacx[i5 + 2];
                int i8 = i7 & 1048575;
                i = 1 << (i7 >>> 20);
                if (i8 != i3) {
                    i4 = zzacw.getInt(t, (long) i8);
                    i3 = i8;
                }
            } else {
                i = 0;
            }
            if (((268435456 & zzbb) != 0) && !zza(t, i5, i4, i)) {
                return false;
            }
            int i9 = (267386880 & zzbb) >>> 20;
            if (i9 != 9 && i9 != 17) {
                if (i9 != 27) {
                    if (i9 == 60 || i9 == 68) {
                        if (zza(t, i6, i5) && !zza((Object) t, zzbb, zzay(i5))) {
                            return false;
                        }
                    } else if (i9 != 49) {
                        if (i9 != 50) {
                            continue;
                        } else {
                            Map zzj = this.zzadj.zzj(zzkq.zzp(t, (long) (zzbb & 1048575)));
                            if (!zzj.isEmpty()) {
                                if (this.zzadj.zzn(zzaz(i5)).zzacm.zzkx() == zzle.MESSAGE) {
                                    zzjs zzjs = null;
                                    Iterator it = zzj.values().iterator();
                                    while (true) {
                                        if (!it.hasNext()) {
                                            break;
                                        }
                                        Object next = it.next();
                                        if (zzjs == null) {
                                            zzjs = zzjo.zzjv().zzf(next.getClass());
                                        }
                                        if (!zzjs.zzp(next)) {
                                            z = false;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!z) {
                                return false;
                            }
                        }
                    }
                }
                List list = (List) zzkq.zzp(t, (long) (zzbb & 1048575));
                if (!list.isEmpty()) {
                    zzjs zzay = zzay(i5);
                    int i10 = 0;
                    while (true) {
                        if (i10 >= list.size()) {
                            break;
                        } else if (!zzay.zzp(list.get(i10))) {
                            z = false;
                            break;
                        } else {
                            i10++;
                        }
                    }
                }
                if (!z) {
                    return false;
                }
            } else if (zza(t, i5, i4, i) && !zza((Object) t, zzbb, zzay(i5))) {
                return false;
            }
            i2++;
        }
    }

    private static boolean zza(Object obj, int i, zzjs zzjs) {
        return zzjs.zzp(zzkq.zzp(obj, (long) (i & 1048575)));
    }

    private static void zza(int i, Object obj, zzlh zzlh) throws IOException {
        if (obj instanceof String) {
            zzlh.zza(i, (String) obj);
        } else {
            zzlh.zza(i, (zzgf) obj);
        }
    }

    private final void zza(Object obj, int i, zzjp zzjp) throws IOException {
        if (zzbd(i)) {
            zzkq.zza(obj, (long) (i & 1048575), (Object) zzjp.zzgp());
        } else if (this.zzadb) {
            zzkq.zza(obj, (long) (i & 1048575), (Object) zzjp.readString());
        } else {
            zzkq.zza(obj, (long) (i & 1048575), (Object) zzjp.zzgq());
        }
    }

    private final int zzbb(int i) {
        return this.zzacx[i + 1];
    }

    private final int zzbc(int i) {
        return this.zzacx[i + 2];
    }

    private static <T> double zzf(T t, long j) {
        return ((Double) zzkq.zzp(t, j)).doubleValue();
    }

    private static <T> float zzg(T t, long j) {
        return ((Float) zzkq.zzp(t, j)).floatValue();
    }

    private static <T> int zzh(T t, long j) {
        return ((Integer) zzkq.zzp(t, j)).intValue();
    }

    private static <T> long zzi(T t, long j) {
        return ((Long) zzkq.zzp(t, j)).longValue();
    }

    private static <T> boolean zzj(T t, long j) {
        return ((Boolean) zzkq.zzp(t, j)).booleanValue();
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza(t, i) == zza(t2, i);
    }

    private final boolean zza(T t, int i, int i2, int i3) {
        if (this.zzadc) {
            return zza(t, i);
        }
        return (i2 & i3) != 0;
    }

    private final boolean zza(T t, int i) {
        if (this.zzadc) {
            int zzbb = zzbb(i);
            long j = (long) (zzbb & 1048575);
            switch ((zzbb & 267386880) >>> 20) {
                case 0:
                    return zzkq.zzo(t, j) != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                case 1:
                    return zzkq.zzn(t, j) != 0.0f;
                case 2:
                    return zzkq.zzl(t, j) != 0;
                case 3:
                    return zzkq.zzl(t, j) != 0;
                case 4:
                    return zzkq.zzk(t, j) != 0;
                case 5:
                    return zzkq.zzl(t, j) != 0;
                case 6:
                    return zzkq.zzk(t, j) != 0;
                case 7:
                    return zzkq.zzm(t, j);
                case 8:
                    Object zzp = zzkq.zzp(t, j);
                    if (zzp instanceof String) {
                        return !((String) zzp).isEmpty();
                    }
                    if (zzp instanceof zzgf) {
                        return !zzgf.zzvv.equals(zzp);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zzkq.zzp(t, j) != null;
                case 10:
                    return !zzgf.zzvv.equals(zzkq.zzp(t, j));
                case 11:
                    return zzkq.zzk(t, j) != 0;
                case 12:
                    return zzkq.zzk(t, j) != 0;
                case 13:
                    return zzkq.zzk(t, j) != 0;
                case 14:
                    return zzkq.zzl(t, j) != 0;
                case 15:
                    return zzkq.zzk(t, j) != 0;
                case 16:
                    return zzkq.zzl(t, j) != 0;
                case 17:
                    return zzkq.zzp(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            int zzbc = zzbc(i);
            return (zzkq.zzk(t, (long) (zzbc & 1048575)) & (1 << (zzbc >>> 20))) != 0;
        }
    }

    private final void zzb(T t, int i) {
        if (!this.zzadc) {
            int zzbc = zzbc(i);
            long j = (long) (zzbc & 1048575);
            zzkq.zzb((Object) t, j, zzkq.zzk(t, j) | (1 << (zzbc >>> 20)));
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzkq.zzk(t, (long) (zzbc(i2) & 1048575)) == i;
    }

    private final void zzb(T t, int i, int i2) {
        zzkq.zzb((Object) t, (long) (zzbc(i2) & 1048575), i);
    }
}
