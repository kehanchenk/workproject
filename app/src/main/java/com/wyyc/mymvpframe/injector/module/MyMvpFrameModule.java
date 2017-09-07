package com.wyyc.mymvpframe.injector.module;

import android.app.ProgressDialog;

import com.wyyc.mymvpframe.adapter.NewsAdapter;
import com.wyyc.mymvpframe.data.remote.MyMvpFrameModel;
import com.wyyc.mymvpframe.ui.news.MyMvpFrameActivity;
import com.wyyc.mymvpframe.ui.news.MyMvpFrameContract;
import com.wyyc.mymvpframe.ui.news.MyMvpFramepresenter;
import com.wyyc.network.api.ZqqApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zqq on 2017/6/20.
 */

@Module
public class MyMvpFrameModule {

    private MyMvpFrameContract.View mView;

    public MyMvpFrameModule(MyMvpFrameContract.View view) {
        mView = view;
    }

    @Provides
    public MyMvpFrameContract.View providerView() {

        return mView;
    }


    @Provides
    public MyMvpFramepresenter providerPresenter( MyMvpFrameModel model,MyMvpFrameContract.View view) {

        return new MyMvpFramepresenter(model,view);
    }

    @Provides
    public MyMvpFrameModel providerMvpFrameModel(ZqqApi zqqApi) {

        return new MyMvpFrameModel(zqqApi);
    }

    @Provides
    public NewsAdapter providerNewsAdapter() {

        return new NewsAdapter();
    }

    @Provides
    public ProgressDialog providerProgressDialog(MyMvpFrameContract.View view) {
        return new ProgressDialog((MyMvpFrameActivity) view);
    }

}
