package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.AsyncCompositionLoader;
import com.airbnb.lottie.parser.LottieCompositionParser;
import com.airbnb.lottie.utils.C0894Utils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class LottieComposition {
    private Rect bounds;
    private SparseArrayCompat<FontCharacter> characters;
    private float endFrame;
    private Map<String, Font> fonts;
    private float frameRate;
    private Map<String, LottieImageAsset> images;
    private LongSparseArray<Layer> layerMap;
    private List<Layer> layers;
    private final PerformanceTracker performanceTracker = new PerformanceTracker();
    private Map<String, List<Layer>> precomps;
    private float startFrame;
    private final HashSet<String> warnings = new HashSet<>();

    public static class Factory {
        private Factory() {
        }

        public static Cancellable fromAssetFileName(Context context, String str, OnCompositionLoadedListener onCompositionLoadedListener) {
            try {
                return fromInputStream(context.getAssets().open(str), onCompositionLoadedListener);
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to find file ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString(), e);
            }
        }

        public static Cancellable fromRawFile(Context context, int i, OnCompositionLoadedListener onCompositionLoadedListener) {
            return fromInputStream(context.getResources().openRawResource(i), onCompositionLoadedListener);
        }

        public static Cancellable fromInputStream(InputStream inputStream, OnCompositionLoadedListener onCompositionLoadedListener) {
            return fromJsonReader(new JsonReader(new InputStreamReader(inputStream)), onCompositionLoadedListener);
        }

        public static Cancellable fromJsonString(String str, OnCompositionLoadedListener onCompositionLoadedListener) {
            return fromJsonReader(new JsonReader(new StringReader(str)), onCompositionLoadedListener);
        }

        public static Cancellable fromJsonReader(JsonReader jsonReader, OnCompositionLoadedListener onCompositionLoadedListener) {
            AsyncCompositionLoader asyncCompositionLoader = new AsyncCompositionLoader(onCompositionLoadedListener);
            asyncCompositionLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new JsonReader[]{jsonReader});
            return asyncCompositionLoader;
        }

        public static LottieComposition fromFileSync(Context context, String str) {
            try {
                return fromInputStreamSync(context.getAssets().open(str));
            } catch (IOException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to open asset ");
                sb.append(str);
                throw new IllegalArgumentException(sb.toString(), e);
            }
        }

        public static LottieComposition fromInputStreamSync(InputStream inputStream) {
            return fromInputStreamSync(inputStream, true);
        }

        public static LottieComposition fromInputStreamSync(InputStream inputStream, boolean z) {
            try {
                LottieComposition fromJsonSync = fromJsonSync(new JsonReader(new InputStreamReader(inputStream)));
                if (z) {
                    C0894Utils.closeQuietly(inputStream);
                }
                return fromJsonSync;
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to parse composition.", e);
            } catch (Throwable th) {
                if (z) {
                    C0894Utils.closeQuietly(inputStream);
                }
                throw th;
            }
        }

        @Deprecated
        public static LottieComposition fromJsonSync(Resources resources, JSONObject jSONObject) {
            return fromJsonSync(jSONObject.toString());
        }

        public static LottieComposition fromJsonSync(String str) {
            try {
                return fromJsonSync(new JsonReader(new StringReader(str)));
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        public static LottieComposition fromJsonSync(JsonReader jsonReader) throws IOException {
            return LottieCompositionParser.parse(jsonReader);
        }
    }

    public void init(Rect rect, float f, float f2, float f3, List<Layer> list, LongSparseArray<Layer> longSparseArray, Map<String, List<Layer>> map, Map<String, LottieImageAsset> map2, SparseArrayCompat<FontCharacter> sparseArrayCompat, Map<String, Font> map3) {
        this.bounds = rect;
        this.startFrame = f;
        this.endFrame = f2;
        this.frameRate = f3;
        this.layers = list;
        this.layerMap = longSparseArray;
        this.precomps = map;
        this.images = map2;
        this.characters = sparseArrayCompat;
        this.fonts = map3;
    }

    public void addWarning(String str) {
        Log.w(C0873L.TAG, str);
        this.warnings.add(str);
    }

    public ArrayList<String> getWarnings() {
        HashSet<String> hashSet = this.warnings;
        return new ArrayList<>(Arrays.asList(hashSet.toArray(new String[hashSet.size()])));
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        this.performanceTracker.setEnabled(z);
    }

    public PerformanceTracker getPerformanceTracker() {
        return this.performanceTracker;
    }

    public Layer layerModelForId(long j) {
        return (Layer) this.layerMap.get(j);
    }

    public Rect getBounds() {
        return this.bounds;
    }

    public float getDuration() {
        return (float) ((long) ((getDurationFrames() / this.frameRate) * 1000.0f));
    }

    public float getStartFrame() {
        return this.startFrame;
    }

    public float getEndFrame() {
        return this.endFrame;
    }

    public float getFrameRate() {
        return this.frameRate;
    }

    public List<Layer> getLayers() {
        return this.layers;
    }

    public List<Layer> getPrecomps(String str) {
        return (List) this.precomps.get(str);
    }

    public SparseArrayCompat<FontCharacter> getCharacters() {
        return this.characters;
    }

    public Map<String, Font> getFonts() {
        return this.fonts;
    }

    public boolean hasImages() {
        return !this.images.isEmpty();
    }

    public Map<String, LottieImageAsset> getImages() {
        return this.images;
    }

    public float getDurationFrames() {
        return this.endFrame - this.startFrame;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        for (Layer layer : this.layers) {
            sb.append(layer.toString("\t"));
        }
        return sb.toString();
    }
}
