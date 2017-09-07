package com.wyyc.daggertest;

/**
 * Created by Administrator on 2017/3/30.
 */
public class Cooker {

    TitleFactory name;  //咖啡师名字
    String coffeeKind;  //咖啡种类


    public Cooker(TitleFactory factory, String coffeeKind) {
        this.name = name;
        this.coffeeKind = coffeeKind;
    }


    public  String  makeCoffee() {

        return name.getName() + "make" + coffeeKind;  //咖啡师制作什么样的咖啡
    }

}
