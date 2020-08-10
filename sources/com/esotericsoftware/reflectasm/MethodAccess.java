package com.esotericsoftware.reflectasm;

import com.fasterxml.jackson.core.JsonPointer;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public abstract class MethodAccess {
    private String[] methodNames;
    private Class[][] parameterTypes;
    private Class[] returnTypes;

    public abstract Object invoke(Object obj, int i, Object... objArr);

    public Object invoke(Object obj, String str, Class[] clsArr, Object... objArr) {
        return invoke(obj, getIndex(str, clsArr), objArr);
    }

    public Object invoke(Object obj, String str, Object... objArr) {
        return invoke(obj, getIndex(str, objArr == null ? 0 : objArr.length), objArr);
    }

    public int getIndex(String str) {
        int length = this.methodNames.length;
        for (int i = 0; i < length; i++) {
            if (this.methodNames[i].equals(str)) {
                return i;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unable to find non-private method: ");
        sb.append(str);
        throw new IllegalArgumentException(sb.toString());
    }

    public int getIndex(String str, Class... clsArr) {
        int length = this.methodNames.length;
        for (int i = 0; i < length; i++) {
            if (this.methodNames[i].equals(str) && Arrays.equals(clsArr, this.parameterTypes[i])) {
                return i;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unable to find non-private method: ");
        sb.append(str);
        sb.append(" ");
        sb.append(Arrays.toString(clsArr));
        throw new IllegalArgumentException(sb.toString());
    }

    public int getIndex(String str, int i) {
        int length = this.methodNames.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (this.methodNames[i2].equals(str) && this.parameterTypes[i2].length == i) {
                return i2;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unable to find non-private method: ");
        sb.append(str);
        sb.append(" with ");
        sb.append(i);
        sb.append(" params.");
        throw new IllegalArgumentException(sb.toString());
    }

    public String[] getMethodNames() {
        return this.methodNames;
    }

    public Class[][] getParameterTypes() {
        return this.parameterTypes;
    }

    public Class[] getReturnTypes() {
        return this.returnTypes;
    }

    public static MethodAccess get(Class cls) {
        Class[][] clsArr;
        Class[] clsArr2;
        Class cls2;
        String str;
        int i;
        String str2;
        Class[][] clsArr3;
        Class[] clsArr4;
        ArrayList arrayList = new ArrayList();
        boolean isInterface = cls.isInterface();
        if (!isInterface) {
            for (Class cls3 = cls; cls3 != Object.class; cls3 = cls3.getSuperclass()) {
                addDeclaredMethodsToList(cls3, arrayList);
            }
            Class cls4 = cls;
        } else {
            recursiveAddInterfaceMethodsToList(cls, arrayList);
        }
        int size = arrayList.size();
        String[] strArr = new String[size];
        Class[][] clsArr5 = new Class[size][];
        Class[] clsArr6 = new Class[size];
        for (int i2 = 0; i2 < size; i2++) {
            Method method = (Method) arrayList.get(i2);
            strArr[i2] = method.getName();
            clsArr5[i2] = method.getParameterTypes();
            clsArr6[i2] = method.getReturnType();
        }
        String name = cls.getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("MethodAccess");
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
            clsArr = clsArr5;
            clsArr2 = clsArr6;
        } catch (ClassNotFoundException unused) {
            synchronized (accessClassLoader) {
                try {
                    cls2 = accessClassLoader.loadClass(sb2);
                    clsArr = clsArr5;
                    clsArr2 = clsArr6;
                } catch (ClassNotFoundException unused2) {
                    String replace = sb2.replace('.', JsonPointer.SEPARATOR);
                    String replace2 = name.replace('.', JsonPointer.SEPARATOR);
                    ClassWriter classWriter = new ClassWriter(1);
                    classWriter.visit(Opcodes.V1_1, 33, replace, null, "com/esotericsoftware/reflectasm/MethodAccess", null);
                    MethodVisitor visitMethod = classWriter.visitMethod(1, "<init>", "()V", null, null);
                    visitMethod.visitCode();
                    visitMethod.visitVarInsn(25, 0);
                    visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/esotericsoftware/reflectasm/MethodAccess", "<init>", "()V");
                    visitMethod.visitInsn(Opcodes.RETURN);
                    visitMethod.visitMaxs(0, 0);
                    visitMethod.visitEnd();
                    MethodVisitor visitMethod2 = classWriter.visitMethod(129, "invoke", "(Ljava/lang/Object;I[Ljava/lang/Object;)Ljava/lang/Object;", null, null);
                    visitMethod2.visitCode();
                    if (!arrayList.isEmpty()) {
                        visitMethod2.visitVarInsn(25, 1);
                        visitMethod2.visitTypeInsn(192, replace2);
                        visitMethod2.visitVarInsn(58, 4);
                        visitMethod2.visitVarInsn(21, 2);
                        Label[] labelArr = new Label[size];
                        for (int i3 = 0; i3 < size; i3++) {
                            labelArr[i3] = new Label();
                        }
                        Label label = new Label();
                        visitMethod2.visitTableSwitchInsn(0, labelArr.length - 1, label, labelArr);
                        StringBuilder sb4 = new StringBuilder(128);
                        int i4 = 0;
                        while (i4 < size) {
                            visitMethod2.visitLabel(labelArr[i4]);
                            if (i4 == 0) {
                                i = size;
                                visitMethod2.visitFrame(1, 1, new Object[]{replace2}, 0, null);
                            } else {
                                i = size;
                                visitMethod2.visitFrame(3, 0, null, 0, null);
                            }
                            visitMethod2.visitVarInsn(25, 4);
                            sb4.setLength(0);
                            sb4.append('(');
                            Class[] clsArr7 = clsArr5[i4];
                            Class cls5 = clsArr6[i4];
                            Label[] labelArr2 = labelArr;
                            int i5 = 0;
                            while (i5 < clsArr7.length) {
                                Class[] clsArr8 = clsArr6;
                                visitMethod2.visitVarInsn(25, 3);
                                visitMethod2.visitIntInsn(16, i5);
                                visitMethod2.visitInsn(50);
                                Type type = Type.getType(clsArr7[i5]);
                                switch (type.getSort()) {
                                    case 1:
                                        clsArr3 = clsArr5;
                                        clsArr4 = clsArr7;
                                        str2 = sb2;
                                        visitMethod2.visitTypeInsn(192, "java/lang/Boolean");
                                        visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
                                        break;
                                    case 2:
                                        clsArr3 = clsArr5;
                                        clsArr4 = clsArr7;
                                        str2 = sb2;
                                        visitMethod2.visitTypeInsn(192, "java/lang/Character");
                                        visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
                                        break;
                                    case 3:
                                        clsArr3 = clsArr5;
                                        clsArr4 = clsArr7;
                                        str2 = sb2;
                                        visitMethod2.visitTypeInsn(192, "java/lang/Byte");
                                        visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
                                        break;
                                    case 4:
                                        clsArr3 = clsArr5;
                                        clsArr4 = clsArr7;
                                        str2 = sb2;
                                        visitMethod2.visitTypeInsn(192, "java/lang/Short");
                                        visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
                                        break;
                                    case 5:
                                        clsArr3 = clsArr5;
                                        clsArr4 = clsArr7;
                                        str2 = sb2;
                                        visitMethod2.visitTypeInsn(192, "java/lang/Integer");
                                        visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
                                        break;
                                    case 6:
                                        clsArr3 = clsArr5;
                                        clsArr4 = clsArr7;
                                        str2 = sb2;
                                        visitMethod2.visitTypeInsn(192, "java/lang/Float");
                                        visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
                                        break;
                                    case 7:
                                        clsArr3 = clsArr5;
                                        clsArr4 = clsArr7;
                                        str2 = sb2;
                                        visitMethod2.visitTypeInsn(192, "java/lang/Long");
                                        visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
                                        break;
                                    case 8:
                                        clsArr4 = clsArr7;
                                        visitMethod2.visitTypeInsn(192, "java/lang/Double");
                                        clsArr3 = clsArr5;
                                        str2 = sb2;
                                        visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
                                        break;
                                    case 9:
                                        clsArr4 = clsArr7;
                                        visitMethod2.visitTypeInsn(192, type.getDescriptor());
                                        break;
                                    case 10:
                                        clsArr4 = clsArr7;
                                        visitMethod2.visitTypeInsn(192, type.getInternalName());
                                        break;
                                    default:
                                        clsArr3 = clsArr5;
                                        clsArr4 = clsArr7;
                                        break;
                                }
                                clsArr3 = clsArr5;
                                str2 = sb2;
                                sb4.append(type.getDescriptor());
                                i5++;
                                clsArr7 = clsArr4;
                                clsArr6 = clsArr8;
                                clsArr5 = clsArr3;
                                sb2 = str2;
                            }
                            Class[][] clsArr9 = clsArr5;
                            Class[] clsArr10 = clsArr6;
                            String str3 = sb2;
                            sb4.append(')');
                            sb4.append(Type.getDescriptor(cls5));
                            int i6 = isInterface ? Opcodes.INVOKEINTERFACE : Modifier.isStatic(((Method) arrayList.get(i4)).getModifiers()) ? Opcodes.INVOKESTATIC : Opcodes.INVOKEVIRTUAL;
                            visitMethod2.visitMethodInsn(i6, replace2, strArr[i4], sb4.toString());
                            switch (Type.getType(cls5).getSort()) {
                                case 0:
                                    visitMethod2.visitInsn(1);
                                    continue;
                                case 1:
                                    visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
                                    break;
                                case 2:
                                    visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
                                    break;
                                case 3:
                                    visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                                    break;
                                case 4:
                                    visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                                    break;
                                case 5:
                                    visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                                    break;
                                case 6:
                                    visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                                    break;
                                case 7:
                                    visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                                    break;
                                case 8:
                                    visitMethod2.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                                    break;
                            }
                            visitMethod2.visitInsn(Opcodes.ARETURN);
                            i4++;
                            labelArr = labelArr2;
                            size = i;
                            clsArr6 = clsArr10;
                            clsArr5 = clsArr9;
                            sb2 = str3;
                        }
                        clsArr = clsArr5;
                        clsArr2 = clsArr6;
                        str = sb2;
                        visitMethod2.visitLabel(label);
                        visitMethod2.visitFrame(3, 0, null, 0, null);
                    } else {
                        clsArr = clsArr5;
                        clsArr2 = clsArr6;
                        str = sb2;
                    }
                    visitMethod2.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalArgumentException");
                    visitMethod2.visitInsn(89);
                    visitMethod2.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
                    visitMethod2.visitInsn(89);
                    visitMethod2.visitLdcInsn("Method not found: ");
                    visitMethod2.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
                    visitMethod2.visitVarInsn(21, 2);
                    visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
                    visitMethod2.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
                    visitMethod2.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
                    visitMethod2.visitInsn(Opcodes.ATHROW);
                    visitMethod2.visitMaxs(0, 0);
                    visitMethod2.visitEnd();
                    classWriter.visitEnd();
                    sb2 = str;
                    cls2 = accessClassLoader.defineClass(sb2, classWriter.toByteArray());
                }
            }
        }
        try {
            MethodAccess methodAccess = (MethodAccess) cls2.newInstance();
            methodAccess.methodNames = strArr;
            methodAccess.parameterTypes = clsArr;
            methodAccess.returnTypes = clsArr2;
            return methodAccess;
        } catch (Throwable th) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("Error constructing method access class: ");
            sb5.append(sb2);
            throw new RuntimeException(sb5.toString(), th);
        }
    }

    private static void addDeclaredMethodsToList(Class cls, ArrayList<Method> arrayList) {
        Method[] declaredMethods;
        for (Method method : cls.getDeclaredMethods()) {
            if (!Modifier.isPrivate(method.getModifiers())) {
                arrayList.add(method);
            }
        }
    }

    private static void recursiveAddInterfaceMethodsToList(Class cls, ArrayList<Method> arrayList) {
        addDeclaredMethodsToList(cls, arrayList);
        for (Class recursiveAddInterfaceMethodsToList : cls.getInterfaces()) {
            recursiveAddInterfaceMethodsToList(recursiveAddInterfaceMethodsToList, arrayList);
        }
    }
}
