package com.flurry.sdk;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/* renamed from: com.flurry.sdk.js */
public final class C1933js {
    /* renamed from: a */
    public static boolean m1205a(C1932jr jrVar, C1932jr jrVar2) {
        if (!C1778fg.m948a(jrVar, jrVar2)) {
            return false;
        }
        if (!C1778fg.m949a(jrVar.f1387a, jrVar.f1388b, jrVar2.f1387a, jrVar2.f1388b)) {
            return false;
        }
        boolean c = m1208c(jrVar, jrVar2);
        if (c) {
            c = m1204a(jrVar);
        }
        return c;
    }

    /* renamed from: a */
    public static boolean m1204a(C1932jr jrVar) {
        return new File(jrVar.f1387a, jrVar.f1388b).delete();
    }

    /* renamed from: c */
    private static boolean m1208c(C1932jr jrVar, C1932jr jrVar2) {
        FileChannel fileChannel;
        FileChannel channel;
        FileChannel fileChannel2 = null;
        try {
            File file = new File(jrVar.f1387a, jrVar.f1388b);
            File file2 = new File(jrVar2.f1387a, jrVar2.f1388b);
            file2.getParentFile().mkdirs();
            file2.delete();
            FileChannel channel2 = new FileInputStream(file).getChannel();
            try {
                channel = new FileOutputStream(file2).getChannel();
            } catch (Exception e) {
                e = e;
                fileChannel2 = channel2;
                fileChannel = null;
                String str = "FileProcessor";
                try {
                    StringBuilder sb = new StringBuilder("Copy file failed. ");
                    sb.append(e.getMessage());
                    C1685cy.m754a(6, str, sb.toString());
                    C1734dz.m871a((Closeable) fileChannel2);
                    C1734dz.m871a((Closeable) fileChannel);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    C1734dz.m871a((Closeable) fileChannel2);
                    C1734dz.m871a((Closeable) fileChannel);
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                fileChannel2 = channel2;
                fileChannel = null;
                C1734dz.m871a((Closeable) fileChannel2);
                C1734dz.m871a((Closeable) fileChannel);
                throw th;
            }
            try {
                channel.transferFrom(channel2, 0, channel2.size());
                C1734dz.m871a((Closeable) channel2);
                C1734dz.m871a((Closeable) channel);
                return true;
            } catch (Exception e2) {
                FileChannel fileChannel3 = channel2;
                fileChannel = channel;
                e = e2;
                fileChannel2 = fileChannel3;
                String str2 = "FileProcessor";
                StringBuilder sb2 = new StringBuilder("Copy file failed. ");
                sb2.append(e.getMessage());
                C1685cy.m754a(6, str2, sb2.toString());
                C1734dz.m871a((Closeable) fileChannel2);
                C1734dz.m871a((Closeable) fileChannel);
                return false;
            } catch (Throwable th3) {
                FileChannel fileChannel4 = channel2;
                fileChannel = channel;
                th = th3;
                fileChannel2 = fileChannel4;
                C1734dz.m871a((Closeable) fileChannel2);
                C1734dz.m871a((Closeable) fileChannel);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileChannel = null;
            String str22 = "FileProcessor";
            StringBuilder sb22 = new StringBuilder("Copy file failed. ");
            sb22.append(e.getMessage());
            C1685cy.m754a(6, str22, sb22.toString());
            C1734dz.m871a((Closeable) fileChannel2);
            C1734dz.m871a((Closeable) fileChannel);
            return false;
        } catch (Throwable th4) {
            th = th4;
            fileChannel = null;
            C1734dz.m871a((Closeable) fileChannel2);
            C1734dz.m871a((Closeable) fileChannel);
            throw th;
        }
    }

    /* renamed from: a */
    public static boolean m1206a(File file, File file2) {
        FileChannel fileChannel;
        FileChannel fileChannel2 = null;
        try {
            file2.getParentFile().mkdirs();
            file2.delete();
            file2.createNewFile();
            FileChannel channel = new FileInputStream(file).getChannel();
            try {
                fileChannel = new FileOutputStream(file2).getChannel();
            } catch (Exception e) {
                fileChannel2 = channel;
                e = e;
                fileChannel = null;
                String str = "FileProcessor";
                try {
                    StringBuilder sb = new StringBuilder("Copy file failed. ");
                    sb.append(e.getMessage());
                    C1685cy.m754a(6, str, sb.toString());
                    C1734dz.m871a((Closeable) fileChannel2);
                    C1734dz.m871a((Closeable) fileChannel);
                    return false;
                } catch (Throwable th) {
                    th = th;
                    C1734dz.m871a((Closeable) fileChannel2);
                    C1734dz.m871a((Closeable) fileChannel);
                    throw th;
                }
            } catch (Throwable th2) {
                fileChannel2 = channel;
                th = th2;
                fileChannel = null;
                C1734dz.m871a((Closeable) fileChannel2);
                C1734dz.m871a((Closeable) fileChannel);
                throw th;
            }
            try {
                fileChannel.transferFrom(channel, 0, channel.size());
                C1734dz.m871a((Closeable) channel);
                C1734dz.m871a((Closeable) fileChannel);
                return true;
            } catch (Exception e2) {
                Exception exc = e2;
                fileChannel2 = channel;
                e = exc;
                String str2 = "FileProcessor";
                StringBuilder sb2 = new StringBuilder("Copy file failed. ");
                sb2.append(e.getMessage());
                C1685cy.m754a(6, str2, sb2.toString());
                C1734dz.m871a((Closeable) fileChannel2);
                C1734dz.m871a((Closeable) fileChannel);
                return false;
            } catch (Throwable th3) {
                Throwable th4 = th3;
                fileChannel2 = channel;
                th = th4;
                C1734dz.m871a((Closeable) fileChannel2);
                C1734dz.m871a((Closeable) fileChannel);
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            fileChannel = null;
            String str22 = "FileProcessor";
            StringBuilder sb22 = new StringBuilder("Copy file failed. ");
            sb22.append(e.getMessage());
            C1685cy.m754a(6, str22, sb22.toString());
            C1734dz.m871a((Closeable) fileChannel2);
            C1734dz.m871a((Closeable) fileChannel);
            return false;
        } catch (Throwable th5) {
            th = th5;
            fileChannel = null;
            C1734dz.m871a((Closeable) fileChannel2);
            C1734dz.m871a((Closeable) fileChannel);
            throw th;
        }
    }

    /* renamed from: b */
    public static boolean m1207b(C1932jr jrVar, C1932jr jrVar2) {
        FileChannel fileChannel;
        FileChannel fileChannel2;
        FileChannel fileChannel3 = null;
        try {
            File file = new File(jrVar.f1387a, jrVar.f1388b);
            fileChannel = new FileInputStream(new File(jrVar2.f1387a, jrVar2.f1388b)).getChannel();
            try {
                fileChannel2 = new FileOutputStream(file, true).getChannel();
                try {
                    fileChannel2.transferFrom(fileChannel, fileChannel2.size(), fileChannel.size());
                    C1734dz.m871a((Closeable) fileChannel);
                    C1734dz.m871a((Closeable) fileChannel2);
                    return true;
                } catch (Exception unused) {
                    fileChannel3 = fileChannel2;
                    C1734dz.m871a((Closeable) fileChannel);
                    C1734dz.m871a((Closeable) fileChannel3);
                    return false;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    fileChannel3 = fileChannel;
                    th = th2;
                    C1734dz.m871a((Closeable) fileChannel3);
                    C1734dz.m871a((Closeable) fileChannel2);
                    throw th;
                }
            } catch (Exception unused2) {
                C1734dz.m871a((Closeable) fileChannel);
                C1734dz.m871a((Closeable) fileChannel3);
                return false;
            } catch (Throwable th3) {
                fileChannel3 = fileChannel;
                th = th3;
                fileChannel2 = null;
                C1734dz.m871a((Closeable) fileChannel3);
                C1734dz.m871a((Closeable) fileChannel2);
                throw th;
            }
        } catch (Exception unused3) {
            fileChannel = null;
            C1734dz.m871a((Closeable) fileChannel);
            C1734dz.m871a((Closeable) fileChannel3);
            return false;
        } catch (Throwable th4) {
            th = th4;
            fileChannel2 = null;
            C1734dz.m871a((Closeable) fileChannel3);
            C1734dz.m871a((Closeable) fileChannel2);
            throw th;
        }
    }
}
