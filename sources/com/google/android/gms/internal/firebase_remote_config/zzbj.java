package com.google.android.gms.internal.firebase_remote_config;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

final class zzbj extends zzbb {
    private final zzbg zzdw;
    private final zzfi zzdx;
    private List<String> zzdy = new ArrayList();
    private zzbf zzdz;
    private String zzea;

    zzbj(zzbg zzbg, zzfi zzfi) {
        this.zzdw = zzbg;
        this.zzdx = zzfi;
        zzfi.setLenient(true);
    }

    public final void close() throws IOException {
        this.zzdx.close();
    }

    public final String zzbd() {
        if (this.zzdy.isEmpty()) {
            return null;
        }
        List<String> list = this.zzdy;
        return (String) list.get(list.size() - 1);
    }

    public final zzbf zzbc() {
        return this.zzdz;
    }

    public final zzax zzba() {
        return this.zzdw;
    }

    public final byte zzbf() {
        zzbs();
        return Byte.parseByte(this.zzea);
    }

    public final short zzbg() {
        zzbs();
        return Short.parseShort(this.zzea);
    }

    public final int getIntValue() {
        zzbs();
        return Integer.parseInt(this.zzea);
    }

    public final float zzbh() {
        zzbs();
        return Float.parseFloat(this.zzea);
    }

    public final BigInteger zzbk() {
        zzbs();
        return new BigInteger(this.zzea);
    }

    public final BigDecimal zzbl() {
        zzbs();
        return new BigDecimal(this.zzea);
    }

    public final double zzbj() {
        zzbs();
        return Double.parseDouble(this.zzea);
    }

    public final long zzbi() {
        zzbs();
        return Long.parseLong(this.zzea);
    }

    private final void zzbs() {
        if (!(this.zzdz == zzbf.VALUE_NUMBER_INT || this.zzdz == zzbf.VALUE_NUMBER_FLOAT)) {
            throw new IllegalArgumentException();
        }
    }

    public final String getText() {
        return this.zzea;
    }

    public final zzbf zzbb() throws IOException {
        zzfk zzfk;
        zzbf zzbf;
        if (this.zzdz != null) {
            int i = zzbk.zzdg[this.zzdz.ordinal()];
            if (i == 1) {
                this.zzdx.beginArray();
                this.zzdy.add(null);
            } else if (i == 2) {
                this.zzdx.beginObject();
                this.zzdy.add(null);
            }
        }
        try {
            zzfk = this.zzdx.zzdx();
        } catch (EOFException unused) {
            zzfk = zzfk.END_DOCUMENT;
        }
        switch (zzbk.zzeb[zzfk.ordinal()]) {
            case 1:
                this.zzea = "[";
                this.zzdz = zzbf.START_ARRAY;
                break;
            case 2:
                this.zzea = "]";
                this.zzdz = zzbf.END_ARRAY;
                List<String> list = this.zzdy;
                list.remove(list.size() - 1);
                this.zzdx.endArray();
                break;
            case 3:
                this.zzea = "{";
                this.zzdz = zzbf.START_OBJECT;
                break;
            case 4:
                this.zzea = "}";
                this.zzdz = zzbf.END_OBJECT;
                List<String> list2 = this.zzdy;
                list2.remove(list2.size() - 1);
                this.zzdx.endObject();
                break;
            case 5:
                if (!this.zzdx.nextBoolean()) {
                    this.zzea = "false";
                    this.zzdz = zzbf.VALUE_FALSE;
                    break;
                } else {
                    this.zzea = "true";
                    this.zzdz = zzbf.VALUE_TRUE;
                    break;
                }
            case 6:
                this.zzea = "null";
                this.zzdz = zzbf.VALUE_NULL;
                this.zzdx.nextNull();
                break;
            case 7:
                this.zzea = this.zzdx.nextString();
                this.zzdz = zzbf.VALUE_STRING;
                break;
            case 8:
                this.zzea = this.zzdx.nextString();
                if (this.zzea.indexOf(46) == -1) {
                    zzbf = zzbf.VALUE_NUMBER_INT;
                } else {
                    zzbf = zzbf.VALUE_NUMBER_FLOAT;
                }
                this.zzdz = zzbf;
                break;
            case 9:
                this.zzea = this.zzdx.nextName();
                this.zzdz = zzbf.FIELD_NAME;
                List<String> list3 = this.zzdy;
                list3.set(list3.size() - 1, this.zzea);
                break;
            default:
                this.zzea = null;
                this.zzdz = null;
                break;
        }
        return this.zzdz;
    }

    public final zzbb zzbe() throws IOException {
        if (this.zzdz != null) {
            int i = zzbk.zzdg[this.zzdz.ordinal()];
            if (i == 1) {
                this.zzdx.skipValue();
                this.zzea = "]";
                this.zzdz = zzbf.END_ARRAY;
            } else if (i == 2) {
                this.zzdx.skipValue();
                this.zzea = "}";
                this.zzdz = zzbf.END_OBJECT;
            }
        }
        return this;
    }
}
