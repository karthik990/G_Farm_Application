package p098id.zelory.compressor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import java.io.File;
import java.io.IOException;

/* renamed from: id.zelory.compressor.ImageUtil */
class ImageUtil {
    private ImageUtil() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0031  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.io.File compressImage(java.io.File r2, int r3, int r4, android.graphics.Bitmap.CompressFormat r5, int r6, java.lang.String r7) throws java.io.IOException {
        /*
            java.io.File r0 = new java.io.File
            r0.<init>(r7)
            java.io.File r0 = r0.getParentFile()
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x0012
            r0.mkdirs()
        L_0x0012:
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ all -> 0x002e }
            r1.<init>(r7)     // Catch:{ all -> 0x002e }
            android.graphics.Bitmap r2 = decodeSampledBitmapFromFile(r2, r3, r4)     // Catch:{ all -> 0x002b }
            r2.compress(r5, r6, r1)     // Catch:{ all -> 0x002b }
            r1.flush()
            r1.close()
            java.io.File r2 = new java.io.File
            r2.<init>(r7)
            return r2
        L_0x002b:
            r2 = move-exception
            r0 = r1
            goto L_0x002f
        L_0x002e:
            r2 = move-exception
        L_0x002f:
            if (r0 == 0) goto L_0x0037
            r0.flush()
            r0.close()
        L_0x0037:
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: p098id.zelory.compressor.ImageUtil.compressImage(java.io.File, int, int, android.graphics.Bitmap$CompressFormat, int, java.lang.String):java.io.File");
    }

    static Bitmap decodeSampledBitmapFromFile(File file, int i, int i2) throws IOException {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        Bitmap decodeFile = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        int attributeInt = new ExifInterface(file.getAbsolutePath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        if (attributeInt == 6) {
            matrix.postRotate(90.0f);
        } else if (attributeInt == 3) {
            matrix.postRotate(180.0f);
        } else if (attributeInt == 8) {
            matrix.postRotate(270.0f);
        }
        return Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
    }

    private static int calculateInSampleSize(Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 >= i2 && i7 / i5 >= i) {
                i5 *= 2;
            }
        }
        return i5;
    }
}
