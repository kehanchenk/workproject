package com.wyyc.network.api;

import com.wyyc.bean.ElemenntBean;
import com.wyyc.mymvpframe.adapter.item.NewsInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zqq on 2017/3/22.
 */
public interface ZqqApi {

    public static final String BASE_URL = "http://www.zhuangbi.info/";

    //    http://www.zhuangbi.info/search'

    @GET("search")
    Observable<List<ElemenntBean>> search(@Query("q") String query);

    @GET("search")
    Observable<List<NewsInfo>> search_z(@Query("q") String query);


}
