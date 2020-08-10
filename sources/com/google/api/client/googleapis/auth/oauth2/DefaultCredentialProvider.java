package com.google.api.client.googleapis.auth.oauth2;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessControlException;
import java.util.Locale;

class DefaultCredentialProvider extends SystemEnvironmentProvider {
    static final String APP_ENGINE_CREDENTIAL_CLASS = "com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential$AppEngineCredentialWrapper";
    static final String CLOUDSDK_CONFIG_DIRECTORY = "gcloud";
    static final String CLOUD_SHELL_ENV_VAR = "DEVSHELL_CLIENT_PORT";
    static final String CREDENTIAL_ENV_VAR = "GOOGLE_APPLICATION_CREDENTIALS";
    static final String HELP_PERMALINK = "https://developers.google.com/accounts/docs/application-default-credentials";
    static final String WELL_KNOWN_CREDENTIALS_FILE = "application_default_credentials.json";
    private GoogleCredential cachedCredential = null;
    private Environment detectedEnvironment = null;

    /* renamed from: com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider$1 */
    static /* synthetic */ class C28911 {

        /* renamed from: $SwitchMap$com$google$api$client$googleapis$auth$oauth2$DefaultCredentialProvider$Environment */
        static final /* synthetic */ int[] f1782x52b60e20 = new int[Environment.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider$Environment[] r0 = com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.Environment.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1782x52b60e20 = r0
                int[] r0 = f1782x52b60e20     // Catch:{ NoSuchFieldError -> 0x0014 }
                com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider$Environment r1 = com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.Environment.ENVIRONMENT_VARIABLE     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = f1782x52b60e20     // Catch:{ NoSuchFieldError -> 0x001f }
                com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider$Environment r1 = com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.Environment.WELL_KNOWN_FILE     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = f1782x52b60e20     // Catch:{ NoSuchFieldError -> 0x002a }
                com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider$Environment r1 = com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.Environment.APP_ENGINE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = f1782x52b60e20     // Catch:{ NoSuchFieldError -> 0x0035 }
                com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider$Environment r1 = com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.Environment.CLOUD_SHELL     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = f1782x52b60e20     // Catch:{ NoSuchFieldError -> 0x0040 }
                com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider$Environment r1 = com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.Environment.COMPUTE_ENGINE     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.C28911.<clinit>():void");
        }
    }

    private static class ComputeGoogleCredential extends GoogleCredential {
        private static final String TOKEN_SERVER_ENCODED_URL;

        static {
            StringBuilder sb = new StringBuilder();
            sb.append(OAuth2Utils.getMetadataServerUrl());
            sb.append("/computeMetadata/v1/instance/service-accounts/default/token");
            TOKEN_SERVER_ENCODED_URL = sb.toString();
        }

        ComputeGoogleCredential(HttpTransport httpTransport, JsonFactory jsonFactory) {
            super(new Builder().setTransport(httpTransport).setJsonFactory(jsonFactory).setTokenServerEncodedUrl(TOKEN_SERVER_ENCODED_URL));
        }

        /* access modifiers changed from: protected */
        public TokenResponse executeRefreshToken() throws IOException {
            HttpRequest buildGetRequest = getTransport().createRequestFactory().buildGetRequest(new GenericUrl(getTokenServerEncodedUrl()));
            JsonObjectParser jsonObjectParser = new JsonObjectParser(getJsonFactory());
            buildGetRequest.setParser(jsonObjectParser);
            buildGetRequest.getHeaders().set("Metadata-Flavor", (Object) "Google");
            buildGetRequest.setThrowExceptionOnExecuteError(false);
            HttpResponse execute = buildGetRequest.execute();
            int statusCode = execute.getStatusCode();
            if (statusCode == 200) {
                InputStream content = execute.getContent();
                if (content != null) {
                    return (TokenResponse) jsonObjectParser.parseAndClose(content, execute.getContentCharset(), TokenResponse.class);
                }
                throw new IOException("Empty content from metadata token server request.");
            } else if (statusCode == 404) {
                throw new IOException(String.format("Error code %s trying to get security access token from Compute Engine metadata for the default service account. This may be because the virtual machine instance does not have permission scopes specified.", new Object[]{Integer.valueOf(statusCode)}));
            } else {
                throw new IOException(String.format("Unexpected Error code %s trying to get security access token from Compute Engine metadata for the default service account: %s", new Object[]{Integer.valueOf(statusCode), execute.parseAsString()}));
            }
        }
    }

    private enum Environment {
        UNKNOWN,
        ENVIRONMENT_VARIABLE,
        WELL_KNOWN_FILE,
        CLOUD_SHELL,
        APP_ENGINE,
        COMPUTE_ENGINE
    }

    DefaultCredentialProvider() {
    }

