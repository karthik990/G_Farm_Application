package androidx.work.impl.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class Preferences {
    private static final String KEY_LAST_CANCEL_ALL_TIME_MS = "last_cancel_all_time_ms";
    private static final String KEY_RESCHEDULE_NEEDED = "reschedule_needed";
    private static final String PREFERENCES_FILE_NAME = "androidx.work.util.preferences";
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    private static class LastCancelAllLiveData extends MutableLiveData<Long> implements OnSharedPreferenceChangeListener {
        private long mLastCancelAllTimeMillis = this.mSharedPreferences.getLong(Preferences.KEY_LAST_CANCEL_ALL_TIME_MS, 0);
        private SharedPreferences mSharedPreferences;

        LastCancelAllLiveData(SharedPreferences sharedPreferences) {
            this.mSharedPreferences = sharedPreferences;
            postValue(Long.valueOf(this.mLastCancelAllTimeMillis));
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
            if (Preferences.KEY_LAST_CANCEL_ALL_TIME_MS.equals(str)) {
                long j = sharedPreferences.getLong(str, 0);
                if (this.mLastCancelAllTimeMillis != j) {
                    this.mLastCancelAllTimeMillis = j;
                    setValue(Long.valueOf(this.mLastCancelAllTimeMillis));
                }
            }
        }

        /* access modifiers changed from: protected */
        public void onActive() {
            super.onActive();
            this.mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        }

        /* access modifiers changed from: protected */
        public void onInactive() {
            super.onInactive();
            this.mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    public Preferences(Context context) {
        this.mContext = context;
    }

    public Preferences(SharedPreferences sharedPreferences) {
        this.mSharedPreferences = sharedPreferences;
    }

    public long getLastCancelAllTimeMillis() {
        return getSharedPreferences().getLong(KEY_LAST_CANCEL_ALL_TIME_MS, 0);
    }

    public LiveData<Long> getLastCancelAllTimeMillisLiveData() {
        return new LastCancelAllLiveData(getSharedPreferences());
    }

    public void setLastCancelAllTimeMillis(long j) {
        getSharedPreferences().edit().putLong(KEY_LAST_CANCEL_ALL_TIME_MS, j).apply();
    }

    public boolean needsReschedule() {
        return getSharedPreferences().getBoolean(KEY_RESCHEDULE_NEEDED, false);
    }

    public void setNeedsReschedule(boolean z) {
        getSharedPreferences().edit().putBoolean(KEY_RESCHEDULE_NEEDED, z).apply();
    }

    private SharedPreferences getSharedPreferences() {
        SharedPreferences sharedPreferences;
        synchronized (Preferences.class) {
            if (this.mSharedPreferences == null) {
                this.mSharedPreferences = this.mContext.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
            }
            sharedPreferences = this.mSharedPreferences;
        }
        return sharedPreferences;
    }
}
