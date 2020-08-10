package org.junit.internal;

import java.io.PrintStream;

public class RealSystem implements JUnitSystem {
    @Deprecated
    public void exit(int i) {
        System.exit(i);
    }

    public PrintStream out() {
        return System.out;
    }
}
