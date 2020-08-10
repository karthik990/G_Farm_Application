package com.google.common.net;

import com.fasterxml.jackson.core.JsonPointer;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.message.TokenParser;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.objectweb.asm.signature.SignatureVisitor;

@Immutable
public final class MediaType {
    public static final MediaType AAC_AUDIO;
    public static final MediaType ANY_APPLICATION_TYPE;
    public static final MediaType ANY_AUDIO_TYPE;
    public static final MediaType ANY_IMAGE_TYPE;
    public static final MediaType ANY_TEXT_TYPE;
    public static final MediaType ANY_TYPE;
    public static final MediaType ANY_VIDEO_TYPE;
    public static final MediaType APPLE_MOBILE_CONFIG;
    public static final MediaType APPLE_PASSBOOK;
    public static final MediaType APPLICATION_BINARY;
    private static final String APPLICATION_TYPE = "application";
    public static final MediaType APPLICATION_XML_UTF_8;
    public static final MediaType ATOM_UTF_8;
    private static final String AUDIO_TYPE = "audio";
    public static final MediaType BASIC_AUDIO;
    public static final MediaType BMP;
    public static final MediaType BZIP2;
    public static final MediaType CACHE_MANIFEST_UTF_8;
    private static final String CHARSET_ATTRIBUTE = "charset";
    public static final MediaType CRW;
    public static final MediaType CSS_UTF_8;
    public static final MediaType CSV_UTF_8;
    public static final MediaType DART_UTF_8;
    public static final MediaType EOT;
    public static final MediaType EPUB;
    public static final MediaType FLV_VIDEO;
    public static final MediaType FORM_DATA;
    public static final MediaType GIF;
    public static final MediaType GZIP;
    public static final MediaType HAL_JSON;
    public static final MediaType HTML_UTF_8;
    public static final MediaType ICO;
    private static final String IMAGE_TYPE = "image";
    public static final MediaType I_CALENDAR_UTF_8;
    public static final MediaType JAVASCRIPT_UTF_8;
    public static final MediaType JPEG;
    public static final MediaType JSON_UTF_8;
    public static final MediaType KEY_ARCHIVE;
    public static final MediaType KML;
    public static final MediaType KMZ;
    private static final Map<MediaType, MediaType> KNOWN_TYPES = Maps.newHashMap();
    public static final MediaType L16_AUDIO;
    public static final MediaType L24_AUDIO;
    private static final CharMatcher LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
    public static final MediaType MANIFEST_JSON_UTF_8;
    public static final MediaType MBOX;
    public static final MediaType MICROSOFT_EXCEL;
    public static final MediaType MICROSOFT_POWERPOINT;
    public static final MediaType MICROSOFT_WORD;
    public static final MediaType MP4_AUDIO;
    public static final MediaType MP4_VIDEO;
    public static final MediaType MPEG_AUDIO;
    public static final MediaType MPEG_VIDEO;
    public static final MediaType NACL_APPLICATION;
    public static final MediaType NACL_PORTABLE_APPLICATION;
    public static final MediaType OCTET_STREAM;
    public static final MediaType OGG_AUDIO;
    public static final MediaType OGG_CONTAINER;
    public static final MediaType OGG_VIDEO;
    public static final MediaType OOXML_DOCUMENT;
    public static final MediaType OOXML_PRESENTATION;
    public static final MediaType OOXML_SHEET;
    public static final MediaType OPENDOCUMENT_GRAPHICS;
    public static final MediaType OPENDOCUMENT_PRESENTATION;
    public static final MediaType OPENDOCUMENT_SPREADSHEET;
    public static final MediaType OPENDOCUMENT_TEXT;
    private static final MapJoiner PARAMETER_JOINER = Joiner.m1775on("; ").withKeyValueSeparator("=");
    public static final MediaType PDF;
    public static final MediaType PLAIN_TEXT_UTF_8;
    public static final MediaType PNG;
    public static final MediaType POSTSCRIPT;
    public static final MediaType PROTOBUF;
    public static final MediaType PSD;
    public static final MediaType QUICKTIME;
    private static final CharMatcher QUOTED_TEXT_MATCHER = CharMatcher.ascii().and(CharMatcher.noneOf("\"\\\r"));
    public static final MediaType RDF_XML_UTF_8;
    public static final MediaType RTF_UTF_8;
    public static final MediaType SFNT;
    public static final MediaType SHOCKWAVE_FLASH;
    public static final MediaType SKETCHUP;
    public static final MediaType SOAP_XML_UTF_8;
    public static final MediaType SVG_UTF_8;
    public static final MediaType TAR;
    public static final MediaType TEXT_JAVASCRIPT_UTF_8;
    private static final String TEXT_TYPE = "text";
    public static final MediaType THREE_GPP2_VIDEO;
    public static final MediaType THREE_GPP_VIDEO;
    public static final MediaType TIFF;
    /* access modifiers changed from: private */
    public static final CharMatcher TOKEN_MATCHER = CharMatcher.ascii().and(CharMatcher.javaIsoControl().negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
    public static final MediaType TSV_UTF_8;
    private static final ImmutableListMultimap<String, String> UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.m1897of("charset", Ascii.toLowerCase(Charsets.UTF_8.name()));
    public static final MediaType VCARD_UTF_8;
    private static final String VIDEO_TYPE = "video";
    public static final MediaType VND_REAL_AUDIO;
    public static final MediaType VND_WAVE_AUDIO;
    public static final MediaType VORBIS_AUDIO;
    public static final MediaType VTT_UTF_8;
    public static final MediaType WAX_AUDIO;
    public static final MediaType WEBM_AUDIO;
    public static final MediaType WEBM_VIDEO;
    public static final MediaType WEBP;
    private static final String WILDCARD = "*";
    public static final MediaType WMA_AUDIO;
    public static final MediaType WML_UTF_8;
    public static final MediaType WMV;
    public static final MediaType WOFF;
    public static final MediaType WOFF2;
    public static final MediaType XHTML_UTF_8;
    public static final MediaType XML_UTF_8;
    public static final MediaType XRD_UTF_8;
    public static final MediaType ZIP;
    @LazyInit
    private int hashCode;
    private final ImmutableListMultimap<String, String> parameters;
    @LazyInit
    private Optional<Charset> parsedCharset;
    private final String subtype;
    @LazyInit
    private String toString;
    private final String type;

    private static final class Tokenizer {
        final String input;
        int position = 0;

        Tokenizer(String str) {
            this.input = str;
        }

        /* access modifiers changed from: 0000 */
        public String consumeTokenIfPresent(CharMatcher charMatcher) {
            Preconditions.checkState(hasMore());
            int i = this.position;
            this.position = charMatcher.negate().indexIn(this.input, i);
            return hasMore() ? this.input.substring(i, this.position) : this.input.substring(i);
        }

        /* access modifiers changed from: 0000 */
        public String consumeToken(CharMatcher charMatcher) {
            int i = this.position;
            String consumeTokenIfPresent = consumeTokenIfPresent(charMatcher);
            Preconditions.checkState(this.position != i);
            return consumeTokenIfPresent;
        }

        /* access modifiers changed from: 0000 */
        public char consumeCharacter(CharMatcher charMatcher) {
            Preconditions.checkState(hasMore());
            char previewChar = previewChar();
            Preconditions.checkState(charMatcher.matches(previewChar));
            this.position++;
            return previewChar;
        }

        /* access modifiers changed from: 0000 */
        public char consumeCharacter(char c) {
            Preconditions.checkState(hasMore());
            Preconditions.checkState(previewChar() == c);
            this.position++;
            return c;
        }

        /* access modifiers changed from: 0000 */
        public char previewChar() {
            Preconditions.checkState(hasMore());
            return this.input.charAt(this.position);
        }

        /* access modifiers changed from: 0000 */
        public boolean hasMore() {
            int i = this.position;
            return i >= 0 && i < this.input.length();
        }
    }

    static {
        String str = "*";
        ANY_TYPE = createConstant(str, str);
        String str2 = "text";
        ANY_TEXT_TYPE = createConstant(str2, str);
        String str3 = "image";
        ANY_IMAGE_TYPE = createConstant(str3, str);
        String str4 = "audio";
        ANY_AUDIO_TYPE = createConstant(str4, str);
        String str5 = "video";
        ANY_VIDEO_TYPE = createConstant(str5, str);
        String str6 = "application";
        ANY_APPLICATION_TYPE = createConstant(str6, str);
        CACHE_MANIFEST_UTF_8 = createConstantUtf8(str2, "cache-manifest");
        CSS_UTF_8 = createConstantUtf8(str2, "css");
        CSV_UTF_8 = createConstantUtf8(str2, "csv");
        HTML_UTF_8 = createConstantUtf8(str2, "html");
        I_CALENDAR_UTF_8 = createConstantUtf8(str2, "calendar");
        PLAIN_TEXT_UTF_8 = createConstantUtf8(str2, "plain");
        String str7 = "javascript";
        TEXT_JAVASCRIPT_UTF_8 = createConstantUtf8(str2, str7);
        TSV_UTF_8 = createConstantUtf8(str2, "tab-separated-values");
        VCARD_UTF_8 = createConstantUtf8(str2, "vcard");
        WML_UTF_8 = createConstantUtf8(str2, "vnd.wap.wml");
        String str8 = "xml";
        XML_UTF_8 = createConstantUtf8(str2, str8);
        VTT_UTF_8 = createConstantUtf8(str2, "vtt");
        BMP = createConstant(str3, "bmp");
        CRW = createConstant(str3, "x-canon-crw");
        GIF = createConstant(str3, "gif");
        ICO = createConstant(str3, "vnd.microsoft.icon");
        JPEG = createConstant(str3, "jpeg");
        PNG = createConstant(str3, "png");
        PSD = createConstant(str3, "vnd.adobe.photoshop");
        SVG_UTF_8 = createConstantUtf8(str3, "svg+xml");
        TIFF = createConstant(str3, "tiff");
        WEBP = createConstant(str3, "webp");
        String str9 = "mp4";
        MP4_AUDIO = createConstant(str4, str9);
        String str10 = "mpeg";
        MPEG_AUDIO = createConstant(str4, str10);
        String str11 = "ogg";
        OGG_AUDIO = createConstant(str4, str11);
        String str12 = "webm";
        WEBM_AUDIO = createConstant(str4, str12);
        L16_AUDIO = createConstant(str4, "l16");
        L24_AUDIO = createConstant(str4, "l24");
        BASIC_AUDIO = createConstant(str4, "basic");
        AAC_AUDIO = createConstant(str4, "aac");
        VORBIS_AUDIO = createConstant(str4, "vorbis");
        WMA_AUDIO = createConstant(str4, "x-ms-wma");
        WAX_AUDIO = createConstant(str4, "x-ms-wax");
        VND_REAL_AUDIO = createConstant(str4, "vnd.rn-realaudio");
        VND_WAVE_AUDIO = createConstant(str4, "vnd.wave");
        MP4_VIDEO = createConstant(str5, str9);
        MPEG_VIDEO = createConstant(str5, str10);
        OGG_VIDEO = createConstant(str5, str11);
        QUICKTIME = createConstant(str5, "quicktime");
        WEBM_VIDEO = createConstant(str5, str12);
        WMV = createConstant(str5, "x-ms-wmv");
        FLV_VIDEO = createConstant(str5, "x-flv");
        THREE_GPP_VIDEO = createConstant(str5, "3gpp");
        THREE_GPP2_VIDEO = createConstant(str5, "3gpp2");
        APPLICATION_XML_UTF_8 = createConstantUtf8(str6, str8);
        ATOM_UTF_8 = createConstantUtf8(str6, "atom+xml");
        BZIP2 = createConstant(str6, "x-bzip2");
        DART_UTF_8 = createConstantUtf8(str6, "dart");
        APPLE_PASSBOOK = createConstant(str6, "vnd.apple.pkpass");
        EOT = createConstant(str6, "vnd.ms-fontobject");
        EPUB = createConstant(str6, "epub+zip");
        FORM_DATA = createConstant(str6, "x-www-form-urlencoded");
        KEY_ARCHIVE = createConstant(str6, "pkcs12");
        APPLICATION_BINARY = createConstant(str6, "binary");
        GZIP = createConstant(str6, "x-gzip");
        HAL_JSON = createConstant(str6, "hal+json");
        JAVASCRIPT_UTF_8 = createConstantUtf8(str6, str7);
        JSON_UTF_8 = createConstantUtf8(str6, "json");
        MANIFEST_JSON_UTF_8 = createConstantUtf8(str6, "manifest+json");
        KML = createConstant(str6, "vnd.google-earth.kml+xml");
        KMZ = createConstant(str6, "vnd.google-earth.kmz");
        MBOX = createConstant(str6, "mbox");
        APPLE_MOBILE_CONFIG = createConstant(str6, "x-apple-aspen-config");
        MICROSOFT_EXCEL = createConstant(str6, "vnd.ms-excel");
        MICROSOFT_POWERPOINT = createConstant(str6, "vnd.ms-powerpoint");
        MICROSOFT_WORD = createConstant(str6, "msword");
        NACL_APPLICATION = createConstant(str6, "x-nacl");
        NACL_PORTABLE_APPLICATION = createConstant(str6, "x-pnacl");
        OCTET_STREAM = createConstant(str6, "octet-stream");
        OGG_CONTAINER = createConstant(str6, str11);
        OOXML_DOCUMENT = createConstant(str6, "vnd.openxmlformats-officedocument.wordprocessingml.document");
        OOXML_PRESENTATION = createConstant(str6, "vnd.openxmlformats-officedocument.presentationml.presentation");
        OOXML_SHEET = createConstant(str6, "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        OPENDOCUMENT_GRAPHICS = createConstant(str6, "vnd.oasis.opendocument.graphics");
        OPENDOCUMENT_PRESENTATION = createConstant(str6, "vnd.oasis.opendocument.presentation");
        OPENDOCUMENT_SPREADSHEET = createConstant(str6, "vnd.oasis.opendocument.spreadsheet");
        OPENDOCUMENT_TEXT = createConstant(str6, "vnd.oasis.opendocument.text");
        PDF = createConstant(str6, "pdf");
        POSTSCRIPT = createConstant(str6, "postscript");
        PROTOBUF = createConstant(str6, "protobuf");
        RDF_XML_UTF_8 = createConstantUtf8(str6, "rdf+xml");
        RTF_UTF_8 = createConstantUtf8(str6, "rtf");
        SFNT = createConstant(str6, "font-sfnt");
        SHOCKWAVE_FLASH = createConstant(str6, "x-shockwave-flash");
        SKETCHUP = createConstant(str6, "vnd.sketchup.skp");
        SOAP_XML_UTF_8 = createConstantUtf8(str6, "soap+xml");
        TAR = createConstant(str6, "x-tar");
        WOFF = createConstant(str6, "font-woff");
        WOFF2 = createConstant(str6, "font-woff2");
        XHTML_UTF_8 = createConstantUtf8(str6, "xhtml+xml");
        XRD_UTF_8 = createConstantUtf8(str6, "xrd+xml");
        ZIP = createConstant(str6, "zip");
    }

    private static MediaType createConstant(String str, String str2) {
        MediaType addKnownType = addKnownType(new MediaType(str, str2, ImmutableListMultimap.m1896of()));
        addKnownType.parsedCharset = Optional.absent();
        return addKnownType;
    }

    private static MediaType createConstantUtf8(String str, String str2) {
        MediaType addKnownType = addKnownType(new MediaType(str, str2, UTF_8_CONSTANT_PARAMETERS));
        addKnownType.parsedCharset = Optional.m1776of(Charsets.UTF_8);
        return addKnownType;
    }

    private static MediaType addKnownType(MediaType mediaType) {
        KNOWN_TYPES.put(mediaType, mediaType);
        return mediaType;
    }

    private MediaType(String str, String str2, ImmutableListMultimap<String, String> immutableListMultimap) {
        this.type = str;
        this.subtype = str2;
        this.parameters = immutableListMultimap;
    }

    public String type() {
        return this.type;
    }

    public String subtype() {
        return this.subtype;
    }

    public ImmutableListMultimap<String, String> parameters() {
        return this.parameters;
    }

    private Map<String, ImmutableMultiset<String>> parametersAsMap() {
        return Maps.transformValues((Map<K, V1>) this.parameters.asMap(), (Function<? super V1, V2>) new Function<Collection<String>, ImmutableMultiset<String>>() {
            public ImmutableMultiset<String> apply(Collection<String> collection) {
                return ImmutableMultiset.copyOf((Iterable<? extends E>) collection);
            }
        });
    }

    public Optional<Charset> charset() {
        Optional<Charset> optional = this.parsedCharset;
        if (optional == null) {
            Optional<Charset> absent = Optional.absent();
            UnmodifiableIterator it = this.parameters.get((Object) "charset").iterator();
            Optional<Charset> optional2 = absent;
            String str = null;
            optional = optional2;
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (str == null) {
                    optional = Optional.m1776of(Charset.forName(str2));
                    str = str2;
                } else if (!str.equals(str2)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Multiple charset values defined: ");
                    sb.append(str);
                    sb.append(", ");
                    sb.append(str2);
                    throw new IllegalStateException(sb.toString());
                }
            }
            this.parsedCharset = optional;
        }
        return optional;
    }

    public MediaType withoutParameters() {
        return this.parameters.isEmpty() ? this : create(this.type, this.subtype);
    }

    public MediaType withParameters(Multimap<String, String> multimap) {
        return create(this.type, this.subtype, multimap);
    }

    public MediaType withParameters(String str, Iterable<String> iterable) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(iterable);
        String normalizeToken = normalizeToken(str);
        Builder builder = ImmutableListMultimap.builder();
        UnmodifiableIterator it = this.parameters.entries().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            String str2 = (String) entry.getKey();
            if (!normalizeToken.equals(str2)) {
                builder.put(str2, entry.getValue());
            }
        }
        for (String normalizeParameterValue : iterable) {
            builder.put(normalizeToken, normalizeParameterValue(normalizeToken, normalizeParameterValue));
        }
        MediaType mediaType = new MediaType(this.type, this.subtype, builder.build());
        if (!normalizeToken.equals("charset")) {
            mediaType.parsedCharset = this.parsedCharset;
        }
        return (MediaType) MoreObjects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    public MediaType withParameter(String str, String str2) {
        return withParameters(str, ImmutableSet.m1926of(str2));
    }

