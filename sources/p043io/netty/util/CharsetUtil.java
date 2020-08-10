package p043io.netty.util;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Map;
import org.apache.commons.codec.CharEncoding;
import p043io.fabric.sdk.android.services.network.HttpRequest;
import p043io.netty.util.internal.InternalThreadLocalMap;
import p043io.netty.util.internal.ObjectUtil;

/* renamed from: io.netty.util.CharsetUtil */
public final class CharsetUtil {
    private static final Charset[] CHARSETS = {UTF_16, UTF_16BE, UTF_16LE, UTF_8, ISO_8859_1, US_ASCII};
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    public static final Charset UTF_16 = Charset.forName("UTF-16");
    public static final Charset UTF_16BE = Charset.forName(CharEncoding.UTF_16BE);
    public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static Charset[] values() {
        return CHARSETS;
    }

    @Deprecated
    public static CharsetEncoder getEncoder(Charset charset) {
        return encoder(charset);
    }

    public static CharsetEncoder encoder(Charset charset, CodingErrorAction codingErrorAction, CodingErrorAction codingErrorAction2) {
        ObjectUtil.checkNotNull(charset, HttpRequest.PARAM_CHARSET);
        CharsetEncoder newEncoder = charset.newEncoder();
        newEncoder.onMalformedInput(codingErrorAction).onUnmappableCharacter(codingErrorAction2);
        return newEncoder;
    }

    public static CharsetEncoder encoder(Charset charset, CodingErrorAction codingErrorAction) {
        return encoder(charset, codingErrorAction, codingErrorAction);
    }

    public static CharsetEncoder encoder(Charset charset) {
        ObjectUtil.checkNotNull(charset, HttpRequest.PARAM_CHARSET);
        Map charsetEncoderCache = InternalThreadLocalMap.get().charsetEncoderCache();
        CharsetEncoder charsetEncoder = (CharsetEncoder) charsetEncoderCache.get(charset);
        if (charsetEncoder != null) {
            charsetEncoder.reset().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            return charsetEncoder;
        }
        CharsetEncoder encoder = encoder(charset, CodingErrorAction.REPLACE, CodingErrorAction.REPLACE);
        charsetEncoderCache.put(charset, encoder);
        return encoder;
    }

    @Deprecated
    public static CharsetDecoder getDecoder(Charset charset) {
        return decoder(charset);
    }

    public static CharsetDecoder decoder(Charset charset, CodingErrorAction codingErrorAction, CodingErrorAction codingErrorAction2) {
        ObjectUtil.checkNotNull(charset, HttpRequest.PARAM_CHARSET);
        CharsetDecoder newDecoder = charset.newDecoder();
        newDecoder.onMalformedInput(codingErrorAction).onUnmappableCharacter(codingErrorAction2);
        return newDecoder;
    }

    public static CharsetDecoder decoder(Charset charset, CodingErrorAction codingErrorAction) {
        return decoder(charset, codingErrorAction, codingErrorAction);
    }

    public static CharsetDecoder decoder(Charset charset) {
        ObjectUtil.checkNotNull(charset, HttpRequest.PARAM_CHARSET);
        Map charsetDecoderCache = InternalThreadLocalMap.get().charsetDecoderCache();
        CharsetDecoder charsetDecoder = (CharsetDecoder) charsetDecoderCache.get(charset);
        if (charsetDecoder != null) {
            charsetDecoder.reset().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            return charsetDecoder;
        }
        CharsetDecoder decoder = decoder(charset, CodingErrorAction.REPLACE, CodingErrorAction.REPLACE);
        charsetDecoderCache.put(charset, decoder);
        return decoder;
    }

    private CharsetUtil() {
    }
}