    /* access modifiers changed from: 0000 */
    public final GoogleCredential getDefaultCredential(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        synchronized (this) {
            if (this.cachedCredential == null) {
                this.cachedCredential = getDefaultCredentialUnsynchronized(httpTransport, jsonFactory);
            }
            if (this.cachedCredential != null) {
                GoogleCredential googleCredential = this.cachedCredential;
                return googleCredential;
            }
            throw new IOException(String.format("The Application Default Credentials are not available. They are available if running on Google App Engine, Google Compute Engine, or Google Cloud Shell. Otherwise, the environment variable %s must be defined pointing to a file defining the credentials. See %s for more information.", new Object[]{CREDENTIAL_ENV_VAR, HELP_PERMALINK}));
        }
    }

    private final GoogleCredential getDefaultCredentialUnsynchronized(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        if (this.detectedEnvironment == null) {
            this.detectedEnvironment = detectEnvironment(httpTransport);
        }
        int i = C28911.f1782x52b60e20[this.detectedEnvironment.ordinal()];
        if (i == 1) {
            return getCredentialUsingEnvironmentVariable(httpTransport, jsonFactory);
        }
        if (i == 2) {
            return getCredentialUsingWellKnownFile(httpTransport, jsonFactory);
        }
        if (i == 3) {
            return getAppEngineCredential(httpTransport, jsonFactory);
        }
        if (i == 4) {
            return getCloudShellCredential(jsonFactory);
        }
        if (i != 5) {
            return null;
        }
        return getComputeCredential(httpTransport, jsonFactory);
    }

    private final File getWellKnownCredentialsFile() {
        File file;
        String str = "";
        int indexOf = getProperty("os.name", str).toLowerCase(Locale.US).indexOf("windows");
        String str2 = CLOUDSDK_CONFIG_DIRECTORY;
        if (indexOf >= 0) {
            file = new File(new File(getEnv("APPDATA")), str2);
        } else {
            file = new File(new File(getProperty("user.home", str), ".config"), str2);
        }
        return new File(file, WELL_KNOWN_CREDENTIALS_FILE);
    }

    /* access modifiers changed from: 0000 */
    public boolean fileExists(File file) {
        return file.exists() && !file.isDirectory();
    }

    /* access modifiers changed from: 0000 */
    public String getProperty(String str, String str2) {
        return System.getProperty(str, str2);
    }

    /* access modifiers changed from: 0000 */
    public Class<?> forName(String str) throws ClassNotFoundException {
        return Class.forName(str);
    }

    private final Environment detectEnvironment(HttpTransport httpTransport) throws IOException {
        if (runningUsingEnvironmentVariable()) {
            return Environment.ENVIRONMENT_VARIABLE;
        }
        if (runningUsingWellKnownFile()) {
            return Environment.WELL_KNOWN_FILE;
        }
        if (useGAEStandardAPI()) {
            return Environment.APP_ENGINE;
        }
        if (runningOnCloudShell()) {
            return Environment.CLOUD_SHELL;
        }
        if (OAuth2Utils.runningOnComputeEngine(httpTransport, this)) {
            return Environment.COMPUTE_ENGINE;
        }
        return Environment.UNKNOWN;
    }