    public MediaType withCharset(Charset charset) {
        Preconditions.checkNotNull(charset);
        MediaType withParameter = withParameter("charset", charset.name());
        withParameter.parsedCharset = Optional.m1776of(charset);
        return withParameter;
    }

    public boolean hasWildcard() {
        String str = "*";
        return str.equals(this.type) || str.equals(this.subtype);
    }

    /* renamed from: is */
    public boolean mo47677is(MediaType mediaType) {
        String str = "*";
        return (mediaType.type.equals(str) || mediaType.type.equals(this.type)) && (mediaType.subtype.equals(str) || mediaType.subtype.equals(this.subtype)) && this.parameters.entries().containsAll(mediaType.parameters.entries());
    }

    public static MediaType create(String str, String str2) {
        MediaType create = create(str, str2, ImmutableListMultimap.m1896of());
        create.parsedCharset = Optional.absent();
        return create;
    }

    private static MediaType create(String str, String str2, Multimap<String, String> multimap) {
        Preconditions.checkNotNull(str);
        Preconditions.checkNotNull(str2);
        Preconditions.checkNotNull(multimap);
        String normalizeToken = normalizeToken(str);
        String normalizeToken2 = normalizeToken(str2);
        String str3 = "*";
        Preconditions.checkArgument(!str3.equals(normalizeToken) || str3.equals(normalizeToken2), "A wildcard type cannot be used with a non-wildcard subtype");
        Builder builder = ImmutableListMultimap.builder();
        for (Entry entry : multimap.entries()) {
            String normalizeToken3 = normalizeToken((String) entry.getKey());
            builder.put(normalizeToken3, normalizeParameterValue(normalizeToken3, (String) entry.getValue()));
        }
        MediaType mediaType = new MediaType(normalizeToken, normalizeToken2, builder.build());
        return (MediaType) MoreObjects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    static MediaType createApplicationType(String str) {
        return create("application", str);
    }

    static MediaType createAudioType(String str) {
        return create("audio", str);
    }

    static MediaType createImageType(String str) {
        return create("image", str);
    }

    static MediaType createTextType(String str) {
        return create("text", str);
    }

    static MediaType createVideoType(String str) {
        return create("video", str);
    }

    private static String normalizeToken(String str) {
        Preconditions.checkArgument(TOKEN_MATCHER.matchesAllOf(str));
        return Ascii.toLowerCase(str);
    }

    private static String normalizeParameterValue(String str, String str2) {
        return "charset".equals(str) ? Ascii.toLowerCase(str2) : str2;
    }

    public static MediaType parse(String str) {
        String str2;
        Preconditions.checkNotNull(str);
        Tokenizer tokenizer = new Tokenizer(str);
        try {
            String consumeToken = tokenizer.consumeToken(TOKEN_MATCHER);
            tokenizer.consumeCharacter((char) JsonPointer.SEPARATOR);
            String consumeToken2 = tokenizer.consumeToken(TOKEN_MATCHER);
            Builder builder = ImmutableListMultimap.builder();
            while (tokenizer.hasMore()) {
                tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                tokenizer.consumeCharacter(';');
                tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                String consumeToken3 = tokenizer.consumeToken(TOKEN_MATCHER);
                tokenizer.consumeCharacter((char) SignatureVisitor.INSTANCEOF);
                if ('\"' == tokenizer.previewChar()) {
                    tokenizer.consumeCharacter('\"');
                    StringBuilder sb = new StringBuilder();
                    while ('\"' != tokenizer.previewChar()) {
                        if ('\\' == tokenizer.previewChar()) {
                            tokenizer.consumeCharacter((char) TokenParser.ESCAPE);
                            sb.append(tokenizer.consumeCharacter(CharMatcher.ascii()));
                        } else {
                            sb.append(tokenizer.consumeToken(QUOTED_TEXT_MATCHER));
                        }
                    }
                    str2 = sb.toString();
                    tokenizer.consumeCharacter('\"');
                } else {
                    str2 = tokenizer.consumeToken(TOKEN_MATCHER);
                }
                builder.put(consumeToken3, str2);
            }
            return create(consumeToken, consumeToken2, builder.build());
        } catch (IllegalStateException e) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Could not parse '");
            sb2.append(str);
            sb2.append("'");
            throw new IllegalArgumentException(sb2.toString(), e);
        }
    }

