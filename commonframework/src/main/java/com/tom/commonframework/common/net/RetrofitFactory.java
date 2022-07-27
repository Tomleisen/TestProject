package com.tom.commonframework.common.net;


import androidx.annotation.NonNull;

import com.lkn.net.creator.ServiceGenerator;
import com.lkn.net.interceptor.UpLoadProgressInterceptor;
import com.tom.commonframework.common.http.UrlBuilder;
import com.tom.commonframework.common.http.constant.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
/**
 * Created by TomLeisen on 2017/7/25
 * Email: xy162162a@163.com
 * Description:
 */
public class RetrofitFactory {

    public static OkHttpClient.Builder okHttpClientBuilder;

    public static <T> T create(Class<T> clazz, boolean isHttps) {
        String url = getBaseUrl(isHttps);
        OkHttpClient.Builder httpClientBuilder = getOkHttpClientBuilder(-1);
        return ServiceGenerator.createService(httpClientBuilder, clazz, url, Response.class);
    }

    /**
     * @param clazz
     * @param timeOut 设置超时时长，单位为毫秒
     */
    public static <T> T create(Class<T> clazz, boolean isHttps, long timeOut) {
        String url = getBaseUrl(isHttps);
        OkHttpClient.Builder httpClientBuilder = getOkHttpClientBuilder(timeOut);
        return ServiceGenerator.createService(httpClientBuilder, clazz, url, Response.class);
    }


    public static <T> T createUpload(Class<T> clazz, boolean isHttps, UpLoadProgressInterceptor.UploadListener uploadListener) {
        String url = getBaseUrl(isHttps);
        return ServiceGenerator.createService(clazz, url, Response.class,uploadListener);
    }

    /**
     * @param timeOut 设置超时时长，单位为毫秒
     */
    public static <T> T createUpload(Class<T> clazz, boolean isHttps, UpLoadProgressInterceptor.UploadListener uploadListener, long timeOut) {
        String url = getBaseUrl(isHttps);
        return ServiceGenerator.createService(clazz, url, Response.class, uploadListener, timeOut);
    }

    @NonNull
    private static OkHttpClient.Builder getOkHttpClientBuilder(long timeOut) {
        if (okHttpClientBuilder == null) {
            okHttpClientBuilder = ServiceGenerator.getDefaultOkHttp();
        }

        if (timeOut != -1) {
            // 设置了超时时长
            okHttpClientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS);
            okHttpClientBuilder.readTimeout(timeOut, TimeUnit.MILLISECONDS);
            okHttpClientBuilder.writeTimeout(timeOut, TimeUnit.MILLISECONDS);
        } else {
            // 未设置超时时长，使用默认的
//            okHttpClientBuilder.connectTimeout(30L, TimeUnit.SECONDS);
            okHttpClientBuilder.connectTimeout(5L, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(15L, TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(20L, TimeUnit.SECONDS);
        }

        //日志拦截
        HttpLogInterceptor logInterceptor = new HttpLogInterceptor();
        logInterceptor.setLevel(HttpLogInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logInterceptor);

        // 暂时默认都是表单形式
        okHttpClientBuilder.addInterceptor(new HeaderInterceptor(Constants.Http.ContentType.CONTENT_TYPE_JSON)).addInterceptor(new ConnectionInterceptor());
        return okHttpClientBuilder;
    }

    private static String getBaseUrl(boolean isHttps) {
        return new UrlBuilder().getUrl("", (isHttps ? Constants.REQUEST_TYPE.HTTPS : Constants.REQUEST_TYPE.HTTP));
    }
}