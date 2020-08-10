package org.objectweb.asm;

import com.fasterxml.jackson.core.JsonPointer;

public class ClassWriter extends ClassVisitor {
    public static final int COMPUTE_FRAMES = 2;
    public static final int COMPUTE_MAXS = 1;

    /* renamed from: a */
    static final byte[] f4574a;

    /* renamed from: A */
    ByteVector f4575A;

    /* renamed from: B */
    FieldWriter f4576B;

    /* renamed from: C */
    FieldWriter f4577C;

    /* renamed from: D */
    MethodWriter f4578D;

    /* renamed from: E */
    MethodWriter f4579E;

    /* renamed from: G */
    private short f4580G;

    /* renamed from: H */
    Item[] f4581H;

    /* renamed from: I */
    String f4582I;

    /* renamed from: J */
    private boolean f4583J;

    /* renamed from: K */
    private boolean f4584K;

    /* renamed from: L */
    boolean f4585L;

    /* renamed from: M */
    ClassReader f4586M;

    /* renamed from: N */
    private AnnotationWriter f4587N;

    /* renamed from: O */
    private AnnotationWriter f4588O;

    /* renamed from: b */
    int f4589b;

    /* renamed from: c */
    int f4590c;

    /* renamed from: d */
    final ByteVector f4591d;

    /* renamed from: e */
    Item[] f4592e;

    /* renamed from: f */
    int f4593f;

    /* renamed from: g */
    final Item f4594g;

    /* renamed from: h */
    final Item f4595h;

    /* renamed from: i */
    final Item f4596i;

    /* renamed from: j */
    final Item f4597j;

    /* renamed from: k */
    private int f4598k;

    /* renamed from: l */
    private int f4599l;

    /* renamed from: m */
    private int f4600m;

    /* renamed from: n */
    private int f4601n;

    /* renamed from: o */
    private int f4602o;

    /* renamed from: p */
    private int[] f4603p;

    /* renamed from: q */
    private int f4604q;

    /* renamed from: r */
    private ByteVector f4605r;

    /* renamed from: s */
    private int f4606s;

    /* renamed from: t */
    private int f4607t;

    /* renamed from: u */
    private AnnotationWriter f4608u;

    /* renamed from: v */
    private AnnotationWriter f4609v;

    /* renamed from: w */
    private Attribute f4610w;

    /* renamed from: x */
    private int f4611x;

    /* renamed from: y */
    private ByteVector f4612y;

    /* renamed from: z */
    int f4613z;

