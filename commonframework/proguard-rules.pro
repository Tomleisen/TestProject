
# 设置混淆的压缩比率 0 ~ 7
-optimizationpasses 5
# 混淆后类名都为小写   Aa aA
-dontusemixedcaseclassnames
# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses

#不做预校验的操作
-dontpreverify
# 混淆时不记录日志
-verbose
# 混淆采用的算法.
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保留代码行号，方便异常信息的追踪
-keepattributes SourceFile,LineNumberTable
#dump文件列出apk包内所有class的内部结构
-dump class_files.txt
#seeds.txt文件列出未混淆的类和成员
-printseeds seeds.txt
#usage.txt文件列出从apk中删除的代码
-printusage unused.txt
#mapping文件列出混淆前后的映射
-printmapping mapping.txt


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment


#不提示V4包下错误警告
-dontwarn android.support.v4.**
#保持下面的V4兼容包的类不被混淆
-keep class android.support.v4.**{*;}

-dontwarn android.support.v7.**
-keep class android.support.v7.**{*;}

#避免混淆所有native的方法,涉及到C、C++
-keepclasseswithmembernames class * {
    native <methods>;
}

#避免混淆自定义控件类的get/set方法和构造函数
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context,android.util.AttributeSet);
    public <init>(android.content.Context,android.util.AttributeSet,int);
}
#避免混淆枚举类
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#不混淆Parcelable和它的实现子类，还有Creator成员变量
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#不混淆Serializable和它的实现子类、其成员变量
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#使用GSON、fastjson等框架时，所写的JSON对象类不混淆，否则无法将JSON解析成对应的对象
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}

#bugly
-dontwarn com.tencent.bugly.**
-keep public interface com.tencent.**
-keep public class com.tencent.** {*;}
-keep public class com.tencent.bugly.**{*;}


-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String);
}

#百度定位
-keep class vi.com.gdi.** { *; }
-keep public class com.baidu.** {*;}
-keep public class com.mobclick.** {*;}
-dontwarn com.baidu.mapapi.utils.*
-dontwarn com.baidu.platform.comapi.b.*
-dontwarn com.baidu.platform.comapi.map.*

#EventBus
#-keep class org.greenrobot.** {*;}
#-keep class de.greenrobot.** {*;}
-keepclassmembers class ** {
     void onEvent*(**);
    void onEvent*(**);
}
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# okhttp
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-keep class okio.**{*;}
-dontwarn okio.**


#避免混淆属性动画兼容库
-dontwarn com.nineoldandroids.*
-keep class com.nineoldandroids.** { *;}

#不混淆泛型
-keepattributes Signature
#避免混淆注解类
-dontwarn android.annotation
-keepattributes *Annotation*

#避免混淆内部类
-keep class *.R$ {*;}
-keepattributes InnerClasses

#避免混淆实体类，修改成你对应的包名
#避免混淆Rxjava/RxAndroid
-dontwarn sun.misc.**

#避免混淆js相关的接口
-keepattributes *JavascriptInterface*
#-keep class com.wyk.test.js.** { *; }

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}


-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-dontnote org.apache.commons.**
-dontnote android.net.compatibility.**

-dontwarn android.net.http.*
-dontwarn org.apache.commons.codec.**
-dontwarn org.apache.http.**
-dontwarn org.apache.commons.**
-dontwarn android.net.compatibility.**


-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

-dontwarn com.android.databinding.**
-keep class com.android.databinding.** { *; }

-keep class * extends android.databinding.ViewDataBinding{
*;
}


-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*




-dontwarn android.support.**
-keep class android.support.** { *; }

#-dontwarn com.bumptech.**
#-keep class com.bumptech.** { *; }

-dontwarn com.jakewharton.**
-keep class com.jakewharton.** { *; }


-dontwarn com.meituan.**
-keep class com.meituan.** { *; }


-dontwarn com.orhanobut.**
-keep class com.orhanobut.** { *; }

-dontwarn com.qiniu.**
-keep class com.qiniu.** { *; }




-dontwarn com.squareup.**
-keep class com.squareup.** { *; }


-dontwarn com.trello.**
-keep class com.trello.** { *; }


