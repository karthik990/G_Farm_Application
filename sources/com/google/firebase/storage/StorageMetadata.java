package com.google.firebase.storage;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.storage.internal.SlashUtil;
import com.google.firebase.storage.internal.Util;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
public class StorageMetadata {
    private static final String BUCKET_KEY = "bucket";
    private static final String CACHE_CONTROL = "cacheControl";
    private static final String CONTENT_DISPOSITION = "contentDisposition";
    private static final String CONTENT_ENCODING = "contentEncoding";
    private static final String CONTENT_LANGUAGE = "contentLanguage";
    private static final String CONTENT_TYPE_KEY = "contentType";
    private static final String CUSTOM_METADATA_KEY = "metadata";
    private static final String GENERATION_KEY = "generation";
    private static final String MD5_HASH_KEY = "md5Hash";
    private static final String META_GENERATION_KEY = "metageneration";
    private static final String NAME_KEY = "name";
    private static final String SIZE_KEY = "size";
    private static final String TAG = "StorageMetadata";
    private static final String TIME_CREATED_KEY = "timeCreated";
    private static final String TIME_UPDATED_KEY = "updated";
    /* access modifiers changed from: private */
    public String mBucket;
    /* access modifiers changed from: private */
    public MetadataValue<String> mCacheControl;
    /* access modifiers changed from: private */
    public MetadataValue<String> mContentDisposition;
    /* access modifiers changed from: private */
    public MetadataValue<String> mContentEncoding;
    /* access modifiers changed from: private */
    public MetadataValue<String> mContentLanguage;
    /* access modifiers changed from: private */
    public MetadataValue<String> mContentType;
    /* access modifiers changed from: private */
    public String mCreationTime;
    /* access modifiers changed from: private */
    public MetadataValue<Map<String, String>> mCustomMetadata;
    /* access modifiers changed from: private */
    public String mGeneration;
    /* access modifiers changed from: private */
    public String mMD5Hash;
    /* access modifiers changed from: private */
    public String mMetadataGeneration;
    /* access modifiers changed from: private */
    public String mPath;
    /* access modifiers changed from: private */
    public long mSize;
    private FirebaseStorage mStorage;
    /* access modifiers changed from: private */
    public StorageReference mStorageRef;
    /* access modifiers changed from: private */
    public String mUpdatedTime;

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    public static class Builder {
        boolean mFromJSON;
        StorageMetadata mMetadata;

        public Builder() {
            this.mMetadata = new StorageMetadata();
        }

        public Builder(StorageMetadata storageMetadata) {
            this.mMetadata = new StorageMetadata(false);
        }

        Builder(JSONObject jSONObject, StorageReference storageReference) throws JSONException {
            this(jSONObject);
            this.mMetadata.mStorageRef = storageReference;
        }

        Builder(JSONObject jSONObject) throws JSONException {
            this.mMetadata = new StorageMetadata();
            if (jSONObject != null) {
                parseJSON(jSONObject);
                this.mFromJSON = true;
            }
        }

        private String extractString(JSONObject jSONObject, String str) throws JSONException {
            if (!jSONObject.has(str) || jSONObject.isNull(str)) {
                return null;
            }
            return jSONObject.getString(str);
        }

        private void parseJSON(JSONObject jSONObject) throws JSONException {
            this.mMetadata.mGeneration = jSONObject.optString(StorageMetadata.GENERATION_KEY);
            this.mMetadata.mPath = jSONObject.optString("name");
            this.mMetadata.mBucket = jSONObject.optString(StorageMetadata.BUCKET_KEY);
            this.mMetadata.mMetadataGeneration = jSONObject.optString(StorageMetadata.META_GENERATION_KEY);
            this.mMetadata.mCreationTime = jSONObject.optString(StorageMetadata.TIME_CREATED_KEY);
            this.mMetadata.mUpdatedTime = jSONObject.optString(StorageMetadata.TIME_UPDATED_KEY);
            this.mMetadata.mSize = jSONObject.optLong(StorageMetadata.SIZE_KEY);
            this.mMetadata.mMD5Hash = jSONObject.optString(StorageMetadata.MD5_HASH_KEY);
            String str = "metadata";
            if (jSONObject.has(str) && !jSONObject.isNull(str)) {
                JSONObject jSONObject2 = jSONObject.getJSONObject(str);
                Iterator keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String str2 = (String) keys.next();
                    setCustomMetadata(str2, jSONObject2.getString(str2));
                }
            }
            String extractString = extractString(jSONObject, StorageMetadata.CONTENT_TYPE_KEY);
            if (extractString != null) {
                setContentType(extractString);
            }
            String extractString2 = extractString(jSONObject, StorageMetadata.CACHE_CONTROL);
            if (extractString2 != null) {
                setCacheControl(extractString2);
            }
            String extractString3 = extractString(jSONObject, StorageMetadata.CONTENT_DISPOSITION);
            if (extractString3 != null) {
                setContentDisposition(extractString3);
            }
            String extractString4 = extractString(jSONObject, StorageMetadata.CONTENT_ENCODING);
            if (extractString4 != null) {
                setContentEncoding(extractString4);
            }
            String extractString5 = extractString(jSONObject, StorageMetadata.CONTENT_LANGUAGE);
            if (extractString5 != null) {
                setContentLanguage(extractString5);
            }
        }

