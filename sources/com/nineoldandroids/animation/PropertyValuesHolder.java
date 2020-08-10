package com.nineoldandroids.animation;

import android.util.Log;
import com.nineoldandroids.util.FloatProperty;
import com.nineoldandroids.util.IntProperty;
import com.nineoldandroids.util.Property;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PropertyValuesHolder implements Cloneable {
    private static Class[] DOUBLE_VARIANTS = {Double.TYPE, Double.class, Float.TYPE, Integer.TYPE, Float.class, Integer.class};
    private static Class[] FLOAT_VARIANTS = {Float.TYPE, Float.class, Double.TYPE, Integer.TYPE, Double.class, Integer.class};
    private static Class[] INTEGER_VARIANTS = {Integer.TYPE, Integer.class, Float.TYPE, Double.TYPE, Float.class, Double.class};
    private static final TypeEvaluator sFloatEvaluator = new FloatEvaluator();
    private static final HashMap<Class, HashMap<String, Method>> sGetterPropertyMap = new HashMap<>();
    private static final TypeEvaluator sIntEvaluator = new IntEvaluator();
    private static final HashMap<Class, HashMap<String, Method>> sSetterPropertyMap = new HashMap<>();
    private Object mAnimatedValue;
    private TypeEvaluator mEvaluator;
    private Method mGetter;
    KeyframeSet mKeyframeSet;
    protected Property mProperty;
    final ReentrantReadWriteLock mPropertyMapLock;
    String mPropertyName;
    Method mSetter;
    final Object[] mTmpValueArray;
    Class mValueType;

    static class FloatPropertyValuesHolder extends PropertyValuesHolder {
        float mFloatAnimatedValue;
        FloatKeyframeSet mFloatKeyframeSet;
        private FloatProperty mFloatProperty;

        public FloatPropertyValuesHolder(String str, FloatKeyframeSet floatKeyframeSet) {
            super(str);
            this.mValueType = Float.TYPE;
            this.mKeyframeSet = floatKeyframeSet;
            this.mFloatKeyframeSet = (FloatKeyframeSet) this.mKeyframeSet;
        }

        public FloatPropertyValuesHolder(Property property, FloatKeyframeSet floatKeyframeSet) {
            super(property);
            this.mValueType = Float.TYPE;
            this.mKeyframeSet = floatKeyframeSet;
            this.mFloatKeyframeSet = (FloatKeyframeSet) this.mKeyframeSet;
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) this.mProperty;
            }
        }

        public FloatPropertyValuesHolder(String str, float... fArr) {
            super(str);
            setFloatValues(fArr);
        }

        public FloatPropertyValuesHolder(Property property, float... fArr) {
            super(property);
            setFloatValues(fArr);
            if (property instanceof FloatProperty) {
                this.mFloatProperty = (FloatProperty) this.mProperty;
            }
        }

        public void setFloatValues(float... fArr) {
            PropertyValuesHolder.super.setFloatValues(fArr);
            this.mFloatKeyframeSet = (FloatKeyframeSet) this.mKeyframeSet;
        }

        /* access modifiers changed from: 0000 */
        public void calculateValue(float f) {
            this.mFloatAnimatedValue = this.mFloatKeyframeSet.getFloatValue(f);
        }

        /* access modifiers changed from: 0000 */
        public Object getAnimatedValue() {
            return Float.valueOf(this.mFloatAnimatedValue);
        }

        public FloatPropertyValuesHolder clone() {
            FloatPropertyValuesHolder floatPropertyValuesHolder = (FloatPropertyValuesHolder) PropertyValuesHolder.super.clone();
            floatPropertyValuesHolder.mFloatKeyframeSet = (FloatKeyframeSet) floatPropertyValuesHolder.mKeyframeSet;
            return floatPropertyValuesHolder;
        }

        /* access modifiers changed from: 0000 */
        public void setAnimatedValue(Object obj) {
            String str = "PropertyValuesHolder";
            FloatProperty floatProperty = this.mFloatProperty;
            if (floatProperty != null) {
                floatProperty.setValue(obj, this.mFloatAnimatedValue);
            } else if (this.mProperty != null) {
                this.mProperty.set(obj, Float.valueOf(this.mFloatAnimatedValue));
            } else {
                if (this.mSetter != null) {
                    try {
                        this.mTmpValueArray[0] = Float.valueOf(this.mFloatAnimatedValue);
                        this.mSetter.invoke(obj, this.mTmpValueArray);
                    } catch (InvocationTargetException e) {
                        Log.e(str, e.toString());
                    } catch (IllegalAccessException e2) {
                        Log.e(str, e2.toString());
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void setupSetter(Class cls) {
            if (this.mProperty == null) {
                PropertyValuesHolder.super.setupSetter(cls);
            }
        }
    }

    static class IntPropertyValuesHolder extends PropertyValuesHolder {
        int mIntAnimatedValue;
        IntKeyframeSet mIntKeyframeSet;
        private IntProperty mIntProperty;

        public IntPropertyValuesHolder(String str, IntKeyframeSet intKeyframeSet) {
            super(str);
            this.mValueType = Integer.TYPE;
            this.mKeyframeSet = intKeyframeSet;
            this.mIntKeyframeSet = (IntKeyframeSet) this.mKeyframeSet;
        }

        public IntPropertyValuesHolder(Property property, IntKeyframeSet intKeyframeSet) {
            super(property);
            this.mValueType = Integer.TYPE;
            this.mKeyframeSet = intKeyframeSet;
            this.mIntKeyframeSet = (IntKeyframeSet) this.mKeyframeSet;
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) this.mProperty;
            }
        }

        public IntPropertyValuesHolder(String str, int... iArr) {
            super(str);
            setIntValues(iArr);
        }

        public IntPropertyValuesHolder(Property property, int... iArr) {
            super(property);
            setIntValues(iArr);
            if (property instanceof IntProperty) {
                this.mIntProperty = (IntProperty) this.mProperty;
            }
        }

        public void setIntValues(int... iArr) {
            PropertyValuesHolder.super.setIntValues(iArr);
            this.mIntKeyframeSet = (IntKeyframeSet) this.mKeyframeSet;
        }

        /* access modifiers changed from: 0000 */
        public void calculateValue(float f) {
            this.mIntAnimatedValue = this.mIntKeyframeSet.getIntValue(f);
        }

        /* access modifiers changed from: 0000 */
        public Object getAnimatedValue() {
            return Integer.valueOf(this.mIntAnimatedValue);
        }

        public IntPropertyValuesHolder clone() {
            IntPropertyValuesHolder intPropertyValuesHolder = (IntPropertyValuesHolder) PropertyValuesHolder.super.clone();
            intPropertyValuesHolder.mIntKeyframeSet = (IntKeyframeSet) intPropertyValuesHolder.mKeyframeSet;
            return intPropertyValuesHolder;
        }

        /* access modifiers changed from: 0000 */
        public void setAnimatedValue(Object obj) {
            String str = "PropertyValuesHolder";
            IntProperty intProperty = this.mIntProperty;
            if (intProperty != null) {
                intProperty.setValue(obj, this.mIntAnimatedValue);
            } else if (this.mProperty != null) {
                this.mProperty.set(obj, Integer.valueOf(this.mIntAnimatedValue));
            } else {
                if (this.mSetter != null) {
                    try {
                        this.mTmpValueArray[0] = Integer.valueOf(this.mIntAnimatedValue);
                        this.mSetter.invoke(obj, this.mTmpValueArray);
                    } catch (InvocationTargetException e) {
                        Log.e(str, e.toString());
                    } catch (IllegalAccessException e2) {
                        Log.e(str, e2.toString());
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void setupSetter(Class cls) {
            if (this.mProperty == null) {
                PropertyValuesHolder.super.setupSetter(cls);
            }
        }
    }

    private PropertyValuesHolder(String str) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframeSet = null;
        this.mPropertyMapLock = new ReentrantReadWriteLock();
        this.mTmpValueArray = new Object[1];
        this.mPropertyName = str;
    }

    private PropertyValuesHolder(Property property) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframeSet = null;
        this.mPropertyMapLock = new ReentrantReadWriteLock();
        this.mTmpValueArray = new Object[1];
        this.mProperty = property;
        if (property != null) {
            this.mPropertyName = property.getName();
        }
    }

    public static PropertyValuesHolder ofInt(String str, int... iArr) {
        return new IntPropertyValuesHolder(str, iArr);
    }

    public static PropertyValuesHolder ofInt(Property<?, Integer> property, int... iArr) {
        return new IntPropertyValuesHolder((Property) property, iArr);
    }

    public static PropertyValuesHolder ofFloat(String str, float... fArr) {
        return new FloatPropertyValuesHolder(str, fArr);
    }

    public static PropertyValuesHolder ofFloat(Property<?, Float> property, float... fArr) {
        return new FloatPropertyValuesHolder((Property) property, fArr);
    }

    public static PropertyValuesHolder ofObject(String str, TypeEvaluator typeEvaluator, Object... objArr) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(str);
        propertyValuesHolder.setObjectValues(objArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    public static <V> PropertyValuesHolder ofObject(Property property, TypeEvaluator<V> typeEvaluator, V... vArr) {
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(property);
        propertyValuesHolder.setObjectValues(vArr);
        propertyValuesHolder.setEvaluator(typeEvaluator);
        return propertyValuesHolder;
    }

    public static PropertyValuesHolder ofKeyframe(String str, Keyframe... keyframeArr) {
        KeyframeSet ofKeyframe = KeyframeSet.ofKeyframe(keyframeArr);
        if (ofKeyframe instanceof IntKeyframeSet) {
            return new IntPropertyValuesHolder(str, (IntKeyframeSet) ofKeyframe);
        }
        if (ofKeyframe instanceof FloatKeyframeSet) {
            return new FloatPropertyValuesHolder(str, (FloatKeyframeSet) ofKeyframe);
        }
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(str);
        propertyValuesHolder.mKeyframeSet = ofKeyframe;
        propertyValuesHolder.mValueType = keyframeArr[0].getType();
        return propertyValuesHolder;
    }

    public static PropertyValuesHolder ofKeyframe(Property property, Keyframe... keyframeArr) {
        KeyframeSet ofKeyframe = KeyframeSet.ofKeyframe(keyframeArr);
        if (ofKeyframe instanceof IntKeyframeSet) {
            return new IntPropertyValuesHolder(property, (IntKeyframeSet) ofKeyframe);
        }
        if (ofKeyframe instanceof FloatKeyframeSet) {
            return new FloatPropertyValuesHolder(property, (FloatKeyframeSet) ofKeyframe);
        }
        PropertyValuesHolder propertyValuesHolder = new PropertyValuesHolder(property);
        propertyValuesHolder.mKeyframeSet = ofKeyframe;
        propertyValuesHolder.mValueType = keyframeArr[0].getType();
        return propertyValuesHolder;
    }

    public void setIntValues(int... iArr) {
        this.mValueType = Integer.TYPE;
        this.mKeyframeSet = KeyframeSet.ofInt(iArr);
    }

    public void setFloatValues(float... fArr) {
        this.mValueType = Float.TYPE;
        this.mKeyframeSet = KeyframeSet.ofFloat(fArr);
    }

    public void setKeyframes(Keyframe... keyframeArr) {
        int length = keyframeArr.length;
        Keyframe[] keyframeArr2 = new Keyframe[Math.max(length, 2)];
        this.mValueType = keyframeArr[0].getType();
        for (int i = 0; i < length; i++) {
            keyframeArr2[i] = keyframeArr[i];
        }
        this.mKeyframeSet = new KeyframeSet(keyframeArr2);
    }

    public void setObjectValues(Object... objArr) {
        this.mValueType = objArr[0].getClass();
        this.mKeyframeSet = KeyframeSet.ofObject(objArr);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007b, code lost:
        return r6;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:26:0x007c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.reflect.Method getPropertyFunction(java.lang.Class r9, java.lang.String r10, java.lang.Class r11) {
        /*
            r8 = this;
            java.lang.String r0 = r8.mPropertyName
            java.lang.String r10 = getMethodName(r10, r0)
            java.lang.String r0 = "PropertyValuesHolder"
            r1 = 0
            r2 = 1
            if (r11 != 0) goto L_0x003c
            java.lang.reflect.Method r9 = r9.getMethod(r10, r1)     // Catch:{ NoSuchMethodException -> 0x0012 }
            goto L_0x00aa
        L_0x0012:
            r11 = move-exception
            java.lang.reflect.Method r1 = r9.getDeclaredMethod(r10, r1)     // Catch:{ NoSuchMethodException -> 0x001b }
            r1.setAccessible(r2)     // Catch:{ NoSuchMethodException -> 0x001b }
            goto L_0x0039
        L_0x001b:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Couldn't find no-arg method for property "
            r9.append(r10)
            java.lang.String r10 = r8.mPropertyName
            r9.append(r10)
            java.lang.String r10 = ": "
            r9.append(r10)
            r9.append(r11)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r0, r9)
        L_0x0039:
            r9 = r1
            goto L_0x00aa
        L_0x003c:
            java.lang.Class[] r11 = new java.lang.Class[r2]
            java.lang.Class r3 = r8.mValueType
            java.lang.Class<java.lang.Float> r4 = java.lang.Float.class
            boolean r3 = r3.equals(r4)
            r4 = 0
            if (r3 == 0) goto L_0x004c
            java.lang.Class[] r3 = FLOAT_VARIANTS
            goto L_0x006c
        L_0x004c:
            java.lang.Class r3 = r8.mValueType
            java.lang.Class<java.lang.Integer> r5 = java.lang.Integer.class
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0059
            java.lang.Class[] r3 = INTEGER_VARIANTS
            goto L_0x006c
        L_0x0059:
            java.lang.Class r3 = r8.mValueType
            java.lang.Class<java.lang.Double> r5 = java.lang.Double.class
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0066
            java.lang.Class[] r3 = DOUBLE_VARIANTS
            goto L_0x006c
        L_0x0066:
            java.lang.Class[] r3 = new java.lang.Class[r2]
            java.lang.Class r5 = r8.mValueType
            r3[r4] = r5
        L_0x006c:
            int r5 = r3.length
            r6 = r1
            r1 = 0
        L_0x006f:
            if (r1 >= r5) goto L_0x0089
            r7 = r3[r1]
            r11[r4] = r7
            java.lang.reflect.Method r6 = r9.getMethod(r10, r11)     // Catch:{ NoSuchMethodException -> 0x007c }
            r8.mValueType = r7     // Catch:{ NoSuchMethodException -> 0x007c }
            return r6
        L_0x007c:
            java.lang.reflect.Method r6 = r9.getDeclaredMethod(r10, r11)     // Catch:{ NoSuchMethodException -> 0x0086 }
            r6.setAccessible(r2)     // Catch:{ NoSuchMethodException -> 0x0086 }
            r8.mValueType = r7     // Catch:{ NoSuchMethodException -> 0x0086 }
            return r6
        L_0x0086:
            int r1 = r1 + 1
            goto L_0x006f
        L_0x0089:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "Couldn't find setter/getter for property "
            r9.append(r10)
            java.lang.String r10 = r8.mPropertyName
            r9.append(r10)
            java.lang.String r10 = " with value type "
            r9.append(r10)
            java.lang.Class r10 = r8.mValueType
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r0, r9)
            r9 = r6
        L_0x00aa:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nineoldandroids.animation.PropertyValuesHolder.getPropertyFunction(java.lang.Class, java.lang.String, java.lang.Class):java.lang.reflect.Method");
    }

    private Method setupSetterOrGetter(Class cls, HashMap<Class, HashMap<String, Method>> hashMap, String str, Class cls2) {
        try {
            this.mPropertyMapLock.writeLock().lock();
            HashMap hashMap2 = (HashMap) hashMap.get(cls);
            Method method = hashMap2 != null ? (Method) hashMap2.get(this.mPropertyName) : null;
            if (method == null) {
                method = getPropertyFunction(cls, str, cls2);
                if (hashMap2 == null) {
                    hashMap2 = new HashMap();
                    hashMap.put(cls, hashMap2);
                }
                hashMap2.put(this.mPropertyName, method);
            }
            return method;
        } finally {
            this.mPropertyMapLock.writeLock().unlock();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setupSetter(Class cls) {
        this.mSetter = setupSetterOrGetter(cls, sSetterPropertyMap, "set", this.mValueType);
    }

    private void setupGetter(Class cls) {
        this.mGetter = setupSetterOrGetter(cls, sGetterPropertyMap, "get", null);
    }

    /* access modifiers changed from: 0000 */
    public void setupSetterAndGetter(Object obj) {
        Property property = this.mProperty;
        String str = "PropertyValuesHolder";
        if (property != null) {
            try {
                property.get(obj);
                Iterator it = this.mKeyframeSet.mKeyframes.iterator();
                while (it.hasNext()) {
                    Keyframe keyframe = (Keyframe) it.next();
                    if (!keyframe.hasValue()) {
                        keyframe.setValue(this.mProperty.get(obj));
                    }
                }
                return;
            } catch (ClassCastException unused) {
                StringBuilder sb = new StringBuilder();
                sb.append("No such property (");
                sb.append(this.mProperty.getName());
                sb.append(") on target object ");
                sb.append(obj);
                sb.append(". Trying reflection instead");
                Log.e(str, sb.toString());
                this.mProperty = null;
            }
        }
        Class cls = obj.getClass();
        if (this.mSetter == null) {
            setupSetter(cls);
        }
        Iterator it2 = this.mKeyframeSet.mKeyframes.iterator();
        while (it2.hasNext()) {
            Keyframe keyframe2 = (Keyframe) it2.next();
            if (!keyframe2.hasValue()) {
                if (this.mGetter == null) {
                    setupGetter(cls);
                }
                try {
                    keyframe2.setValue(this.mGetter.invoke(obj, new Object[0]));
                } catch (InvocationTargetException e) {
                    Log.e(str, e.toString());
                } catch (IllegalAccessException e2) {
                    Log.e(str, e2.toString());
                }
            }
        }
    }

    private void setupValue(Object obj, Keyframe keyframe) {
        String str = "PropertyValuesHolder";
        Property property = this.mProperty;
        if (property != null) {
            keyframe.setValue(property.get(obj));
        }
        try {
            if (this.mGetter == null) {
                setupGetter(obj.getClass());
            }
            keyframe.setValue(this.mGetter.invoke(obj, new Object[0]));
        } catch (InvocationTargetException e) {
            Log.e(str, e.toString());
        } catch (IllegalAccessException e2) {
            Log.e(str, e2.toString());
        }
    }

    /* access modifiers changed from: 0000 */
    public void setupStartValue(Object obj) {
        setupValue(obj, (Keyframe) this.mKeyframeSet.mKeyframes.get(0));
    }

    /* access modifiers changed from: 0000 */
    public void setupEndValue(Object obj) {
        setupValue(obj, (Keyframe) this.mKeyframeSet.mKeyframes.get(this.mKeyframeSet.mKeyframes.size() - 1));
    }

    public PropertyValuesHolder clone() {
        try {
            PropertyValuesHolder propertyValuesHolder = (PropertyValuesHolder) super.clone();
            propertyValuesHolder.mPropertyName = this.mPropertyName;
            propertyValuesHolder.mProperty = this.mProperty;
            propertyValuesHolder.mKeyframeSet = this.mKeyframeSet.clone();
            propertyValuesHolder.mEvaluator = this.mEvaluator;
            return propertyValuesHolder;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void setAnimatedValue(Object obj) {
        String str = "PropertyValuesHolder";
        Property property = this.mProperty;
        if (property != null) {
            property.set(obj, getAnimatedValue());
        }
        if (this.mSetter != null) {
            try {
                this.mTmpValueArray[0] = getAnimatedValue();
                this.mSetter.invoke(obj, this.mTmpValueArray);
            } catch (InvocationTargetException e) {
                Log.e(str, e.toString());
            } catch (IllegalAccessException e2) {
                Log.e(str, e2.toString());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void init() {
        if (this.mEvaluator == null) {
            Class<Float> cls = this.mValueType;
            TypeEvaluator typeEvaluator = cls == Integer.class ? sIntEvaluator : cls == Float.class ? sFloatEvaluator : null;
            this.mEvaluator = typeEvaluator;
        }
        TypeEvaluator typeEvaluator2 = this.mEvaluator;
        if (typeEvaluator2 != null) {
            this.mKeyframeSet.setEvaluator(typeEvaluator2);
        }
    }

    public void setEvaluator(TypeEvaluator typeEvaluator) {
        this.mEvaluator = typeEvaluator;
        this.mKeyframeSet.setEvaluator(typeEvaluator);
    }

    /* access modifiers changed from: 0000 */
    public void calculateValue(float f) {
        this.mAnimatedValue = this.mKeyframeSet.getValue(f);
    }

    public void setPropertyName(String str) {
        this.mPropertyName = str;
    }

    public void setProperty(Property property) {
        this.mProperty = property;
    }

    public String getPropertyName() {
        return this.mPropertyName;
    }

    /* access modifiers changed from: 0000 */
    public Object getAnimatedValue() {
        return this.mAnimatedValue;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mPropertyName);
        sb.append(": ");
        sb.append(this.mKeyframeSet.toString());
        return sb.toString();
    }

    static String getMethodName(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        char upperCase = Character.toUpperCase(str2.charAt(0));
        String substring = str2.substring(1);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(upperCase);
        sb.append(substring);
        return sb.toString();
    }
}
