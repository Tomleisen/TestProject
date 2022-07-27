package com.test.testproject.main.module;

import com.lkn.net.transformer.DefaultTransformer;
import com.test.testproject.apiInterface.ApiInterface;
import com.tom.commonframework.common.net.RetrofitSingleton;

import io.reactivex.Flowable;

/**
 * Created by TomLeisen on 2022/7/23 12:59 上午
 * Email: xy162162a@163.com
 * Description:
 */
public class MainModule {

    /**
     * 推荐App
     */
    public static Flowable<Object> getTopGrossingapplications(int num) {
        return RetrofitSingleton.getInstance()
                .getHttpApiService(ApiInterface.class)
                .getTopGrossingapplications(num)
                .compose(new DefaultTransformer<>());
    }

    /**
     * App列表
     */
    public static Flowable<Object> getTopFreeapplications(int num) {
        return RetrofitSingleton.getInstance()
                .getHttpApiService(ApiInterface.class)
                .getTopFreeapplications(num)
                .compose(new DefaultTransformer<>());
    }

    /**
     * 搜索
     */
    public static Flowable<Object> getLookup(String id) {
        return RetrofitSingleton.getInstance()
                .getHttpApiService(ApiInterface.class)
                .getLookup(id)
                .compose(new DefaultTransformer<>());
    }

} 

