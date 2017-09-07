package com.wyyc.daggertest.component;

import com.wyyc.daggertest.annotation.PerActivity;
import com.wyyc.daggertest.module.SimpleModule;
import com.wyyc.zqqworkproject.DaggerTestActivity;

import dagger.Component;

/**
 * Created by zqq on 2017/3/30.
 *
 * dependencies  是依赖关系 ，在实际项目中如果需要全局使用的类，可以在application中的
 */

@PerActivity
@Component(dependencies = AppComponent.class,modules = SimpleModule.class)
public interface SimpleComponent {

    void inject(DaggerTestActivity activity);

}
