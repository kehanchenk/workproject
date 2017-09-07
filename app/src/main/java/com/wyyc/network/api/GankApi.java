package com.wyyc.network.api;

import com.wyyc.bean.GankBeanResult;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/22.
 */
public interface GankApi {

    //@path  动态url请求  参数替 number  page

    @GET("data/福利/{number}/{page}")
    Observable<GankBeanResult> getGankImage(@Path("number") int number, @Path("page") int page);
}
