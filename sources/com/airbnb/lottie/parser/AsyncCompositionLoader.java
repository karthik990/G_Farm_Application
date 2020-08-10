package com.airbnb.lottie.parser;

import android.os.AsyncTask;
import android.util.JsonReader;
import com.airbnb.lottie.Cancellable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.OnCompositionLoadedListener;
import java.io.IOException;

public final class AsyncCompositionLoader extends AsyncTask<JsonReader, Void, LottieComposition> implements Cancellable {
    private final OnCompositionLoadedListener loadedListener;

    public AsyncCompositionLoader(OnCompositionLoadedListener onCompositionLoadedListener) {
        this.loadedListener = onCompositionLoadedListener;
    }

    /* access modifiers changed from: protected */
    public LottieComposition doInBackground(JsonReader... jsonReaderArr) {
        try {
            return Factory.fromJsonSync(jsonReaderArr[0]);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(LottieComposition lottieComposition) {
        this.loadedListener.onCompositionLoaded(lottieComposition);
    }

    public void cancel() {
        cancel(true);
    }
}
