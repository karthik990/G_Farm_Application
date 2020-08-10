package com.google.android.gms.internal.firebase_remote_config;

import org.apache.http.message.TokenParser;

final class zzjx {
    static String zzg(zzfw zzfw) {
        zzjy zzjy = new zzjy(zzfw);
        StringBuilder sb = new StringBuilder(zzjy.size());
        for (int i = 0; i < zzjy.size(); i++) {
            byte zzv = zzjy.zzv(i);
            if (zzv == 34) {
                sb.append("\\\"");
            } else if (zzv == 39) {
                sb.append("\\'");
            } else if (zzv != 92) {
                switch (zzv) {
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
                        if (zzv >= 32 && zzv <= 126) {
                            sb.append((char) zzv);
                            break;
                        } else {
                            sb.append(TokenParser.ESCAPE);
                            sb.append((char) (((zzv >>> 6) & 3) + 48));
                            sb.append((char) (((zzv >>> 3) & 7) + 48));
                            sb.append((char) ((zzv & 7) + 48));
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
