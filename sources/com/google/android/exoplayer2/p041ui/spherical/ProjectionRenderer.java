package com.google.android.exoplayer2.p041ui.spherical;

import android.opengl.GLES20;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.video.spherical.Projection;
import com.google.android.exoplayer2.video.spherical.Projection.Mesh;
import com.google.android.exoplayer2.video.spherical.Projection.SubMesh;
import java.nio.FloatBuffer;

/* renamed from: com.google.android.exoplayer2.ui.spherical.ProjectionRenderer */
final class ProjectionRenderer {
    private static final String[] FRAGMENT_SHADER_CODE;
    private static final float[] TEX_MATRIX_BOTTOM = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_LEFT = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_RIGHT = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.5f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_TOP = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 0.5f, 1.0f};
    private static final float[] TEX_MATRIX_WHOLE = {1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final String[] VERTEX_SHADER_CODE;
    private MeshData leftMeshData;
    private int mvpMatrixHandle;
    private int positionHandle;
    private int program;
    private MeshData rightMeshData;
    private int stereoMode;
    private int texCoordsHandle;
    private int textureHandle;
    private int uTexMatrixHandle;

    /* renamed from: com.google.android.exoplayer2.ui.spherical.ProjectionRenderer$MeshData */
    private static class MeshData {
        /* access modifiers changed from: private */
        public final int drawMode;
        /* access modifiers changed from: private */
        public final FloatBuffer textureBuffer;
        /* access modifiers changed from: private */
        public final FloatBuffer vertexBuffer;
        /* access modifiers changed from: private */
        public final int vertexCount;

        public MeshData(SubMesh subMesh) {
            this.vertexCount = subMesh.getVertexCount();
            this.vertexBuffer = GlUtil.createBuffer(subMesh.vertices);
            this.textureBuffer = GlUtil.createBuffer(subMesh.textureCoords);
            int i = subMesh.mode;
            if (i == 1) {
                this.drawMode = 5;
            } else if (i != 2) {
                this.drawMode = 4;
            } else {
                this.drawMode = 6;
            }
        }
    }

    ProjectionRenderer() {
    }

    public static boolean isSupported(Projection projection) {
        Mesh mesh = projection.leftMesh;
        Mesh mesh2 = projection.rightMesh;
        if (mesh.getSubMeshCount() == 1 && mesh.getSubMesh(0).textureId == 0 && mesh2.getSubMeshCount() == 1 && mesh2.getSubMesh(0).textureId == 0) {
            return true;
        }
        return false;
    }

    static {
        String str = "varying vec2 vTexCoords;";
        String str2 = "void main() {";
        String str3 = "}";
        VERTEX_SHADER_CODE = new String[]{"uniform mat4 uMvpMatrix;", "uniform mat3 uTexMatrix;", "attribute vec4 aPosition;", "attribute vec2 aTexCoords;", str, str2, "  gl_Position = uMvpMatrix * aPosition;", "  vTexCoords = (uTexMatrix * vec3(aTexCoords, 1)).xy;", str3};
        FRAGMENT_SHADER_CODE = new String[]{"#extension GL_OES_EGL_image_external : require", "precision mediump float;", "uniform samplerExternalOES uTexture;", str, str2, "  gl_FragColor = texture2D(uTexture, vTexCoords);", str3};
    }

    public void setProjection(Projection projection) {
        MeshData meshData;
        if (isSupported(projection)) {
            this.stereoMode = projection.stereoMode;
            this.leftMeshData = new MeshData(projection.leftMesh.getSubMesh(0));
            if (projection.singleMesh) {
                meshData = this.leftMeshData;
            } else {
                meshData = new MeshData(projection.rightMesh.getSubMesh(0));
            }
            this.rightMeshData = meshData;
        }
    }

    /* access modifiers changed from: 0000 */
    public void init() {
        this.program = GlUtil.compileProgram(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
        this.mvpMatrixHandle = GLES20.glGetUniformLocation(this.program, "uMvpMatrix");
        this.uTexMatrixHandle = GLES20.glGetUniformLocation(this.program, "uTexMatrix");
        this.positionHandle = GLES20.glGetAttribLocation(this.program, "aPosition");
        this.texCoordsHandle = GLES20.glGetAttribLocation(this.program, "aTexCoords");
        this.textureHandle = GLES20.glGetUniformLocation(this.program, "uTexture");
    }

    /* access modifiers changed from: 0000 */
    public void draw(int i, float[] fArr, boolean z) {
        MeshData meshData = z ? this.rightMeshData : this.leftMeshData;
        if (meshData != null) {
            GLES20.glUseProgram(this.program);
            GlUtil.checkGlError();
            GLES20.glEnableVertexAttribArray(this.positionHandle);
            GLES20.glEnableVertexAttribArray(this.texCoordsHandle);
            GlUtil.checkGlError();
            int i2 = this.stereoMode;
            float[] fArr2 = i2 == 1 ? z ? TEX_MATRIX_BOTTOM : TEX_MATRIX_TOP : i2 == 2 ? z ? TEX_MATRIX_RIGHT : TEX_MATRIX_LEFT : TEX_MATRIX_WHOLE;
            GLES20.glUniformMatrix3fv(this.uTexMatrixHandle, 1, false, fArr2, 0);
            GLES20.glUniformMatrix4fv(this.mvpMatrixHandle, 1, false, fArr, 0);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, i);
            GLES20.glUniform1i(this.textureHandle, 0);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(this.positionHandle, 3, 5126, false, 12, meshData.vertexBuffer);
            GlUtil.checkGlError();
            GLES20.glVertexAttribPointer(this.texCoordsHandle, 2, 5126, false, 8, meshData.textureBuffer);
            GlUtil.checkGlError();
            GLES20.glDrawArrays(meshData.drawMode, 0, meshData.vertexCount);
            GlUtil.checkGlError();
            GLES20.glDisableVertexAttribArray(this.positionHandle);
            GLES20.glDisableVertexAttribArray(this.texCoordsHandle);
        }
    }

    /* access modifiers changed from: 0000 */
    public void shutdown() {
        int i = this.program;
        if (i != 0) {
            GLES20.glDeleteProgram(i);
        }
    }
}
