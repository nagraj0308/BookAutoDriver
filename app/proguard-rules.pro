-keep class com.book.auto.driver.data.remote.reqres.*{*;}
-keep class com.book.auto.driver.data.remote.*{*;}
-keep class com.book.auto.driver.data.*{*;}
-keep class com.book.auto.driver.di.*{*;}
-keep class com.book.auto.driver.domain.*{*;}
-keep class com.book.auto.driver.presentation.home.*{*;}
-keep class com.book.auto.driver.presentation.login.*{*;}
-keep class com.book.auto.driver.presentation.addeditauto.*{*;}
-keep class com.book.auto.driver.presentation.base.*{*;}
-keep class com.book.auto.driver.presentation.vehicle.*{*;}
-keep class com.book.auto.driver.presentation.home.*{*;}
-keep class com.book.auto.driver.presentation.login.*{*;}
-keep class com.book.auto.driver.presentation.synclocation.*{*;}
-keep class com.book.auto.driver.presentation.*{*;}
-keep class com.book.auto.driver.utils.*{*;}
-keep class com.book.auto.driver.*{*;}


-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keepattributes Signature
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

-keep class com.google.gson.reflect.TypeToken
-keep class * extends com.google.gson.reflect.TypeToken
-keep public class * implements java.lang.reflect.Type


# Retain generic signatures of TypeToken and its subclasses with R8 version 3.0 and higher.
-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken