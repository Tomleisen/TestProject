package com.tom.commonframework.common.net;


import androidx.annotation.NonNull;

import com.lkn.net.utils.AppInstanceUtils;
import com.lkn.net.utils.NetWorkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Tomleisen on 2017/8/15.
 */

public class ConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (NetWorkUtils.isConnected(AppInstanceUtils.INSTANCE)) {
            return chain.proceed(chain.request());
        } else {
            throw new RuntimeException("网络不给力，请稍后再试");
        }
    }
}