-dontwarn com.umeng.**
-keep class com.umeng.** { *; }



-dontwarn de.hdodenhof.**
-keep class de.hdodenhof.** { *; }



-dontwarn io.reactivex.**
-keep class io.reactivex.** { *; }


-dontwarn org.hamcrest.**
-keep class org.hamcrest.** { *; }


-dontwarn com.baidu.**
-keep class com.baidu.** { *; }

-dontwarn vi.com.gdi.bgl.android.java.**
-keep class vi.com.gdi.bgl.android.java.** { *; }

-dontwarn com.example.exceptiondemo.**
-keep class com.example.exceptiondemo.** { *; }


-dontwarn com.alibaba.fastjson.**
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses
-keep class com.alibaba.fastjson.** { *; }

-dontwarn com.baidu.location.**
-keep class com.baidu.location.** { *; }


-dontwarn com.mob.logcollector.**
-keep class com.mob.logcollector.** { *; }

-dontwarn com.mob.tools.**
-keep class com.mob.tools.** { *; }

-dontwarn com.github.mikephil.charting.**
-keep class com.github.mikephil.charting.** { *; }



-dontwarn com.tencent.**
-keep class com.tencent.** { *; }
-dontwarn MTT.**
-keep class MTT.** { *; }

-keep class org.jsoup.**{*;}

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
# Retrofit
-dontwarn retrofit2.Platform$Java8
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn okhttp3.logging.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# RxJava RxAndroid
-dontwarn sun.misc.**

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod


-dontwarn com.uuzuche.**
-keep class com.uuzuche.** { *; }


-dontwarn com.google.**
-keep class com.google.gson.** {*;}


-dontwarn com.android.internal.**
-dontnote com.android.internal.**
-keep class com.android.internal.R$*{*;}

#高德地图
#3D 地图 V5.0.0之前：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
-keep   class com.amap.api.trace.**{*;}
#3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
#搜索
-keep   class com.amap.api.services.**{*;}
#2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}
#友盟
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class [com.sunshine.mhs.pension.physician].R$*{
public static final int *;
}

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#glide如果你的API级别<=Android API 27 则需要添加
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule


# ShareSDK
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-keep class m.framework.**{*;}
-keep class com.bytedance.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
-dontwarn **.R$*

-keep class com.lkn.net.** { *; }
-keep class com.tom.commonframework.** { *; }
-keep class com.tom.socket.** { *; }
-keep class pl.droidsonroids.gif.** { *; }

#腾讯地图 start
-keepclassmembers class ** {
    public void on*Event(...);
}

-keep class c.t.**{*;}
-keep class com.tencent.map.geolocation.**{*;}

-dontwarn  org.eclipse.jdt.annotation.**
-dontwarn  c.t.**

-keep class com.tencent.tencentmap.**{*;}
-keep class com.tencent.map.**{*;}
-keep class com.tencent.beacontmap.**{*;}
-keep class navsns.**{*;}
-dontwarn com.qq.**
-dontwarn com.tencent.**
#腾讯地图 end

#阿里一键登录start
 -keep class cn.com.chinatelecom.** {*;}
 -keep class com.unicom.xiaowo.login.** {*;}
 -keep class com.cmic.sso.sdk.** {*;}
 -keep class com.mobile.auth.** {*;}
 -keep class com.nirvana.** {*;}
 -keep class com.alibaba.fastjson.** {*;}
-keepclasseswithmembernames class * {
    native <methods>;
 }
 -keepclassmembers class * {
    @android.support.annotation.Keep <fields>;
    @android.support.annotation.Keep <methods>;
 }
 -keep @android.support.annotation.Keep class * {*;}
 -dontwarn

 # --- uc crash start ----（如果集成了UC crash收集组件需要增加这个配置）
 -keep class com.uc.crashsdk.** { *; }
 -keep interface com.uc.crashsdk.** { *; }
 # --- uc crash end ---
 -keepattributes Signature
 -keepattributes *Annotation*
#阿里一键登录end

#友盟统计start  https://developer.umeng.com/docs/119267/detail/118584
-keep class com.umeng.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#友盟统计end
