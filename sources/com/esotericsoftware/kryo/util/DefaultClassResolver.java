package com.esotericsoftware.kryo.util;

import com.esotericsoftware.kryo.ClassResolver;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.minlog.Log;

public class DefaultClassResolver implements ClassResolver {
    public static final byte NAME = -1;
    protected IdentityObjectIntMap<Class> classToNameId;
    protected final ObjectMap<Class, Registration> classToRegistration = new ObjectMap<>();
    protected final IntMap<Registration> idToRegistration = new IntMap<>();
    protected Kryo kryo;
    private Class memoizedClass;
    private int memoizedClassId = -1;
    private Registration memoizedClassIdValue;
    private Registration memoizedClassValue;
    protected IntMap<Class> nameIdToClass;
    protected ObjectMap<String, Class> nameToClass;
    protected int nextNameId;

    public void setKryo(Kryo kryo2) {
        this.kryo = kryo2;
    }

    public Registration register(Registration registration) {
        if (registration != null) {
            String str = ")";
            String str2 = " (";
            String str3 = "kryo";
            if (registration.getId() != -1) {
                if (Log.TRACE) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Register class ID ");
                    sb.append(registration.getId());
                    sb.append(": ");
                    sb.append(Util.className(registration.getType()));
                    sb.append(str2);
                    sb.append(registration.getSerializer().getClass().getName());
                    sb.append(str);
                    Log.trace(str3, sb.toString());
                }
                this.idToRegistration.put(registration.getId(), registration);
            } else if (Log.TRACE) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Register class name: ");
                sb2.append(Util.className(registration.getType()));
                sb2.append(str2);
                sb2.append(registration.getSerializer().getClass().getName());
                sb2.append(str);
                Log.trace(str3, sb2.toString());
            }
            this.classToRegistration.put(registration.getType(), registration);
            if (registration.getType().isPrimitive()) {
                this.classToRegistration.put(Util.getWrapperClass(registration.getType()), registration);
            }
            return registration;
        }
        throw new IllegalArgumentException("registration cannot be null.");
    }

    public Registration registerImplicit(Class cls) {
        return register(new Registration(cls, this.kryo.getDefaultSerializer(cls), -1));
    }

    public Registration getRegistration(Class cls) {
        if (cls == this.memoizedClass) {
            return this.memoizedClassValue;
        }
        Registration registration = (Registration) this.classToRegistration.get(cls);
        if (registration != null) {
            this.memoizedClass = cls;
            this.memoizedClassValue = registration;
        }
        return registration;
    }

    public Registration getRegistration(int i) {
        return (Registration) this.idToRegistration.get(i);
    }

    public Registration writeClass(Output output, Class cls) {
        if (cls == null) {
            if (Log.TRACE || (Log.DEBUG && this.kryo.getDepth() == 1)) {
                Util.log("Write", null);
            }
            output.writeVarInt(0, true);
            return null;
        }
        Registration registration = this.kryo.getRegistration(cls);
        if (registration.getId() == -1) {
            writeName(output, cls, registration);
        } else {
            if (Log.TRACE) {
                StringBuilder sb = new StringBuilder();
                sb.append("Write class ");
                sb.append(registration.getId());
                sb.append(": ");
                sb.append(Util.className(cls));
                Log.trace("kryo", sb.toString());
            }
            output.writeVarInt(registration.getId() + 2, true);
        }
        return registration;
    }

    /* access modifiers changed from: protected */
    public void writeName(Output output, Class cls, Registration registration) {
        output.writeVarInt(1, true);
        IdentityObjectIntMap<Class> identityObjectIntMap = this.classToNameId;
        String str = "kryo";
        if (identityObjectIntMap != null) {
            int i = identityObjectIntMap.get(cls, -1);
            if (i != -1) {
                if (Log.TRACE) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Write class name reference ");
                    sb.append(i);
                    sb.append(": ");
                    sb.append(Util.className(cls));
                    Log.trace(str, sb.toString());
                }
                output.writeVarInt(i, true);
                return;
            }
        }
        if (Log.TRACE) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Write class name: ");
            sb2.append(Util.className(cls));
            Log.trace(str, sb2.toString());
        }
        int i2 = this.nextNameId;
        this.nextNameId = i2 + 1;
        if (this.classToNameId == null) {
            this.classToNameId = new IdentityObjectIntMap<>();
        }
        this.classToNameId.put(cls, i2);
        output.writeVarInt(i2, true);
        output.writeString(cls.getName());
    }

    public Registration readClass(Input input) {
        int readVarInt = input.readVarInt(true);
        if (readVarInt == 0) {
            if (Log.TRACE || (Log.DEBUG && this.kryo.getDepth() == 1)) {
                Util.log("Read", null);
            }
            return null;
        } else if (readVarInt == 1) {
            return readName(input);
        } else {
            if (readVarInt == this.memoizedClassId) {
                return this.memoizedClassIdValue;
            }
            int i = readVarInt - 2;
            Registration registration = (Registration) this.idToRegistration.get(i);
            if (registration != null) {
                if (Log.TRACE) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Read class ");
                    sb.append(i);
                    sb.append(": ");
                    sb.append(Util.className(registration.getType()));
                    Log.trace("kryo", sb.toString());
                }
                this.memoizedClassId = readVarInt;
                this.memoizedClassIdValue = registration;
                return registration;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Encountered unregistered class ID: ");
            sb2.append(i);
            throw new KryoException(sb2.toString());
        }
    }

    /* access modifiers changed from: protected */
    public Registration readName(Input input) {
        int readVarInt = input.readVarInt(true);
        if (this.nameIdToClass == null) {
            this.nameIdToClass = new IntMap<>();
        }
        Class cls = (Class) this.nameIdToClass.get(readVarInt);
        String str = "kryo";
        if (cls == null) {
            String readString = input.readString();
            cls = getTypeByName(readString);
            if (cls == null) {
                try {
                    cls = Class.forName(readString, false, this.kryo.getClassLoader());
                } catch (ClassNotFoundException e) {
                    if (Log.WARN) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Unable to load class ");
                        sb.append(readString);
                        sb.append(" with kryo's ClassLoader. Retrying with current..");
                        Log.warn(str, sb.toString());
                    }
                    try {
                        cls = Class.forName(readString);
                    } catch (ClassNotFoundException unused) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("Unable to find class: ");
                        sb2.append(readString);
                        throw new KryoException(sb2.toString(), e);
                    }
                }
                if (this.nameToClass == null) {
                    this.nameToClass = new ObjectMap<>();
                }
                this.nameToClass.put(readString, cls);
            }
            this.nameIdToClass.put(readVarInt, cls);
            if (Log.TRACE) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Read class name: ");
                sb3.append(readString);
                Log.trace(str, sb3.toString());
            }
        } else if (Log.TRACE) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Read class name reference ");
            sb4.append(readVarInt);
            sb4.append(": ");
            sb4.append(Util.className(cls));
            Log.trace(str, sb4.toString());
        }
        return this.kryo.getRegistration(cls);
    }

    /* access modifiers changed from: protected */
    public Class<?> getTypeByName(String str) {
        ObjectMap<String, Class> objectMap = this.nameToClass;
        if (objectMap != null) {
            return (Class) objectMap.get(str);
        }
        return null;
    }

    public void reset() {
        if (!this.kryo.isRegistrationRequired()) {
            IdentityObjectIntMap<Class> identityObjectIntMap = this.classToNameId;
            if (identityObjectIntMap != null) {
                identityObjectIntMap.clear();
            }
            IntMap<Class> intMap = this.nameIdToClass;
            if (intMap != null) {
                intMap.clear();
            }
            this.nextNameId = 0;
        }
    }
}
