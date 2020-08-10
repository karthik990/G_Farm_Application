package com.google.api.client.http;

import java.io.IOException;

public interface HttpIOExceptionHandler {
    boolean handleIOException(HttpRequest httpRequest, boolean z) throws IOException;
}
