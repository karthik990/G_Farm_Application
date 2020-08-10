package p043io.opencensus.contrib.http.util;

import javax.annotation.Nullable;
import org.apache.http.HttpStatus;
import p043io.opencensus.trace.Status;

/* renamed from: io.opencensus.contrib.http.util.HttpTraceUtil */
public final class HttpTraceUtil {
    private static final Status STATUS_100 = Status.UNKNOWN.withDescription("Continue");
    private static final Status STATUS_101 = Status.UNKNOWN.withDescription("Switching Protocols");
    private static final Status STATUS_402 = Status.UNKNOWN.withDescription("Payment Required");
    private static final Status STATUS_405 = Status.UNKNOWN.withDescription("Method Not Allowed");
    private static final Status STATUS_406 = Status.UNKNOWN.withDescription("Not Acceptable");
    private static final Status STATUS_407 = Status.UNKNOWN.withDescription("Proxy Authentication Required");
    private static final Status STATUS_408 = Status.UNKNOWN.withDescription("Request Time-out");
    private static final Status STATUS_409 = Status.UNKNOWN.withDescription("Conflict");
    private static final Status STATUS_410 = Status.UNKNOWN.withDescription("Gone");
    private static final Status STATUS_411 = Status.UNKNOWN.withDescription("Length Required");
    private static final Status STATUS_412 = Status.UNKNOWN.withDescription("Precondition Failed");
    private static final Status STATUS_413 = Status.UNKNOWN.withDescription("Request Entity Too Large");
    private static final Status STATUS_414 = Status.UNKNOWN.withDescription("Request-URI Too Large");
    private static final Status STATUS_415 = Status.UNKNOWN.withDescription("Unsupported Media Type");
    private static final Status STATUS_416 = Status.UNKNOWN.withDescription("Requested range not satisfiable");
    private static final Status STATUS_417 = Status.UNKNOWN.withDescription("Expectation Failed");
    private static final Status STATUS_500 = Status.UNKNOWN.withDescription("Internal Server Error");
    private static final Status STATUS_502 = Status.UNKNOWN.withDescription("Bad Gateway");
    private static final Status STATUS_505 = Status.UNKNOWN.withDescription("HTTP Version not supported");

    private HttpTraceUtil() {
    }

    public static final Status parseResponseStatus(int i, @Nullable Throwable th) {
        String str;
        if (th != null) {
            str = th.getMessage();
            if (str == null) {
                str = th.getClass().getSimpleName();
            }
        } else {
            str = null;
        }
        if (i == 0) {
            return Status.UNKNOWN.withDescription(str);
        }
        if (i >= 200 && i < 400) {
            return Status.f3765OK;
        }
        if (i == 100) {
            return STATUS_100;
        }
        if (i == 101) {
            return STATUS_101;
        }
        if (i == 429) {
            return Status.RESOURCE_EXHAUSTED.withDescription(str);
        }
        switch (i) {
            case 400:
                return Status.INVALID_ARGUMENT.withDescription(str);
            case 401:
                return Status.UNAUTHENTICATED.withDescription(str);
            case HttpStatus.SC_PAYMENT_REQUIRED /*402*/:
                return STATUS_402;
            case 403:
                return Status.PERMISSION_DENIED.withDescription(str);
            case 404:
                return Status.NOT_FOUND.withDescription(str);
            case 405:
                return STATUS_405;
            case HttpStatus.SC_NOT_ACCEPTABLE /*406*/:
                return STATUS_406;
            case HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED /*407*/:
                return STATUS_407;
            case HttpStatus.SC_REQUEST_TIMEOUT /*408*/:
                return STATUS_408;
            case 409:
                return STATUS_409;
            case HttpStatus.SC_GONE /*410*/:
                return STATUS_410;
            case HttpStatus.SC_LENGTH_REQUIRED /*411*/:
                return STATUS_411;
            case 412:
                return STATUS_412;
            case HttpStatus.SC_REQUEST_TOO_LONG /*413*/:
                return STATUS_413;
            case HttpStatus.SC_REQUEST_URI_TOO_LONG /*414*/:
                return STATUS_414;
            case HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE /*415*/:
                return STATUS_415;
            case HttpStatus.SC_REQUESTED_RANGE_NOT_SATISFIABLE /*416*/:
                return STATUS_416;
            case HttpStatus.SC_EXPECTATION_FAILED /*417*/:
                return STATUS_417;
            default:
                switch (i) {
                    case 500:
                        return STATUS_500;
                    case HttpStatus.SC_NOT_IMPLEMENTED /*501*/:
                        return Status.UNIMPLEMENTED.withDescription(str);
                    case 502:
                        return STATUS_502;
                    case 503:
                        return Status.UNAVAILABLE.withDescription(str);
                    case HttpStatus.SC_GATEWAY_TIMEOUT /*504*/:
                        return Status.DEADLINE_EXCEEDED.withDescription(str);
                    case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED /*505*/:
                        return STATUS_505;
                    default:
                        return Status.UNKNOWN.withDescription(str);
                }
        }
    }
}
