package com.tom.commonframework.common.base.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by TomLeisen on 2018/1/27 下午6:14
 * Email: xy162162a@163.com
 * Description: EventBus统一管理工具类
 */

public class EventBusUtils {

    public static void register(Object context){
        if (!EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().register(context);
        }
    }
    public static void unregister(Object context){
        if (EventBus.getDefault().isRegistered(context)) {
            EventBus.getDefault().unregister(context);
        }
    }
    public static void sendEvent(Object object){
        EventBus.getDefault().post(object);
    }

}
