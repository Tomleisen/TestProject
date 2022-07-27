package com.test.testproject.apiInterface;

import com.tom.commonframework.common.net.Response;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by TomLeisen on 2019-12-30 10:35
 * Email: xy162162a@163.com
 * Description:
 */

public interface ApiInterface {

    /**
     * 推荐App
     * https://itunes.apple.com/hk/rss/topgrossingapplications/limit=${limit}/json
     */
    @GET("rss/topgrossingapplications/limit={limit}/json")
    Flowable<Response<Object>> getTopGrossingapplications(@Path("limit") int num);


    /**
     * App列表
     * https://itunes.apple.com/hk/rss/topfreeapplications/limit=${limit}/json
     */
    @GET("rss/topfreeapplications/limit={limit}/json")
    Flowable<Response<Object>> getTopFreeapplications(@Path("limit") int num);


    /**
     * 搜索
     * https://itunes.apple.com/hk/lookup?id=${id}
     */
    @GET("lookup")
    Flowable<Response<Object>> getLookup(@Query("id") String id);


}
