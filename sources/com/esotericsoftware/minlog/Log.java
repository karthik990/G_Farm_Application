package com.esotericsoftware.minlog;

import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public class Log {
    public static boolean DEBUG = (level <= 2);
    public static boolean ERROR = (level <= 5);
    public static boolean INFO = (level <= 3);
    public static final int LEVEL_DEBUG = 2;
    public static final int LEVEL_ERROR = 5;
    public static final int LEVEL_INFO = 3;
    public static final int LEVEL_NONE = 6;
    public static final int LEVEL_TRACE = 1;
    public static final int LEVEL_WARN = 4;
    public static boolean TRACE = false;
    public static boolean WARN = (level <= 4);
    private static int level = 3;
    private static Logger logger = new Logger();

    public static class Logger {
        private long firstLogTime = new Date().getTime();

        public void log(int i, String str, String str2, Throwable th) {
            StringBuilder sb = new StringBuilder(256);
            long time = new Date().getTime() - this.firstLogTime;
            long j = time / DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS;
            long j2 = (time / 1000) % 60;
            if (j <= 9) {
                sb.append('0');
            }
            sb.append(j);
            sb.append(':');
            if (j2 <= 9) {
                sb.append('0');
            }
            sb.append(j2);
            if (i == 1) {
                sb.append(" TRACE: ");
            } else if (i == 2) {
                sb.append(" DEBUG: ");
            } else if (i == 3) {
                sb.append("  INFO: ");
            } else if (i == 4) {
                sb.append("  WARN: ");
            } else if (i == 5) {
                sb.append(" ERROR: ");
            }
            if (str != null) {
                sb.append('[');
                sb.append(str);
                sb.append("] ");
            }
            sb.append(str2);
            if (th != null) {
                StringWriter stringWriter = new StringWriter(256);
                th.printStackTrace(new PrintWriter(stringWriter));
                sb.append(10);
                sb.append(stringWriter.toString().trim());
            }
            print(sb.toString());
        }

        /* access modifiers changed from: protected */
        public void print(String str) {
            System.out.println(str);
        }
    }

    static {
        boolean z = false;
        if (level <= 1) {
            z = true;
        }
        TRACE = z;
    }

    public static void set(int i) {
        level = i;
        boolean z = false;
        ERROR = i <= 5;
        WARN = i <= 4;
        INFO = i <= 3;
        DEBUG = i <= 2;
        if (i <= 1) {
            z = true;
        }
        TRACE = z;
    }

    public static void NONE() {
        set(6);
    }

    public static void ERROR() {
        set(5);
    }

    public static void WARN() {
        set(4);
    }

    public static void INFO() {
        set(3);
    }

    public static void DEBUG() {
        set(2);
    }

    public static void TRACE() {
        set(1);
    }

    public static void setLogger(Logger logger2) {
        logger = logger2;
    }

    public static void error(String str, Throwable th) {
        if (ERROR) {
            logger.log(5, null, str, th);
        }
    }

    public static void error(String str, String str2, Throwable th) {
        if (ERROR) {
            logger.log(5, str, str2, th);
        }
    }

    public static void error(String str) {
        if (ERROR) {
            logger.log(5, null, str, null);
        }
    }

    public static void error(String str, String str2) {
        if (ERROR) {
            logger.log(5, str, str2, null);
        }
    }

    public static void warn(String str, Throwable th) {
        if (WARN) {
            logger.log(4, null, str, th);
        }
    }

    public static void warn(String str, String str2, Throwable th) {
        if (WARN) {
            logger.log(4, str, str2, th);
        }
    }

    public static void warn(String str) {
        if (WARN) {
            logger.log(4, null, str, null);
        }
    }

    public static void warn(String str, String str2) {
        if (WARN) {
            logger.log(4, str, str2, null);
        }
    }

    public static void info(String str, Throwable th) {
        if (INFO) {
            logger.log(3, null, str, th);
        }
    }

    public static void info(String str, String str2, Throwable th) {
        if (INFO) {
            logger.log(3, str, str2, th);
        }
    }

    public static void info(String str) {
        if (INFO) {
            logger.log(3, null, str, null);
        }
    }

    public static void info(String str, String str2) {
        if (INFO) {
            logger.log(3, str, str2, null);
        }
    }

    public static void debug(String str, Throwable th) {
        if (DEBUG) {
            logger.log(2, null, str, th);
        }
    }

    public static void debug(String str, String str2, Throwable th) {
        if (DEBUG) {
            logger.log(2, str, str2, th);
        }
    }

    public static void debug(String str) {
        if (DEBUG) {
            logger.log(2, null, str, null);
        }
    }

    public static void debug(String str, String str2) {
        if (DEBUG) {
            logger.log(2, str, str2, null);
        }
    }

    public static void trace(String str, Throwable th) {
        if (TRACE) {
            logger.log(1, null, str, th);
        }
    }

    public static void trace(String str, String str2, Throwable th) {
        if (TRACE) {
            logger.log(1, str, str2, th);
        }
    }

    public static void trace(String str) {
        if (TRACE) {
            logger.log(1, null, str, null);
        }
    }

    public static void trace(String str, String str2) {
        if (TRACE) {
            logger.log(1, str, str2, null);
        }
    }

    private Log() {
    }
}
