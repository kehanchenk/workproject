package com.wyyc.mymvpframe.injector.module;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zqq on 2017/6/20.
 * 在application 中提供的 单例模式使用@Singleton
 */
@Module
public class ApplicationModule {


    private Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    public Application providerApplication() {

        return mApplication;

    }

    @Provides
    @Singleton
    public Gson providerGson() {
        return new Gson();
    }


}
