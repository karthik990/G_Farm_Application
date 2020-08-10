package org.jdom2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.jdom2.Content.CType;
import org.jdom2.output.XMLOutputter;

public class ProcessingInstruction extends Content {
    private static final long serialVersionUID = 200;
    protected transient Map<String, String> mapData;
    protected String rawData;
    protected String target;

    protected ProcessingInstruction() {
        super(CType.ProcessingInstruction);
        this.mapData = null;
    }

    public ProcessingInstruction(String str) {
        this(str, "");
    }

    public ProcessingInstruction(String str, Map<String, String> map) {
        super(CType.ProcessingInstruction);
        this.mapData = null;
        setTarget(str);
        setData(map);
    }

    public ProcessingInstruction(String str, String str2) {
        super(CType.ProcessingInstruction);
        this.mapData = null;
        setTarget(str);
        setData(str2);
    }

    public ProcessingInstruction setTarget(String str) {
        String checkProcessingInstructionTarget = Verifier.checkProcessingInstructionTarget(str);
        if (checkProcessingInstructionTarget == null) {
            this.target = str;
            return this;
        }
        throw new IllegalTargetException(str, checkProcessingInstructionTarget);
    }

    public String getValue() {
        return this.rawData;
    }

    public String getTarget() {
        return this.target;
    }

    public String getData() {
        return this.rawData;
    }

    public List<String> getPseudoAttributeNames() {
        return new ArrayList(this.mapData.keySet());
    }

    public ProcessingInstruction setData(String str) {
        String checkProcessingInstructionData = Verifier.checkProcessingInstructionData(str);
        if (checkProcessingInstructionData == null) {
            this.rawData = str;
            this.mapData = parseData(str);
            return this;
        }
        throw new IllegalDataException(str, checkProcessingInstructionData);
    }

    public ProcessingInstruction setData(Map<String, String> map) {
        String processingInstruction = toString(map);
        String checkProcessingInstructionData = Verifier.checkProcessingInstructionData(processingInstruction);
        if (checkProcessingInstructionData == null) {
            this.rawData = processingInstruction;
            this.mapData = new LinkedHashMap(map);
            return this;
        }
        throw new IllegalDataException(processingInstruction, checkProcessingInstructionData);
    }

    public String getPseudoAttributeValue(String str) {
        return (String) this.mapData.get(str);
    }

    public ProcessingInstruction setPseudoAttribute(String str, String str2) {
        String checkProcessingInstructionData = Verifier.checkProcessingInstructionData(str);
        if (checkProcessingInstructionData == null) {
            String checkProcessingInstructionData2 = Verifier.checkProcessingInstructionData(str2);
            if (checkProcessingInstructionData2 == null) {
                this.mapData.put(str, str2);
                this.rawData = toString(this.mapData);
                return this;
            }
            throw new IllegalDataException(str2, checkProcessingInstructionData2);
        }
        throw new IllegalDataException(str, checkProcessingInstructionData);
    }

    public boolean removePseudoAttribute(String str) {
        if (this.mapData.remove(str) == null) {
            return false;
        }
        this.rawData = toString(this.mapData);
        return true;
    }

    private static final String toString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            sb.append((String) entry.getKey());
            sb.append("=\"");
            sb.append((String) entry.getValue());
            sb.append("\" ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private Map<String, String> parseData(String str) {
        String str2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        String trim = str.trim();
        while (true) {
            String str3 = "";
            if (trim.trim().equals(str3)) {
                return linkedHashMap;
            }
            char charAt = trim.charAt(0);
            int i = 1;
            int i2 = 0;
            while (true) {
                if (i >= trim.length()) {
                    str2 = str3;
                    break;
                }
                char charAt2 = trim.charAt(i);
                if (charAt2 == '=') {
                    str3 = trim.substring(i2, i).trim();
                    int[] extractQuotedString = extractQuotedString(trim.substring(i + 1));
                    if (extractQuotedString == null) {
                        return Collections.emptyMap();
                    }
                    str2 = trim.substring(extractQuotedString[0] + i + 1, extractQuotedString[1] + i + 1);
                    i += extractQuotedString[1] + 1;
                } else {
                    if (Character.isWhitespace(charAt) && !Character.isWhitespace(charAt2)) {
                        i2 = i;
                    }
                    i++;
                    charAt = charAt2;
                }
            }
            trim = trim.substring(i);
            if (str3.length() > 0) {
                linkedHashMap.put(str3, str2);
            }
        }
    }

    private static int[] extractQuotedString(String str) {
        boolean z = false;
        char c = '\"';
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (charAt == '\"' || charAt == '\'') {
                if (!z) {
                    i = i2 + 1;
                    c = charAt;
                    z = true;
                } else if (c == charAt) {
                    return new int[]{i, i2};
                }
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ProcessingInstruction: ");
        sb.append(new XMLOutputter().outputString(this));
        sb.append("]");
        return sb.toString();
    }

    public ProcessingInstruction clone() {
        ProcessingInstruction processingInstruction = (ProcessingInstruction) super.clone();
        processingInstruction.mapData = parseData(this.rawData);
        return processingInstruction;
    }

    public ProcessingInstruction detach() {
        return (ProcessingInstruction) super.detach();
    }

    /* access modifiers changed from: protected */
    public ProcessingInstruction setParent(Parent parent) {
        return (ProcessingInstruction) super.setParent(parent);
    }
}
