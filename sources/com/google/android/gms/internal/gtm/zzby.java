package com.google.android.gms.internal.gtm;

import androidx.work.WorkRequest;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;

public final class zzby {
    public static zzbz<String> zzaaa;
    public static zzbz<Integer> zzaab = zzbz.zza("analytics.batch_retry_interval.seconds.k", 3600, 3600);
    private static zzbz<Long> zzaac = zzbz.zza("analytics.service_monitor_interval", 86400000, 86400000);
    public static zzbz<Integer> zzaad = zzbz.zza("analytics.http_connection.connect_timeout_millis", 60000, 60000);
    public static zzbz<Integer> zzaae = zzbz.zza("analytics.http_connection.read_timeout_millis", 61000, 61000);
    public static zzbz<Long> zzaaf = zzbz.zza("analytics.campaigns.time_limit", 86400000, 86400000);
    private static zzbz<String> zzaag;
    private static zzbz<Integer> zzaah = zzbz.zza("analytics.first_party_experiment_variant", 0, 0);
    public static zzbz<Boolean> zzaai = zzbz.zza("analytics.test.disable_receiver", false, false);
    public static zzbz<Long> zzaaj = zzbz.zza("analytics.service_client.idle_disconnect_millis", (long) WorkRequest.MIN_BACKOFF_MILLIS, (long) WorkRequest.MIN_BACKOFF_MILLIS);
    public static zzbz<Long> zzaak = zzbz.zza("analytics.service_client.connect_timeout_millis", (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    private static zzbz<Long> zzaal = zzbz.zza("analytics.service_client.second_connect_delay_millis", (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    private static zzbz<Long> zzaam = zzbz.zza("analytics.service_client.unexpected_reconnect_millis", (long) DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS, (long) DefaultLoadErrorHandlingPolicy.DEFAULT_TRACK_BLACKLIST_MS);
    public static zzbz<Long> zzaan = zzbz.zza("analytics.service_client.reconnect_throttle_millis", 1800000, 1800000);
    public static zzbz<Long> zzaao = zzbz.zza("analytics.monitoring.sample_period_millis", 86400000, 86400000);
    public static zzbz<Long> zzaap = zzbz.zza("analytics.initialization_warning_threshold", (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    public static zzbz<Boolean> zzaaq = zzbz.zza("analytics.gcm_task_service", false, false);
    private static zzbz<Boolean> zzyz = zzbz.zza("analytics.service_enabled", false, false);
    public static zzbz<Boolean> zzza = zzbz.zza("analytics.service_client_enabled", true, true);
    public static zzbz<String> zzzb = zzbz.zza("analytics.log_tag", "GAv4", "GAv4-SVC");
    private static zzbz<Long> zzzc = zzbz.zza("analytics.max_tokens", 60, 60);
    private static zzbz<Float> zzzd = zzbz.zza("analytics.tokens_per_sec", 0.5f, 0.5f);
    public static zzbz<Integer> zzze = zzbz.zza("analytics.max_stored_hits", 2000, 20000);
    private static zzbz<Integer> zzzf = zzbz.zza("analytics.max_stored_hits_per_app", 2000, 2000);
    public static zzbz<Integer> zzzg = zzbz.zza("analytics.max_stored_properties_per_app", 100, 100);
    public static zzbz<Long> zzzh = zzbz.zza("analytics.local_dispatch_millis", 1800000, 120000);
    public static zzbz<Long> zzzi = zzbz.zza("analytics.initial_local_dispatch_millis", (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS, (long) DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    private static zzbz<Long> zzzj = zzbz.zza("analytics.min_local_dispatch_millis", 120000, 120000);
    private static zzbz<Long> zzzk = zzbz.zza("analytics.max_local_dispatch_millis", 7200000, 7200000);
    public static zzbz<Long> zzzl = zzbz.zza("analytics.dispatch_alarm_millis", 7200000, 7200000);
    public static zzbz<Long> zzzm = zzbz.zza("analytics.max_dispatch_alarm_millis", 32400000, 32400000);
    public static zzbz<Integer> zzzn = zzbz.zza("analytics.max_hits_per_dispatch", 20, 20);
    public static zzbz<Integer> zzzo = zzbz.zza("analytics.max_hits_per_batch", 20, 20);
    public static zzbz<String> zzzp;
    public static zzbz<String> zzzq;
    public static zzbz<String> zzzr;
    public static zzbz<String> zzzs;
    public static zzbz<Integer> zzzt = zzbz.zza("analytics.max_get_length", 2036, 2036);
    public static zzbz<String> zzzu = zzbz.zza("analytics.batching_strategy.k", zzbg.BATCH_BY_COUNT.name(), zzbg.BATCH_BY_COUNT.name());
    public static zzbz<String> zzzv;
    private static zzbz<Integer> zzzw = zzbz.zza("analytics.max_hits_per_request.k", 20, 20);
    public static zzbz<Integer> zzzx = zzbz.zza("analytics.max_hit_length.k", 8192, 8192);
    public static zzbz<Integer> zzzy = zzbz.zza("analytics.max_post_length.k", 8192, 8192);
    public static zzbz<Integer> zzzz = zzbz.zza("analytics.max_batch_post_length", 8192, 8192);

    static {
        String str = "http://www.google-analytics.com";
        zzzp = zzbz.zza("analytics.insecure_host", str, str);
        String str2 = "https://ssl.google-analytics.com";
        zzzq = zzbz.zza("analytics.secure_host", str2, str2);
        String str3 = "/collect";
        zzzr = zzbz.zza("analytics.simple_endpoint", str3, str3);
        String str4 = "/batch";
        zzzs = zzbz.zza("analytics.batching_endpoint", str4, str4);
        String name = zzbm.GZIP.name();
        zzzv = zzbz.zza("analytics.compression_strategy.k", name, name);
        String str5 = "404,502";
        zzaaa = zzbz.zza("analytics.fallback_responses.k", str5, str5);
        String str6 = "";
        zzaag = zzbz.zza("analytics.first_party_experiment_id", str6, str6);
    }
}
