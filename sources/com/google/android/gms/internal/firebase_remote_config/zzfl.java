package com.google.android.gms.internal.firebase_remote_config;

import com.mobiroller.constants.Constants;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import p043io.netty.util.internal.StringUtil;

public final class zzfl implements Closeable, Flushable {
    private static final String[] zzny = new String[128];
    private static final String[] zznz;
    private final Writer out;
    private String separator;
    private boolean zznb;
    private int[] zznj = new int[32];
    private int zznk = 0;
    private String zzoa;
    private String zzob;
    private boolean zzoc;

    public zzfl(Writer writer) {
        zzo(6);
        this.separator = ":";
        this.zzoc = true;
        this.out = writer;
    }

    public final void setIndent(String str) {
        if (str.length() == 0) {
            this.zzoa = null;
            this.separator = ":";
            return;
        }
        this.zzoa = str;
        this.separator = ": ";
    }

    public final void setLenient(boolean z) {
        this.zznb = true;
    }

    public final zzfl zzee() throws IOException {
        zzej();
        return zza(1, "[");
    }

    public final zzfl zzef() throws IOException {
        return zzb(1, 2, "]");
    }

    public final zzfl zzeg() throws IOException {
        zzej();
        return zza(3, "{");
    }

    public final zzfl zzeh() throws IOException {
        return zzb(3, 5, "}");
    }

    private final zzfl zza(int i, String str) throws IOException {
        zzem();
        zzo(i);
        this.out.write(str);
        return this;
    }

    private final zzfl zzb(int i, int i2, String str) throws IOException {
        int zzei = zzei();
        if (zzei != i2 && zzei != i) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.zzob == null) {
            this.zznk--;
            if (zzei == i2) {
                zzel();
            }
            this.out.write(str);
            return this;
        } else {
            StringBuilder sb = new StringBuilder("Dangling name: ");
            sb.append(this.zzob);
            throw new IllegalStateException(sb.toString());
        }
    }

    private final void zzo(int i) {
        int i2 = this.zznk;
        int[] iArr = this.zznj;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[(i2 << 1)];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.zznj = iArr2;
        }
        int[] iArr3 = this.zznj;
        int i3 = this.zznk;
        this.zznk = i3 + 1;
        iArr3[i3] = i;
    }

    private final int zzei() {
        int i = this.zznk;
        if (i != 0) {
            return this.zznj[i - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private final void zzq(int i) {
        this.zznj[this.zznk - 1] = i;
    }

    public final zzfl zzbh(String str) throws IOException {
        if (str == null) {
            throw new NullPointerException("name == null");
        } else if (this.zzob != null) {
            throw new IllegalStateException();
        } else if (this.zznk != 0) {
            this.zzob = str;
            return this;
        } else {
            throw new IllegalStateException("JsonWriter is closed.");
        }
    }

    private final void zzej() throws IOException {
        if (this.zzob != null) {
            int zzei = zzei();
            if (zzei == 5) {
                this.out.write(44);
            } else if (zzei != 3) {
                throw new IllegalStateException("Nesting problem.");
            }
            zzel();
            zzq(4);
            zzbj(this.zzob);
            this.zzob = null;
        }
    }

    public final zzfl zzbi(String str) throws IOException {
        if (str == null) {
            return zzek();
        }
        zzej();
        zzem();
        zzbj(str);
        return this;
    }

    public final zzfl zzek() throws IOException {
        if (this.zzob != null) {
            if (this.zzoc) {
                zzej();
            } else {
                this.zzob = null;
                return this;
            }
        }
        zzem();
        this.out.write("null");
        return this;
    }

    public final zzfl zzd(boolean z) throws IOException {
        zzej();
        zzem();
        this.out.write(z ? "true" : "false");
        return this;
    }

    public final zzfl zzb(double d) throws IOException {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            StringBuilder sb = new StringBuilder("Numeric values must be finite, but was ");
            sb.append(d);
            throw new IllegalArgumentException(sb.toString());
        }
        zzej();
        zzem();
        this.out.append(Double.toString(d));
        return this;
    }

    public final zzfl zzd(long j) throws IOException {
        zzej();
        zzem();
        this.out.write(Long.toString(j));
        return this;
    }

    public final zzfl zza(Number number) throws IOException {
        if (number == null) {
            return zzek();
        }
        zzej();
        String obj = number.toString();
        if (this.zznb || (!obj.equals("-Infinity") && !obj.equals("Infinity") && !obj.equals("NaN"))) {
            zzem();
            this.out.append(obj);
            return this;
        }
        StringBuilder sb = new StringBuilder("Numeric values must be finite, but was ");
        sb.append(number);
        throw new IllegalArgumentException(sb.toString());
    }

    public final void flush() throws IOException {
        if (this.zznk != 0) {
            this.out.flush();
            return;
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    public final void close() throws IOException {
        this.out.close();
        int i = this.zznk;
        if (i > 1 || (i == 1 && this.zznj[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.zznk = 0;
    }

    private final void zzbj(String str) throws IOException {
        String str2;
        String[] strArr = zzny;
        String str3 = "\"";
        this.out.write(str3);
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt < 128) {
                str2 = strArr[charAt];
                if (str2 == null) {
                }
            } else if (charAt == 8232) {
                str2 = "\\u2028";
            } else if (charAt == 8233) {
                str2 = "\\u2029";
            }
            if (i < i2) {
                this.out.write(str, i, i2 - i);
            }
            this.out.write(str2);
            i = i2 + 1;
        }
        if (i < length) {
            this.out.write(str, i, length - i);
        }
        this.out.write(str3);
    }

    private final void zzel() throws IOException {
        if (this.zzoa != null) {
            this.out.write(Constants.NEW_LINE);
            int i = this.zznk;
            for (int i2 = 1; i2 < i; i2++) {
                this.out.write(this.zzoa);
            }
        }
    }

    private final void zzem() throws IOException {
        int zzei = zzei();
        if (zzei == 1) {
            zzq(2);
            zzel();
        } else if (zzei == 2) {
            this.out.append(StringUtil.COMMA);
            zzel();
        } else if (zzei != 4) {
            if (zzei != 6) {
                if (zzei != 7) {
                    throw new IllegalStateException("Nesting problem.");
                } else if (!this.zznb) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            zzq(7);
        } else {
            this.out.append(this.separator);
            zzq(5);
        }
    }

    static {
        for (int i = 0; i <= 31; i++) {
            zzny[i] = String.format("\\u%04x", new Object[]{Integer.valueOf(i)});
        }
        String[] strArr = zzny;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        String[] strArr2 = (String[]) strArr.clone();
        zznz = strArr2;
        strArr2[60] = "\\u003c";
        String[] strArr3 = zznz;
        strArr3[62] = "\\u003e";
        strArr3[38] = "\\u0026";
        strArr3[61] = "\\u003d";
        strArr3[39] = "\\u0027";
    }
}
