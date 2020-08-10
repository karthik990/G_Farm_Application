package p043io.paperdb;

import android.content.Context;
import android.util.Log;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Kryo.DefaultInstantiatorStrategy;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.p035io.Input;
import com.esotericsoftware.kryo.p035io.Output;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.objenesis.strategy.StdInstantiatorStrategy;
import p043io.paperdb.serializer.NoArgCollectionSerializer;
import p097de.javakaffee.kryoserializers.ArraysAsListSerializer;
import p097de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import p097de.javakaffee.kryoserializers.UUIDSerializer;
import p097de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;

/* renamed from: io.paperdb.DbStoragePlainFile */
public class DbStoragePlainFile {
    private KeyLocker keyLocker = new KeyLocker();
    private final HashMap<Class, Serializer> mCustomSerializers;
    private final String mDbPath;
    private final ThreadLocal<Kryo> mKryo = new ThreadLocal<Kryo>() {
        /* access modifiers changed from: protected */
        public Kryo initialValue() {
            return DbStoragePlainFile.this.createKryoInstance(false);
        }
    };
    private volatile boolean mPaperDirIsCreated;

    private Kryo getKryo() {
        return (Kryo) this.mKryo.get();
    }

    /* access modifiers changed from: private */
    public Kryo createKryoInstance(boolean z) {
        Kryo kryo = new Kryo();
        if (z) {
            kryo.getFieldSerializerConfig().setOptimizedGenerics(true);
        }
        kryo.register(PaperTable.class);
        kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
        kryo.setReferences(false);
        kryo.register(Arrays.asList(new String[]{""}).getClass(), (Serializer) new ArraysAsListSerializer());
        UnmodifiableCollectionsSerializer.registerSerializers(kryo);
        SynchronizedCollectionsSerializer.registerSerializers(kryo);
        kryo.addDefaultSerializer(new ArrayList().subList(0, 0).getClass(), (Serializer) new NoArgCollectionSerializer());
        kryo.addDefaultSerializer(new LinkedList().subList(0, 0).getClass(), (Serializer) new NoArgCollectionSerializer());
        kryo.register(UUID.class, (Serializer) new UUIDSerializer());
        for (Class cls : this.mCustomSerializers.keySet()) {
            kryo.register(cls, (Serializer) this.mCustomSerializers.get(cls));
        }
        kryo.setInstantiatorStrategy(new DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        return kryo;
    }

    DbStoragePlainFile(Context context, String str, HashMap<Class, Serializer> hashMap) {
        this.mCustomSerializers = hashMap;
        StringBuilder sb = new StringBuilder();
        sb.append(context.getFilesDir());
        sb.append(File.separator);
        sb.append(str);
        this.mDbPath = sb.toString();
    }

    DbStoragePlainFile(String str, String str2, HashMap<Class, Serializer> hashMap) {
        this.mCustomSerializers = hashMap;
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(File.separator);
        sb.append(str2);
        this.mDbPath = sb.toString();
    }

    public synchronized void destroy() {
        assertInit();
        if (!deleteDirectory(this.mDbPath)) {
            StringBuilder sb = new StringBuilder();
            sb.append("Couldn't delete Paper dir ");
            sb.append(this.mDbPath);
            Log.e("paperdb", sb.toString());
        }
        this.mPaperDirIsCreated = false;
    }

    /* access modifiers changed from: 0000 */
    public <E> void insert(String str, E e) {
        try {
            this.keyLocker.acquire(str);
            assertInit();
            PaperTable paperTable = new PaperTable(e);
            File originalFile = getOriginalFile(str);
            File makeBackupFile = makeBackupFile(originalFile);
            if (originalFile.exists()) {
                if (makeBackupFile.exists()) {
                    originalFile.delete();
                } else if (!originalFile.renameTo(makeBackupFile)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Couldn't rename file ");
                    sb.append(originalFile);
                    sb.append(" to backup file ");
                    sb.append(makeBackupFile);
                    throw new PaperDbException(sb.toString());
                }
            }
            writeTableFile(str, paperTable, originalFile, makeBackupFile);
        } finally {
            this.keyLocker.release(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public <E> E select(String str) {
        E readTableFile;
        try {
            this.keyLocker.acquire(str);
            assertInit();
            File originalFile = getOriginalFile(str);
            File makeBackupFile = makeBackupFile(originalFile);
            if (makeBackupFile.exists()) {
                originalFile.delete();
                makeBackupFile.renameTo(originalFile);
            }
            if (!existsInternal(str)) {
                readTableFile = null;
            } else {
                readTableFile = readTableFile(str, originalFile);
            }
            return readTableFile;
        } finally {
            this.keyLocker.release(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean exists(String str) {
        try {
            this.keyLocker.acquire(str);
            return existsInternal(str);
        } finally {
            this.keyLocker.release(str);
        }
    }

    private boolean existsInternal(String str) {
        assertInit();
        return getOriginalFile(str).exists();
    }

    /* access modifiers changed from: 0000 */
    public long lastModified(String str) {
        try {
            this.keyLocker.acquire(str);
            assertInit();
            File originalFile = getOriginalFile(str);
            return originalFile.exists() ? originalFile.lastModified() : -1;
        } finally {
            this.keyLocker.release(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public synchronized List<String> getAllKeys() {
        assertInit();
        String[] list = new File(this.mDbPath).list();
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                list[i] = list[i].replace(".pt", "");
            }
            return Arrays.asList(list);
        }
        return new ArrayList();
    }

    /* access modifiers changed from: 0000 */
    public void deleteIfExists(String str) {
        try {
            this.keyLocker.acquire(str);
            assertInit();
            File originalFile = getOriginalFile(str);
            if (originalFile.exists()) {
                if (originalFile.delete()) {
                    this.keyLocker.release(str);
                    return;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Couldn't delete file ");
                sb.append(originalFile);
                sb.append(" for table ");
                sb.append(str);
                throw new PaperDbException(sb.toString());
            }
        } finally {
            this.keyLocker.release(str);
        }
    }

    /* access modifiers changed from: 0000 */
    public void setLogLevel(int i) {
        com.esotericsoftware.minlog.Log.set(i);
    }

    /* access modifiers changed from: 0000 */
    public String getOriginalFilePath(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mDbPath);
        sb.append(File.separator);
        sb.append(str);
        sb.append(".pt");
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public String getRootFolderPath() {
        return this.mDbPath;
    }

    private File getOriginalFile(String str) {
        return new File(getOriginalFilePath(str));
    }

    private <E> void writeTableFile(String str, PaperTable<E> paperTable, File file, File file2) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Output output = new Output((OutputStream) fileOutputStream);
            getKryo().writeObject(output, paperTable);
            output.flush();
            fileOutputStream.flush();
            sync(fileOutputStream);
            output.close();
            file2.delete();
        } catch (KryoException | IOException e) {
            if (!file.exists() || file.delete()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Couldn't save table: ");
                sb.append(str);
                sb.append(". Backed up table will be used on next read attempt");
                throw new PaperDbException(sb.toString(), e);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Couldn't clean up partially-written file ");
            sb2.append(file);
            throw new PaperDbException(sb2.toString(), e);
        }
    }

    private <E> E readTableFile(String str, File file) {
        try {
            return readContent(file, getKryo());
        } catch (KryoException | FileNotFoundException | ClassCastException e) {
            e = e;
            if (e instanceof KryoException) {
                try {
                    return readContent(file, createKryoInstance(true));
                } catch (KryoException | FileNotFoundException | ClassCastException e2) {
                    e = e2;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Couldn't read/deserialize file ");
                    sb.append(file);
                    sb.append(" for table ");
                    sb.append(str);
                    throw new PaperDbException(sb.toString(), e);
                }
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Couldn't read/deserialize file ");
            sb2.append(file);
            sb2.append(" for table ");
            sb2.append(str);
            throw new PaperDbException(sb2.toString(), e);
        }
    }

    private <E> E readContent(File file, Kryo kryo) throws FileNotFoundException, KryoException {
        Input input = new Input((InputStream) new FileInputStream(file));
        try {
            return ((PaperTable) kryo.readObject(input, PaperTable.class)).mContent;
        } finally {
            input.close();
        }
    }

    private void assertInit() {
        if (!this.mPaperDirIsCreated) {
            createPaperDir();
            this.mPaperDirIsCreated = true;
        }
    }

    private void createPaperDir() {
        if (!new File(this.mDbPath).exists() && !new File(this.mDbPath).mkdirs()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Couldn't create Paper dir: ");
            sb.append(this.mDbPath);
            throw new RuntimeException(sb.toString());
        }
    }

    private static boolean deleteDirectory(String str) {
        File file = new File(str);
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        deleteDirectory(file2.toString());
                    } else {
                        file2.delete();
                    }
                }
            }
        }
        return file.delete();
    }

    private File makeBackupFile(File file) {
        StringBuilder sb = new StringBuilder();
        sb.append(file.getPath());
        sb.append(".bak");
        return new File(sb.toString());
    }

    private static void sync(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.getFD().sync();
            } catch (IOException unused) {
            }
        }
    }
}
