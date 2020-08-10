package com.google.android.exoplayer2.source.hls.playlist;

import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;

public final class DefaultHlsPlaylistParserFactory implements HlsPlaylistParserFactory {
    public Parser<HlsPlaylist> createPlaylistParser() {
        return new HlsPlaylistParser();
    }

    public Parser<HlsPlaylist> createPlaylistParser(HlsMasterPlaylist hlsMasterPlaylist) {
        return new HlsPlaylistParser(hlsMasterPlaylist);
    }
}