    public boolean equals(@NullableDecl Object obj) {
        boolean z = true;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MediaType)) {
            return false;
        }
        MediaType mediaType = (MediaType) obj;
        if (!this.type.equals(mediaType.type) || !this.subtype.equals(mediaType.subtype) || !parametersAsMap().equals(mediaType.parametersAsMap())) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        int i = this.hashCode;
        if (i != 0) {
            return i;
        }
        int hashCode2 = Objects.hashCode(this.type, this.subtype, parametersAsMap());
        this.hashCode = hashCode2;
        return hashCode2;
    }

    public String toString() {
        String str = this.toString;
        if (str != null) {
            return str;
        }
        String computeToString = computeToString();
        this.toString = computeToString;
        return computeToString;
    }

    private String computeToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.type);
        sb.append(JsonPointer.SEPARATOR);
        sb.append(this.subtype);
        if (!this.parameters.isEmpty()) {
            sb.append("; ");
            PARAMETER_JOINER.appendTo(sb, (Iterable<? extends Entry<?, ?>>) Multimaps.transformValues((ListMultimap<K, V1>) this.parameters, (Function<? super V1, V2>) new Function<String, String>() {
                public String apply(String str) {
                    return MediaType.TOKEN_MATCHER.matchesAllOf(str) ? str : MediaType.escapeAndQuote(str);
                }
            }).entries());
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public static String escapeAndQuote(String str) {
        StringBuilder sb = new StringBuilder(str.length() + 16);
        sb.append('\"');
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == 13 || charAt == '\\' || charAt == '\"') {
                sb.append(TokenParser.ESCAPE);
            }
            sb.append(charAt);
        }
        sb.append('\"');
        return sb.toString();
    }
}