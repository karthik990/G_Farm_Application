package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.p040ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.p040ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.p040ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.p040ts.DefaultTsPayloadReaderFactory;
import com.google.android.exoplayer2.extractor.p040ts.TsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.Metadata.Entry;
import com.google.android.exoplayer2.source.hls.HlsExtractorFactory.Result;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import java.io.EOFException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class DefaultHlsExtractorFactory implements HlsExtractorFactory {
    public static final String AAC_FILE_EXTENSION = ".aac";
    public static final String AC3_FILE_EXTENSION = ".ac3";
    public static final String AC4_FILE_EXTENSION = ".ac4";
    public static final String CMF_FILE_EXTENSION_PREFIX = ".cmf";
    public static final String EC3_FILE_EXTENSION = ".ec3";
    public static final String M4_FILE_EXTENSION_PREFIX = ".m4";
    public static final String MP3_FILE_EXTENSION = ".mp3";
    public static final String MP4_FILE_EXTENSION = ".mp4";
    public static final String MP4_FILE_EXTENSION_PREFIX = ".mp4";
    public static final String VTT_FILE_EXTENSION = ".vtt";
    public static final String WEBVTT_FILE_EXTENSION = ".webvtt";
    private final boolean exposeCea608WhenMissingDeclarations;
    private final int payloadReaderFactoryFlags;

    public DefaultHlsExtractorFactory() {
        this(0, true);
    }

    public DefaultHlsExtractorFactory(int i, boolean z) {
        this.payloadReaderFactoryFlags = i;
        this.exposeCea608WhenMissingDeclarations = z;
    }

    public Result createExtractor(Extractor extractor, Uri uri, Format format, List<Format> list, DrmInitData drmInitData, TimestampAdjuster timestampAdjuster, Map<String, List<String>> map, ExtractorInput extractorInput) throws InterruptedException, IOException {
        if (extractor != null) {
            if (isReusable(extractor)) {
                return buildResult(extractor);
            }
            if (buildResultForSameExtractorType(extractor, format, timestampAdjuster) == null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected previousExtractor type: ");
                sb.append(extractor.getClass().getSimpleName());
                throw new IllegalArgumentException(sb.toString());
            }
        }
        Extractor createExtractorByFileExtension = createExtractorByFileExtension(uri, format, list, drmInitData, timestampAdjuster);
        extractorInput.resetPeekPosition();
        if (sniffQuietly(createExtractorByFileExtension, extractorInput)) {
            return buildResult(createExtractorByFileExtension);
        }
        if (!(createExtractorByFileExtension instanceof WebvttExtractor)) {
            WebvttExtractor webvttExtractor = new WebvttExtractor(format.language, timestampAdjuster);
            if (sniffQuietly(webvttExtractor, extractorInput)) {
                return buildResult(webvttExtractor);
            }
        }
        if (!(createExtractorByFileExtension instanceof AdtsExtractor)) {
            AdtsExtractor adtsExtractor = new AdtsExtractor();
            if (sniffQuietly(adtsExtractor, extractorInput)) {
                return buildResult(adtsExtractor);
            }
        }
        if (!(createExtractorByFileExtension instanceof Ac3Extractor)) {
            Ac3Extractor ac3Extractor = new Ac3Extractor();
            if (sniffQuietly(ac3Extractor, extractorInput)) {
                return buildResult(ac3Extractor);
            }
        }
        if (!(createExtractorByFileExtension instanceof Ac4Extractor)) {
            Ac4Extractor ac4Extractor = new Ac4Extractor();
            if (sniffQuietly(ac4Extractor, extractorInput)) {
                return buildResult(ac4Extractor);
            }
        }
        if (!(createExtractorByFileExtension instanceof Mp3Extractor)) {
            Mp3Extractor mp3Extractor = new Mp3Extractor(0, 0);
            if (sniffQuietly(mp3Extractor, extractorInput)) {
                return buildResult(mp3Extractor);
            }
        }
        if (!(createExtractorByFileExtension instanceof FragmentedMp4Extractor)) {
            FragmentedMp4Extractor createFragmentedMp4Extractor = createFragmentedMp4Extractor(timestampAdjuster, format, drmInitData, list);
            if (sniffQuietly(createFragmentedMp4Extractor, extractorInput)) {
                return buildResult(createFragmentedMp4Extractor);
            }
        }
        if (!(createExtractorByFileExtension instanceof TsExtractor)) {
            TsExtractor createTsExtractor = createTsExtractor(this.payloadReaderFactoryFlags, this.exposeCea608WhenMissingDeclarations, format, list, timestampAdjuster);
            if (sniffQuietly(createTsExtractor, extractorInput)) {
                return buildResult(createTsExtractor);
            }
        }
        return buildResult(createExtractorByFileExtension);
    }

    private Extractor createExtractorByFileExtension(Uri uri, Format format, List<Format> list, DrmInitData drmInitData, TimestampAdjuster timestampAdjuster) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment == null) {
            lastPathSegment = "";
        }
        if (MimeTypes.TEXT_VTT.equals(format.sampleMimeType) || lastPathSegment.endsWith(WEBVTT_FILE_EXTENSION) || lastPathSegment.endsWith(VTT_FILE_EXTENSION)) {
            return new WebvttExtractor(format.language, timestampAdjuster);
        }
        if (lastPathSegment.endsWith(AAC_FILE_EXTENSION)) {
            return new AdtsExtractor();
        }
        if (lastPathSegment.endsWith(AC3_FILE_EXTENSION) || lastPathSegment.endsWith(EC3_FILE_EXTENSION)) {
            return new Ac3Extractor();
        }
        if (lastPathSegment.endsWith(AC4_FILE_EXTENSION)) {
            return new Ac4Extractor();
        }
        if (lastPathSegment.endsWith(MP3_FILE_EXTENSION)) {
            return new Mp3Extractor(0, 0);
        }
        String str = ".mp4";
        if (!lastPathSegment.endsWith(str)) {
            if (!lastPathSegment.startsWith(M4_FILE_EXTENSION_PREFIX, lastPathSegment.length() - 4) && !lastPathSegment.startsWith(str, lastPathSegment.length() - 5)) {
                if (!lastPathSegment.startsWith(CMF_FILE_EXTENSION_PREFIX, lastPathSegment.length() - 5)) {
                    return createTsExtractor(this.payloadReaderFactoryFlags, this.exposeCea608WhenMissingDeclarations, format, list, timestampAdjuster);
                }
            }
        }
        return createFragmentedMp4Extractor(timestampAdjuster, format, drmInitData, list);
    }

    private static TsExtractor createTsExtractor(int i, boolean z, Format format, List<Format> list, TimestampAdjuster timestampAdjuster) {
        int i2 = i | 16;
        if (list != null) {
            i2 |= 32;
        } else if (z) {
            list = Collections.singletonList(Format.createTextSampleFormat(null, MimeTypes.APPLICATION_CEA608, 0, null));
        } else {
            list = Collections.emptyList();
        }
        String str = format.codecs;
        if (!TextUtils.isEmpty(str)) {
            if (!MimeTypes.AUDIO_AAC.equals(MimeTypes.getAudioMediaMimeType(str))) {
                i2 |= 2;
            }
            if (!MimeTypes.VIDEO_H264.equals(MimeTypes.getVideoMediaMimeType(str))) {
                i2 |= 4;
            }
        }
        return new TsExtractor(2, timestampAdjuster, new DefaultTsPayloadReaderFactory(i2, list));
    }

    private static FragmentedMp4Extractor createFragmentedMp4Extractor(TimestampAdjuster timestampAdjuster, Format format, DrmInitData drmInitData, List<Format> list) {
        int i = isFmp4Variant(format) ? 4 : 0;
        if (list == null) {
            list = Collections.emptyList();
        }
        FragmentedMp4Extractor fragmentedMp4Extractor = new FragmentedMp4Extractor(i, timestampAdjuster, null, drmInitData, list);
        return fragmentedMp4Extractor;
    }

    private static boolean isFmp4Variant(Format format) {
        Metadata metadata = format.metadata;
        if (metadata == null) {
            return false;
        }
        for (int i = 0; i < metadata.length(); i++) {
            Entry entry = metadata.get(i);
            if (entry instanceof HlsTrackMetadataEntry) {
                return !((HlsTrackMetadataEntry) entry).variantInfos.isEmpty();
            }
        }
        return false;
    }

    private static Result buildResultForSameExtractorType(Extractor extractor, Format format, TimestampAdjuster timestampAdjuster) {
        if (extractor instanceof WebvttExtractor) {
            return buildResult(new WebvttExtractor(format.language, timestampAdjuster));
        }
        if (extractor instanceof AdtsExtractor) {
            return buildResult(new AdtsExtractor());
        }
        if (extractor instanceof Ac3Extractor) {
            return buildResult(new Ac3Extractor());
        }
        if (extractor instanceof Ac4Extractor) {
            return buildResult(new Ac4Extractor());
        }
        if (extractor instanceof Mp3Extractor) {
            return buildResult(new Mp3Extractor());
        }
        return null;
    }

    private static Result buildResult(Extractor extractor) {
        return new Result(extractor, (extractor instanceof AdtsExtractor) || (extractor instanceof Ac3Extractor) || (extractor instanceof Ac4Extractor) || (extractor instanceof Mp3Extractor), isReusable(extractor));
    }

    /* JADX INFO: finally extract failed */
    private static boolean sniffQuietly(Extractor extractor, ExtractorInput extractorInput) throws InterruptedException, IOException {
        try {
            boolean sniff = extractor.sniff(extractorInput);
            extractorInput.resetPeekPosition();
            return sniff;
        } catch (EOFException unused) {
            extractorInput.resetPeekPosition();
            return false;
        } catch (Throwable th) {
            extractorInput.resetPeekPosition();
            throw th;
        }
    }

    private static boolean isReusable(Extractor extractor) {
        return (extractor instanceof TsExtractor) || (extractor instanceof FragmentedMp4Extractor);
    }
}