    static {
        _clinit_();
        byte[] bArr = new byte[220];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ("AAAAAAAAAAAAAAAABCLMMDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANAAAAAAAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJDOPAAAAAAGGGGGGGHIFBFAAFFAARQJJKKJJJJJJJJJJJJJJJJJJ".charAt(i) - 'A');
        }
        f4574a = bArr;
    }

    public ClassWriter(int i) {
        super(Opcodes.ASM5);
        boolean z = true;
        this.f4590c = 1;
        this.f4591d = new ByteVector();
        this.f4592e = new Item[256];
        double length = (double) this.f4592e.length;
        Double.isNaN(length);
        this.f4593f = (int) (length * 0.75d);
        this.f4594g = new Item();
        this.f4595h = new Item();
        this.f4596i = new Item();
        this.f4597j = new Item();
        this.f4584K = (i & 1) != 0;
        if ((i & 2) == 0) {
            z = false;
        }
        this.f4583J = z;
    }

    public ClassWriter(ClassReader classReader, int i) {
        this(i);
        classReader.mo76714a(this);
        this.f4586M = classReader;
    }

    static /* synthetic */ void _clinit_() {
    }

    /* renamed from: a */
    private Item m4172a(Item item) {
        Item item2 = this.f4592e[item.f4675j % this.f4592e.length];
        while (item2 != null && (item2.f4669b != item.f4669b || !item.mo76791a(item2))) {
            item2 = item2.f4676k;
        }
        return item2;
    }

    /* renamed from: a */
    private void m4173a(int i, int i2, int i3) {
        this.f4591d.mo76706b(i, i2).putShort(i3);
    }

    /* renamed from: b */
    private Item m4174b(String str) {
        this.f4595h.mo76788a(8, str, null, null);
        Item a = m4172a(this.f4595h);
        if (a != null) {
            return a;
        }
        this.f4591d.mo76706b(8, newUTF8(str));
        int i = this.f4590c;
        this.f4590c = i + 1;
        Item item = new Item(i, this.f4595h);
        m4176b(item);
        return item;
    }

    /* renamed from: b */
    private void m4175b(int i, int i2, int i3) {
        this.f4591d.mo76705a(i, i2).putShort(i3);
    }

    /* renamed from: b */
    private void m4176b(Item item) {
        if (this.f4590c + this.f4580G > this.f4593f) {
            int length = this.f4592e.length;
            int i = (length * 2) + 1;
            Item[] itemArr = new Item[i];
            for (int i2 = length - 1; i2 >= 0; i2--) {
                Item item2 = this.f4592e[i2];
                while (item2 != null) {
                    int length2 = item2.f4675j % itemArr.length;
                    Item item3 = item2.f4676k;
                    item2.f4676k = itemArr[length2];
                    itemArr[length2] = item2;
                    item2 = item3;
                }
            }
            this.f4592e = itemArr;
            double d = (double) i;
            Double.isNaN(d);
            this.f4593f = (int) (d * 0.75d);
        }
        int i3 = item.f4675j;
        Item[] itemArr2 = this.f4592e;
        int length3 = i3 % itemArr2.length;
        item.f4676k = itemArr2[length3];
        itemArr2[length3] = item;
    }

    /* renamed from: c */
    private Item m4177c(Item item) {
        this.f4580G = (short) (this.f4580G + 1);
        Item item2 = new Item(this.f4580G, this.f4594g);
        m4176b(item2);
        if (this.f4581H == null) {
            this.f4581H = new Item[16];
        }
        short s = this.f4580G;
        Item[] itemArr = this.f4581H;
        if (s == itemArr.length) {
            Item[] itemArr2 = new Item[(itemArr.length * 2)];
            System.arraycopy(itemArr, 0, itemArr2, 0, itemArr.length);
            this.f4581H = itemArr2;
        }
        this.f4581H[this.f4580G] = item2;
        return item2;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public int mo76743a(int i, int i2) {
        Item item = this.f4595h;
        item.f4669b = 32;
        item.f4671d = ((long) i) | (((long) i2) << 32);
        item.f4675j = (i + 32 + i2) & Integer.MAX_VALUE;
        Item a = m4172a(item);
        if (a == null) {
            String str = this.f4581H[i].f4672g;
            String str2 = this.f4581H[i2].f4672g;
            this.f4595h.f4670c = mo76756c(getCommonSuperClass(str, str2));
            a = new Item(0, this.f4595h);
            m4176b(a);
        }
        return a.f4670c;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public int mo76744a(String str, int i) {
        Item item = this.f4594g;
        item.f4669b = 31;
        item.f4670c = i;
        item.f4672g = str;
        item.f4675j = (str.hashCode() + 31 + i) & Integer.MAX_VALUE;
        Item a = m4172a(this.f4594g);
        if (a == null) {
            a = m4177c(this.f4594g);
        }
        return a.f4668a;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76745a(double d) {
        this.f4594g.mo76784a(d);
        Item a = m4172a(this.f4594g);
        if (a != null) {
            return a;
        }
        this.f4591d.putByte(6).putLong(this.f4594g.f4671d);
        Item item = new Item(this.f4590c, this.f4594g);
        this.f4590c += 2;
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76746a(float f) {
        this.f4594g.mo76785a(f);
        Item a = m4172a(this.f4594g);
        if (a != null) {
            return a;
        }
        this.f4591d.putByte(4).putInt(this.f4594g.f4670c);
        int i = this.f4590c;
        this.f4590c = i + 1;
        Item item = new Item(i, this.f4594g);
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76747a(int i) {
        this.f4594g.mo76786a(i);
        Item a = m4172a(this.f4594g);
        if (a != null) {
            return a;
        }
        this.f4591d.putByte(3).putInt(i);
        int i2 = this.f4590c;
        this.f4590c = i2 + 1;
        Item item = new Item(i2, this.f4594g);
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76748a(int i, String str, String str2, String str3) {
        int newMethod;
        this.f4597j.mo76788a(i + 20, str, str2, str3);
        Item a = m4172a(this.f4597j);
        if (a != null) {
            return a;
        }
        if (i <= 4) {
            newMethod = newField(str, str2, str3);
        } else {
            newMethod = newMethod(str, str2, str3, i == 9);
        }
        m4175b(15, i, newMethod);
        int i2 = this.f4590c;
        this.f4590c = i2 + 1;
        Item item = new Item(i2, this.f4597j);
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76749a(long j) {
        this.f4594g.mo76789a(j);
        Item a = m4172a(this.f4594g);
        if (a != null) {
            return a;
        }
        this.f4591d.putByte(5).putLong(j);
        Item item = new Item(this.f4590c, this.f4594g);
        this.f4590c += 2;
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76750a(Object obj) {
        if (obj instanceof Integer) {
            return mo76747a(((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return mo76747a(((Byte) obj).intValue());
        }
        if (obj instanceof Character) {
            return mo76747a((int) ((Character) obj).charValue());
        }
        if (obj instanceof Short) {
            return mo76747a(((Short) obj).intValue());
        }
        if (obj instanceof Boolean) {
            return mo76747a(((Boolean) obj).booleanValue() ? 1 : 0);
        }
        if (obj instanceof Float) {
            return mo76746a(((Float) obj).floatValue());
        }
        if (obj instanceof Long) {
            return mo76749a(((Long) obj).longValue());
        }
        if (obj instanceof Double) {
            return mo76745a(((Double) obj).doubleValue());
        }
        if (obj instanceof String) {
            return m4174b((String) obj);
        }
        if (obj instanceof Type) {
            Type type = (Type) obj;
            int sort = type.getSort();
            if (sort == 10) {
                return mo76751a(type.getInternalName());
            }
            String descriptor = type.getDescriptor();
            return sort == 11 ? mo76756c(descriptor) : mo76751a(descriptor);
        } else if (obj instanceof Handle) {
            Handle handle = (Handle) obj;
            return mo76748a(handle.f4658a, handle.f4659b, handle.f4660c, handle.f4661d);
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("value ");
            stringBuffer.append(obj);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76751a(String str) {
        this.f4595h.mo76788a(7, str, null, null);
        Item a = m4172a(this.f4595h);
        if (a != null) {
            return a;
        }
        this.f4591d.mo76706b(7, newUTF8(str));
        int i = this.f4590c;
        this.f4590c = i + 1;
        Item item = new Item(i, this.f4595h);
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76752a(String str, String str2) {
        this.f4595h.mo76788a(12, str, str2, null);
        Item a = m4172a(this.f4595h);
        if (a != null) {
            return a;
        }
        m4173a(12, newUTF8(str), newUTF8(str2));
        int i = this.f4590c;
        this.f4590c = i + 1;
        Item item = new Item(i, this.f4595h);
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76753a(String str, String str2, String str3) {
        this.f4596i.mo76788a(9, str, str2, str3);
        Item a = m4172a(this.f4596i);
        if (a != null) {
            return a;
        }
        m4173a(9, newClass(str), newNameType(str2, str3));
        int i = this.f4590c;
        this.f4590c = i + 1;
        Item item = new Item(i, this.f4596i);
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76754a(String str, String str2, String str3, boolean z) {
        int i = z ? 11 : 10;
        this.f4596i.mo76788a(i, str, str2, str3);
        Item a = m4172a(this.f4596i);
        if (a != null) {
            return a;
        }
        m4173a(i, newClass(str), newNameType(str2, str3));
        int i2 = this.f4590c;
        this.f4590c = i2 + 1;
        Item item = new Item(i2, this.f4596i);
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: a */
    public Item mo76755a(String str, String str2, Handle handle, Object... objArr) {
        int i;
        ByteVector byteVector = this.f4575A;
        if (byteVector == null) {
            byteVector = new ByteVector();
            this.f4575A = byteVector;
        }
        int i2 = byteVector.f4568b;
        int hashCode = handle.hashCode();
        byteVector.putShort(newHandle(handle.f4658a, handle.f4659b, handle.f4660c, handle.f4661d));
        byteVector.putShort(r12);
        int i3 = hashCode;
        for (Object obj : objArr) {
            i3 ^= obj.hashCode();
            byteVector.putShort(newConst(obj));
        }
        byte[] bArr = byteVector.f4567a;
        int i4 = (r12 + 2) << 1;
        int i5 = Integer.MAX_VALUE & i3;
        Item[] itemArr = this.f4592e;
        Item item = itemArr[i5 % itemArr.length];
        loop1:
        while (item != null) {
            if (item.f4669b == 33 && item.f4675j == i5) {
                int i6 = item.f4670c;
                int i7 = 0;
                while (i7 < i4) {
                    if (bArr[i2 + i7] == bArr[i6 + i7]) {
                        i7++;
                    }
                }
                break loop1;
            }
            item = item.f4676k;
        }
        if (item != null) {
            i = item.f4668a;
            byteVector.f4568b = i2;
        } else {
            i = this.f4613z;
            this.f4613z = i + 1;
            Item item2 = new Item(i);
            item2.mo76787a(i2, i5);
            m4176b(item2);
        }
        this.f4596i.mo76790a(str, str2, i);
        Item a = m4172a(this.f4596i);
        if (a != null) {
            return a;
        }
        m4173a(18, i, newNameType(str, str2));
        int i8 = this.f4590c;
        this.f4590c = i8 + 1;
        Item item3 = new Item(i8, this.f4596i);
        m4176b(item3);
        return item3;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public int mo76756c(String str) {
        this.f4594g.mo76788a(30, str, null, null);
        Item a = m4172a(this.f4594g);
        if (a == null) {
            a = m4177c(this.f4594g);
        }
        return a.f4668a;
    }

    /* access modifiers changed from: 0000 */
    /* renamed from: c */
    public Item m4192c(String str) {
        this.f4595h.mo76788a(16, str, null, null);
        Item a = m4172a(this.f4595h);
        if (a != null) {
            return a;
        }
        this.f4591d.mo76706b(16, newUTF8(str));
        int i = this.f4590c;
        this.f4590c = i + 1;
        Item item = new Item(i, this.f4595h);
        m4176b(item);
        return item;
    }

    /* access modifiers changed from: protected */
    public String getCommonSuperClass(String str, String str2) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            Class cls = Class.forName(str.replace(JsonPointer.SEPARATOR, '.'), false, classLoader);
            Class cls2 = Class.forName(str2.replace(JsonPointer.SEPARATOR, '.'), false, classLoader);
            if (cls.isAssignableFrom(cls2)) {
                return str;
            }
            if (cls2.isAssignableFrom(cls)) {
                return str2;
            }
            if (cls.isInterface() || cls2.isInterface()) {
                return "java/lang/Object";
            }
            do {
                cls = cls.getSuperclass();
            } while (!cls.isAssignableFrom(cls2));
            return cls.getName().replace('.', JsonPointer.SEPARATOR);
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    public int newClass(String str) {
        return mo76751a(str).f4668a;
    }

    public int newConst(Object obj) {
        return mo76750a(obj).f4668a;
    }

    public int newField(String str, String str2, String str3) {
        return mo76753a(str, str2, str3).f4668a;
    }

    public int newHandle(int i, String str, String str2, String str3) {
        return mo76748a(i, str, str2, str3).f4668a;
    }

    public int newInvokeDynamic(String str, String str2, Handle handle, Object... objArr) {
        return mo76755a(str, str2, handle, objArr).f4668a;
    }

    public int newMethod(String str, String str2, String str3, boolean z) {
        return mo76754a(str, str2, str3, z).f4668a;
    }

    public int newMethodType(String str) {
        return mo76756c(str).f4668a;
    }

    public int newNameType(String str, String str2) {
        return mo76752a(str, str2).f4668a;
    }

    public int newUTF8(String str) {
        this.f4594g.mo76788a(1, str, null, null);
        Item a = m4172a(this.f4594g);
        if (a == null) {
            this.f4591d.putByte(1).putUTF8(str);
            int i = this.f4590c;
            this.f4590c = i + 1;
            a = new Item(i, this.f4594g);
            m4176b(a);
        }
        return a.f4668a;
    }

    public byte[] toByteArray() {
        int i;
        int i2;
        String str;
        String str2;
        ByteVector byteVector;
        if (this.f4590c <= 65535) {
            int i3 = (this.f4602o * 2) + 24;
            int i4 = 0;
            for (FieldWriter fieldWriter = this.f4576B; fieldWriter != null; fieldWriter = (FieldWriter) fieldWriter.f4637fv) {
                i4++;
                i3 += fieldWriter.mo76772a();
            }
            int i5 = 0;
            for (MethodWriter methodWriter = this.f4578D; methodWriter != null; methodWriter = (MethodWriter) methodWriter.f4688mv) {
                i5++;
                i3 += methodWriter.mo76832a();
            }
            ByteVector byteVector2 = this.f4575A;
            String str3 = "BootstrapMethods";
            if (byteVector2 != null) {
                int i6 = i3 + byteVector2.f4568b + 8;
                newUTF8(str3);
                i = i6;
                i2 = 1;
            } else {
                i = i3;
                i2 = 0;
            }
            String str4 = "Signature";
            if (this.f4600m != 0) {
                i2++;
                i += 8;
                newUTF8(str4);
            }
            String str5 = "SourceFile";
            if (this.f4604q != 0) {
                i2++;
                i += 8;
                newUTF8(str5);
            }
            ByteVector byteVector3 = this.f4605r;
            String str6 = "SourceDebugExtension";
            if (byteVector3 != null) {
                i2++;
                i += byteVector3.f4568b + 6;
                newUTF8(str6);
            }
            String str7 = "EnclosingMethod";
            if (this.f4606s != 0) {
                i2++;
                i += 10;
                newUTF8(str7);
            }
            if ((this.f4598k & 131072) != 0) {
                i2++;
                i += 6;
                newUTF8("Deprecated");
            }
            int i7 = this.f4598k;
            if ((i7 & 4096) != 0 && ((this.f4589b & 65535) < 49 || (i7 & 262144) != 0)) {
                i2++;
                i += 6;
                newUTF8("Synthetic");
            }
            ByteVector byteVector4 = this.f4612y;
            if (byteVector4 != null) {
                i2++;
                i += byteVector4.f4568b + 8;
                newUTF8("InnerClasses");
            }
            AnnotationWriter annotationWriter = this.f4608u;
            if (annotationWriter != null) {
                i2++;
                i += annotationWriter.mo76695a() + 8;
                newUTF8("RuntimeVisibleAnnotations");
            }
            AnnotationWriter annotationWriter2 = this.f4609v;
            if (annotationWriter2 != null) {
                i2++;
                i += annotationWriter2.mo76695a() + 8;
                newUTF8("RuntimeInvisibleAnnotations");
            }
            AnnotationWriter annotationWriter3 = this.f4587N;
            if (annotationWriter3 != null) {
                i2++;
                i += annotationWriter3.mo76695a() + 8;
                newUTF8("RuntimeVisibleTypeAnnotations");
            }
            AnnotationWriter annotationWriter4 = this.f4588O;
            if (annotationWriter4 != null) {
                i2++;
                i += annotationWriter4.mo76695a() + 8;
                newUTF8("RuntimeInvisibleTypeAnnotations");
            }
            int i8 = i;
            Attribute attribute = this.f4610w;
            if (attribute != null) {
                str2 = str7;
                str = str6;
                i8 += this.f4610w.mo76698a(this, null, 0, -1, -1);
                i2 += attribute.mo76697a();
            } else {
                str2 = str7;
                str = str6;
            }
            ByteVector byteVector5 = new ByteVector(i8 + this.f4591d.f4568b);
            byteVector5.putInt(-889275714).putInt(this.f4589b);
            byteVector5.putShort(this.f4590c).putByteArray(this.f4591d.f4567a, 0, this.f4591d.f4568b);
            int i9 = this.f4598k;
            byteVector5.putShort(((393216 | ((i9 & 262144) / 64)) ^ -1) & i9).putShort(this.f4599l).putShort(this.f4601n);
            byteVector5.putShort(this.f4602o);
            for (int i10 = 0; i10 < this.f4602o; i10++) {
                byteVector5.putShort(this.f4603p[i10]);
            }
            byteVector5.putShort(i4);
            for (FieldWriter fieldWriter2 = this.f4576B; fieldWriter2 != null; fieldWriter2 = (FieldWriter) fieldWriter2.f4637fv) {
                fieldWriter2.mo76773a(byteVector5);
            }
            byteVector5.putShort(i5);
            for (MethodWriter methodWriter2 = this.f4578D; methodWriter2 != null; methodWriter2 = (MethodWriter) methodWriter2.f4688mv) {
                methodWriter2.mo76833a(byteVector5);
            }
            byteVector5.putShort(i2);
            if (this.f4575A != null) {
                byteVector5.putShort(newUTF8(str3));
                byteVector5.putInt(this.f4575A.f4568b + 2).putShort(this.f4613z);
                byteVector5.putByteArray(this.f4575A.f4567a, 0, this.f4575A.f4568b);
            }
            if (this.f4600m != 0) {
                byteVector5.putShort(newUTF8(str4)).putInt(2).putShort(this.f4600m);
            }
            if (this.f4604q != 0) {
                byteVector5.putShort(newUTF8(str5)).putInt(2).putShort(this.f4604q);
            }
            ByteVector byteVector6 = this.f4605r;
            if (byteVector6 != null) {
                int i11 = byteVector6.f4568b;
                byteVector5.putShort(newUTF8(str)).putInt(i11);
                byteVector5.putByteArray(this.f4605r.f4567a, 0, i11);
            }
            if (this.f4606s != 0) {
                byteVector5.putShort(newUTF8(str2)).putInt(4);
                byteVector5.putShort(this.f4606s).putShort(this.f4607t);
            }
            if ((this.f4598k & 131072) != 0) {
                byteVector5.putShort(newUTF8("Deprecated")).putInt(0);
            }
            int i12 = this.f4598k;
            if ((i12 & 4096) != 0 && ((this.f4589b & 65535) < 49 || (i12 & 262144) != 0)) {
                byteVector5.putShort(newUTF8("Synthetic")).putInt(0);
            }
            if (this.f4612y != null) {
                byteVector5.putShort(newUTF8("InnerClasses"));
                byteVector5.putInt(this.f4612y.f4568b + 2).putShort(this.f4611x);
                byteVector5.putByteArray(this.f4612y.f4567a, 0, this.f4612y.f4568b);
            }
            if (this.f4608u != null) {
                byteVector5.putShort(newUTF8("RuntimeVisibleAnnotations"));
                this.f4608u.mo76696a(byteVector5);
            }
            if (this.f4609v != null) {
                byteVector5.putShort(newUTF8("RuntimeInvisibleAnnotations"));
                this.f4609v.mo76696a(byteVector5);
            }
            if (this.f4587N != null) {
                byteVector5.putShort(newUTF8("RuntimeVisibleTypeAnnotations"));
                this.f4587N.mo76696a(byteVector5);
            }
            if (this.f4588O != null) {
                byteVector5.putShort(newUTF8("RuntimeInvisibleTypeAnnotations"));
                this.f4588O.mo76696a(byteVector5);
            }
            Attribute attribute2 = this.f4610w;
            if (attribute2 != null) {
                byteVector = byteVector5;
                attribute2.mo76699a(this, null, 0, -1, -1, byteVector);
            } else {
                byteVector = byteVector5;
            }
            if (!this.f4585L) {
                return byteVector.f4567a;
            }
            this.f4608u = null;
            this.f4609v = null;
            this.f4610w = null;
            this.f4611x = 0;
            this.f4612y = null;
            this.f4613z = 0;
            this.f4575A = null;
            this.f4576B = null;
            this.f4577C = null;
            this.f4578D = null;
            this.f4579E = null;
            this.f4584K = false;
            this.f4583J = true;
            this.f4585L = false;
            new ClassReader(byteVector.f4567a).accept(this, 4);
            return toByteArray();
        }
        throw new RuntimeException("Class file too large!");
    }

    public final void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
        this.f4589b = i;
        this.f4598k = i2;
        this.f4599l = newClass(str);
        this.f4582I = str;
        if (str2 != null) {
            this.f4600m = newUTF8(str2);
        }
        this.f4601n = str3 == null ? 0 : newClass(str3);
        if (strArr != null && strArr.length > 0) {
            this.f4602o = strArr.length;
            this.f4603p = new int[this.f4602o];
            for (int i3 = 0; i3 < this.f4602o; i3++) {
                this.f4603p[i3] = newClass(strArr[i3]);
            }
        }
    }

    public final AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.f4563g = this.f4608u;
            this.f4608u = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4609v;
            this.f4609v = annotationWriter;
        }
        return annotationWriter;
    }

    public final void visitAttribute(Attribute attribute) {
        attribute.f4565a = this.f4610w;
        this.f4610w = attribute;
    }

    public final void visitEnd() {
    }

    public final FieldVisitor visitField(int i, String str, String str2, String str3, Object obj) {
        FieldWriter fieldWriter = new FieldWriter(this, i, str, str2, str3, obj);
        return fieldWriter;
    }

    public final void visitInnerClass(String str, String str2, String str3, int i) {
        if (this.f4612y == null) {
            this.f4612y = new ByteVector();
        }
        Item a = mo76751a(str);
        if (a.f4670c == 0) {
            this.f4611x++;
            this.f4612y.putShort(a.f4668a);
            int i2 = 0;
            this.f4612y.putShort(str2 == null ? 0 : newClass(str2));
            ByteVector byteVector = this.f4612y;
            if (str3 != null) {
                i2 = newUTF8(str3);
            }
            byteVector.putShort(i2);
            this.f4612y.putShort(i);
            a.f4670c = this.f4611x;
        }
    }

    public final MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        MethodWriter methodWriter = new MethodWriter(this, i, str, str2, str3, strArr, this.f4584K, this.f4583J);
        return methodWriter;
    }

    public final void visitOuterClass(String str, String str2, String str3) {
        this.f4606s = newClass(str);
        if (str2 != null && str3 != null) {
            this.f4607t = newNameType(str2, str3);
        }
    }

    public final void visitSource(String str, String str2) {
        if (str != null) {
            this.f4604q = newUTF8(str);
        }
        if (str2 != null) {
            this.f4605r = new ByteVector().mo76707c(str2, 0, Integer.MAX_VALUE);
        }
    }

    public final AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.m4144a(i, typePath, byteVector);
        byteVector.putShort(newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this, true, byteVector, byteVector, byteVector.f4568b - 2);
        if (z) {
            annotationWriter.f4563g = this.f4587N;
            this.f4587N = annotationWriter;
        } else {
            annotationWriter.f4563g = this.f4588O;
            this.f4588O = annotationWriter;
        }
        return annotationWriter;
    }
}
