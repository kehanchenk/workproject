// (c)2016 Flipboard Inc, All Rights Reserved.

package com.wyyc;

import android.app.Application;

import com.wyyc.mymvpframe.injector.component.ApplicationComponent;
import com.wyyc.mymvpframe.injector.component.DaggerApplicationComponent;
import com.wyyc.mymvpframe.injector.module.ApplicationModule;
import com.wyyc.mymvpframe.injector.module.HttpModule;

public class App extends Application {
    private static App INSTANCE;

//    private AppComponent mAppComponent;

    private ApplicationComponent mAppComponent;

    public static App getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
//        mApplicationComponent.builder().applicationModule(new ApplicationModule(this))
//                .build();

//        mAppComponent = DaggerAppComponent.builder().build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }

    //    public AppComponent getAppComponent() {
//        return mAppComponent;
//    }
}
