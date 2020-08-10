package junit.framework;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.junit.internal.MethodSorter;

public class TestSuite implements Test {
    private String fName;
    private Vector<Test> fTests;

    public static Test createTest(Class<?> cls, String str) {
        Object obj;
        String str2 = ")";
        String str3 = " (";
        try {
            Constructor testConstructor = getTestConstructor(cls);
            try {
                if (testConstructor.getParameterTypes().length == 0) {
                    obj = testConstructor.newInstance(new Object[0]);
                    if (obj instanceof TestCase) {
                        ((TestCase) obj).setName(str);
                    }
                } else {
                    obj = testConstructor.newInstance(new Object[]{str});
                }
                return (Test) obj;
            } catch (InstantiationException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Cannot instantiate test case: ");
                sb.append(str);
                sb.append(str3);
                sb.append(exceptionToString(e));
                sb.append(str2);
                return warning(sb.toString());
            } catch (InvocationTargetException e2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Exception in constructor: ");
                sb2.append(str);
                sb2.append(str3);
                sb2.append(exceptionToString(e2.getTargetException()));
                sb2.append(str2);
                return warning(sb2.toString());
            } catch (IllegalAccessException e3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Cannot access test case: ");
                sb3.append(str);
                sb3.append(str3);
                sb3.append(exceptionToString(e3));
                sb3.append(str2);
                return warning(sb3.toString());
            }
        } catch (NoSuchMethodException unused) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append("Class ");
            sb4.append(cls.getName());
            sb4.append(" has no public constructor TestCase(String name) or TestCase()");
            return warning(sb4.toString());
        }
    }

    public static Constructor<?> getTestConstructor(Class<?> cls) throws NoSuchMethodException {
        try {
            return cls.getConstructor(new Class[]{String.class});
        } catch (NoSuchMethodException unused) {
            return cls.getConstructor(new Class[0]);
        }
    }

    public static Test warning(final String str) {
        return new TestCase("warning") {
            /* access modifiers changed from: protected */
            public void runTest() {
                fail(str);
            }
        };
    }

    private static String exceptionToString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public TestSuite() {
        this.fTests = new Vector<>(10);
    }

    public TestSuite(Class<?> cls) {
        this.fTests = new Vector<>(10);
        addTestsFromTestCase(cls);
    }

    private void addTestsFromTestCase(Class<?> cls) {
        String str = "Class ";
        this.fName = cls.getName();
        try {
            getTestConstructor(cls);
            if (!Modifier.isPublic(cls.getModifiers())) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append(cls.getName());
                sb.append(" is not public");
                addTest(warning(sb.toString()));
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (Class<?> cls2 = cls; Test.class.isAssignableFrom(cls2); cls2 = cls2.getSuperclass()) {
                for (Method addTestMethod : MethodSorter.getDeclaredMethods(cls2)) {
                    addTestMethod(addTestMethod, arrayList, cls);
                }
            }
            if (this.fTests.size() == 0) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("No tests found in ");
                sb2.append(cls.getName());
                addTest(warning(sb2.toString()));
            }
        } catch (NoSuchMethodException unused) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(cls.getName());
            sb3.append(" has no public constructor TestCase(String name) or TestCase()");
            addTest(warning(sb3.toString()));
        }
    }

    public TestSuite(Class<? extends TestCase> cls, String str) {
        this(cls);
        setName(str);
    }

    public TestSuite(String str) {
        this.fTests = new Vector<>(10);
        setName(str);
    }

    public TestSuite(Class<?>... clsArr) {
        this.fTests = new Vector<>(10);
        for (Class<?> testCaseForClass : clsArr) {
            addTest(testCaseForClass(testCaseForClass));
        }
    }

    private Test testCaseForClass(Class<?> cls) {
        if (TestCase.class.isAssignableFrom(cls)) {
            return new TestSuite(cls.asSubclass(TestCase.class));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getCanonicalName());
        sb.append(" does not extend TestCase");
        return warning(sb.toString());
    }

    public TestSuite(Class<? extends TestCase>[] clsArr, String str) {
        this((Class<?>[]) clsArr);
        setName(str);
    }

    public void addTest(Test test) {
        this.fTests.add(test);
    }

    public void addTestSuite(Class<? extends TestCase> cls) {
        addTest(new TestSuite(cls));
    }

    public int countTestCases() {
        Iterator it = this.fTests.iterator();
        int i = 0;
        while (it.hasNext()) {
            i += ((Test) it.next()).countTestCases();
        }
        return i;
    }

    public String getName() {
        return this.fName;
    }

    public void run(TestResult testResult) {
        Iterator it = this.fTests.iterator();
        while (it.hasNext()) {
            Test test = (Test) it.next();
            if (!testResult.shouldStop()) {
                runTest(test, testResult);
            } else {
                return;
            }
        }
    }

    public void runTest(Test test, TestResult testResult) {
        test.run(testResult);
    }

    public void setName(String str) {
        this.fName = str;
    }

    public Test testAt(int i) {
        return (Test) this.fTests.get(i);
    }

    public int testCount() {
        return this.fTests.size();
    }

    public Enumeration<Test> tests() {
        return this.fTests.elements();
    }

    public String toString() {
        if (getName() != null) {
            return getName();
        }
        return super.toString();
    }

    private void addTestMethod(Method method, List<String> list, Class<?> cls) {
        String name = method.getName();
        if (!list.contains(name)) {
            if (!isPublicTestMethod(method)) {
                if (isTestMethod(method)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Test method isn't public: ");
                    sb.append(method.getName());
                    sb.append("(");
                    sb.append(cls.getCanonicalName());
                    sb.append(")");
                    addTest(warning(sb.toString()));
                }
                return;
            }
            list.add(name);
            addTest(createTest(cls, name));
        }
    }

    private boolean isPublicTestMethod(Method method) {
        return isTestMethod(method) && Modifier.isPublic(method.getModifiers());
    }

    private boolean isTestMethod(Method method) {
        return method.getParameterTypes().length == 0 && method.getName().startsWith("test") && method.getReturnType().equals(Void.TYPE);
    }
}
