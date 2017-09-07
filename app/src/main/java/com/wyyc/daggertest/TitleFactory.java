package com.wyyc.daggertest;

/**
 * Created by zqq on 2017/3/31.
 */
public class TitleFactory {

    private String name;
    private String mString;


    public TitleFactory(String name, String mString) {
        this.name = name;
        this.mString = mString;
    }

    public String getName() {

        return name+mString;
    }

}
