package com.wyyc.daggertest.module;

import com.wyyc.daggertest.CoffeeMaker;
import com.wyyc.daggertest.Cooker;
import com.wyyc.daggertest.SimpleMaker;
import com.wyyc.daggertest.TitleFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zqq on 2017/3/30.
 */

@Module
public class SimpleModule {

    //Module中创建实例的方法是Provider进行标注的，Component在搜索到目标类中用Inject标注的属性后，Component就会去Module中去
//    查找用Provider标注的对应的创建类实例方法，这样完成整个依赖注入过程。

    @Provides
    Cooker providerCooker(TitleFactory factory) {

        return new Cooker(factory, "natie");
    }

    @Provides
    CoffeeMaker providerCoffeeMaker(Cooker cooker) {
        return new SimpleMaker(cooker);
    }




}
