package p043io.paperdb;

import android.content.Context;
import com.esotericsoftware.kryo.Serializer;
import java.util.HashMap;
import java.util.List;

/* renamed from: io.paperdb.Book */
public class Book {
    private final DbStoragePlainFile mStorage;

    protected Book(Context context, String str, HashMap<Class, Serializer> hashMap) {
        this.mStorage = new DbStoragePlainFile(context.getApplicationContext(), str, hashMap);
    }

    protected Book(String str, String str2, HashMap<Class, Serializer> hashMap) {
        this.mStorage = new DbStoragePlainFile(str, str2, hashMap);
    }

    public void destroy() {
        this.mStorage.destroy();
    }

    public <T> Book write(String str, T t) {
        if (t != null) {
            this.mStorage.insert(str, t);
            return this;
        }
        throw new PaperDbException("Paper doesn't support writing null root values");
    }

    public <T> T read(String str) {
        return read(str, null);
    }

    public <T> T read(String str, T t) {
        T select = this.mStorage.select(str);
        return select == null ? t : select;
    }

    public boolean contains(String str) {
        return this.mStorage.exists(str);
    }

    public boolean exist(String str) {
        return this.mStorage.exists(str);
    }

    public long lastModified(String str) {
        return this.mStorage.lastModified(str);
    }

    public void delete(String str) {
        this.mStorage.deleteIfExists(str);
    }

    public List<String> getAllKeys() {
        return this.mStorage.getAllKeys();
    }

    public void setLogLevel(int i) {
        this.mStorage.setLogLevel(i);
    }

    public String getPath() {
        return this.mStorage.getRootFolderPath();
    }

    public String getPath(String str) {
        return this.mStorage.getOriginalFilePath(str);
    }
}
