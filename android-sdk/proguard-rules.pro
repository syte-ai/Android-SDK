-keepparameternames
-keepclasseswithmembernames,includedescriptorclasses class * {
    public <methods>;
}

-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
-keep class me.shouheng.compress.** { *; }
-keep class androidx.exifinterface.** { *; }
-keep class androidx.security.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


-keep interface * { *; }

-keep class com.syte.ai.android_sdk.* {
    public<methods>;
    public<fields>;
}
-keep class com.syte.ai.android_sdk.data.** { *; }
-keep class com.syte.ai.android_sdk.enums.* {
    public<methods>;
    public<fields>;
}
-keep class com.syte.ai.android_sdk.events.* {
    public<methods>;
    public<fields>;
}
-keep class com.syte.ai.android_sdk.exceptions.* {
    public<methods>;
    public<fields>;
}
-keep class com.syte.ai.android_sdk.util.* {
    public<methods>;
    public<fields>;
}
-keep class com.syte.ai.android_sdk.core.InitSyte {
    public<methods>;
}
-keep class com.syte.ai.android_sdk.core.SyteConfiguration {
    public<init>(android.content.Context, java.lang.String, java.lang.String);
    void setLocale(java.lang.String);
    java.lang.String getLocale();
    java.lang.String getAccountId();
    java.lang.String getApiSignature();
    java.lang.String getUserId();
    java.lang.Long getSessionId();
    boolean getAllowAutoCompletionQueue();
    void setAllowAutoCompletionQueue(boolean);
}