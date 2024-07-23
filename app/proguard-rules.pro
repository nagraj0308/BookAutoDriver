-keep class com.bluetooth.printer.data.remote.reqres.*{*;}
-keep class com.bluetooth.printer.data.remote.*{*;}
-keep class com.bluetooth.printer.data.*{*;}
-keep class com.bluetooth.printer.di.*{*;}
-keep class com.bluetooth.printer.domain.*{*;}
-keep class com.bluetooth.printer.view.login.*{*;}
-keep class com.bluetooth.printer.view.addeditauto.*{*;}
-keep class com.bluetooth.printer.view.base.*{*;}
-keep class com.bluetooth.printer.view.vehicle.*{*;}
-keep class com.bluetooth.printer.view.home.*{*;}
-keep class com.bluetooth.printer.view.myhouse.*{*;}
-keep class com.bluetooth.printer.view.synclocation.*{*;}
-keep class com.bluetooth.printer.view.*{*;}
-keep class com.bluetooth.printer.utils.*{*;}
-keep class com.bluetooth.printer.*{*;}


-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class com.google.gson.stream.** { *; }

-keep public class * implements java.lang.reflect.Type

