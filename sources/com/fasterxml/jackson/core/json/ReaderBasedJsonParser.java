package com.fasterxml.jackson.core.json;

import com.braintreepayments.api.models.PostalAddressParser;
import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.base.ParserBase;
import com.fasterxml.jackson.core.p036io.CharTypes;
import com.fasterxml.jackson.core.p036io.IOContext;
import com.fasterxml.jackson.core.sym.CharsToNameCanonicalizer;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import com.fasterxml.jackson.core.util.TextBuffer;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class ReaderBasedJsonParser extends ParserBase {
    protected static final int FEAT_MASK_TRAILING_COMMA = Feature.ALLOW_TRAILING_COMMA.getMask();
    protected static final int[] _icLatin1 = CharTypes.getInputCodeLatin1();
    protected boolean _bufferRecyclable;
    protected final int _hashSeed;
    protected char[] _inputBuffer;
    protected int _nameStartCol;
    protected long _nameStartOffset;
    protected int _nameStartRow;
    protected ObjectCodec _objectCodec;
    protected Reader _reader;
    protected final CharsToNameCanonicalizer _symbols;
    protected boolean _tokenIncomplete;

    public ReaderBasedJsonParser(IOContext iOContext, int i, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer, char[] cArr, int i2, int i3, boolean z) {
        super(iOContext, i);
        this._reader = reader;
        this._inputBuffer = cArr;
        this._inputPtr = i2;
        this._inputEnd = i3;
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
        this._bufferRecyclable = z;
    }

    public ReaderBasedJsonParser(IOContext iOContext, int i, Reader reader, ObjectCodec objectCodec, CharsToNameCanonicalizer charsToNameCanonicalizer) {
        super(iOContext, i);
        this._reader = reader;
        this._inputBuffer = iOContext.allocTokenBuffer();
        this._inputPtr = 0;
        this._inputEnd = 0;
        this._objectCodec = objectCodec;
        this._symbols = charsToNameCanonicalizer;
        this._hashSeed = charsToNameCanonicalizer.hashSeed();
        this._bufferRecyclable = true;
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public void setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    public int releaseBuffered(Writer writer) throws IOException {
        int i = this._inputEnd - this._inputPtr;
        if (i < 1) {
            return 0;
        }
        writer.write(this._inputBuffer, this._inputPtr, i);
        return i;
    }

    public Object getInputSource() {
        return this._reader;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public char getNextChar(String str) throws IOException {
        return getNextChar(str, null);
    }

    /* access modifiers changed from: protected */
    public char getNextChar(String str, JsonToken jsonToken) throws IOException {
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(str, jsonToken);
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        return cArr[i];
    }

    /* access modifiers changed from: protected */
    public void _closeInput() throws IOException {
        if (this._reader != null) {
            if (this._ioContext.isResourceManaged() || isEnabled(Feature.AUTO_CLOSE_SOURCE)) {
                this._reader.close();
            }
            this._reader = null;
        }
    }

    /* access modifiers changed from: protected */
    public void _releaseBuffers() throws IOException {
        super._releaseBuffers();
        this._symbols.release();
        if (this._bufferRecyclable) {
            char[] cArr = this._inputBuffer;
            if (cArr != null) {
                this._inputBuffer = null;
                this._ioContext.releaseTokenBuffer(cArr);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void _loadMoreGuaranteed() throws IOException {
        if (!_loadMore()) {
            _reportInvalidEOF();
        }
    }

    /* access modifiers changed from: protected */
    public boolean _loadMore() throws IOException {
        int i = this._inputEnd;
        long j = (long) i;
        this._currInputProcessed += j;
        this._currInputRowStart -= i;
        this._nameStartOffset -= j;
        Reader reader = this._reader;
        if (reader != null) {
            char[] cArr = this._inputBuffer;
            int read = reader.read(cArr, 0, cArr.length);
            if (read > 0) {
                this._inputPtr = 0;
                this._inputEnd = read;
                return true;
            }
            _closeInput();
            if (read == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("Reader returned 0 characters when trying to read ");
                sb.append(this._inputEnd);
                throw new IOException(sb.toString());
            }
        }
        return false;
    }

    public final String getText() throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken != JsonToken.VALUE_STRING) {
            return _getText2(jsonToken);
        }
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.contentsAsString();
    }

    public int getText(Writer writer) throws IOException {
        JsonToken jsonToken = this._currToken;
        if (jsonToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.contentsToWriter(writer);
        } else if (jsonToken == JsonToken.FIELD_NAME) {
            String currentName = this._parsingContext.getCurrentName();
            writer.write(currentName);
            return currentName.length();
        } else if (jsonToken == null) {
            return 0;
        } else {
            if (jsonToken.isNumeric()) {
                return this._textBuffer.contentsToWriter(writer);
            }
            char[] asCharArray = jsonToken.asCharArray();
            writer.write(asCharArray);
            return asCharArray.length;
        }
    }

    public final String getValueAsString() throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.contentsAsString();
        } else if (this._currToken == JsonToken.FIELD_NAME) {
            return getCurrentName();
        } else {
            return super.getValueAsString(null);
        }
    }

    public final String getValueAsString(String str) throws IOException {
        if (this._currToken == JsonToken.VALUE_STRING) {
            if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.contentsAsString();
        } else if (this._currToken == JsonToken.FIELD_NAME) {
            return getCurrentName();
        } else {
            return super.getValueAsString(str);
        }
    }

    /* access modifiers changed from: protected */
    public final String _getText2(JsonToken jsonToken) {
        if (jsonToken == null) {
            return null;
        }
        int id = jsonToken.mo15042id();
        if (id == 5) {
            return this._parsingContext.getCurrentName();
        }
        if (id == 6 || id == 7 || id == 8) {
            return this._textBuffer.contentsAsString();
        }
        return jsonToken.asString();
    }

    public final char[] getTextCharacters() throws IOException {
        if (this._currToken == null) {
            return null;
        }
        int id = this._currToken.mo15042id();
        if (id != 5) {
            if (id != 6) {
                if (!(id == 7 || id == 8)) {
                    return this._currToken.asCharArray();
                }
            } else if (this._tokenIncomplete) {
                this._tokenIncomplete = false;
                _finishString();
            }
            return this._textBuffer.getTextBuffer();
        }
        if (!this._nameCopied) {
            String currentName = this._parsingContext.getCurrentName();
            int length = currentName.length();
            if (this._nameCopyBuffer == null) {
                this._nameCopyBuffer = this._ioContext.allocNameCopyBuffer(length);
            } else if (this._nameCopyBuffer.length < length) {
                this._nameCopyBuffer = new char[length];
            }
            currentName.getChars(0, length, this._nameCopyBuffer, 0);
            this._nameCopied = true;
        }
        return this._nameCopyBuffer;
    }

    public final int getTextLength() throws IOException {
        if (this._currToken == null) {
            return 0;
        }
        int id = this._currToken.mo15042id();
        if (id == 5) {
            return this._parsingContext.getCurrentName().length();
        }
        if (id != 6) {
            if (!(id == 7 || id == 8)) {
                return this._currToken.asCharArray().length;
            }
        } else if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
        return this._textBuffer.size();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0016, code lost:
        if (r0 != 8) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int getTextOffset() throws java.io.IOException {
        /*
            r3 = this;
            com.fasterxml.jackson.core.JsonToken r0 = r3._currToken
            r1 = 0
            if (r0 == 0) goto L_0x0029
            com.fasterxml.jackson.core.JsonToken r0 = r3._currToken
            int r0 = r0.mo15042id()
            r2 = 5
            if (r0 == r2) goto L_0x0029
            r2 = 6
            if (r0 == r2) goto L_0x0019
            r2 = 7
            if (r0 == r2) goto L_0x0022
            r2 = 8
            if (r0 == r2) goto L_0x0022
            goto L_0x0029
        L_0x0019:
            boolean r0 = r3._tokenIncomplete
            if (r0 == 0) goto L_0x0022
            r3._tokenIncomplete = r1
            r3._finishString()
        L_0x0022:
            com.fasterxml.jackson.core.util.TextBuffer r0 = r3._textBuffer
            int r0 = r0.getTextOffset()
            return r0
        L_0x0029:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser.getTextOffset():int");
    }

    public byte[] getBinaryValue(Base64Variant base64Variant) throws IOException {
        if (this._currToken == JsonToken.VALUE_EMBEDDED_OBJECT && this._binaryValue != null) {
            return this._binaryValue;
        }
        if (this._currToken != JsonToken.VALUE_STRING) {
            StringBuilder sb = new StringBuilder();
            sb.append("Current token (");
            sb.append(this._currToken);
            sb.append(") not VALUE_STRING or VALUE_EMBEDDED_OBJECT, can not access as binary");
            _reportError(sb.toString());
        }
        if (this._tokenIncomplete) {
            try {
                this._binaryValue = _decodeBase64(base64Variant);
                this._tokenIncomplete = false;
            } catch (IllegalArgumentException e) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Failed to decode VALUE_STRING as base64 (");
                sb2.append(base64Variant);
                sb2.append("): ");
                sb2.append(e.getMessage());
                throw _constructError(sb2.toString());
            }
        } else if (this._binaryValue == null) {
            ByteArrayBuilder _getByteArrayBuilder = _getByteArrayBuilder();
            _decodeBase64(getText(), _getByteArrayBuilder, base64Variant);
            this._binaryValue = _getByteArrayBuilder.toByteArray();
        }
        return this._binaryValue;
    }

    public int readBinaryValue(Base64Variant base64Variant, OutputStream outputStream) throws IOException {
        if (!this._tokenIncomplete || this._currToken != JsonToken.VALUE_STRING) {
            byte[] binaryValue = getBinaryValue(base64Variant);
            outputStream.write(binaryValue);
            return binaryValue.length;
        }
        byte[] allocBase64Buffer = this._ioContext.allocBase64Buffer();
        try {
            int _readBinary = _readBinary(base64Variant, outputStream, allocBase64Buffer);
            return _readBinary;
        } finally {
            this._ioContext.releaseBase64Buffer(allocBase64Buffer);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0035, code lost:
        if (r10 < 0) goto L_0x015f;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int _readBinary(com.fasterxml.jackson.core.Base64Variant r17, java.io.OutputStream r18, byte[] r19) throws java.io.IOException {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r2 = r18
            r3 = r19
            int r4 = r3.length
            r5 = 3
            int r4 = r4 - r5
            r6 = 0
            r7 = 0
            r8 = 0
        L_0x000e:
            int r9 = r0._inputPtr
            int r10 = r0._inputEnd
            if (r9 < r10) goto L_0x0017
            r16._loadMoreGuaranteed()
        L_0x0017:
            char[] r9 = r0._inputBuffer
            int r10 = r0._inputPtr
            int r11 = r10 + 1
            r0._inputPtr = r11
            char r9 = r9[r10]
            r10 = 32
            if (r9 <= r10) goto L_0x015f
            int r10 = r1.decodeBase64Char(r9)
            r11 = 34
            if (r10 >= 0) goto L_0x0039
            if (r9 != r11) goto L_0x0031
            goto L_0x0124
        L_0x0031:
            int r10 = r0._decodeBase64Escape(r1, r9, r6)
            if (r10 >= 0) goto L_0x0039
            goto L_0x015f
        L_0x0039:
            if (r8 <= r4) goto L_0x0040
            int r7 = r7 + r8
            r2.write(r3, r6, r8)
            r8 = 0
        L_0x0040:
            int r9 = r0._inputPtr
            int r12 = r0._inputEnd
            if (r9 < r12) goto L_0x0049
            r16._loadMoreGuaranteed()
        L_0x0049:
            char[] r9 = r0._inputBuffer
            int r12 = r0._inputPtr
            int r13 = r12 + 1
            r0._inputPtr = r13
            char r9 = r9[r12]
            int r12 = r1.decodeBase64Char(r9)
            r13 = 1
            if (r12 >= 0) goto L_0x005e
            int r12 = r0._decodeBase64Escape(r1, r9, r13)
        L_0x005e:
            int r9 = r10 << 6
            r9 = r9 | r12
            int r10 = r0._inputPtr
            int r12 = r0._inputEnd
            if (r10 < r12) goto L_0x006a
            r16._loadMoreGuaranteed()
        L_0x006a:
            char[] r10 = r0._inputBuffer
            int r12 = r0._inputPtr
            int r14 = r12 + 1
            r0._inputPtr = r14
            char r10 = r10[r12]
            int r12 = r1.decodeBase64Char(r10)
            r14 = 2
            r15 = -2
            if (r12 >= 0) goto L_0x00e8
            if (r12 == r15) goto L_0x009d
            if (r10 != r11) goto L_0x0098
            int r4 = r9 >> 4
            int r5 = r8 + 1
            byte r4 = (byte) r4
            r3[r8] = r4
            boolean r4 = r17.usesPadding()
            if (r4 == 0) goto L_0x0095
            int r4 = r0._inputPtr
            int r4 = r4 - r13
            r0._inputPtr = r4
            r16._handleBase64MissingPadding(r17)
        L_0x0095:
            r8 = r5
            goto L_0x0124
        L_0x0098:
            int r10 = r0._decodeBase64Escape(r1, r10, r14)
            r12 = r10
        L_0x009d:
            if (r12 != r15) goto L_0x00e8
            int r10 = r0._inputPtr
            int r11 = r0._inputEnd
            if (r10 < r11) goto L_0x00a8
            r16._loadMoreGuaranteed()
        L_0x00a8:
            char[] r10 = r0._inputBuffer
            int r11 = r0._inputPtr
            int r12 = r11 + 1
            r0._inputPtr = r12
            char r10 = r10[r11]
            boolean r11 = r1.usesPaddingChar(r10)
            if (r11 != 0) goto L_0x00de
            int r11 = r0._decodeBase64Escape(r1, r10, r5)
            if (r11 != r15) goto L_0x00bf
            goto L_0x00de
        L_0x00bf:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "expected padding character '"
            r2.append(r3)
            char r3 = r17.getPaddingChar()
            r2.append(r3)
            java.lang.String r3 = "'"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.IllegalArgumentException r1 = r0.reportInvalidBase64Char(r1, r10, r5, r2)
            throw r1
        L_0x00de:
            int r9 = r9 >> 4
            int r10 = r8 + 1
            byte r9 = (byte) r9
            r3[r8] = r9
            r8 = r10
            goto L_0x000e
        L_0x00e8:
            int r9 = r9 << 6
            r9 = r9 | r12
            int r10 = r0._inputPtr
            int r12 = r0._inputEnd
            if (r10 < r12) goto L_0x00f4
            r16._loadMoreGuaranteed()
        L_0x00f4:
            char[] r10 = r0._inputBuffer
            int r12 = r0._inputPtr
            int r5 = r12 + 1
            r0._inputPtr = r5
            char r5 = r10[r12]
            int r10 = r1.decodeBase64Char(r5)
            if (r10 >= 0) goto L_0x0146
            if (r10 == r15) goto L_0x0134
            if (r5 != r11) goto L_0x012d
            int r4 = r9 >> 2
            int r5 = r8 + 1
            int r9 = r4 >> 8
            byte r9 = (byte) r9
            r3[r8] = r9
            int r8 = r5 + 1
            byte r4 = (byte) r4
            r3[r5] = r4
            boolean r4 = r17.usesPadding()
            if (r4 == 0) goto L_0x0124
            int r4 = r0._inputPtr
            int r4 = r4 - r13
            r0._inputPtr = r4
            r16._handleBase64MissingPadding(r17)
        L_0x0124:
            r0._tokenIncomplete = r6
            if (r8 <= 0) goto L_0x012c
            int r7 = r7 + r8
            r2.write(r3, r6, r8)
        L_0x012c:
            return r7
        L_0x012d:
            r11 = 3
            int r5 = r0._decodeBase64Escape(r1, r5, r11)
            r10 = r5
            goto L_0x0135
        L_0x0134:
            r11 = 3
        L_0x0135:
            if (r10 != r15) goto L_0x0147
            int r5 = r9 >> 2
            int r9 = r8 + 1
            int r10 = r5 >> 8
            byte r10 = (byte) r10
            r3[r8] = r10
            int r8 = r9 + 1
            byte r5 = (byte) r5
            r3[r9] = r5
            goto L_0x0160
        L_0x0146:
            r11 = 3
        L_0x0147:
            int r5 = r9 << 6
            r5 = r5 | r10
            int r9 = r8 + 1
            int r10 = r5 >> 16
            byte r10 = (byte) r10
            r3[r8] = r10
            int r8 = r9 + 1
            int r10 = r5 >> 8
            byte r10 = (byte) r10
            r3[r9] = r10
            int r9 = r8 + 1
            byte r5 = (byte) r5
            r3[r8] = r5
            r8 = r9
            goto L_0x0160
        L_0x015f:
            r11 = 3
        L_0x0160:
            r5 = 3
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser._readBinary(com.fasterxml.jackson.core.Base64Variant, java.io.OutputStream, byte[]):int");
    }

    public final JsonToken nextToken() throws IOException {
        JsonToken jsonToken;
        if (this._currToken == JsonToken.FIELD_NAME) {
            return _nextAfterName();
        }
        this._numTypesValid = 0;
        if (this._tokenIncomplete) {
            _skipString();
        }
        int _skipWSOrEnd = _skipWSOrEnd();
        if (_skipWSOrEnd < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (_skipWSOrEnd == 93 || _skipWSOrEnd == 125) {
            _closeScope(_skipWSOrEnd);
            return this._currToken;
        }
        if (this._parsingContext.expectComma()) {
            _skipWSOrEnd = _skipComma(_skipWSOrEnd);
            if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (_skipWSOrEnd == 93 || _skipWSOrEnd == 125)) {
                _closeScope(_skipWSOrEnd);
                return this._currToken;
            }
        }
        boolean inObject = this._parsingContext.inObject();
        if (inObject) {
            _updateNameLocation();
            this._parsingContext.setCurrentName(_skipWSOrEnd == 34 ? _parseName() : _handleOddName(_skipWSOrEnd));
            this._currToken = JsonToken.FIELD_NAME;
            _skipWSOrEnd = _skipColon();
        }
        _updateLocation();
        if (_skipWSOrEnd == 34) {
            this._tokenIncomplete = true;
            jsonToken = JsonToken.VALUE_STRING;
        } else if (_skipWSOrEnd == 45) {
            jsonToken = _parseNegNumber();
        } else if (_skipWSOrEnd == 91) {
            if (!inObject) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            }
            jsonToken = JsonToken.START_ARRAY;
        } else if (_skipWSOrEnd == 102) {
            _matchFalse();
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (_skipWSOrEnd != 110) {
            if (_skipWSOrEnd != 116) {
                if (_skipWSOrEnd == 123) {
                    if (!inObject) {
                        this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    }
                    jsonToken = JsonToken.START_OBJECT;
                } else if (_skipWSOrEnd != 125) {
                    switch (_skipWSOrEnd) {
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                            jsonToken = _parsePosNumber(_skipWSOrEnd);
                            break;
                        default:
                            jsonToken = _handleOddValue(_skipWSOrEnd);
                            break;
                    }
                } else {
                    _reportUnexpectedChar(_skipWSOrEnd, "expected a value");
                }
            }
            _matchTrue();
            jsonToken = JsonToken.VALUE_TRUE;
        } else {
            _matchNull();
            jsonToken = JsonToken.VALUE_NULL;
        }
        if (inObject) {
            this._nextToken = jsonToken;
            return this._currToken;
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    private final JsonToken _nextAfterName() {
        this._nameCopied = false;
        JsonToken jsonToken = this._nextToken;
        this._nextToken = null;
        if (jsonToken == JsonToken.START_ARRAY) {
            this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
        } else if (jsonToken == JsonToken.START_OBJECT) {
            this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
        }
        this._currToken = jsonToken;
        return jsonToken;
    }

    public void finishToken() throws IOException {
        if (this._tokenIncomplete) {
            this._tokenIncomplete = false;
            _finishString();
        }
    }

    public boolean nextFieldName(SerializableString serializableString) throws IOException {
        int i = 0;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            _nextAfterName();
            return false;
        }
        if (this._tokenIncomplete) {
            _skipString();
        }
        int _skipWSOrEnd = _skipWSOrEnd();
        if (_skipWSOrEnd < 0) {
            close();
            this._currToken = null;
            return false;
        }
        this._binaryValue = null;
        if (_skipWSOrEnd == 93 || _skipWSOrEnd == 125) {
            _closeScope(_skipWSOrEnd);
            return false;
        }
        if (this._parsingContext.expectComma()) {
            _skipWSOrEnd = _skipComma(_skipWSOrEnd);
            if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (_skipWSOrEnd == 93 || _skipWSOrEnd == 125)) {
                _closeScope(_skipWSOrEnd);
                return false;
            }
        }
        if (!this._parsingContext.inObject()) {
            _updateLocation();
            _nextTokenNotInObject(_skipWSOrEnd);
            return false;
        }
        _updateNameLocation();
        if (_skipWSOrEnd == 34) {
            char[] asQuotedChars = serializableString.asQuotedChars();
            int length = asQuotedChars.length;
            if (this._inputPtr + length + 4 < this._inputEnd) {
                int i2 = this._inputPtr + length;
                if (this._inputBuffer[i2] == '\"') {
                    int i3 = this._inputPtr;
                    while (i3 != i2) {
                        if (asQuotedChars[i] == this._inputBuffer[i3]) {
                            i++;
                            i3++;
                        }
                    }
                    this._parsingContext.setCurrentName(serializableString.getValue());
                    _isNextTokenNameYes(_skipColonFast(i3 + 1));
                    return true;
                }
            }
        }
        return _isNextTokenNameMaybe(_skipWSOrEnd, serializableString.getValue());
    }

    public String nextFieldName() throws IOException {
        JsonToken jsonToken;
        this._numTypesValid = 0;
        if (this._currToken == JsonToken.FIELD_NAME) {
            _nextAfterName();
            return null;
        }
        if (this._tokenIncomplete) {
            _skipString();
        }
        int _skipWSOrEnd = _skipWSOrEnd();
        if (_skipWSOrEnd < 0) {
            close();
            this._currToken = null;
            return null;
        }
        this._binaryValue = null;
        if (_skipWSOrEnd == 93 || _skipWSOrEnd == 125) {
            _closeScope(_skipWSOrEnd);
            return null;
        }
        if (this._parsingContext.expectComma()) {
            _skipWSOrEnd = _skipComma(_skipWSOrEnd);
            if ((this._features & FEAT_MASK_TRAILING_COMMA) != 0 && (_skipWSOrEnd == 93 || _skipWSOrEnd == 125)) {
                _closeScope(_skipWSOrEnd);
                return null;
            }
        }
        if (!this._parsingContext.inObject()) {
            _updateLocation();
            _nextTokenNotInObject(_skipWSOrEnd);
            return null;
        }
        _updateNameLocation();
        String _parseName = _skipWSOrEnd == 34 ? _parseName() : _handleOddName(_skipWSOrEnd);
        this._parsingContext.setCurrentName(_parseName);
        this._currToken = JsonToken.FIELD_NAME;
        int _skipColon = _skipColon();
        _updateLocation();
        if (_skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return _parseName;
        }
        if (_skipColon == 45) {
            jsonToken = _parseNegNumber();
        } else if (_skipColon == 91) {
            jsonToken = JsonToken.START_ARRAY;
        } else if (_skipColon == 102) {
            _matchFalse();
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (_skipColon == 110) {
            _matchNull();
            jsonToken = JsonToken.VALUE_NULL;
        } else if (_skipColon == 116) {
            _matchTrue();
            jsonToken = JsonToken.VALUE_TRUE;
        } else if (_skipColon != 123) {
            switch (_skipColon) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    jsonToken = _parsePosNumber(_skipColon);
                    break;
                default:
                    jsonToken = _handleOddValue(_skipColon);
                    break;
            }
        } else {
            jsonToken = JsonToken.START_OBJECT;
        }
        this._nextToken = jsonToken;
        return _parseName;
    }

    private final void _isNextTokenNameYes(int i) throws IOException {
        this._currToken = JsonToken.FIELD_NAME;
        _updateLocation();
        if (i == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
        } else if (i == 45) {
            this._nextToken = _parseNegNumber();
        } else if (i == 91) {
            this._nextToken = JsonToken.START_ARRAY;
        } else if (i == 102) {
            _matchToken("false", 1);
            this._nextToken = JsonToken.VALUE_FALSE;
        } else if (i == 110) {
            _matchToken("null", 1);
            this._nextToken = JsonToken.VALUE_NULL;
        } else if (i == 116) {
            _matchToken("true", 1);
            this._nextToken = JsonToken.VALUE_TRUE;
        } else if (i != 123) {
            switch (i) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    this._nextToken = _parsePosNumber(i);
                    return;
                default:
                    this._nextToken = _handleOddValue(i);
                    return;
            }
        } else {
            this._nextToken = JsonToken.START_OBJECT;
        }
    }

    /* access modifiers changed from: protected */
    public boolean _isNextTokenNameMaybe(int i, String str) throws IOException {
        JsonToken jsonToken;
        String _parseName = i == 34 ? _parseName() : _handleOddName(i);
        this._parsingContext.setCurrentName(_parseName);
        this._currToken = JsonToken.FIELD_NAME;
        int _skipColon = _skipColon();
        _updateLocation();
        if (_skipColon == 34) {
            this._tokenIncomplete = true;
            this._nextToken = JsonToken.VALUE_STRING;
            return str.equals(_parseName);
        }
        if (_skipColon == 45) {
            jsonToken = _parseNegNumber();
        } else if (_skipColon == 91) {
            jsonToken = JsonToken.START_ARRAY;
        } else if (_skipColon == 102) {
            _matchFalse();
            jsonToken = JsonToken.VALUE_FALSE;
        } else if (_skipColon == 110) {
            _matchNull();
            jsonToken = JsonToken.VALUE_NULL;
        } else if (_skipColon == 116) {
            _matchTrue();
            jsonToken = JsonToken.VALUE_TRUE;
        } else if (_skipColon != 123) {
            switch (_skipColon) {
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                    jsonToken = _parsePosNumber(_skipColon);
                    break;
                default:
                    jsonToken = _handleOddValue(_skipColon);
                    break;
            }
        } else {
            jsonToken = JsonToken.START_OBJECT;
        }
        this._nextToken = jsonToken;
        return str.equals(_parseName);
    }

    private final JsonToken _nextTokenNotInObject(int i) throws IOException {
        if (i == 34) {
            this._tokenIncomplete = true;
            JsonToken jsonToken = JsonToken.VALUE_STRING;
            this._currToken = jsonToken;
            return jsonToken;
        }
        if (i != 44) {
            if (i == 45) {
                JsonToken _parseNegNumber = _parseNegNumber();
                this._currToken = _parseNegNumber;
                return _parseNegNumber;
            } else if (i == 91) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
                JsonToken jsonToken2 = JsonToken.START_ARRAY;
                this._currToken = jsonToken2;
                return jsonToken2;
            } else if (i != 93) {
                if (i == 102) {
                    _matchToken("false", 1);
                    JsonToken jsonToken3 = JsonToken.VALUE_FALSE;
                    this._currToken = jsonToken3;
                    return jsonToken3;
                } else if (i == 110) {
                    _matchToken("null", 1);
                    JsonToken jsonToken4 = JsonToken.VALUE_NULL;
                    this._currToken = jsonToken4;
                    return jsonToken4;
                } else if (i == 116) {
                    _matchToken("true", 1);
                    JsonToken jsonToken5 = JsonToken.VALUE_TRUE;
                    this._currToken = jsonToken5;
                    return jsonToken5;
                } else if (i != 123) {
                    switch (i) {
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                        case 54:
                        case 55:
                        case 56:
                        case 57:
                            JsonToken _parsePosNumber = _parsePosNumber(i);
                            this._currToken = _parsePosNumber;
                            return _parsePosNumber;
                    }
                    JsonToken _handleOddValue = _handleOddValue(i);
                    this._currToken = _handleOddValue;
                    return _handleOddValue;
                } else {
                    this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
                    JsonToken jsonToken6 = JsonToken.START_OBJECT;
                    this._currToken = jsonToken6;
                    return jsonToken6;
                }
            }
        }
        if (isEnabled(Feature.ALLOW_MISSING_VALUES)) {
            this._inputPtr--;
            JsonToken jsonToken7 = JsonToken.VALUE_NULL;
            this._currToken = jsonToken7;
            return jsonToken7;
        }
        JsonToken _handleOddValue2 = _handleOddValue(i);
        this._currToken = _handleOddValue2;
        return _handleOddValue2;
    }

    public final String nextTextValue() throws IOException {
        String str = null;
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_STRING) {
                if (this._tokenIncomplete) {
                    this._tokenIncomplete = false;
                    _finishString();
                }
                return this._textBuffer.contentsAsString();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (jsonToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return null;
        }
        if (nextToken() == JsonToken.VALUE_STRING) {
            str = getText();
        }
        return str;
    }

    public final int nextIntValue(int i) throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return getIntValue();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (jsonToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return i;
        }
        if (nextToken() == JsonToken.VALUE_NUMBER_INT) {
            i = getIntValue();
        }
        return i;
    }

    public final long nextLongValue(long j) throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_NUMBER_INT) {
                return getLongValue();
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (jsonToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return j;
        }
        if (nextToken() == JsonToken.VALUE_NUMBER_INT) {
            j = getLongValue();
        }
        return j;
    }

    public final Boolean nextBooleanValue() throws IOException {
        if (this._currToken == JsonToken.FIELD_NAME) {
            this._nameCopied = false;
            JsonToken jsonToken = this._nextToken;
            this._nextToken = null;
            this._currToken = jsonToken;
            if (jsonToken == JsonToken.VALUE_TRUE) {
                return Boolean.TRUE;
            }
            if (jsonToken == JsonToken.VALUE_FALSE) {
                return Boolean.FALSE;
            }
            if (jsonToken == JsonToken.START_ARRAY) {
                this._parsingContext = this._parsingContext.createChildArrayContext(this._tokenInputRow, this._tokenInputCol);
            } else if (jsonToken == JsonToken.START_OBJECT) {
                this._parsingContext = this._parsingContext.createChildObjectContext(this._tokenInputRow, this._tokenInputCol);
            }
            return null;
        }
        JsonToken nextToken = nextToken();
        if (nextToken != null) {
            int id = nextToken.mo15042id();
            if (id == 9) {
                return Boolean.TRUE;
            }
            if (id == 10) {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public final JsonToken _parsePosNumber(int i) throws IOException {
        int i2 = this._inputPtr;
        int i3 = i2 - 1;
        int i4 = this._inputEnd;
        if (i == 48) {
            return _parseNumber2(false, i3);
        }
        int i5 = 1;
        while (i2 < i4) {
            int i6 = i2 + 1;
            char c = this._inputBuffer[i2];
            if (c >= '0' && c <= '9') {
                i5++;
                i2 = i6;
            } else if (c == '.' || c == 'e' || c == 'E') {
                this._inputPtr = i6;
                return _parseFloat(c, i3, i6, false, i5);
            } else {
                int i7 = i6 - 1;
                this._inputPtr = i7;
                if (this._parsingContext.inRoot()) {
                    _verifyRootSpace(c);
                }
                this._textBuffer.resetWithShared(this._inputBuffer, i3, i7 - i3);
                return resetInt(false, i5);
            }
        }
        this._inputPtr = i3;
        return _parseNumber2(false, i3);
    }

    /* JADX WARNING: type inference failed for: r7v0, types: [int] */
    /* JADX WARNING: type inference failed for: r7v1 */
    /* JADX WARNING: type inference failed for: r7v2, types: [int] */
    /* JADX WARNING: type inference failed for: r7v5, types: [char[]] */
    /* JADX WARNING: type inference failed for: r7v6, types: [char] */
    /* JADX WARNING: type inference failed for: r7v7, types: [int] */
    /* JADX WARNING: type inference failed for: r7v8, types: [char[]] */
    /* JADX WARNING: type inference failed for: r7v9, types: [char] */
    /* JADX WARNING: type inference failed for: r7v11, types: [char[]] */
    /* JADX WARNING: type inference failed for: r7v12, types: [char] */
    /* JADX WARNING: type inference failed for: r7v14 */
    /* JADX WARNING: type inference failed for: r4v3, types: [char[]] */
    /* JADX WARNING: type inference failed for: r9v11, types: [int, char] */
    /* JADX WARNING: type inference failed for: r7v19 */
    /* JADX WARNING: type inference failed for: r7v21 */
    /* JADX WARNING: type inference failed for: r7v22 */
    /* JADX WARNING: type inference failed for: r7v23 */
    /* JADX WARNING: type inference failed for: r7v24 */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r7v12, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r7v6, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r7v9, types: [char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char, code=null, for r9v11, types: [int, char] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r4v3, types: [char[]] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r7v11, types: [char[]] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r7v5, types: [char[]] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=char[], code=null, for r7v8, types: [char[]] */
    /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=null, for r7v0, types: [int] */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r7v14
      assigns: []
      uses: []
      mth insns count: 69
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
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0077  */
    /* JADX WARNING: Unknown variable types count: 11 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final com.fasterxml.jackson.core.JsonToken _parseFloat(int r7, int r8, int r9, boolean r10, int r11) throws java.io.IOException {
        /*
            r6 = this;
            int r0 = r6._inputEnd
            r1 = 57
            r2 = 48
            r3 = 0
            r4 = 46
            if (r7 != r4) goto L_0x002d
            r7 = 0
        L_0x000c:
            if (r9 < r0) goto L_0x0013
            com.fasterxml.jackson.core.JsonToken r7 = r6._parseNumber2(r10, r8)
            return r7
        L_0x0013:
            char[] r4 = r6._inputBuffer
            int r5 = r9 + 1
            char r9 = r4[r9]
            if (r9 < r2) goto L_0x0022
            if (r9 <= r1) goto L_0x001e
            goto L_0x0022
        L_0x001e:
            int r7 = r7 + 1
            r9 = r5
            goto L_0x000c
        L_0x0022:
            if (r7 != 0) goto L_0x0029
            java.lang.String r4 = "Decimal point not followed by a digit"
            r6.reportUnexpectedNumberChar(r9, r4)
        L_0x0029:
            r4 = r7
            r7 = r9
            r9 = r5
            goto L_0x002e
        L_0x002d:
            r4 = 0
        L_0x002e:
            r5 = 101(0x65, float:1.42E-43)
            if (r7 == r5) goto L_0x0036
            r5 = 69
            if (r7 != r5) goto L_0x007c
        L_0x0036:
            if (r9 < r0) goto L_0x003f
            r6._inputPtr = r8
            com.fasterxml.jackson.core.JsonToken r7 = r6._parseNumber2(r10, r8)
            return r7
        L_0x003f:
            char[] r7 = r6._inputBuffer
            int r5 = r9 + 1
            char r7 = r7[r9]
            r9 = 45
            if (r7 == r9) goto L_0x0050
            r9 = 43
            if (r7 != r9) goto L_0x004e
            goto L_0x0050
        L_0x004e:
            r9 = r5
            goto L_0x005f
        L_0x0050:
            if (r5 < r0) goto L_0x0059
            r6._inputPtr = r8
            com.fasterxml.jackson.core.JsonToken r7 = r6._parseNumber2(r10, r8)
            return r7
        L_0x0059:
            char[] r7 = r6._inputBuffer
            int r9 = r5 + 1
            char r7 = r7[r5]
        L_0x005f:
            if (r7 > r1) goto L_0x0075
            if (r7 < r2) goto L_0x0075
            int r3 = r3 + 1
            if (r9 < r0) goto L_0x006e
            r6._inputPtr = r8
            com.fasterxml.jackson.core.JsonToken r7 = r6._parseNumber2(r10, r8)
            return r7
        L_0x006e:
            char[] r7 = r6._inputBuffer
            int r5 = r9 + 1
            char r7 = r7[r9]
            goto L_0x004e
        L_0x0075:
            if (r3 != 0) goto L_0x007c
            java.lang.String r0 = "Exponent indicator not followed by a digit"
            r6.reportUnexpectedNumberChar(r7, r0)
        L_0x007c:
            int r9 = r9 + -1
            r6._inputPtr = r9
            com.fasterxml.jackson.core.json.JsonReadContext r0 = r6._parsingContext
            boolean r0 = r0.inRoot()
            if (r0 == 0) goto L_0x008b
            r6._verifyRootSpace(r7)
        L_0x008b:
            int r9 = r9 - r8
            com.fasterxml.jackson.core.util.TextBuffer r7 = r6._textBuffer
            char[] r0 = r6._inputBuffer
            r7.resetWithShared(r0, r8, r9)
            com.fasterxml.jackson.core.JsonToken r7 = r6.resetFloat(r10, r11, r4, r3)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser._parseFloat(int, int, int, boolean, int):com.fasterxml.jackson.core.JsonToken");
    }

    /* access modifiers changed from: protected */
    public final JsonToken _parseNegNumber() throws IOException {
        int i = this._inputPtr;
        int i2 = i - 1;
        int i3 = this._inputEnd;
        if (i >= i3) {
            return _parseNumber2(true, i2);
        }
        int i4 = i + 1;
        char c = this._inputBuffer[i];
        if (c > '9' || c < '0') {
            this._inputPtr = i4;
            return _handleInvalidNumberStart(c, true);
        } else if (c == '0') {
            return _parseNumber2(true, i2);
        } else {
            int i5 = 1;
            while (i4 < i3) {
                int i6 = i4 + 1;
                char c2 = this._inputBuffer[i4];
                if (c2 >= '0' && c2 <= '9') {
                    i5++;
                    i4 = i6;
                } else if (c2 == '.' || c2 == 'e' || c2 == 'E') {
                    this._inputPtr = i6;
                    return _parseFloat(c2, i2, i6, true, i5);
                } else {
                    int i7 = i6 - 1;
                    this._inputPtr = i7;
                    if (this._parsingContext.inRoot()) {
                        _verifyRootSpace(c2);
                    }
                    this._textBuffer.resetWithShared(this._inputBuffer, i2, i7 - i2);
                    return resetInt(true, i5);
                }
            }
            return _parseNumber2(true, i2);
        }
    }

    private final JsonToken _parseNumber2(boolean z, int i) throws IOException {
        int i2;
        char c;
        boolean z2;
        int i3;
        char c2;
        char nextChar;
        int i4;
        boolean z3 = z;
        this._inputPtr = z3 ? i + 1 : i;
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int i5 = 0;
        if (z3) {
            emptyAndGetCurrentSegment[0] = '-';
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (this._inputPtr < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            int i6 = this._inputPtr;
            this._inputPtr = i6 + 1;
            c = cArr[i6];
        } else {
            c = getNextChar("No digit following minus sign", JsonToken.VALUE_NUMBER_INT);
        }
        if (c == '0') {
            c = _verifyNoLeadingZeroes();
        }
        char[] cArr2 = emptyAndGetCurrentSegment;
        int i7 = 0;
        while (true) {
            if (c >= '0' && c <= '9') {
                i7++;
                if (i2 >= cArr2.length) {
                    cArr2 = this._textBuffer.finishCurrentSegment();
                    i2 = 0;
                }
                int i8 = i2 + 1;
                cArr2[i2] = c;
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    i2 = i8;
                    c = 0;
                    z2 = true;
                    break;
                }
                char[] cArr3 = this._inputBuffer;
                int i9 = this._inputPtr;
                this._inputPtr = i9 + 1;
                c = cArr3[i9];
                i2 = i8;
            } else {
                z2 = false;
            }
        }
        z2 = false;
        if (i7 == 0) {
            return _handleInvalidNumberStart(c, z3);
        }
        if (c == '.') {
            if (i2 >= cArr2.length) {
                cArr2 = this._textBuffer.finishCurrentSegment();
                i2 = 0;
            }
            int i10 = i2 + 1;
            cArr2[i2] = c;
            int i11 = 0;
            while (true) {
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    z2 = true;
                    break;
                }
                char[] cArr4 = this._inputBuffer;
                int i12 = this._inputPtr;
                this._inputPtr = i12 + 1;
                c = cArr4[i12];
                if (c < '0' || c > '9') {
                    break;
                }
                i11++;
                if (i10 >= cArr2.length) {
                    cArr2 = this._textBuffer.finishCurrentSegment();
                    i10 = 0;
                }
                int i13 = i10 + 1;
                cArr2[i10] = c;
                i10 = i13;
            }
            if (i11 == 0) {
                reportUnexpectedNumberChar(c, "Decimal point not followed by a digit");
            }
            int i14 = i10;
            i3 = i11;
            i2 = i14;
        } else {
            i3 = 0;
        }
        if (c == 'e' || c == 'E') {
            if (i2 >= cArr2.length) {
                cArr2 = this._textBuffer.finishCurrentSegment();
                i2 = 0;
            }
            int i15 = i2 + 1;
            cArr2[i2] = c;
            String str = "expected a digit for number exponent";
            if (this._inputPtr < this._inputEnd) {
                char[] cArr5 = this._inputBuffer;
                int i16 = this._inputPtr;
                this._inputPtr = i16 + 1;
                c2 = cArr5[i16];
            } else {
                c2 = getNextChar(str);
            }
            if (c2 == '-' || c2 == '+') {
                if (i15 >= cArr2.length) {
                    cArr2 = this._textBuffer.finishCurrentSegment();
                    i15 = 0;
                }
                int i17 = i15 + 1;
                cArr2[i15] = c2;
                if (this._inputPtr < this._inputEnd) {
                    char[] cArr6 = this._inputBuffer;
                    int i18 = this._inputPtr;
                    this._inputPtr = i18 + 1;
                    nextChar = cArr6[i18];
                } else {
                    nextChar = getNextChar(str);
                }
                i15 = i17;
            }
            char c3 = c2;
            int i19 = 0;
            while (true) {
                if (c <= '9' && c >= '0') {
                    i19++;
                    if (i15 >= cArr2.length) {
                        cArr2 = this._textBuffer.finishCurrentSegment();
                        i15 = 0;
                    }
                    i4 = i15 + 1;
                    cArr2[i15] = c;
                    if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                        i5 = i19;
                        z2 = true;
                        break;
                    }
                    char[] cArr7 = this._inputBuffer;
                    int i20 = this._inputPtr;
                    this._inputPtr = i20 + 1;
                    c3 = cArr7[i20];
                    i15 = i4;
                } else {
                    i5 = i19;
                    i4 = i15;
                }
            }
            i5 = i19;
            i4 = i15;
            if (i5 == 0) {
                reportUnexpectedNumberChar(c, "Exponent indicator not followed by a digit");
            }
        }
        if (!z2) {
            this._inputPtr--;
            if (this._parsingContext.inRoot()) {
                _verifyRootSpace(c);
            }
        }
        this._textBuffer.setCurrentLength(i2);
        return reset(z3, i7, i3, i5);
    }

    private final char _verifyNoLeadingZeroes() throws IOException {
        if (this._inputPtr < this._inputEnd) {
            char c = this._inputBuffer[this._inputPtr];
            if (c < '0' || c > '9') {
                return '0';
            }
        }
        return _verifyNLZ2();
    }

    private char _verifyNLZ2() throws IOException {
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            return '0';
        }
        char c = this._inputBuffer[this._inputPtr];
        if (c < '0' || c > '9') {
            return '0';
        }
        if (!isEnabled(Feature.ALLOW_NUMERIC_LEADING_ZEROS)) {
            reportInvalidNumber("Leading zeroes not allowed");
        }
        this._inputPtr++;
        if (c == '0') {
            do {
                if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                    break;
                }
                c = this._inputBuffer[this._inputPtr];
                if (c < '0' || c > '9') {
                    return '0';
                }
                this._inputPtr++;
            } while (c == '0');
        }
        return c;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Incorrect type for immutable var: ssa=int, code=char, for r9v0, types: [int] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.core.JsonToken _handleInvalidNumberStart(char r9, boolean r10) throws java.io.IOException {
        /*
            r8 = this;
            r0 = 73
            if (r9 != r0) goto L_0x0091
            int r9 = r8._inputPtr
            int r0 = r8._inputEnd
            if (r9 < r0) goto L_0x0015
            boolean r9 = r8._loadMore()
            if (r9 != 0) goto L_0x0015
            com.fasterxml.jackson.core.JsonToken r9 = com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT
            r8._reportInvalidEOFInValue(r9)
        L_0x0015:
            char[] r9 = r8._inputBuffer
            int r0 = r8._inputPtr
            int r1 = r0 + 1
            r8._inputPtr = r1
            char r9 = r9[r0]
            r0 = 78
            r1 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            r3 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            java.lang.String r5 = "': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            java.lang.String r6 = "Non-standard token '"
            r7 = 3
            if (r9 != r0) goto L_0x005d
            if (r10 == 0) goto L_0x0031
            java.lang.String r0 = "-INF"
            goto L_0x0033
        L_0x0031:
            java.lang.String r0 = "+INF"
        L_0x0033:
            r8._matchToken(r0, r7)
            com.fasterxml.jackson.core.JsonParser$Feature r7 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r7 = r8.isEnabled(r7)
            if (r7 == 0) goto L_0x0047
            if (r10 == 0) goto L_0x0041
            goto L_0x0042
        L_0x0041:
            r1 = r3
        L_0x0042:
            com.fasterxml.jackson.core.JsonToken r9 = r8.resetAsNaN(r0, r1)
            return r9
        L_0x0047:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r6)
            r10.append(r0)
            r10.append(r5)
            java.lang.String r10 = r10.toString()
            r8._reportError(r10)
            goto L_0x0091
        L_0x005d:
            r0 = 110(0x6e, float:1.54E-43)
            if (r9 != r0) goto L_0x0091
            if (r10 == 0) goto L_0x0066
            java.lang.String r0 = "-Infinity"
            goto L_0x0068
        L_0x0066:
            java.lang.String r0 = "+Infinity"
        L_0x0068:
            r8._matchToken(r0, r7)
            com.fasterxml.jackson.core.JsonParser$Feature r7 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r7 = r8.isEnabled(r7)
            if (r7 == 0) goto L_0x007c
            if (r10 == 0) goto L_0x0076
            goto L_0x0077
        L_0x0076:
            r1 = r3
        L_0x0077:
            com.fasterxml.jackson.core.JsonToken r9 = r8.resetAsNaN(r0, r1)
            return r9
        L_0x007c:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r6)
            r10.append(r0)
            r10.append(r5)
            java.lang.String r10 = r10.toString()
            r8._reportError(r10)
        L_0x0091:
            java.lang.String r10 = "expected digit (0-9) to follow minus sign, for valid numeric value"
            r8.reportUnexpectedNumberChar(r9, r10)
            r9 = 0
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser._handleInvalidNumberStart(int, boolean):com.fasterxml.jackson.core.JsonToken");
    }

    private final void _verifyRootSpace(int i) throws IOException {
        this._inputPtr++;
        if (i != 9) {
            if (i == 10) {
                this._currInputRow++;
                this._currInputRowStart = this._inputPtr;
            } else if (i == 13) {
                _skipCR();
            } else if (i != 32) {
                _reportMissingRootWS(i);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final String _parseName() throws IOException {
        int i = this._inputPtr;
        int i2 = this._hashSeed;
        int[] iArr = _icLatin1;
        while (true) {
            if (i >= this._inputEnd) {
                break;
            }
            char c = this._inputBuffer[i];
            if (c >= iArr.length || iArr[c] == 0) {
                i2 = (i2 * 33) + c;
                i++;
            } else if (c == '\"') {
                int i3 = this._inputPtr;
                this._inputPtr = i + 1;
                return this._symbols.findSymbol(this._inputBuffer, i3, i - i3, i2);
            }
        }
        int i4 = this._inputPtr;
        this._inputPtr = i;
        return _parseName2(i4, i2, 34);
    }

    private String _parseName2(int i, int i2, int i3) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
        char[] currentSegment = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(" in field name", JsonToken.FIELD_NAME);
            }
            char[] cArr = this._inputBuffer;
            int i4 = this._inputPtr;
            this._inputPtr = i4 + 1;
            char c = cArr[i4];
            if (c <= '\\') {
                if (c == '\\') {
                    c = _decodeEscaped();
                } else if (c <= i3) {
                    if (c == i3) {
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        TextBuffer textBuffer = this._textBuffer;
                        return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
                    } else if (c < ' ') {
                        _throwUnquotedSpace(c, PostalAddressParser.USER_ADDRESS_NAME_KEY);
                    }
                }
            }
            i2 = (i2 * 33) + c;
            int i5 = currentSegmentSize + 1;
            currentSegment[currentSegmentSize] = c;
            if (i5 >= currentSegment.length) {
                currentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            } else {
                currentSegmentSize = i5;
            }
        }
    }

    /* access modifiers changed from: protected */
    public String _handleOddName(int i) throws IOException {
        if (i == 39 && isEnabled(Feature.ALLOW_SINGLE_QUOTES)) {
            return _parseAposName();
        }
        if (!isEnabled(Feature.ALLOW_UNQUOTED_FIELD_NAMES)) {
            _reportUnexpectedChar(i, "was expecting double-quote to start field name");
        }
        int[] inputCodeLatin1JsNames = CharTypes.getInputCodeLatin1JsNames();
        int length = inputCodeLatin1JsNames.length;
        boolean z = i < length ? inputCodeLatin1JsNames[i] == 0 : Character.isJavaIdentifierPart((char) i);
        if (!z) {
            _reportUnexpectedChar(i, "was expecting either valid name character (for unquoted name) or double-quote (for quoted) to start field name");
        }
        int i2 = this._inputPtr;
        int i3 = this._hashSeed;
        int i4 = this._inputEnd;
        if (i2 < i4) {
            do {
                char c = this._inputBuffer[i2];
                if (c < length) {
                    if (inputCodeLatin1JsNames[c] != 0) {
                        int i5 = this._inputPtr - 1;
                        this._inputPtr = i2;
                        return this._symbols.findSymbol(this._inputBuffer, i5, i2 - i5, i3);
                    }
                } else if (!Character.isJavaIdentifierPart((char) c)) {
                    int i6 = this._inputPtr - 1;
                    this._inputPtr = i2;
                    return this._symbols.findSymbol(this._inputBuffer, i6, i2 - i6, i3);
                }
                i3 = (i3 * 33) + c;
                i2++;
            } while (i2 < i4);
        }
        int i7 = this._inputPtr - 1;
        this._inputPtr = i2;
        return _handleOddName2(i7, i3, inputCodeLatin1JsNames);
    }

    /* access modifiers changed from: protected */
    public String _parseAposName() throws IOException {
        int i = this._inputPtr;
        int i2 = this._hashSeed;
        int i3 = this._inputEnd;
        if (i < i3) {
            int[] iArr = _icLatin1;
            int length = iArr.length;
            do {
                char c = this._inputBuffer[i];
                if (c != '\'') {
                    if (c < length && iArr[c] != 0) {
                        break;
                    }
                    i2 = (i2 * 33) + c;
                    i++;
                } else {
                    int i4 = this._inputPtr;
                    this._inputPtr = i + 1;
                    return this._symbols.findSymbol(this._inputBuffer, i4, i - i4, i2);
                }
            } while (i < i3);
        }
        int i5 = this._inputPtr;
        this._inputPtr = i;
        return _parseName2(i5, i2, 39);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0017, code lost:
        if (r4 != 44) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0042, code lost:
        if (r3._parsingContext.inArray() == false) goto L_0x0096;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.fasterxml.jackson.core.JsonToken _handleOddValue(int r4) throws java.io.IOException {
        /*
            r3 = this;
            r0 = 39
            if (r4 == r0) goto L_0x0089
            r0 = 73
            r1 = 1
            if (r4 == r0) goto L_0x006f
            r0 = 78
            if (r4 == r0) goto L_0x0055
            r0 = 93
            if (r4 == r0) goto L_0x003c
            r0 = 43
            if (r4 == r0) goto L_0x001b
            r0 = 44
            if (r4 == r0) goto L_0x0045
            goto L_0x0096
        L_0x001b:
            int r4 = r3._inputPtr
            int r0 = r3._inputEnd
            if (r4 < r0) goto L_0x002c
            boolean r4 = r3._loadMore()
            if (r4 != 0) goto L_0x002c
            com.fasterxml.jackson.core.JsonToken r4 = com.fasterxml.jackson.core.JsonToken.VALUE_NUMBER_INT
            r3._reportInvalidEOFInValue(r4)
        L_0x002c:
            char[] r4 = r3._inputBuffer
            int r0 = r3._inputPtr
            int r1 = r0 + 1
            r3._inputPtr = r1
            char r4 = r4[r0]
            r0 = 0
            com.fasterxml.jackson.core.JsonToken r4 = r3._handleInvalidNumberStart(r4, r0)
            return r4
        L_0x003c:
            com.fasterxml.jackson.core.json.JsonReadContext r0 = r3._parsingContext
            boolean r0 = r0.inArray()
            if (r0 != 0) goto L_0x0045
            goto L_0x0096
        L_0x0045:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_MISSING_VALUES
            boolean r0 = r3.isEnabled(r0)
            if (r0 == 0) goto L_0x0096
            int r4 = r3._inputPtr
            int r4 = r4 - r1
            r3._inputPtr = r4
            com.fasterxml.jackson.core.JsonToken r4 = com.fasterxml.jackson.core.JsonToken.VALUE_NULL
            return r4
        L_0x0055:
            java.lang.String r0 = "NaN"
            r3._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r1 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r1 = r3.isEnabled(r1)
            if (r1 == 0) goto L_0x0069
            r1 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            com.fasterxml.jackson.core.JsonToken r4 = r3.resetAsNaN(r0, r1)
            return r4
        L_0x0069:
            java.lang.String r0 = "Non-standard token 'NaN': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r3._reportError(r0)
            goto L_0x0096
        L_0x006f:
            java.lang.String r0 = "Infinity"
            r3._matchToken(r0, r1)
            com.fasterxml.jackson.core.JsonParser$Feature r1 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS
            boolean r1 = r3.isEnabled(r1)
            if (r1 == 0) goto L_0x0083
            r1 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            com.fasterxml.jackson.core.JsonToken r4 = r3.resetAsNaN(r0, r1)
            return r4
        L_0x0083:
            java.lang.String r0 = "Non-standard token 'Infinity': enable JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS to allow"
            r3._reportError(r0)
            goto L_0x0096
        L_0x0089:
            com.fasterxml.jackson.core.JsonParser$Feature r0 = com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES
            boolean r0 = r3.isEnabled(r0)
            if (r0 == 0) goto L_0x0096
            com.fasterxml.jackson.core.JsonToken r4 = r3._handleApos()
            return r4
        L_0x0096:
            boolean r0 = java.lang.Character.isJavaIdentifierStart(r4)
            if (r0 == 0) goto L_0x00b3
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = ""
            r0.append(r1)
            char r1 = (char) r4
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "('true', 'false' or 'null')"
            r3._reportInvalidToken(r0, r1)
        L_0x00b3:
            java.lang.String r0 = "expected a valid value (number, String, array, object, 'true', 'false' or 'null')"
            r3._reportUnexpectedChar(r4, r0)
            r4 = 0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fasterxml.jackson.core.json.ReaderBasedJsonParser._handleOddValue(int):com.fasterxml.jackson.core.JsonToken");
    }

    /* access modifiers changed from: protected */
    public JsonToken _handleApos() throws IOException {
        char[] emptyAndGetCurrentSegment = this._textBuffer.emptyAndGetCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c <= '\\') {
                if (c == '\\') {
                    c = _decodeEscaped();
                } else if (c <= '\'') {
                    if (c == '\'') {
                        this._textBuffer.setCurrentLength(currentSegmentSize);
                        return JsonToken.VALUE_STRING;
                    } else if (c < ' ') {
                        _throwUnquotedSpace(c, "string value");
                    }
                }
            }
            if (currentSegmentSize >= emptyAndGetCurrentSegment.length) {
                emptyAndGetCurrentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            int i2 = currentSegmentSize + 1;
            emptyAndGetCurrentSegment[currentSegmentSize] = c;
            currentSegmentSize = i2;
        }
    }

    private String _handleOddName2(int i, int i2, int[] iArr) throws IOException {
        this._textBuffer.resetWithShared(this._inputBuffer, i, this._inputPtr - i);
        char[] currentSegment = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        int length = iArr.length;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                break;
            }
            char c = this._inputBuffer[this._inputPtr];
            if (c >= length) {
                if (!Character.isJavaIdentifierPart(c)) {
                    break;
                }
            } else if (iArr[c] != 0) {
                break;
            }
            this._inputPtr++;
            i2 = (i2 * 33) + c;
            int i3 = currentSegmentSize + 1;
            currentSegment[currentSegmentSize] = c;
            if (i3 >= currentSegment.length) {
                currentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            } else {
                currentSegmentSize = i3;
            }
        }
        this._textBuffer.setCurrentLength(currentSegmentSize);
        TextBuffer textBuffer = this._textBuffer;
        return this._symbols.findSymbol(textBuffer.getTextBuffer(), textBuffer.getTextOffset(), textBuffer.size(), i2);
    }

    /* access modifiers changed from: protected */
    public final void _finishString() throws IOException {
        int i = this._inputPtr;
        int i2 = this._inputEnd;
        if (i < i2) {
            int[] iArr = _icLatin1;
            int length = iArr.length;
            while (true) {
                char c = this._inputBuffer[i];
                if (c >= length || iArr[c] == 0) {
                    i++;
                    if (i >= i2) {
                        break;
                    }
                } else if (c == '\"') {
                    this._textBuffer.resetWithShared(this._inputBuffer, this._inputPtr, i - this._inputPtr);
                    this._inputPtr = i + 1;
                    return;
                }
            }
        }
        this._textBuffer.resetWithCopy(this._inputBuffer, this._inputPtr, i - this._inputPtr);
        this._inputPtr = i;
        _finishString2();
    }

    /* access modifiers changed from: protected */
    public void _finishString2() throws IOException {
        char[] currentSegment = this._textBuffer.getCurrentSegment();
        int currentSegmentSize = this._textBuffer.getCurrentSegmentSize();
        int[] iArr = _icLatin1;
        int length = iArr.length;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c < length && iArr[c] != 0) {
                if (c == '\"') {
                    this._textBuffer.setCurrentLength(currentSegmentSize);
                    return;
                } else if (c == '\\') {
                    c = _decodeEscaped();
                } else if (c < ' ') {
                    _throwUnquotedSpace(c, "string value");
                }
            }
            if (currentSegmentSize >= currentSegment.length) {
                currentSegment = this._textBuffer.finishCurrentSegment();
                currentSegmentSize = 0;
            }
            int i2 = currentSegmentSize + 1;
            currentSegment[currentSegmentSize] = c;
            currentSegmentSize = i2;
        }
    }

    /* access modifiers changed from: protected */
    public final void _skipString() throws IOException {
        this._tokenIncomplete = false;
        int i = this._inputPtr;
        int i2 = this._inputEnd;
        char[] cArr = this._inputBuffer;
        while (true) {
            if (i >= i2) {
                this._inputPtr = i;
                if (!_loadMore()) {
                    _reportInvalidEOF(": was expecting closing quote for a string value", JsonToken.VALUE_STRING);
                }
                i = this._inputPtr;
                i2 = this._inputEnd;
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c <= '\\') {
                if (c == '\\') {
                    this._inputPtr = i3;
                    _decodeEscaped();
                    i = this._inputPtr;
                    i2 = this._inputEnd;
                } else if (c <= '\"') {
                    if (c == '\"') {
                        this._inputPtr = i3;
                        return;
                    } else if (c < ' ') {
                        this._inputPtr = i3;
                        _throwUnquotedSpace(c, "string value");
                    }
                }
            }
            i = i3;
        }
    }

    /* access modifiers changed from: protected */
    public final void _skipCR() throws IOException {
        if ((this._inputPtr < this._inputEnd || _loadMore()) && this._inputBuffer[this._inputPtr] == 10) {
            this._inputPtr++;
        }
        this._currInputRow++;
        this._currInputRowStart = this._inputPtr;
    }

    private final int _skipColon() throws IOException {
        if (this._inputPtr + 4 >= this._inputEnd) {
            return _skipColon2(false);
        }
        char c = this._inputBuffer[this._inputPtr];
        if (c == ':') {
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr + 1;
            this._inputPtr = i;
            char c2 = cArr[i];
            if (c2 <= ' ') {
                if (c2 == ' ' || c2 == 9) {
                    char[] cArr2 = this._inputBuffer;
                    int i2 = this._inputPtr + 1;
                    this._inputPtr = i2;
                    char c3 = cArr2[i2];
                    if (c3 > ' ') {
                        if (c3 == '/' || c3 == '#') {
                            return _skipColon2(true);
                        }
                        this._inputPtr++;
                        return c3;
                    }
                }
                return _skipColon2(true);
            } else if (c2 == '/' || c2 == '#') {
                return _skipColon2(true);
            } else {
                this._inputPtr++;
                return c2;
            }
        } else {
            if (c == ' ' || c == 9) {
                char[] cArr3 = this._inputBuffer;
                int i3 = this._inputPtr + 1;
                this._inputPtr = i3;
                c = cArr3[i3];
            }
            if (c != ':') {
                return _skipColon2(false);
            }
            char[] cArr4 = this._inputBuffer;
            int i4 = this._inputPtr + 1;
            this._inputPtr = i4;
            char c4 = cArr4[i4];
            if (c4 <= ' ') {
                if (c4 == ' ' || c4 == 9) {
                    char[] cArr5 = this._inputBuffer;
                    int i5 = this._inputPtr + 1;
                    this._inputPtr = i5;
                    char c5 = cArr5[i5];
                    if (c5 > ' ') {
                        if (c5 == '/' || c5 == '#') {
                            return _skipColon2(true);
                        }
                        this._inputPtr++;
                        return c5;
                    }
                }
                return _skipColon2(true);
            } else if (c4 == '/' || c4 == '#') {
                return _skipColon2(true);
            } else {
                this._inputPtr++;
                return c4;
            }
        }
    }

    private final int _skipColon2(boolean z) throws IOException {
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char c = cArr[i];
                if (c > ' ') {
                    if (c == '/') {
                        _skipComment();
                    } else if (c != '#' || !_skipYAMLComment()) {
                        if (z) {
                            return c;
                        }
                        if (c != ':') {
                            _reportUnexpectedChar(c, "was expecting a colon to separate field name and value");
                        }
                        z = true;
                    }
                } else if (c < ' ') {
                    if (c == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (c == 13) {
                        _skipCR();
                    } else if (c != 9) {
                        _throwInvalidSpace(c);
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(" within/between ");
                sb.append(this._parsingContext.typeDesc());
                sb.append(" entries");
                _reportInvalidEOF(sb.toString(), null);
                return -1;
            }
        }
    }

    private final int _skipColonFast(int i) throws IOException {
        int i2;
        char[] cArr = this._inputBuffer;
        int i3 = i + 1;
        char c = cArr[i];
        if (c == ':') {
            int i4 = i3 + 1;
            char c2 = cArr[i3];
            if (c2 > ' ') {
                if (!(c2 == '/' || c2 == '#')) {
                    this._inputPtr = i4;
                    return c2;
                }
            } else if (c2 == ' ' || c2 == 9) {
                int i5 = i4 + 1;
                char c3 = this._inputBuffer[i4];
                if (c3 <= ' ' || c3 == '/' || c3 == '#') {
                    i4 = i5;
                } else {
                    this._inputPtr = i5;
                    return c3;
                }
            }
            this._inputPtr = i4 - 1;
            return _skipColon2(true);
        }
        if (c == ' ' || c == 9) {
            i2 = i3 + 1;
            c = this._inputBuffer[i3];
        } else {
            i2 = i3;
        }
        boolean z = c == ':';
        if (z) {
            int i6 = i2 + 1;
            char c4 = this._inputBuffer[i2];
            if (c4 > ' ') {
                if (!(c4 == '/' || c4 == '#')) {
                    this._inputPtr = i6;
                    return c4;
                }
            } else if (c4 == ' ' || c4 == 9) {
                int i7 = i6 + 1;
                char c5 = this._inputBuffer[i6];
                if (c5 <= ' ' || c5 == '/' || c5 == '#') {
                    i2 = i7;
                } else {
                    this._inputPtr = i7;
                    return c5;
                }
            }
            i2 = i6;
        }
        this._inputPtr = i2 - 1;
        return _skipColon2(z);
    }

    private final int _skipComma(int i) throws IOException {
        if (i != 44) {
            StringBuilder sb = new StringBuilder();
            sb.append("was expecting comma to separate ");
            sb.append(this._parsingContext.typeDesc());
            sb.append(" entries");
            _reportUnexpectedChar(i, sb.toString());
        }
        while (this._inputPtr < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            int i2 = this._inputPtr;
            this._inputPtr = i2 + 1;
            char c = cArr[i2];
            if (c > ' ') {
                if (c != '/' && c != '#') {
                    return c;
                }
                this._inputPtr--;
                return _skipAfterComma2();
            } else if (c < ' ') {
                if (c == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (c == 13) {
                    _skipCR();
                } else if (c != 9) {
                    _throwInvalidSpace(c);
                }
            }
        }
        return _skipAfterComma2();
    }

    private final int _skipAfterComma2() throws IOException {
        char c;
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                c = cArr[i];
                if (c > ' ') {
                    if (c == '/') {
                        _skipComment();
                    } else if (c != '#' || !_skipYAMLComment()) {
                        return c;
                    }
                } else if (c < ' ') {
                    if (c == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (c == 13) {
                        _skipCR();
                    } else if (c != 9) {
                        _throwInvalidSpace(c);
                    }
                }
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Unexpected end-of-input within/between ");
                sb.append(this._parsingContext.typeDesc());
                sb.append(" entries");
                throw _constructError(sb.toString());
            }
        }
        return c;
    }

    private final int _skipWSOrEnd() throws IOException {
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            return _eofAsNextChar();
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        if (c <= ' ') {
            if (c != ' ') {
                if (c == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (c == 13) {
                    _skipCR();
                } else if (c != 9) {
                    _throwInvalidSpace(c);
                }
            }
            while (this._inputPtr < this._inputEnd) {
                char[] cArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                char c2 = cArr2[i2];
                if (c2 > ' ') {
                    if (c2 != '/' && c2 != '#') {
                        return c2;
                    }
                    this._inputPtr--;
                    return _skipWSOrEnd2();
                } else if (c2 != ' ') {
                    if (c2 == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (c2 == 13) {
                        _skipCR();
                    } else if (c2 != 9) {
                        _throwInvalidSpace(c2);
                    }
                }
            }
            return _skipWSOrEnd2();
        } else if (c != '/' && c != '#') {
            return c;
        } else {
            this._inputPtr--;
            return _skipWSOrEnd2();
        }
    }

    private int _skipWSOrEnd2() throws IOException {
        char c;
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                return _eofAsNextChar();
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            c = cArr[i];
            if (c > ' ') {
                if (c == '/') {
                    _skipComment();
                } else if (c != '#' || !_skipYAMLComment()) {
                    return c;
                }
            } else if (c != ' ') {
                if (c == 10) {
                    this._currInputRow++;
                    this._currInputRowStart = this._inputPtr;
                } else if (c == 13) {
                    _skipCR();
                } else if (c != 9) {
                    _throwInvalidSpace(c);
                }
            }
        }
        return c;
    }

    private void _skipComment() throws IOException {
        if (!isEnabled(Feature.ALLOW_COMMENTS)) {
            _reportUnexpectedChar(47, "maybe a (non-standard) comment? (not recognized as one since Feature 'ALLOW_COMMENTS' not enabled for parser)");
        }
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(" in a comment", null);
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        if (c == '/') {
            _skipLine();
        } else if (c == '*') {
            _skipCComment();
        } else {
            _reportUnexpectedChar(c, "was expecting either '*' or '/' for a comment");
        }
    }

    private void _skipCComment() throws IOException {
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                break;
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c <= '*') {
                if (c == '*') {
                    if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                        break;
                    } else if (this._inputBuffer[this._inputPtr] == '/') {
                        this._inputPtr++;
                        return;
                    }
                } else if (c < ' ') {
                    if (c == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                    } else if (c == 13) {
                        _skipCR();
                    } else if (c != 9) {
                        _throwInvalidSpace(c);
                    }
                }
            }
        }
        _reportInvalidEOF(" in a comment", null);
    }

    private boolean _skipYAMLComment() throws IOException {
        if (!isEnabled(Feature.ALLOW_YAML_COMMENTS)) {
            return false;
        }
        _skipLine();
        return true;
    }

    private void _skipLine() throws IOException {
        while (true) {
            if (this._inputPtr < this._inputEnd || _loadMore()) {
                char[] cArr = this._inputBuffer;
                int i = this._inputPtr;
                this._inputPtr = i + 1;
                char c = cArr[i];
                if (c < ' ') {
                    if (c == 10) {
                        this._currInputRow++;
                        this._currInputRowStart = this._inputPtr;
                        return;
                    } else if (c == 13) {
                        _skipCR();
                        return;
                    } else if (c != 9) {
                        _throwInvalidSpace(c);
                    }
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public char _decodeEscaped() throws IOException {
        String str = " in character escape sequence";
        if (this._inputPtr >= this._inputEnd && !_loadMore()) {
            _reportInvalidEOF(str, JsonToken.VALUE_STRING);
        }
        char[] cArr = this._inputBuffer;
        int i = this._inputPtr;
        this._inputPtr = i + 1;
        char c = cArr[i];
        if (!(c == '\"' || c == '/' || c == '\\')) {
            if (c == 'b') {
                c = 8;
            } else if (c == 'f') {
                return 12;
            } else {
                if (c == 'n') {
                    return 10;
                }
                if (c == 'r') {
                    return 13;
                }
                if (c == 't') {
                    return 9;
                }
                if (c != 'u') {
                    return _handleUnrecognizedCharacterEscape(c);
                }
                int i2 = 0;
                for (int i3 = 0; i3 < 4; i3++) {
                    if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                        _reportInvalidEOF(str, JsonToken.VALUE_STRING);
                    }
                    char[] cArr2 = this._inputBuffer;
                    int i4 = this._inputPtr;
                    this._inputPtr = i4 + 1;
                    char c2 = cArr2[i4];
                    int charToHex = CharTypes.charToHex(c2);
                    if (charToHex < 0) {
                        _reportUnexpectedChar(c2, "expected a hex-digit for character escape sequence");
                    }
                    i2 = (i2 << 4) | charToHex;
                }
                return (char) i2;
            }
        }
        return c;
    }

    private final void _matchTrue() throws IOException {
        int i = this._inputPtr;
        if (i + 3 < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            if (cArr[i] == 'r') {
                int i2 = i + 1;
                if (cArr[i2] == 'u') {
                    int i3 = i2 + 1;
                    if (cArr[i3] == 'e') {
                        int i4 = i3 + 1;
                        char c = cArr[i4];
                        if (c < '0' || c == ']' || c == '}') {
                            this._inputPtr = i4;
                            return;
                        }
                    }
                }
            }
        }
        _matchToken("true", 1);
    }

    private final void _matchFalse() throws IOException {
        int i = this._inputPtr;
        if (i + 4 < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            if (cArr[i] == 'a') {
                int i2 = i + 1;
                if (cArr[i2] == 'l') {
                    int i3 = i2 + 1;
                    if (cArr[i3] == 's') {
                        int i4 = i3 + 1;
                        if (cArr[i4] == 'e') {
                            int i5 = i4 + 1;
                            char c = cArr[i5];
                            if (c < '0' || c == ']' || c == '}') {
                                this._inputPtr = i5;
                                return;
                            }
                        }
                    }
                }
            }
        }
        _matchToken("false", 1);
    }

    private final void _matchNull() throws IOException {
        int i = this._inputPtr;
        if (i + 3 < this._inputEnd) {
            char[] cArr = this._inputBuffer;
            if (cArr[i] == 'u') {
                int i2 = i + 1;
                if (cArr[i2] == 'l') {
                    int i3 = i2 + 1;
                    if (cArr[i3] == 'l') {
                        int i4 = i3 + 1;
                        char c = cArr[i4];
                        if (c < '0' || c == ']' || c == '}') {
                            this._inputPtr = i4;
                            return;
                        }
                    }
                }
            }
        }
        _matchToken("null", 1);
    }

    /* access modifiers changed from: protected */
    public final void _matchToken(String str, int i) throws IOException {
        int length = str.length();
        if (this._inputPtr + length >= this._inputEnd) {
            _matchToken2(str, i);
            return;
        }
        do {
            if (this._inputBuffer[this._inputPtr] != str.charAt(i)) {
                _reportInvalidToken(str.substring(0, i));
            }
            this._inputPtr++;
            i++;
        } while (i < length);
        char c = this._inputBuffer[this._inputPtr];
        if (!(c < '0' || c == ']' || c == '}')) {
            _checkMatchEnd(str, i, c);
        }
    }

    private final void _matchToken2(String str, int i) throws IOException {
        int length = str.length();
        do {
            if ((this._inputPtr >= this._inputEnd && !_loadMore()) || this._inputBuffer[this._inputPtr] != str.charAt(i)) {
                _reportInvalidToken(str.substring(0, i));
            }
            this._inputPtr++;
            i++;
        } while (i < length);
        if (this._inputPtr < this._inputEnd || _loadMore()) {
            char c = this._inputBuffer[this._inputPtr];
            if (!(c < '0' || c == ']' || c == '}')) {
                _checkMatchEnd(str, i, c);
            }
        }
    }

    private final void _checkMatchEnd(String str, int i, int i2) throws IOException {
        if (Character.isJavaIdentifierPart((char) i2)) {
            _reportInvalidToken(str.substring(0, i));
        }
    }

    /* access modifiers changed from: protected */
    public byte[] _decodeBase64(Base64Variant base64Variant) throws IOException {
        ByteArrayBuilder _getByteArrayBuilder = _getByteArrayBuilder();
        while (true) {
            if (this._inputPtr >= this._inputEnd) {
                _loadMoreGuaranteed();
            }
            char[] cArr = this._inputBuffer;
            int i = this._inputPtr;
            this._inputPtr = i + 1;
            char c = cArr[i];
            if (c > ' ') {
                int decodeBase64Char = base64Variant.decodeBase64Char(c);
                if (decodeBase64Char < 0) {
                    if (c == '\"') {
                        return _getByteArrayBuilder.toByteArray();
                    }
                    decodeBase64Char = _decodeBase64Escape(base64Variant, c, 0);
                    if (decodeBase64Char < 0) {
                        continue;
                    }
                }
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr2 = this._inputBuffer;
                int i2 = this._inputPtr;
                this._inputPtr = i2 + 1;
                char c2 = cArr2[i2];
                int decodeBase64Char2 = base64Variant.decodeBase64Char(c2);
                if (decodeBase64Char2 < 0) {
                    decodeBase64Char2 = _decodeBase64Escape(base64Variant, c2, 1);
                }
                int i3 = (decodeBase64Char << 6) | decodeBase64Char2;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr3 = this._inputBuffer;
                int i4 = this._inputPtr;
                this._inputPtr = i4 + 1;
                char c3 = cArr3[i4];
                int decodeBase64Char3 = base64Variant.decodeBase64Char(c3);
                if (decodeBase64Char3 < 0) {
                    if (decodeBase64Char3 != -2) {
                        if (c3 == '\"') {
                            _getByteArrayBuilder.append(i3 >> 4);
                            if (base64Variant.usesPadding()) {
                                this._inputPtr--;
                                _handleBase64MissingPadding(base64Variant);
                            }
                            return _getByteArrayBuilder.toByteArray();
                        }
                        decodeBase64Char3 = _decodeBase64Escape(base64Variant, c3, 2);
                    }
                    if (decodeBase64Char3 == -2) {
                        if (this._inputPtr >= this._inputEnd) {
                            _loadMoreGuaranteed();
                        }
                        char[] cArr4 = this._inputBuffer;
                        int i5 = this._inputPtr;
                        this._inputPtr = i5 + 1;
                        char c4 = cArr4[i5];
                        if (base64Variant.usesPaddingChar(c4) || _decodeBase64Escape(base64Variant, c4, 3) == -2) {
                            _getByteArrayBuilder.append(i3 >> 4);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("expected padding character '");
                            sb.append(base64Variant.getPaddingChar());
                            sb.append("'");
                            throw reportInvalidBase64Char(base64Variant, c4, 3, sb.toString());
                        }
                    }
                }
                int i6 = (i3 << 6) | decodeBase64Char3;
                if (this._inputPtr >= this._inputEnd) {
                    _loadMoreGuaranteed();
                }
                char[] cArr5 = this._inputBuffer;
                int i7 = this._inputPtr;
                this._inputPtr = i7 + 1;
                char c5 = cArr5[i7];
                int decodeBase64Char4 = base64Variant.decodeBase64Char(c5);
                if (decodeBase64Char4 < 0) {
                    if (decodeBase64Char4 != -2) {
                        if (c5 == '\"') {
                            _getByteArrayBuilder.appendTwoBytes(i6 >> 2);
                            if (base64Variant.usesPadding()) {
                                this._inputPtr--;
                                _handleBase64MissingPadding(base64Variant);
                            }
                            return _getByteArrayBuilder.toByteArray();
                        }
                        decodeBase64Char4 = _decodeBase64Escape(base64Variant, c5, 3);
                    }
                    if (decodeBase64Char4 == -2) {
                        _getByteArrayBuilder.appendTwoBytes(i6 >> 2);
                    }
                }
                _getByteArrayBuilder.appendThreeBytes((i6 << 6) | decodeBase64Char4);
            }
        }
    }

    public JsonLocation getTokenLocation() {
        if (this._currToken == JsonToken.FIELD_NAME) {
            JsonLocation jsonLocation = new JsonLocation(_getSourceReference(), -1, this._currInputProcessed + (this._nameStartOffset - 1), this._nameStartRow, this._nameStartCol);
            return jsonLocation;
        }
        JsonLocation jsonLocation2 = new JsonLocation(_getSourceReference(), -1, this._tokenInputTotal - 1, this._tokenInputRow, this._tokenInputCol);
        return jsonLocation2;
    }

    public JsonLocation getCurrentLocation() {
        int i = (this._inputPtr - this._currInputRowStart) + 1;
        JsonLocation jsonLocation = new JsonLocation(_getSourceReference(), -1, ((long) this._inputPtr) + this._currInputProcessed, this._currInputRow, i);
        return jsonLocation;
    }

    private final void _updateLocation() {
        int i = this._inputPtr;
        this._tokenInputTotal = this._currInputProcessed + ((long) i);
        this._tokenInputRow = this._currInputRow;
        this._tokenInputCol = i - this._currInputRowStart;
    }

    private final void _updateNameLocation() {
        int i = this._inputPtr;
        this._nameStartOffset = (long) i;
        this._nameStartRow = this._currInputRow;
        this._nameStartCol = i - this._currInputRowStart;
    }

    /* access modifiers changed from: protected */
    public void _reportInvalidToken(String str) throws IOException {
        _reportInvalidToken(str, "'null', 'true', 'false' or NaN");
    }

    /* access modifiers changed from: protected */
    public void _reportInvalidToken(String str, String str2) throws IOException {
        StringBuilder sb = new StringBuilder(str);
        while (true) {
            if (this._inputPtr >= this._inputEnd && !_loadMore()) {
                break;
            }
            char c = this._inputBuffer[this._inputPtr];
            if (Character.isJavaIdentifierPart(c)) {
                this._inputPtr++;
                sb.append(c);
                if (sb.length() >= 256) {
                    sb.append("...");
                    break;
                }
            } else {
                break;
            }
        }
        _reportError("Unrecognized token '%s': was expecting %s", sb, str2);
    }

    private void _closeScope(int i) throws JsonParseException {
        if (i == 93) {
            _updateLocation();
            if (!this._parsingContext.inArray()) {
                _reportMismatchedEndMarker(i, '}');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_ARRAY;
        }
        if (i == 125) {
            _updateLocation();
            if (!this._parsingContext.inObject()) {
                _reportMismatchedEndMarker(i, ']');
            }
            this._parsingContext = this._parsingContext.clearAndGetParent();
            this._currToken = JsonToken.END_OBJECT;
        }
    }
}
