package com.tom.commonframework.common.base.utils;

/**
 * Created by TomLeisen on 2019-07-30 13:35
 * Email: xy162162a@163.com
 * Description:接收到后台返回的错误码为 10003 时的 EventBus  通知类
 */
public class LogOutEventBus {

    private String msg;//提示

    public LogOutEventBus() {
    }

    public LogOutEventBus(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
