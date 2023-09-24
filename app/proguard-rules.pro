# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.book.auto.driver.presentation.home.HomeFragment
-keep class com.book.auto.driver.utils.BaseFragment
-keep class com.book.auto.driver.presentation.home.HomeActivity
-keep class com.book.auto.driver.driver** { *; }
-keep class com.book.auto.driver.utils.Utils
-keep class com.book.auto.driver.utils.Constants
-keep class com.book.auto.driver.utils.PermissionUtils
-keep class com.book.auto.driver.utils.RequestCode
-keep class com.book.auto.driver.domain.BVApi
-keep class com.book.auto.driver.data.remote.BVApiImp
-keep class com.book.auto.driver.data.remote.reqres.BasicResponse
-keep class com.book.auto.driver.data.remote.reqres.DeleteVehicleRequest
-keep class com.book.auto.driver.data.remote.reqres.GetVehicleByGmailIdRequest
-keep class com.book.auto.driver.data.remote.reqres.Vehicle
-keep class com.book.auto.driver.data.remote.reqres.VehicleLocationRequest
-keep class com.book.auto.driver.data.remote.reqres.VehicleRequest
-keep class com.book.auto.driver.data.remote.reqres.VehicleResponse
-keep class com.book.auto.driver.data.DataStore
-keep class com.book.auto.driver.data.DataStorePref
-keep class com.book.auto.driver.data.remote.BVService


-keep class com.book.auto.driver.* {*;}
-keep class com.book.auto.driver.presentation.addeditauto.AddEditAutoFragment
-keep class com.book.auto.driver.presentation.home.HomeViewModel


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
-keep class com.book.auto.driver.utils.FBS
-keep class com.book.auto.driver.utils.Utils