package org.objectweb.asm;

final class Item {

    /* renamed from: a */
    int f4668a;

    /* renamed from: b */
    int f4669b;

    /* renamed from: c */
    int f4670c;

    /* renamed from: d */
    long f4671d;

    /* renamed from: g */
    String f4672g;

    /* renamed from: h */
    String f4673h;

    /* renamed from: i */
    String f4674i;

    /* renamed from: j */
    int f4675j;

    /* renamed from: k */
    Item f4676k;

    Item() {
    }

    Item(int i) {
        this.f4668a = i;
    }

    Item(int i, Item item) {
        this.f4668a = i;
        this.f4669b = item.f4669b;
        this.f4670c = item.f4670c;
        this.f4671d = item.f4671d;
        this.f4672g = item.f4672g;
        this.f4673h = item.f4673h;
        this.f4674i = item.f4674i;
        this.f4675j = item.f4675j;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76784a(double d) {
        this.f4669b = 6;
        this.f4671d = Double.doubleToRawLongBits(d);
        this.f4675j = Integer.MAX_VALUE & (this.f4669b + ((int) d));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76785a(float f) {
        this.f4669b = 4;
        this.f4670c = Float.floatToRawIntBits(f);
        this.f4675j = Integer.MAX_VALUE & (this.f4669b + ((int) f));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76786a(int i) {
        this.f4669b = 3;
        this.f4670c = i;
        this.f4675j = Integer.MAX_VALUE & (this.f4669b + i);
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76787a(int i, int i2) {
        this.f4669b = 33;
        this.f4670c = i;
        this.f4675j = i2;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76788a(int i, String str, String str2, String str3) {
        int hashCode;
        int hashCode2;
        int hashCode3;
        this.f4669b = i;
        this.f4672g = str;
        this.f4673h = str2;
        this.f4674i = str3;
        if (i != 1) {
            if (i == 12) {
                hashCode2 = str.hashCode();
                hashCode3 = str2.hashCode();
            } else if (!(i == 16 || i == 30)) {
                if (i == 7) {
                    this.f4670c = 0;
                } else if (i != 8) {
                    hashCode2 = str.hashCode() * str2.hashCode();
                    hashCode3 = str3.hashCode();
                }
            }
            hashCode = hashCode2 * hashCode3;
            this.f4675j = (i + hashCode) & Integer.MAX_VALUE;
        }
        hashCode = str.hashCode();
        this.f4675j = (i + hashCode) & Integer.MAX_VALUE;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76789a(long j) {
        this.f4669b = 5;
        this.f4671d = j;
        this.f4675j = Integer.MAX_VALUE & (this.f4669b + ((int) j));
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public void mo76790a(String str, String str2, int i) {
        this.f4669b = 18;
        this.f4671d = (long) i;
        this.f4672g = str;
        this.f4673h = str2;
        this.f4675j = Integer.MAX_VALUE & ((i * this.f4672g.hashCode() * this.f4673h.hashCode()) + 18);
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0052, code lost:
        if (r9.f4671d != r8.f4671d) goto L_0x0055;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
        return r1;
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean mo76791a(org.objectweb.asm.Item r9) {
        /*
            r8 = this;
            int r0 = r8.f4669b
            r1 = 1
            if (r0 == r1) goto L_0x0096
            r2 = 12
            r3 = 0
            if (r0 == r2) goto L_0x007f
            r2 = 16
            if (r0 == r2) goto L_0x0096
            r2 = 18
            if (r0 == r2) goto L_0x0060
            switch(r0) {
                case 3: goto L_0x0057;
                case 4: goto L_0x0057;
                case 5: goto L_0x004c;
                case 6: goto L_0x004c;
                case 7: goto L_0x0096;
                case 8: goto L_0x0096;
                default: goto L_0x0015;
            }
        L_0x0015:
            switch(r0) {
                case 30: goto L_0x0096;
                case 31: goto L_0x0039;
                case 32: goto L_0x004c;
                default: goto L_0x0018;
            }
        L_0x0018:
            java.lang.String r0 = r9.f4672g
            java.lang.String r2 = r8.f4672g
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0037
            java.lang.String r0 = r9.f4673h
            java.lang.String r2 = r8.f4673h
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0037
            java.lang.String r9 = r9.f4674i
            java.lang.String r0 = r8.f4674i
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x0037
            goto L_0x0038
        L_0x0037:
            r1 = 0
        L_0x0038:
            return r1
        L_0x0039:
            int r0 = r9.f4670c
            int r2 = r8.f4670c
            if (r0 != r2) goto L_0x004a
            java.lang.String r9 = r9.f4672g
            java.lang.String r0 = r8.f4672g
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x004a
            goto L_0x004b
        L_0x004a:
            r1 = 0
        L_0x004b:
            return r1
        L_0x004c:
            long r4 = r9.f4671d
            long r6 = r8.f4671d
            int r9 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r9 != 0) goto L_0x0055
            goto L_0x0056
        L_0x0055:
            r1 = 0
        L_0x0056:
            return r1
        L_0x0057:
            int r9 = r9.f4670c
            int r0 = r8.f4670c
            if (r9 != r0) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r1 = 0
        L_0x005f:
            return r1
        L_0x0060:
            long r4 = r9.f4671d
            long r6 = r8.f4671d
            int r0 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r0 != 0) goto L_0x007d
            java.lang.String r0 = r9.f4672g
            java.lang.String r2 = r8.f4672g
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x007d
            java.lang.String r9 = r9.f4673h
            java.lang.String r0 = r8.f4673h
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x007d
            goto L_0x007e
        L_0x007d:
            r1 = 0
        L_0x007e:
            return r1
        L_0x007f:
            java.lang.String r0 = r9.f4672g
            java.lang.String r2 = r8.f4672g
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0094
            java.lang.String r9 = r9.f4673h
            java.lang.String r0 = r8.f4673h
            boolean r9 = r9.equals(r0)
            if (r9 == 0) goto L_0x0094
            goto L_0x0095
        L_0x0094:
            r1 = 0
        L_0x0095:
            return r1
        L_0x0096:
            java.lang.String r9 = r9.f4672g
            java.lang.String r0 = r8.f4672g
            boolean r9 = r9.equals(r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.Item.mo76791a(org.objectweb.asm.Item):boolean");
    }
}
