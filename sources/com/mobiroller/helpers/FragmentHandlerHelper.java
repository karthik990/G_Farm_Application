package com.mobiroller.helpers;

public class FragmentHandlerHelper {
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static androidx.fragment.app.Fragment getFragment(java.lang.String r2, java.lang.String r3) {
        /*
            com.mobiroller.views.EmptyFragment r0 = new com.mobiroller.views.EmptyFragment
            r0.<init>()
            int r1 = r2.hashCode()
            switch(r1) {
                case -1771486603: goto L_0x00e2;
                case -1550268295: goto L_0x00d8;
                case -1243175570: goto L_0x00cd;
                case -1223439691: goto L_0x00c2;
                case -843903509: goto L_0x00b7;
                case -442817369: goto L_0x00ac;
                case -312878216: goto L_0x00a2;
                case 330767803: goto L_0x0098;
                case 429917810: goto L_0x008e;
                case 874838880: goto L_0x0083;
                case 923719348: goto L_0x0077;
                case 951949356: goto L_0x006c;
                case 961437749: goto L_0x0060;
                case 1475386597: goto L_0x0054;
                case 1643126201: goto L_0x0048;
                case 1798267217: goto L_0x003c;
                case 1807071320: goto L_0x0030;
                case 1854809030: goto L_0x0025;
                case 1879413220: goto L_0x0019;
                case 2018416945: goto L_0x000e;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x00ec
        L_0x000e:
            java.lang.String r1 = "aveMapView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 3
            goto L_0x00ed
        L_0x0019:
            java.lang.String r1 = "aveEmergencyCallView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 15
            goto L_0x00ed
        L_0x0025:
            java.lang.String r1 = "aveTweetView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 4
            goto L_0x00ed
        L_0x0030:
            java.lang.String r1 = "aveSettingsView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 13
            goto L_0x00ed
        L_0x003c:
            java.lang.String r1 = "aveFavoriteView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 14
            goto L_0x00ed
        L_0x0048:
            java.lang.String r1 = "aveTodoListView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 10
            goto L_0x00ed
        L_0x0054:
            java.lang.String r1 = "aveMP3View"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 9
            goto L_0x00ed
        L_0x0060:
            java.lang.String r1 = "aveECommerceView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 17
            goto L_0x00ed
        L_0x006c:
            java.lang.String r1 = "aveMainListView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 0
            goto L_0x00ed
        L_0x0077:
            java.lang.String r1 = "aveTvBroadcastView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 8
            goto L_0x00ed
        L_0x0083:
            java.lang.String r1 = "aveAboutUsView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 19
            goto L_0x00ed
        L_0x008e:
            java.lang.String r1 = "aveCustomScreenView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 2
            goto L_0x00ed
        L_0x0098:
            java.lang.String r1 = "aveRadioBroadcastView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 7
            goto L_0x00ed
        L_0x00a2:
            java.lang.String r1 = "aveYoutubeView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 5
            goto L_0x00ed
        L_0x00ac:
            java.lang.String r1 = "aveNoteView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 11
            goto L_0x00ed
        L_0x00b7:
            java.lang.String r1 = "aveFAQView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 16
            goto L_0x00ed
        L_0x00c2:
            java.lang.String r1 = "aveNotificationBoxView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 12
            goto L_0x00ed
        L_0x00cd:
            java.lang.String r1 = "aveCatalogView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 18
            goto L_0x00ed
        L_0x00d8:
            java.lang.String r1 = "aveFormView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 1
            goto L_0x00ed
        L_0x00e2:
            java.lang.String r1 = "avePhotoGalleryView"
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x00ec
            r2 = 6
            goto L_0x00ed
        L_0x00ec:
            r2 = -1
        L_0x00ed:
            switch(r2) {
                case 0: goto L_0x0168;
                case 1: goto L_0x0162;
                case 2: goto L_0x015c;
                case 3: goto L_0x0156;
                case 4: goto L_0x0150;
                case 5: goto L_0x014a;
                case 6: goto L_0x0144;
                case 7: goto L_0x013e;
                case 8: goto L_0x0138;
                case 9: goto L_0x0132;
                case 10: goto L_0x012c;
                case 11: goto L_0x0126;
                case 12: goto L_0x0120;
                case 13: goto L_0x011a;
                case 14: goto L_0x0114;
                case 15: goto L_0x010e;
                case 16: goto L_0x0107;
                case 17: goto L_0x0100;
                case 18: goto L_0x00f9;
                case 19: goto L_0x00f2;
                default: goto L_0x00f0;
            }
        L_0x00f0:
            goto L_0x016d
        L_0x00f2:
            com.mobiroller.fragments.aveAboutUsViewFragment r0 = new com.mobiroller.fragments.aveAboutUsViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x00f9:
            com.mobiroller.fragments.aveCatalogViewFragment r0 = new com.mobiroller.fragments.aveCatalogViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0100:
            com.mobiroller.fragments.aveECommerceViewFragment r0 = new com.mobiroller.fragments.aveECommerceViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0107:
            com.mobiroller.fragments.aveFAQViewFragment r0 = new com.mobiroller.fragments.aveFAQViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x010e:
            com.mobiroller.fragments.aveEmergencyCallViewFragment r0 = new com.mobiroller.fragments.aveEmergencyCallViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0114:
            com.mobiroller.fragments.aveFavoriteViewFragment r0 = new com.mobiroller.fragments.aveFavoriteViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x011a:
            com.mobiroller.fragments.aveSettingsViewFragment r0 = new com.mobiroller.fragments.aveSettingsViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0120:
            com.mobiroller.fragments.aveNotificationBoxViewFragment r0 = new com.mobiroller.fragments.aveNotificationBoxViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0126:
            com.mobiroller.fragments.aveNoteViewFragment r0 = new com.mobiroller.fragments.aveNoteViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x012c:
            com.mobiroller.fragments.aveTodoListViewFragment r0 = new com.mobiroller.fragments.aveTodoListViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0132:
            com.mobiroller.fragments.aveMP3ViewFragment r0 = new com.mobiroller.fragments.aveMP3ViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0138:
            com.mobiroller.fragments.aveTVBroadcastViewFragment r0 = new com.mobiroller.fragments.aveTVBroadcastViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x013e:
            com.mobiroller.fragments.aveRadioBroadcastViewFragment r0 = new com.mobiroller.fragments.aveRadioBroadcastViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0144:
            com.mobiroller.fragments.avePhotoGalleryViewFragment r0 = new com.mobiroller.fragments.avePhotoGalleryViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x014a:
            com.mobiroller.fragments.aveYoutubeViewFragment r0 = new com.mobiroller.fragments.aveYoutubeViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0150:
            com.mobiroller.fragments.aveTweetViewFragment r0 = new com.mobiroller.fragments.aveTweetViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0156:
            com.mobiroller.fragments.aveMapViewFragment r0 = new com.mobiroller.fragments.aveMapViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x015c:
            com.mobiroller.fragments.aveCustomScreenViewFragment r0 = new com.mobiroller.fragments.aveCustomScreenViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0162:
            com.mobiroller.fragments.aveFormViewFragment r0 = new com.mobiroller.fragments.aveFormViewFragment
            r0.<init>()
            goto L_0x016d
        L_0x0168:
            com.mobiroller.fragments.aveMainListViewFragment r0 = new com.mobiroller.fragments.aveMainListViewFragment
            r0.<init>()
        L_0x016d:
            if (r3 == 0) goto L_0x017c
            java.lang.String r2 = "avePDFView"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x017c
            com.mobiroller.fragments.avePDFViewFragment r0 = new com.mobiroller.fragments.avePDFViewFragment
            r0.<init>()
        L_0x017c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobiroller.helpers.FragmentHandlerHelper.getFragment(java.lang.String, java.lang.String):androidx.fragment.app.Fragment");
    }
}