    private boolean runningUsingEnvironmentVariable() throws IOException {
        String str = CREDENTIAL_ENV_VAR;
        String env = getEnv(str);
        if (!(env == null || env.length() == 0)) {
            try {
                File file = new File(env);
                if (file.exists() && !file.isDirectory()) {
                    return true;
                }
                throw new IOException(String.format("Error reading credential file from environment variable %s, value '%s': File does not exist.", new Object[]{str, env}));
            } catch (AccessControlException unused) {
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.api.client.googleapis.auth.oauth2.GoogleCredential getCredentialUsingEnvironmentVariable(com.google.api.client.http.HttpTransport r7, com.google.api.client.json.JsonFactory r8) throws java.io.IOException {
        /*
            r6 = this;
            java.lang.String r0 = "GOOGLE_APPLICATION_CREDENTIALS"
            java.lang.String r1 = r6.getEnv(r0)
            r2 = 0
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x001c }
            r3.<init>(r1)     // Catch:{ IOException -> 0x001c }
            com.google.api.client.googleapis.auth.oauth2.GoogleCredential r7 = com.google.api.client.googleapis.auth.oauth2.GoogleCredential.fromStream(r3, r7, r8)     // Catch:{ IOException -> 0x0017, all -> 0x0014 }
            r3.close()
            return r7
        L_0x0014:
            r7 = move-exception
            r2 = r3
            goto L_0x003f
        L_0x0017:
            r7 = move-exception
            r2 = r3
            goto L_0x001d
        L_0x001a:
            r7 = move-exception
            goto L_0x003f
        L_0x001c:
            r7 = move-exception
        L_0x001d:
            java.io.IOException r8 = new java.io.IOException     // Catch:{ all -> 0x001a }
            java.lang.String r3 = "Error reading credential file from environment variable %s, value '%s': %s"
            r4 = 3
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x001a }
            r5 = 0
            r4[r5] = r0     // Catch:{ all -> 0x001a }
            r0 = 1
            r4[r0] = r1     // Catch:{ all -> 0x001a }
            r0 = 2
            java.lang.String r1 = r7.getMessage()     // Catch:{ all -> 0x001a }
            r4[r0] = r1     // Catch:{ all -> 0x001a }
            java.lang.String r0 = java.lang.String.format(r3, r4)     // Catch:{ all -> 0x001a }
            r8.<init>(r0)     // Catch:{ all -> 0x001a }
            java.lang.Throwable r7 = com.google.api.client.googleapis.auth.oauth2.OAuth2Utils.exceptionWithCause(r8, r7)     // Catch:{ all -> 0x001a }
            java.io.IOException r7 = (java.io.IOException) r7     // Catch:{ all -> 0x001a }
            throw r7     // Catch:{ all -> 0x001a }
        L_0x003f:
            if (r2 == 0) goto L_0x0044
            r2.close()
        L_0x0044:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.getCredentialUsingEnvironmentVariable(com.google.api.client.http.HttpTransport, com.google.api.client.json.JsonFactory):com.google.api.client.googleapis.auth.oauth2.GoogleCredential");
    }

    private boolean runningUsingWellKnownFile() {
        try {
            return fileExists(getWellKnownCredentialsFile());
        } catch (AccessControlException unused) {
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.api.client.googleapis.auth.oauth2.GoogleCredential getCredentialUsingWellKnownFile(com.google.api.client.http.HttpTransport r6, com.google.api.client.json.JsonFactory r7) throws java.io.IOException {
        /*
            r5 = this;
            java.io.File r0 = r5.getWellKnownCredentialsFile()
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x001a }
            r2.<init>(r0)     // Catch:{ IOException -> 0x001a }
            com.google.api.client.googleapis.auth.oauth2.GoogleCredential r6 = com.google.api.client.googleapis.auth.oauth2.GoogleCredential.fromStream(r2, r6, r7)     // Catch:{ IOException -> 0x0015, all -> 0x0012 }
            r2.close()
            return r6
        L_0x0012:
            r6 = move-exception
            r1 = r2
            goto L_0x0034
        L_0x0015:
            r6 = move-exception
            r1 = r2
            goto L_0x001b
        L_0x0018:
            r6 = move-exception
            goto L_0x0034
        L_0x001a:
            r6 = move-exception
        L_0x001b:
            java.io.IOException r7 = new java.io.IOException     // Catch:{ all -> 0x0018 }
            java.lang.String r2 = "Error reading credential file from location %s: %s"
            r3 = 2
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0018 }
            r4 = 0
            r3[r4] = r0     // Catch:{ all -> 0x0018 }
            r0 = 1
            java.lang.String r6 = r6.getMessage()     // Catch:{ all -> 0x0018 }
            r3[r0] = r6     // Catch:{ all -> 0x0018 }
            java.lang.String r6 = java.lang.String.format(r2, r3)     // Catch:{ all -> 0x0018 }
            r7.<init>(r6)     // Catch:{ all -> 0x0018 }
            throw r7     // Catch:{ all -> 0x0018 }
        L_0x0034:
            if (r1 == 0) goto L_0x0039
            r1.close()
        L_0x0039:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.api.client.googleapis.auth.oauth2.DefaultCredentialProvider.getCredentialUsingWellKnownFile(com.google.api.client.http.HttpTransport, com.google.api.client.json.JsonFactory):com.google.api.client.googleapis.auth.oauth2.GoogleCredential");
    }

    private boolean useGAEStandardAPI() {
        boolean z = false;
        try {
            try {
                Field field = forName("com.google.appengine.api.utils.SystemProperty").getField("environment");
                if (field.getType().getMethod(Param.VALUE, new Class[0]).invoke(field.get(null), new Object[0]) != null) {
                    z = true;
                }
                return z;
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                throw ((RuntimeException) OAuth2Utils.exceptionWithCause(new RuntimeException(String.format("Unexpcted error trying to determine if runnning on Google App Engine: %s", new Object[]{e.getMessage()})), e));
            }
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    private final GoogleCredential getAppEngineCredential(HttpTransport httpTransport, JsonFactory jsonFactory) throws IOException {
        String str = APP_ENGINE_CREDENTIAL_CLASS;
        try {
            return (GoogleCredential) forName(str).getConstructor(new Class[]{HttpTransport.class, JsonFactory.class}).newInstance(new Object[]{httpTransport, jsonFactory});
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw ((IOException) OAuth2Utils.exceptionWithCause(new IOException(String.format("Application Default Credentials failed to create the Google App Engine service account credentials class %s. Check that the component 'google-api-client-appengine' is deployed.", new Object[]{str})), e));
        }
    }

    private boolean runningOnCloudShell() {
        return getEnv(CLOUD_SHELL_ENV_VAR) != null;
    }

    private GoogleCredential getCloudShellCredential(JsonFactory jsonFactory) {
        return new CloudShellCredential(Integer.parseInt(getEnv(CLOUD_SHELL_ENV_VAR)), jsonFactory);
    }

    private final GoogleCredential getComputeCredential(HttpTransport httpTransport, JsonFactory jsonFactory) {
        return new ComputeGoogleCredential(httpTransport, jsonFactory);
    }
}
