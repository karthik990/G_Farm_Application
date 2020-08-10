package com.bumptech.glide;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.data.DataRewinder;
import com.bumptech.glide.load.data.InputStreamRewinder;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.prefill.BitmapPreFiller;
import com.bumptech.glide.load.engine.prefill.PreFillType.Builder;
import com.bumptech.glide.load.model.AssetUriLoader;
import com.bumptech.glide.load.model.ByteArrayLoader;
import com.bumptech.glide.load.model.ByteArrayLoader.ByteBufferFactory;
import com.bumptech.glide.load.model.ByteBufferEncoder;
import com.bumptech.glide.load.model.ByteBufferFileLoader;
import com.bumptech.glide.load.model.DataUrlLoader;
import com.bumptech.glide.load.model.FileLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.MediaStoreFileLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.ResourceLoader.AssetFileDescriptorFactory;
import com.bumptech.glide.load.model.ResourceLoader.FileDescriptorFactory;
import com.bumptech.glide.load.model.ResourceLoader.StreamFactory;
import com.bumptech.glide.load.model.ResourceLoader.UriFactory;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.model.StringLoader;
import com.bumptech.glide.load.model.UnitModelLoader.Factory;
import com.bumptech.glide.load.model.UriLoader;
import com.bumptech.glide.load.model.UrlUriLoader;
import com.bumptech.glide.load.model.stream.HttpGlideUrlLoader;
import com.bumptech.glide.load.model.stream.HttpUriLoader;
import com.bumptech.glide.load.model.stream.MediaStoreImageThumbLoader;
import com.bumptech.glide.load.model.stream.MediaStoreVideoThumbLoader;
import com.bumptech.glide.load.model.stream.QMediaStoreUriLoader;
import com.bumptech.glide.load.model.stream.QMediaStoreUriLoader.InputStreamFactory;
import com.bumptech.glide.load.model.stream.UrlLoader;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableDecoder;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableEncoder;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
import com.bumptech.glide.load.resource.bitmap.ByteBufferBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.ByteBufferBitmapImageDecoderResourceDecoder;
import com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.load.resource.bitmap.ExifInterfaceImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.InputStreamBitmapImageDecoderResourceDecoder;
import com.bumptech.glide.load.resource.bitmap.ParcelFileDescriptorBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.ResourceBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.StreamBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.UnitBitmapDecoder;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bytes.ByteBufferRewinder;
import com.bumptech.glide.load.resource.drawable.ResourceDrawableDecoder;
import com.bumptech.glide.load.resource.drawable.UnitDrawableDecoder;
import com.bumptech.glide.load.resource.file.FileDecoder;
import com.bumptech.glide.load.resource.gif.ByteBufferGifDecoder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawableEncoder;
import com.bumptech.glide.load.resource.gif.GifFrameResourceDecoder;
import com.bumptech.glide.load.resource.gif.StreamGifDecoder;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.bumptech.glide.load.resource.transcode.BitmapDrawableTranscoder;
import com.bumptech.glide.load.resource.transcode.DrawableBytesTranscoder;
import com.bumptech.glide.load.resource.transcode.GifDrawableBytesTranscoder;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.module.ManifestParser;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTargetFactory;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Glide implements ComponentCallbacks2 {
    private static final String DEFAULT_DISK_CACHE_DIR = "image_manager_disk_cache";
    private static final String TAG = "Glide";
    private static volatile Glide glide;
    private static volatile boolean isInitializing;
    private final ArrayPool arrayPool;
    private final BitmapPool bitmapPool;
    private BitmapPreFiller bitmapPreFiller;
    private final ConnectivityMonitorFactory connectivityMonitorFactory;
    private final RequestOptionsFactory defaultRequestOptionsFactory;
    private final Engine engine;
    private final GlideContext glideContext;
    private final List<RequestManager> managers = new ArrayList();
    private final MemoryCache memoryCache;
    private MemoryCategory memoryCategory = MemoryCategory.NORMAL;
    private final Registry registry;
    private final RequestManagerRetriever requestManagerRetriever;

    public interface RequestOptionsFactory {
        RequestOptions build();
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public static File getPhotoCacheDir(Context context) {
        return getPhotoCacheDir(context, "image_manager_disk_cache");
    }

    public static File getPhotoCacheDir(Context context, String str) {
        File cacheDir = context.getCacheDir();
        if (cacheDir != null) {
            File file = new File(cacheDir, str);
            if (file.mkdirs() || (file.exists() && file.isDirectory())) {
                return file;
            }
            return null;
        }
        String str2 = TAG;
        if (Log.isLoggable(str2, 6)) {
            Log.e(str2, "default disk cache dir is null");
        }
        return null;
    }

    public static Glide get(Context context) {
        if (glide == null) {
            GeneratedAppGlideModule annotationGeneratedGlideModules = getAnnotationGeneratedGlideModules(context.getApplicationContext());
            synchronized (Glide.class) {
                if (glide == null) {
                    checkAndInitializeGlide(context, annotationGeneratedGlideModules);
                }
            }
        }
        return glide;
    }

    private static void checkAndInitializeGlide(Context context, GeneratedAppGlideModule generatedAppGlideModule) {
        if (!isInitializing) {
            isInitializing = true;
            initializeGlide(context, generatedAppGlideModule);
            isInitializing = false;
            return;
        }
        throw new IllegalStateException("You cannot call Glide.get() in registerComponents(), use the provided Glide instance instead");
    }

    @Deprecated
    public static synchronized void init(Glide glide2) {
        synchronized (Glide.class) {
            if (glide != null) {
                tearDown();
            }
            glide = glide2;
        }
    }

    public static void init(Context context, GlideBuilder glideBuilder) {
        GeneratedAppGlideModule annotationGeneratedGlideModules = getAnnotationGeneratedGlideModules(context);
        synchronized (Glide.class) {
            if (glide != null) {
                tearDown();
            }
            initializeGlide(context, glideBuilder, annotationGeneratedGlideModules);
        }
    }

    public static synchronized void tearDown() {
        synchronized (Glide.class) {
            if (glide != null) {
                glide.getContext().getApplicationContext().unregisterComponentCallbacks(glide);
                glide.engine.shutdown();
            }
            glide = null;
        }
    }

    private static void initializeGlide(Context context, GeneratedAppGlideModule generatedAppGlideModule) {
        initializeGlide(context, new GlideBuilder(), generatedAppGlideModule);
    }

    private static void initializeGlide(Context context, GlideBuilder glideBuilder, GeneratedAppGlideModule generatedAppGlideModule) {
        Context applicationContext = context.getApplicationContext();
        List<GlideModule> emptyList = Collections.emptyList();
        if (generatedAppGlideModule == null || generatedAppGlideModule.isManifestParsingEnabled()) {
            emptyList = new ManifestParser(applicationContext).parse();
        }
        String str = TAG;
        if (generatedAppGlideModule != null && !generatedAppGlideModule.getExcludedModuleClasses().isEmpty()) {
            Set excludedModuleClasses = generatedAppGlideModule.getExcludedModuleClasses();
            Iterator it = emptyList.iterator();
            while (it.hasNext()) {
                GlideModule glideModule = (GlideModule) it.next();
                if (excludedModuleClasses.contains(glideModule.getClass())) {
                    if (Log.isLoggable(str, 3)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("AppGlideModule excludes manifest GlideModule: ");
                        sb.append(glideModule);
                        Log.d(str, sb.toString());
                    }
                    it.remove();
                }
            }
        }
        if (Log.isLoggable(str, 3)) {
            for (GlideModule glideModule2 : emptyList) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Discovered GlideModule from manifest: ");
                sb2.append(glideModule2.getClass());
                Log.d(str, sb2.toString());
            }
        }
        glideBuilder.setRequestManagerFactory(generatedAppGlideModule != null ? generatedAppGlideModule.getRequestManagerFactory() : null);
        for (GlideModule applyOptions : emptyList) {
            applyOptions.applyOptions(applicationContext, glideBuilder);
        }
        if (generatedAppGlideModule != null) {
            generatedAppGlideModule.applyOptions(applicationContext, glideBuilder);
        }
        Glide build = glideBuilder.build(applicationContext);
        for (GlideModule glideModule3 : emptyList) {
            try {
                glideModule3.registerComponents(applicationContext, build, build.registry);
            } catch (AbstractMethodError e) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("Attempting to register a Glide v3 module. If you see this, you or one of your dependencies may be including Glide v3 even though you're using Glide v4. You'll need to find and remove (or update) the offending dependency. The v3 module name is: ");
                sb3.append(glideModule3.getClass().getName());
                throw new IllegalStateException(sb3.toString(), e);
            }
        }
        if (generatedAppGlideModule != null) {
            generatedAppGlideModule.registerComponents(applicationContext, build, build.registry);
        }
        applicationContext.registerComponentCallbacks(build);
        glide = build;
    }

    private static GeneratedAppGlideModule getAnnotationGeneratedGlideModules(Context context) {
        try {
            return (GeneratedAppGlideModule) Class.forName("com.bumptech.glide.GeneratedAppGlideModuleImpl").getDeclaredConstructor(new Class[]{Context.class}).newInstance(new Object[]{context.getApplicationContext()});
        } catch (ClassNotFoundException unused) {
            String str = TAG;
            if (Log.isLoggable(str, 5)) {
                Log.w(str, "Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored");
            }
        } catch (InstantiationException e) {
            throwIncorrectGlideModule(e);
        } catch (IllegalAccessException e2) {
            throwIncorrectGlideModule(e2);
        } catch (NoSuchMethodException e3) {
            throwIncorrectGlideModule(e3);
        } catch (InvocationTargetException e4) {
            throwIncorrectGlideModule(e4);
        }
        return null;
    }

    private static void throwIncorrectGlideModule(Exception exc) {
        throw new IllegalStateException("GeneratedAppGlideModuleImpl is implemented incorrectly. If you've manually implemented this class, remove your implementation. The Annotation processor will generate a correct implementation.", exc);
    }

    Glide(Context context, Engine engine2, MemoryCache memoryCache2, BitmapPool bitmapPool2, ArrayPool arrayPool2, RequestManagerRetriever requestManagerRetriever2, ConnectivityMonitorFactory connectivityMonitorFactory2, int i, RequestOptionsFactory requestOptionsFactory, Map<Class<?>, TransitionOptions<?, ?>> map, List<RequestListener<Object>> list, boolean z, boolean z2) {
        ResourceDecoder resourceDecoder;
        ResourceDecoder resourceDecoder2;
        ResourceDrawableDecoder resourceDrawableDecoder;
        Context context2 = context;
        BitmapPool bitmapPool3 = bitmapPool2;
        ArrayPool arrayPool3 = arrayPool2;
        Class<byte[]> cls = byte[].class;
        this.engine = engine2;
        this.bitmapPool = bitmapPool3;
        this.arrayPool = arrayPool3;
        this.memoryCache = memoryCache2;
        this.requestManagerRetriever = requestManagerRetriever2;
        this.connectivityMonitorFactory = connectivityMonitorFactory2;
        this.defaultRequestOptionsFactory = requestOptionsFactory;
        Resources resources = context.getResources();
        this.registry = new Registry();
        this.registry.register((ImageHeaderParser) new DefaultImageHeaderParser());
        if (VERSION.SDK_INT >= 27) {
            this.registry.register((ImageHeaderParser) new ExifInterfaceImageHeaderParser());
        }
        List imageHeaderParsers = this.registry.getImageHeaderParsers();
        ByteBufferGifDecoder byteBufferGifDecoder = new ByteBufferGifDecoder(context2, imageHeaderParsers, bitmapPool3, arrayPool3);
        ResourceDecoder parcel = VideoDecoder.parcel(bitmapPool2);
        Downsampler downsampler = new Downsampler(this.registry.getImageHeaderParsers(), resources.getDisplayMetrics(), bitmapPool3, arrayPool3);
        if (!z2 || VERSION.SDK_INT < 28) {
            resourceDecoder = new ByteBufferBitmapDecoder(downsampler);
            resourceDecoder2 = new StreamBitmapDecoder(downsampler, arrayPool3);
        } else {
            resourceDecoder2 = new InputStreamBitmapImageDecoderResourceDecoder();
            resourceDecoder = new ByteBufferBitmapImageDecoderResourceDecoder();
        }
        ResourceDrawableDecoder resourceDrawableDecoder2 = new ResourceDrawableDecoder(context2);
        StreamFactory streamFactory = new StreamFactory(resources);
        UriFactory uriFactory = new UriFactory(resources);
        FileDescriptorFactory fileDescriptorFactory = new FileDescriptorFactory(resources);
        Class<byte[]> cls2 = cls;
        AssetFileDescriptorFactory assetFileDescriptorFactory = new AssetFileDescriptorFactory(resources);
        BitmapEncoder bitmapEncoder = new BitmapEncoder(arrayPool3);
        AssetFileDescriptorFactory assetFileDescriptorFactory2 = assetFileDescriptorFactory;
        BitmapBytesTranscoder bitmapBytesTranscoder = new BitmapBytesTranscoder();
        GifDrawableBytesTranscoder gifDrawableBytesTranscoder = new GifDrawableBytesTranscoder();
        ContentResolver contentResolver = context.getContentResolver();
        UriFactory uriFactory2 = uriFactory;
        FileDescriptorFactory fileDescriptorFactory2 = fileDescriptorFactory;
        StreamFactory streamFactory2 = streamFactory;
        String str = Registry.BUCKET_BITMAP;
        this.registry.append(ByteBuffer.class, (Encoder<Data>) new ByteBufferEncoder<Data>()).append(InputStream.class, (Encoder<Data>) new StreamEncoder<Data>(arrayPool3)).append(str, ByteBuffer.class, Bitmap.class, resourceDecoder).append(str, InputStream.class, Bitmap.class, resourceDecoder2);
        if (ParcelFileDescriptorRewinder.isSupported()) {
            resourceDrawableDecoder = resourceDrawableDecoder2;
            this.registry.append(str, ParcelFileDescriptor.class, Bitmap.class, new ParcelFileDescriptorBitmapDecoder(downsampler));
        } else {
            resourceDrawableDecoder = resourceDrawableDecoder2;
        }
        BitmapDrawableDecoder bitmapDrawableDecoder = new BitmapDrawableDecoder(resources, resourceDecoder);
        String str2 = Registry.BUCKET_BITMAP_DRAWABLE;
        StreamGifDecoder streamGifDecoder = new StreamGifDecoder(imageHeaderParsers, byteBufferGifDecoder, arrayPool3);
        String str3 = Registry.BUCKET_GIF;
        ResourceDrawableDecoder resourceDrawableDecoder3 = resourceDrawableDecoder;
        this.registry.append(str, ParcelFileDescriptor.class, Bitmap.class, parcel).append(str, AssetFileDescriptor.class, Bitmap.class, VideoDecoder.asset(bitmapPool2)).append(Bitmap.class, Bitmap.class, (ModelLoaderFactory<Model, Data>) Factory.getInstance()).append(str, Bitmap.class, Bitmap.class, new UnitBitmapDecoder()).append(Bitmap.class, (ResourceEncoder<TResource>) bitmapEncoder).append(str2, ByteBuffer.class, BitmapDrawable.class, bitmapDrawableDecoder).append(str2, InputStream.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, resourceDecoder2)).append(str2, ParcelFileDescriptor.class, BitmapDrawable.class, new BitmapDrawableDecoder(resources, parcel)).append(BitmapDrawable.class, (ResourceEncoder<TResource>) new BitmapDrawableEncoder<TResource>(bitmapPool3, bitmapEncoder)).append(str3, InputStream.class, GifDrawable.class, streamGifDecoder).append(str3, ByteBuffer.class, GifDrawable.class, byteBufferGifDecoder).append(GifDrawable.class, (ResourceEncoder<TResource>) new GifDrawableEncoder<TResource>()).append(GifDecoder.class, GifDecoder.class, (ModelLoaderFactory<Model, Data>) Factory.getInstance()).append(str, GifDecoder.class, Bitmap.class, new GifFrameResourceDecoder(bitmapPool3)).append(Uri.class, Drawable.class, (ResourceDecoder<Data, TResource>) resourceDrawableDecoder3).append(Uri.class, Bitmap.class, (ResourceDecoder<Data, TResource>) new ResourceBitmapDecoder<Data,TResource>(resourceDrawableDecoder3, bitmapPool3)).register((DataRewinder.Factory<?>) new ByteBufferRewinder.Factory<Object>()).append(File.class, ByteBuffer.class, (ModelLoaderFactory<Model, Data>) new ByteBufferFileLoader.Factory<Model,Data>()).append(File.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new FileLoader.StreamFactory<Model,Data>()).append(File.class, File.class, (ResourceDecoder<Data, TResource>) new FileDecoder<Data,TResource>()).append(File.class, ParcelFileDescriptor.class, (ModelLoaderFactory<Model, Data>) new FileLoader.FileDescriptorFactory<Model,Data>()).append(File.class, File.class, (ModelLoaderFactory<Model, Data>) Factory.getInstance()).register((DataRewinder.Factory<?>) new InputStreamRewinder.Factory<Object>(arrayPool3));
        if (ParcelFileDescriptorRewinder.isSupported()) {
            this.registry.register((DataRewinder.Factory<?>) new ParcelFileDescriptorRewinder.Factory<Object>());
        }
        StreamFactory streamFactory3 = streamFactory2;
        FileDescriptorFactory fileDescriptorFactory3 = fileDescriptorFactory2;
        UriFactory uriFactory3 = uriFactory2;
        AssetFileDescriptorFactory assetFileDescriptorFactory3 = assetFileDescriptorFactory2;
        Context context3 = context;
        this.registry.append(Integer.TYPE, InputStream.class, (ModelLoaderFactory<Model, Data>) streamFactory3).append(Integer.TYPE, ParcelFileDescriptor.class, (ModelLoaderFactory<Model, Data>) fileDescriptorFactory3).append(Integer.class, InputStream.class, (ModelLoaderFactory<Model, Data>) streamFactory3).append(Integer.class, ParcelFileDescriptor.class, (ModelLoaderFactory<Model, Data>) fileDescriptorFactory3).append(Integer.class, Uri.class, (ModelLoaderFactory<Model, Data>) uriFactory3).append(Integer.TYPE, AssetFileDescriptor.class, (ModelLoaderFactory<Model, Data>) assetFileDescriptorFactory3).append(Integer.class, AssetFileDescriptor.class, (ModelLoaderFactory<Model, Data>) assetFileDescriptorFactory3).append(Integer.TYPE, Uri.class, (ModelLoaderFactory<Model, Data>) uriFactory3).append(String.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new DataUrlLoader.StreamFactory<Model,Data>()).append(Uri.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new DataUrlLoader.StreamFactory<Model,Data>()).append(String.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new StringLoader.StreamFactory<Model,Data>()).append(String.class, ParcelFileDescriptor.class, (ModelLoaderFactory<Model, Data>) new StringLoader.FileDescriptorFactory<Model,Data>()).append(String.class, AssetFileDescriptor.class, (ModelLoaderFactory<Model, Data>) new StringLoader.AssetFileDescriptorFactory<Model,Data>()).append(Uri.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new HttpUriLoader.Factory<Model,Data>()).append(Uri.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new AssetUriLoader.StreamFactory<Model,Data>(context.getAssets())).append(Uri.class, ParcelFileDescriptor.class, (ModelLoaderFactory<Model, Data>) new AssetUriLoader.FileDescriptorFactory<Model,Data>(context.getAssets())).append(Uri.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new MediaStoreImageThumbLoader.Factory<Model,Data>(context3)).append(Uri.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new MediaStoreVideoThumbLoader.Factory<Model,Data>(context3));
        if (VERSION.SDK_INT >= 29) {
            this.registry.append(Uri.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new InputStreamFactory<Model,Data>(context3));
            this.registry.append(Uri.class, ParcelFileDescriptor.class, (ModelLoaderFactory<Model, Data>) new QMediaStoreUriLoader.FileDescriptorFactory<Model,Data>(context3));
        }
        ContentResolver contentResolver2 = contentResolver;
        Class<byte[]> cls3 = cls2;
        BitmapBytesTranscoder bitmapBytesTranscoder2 = bitmapBytesTranscoder;
        GifDrawableBytesTranscoder gifDrawableBytesTranscoder2 = gifDrawableBytesTranscoder;
        this.registry.append(Uri.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new UriLoader.StreamFactory<Model,Data>(contentResolver2)).append(Uri.class, ParcelFileDescriptor.class, (ModelLoaderFactory<Model, Data>) new UriLoader.FileDescriptorFactory<Model,Data>(contentResolver2)).append(Uri.class, AssetFileDescriptor.class, (ModelLoaderFactory<Model, Data>) new UriLoader.AssetFileDescriptorFactory<Model,Data>(contentResolver2)).append(Uri.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new UrlUriLoader.StreamFactory<Model,Data>()).append(URL.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new UrlLoader.StreamFactory<Model,Data>()).append(Uri.class, File.class, (ModelLoaderFactory<Model, Data>) new MediaStoreFileLoader.Factory<Model,Data>(context3)).append(GlideUrl.class, InputStream.class, (ModelLoaderFactory<Model, Data>) new HttpGlideUrlLoader.Factory<Model,Data>()).append(cls3, ByteBuffer.class, (ModelLoaderFactory<Model, Data>) new ByteBufferFactory<Model,Data>()).append(cls3, InputStream.class, (ModelLoaderFactory<Model, Data>) new ByteArrayLoader.StreamFactory<Model,Data>()).append(Uri.class, Uri.class, (ModelLoaderFactory<Model, Data>) Factory.getInstance()).append(Drawable.class, Drawable.class, (ModelLoaderFactory<Model, Data>) Factory.getInstance()).append(Drawable.class, Drawable.class, (ResourceDecoder<Data, TResource>) new UnitDrawableDecoder<Data,TResource>()).register(Bitmap.class, BitmapDrawable.class, new BitmapDrawableTranscoder(resources)).register(Bitmap.class, cls3, bitmapBytesTranscoder2).register(Drawable.class, cls3, new DrawableBytesTranscoder(bitmapPool3, bitmapBytesTranscoder2, gifDrawableBytesTranscoder2)).register(GifDrawable.class, cls3, gifDrawableBytesTranscoder2);
        if (VERSION.SDK_INT >= 23) {
            ResourceDecoder byteBuffer = VideoDecoder.byteBuffer(bitmapPool2);
            this.registry.append(ByteBuffer.class, Bitmap.class, byteBuffer);
            this.registry.append(ByteBuffer.class, BitmapDrawable.class, (ResourceDecoder<Data, TResource>) new BitmapDrawableDecoder<Data,TResource>(resources, byteBuffer));
        }
        Context context4 = context;
        ArrayPool arrayPool4 = arrayPool2;
        GlideContext glideContext2 = new GlideContext(context4, arrayPool4, this.registry, new ImageViewTargetFactory(), requestOptionsFactory, map, list, engine2, z, i);
        this.glideContext = glideContext2;
    }

    public BitmapPool getBitmapPool() {
        return this.bitmapPool;
    }

    public ArrayPool getArrayPool() {
        return this.arrayPool;
    }

    public Context getContext() {
        return this.glideContext.getBaseContext();
    }

    /* access modifiers changed from: 0000 */
    public ConnectivityMonitorFactory getConnectivityMonitorFactory() {
        return this.connectivityMonitorFactory;
    }

    /* access modifiers changed from: 0000 */
    public GlideContext getGlideContext() {
        return this.glideContext;
    }

    public synchronized void preFillBitmapPool(Builder... builderArr) {
        if (this.bitmapPreFiller == null) {
            this.bitmapPreFiller = new BitmapPreFiller(this.memoryCache, this.bitmapPool, (DecodeFormat) this.defaultRequestOptionsFactory.build().getOptions().get(Downsampler.DECODE_FORMAT));
        }
        this.bitmapPreFiller.preFill(builderArr);
    }

    public void clearMemory() {
        Util.assertMainThread();
        this.memoryCache.clearMemory();
        this.bitmapPool.clearMemory();
        this.arrayPool.clearMemory();
    }

    public void trimMemory(int i) {
        Util.assertMainThread();
        for (RequestManager onTrimMemory : this.managers) {
            onTrimMemory.onTrimMemory(i);
        }
        this.memoryCache.trimMemory(i);
        this.bitmapPool.trimMemory(i);
        this.arrayPool.trimMemory(i);
    }

    public void clearDiskCache() {
        Util.assertBackgroundThread();
        this.engine.clearDiskCache();
    }

    public RequestManagerRetriever getRequestManagerRetriever() {
        return this.requestManagerRetriever;
    }

    public MemoryCategory setMemoryCategory(MemoryCategory memoryCategory2) {
        Util.assertMainThread();
        this.memoryCache.setSizeMultiplier(memoryCategory2.getMultiplier());
        this.bitmapPool.setSizeMultiplier(memoryCategory2.getMultiplier());
        MemoryCategory memoryCategory3 = this.memoryCategory;
        this.memoryCategory = memoryCategory2;
        return memoryCategory3;
    }

    private static RequestManagerRetriever getRetriever(Context context) {
        Preconditions.checkNotNull(context, "You cannot start a load on a not yet attached View or a Fragment where getActivity() returns null (which usually occurs when getActivity() is called before the Fragment is attached or after the Fragment is destroyed).");
        return get(context).getRequestManagerRetriever();
    }

    public static RequestManager with(Context context) {
        return getRetriever(context).get(context);
    }

    public static RequestManager with(Activity activity) {
        return getRetriever(activity).get(activity);
    }

    public static RequestManager with(FragmentActivity fragmentActivity) {
        return getRetriever(fragmentActivity).get(fragmentActivity);
    }

    public static RequestManager with(Fragment fragment) {
        return getRetriever(fragment.getContext()).get(fragment);
    }

    @Deprecated
    public static RequestManager with(android.app.Fragment fragment) {
        return getRetriever(fragment.getActivity()).get(fragment);
    }

    public static RequestManager with(View view) {
        return getRetriever(view.getContext()).get(view);
    }

    public Registry getRegistry() {
        return this.registry;
    }

    /* access modifiers changed from: 0000 */
    public boolean removeFromManagers(Target<?> target) {
        synchronized (this.managers) {
            for (RequestManager untrack : this.managers) {
                if (untrack.untrack(target)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: 0000 */
    public void registerRequestManager(RequestManager requestManager) {
        synchronized (this.managers) {
            if (!this.managers.contains(requestManager)) {
                this.managers.add(requestManager);
            } else {
                throw new IllegalStateException("Cannot register already registered manager");
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void unregisterRequestManager(RequestManager requestManager) {
        synchronized (this.managers) {
            if (this.managers.contains(requestManager)) {
                this.managers.remove(requestManager);
            } else {
                throw new IllegalStateException("Cannot unregister not yet registered manager");
            }
        }
    }

    public void onTrimMemory(int i) {
        trimMemory(i);
    }

    public void onLowMemory() {
        clearMemory();
    }
}
