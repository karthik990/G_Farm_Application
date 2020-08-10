package com.google.android.youtube.player.internal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import p043io.fabric.sdk.android.services.events.EventsFilesManager;

/* renamed from: com.google.android.youtube.player.internal.x */
public final class C2836x {
    /* renamed from: a */
    public static Map<String, String> m1744a(Locale locale) {
        HashMap hashMap = new HashMap();
        hashMap.put("error_initializing_player", "An error occurred while initializing the YouTube player.");
        String str = "Get YouTube App";
        hashMap.put("get_youtube_app_title", str);
        hashMap.put("get_youtube_app_text", "This app won't run without the YouTube App, which is missing from your device");
        hashMap.put("get_youtube_app_action", str);
        String str2 = "Enable YouTube App";
        hashMap.put("enable_youtube_app_title", str2);
        hashMap.put("enable_youtube_app_text", "This app won't work unless you enable the YouTube App.");
        hashMap.put("enable_youtube_app_action", str2);
        String str3 = "Update YouTube App";
        hashMap.put("update_youtube_app_title", str3);
        hashMap.put("update_youtube_app_text", "This app won't work unless you update the YouTube App.");
        hashMap.put("update_youtube_app_action", str3);
        m1745a(hashMap, locale.getLanguage());
        String valueOf = String.valueOf(locale.getLanguage());
        String valueOf2 = String.valueOf(locale.getCountry());
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 1 + String.valueOf(valueOf2).length());
        sb.append(valueOf);
        sb.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
        sb.append(valueOf2);
        m1745a(hashMap, sb.toString());
        return hashMap;
    }

    /* renamed from: a */
    private static void m1745a(Map<String, String> map, String str) {
        String str2 = "update_youtube_app_action";
        String str3 = "update_youtube_app_text";
        String str4 = "update_youtube_app_title";
        String str5 = "enable_youtube_app_action";
        String str6 = "enable_youtube_app_text";
        String str7 = "enable_youtube_app_title";
        String str8 = "get_youtube_app_action";
        String str9 = "get_youtube_app_text";
        String str10 = "get_youtube_app_title";
        String str11 = "error_initializing_player";
        if ("af".equals(str)) {
            map.put(str11, "Kon nie die YouTube-speler inisialiseer nie.");
            String str12 = "Kry YouTube-program";
            map.put(str10, str12);
            map.put(str9, "Hierdie program sal nie loop sonder die YouTube-program, wat ontbreek van jou toestel, nie");
            map.put(str8, str12);
            String str13 = "Aktiveer YouTube-program";
            map.put(str7, str13);
            map.put(str6, "Hierdie program sal nie werk tensy jy die YouTube-program aktiveer nie.");
            map.put(str5, str13);
            String str14 = "Dateer YouTube-program op";
            map.put(str4, str14);
            map.put(str3, "Hierdie program sal nie werk tensy jy die YouTube-program opdateer nie.");
            map.put(str2, str14);
        } else if ("am".equals(str)) {
            map.put(str11, "የYouTube አጫዋቹን በማስጀመር ላይ ሳለ አንድ ስህተት ተከስቷል።");
            String str15 = "የYouTube መተግበሪያውን ያግኙ";
            map.put(str10, str15);
            map.put(str9, "ይህ መተግበሪያ ያለ YouTube መተግበሪያው አይሂድም፣ እሱ ደግሞ በመሣሪያዎ ላይ የለም።");
            map.put(str8, str15);
            map.put(str7, "የYouTube መተግበሪያውን ያንቁ");
            map.put(str6, "የYouTube መተግበሪያውን እስካላነቁ ድረስ ይህ መተግበሪያ አይሰራም።");
            map.put(str5, "የYouTube መተግበሪያውን ያንቁ");
            map.put(str4, "የYouTube መተግበሪያውን ያዘምኑ");
            map.put(str3, "የYouTube መተግበሪያውን እስካላዘመኑት ድረስ ይህ መተግበሪያ አይሰራም።");
            map.put(str2, "የYouTube መተግበሪያውን ያዘምኑ");
        } else if ("ar".equals(str)) {
            map.put(str11, "حدث خطأ أثناء تهيئة مشغل YouTube.");
            map.put(str10, "الحصول على تطبيق YouTube");
            map.put(str9, "لن يعمل هذا التطبيق بدون تطبيق YouTube الذي لا يتوفر على جهازك");
            map.put(str8, "الحصول على تطبيق YouTube");
            map.put(str7, "تمكين تطبيق YouTube");
            map.put(str6, "لن يعمل هذا التطبيق ما لم يتم تمكين تطبيق YouTube.");
            map.put(str5, "تمكين تطبيق YouTube");
            map.put(str4, "تحديث تطبيق YouTube");
            map.put(str3, "لن يعمل هذا التطبيق ما لم يتم تحديث تطبيق YouTube.");
            map.put(str2, "تحديث تطبيق YouTube");
        } else if ("be".equals(str)) {
            map.put(str11, "Памылка падчас ініцыялізацыі прайгравальнiка YouTube.");
            map.put(str10, "Спампаваць прыкладанне YouTube");
            map.put(str9, "Гэта прыкладанне не будзе працаваць без прыкладання YouTube, якое адсутнічае на прыладзе");
            map.put(str8, "Спампаваць прыкладанне YouTube");
            map.put(str7, "Уключыць прыкладанне YouTube");
            map.put(str6, "Гэта прыкладанне не будзе працаваць, пакуль вы не ўключыце прыкладанне YouTube.");
            map.put(str5, "Уключыць прыкладанне YouTube");
            map.put(str4, "Абнавiць прыкладанне YouTube");
            map.put(str3, "Гэта прыкладанне не будзе працаваць, пакуль вы не абнавiце прыкладанне YouTube.");
            map.put(str2, "Абнавiць прыкладанне YouTube");
        } else if ("bg".equals(str)) {
            map.put(str11, "При подготвянето на плейъра на YouTube за работа възникна грешка.");
            map.put(str10, "Изтегл. на прил. YouTube");
            map.put(str9, "Това приложение няма да работи без приложението YouTube, което липсва на устройството ви");
            map.put(str8, "Изтегл. на прил. YouTube");
            map.put(str7, "Акт. на прил. YouTube");
            map.put(str6, "Това приложение няма да работи, освен ако не активирате приложението YouTube.");
            map.put(str5, "Акт. на прил. YouTube");
            map.put(str4, "Актул. на прил. YouTube");
            map.put(str3, "Това приложение няма да работи, освен ако не актуализирате приложението YouTube.");
            map.put(str2, "Актул. на прил. YouTube");
        } else if ("ca".equals(str)) {
            map.put(str11, "S'ha produït un error en iniciar el reproductor de YouTube.");
            map.put(str10, "Obtenció aplicac. YouTube");
            map.put(str9, "Aquesta aplicació no funcionarà sense l'aplicació de YouTube, que encara no és al dispositiu.");
            map.put(str8, "Obtén l'aplic. de YouTube");
            map.put(str7, "Activació aplic. YouTube");
            map.put(str6, "Aquesta aplicació no funcionarà fins que no activis l'aplicació de YouTube.");
            map.put(str5, "Activa aplicació YouTube");
            map.put(str4, "Actualitz. aplic. YouTube");
            map.put(str3, "Aquesta aplicació no funcionarà fins que no actualitzis l'aplicació de YouTube.");
            map.put(str2, "Actualitza aplic. YouTube");
        } else if ("cs".equals(str)) {
            map.put(str11, "Při inicializaci přehrávače YouTube došlo k chybě.");
            map.put(str10, "Stáhněte aplikaci YouTube");
            map.put(str9, "Tuto aplikaci nelze spustit bez aplikace YouTube, kterou v zařízení nemáte nainstalovanou");
            map.put(str8, "Stáhnout aplikaci YouTube");
            map.put(str7, "Aktivujte aplik. YouTube");
            map.put(str6, "Ke spuštění této aplikace je třeba aktivovat aplikaci YouTube.");
            map.put(str5, "Zapnout aplikaci YouTube");
            map.put(str4, "Aktualizujte apl. YouTube");
            map.put(str3, "Ke spuštění této aplikace je třeba aktualizovat aplikaci YouTube.");
            map.put(str2, "Aktualizovat apl. YouTube");
        } else if ("da".equals(str)) {
            map.put(str11, "Der opstod en fejl under initialisering af YouTube-afspilleren.");
            map.put(str10, "Få YouTube-appen");
            map.put(str9, "Denne app kan ikke køre uden YouTube-appen, som ikke findes på din enhed");
            map.put(str8, "Få YouTube-appen");
            map.put(str7, "Aktivér YouTube-appen");
            map.put(str6, "Denne app fungerer ikke, medmindre du aktiverer YouTube-appen.");
            map.put(str5, "Aktivér YouTube-appen");
            map.put(str4, "Opdater YouTube-appen");
            map.put(str3, "Denne app fungerer ikke, hvis du ikke opdaterer YouTube-appen.");
            map.put(str2, "Opdater YouTube-appen");
        } else if ("de".equals(str)) {
            map.put(str11, "Bei der Initialisierung des YouTube-Players ist ein Fehler aufgetreten.");
            map.put(str10, "YouTube App herunterladen");
            map.put(str9, "Diese App kann nur ausgeführt werden, wenn die YouTube App bereitgestellt ist. Diese ist auf deinem Gerät nicht vorhanden.");
            map.put(str8, "YouTube App herunterladen");
            map.put(str7, "YouTube App aktivieren");
            map.put(str6, "Diese App funktioniert nur, wenn die YouTube App aktiviert wird.");
            map.put(str5, "YouTube App aktivieren");
            map.put(str4, "YouTube App aktualisieren");
            map.put(str3, "Diese App funktioniert nur, wenn die YouTube App aktualisiert wird.");
            map.put(str2, "YouTube App aktualisieren");
        } else if ("el".equals(str)) {
            map.put(str11, "Παρουσιάστηκε σφάλμα κατά την προετοιμασία του προγράμματος αναπαραγωγής του YouTube.");
            map.put(str10, "Λήψη YouTube");
            map.put(str9, "Δεν είναι δυνατή η εκτέλεση αυτής της εφαρμογής χωρίς την εφαρμογή YouTube, η οποία απουσιάζει από τη συσκευή σας");
            map.put(str8, "Λήψη YouTube");
            map.put(str7, "Ενεργοποίηση YouTube");
            map.put(str6, "Δεν είναι δυνατή η λειτουργία αυτής της εφαρμογής εάν δεν ενεργοποιήσετε την εφαρμογή YouTube.");
            map.put(str5, "Ενεργοποίηση YouTube");
            map.put(str4, "Ενημέρωση YouTube");
            map.put(str3, "Δεν είναι δυνατή η λειτουργία αυτής της εφαρμογής εάν δεν ενημερώσετε την εφαρμογή YouTube.");
            map.put(str2, "Ενημέρωση YouTube");
        } else if ("en_GB".equals(str)) {
            map.put(str11, "An error occurred while initialising the YouTube player.");
            map.put(str10, "Get YouTube App");
            map.put(str9, "This app won't run without the YouTube App, which is missing from your device");
            map.put(str8, "Get YouTube App");
            map.put(str7, "Enable YouTube App");
            map.put(str6, "This app won't work unless you enable the YouTube App.");
            map.put(str5, "Enable YouTube App");
            map.put(str4, "Update YouTube App");
            map.put(str3, "This app won't work unless you update the YouTube App.");
            map.put(str2, "Update YouTube App");
        } else {
            String str16 = "Actualizar YouTube";
            if ("es_US".equals(str)) {
                map.put(str11, "Se produjo un error al iniciar el reproductor de YouTube.");
                map.put(str10, "Obtener YouTube");
                map.put(str9, "Esta aplicación no se ejecutará sin la aplicación YouTube, la cual no se instaló en tu dispositivo.");
                map.put(str8, "Obtener YouTube");
                map.put(str7, "Activar YouTube");
                map.put(str6, "Esta aplicación no funcionará a menos que actives la aplicación YouTube.");
                map.put(str5, "Activar YouTube");
                map.put(str4, str16);
                map.put(str3, "Esta aplicación no funcionará a menos que actualices la aplicación YouTube.");
                map.put(str2, str16);
            } else if ("es".equals(str)) {
                map.put(str11, "Se ha producido un error al iniciar el reproductor de YouTube.");
                map.put(str10, "Descarga YouTube");
                map.put(str9, "Esta aplicación no funcionará sin la aplicación YouTube, que no está instalada en el dispositivo.");
                map.put(str8, "Descargar YouTube");
                map.put(str7, "Habilita la aplicación YouTube");
                map.put(str6, "Esta aplicación no funcionará si no habilitas la aplicación YouTube.");
                map.put(str5, "Habilitar YouTube");
                map.put(str4, "Actualiza YouTube");
                map.put(str3, "Esta aplicación no funcionará si no actualizas la aplicación YouTube.");
                map.put(str2, str16);
            } else if ("et".equals(str)) {
                map.put(str11, "YouTube'i mängija lähtestamisel tekkis viga.");
                map.put(str10, "YouTube'i rak. hankimine");
                map.put(str9, "Rakendus ei käivitu ilma YouTube'i rakenduseta ja teie seadmes see praegu puudub");
                map.put(str8, "Hangi YouTube'i rakendus");
                map.put(str7, "YouTube'i rakenduse lubamine");
                map.put(str6, "Rakendus ei toimi, kui te ei luba kasutada YouTube'i rakendust.");
                map.put(str5, "Luba YouTube'i rakendus");
                map.put(str4, "Värskenda YouTube");
                map.put(str3, "Rakendus ei toimi enne, kui olete YouTube'i rakendust värskendanud.");
                map.put(str2, "Värsk. YouTube'i rakend.");
            } else if ("fa".equals(str)) {
                map.put(str11, "هنگام مقداردهی اولیه پخش‌کننده YouTube، خطایی روی داد.");
                map.put(str10, "دریافت برنامه YouTube");
                map.put(str9, "این برنامه بدون برنامه YouTube که در دستگاه شما موجود نیست، اجرا نمی‌شود");
                map.put(str8, "دریافت برنامه YouTube");
                map.put(str7, "فعال کردن برنامه YouTube");
                map.put(str6, "این برنامه تنها در صورتی کار خواهد کرد که برنامه YouTube را فعال کنید.");
                map.put(str5, "فعال کردن برنامه YouTube");
                map.put(str4, "به‌روزرسانی برنامه YouTube");
                map.put(str3, "این برنامه کار نخواهد کرد مگر اینکه برنامه YouTube را به روز کنید.");
                map.put(str2, "به‌روزرسانی برنامه YouTube");
            } else if ("fi".equals(str)) {
                map.put(str11, "Virhe alustettaessa YouTube-soitinta.");
                map.put(str10, "Hanki YouTube-sovellus");
                map.put(str9, "Tämä sovellus ei toimi ilman YouTube-sovellusta, joka puuttuu laitteesta.");
                map.put(str8, "Hanki YouTube-sovellus");
                map.put(str7, "Ota YouTube-sov. käyttöön");
                map.put(str6, "Tämä sovellus ei toimi, ellet ota YouTube-sovellusta käyttöön.");
                map.put(str5, "Ota YouTube-sov. käyttöön");
                map.put(str4, "Päivitä YouTube-sovellus");
                map.put(str3, "Tämä sovellus ei toimi, ellet päivitä YouTube-sovellusta.");
                map.put(str2, "Päivitä YouTube-sovellus");
            } else if ("fr".equals(str)) {
                map.put(str11, "Une erreur s'est produite lors de l'initialisation du lecteur YouTube.");
                map.put(str10, "Télécharger appli YouTube");
                map.put(str9, "Cette application ne fonctionnera pas sans l'application YouTube, qui n'est pas installée sur votre appareil.");
                map.put(str8, "Télécharger appli YouTube");
                map.put(str7, "Activer l'appli YouTube");
                map.put(str6, "Cette application ne fonctionnera que si vous activez l'application YouTube.");
                map.put(str5, "Activer l'appli YouTube");
                map.put(str4, "Mise à jour appli YouTube");
                map.put(str3, "Cette application ne fonctionnera que si vous mettez à jour l'application YouTube.");
                map.put(str2, "Mise à jour appli YouTube");
            } else if ("hi".equals(str)) {
                map.put(str11, "YouTube प्लेयर को प्रारंभ करते समय कोई त्रुटि आई.");
                map.put(str10, "YouTube एप्लि. प्राप्त करें");
                map.put(str9, "यह एप्लिकेशन YouTube एप्लिकेशन के बिना नहीं चलेगा, जो आपके उपकरण पर मौजूद नहीं है");
                map.put(str8, "YouTube एप्लि. प्राप्त करें");
                map.put(str7, "YouTube एप्लि. सक्षम करें");
                map.put(str6, "जब तक आप YouTube एप्लिकेशन सक्षम नहीं करते, तब तक यह एप्लिकेशन कार्य नहीं करेगा.");
                map.put(str5, "YouTube एप्लि. सक्षम करें");
                map.put(str4, "YouTube एप्लि. अपडेट करें");
                map.put(str3, "जब तक आप YouTube एप्लिकेशन अपडेट नहीं करते, तब तक यह एप्लिकेशन कार्य नहीं करेगा.");
                map.put(str2, "YouTube एप्लि. अपडेट करें");
            } else if ("hr".equals(str)) {
                map.put(str11, "Dogodila se pogreška tijekom pokretanja playera usluge YouTube.");
                map.put(str10, "Preuzimanje apl. YouTube");
                map.put(str9, "Ova se aplikacija ne može pokrenuti bez aplikacije YouTube, koja nije instalirana na vaš uređaj");
                map.put(str8, "Preuzmi apl. YouTube");
                map.put(str7, "Omogućavanje apl. YouTube");
                map.put(str6, "Ova aplikacija neće funkcionirati ako ne omogućite aplikaciju YouTube.");
                map.put(str5, "Omogući apl. YouTube");
                map.put(str4, "Ažuriranje apl. YouTube");
                map.put(str3, "Ova aplikacija neće funkcionirati ako ne ažurirate aplikaciju YouTube.");
                map.put(str2, "Ažuriraj apl. YouTube");
            } else if ("hu".equals(str)) {
                map.put(str11, "Hiba történt a YouTube lejátszó inicializálása során.");
                map.put(str10, "YouTube alk. letöltése");
                map.put(str9, "Ez az alkalmazás nem fut a YouTube alkalmazás nélkül, amely hiányzik az eszközéről.");
                map.put(str8, "YouTube alk. letöltése");
                map.put(str7, "YouTube alkalmazás enged.");
                map.put(str6, "Az alkalmazás csak akkor fog működni, ha engedélyezi a YouTube alkalmazást.");
                map.put(str5, "YouTube alkalmazás enged.");
                map.put(str4, "YouTube alk. frissítése");
                map.put(str3, "Az alkalmazás csak akkor fog működni, ha frissíti a YouTube alkalmazást.");
                map.put(str2, "YouTube alk. frissítése");
            } else if ("in".equals(str)) {
                map.put(str11, "Terjadi kesalahan saat memulai pemutar YouTube.");
                map.put(str10, "Dapatkan Aplikasi YouTube");
                map.put(str9, "Aplikasi ini tidak akan berjalan tanpa Aplikasi YouTube, yang hilang dari perangkat Anda");
                map.put(str8, "Dapatkan Aplikasi YouTube");
                map.put(str7, "Aktifkan Aplikasi YouTube");
                map.put(str6, "Aplikasi ini tidak akan bekerja kecuali Anda mengaktifkan Aplikasi YouTube.");
                map.put(str5, "Aktifkan Aplikasi YouTube");
                map.put(str4, "Perbarui Aplikasi YouTube");
                map.put(str3, "Aplikasi ini tidak akan bekerja kecuali Anda memperbarui Aplikasi YouTube.");
                map.put(str2, "Perbarui Aplikasi YouTube");
            } else if ("it".equals(str)) {
                map.put(str11, "Si è verificato un errore durante l'inizializzazione del player di YouTube.");
                map.put(str10, "Scarica app YouTube");
                map.put(str9, "Questa applicazione non funzionerà senza l'applicazione YouTube che non è presente sul tuo dispositivo");
                map.put(str8, "Scarica app YouTube");
                map.put(str7, "Attiva app YouTube");
                map.put(str6, "Questa applicazione non funzionerà se non attivi l'applicazione YouTube.");
                map.put(str5, "Attiva app YouTube");
                map.put(str4, "Aggiorna app YouTube");
                map.put(str3, "Questa applicazione non funzionerà se non aggiorni l'applicazione YouTube.");
                map.put(str2, "Aggiorna app YouTube");
            } else if ("iw".equals(str)) {
                map.put(str11, "אירעה שגיאה בעת אתחול נגן YouTube.");
                map.put(str10, "קבל את יישום YouTube");
                map.put(str9, "יישום זה לא יפעל ללא יישום YouTube, שאינו מותקן במכשיר שלך");
                map.put(str8, "קבל את יישום YouTube");
                map.put(str7, "הפעל את יישום YouTube");
                map.put(str6, "יישום זה לא יעבוד אלא אם תפעיל את יישום YouTube.");
                map.put(str5, "הפעל את יישום YouTube");
                map.put(str4, "עדכן את יישום YouTube");
                map.put(str3, "יישום זה לא יעבוד אלא אם תעדכן את יישום YouTube.");
                map.put(str2, "עדכן את יישום YouTube");
            } else if ("ja".equals(str)) {
                map.put(str11, "YouTubeプレーヤーの初期化中にエラーが発生しました。");
                map.put(str10, "YouTubeアプリを入手");
                map.put(str9, "このアプリの実行に必要なYouTubeアプリが端末にありません");
                map.put(str8, "YouTubeアプリを入手");
                map.put(str7, "YouTubeアプリを有効化");
                map.put(str6, "このアプリの実行にはYouTubeアプリの有効化が必要です。");
                map.put(str5, "YouTubeアプリを有効化");
                map.put(str4, "YouTubeアプリを更新");
                map.put(str3, "このアプリの実行にはYouTubeアプリの更新が必要です。");
                map.put(str2, "YouTubeアプリを更新");
            } else if ("ko".equals(str)) {
                map.put(str11, "YouTube 플레이어를 초기화하는 중에 오류가 발생했습니다.");
                map.put(str10, "YouTube 앱 다운로드");
                map.put(str9, "이 앱은 내 기기에 YouTube 앱이 없어서 실행되지 않습니다.");
                map.put(str8, "YouTube 앱 다운로드");
                map.put(str7, "YouTube 앱 사용 설정");
                map.put(str6, "이 앱은 YouTube 앱을 사용하도록 설정하지 않으면 작동하지 않습니다.");
                map.put(str5, "YouTube 앱 사용");
                map.put(str4, "YouTube 앱 업데이트");
                map.put(str3, "이 앱은 YouTube 앱을 업데이트하지 않으면 작동하지 않습니다.");
                map.put(str2, "YouTube 앱 업데이트");
            } else if ("lt".equals(str)) {
                map.put(str11, "Inicijuojant „YouTube“ grotuvą įvyko klaida.");
                map.put(str10, "Gauti „YouTube“ programą");
                map.put(str9, "Ši programa neveikia be „YouTube“ programos, o jos įrenginyje nėra.");
                map.put(str8, "Gauti „YouTube“ programą");
                map.put(str7, "Įgalinti „YouTube“ progr.");
                map.put(str6, "Ši programa neveiks, jei neįgalinsite „YouTube“ programos.");
                map.put(str5, "Įgalinti „YouTube“ progr.");
                map.put(str4, "Atnauj. „YouTube“ progr.");
                map.put(str3, "Ši programa neveiks, jei neatnaujinsite „YouTube“ programos.");
                map.put(str2, "Atnauj. „YouTube“ progr.");
            } else if ("lv".equals(str)) {
                map.put(str11, "Inicializējot YouTube atskaņotāju, radās kļūda.");
                map.put(str10, "YouTube liet. iegūšana");
                map.put(str9, "Šī lietotne nedarbosies bez YouTube lietotnes, kuras nav šajā ierīcē.");
                map.put(str8, "Iegūt YouTube lietotni");
                map.put(str7, "YouTube liet. iespējošana");
                map.put(str6, "Lai šī lietotne darbotos, iespējojiet YouTube lietotni.");
                map.put(str5, "Iespējot YouTube lietotni");
                map.put(str4, "YouTube liet. atjaunin.");
                map.put(str3, "Lai šī lietotne darbotos, atjauniniet YouTube lietotni.");
                map.put(str2, "Atjaun. YouTube lietotni");
            } else if ("ms".equals(str)) {
                map.put(str11, "Ralat berlaku semasa memulakan alat main YouTube.");
                map.put(str10, "Dapatkan Apl YouTube");
                map.put(str9, "Apl ini tidak akan berjalan tanpa Apl YouTube, yang tidak ada pada peranti anda");
                map.put(str8, "Dapatkan Apl YouTube");
                map.put(str7, "Dayakan Apl YouTube");
                map.put(str6, "Apl ini tidak akan berfungsi kecuali anda mendayakan Apl YouTube.");
                map.put(str5, "Dayakan Apl YouTube");
                map.put(str4, "Kemas kini Apl YouTube");
                map.put(str3, "Apl ini tidak akan berfungsi kecuali anda mengemas kini Apl YouTube.");
                map.put(str2, "Kemas kini Apl YouTube");
            } else if ("nb".equals(str)) {
                map.put(str11, "Det oppsto en feil da YouTube-avspilleren startet.");
                map.put(str10, "Skaff deg YouTube-appen");
                map.put(str9, "Denne appen kan ikke kjøre uten YouTube-appen, som du ikke har på enheten");
                map.put(str8, "Skaff deg YouTube-appen");
                map.put(str7, "Aktiver YouTube-appen");
                map.put(str6, "Denne appen fungerer ikke før du aktiverer YouTube-appen.");
                map.put(str5, "Aktiver YouTube-appen");
                map.put(str4, "Oppdater YouTube-appen");
                map.put(str3, "Denne appen fungerer ikke før du oppdaterer YouTube-appen.");
                map.put(str2, "Oppdater YouTube-appen");
            } else if ("nl".equals(str)) {
                map.put(str11, "Er is een fout opgetreden bij het initialiseren van de YouTube-speler.");
                map.put(str10, "YouTube-app downloaden");
                map.put(str9, "Deze app wordt niet uitgevoerd zonder de YouTube-app, die op uw apparaat ontbreekt");
                map.put(str8, "YouTube-app downloaden");
                map.put(str7, "YouTube-app inschakelen");
                map.put(str6, "Deze app werkt niet, tenzij u de YouTube-app inschakelt.");
                map.put(str5, "YouTube-app inschakelen");
                map.put(str4, "YouTube-app bijwerken");
                map.put(str3, "Deze app werkt niet, tenzij u de YouTube-app bijwerkt.");
                map.put(str2, "YouTube-app bijwerken");
            } else if ("pl".equals(str)) {
                map.put(str11, "Podczas inicjowania odtwarzacza YouTube wystąpił błąd.");
                map.put(str10, "Pobierz aplikację YouTube");
                map.put(str9, "Ta aplikacja nie będzie działać bez aplikacji YouTube, której nie ma na tym urządzeniu");
                map.put(str8, "Pobierz aplikację YouTube");
                map.put(str7, "Włącz aplikację YouTube");
                map.put(str6, "Ta aplikacja nie będzie działać, jeśli nie włączysz aplikacji YouTube.");
                map.put(str5, "Włącz aplikację YouTube");
                map.put(str4, "Zaktualizuj aplikację YouTube");
                map.put(str3, "Ta aplikacja nie będzie działać, jeśli nie zaktualizujesz aplikacji YouTube.");
                map.put(str2, "Zaktualizuj aplikację YouTube");
            } else if ("pt_PT".equals(str)) {
                map.put(str11, "Ocorreu um erro ao iniciar o leitor do YouTube.");
                map.put(str10, "Obter a Aplicação YouTube");
                map.put(str9, "Esta aplicação não será executada sem a Aplicação YouTube, que está em falta no seu dispositivo");
                map.put(str8, "Obter a Aplicação YouTube");
                map.put(str7, "Ativar Aplicação YouTube");
                map.put(str6, "Esta aplicação não irá funcionar enquanto não ativar a Aplicação YouTube.");
                map.put(str5, "Ativar Aplicação YouTube");
                map.put(str4, "Atualizar Aplica. YouTube");
                map.put(str3, "Esta aplicação não irá funcionar enquanto não atualizar a Aplicação YouTube.");
                map.put(str2, "Atualizar Aplica. YouTube");
            } else if ("pt".equals(str)) {
                map.put(str11, "Ocorreu um erro ao inicializar o player do YouTube.");
                map.put(str10, "Obter aplicativo YouTube");
                map.put(str9, "Este aplicativo só funciona com o aplicativo YouTube, que está ausente no dispositivo.");
                map.put(str8, "Obter aplicativo YouTube");
                map.put(str7, "Ativar aplicativo YouTube");
                map.put(str6, "Este aplicativo só funciona com o aplicativo YouTube ativado.");
                map.put(str5, "Ativar aplicativo YouTube");
                map.put(str4, "Atualizar aplic. YouTube");
                map.put(str3, "Este aplicativo só funciona com o aplicativo YouTube atualizado.");
                map.put(str2, "Atualizar aplic. YouTube");
            } else if ("ro".equals(str)) {
                map.put(str11, "A apărut o eroare la iniţializarea playerului YouTube.");
                map.put(str10, "Descărcaţi YouTube");
                map.put(str9, "Această aplicaţie nu va rula fără aplicaţia YouTube, care lipseşte de pe gadget");
                map.put(str8, "Descărcaţi YouTube");
                map.put(str7, "Activaţi YouTube");
                map.put(str6, "Această aplicaţie nu va funcţiona decât dacă activaţi aplicaţia YouTube.");
                map.put(str5, "Activaţi YouTube");
                map.put(str4, "Actualizaţi YouTube");
                map.put(str3, "Această aplicaţie nu va funcţiona decât dacă actualizaţi aplicaţia YouTube.");
                map.put(str2, "Actualizaţi YouTube");
            } else if ("ru".equals(str)) {
                map.put(str11, "Не удалось запустить проигрыватель YouTube.");
                map.put(str10, "Загрузите YouTube");
                map.put(str9, "Чтобы запустить эту программу, установите приложение YouTube.");
                map.put(str8, "Загрузить YouTube");
                map.put(str7, "Активация YouTube");
                map.put(str6, "Чтобы запустить эту программу, активируйте приложение YouTube.");
                map.put(str5, "Активировать YouTube");
                map.put(str4, "Обновление YouTube");
                map.put(str3, "Чтобы запустить эту программу, обновите приложение YouTube.");
                map.put(str2, "Обновить YouTube");
            } else if ("sk".equals(str)) {
                map.put(str11, "Pri inicializácii prehrávača YouTube sa vyskytla chyba.");
                map.put(str10, "Získať aplikáciu YouTube");
                map.put(str9, "Túto aplikáciu nebude možné spustiť bez aplikácie YouTube, ktorá na zariadení nie je nainštalovaná.");
                map.put(str8, "Získať aplikáciu YouTube");
                map.put(str7, "Povoliť aplikáciu YouTube");
                map.put(str6, "Táto aplikácia bude fungovať až po povolení aplikácie YouTube.");
                map.put(str5, "Povoliť aplikáciu YouTube");
                map.put(str4, "Aktualizovať apl. YouTube");
                map.put(str3, "Táto aplikácia bude fungovať až po aktualizácii aplikácie YouTube.");
                map.put(str2, "Aktualizovať apl. YouTube");
            } else if ("sl".equals(str)) {
                map.put(str11, "Napaka med inicializacijo YouTubovega predvajalnika.");
                map.put(str10, "Prenos aplikacije YouTube");
                map.put(str9, "Ta aplikacija ne bo delovala brez aplikacije YouTube, ki je ni v vaši napravi");
                map.put(str8, "Prenos aplikacije YouTube");
                map.put(str7, "Omog. aplikacije YouTube");
                map.put(str6, "Ta aplikacija ne bo delovala, če ne omogočite aplikacije YouTube.");
                map.put(str5, "Omog. aplikacijo YouTube");
                map.put(str4, "Posodob. aplikacije YouTube");
                map.put(str3, "Ta aplikacija ne bo delovala, če ne posodobite aplikacije YouTube.");
                map.put(str2, "Posod. aplikacijo YouTube");
            } else if ("sr".equals(str)) {
                map.put(str11, "Дошло је до грешке при покретању YouTube плејера.");
                map.put(str10, "Преузимање аплик. YouTube");
                map.put(str9, "Ова апликација неће функционисати без апликације YouTube, која недостаје на уређају");
                map.put(str8, "Преузми апликац. YouTube");
                map.put(str7, "Омогућавање апл. YouTube");
                map.put(str6, "Ова апликације неће функционисати ако не омогућите апликацију YouTube.");
                map.put(str5, "Омогући апликац. YouTube");
                map.put(str4, "Ажурирање аплик. YouTube");
                map.put(str3, "Ова апликације неће функционисати ако не ажурирате апликацију YouTube.");
                map.put(str2, "Ажурирај апликац. YouTube");
            } else if ("sv".equals(str)) {
                map.put(str11, "Ett fel uppstod när YouTube-spelaren skulle startas.");
                map.put(str10, "Hämta YouTube-appen");
                map.put(str9, "YouTube-appen krävs för att den här appen ska kunna köras. Du har inte YouTube-appen på din enhet.");
                map.put(str8, "Hämta YouTube-appen");
                map.put(str7, "Aktivera YouTube-appen");
                map.put(str6, "Du måste aktivera YouTube-appen för att den här appen ska fungera.");
                map.put(str5, "Aktivera YouTube-appen");
                map.put(str4, "Uppdatera YouTube-appen");
                map.put(str3, "Du måste uppdatera YouTube-appen för att den här appen ska fungera.");
                map.put(str2, "Uppdatera YouTube-appen");
            } else if ("sw".equals(str)) {
                map.put(str11, "Hitilafu ilitokea wakati wa kuanzisha kichezeshi cha YouTube.");
                map.put(str10, "Pata Programu ya YouTube");
                map.put(str9, "Programu hii haitaendeshwa bila Programu ya YouTube, ambayo inakosekana kwenye kifaa chako.");
                map.put(str8, "Pata Programu ya YouTube");
                map.put(str7, "Wezesha Programu ya YouTube");
                map.put(str6, "Programu hii haitafanya kazi isipokuwa uwezeshe Programu ya YouTube.");
                map.put(str5, "Wezesha Programu ya YouTube");
                map.put(str4, "Sasisha Programu ya YouTube");
                map.put(str3, "Programu hii haitafanya kazi mpaka usasishe Programu ya YouTube.");
                map.put(str2, "Sasisha Programu ya YouTube");
            } else if ("th".equals(str)) {
                map.put(str11, "เกิดข้อผิดพลาดในขณะเริ่มต้นโปรแกรมเล่น YouTube");
                map.put(str10, "รับแอปพลิเคชัน YouTube");
                map.put(str9, "แอปพลิเคชันนี้จะไม่ทำงานหากไม่มีแอปพลิเคชัน YouTube ซึ่งไม่มีในอุปกรณ์ของคุณ");
                map.put(str8, "รับแอปพลิเคชัน YouTube");
                map.put(str7, "เปิดใช้งานแอป YouTube");
                map.put(str6, "แอปพลิเคชันนี้จะไม่ทำงานจนกว่าคุณจะเปิดใช้งานแอปพลิเคชัน YouTube");
                map.put(str5, "เปิดใช้งานแอป YouTube");
                map.put(str4, "อัปเดตแอปพลิเคชัน YouTube");
                map.put(str3, "แอปพลิเคชันนี้จะไม่ทำงานจนกว่าคุณจะอัปเดตแอปพลิเคชัน YouTube");
                map.put(str2, "อัปเดตแอปพลิเคชัน YouTube");
            } else if ("tl".equals(str)) {
                map.put(str11, "May naganap na error habang sinisimulan ang player ng YouTube.");
                map.put(str10, "Kunin ang YouTube App");
                map.put(str9, "Hindi gagana ang app na ito nang wala ang YouTube App, na nawawala sa iyong device");
                map.put(str8, "Kunin ang YouTube App");
                map.put(str7, "Paganahin ang YouTube App");
                map.put(str6, "Hindi gagana ang app na ito maliban kung paganahin mo ang YouTube App.");
                map.put(str5, "Paganahin ang YouTube App");
                map.put(str4, "I-update ang YouTube App");
                map.put(str3, "Hindi gagana ang app na ito maliban kung i-update mo ang YouTube App.");
                map.put(str2, "I-update ang YouTube App");
            } else if ("tr".equals(str)) {
                map.put(str11, "YouTube oynatıcısı başlatılırken bir hata oluştu.");
                map.put(str10, "YouTube Uygulamasını edinin");
                map.put(str9, "Cihazınızda bulunmayan YouTube Uygulaması olmadan bu uygulama çalışmaz");
                map.put(str8, "YouTube Uygulamasını edinin");
                map.put(str7, "YouTube Uygulamasını etkinleştirin");
                map.put(str6, "YouTube Uygulamasını etkinleştirmediğiniz sürece bu uygulama çalışmaz.");
                map.put(str5, "YouTube Uygulamasını etkinleştirin");
                map.put(str4, "YouTube Uygulamasını güncelleyin");
                map.put(str3, "YouTube Uygulaması güncellenmedikçe bu uygulama çalışmaz.");
                map.put(str2, "YouTube Uygulamasını güncelle");
            } else if ("uk".equals(str)) {
                map.put(str11, "Під час ініціалізації програвача YouTube сталася помилка.");
                map.put(str10, "Отримати програму YouTube");
                map.put(str9, "Ця програма не запуститься без програми YouTube, яку не встановлено на вашому пристрої");
                map.put(str8, "Отримати програму YouTube");
                map.put(str7, "Увімк. програму YouTube");
                map.put(str6, "Ця програма не працюватиме, поки ви не ввімкнете програму YouTube.");
                map.put(str5, "Увімк. програму YouTube");
                map.put(str4, "Оновити програму YouTube");
                map.put(str3, "Ця програма не працюватиме, поки ви не оновите програму YouTube.");
                map.put(str2, "Оновити програму YouTube");
            } else if ("vi".equals(str)) {
                map.put(str11, "Đã xảy ra lỗi trong khi khởi chạy trình phát YouTube.");
                map.put(str10, "Tải ứng dụng YouTube");
                map.put(str9, "Ứng dụng này sẽ không chạy nếu không có ứng dụng YouTube, ứng dụng này bị thiếu trong thiết bị của bạn");
                map.put(str8, "Tải ứng dụng YouTube");
                map.put(str7, "Bật ứng dụng YouTube");
                map.put(str6, "Ứng dụng này sẽ không hoạt động trừ khi bạn bật ứng dụng YouTube.");
                map.put(str5, "Bật ứng dụng YouTube");
                map.put(str4, "Cập nhật ứng dụng YouTube");
                map.put(str3, "Ứng dụng này sẽ không hoạt động trừ khi bạn cập nhật ứng dụng YouTube.");
                map.put(str2, "Cập nhật ứng dụng YouTube");
            } else if ("zh_CN".equals(str)) {
                map.put(str11, "初始化 YouTube 播放器时出现错误。");
                map.put(str10, "获取 YouTube 应用");
                map.put(str9, "您的设备中没有 YouTube 应用，您必须先安装 YouTube 应用才能运行此应用。");
                map.put(str8, "获取 YouTube 应用");
                map.put(str7, "启用 YouTube 应用");
                map.put(str6, "您需要启用 YouTube 应用才能运行该应用。");
                map.put(str5, "启用 YouTube 应用");
                map.put(str4, "更新 YouTube 应用");
                map.put(str3, "您必须更新 YouTube 应用才能运行此应用。");
                map.put(str2, "更新 YouTube 应用");
            } else if ("zh_TW".equals(str)) {
                map.put(str11, "初始化 YouTube 播放器時發生錯誤。");
                map.put(str10, "取得 YouTube 應用程式");
                map.put(str9, "您必須啟用 YouTube 應用程式，這個應用程式才能運作，但系統在裝置中找不到 YouTube 應用程式。");
                map.put(str8, "取得 YouTube 應用程式");
                map.put(str7, "啟用 YouTube 應用程式");
                map.put(str6, "您必須啟用 YouTube 應用程式，這個應用程式才能運作。");
                map.put(str5, "啟用 YouTube 應用程式");
                map.put(str4, "更新 YouTube 應用程式");
                map.put(str3, "您必須更新 YouTube 應用程式，這個應用程式才能運作。");
                map.put(str2, "更新 YouTube 應用程式");
            } else {
                if ("zu".equals(str)) {
                    map.put(str11, "Kuvele iphutha ngenkathi kuqaliswa isidlali se-YouTube");
                    map.put(str10, "Thola uhlelo lokusebenza lwe-YouTube");
                    map.put(str9, "Lolu hlelo kusebenza angeke lusebenze ngaphandle kohlelo lokusebenza lwe-YouTube, olungekho kudivayisi yakho");
                    map.put(str8, "Thola uhelo lokusebenza lwe-YouTube");
                    map.put(str7, "Nika amandla uhlelo lokusebenza lwe-YouTube");
                    map.put(str6, "Lolu hlelo lokusebenza angeke lusebenze uma unganikanga amandla uhlelo lokusebenza lwe-YouTube.");
                    map.put(str5, "Nika amandla uhlelo lokusebenza lwe-YouTube");
                    map.put(str4, "Buyekeza uhlelo lokusebenza lwe-YouTube");
                    map.put(str3, "Lolu hlelo lokusebenza angeke lusebenze uma ungabuyekezanga uhlelo lokusebenza lwe-YouTube.");
                    map.put(str2, "Buyekeza uhlelo lokusebenza lwe-YouTube");
                }
            }
        }
    }
}
