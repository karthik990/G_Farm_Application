package retrofit2.adapter.rxjava2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import p043io.reactivex.Completable;
import p043io.reactivex.Flowable;
import p043io.reactivex.Maybe;
import p043io.reactivex.Observable;
import p043io.reactivex.Scheduler;
import p043io.reactivex.Single;
import retrofit2.CallAdapter;
import retrofit2.CallAdapter.Factory;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RxJava2CallAdapterFactory extends Factory {
    private final boolean isAsync;
    @Nullable
    private final Scheduler scheduler;

    public static RxJava2CallAdapterFactory create() {
        return new RxJava2CallAdapterFactory(null, false);
    }

    public static RxJava2CallAdapterFactory createAsync() {
        return new RxJava2CallAdapterFactory(null, true);
    }

    public static RxJava2CallAdapterFactory createWithScheduler(Scheduler scheduler2) {
        if (scheduler2 != null) {
            return new RxJava2CallAdapterFactory(scheduler2, false);
        }
        throw new NullPointerException("scheduler == null");
    }

    private RxJava2CallAdapterFactory(@Nullable Scheduler scheduler2, boolean z) {
        this.scheduler = scheduler2;
        this.isAsync = z;
    }

    public CallAdapter<?, ?> get(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        boolean z;
        boolean z2;
        Type type2;
        Class<Observable> rawType = getRawType(type);
        if (rawType == Completable.class) {
            RxJava2CallAdapter rxJava2CallAdapter = new RxJava2CallAdapter(Void.class, this.scheduler, this.isAsync, false, true, false, false, false, true);
            return rxJava2CallAdapter;
        }
        boolean z3 = rawType == Flowable.class;
        boolean z4 = rawType == Single.class;
        boolean z5 = rawType == Maybe.class;
        if (rawType != Observable.class && !z3 && !z4 && !z5) {
            return null;
        }
        if (!(type instanceof ParameterizedType)) {
            String str = !z3 ? !z4 ? z5 ? "Maybe" : "Observable" : "Single" : "Flowable";
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" return type must be parameterized as ");
            sb.append(str);
            sb.append("<Foo> or ");
            sb.append(str);
            sb.append("<? extends Foo>");
            throw new IllegalStateException(sb.toString());
        }
        Type parameterUpperBound = getParameterUpperBound(0, (ParameterizedType) type);
        Class<Result> rawType2 = getRawType(parameterUpperBound);
        if (rawType2 == Response.class) {
            if (parameterUpperBound instanceof ParameterizedType) {
                type2 = getParameterUpperBound(0, (ParameterizedType) parameterUpperBound);
                z2 = false;
            } else {
                throw new IllegalStateException("Response must be parameterized as Response<Foo> or Response<? extends Foo>");
            }
        } else if (rawType2 != Result.class) {
            type2 = parameterUpperBound;
            z2 = false;
            z = true;
            RxJava2CallAdapter rxJava2CallAdapter2 = new RxJava2CallAdapter(type2, this.scheduler, this.isAsync, z2, z, z3, z4, z5, false);
            return rxJava2CallAdapter2;
        } else if (parameterUpperBound instanceof ParameterizedType) {
            type2 = getParameterUpperBound(0, (ParameterizedType) parameterUpperBound);
            z2 = true;
        } else {
            throw new IllegalStateException("Result must be parameterized as Result<Foo> or Result<? extends Foo>");
        }
        z = false;
        RxJava2CallAdapter rxJava2CallAdapter22 = new RxJava2CallAdapter(type2, this.scheduler, this.isAsync, z2, z, z3, z4, z5, false);
        return rxJava2CallAdapter22;
    }
}
