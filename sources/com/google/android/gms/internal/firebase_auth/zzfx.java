package com.google.android.gms.internal.firebase_auth;

import com.google.android.gms.internal.firebase_auth.zzfx;
import com.google.android.gms.internal.firebase_auth.zzga;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class zzfx<MessageType extends zzfx<MessageType, BuilderType>, BuilderType extends zzga<MessageType, BuilderType>> implements zzjc {
    private static boolean zzvn = false;
    protected int zzvm = 0;

    public final zzgf zzft() {
        try {
            zzgn zzr = zzgf.zzr(zzik());
            zzb(zzr.zzgh());
            return zzr.zzgg();
        } catch (IOException e) {
            String str = "ByteString";
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + str.length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append(str);
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    public final byte[] toByteArray() {
        try {
            byte[] bArr = new byte[zzik()];
            zzha zzc = zzha.zzc(bArr);
            zzb(zzc);
            zzc.zzhj();
            return bArr;
        } catch (IOException e) {
            String str = "byte array";
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + str.length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append(str);
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    /* access modifiers changed from: 0000 */
    public int zzfu() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: 0000 */
    public void zzl(int i) {
        throw new UnsupportedOperationException();
    }

    protected static <T> void zza(Iterable<T> iterable, List<? super T> list) {
        zzht.checkNotNull(iterable);
        String str = " is null.";
        String str2 = "Element at index ";
        if (iterable instanceof zzij) {
            List zzjd = ((zzij) iterable).zzjd();
            zzij zzij = (zzij) list;
            int size = list.size();
            for (Object next : zzjd) {
                if (next == null) {
                    int size2 = zzij.size() - size;
                    StringBuilder sb = new StringBuilder(37);
                    sb.append(str2);
                    sb.append(size2);
                    sb.append(str);
                    String sb2 = sb.toString();
                    for (int size3 = zzij.size() - 1; size3 >= size; size3--) {
                        zzij.remove(size3);
                    }
                    throw new NullPointerException(sb2);
                } else if (next instanceof zzgf) {
                    zzij.zzc((zzgf) next);
                } else {
                    zzij.add((String) next);
                }
            }
        } else if (iterable instanceof zzjl) {
            list.addAll((Collection) iterable);
        } else {
            if ((list instanceof ArrayList) && (iterable instanceof Collection)) {
                ((ArrayList) list).ensureCapacity(list.size() + ((Collection) iterable).size());
            }
            int size4 = list.size();
            for (Object next2 : iterable) {
                if (next2 == null) {
                    int size5 = list.size() - size4;
                    StringBuilder sb3 = new StringBuilder(37);
                    sb3.append(str2);
                    sb3.append(size5);
                    sb3.append(str);
                    String sb4 = sb3.toString();
                    for (int size6 = list.size() - 1; size6 >= size4; size6--) {
                        list.remove(size6);
                    }
                    throw new NullPointerException(sb4);
                }
                list.add(next2);
            }
        }
    }
}
