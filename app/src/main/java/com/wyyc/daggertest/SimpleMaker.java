package com.wyyc.daggertest;

import javax.inject.Inject;

/**
 * Created by zqq on 2017/3/30.
 * 制作咖啡实现类
 */
public class SimpleMaker implements CoffeeMaker {

    Cooker mCooker;  //需要咖啡师制作咖啡


    @Inject
    public SimpleMaker(Cooker cooker) {

        //咖啡制作需要持有Cooker对象
        this.mCooker = cooker;

    }

    @Override
    public String makeCoffee() {

        return mCooker.makeCoffee();
    }
}
