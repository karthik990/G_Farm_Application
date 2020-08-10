package com.google.android.exoplayer2.source.hls.offline;

import android.net.Uri;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.offline.SegmentDownloader;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist.Segment;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistParser;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;
import com.google.android.exoplayer2.util.UriUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public final class HlsDownloader extends SegmentDownloader<HlsPlaylist> {
    public HlsDownloader(Uri uri, List<StreamKey> list, DownloaderConstructorHelper downloaderConstructorHelper) {
        super(uri, list, downloaderConstructorHelper);
    }

    /* access modifiers changed from: protected */
    public HlsPlaylist getManifest(DataSource dataSource, DataSpec dataSpec) throws IOException {
        return loadManifest(dataSource, dataSpec);
    }

    /* access modifiers changed from: protected */
    public List<Segment> getSegments(DataSource dataSource, HlsPlaylist hlsPlaylist, boolean z) throws IOException {
        ArrayList arrayList = new ArrayList();
        if (hlsPlaylist instanceof HlsMasterPlaylist) {
            addMediaPlaylistDataSpecs(((HlsMasterPlaylist) hlsPlaylist).mediaPlaylistUrls, arrayList);
        } else {
            arrayList.add(SegmentDownloader.getCompressibleDataSpec(Uri.parse(hlsPlaylist.baseUri)));
        }
        ArrayList arrayList2 = new ArrayList();
        HashSet hashSet = new HashSet();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            DataSpec dataSpec = (DataSpec) it.next();
            arrayList2.add(new Segment(0, dataSpec));
            try {
                HlsMediaPlaylist hlsMediaPlaylist = (HlsMediaPlaylist) loadManifest(dataSource, dataSpec);
                Segment segment = null;
                List<Segment> list = hlsMediaPlaylist.segments;
                for (int i = 0; i < list.size(); i++) {
                    Segment segment2 = (Segment) list.get(i);
                    Segment segment3 = segment2.initializationSegment;
                    if (!(segment3 == null || segment3 == segment)) {
                        addSegment(hlsMediaPlaylist, segment3, hashSet, arrayList2);
                        segment = segment3;
                    }
                    addSegment(hlsMediaPlaylist, segment2, hashSet, arrayList2);
                }
            } catch (IOException e) {
                if (!z) {
                    throw e;
                }
            }
        }
        return arrayList2;
    }

    private void addMediaPlaylistDataSpecs(List<Uri> list, List<DataSpec> list2) {
        for (int i = 0; i < list.size(); i++) {
            list2.add(SegmentDownloader.getCompressibleDataSpec((Uri) list.get(i)));
        }
    }

    private static HlsPlaylist loadManifest(DataSource dataSource, DataSpec dataSpec) throws IOException {
        return (HlsPlaylist) ParsingLoadable.load(dataSource, (Parser<? extends T>) new HlsPlaylistParser<Object>(), dataSpec, 4);
    }

    private void addSegment(HlsMediaPlaylist hlsMediaPlaylist, Segment segment, HashSet<Uri> hashSet, ArrayList<Segment> arrayList) {
        String str = hlsMediaPlaylist.baseUri;
        long j = hlsMediaPlaylist.startTimeUs + segment.relativeStartTimeUs;
        if (segment.fullSegmentEncryptionKeyUri != null) {
            Uri resolveToUri = UriUtil.resolveToUri(str, segment.fullSegmentEncryptionKeyUri);
            if (hashSet.add(resolveToUri)) {
                arrayList.add(new Segment(j, SegmentDownloader.getCompressibleDataSpec(resolveToUri)));
            }
        }
        DataSpec dataSpec = new DataSpec(UriUtil.resolveToUri(str, segment.url), segment.byterangeOffset, segment.byterangeLength, null);
        arrayList.add(new Segment(j, dataSpec));
    }
}
