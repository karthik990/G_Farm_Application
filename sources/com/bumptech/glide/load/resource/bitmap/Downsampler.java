package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.ColorSpace;
import android.graphics.ColorSpace.Named;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParser.ImageType;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.PreferredColorSpace;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy.SampleSizeRounding;
import com.bumptech.glide.load.resource.bitmap.ImageReader.InputStreamImageReader;
import com.bumptech.glide.load.resource.bitmap.ImageReader.ParcelFileDescriptorImageReader;
import com.bumptech.glide.util.LogTime;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public final class Downsampler {
    public static final Option<Boolean> ALLOW_HARDWARE_CONFIG;
    public static final Option<DecodeFormat> DECODE_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeFormat", DecodeFormat.DEFAULT);
    @Deprecated
    public static final Option<DownsampleStrategy> DOWNSAMPLE_STRATEGY = DownsampleStrategy.OPTION;
    private static final DecodeCallbacks EMPTY_CALLBACKS = new DecodeCallbacks() {
        public void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) {
        }

        public void onObtainBounds() {
        }
    };
    public static final Option<Boolean> FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS;
    private static final String ICO_MIME_TYPE = "image/x-ico";
    private static final Set<String> NO_DOWNSAMPLE_PRE_N_MIME_TYPES = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{WBMP_MIME_TYPE, ICO_MIME_TYPE})));
    private static final Queue<Options> OPTIONS_QUEUE = Util.createQueue(0);
    public static final Option<PreferredColorSpace> PREFERRED_COLOR_SPACE = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.PreferredColorSpace", PreferredColorSpace.SRGB);
    static final String TAG = "Downsampler";
    private static final Set<ImageType> TYPES_THAT_USE_POOL_PRE_KITKAT = Collections.unmodifiableSet(EnumSet.of(ImageType.JPEG, ImageType.PNG_A, ImageType.PNG));
    private static final String WBMP_MIME_TYPE = "image/vnd.wap.wbmp";
    private final BitmapPool bitmapPool;
    private final ArrayPool byteArrayPool;
    private final DisplayMetrics displayMetrics;
    private final HardwareConfigState hardwareConfigState = HardwareConfigState.getInstance();
    private final List<ImageHeaderParser> parsers;

    public interface DecodeCallbacks {
        void onDecodeComplete(BitmapPool bitmapPool, Bitmap bitmap) throws IOException;

        void onObtainBounds();
    }

    private static boolean isRotationRequired(int i) {
        return i == 90 || i == 270;
    }

    private static int round(double d) {
        return (int) (d + 0.5d);
    }

    public boolean handles(InputStream inputStream) {
        return true;
    }

    public boolean handles(ByteBuffer byteBuffer) {
        return true;
    }

    static {
        Boolean valueOf = Boolean.valueOf(false);
        FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.FixBitmapSize", valueOf);
        ALLOW_HARDWARE_CONFIG = Option.memory("com.bumptech.glide.load.resource.bitmap.Downsampler.AllowHardwareDecode", valueOf);
    }

    public Downsampler(List<ImageHeaderParser> list, DisplayMetrics displayMetrics2, BitmapPool bitmapPool2, ArrayPool arrayPool) {
        this.parsers = list;
        this.displayMetrics = (DisplayMetrics) Preconditions.checkNotNull(displayMetrics2);
        this.bitmapPool = (BitmapPool) Preconditions.checkNotNull(bitmapPool2);
        this.byteArrayPool = (ArrayPool) Preconditions.checkNotNull(arrayPool);
    }

    public boolean handles(ParcelFileDescriptor parcelFileDescriptor) {
        return ParcelFileDescriptorRewinder.isSupported();
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, com.bumptech.glide.load.Options options) throws IOException {
        return decode(inputStream, i, i2, options, EMPTY_CALLBACKS);
    }

    public Resource<Bitmap> decode(InputStream inputStream, int i, int i2, com.bumptech.glide.load.Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        return decode((ImageReader) new InputStreamImageReader(inputStream, this.parsers, this.byteArrayPool), i, i2, options, decodeCallbacks);
    }

    public Resource<Bitmap> decode(ParcelFileDescriptor parcelFileDescriptor, int i, int i2, com.bumptech.glide.load.Options options) throws IOException {
        return decode((ImageReader) new ParcelFileDescriptorImageReader(parcelFileDescriptor, this.parsers, this.byteArrayPool), i, i2, options, EMPTY_CALLBACKS);
    }

    private Resource<Bitmap> decode(ImageReader imageReader, int i, int i2, com.bumptech.glide.load.Options options, DecodeCallbacks decodeCallbacks) throws IOException {
        com.bumptech.glide.load.Options options2 = options;
        byte[] bArr = (byte[]) this.byteArrayPool.get(65536, byte[].class);
        Options defaultOptions = getDefaultOptions();
        defaultOptions.inTempStorage = bArr;
        try {
            return BitmapResource.obtain(decodeFromWrappedStreams(imageReader, defaultOptions, (DownsampleStrategy) options2.get(DownsampleStrategy.OPTION), (DecodeFormat) options2.get(DECODE_FORMAT), (PreferredColorSpace) options2.get(PREFERRED_COLOR_SPACE), options2.get(ALLOW_HARDWARE_CONFIG) != null && ((Boolean) options2.get(ALLOW_HARDWARE_CONFIG)).booleanValue(), i, i2, ((Boolean) options2.get(FIX_BITMAP_SIZE_TO_REQUESTED_DIMENSIONS)).booleanValue(), decodeCallbacks), this.bitmapPool);
        } finally {
            releaseOptions(defaultOptions);
            this.byteArrayPool.put(bArr);
        }
    }

    private Bitmap decodeFromWrappedStreams(ImageReader imageReader, Options options, DownsampleStrategy downsampleStrategy, DecodeFormat decodeFormat, PreferredColorSpace preferredColorSpace, boolean z, int i, int i2, boolean z2, DecodeCallbacks decodeCallbacks) throws IOException {
        int i3;
        int i4;
        Downsampler downsampler;
        int i5;
        int i6;
        int i7;
        ImageReader imageReader2 = imageReader;
        Options options2 = options;
        DecodeCallbacks decodeCallbacks2 = decodeCallbacks;
        long logTime = LogTime.getLogTime();
        int[] dimensions = getDimensions(imageReader2, options2, decodeCallbacks2, this.bitmapPool);
        boolean z3 = false;
        int i8 = dimensions[0];
        int i9 = dimensions[1];
        String str = options2.outMimeType;
        boolean z4 = (i8 == -1 || i9 == -1) ? false : z;
        int imageOrientation = imageReader.getImageOrientation();
        int exifOrientationDegrees = TransformationUtils.getExifOrientationDegrees(imageOrientation);
        boolean isExifOrientationRequired = TransformationUtils.isExifOrientationRequired(imageOrientation);
        int i10 = i;
        if (i10 == Integer.MIN_VALUE) {
            i4 = i2;
            i3 = isRotationRequired(exifOrientationDegrees) ? i9 : i8;
        } else {
            i4 = i2;
            i3 = i10;
        }
        int i11 = i4 == Integer.MIN_VALUE ? isRotationRequired(exifOrientationDegrees) ? i8 : i9 : i4;
        ImageType imageType = imageReader.getImageType();
        BitmapPool bitmapPool2 = this.bitmapPool;
        ImageType imageType2 = imageType;
        calculateScaling(imageType, imageReader, decodeCallbacks, bitmapPool2, downsampleStrategy, exifOrientationDegrees, i8, i9, i3, i11, options);
        int i12 = imageOrientation;
        String str2 = str;
        int i13 = i9;
        int i14 = i8;
        DecodeCallbacks decodeCallbacks3 = decodeCallbacks2;
        Options options3 = options2;
        calculateConfig(imageReader, decodeFormat, z4, isExifOrientationRequired, options, i3, i11);
        boolean z5 = VERSION.SDK_INT >= 19;
        int i15 = options3.inSampleSize;
        String str3 = TAG;
        if (i15 == 1 || z5) {
            downsampler = this;
            if (downsampler.shouldUsePool(imageType2)) {
                if (i14 < 0 || i13 < 0 || !z2 || !z5) {
                    float f = isScaling(options) ? ((float) options3.inTargetDensity) / ((float) options3.inDensity) : 1.0f;
                    int i16 = options3.inSampleSize;
                    float f2 = (float) i16;
                    int ceil = (int) Math.ceil((double) (((float) i14) / f2));
                    int ceil2 = (int) Math.ceil((double) (((float) i13) / f2));
                    i7 = Math.round(((float) ceil) * f);
                    i6 = Math.round(((float) ceil2) * f);
                    if (Log.isLoggable(str3, 2)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Calculated target [");
                        sb.append(i7);
                        String str4 = "x";
                        sb.append(str4);
                        sb.append(i6);
                        sb.append("] for source [");
                        sb.append(i14);
                        sb.append(str4);
                        sb.append(i13);
                        sb.append("], sampleSize: ");
                        sb.append(i16);
                        sb.append(", targetDensity: ");
                        sb.append(options3.inTargetDensity);
                        sb.append(", density: ");
                        sb.append(options3.inDensity);
                        sb.append(", density multiplier: ");
                        sb.append(f);
                        Log.v(str3, sb.toString());
                    }
                } else {
                    i7 = i3;
                    i6 = i11;
                }
                if (i7 > 0 && i6 > 0) {
                    setInBitmap(options3, downsampler.bitmapPool, i7, i6);
                }
            }
        } else {
            downsampler = this;
        }
        if (VERSION.SDK_INT >= 28) {
            if (preferredColorSpace == PreferredColorSpace.DISPLAY_P3 && options3.outColorSpace != null && options3.outColorSpace.isWideGamut()) {
                z3 = true;
            }
            options3.inPreferredColorSpace = ColorSpace.get(z3 ? Named.DISPLAY_P3 : Named.SRGB);
        } else if (VERSION.SDK_INT >= 26) {
            options3.inPreferredColorSpace = ColorSpace.get(Named.SRGB);
        }
        Bitmap decodeStream = decodeStream(imageReader, options3, decodeCallbacks3, downsampler.bitmapPool);
        decodeCallbacks3.onDecodeComplete(downsampler.bitmapPool, decodeStream);
        if (Log.isLoggable(str3, 2)) {
            i5 = i12;
            logDecode(i14, i13, str2, options, decodeStream, i, i2, logTime);
        } else {
            i5 = i12;
        }
        Bitmap bitmap = null;
        if (decodeStream != null) {
            decodeStream.setDensity(downsampler.displayMetrics.densityDpi);
            bitmap = TransformationUtils.rotateImageExif(downsampler.bitmapPool, decodeStream, i5);
            if (!decodeStream.equals(bitmap)) {
                downsampler.bitmapPool.put(decodeStream);
            }
        }
        return bitmap;
    }

    private static void calculateScaling(ImageType imageType, ImageReader imageReader, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool2, DownsampleStrategy downsampleStrategy, int i, int i2, int i3, int i4, int i5, Options options) throws IOException {
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        double d;
        ImageType imageType2 = imageType;
        DownsampleStrategy downsampleStrategy2 = downsampleStrategy;
        int i12 = i2;
        int i13 = i3;
        int i14 = i4;
        int i15 = i5;
        Options options2 = options;
        String str = "]";
        String str2 = TAG;
        String str3 = "x";
        if (i12 <= 0 || i13 <= 0) {
            String str4 = str3;
            if (Log.isLoggable(str2, 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to determine dimensions for: ");
                sb.append(imageType2);
                sb.append(" with target [");
                sb.append(i14);
                sb.append(str4);
                sb.append(i15);
                sb.append(str);
                Log.d(str2, sb.toString());
            }
            return;
        }
        if (isRotationRequired(i)) {
            i6 = i12;
            i7 = i13;
        } else {
            i7 = i12;
            i6 = i13;
        }
        float scaleFactor = downsampleStrategy2.getScaleFactor(i7, i6, i14, i15);
        if (scaleFactor > 0.0f) {
            SampleSizeRounding sampleSizeRounding = downsampleStrategy2.getSampleSizeRounding(i7, i6, i14, i15);
            if (sampleSizeRounding != null) {
                float f = (float) i7;
                float f2 = (float) i6;
                int round = i7 / round((double) (scaleFactor * f));
                int round2 = i6 / round((double) (scaleFactor * f2));
                if (sampleSizeRounding == SampleSizeRounding.MEMORY) {
                    i8 = Math.max(round, round2);
                } else {
                    i8 = Math.min(round, round2);
                }
                String str5 = str3;
                if (VERSION.SDK_INT > 23 || !NO_DOWNSAMPLE_PRE_N_MIME_TYPES.contains(options2.outMimeType)) {
                    i9 = Math.max(1, Integer.highestOneBit(i8));
                    if (sampleSizeRounding == SampleSizeRounding.MEMORY && ((float) i9) < 1.0f / scaleFactor) {
                        i9 <<= 1;
                    }
                } else {
                    i9 = 1;
                }
                options2.inSampleSize = i9;
                if (imageType2 == ImageType.JPEG) {
                    float min = (float) Math.min(i9, 8);
                    i10 = (int) Math.ceil((double) (f / min));
                    i11 = (int) Math.ceil((double) (f2 / min));
                    int i16 = i9 / 8;
                    if (i16 > 0) {
                        i10 /= i16;
                        i11 /= i16;
                    }
                } else {
                    if (imageType2 == ImageType.PNG || imageType2 == ImageType.PNG_A) {
                        float f3 = (float) i9;
                        i10 = (int) Math.floor((double) (f / f3));
                        d = Math.floor((double) (f2 / f3));
                    } else if (imageType2 == ImageType.WEBP || imageType2 == ImageType.WEBP_A) {
                        if (VERSION.SDK_INT >= 24) {
                            float f4 = (float) i9;
                            i10 = Math.round(f / f4);
                            i11 = Math.round(f2 / f4);
                        } else {
                            float f5 = (float) i9;
                            i10 = (int) Math.floor((double) (f / f5));
                            d = Math.floor((double) (f2 / f5));
                        }
                    } else if (i7 % i9 == 0 && i6 % i9 == 0) {
                        i10 = i7 / i9;
                        i11 = i6 / i9;
                    } else {
                        int[] dimensions = getDimensions(imageReader, options2, decodeCallbacks, bitmapPool2);
                        i10 = dimensions[0];
                        i11 = dimensions[1];
                    }
                    i11 = (int) d;
                }
                double scaleFactor2 = (double) downsampleStrategy2.getScaleFactor(i10, i11, i14, i15);
                if (VERSION.SDK_INT >= 19) {
                    options2.inTargetDensity = adjustTargetDensityForError(scaleFactor2);
                    options2.inDensity = getDensityMultiplier(scaleFactor2);
                }
                if (isScaling(options)) {
                    options2.inScaled = true;
                } else {
                    options2.inTargetDensity = 0;
                    options2.inDensity = 0;
                }
                if (Log.isLoggable(str2, 2)) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Calculate scaling, source: [");
                    sb2.append(i2);
                    String str6 = str5;
                    sb2.append(str6);
                    sb2.append(i3);
                    sb2.append("], degreesToRotate: ");
                    sb2.append(i);
                    sb2.append(", target: [");
                    sb2.append(i14);
                    sb2.append(str6);
                    sb2.append(i15);
                    sb2.append("], power of two scaled: [");
                    sb2.append(i10);
                    sb2.append(str6);
                    sb2.append(i11);
                    sb2.append("], exact scale factor: ");
                    sb2.append(scaleFactor);
                    sb2.append(", power of 2 sample size: ");
                    sb2.append(i9);
                    sb2.append(", adjusted scale factor: ");
                    sb2.append(scaleFactor2);
                    sb2.append(", target density: ");
                    sb2.append(options2.inTargetDensity);
                    sb2.append(", density: ");
                    sb2.append(options2.inDensity);
                    Log.v(str2, sb2.toString());
                }
                return;
            }
            throw new IllegalArgumentException("Cannot round with null rounding");
        }
        int i17 = i13;
        String str7 = str3;
        int i18 = i12;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Cannot scale with factor: ");
        sb3.append(scaleFactor);
        sb3.append(" from: ");
        sb3.append(downsampleStrategy2);
        sb3.append(", source: [");
        sb3.append(i18);
        sb3.append(str7);
        sb3.append(i17);
        sb3.append("], target: [");
        sb3.append(i14);
        sb3.append(str7);
        sb3.append(i15);
        sb3.append(str);
        throw new IllegalArgumentException(sb3.toString());
    }

    private static int adjustTargetDensityForError(double d) {
        int densityMultiplier = getDensityMultiplier(d);
        double d2 = (double) densityMultiplier;
        Double.isNaN(d2);
        int round = round(d2 * d);
        double d3 = (double) (((float) round) / ((float) densityMultiplier));
        Double.isNaN(d3);
        double d4 = d / d3;
        double d5 = (double) round;
        Double.isNaN(d5);
        return round(d4 * d5);
    }

    private static int getDensityMultiplier(double d) {
        if (d > 1.0d) {
            d = 1.0d / d;
        }
        return (int) Math.round(d * 2.147483647E9d);
    }

    private boolean shouldUsePool(ImageType imageType) {
        if (VERSION.SDK_INT >= 19) {
            return true;
        }
        return TYPES_THAT_USE_POOL_PRE_KITKAT.contains(imageType);
    }

    private void calculateConfig(ImageReader imageReader, DecodeFormat decodeFormat, boolean z, boolean z2, Options options, int i, int i2) {
        if (!this.hardwareConfigState.setHardwareConfigIfAllowed(i, i2, options, z, z2)) {
            if (decodeFormat == DecodeFormat.PREFER_ARGB_8888 || VERSION.SDK_INT == 16) {
                options.inPreferredConfig = Config.ARGB_8888;
                return;
            }
            boolean z3 = false;
            try {
                z3 = imageReader.getImageType().hasAlpha();
            } catch (IOException e) {
                String str = TAG;
                if (Log.isLoggable(str, 3)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Cannot determine whether the image has alpha or not from header, format ");
                    sb.append(decodeFormat);
                    Log.d(str, sb.toString(), e);
                }
            }
            options.inPreferredConfig = z3 ? Config.ARGB_8888 : Config.RGB_565;
            if (options.inPreferredConfig == Config.RGB_565) {
                options.inDither = true;
            }
        }
    }

    private static int[] getDimensions(ImageReader imageReader, Options options, DecodeCallbacks decodeCallbacks, BitmapPool bitmapPool2) throws IOException {
        options.inJustDecodeBounds = true;
        decodeStream(imageReader, options, decodeCallbacks, bitmapPool2);
        options.inJustDecodeBounds = false;
        return new int[]{options.outWidth, options.outHeight};
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:20|21) */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        throw r1;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0050 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.Bitmap decodeStream(com.bumptech.glide.load.resource.bitmap.ImageReader r5, android.graphics.BitmapFactory.Options r6, com.bumptech.glide.load.resource.bitmap.Downsampler.DecodeCallbacks r7, com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool r8) throws java.io.IOException {
        /*
            java.lang.String r0 = "Downsampler"
            boolean r1 = r6.inJustDecodeBounds
            if (r1 != 0) goto L_0x000c
            r7.onObtainBounds()
            r5.stopGrowingBuffers()
        L_0x000c:
            int r1 = r6.outWidth
            int r2 = r6.outHeight
            java.lang.String r3 = r6.outMimeType
            java.util.concurrent.locks.Lock r4 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r4.lock()
            android.graphics.Bitmap r5 = r5.decodeBitmap(r6)     // Catch:{ IllegalArgumentException -> 0x0027 }
            java.util.concurrent.locks.Lock r6 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r6.unlock()
            return r5
        L_0x0025:
            r5 = move-exception
            goto L_0x0052
        L_0x0027:
            r4 = move-exception
            java.io.IOException r1 = newIoExceptionForInBitmapAssertion(r4, r1, r2, r3, r6)     // Catch:{ all -> 0x0025 }
            r2 = 3
            boolean r2 = android.util.Log.isLoggable(r0, r2)     // Catch:{ all -> 0x0025 }
            if (r2 == 0) goto L_0x0038
            java.lang.String r2 = "Failed to decode with inBitmap, trying again without Bitmap re-use"
            android.util.Log.d(r0, r2, r1)     // Catch:{ all -> 0x0025 }
        L_0x0038:
            android.graphics.Bitmap r0 = r6.inBitmap     // Catch:{ all -> 0x0025 }
            if (r0 == 0) goto L_0x0051
            android.graphics.Bitmap r0 = r6.inBitmap     // Catch:{ IOException -> 0x0050 }
            r8.put(r0)     // Catch:{ IOException -> 0x0050 }
            r0 = 0
            r6.inBitmap = r0     // Catch:{ IOException -> 0x0050 }
            android.graphics.Bitmap r5 = decodeStream(r5, r6, r7, r8)     // Catch:{ IOException -> 0x0050 }
            java.util.concurrent.locks.Lock r6 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r6.unlock()
            return r5
        L_0x0050:
            throw r1     // Catch:{ all -> 0x0025 }
        L_0x0051:
            throw r1     // Catch:{ all -> 0x0025 }
        L_0x0052:
            java.util.concurrent.locks.Lock r6 = com.bumptech.glide.load.resource.bitmap.TransformationUtils.getBitmapDrawableLock()
            r6.unlock()
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.Downsampler.decodeStream(com.bumptech.glide.load.resource.bitmap.ImageReader, android.graphics.BitmapFactory$Options, com.bumptech.glide.load.resource.bitmap.Downsampler$DecodeCallbacks, com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool):android.graphics.Bitmap");
    }

    private static boolean isScaling(Options options) {
        return options.inTargetDensity > 0 && options.inDensity > 0 && options.inTargetDensity != options.inDensity;
    }

    private static void logDecode(int i, int i2, String str, Options options, Bitmap bitmap, int i3, int i4, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append("Decoded ");
        sb.append(getBitmapString(bitmap));
        sb.append(" from [");
        sb.append(i);
        String str2 = "x";
        sb.append(str2);
        sb.append(i2);
        sb.append("] ");
        sb.append(str);
        sb.append(" with inBitmap ");
        sb.append(getInBitmapString(options));
        sb.append(" for [");
        sb.append(i3);
        sb.append(str2);
        sb.append(i4);
        sb.append("], sample size: ");
        sb.append(options.inSampleSize);
        sb.append(", density: ");
        sb.append(options.inDensity);
        sb.append(", target density: ");
        sb.append(options.inTargetDensity);
        sb.append(", thread: ");
        sb.append(Thread.currentThread().getName());
        sb.append(", duration: ");
        sb.append(LogTime.getElapsedMillis(j));
        Log.v(TAG, sb.toString());
    }

    private static String getInBitmapString(Options options) {
        return getBitmapString(options.inBitmap);
    }

    private static String getBitmapString(Bitmap bitmap) {
        String str;
        if (bitmap == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 19) {
            StringBuilder sb = new StringBuilder();
            sb.append(" (");
            sb.append(bitmap.getAllocationByteCount());
            sb.append(")");
            str = sb.toString();
        } else {
            str = "";
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("[");
        sb2.append(bitmap.getWidth());
        sb2.append("x");
        sb2.append(bitmap.getHeight());
        sb2.append("] ");
        sb2.append(bitmap.getConfig());
        sb2.append(str);
        return sb2.toString();
    }

    private static IOException newIoExceptionForInBitmapAssertion(IllegalArgumentException illegalArgumentException, int i, int i2, String str, Options options) {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception decoding bitmap, outWidth: ");
        sb.append(i);
        sb.append(", outHeight: ");
        sb.append(i2);
        sb.append(", outMimeType: ");
        sb.append(str);
        sb.append(", inBitmap: ");
        sb.append(getInBitmapString(options));
        return new IOException(sb.toString(), illegalArgumentException);
    }

    private static void setInBitmap(Options options, BitmapPool bitmapPool2, int i, int i2) {
        Config config;
        if (VERSION.SDK_INT < 26) {
            config = null;
        } else if (options.inPreferredConfig != Config.HARDWARE) {
            config = options.outConfig;
        } else {
            return;
        }
        if (config == null) {
            config = options.inPreferredConfig;
        }
        options.inBitmap = bitmapPool2.getDirty(i, i2, config);
    }

    private static synchronized Options getDefaultOptions() {
        Options options;
        synchronized (Downsampler.class) {
            synchronized (OPTIONS_QUEUE) {
                options = (Options) OPTIONS_QUEUE.poll();
            }
            if (options == null) {
                options = new Options();
                resetOptions(options);
            }
        }
        return options;
    }

    private static void releaseOptions(Options options) {
        resetOptions(options);
        synchronized (OPTIONS_QUEUE) {
            OPTIONS_QUEUE.offer(options);
        }
    }

    private static void resetOptions(Options options) {
        options.inTempStorage = null;
        options.inDither = false;
        options.inScaled = false;
        options.inSampleSize = 1;
        options.inPreferredConfig = null;
        options.inJustDecodeBounds = false;
        options.inDensity = 0;
        options.inTargetDensity = 0;
        if (VERSION.SDK_INT >= 26) {
            options.inPreferredColorSpace = null;
            options.outColorSpace = null;
            options.outConfig = null;
        }
        options.outWidth = 0;
        options.outHeight = 0;
        options.outMimeType = null;
        options.inBitmap = null;
        options.inMutable = true;
    }
}
