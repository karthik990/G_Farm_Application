package p043io.paperdb;

import android.content.Context;
import com.esotericsoftware.kryo.Serializer;
import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/* renamed from: io.paperdb.Paper */
public class Paper {
    static final String DEFAULT_DB_NAME = "io.paperdb";
    static final String TAG = "paperdb";
    private static final ConcurrentHashMap<String, Book> mBookMap = new ConcurrentHashMap<>();
    private static Context mContext;
    private static final HashMap<Class, Serializer> mCustomSerializers = new HashMap<>();

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static Book book(String str) {
        if (!str.equals("io.paperdb")) {
            return getBook(null, str);
        }
        throw new PaperDbException("io.paperdb name is reserved for default library name");
    }

    public static Book book() {
        return getBook(null, "io.paperdb");
    }

    public static Book bookOn(String str, String str2) {
        return getBook(removeLastFileSeparatorIfExists(str), str2);
    }

    public static Book bookOn(String str) {
        return bookOn(str, "io.paperdb");
    }

    private static Book getBook(String str, String str2) {
        Book book;
        if (mContext != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(str == null ? "" : str);
            sb.append(str2);
            String sb2 = sb.toString();
            synchronized (mBookMap) {
                book = (Book) mBookMap.get(sb2);
                if (book == null) {
                    if (str == null) {
                        book = new Book(mContext, str2, mCustomSerializers);
                    } else {
                        book = new Book(str, str2, mCustomSerializers);
                    }
                    mBookMap.put(sb2, book);
                }
            }
            return book;
        }
        throw new PaperDbException("Paper.init is not called");
    }

    private static String removeLastFileSeparatorIfExists(String str) {
        return str.endsWith(File.separator) ? str.substring(0, str.length() - 1) : str;
    }

    public static <T> Book put(String str, T t) {
        return book().write(str, t);
    }

    public static <T> T get(String str) {
        return book().read(str);
    }

    public static <T> T get(String str, T t) {
        return book().read(str, t);
    }

    public static boolean exist(String str) {
        return book().contains(str);
    }

    public static void delete(String str) {
        book().delete(str);
    }

    public static void clear(Context context) {
        init(context);
        book().destroy();
    }

    public static void setLogLevel(int i) {
        for (Entry value : mBookMap.entrySet()) {
            ((Book) value.getValue()).setLogLevel(i);
        }
    }

    public static <T> void addSerializer(Class<T> cls, Serializer<T> serializer) {
        if (!mCustomSerializers.containsKey(cls)) {
            mCustomSerializers.put(cls, serializer);
        }
    }
}
