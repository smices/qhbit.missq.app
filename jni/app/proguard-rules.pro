# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/syxc/Application/android-sdk-macosx/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the ProGuard
# include property in project.properties.
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

#-keep class com.buybuychat.android.bms.misc.WebAppInterface

-keepclassmembers class com.buybuychat.android.bms.misc.WebAppInterface {
   public *;
}


# Picasso
-dontwarn com.squareup.okhttp.**


# okio
-dontwarn okio.**


# Volley
-keep class com.android.volley.** { *; }
-keep class com.android.volley.toolbox.** { *; }
-keep class com.android.volley.Response$* { *; }
-keep class com.android.volley.Request$* { *; }
-keep class com.android.volley.RequestQueue$* { *; }
-keep class com.android.volley.toolbox.HurlStack$* { *; }
-keep class com.android.volley.toolbox.ImageLoader$* { *; }


# ButterKnife
-dontwarn butterknife.internal.**
-keep class **$$ViewInjector { *; }
-keepnames class * { @butterknife.InjectView *;}


# EventBus
-keepclassmembers class ** {
    public void onEvent*(**);
}

# Only required if you use AsyncExecutor
#-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
#    (java.lang.Throwable);
#}

# GreenDao
#-keep class com.manjay.housebox.greendao.** { *; }
#-keepclassmembers class * extends de.greenrobot.dao.AbstractDao {
#    public static java.lang.String TABLENAME;
#}
#-keep class **$Properties


# UIL
-keep class com.nostra13.universalimageloader.** { *; }
-keepclassmembers class com.nostra13.universalimageloader.** {*;}


# Pretty time
-keep public class com.ocpsoft.pretty.time.**
# fixes java.util.MissingResourceException: Can't find resource
# for bundle 'org.ocpsoft.prettytime.i18n.Resources_en_US'
-keep class org.ocpsoft.prettytime.i18n.**


# AutoScrollViewPager
#-keep class cn.trinea.android.** { *; }
#-keepclassmembers class cn.trinea.android.** { *; }
#-dontwarn cn.trinea.android.**


# https://code.google.com/p/google-gson/source/browse/trunk/examples/android-proguard-example/proguard.cfg

##---------------Begin: proguard configuration common for all Android apps ----------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump build/class_files.txt
-printseeds build/seeds.txt
-printusage build/unused.txt
-printmapping build/mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-dontnote com.android.vending.licensing.ILicensingService

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * {
    public protected *;
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.idkin.android.idkclient.model.** { *; }

##---------------End: proguard configuration for Gson  ----------


# com.esotoricsoftware.
# Kryo and kryonet
#-dontwarn com.esotericsoftware.kryo.**
#-dontwarn com.esotericsoftware.shaded.**


# proguard-paypal.cnf
# ---- REQUIRED card.io CONFIG ----------------------------------------
# card.io is a native lib, so anything crossing JNI must not be changed

# Don't obfuscate DetectionInfo or public fields, since
# it is used by native methods
-keep class io.card.payment.DetectionInfo
-keepclassmembers class io.card.payment.DetectionInfo {
    public *;
}

-keep class io.card.payment.CreditCard
-keep class io.card.payment.CreditCard$1
-keepclassmembers class io.card.payment.CreditCard {
	*;
}

-keepclassmembers class io.card.payment.CardScanner {
	*** onEdgeUpdate(...);
}

# Don't mess with classes with native methods

-keepclasseswithmembers class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class io.card.payment.* {
    public protected *;
}

# -------- PayPal SDK ----------
# (does not include card.io)

-keep public class com.paypal.android.sdk.payments.* {
	public *;
}


-dontwarn com.google.android.gms.**


-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry

-dontwarn org.objenesis.instantiator.sun.UnsafeFactoryInstantiator



# -------- zxing begin --------

-optimizations !code/simplification/cast,!field/*,!class/merging/*,!code/allocation/variable,!method/marking/private
-optimizationpasses 5
-allowaccessmodification
-dontpreverify

# The remainder of this file is identical to the non-optimized version
# of the Proguard configuration file (except that the other file has
# flags to turn off optimization).

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# ADDED
-dontshrink
-dontobfuscate

-keepattributes *Annotation*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

# -------- zxing end --------