package com.test.testproject.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.lkn.net.utils.ActivityManager;
import com.test.testproject.BuildConfig;
import com.tom.commonframework.common.http.LocalInfoControlCenter;

import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.unit.Subunits;


/**
 * Created by TomLeisen on 2022/7/22 4:42 下午
 * Email: xy162162a@163.com
 * Description:
 */
public class BaseApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //初始化适配，对单位的自定义配置
        configUnits();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //设置线程的优先级，不与主线程抢资源
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                if (isMainProcess()) {//判断在APP进程才进行注册
                    initSdk();
                }
            }
        }).start();

    }

    private void initSdk() {
        initEnvParams();//初始环境的配置
        initActivityLifeCb();// 初始化activity生命周期监听器
    }

    /**
     * 初始环境的配置
     */
    private void initEnvParams() {
        // 环境初始化
        LocalInfoControlCenter.INSTANCES.setLog_debug(BuildConfig.LOG_DEBUG);
        LocalInfoControlCenter.INSTANCES.setEnv(BuildConfig.ENVIRONMENT);
        LocalInfoControlCenter.INSTANCES.init(this);
    }

    private boolean isMainProcess() {
        try {
            android.app.ActivityManager am = (android.app.ActivityManager) getSystemService(ACTIVITY_SERVICE);//app运行状态管理权限
            if (am.getRunningAppProcesses() == null) {
                return true;
            }
            int pid = android.os.Process.myPid();//当前运行时的进程id
            String packageName = getPackageName();
            for (android.app.ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == pid && packageName.equals(info.processName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 初始化activity生命周期监听器
     */
    private void initActivityLifeCb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    ActivityManager.INSTANCE.setCurrentActivity(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {
                }

                @Override
                public void onActivityResumed(Activity activity) {
                    ActivityManager.INSTANCE.setCurrentActivity(activity);
                }

                @Override
                public void onActivityPaused(Activity activity) {
                }

                @Override
                public void onActivityStopped(Activity activity) {
                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                }
            });
        }
    }

    //初始化适配，对单位的自定义配置
    public void configUnits() {
        AutoSizeConfig.getInstance()
                .setCustomFragment(true)
                .getUnitsManager()
                .setSupportDP(false)
                .setSupportSP(false)
                .setSupportSubunits(Subunits.MM);
    }

} 

