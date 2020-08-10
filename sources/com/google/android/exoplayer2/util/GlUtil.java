package com.google.android.exoplayer2.util;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.text.TextUtils;
import com.mobiroller.constants.Constants;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class GlUtil {
    private static final String TAG = "GlUtil";

    private GlUtil() {
    }

    public static void checkGlError() {
        while (true) {
            int glGetError = GLES20.glGetError();
            if (glGetError != 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("glError ");
                sb.append(GLU.gluErrorString(glGetError));
                Log.m1392e(TAG, sb.toString());
            } else {
                return;
            }
        }
    }

    public static int compileProgram(String[] strArr, String[] strArr2) {
        String str = Constants.NEW_LINE;
        return compileProgram(TextUtils.join(str, strArr), TextUtils.join(str, strArr2));
    }

    public static int compileProgram(String str, String str2) {
        int glCreateProgram = GLES20.glCreateProgram();
        checkGlError();
        addShader(35633, str, glCreateProgram);
        addShader(35632, str2, glCreateProgram);
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = {0};
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to link shader program: \n");
            sb.append(GLES20.glGetProgramInfoLog(glCreateProgram));
            throwGlError(sb.toString());
        }
        checkGlError();
        return glCreateProgram;
    }

    public static FloatBuffer createBuffer(float[] fArr) {
        return (FloatBuffer) createBuffer(fArr.length).put(fArr).flip();
    }

    public static FloatBuffer createBuffer(int i) {
        return ByteBuffer.allocateDirect(i * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public static int createExternalTexture() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, IntBuffer.wrap(iArr));
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameteri(36197, 10241, 9729);
        GLES20.glTexParameteri(36197, 10240, 9729);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        checkGlError();
        return iArr[0];
    }

    private static void addShader(int i, String str, int i2) {
        int glCreateShader = GLES20.glCreateShader(i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = {0};
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 1) {
            StringBuilder sb = new StringBuilder();
            sb.append(GLES20.glGetShaderInfoLog(glCreateShader));
            sb.append(", source: ");
            sb.append(str);
            throwGlError(sb.toString());
        }
        GLES20.glAttachShader(i2, glCreateShader);
        GLES20.glDeleteShader(glCreateShader);
        checkGlError();
    }

    private static void throwGlError(String str) {
        Log.m1392e(TAG, str);
    }
}
