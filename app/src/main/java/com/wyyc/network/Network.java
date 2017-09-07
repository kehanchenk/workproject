package com.wyyc.network;

import com.wyyc.network.api.FakeApi;
import com.wyyc.network.api.GankApi;
import com.wyyc.network.api.ZqqApi;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zqq on 2017/3/22.
 */
public class Network {

    private static ZqqApi mZqqApi;
    private static GankApi mgankApi;
    private static FakeApi mfakeApi;
    private static OkHttpClient okHttpClient=new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();


    public static ZqqApi getZqqApi() {

        if (mZqqApi == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.zhuangbi.info/")
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();

            mZqqApi = retrofit.create(ZqqApi.class);
        }
        return mZqqApi;
    }

    public static GankApi getGankApi() {

        if (mgankApi == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://gank.io/api/")
                    .client(okHttpClient)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();

            mgankApi = retrofit.create(GankApi.class);

        }

        return mgankApi;
    }


    public static FakeApi getFakeApi() {

        if (mfakeApi == null) {

            mfakeApi=new FakeApi();
        }

        return mfakeApi;
    }


}
