package com.tom.commonframework.common.net;


import androidx.collection.ArrayMap;

import com.lkn.net.interceptor.UpLoadProgressInterceptor;

/**
 * Created by TomLeisen on 2017/8/10
 * Email: xy162162a@163.com
 * Description:
 */
public class RetrofitSingleton {

    private ArrayMap<String, Object> cacheHttpMap = new ArrayMap<>();
    private ArrayMap<String, Object> cacheHttpsMap = new ArrayMap<>();


    private static class SingletonHolder {
        private static final RetrofitSingleton INSTANCE = new RetrofitSingleton();
    }

    private RetrofitSingleton() {

    }

    public static RetrofitSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * HTTP
     */
    public <T> T getHttpApiService(Class<T> clazz) {
        Object result;
        if (cacheHttpMap.containsKey(clazz.getName())) {
            result = cacheHttpMap.get(clazz.getName());
        } else {
            result = RetrofitFactory.create(clazz, false);
            cacheHttpMap.put(clazz.getName(), result);
        }
        return (T) result;
    }

    /**
     * HTTP
     *
     * @param timeOut 设置超时时长，单位为毫秒
     */
    public <T> T getHttpApiService(Class<T> clazz, long timeOut) {
        Object result;
        if (cacheHttpMap.containsKey(clazz.getName())) {
            result = cacheHttpMap.get(clazz.getName());
        } else {
            result = RetrofitFactory.create(clazz, false, timeOut);
            cacheHttpMap.put(clazz.getName(), result);
        }
        return (T) result;
    }

    /**
     * HTTPS
     */
    public <T> T getHttpsApiService(Class<T> clazz) {
        Object result;
        if (cacheHttpsMap.containsKey(clazz.getName())) {
            result = cacheHttpsMap.get(clazz.getName());
        } else {
            result = RetrofitFactory.create(clazz, true);
            cacheHttpsMap.put(clazz.getName(), result);
        }
        return (T) result;
    }

    /**
     * HTTPS
     *
     * @param timeOut 设置超时时长，单位为毫秒
     */
    public <T> T getHttpsApiService(Class<T> clazz, long timeOut) {
        Object result;
        if (cacheHttpMap.containsKey(clazz.getName())) {
            result = cacheHttpMap.get(clazz.getName());
        } else {
            result = RetrofitFactory.create(clazz, true, timeOut);
            cacheHttpMap.put(clazz.getName(), result);
        }
        return (T) result;
    }


    public <T> T getUploadApiService(Class<T> clazz, UpLoadProgressInterceptor.UploadListener uploadListener) {
        Object result;
        if (cacheHttpMap.containsKey(clazz.getName())) {
            result = cacheHttpMap.get(clazz.getName());
        } else {
            result = RetrofitFactory.createUpload(clazz, false, uploadListener);
            cacheHttpMap.put(clazz.getName(), result);
        }
        return (T) result;
    }

    /**
     * @param timeOut 设置超时时长，单位为毫秒
     */
    public <T> T getUploadApiService(Class<T> clazz, long timeOut, UpLoadProgressInterceptor.UploadListener uploadListener) {
        Object result;
        if (cacheHttpMap.containsKey(clazz.getName())) {
            result = cacheHttpMap.get(clazz.getName());
        } else {
            result = RetrofitFactory.createUpload(clazz, false, uploadListener, timeOut);
            cacheHttpMap.put(clazz.getName(), result);
        }
        return (T) result;
    }

}
