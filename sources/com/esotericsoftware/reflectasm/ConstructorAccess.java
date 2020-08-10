package com.esotericsoftware.reflectasm;

import com.fasterxml.jackson.core.JsonPointer;
import java.lang.reflect.Modifier;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public abstract class ConstructorAccess<T> {
    boolean isNonStaticMemberClass;

    public abstract T newInstance();

    public abstract T newInstance(Object obj);

    public boolean isNonStaticMemberClass() {
        return this.isNonStaticMemberClass;
    }

    public static <T> ConstructorAccess<T> get(Class<T> cls) {
        Class cls2;
        int modifiers;
        Class<T> cls3 = cls;
        Class enclosingClass = cls.getEnclosingClass();
        boolean z = enclosingClass != null && cls.isMemberClass() && !Modifier.isStatic(cls.getModifiers());
        String name = cls.getName();
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("ConstructorAccess");
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
                    cls2 = accessClassLoader.loadClass(sb2);
                } catch (Exception e) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Non-static member class cannot be created (missing enclosing class constructor): ");
                    sb4.append(cls.getName());
                    throw new RuntimeException(sb4.toString(), e);
                } catch (Exception e2) {
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("Class cannot be created (missing no-arg constructor): ");
                    sb5.append(cls.getName());
                    throw new RuntimeException(sb5.toString(), e2);
                } catch (ClassNotFoundException unused2) {
                    String replace = sb2.replace('.', JsonPointer.SEPARATOR);
                    String replace2 = name.replace('.', JsonPointer.SEPARATOR);
                    String str = null;
                    if (!z) {
                        modifiers = cls3.getDeclaredConstructor(null).getModifiers();
                        if (Modifier.isPrivate(modifiers)) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append("Class cannot be created (the no-arg constructor is private): ");
                            sb6.append(cls.getName());
                            throw new RuntimeException(sb6.toString());
                        }
                    } else {
                        str = enclosingClass.getName().replace('.', JsonPointer.SEPARATOR);
                        modifiers = cls3.getDeclaredConstructor(new Class[]{enclosingClass}).getModifiers();
                        if (Modifier.isPrivate(modifiers)) {
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append("Non-static member class cannot be created (the enclosing class constructor is private): ");
                            sb7.append(cls.getName());
                            throw new RuntimeException(sb7.toString());
                        }
                    }
                    String str2 = str;
                    String str3 = Modifier.isPublic(modifiers) ? "com/esotericsoftware/reflectasm/PublicConstructorAccess" : "com/esotericsoftware/reflectasm/ConstructorAccess";
                    ClassWriter classWriter = new ClassWriter(0);
                    classWriter.visit(Opcodes.V1_1, 33, replace, null, str3, null);
                    insertConstructor(classWriter, str3);
                    insertNewInstance(classWriter, replace2);
                    insertNewInstanceInner(classWriter, replace2, str2);
                    classWriter.visitEnd();
                    cls2 = accessClassLoader.defineClass(sb2, classWriter.toByteArray());
                }
            }
        }
        try {
            ConstructorAccess<T> constructorAccess = (ConstructorAccess) cls2.newInstance();
            if ((constructorAccess instanceof PublicConstructorAccess) || AccessClassLoader.areInSameRuntimeClassLoader(cls3, cls2)) {
                constructorAccess.isNonStaticMemberClass = z;
                return constructorAccess;
            }
            StringBuilder sb8 = new StringBuilder();
            sb8.append(!z ? "Class cannot be created (the no-arg constructor is protected or package-protected, and its ConstructorAccess could not be defined in the same class loader): " : "Non-static member class cannot be created (the enclosing class constructor is protected or package-protected, and its ConstructorAccess could not be defined in the same class loader): ");
            sb8.append(cls.getName());
            throw new RuntimeException(sb8.toString());
        } catch (Throwable th) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("Exception constructing constructor access class: ");
            sb9.append(sb2);
            throw new RuntimeException(sb9.toString(), th);
        }
    }

    private static void insertConstructor(ClassWriter classWriter, String str) {
        MethodVisitor visitMethod = classWriter.visitMethod(1, "<init>", "()V", null, null);
        visitMethod.visitCode();
        visitMethod.visitVarInsn(25, 0);
        visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, str, "<init>", "()V");
        visitMethod.visitInsn(Opcodes.RETURN);
        visitMethod.visitMaxs(1, 1);
        visitMethod.visitEnd();
    }

    static void insertNewInstance(ClassWriter classWriter, String str) {
        MethodVisitor visitMethod = classWriter.visitMethod(1, "newInstance", "()Ljava/lang/Object;", null, null);
        visitMethod.visitCode();
        visitMethod.visitTypeInsn(Opcodes.NEW, str);
        visitMethod.visitInsn(89);
        visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, str, "<init>", "()V");
        visitMethod.visitInsn(Opcodes.ARETURN);
        visitMethod.visitMaxs(2, 1);
        visitMethod.visitEnd();
    }

    static void insertNewInstanceInner(ClassWriter classWriter, String str, String str2) {
        MethodVisitor visitMethod = classWriter.visitMethod(1, "newInstance", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        visitMethod.visitCode();
        String str3 = "<init>";
        if (str2 != null) {
            visitMethod.visitTypeInsn(Opcodes.NEW, str);
            visitMethod.visitInsn(89);
            visitMethod.visitVarInsn(25, 1);
            visitMethod.visitTypeInsn(192, str2);
            visitMethod.visitInsn(89);
            visitMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class;");
            visitMethod.visitInsn(87);
            StringBuilder sb = new StringBuilder();
            sb.append("(L");
            sb.append(str2);
            sb.append(";)V");
            visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, str, str3, sb.toString());
            visitMethod.visitInsn(Opcodes.ARETURN);
            visitMethod.visitMaxs(4, 2);
        } else {
            String str4 = "java/lang/UnsupportedOperationException";
            visitMethod.visitTypeInsn(Opcodes.NEW, str4);
            visitMethod.visitInsn(89);
            visitMethod.visitLdcInsn("Not an inner class.");
            visitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, str4, str3, "(Ljava/lang/String;)V");
            visitMethod.visitInsn(Opcodes.ATHROW);
            visitMethod.visitMaxs(3, 2);
        }
        visitMethod.visitEnd();
    }
}
