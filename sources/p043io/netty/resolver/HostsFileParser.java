package p043io.netty.resolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;
import p043io.netty.util.NetUtil;
import p043io.netty.util.internal.ObjectUtil;
import p043io.netty.util.internal.PlatformDependent;
import p043io.netty.util.internal.logging.InternalLogger;
import p043io.netty.util.internal.logging.InternalLoggerFactory;

/* renamed from: io.netty.resolver.HostsFileParser */
public final class HostsFileParser {
    private static final Pattern WHITESPACES = Pattern.compile("[ \t]+");
    private static final String WINDOWS_DEFAULT_SYSTEM_ROOT = "C:\\Windows";
    private static final String WINDOWS_HOSTS_FILE_RELATIVE_PATH = "\\system32\\drivers\\etc\\hosts";
    private static final String X_PLATFORMS_HOSTS_FILE_PATH = "/etc/hosts";
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(HostsFileParser.class);

    private static File locateHostsFile() {
        if (!PlatformDependent.isWindows()) {
            return new File(X_PLATFORMS_HOSTS_FILE_PATH);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(System.getenv("SystemRoot"));
        sb.append(WINDOWS_HOSTS_FILE_RELATIVE_PATH);
        File file = new File(sb.toString());
        if (!file.exists()) {
            return new File("C:\\Windows\\system32\\drivers\\etc\\hosts");
        }
        return file;
    }

    public static HostsFileEntries parseSilently() {
        File locateHostsFile = locateHostsFile();
        try {
            return parse(locateHostsFile);
        } catch (IOException e) {
            InternalLogger internalLogger = logger;
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to load and parse hosts file at ");
            sb.append(locateHostsFile.getPath());
            internalLogger.warn(sb.toString(), (Throwable) e);
            return HostsFileEntries.EMPTY;
        }
    }

    public static HostsFileEntries parse() throws IOException {
        return parse(locateHostsFile());
    }

    public static HostsFileEntries parse(File file) throws IOException {
        ObjectUtil.checkNotNull(file, "file");
        if (!file.exists() || !file.isFile()) {
            return HostsFileEntries.EMPTY;
        }
        return parse((Reader) new BufferedReader(new FileReader(file)));
    }

    public static HostsFileEntries parse(Reader reader) throws IOException {
        String[] split;
        String str = "Failed to close a reader";
        ObjectUtil.checkNotNull(reader, "reader");
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                int indexOf = readLine.indexOf(35);
                if (indexOf != -1) {
                    readLine = readLine.substring(0, indexOf);
                }
                String trim = readLine.trim();
                if (!trim.isEmpty()) {
                    ArrayList arrayList = new ArrayList();
                    for (String str2 : WHITESPACES.split(trim)) {
                        if (!str2.isEmpty()) {
                            arrayList.add(str2);
                        }
                    }
                    if (arrayList.size() >= 2) {
                        byte[] createByteArrayFromIpAddressString = NetUtil.createByteArrayFromIpAddressString((String) arrayList.get(0));
                        if (createByteArrayFromIpAddressString != null) {
                            for (int i = 1; i < arrayList.size(); i++) {
                                String str3 = (String) arrayList.get(i);
                                String lowerCase = str3.toLowerCase(Locale.ENGLISH);
                                InetAddress byAddress = InetAddress.getByAddress(str3, createByteArrayFromIpAddressString);
                                if (byAddress instanceof Inet4Address) {
                                    Inet4Address inet4Address = (Inet4Address) hashMap.put(lowerCase, (Inet4Address) byAddress);
                                    if (inet4Address != null) {
                                        hashMap.put(lowerCase, inet4Address);
                                    }
                                } else {
                                    Inet6Address inet6Address = (Inet6Address) hashMap2.put(lowerCase, (Inet6Address) byAddress);
                                    if (inet6Address != null) {
                                        hashMap2.put(lowerCase, inet6Address);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            HostsFileEntries hostsFileEntries = (!hashMap.isEmpty() || !hashMap2.isEmpty()) ? new HostsFileEntries(hashMap, hashMap2) : HostsFileEntries.EMPTY;
            try {
            } catch (IOException e) {
                logger.warn(str, (Throwable) e);
            }
            return hostsFileEntries;
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e2) {
                logger.warn(str, (Throwable) e2);
            }
        }
    }

    private HostsFileParser() {
    }
}
