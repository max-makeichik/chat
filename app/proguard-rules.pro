# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Program Files (x86)\Android\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-verbose

-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient
-dontwarn android.net.http.SslError

-dontwarn java.nio.file.Files
-dontwarn java.nio.file.Path
-dontwarn java.nio.file.OpenOption
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn com.google.android.gms.**
-dontwarn twitter4j.**
-dontwarn android.support.v4.**
-dontwarn android.support.v7.**
-dontwarn org.apache.commons.**
-dontwarn oauth.signpost.**
-dontwarn com.vk.sdk.**
-dontwarn ru.ok.android.sdk.**
-dontwarn com.squareup.**
-dontwarn okio.**

-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-keepattributes Signature
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class com.google.android.gms.** { *; }
-keep public class com.google.ads.** {*;}
-keep class com.facebook.** { *; }
-keep class com.vk.** { *; }
-keep class ru.ok.android.sdk.** { *; }
-keep class android.support.v7.internal.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

-keep class org.apache.http.** { *; }
-keep class org.apache.commons.** { *; }
-keep class android.net.compatibility.** { *; }
-keep class android.net.http.** { *; }
-keep class com.android.internal.http.multipart.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.webkit.**

# Add the gson class
-keep public class com.google.gson
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.example.chat.model.ChatMessage { *; }
-keep class com.example.chat.model.Message { *; }
-keep class com.example.chat.model.User { *; }

# For using GSON @Expose annotation
-keepattributes *Annotation*

-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}