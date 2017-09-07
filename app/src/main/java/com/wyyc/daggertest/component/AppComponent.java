package com.wyyc.daggertest.component;

import com.wyyc.App;
import com.wyyc.daggertest.TitleFactory;
import com.wyyc.daggertest.module.AppModule;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/31.
 */

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);

    TitleFactory getFactory();//需要使用这个将需要提供全局的变量提供出去
}
