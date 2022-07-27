package com.tom.commonframework.common.http;

import android.content.Context;


/**
 * Created by Tomleisen on 2016/9/18 0018.
 */
public enum LocalInfoControlCenter {

    INSTANCES;

    private int env;
    private boolean log_debug;
    private Context context;

    public void init(Context context) {
        this.context = context;
    }

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
        this.env = env;
    }

    public boolean isLog_debug() {
        return log_debug;
    }

    public void setLog_debug(boolean log_debug) {
        this.log_debug = log_debug;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
