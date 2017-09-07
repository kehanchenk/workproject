package com.wyyc.mymvpframe.injector.component;

import com.wyyc.mymvpframe.injector.module.ApplicationModule;
import com.wyyc.mymvpframe.injector.module.HttpModule;
import com.wyyc.network.api.ZqqApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zqq on 2017/6/20.
 *
 * @Singleton 因为HttpModule 模块中提供的方法是 Singleton ，所以此处必须添加单例 否则无法对外提供单例 报错
 */
@Singleton
@Component(modules = {ApplicationModule.class, HttpModule.class})
public interface ApplicationComponent {

    ZqqApi getZqqApi();

}
