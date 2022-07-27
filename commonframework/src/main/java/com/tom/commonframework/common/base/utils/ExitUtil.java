package com.tom.commonframework.common.base.utils;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import com.tom.commonframework.common.base.ExitEvent;


/**
 * Created by TomLeisen on 2019/5/4 下午6:03
 * Email: xy162162a@163.com
 * Description: 点击返回退出登录
 */
public class ExitUtil {
    private static int mOnClickedCancelCount;
    private static Handler exitHandler = new Handler();

    public static void exitApp(Activity context) {
        try {
            if (mOnClickedCancelCount == 0) {
                Toast.makeText(context.getApplicationContext(), "再按一次退出程序", Toast.LENGTH_LONG).show();
                exitHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mOnClickedCancelCount = 0;
                    }
                }, 2000);
                mOnClickedCancelCount++;
            } else {
                EventBusUtils.sendEvent(new ExitEvent());
                //  killAll();//不能杀进程，无法收到推送
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
