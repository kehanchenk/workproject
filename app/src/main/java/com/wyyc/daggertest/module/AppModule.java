package com.wyyc.daggertest.module;

import com.wyyc.daggertest.TitleFactory;
import com.wyyc.daggertest.annotation.Stringone;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/31.
 */

@Module
public class AppModule {


    @Provides
    public TitleFactory provideTitleFactory(@Stringone String name, String title) {

        //在Application中,需要全局提供一个Factory
        return new TitleFactory(name,title);
    }

    @Provides
    public String providerString() {

        return "Tom";
    }

    //@Qualifier 是为了区别同一个Module中相同返回类型的，在参数使用的位置和提供的方法同时加入该注解用于区分采用。
    @Provides
    @Stringone
    public String provideTitle() {

        return "title";
    }

}
