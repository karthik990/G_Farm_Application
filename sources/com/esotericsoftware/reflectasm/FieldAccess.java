package com.esotericsoftware.reflectasm;

import com.fasterxml.jackson.core.JsonPointer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public abstract class FieldAccess {
    private String[] fieldNames;
    private Class[] fieldTypes;

    public abstract Object get(Object obj, int i);

    public abstract boolean getBoolean(Object obj, int i);

    public abstract byte getByte(Object obj, int i);

    public abstract char getChar(Object obj, int i);

    public abstract double getDouble(Object obj, int i);

    public abstract float getFloat(Object obj, int i);

    public abstract int getInt(Object obj, int i);

    public abstract long getLong(Object obj, int i);

    public abstract short getShort(Object obj, int i);

    public abstract String getString(Object obj, int i);

    public abstract void set(Object obj, int i, Object obj2);

    public abstract void setBoolean(Object obj, int i, boolean z);

    public abstract void setByte(Object obj, int i, byte b);

    public abstract void setChar(Object obj, int i, char c);

    public abstract void setDouble(Object obj, int i, double d);

    public abstract void setFloat(Object obj, int i, float f);

    public abstract void setInt(Object obj, int i, int i2);

    public abstract void setLong(Object obj, int i, long j);

    public abstract void setShort(Object obj, int i, short s);

    public int getIndex(String str) {
        int length = this.fieldNames.length;
        for (int i = 0; i < length; i++) {
            if (this.fieldNames[i].equals(str)) {
                return i;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unable to find non-private field: ");
        sb.append(str);
        throw new IllegalArgumentException(sb.toString());
    }

    public void set(Object obj, String str, Object obj2) {
        set(obj, getIndex(str), obj2);
    }

    public Object get(Object obj, String str) {
        return get(obj, getIndex(str));
    }

    public String[] getFieldNames() {
        return this.fieldNames;
    }

    public Class[] getFieldTypes() {
        return this.fieldTypes;
    }

    public int getFieldCount() {
        return this.fieldTypes.length;
    }

    public static FieldAccess get(Class cls) {
        Class cls2;
        Class cls3;
        Field[] declaredFields;
        ArrayList arrayList = new ArrayList();
        Class cls4 = cls;
        while (true) {
            if (cls4 == Object.class) {
                break;
            }
            for (Field field : cls4.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) && !Modifier.isPrivate(modifiers)) {
                    arrayList.add(field);
                }
            }
            cls4 = cls4.getSuperclass();
        }
        String[] strArr = new String[arrayList.size()];
        Class[] clsArr = new Class[arrayList.size()];
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            strArr[i] = ((Field) arrayList.get(i)).getName();
            clsArr[i] = ((Field) arrayList.get(i)).getType();
        }
        String name = cls.getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("FieldAccess");
        String sb2 = sb.toString();
        if (sb2.startsWith("java.")) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("reflectasm.");
            sb3.append(sb2);
            sb2 = sb3.toString();
        }
        AccessClassLoader accessClassLoader = AccessClassLoader.get(cls);
        try {
            cls2 = accessClassLoader.loadClass(sb2);
        } catch (ClassNotFoundException unused) {
            synchronized (accessClassLoader) {
                try {
                    cls3 = accessClassLoader.loadClass(sb2);
                } catch (ClassNotFoundException unused2) {
                    String replace = sb2.replace('.', JsonPointer.SEPARATOR);
                    String replace2 = name.replace('.', JsonPointer.SEPARATOR);
                    ClassWriter classWriter = new ClassWriter(0);
                    classWriter.visit(Opcodes.V1_1, 33, replace, null, "com/esotericsoftware/reflectasm/FieldAccess", null);
                    insertConstructor(classWriter);
                    insertGetObject(classWriter, replace2, arrayList);
                    insertSetObject(classWriter, replace2, arrayList);
                    insertGetPrimitive(classWriter, replace2, arrayList, Type.BOOLEAN_TYPE);
                    insertSetPrimitive(classWriter, replace2, arrayList, Type.BOOLEAN_TYPE);
                    insertGetPrimitive(classWriter, replace2, arrayList, Type.BYTE_TYPE);
                    insertSetPrimitive(classWriter, replace2, arrayList, Type.BYTE_TYPE);
                    insertGetPrimitive(classWriter, replace2, arrayList, Type.SHORT_TYPE);
                    insertSetPrimitive(classWriter, replace2, arrayList, Type.SHORT_TYPE);
                    insertGetPrimitive(classWriter, replace2, arrayList, Type.INT_TYPE);
                    insertSetPrimitive(classWriter, replace2, arrayList, Type.INT_TYPE);
                    insertGetPrimitive(classWriter, replace2, arrayList, Type.LONG_TYPE);
                    insertSetPrimitive(classWriter, replace2, arrayList, Type.LONG_TYPE);
                    insertGetPrimitive(classWriter, replace2, arrayList, Type.DOUBLE_TYPE);
                    insertSetPrimitive(classWriter, replace2, arrayList, Type.DOUBLE_TYPE);
                    insertGetPrimitive(classWriter, replace2, arrayList, Type.FLOAT_TYPE);
                    insertSetPrimitive(classWriter, replace2, arrayList, Type.FLOAT_TYPE);
                    insertGetPrimitive(classWriter, replace2, arrayList, Type.CHAR_TYPE);
                    insertSetPrimitive(classWriter, replace2, arrayList, Type.CHAR_TYPE);
                    insertGetString(classWriter, replace2, arrayList);
                    classWriter.visitEnd();
                    cls3 = accessClassLoader.defineClass(sb2, classWriter.toByteArray());
                }
                cls2 = cls3;
            }
        }
        try {
            FieldAccess fieldAccess = (FieldAccess) cls2.newInstance();
            fieldAccess.fieldNames = strArr;
            fieldAccess.fieldTypes = clsArr;
            return fieldAccess;
        } catch (Throwable th) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Error constructing field access class: ");
            sb4.append(sb2);
            throw new RuntimeException(sb4.toString(), th);
        }
    }

    private static void insertConstructor(ClassWriter classWriter) {
        MethodVisitor visitMethod = classWriter.visitMethod(1, "<init>", "()V", null, null);
        visitMethod.visitCode();
        visitMethod.visitVarInsn(25, 0);
        visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/esotericsoftware/reflectasm/FieldAccess", "<init>", "()V");
        visitMethod.visitInsn(Opcodes.RETURN);
        visitMethod.visitMaxs(1, 1);
        visitMethod.visitEnd();
    }

    private static void insertSetObject(ClassWriter classWriter, String str, ArrayList<Field> arrayList) {
        int i;
        String str2 = str;
        MethodVisitor visitMethod = classWriter.visitMethod(1, "set", "(Ljava/lang/Object;ILjava/lang/Object;)V", null, null);
        visitMethod.visitCode();
        visitMethod.visitVarInsn(21, 2);
        if (!arrayList.isEmpty()) {
            i = 5;
            Label[] labelArr = new Label[arrayList.size()];
            int length = labelArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                labelArr[i2] = new Label();
            }
            Label label = new Label();
            visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label, labelArr);
            int length2 = labelArr.length;
            for (int i3 = 0; i3 < length2; i3++) {
                Field field = (Field) arrayList.get(i3);
                Type type = Type.getType(field.getType());
                visitMethod.visitLabel(labelArr[i3]);
                visitMethod.visitFrame(3, 0, null, 0, null);
                visitMethod.visitVarInsn(25, 1);
                visitMethod.visitTypeInsn(192, str2);
                visitMethod.visitVarInsn(25, 3);
                switch (type.getSort()) {
                    case 1:
                        String str3 = "java/lang/Boolean";
                        visitMethod.visitTypeInsn(192, str3);
                        visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, "booleanValue", "()Z");
                        break;
                    case 2:
                        String str4 = "java/lang/Character";
                        visitMethod.visitTypeInsn(192, str4);
                        visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str4, "charValue", "()C");
                        break;
                    case 3:
                        String str5 = "java/lang/Byte";
                        visitMethod.visitTypeInsn(192, str5);
                        visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str5, "byteValue", "()B");
                        break;
                    case 4:
                        String str6 = "java/lang/Short";
                        visitMethod.visitTypeInsn(192, str6);
                        visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str6, "shortValue", "()S");
                        break;
                    case 5:
                        String str7 = "java/lang/Integer";
                        visitMethod.visitTypeInsn(192, str7);
                        visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str7, "intValue", "()I");
                        break;
                    case 6:
                        String str8 = "java/lang/Float";
                        visitMethod.visitTypeInsn(192, str8);
                        visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str8, "floatValue", "()F");
                        break;
                    case 7:
                        String str9 = "java/lang/Long";
                        visitMethod.visitTypeInsn(192, str9);
                        visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str9, "longValue", "()J");
                        break;
                    case 8:
                        String str10 = "java/lang/Double";
                        visitMethod.visitTypeInsn(192, str10);
                        visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str10, "doubleValue", "()D");
                        break;
                    case 9:
                        visitMethod.visitTypeInsn(192, type.getDescriptor());
                        break;
                    case 10:
                        visitMethod.visitTypeInsn(192, type.getInternalName());
                        break;
                }
                visitMethod.visitFieldInsn(Opcodes.PUTFIELD, str2, field.getName(), type.getDescriptor());
                visitMethod.visitInsn(Opcodes.RETURN);
            }
            visitMethod.visitLabel(label);
            visitMethod.visitFrame(3, 0, null, 0, null);
        } else {
            i = 6;
        }
        MethodVisitor insertThrowExceptionForFieldNotFound = insertThrowExceptionForFieldNotFound(visitMethod);
        insertThrowExceptionForFieldNotFound.visitMaxs(i, 4);
        insertThrowExceptionForFieldNotFound.visitEnd();
    }

    private static void insertGetObject(ClassWriter classWriter, String str, ArrayList<Field> arrayList) {
        int i;
        MethodVisitor visitMethod = classWriter.visitMethod(1, "get", "(Ljava/lang/Object;I)Ljava/lang/Object;", null, null);
        visitMethod.visitCode();
        visitMethod.visitVarInsn(21, 2);
        if (!arrayList.isEmpty()) {
            i = 5;
            Label[] labelArr = new Label[arrayList.size()];
            int length = labelArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                labelArr[i2] = new Label();
            }
            Label label = new Label();
            visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label, labelArr);
            int length2 = labelArr.length;
            for (int i3 = 0; i3 < length2; i3++) {
                Field field = (Field) arrayList.get(i3);
                visitMethod.visitLabel(labelArr[i3]);
                visitMethod.visitFrame(3, 0, null, 0, null);
                visitMethod.visitVarInsn(25, 1);
                visitMethod.visitTypeInsn(192, str);
                visitMethod.visitFieldInsn(Opcodes.GETFIELD, str, field.getName(), Type.getDescriptor(field.getType()));
                String str2 = "valueOf";
                switch (Type.getType(field.getType()).getSort()) {
                    case 1:
                        visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", str2, "(Z)Ljava/lang/Boolean;");
                        break;
                    case 2:
                        visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", str2, "(C)Ljava/lang/Character;");
                        break;
                    case 3:
                        visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", str2, "(B)Ljava/lang/Byte;");
                        break;
                    case 4:
                        visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", str2, "(S)Ljava/lang/Short;");
                        break;
                    case 5:
                        visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", str2, "(I)Ljava/lang/Integer;");
                        break;
                    case 6:
                        visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", str2, "(F)Ljava/lang/Float;");
                        break;
                    case 7:
                        visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", str2, "(J)Ljava/lang/Long;");
                        break;
                    case 8:
                        visitMethod.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", str2, "(D)Ljava/lang/Double;");
                        break;
                }
                visitMethod.visitInsn(Opcodes.ARETURN);
            }
            visitMethod.visitLabel(label);
            visitMethod.visitFrame(3, 0, null, 0, null);
        } else {
            i = 6;
        }
        insertThrowExceptionForFieldNotFound(visitMethod);
        visitMethod.visitMaxs(i, 3);
        visitMethod.visitEnd();
    }

    private static void insertGetString(ClassWriter classWriter, String str, ArrayList<Field> arrayList) {
        int i;
        int i2;
        String str2 = str;
        ArrayList<Field> arrayList2 = arrayList;
        MethodVisitor visitMethod = classWriter.visitMethod(1, "getString", "(Ljava/lang/Object;I)Ljava/lang/String;", null, null);
        visitMethod.visitCode();
        visitMethod.visitVarInsn(21, 2);
        if (!arrayList.isEmpty()) {
            i = 5;
            Label[] labelArr = new Label[arrayList.size()];
            Label label = new Label();
            int length = labelArr.length;
            int i3 = 0;
            boolean z = false;
            for (int i4 = 0; i4 < length; i4++) {
                if (((Field) arrayList2.get(i4)).getType().equals(String.class)) {
                    labelArr[i4] = new Label();
                } else {
                    labelArr[i4] = label;
                    z = true;
                }
            }
            Label label2 = new Label();
            visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label2, labelArr);
            int length2 = labelArr.length;
            while (i3 < length2) {
                if (!labelArr[i3].equals(label)) {
                    visitMethod.visitLabel(labelArr[i3]);
                    i2 = length2;
                    visitMethod.visitFrame(3, 0, null, 0, null);
                    visitMethod.visitVarInsn(25, 1);
                    visitMethod.visitTypeInsn(192, str2);
                    visitMethod.visitFieldInsn(Opcodes.GETFIELD, str2, ((Field) arrayList2.get(i3)).getName(), "Ljava/lang/String;");
                    visitMethod.visitInsn(Opcodes.ARETURN);
                } else {
                    i2 = length2;
                }
                i3++;
                length2 = i2;
            }
            if (z) {
                visitMethod.visitLabel(label);
                visitMethod.visitFrame(3, 0, null, 0, null);
                insertThrowExceptionForFieldType(visitMethod, "String");
            }
            visitMethod.visitLabel(label2);
            visitMethod.visitFrame(3, 0, null, 0, null);
        } else {
            i = 6;
        }
        insertThrowExceptionForFieldNotFound(visitMethod);
        visitMethod.visitMaxs(i, 3);
        visitMethod.visitEnd();
    }

    /* JADX INFO: used method not loaded: org.objectweb.asm.Type.getType(java.lang.Class):null, types can be incorrect */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0038, code lost:
        r10 = r3;
        r3 = 21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003b, code lost:
        r8 = new java.lang.StringBuilder();
        r8.append("(Ljava/lang/Object;I");
        r8.append(r2);
        r8.append(")V");
        r8 = r21.visitMethod(1, r10, r8.toString(), null, null);
        r8.visitCode();
        r8.visitVarInsn(21, 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0065, code lost:
        if (r23.isEmpty() != false) goto L_0x0126;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0067, code lost:
        r6 = new org.objectweb.asm.Label[r23.size()];
        r9 = new org.objectweb.asm.Label();
        r10 = r6.length;
        r11 = 0;
        r12 = 0;
        r13 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0077, code lost:
        if (r12 >= r10) goto L_0x009d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x008d, code lost:
        if (org.objectweb.asm.Type.getType(((java.lang.reflect.Field) r1.get(r12)).getType()).equals(r24) == false) goto L_0x0097;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x008f, code lost:
        r6[r12] = new org.objectweb.asm.Label();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0097, code lost:
        r6[r12] = r9;
        r13 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009a, code lost:
        r12 = r12 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x009d, code lost:
        r5 = r24;
        r10 = new org.objectweb.asm.Label();
        r8.visitTableSwitchInsn(0, r6.length - 1, r10, r6);
        r12 = r6.length;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00aa, code lost:
        if (r11 >= r12) goto L_0x00fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00b2, code lost:
        if (r6[r11].equals(r9) != false) goto L_0x00f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00b4, code lost:
        r8.visitLabel(r6[r11]);
        r8.visitFrame(3, 0, null, 0, null);
        r8.visitVarInsn(25, 1);
        r8.visitTypeInsn(192, r0);
        r8.visitVarInsn(r3, 3);
        r8.visitFieldInsn(org.objectweb.asm.Opcodes.PUTFIELD, r0, ((java.lang.reflect.Field) r1.get(r11)).getName(), r2);
        r8.visitInsn(org.objectweb.asm.Opcodes.RETURN);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00f7, code lost:
        r11 = r11 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00fb, code lost:
        if (r13 == false) goto L_0x0114;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x00fd, code lost:
        r8.visitLabel(r9);
        r8.visitFrame(3, 0, null, 0, null);
        insertThrowExceptionForFieldType(r8, r24.getClassName());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0114, code lost:
        r8.visitLabel(r10);
        r8.visitFrame(3, 0, null, 0, null);
        r5 = 5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0126, code lost:
        r5 = 6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0127, code lost:
        r0 = insertThrowExceptionForFieldNotFound(r8);
        r0.visitMaxs(r5, r7);
        r0.visitEnd();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0131, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0021, code lost:
        r10 = r7;
        r7 = 5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void insertSetPrimitive(org.objectweb.asm.ClassWriter r21, java.lang.String r22, java.util.ArrayList<java.lang.reflect.Field> r23, org.objectweb.asm.Type r24) {
        /*
            r0 = r22
            r1 = r23
            java.lang.String r2 = r24.getDescriptor()
            int r3 = r24.getSort()
            r6 = 21
            r7 = 4
            switch(r3) {
                case 1: goto L_0x0036;
                case 2: goto L_0x0033;
                case 3: goto L_0x0030;
                case 4: goto L_0x002d;
                case 5: goto L_0x002a;
                case 6: goto L_0x0024;
                case 7: goto L_0x001d;
                case 8: goto L_0x0018;
                default: goto L_0x0012;
            }
        L_0x0012:
            java.lang.String r3 = "set"
            r10 = r3
            r3 = 25
            goto L_0x003b
        L_0x0018:
            r3 = 24
            java.lang.String r7 = "setDouble"
            goto L_0x0021
        L_0x001d:
            r3 = 22
            java.lang.String r7 = "setLong"
        L_0x0021:
            r10 = r7
            r7 = 5
            goto L_0x003b
        L_0x0024:
            r3 = 23
            java.lang.String r8 = "setFloat"
            r10 = r8
            goto L_0x003b
        L_0x002a:
            java.lang.String r3 = "setInt"
            goto L_0x0038
        L_0x002d:
            java.lang.String r3 = "setShort"
            goto L_0x0038
        L_0x0030:
            java.lang.String r3 = "setByte"
            goto L_0x0038
        L_0x0033:
            java.lang.String r3 = "setChar"
            goto L_0x0038
        L_0x0036:
            java.lang.String r3 = "setBoolean"
        L_0x0038:
            r10 = r3
            r3 = 21
        L_0x003b:
            r9 = 1
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r11 = "(Ljava/lang/Object;I"
            r8.append(r11)
            r8.append(r2)
            java.lang.String r11 = ")V"
            r8.append(r11)
            java.lang.String r11 = r8.toString()
            r12 = 0
            r13 = 0
            r8 = r21
            org.objectweb.asm.MethodVisitor r8 = r8.visitMethod(r9, r10, r11, r12, r13)
            r8.visitCode()
            r9 = 2
            r8.visitVarInsn(r6, r9)
            boolean r6 = r23.isEmpty()
            if (r6 != 0) goto L_0x0126
            int r6 = r23.size()
            org.objectweb.asm.Label[] r6 = new org.objectweb.asm.Label[r6]
            org.objectweb.asm.Label r9 = new org.objectweb.asm.Label
            r9.<init>()
            int r10 = r6.length
            r11 = 0
            r12 = 0
            r13 = 0
        L_0x0076:
            r15 = 1
            if (r12 >= r10) goto L_0x009d
            java.lang.Object r14 = r1.get(r12)
            java.lang.reflect.Field r14 = (java.lang.reflect.Field) r14
            java.lang.Class r14 = r14.getType()
            org.objectweb.asm.Type r14 = org.objectweb.asm.Type.getType(r14)
            r5 = r24
            boolean r14 = r14.equals(r5)
            if (r14 == 0) goto L_0x0097
            org.objectweb.asm.Label r14 = new org.objectweb.asm.Label
            r14.<init>()
            r6[r12] = r14
            goto L_0x009a
        L_0x0097:
            r6[r12] = r9
            r13 = 1
        L_0x009a:
            int r12 = r12 + 1
            goto L_0x0076
        L_0x009d:
            r5 = r24
            org.objectweb.asm.Label r10 = new org.objectweb.asm.Label
            r10.<init>()
            int r12 = r6.length
            int r12 = r12 - r15
            r8.visitTableSwitchInsn(r11, r12, r10, r6)
            int r12 = r6.length
        L_0x00aa:
            if (r11 >= r12) goto L_0x00fb
            r14 = r6[r11]
            boolean r14 = r14.equals(r9)
            if (r14 != 0) goto L_0x00f5
            r14 = r6[r11]
            r8.visitLabel(r14)
            r16 = 3
            r17 = 0
            r18 = 0
            r19 = 0
            r20 = 0
            r14 = r8
            r4 = 1
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r19
            r19 = r20
            r14.visitFrame(r15, r16, r17, r18, r19)
            r14 = 25
            r8.visitVarInsn(r14, r4)
            r15 = 192(0xc0, float:2.69E-43)
            r8.visitTypeInsn(r15, r0)
            r15 = 3
            r8.visitVarInsn(r3, r15)
            r15 = 181(0xb5, float:2.54E-43)
            java.lang.Object r16 = r1.get(r11)
            java.lang.reflect.Field r16 = (java.lang.reflect.Field) r16
            java.lang.String r4 = r16.getName()
            r8.visitFieldInsn(r15, r0, r4, r2)
            r4 = 177(0xb1, float:2.48E-43)
            r8.visitInsn(r4)
            goto L_0x00f7
        L_0x00f5:
            r14 = 25
        L_0x00f7:
            int r11 = r11 + 1
            r15 = 1
            goto L_0x00aa
        L_0x00fb:
            if (r13 == 0) goto L_0x0114
            r8.visitLabel(r9)
            r15 = 3
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r14 = r8
            r14.visitFrame(r15, r16, r17, r18, r19)
            java.lang.String r0 = r24.getClassName()
            insertThrowExceptionForFieldType(r8, r0)
        L_0x0114:
            r8.visitLabel(r10)
            r15 = 3
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            r14 = r8
            r14.visitFrame(r15, r16, r17, r18, r19)
            r5 = 5
            goto L_0x0127
        L_0x0126:
            r5 = 6
        L_0x0127:
            org.objectweb.asm.MethodVisitor r0 = insertThrowExceptionForFieldNotFound(r8)
            r0.visitMaxs(r5, r7)
            r0.visitEnd()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.esotericsoftware.reflectasm.FieldAccess.insertSetPrimitive(org.objectweb.asm.ClassWriter, java.lang.String, java.util.ArrayList, org.objectweb.asm.Type):void");
    }

    private static void insertGetPrimitive(ClassWriter classWriter, String str, ArrayList<Field> arrayList, Type type) {
        String str2;
        int i;
        String str3 = str;
        ArrayList<Field> arrayList2 = arrayList;
        String descriptor = type.getDescriptor();
        int i2 = 172;
        switch (type.getSort()) {
            case 1:
                str2 = "getBoolean";
                break;
            case 2:
                str2 = "getChar";
                break;
            case 3:
                str2 = "getByte";
                break;
            case 4:
                str2 = "getShort";
                break;
            case 5:
                str2 = "getInt";
                break;
            case 6:
                i2 = Opcodes.FRETURN;
                str2 = "getFloat";
                break;
            case 7:
                i2 = 173;
                str2 = "getLong";
                break;
            case 8:
                i2 = Opcodes.DRETURN;
                str2 = "getDouble";
                break;
            default:
                i2 = Opcodes.ARETURN;
                str2 = "get";
                break;
        }
        String str4 = str2;
        StringBuilder sb = new StringBuilder();
        sb.append("(Ljava/lang/Object;I)");
        sb.append(descriptor);
        MethodVisitor visitMethod = classWriter.visitMethod(1, str4, sb.toString(), null, null);
        visitMethod.visitCode();
        visitMethod.visitVarInsn(21, 2);
        if (!arrayList.isEmpty()) {
            Label[] labelArr = new Label[arrayList.size()];
            Label label = new Label();
            int length = labelArr.length;
            int i3 = 0;
            boolean z = false;
            for (int i4 = 0; i4 < length; i4++) {
                if (Type.getType(((Field) arrayList2.get(i4)).getType()).equals(type)) {
                    labelArr[i4] = new Label();
                } else {
                    labelArr[i4] = label;
                    z = true;
                }
            }
            Type type2 = type;
            Label label2 = new Label();
            visitMethod.visitTableSwitchInsn(0, labelArr.length - 1, label2, labelArr);
            int length2 = labelArr.length;
            while (i3 < length2) {
                Field field = (Field) arrayList2.get(i3);
                if (!labelArr[i3].equals(label)) {
                    visitMethod.visitLabel(labelArr[i3]);
                    visitMethod.visitFrame(3, 0, null, 0, null);
                    visitMethod.visitVarInsn(25, 1);
                    visitMethod.visitTypeInsn(192, str3);
                    visitMethod.visitFieldInsn(Opcodes.GETFIELD, str3, field.getName(), descriptor);
                    visitMethod.visitInsn(i2);
                }
                i3++;
                Type type3 = type;
            }
            if (z) {
                visitMethod.visitLabel(label);
                visitMethod.visitFrame(3, 0, null, 0, null);
                insertThrowExceptionForFieldType(visitMethod, type.getClassName());
            }
            visitMethod.visitLabel(label2);
            visitMethod.visitFrame(3, 0, null, 0, null);
            i = 5;
        } else {
            i = 6;
        }
        MethodVisitor insertThrowExceptionForFieldNotFound = insertThrowExceptionForFieldNotFound(visitMethod);
        insertThrowExceptionForFieldNotFound.visitMaxs(i, 3);
        insertThrowExceptionForFieldNotFound.visitEnd();
    }

    private static MethodVisitor insertThrowExceptionForFieldNotFound(MethodVisitor methodVisitor) {
        String str = "java/lang/IllegalArgumentException";
        methodVisitor.visitTypeInsn(Opcodes.NEW, str);
        methodVisitor.visitInsn(89);
        String str2 = "java/lang/StringBuilder";
        methodVisitor.visitTypeInsn(Opcodes.NEW, str2);
        methodVisitor.visitInsn(89);
        methodVisitor.visitLdcInsn("Field not found: ");
        String str3 = "(Ljava/lang/String;)V";
        String str4 = "<init>";
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, str2, str4, str3);
        methodVisitor.visitVarInsn(21, 2);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "append", "(I)Ljava/lang/StringBuilder;");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str2, "toString", "()Ljava/lang/String;");
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, str, str4, str3);
        methodVisitor.visitInsn(Opcodes.ATHROW);
        return methodVisitor;
    }

    private static MethodVisitor insertThrowExceptionForFieldType(MethodVisitor methodVisitor, String str) {
        String str2 = "java/lang/IllegalArgumentException";
        methodVisitor.visitTypeInsn(Opcodes.NEW, str2);
        methodVisitor.visitInsn(89);
        String str3 = "java/lang/StringBuilder";
        methodVisitor.visitTypeInsn(Opcodes.NEW, str3);
        methodVisitor.visitInsn(89);
        StringBuilder sb = new StringBuilder();
        sb.append("Field not declared as ");
        sb.append(str);
        sb.append(": ");
        methodVisitor.visitLdcInsn(sb.toString());
        String str4 = "(Ljava/lang/String;)V";
        String str5 = "<init>";
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, str3, str5, str4);
        methodVisitor.visitVarInsn(21, 2);
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, "append", "(I)Ljava/lang/StringBuilder;");
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, str3, "toString", "()Ljava/lang/String;");
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, str2, str5, str4);
        methodVisitor.visitInsn(Opcodes.ATHROW);
        return methodVisitor;
    }
}
