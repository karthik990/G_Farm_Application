package com.google.android.exoplayer2.text.webvtt;

import android.text.TextUtils;
import com.google.android.exoplayer2.util.ColorParser;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class CssParser {
    private static final String PROPERTY_BGCOLOR = "background-color";
    private static final String PROPERTY_FONT_FAMILY = "font-family";
    private static final String PROPERTY_FONT_STYLE = "font-style";
    private static final String PROPERTY_FONT_WEIGHT = "font-weight";
    private static final String PROPERTY_TEXT_DECORATION = "text-decoration";
    private static final String RULE_END = "}";
    private static final String RULE_START = "{";
    private static final String VALUE_BOLD = "bold";
    private static final String VALUE_ITALIC = "italic";
    private static final String VALUE_UNDERLINE = "underline";
    private static final Pattern VOICE_NAME_PATTERN = Pattern.compile("\\[voice=\"([^\"]*)\"\\]");
    private final StringBuilder stringBuilder = new StringBuilder();
    private final ParsableByteArray styleInput = new ParsableByteArray();

    public List<WebvttCssStyle> parseBlock(ParsableByteArray parsableByteArray) {
        String str;
        this.stringBuilder.setLength(0);
        int position = parsableByteArray.getPosition();
        skipStyleBlock(parsableByteArray);
        this.styleInput.reset(parsableByteArray.data, parsableByteArray.getPosition());
        this.styleInput.setPosition(position);
        ArrayList arrayList = new ArrayList();
        while (true) {
            String parseSelector = parseSelector(this.styleInput, this.stringBuilder);
            if (parseSelector == null) {
                return arrayList;
            }
            if (!RULE_START.equals(parseNextToken(this.styleInput, this.stringBuilder))) {
                return arrayList;
            }
            WebvttCssStyle webvttCssStyle = new WebvttCssStyle();
            applySelectorToStyle(webvttCssStyle, parseSelector);
            String str2 = null;
            boolean z = false;
            while (true) {
                str = RULE_END;
                if (z) {
                    break;
                }
                int position2 = this.styleInput.getPosition();
                str2 = parseNextToken(this.styleInput, this.stringBuilder);
                boolean z2 = str2 == null || str.equals(str2);
                if (!z2) {
                    this.styleInput.setPosition(position2);
                    parseStyleDeclaration(this.styleInput, webvttCssStyle, this.stringBuilder);
                }
                z = z2;
            }
            if (str.equals(str2)) {
                arrayList.add(webvttCssStyle);
            }
        }
    }

    private static String parseSelector(ParsableByteArray parsableByteArray, StringBuilder sb) {
        skipWhitespaceAndComments(parsableByteArray);
        if (parsableByteArray.bytesLeft() < 5) {
            return null;
        }
        if (!"::cue".equals(parsableByteArray.readString(5))) {
            return null;
        }
        int position = parsableByteArray.getPosition();
        String parseNextToken = parseNextToken(parsableByteArray, sb);
        if (parseNextToken == null) {
            return null;
        }
        if (RULE_START.equals(parseNextToken)) {
            parsableByteArray.setPosition(position);
            return "";
        }
        String readCueTarget = "(".equals(parseNextToken) ? readCueTarget(parsableByteArray) : null;
        if (!")".equals(parseNextToken(parsableByteArray, sb))) {
            return null;
        }
        return readCueTarget;
    }

    private static String readCueTarget(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        boolean z = false;
        while (position < limit && !z) {
            int i = position + 1;
            z = ((char) parsableByteArray.data[position]) == ')';
            position = i;
        }
        return parsableByteArray.readString((position - 1) - parsableByteArray.getPosition()).trim();
    }

    private static void parseStyleDeclaration(ParsableByteArray parsableByteArray, WebvttCssStyle webvttCssStyle, StringBuilder sb) {
        skipWhitespaceAndComments(parsableByteArray);
        String parseIdentifier = parseIdentifier(parsableByteArray, sb);
        String str = "";
        if (!str.equals(parseIdentifier)) {
            if (":".equals(parseNextToken(parsableByteArray, sb))) {
                skipWhitespaceAndComments(parsableByteArray);
                String parsePropertyValue = parsePropertyValue(parsableByteArray, sb);
                if (parsePropertyValue != null && !str.equals(parsePropertyValue)) {
                    int position = parsableByteArray.getPosition();
                    String parseNextToken = parseNextToken(parsableByteArray, sb);
                    if (!";".equals(parseNextToken)) {
                        if (RULE_END.equals(parseNextToken)) {
                            parsableByteArray.setPosition(position);
                        }
                    }
                    if (TtmlNode.ATTR_TTS_COLOR.equals(parseIdentifier)) {
                        webvttCssStyle.setFontColor(ColorParser.parseCssColor(parsePropertyValue));
                    } else if (PROPERTY_BGCOLOR.equals(parseIdentifier)) {
                        webvttCssStyle.setBackgroundColor(ColorParser.parseCssColor(parsePropertyValue));
                    } else if (PROPERTY_TEXT_DECORATION.equals(parseIdentifier)) {
                        if ("underline".equals(parsePropertyValue)) {
                            webvttCssStyle.setUnderline(true);
                        }
                    } else if (PROPERTY_FONT_FAMILY.equals(parseIdentifier)) {
                        webvttCssStyle.setFontFamily(parsePropertyValue);
                    } else if (PROPERTY_FONT_WEIGHT.equals(parseIdentifier)) {
                        if ("bold".equals(parsePropertyValue)) {
                            webvttCssStyle.setBold(true);
                        }
                    } else if (PROPERTY_FONT_STYLE.equals(parseIdentifier) && "italic".equals(parsePropertyValue)) {
                        webvttCssStyle.setItalic(true);
                    }
                }
            }
        }
    }

    static void skipWhitespaceAndComments(ParsableByteArray parsableByteArray) {
        while (true) {
            boolean z = true;
            while (parsableByteArray.bytesLeft() > 0 && z) {
                if (!maybeSkipWhitespace(parsableByteArray) && !maybeSkipComment(parsableByteArray)) {
                    z = false;
                }
            }
            return;
        }
    }

    static String parseNextToken(ParsableByteArray parsableByteArray, StringBuilder sb) {
        skipWhitespaceAndComments(parsableByteArray);
        if (parsableByteArray.bytesLeft() == 0) {
            return null;
        }
        String parseIdentifier = parseIdentifier(parsableByteArray, sb);
        String str = "";
        if (!str.equals(parseIdentifier)) {
            return parseIdentifier;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append((char) parsableByteArray.readUnsignedByte());
        return sb2.toString();
    }

    private static boolean maybeSkipWhitespace(ParsableByteArray parsableByteArray) {
        char peekCharAtPosition = peekCharAtPosition(parsableByteArray, parsableByteArray.getPosition());
        if (peekCharAtPosition != 9 && peekCharAtPosition != 10 && peekCharAtPosition != 12 && peekCharAtPosition != 13 && peekCharAtPosition != ' ') {
            return false;
        }
        parsableByteArray.skipBytes(1);
        return true;
    }

    static void skipStyleBlock(ParsableByteArray parsableByteArray) {
        do {
        } while (!TextUtils.isEmpty(parsableByteArray.readLine()));
    }

    private static char peekCharAtPosition(ParsableByteArray parsableByteArray, int i) {
        return (char) parsableByteArray.data[i];
    }

    private static String parsePropertyValue(ParsableByteArray parsableByteArray, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder();
        boolean z = false;
        while (!z) {
            int position = parsableByteArray.getPosition();
            String parseNextToken = parseNextToken(parsableByteArray, sb);
            if (parseNextToken == null) {
                return null;
            }
            if (RULE_END.equals(parseNextToken) || ";".equals(parseNextToken)) {
                parsableByteArray.setPosition(position);
                z = true;
            } else {
                sb2.append(parseNextToken);
            }
        }
        return sb2.toString();
    }

    private static boolean maybeSkipComment(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        byte[] bArr = parsableByteArray.data;
        if (position + 2 <= limit) {
            int i = position + 1;
            if (bArr[position] == 47) {
                int i2 = i + 1;
                if (bArr[i] == 42) {
                    while (true) {
                        int i3 = i2 + 1;
                        if (i3 >= limit) {
                            parsableByteArray.skipBytes(limit - parsableByteArray.getPosition());
                            return true;
                        } else if (((char) bArr[i2]) == '*' && ((char) bArr[i3]) == '/') {
                            i2 = i3 + 1;
                            limit = i2;
                        } else {
                            i2 = i3;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static String parseIdentifier(ParsableByteArray parsableByteArray, StringBuilder sb) {
        boolean z = false;
        sb.setLength(0);
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit && !z) {
            char c = (char) parsableByteArray.data[position];
            if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && !((c >= '0' && c <= '9') || c == '#' || c == '-' || c == '.' || c == '_'))) {
                z = true;
            } else {
                position++;
                sb.append(c);
            }
        }
        parsableByteArray.skipBytes(position - parsableByteArray.getPosition());
        return sb.toString();
    }

    private void applySelectorToStyle(WebvttCssStyle webvttCssStyle, String str) {
        if (!"".equals(str)) {
            int indexOf = str.indexOf(91);
            if (indexOf != -1) {
                Matcher matcher = VOICE_NAME_PATTERN.matcher(str.substring(indexOf));
                if (matcher.matches()) {
                    webvttCssStyle.setTargetVoice(matcher.group(1));
                }
                str = str.substring(0, indexOf);
            }
            String[] split = Util.split(str, "\\.");
            String str2 = split[0];
            int indexOf2 = str2.indexOf(35);
            if (indexOf2 != -1) {
                webvttCssStyle.setTargetTagName(str2.substring(0, indexOf2));
                webvttCssStyle.setTargetId(str2.substring(indexOf2 + 1));
            } else {
                webvttCssStyle.setTargetTagName(str2);
            }
            if (split.length > 1) {
                webvttCssStyle.setTargetClasses((String[]) Util.nullSafeArrayCopyOfRange(split, 1, split.length));
            }
        }
    }
}
