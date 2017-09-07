package com.wyyc.mymvpframe.data.remote;

import com.wyyc.mymvpframe.adapter.item.NewsInfo;
import com.wyyc.network.api.ZqqApi;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zqq on 2017/6/20.
 */
public class MyMvpFrameModel {

    private ZqqApi mZqqApi;

    public MyMvpFrameModel(ZqqApi zqqApi) {
        mZqqApi = zqqApi;
    }

    public void getData(String param, Subscriber<List<NewsInfo>> subscriber) {
        mZqqApi.search_z(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
}
