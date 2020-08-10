package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Xml;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.C1996C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmInitData.SchemeData;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentList;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTimelineElement;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase;
import com.google.android.exoplayer2.upstream.ParsingLoadable.Parser;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class DashManifestParser extends DefaultHandler implements Parser<DashManifest> {
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final String TAG = "MpdParser";
    private final XmlPullParserFactory xmlParserFactory;

    protected static final class RepresentationInfo {
        public final String baseUrl;
        public final ArrayList<SchemeData> drmSchemeDatas;
        public final String drmSchemeType;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;

        public RepresentationInfo(Format format2, String str, SegmentBase segmentBase2, String str2, ArrayList<SchemeData> arrayList, ArrayList<Descriptor> arrayList2, long j) {
            this.format = format2;
            this.baseUrl = str;
            this.segmentBase = segmentBase2;
            this.drmSchemeType = str2;
            this.drmSchemeDatas = arrayList;
            this.inbandEventStreams = arrayList2;
            this.revisionId = j;
        }
    }

    public DashManifestParser() {
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    public DashManifest parse(Uri uri, InputStream inputStream) throws IOException {
        try {
            XmlPullParser newPullParser = this.xmlParserFactory.newPullParser();
            newPullParser.setInput(inputStream, null);
            if (newPullParser.next() == 2 && "MPD".equals(newPullParser.getName())) {
                return parseMediaPresentationDescription(newPullParser, uri.toString());
            }
            throw new ParserException("inputStream does not contain a valid media presentation description");
        } catch (XmlPullParserException e) {
            throw new ParserException((Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x017b A[LOOP:0: B:15:0x0067->B:61:0x017b, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0136 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.DashManifest parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser r35, java.lang.String r36) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r34 = this;
            r14 = r34
            r0 = r35
            r1 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            java.lang.String r3 = "availabilityStartTime"
            long r3 = parseDateTime(r0, r3, r1)
            java.lang.String r5 = "mediaPresentationDuration"
            long r5 = parseDuration(r0, r5, r1)
            java.lang.String r7 = "minBufferTime"
            long r7 = parseDuration(r0, r7, r1)
            r9 = 0
            java.lang.String r10 = "type"
            java.lang.String r10 = r0.getAttributeValue(r9, r10)
            java.lang.String r11 = "dynamic"
            boolean r10 = r11.equals(r10)
            if (r10 == 0) goto L_0x0031
            java.lang.String r11 = "minimumUpdatePeriod"
            long r11 = parseDuration(r0, r11, r1)
            goto L_0x0032
        L_0x0031:
            r11 = r1
        L_0x0032:
            if (r10 == 0) goto L_0x003b
            java.lang.String r13 = "timeShiftBufferDepth"
            long r15 = parseDuration(r0, r13, r1)
            goto L_0x003c
        L_0x003b:
            r15 = r1
        L_0x003c:
            if (r10 == 0) goto L_0x0045
            java.lang.String r13 = "suggestedPresentationDelay"
            long r17 = parseDuration(r0, r13, r1)
            goto L_0x0047
        L_0x0045:
            r17 = r1
        L_0x0047:
            java.lang.String r13 = "publishTime"
            long r19 = parseDateTime(r0, r13, r1)
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            if (r10 == 0) goto L_0x0057
            r21 = r1
            goto L_0x0059
        L_0x0057:
            r21 = 0
        L_0x0059:
            r23 = 0
            r26 = r9
            r27 = r26
            r1 = r21
            r21 = 0
            r9 = r36
            r22 = r27
        L_0x0067:
            r35.next()
            r28 = r15
            java.lang.String r15 = "BaseURL"
            boolean r15 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r15)
            r16 = 1
            if (r15 == 0) goto L_0x0088
            if (r23 != 0) goto L_0x0082
            java.lang.String r9 = r14.parseBaseUrl(r0, r9)
            r32 = r11
            r23 = 1
            goto L_0x012e
        L_0x0082:
            r30 = r1
            r32 = r11
            goto L_0x012c
        L_0x0088:
            java.lang.String r15 = "ProgramInformation"
            boolean r15 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r15)
            if (r15 == 0) goto L_0x009a
            com.google.android.exoplayer2.source.dash.manifest.ProgramInformation r15 = r34.parseProgramInformation(r35)
            r32 = r11
            r22 = r15
            goto L_0x012e
        L_0x009a:
            java.lang.String r15 = "UTCTiming"
            boolean r15 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r15)
            if (r15 == 0) goto L_0x00ac
            com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement r15 = r34.parseUtcTiming(r35)
            r32 = r11
            r26 = r15
            goto L_0x012e
        L_0x00ac:
            java.lang.String r15 = "Location"
            boolean r15 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r15)
            if (r15 == 0) goto L_0x00c2
            java.lang.String r15 = r35.nextText()
            android.net.Uri r15 = android.net.Uri.parse(r15)
            r32 = r11
            r27 = r15
            goto L_0x012e
        L_0x00c2:
            java.lang.String r15 = "Period"
            boolean r15 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r15)
            if (r15 == 0) goto L_0x0125
            if (r21 != 0) goto L_0x0125
            android.util.Pair r15 = r14.parsePeriod(r0, r9, r1)
            r30 = r1
            java.lang.Object r1 = r15.first
            com.google.android.exoplayer2.source.dash.manifest.Period r1 = (com.google.android.exoplayer2.source.dash.manifest.Period) r1
            r32 = r11
            long r11 = r1.startMs
            r24 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r2 = (r11 > r24 ? 1 : (r11 == r24 ? 0 : -1))
            if (r2 != 0) goto L_0x0101
            if (r10 == 0) goto L_0x00e6
            goto L_0x0122
        L_0x00e6:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unable to determine start of period "
            r1.append(r2)
            int r2 = r13.size()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x0101:
            java.lang.Object r2 = r15.second
            java.lang.Long r2 = (java.lang.Long) r2
            long r11 = r2.longValue()
            r15 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r2 = (r11 > r15 ? 1 : (r11 == r15 ? 0 : -1))
            if (r2 != 0) goto L_0x0118
            r11 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            goto L_0x011b
        L_0x0118:
            long r14 = r1.startMs
            long r11 = r11 + r14
        L_0x011b:
            r13.add(r1)
            r30 = r11
            r16 = r21
        L_0x0122:
            r21 = r16
            goto L_0x012c
        L_0x0125:
            r30 = r1
            r32 = r11
            maybeSkipTag(r35)
        L_0x012c:
            r1 = r30
        L_0x012e:
            java.lang.String r11 = "MPD"
            boolean r11 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r11)
            if (r11 == 0) goto L_0x017b
            r11 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r0 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r0 != 0) goto L_0x0150
            int r0 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r0 == 0) goto L_0x0145
            r5 = r1
            goto L_0x0150
        L_0x0145:
            if (r10 == 0) goto L_0x0148
            goto L_0x0150
        L_0x0148:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Unable to determine duration of static manifest."
            r0.<init>(r1)
            throw r0
        L_0x0150:
            boolean r0 = r13.isEmpty()
            if (r0 != 0) goto L_0x0173
            r0 = r34
            r1 = r3
            r3 = r5
            r5 = r7
            r7 = r10
            r8 = r32
            r10 = r28
            r24 = r13
            r12 = r17
            r14 = r19
            r16 = r22
            r17 = r26
            r18 = r27
            r19 = r24
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r0 = r0.buildMediaPresentationDescription(r1, r3, r5, r7, r8, r10, r12, r14, r16, r17, r18, r19)
            return r0
        L_0x0173:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "No periods found."
            r0.<init>(r1)
            throw r0
        L_0x017b:
            r14 = r34
            r15 = r28
            r11 = r32
            goto L_0x0067
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser, java.lang.String):com.google.android.exoplayer2.source.dash.manifest.DashManifest");
    }

    /* access modifiers changed from: protected */
    public DashManifest buildMediaPresentationDescription(long j, long j2, long j3, boolean z, long j4, long j5, long j6, long j7, ProgramInformation programInformation, UtcTimingElement utcTimingElement, Uri uri, List<Period> list) {
        DashManifest dashManifest = new DashManifest(j, j2, j3, z, j4, j5, j6, j7, programInformation, utcTimingElement, uri, list);
        return dashManifest;
    }

    /* access modifiers changed from: protected */
    public UtcTimingElement parseUtcTiming(XmlPullParser xmlPullParser) {
        return buildUtcTimingElement(xmlPullParser.getAttributeValue(null, "schemeIdUri"), xmlPullParser.getAttributeValue(null, Param.VALUE));
    }

    /* access modifiers changed from: protected */
    public UtcTimingElement buildUtcTimingElement(String str, String str2) {
        return new UtcTimingElement(str, str2);
    }

    /* access modifiers changed from: protected */
    public Pair<Period, Long> parsePeriod(XmlPullParser xmlPullParser, String str, long j) throws XmlPullParserException, IOException {
        String str2;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        String attributeValue = xmlPullParser2.getAttributeValue(null, TtmlNode.ATTR_ID);
        long parseDuration = parseDuration(xmlPullParser2, TtmlNode.START, j);
        long parseDuration2 = parseDuration(xmlPullParser2, "duration", C1996C.TIME_UNSET);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String str3 = str;
        SegmentBase segmentBase = null;
        boolean z = false;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "BaseURL")) {
                if (!z) {
                    str3 = parseBaseUrl(xmlPullParser2, str3);
                    z = true;
                } else {
                    str2 = str3;
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "AdaptationSet")) {
                str2 = str3;
                arrayList.add(parseAdaptationSet(xmlPullParser, str3, segmentBase, parseDuration2));
            } else {
                str2 = str3;
                if (XmlPullParserUtil.isStartTag(xmlPullParser2, "EventStream")) {
                    arrayList2.add(parseEventStream(xmlPullParser));
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentBase")) {
                    segmentBase = parseSegmentBase(xmlPullParser2, null);
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentList")) {
                    segmentBase = parseSegmentList(xmlPullParser2, null, parseDuration2);
                } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTemplate")) {
                    segmentBase = parseSegmentTemplate(xmlPullParser, null, Collections.emptyList(), parseDuration2);
                } else {
                    maybeSkipTag(xmlPullParser);
                }
            }
            str3 = str2;
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "Period"));
        return Pair.create(buildPeriod(attributeValue, parseDuration, arrayList, arrayList2), Long.valueOf(parseDuration2));
    }

    /* access modifiers changed from: protected */
    public Period buildPeriod(String str, long j, List<AdaptationSet> list, List<EventStream> list2) {
        Period period = new Period(str, j, list, list2);
        return period;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0270 A[LOOP:0: B:1:0x006e->B:64:0x0270, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0237 A[EDGE_INSN: B:65:0x0237->B:58:0x0237 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.AdaptationSet parseAdaptationSet(org.xmlpull.v1.XmlPullParser r42, java.lang.String r43, com.google.android.exoplayer2.source.dash.manifest.SegmentBase r44, long r45) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r41 = this;
            r15 = r41
            r14 = r42
            r0 = -1
            java.lang.String r1 = "id"
            int r17 = parseInt(r14, r1, r0)
            int r1 = r41.parseContentType(r42)
            r13 = 0
            java.lang.String r2 = "mimeType"
            java.lang.String r18 = r14.getAttributeValue(r13, r2)
            java.lang.String r2 = "codecs"
            java.lang.String r19 = r14.getAttributeValue(r13, r2)
            java.lang.String r2 = "width"
            int r20 = parseInt(r14, r2, r0)
            java.lang.String r2 = "height"
            int r21 = parseInt(r14, r2, r0)
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r22 = parseFrameRate(r14, r2)
            java.lang.String r2 = "audioSamplingRate"
            int r23 = parseInt(r14, r2, r0)
            java.lang.String r12 = "lang"
            java.lang.String r2 = r14.getAttributeValue(r13, r12)
            java.lang.String r3 = "label"
            java.lang.String r3 = r14.getAttributeValue(r13, r3)
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r24 = 0
            r5 = r43
            r27 = r44
            r4 = r2
            r28 = r3
            r29 = r13
            r25 = 0
            r26 = -1
            r3 = r1
        L_0x006e:
            r42.next()
            java.lang.String r0 = "BaseURL"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00b1
            if (r25 != 0) goto L_0x0098
            java.lang.String r0 = r15.parseBaseUrl(r14, r5)
            r1 = 1
            r5 = r0
            r34 = r7
            r35 = r8
            r36 = r9
            r38 = r11
            r39 = r12
            r40 = r13
            r9 = r14
            r25 = 1
        L_0x0090:
            r7 = r3
        L_0x0091:
            r8 = r6
            r12 = r10
            r6 = r15
            r10 = r45
            goto L_0x022f
        L_0x0098:
            r31 = r4
            r32 = r5
            r34 = r7
            r35 = r8
            r36 = r9
            r38 = r11
            r39 = r12
            r40 = r13
            r9 = r14
            r7 = r3
            r8 = r6
            r12 = r10
            r6 = r15
            r10 = r45
            goto L_0x022b
        L_0x00b1:
            java.lang.String r0 = "ContentProtection"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00de
            android.util.Pair r0 = r41.parseContentProtection(r42)
            java.lang.Object r1 = r0.first
            if (r1 == 0) goto L_0x00c7
            java.lang.Object r1 = r0.first
            r29 = r1
            java.lang.String r29 = (java.lang.String) r29
        L_0x00c7:
            java.lang.Object r1 = r0.second
            if (r1 == 0) goto L_0x00d0
            java.lang.Object r0 = r0.second
            r11.add(r0)
        L_0x00d0:
            r34 = r7
            r35 = r8
            r36 = r9
            r38 = r11
            r39 = r12
            r40 = r13
            r9 = r14
            goto L_0x0090
        L_0x00de:
            java.lang.String r0 = "ContentComponent"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x0105
            java.lang.String r0 = r14.getAttributeValue(r13, r12)
            java.lang.String r4 = checkLanguageConsistency(r4, r0)
            int r0 = r41.parseContentType(r42)
            int r0 = checkContentTypeConsistency(r3, r0)
            r34 = r7
            r35 = r8
            r36 = r9
            r38 = r11
            r39 = r12
            r40 = r13
            r9 = r14
            r7 = r0
            goto L_0x0091
        L_0x0105:
            java.lang.String r0 = "Role"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x0115
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r8.add(r0)
            goto L_0x0098
        L_0x0115:
            java.lang.String r0 = "AudioChannelConfiguration"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x0122
            int r26 = r41.parseAudioChannelConfiguration(r42)
            goto L_0x00d0
        L_0x0122:
            java.lang.String r0 = "Accessibility"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x0133
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r9.add(r0)
            goto L_0x0098
        L_0x0133:
            java.lang.String r0 = "SupplementalProperty"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x0144
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r7.add(r0)
            goto L_0x0098
        L_0x0144:
            java.lang.String r0 = "Representation"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x01a5
            r0 = r41
            r1 = r42
            r2 = r5
            r30 = r3
            r3 = r18
            r31 = r4
            r4 = r19
            r32 = r5
            r5 = r20
            r33 = r6
            r6 = r21
            r34 = r7
            r7 = r22
            r35 = r8
            r8 = r26
            r36 = r9
            r9 = r23
            r37 = r10
            r10 = r31
            r38 = r11
            r11 = r35
            r39 = r12
            r12 = r36
            r40 = r13
            r13 = r34
            r14 = r27
            r15 = r45
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r0 = r0.parseRepresentation(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15)
            com.google.android.exoplayer2.Format r1 = r0.format
            r6 = r41
            int r1 = r6.getContentType(r1)
            r7 = r30
            int r1 = checkContentTypeConsistency(r7, r1)
            r8 = r33
            r8.add(r0)
            r9 = r42
            r10 = r45
            r7 = r1
        L_0x019d:
            r4 = r31
            r5 = r32
            r12 = r37
            goto L_0x022f
        L_0x01a5:
            r31 = r4
            r32 = r5
            r34 = r7
            r35 = r8
            r36 = r9
            r37 = r10
            r38 = r11
            r39 = r12
            r40 = r13
            r7 = r3
            r8 = r6
            r6 = r15
            java.lang.String r0 = "SegmentBase"
            r9 = r42
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r9, r0)
            if (r0 == 0) goto L_0x01d1
            r0 = r27
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r0 = r6.parseSegmentBase(r9, r0)
            r10 = r45
        L_0x01ce:
            r27 = r0
            goto L_0x019d
        L_0x01d1:
            java.lang.String r0 = "SegmentList"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r9, r0)
            if (r0 == 0) goto L_0x01e4
            r0 = r27
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentList) r0
            r10 = r45
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r0 = r6.parseSegmentList(r9, r0, r10)
            goto L_0x01ce
        L_0x01e4:
            r10 = r45
            java.lang.String r0 = "SegmentTemplate"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r9, r0)
            if (r0 == 0) goto L_0x01ff
            r2 = r27
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r2 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate) r2
            r0 = r41
            r1 = r42
            r3 = r34
            r4 = r45
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r0 = r0.parseSegmentTemplate(r1, r2, r3, r4)
            goto L_0x01ce
        L_0x01ff:
            java.lang.String r0 = "InbandEventStream"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r9, r0)
            if (r1 == 0) goto L_0x0211
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r9, r0)
            r12 = r37
            r12.add(r0)
            goto L_0x022b
        L_0x0211:
            r12 = r37
            java.lang.String r0 = "Label"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r9, r0)
            if (r0 == 0) goto L_0x0222
            java.lang.String r0 = r41.parseLabel(r42)
            r28 = r0
            goto L_0x022b
        L_0x0222:
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r42)
            if (r0 == 0) goto L_0x022b
            r41.parseAdaptationSetChild(r42)
        L_0x022b:
            r4 = r31
            r5 = r32
        L_0x022f:
            java.lang.String r0 = "AdaptationSet"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r9, r0)
            if (r0 == 0) goto L_0x0270
            java.util.ArrayList r9 = new java.util.ArrayList
            int r0 = r8.size()
            r9.<init>(r0)
            r10 = 0
        L_0x0241:
            int r0 = r8.size()
            if (r10 >= r0) goto L_0x0261
            java.lang.Object r0 = r8.get(r10)
            r1 = r0
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r1 = (com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.RepresentationInfo) r1
            r0 = r41
            r2 = r28
            r3 = r29
            r4 = r38
            r5 = r12
            com.google.android.exoplayer2.source.dash.manifest.Representation r0 = r0.buildRepresentation(r1, r2, r3, r4, r5)
            r9.add(r0)
            int r10 = r10 + 1
            goto L_0x0241
        L_0x0261:
            r0 = r41
            r1 = r17
            r2 = r7
            r3 = r9
            r4 = r36
            r5 = r34
            com.google.android.exoplayer2.source.dash.manifest.AdaptationSet r0 = r0.buildAdaptationSet(r1, r2, r3, r4, r5)
            return r0
        L_0x0270:
            r15 = r6
            r3 = r7
            r6 = r8
            r14 = r9
            r10 = r12
            r7 = r34
            r8 = r35
            r9 = r36
            r11 = r38
            r12 = r39
            r13 = r40
            goto L_0x006e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseAdaptationSet(org.xmlpull.v1.XmlPullParser, java.lang.String, com.google.android.exoplayer2.source.dash.manifest.SegmentBase, long):com.google.android.exoplayer2.source.dash.manifest.AdaptationSet");
    }

    /* access modifiers changed from: protected */
    public AdaptationSet buildAdaptationSet(int i, int i2, List<Representation> list, List<Descriptor> list2, List<Descriptor> list3) {
        AdaptationSet adaptationSet = new AdaptationSet(i, i2, list, list2, list3);
        return adaptationSet;
    }

    /* access modifiers changed from: protected */
    public int parseContentType(XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "contentType");
        if (TextUtils.isEmpty(attributeValue)) {
            return -1;
        }
        if (MimeTypes.BASE_TYPE_AUDIO.equals(attributeValue)) {
            return 1;
        }
        if ("video".equals(attributeValue)) {
            return 2;
        }
        if ("text".equals(attributeValue)) {
            return 3;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int getContentType(Format format) {
        String str = format.sampleMimeType;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (MimeTypes.isVideo(str)) {
            return 2;
        }
        if (MimeTypes.isAudio(str)) {
            return 1;
        }
        if (mimeTypeIsRawText(str)) {
            return 3;
        }
        return -1;
    }

    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r4v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r5v1 */
    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r5v2, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v2, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r3v4, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r1v6, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r3v6 */
    /* JADX WARNING: type inference failed for: r3v7 */
    /* JADX WARNING: type inference failed for: r5v4, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v8 */
    /* JADX WARNING: type inference failed for: r4v1 */
    /* JADX WARNING: type inference failed for: r5v5 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r1v14, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r5v7 */
    /* JADX WARNING: type inference failed for: r4v4 */
    /* JADX WARNING: type inference failed for: r3v17, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r5v10 */
    /* JADX WARNING: type inference failed for: r4v11 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r5v12 */
    /* JADX WARNING: type inference failed for: r3v22 */
    /* JADX WARNING: type inference failed for: r5v13 */
    /* JADX WARNING: type inference failed for: r5v14 */
    /* JADX WARNING: type inference failed for: r5v15 */
    /* JADX WARNING: type inference failed for: r5v16 */
    /* JADX WARNING: type inference failed for: r3v23 */
    /* JADX WARNING: type inference failed for: r3v24 */
    /* JADX WARNING: type inference failed for: r3v25 */
    /* JADX WARNING: type inference failed for: r5v17 */
    /* JADX WARNING: type inference failed for: r3v26 */
    /* JADX WARNING: type inference failed for: r4v15 */
    /* JADX WARNING: type inference failed for: r3v27 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r5v0
      assigns: []
      uses: []
      mth insns count: 122
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:99)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:92)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00aa  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x010b  */
    /* JADX WARNING: Unknown variable types count: 12 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.util.Pair<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData.SchemeData> parseContentProtection(org.xmlpull.v1.XmlPullParser r10) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r9 = this;
            r0 = 0
            java.lang.String r1 = "schemeIdUri"
            java.lang.String r1 = r10.getAttributeValue(r0, r1)
            r2 = 0
            if (r1 == 0) goto L_0x0094
            java.lang.String r1 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r1)
            r3 = -1
            int r4 = r1.hashCode()
            r5 = 489446379(0x1d2c5beb, float:2.281153E-21)
            r6 = 2
            r7 = 1
            if (r4 == r5) goto L_0x0039
            r5 = 755418770(0x2d06c692, float:7.66111E-12)
            if (r4 == r5) goto L_0x002f
            r5 = 1812765994(0x6c0c9d2a, float:6.799672E26)
            if (r4 == r5) goto L_0x0025
            goto L_0x0042
        L_0x0025:
            java.lang.String r4 = "urn:mpeg:dash:mp4protection:2011"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0042
            r3 = 0
            goto L_0x0042
        L_0x002f:
            java.lang.String r4 = "urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0042
            r3 = 2
            goto L_0x0042
        L_0x0039:
            java.lang.String r4 = "urn:uuid:9a04f079-9840-4286-ab92-e65be0885f95"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x0042
            r3 = 1
        L_0x0042:
            if (r3 == 0) goto L_0x0050
            if (r3 == r7) goto L_0x004c
            if (r3 == r6) goto L_0x0049
            goto L_0x0094
        L_0x0049:
            java.util.UUID r1 = com.google.android.exoplayer2.C1996C.WIDEVINE_UUID
            goto L_0x004e
        L_0x004c:
            java.util.UUID r1 = com.google.android.exoplayer2.C1996C.PLAYREADY_UUID
        L_0x004e:
            r3 = r0
            goto L_0x0096
        L_0x0050:
            java.lang.String r1 = "value"
            java.lang.String r1 = r10.getAttributeValue(r0, r1)
            java.lang.String r3 = "default_KID"
            java.lang.String r3 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValueIgnorePrefix(r10, r3)
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x008f
            java.lang.String r4 = "00000000-0000-0000-0000-000000000000"
            boolean r4 = r4.equals(r3)
            if (r4 != 0) goto L_0x008f
            java.lang.String r4 = "\\s+"
            java.lang.String[] r3 = r3.split(r4)
            int r4 = r3.length
            java.util.UUID[] r4 = new java.util.UUID[r4]
            r5 = 0
        L_0x0074:
            int r6 = r3.length
            if (r5 >= r6) goto L_0x0082
            r6 = r3[r5]
            java.util.UUID r6 = java.util.UUID.fromString(r6)
            r4[r5] = r6
            int r5 = r5 + 1
            goto L_0x0074
        L_0x0082:
            java.util.UUID r3 = com.google.android.exoplayer2.C1996C.COMMON_PSSH_UUID
            byte[] r3 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.buildPsshAtom(r3, r4, r0)
            java.util.UUID r4 = com.google.android.exoplayer2.C1996C.COMMON_PSSH_UUID
            r5 = r0
            r8 = r4
            r4 = r1
            r1 = r8
            goto L_0x0098
        L_0x008f:
            r3 = r0
            r5 = r3
            r4 = r1
            r1 = r5
            goto L_0x0098
        L_0x0094:
            r1 = r0
            r3 = r1
        L_0x0096:
            r4 = r3
            r5 = r4
        L_0x0098:
            r10.next()
            java.lang.String r6 = "ms:laurl"
            boolean r6 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r10, r6)
            if (r6 == 0) goto L_0x00aa
            java.lang.String r5 = "licenseUrl"
            java.lang.String r5 = r10.getAttributeValue(r0, r5)
            goto L_0x0101
        L_0x00aa:
            r6 = 4
            if (r3 != 0) goto L_0x00d7
            java.lang.String r7 = "pssh"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTagIgnorePrefix(r10, r7)
            if (r7 == 0) goto L_0x00d7
            int r7 = r10.next()
            if (r7 != r6) goto L_0x00d7
            java.lang.String r1 = r10.getText()
            byte[] r1 = android.util.Base64.decode(r1, r2)
            java.util.UUID r3 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.parseUuid(r1)
            if (r3 != 0) goto L_0x00d3
            java.lang.String r1 = "MpdParser"
            java.lang.String r6 = "Skipping malformed cenc:pssh data"
            com.google.android.exoplayer2.util.Log.m1396w(r1, r6)
            r1 = r3
            r3 = r0
            goto L_0x0101
        L_0x00d3:
            r8 = r3
            r3 = r1
            r1 = r8
            goto L_0x0101
        L_0x00d7:
            if (r3 != 0) goto L_0x00fe
            java.util.UUID r7 = com.google.android.exoplayer2.C1996C.PLAYREADY_UUID
            boolean r7 = r7.equals(r1)
            if (r7 == 0) goto L_0x00fe
            java.lang.String r7 = "mspr:pro"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r10, r7)
            if (r7 == 0) goto L_0x00fe
            int r7 = r10.next()
            if (r7 != r6) goto L_0x00fe
            java.util.UUID r3 = com.google.android.exoplayer2.C1996C.PLAYREADY_UUID
            java.lang.String r6 = r10.getText()
            byte[] r6 = android.util.Base64.decode(r6, r2)
            byte[] r3 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.buildPsshAtom(r3, r6)
            goto L_0x0101
        L_0x00fe:
            maybeSkipTag(r10)
        L_0x0101:
            java.lang.String r6 = "ContentProtection"
            boolean r6 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r10, r6)
            if (r6 == 0) goto L_0x0098
            if (r1 == 0) goto L_0x0112
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r0 = new com.google.android.exoplayer2.drm.DrmInitData$SchemeData
            java.lang.String r10 = "video/mp4"
            r0.<init>(r1, r5, r10, r3)
        L_0x0112:
            android.util.Pair r10 = android.util.Pair.create(r4, r0)
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseContentProtection(org.xmlpull.v1.XmlPullParser):android.util.Pair");
    }

    /* access modifiers changed from: protected */
    public void parseAdaptationSetChild(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        maybeSkipTag(xmlPullParser);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0177 A[LOOP:0: B:1:0x0058->B:45:0x0177, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x013a A[EDGE_INSN: B:46:0x013a->B:39:0x013a ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.RepresentationInfo parseRepresentation(org.xmlpull.v1.XmlPullParser r24, java.lang.String r25, java.lang.String r26, java.lang.String r27, int r28, int r29, float r30, int r31, int r32, java.lang.String r33, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r34, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r35, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r36, com.google.android.exoplayer2.source.dash.manifest.SegmentBase r37, long r38) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r23 = this;
            r14 = r23
            r0 = r24
            r1 = 0
            java.lang.String r2 = "id"
            java.lang.String r2 = r0.getAttributeValue(r1, r2)
            java.lang.String r3 = "bandwidth"
            r4 = -1
            int r8 = parseInt(r0, r3, r4)
            java.lang.String r3 = "mimeType"
            r4 = r26
            java.lang.String r3 = parseString(r0, r3, r4)
            java.lang.String r4 = "codecs"
            r5 = r27
            java.lang.String r12 = parseString(r0, r4, r5)
            java.lang.String r4 = "width"
            r5 = r28
            int r4 = parseInt(r0, r4, r5)
            java.lang.String r5 = "height"
            r6 = r29
            int r5 = parseInt(r0, r5, r6)
            r6 = r30
            float r6 = parseFrameRate(r0, r6)
            java.lang.String r7 = "audioSamplingRate"
            r9 = r32
            int r7 = parseInt(r0, r7, r9)
            java.util.ArrayList r15 = new java.util.ArrayList
            r15.<init>()
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            r9 = 0
            r16 = r31
            r10 = r37
            r17 = r1
            r1 = r25
        L_0x0058:
            r24.next()
            r18 = r12
            java.lang.String r12 = "BaseURL"
            boolean r12 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r12)
            if (r12 == 0) goto L_0x007c
            if (r9 != 0) goto L_0x0075
            java.lang.String r1 = r14.parseBaseUrl(r0, r1)
            r9 = 1
        L_0x006c:
            r12 = r8
        L_0x006d:
            r8 = r16
            r19 = r17
            r16 = r1
            goto L_0x0130
        L_0x0075:
            r32 = r1
            r12 = r8
            r31 = r9
            goto L_0x0128
        L_0x007c:
            java.lang.String r12 = "AudioChannelConfiguration"
            boolean r12 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r12)
            if (r12 == 0) goto L_0x0095
            int r12 = r23.parseAudioChannelConfiguration(r24)
            r16 = r1
            r19 = r17
            r17 = r10
            r22 = r12
            r12 = r8
            r8 = r22
            goto L_0x0132
        L_0x0095:
            java.lang.String r12 = "SegmentBase"
            boolean r12 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r12)
            if (r12 == 0) goto L_0x00a4
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r10 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase) r10
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r10 = r14.parseSegmentBase(r0, r10)
            goto L_0x006c
        L_0x00a4:
            java.lang.String r12 = "SegmentList"
            boolean r12 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r12)
            if (r12 == 0) goto L_0x00ba
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r10 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentList) r10
            r12 = r8
            r31 = r9
            r8 = r38
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r10 = r14.parseSegmentList(r0, r10, r8)
            r9 = r31
            goto L_0x006d
        L_0x00ba:
            r32 = r1
            r12 = r8
            r31 = r9
            r8 = r38
            java.lang.String r1 = "SegmentTemplate"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r1)
            if (r1 == 0) goto L_0x00e5
            r1 = r10
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r1 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate) r1
            r25 = r23
            r26 = r24
            r27 = r1
            r28 = r36
            r29 = r38
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r1 = r25.parseSegmentTemplate(r26, r27, r28, r29)
            r9 = r31
            r8 = r16
            r19 = r17
            r16 = r32
            r17 = r1
            goto L_0x0132
        L_0x00e5:
            java.lang.String r1 = "ContentProtection"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r1)
            if (r1 == 0) goto L_0x0105
            android.util.Pair r1 = r23.parseContentProtection(r24)
            java.lang.Object r8 = r1.first
            if (r8 == 0) goto L_0x00fb
            java.lang.Object r8 = r1.first
            r17 = r8
            java.lang.String r17 = (java.lang.String) r17
        L_0x00fb:
            java.lang.Object r8 = r1.second
            if (r8 == 0) goto L_0x0128
            java.lang.Object r1 = r1.second
            r15.add(r1)
            goto L_0x0128
        L_0x0105:
            java.lang.String r1 = "InbandEventStream"
            boolean r8 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r1)
            if (r8 == 0) goto L_0x0115
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r1 = parseDescriptor(r0, r1)
            r13.add(r1)
            goto L_0x0128
        L_0x0115:
            java.lang.String r1 = "SupplementalProperty"
            boolean r8 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r1)
            if (r8 == 0) goto L_0x0125
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r1 = parseDescriptor(r0, r1)
            r11.add(r1)
            goto L_0x0128
        L_0x0125:
            maybeSkipTag(r24)
        L_0x0128:
            r9 = r31
            r8 = r16
            r19 = r17
            r16 = r32
        L_0x0130:
            r17 = r10
        L_0x0132:
            java.lang.String r1 = "Representation"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r1)
            if (r1 == 0) goto L_0x0177
            r0 = r23
            r1 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r8
            r8 = r12
            r9 = r33
            r10 = r34
            r20 = r11
            r11 = r35
            r12 = r18
            r21 = r13
            r13 = r20
            com.google.android.exoplayer2.Format r0 = r0.buildFormat(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13)
            if (r17 == 0) goto L_0x015a
            r1 = r17
            goto L_0x015f
        L_0x015a:
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r1 = new com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase
            r1.<init>()
        L_0x015f:
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r2 = new com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo
            r3 = -1
            r24 = r2
            r25 = r0
            r26 = r16
            r27 = r1
            r28 = r19
            r29 = r15
            r30 = r21
            r31 = r3
            r24.<init>(r25, r26, r27, r28, r29, r30, r31)
            return r2
        L_0x0177:
            r1 = r16
            r10 = r17
            r17 = r19
            r16 = r8
            r8 = r12
            r12 = r18
            goto L_0x0058
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseRepresentation(org.xmlpull.v1.XmlPullParser, java.lang.String, java.lang.String, java.lang.String, int, int, float, int, int, java.lang.String, java.util.List, java.util.List, java.util.List, com.google.android.exoplayer2.source.dash.manifest.SegmentBase, long):com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo");
    }

    /* access modifiers changed from: protected */
    public Format buildFormat(String str, String str2, int i, int i2, float f, int i3, int i4, int i5, String str3, List<Descriptor> list, List<Descriptor> list2, String str4, List<Descriptor> list3) {
        String str5;
        int i6;
        int parseCea708AccessibilityChannel;
        List<Descriptor> list4 = list;
        String sampleMimeType = getSampleMimeType(str2, str4);
        int parseSelectionFlagsFromRoleDescriptors = parseSelectionFlagsFromRoleDescriptors(list4);
        int parseRoleFlagsFromRoleDescriptors = parseRoleFlagsFromRoleDescriptors(list4) | parseRoleFlagsFromAccessibilityDescriptors(list2);
        if (sampleMimeType != null) {
            String parseEac3SupplementalProperties = MimeTypes.AUDIO_E_AC3.equals(sampleMimeType) ? parseEac3SupplementalProperties(list3) : sampleMimeType;
            if (MimeTypes.isVideo(parseEac3SupplementalProperties)) {
                return Format.createVideoContainerFormat(str, null, str2, parseEac3SupplementalProperties, str4, null, i5, i, i2, f, null, parseSelectionFlagsFromRoleDescriptors, parseRoleFlagsFromRoleDescriptors);
            }
            if (MimeTypes.isAudio(parseEac3SupplementalProperties)) {
                return Format.createAudioContainerFormat(str, null, str2, parseEac3SupplementalProperties, str4, null, i5, i3, i4, null, parseSelectionFlagsFromRoleDescriptors, parseRoleFlagsFromRoleDescriptors, str3);
            }
            if (mimeTypeIsRawText(parseEac3SupplementalProperties)) {
                if (MimeTypes.APPLICATION_CEA608.equals(parseEac3SupplementalProperties)) {
                    parseCea708AccessibilityChannel = parseCea608AccessibilityChannel(list2);
                } else if (MimeTypes.APPLICATION_CEA708.equals(parseEac3SupplementalProperties)) {
                    parseCea708AccessibilityChannel = parseCea708AccessibilityChannel(list2);
                } else {
                    i6 = -1;
                    return Format.createTextContainerFormat(str, null, str2, parseEac3SupplementalProperties, str4, i5, parseSelectionFlagsFromRoleDescriptors, parseRoleFlagsFromRoleDescriptors, str3, i6);
                }
                i6 = parseCea708AccessibilityChannel;
                return Format.createTextContainerFormat(str, null, str2, parseEac3SupplementalProperties, str4, i5, parseSelectionFlagsFromRoleDescriptors, parseRoleFlagsFromRoleDescriptors, str3, i6);
            }
            str5 = parseEac3SupplementalProperties;
        } else {
            str5 = sampleMimeType;
        }
        return Format.createContainerFormat(str, null, str2, str5, str4, i5, parseSelectionFlagsFromRoleDescriptors, parseRoleFlagsFromRoleDescriptors, str3);
    }

    /* access modifiers changed from: protected */
    public Representation buildRepresentation(RepresentationInfo representationInfo, String str, String str2, ArrayList<SchemeData> arrayList, ArrayList<Descriptor> arrayList2) {
        Format format = representationInfo.format;
        if (str != null) {
            format = format.copyWithLabel(str);
        }
        if (representationInfo.drmSchemeType != null) {
            str2 = representationInfo.drmSchemeType;
        }
        ArrayList<SchemeData> arrayList3 = representationInfo.drmSchemeDatas;
        arrayList3.addAll(arrayList);
        if (!arrayList3.isEmpty()) {
            filterRedundantIncompleteSchemeDatas(arrayList3);
            format = format.copyWithDrmInitData(new DrmInitData(str2, (List<SchemeData>) arrayList3));
        }
        Format format2 = format;
        ArrayList<Descriptor> arrayList4 = representationInfo.inbandEventStreams;
        arrayList4.addAll(arrayList2);
        return Representation.newInstance(representationInfo.revisionId, format2, representationInfo.baseUrl, representationInfo.segmentBase, arrayList4);
    }

    /* access modifiers changed from: protected */
    public SingleSegmentBase parseSegmentBase(XmlPullParser xmlPullParser, SingleSegmentBase singleSegmentBase) throws XmlPullParserException, IOException {
        long j;
        long j2;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SingleSegmentBase singleSegmentBase2 = singleSegmentBase;
        long parseLong = parseLong(xmlPullParser2, "timescale", singleSegmentBase2 != null ? singleSegmentBase2.timescale : 1);
        long j3 = 0;
        long parseLong2 = parseLong(xmlPullParser2, "presentationTimeOffset", singleSegmentBase2 != null ? singleSegmentBase2.presentationTimeOffset : 0);
        long j4 = singleSegmentBase2 != null ? singleSegmentBase2.indexStart : 0;
        if (singleSegmentBase2 != null) {
            j3 = singleSegmentBase2.indexLength;
        }
        RangedUri rangedUri = null;
        String attributeValue = xmlPullParser2.getAttributeValue(null, "indexRange");
        if (attributeValue != null) {
            String[] split = attributeValue.split("-");
            long parseLong3 = Long.parseLong(split[0]);
            j = (Long.parseLong(split[1]) - parseLong3) + 1;
            j2 = parseLong3;
        } else {
            j = j3;
            j2 = j4;
        }
        if (singleSegmentBase2 != null) {
            rangedUri = singleSegmentBase2.initialization;
        }
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentBase"));
        return buildSingleSegmentBase(rangedUri, parseLong, parseLong2, j2, j);
    }

    /* access modifiers changed from: protected */
    public SingleSegmentBase buildSingleSegmentBase(RangedUri rangedUri, long j, long j2, long j3, long j4) {
        SingleSegmentBase singleSegmentBase = new SingleSegmentBase(rangedUri, j, j2, j3, j4);
        return singleSegmentBase;
    }

    /* access modifiers changed from: protected */
    public SegmentList parseSegmentList(XmlPullParser xmlPullParser, SegmentList segmentList, long j) throws XmlPullParserException, IOException {
        RangedUri rangedUri;
        List list;
        RangedUri rangedUri2;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SegmentList segmentList2 = segmentList;
        long j2 = 1;
        long parseLong = parseLong(xmlPullParser2, "timescale", segmentList2 != null ? segmentList2.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser2, "presentationTimeOffset", segmentList2 != null ? segmentList2.presentationTimeOffset : 0);
        long parseLong3 = parseLong(xmlPullParser2, "duration", segmentList2 != null ? segmentList2.duration : C1996C.TIME_UNSET);
        if (segmentList2 != null) {
            j2 = segmentList2.startNumber;
        }
        long parseLong4 = parseLong(xmlPullParser2, "startNumber", j2);
        List list2 = null;
        List list3 = null;
        RangedUri rangedUri3 = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                rangedUri3 = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTimeline")) {
                list2 = parseSegmentTimeline(xmlPullParser, parseLong, j);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentURL")) {
                if (list3 == null) {
                    list3 = new ArrayList();
                }
                list3.add(parseSegmentUrl(xmlPullParser));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentList"));
        if (segmentList2 != null) {
            if (rangedUri3 != null) {
                rangedUri2 = rangedUri3;
            } else {
                rangedUri2 = segmentList2.initialization;
            }
            if (list2 == null) {
                list2 = segmentList2.segmentTimeline;
            }
            if (list3 == null) {
                list3 = segmentList2.mediaSegments;
            }
            rangedUri = rangedUri2;
            list = list3;
        } else {
            list = list3;
            rangedUri = rangedUri3;
        }
        return buildSegmentList(rangedUri, parseLong, parseLong2, parseLong4, parseLong3, list2, list);
    }

    /* access modifiers changed from: protected */
    public SegmentList buildSegmentList(RangedUri rangedUri, long j, long j2, long j3, long j4, List<SegmentTimelineElement> list, List<RangedUri> list2) {
        SegmentList segmentList = new SegmentList(rangedUri, j, j2, j3, j4, list, list2);
        return segmentList;
    }

    /* access modifiers changed from: protected */
    public SegmentTemplate parseSegmentTemplate(XmlPullParser xmlPullParser, SegmentTemplate segmentTemplate, List<Descriptor> list, long j) throws XmlPullParserException, IOException {
        RangedUri rangedUri;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SegmentTemplate segmentTemplate2 = segmentTemplate;
        long j2 = 1;
        long parseLong = parseLong(xmlPullParser2, "timescale", segmentTemplate2 != null ? segmentTemplate2.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser2, "presentationTimeOffset", segmentTemplate2 != null ? segmentTemplate2.presentationTimeOffset : 0);
        long parseLong3 = parseLong(xmlPullParser2, "duration", segmentTemplate2 != null ? segmentTemplate2.duration : C1996C.TIME_UNSET);
        if (segmentTemplate2 != null) {
            j2 = segmentTemplate2.startNumber;
        }
        long parseLong4 = parseLong(xmlPullParser2, "startNumber", j2);
        long parseLastSegmentNumberSupplementalProperty = parseLastSegmentNumberSupplementalProperty(list);
        List list2 = null;
        UrlTemplate parseUrlTemplate = parseUrlTemplate(xmlPullParser2, "media", segmentTemplate2 != null ? segmentTemplate2.mediaTemplate : null);
        UrlTemplate parseUrlTemplate2 = parseUrlTemplate(xmlPullParser2, "initialization", segmentTemplate2 != null ? segmentTemplate2.initializationTemplate : null);
        RangedUri rangedUri2 = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                rangedUri2 = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTimeline")) {
                list2 = parseSegmentTimeline(xmlPullParser, parseLong, j);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentTemplate"));
        if (segmentTemplate2 != null) {
            if (rangedUri2 != null) {
                rangedUri = rangedUri2;
            } else {
                rangedUri = segmentTemplate2.initialization;
            }
            if (list2 == null) {
                list2 = segmentTemplate2.segmentTimeline;
            }
        } else {
            rangedUri = rangedUri2;
        }
        return buildSegmentTemplate(rangedUri, parseLong, parseLong2, parseLong4, parseLastSegmentNumberSupplementalProperty, parseLong3, list2, parseUrlTemplate2, parseUrlTemplate);
    }

    /* access modifiers changed from: protected */
    public SegmentTemplate buildSegmentTemplate(RangedUri rangedUri, long j, long j2, long j3, long j4, long j5, List<SegmentTimelineElement> list, UrlTemplate urlTemplate, UrlTemplate urlTemplate2) {
        SegmentTemplate segmentTemplate = new SegmentTemplate(rangedUri, j, j2, j3, j4, j5, list, urlTemplate, urlTemplate2);
        return segmentTemplate;
    }

    /* access modifiers changed from: protected */
    public EventStream parseEventStream(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String str = "";
        String parseString = parseString(xmlPullParser, "schemeIdUri", str);
        String parseString2 = parseString(xmlPullParser, Param.VALUE, str);
        long parseLong = parseLong(xmlPullParser, "timescale", 1);
        ArrayList arrayList = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Event")) {
                arrayList.add(parseEvent(xmlPullParser, parseString, parseString2, parseLong, byteArrayOutputStream));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "EventStream"));
        long[] jArr = new long[arrayList.size()];
        EventMessage[] eventMessageArr = new EventMessage[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            Pair pair = (Pair) arrayList.get(i);
            jArr[i] = ((Long) pair.first).longValue();
            eventMessageArr[i] = (EventMessage) pair.second;
        }
        return buildEventStream(parseString, parseString2, parseLong, jArr, eventMessageArr);
    }

    /* access modifiers changed from: protected */
    public EventStream buildEventStream(String str, String str2, long j, long[] jArr, EventMessage[] eventMessageArr) {
        EventStream eventStream = new EventStream(str, str2, j, jArr, eventMessageArr);
        return eventStream;
    }

    /* access modifiers changed from: protected */
    public Pair<Long, EventMessage> parseEvent(XmlPullParser xmlPullParser, String str, String str2, long j, ByteArrayOutputStream byteArrayOutputStream) throws IOException, XmlPullParserException {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        long parseLong = parseLong(xmlPullParser2, TtmlNode.ATTR_ID, 0);
        long parseLong2 = parseLong(xmlPullParser2, "duration", C1996C.TIME_UNSET);
        long parseLong3 = parseLong(xmlPullParser2, "presentationTime", 0);
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(parseLong2, 1000, j);
        long scaleLargeTimestamp2 = Util.scaleLargeTimestamp(parseLong3, 1000000, j);
        String parseString = parseString(xmlPullParser2, "messageData", null);
        byte[] parseEventObject = parseEventObject(xmlPullParser2, byteArrayOutputStream);
        Long valueOf = Long.valueOf(scaleLargeTimestamp2);
        if (parseString != null) {
            parseEventObject = Util.getUtf8Bytes(parseString);
        }
        return Pair.create(valueOf, buildEvent(str, str2, parseLong, scaleLargeTimestamp, parseEventObject));
    }

    /* access modifiers changed from: protected */
    public byte[] parseEventObject(XmlPullParser xmlPullParser, ByteArrayOutputStream byteArrayOutputStream) throws XmlPullParserException, IOException {
        byteArrayOutputStream.reset();
        XmlSerializer newSerializer = Xml.newSerializer();
        newSerializer.setOutput(byteArrayOutputStream, "UTF-8");
        xmlPullParser.nextToken();
        while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Event")) {
            switch (xmlPullParser.getEventType()) {
                case 0:
                    newSerializer.startDocument(null, Boolean.valueOf(false));
                    break;
                case 1:
                    newSerializer.endDocument();
                    break;
                case 2:
                    newSerializer.startTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                        newSerializer.attribute(xmlPullParser.getAttributeNamespace(i), xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
                    }
                    break;
                case 3:
                    newSerializer.endTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    break;
                case 4:
                    newSerializer.text(xmlPullParser.getText());
                    break;
                case 5:
                    newSerializer.cdsect(xmlPullParser.getText());
                    break;
                case 6:
                    newSerializer.entityRef(xmlPullParser.getText());
                    break;
                case 7:
                    newSerializer.ignorableWhitespace(xmlPullParser.getText());
                    break;
                case 8:
                    newSerializer.processingInstruction(xmlPullParser.getText());
                    break;
                case 9:
                    newSerializer.comment(xmlPullParser.getText());
                    break;
                case 10:
                    newSerializer.docdecl(xmlPullParser.getText());
                    break;
            }
            xmlPullParser.nextToken();
        }
        newSerializer.flush();
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public EventMessage buildEvent(String str, String str2, long j, long j2, byte[] bArr) {
        EventMessage eventMessage = new EventMessage(str, str2, j2, j, bArr);
        return eventMessage;
    }

    /* access modifiers changed from: protected */
    public List<SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xmlPullParser, long j, long j2) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        ArrayList arrayList = new ArrayList();
        long j3 = 0;
        long j4 = -9223372036854775807L;
        boolean z = false;
        int i = 0;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, ExifInterface.LATITUDE_SOUTH)) {
                long parseLong = parseLong(xmlPullParser2, "t", C1996C.TIME_UNSET);
                if (z) {
                    j3 = addSegmentTimelineElementsToList(arrayList, j3, j4, i, parseLong);
                }
                if (parseLong == C1996C.TIME_UNSET) {
                    parseLong = j3;
                }
                j4 = parseLong(xmlPullParser2, "d", C1996C.TIME_UNSET);
                i = parseInt(xmlPullParser2, "r", 0);
                j3 = parseLong;
                z = true;
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentTimeline"));
        if (z) {
            addSegmentTimelineElementsToList(arrayList, j3, j4, i, Util.scaleLargeTimestamp(j2, j, 1000));
        }
        return arrayList;
    }

    private long addSegmentTimelineElementsToList(List<SegmentTimelineElement> list, long j, long j2, int i, long j3) {
        int ceilDivide = i >= 0 ? i + 1 : (int) Util.ceilDivide(j3 - j, j2);
        for (int i2 = 0; i2 < ceilDivide; i2++) {
            list.add(buildSegmentTimelineElement(j, j2));
            j += j2;
        }
        return j;
    }

    /* access modifiers changed from: protected */
    public SegmentTimelineElement buildSegmentTimelineElement(long j, long j2) {
        return new SegmentTimelineElement(j, j2);
    }

    /* access modifiers changed from: protected */
    public UrlTemplate parseUrlTemplate(XmlPullParser xmlPullParser, String str, UrlTemplate urlTemplate) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue != null ? UrlTemplate.compile(attributeValue) : urlTemplate;
    }

    /* access modifiers changed from: protected */
    public RangedUri parseInitialization(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "sourceURL", "range");
    }

    /* access modifiers changed from: protected */
    public RangedUri parseSegmentUrl(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "media", "mediaRange");
    }

    /* access modifiers changed from: protected */
    public RangedUri parseRangedUrl(XmlPullParser xmlPullParser, String str, String str2) {
        long j;
        long j2;
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        String attributeValue2 = xmlPullParser.getAttributeValue(null, str2);
        if (attributeValue2 != null) {
            String[] split = attributeValue2.split("-");
            j2 = Long.parseLong(split[0]);
            if (split.length == 2) {
                j = (Long.parseLong(split[1]) - j2) + 1;
                return buildRangedUri(attributeValue, j2, j);
            }
        } else {
            j2 = 0;
        }
        j = -1;
        return buildRangedUri(attributeValue, j2, j);
    }

    /* access modifiers changed from: protected */
    public RangedUri buildRangedUri(String str, long j, long j2) {
        RangedUri rangedUri = new RangedUri(str, j, j2);
        return rangedUri;
    }

    /* access modifiers changed from: protected */
    public ProgramInformation parseProgramInformation(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String str = null;
        String parseString = parseString(xmlPullParser, "moreInformationURL", null);
        String parseString2 = parseString(xmlPullParser, "lang", null);
        String str2 = null;
        String str3 = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Title")) {
                str = xmlPullParser.nextText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "Source")) {
                str2 = xmlPullParser.nextText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.TAG_COPYRIGHT)) {
                str3 = xmlPullParser.nextText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
            String str4 = str3;
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "ProgramInformation")) {
                ProgramInformation programInformation = new ProgramInformation(str, str2, str4, parseString, parseString2);
                return programInformation;
            }
            str3 = str4;
        }
    }

    /* access modifiers changed from: protected */
    public String parseLabel(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return parseText(xmlPullParser, "Label");
    }

    /* access modifiers changed from: protected */
    public String parseBaseUrl(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        return UriUtil.resolve(str, parseText(xmlPullParser, "BaseURL"));
    }

    /* access modifiers changed from: protected */
    public int parseAudioChannelConfiguration(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", null);
        int i = -1;
        if ("urn:mpeg:dash:23003:3:audio_channel_configuration:2011".equals(parseString)) {
            i = parseInt(xmlPullParser, Param.VALUE, -1);
        } else if ("tag:dolby.com,2014:dash:audio_channel_configuration:2011".equals(parseString) || "urn:dolby:dash:audio_channel_configuration:2011".equals(parseString)) {
            i = parseDolbyChannelConfiguration(xmlPullParser);
        }
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "AudioChannelConfiguration"));
        return i;
    }

    /* access modifiers changed from: protected */
    public int parseSelectionFlagsFromRoleDescriptors(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:mpeg:dash:role:2011".equalsIgnoreCase(descriptor.schemeIdUri)) {
                if ("main".equals(descriptor.value)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int parseRoleFlagsFromRoleDescriptors(List<Descriptor> list) {
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            if ("urn:mpeg:dash:role:2011".equalsIgnoreCase(descriptor.schemeIdUri)) {
                i |= parseDashRoleSchemeValue(descriptor.value);
            }
        }
        return i;
    }

    /* access modifiers changed from: protected */
    public int parseRoleFlagsFromAccessibilityDescriptors(List<Descriptor> list) {
        int parseTvaAudioPurposeCsValue;
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            Descriptor descriptor = (Descriptor) list.get(i2);
            if ("urn:mpeg:dash:role:2011".equalsIgnoreCase(descriptor.schemeIdUri)) {
                parseTvaAudioPurposeCsValue = parseDashRoleSchemeValue(descriptor.value);
            } else {
                if ("urn:tva:metadata:cs:AudioPurposeCS:2007".equalsIgnoreCase(descriptor.schemeIdUri)) {
                    parseTvaAudioPurposeCsValue = parseTvaAudioPurposeCsValue(descriptor.value);
                }
            }
            i |= parseTvaAudioPurposeCsValue;
        }
        return i;
    }

    /* access modifiers changed from: protected */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int parseDashRoleSchemeValue(java.lang.String r8) {
        /*
            r7 = this;
            r0 = 0
            if (r8 != 0) goto L_0x0004
            return r0
        L_0x0004:
            r1 = -1
            int r2 = r8.hashCode()
            r3 = 8
            r4 = 4
            r5 = 2
            r6 = 1
            switch(r2) {
                case -2060497896: goto L_0x007b;
                case -1724546052: goto L_0x0070;
                case -1580883024: goto L_0x0065;
                case -1408024454: goto L_0x005b;
                case 99825: goto L_0x0051;
                case 3343801: goto L_0x0047;
                case 3530173: goto L_0x003c;
                case 552573414: goto L_0x0032;
                case 899152809: goto L_0x0028;
                case 1629013393: goto L_0x001e;
                case 1855372047: goto L_0x0013;
                default: goto L_0x0011;
            }
        L_0x0011:
            goto L_0x0085
        L_0x0013:
            java.lang.String r2 = "supplementary"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 2
            goto L_0x0086
        L_0x001e:
            java.lang.String r2 = "emergency"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 5
            goto L_0x0086
        L_0x0028:
            java.lang.String r2 = "commentary"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 3
            goto L_0x0086
        L_0x0032:
            java.lang.String r2 = "caption"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 6
            goto L_0x0086
        L_0x003c:
            java.lang.String r2 = "sign"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 8
            goto L_0x0086
        L_0x0047:
            java.lang.String r2 = "main"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 0
            goto L_0x0086
        L_0x0051:
            java.lang.String r2 = "dub"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 4
            goto L_0x0086
        L_0x005b:
            java.lang.String r2 = "alternate"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 1
            goto L_0x0086
        L_0x0065:
            java.lang.String r2 = "enhanced-audio-intelligibility"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 10
            goto L_0x0086
        L_0x0070:
            java.lang.String r2 = "description"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 9
            goto L_0x0086
        L_0x007b:
            java.lang.String r2 = "subtitle"
            boolean r8 = r8.equals(r2)
            if (r8 == 0) goto L_0x0085
            r8 = 7
            goto L_0x0086
        L_0x0085:
            r8 = -1
        L_0x0086:
            switch(r8) {
                case 0: goto L_0x00a2;
                case 1: goto L_0x00a1;
                case 2: goto L_0x00a0;
                case 3: goto L_0x009f;
                case 4: goto L_0x009c;
                case 5: goto L_0x0099;
                case 6: goto L_0x0096;
                case 7: goto L_0x0093;
                case 8: goto L_0x0090;
                case 9: goto L_0x008d;
                case 10: goto L_0x008a;
                default: goto L_0x0089;
            }
        L_0x0089:
            return r0
        L_0x008a:
            r8 = 2048(0x800, float:2.87E-42)
            return r8
        L_0x008d:
            r8 = 512(0x200, float:7.175E-43)
            return r8
        L_0x0090:
            r8 = 256(0x100, float:3.59E-43)
            return r8
        L_0x0093:
            r8 = 128(0x80, float:1.794E-43)
            return r8
        L_0x0096:
            r8 = 64
            return r8
        L_0x0099:
            r8 = 32
            return r8
        L_0x009c:
            r8 = 16
            return r8
        L_0x009f:
            return r3
        L_0x00a0:
            return r4
        L_0x00a1:
            return r5
        L_0x00a2:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseDashRoleSchemeValue(java.lang.String):int");
    }

    /* access modifiers changed from: protected */
    public int parseTvaAudioPurposeCsValue(String str) {
        if (str == null) {
            return 0;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case 49:
                if (str.equals(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) {
                    c = 0;
                    break;
                }
                break;
            case 50:
                if (str.equals(ExifInterface.GPS_MEASUREMENT_2D)) {
                    c = 1;
                    break;
                }
                break;
            case 51:
                if (str.equals(ExifInterface.GPS_MEASUREMENT_3D)) {
                    c = 2;
                    break;
                }
                break;
            case 52:
                if (str.equals("4")) {
                    c = 3;
                    break;
                }
                break;
            case 54:
                if (str.equals("6")) {
                    c = 4;
                    break;
                }
                break;
        }
        if (c == 0) {
            return 512;
        }
        if (c == 1) {
            return 2048;
        }
        if (c == 2) {
            return 4;
        }
        if (c != 3) {
            return c != 4 ? 0 : 1;
        }
        return 8;
    }

    public static void maybeSkipTag(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
            int i = 1;
            while (i != 0) {
                xmlPullParser.next();
                if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                    i++;
                } else if (XmlPullParserUtil.isEndTag(xmlPullParser)) {
                    i--;
                }
            }
        }
    }

    private static void filterRedundantIncompleteSchemeDatas(ArrayList<SchemeData> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            SchemeData schemeData = (SchemeData) arrayList.get(size);
            if (!schemeData.hasData()) {
                int i = 0;
                while (true) {
                    if (i >= arrayList.size()) {
                        break;
                    } else if (((SchemeData) arrayList.get(i)).canReplace(schemeData)) {
                        arrayList.remove(size);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    private static String getSampleMimeType(String str, String str2) {
        if (MimeTypes.isAudio(str)) {
            return MimeTypes.getAudioMediaMimeType(str2);
        }
        if (MimeTypes.isVideo(str)) {
            return MimeTypes.getVideoMediaMimeType(str2);
        }
        if (mimeTypeIsRawText(str)) {
            return str;
        }
        if (MimeTypes.APPLICATION_MP4.equals(str)) {
            if (str2 != null) {
                if (str2.startsWith("stpp")) {
                    return MimeTypes.APPLICATION_TTML;
                }
                if (str2.startsWith("wvtt")) {
                    return MimeTypes.APPLICATION_MP4VTT;
                }
            }
        } else if (MimeTypes.APPLICATION_RAWCC.equals(str) && str2 != null) {
            if (str2.contains("cea708")) {
                return MimeTypes.APPLICATION_CEA708;
            }
            if (str2.contains("eia608") || str2.contains("cea608")) {
                return MimeTypes.APPLICATION_CEA608;
            }
        }
        return null;
    }

    private static boolean mimeTypeIsRawText(String str) {
        return MimeTypes.isText(str) || MimeTypes.APPLICATION_TTML.equals(str) || MimeTypes.APPLICATION_MP4VTT.equals(str) || MimeTypes.APPLICATION_CEA708.equals(str) || MimeTypes.APPLICATION_CEA608.equals(str);
    }

    private static String checkLanguageConsistency(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        Assertions.checkState(str.equals(str2));
        return str;
    }

    private static int checkContentTypeConsistency(int i, int i2) {
        if (i == -1) {
            return i2;
        }
        if (i2 == -1) {
            return i;
        }
        Assertions.checkState(i == i2);
        return i;
    }

    protected static Descriptor parseDescriptor(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", "");
        String parseString2 = parseString(xmlPullParser, Param.VALUE, null);
        String parseString3 = parseString(xmlPullParser, TtmlNode.ATTR_ID, null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return new Descriptor(parseString, parseString2, parseString3);
    }

    protected static int parseCea608AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_608_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to parse CEA-608 channel number from: ");
                sb.append(descriptor.value);
                Log.m1396w(TAG, sb.toString());
            }
        }
        return -1;
    }

    protected static int parseCea708AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_708_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to parse CEA-708 service block number from: ");
                sb.append(descriptor.value);
                Log.m1396w(TAG, sb.toString());
            }
        }
        return -1;
    }

    protected static String parseEac3SupplementalProperties(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            String str = descriptor.schemeIdUri;
            if ("tag:dolby.com,2018:dash:EC3_ExtensionType:2018".equals(str)) {
                if ("JOC".equals(descriptor.value)) {
                    return MimeTypes.AUDIO_E_AC3_JOC;
                }
            }
            if ("tag:dolby.com,2014:dash:DolbyDigitalPlusExtensionType:2014".equals(str)) {
                if ("ec+3".equals(descriptor.value)) {
                    return MimeTypes.AUDIO_E_AC3_JOC;
                }
            }
        }
        return MimeTypes.AUDIO_E_AC3;
    }

    protected static float parseFrameRate(XmlPullParser xmlPullParser, float f) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "frameRate");
        if (attributeValue == null) {
            return f;
        }
        Matcher matcher = FRAME_RATE_PATTERN.matcher(attributeValue);
        if (!matcher.matches()) {
            return f;
        }
        int parseInt = Integer.parseInt(matcher.group(1));
        String group = matcher.group(2);
        return !TextUtils.isEmpty(group) ? ((float) parseInt) / ((float) Integer.parseInt(group)) : (float) parseInt;
    }

    protected static long parseDuration(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            return j;
        }
        return Util.parseXsDuration(attributeValue);
    }

    protected static long parseDateTime(XmlPullParser xmlPullParser, String str, long j) throws ParserException {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            return j;
        }
        return Util.parseXsDateTime(attributeValue);
    }

    protected static String parseText(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String str2 = "";
        do {
            xmlPullParser.next();
            if (xmlPullParser.getEventType() == 4) {
                str2 = xmlPullParser.getText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return str2;
    }

    protected static int parseInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? i : Integer.parseInt(attributeValue);
    }

    protected static long parseLong(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Long.parseLong(attributeValue);
    }

    protected static String parseString(XmlPullParser xmlPullParser, String str, String str2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? str2 : attributeValue;
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static int parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser r5) {
        /*
            r0 = 0
            java.lang.String r1 = "value"
            java.lang.String r5 = r5.getAttributeValue(r0, r1)
            java.lang.String r5 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r5)
            r0 = -1
            if (r5 != 0) goto L_0x000f
            return r0
        L_0x000f:
            int r1 = r5.hashCode()
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r1) {
                case 1596796: goto L_0x0038;
                case 2937391: goto L_0x002e;
                case 3094035: goto L_0x0024;
                case 3133436: goto L_0x001a;
                default: goto L_0x0019;
            }
        L_0x0019:
            goto L_0x0042
        L_0x001a:
            java.lang.String r1 = "fa01"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0042
            r5 = 3
            goto L_0x0043
        L_0x0024:
            java.lang.String r1 = "f801"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0042
            r5 = 2
            goto L_0x0043
        L_0x002e:
            java.lang.String r1 = "a000"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0042
            r5 = 1
            goto L_0x0043
        L_0x0038:
            java.lang.String r1 = "4000"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0042
            r5 = 0
            goto L_0x0043
        L_0x0042:
            r5 = -1
        L_0x0043:
            if (r5 == 0) goto L_0x0052
            if (r5 == r4) goto L_0x0051
            if (r5 == r3) goto L_0x004f
            if (r5 == r2) goto L_0x004c
            return r0
        L_0x004c:
            r5 = 8
            return r5
        L_0x004f:
            r5 = 6
            return r5
        L_0x0051:
            return r3
        L_0x0052:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser):int");
    }

    protected static long parseLastSegmentNumberSupplementalProperty(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("http://dashif.org/guidelines/last-segment-number".equalsIgnoreCase(descriptor.schemeIdUri)) {
                return Long.parseLong(descriptor.value);
            }
        }
        return -1;
    }
}
