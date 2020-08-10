package com.esotericsoftware.kryo.serializers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.FieldSerializer.CachedField;
import com.esotericsoftware.minlog.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.HashMap;

final class FieldSerializerGenericsUtil {
    private Kryo kryo;
    private FieldSerializer serializer;

    public FieldSerializerGenericsUtil(FieldSerializer fieldSerializer) {
        this.serializer = fieldSerializer;
        this.kryo = fieldSerializer.getKryo();
    }

    /* access modifiers changed from: 0000 */
    public Generics buildGenericsScope(Class cls, Class[] clsArr) {
        int i;
        Type genericSuperclass;
        Class cls2 = cls;
        TypeVariable[] typeVariableArr = null;
        while (true) {
            if (cls2 != null) {
                if (cls2 == this.serializer.type) {
                    typeVariableArr = this.serializer.typeParameters;
                } else {
                    typeVariableArr = cls2.getTypeParameters();
                }
                if (typeVariableArr != null && typeVariableArr.length != 0) {
                    break;
                } else if (cls2 == this.serializer.type) {
                    cls2 = this.serializer.componentType;
                    if (cls2 == null) {
                        Class cls3 = this.serializer.type;
                        do {
                            genericSuperclass = cls3.getGenericSuperclass();
                            cls3 = cls3.getSuperclass();
                            if (genericSuperclass == null) {
                                break;
                            }
                        } while (!(genericSuperclass instanceof ParameterizedType));
                        if (genericSuperclass != null) {
                            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                            typeVariableArr = cls3.getTypeParameters();
                            Class[] clsArr2 = new Class[actualTypeArguments.length];
                            for (int i2 = 0; i2 < actualTypeArguments.length; i2++) {
                                clsArr2[i2] = actualTypeArguments[i2] instanceof Class ? (Class) actualTypeArguments[i2] : Object.class;
                            }
                            clsArr = clsArr2;
                        }
                    }
                } else {
                    cls2 = cls2.getComponentType();
                }
            } else {
                break;
            }
        }
        if (typeVariableArr == null || typeVariableArr.length <= 0) {
            return null;
        }
        String str = "kryo";
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Class ");
            sb.append(cls.getName());
            sb.append(" has generic type parameters");
            Log.trace(str, sb.toString());
        }
        HashMap hashMap = new HashMap();
        int i3 = 0;
        for (TypeVariable typeVariable : typeVariableArr) {
            String name = typeVariable.getName();
            if (Log.TRACE) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Type parameter variable: name=");
                sb2.append(name);
                sb2.append(" type bounds=");
                sb2.append(Arrays.toString(typeVariable.getBounds()));
                Log.trace(str, sb2.toString());
            }
            Class typeVarConcreteClass = getTypeVarConcreteClass(clsArr, i3, name);
            if (typeVarConcreteClass != null) {
                hashMap.put(name, typeVarConcreteClass);
                if (Log.TRACE) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("Concrete type used for ");
                    sb3.append(name);
                    sb3.append(" is: ");
                    sb3.append(typeVarConcreteClass.getName());
                    Log.trace(str, sb3.toString());
                }
            }
            i3++;
        }
        return new Generics(hashMap);
    }

    private Class<?> getTypeVarConcreteClass(Class[] clsArr, int i, String str) {
        if (clsArr != null && clsArr.length > i) {
            return clsArr[i];
        }
        if (Log.TRACE) {
            Log.trace("kryo", "Trying to use kryo.getGenericScope");
        }
        GenericsResolver genericsResolver = this.kryo.getGenericsResolver();
        if (genericsResolver.isSet()) {
            return genericsResolver.getConcreteClass(str);
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public Class[] computeFieldGenerics(Type type, Field field, Class[] clsArr) {
        if (type == null) {
            return null;
        }
        String str = "Determined concrete class of '";
        String str2 = "' to be ";
        String str3 = "kryo";
        if (!(type instanceof TypeVariable) || this.serializer.getGenericsScope() == null) {
            String str4 = " where type parameters are ";
            if (type instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                if (actualTypeArguments == null) {
                    return null;
                }
                Class[] clsArr2 = new Class[actualTypeArguments.length];
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    Type type2 = actualTypeArguments[i];
                    if (type2 instanceof Class) {
                        clsArr2[i] = (Class) type2;
                    } else if (type2 instanceof ParameterizedType) {
                        clsArr2[i] = (Class) ((ParameterizedType) type2).getRawType();
                    } else if ((type2 instanceof TypeVariable) && this.serializer.getGenericsScope() != null) {
                        clsArr2[i] = this.serializer.getGenericsScope().getConcreteClass(((TypeVariable) type2).getName());
                        if (clsArr2[i] == null) {
                            clsArr2[i] = Object.class;
                        }
                    } else if (type2 instanceof WildcardType) {
                        clsArr2[i] = Object.class;
                    } else if (type2 instanceof GenericArrayType) {
                        Type genericComponentType = ((GenericArrayType) type2).getGenericComponentType();
                        if (genericComponentType instanceof Class) {
                            clsArr2[i] = Array.newInstance((Class) genericComponentType, 0).getClass();
                        } else if (genericComponentType instanceof TypeVariable) {
                            Generics genericsScope = this.serializer.getGenericsScope();
                            if (genericsScope != null) {
                                Class concreteClass = genericsScope.getConcreteClass(((TypeVariable) genericComponentType).getName());
                                if (concreteClass != null) {
                                    clsArr2[i] = Array.newInstance(concreteClass, 0).getClass();
                                }
                            }
                        }
                    } else {
                        clsArr2[i] = null;
                    }
                }
                if (Log.TRACE) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Determined concrete class of parametrized '");
                    sb.append(field.getName());
                    sb.append(str2);
                    sb.append(type);
                    sb.append(str4);
                    sb.append(Arrays.toString(clsArr2));
                    Log.trace(str3, sb.toString());
                }
                return clsArr2;
            } else if (!(type instanceof GenericArrayType)) {
                return null;
            } else {
                Class[] computeFieldGenerics = computeFieldGenerics(((GenericArrayType) type).getGenericComponentType(), field, new Class[]{clsArr[0]});
                if (Log.TRACE && computeFieldGenerics != null) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Determined concrete class of a generic array '");
                    sb2.append(field.getName());
                    sb2.append(str2);
                    sb2.append(type);
                    sb2.append(str4);
                    sb2.append(Arrays.toString(computeFieldGenerics));
                    Log.trace(str3, sb2.toString());
                    return computeFieldGenerics;
                } else if (!Log.TRACE) {
                    return computeFieldGenerics;
                } else {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(str);
                    sb3.append(field.getName());
                    sb3.append(str2);
                    sb3.append(type);
                    Log.trace(str3, sb3.toString());
                    return computeFieldGenerics;
                }
            }
        } else {
            Class concreteClass2 = this.serializer.getGenericsScope().getConcreteClass(((TypeVariable) type).getName());
            if (concreteClass2 == null) {
                return null;
            }
            clsArr[0] = concreteClass2;
            Class[] clsArr3 = {clsArr[0]};
            if (!Log.TRACE) {
                return clsArr3;
            }
            StringBuilder sb4 = new StringBuilder();
            sb4.append(str);
            sb4.append(field.getName());
            sb4.append(str2);
            sb4.append(clsArr[0].getName());
            Log.trace(str3, sb4.toString());
            return clsArr3;
        }
    }

    /* access modifiers changed from: 0000 */
    public CachedField newCachedFieldOfGenericType(Field field, int i, Class[] clsArr, Type type) {
        String str = "kryo";
        if (Log.TRACE) {
            StringBuilder sb = new StringBuilder();
            sb.append("Field '");
            sb.append(field.getName());
            sb.append("' of type ");
            sb.append(clsArr[0]);
            sb.append(" of generic type ");
            sb.append(type);
            Log.trace(str, sb.toString());
        }
        if (Log.TRACE && type != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Field generic type is of class ");
            sb2.append(type.getClass().getName());
            Log.trace(str, sb2.toString());
        }
        Generics buildGenericsScope = buildGenericsScope(clsArr[0], getGenerics(type, this.kryo));
        if (clsArr[0] == Object.class && (type instanceof TypeVariable) && this.serializer.getGenericsScope() != null) {
            TypeVariable typeVariable = (TypeVariable) type;
            Class concreteClass = this.serializer.getGenericsScope().getConcreteClass(typeVariable.getName());
            if (concreteClass != null) {
                buildGenericsScope = new Generics();
                buildGenericsScope.add(typeVariable.getName(), concreteClass);
            }
        }
        if (Log.TRACE) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Generics scope of field '");
            sb3.append(field.getName());
            sb3.append("' of class ");
            sb3.append(type);
            sb3.append(" is ");
            sb3.append(buildGenericsScope);
            Log.trace(str, sb3.toString());
        }
        Class[] computeFieldGenerics = computeFieldGenerics(type, field, clsArr);
        CachedField newMatchingCachedField = this.serializer.newMatchingCachedField(field, i, clsArr[0], type, computeFieldGenerics);
        if (computeFieldGenerics != null && (newMatchingCachedField instanceof ObjectField) && computeFieldGenerics.length > 0 && computeFieldGenerics[0] != null) {
            ((ObjectField) newMatchingCachedField).generics = computeFieldGenerics;
            if (Log.TRACE) {
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Field generics: ");
                sb4.append(Arrays.toString(computeFieldGenerics));
                Log.trace(str, sb4.toString());
            }
        }
        return newMatchingCachedField;
    }

    public static Class[] getGenerics(Type type, Kryo kryo2) {
        if (type instanceof GenericArrayType) {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            if (!(genericComponentType instanceof Class)) {
                return getGenerics(genericComponentType, kryo2);
            }
            return new Class[]{(Class) genericComponentType};
        } else if (!(type instanceof ParameterizedType)) {
            return null;
        } else {
            String str = "kryo";
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Processing generic type ");
                sb.append(type);
                Log.trace(str, sb.toString());
            }
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            Class[] clsArr = new Class[actualTypeArguments.length];
            int length = actualTypeArguments.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                Type type2 = actualTypeArguments[i2];
                if (Log.TRACE) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Processing actual type ");
                    sb2.append(type2);
                    sb2.append(" (");
                    sb2.append(type2.getClass().getName());
                    sb2.append(")");
                    Log.trace(str, sb2.toString());
                }
                clsArr[i2] = Object.class;
                if (type2 instanceof Class) {
                    clsArr[i2] = (Class) type2;
                } else if (type2 instanceof ParameterizedType) {
                    clsArr[i2] = (Class) ((ParameterizedType) type2).getRawType();
                } else if (type2 instanceof TypeVariable) {
                    GenericsResolver genericsResolver = kryo2.getGenericsResolver();
                    if (genericsResolver.isSet()) {
                        Class concreteClass = genericsResolver.getConcreteClass(((TypeVariable) type2).getName());
                        if (concreteClass != null) {
                            clsArr[i2] = concreteClass;
                        }
                    }
                } else if (type2 instanceof GenericArrayType) {
                    Type genericComponentType2 = ((GenericArrayType) type2).getGenericComponentType();
                    if (genericComponentType2 instanceof Class) {
                        clsArr[i2] = Array.newInstance((Class) genericComponentType2, 0).getClass();
                    } else if (genericComponentType2 instanceof TypeVariable) {
                        GenericsResolver genericsResolver2 = kryo2.getGenericsResolver();
                        if (genericsResolver2.isSet()) {
                            Class concreteClass2 = genericsResolver2.getConcreteClass(((TypeVariable) genericComponentType2).getName());
                            if (concreteClass2 != null) {
                                clsArr[i2] = Array.newInstance(concreteClass2, 0).getClass();
                            }
                        }
                    } else {
                        Class[] generics = getGenerics(genericComponentType2, kryo2);
                        if (generics != null) {
                            clsArr[i2] = generics[0];
                        }
                    }
                }
                i++;
            }
            if (i == 0) {
                return null;
            }
            return clsArr;
        }
    }
}
