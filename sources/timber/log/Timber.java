package timber.log;

import android.os.Build.VERSION;
import android.util.Log;
import com.mobiroller.constants.Constants;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Timber {
    private static final List<Tree> FOREST = new ArrayList();
    private static final Tree[] TREE_ARRAY_EMPTY = new Tree[0];
    private static final Tree TREE_OF_SOULS = new Tree() {
        /* renamed from: v */
        public void mo23235v(String str, Object... objArr) {
            for (Tree v : Timber.forestAsArray) {
                v.mo23235v(str, objArr);
            }
        }

        /* renamed from: v */
        public void mo23237v(Throwable th, String str, Object... objArr) {
            for (Tree v : Timber.forestAsArray) {
                v.mo23237v(th, str, objArr);
            }
        }

        /* renamed from: v */
        public void mo23236v(Throwable th) {
            for (Tree v : Timber.forestAsArray) {
                v.mo23236v(th);
            }
        }

        /* renamed from: d */
        public void mo23218d(String str, Object... objArr) {
            for (Tree d : Timber.forestAsArray) {
                d.mo23218d(str, objArr);
            }
        }

        /* renamed from: d */
        public void mo23220d(Throwable th, String str, Object... objArr) {
            for (Tree d : Timber.forestAsArray) {
                d.mo23220d(th, str, objArr);
            }
        }

        /* renamed from: d */
        public void mo23219d(Throwable th) {
            for (Tree d : Timber.forestAsArray) {
                d.mo23219d(th);
            }
        }

        /* renamed from: i */
        public void mo23226i(String str, Object... objArr) {
            for (Tree i : Timber.forestAsArray) {
                i.mo23226i(str, objArr);
            }
        }

        /* renamed from: i */
        public void mo23228i(Throwable th, String str, Object... objArr) {
            for (Tree i : Timber.forestAsArray) {
                i.mo23228i(th, str, objArr);
            }
        }

        /* renamed from: i */
        public void mo23227i(Throwable th) {
            for (Tree i : Timber.forestAsArray) {
                i.mo23227i(th);
            }
        }

        /* renamed from: w */
        public void mo23238w(String str, Object... objArr) {
            for (Tree w : Timber.forestAsArray) {
                w.mo23238w(str, objArr);
            }
        }

        /* renamed from: w */
        public void mo23240w(Throwable th, String str, Object... objArr) {
            for (Tree w : Timber.forestAsArray) {
                w.mo23240w(th, str, objArr);
            }
        }

        /* renamed from: w */
        public void mo23239w(Throwable th) {
            for (Tree w : Timber.forestAsArray) {
                w.mo23239w(th);
            }
        }

        /* renamed from: e */
        public void mo23221e(String str, Object... objArr) {
            for (Tree e : Timber.forestAsArray) {
                e.mo23221e(str, objArr);
            }
        }

        /* renamed from: e */
        public void mo23223e(Throwable th, String str, Object... objArr) {
            for (Tree e : Timber.forestAsArray) {
                e.mo23223e(th, str, objArr);
            }
        }

        /* renamed from: e */
        public void mo23222e(Throwable th) {
            for (Tree e : Timber.forestAsArray) {
                e.mo23222e(th);
            }
        }

        public void wtf(String str, Object... objArr) {
            for (Tree wtf : Timber.forestAsArray) {
                wtf.wtf(str, objArr);
            }
        }

        public void wtf(Throwable th, String str, Object... objArr) {
            for (Tree wtf : Timber.forestAsArray) {
                wtf.wtf(th, str, objArr);
            }
        }

        public void wtf(Throwable th) {
            for (Tree wtf : Timber.forestAsArray) {
                wtf.wtf(th);
            }
        }

        public void log(int i, String str, Object... objArr) {
            for (Tree log : Timber.forestAsArray) {
                log.log(i, str, objArr);
            }
        }

        public void log(int i, Throwable th, String str, Object... objArr) {
            for (Tree log : Timber.forestAsArray) {
                log.log(i, th, str, objArr);
            }
        }

        public void log(int i, Throwable th) {
            for (Tree log : Timber.forestAsArray) {
                log.log(i, th);
            }
        }

        /* access modifiers changed from: protected */
        public void log(int i, String str, String str2, Throwable th) {
            throw new AssertionError("Missing override for log method.");
        }
    };
    static volatile Tree[] forestAsArray = TREE_ARRAY_EMPTY;

    public static abstract class Tree {
        final ThreadLocal<String> explicitTag = new ThreadLocal<>();

        /* access modifiers changed from: protected */
        @Deprecated
        public boolean isLoggable(int i) {
            return true;
        }

        /* access modifiers changed from: protected */
        public abstract void log(int i, String str, String str2, Throwable th);

        /* access modifiers changed from: 0000 */
        public String getTag() {
            String str = (String) this.explicitTag.get();
            if (str != null) {
                this.explicitTag.remove();
            }
            return str;
        }

        /* renamed from: v */
        public void mo23235v(String str, Object... objArr) {
            prepareLog(2, null, str, objArr);
        }

        /* renamed from: v */
        public void mo23237v(Throwable th, String str, Object... objArr) {
            prepareLog(2, th, str, objArr);
        }

        /* renamed from: v */
        public void mo23236v(Throwable th) {
            prepareLog(2, th, null, new Object[0]);
        }

        /* renamed from: d */
        public void mo23218d(String str, Object... objArr) {
            prepareLog(3, null, str, objArr);
        }

        /* renamed from: d */
        public void mo23220d(Throwable th, String str, Object... objArr) {
            prepareLog(3, th, str, objArr);
        }

        /* renamed from: d */
        public void mo23219d(Throwable th) {
            prepareLog(3, th, null, new Object[0]);
        }

        /* renamed from: i */
        public void mo23226i(String str, Object... objArr) {
            prepareLog(4, null, str, objArr);
        }

        /* renamed from: i */
        public void mo23228i(Throwable th, String str, Object... objArr) {
            prepareLog(4, th, str, objArr);
        }

        /* renamed from: i */
        public void mo23227i(Throwable th) {
            prepareLog(4, th, null, new Object[0]);
        }

        /* renamed from: w */
        public void mo23238w(String str, Object... objArr) {
            prepareLog(5, null, str, objArr);
        }

        /* renamed from: w */
        public void mo23240w(Throwable th, String str, Object... objArr) {
            prepareLog(5, th, str, objArr);
        }

        /* renamed from: w */
        public void mo23239w(Throwable th) {
            prepareLog(5, th, null, new Object[0]);
        }

        /* renamed from: e */
        public void mo23221e(String str, Object... objArr) {
            prepareLog(6, null, str, objArr);
        }

        /* renamed from: e */
        public void mo23223e(Throwable th, String str, Object... objArr) {
            prepareLog(6, th, str, objArr);
        }

        /* renamed from: e */
        public void mo23222e(Throwable th) {
            prepareLog(6, th, null, new Object[0]);
        }

        public void wtf(String str, Object... objArr) {
            prepareLog(7, null, str, objArr);
        }

        public void wtf(Throwable th, String str, Object... objArr) {
            prepareLog(7, th, str, objArr);
        }

        public void wtf(Throwable th) {
            prepareLog(7, th, null, new Object[0]);
        }

        public void log(int i, String str, Object... objArr) {
            prepareLog(i, null, str, objArr);
        }

        public void log(int i, Throwable th, String str, Object... objArr) {
            prepareLog(i, th, str, objArr);
        }

        public void log(int i, Throwable th) {
            prepareLog(i, th, null, new Object[0]);
        }

        /* access modifiers changed from: protected */
        public boolean isLoggable(String str, int i) {
            return isLoggable(i);
        }

        private void prepareLog(int i, Throwable th, String str, Object... objArr) {
            String tag = getTag();
            if (isLoggable(tag, i)) {
                if (str != null && str.length() == 0) {
                    str = null;
                }
                if (str != null) {
                    if (objArr != null && objArr.length > 0) {
                        str = formatMessage(str, objArr);
                    }
                    if (th != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(str);
                        sb.append(Constants.NEW_LINE);
                        sb.append(getStackTraceString(th));
                        str = sb.toString();
                    }
                } else if (th != null) {
                    str = getStackTraceString(th);
                } else {
                    return;
                }
                log(i, tag, str, th);
            }
        }

        /* access modifiers changed from: protected */
        public String formatMessage(String str, Object[] objArr) {
            return String.format(str, objArr);
        }

        private String getStackTraceString(Throwable th) {
            StringWriter stringWriter = new StringWriter(256);
            PrintWriter printWriter = new PrintWriter(stringWriter, false);
            th.printStackTrace(printWriter);
            printWriter.flush();
            return stringWriter.toString();
        }
    }

    public static class DebugTree extends Tree {
        private static final Pattern ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$");
        private static final int CALL_STACK_INDEX = 5;
        private static final int MAX_LOG_LENGTH = 4000;
        private static final int MAX_TAG_LENGTH = 23;

        /* access modifiers changed from: protected */
        public String createStackElementTag(StackTraceElement stackTraceElement) {
            String className = stackTraceElement.getClassName();
            Matcher matcher = ANONYMOUS_CLASS.matcher(className);
            if (matcher.find()) {
                className = matcher.replaceAll("");
            }
            String substring = className.substring(className.lastIndexOf(46) + 1);
            return (substring.length() <= 23 || VERSION.SDK_INT >= 24) ? substring : substring.substring(0, 23);
        }

        /* access modifiers changed from: 0000 */
        public final String getTag() {
            String tag = super.getTag();
            if (tag != null) {
                return tag;
            }
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace.length > 5) {
                return createStackElementTag(stackTrace[5]);
            }
            throw new IllegalStateException("Synthetic stacktrace didn't have enough elements: are you using proguard?");
        }

        /* access modifiers changed from: protected */
        public void log(int i, String str, String str2, Throwable th) {
            int min;
            if (str2.length() < MAX_LOG_LENGTH) {
                if (i == 7) {
                    Log.wtf(str, str2);
                } else {
                    Log.println(i, str, str2);
                }
                return;
            }
            int i2 = 0;
            int length = str2.length();
            while (i2 < length) {
                int indexOf = str2.indexOf(10, i2);
                if (indexOf == -1) {
                    indexOf = length;
                }
                while (true) {
                    min = Math.min(indexOf, i2 + MAX_LOG_LENGTH);
                    String substring = str2.substring(i2, min);
                    if (i == 7) {
                        Log.wtf(str, substring);
                    } else {
                        Log.println(i, str, substring);
                    }
                    if (min >= indexOf) {
                        break;
                    }
                    i2 = min;
                }
                i2 = min + 1;
            }
        }
    }

    /* renamed from: v */
    public static void m1351v(String str, Object... objArr) {
        TREE_OF_SOULS.mo23235v(str, objArr);
    }

    /* renamed from: v */
    public static void m1353v(Throwable th, String str, Object... objArr) {
        TREE_OF_SOULS.mo23237v(th, str, objArr);
    }

    /* renamed from: v */
    public static void m1352v(Throwable th) {
        TREE_OF_SOULS.mo23236v(th);
    }

    /* renamed from: d */
    public static void m1342d(String str, Object... objArr) {
        TREE_OF_SOULS.mo23218d(str, objArr);
    }

    /* renamed from: d */
    public static void m1344d(Throwable th, String str, Object... objArr) {
        TREE_OF_SOULS.mo23220d(th, str, objArr);
    }

    /* renamed from: d */
    public static void m1343d(Throwable th) {
        TREE_OF_SOULS.mo23219d(th);
    }

    /* renamed from: i */
    public static void m1348i(String str, Object... objArr) {
        TREE_OF_SOULS.mo23226i(str, objArr);
    }

    /* renamed from: i */
    public static void m1350i(Throwable th, String str, Object... objArr) {
        TREE_OF_SOULS.mo23228i(th, str, objArr);
    }

    /* renamed from: i */
    public static void m1349i(Throwable th) {
        TREE_OF_SOULS.mo23227i(th);
    }

    /* renamed from: w */
    public static void m1354w(String str, Object... objArr) {
        TREE_OF_SOULS.mo23238w(str, objArr);
    }

    /* renamed from: w */
    public static void m1356w(Throwable th, String str, Object... objArr) {
        TREE_OF_SOULS.mo23240w(th, str, objArr);
    }

    /* renamed from: w */
    public static void m1355w(Throwable th) {
        TREE_OF_SOULS.mo23239w(th);
    }

    /* renamed from: e */
    public static void m1345e(String str, Object... objArr) {
        TREE_OF_SOULS.mo23221e(str, objArr);
    }

    /* renamed from: e */
    public static void m1347e(Throwable th, String str, Object... objArr) {
        TREE_OF_SOULS.mo23223e(th, str, objArr);
    }

    /* renamed from: e */
    public static void m1346e(Throwable th) {
        TREE_OF_SOULS.mo23222e(th);
    }

    public static void wtf(String str, Object... objArr) {
        TREE_OF_SOULS.wtf(str, objArr);
    }

    public static void wtf(Throwable th, String str, Object... objArr) {
        TREE_OF_SOULS.wtf(th, str, objArr);
    }

    public static void wtf(Throwable th) {
        TREE_OF_SOULS.wtf(th);
    }

    public static void log(int i, String str, Object... objArr) {
        TREE_OF_SOULS.log(i, str, objArr);
    }

    public static void log(int i, Throwable th, String str, Object... objArr) {
        TREE_OF_SOULS.log(i, th, str, objArr);
    }

    public static void log(int i, Throwable th) {
        TREE_OF_SOULS.log(i, th);
    }

    public static Tree asTree() {
        return TREE_OF_SOULS;
    }

    public static Tree tag(String str) {
        for (Tree tree : forestAsArray) {
            tree.explicitTag.set(str);
        }
        return TREE_OF_SOULS;
    }

    public static void plant(Tree tree) {
        if (tree == null) {
            throw new NullPointerException("tree == null");
        } else if (tree != TREE_OF_SOULS) {
            synchronized (FOREST) {
                FOREST.add(tree);
                forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
            }
        } else {
            throw new IllegalArgumentException("Cannot plant Timber into itself.");
        }
    }

    public static void plant(Tree... treeArr) {
        if (treeArr != null) {
            int length = treeArr.length;
            int i = 0;
            while (i < length) {
                Tree tree = treeArr[i];
                if (tree == null) {
                    throw new NullPointerException("trees contains null");
                } else if (tree != TREE_OF_SOULS) {
                    i++;
                } else {
                    throw new IllegalArgumentException("Cannot plant Timber into itself.");
                }
            }
            synchronized (FOREST) {
                Collections.addAll(FOREST, treeArr);
                forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
            }
            return;
        }
        throw new NullPointerException("trees == null");
    }

    public static void uproot(Tree tree) {
        synchronized (FOREST) {
            if (FOREST.remove(tree)) {
                forestAsArray = (Tree[]) FOREST.toArray(new Tree[FOREST.size()]);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot uproot tree which is not planted: ");
                sb.append(tree);
                throw new IllegalArgumentException(sb.toString());
            }
        }
    }

    public static void uprootAll() {
        synchronized (FOREST) {
            FOREST.clear();
            forestAsArray = TREE_ARRAY_EMPTY;
        }
    }

    public static List<Tree> forest() {
        List<Tree> unmodifiableList;
        synchronized (FOREST) {
            unmodifiableList = Collections.unmodifiableList(new ArrayList(FOREST));
        }
        return unmodifiableList;
    }

    public static int treeCount() {
        int size;
        synchronized (FOREST) {
            size = FOREST.size();
        }
        return size;
    }

    private Timber() {
        throw new AssertionError("No instances.");
    }
}
