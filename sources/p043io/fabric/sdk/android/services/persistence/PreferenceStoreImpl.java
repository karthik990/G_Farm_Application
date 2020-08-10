package p043io.fabric.sdk.android.services.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import p043io.fabric.sdk.android.Kit;

/* renamed from: io.fabric.sdk.android.services.persistence.PreferenceStoreImpl */
public class PreferenceStoreImpl implements PreferenceStore {
    private final Context context;
    private final String preferenceName;
    private final SharedPreferences sharedPreferences;

    public PreferenceStoreImpl(Context context2, String str) {
        if (context2 != null) {
            this.context = context2;
            this.preferenceName = str;
            this.sharedPreferences = this.context.getSharedPreferences(this.preferenceName, 0);
            return;
        }
        throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
    }

    @Deprecated
    public PreferenceStoreImpl(Kit kit) {
        this(kit.getContext(), kit.getClass().getName());
    }

    public SharedPreferences get() {
        return this.sharedPreferences;
    }

    public Editor edit() {
        return this.sharedPreferences.edit();
    }

    public boolean save(Editor editor) {
        if (VERSION.SDK_INT < 9) {
            return editor.commit();
        }
        editor.apply();
        return true;
    }
}
