package p098id.zelory.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import p043io.reactivex.Flowable;

/* renamed from: id.zelory.compressor.Compressor */
public class Compressor {
    private CompressFormat compressFormat = CompressFormat.JPEG;
    private String destinationDirectoryPath;
    private int maxHeight = 816;
    private int maxWidth = 612;
    private int quality = 80;

    public Compressor(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getCacheDir().getPath());
        sb.append(File.separator);
        sb.append("images");
        this.destinationDirectoryPath = sb.toString();
    }

    public Compressor setMaxWidth(int i) {
        this.maxWidth = i;
        return this;
    }

    public Compressor setMaxHeight(int i) {
        this.maxHeight = i;
        return this;
    }

    public Compressor setCompressFormat(CompressFormat compressFormat2) {
        this.compressFormat = compressFormat2;
        return this;
    }

    public Compressor setQuality(int i) {
        this.quality = i;
        return this;
    }

    public Compressor setDestinationDirectoryPath(String str) {
        this.destinationDirectoryPath = str;
        return this;
    }

    public File compressToFile(File file) throws IOException {
        return compressToFile(file, file.getName());
    }

    public File compressToFile(File file, String str) throws IOException {
        int i = this.maxWidth;
        int i2 = this.maxHeight;
        CompressFormat compressFormat2 = this.compressFormat;
        int i3 = this.quality;
        StringBuilder sb = new StringBuilder();
        sb.append(this.destinationDirectoryPath);
        sb.append(File.separator);
        sb.append(str);
        return ImageUtil.compressImage(file, i, i2, compressFormat2, i3, sb.toString());
    }

    public Bitmap compressToBitmap(File file) throws IOException {
        return ImageUtil.decodeSampledBitmapFromFile(file, this.maxWidth, this.maxHeight);
    }

    public Flowable<File> compressToFileAsFlowable(File file) {
        return compressToFileAsFlowable(file, file.getName());
    }

    public Flowable<File> compressToFileAsFlowable(final File file, final String str) {
        return Flowable.defer(new Callable<Flowable<File>>() {
            public Flowable<File> call() {
                try {
                    return Flowable.just(Compressor.this.compressToFile(file, str));
                } catch (IOException e) {
                    return Flowable.error((Throwable) e);
                }
            }
        });
    }

    public Flowable<Bitmap> compressToBitmapAsFlowable(final File file) {
        return Flowable.defer(new Callable<Flowable<Bitmap>>() {
            public Flowable<Bitmap> call() {
                try {
                    return Flowable.just(Compressor.this.compressToBitmap(file));
                } catch (IOException e) {
                    return Flowable.error((Throwable) e);
                }
            }
        });
    }
}