        public StorageMetadata build() {
            return new StorageMetadata(this.mFromJSON);
        }

        public Builder setContentLanguage(String str) {
            this.mMetadata.mContentLanguage = MetadataValue.withUserValue(str);
            return this;
        }

        public Builder setContentEncoding(String str) {
            this.mMetadata.mContentEncoding = MetadataValue.withUserValue(str);
            return this;
        }

        public Builder setContentDisposition(String str) {
            this.mMetadata.mContentDisposition = MetadataValue.withUserValue(str);
            return this;
        }

        public Builder setCacheControl(String str) {
            this.mMetadata.mCacheControl = MetadataValue.withUserValue(str);
            return this;
        }

        public Builder setCustomMetadata(String str, String str2) {
            if (!this.mMetadata.mCustomMetadata.isUserProvided()) {
                this.mMetadata.mCustomMetadata = MetadataValue.withUserValue(new HashMap());
            }
            ((Map) this.mMetadata.mCustomMetadata.getValue()).put(str, str2);
            return this;
        }

        public Builder setContentType(String str) {
            this.mMetadata.mContentType = MetadataValue.withUserValue(str);
            return this;
        }
    }

    /* compiled from: com.google.firebase:firebase-storage@@17.0.0 */
    private static class MetadataValue<T> {
        private final boolean userProvided;
        private final T value;

        MetadataValue(T t, boolean z) {
            this.userProvided = z;
            this.value = t;
        }

        static <T> MetadataValue<T> withDefaultValue(T t) {
            return new MetadataValue<>(t, false);
        }

        static <T> MetadataValue<T> withUserValue(T t) {
            return new MetadataValue<>(t, true);
        }

        /* access modifiers changed from: 0000 */
        public boolean isUserProvided() {
            return this.userProvided;
        }

        /* access modifiers changed from: 0000 */
        public T getValue() {
            return this.value;
        }
    }

    public StorageMetadata() {
        this.mPath = null;
        this.mStorage = null;
        this.mStorageRef = null;
        this.mBucket = null;
        this.mGeneration = null;
        String str = "";
        this.mContentType = MetadataValue.withDefaultValue(str);
        this.mMetadataGeneration = null;
        this.mCreationTime = null;
        this.mUpdatedTime = null;
        this.mMD5Hash = null;
        this.mCacheControl = MetadataValue.withDefaultValue(str);
        this.mContentDisposition = MetadataValue.withDefaultValue(str);
        this.mContentEncoding = MetadataValue.withDefaultValue(str);
        this.mContentLanguage = MetadataValue.withDefaultValue(str);
        this.mCustomMetadata = MetadataValue.withDefaultValue(Collections.emptyMap());
    }

    private StorageMetadata(StorageMetadata storageMetadata, boolean z) {
        this.mPath = null;
        this.mStorage = null;
        this.mStorageRef = null;
        this.mBucket = null;
        this.mGeneration = null;
        String str = "";
        this.mContentType = MetadataValue.withDefaultValue(str);
        this.mMetadataGeneration = null;
        this.mCreationTime = null;
        this.mUpdatedTime = null;
        this.mMD5Hash = null;
        this.mCacheControl = MetadataValue.withDefaultValue(str);
        this.mContentDisposition = MetadataValue.withDefaultValue(str);
        this.mContentEncoding = MetadataValue.withDefaultValue(str);
        this.mContentLanguage = MetadataValue.withDefaultValue(str);
        this.mCustomMetadata = MetadataValue.withDefaultValue(Collections.emptyMap());
        Preconditions.checkNotNull(storageMetadata);
        this.mPath = storageMetadata.mPath;
        this.mStorage = storageMetadata.mStorage;
        this.mStorageRef = storageMetadata.mStorageRef;
        this.mBucket = storageMetadata.mBucket;
        this.mContentType = storageMetadata.mContentType;
        this.mCacheControl = storageMetadata.mCacheControl;
        this.mContentDisposition = storageMetadata.mContentDisposition;
        this.mContentEncoding = storageMetadata.mContentEncoding;
        this.mContentLanguage = storageMetadata.mContentLanguage;
        this.mCustomMetadata = storageMetadata.mCustomMetadata;
        if (z) {
            this.mMD5Hash = storageMetadata.mMD5Hash;
            this.mSize = storageMetadata.mSize;
            this.mUpdatedTime = storageMetadata.mUpdatedTime;
            this.mCreationTime = storageMetadata.mCreationTime;
            this.mMetadataGeneration = storageMetadata.mMetadataGeneration;
            this.mGeneration = storageMetadata.mGeneration;
        }
    }

    public String getContentType() {
        return (String) this.mContentType.getValue();
    }

    public String getCustomMetadata(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (String) ((Map) this.mCustomMetadata.getValue()).get(str);
    }

    public Set<String> getCustomMetadataKeys() {
        return ((Map) this.mCustomMetadata.getValue()).keySet();
    }

    public String getPath() {
        String str = this.mPath;
        return str != null ? str : "";
    }

    public String getName() {
        String path = getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        int lastIndexOf = path.lastIndexOf(47);
        if (lastIndexOf != -1) {
            path = path.substring(lastIndexOf + 1);
        }
        return path;
    }

    public String getBucket() {
        return this.mBucket;
    }

    public String getGeneration() {
        return this.mGeneration;
    }

    public String getMetadataGeneration() {
        return this.mMetadataGeneration;
    }

    public long getCreationTimeMillis() {
        return Util.parseDateTime(this.mCreationTime);
    }

    public long getUpdatedTimeMillis() {
        return Util.parseDateTime(this.mUpdatedTime);
    }

    public long getSizeBytes() {
        return this.mSize;
    }

    public String getMd5Hash() {
        return this.mMD5Hash;
    }

    public String getCacheControl() {
        return (String) this.mCacheControl.getValue();
    }

    public String getContentDisposition() {
        return (String) this.mContentDisposition.getValue();
    }

    public String getContentEncoding() {
        return (String) this.mContentEncoding.getValue();
    }

    public String getContentLanguage() {
        return (String) this.mContentLanguage.getValue();
    }

    public StorageReference getReference() {
        if (this.mStorageRef != null || this.mStorage == null) {
            return this.mStorageRef;
        }
        String bucket = getBucket();
        String path = getPath();
        if (TextUtils.isEmpty(bucket) || TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            return new StorageReference(new android.net.Uri.Builder().scheme("gs").authority(bucket).encodedPath(SlashUtil.preserveSlashEncode(path)).build(), this.mStorage);
        } catch (UnsupportedEncodingException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to create a valid default Uri. ");
            sb.append(bucket);
            sb.append(path);
            Log.e(TAG, sb.toString(), e);
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: 0000 */
    public JSONObject createJSONObject() throws JSONException {
        HashMap hashMap = new HashMap();
        if (this.mContentType.isUserProvided()) {
            hashMap.put(CONTENT_TYPE_KEY, getContentType());
        }
        if (this.mCustomMetadata.isUserProvided()) {
            hashMap.put("metadata", new JSONObject((Map) this.mCustomMetadata.getValue()));
        }
        if (this.mCacheControl.isUserProvided()) {
            hashMap.put(CACHE_CONTROL, getCacheControl());
        }
        if (this.mContentDisposition.isUserProvided()) {
            hashMap.put(CONTENT_DISPOSITION, getContentDisposition());
        }
        if (this.mContentEncoding.isUserProvided()) {
            hashMap.put(CONTENT_ENCODING, getContentEncoding());
        }
        if (this.mContentLanguage.isUserProvided()) {
            hashMap.put(CONTENT_LANGUAGE, getContentLanguage());
        }
        return new JSONObject(hashMap);
    }
}
