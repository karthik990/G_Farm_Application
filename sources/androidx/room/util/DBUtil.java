package androidx.room.util;

import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.os.Build.VERSION;
import androidx.room.RoomDatabase;
import androidx.sqlite.p014db.SupportSQLiteDatabase;
import androidx.sqlite.p014db.SupportSQLiteQuery;
import java.util.ArrayList;

public class DBUtil {
    public static Cursor query(RoomDatabase roomDatabase, SupportSQLiteQuery supportSQLiteQuery, boolean z) {
        Cursor query = roomDatabase.query(supportSQLiteQuery);
        if (!z || !(query instanceof AbstractWindowedCursor)) {
            return query;
        }
        AbstractWindowedCursor abstractWindowedCursor = (AbstractWindowedCursor) query;
        int count = abstractWindowedCursor.getCount();
        return (VERSION.SDK_INT < 23 || (abstractWindowedCursor.hasWindow() ? abstractWindowedCursor.getWindow().getNumRows() : count) < count) ? CursorUtil.copyAndClose(abstractWindowedCursor) : query;
    }

    /* JADX INFO: finally extract failed */
    public static void dropFtsSyncTriggers(SupportSQLiteDatabase supportSQLiteDatabase) {
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor query = supportSQLiteDatabase.query("SELECT name FROM sqlite_master WHERE type = 'trigger'");
        while (query.moveToNext()) {
            try {
                arrayList.add(query.getString(0));
            } catch (Throwable th) {
                query.close();
                throw th;
            }
        }
        query.close();
        for (String str : arrayList) {
            if (str.startsWith("room_fts_content_sync_")) {
                StringBuilder sb = new StringBuilder();
                sb.append("DROP TRIGGER IF EXISTS ");
                sb.append(str);
                supportSQLiteDatabase.execSQL(sb.toString());
            }
        }
    }

    private DBUtil() {
    }
}
