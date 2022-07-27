package com.tom.commonframework.common.net;

import android.content.Context;
import android.text.TextUtils;

import com.lkn.net.utils.AppInstanceUtils;
import com.tom.commonframework.common.http.constant.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.ParametersAreNonnullByDefault;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tomleisen on 2017/7/26.
 */

public class HeaderInterceptor implements Interceptor {
    private Context context;
    private String contentType;

    public HeaderInterceptor(String contentType) {
        this.contentType = contentType;
        context = AppInstanceUtils.INSTANCE;
    }

    @Override
    public Response intercept(@ParametersAreNonnullByDefault Chain chain) throws IOException {
        Request originalRequest = chain.request();
        RequestBody body = originalRequest.body();
        Request compressedRequest = originalRequest.newBuilder()
                .headers(Headers.of(getHeaders()))
                .method(originalRequest.method(), body)
                .build();
        return chain.proceed(compressedRequest);
    }

    private Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
        if (!TextUtils.isEmpty(contentType) && contentType.equals(Constants.Http.ContentType.CONTENT_TYPE_JSON)) {
            headers.put("Content-Type", Constants.Http.ContentType.CONTENT_TYPE_JSON);
        }
        return headers;
    }

}
