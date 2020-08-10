package com.google.android.gms.internal.gtm;

import org.apache.http.message.TokenParser;

final class zztn {
    static String zzd(zzps zzps) {
        zzto zzto = new zzto(zzps);
        StringBuilder sb = new StringBuilder(zzto.size());
        for (int i = 0; i < zzto.size(); i++) {
            byte zzak = zzto.zzak(i);
            if (zzak == 34) {
                sb.append("\\\"");
            } else if (zzak == 39) {
                sb.append("\\'");
            } else if (zzak != 92) {
                switch (zzak) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (zzak >= 32 && zzak <= 126) {
                            sb.append((char) zzak);
                            break;
                        } else {
                            sb.append(TokenParser.ESCAPE);
                            sb.append((char) (((zzak >>> 6) & 3) + 48));
                            sb.append((char) (((zzak >>> 3) & 7) + 48));
                            sb.append((char) ((zzak & 7) + 48));
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
