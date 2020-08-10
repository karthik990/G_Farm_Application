package com.google.api.services.youtube.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import java.util.Map;

public final class Playlist extends GenericJson {
    @Key
    private PlaylistContentDetails contentDetails;
    @Key
    private String etag;
    @Key

    /* renamed from: id */
    private String f1851id;
    @Key
    private String kind;
    @Key
    private Map<String, PlaylistLocalization> localizations;
    @Key
    private PlaylistPlayer player;
    @Key
    private PlaylistSnippet snippet;
    @Key
    private PlaylistStatus status;

    public PlaylistContentDetails getContentDetails() {
        return this.contentDetails;
    }

    public Playlist setContentDetails(PlaylistContentDetails playlistContentDetails) {
        this.contentDetails = playlistContentDetails;
        return this;
    }

    public String getEtag() {
        return this.etag;
    }

    public Playlist setEtag(String str) {
        this.etag = str;
        return this;
    }

    public String getId() {
        return this.f1851id;
    }

    public Playlist setId(String str) {
        this.f1851id = str;
        return this;
    }

    public String getKind() {
        return this.kind;
    }

    public Playlist setKind(String str) {
        this.kind = str;
        return this;
    }

    public Map<String, PlaylistLocalization> getLocalizations() {
        return this.localizations;
    }

    public Playlist setLocalizations(Map<String, PlaylistLocalization> map) {
        this.localizations = map;
        return this;
    }

    public PlaylistPlayer getPlayer() {
        return this.player;
    }

    public Playlist setPlayer(PlaylistPlayer playlistPlayer) {
        this.player = playlistPlayer;
        return this;
    }

    public PlaylistSnippet getSnippet() {
        return this.snippet;
    }

    public Playlist setSnippet(PlaylistSnippet playlistSnippet) {
        this.snippet = playlistSnippet;
        return this;
    }

    public PlaylistStatus getStatus() {
        return this.status;
    }

    public Playlist setStatus(PlaylistStatus playlistStatus) {
        this.status = playlistStatus;
        return this;
    }

    public Playlist set(String str, Object obj) {
        return (Playlist) super.set(str, obj);
    }

    public Playlist clone() {
        return (Playlist) super.clone();
    }
}
