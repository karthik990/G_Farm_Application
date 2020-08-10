package com.google.android.gms.tagmanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import com.braintreepayments.api.models.PostalAddressParser;
import java.util.HashSet;

final class zzed extends SQLiteOpenHelper {
    private final /* synthetic */ zzeb zzaik;
    private boolean zzail;
    private long zzaim = 0;

    zzed(zzeb zzeb, Context context, String str) {
        this.zzaik = zzeb;
        super(context, str, null, 1);
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    private static boolean zza(String str, SQLiteDatabase sQLiteDatabase) {
        Cursor cursor = null;
        try {
            Cursor query = sQLiteDatabase.query("SQLITE_MASTER", new String[]{PostalAddressParser.USER_ADDRESS_NAME_KEY}, "name=?", new String[]{str}, null, null, null);
            boolean moveToFirst = query.moveToFirst();
            if (query != null) {
                query.close();
            }
            return moveToFirst;
        } catch (SQLiteException unused) {
            String str2 = "Error querying for table ";
            String valueOf = String.valueOf(str);
            zzdi.zzac(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            if (cursor != null) {
                cursor.close();
            }
            return false;
        } catch (Throwable th) {
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (!this.zzail || this.zzaim + 3600000 <= this.zzaik.zzsd.currentTimeMillis()) {
            SQLiteDatabase sQLiteDatabase = null;
            this.zzail = true;
            this.zzaim = this.zzaik.zzsd.currentTimeMillis();
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException unused) {
                this.zzaik.zzrm.getDatabasePath(this.zzaik.zzaih).delete();
            }
            if (sQLiteDatabase == null) {
                sQLiteDatabase = super.getWritableDatabase();
            }
            this.zzail = false;
            return sQLiteDatabase;
        }
        throw new SQLiteException("Database creation failed");
    }

    /* JADX INFO: finally extract failed */
    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        if (VERSION.SDK_INT < 15) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
            try {
                rawQuery.moveToFirst();
            } finally {
                rawQuery.close();
            }
        }
        if (!zza("gtm_hits", sQLiteDatabase)) {
            sQLiteDatabase.execSQL(zzeb.zzxj);
            return;
        }
        Cursor rawQuery2 = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
        HashSet hashSet = new HashSet();
        try {
            String[] columnNames = rawQuery2.getColumnNames();
            for (String add : columnNames) {
                hashSet.add(add);
            }
            rawQuery2.close();
            if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_time") || !hashSet.remove("hit_first_send_time")) {
                throw new SQLiteException("Database column missing");
            } else if (!hashSet.isEmpty()) {
                throw new SQLiteException("Database has extra columns");
            }
        } catch (Throwable th) {
            rawQuery2.close();
            throw th;
        }
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzbr.zzbb(sQLiteDatabase.getPath());
    }
}
