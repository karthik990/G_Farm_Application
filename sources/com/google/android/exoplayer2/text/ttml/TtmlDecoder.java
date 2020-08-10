package com.google.android.exoplayer2.text.ttml;

import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public final class TtmlDecoder extends SimpleSubtitleDecoder {
    private static final String ATTR_BEGIN = "begin";
    private static final String ATTR_DURATION = "dur";
    private static final String ATTR_END = "end";
    private static final String ATTR_IMAGE = "backgroundImage";
    private static final String ATTR_REGION = "region";
    private static final String ATTR_STYLE = "style";
    private static final Pattern CELL_RESOLUTION = Pattern.compile("^(\\d+) (\\d+)$");
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final CellResolution DEFAULT_CELL_RESOLUTION = new CellResolution(32, 15);
    private static final FrameAndTickRate DEFAULT_FRAME_AND_TICK_RATE = new FrameAndTickRate(30.0f, 1, 1);
    private static final int DEFAULT_FRAME_RATE = 30;
    private static final Pattern FONT_SIZE = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    private static final Pattern PERCENTAGE_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)% (\\d+\\.?\\d*?)%$");
    private static final Pattern PIXEL_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)px (\\d+\\.?\\d*?)px$");
    private static final String TAG = "TtmlDecoder";
    private static final String TTP = "http://www.w3.org/ns/ttml#parameter";
    private final XmlPullParserFactory xmlParserFactory;

    private static final class CellResolution {
        final int columns;
        final int rows;

        CellResolution(int i, int i2) {
            this.columns = i;
            this.rows = i2;
        }
    }

    private static final class FrameAndTickRate {
        final float effectiveFrameRate;
        final int subFrameRate;
        final int tickRate;

        FrameAndTickRate(float f, int i, int i2) {
            this.effectiveFrameRate = f;
            this.subFrameRate = i;
            this.tickRate = i2;
        }
    }

    private static final class TtsExtent {
        final int height;
        final int width;

        TtsExtent(int i, int i2) {
            this.width = i;
            this.height = i2;
        }
    }

    public TtmlDecoder() {
        super(TAG);
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
            this.xmlParserFactory.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    /* access modifiers changed from: protected */
    public Subtitle decode(byte[] bArr, int i, boolean z) throws SubtitleDecoderException {
        TtsExtent ttsExtent;
        CellResolution cellResolution;
        FrameAndTickRate frameAndTickRate;
        Subtitle subtitle;
        FrameAndTickRate frameAndTickRate2;
        try {
            XmlPullParser newPullParser = this.xmlParserFactory.newPullParser();
            HashMap hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            HashMap hashMap3 = new HashMap();
            TtsExtent ttsExtent2 = null;
            hashMap2.put("", new TtmlRegion(null));
            newPullParser.setInput(new ByteArrayInputStream(bArr, 0, i), null);
            ArrayDeque arrayDeque = new ArrayDeque();
            FrameAndTickRate frameAndTickRate3 = DEFAULT_FRAME_AND_TICK_RATE;
            CellResolution cellResolution2 = DEFAULT_CELL_RESOLUTION;
            Subtitle subtitle2 = null;
            int i2 = 0;
            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.getEventType()) {
                TtmlNode ttmlNode = (TtmlNode) arrayDeque.peek();
                if (i2 == 0) {
                    String name = newPullParser.getName();
                    String str = TtmlNode.TAG_TT;
                    if (eventType == 2) {
                        if (str.equals(name)) {
                            FrameAndTickRate parseFrameAndTickRates = parseFrameAndTickRates(newPullParser);
                            cellResolution = parseCellResolution(newPullParser, DEFAULT_CELL_RESOLUTION);
                            ttsExtent = parseTtsExtent(newPullParser);
                            frameAndTickRate = parseFrameAndTickRates;
                        } else {
                            ttsExtent = ttsExtent2;
                            frameAndTickRate = frameAndTickRate3;
                            cellResolution = cellResolution2;
                        }
                        boolean isSupportedTag = isSupportedTag(name);
                        String str2 = TAG;
                        if (!isSupportedTag) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ignoring unsupported tag: ");
                            sb.append(newPullParser.getName());
                            Log.m1394i(str2, sb.toString());
                            i2++;
                            frameAndTickRate3 = frameAndTickRate;
                        } else {
                            if (TtmlNode.TAG_HEAD.equals(name)) {
                                subtitle = subtitle2;
                                frameAndTickRate2 = frameAndTickRate;
                                parseHeader(newPullParser, hashMap, cellResolution, ttsExtent, hashMap2, hashMap3);
                            } else {
                                subtitle = subtitle2;
                                frameAndTickRate2 = frameAndTickRate;
                                try {
                                    TtmlNode parseNode = parseNode(newPullParser, ttmlNode, hashMap2, frameAndTickRate2);
                                    arrayDeque.push(parseNode);
                                    if (ttmlNode != null) {
                                        ttmlNode.addChild(parseNode);
                                    }
                                } catch (SubtitleDecoderException e) {
                                    Log.m1397w(str2, "Suppressing parser error", e);
                                    i2++;
                                }
                            }
                            frameAndTickRate3 = frameAndTickRate2;
                            subtitle2 = subtitle;
                        }
                    } else {
                        Subtitle subtitle3 = subtitle2;
                        if (eventType == 4) {
                            ttmlNode.addChild(TtmlNode.buildTextNode(newPullParser.getText()));
                        } else if (eventType == 3) {
                            subtitle2 = newPullParser.getName().equals(str) ? new TtmlSubtitle((TtmlNode) arrayDeque.peek(), hashMap, hashMap2, hashMap3) : subtitle3;
                            arrayDeque.pop();
                            ttsExtent = ttsExtent2;
                            cellResolution = cellResolution2;
                        }
                        subtitle2 = subtitle3;
                        ttsExtent = ttsExtent2;
                        cellResolution = cellResolution2;
                    }
                    cellResolution2 = cellResolution;
                    ttsExtent2 = ttsExtent;
                } else {
                    Subtitle subtitle4 = subtitle2;
                    if (eventType == 2) {
                        i2++;
                    } else if (eventType == 3) {
                        i2--;
                    }
                    subtitle2 = subtitle4;
                }
                newPullParser.next();
            }
            return subtitle2;
        } catch (XmlPullParserException e2) {
            throw new SubtitleDecoderException("Unable to decode source", e2);
        } catch (IOException e3) {
            throw new IllegalStateException("Unexpected error when reading input.", e3);
        }
    }

    private FrameAndTickRate parseFrameAndTickRates(XmlPullParser xmlPullParser) throws SubtitleDecoderException {
        String str = TTP;
        String attributeValue = xmlPullParser.getAttributeValue(str, "frameRate");
        int parseInt = attributeValue != null ? Integer.parseInt(attributeValue) : 30;
        float f = 1.0f;
        String attributeValue2 = xmlPullParser.getAttributeValue(str, "frameRateMultiplier");
        if (attributeValue2 != null) {
            String[] split = Util.split(attributeValue2, " ");
            if (split.length == 2) {
                f = ((float) Integer.parseInt(split[0])) / ((float) Integer.parseInt(split[1]));
            } else {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
        }
        int i = DEFAULT_FRAME_AND_TICK_RATE.subFrameRate;
        String attributeValue3 = xmlPullParser.getAttributeValue(str, "subFrameRate");
        if (attributeValue3 != null) {
            i = Integer.parseInt(attributeValue3);
        }
        int i2 = DEFAULT_FRAME_AND_TICK_RATE.tickRate;
        String attributeValue4 = xmlPullParser.getAttributeValue(str, "tickRate");
        if (attributeValue4 != null) {
            i2 = Integer.parseInt(attributeValue4);
        }
        return new FrameAndTickRate(((float) parseInt) * f, i, i2);
    }

    private CellResolution parseCellResolution(XmlPullParser xmlPullParser, CellResolution cellResolution) throws SubtitleDecoderException {
        String attributeValue = xmlPullParser.getAttributeValue(TTP, "cellResolution");
        if (attributeValue == null) {
            return cellResolution;
        }
        Matcher matcher = CELL_RESOLUTION.matcher(attributeValue);
        boolean matches = matcher.matches();
        String str = "Ignoring malformed cell resolution: ";
        String str2 = TAG;
        if (!matches) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(attributeValue);
            Log.m1396w(str2, sb.toString());
            return cellResolution;
        }
        try {
            int parseInt = Integer.parseInt(matcher.group(1));
            int parseInt2 = Integer.parseInt(matcher.group(2));
            if (parseInt != 0 && parseInt2 != 0) {
                return new CellResolution(parseInt, parseInt2);
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Invalid cell resolution ");
            sb2.append(parseInt);
            sb2.append(" ");
            sb2.append(parseInt2);
            throw new SubtitleDecoderException(sb2.toString());
        } catch (NumberFormatException unused) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append(attributeValue);
            Log.m1396w(str2, sb3.toString());
            return cellResolution;
        }
    }

    private TtsExtent parseTtsExtent(XmlPullParser xmlPullParser) {
        String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, TtmlNode.ATTR_TTS_EXTENT);
        if (attributeValue == null) {
            return null;
        }
        Matcher matcher = PIXEL_COORDINATES.matcher(attributeValue);
        boolean matches = matcher.matches();
        String str = TAG;
        if (!matches) {
            StringBuilder sb = new StringBuilder();
            sb.append("Ignoring non-pixel tts extent: ");
            sb.append(attributeValue);
            Log.m1396w(str, sb.toString());
            return null;
        }
        try {
            return new TtsExtent(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
        } catch (NumberFormatException unused) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Ignoring malformed tts extent: ");
            sb2.append(attributeValue);
            Log.m1396w(str, sb2.toString());
            return null;
        }
    }

    private Map<String, TtmlStyle> parseHeader(XmlPullParser xmlPullParser, Map<String, TtmlStyle> map, CellResolution cellResolution, TtsExtent ttsExtent, Map<String, TtmlRegion> map2, Map<String, String> map3) throws IOException, XmlPullParserException {
        do {
            xmlPullParser.next();
            String str = "style";
            if (XmlPullParserUtil.isStartTag(xmlPullParser, str)) {
                String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, str);
                TtmlStyle parseStyleAttributes = parseStyleAttributes(xmlPullParser, new TtmlStyle());
                if (attributeValue != null) {
                    for (String str2 : parseStyleIds(attributeValue)) {
                        parseStyleAttributes.chain((TtmlStyle) map.get(str2));
                    }
                }
                if (parseStyleAttributes.getId() != null) {
                    map.put(parseStyleAttributes.getId(), parseStyleAttributes);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "region")) {
                TtmlRegion parseRegionAttributes = parseRegionAttributes(xmlPullParser, cellResolution, ttsExtent);
                if (parseRegionAttributes != null) {
                    map2.put(parseRegionAttributes.f1526id, parseRegionAttributes);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, TtmlNode.TAG_METADATA)) {
                parseMetadata(xmlPullParser, map3);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, TtmlNode.TAG_HEAD));
        return map;
    }

    private void parseMetadata(XmlPullParser xmlPullParser, Map<String, String> map) throws IOException, XmlPullParserException {
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, TtmlNode.TAG_IMAGE)) {
                String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, TtmlNode.ATTR_ID);
                if (attributeValue != null) {
                    map.put(attributeValue, xmlPullParser.nextText());
                }
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, TtmlNode.TAG_METADATA));
    }

    private TtmlRegion parseRegionAttributes(XmlPullParser xmlPullParser, CellResolution cellResolution, TtsExtent ttsExtent) {
        float f;
        float f2;
        float f3;
        float f4;
        int i;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        TtsExtent ttsExtent2 = ttsExtent;
        String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser2, TtmlNode.ATTR_ID);
        if (attributeValue == null) {
            return null;
        }
        String attributeValue2 = XmlPullParserUtil.getAttributeValue(xmlPullParser2, "origin");
        String str = TAG;
        if (attributeValue2 != null) {
            Matcher matcher = PERCENTAGE_COORDINATES.matcher(attributeValue2);
            Matcher matcher2 = PIXEL_COORDINATES.matcher(attributeValue2);
            String str2 = "Ignoring region with malformed origin: ";
            String str3 = "Ignoring region with missing tts:extent: ";
            if (matcher.matches()) {
                try {
                    float parseFloat = Float.parseFloat(matcher.group(1)) / 100.0f;
                    f = Float.parseFloat(matcher.group(2)) / 100.0f;
                    f2 = parseFloat;
                } catch (NumberFormatException unused) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str2);
                    sb.append(attributeValue2);
                    Log.m1396w(str, sb.toString());
                    return null;
                }
            } else if (!matcher2.matches()) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Ignoring region with unsupported origin: ");
                sb2.append(attributeValue2);
                Log.m1396w(str, sb2.toString());
                return null;
            } else if (ttsExtent2 == null) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(str3);
                sb3.append(attributeValue2);
                Log.m1396w(str, sb3.toString());
                return null;
            } else {
                try {
                    int parseInt = Integer.parseInt(matcher2.group(1));
                    f2 = ((float) parseInt) / ((float) ttsExtent2.width);
                    f = ((float) Integer.parseInt(matcher2.group(2))) / ((float) ttsExtent2.height);
                } catch (NumberFormatException unused2) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str2);
                    sb4.append(attributeValue2);
                    Log.m1396w(str, sb4.toString());
                    return null;
                }
            }
            String attributeValue3 = XmlPullParserUtil.getAttributeValue(xmlPullParser2, TtmlNode.ATTR_TTS_EXTENT);
            if (attributeValue3 != null) {
                Matcher matcher3 = PERCENTAGE_COORDINATES.matcher(attributeValue3);
                Matcher matcher4 = PIXEL_COORDINATES.matcher(attributeValue3);
                String str4 = "Ignoring region with malformed extent: ";
                if (matcher3.matches()) {
                    try {
                        f4 = Float.parseFloat(matcher3.group(1)) / 100.0f;
                        f3 = Float.parseFloat(matcher3.group(2)) / 100.0f;
                    } catch (NumberFormatException unused3) {
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append(str4);
                        sb5.append(attributeValue2);
                        Log.m1396w(str, sb5.toString());
                        return null;
                    }
                } else if (!matcher4.matches()) {
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("Ignoring region with unsupported extent: ");
                    sb6.append(attributeValue2);
                    Log.m1396w(str, sb6.toString());
                    return null;
                } else if (ttsExtent2 == null) {
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append(str3);
                    sb7.append(attributeValue2);
                    Log.m1396w(str, sb7.toString());
                    return null;
                } else {
                    try {
                        int parseInt2 = Integer.parseInt(matcher4.group(1));
                        f4 = ((float) parseInt2) / ((float) ttsExtent2.width);
                        f3 = ((float) Integer.parseInt(matcher4.group(2))) / ((float) ttsExtent2.height);
                    } catch (NumberFormatException unused4) {
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append(str4);
                        sb8.append(attributeValue2);
                        Log.m1396w(str, sb8.toString());
                        return null;
                    }
                }
                String attributeValue4 = XmlPullParserUtil.getAttributeValue(xmlPullParser2, TtmlNode.ATTR_TTS_DISPLAY_ALIGN);
                if (attributeValue4 != null) {
                    String lowerInvariant = Util.toLowerInvariant(attributeValue4);
                    char c = 65535;
                    int hashCode = lowerInvariant.hashCode();
                    if (hashCode != -1364013995) {
                        if (hashCode == 92734940 && lowerInvariant.equals("after")) {
                            c = 1;
                        }
                    } else if (lowerInvariant.equals(TtmlNode.CENTER)) {
                        c = 0;
                    }
                    if (c == 0) {
                        f += f3 / 2.0f;
                        i = 1;
                    } else if (c == 1) {
                        f += f3;
                        i = 2;
                    }
                    TtmlRegion ttmlRegion = new TtmlRegion(attributeValue, f2, f, 0, i, f4, f3, 1, 1.0f / ((float) cellResolution.rows));
                    return ttmlRegion;
                }
                i = 0;
                TtmlRegion ttmlRegion2 = new TtmlRegion(attributeValue, f2, f, 0, i, f4, f3, 1, 1.0f / ((float) cellResolution.rows));
                return ttmlRegion2;
            }
            Log.m1396w(str, "Ignoring region without an extent");
            return null;
        }
        Log.m1396w(str, "Ignoring region without an origin");
        return null;
    }

    private String[] parseStyleIds(String str) {
        String trim = str.trim();
        return trim.isEmpty() ? new String[0] : Util.split(trim, "\\s+");
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.exoplayer2.text.ttml.TtmlStyle parseStyleAttributes(org.xmlpull.v1.XmlPullParser r12, com.google.android.exoplayer2.text.ttml.TtmlStyle r13) {
        /*
            r11 = this;
            int r0 = r12.getAttributeCount()
            r1 = 0
            r2 = r13
            r13 = 0
        L_0x0007:
            if (r13 >= r0) goto L_0x020c
            java.lang.String r3 = r12.getAttributeValue(r13)
            java.lang.String r4 = r12.getAttributeName(r13)
            int r5 = r4.hashCode()
            r6 = 4
            r7 = -1
            r8 = 2
            r9 = 3
            r10 = 1
            switch(r5) {
                case -1550943582: goto L_0x006f;
                case -1224696685: goto L_0x0065;
                case -1065511464: goto L_0x005b;
                case -879295043: goto L_0x0050;
                case -734428249: goto L_0x0046;
                case 3355: goto L_0x003c;
                case 94842723: goto L_0x0032;
                case 365601008: goto L_0x0028;
                case 1287124693: goto L_0x001e;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x0079
        L_0x001e:
            java.lang.String r5 = "backgroundColor"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 1
            goto L_0x007a
        L_0x0028:
            java.lang.String r5 = "fontSize"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 4
            goto L_0x007a
        L_0x0032:
            java.lang.String r5 = "color"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 2
            goto L_0x007a
        L_0x003c:
            java.lang.String r5 = "id"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 0
            goto L_0x007a
        L_0x0046:
            java.lang.String r5 = "fontWeight"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 5
            goto L_0x007a
        L_0x0050:
            java.lang.String r5 = "textDecoration"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 8
            goto L_0x007a
        L_0x005b:
            java.lang.String r5 = "textAlign"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 7
            goto L_0x007a
        L_0x0065:
            java.lang.String r5 = "fontFamily"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 3
            goto L_0x007a
        L_0x006f:
            java.lang.String r5 = "fontStyle"
            boolean r4 = r4.equals(r5)
            if (r4 == 0) goto L_0x0079
            r4 = 6
            goto L_0x007a
        L_0x0079:
            r4 = -1
        L_0x007a:
            java.lang.String r5 = "TtmlDecoder"
            switch(r4) {
                case 0: goto L_0x01f4;
                case 1: goto L_0x01d3;
                case 2: goto L_0x01b2;
                case 3: goto L_0x01a9;
                case 4: goto L_0x018b;
                case 5: goto L_0x017b;
                case 6: goto L_0x016b;
                case 7: goto L_0x00e6;
                case 8: goto L_0x0081;
                default: goto L_0x007f;
            }
        L_0x007f:
            goto L_0x0208
        L_0x0081:
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r3)
            int r4 = r3.hashCode()
            switch(r4) {
                case -1461280213: goto L_0x00ab;
                case -1026963764: goto L_0x00a1;
                case 913457136: goto L_0x0097;
                case 1679736913: goto L_0x008d;
                default: goto L_0x008c;
            }
        L_0x008c:
            goto L_0x00b4
        L_0x008d:
            java.lang.String r4 = "linethrough"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x00b4
            r7 = 0
            goto L_0x00b4
        L_0x0097:
            java.lang.String r4 = "nolinethrough"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x00b4
            r7 = 1
            goto L_0x00b4
        L_0x00a1:
            java.lang.String r4 = "underline"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x00b4
            r7 = 2
            goto L_0x00b4
        L_0x00ab:
            java.lang.String r4 = "nounderline"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x00b4
            r7 = 3
        L_0x00b4:
            if (r7 == 0) goto L_0x00dc
            if (r7 == r10) goto L_0x00d2
            if (r7 == r8) goto L_0x00c8
            if (r7 == r9) goto L_0x00be
            goto L_0x0208
        L_0x00be:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setUnderline(r1)
            goto L_0x0208
        L_0x00c8:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setUnderline(r10)
            goto L_0x0208
        L_0x00d2:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setLinethrough(r1)
            goto L_0x0208
        L_0x00dc:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setLinethrough(r10)
            goto L_0x0208
        L_0x00e6:
            java.lang.String r3 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r3)
            int r4 = r3.hashCode()
            switch(r4) {
                case -1364013995: goto L_0x011a;
                case 100571: goto L_0x0110;
                case 3317767: goto L_0x0106;
                case 108511772: goto L_0x00fc;
                case 109757538: goto L_0x00f2;
                default: goto L_0x00f1;
            }
        L_0x00f1:
            goto L_0x0123
        L_0x00f2:
            java.lang.String r4 = "start"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0123
            r7 = 1
            goto L_0x0123
        L_0x00fc:
            java.lang.String r4 = "right"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0123
            r7 = 2
            goto L_0x0123
        L_0x0106:
            java.lang.String r4 = "left"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0123
            r7 = 0
            goto L_0x0123
        L_0x0110:
            java.lang.String r4 = "end"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0123
            r7 = 3
            goto L_0x0123
        L_0x011a:
            java.lang.String r4 = "center"
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0123
            r7 = 4
        L_0x0123:
            if (r7 == 0) goto L_0x015f
            if (r7 == r10) goto L_0x0153
            if (r7 == r8) goto L_0x0147
            if (r7 == r9) goto L_0x013b
            if (r7 == r6) goto L_0x012f
            goto L_0x0208
        L_0x012f:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            android.text.Layout$Alignment r3 = android.text.Layout.Alignment.ALIGN_CENTER
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setTextAlign(r3)
            goto L_0x0208
        L_0x013b:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            android.text.Layout$Alignment r3 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setTextAlign(r3)
            goto L_0x0208
        L_0x0147:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            android.text.Layout$Alignment r3 = android.text.Layout.Alignment.ALIGN_OPPOSITE
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setTextAlign(r3)
            goto L_0x0208
        L_0x0153:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            android.text.Layout$Alignment r3 = android.text.Layout.Alignment.ALIGN_NORMAL
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setTextAlign(r3)
            goto L_0x0208
        L_0x015f:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            android.text.Layout$Alignment r3 = android.text.Layout.Alignment.ALIGN_NORMAL
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setTextAlign(r3)
            goto L_0x0208
        L_0x016b:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            java.lang.String r4 = "italic"
            boolean r3 = r4.equalsIgnoreCase(r3)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setItalic(r3)
            goto L_0x0208
        L_0x017b:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            java.lang.String r4 = "bold"
            boolean r3 = r4.equalsIgnoreCase(r3)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setBold(r3)
            goto L_0x0208
        L_0x018b:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)     // Catch:{ SubtitleDecoderException -> 0x0194 }
            parseFontSize(r3, r2)     // Catch:{ SubtitleDecoderException -> 0x0194 }
            goto L_0x0208
        L_0x0194:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Failed parsing fontSize value: "
            r4.append(r6)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            com.google.android.exoplayer2.util.Log.m1396w(r5, r3)
            goto L_0x0208
        L_0x01a9:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setFontFamily(r3)
            goto L_0x0208
        L_0x01b2:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            int r4 = com.google.android.exoplayer2.util.ColorParser.parseTtmlColor(r3)     // Catch:{ IllegalArgumentException -> 0x01be }
            r2.setFontColor(r4)     // Catch:{ IllegalArgumentException -> 0x01be }
            goto L_0x0208
        L_0x01be:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Failed parsing color value: "
            r4.append(r6)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            com.google.android.exoplayer2.util.Log.m1396w(r5, r3)
            goto L_0x0208
        L_0x01d3:
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            int r4 = com.google.android.exoplayer2.util.ColorParser.parseTtmlColor(r3)     // Catch:{ IllegalArgumentException -> 0x01df }
            r2.setBackgroundColor(r4)     // Catch:{ IllegalArgumentException -> 0x01df }
            goto L_0x0208
        L_0x01df:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "Failed parsing background value: "
            r4.append(r6)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            com.google.android.exoplayer2.util.Log.m1396w(r5, r3)
            goto L_0x0208
        L_0x01f4:
            java.lang.String r4 = r12.getName()
            java.lang.String r5 = "style"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L_0x0208
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r11.createIfNull(r2)
            com.google.android.exoplayer2.text.ttml.TtmlStyle r2 = r2.setId(r3)
        L_0x0208:
            int r13 = r13 + 1
            goto L_0x0007
        L_0x020c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseStyleAttributes(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlStyle):com.google.android.exoplayer2.text.ttml.TtmlStyle");
    }

    private TtmlStyle createIfNull(TtmlStyle ttmlStyle) {
        return ttmlStyle == null ? new TtmlStyle() : ttmlStyle;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.exoplayer2.text.ttml.TtmlNode parseNode(org.xmlpull.v1.XmlPullParser r21, com.google.android.exoplayer2.text.ttml.TtmlNode r22, java.util.Map<java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlRegion> r23, com.google.android.exoplayer2.text.ttml.TtmlDecoder.FrameAndTickRate r24) throws com.google.android.exoplayer2.text.SubtitleDecoderException {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r2 = r22
            r3 = r24
            int r4 = r21.getAttributeCount()
            r5 = 0
            com.google.android.exoplayer2.text.ttml.TtmlStyle r11 = r0.parseStyleAttributes(r1, r5)
            java.lang.String r9 = ""
            r17 = r5
            r18 = r17
            r16 = r9
            r5 = 0
            r9 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r12 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r14 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x0029:
            if (r5 >= r4) goto L_0x00ce
            java.lang.String r6 = r1.getAttributeName(r5)
            java.lang.String r7 = r1.getAttributeValue(r5)
            int r19 = r6.hashCode()
            switch(r19) {
                case -934795532: goto L_0x006d;
                case 99841: goto L_0x0063;
                case 100571: goto L_0x0059;
                case 93616297: goto L_0x004f;
                case 109780401: goto L_0x0045;
                case 1292595405: goto L_0x003b;
                default: goto L_0x003a;
            }
        L_0x003a:
            goto L_0x0077
        L_0x003b:
            java.lang.String r8 = "backgroundImage"
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L_0x0077
            r6 = 5
            goto L_0x0078
        L_0x0045:
            java.lang.String r8 = "style"
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L_0x0077
            r6 = 3
            goto L_0x0078
        L_0x004f:
            java.lang.String r8 = "begin"
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L_0x0077
            r6 = 0
            goto L_0x0078
        L_0x0059:
            java.lang.String r8 = "end"
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L_0x0077
            r6 = 1
            goto L_0x0078
        L_0x0063:
            java.lang.String r8 = "dur"
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L_0x0077
            r6 = 2
            goto L_0x0078
        L_0x006d:
            java.lang.String r8 = "region"
            boolean r6 = r6.equals(r8)
            if (r6 == 0) goto L_0x0077
            r6 = 4
            goto L_0x0078
        L_0x0077:
            r6 = -1
        L_0x0078:
            if (r6 == 0) goto L_0x00c3
            r8 = 1
            if (r6 == r8) goto L_0x00bb
            r8 = 2
            if (r6 == r8) goto L_0x00b3
            r8 = 3
            if (r6 == r8) goto L_0x00a7
            r8 = 4
            if (r6 == r8) goto L_0x009c
            r8 = 5
            if (r6 == r8) goto L_0x008a
            goto L_0x0099
        L_0x008a:
            java.lang.String r6 = "#"
            boolean r6 = r7.startsWith(r6)
            if (r6 == 0) goto L_0x0099
            r6 = 1
            java.lang.String r6 = r7.substring(r6)
            r17 = r6
        L_0x0099:
            r6 = r23
            goto L_0x00ca
        L_0x009c:
            r6 = r23
            boolean r8 = r6.containsKey(r7)
            if (r8 == 0) goto L_0x00ca
            r16 = r7
            goto L_0x00ca
        L_0x00a7:
            r6 = r23
            java.lang.String[] r7 = r0.parseStyleIds(r7)
            int r8 = r7.length
            if (r8 <= 0) goto L_0x00ca
            r18 = r7
            goto L_0x00ca
        L_0x00b3:
            r6 = r23
            long r7 = parseTimeExpression(r7, r3)
            r14 = r7
            goto L_0x00ca
        L_0x00bb:
            r6 = r23
            long r7 = parseTimeExpression(r7, r3)
            r12 = r7
            goto L_0x00ca
        L_0x00c3:
            r6 = r23
            long r7 = parseTimeExpression(r7, r3)
            r9 = r7
        L_0x00ca:
            int r5 = r5 + 1
            goto L_0x0029
        L_0x00ce:
            if (r2 == 0) goto L_0x00ea
            long r3 = r2.startTimeUs
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x00ef
            int r3 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x00e2
            long r3 = r2.startTimeUs
            long r9 = r9 + r3
        L_0x00e2:
            int r3 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x00ef
            long r3 = r2.startTimeUs
            long r12 = r12 + r3
            goto L_0x00ef
        L_0x00ea:
            r5 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
        L_0x00ef:
            r7 = r9
            int r3 = (r12 > r5 ? 1 : (r12 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x0107
            int r3 = (r14 > r5 ? 1 : (r14 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x00fb
            long r14 = r14 + r7
            r9 = r14
            goto L_0x0108
        L_0x00fb:
            if (r2 == 0) goto L_0x0107
            long r3 = r2.endTimeUs
            int r9 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r9 == 0) goto L_0x0107
            long r2 = r2.endTimeUs
            r9 = r2
            goto L_0x0108
        L_0x0107:
            r9 = r12
        L_0x0108:
            java.lang.String r6 = r21.getName()
            r12 = r18
            r13 = r16
            r14 = r17
            com.google.android.exoplayer2.text.ttml.TtmlNode r1 = com.google.android.exoplayer2.text.ttml.TtmlNode.buildNode(r6, r7, r9, r11, r12, r13, r14)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseNode(org.xmlpull.v1.XmlPullParser, com.google.android.exoplayer2.text.ttml.TtmlNode, java.util.Map, com.google.android.exoplayer2.text.ttml.TtmlDecoder$FrameAndTickRate):com.google.android.exoplayer2.text.ttml.TtmlNode");
    }

    private static boolean isSupportedTag(String str) {
        return str.equals(TtmlNode.TAG_TT) || str.equals(TtmlNode.TAG_HEAD) || str.equals(TtmlNode.TAG_BODY) || str.equals(TtmlNode.TAG_DIV) || str.equals(TtmlNode.TAG_P) || str.equals(TtmlNode.TAG_SPAN) || str.equals(TtmlNode.TAG_BR) || str.equals("style") || str.equals(TtmlNode.TAG_STYLING) || str.equals(TtmlNode.TAG_LAYOUT) || str.equals("region") || str.equals(TtmlNode.TAG_METADATA) || str.equals(TtmlNode.TAG_IMAGE) || str.equals("data") || str.equals(TtmlNode.TAG_INFORMATION);
    }

    private static void parseFontSize(String str, TtmlStyle ttmlStyle) throws SubtitleDecoderException {
        Matcher matcher;
        String[] split = Util.split(str, "\\s+");
        if (split.length == 1) {
            matcher = FONT_SIZE.matcher(str);
        } else if (split.length == 2) {
            matcher = FONT_SIZE.matcher(split[1]);
            Log.m1396w(TAG, "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid number of entries for fontSize: ");
            sb.append(split.length);
            sb.append(".");
            throw new SubtitleDecoderException(sb.toString());
        }
        String str2 = "'.";
        if (matcher.matches()) {
            String group = matcher.group(3);
            char c = 65535;
            int hashCode = group.hashCode();
            if (hashCode != 37) {
                if (hashCode != 3240) {
                    if (hashCode == 3592 && group.equals("px")) {
                        c = 0;
                    }
                } else if (group.equals("em")) {
                    c = 1;
                }
            } else if (group.equals("%")) {
                c = 2;
            }
            if (c == 0) {
                ttmlStyle.setFontSizeUnit(1);
            } else if (c == 1) {
                ttmlStyle.setFontSizeUnit(2);
            } else if (c == 2) {
                ttmlStyle.setFontSizeUnit(3);
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Invalid unit for fontSize: '");
                sb2.append(group);
                sb2.append(str2);
                throw new SubtitleDecoderException(sb2.toString());
            }
            ttmlStyle.setFontSize(Float.valueOf(matcher.group(1)).floatValue());
            return;
        }
        StringBuilder sb3 = new StringBuilder();
        sb3.append("Invalid expression for fontSize: '");
        sb3.append(str);
        sb3.append(str2);
        throw new SubtitleDecoderException(sb3.toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x00fd  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0120  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static long parseTimeExpression(java.lang.String r14, com.google.android.exoplayer2.text.ttml.TtmlDecoder.FrameAndTickRate r15) throws com.google.android.exoplayer2.text.SubtitleDecoderException {
        /*
            java.util.regex.Pattern r0 = CLOCK_TIME
            java.util.regex.Matcher r0 = r0.matcher(r14)
            boolean r1 = r0.matches()
            r2 = 4696837146684686336(0x412e848000000000, double:1000000.0)
            r4 = 5
            r5 = 4
            r6 = 3
            r7 = 2
            r8 = 1
            if (r1 == 0) goto L_0x0088
            java.lang.String r14 = r0.group(r8)
            long r8 = java.lang.Long.parseLong(r14)
            r10 = 3600(0xe10, double:1.7786E-320)
            long r8 = r8 * r10
            double r8 = (double) r8
            java.lang.String r14 = r0.group(r7)
            long r10 = java.lang.Long.parseLong(r14)
            r12 = 60
            long r10 = r10 * r12
            double r10 = (double) r10
            java.lang.Double.isNaN(r8)
            java.lang.Double.isNaN(r10)
            double r8 = r8 + r10
            java.lang.String r14 = r0.group(r6)
            long r6 = java.lang.Long.parseLong(r14)
            double r6 = (double) r6
            java.lang.Double.isNaN(r6)
            double r8 = r8 + r6
            java.lang.String r14 = r0.group(r5)
            r5 = 0
            if (r14 == 0) goto L_0x0051
            double r10 = java.lang.Double.parseDouble(r14)
            goto L_0x0052
        L_0x0051:
            r10 = r5
        L_0x0052:
            double r8 = r8 + r10
            java.lang.String r14 = r0.group(r4)
            if (r14 == 0) goto L_0x0063
            long r10 = java.lang.Long.parseLong(r14)
            float r14 = (float) r10
            float r1 = r15.effectiveFrameRate
            float r14 = r14 / r1
            double r10 = (double) r14
            goto L_0x0064
        L_0x0063:
            r10 = r5
        L_0x0064:
            double r8 = r8 + r10
            r14 = 6
            java.lang.String r14 = r0.group(r14)
            if (r14 == 0) goto L_0x0083
            long r0 = java.lang.Long.parseLong(r14)
            double r0 = (double) r0
            int r14 = r15.subFrameRate
            double r4 = (double) r14
            java.lang.Double.isNaN(r0)
            java.lang.Double.isNaN(r4)
            double r0 = r0 / r4
            float r14 = r15.effectiveFrameRate
            double r14 = (double) r14
            java.lang.Double.isNaN(r14)
            double r5 = r0 / r14
        L_0x0083:
            double r8 = r8 + r5
            double r8 = r8 * r2
            long r14 = (long) r8
            return r14
        L_0x0088:
            java.util.regex.Pattern r0 = OFFSET_TIME
            java.util.regex.Matcher r0 = r0.matcher(r14)
            boolean r1 = r0.matches()
            if (r1 == 0) goto L_0x012b
            java.lang.String r14 = r0.group(r8)
            double r9 = java.lang.Double.parseDouble(r14)
            java.lang.String r14 = r0.group(r7)
            r0 = -1
            int r1 = r14.hashCode()
            r11 = 102(0x66, float:1.43E-43)
            if (r1 == r11) goto L_0x00f0
            r11 = 104(0x68, float:1.46E-43)
            if (r1 == r11) goto L_0x00e6
            r11 = 109(0x6d, float:1.53E-43)
            if (r1 == r11) goto L_0x00dc
            r11 = 3494(0xda6, float:4.896E-42)
            if (r1 == r11) goto L_0x00d2
            r11 = 115(0x73, float:1.61E-43)
            if (r1 == r11) goto L_0x00c8
            r11 = 116(0x74, float:1.63E-43)
            if (r1 == r11) goto L_0x00be
            goto L_0x00fa
        L_0x00be:
            java.lang.String r1 = "t"
            boolean r14 = r14.equals(r1)
            if (r14 == 0) goto L_0x00fa
            r14 = 5
            goto L_0x00fb
        L_0x00c8:
            java.lang.String r1 = "s"
            boolean r14 = r14.equals(r1)
            if (r14 == 0) goto L_0x00fa
            r14 = 2
            goto L_0x00fb
        L_0x00d2:
            java.lang.String r1 = "ms"
            boolean r14 = r14.equals(r1)
            if (r14 == 0) goto L_0x00fa
            r14 = 3
            goto L_0x00fb
        L_0x00dc:
            java.lang.String r1 = "m"
            boolean r14 = r14.equals(r1)
            if (r14 == 0) goto L_0x00fa
            r14 = 1
            goto L_0x00fb
        L_0x00e6:
            java.lang.String r1 = "h"
            boolean r14 = r14.equals(r1)
            if (r14 == 0) goto L_0x00fa
            r14 = 0
            goto L_0x00fb
        L_0x00f0:
            java.lang.String r1 = "f"
            boolean r14 = r14.equals(r1)
            if (r14 == 0) goto L_0x00fa
            r14 = 4
            goto L_0x00fb
        L_0x00fa:
            r14 = -1
        L_0x00fb:
            if (r14 == 0) goto L_0x0120
            if (r14 == r8) goto L_0x011d
            if (r14 == r7) goto L_0x0127
            if (r14 == r6) goto L_0x0116
            if (r14 == r5) goto L_0x010f
            if (r14 == r4) goto L_0x0108
            goto L_0x0127
        L_0x0108:
            int r14 = r15.tickRate
            double r14 = (double) r14
            java.lang.Double.isNaN(r14)
            goto L_0x011b
        L_0x010f:
            float r14 = r15.effectiveFrameRate
            double r14 = (double) r14
            java.lang.Double.isNaN(r14)
            goto L_0x011b
        L_0x0116:
            r14 = 4652007308841189376(0x408f400000000000, double:1000.0)
        L_0x011b:
            double r9 = r9 / r14
            goto L_0x0127
        L_0x011d:
            r14 = 4633641066610819072(0x404e000000000000, double:60.0)
            goto L_0x0125
        L_0x0120:
            r14 = 4660134898793709568(0x40ac200000000000, double:3600.0)
        L_0x0125:
            double r9 = r9 * r14
        L_0x0127:
            double r9 = r9 * r2
            long r14 = (long) r9
            return r14
        L_0x012b:
            com.google.android.exoplayer2.text.SubtitleDecoderException r15 = new com.google.android.exoplayer2.text.SubtitleDecoderException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Malformed time expression: "
            r0.append(r1)
            r0.append(r14)
            java.lang.String r14 = r0.toString()
            r15.<init>(r14)
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.ttml.TtmlDecoder.parseTimeExpression(java.lang.String, com.google.android.exoplayer2.text.ttml.TtmlDecoder$FrameAndTickRate):long");
    }
}
