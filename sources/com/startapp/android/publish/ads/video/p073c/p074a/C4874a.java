package com.startapp.android.publish.ads.video.p073c.p074a;

import org.apache.http.HttpStatus;

/* renamed from: com.startapp.android.publish.ads.video.c.a.a */
/* compiled from: StartAppSDK */
public enum C4874a {
    ErrorNone(0),
    XMLParsingError(100),
    SchemaValidationError(101),
    VersionOfResponseNotSupported(102),
    TraffickingError(200),
    VideoPlayerExpectingDifferentLinearity(201),
    VideoPlayerExpectingDifferentDuration(202),
    VideoPlayerExpectingDifferentSize(203),
    AdCategoryRequired(204),
    GeneralWrapperError(300),
    WrapperTimeout(301),
    WrapperLimitReached(302),
    WrapperNoReponse(303),
    InlineResponseTimeout(304),
    GeneralLinearError(400),
    FileNotFound(401),
    TimeoutMediaFileURI(HttpStatus.SC_PAYMENT_REQUIRED),
    MediaNotSupported(403),
    MediaFileDisplayError(405),
    MezzanineNotPovided(HttpStatus.SC_NOT_ACCEPTABLE),
    MezzanineDownloadInProgrees(HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED),
    ConditionalAdRejected(HttpStatus.SC_REQUEST_TIMEOUT),
    InteractiveCreativeFileNotExecuted(409),
    VerificationNotExecuted(HttpStatus.SC_GONE),
    MezzanineNotAsExpected(HttpStatus.SC_LENGTH_REQUIRED),
    GeneralNonLinearAdsError(500),
    CreativeTooLarge(HttpStatus.SC_NOT_IMPLEMENTED),
    ResourceDownloadFailed(502),
    NonLinearResourceNotSupported(503),
    GeneralCompanionAdsError(600),
    CompanionTooLarge(601),
    CompanionNotDisplay(602),
    CompanionFetchFailed(603),
    CompanionNotSupported(604),
    UndefinedError(900),
    GeneralVPAIDerror(901),
    SAShowBeforeVast(10000),
    SAProcessSuccess(20000);
    
    private int value;

    private C4874a(int i) {
        this.value = i;
    }

    /* renamed from: a */
    public int mo61698a() {
        return this.value;
    }
}
