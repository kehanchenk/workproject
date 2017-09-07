package com.wyyc.bean;

/**
 * Created by Administrator on 2017/4/12.
 */
public class MvpBean {

    private String name;
    private int image;

    public MvpBean(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
