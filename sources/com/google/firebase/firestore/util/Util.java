package com.google.firebase.firestore.util;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.cloud.datastore.core.number.NumberComparisonHelper;
import com.google.common.base.Ascii;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreException.Code;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import p043io.grpc.Status;
import p043io.grpc.StatusException;
import p043io.grpc.StatusRuntimeException;

/* compiled from: com.google.firebase:firebase-firestore@@19.0.0 */
public class Util {
    private static final String AUTO_ID_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int AUTO_ID_LENGTH = 20;
    private static final Comparator COMPARABLE_COMPARATOR = new Comparator<Comparable<?>>() {
        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    };
    private static final Continuation<Void, Void> VOID_ERROR_TRANSFORMER = Util$$Lambda$1.lambdaFactory$();
    private static final Random rand = new Random();

    public static int compareBooleans(boolean z, boolean z2) {
        if (z == z2) {
            return 0;
        }
        return z ? 1 : -1;
    }

    public static int compareIntegers(int i, int i2) {
        if (i < i2) {
            return -1;
        }
        return i > i2 ? 1 : 0;
    }

    public static String autoId() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append(AUTO_ID_ALPHABET.charAt(rand.nextInt(62)));
        }
        return sb.toString();
    }

    public static int compareLongs(long j, long j2) {
        return NumberComparisonHelper.compareLongs(j, j2);
    }

    public static int compareInts(int i, int i2) {
        return NumberComparisonHelper.compareLongs((long) i, (long) i2);
    }

    public static int compareDoubles(double d, double d2) {
        return NumberComparisonHelper.firestoreCompareDoubles(d, d2);
    }

    public static int compareMixed(double d, long j) {
        return NumberComparisonHelper.firestoreCompareDoubleWithLong(d, j);
    }

    public static <T extends Comparable<T>> Comparator<T> comparator() {
        return COMPARABLE_COMPARATOR;
    }

    public static FirebaseFirestoreException exceptionFromStatus(Status status) {
        StatusException asException = status.asException();
        return new FirebaseFirestoreException(asException.getMessage(), Code.fromValue(status.getCode().value()), asException);
    }

    private static Exception convertStatusException(Exception exc) {
        if (exc instanceof StatusException) {
            return exceptionFromStatus(((StatusException) exc).getStatus());
        }
        if (exc instanceof StatusRuntimeException) {
            exc = exceptionFromStatus(((StatusRuntimeException) exc).getStatus());
        }
        return exc;
    }

    public static Exception convertThrowableToException(Throwable th) {
        if (th instanceof Exception) {
            return convertStatusException((Exception) th);
        }
        return new Exception(th);
    }

    static /* synthetic */ Void lambda$static$0(Task task) throws Exception {
        if (task.isSuccessful()) {
            return (Void) task.getResult();
        }
        Exception convertStatusException = convertStatusException(task.getException());
        if (convertStatusException instanceof FirebaseFirestoreException) {
            throw convertStatusException;
        }
        throw new FirebaseFirestoreException(convertStatusException.getMessage(), Code.UNKNOWN, convertStatusException);
    }

    public static Continuation<Void, Void> voidErrorTransformer() {
        return VOID_ERROR_TRANSFORMER;
    }

    public static List<Object> collectUpdateArguments(int i, Object obj, Object obj2, Object... objArr) {
        if (objArr.length % 2 != 1) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(obj);
            arrayList.add(obj2);
            Collections.addAll(arrayList, objArr);
            int i2 = 0;
            while (i2 < arrayList.size()) {
                Object obj3 = arrayList.get(i2);
                if ((obj3 instanceof String) || (obj3 instanceof FieldPath)) {
                    i2 += 2;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Excepted field name at argument position ");
                    sb.append(i2 + i + 1);
                    sb.append(" but got ");
                    sb.append(obj3);
                    sb.append(" in call to update.  The arguments to update should alternate between field names and values");
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            return arrayList;
        }
        throw new IllegalArgumentException("Missing value in call to update().  There must be an even number of arguments that alternate between field names and values");
    }

    public static String toDebugString(ByteString byteString) {
        int size = byteString.size();
        StringBuilder sb = new StringBuilder(size * 2);
        for (int i = 0; i < size; i++) {
            byte byteAt = byteString.byteAt(i) & 255;
            sb.append(Character.forDigit(byteAt >>> 4, 16));
            sb.append(Character.forDigit(byteAt & Ascii.f1875SI, 16));
        }
        return sb.toString();
    }

    public static String typeName(Object obj) {
        return obj == null ? "null" : obj.getClass().getName();
    }
}
