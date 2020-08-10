package junit.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class TestCase extends Assert implements Test {
    private String fName;

    public int countTestCases() {
        return 1;
    }

    /* access modifiers changed from: protected */
    public void setUp() throws Exception {
    }

    /* access modifiers changed from: protected */
    public void tearDown() throws Exception {
    }

    public TestCase() {
        this.fName = null;
    }

    public TestCase(String str) {
        this.fName = str;
    }

    /* access modifiers changed from: protected */
    public TestResult createResult() {
        return new TestResult();
    }

    public TestResult run() {
        TestResult createResult = createResult();
        run(createResult);
        return createResult;
    }

    public void run(TestResult testResult) {
        testResult.run(this);
    }

    public void runBare() throws Throwable {
        setUp();
        try {
            runTest();
            try {
                tearDown();
                th = null;
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable unused) {
        }
        if (th != null) {
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public void runTest() throws Throwable {
        String str = "Method \"";
        assertNotNull("TestCase.fName cannot be null", this.fName);
        Method method = null;
        try {
            method = getClass().getMethod(this.fName, null);
        } catch (NoSuchMethodException unused) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(this.fName);
            sb.append("\" not found");
            fail(sb.toString());
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(this.fName);
            sb2.append("\" should be public");
            fail(sb2.toString());
        }
        try {
            method.invoke(this, new Object[0]);
        } catch (InvocationTargetException e) {
            e.fillInStackTrace();
            throw e.getTargetException();
        } catch (IllegalAccessException e2) {
            e2.fillInStackTrace();
            throw e2;
        }
    }

    public static void assertTrue(String str, boolean z) {
        Assert.assertTrue(str, z);
    }

    public static void assertTrue(boolean z) {
        Assert.assertTrue(z);
    }

    public static void assertFalse(String str, boolean z) {
        Assert.assertFalse(str, z);
    }

    public static void assertFalse(boolean z) {
        Assert.assertFalse(z);
    }

    public static void fail(String str) {
        Assert.fail(str);
    }

    public static void fail() {
        Assert.fail();
    }

    public static void assertEquals(String str, Object obj, Object obj2) {
        Assert.assertEquals(str, obj, obj2);
    }

    public static void assertEquals(Object obj, Object obj2) {
        Assert.assertEquals(obj, obj2);
    }

    public static void assertEquals(String str, String str2, String str3) {
        Assert.assertEquals(str, str2, str3);
    }

    public static void assertEquals(String str, String str2) {
        Assert.assertEquals(str, str2);
    }

    public static void assertEquals(String str, double d, double d2, double d3) {
        Assert.assertEquals(str, d, d2, d3);
    }

    public static void assertEquals(double d, double d2, double d3) {
        Assert.assertEquals(d, d2, d3);
    }

    public static void assertEquals(String str, float f, float f2, float f3) {
        Assert.assertEquals(str, f, f2, f3);
    }

    public static void assertEquals(float f, float f2, float f3) {
        Assert.assertEquals(f, f2, f3);
    }

    public static void assertEquals(String str, long j, long j2) {
        Assert.assertEquals(str, j, j2);
    }

    public static void assertEquals(long j, long j2) {
        Assert.assertEquals(j, j2);
    }

    public static void assertEquals(String str, boolean z, boolean z2) {
        Assert.assertEquals(str, z, z2);
    }

    public static void assertEquals(boolean z, boolean z2) {
        Assert.assertEquals(z, z2);
    }

    public static void assertEquals(String str, byte b, byte b2) {
        Assert.assertEquals(str, b, b2);
    }

    public static void assertEquals(byte b, byte b2) {
        Assert.assertEquals(b, b2);
    }

    public static void assertEquals(String str, char c, char c2) {
        Assert.assertEquals(str, c, c2);
    }

    public static void assertEquals(char c, char c2) {
        Assert.assertEquals(c, c2);
    }

    public static void assertEquals(String str, short s, short s2) {
        Assert.assertEquals(str, s, s2);
    }

    public static void assertEquals(short s, short s2) {
        Assert.assertEquals(s, s2);
    }

    public static void assertEquals(String str, int i, int i2) {
        Assert.assertEquals(str, i, i2);
    }

    public static void assertEquals(int i, int i2) {
        Assert.assertEquals(i, i2);
    }

    public static void assertNotNull(Object obj) {
        Assert.assertNotNull(obj);
    }

    public static void assertNotNull(String str, Object obj) {
        Assert.assertNotNull(str, obj);
    }

    public static void assertNull(Object obj) {
        Assert.assertNull(obj);
    }

    public static void assertNull(String str, Object obj) {
        Assert.assertNull(str, obj);
    }

    public static void assertSame(String str, Object obj, Object obj2) {
        Assert.assertSame(str, obj, obj2);
    }

    public static void assertSame(Object obj, Object obj2) {
        Assert.assertSame(obj, obj2);
    }

    public static void assertNotSame(String str, Object obj, Object obj2) {
        Assert.assertNotSame(str, obj, obj2);
    }

    public static void assertNotSame(Object obj, Object obj2) {
        Assert.assertNotSame(obj, obj2);
    }

    public static void failSame(String str) {
        Assert.failSame(str);
    }

    public static void failNotSame(String str, Object obj, Object obj2) {
        Assert.failNotSame(str, obj, obj2);
    }

    public static void failNotEquals(String str, Object obj, Object obj2) {
        Assert.failNotEquals(str, obj, obj2);
    }

    public static String format(String str, Object obj, Object obj2) {
        return Assert.format(str, obj, obj2);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append("(");
        sb.append(getClass().getName());
        sb.append(")");
        return sb.toString();
    }

    public String getName() {
        return this.fName;
    }

    public void setName(String str) {
        this.fName = str;
    }
}
