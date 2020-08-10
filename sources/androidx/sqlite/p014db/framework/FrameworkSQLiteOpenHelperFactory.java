package androidx.sqlite.p014db.framework;

import androidx.sqlite.p014db.SupportSQLiteOpenHelper;
import androidx.sqlite.p014db.SupportSQLiteOpenHelper.Configuration;
import androidx.sqlite.p014db.SupportSQLiteOpenHelper.Factory;

/* renamed from: androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory */
public final class FrameworkSQLiteOpenHelperFactory implements Factory {
    public SupportSQLiteOpenHelper create(Configuration configuration) {
        return new FrameworkSQLiteOpenHelper(configuration.context, configuration.name, configuration.callback);
    }
}
