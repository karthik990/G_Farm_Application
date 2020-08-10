package com.google.android.gms.internal.firebase_auth;

import org.apache.http.message.TokenParser;

final class zzkg {
    static String zzd(zzgf zzgf) {
        zzkf zzkf = new zzkf(zzgf);
        StringBuilder sb = new StringBuilder(zzkf.size());
        for (int i = 0; i < zzkf.size(); i++) {
            byte zzp = zzkf.zzp(i);
            if (zzp == 34) {
                sb.append("\\\"");
            } else if (zzp == 39) {
                sb.append("\\'");
            } else if (zzp != 92) {
                switch (zzp) {
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
                        if (zzp >= 32 && zzp <= 126) {
                            sb.append((char) zzp);
                            break;
                        } else {
                            sb.append(TokenParser.ESCAPE);
                            sb.append((char) (((zzp >>> 6) & 3) + 48));
                            sb.append((char) (((zzp >>> 3) & 7) + 48));
                            sb.append((char) ((zzp & 7) + 48));
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
