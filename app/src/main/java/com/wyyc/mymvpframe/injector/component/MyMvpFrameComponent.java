package com.wyyc.mymvpframe.injector.component;

import com.wyyc.mymvpframe.injector.PerActivity;
import com.wyyc.mymvpframe.injector.module.MyMvpFrameModule;
import com.wyyc.mymvpframe.ui.news.MyMvpFrameActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/6/20.
 *
 * 此处因为 ApplicationComponent 被定义成 Singleton , MyMvpFrameComponent 依赖于它，
 * 被依赖对象 Scope 的 级别不能高于 Singleton 需要自定义 Scope
 */
@PerActivity
@Component(dependencies =ApplicationComponent.class,modules = MyMvpFrameModule.class)
public interface MyMvpFrameComponent {

    void inject(MyMvpFrameActivity activity);

}
