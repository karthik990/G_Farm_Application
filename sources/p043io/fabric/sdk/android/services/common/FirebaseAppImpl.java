package p043io.fabric.sdk.android.services.common;

import android.content.Context;
import java.lang.reflect.Method;
import p043io.fabric.sdk.android.Fabric;
import p043io.fabric.sdk.android.Logger;

/* renamed from: io.fabric.sdk.android.services.common.FirebaseAppImpl */
final class FirebaseAppImpl implements FirebaseApp {
    private static final String FIREBASE_APP_CLASS = "com.google.firebase.FirebaseApp";
    private static final String GET_INSTANCE_METHOD = "getInstance";
    private static final String IS_DATA_COLLECTION_ENABLED_METHOD = "isDataCollectionDefaultEnabled";
    private final Object firebaseAppInstance;
    private final Method isDataCollectionDefaultEnabledMethod;

    public static FirebaseApp getInstance(Context context) {
        String str = Fabric.TAG;
        try {
            Class loadClass = context.getClassLoader().loadClass(FIREBASE_APP_CLASS);
            return new FirebaseAppImpl(loadClass, loadClass.getDeclaredMethod(GET_INSTANCE_METHOD, new Class[0]).invoke(loadClass, new Object[0]));
        } catch (ClassNotFoundException unused) {
            Fabric.getLogger().mo64074d(str, "Could not find class: com.google.firebase.FirebaseApp");
            return null;
        } catch (NoSuchMethodException e) {
            Logger logger = Fabric.getLogger();
            StringBuilder sb = new StringBuilder();
            sb.append("Could not find method: ");
            sb.append(e.getMessage());
            logger.mo64074d(str, sb.toString());
            return null;
        } catch (Exception e2) {
            Fabric.getLogger().mo64075d(str, "Unexpected error loading FirebaseApp instance.", e2);
            return null;
        }
    }

    private FirebaseAppImpl(Class cls, Object obj) throws NoSuchMethodException {
        this.firebaseAppInstance = obj;
        this.isDataCollectionDefaultEnabledMethod = cls.getDeclaredMethod(IS_DATA_COLLECTION_ENABLED_METHOD, new Class[0]);
    }

    public boolean isDataCollectionDefaultEnabled() {
        try {
            return ((Boolean) this.isDataCollectionDefaultEnabledMethod.invoke(this.firebaseAppInstance, new Object[0])).booleanValue();
        } catch (Exception e) {
            Fabric.getLogger().mo64075d(Fabric.TAG, "Cannot check isDataCollectionDefaultEnabled on FirebaseApp.", e);
            return false;
        }
    }
}
