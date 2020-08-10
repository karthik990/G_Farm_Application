package com.mobiroller.util;

import android.content.Context;
import android.util.TypedValue;
import com.mobiroller.MobiRollerApplication;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Attribute;
import org.jdom2.Element;

public class RssUtil {
    public static String rssGetAuthor() {
        return "";
    }

    public static String rssGetImageUrl(SyndEntry syndEntry) {
        String str = "";
        String url = (syndEntry.getEnclosures() == null || syndEntry.getEnclosures().size() == 0 || (!((SyndEnclosure) syndEntry.getEnclosures().get(0)).getType().startsWith(TtmlNode.TAG_IMAGE) && !((SyndEnclosure) syndEntry.getEnclosures().get(0)).getType().startsWith("img"))) ? str : ((SyndEnclosure) syndEntry.getEnclosures().get(0)).getUrl();
        if (!(!url.equals(str) || syndEntry.getForeignMarkup() == null || syndEntry.getForeignMarkup().size() == 0)) {
            String str2 = url;
            for (int i = 0; i < syndEntry.getForeignMarkup().size(); i++) {
                if (!(((Element) syndEntry.getForeignMarkup().get(i)).getAttributes() == null || ((Element) syndEntry.getForeignMarkup().get(i)).getAttributes().size() == 0)) {
                    String str3 = str2;
                    for (int i2 = 0; i2 < ((Element) syndEntry.getForeignMarkup().get(i)).getAttributes().size(); i2++) {
                        if (((Attribute) ((Element) syndEntry.getForeignMarkup().get(i)).getAttributes().get(i2)).getName().equalsIgnoreCase("url")) {
                            str3 = ((Attribute) ((Element) syndEntry.getForeignMarkup().get(i)).getAttributes().get(i2)).getValue();
                        }
                    }
                    str2 = str3;
                }
            }
            url = str2;
        }
        String str4 = "src='(.*?)'";
        String str5 = "src=\"(.*?)\"";
        String str6 = "<img.+?>";
        if (!(!url.equals(str) || syndEntry.getContents() == null || syndEntry.getContents().size() == 0)) {
            Pattern compile = Pattern.compile(str5);
            Pattern compile2 = Pattern.compile(str4);
            Matcher matcher = compile.matcher(((SyndContent) syndEntry.getContents().get(0)).getValue());
            Matcher matcher2 = compile2.matcher(((SyndContent) syndEntry.getContents().get(0)).getValue());
            if (matcher.find() && matcher.groupCount() == 1) {
                url = matcher.group(1);
                ((SyndContent) syndEntry.getContents().get(0)).setValue(((SyndContent) syndEntry.getContents().get(0)).getValue().replaceFirst(str6, str));
            } else if (matcher2.find() && matcher2.groupCount() == 1) {
                url = matcher2.group(1);
                ((SyndContent) syndEntry.getContents().get(0)).setValue(((SyndContent) syndEntry.getContents().get(0)).getValue().replaceFirst(str6, str));
            }
        }
        if (!(!url.equals(str) || syndEntry.getDescription() == null || syndEntry.getDescription().getValue() == null)) {
            Pattern compile3 = Pattern.compile(str5);
            Pattern compile4 = Pattern.compile(str4);
            Matcher matcher3 = compile3.matcher(syndEntry.getDescription().getValue());
            Matcher matcher4 = compile4.matcher(syndEntry.getDescription().getValue());
            if (matcher3.find() && matcher3.groupCount() == 1) {
                url = matcher3.group(1);
                syndEntry.getDescription().setValue(syndEntry.getDescription().getValue().replaceFirst(str6, str));
            } else if (matcher4.find() && matcher4.groupCount() == 1) {
                url = matcher4.group(1);
                syndEntry.getDescription().setValue(syndEntry.getDescription().getValue().replaceFirst(str6, str));
            }
        }
        String str7 = "http://";
        if (!url.startsWith(str7) && !url.startsWith("https://")) {
            String str8 = "file:";
            if (url.startsWith(str8)) {
                return url.replaceFirst(str8, "http:");
            }
            String str9 = "//";
            if (url.startsWith(str9)) {
                return url.replaceFirst(str9, str7);
            }
        }
        return url;
    }

    public static String rssGetDescription(SyndEntry syndEntry) {
        if (syndEntry.getContents() == null || syndEntry.getContents().size() == 0) {
            return (syndEntry.getDescription() == null || syndEntry.getDescription().getValue() == null) ? "" : getDescription(syndEntry.getDescription().getValue());
        }
        return ((SyndContent) syndEntry.getContents().get(0)).getValue();
    }

    public static String getDescription(String str) {
        return str.replaceFirst("<img.+?>", "");
    }

    public static int convertDpToPixel(float f, Context context) {
        return Math.round(TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics()));
    }

    public static int convertDpToPixel(float f) {
        return convertDpToPixel(f, MobiRollerApplication.context);
    }
}
