package com.wyyc.daggertest;

import javax.inject.Inject;

/**
 * Created by zqq on 2017/3/30.
 */
public class CoffeeMachine {


    static {
        String describe = " 咖啡制作过程------咖啡机制作，需要一个咖啡机 ";
    }

    CoffeeMaker mMaker;

    @Inject
    public CoffeeMachine(CoffeeMaker coffeeMaker) {

        //构造方法持有咖啡制作实现类
//        mMaker=new SimpleMaker(cooker);
        this.mMaker = coffeeMaker;
    }


    /**
     * 制作咖啡
     */
    public String makeCoffee() {

        String coffee = mMaker.makeCoffee();
        return coffee;
    }
}
